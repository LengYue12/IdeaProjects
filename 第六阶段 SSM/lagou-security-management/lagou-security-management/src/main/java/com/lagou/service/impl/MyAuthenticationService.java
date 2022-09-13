package com.lagou.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @BelongsProject lagou-security-management
 * @Author lengy
 * @CreateTime 2022/8/18 15:08
 * @Description 自定义登录成功或失败的处理器，退出登录处理器
 */
@Service
public class MyAuthenticationService implements AuthenticationSuccessHandler,
    AuthenticationFailureHandler, LogoutSuccessHandler {


    RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    ObjectMapper objectMapper;

    /**
     * 登录成功后的处理逻辑
     * @param httpServletRequest
     * @param httpServletResponse
     * @param authentication
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        System.out.println("登录成功后继续处理...");
        // 重定向到index页面
        //redirectStrategy.sendRedirect(httpServletRequest,httpServletResponse,"/");

        Map result = new HashMap();
        result.put("code", HttpStatus.OK.value());  // 200
        result.put("message","登录成功");

        httpServletResponse.setContentType("application/json;charset=UTF-8");
        httpServletResponse.getWriter().write(objectMapper.writeValueAsString(result));
    }

    /**
     * 登录失败的处理逻辑
     * @param httpServletRequest
     * @param httpServletResponse
     * @param e
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException exception) throws IOException, ServletException {
        System.out.println("登录失败后继承处理...");
        // 重定向到login页面
//        redirectStrategy.sendRedirect(httpServletRequest,httpServletResponse,"/toLoginPage");

        Map result = new HashMap();
        result.put("code", HttpStatus.UNAUTHORIZED.value());  // 401
        result.put("message",exception.getMessage());

        httpServletResponse.setContentType("application/json;charset=UTF-8");
        httpServletResponse.getWriter().write(objectMapper.writeValueAsString(result));
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {

        System.out.println("退出之后继续处理...");
        redirectStrategy.sendRedirect(httpServletRequest,httpServletResponse,"/toLoginPage");
    }
}
