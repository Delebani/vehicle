package com.vehicle.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vehicle.base.web.Response;
import com.vehicle.dto.req.ExpensePageReq;
import com.vehicle.dto.req.ExpenseReq;
import com.vehicle.dto.vo.ExpenseVo;
import com.vehicle.service.ExpenseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lijianbing
 * @date 2023/7/31 15:14
 */
@RestController
@RequestMapping(value = "/expense")
@Api(value = "费用管理", tags = "费用管理")
@Slf4j
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @ApiOperation(value = "提交", notes = "提交")
    @PostMapping(value = "/submit")
    public Response submit(@Validated @RequestBody ExpenseReq req) {
        expenseService.submit(req);
        return Response.success();
    }

    @ApiOperation(value = "分页", notes = "分页")
    @PostMapping(value = "/page")
    public Response<Page<ExpenseVo>> pages(@RequestBody ExpensePageReq req) {
        return Response.success(expenseService.pages(req));
    }
}