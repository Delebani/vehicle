package com.vehicle.controller;

import com.vehicle.base.cache.CacheManager;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lijianbing
 * @date 2023/7/31 15:14
 */
@RestController
@RequestMapping(value = "/logout")
@Api(value = "登出", tags = "登出")
@Slf4j
public class LogoutController {

    @GetMapping("")
    public Response toLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String token = request.getHeader("token");
        CacheManager.clear(token);
        return Response.success();
    }

}