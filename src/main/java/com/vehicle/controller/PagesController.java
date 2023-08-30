package com.vehicle.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

}