package com.vehicle.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vehicle.base.web.Response;
import com.vehicle.dto.req.ExpenseTypePageReq;
import com.vehicle.dto.req.ExpenseTypeReq;
import com.vehicle.dto.vo.ExpenseTypeVo;
import com.vehicle.service.ExpenseService;
import com.vehicle.service.ExpenseTypeService;
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
@RequestMapping(value = "/expense_type")
@Api(value = "费用类型管理", tags = "费用类型管理")
@Slf4j
public class ExpenseTypeController {

    @Autowired
    private ExpenseTypeService expenseTypeService;

    @ApiOperation(value = "新增更新", notes = "新增更新")
    @PostMapping(value = "/save_or_update")
    public Response saveOrUpDate(@Validated @RequestBody ExpenseTypeReq req) {
        expenseTypeService.saveOrUpdate(req);
        return Response.success();
    }

    @ApiOperation(value = "删除", notes = "删除")
    @GetMapping(value = "/del")
    public Response del(@RequestParam(name = "id") Long id) {
        expenseTypeService.del(id);
        return Response.success();
    }

    @ApiOperation(value = "查看", notes = "查看")
    @GetMapping(value = "/view")
    public Response<ExpenseTypeVo> view(@RequestParam(name = "id") Long id) {
        return Response.success(expenseTypeService.view(id));
    }

    @ApiOperation(value = "分页", notes = "分页")
    @PostMapping(value = "/page")
    public Response<Page<ExpenseTypeVo>> pages(@RequestBody ExpenseTypePageReq req) {
        return Response.success(expenseTypeService.pages(req));
    }

    @ApiOperation(value = "所有", notes = "所有")
    @GetMapping(value = "/all")
    public Response<List<ExpenseTypeVo>> all() {
        return Response.success(expenseTypeService.all());
    }
}
