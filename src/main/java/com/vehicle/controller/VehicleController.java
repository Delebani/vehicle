package com.vehicle.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vehicle.base.web.Response;
import com.vehicle.dto.req.UserPageReq;
import com.vehicle.dto.req.UserReq;
import com.vehicle.dto.req.UserRoleReq;
import com.vehicle.dto.vo.MenuTreeVo;
import com.vehicle.dto.vo.UserVo;
import com.vehicle.service.UserService;
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
}
