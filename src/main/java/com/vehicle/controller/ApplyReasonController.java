package com.vehicle.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vehicle.base.web.Response;
import com.vehicle.dto.req.ApplyReasonPageReq;
import com.vehicle.dto.req.ApplyReasonReq;
import com.vehicle.dto.req.MenuPageReq;
import com.vehicle.dto.req.MenuReq;
import com.vehicle.dto.vo.ApplyReasonVo;
import com.vehicle.dto.vo.MenuTreeVo;
import com.vehicle.dto.vo.MenuVo;
import com.vehicle.service.ApplyReasonService;
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
@RequestMapping(value = "/apply_reason")
@Api(value = "申请原因管理", tags = "申请原因管理")
@Slf4j
public class ApplyReasonController {

    @Autowired
    private ApplyReasonService applyReasonService;

    @ApiOperation(value = "新增更新", notes = "新增更新")
    @PostMapping(value = "/save_or_update")
    public Response saveOrUpDate(@Validated @RequestBody ApplyReasonReq req) {
        applyReasonService.saveOrUpdate(req);
        return Response.success();
    }

    @ApiOperation(value = "删除", notes = "删除")
    @GetMapping(value = "/del")
    public Response del(@RequestParam(name = "id") Long id) {
        applyReasonService.delById(id);
        return Response.success();
    }

    @ApiOperation(value = "分页", notes = "分页")
    @PostMapping(value = "/page")
    public Response<Page<ApplyReasonVo>> page(@Validated @RequestBody ApplyReasonPageReq req) {
        return Response.success(applyReasonService.pages(req));
    }

    @ApiOperation(value = "所有", notes = "所有")
    @GetMapping(value = "/all")
    public Response<List<ApplyReasonVo>> all() {
        return Response.success(applyReasonService.all());
    }

}
