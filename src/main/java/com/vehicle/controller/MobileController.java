package com.vehicle.controller;

import com.vehicle.base.cache.CacheManager;
import com.vehicle.base.constants.Constants;
import com.vehicle.base.web.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 手机验证码
 *
 * @author lijianbing
 * @date 2023/8/22 1:27
 */
@RestController
@RequestMapping(value = "/mobile")
@Api(value = "手机", tags = "手机")
@Slf4j
public class MobileController {

    @ApiOperation(value = "发送验证码", notes = "发送验证码")
    @GetMapping("/send_code")
    public Response sendCode(@RequestParam(name = "mobile") String mobile){
        // 发送手机号验证码 todo 1234
        CacheManager.setData(Constants.MOBILE_CODE + mobile, "1234", 300);
        return Response.success();
    }
}
