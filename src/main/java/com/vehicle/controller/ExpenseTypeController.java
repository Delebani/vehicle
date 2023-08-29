package com.vehicle.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lijianbing
 * @date 2023/7/31 15:14
 */
@RestController
@RequestMapping(value = "/expense_type")
@Api(value = "费用类型管理", tags = "费用类型管理")
@Slf4j
public class ExpenseTypeController {

}
