package com.vehicle.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.vehicle.base.cache.CacheManager;
import com.vehicle.base.constants.Constants;
import com.vehicle.base.constants.UserStateEnum;
import com.vehicle.base.exception.BizException;
import com.vehicle.dto.res.weixin.AccessTokenResDto;
import com.vehicle.dto.res.weixin.Code2SessionResDto;
import com.vehicle.dto.res.weixin.SubscribeMessageSendReqDto;
import com.vehicle.dto.res.weixin.WxBaseResDto;
import com.vehicle.dto.vo.UserVo;
import com.vehicle.po.UserPo;
import com.vehicle.po.WxSessionPo;
import com.vehicle.transform.UserTransform;
import com.vehicle.utils.HttpClientUtil;
import com.vehicle.utils.InputStreamToBase64;
import com.vehicle.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.InputStream;
import java.security.AlgorithmParameters;
import java.security.Key;
import java.util.Base64;

/**
 * 微信服务
 * https://developers.weixin.qq.com/miniprogram/dev/OpenApiDoc/
 *
 * @author lijianbing
 * @date 2023/9/7 22:45
 */
@Service
@Slf4j
public class WechatService {

    @Value("${weixin.url}")
    private String url;

    @Value("${weixin.appid}")
    private String appid;

    @Value("${weixin.secret}")
    private String secret;

    @Autowired
    private WxSessionService wxSessionService;

    @Autowired
    private UserService userService;

    /**
     * 获取微信openid
     *
     * @param code
     * @return
     */
    public Code2SessionResDto jsCode2Session(String code) {
        String httpUrl = url + "/sns/jscode2session?appid=" + appid + "&secret=" + secret + "&js_code=" + code + "&grant_type=authorization_code";
        String res = HttpClientUtil.sendHttpGet(httpUrl);
        log.info("jsCode2Session req:{}, res:{}", code, res);
        Code2SessionResDto result = JSON.parseObject(res, Code2SessionResDto.class);
        if (!result.isOk()) {
            log.error("获取微信openid失败, req:{}, res:{}", code, res);
        }
        WxSessionPo wxSession = new WxSessionPo();
        wxSession.setSessionKey(result.getSessionKey());
        wxSession.setUnionId(result.getUnionid());
        wxSession.setOpenid(result.getOpenid());
        wxSessionService.saveOrUpdate(wxSession, Wrappers.lambdaUpdate(WxSessionPo.class).eq(WxSessionPo::getOpenid, result.getOpenid()));
        return result;
    }


    /**
     * 获取 AccessToken
     *
     * @return
     */
    public String getAccessToken() {
        String key = Constants.WEIXIN_ACCESS_TOKEN;
        String cacheResult = CacheManager.getData(key);
        if (cacheResult == null) {
            String httpUrl = url + "/cgi-bin/token?appid=" + appid + "&secret=" + secret + "&grant_type=client_credential";
            String res = HttpClientUtil.sendHttpGet(httpUrl);
            log.info("getAccessToken res:{}", res);
            AccessTokenResDto result = JSON.parseObject(res, AccessTokenResDto.class);
            if (result.isOk()) {
                CacheManager.setData(key, JSON.toJSONString(result), 6000);
            } else {
                log.error("获取微信access_token失败, {}", JSON.toJSONString(result));
            }
            return result.getAccessToken();
        } else {
            return JSON.parseObject(cacheResult, AccessTokenResDto.class).getAccessToken();
        }
    }

    public String getQrcode(String scene) throws Exception {
        //获取小程序码
        String httpUrl = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=ACCESS_TOKEN"
                .replace("ACCESS_TOKEN", getAccessToken());
        JSONObject reqJson = new JSONObject();
        reqJson.put("scene", scene);
        reqJson.put("page", "pages/index/index");
        reqJson.put("check_path", true);

        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        try {


            HttpPost httpPost = new HttpPost(httpUrl);
            StringEntity stringEntity = new StringEntity(reqJson.toString(), "UTF-8");
            stringEntity.setContentType("application/x-www-form-urlencoded");
            httpPost.setEntity(stringEntity);
            httpClient = HttpClientUtil.getHttpClient();
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            InputStream content = entity.getContent();
//        String res = HttpClientUtil.sendHttpPost(httpUrl, reqJson.toJSONString());
            return "data:image/jpeg;base64," + InputStreamToBase64.convertInputStreamToBase64(content);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw BizException.error("获取二维码失败");
        } finally {
            if (null != response) {
                response.close();
            }
        }
    }

    /**
     * 发送订阅消息
     *
     * @param req
     * @return
     */
    public WxBaseResDto subscribeMessageSend(SubscribeMessageSendReqDto req) {
        String httpUrl = url + "/cgi-bin/message/subscribe/send?access_token=" + getAccessToken();
        String res = HttpClientUtil.sendHttpPostJson(httpUrl, JSON.toJSONString(req));
        log.info("subscribeMessageSend req:{}, res:{}", JSON.toJSONString(req), JSON.toJSONString(res));
        WxBaseResDto result = JSON.parseObject(res, WxBaseResDto.class);
        if (!result.isOk()) {
            log.error("微信发送订阅消息失败, req:{}, res:{}", JSON.toJSONString(req), JSON.toJSONString(res));
        }
        return result;
    }

    public String decryptPhoneV2(String data, String iv, String openid) {
        log.info("微信数据解密参数 data：{}，iv：{}，openid：{}", data, iv, openid);
        WxSessionPo session = wxSessionService.getByOpenid(openid);

        BizException.isNull(session, "无法获取sessionKey");

        String sessionKey = session.getSessionKey();
        try {
            if (StringUtils.isBlank(sessionKey) || sessionKey.length() != 24) {
                throw BizException.error("sessionKey不能为空并且长度必须为24");
            } else if (StringUtils.isBlank(iv) || iv.length() != 24) {
                throw BizException.error("iv不能为空并且长度必须为24");
            } else {
                JSONObject obj = decryptData(data, iv, sessionKey, appid);
                return obj.getString("purePhoneNumber");
            }
        } catch (Exception e) {
            log.error("解密微信手机号失败", e);
            throw BizException.error("获取手机号失败");
        }
    }

    @Deprecated
    public String decryptPhone(String data, String iv, String openid) {
        log.info("微信数据解密参数 data：{}，iv：{}，openid：{}", data, iv, openid);
        WxSessionPo session = wxSessionService.getByOpenid(openid);

        BizException.isNull(session, "无法获取sessionKey");

        String sessionKey = session.getSessionKey();
        if (sessionKey == null) {
            throw BizException.error("无法获取用户，请先登录");
        }
        try {
            if (StringUtils.isBlank(sessionKey) || sessionKey.length() != 24) {
                throw BizException.error("sessionKey不能为空并且长度必须为24");
            } else if (StringUtils.isBlank(iv) || iv.length() != 24) {
                throw BizException.error("iv不能为空并且长度必须为24");
            } else {
                JSONObject obj = decryptData(data, iv, sessionKey, appid);
                return obj.getString("purePhoneNumber");
            }
        } catch (Exception e) {
            //异常不抛出，前端重试
            return null;
        }
    }

    private static JSONObject decryptData(String encryptedData, String iv, String sessionKey, String appid) {
        log.error("微信数据解密参数 encryptedData：{}，iv：{}，sessionKey：{}，appid：{}", encryptedData, iv, sessionKey, appid);
        try {
            byte[] aesKey = Base64.getDecoder().decode(sessionKey);
            byte[] aesIV = Base64.getDecoder().decode(iv);
            byte[] aesCipher = Base64.getDecoder().decode(encryptedData);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            Key sKeySpec = new SecretKeySpec(aesKey, "AES");
            AlgorithmParameters params = AlgorithmParameters.getInstance("AES");
            params.init(new IvParameterSpec(aesIV));
            cipher.init(Cipher.DECRYPT_MODE, sKeySpec, params);// 初始化
            byte[] resultByte = cipher.doFinal(aesCipher);
            if (resultByte == null) {
                throw BizException.error("获取数据失败");
            }
            JSONObject obj = JSONObject.parseObject(new String(resultByte, "UTF-8"));
            String watermarkAppid = obj.getJSONObject("watermark").getString("appid");
            if (!appid.equals(watermarkAppid)) {
                log.error("获取数据失败,appid与数据水印不符,watermark.appid={}", watermarkAppid);
                throw BizException.error("获取数据失败");
            } else {
                return obj;
            }
        } catch (Exception e) {
            log.error("微信数据解密失败：encryptedData：{}，iv：{}，sessionKey：{}，appid：{}", encryptedData, iv, sessionKey, appid);
            log.error("微信数据解密失败", e);
            throw BizException.error("获取数据失败");
        }
    }

    public static void main(String[] args) {
        JSONObject data = decryptData("pVrMX0nwKLHk5JIjheFsDdeR+JGwjooKpeeZs/7QWhvnzaaUD1LZEhm3sKMoKtSWrSysf7QwmQiDQJaC+AGpFGdr/+nQtfkWsbRfA4WUHeYm7u1VllLNlRqQFY6xGnon/12SwmhOIBB8DoIKka+2QDsK/Tz6UspKCK8Rn5iGaJoC0qM6NXVTTzOtCA+L5xleAHKHdR2S/KC00lV5yagI0Q==",
                "hSsy6pzFddQt6/c5xXTrqA==",
                "t23UTeuBeCz/Wtr1jUeGUg==",
                "wx48b176a5957a50e6");
        System.out.println(data);
    }

    public UserVo minLogin(String mobile, String password) {
        UserPo userPo = userService.getByMobileAndPassword(mobile, password);
        if (null == userPo) {
            throw BizException.error("用户不存在");
        }
        if(UserStateEnum.FREEZE.getCode() == userPo.getState()){
            throw BizException.error("账号被冻结，请联系管理员");
        }
        TokenUtil.setToken(userPo);
        userService.updateToken(userPo);
        return UserTransform.INSTANCE.po2Vo(userPo);
    }
}
