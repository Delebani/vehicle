package com.vehicle.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vehicle.base.web.Response;
import com.vehicle.dto.req.RolePageReq;
import com.vehicle.dto.req.RoleReq;
import com.vehicle.dto.vo.RoleVo;
import com.vehicle.service.RoleService;
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
@RequestMapping(value = "/role")
@Api(value = "角色管理", tags = "角色管理")
@Slf4j
public class RoleController {

    @Autowired
    private RoleService roleService;

    @ApiOperation(value = "新增更新", notes = "新增更新")
    @PostMapping(value = "/save_or_update")
    public Response saveOrUpDate(@Validated @RequestBody RoleReq req) {
        roleService.saveOrUpdate(req);
        return Response.success();
    }

    @ApiOperation(value = "删除", notes = "删除")
    @GetMapping(value = "/del")
    public Response del(@RequestParam(name = "id") Long id) {
        roleService.delById(id);
        return Response.success();
    }

    @ApiOperation(value = "查看", notes = "查看")
    @GetMapping(value = "/view")
    public Response<RoleVo> view(@RequestParam(name = "id") Long id) {
        return Response.success(roleService.view(id));
    }

    @ApiOperation(value = "分页", notes = "分页")
    @PostMapping(value = "/page")
    public Response<Page<RoleVo>> page(@RequestBody RolePageReq req) {
        return Response.success(roleService.page(req));
    }
    @ApiOperation(value = "所有角色", notes = "所有角色")
    @GetMapping(value = "/find_all")
    public Response<List<RoleVo>> findAll() {
        return Response.success(roleService.findAll());
    }
}
