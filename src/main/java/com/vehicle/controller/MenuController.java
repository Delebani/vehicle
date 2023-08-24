package com.vehicle.controller;

import com.vehicle.base.web.Response;
import com.vehicle.dto.req.MenuReq;
import com.vehicle.dto.vo.MenuTreeVo;
import com.vehicle.service.MenuService;
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
@RequestMapping(value = "/menu")
@Api(value = "菜单管理", tags = "菜单管理")
@Slf4j
public class MenuController {

    @Autowired
    private MenuService menuService;

    @ApiOperation(value = "新增更新", notes = "新增更新")
    @PostMapping(value = "/save_or_update")
    public Response saveOrUpDate(@Validated @RequestBody MenuReq req) {
        menuService.saveOrUpdate(req);
        return Response.success();
    }

    @ApiOperation(value = "删除", notes = "删除")
    @GetMapping(value = "/del")
    public Response del(@RequestParam(name = "id") Long id) {
        menuService.delById(id);
        return Response.success();
    }

    @ApiOperation(value = "菜单树", notes = "菜单树")
    @GetMapping(value = "/tree")
    public Response<List<MenuTreeVo>> tree() {
        return Response.success(menuService.tree());
    }

    @RequestMapping(value = "/fetch/{id}", method = RequestMethod.GET)
    String getDynamicUriValue(@PathVariable String id) {
        System.out.println("ID is " + id);
        return "Dynamic URI parameter fetched";
    }
}
