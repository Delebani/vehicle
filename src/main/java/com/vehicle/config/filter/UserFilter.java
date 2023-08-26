package com.vehicle.config.filter;

import com.alibaba.fastjson.JSON;
import com.vehicle.base.cache.CacheManager;
import com.vehicle.base.cas.CurrentUser;
import com.vehicle.base.cas.UserHolder;
import com.vehicle.base.exception.BaseExceptionEnum;
import com.vehicle.base.web.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 用户信息解析拦截器
 */
@Component
@Slf4j
@Order(value = 1)
@WebFilter(filterName = "userFilter", urlPatterns = {"/*"})
public class UserFilter implements Filter {

    @Resource
    private WhiteConfig whiteConfig;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String uri = request.getRequestURI();
        if (isWhite(uri)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        String token = request.getHeader("token");
        if (StringUtils.isBlank(token)) {
            log.info("未登录");
            writeError(response, BaseExceptionEnum.RELOGIN.getCode(), BaseExceptionEnum.RELOGIN.getMsg());
            //response.sendRedirect("/login");
            return;
        }
        CurrentUser currentUser = CacheManager.getData(token);
        if (null == currentUser) {
            log.info("未登录");
            writeError(response, BaseExceptionEnum.RELOGIN.getCode(), BaseExceptionEnum.RELOGIN.getMsg());
            //response.sendRedirect("/login");
            return;
        }
        UserHolder.put(currentUser);
        filterChain.doFilter(servletRequest, servletResponse);
        UserHolder.remove();
    }

    private boolean isWhite(String uri) {

        List<String> whiteList = whiteConfig.getWhiteList();
        if (whiteList == null) {
            return false;
        }
        if("/".equals(uri)){
            return true;
        }
        for (String allowPath : whiteList) {
            //判断是否允许
            if (uri.startsWith(allowPath)) {
                return true;
            }
        }
        return false;
    }

    public static void writeError(HttpServletResponse response, Integer code, String message) throws IOException {
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ServletOutputStream writer = response.getOutputStream();
        writer.write(JSON.toJSONString(
                new Response<>(code, message)).getBytes(StandardCharsets.UTF_8.toString())
        );
    }
}
