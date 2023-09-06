package com.vehicle.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vehicle.base.web.Response;
import com.vehicle.dto.req.VehiclePageReq;
import com.vehicle.dto.req.VehicleReq;
import com.vehicle.dto.vo.VehicleVo;
import com.vehicle.service.VehicleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author lijianbing
 * @date 2023/7/31 15:14
 */
@RestController
@RequestMapping(value = "/vehicle")
@Api(value = "车辆管理", tags = "车辆管理")
@Slf4j
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @ApiOperation(value = "新增更新", notes = "新增更新")
    @PostMapping(value = "/save_or_update")
    public Response saveOrUpDate(@Validated @RequestBody VehicleReq req) {
        vehicleService.saveOrUpdate(req);
        return Response.success();
    }

    @ApiOperation(value = "删除", notes = "删除")
    @GetMapping(value = "/del")
    public Response del(@RequestParam(name = "id") Long id) {
        vehicleService.del(id);
        return Response.success();
    }

    @ApiOperation(value = "查看", notes = "查看")
    @GetMapping(value = "/view")
    public Response<VehicleVo> view(@RequestParam(name = "id") Long id) {
        return Response.success(vehicleService.view(id));
    }

    @ApiOperation(value = "分页", notes = "分页")
    @PostMapping(value = "/page")
    public Response<Page<VehicleVo>> page(@RequestBody VehiclePageReq req) {
        return Response.success(vehicleService.page(req));
    }

    @ApiOperation(value = "所有", notes = "所有")
    @GetMapping(value = "/all")
    public Response<List<VehicleVo>> all() {
        return Response.success(vehicleService.all());
    }
}
