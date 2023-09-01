package com.vehicle.controller;

import com.vehicle.base.web.Response;
import com.vehicle.dto.req.ApproveReq;
import com.vehicle.service.ApproveService;
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
 * @date 2023/8/30 0:03
 */
@RestController
@RequestMapping(value = "/approve")
@Api(value = "申请记录管理", tags = "申请记录管理")
@Slf4j
public class ApproveController {

    @Autowired
    private ApproveService approveService;

    @ApiOperation(value = "审批", notes = "审批")
    @PostMapping(value = "/submit")
    public Response approve(@Validated @RequestBody ApproveReq req) {
        approveService.approve(req);
        return Response.success();
    }
}
