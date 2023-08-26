package com.vehicle.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vehicle.base.web.Response;
import com.vehicle.dto.req.UserPageReq;
import com.vehicle.dto.req.UserReq;
import com.vehicle.dto.req.UserRoleReq;
import com.vehicle.dto.vo.MenuTreeVo;
import com.vehicle.dto.vo.UserVo;
import com.vehicle.service.UserService;
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
@RequestMapping(value = "/user")
@Api(value = "用户管理", tags = "用户管理")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "新增更新", notes = "新增更新")
    @PostMapping(value = "/save_or_update")
    public Response saveOrUpDate(@Validated @RequestBody UserReq req) {
        userService.saveOrUpdate(req);
        return Response.success();
    }

    @ApiOperation(value = "删除", notes = "删除")
    @GetMapping(value = "/del")
    public Response del(@RequestParam(name = "id") Long id) {
        userService.del(id);
        return Response.success();
    }

    @ApiOperation(value = "查看", notes = "查看")
    @GetMapping(value = "/view")
    public Response<UserVo> view(@RequestParam(name = "id") Long id) {
        return Response.success(userService.view(id));
    }

    @ApiOperation(value = "分页", notes = "分页")
    @PostMapping(value = "/page")
    public Response<Page<UserVo>> page(@RequestBody UserPageReq req) {
        return Response.success(userService.page(req));
    }

    @ApiOperation(value = "用户角色保存", notes = "用户角色保存")
    @PostMapping(value = "/save_role")
    public Response saveRole(@Validated @RequestBody UserRoleReq req) {
        userService.saveRole(req);
        return Response.success();
    }

    @ApiOperation(value = "用户菜单树", notes = "用户菜单树")
    @GetMapping(value = "/menu_tree")
    public Response<List<MenuTreeVo>> menuTree() {
        return Response.success(userService.menuTree());
    }
}
