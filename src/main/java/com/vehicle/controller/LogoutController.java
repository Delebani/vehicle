package com.vehicle.controller;

import com.vehicle.base.cache.CacheManager;
import com.vehicle.base.web.Response;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
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