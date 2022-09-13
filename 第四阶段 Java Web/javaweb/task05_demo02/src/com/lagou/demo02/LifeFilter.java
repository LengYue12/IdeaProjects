package com.lagou.demo02;

import javax.servlet.*;
import java.io.IOException;
import java.util.Enumeration;

public class LifeFilter implements Filter {
    public LifeFilter() {
        System.out.println("构造方法执行！");
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("初始化操作正在火热进行中...");
        System.out.println("获取到的过滤器名称为：" + filterConfig.getFilterName());
        String userName = filterConfig.getInitParameter("userName");
        System.out.println("获取到指定初始化参数的数值为：" + userName);   // admin
        Enumeration<String> initParameterNames = filterConfig.getInitParameterNames();
        while (initParameterNames.hasMoreElements()) {
            // userName  password
            System.out.println("获取到的初始化参数名为：" + initParameterNames.nextElement());
        }
        ServletContext servletContext = filterConfig.getServletContext();
        System.out.println("获取到的上下文对象是：" + servletContext);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("阻拦一切不合理的访问哦！");
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {
        System.out.println("销毁操作执行完毕了！");
    }
}
