package com.vehicle.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author lijianbing
 * @date 2023/7/31 15:14
 */
@Controller
@RequestMapping(value = "/pages")
@Api(value = "页面", tags = "页面")
@Slf4j
public class PagesController {

    @GetMapping("/register")
    public String register() throws Exception {

        return "/pages/register";
    }

    @GetMapping("/forget")
    public String forget() throws Exception {

        return "/pages/forget";
    }

    @GetMapping("/home")
    public String home() throws Exception {

        return "/pages/home";
    }

    @GetMapping("/user")
    public String user() throws Exception {

        return "/pages/user";
    }

    @GetMapping("/role")
    public String role() throws Exception {

        return "/pages/role";
    }

    @GetMapping("/menu")
    public String menu() throws Exception {

        return "/pages/menu";
    }

    @GetMapping("/mine")
    public String mine() throws Exception {

        return "/pages/mine";
    }

    @GetMapping("/vehicle")
    public String vehic() throws Exception {

        return "/pages/vehicle";
    }

    @GetMapping("/vehicletype")
    public String vehicType() throws Exception {

        return "/pages/vehicletype";
    }


    @GetMapping("/applyreason")
    public String applyreason() throws Exception {

        return "/pages/applyreason";
    }

    @GetMapping("/applyvehicle")
    public String applylog() throws Exception {

        return "/pages/applyvehicle";
    }

    @GetMapping("/approve_apply_vehicle")
    public String approveApplyVehicle() throws Exception {

        return "/pages/approve_apply_vehicle";
    }

    @GetMapping("/useraddress")
    public String userAddress() throws Exception {

        return "/pages/useraddress";
    }

    @GetMapping("/driversort")
    public String driver() throws Exception {

        return "/pages/driversort";
    }

    @GetMapping("/expense")
    public String expense() throws Exception {

        return "/pages/expense";
    }

    @GetMapping("/expensetype")
    public String expensetype() throws Exception {

        return "/pages/expensetype";
    }

    @GetMapping("/approveexpense")
    public String approveexpense() throws Exception {

        return "/pages/approveexpense";
    }
}