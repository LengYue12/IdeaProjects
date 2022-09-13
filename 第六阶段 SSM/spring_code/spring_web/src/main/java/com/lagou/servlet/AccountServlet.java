package com.lagou.servlet;

import com.lagou.domain.Account;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import java.io.IOException;

/**
 * @author zs
 * @date 2022/6/19 22:41
 * @description
 */
public class AccountServlet extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {


        // classPathXmlApplicationContext对象存到servletContext域中，因为servletContext域 是整个web项目
        // 所以就可以在这个web项目任意地方都可以获取到servletContext域对象
        // 从域对象中取出存入的 应用上下文对象classPathXmlApplicationContext对象

        // 获取到Spring的应用上下文对象
        //ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        // 借助应用上下文对象从IOC容器中取出account实例对象，并打印
        //Account account = (Account) classPathXmlApplicationContext.getBean("account");
        //System.out.println(account);


        // 借助 WebApplicationContextUtils 这个类获取  ApplicationContext  应用上下文对象
        // 应用上下文对象是直接从servletContext域中 获取到的

        // 这样就避免了应用上下文对象被创建多次，以及核心配置文件被加载解析多次
        ApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
        Account account = (Account) webApplicationContext.getBean("account");
        System.out.println(account);

    }
}
