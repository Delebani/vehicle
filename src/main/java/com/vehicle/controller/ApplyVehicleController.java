package com.vehicle.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vehicle.base.web.Response;
import com.vehicle.dto.req.ApplyLogPageReq;
import com.vehicle.dto.req.ApplyLogReq;
import com.vehicle.dto.vo.ApplyLogVo;
import com.vehicle.service.ApplyLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author lijianbing
 * @date 2023/7/31 15:14
 */
@RestController
@RequestMapping(value = "/apply_vehicle")
@Api(value = "申请记录管理", tags = "申请记录管理")
@Slf4j
public class ApplyVehicleController {

    @Autowired
    private ApplyLogService ApplyLogService;

    @ApiOperation(value = "提交", notes = "提交")
    @PostMapping(value = "/submit")
    public Response submit(@Validated @RequestBody ApplyLogReq req) {
        ApplyLogService.submit(req);
        return Response.success();
    }


    @ApiOperation(value = "查看", notes = "查看")
    @GetMapping(value = "/view")
    public Response<ApplyLogVo> view(@RequestParam(name = "id") Long id) {
        return Response.success(ApplyLogService.view(id));
    }

    @ApiOperation(value = "分页", notes = "分页")
    @PostMapping(value = "/page")
    public Response<Page<ApplyLogVo>> pages(@RequestBody ApplyLogPageReq req) {
        return Response.success(ApplyLogService.pages(req));
    }

    @ApiOperation(value = "出车", notes = "出车")
    @GetMapping(value = "/departure")
    public Response departure(@RequestParam(name = "id") Long id) {
        ApplyLogService.departure(id);
        return Response.success();
    }

    @ApiOperation(value = "还车", notes = "还车")
    @GetMapping(value = "/return")
    public Response returnVehicle(@RequestParam(name = "id") Long id) {
        ApplyLogService.returnVehicle(id);
        return Response.success();
    }
}
