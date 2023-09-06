package com.vehicle.controller;

import com.vehicle.base.web.Response;
import com.vehicle.dto.req.DriverSortReq;
import com.vehicle.dto.vo.DriverVo;
import com.vehicle.service.DriverService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author lijianbing
 * @date 2023/9/5 23:22
 */
@RestController
@RequestMapping(value = "/driver")
@Api(value = "司机管理", tags = "司机管理")
@Slf4j
public class DriverController {

    @Autowired
    private DriverService driverService;

    @ApiOperation(value = "所有", notes = "所有")
    @GetMapping(value = "/all")
    public Response<List<DriverVo>> all() {
        return Response.success(driverService.all());
    }

    @ApiOperation(value = "排班", notes = "排班")
    @PostMapping(value = "/sort")
    public Response sort(@Validated @RequestBody List<DriverSortReq> reqList) {
        driverService.sort(reqList);
        return Response.success();
    }

    @ApiOperation(value = "签到", notes = "签到")
    @GetMapping(value = "/signin")
    public Response signin() {
        return Response.success();
    }

    @ApiOperation(value = "签退", notes = "签退")
    @GetMapping(value = "/signout")
    public Response signout() {
        return Response.success();
    }
}
