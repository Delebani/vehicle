package com.vehicle.controller;

import com.vehicle.base.exception.BizException;
import com.vehicle.base.web.Response;
import com.vehicle.dto.req.PcLoginReq;
import com.vehicle.dto.req.PcRegisterReq;
import com.vehicle.dto.req.PcResetReq;
import com.vehicle.dto.res.weixin.Code2SessionResDto;
import com.vehicle.dto.vo.UserVo;
import com.vehicle.service.LoginService;
import com.vehicle.service.WeixinService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author lijianbing
 * @date 2023/7/31 15:14
 */
@Controller
@RequestMapping(value = "/login")
@Api(value = "登录", tags = "登录")
@Slf4j
public class LoginController {

    @Autowired
    private WeixinService weixinService;

    @Autowired
    private LoginService loginService;

    @GetMapping("")
    public String toLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //request.getRequestDispatcher("/login.html").forward(request, response);
        return "login";
    }

    @ApiOperation(value = "pc端注册", notes = "pc端注册")
    @PostMapping(value = "/pc_register")
    @ResponseBody
    public Response<UserVo> pcRegister(@Validated @RequestBody PcRegisterReq req) {
        return Response.success(loginService.pcRegister(req));
    }

    @ApiOperation(value = "pc端登录", notes = "pc端登录")
    @PostMapping(value = "/pc_login")
    @ResponseBody
    public Response<UserVo> pcLogin(@Validated @RequestBody PcLoginReq req, HttpServletRequest request, HttpServletResponse response) {
        UserVo userVo = loginService.pcLogin(req);
        return Response.success(userVo);
    }

    @ApiOperation(value = "pc端重置密码", notes = "pc端重置密码")
    @PostMapping(value = "/pc_reset_password")
    @ResponseBody
    public Response<UserVo> pcResetPassword(@Validated @RequestBody PcResetReq req) {
        return Response.success(loginService.pcResetPassword(req));
    }

    @ApiOperation(value = "获取openid", notes = "获取openid")
    @GetMapping(value = "/getOpenId")
    @ResponseBody
    public Response<String> getOpenId(@RequestParam String code) {
        Code2SessionResDto code2SessionResDto = weixinService.jsCode2Session(code);
        if (!code2SessionResDto.isOk()) {
            throw BizException.error("获取openid失败");
        }
        return Response.success(code2SessionResDto.getOpenid());
    }

    /*@ApiOperation(value = "短信验证码登陆", notes = "通过手机号+验证码登录")
    @PostMapping(value = "/smsLogin")
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