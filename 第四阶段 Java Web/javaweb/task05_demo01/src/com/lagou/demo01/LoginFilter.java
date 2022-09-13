package com.lagou.demo01;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 1.实现用户访问主页面的过滤操作，也就是只有用户登录后才能访问主页面，否则一律拦截
        // 判断session中是否已有用户名信息，若没有则进行拦截，否则放行
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpSession session = request.getSession();
        Object userName = session.getAttribute("userName");
        // 获取Servlet的请求路径
        String servletPath = request.getServletPath();
        // 若没有登录，则回到登录页面
        if (null == userName && !servletPath.contains("login")) {
            servletRequest.getRequestDispatcher("login.jsp").forward(servletRequest,servletResponse);
        } else {
            // 若已经登录，则放行
            filterChain.doFilter(servletRequest,servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
