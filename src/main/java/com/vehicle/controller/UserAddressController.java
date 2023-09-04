package com.vehicle.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vehicle.base.web.Response;
import com.vehicle.dto.req.UserAddressPageReq;
import com.vehicle.dto.req.UserAddressReq;
import com.vehicle.dto.vo.UserAddressVo;
import com.vehicle.service.UserAddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author lijianbing
 * @date 2023/9/2 2:08
 */
@RestController
@RequestMapping(value = "/user_address")
@Api(value = "用户地址管理", tags = "用户地址管理")
@Slf4j
public class UserAddressController {

    @Autowired
    private UserAddressService addressService;

    @ApiOperation(value = "新增更新", notes = "新增更新")
    @PostMapping(value = "/save_or_update")
    public Response saveOrUpDate(@Validated @RequestBody UserAddressReq req) {
        addressService.saveOrUpdate(req);
        return Response.success();
    }

    @ApiOperation(value = "删除", notes = "删除")
    @GetMapping(value = "/del")
    public Response del(@RequestParam(name = "id") Long id) {
        addressService.delById(id);
        return Response.success();
    }

    @ApiOperation(value = "查看", notes = "查看")
    @GetMapping(value = "/view")
    public Response<UserAddressVo> view(@RequestParam(name = "id") Long id) {
        return Response.success(addressService.view(id));
    }

    @ApiOperation(value = "分页", notes = "分页")
    @PostMapping(value = "/page")
    public Response<Page<UserAddressVo>> page(@RequestBody UserAddressPageReq req) {
        return Response.success(addressService.pages(req));
    }

    @ApiOperation(value = "所有", notes = "所有")
    @GetMapping(value = "/all")
    public Response<List<UserAddressVo>> all() {
        return Response.success(addressService.all());
    }
}
