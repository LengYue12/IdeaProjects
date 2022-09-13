package com.lagou.demo.filter;

import com.lagou.demo.entity.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.http.HttpRequest;

/**
 * 登录功能的过滤，必须要通过首页登录成功后才能访问主页面，否则不能访问主页面
 */
@WebFilter(filterName = "LoginFilter",urlPatterns = {"/mainPage.jsp","/manageClassGrade.jsp","/main.jsp"})
public class LoginFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        // 实现功能，只有用户登录后才能访问主页面，否则一律拦截
        // 把ServletRequest类型的对象强转为HttpServletRequest对象，来获取session对象
        HttpServletRequest hsr = (HttpServletRequest)req;

        // 判断session中是否有用户名信息，若有则放行，若没有则判断Cookie中是否有，若有则放行，否则拦截
        HttpSession session = hsr.getSession();
        // 获取session中用户信息
        User user = (User)session.getAttribute("user");
        // 获取请求中调用Servlet的路径部分
        String servletPath = hsr.getServletPath();
        // 设置一个userName变量，用于存放从cookie中获取到的用户名名字
        String userName = null;
        // 判断session中是否有用户信息和包含Servlet请求路径,有就放行，否则判断cookie
        if (null != user && servletPath.contains("login")){
            chain.doFilter(req,resp);
        } else {
            Cookie[] cookies = hsr.getCookies();
            if (cookies != null && cookies.length > 0){
                for (Cookie cookie : cookies) {
                    // 获取cookie对象中的名字
                    String name = cookie.getName();
                    if ("userName".equals(name)) {
                        // 将用户名名字放到userName变量中
                        userName = name;
                        break;
                    }
                }
            }
        }
        if (null != userName) {
            chain.doFilter(req,resp);
        } else {
            // 如果session和cookie中都没有，则说明没有登录，则拦截，回到登录页面重新登录
            req.getRequestDispatcher("index.jsp").forward(req,resp);
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
