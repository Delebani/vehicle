package com.vehicle.controller;

import com.vehicle.base.exception.BizException;
import com.vehicle.base.web.Response;
import com.vehicle.dto.req.PcLoginReq;
import com.vehicle.dto.req.PcRegisterReq;
import com.vehicle.dto.req.PcResetReq;
import com.vehicle.dto.res.weixin.Code2SessionResDto;
import com.vehicle.dto.vo.UserVo;
import com.vehicle.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
}