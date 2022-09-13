package com.lagou.demo.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import javax.servlet.http.HttpSessionBindingEvent;

/**
 * 统计当前在线用户数量
 */
@WebListener()
public class LoginListener implements ServletContextListener,
        HttpSessionListener, HttpSessionAttributeListener {

    // 声明一个ServletContext对象负责作为全局对象来记录当前在线用户数量，通过属性记录
    private ServletContext servletContext = null;

    // Public constructor is required by servlet spec
    public LoginListener() {
    }

    // -------------------------------------------------------
    // ServletContextListener implementation
    // -------------------------------------------------------
    // 初始化ServletContext
    public void contextInitialized(ServletContextEvent sce) {
      /* This method is called when the servlet context is
         initialized(when the Web application is deployed). 
         You can initialize servlet context related data here.
      */
      // 得到全局对象，记录数据内容
      servletContext = sce.getServletContext();
    }

    // 销毁ServletContext
    public void contextDestroyed(ServletContextEvent sce) {
      /* This method is invoked when the Servlet Context 
         (the Web application) is undeployed or 
         Application Server shuts down.
      */
      servletContext = null;
    }

    // -------------------------------------------------------
    // HttpSessionListener implementation
    // -------------------------------------------------------
    public void sessionCreated(HttpSessionEvent se) {
        /* Session is created. */
        System.out.println("有新用户上线了...");
        // 将当前的在线人数加1，并保存到ServletContext中
        Object count = servletContext.getAttribute("count");
        // 判断当前用户是否为第一个用户，若是则将全局对象中的属性值设为1
        if (null == count){
            servletContext.setAttribute("count",1);
        }
        // 若当前用户不是第一个用户，则将全局对象中的属性值取出加1放入
        else {
            Integer integer = (Integer)count;
            integer++;
            servletContext.setAttribute("count",integer);
        }
        System.out.println("当前在线用户数量为：" + servletContext.getAttribute("count"));
    }

    // 当session被销毁，就是有用户下线了
    public void sessionDestroyed(HttpSessionEvent se) {
        /* Session is destroyed. */
        System.out.println("有用户已下线...");
        Integer integer = (Integer) servletContext.getAttribute("count");
        integer--;
        servletContext.setAttribute("count",integer);
    }

    // -------------------------------------------------------
    // HttpSessionAttributeListener implementation
    // -------------------------------------------------------

    public void attributeAdded(HttpSessionBindingEvent sbe) {
      /* This method is called when an attribute 
         is added to a session.
      */
      // 登录首页里存储到session中的user对象
        System.out.println("session中加入了属性：" + sbe.getName());
    }

    public void attributeRemoved(HttpSessionBindingEvent sbe) {
      /* This method is called when an attribute
         is removed from a session.
      */
        System.out.println("session中删除了属性");
    }

    public void attributeReplaced(HttpSessionBindingEvent sbe) {
      /* This method is invoked when an attribute
         is replaced in a session.
      */
        System.out.println("修改了session中的属性");
    }
}
