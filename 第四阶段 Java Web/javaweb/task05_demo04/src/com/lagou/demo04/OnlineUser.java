package com.lagou.demo04;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class OnlineUser implements HttpSessionListener, ServletContextListener {
    // 声明一个ServletContext类型的引用负责作为全局对象来记录当前用户的数量，通过属性记录
    private ServletContext servletContext = null;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        servletContext = servletContextEvent.getServletContext();

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        servletContext = null;
    }

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        System.out.println("有新用户上线了...");
        Object count = servletContext.getAttribute("count");
        // 若当前用户为第一个用户，则将全局对象中的属性值设置为1即可
        if (null == count) {
            servletContext.setAttribute("count",1);
        }
        // 若当前用户不是第一个用户，则将全局对象中原有的数值取出来加1后再设置进去
        else {
            Integer integer = (Integer)count;
            integer++;
            servletContext.setAttribute("count",integer);
        }
        System.out.println("当前在线用户数量为：" + servletContext.getAttribute("count"));
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        System.out.println("有用户已下线...");
    }
}
