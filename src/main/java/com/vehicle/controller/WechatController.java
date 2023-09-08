package com.vehicle.controller;

import com.vehicle.base.cache.CacheManager;
import com.vehicle.base.exception.BizException;
import com.vehicle.base.web.Response;
import com.vehicle.dto.req.PcLoginReq;
import com.vehicle.dto.res.weixin.Code2SessionResDto;
import com.vehicle.dto.vo.UserVo;
import com.vehicle.service.UserService;
import com.vehicle.service.WechatService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lijianbing
 * @date 2023/9/7 22:44
 */
@RestController
@RequestMapping(value = "/wechat")
@Api(value = "微信", tags = "微信")
@Slf4j
public class WechatController {

    @Autowired
    private WechatService wechatService;

    @Autowired
    private UserService userService;


    @ApiOperation(value = "获取openid", notes = "获取openid")
    @GetMapping(value = "/getOpenId")
    public Response<String> getOpenId(@RequestParam(name = "code", required = true) String code) {
        Code2SessionResDto code2SessionResDto = wechatService.jsCode2Session(code);
        if (!code2SessionResDto.isOk()) {
            throw BizException.error("获取openid失败");
        }
        return Response.success(code2SessionResDto.getOpenid());
    }

    @ApiOperation(value = "根据openId获取用户", notes = "根据openId获取用户")
    @GetMapping(value = "/getUserByOpenId")
    public Response<UserVo> getUserByOpenId(@RequestParam(name = "openId", required = true) String openId) {
        return Response.success(userService.getByOpenId(openId));
    }

    @ApiOperation(value = "小程序端登录", notes = "小程序端登录")
    @GetMapping(value = "/minLogin")
    public Response<UserVo> minLogin(@RequestParam(name = "mobile", required = true) String mobile,
                                     @RequestParam(name = "password", required = true) String password) {
        UserVo userVo = wechatService.minLogin(mobile,password);
        return Response.success(userVo);
    }

    @ApiOperation(value = "获取二维码", notes = "获取二维码")
    @GetMapping(value = "/getQrcode")
    public Response<String> getQrcode(@RequestParam(name = "scene", required = true) String scene) throws Exception {
        String res = wechatService.getQrcode(scene);
        return Response.success(res);
    }

    @ApiOperation(value = "获取小程序授权", notes = "获取小程序授权")
    @GetMapping(value = "/getAuth")
    public Response getAuth(@RequestParam(name = "scene", required = true) String scene,
                            @RequestParam(name = "openId", required = true) String openId) {
        CacheManager.setData(scene, openId, 300);
        return Response.success();
    }

    @ApiOperation(value = "检查扫码", notes = "检查扫码")
    @GetMapping(value = "/checkLogin")
    public Response<UserVo> checkLogin(@RequestParam(name = "scene", required = true) String scene) {
        String openId = CacheManager.getData(scene);
        if (StringUtils.isBlank(openId)) {
            return Response.fail("用户未扫码");
        }
        CacheManager.clear(scene);
        return Response.success(userService.getByOpenId(openId));
    }


    /*@ApiOperation(value = "短信验证码登陆", notes = "通过手机号+验证码登录")
    @PostMapping(value = "/login")
    public Response<PhoneUserVo> smsLogin(@RequestParam String phone,
                                          @RequestParam String code,
                                          @RequestParam("openid") String openid,
                                          @RequestParam(required = false) String inviteCode,
                                          @RequestParam(required = false) BigDecimal longitude,
                                          @RequestParam(required = false) BigDecimal latitude,
                                          @RequestParam(required = false) Integer sceneId,
                                          @RequestParam(required = false) String anonymousId,
                                          HttpServletRequest request) {

        String key = RedisConstants.USER_SEND_SMS_CODE_PREFIX + phone;
        String confirmCode = redisTemplateUtil.getString(key);
        if (confirmCode == null) {
            throw BizException.error("验证码过期");
        }
        if (!confirmCode.equals(code)) {
            throw BizException.error("验证码错误");
        }
        return Response.success(phoneUserService.loginV2(phone, openid, inviteCode, longitude, latitude, sceneId, SensorsUtil.getAnonymousId(anonymousId), request));
    }

    @ApiOperation(value = "微信手机号登陆", notes = "通过获取的微信手机号进行登录")
    @PostMapping(value = "/wxPhoneLogin")
    public Response<PhoneUserVo> wxPhoneLogin(@RequestParam String data,
                                              @RequestParam String iv,
                                              @RequestParam String openid,
                                              @RequestParam(required = false) String inviteCode,
                                              @RequestParam(required = false) BigDecimal longitude,
                                              @RequestParam(required = false) BigDecimal latitude,
                                              @RequestParam(required = false) Integer sceneId,
                                              @RequestParam(required = false) String anonymousId,
                                              HttpServletRequest request) {
        String phone = weixinService.decryptPhoneV2(data, iv, openid);
        return Response.success(phoneUserService.loginV2(phone, openid, inviteCode, longitude, latitude, sceneId, SensorsUtil.getAnonymousId(anonymousId), request));
    }*/
}
