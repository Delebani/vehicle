package com.vehicle.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vehicle.base.web.Response;
import com.vehicle.dto.req.VehicleLogPageReq;
import com.vehicle.dto.req.VehiclePageReq;
import com.vehicle.dto.req.VehicleReq;
import com.vehicle.dto.vo.VehicleLogVo;
import com.vehicle.dto.vo.VehicleVo;
import com.vehicle.service.VehicleLogService;
import com.vehicle.service.VehicleService;
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
@RequestMapping(value = "/vehicle_log")
@Api(value = "车辆记录管理", tags = "车辆记录管理")
@Slf4j
public class VehicleLogController {

    @Autowired
    private VehicleLogService vehicleLogService;

    @ApiOperation(value = "查看", notes = "查看")
    @GetMapping(value = "/view")
    public Response<VehicleLogVo> view(@RequestParam(name = "id") Long id) {
        return Response.success(vehicleLogService.view(id));
    }

    @ApiOperation(value = "分页", notes = "分页")
    @PostMapping(value = "/page")
    public Response<Page<VehicleLogVo>> pages(@RequestBody VehicleLogPageReq req) {
        return Response.success(vehicleLogService.pages(req));
    }
}
