package com.vehicle.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lijianbing
 * @date 2023/7/31 15:14
 */
@Controller
@RequestMapping(value = "/index")
@Api(value = "首页", tags = "首页")
@Slf4j
public class IndexController {

    @GetMapping("")
    public ModelAndView toLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {

        return new ModelAndView ("index");
    }

}