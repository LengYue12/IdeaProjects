package com.lagou.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zs
 * @date 2022/6/23 21:32
 * @description
 */
/*
    借助拦截器实现对目标方法的拦截增强
        拦截器拦截Controller里面的方法
 */
public class MyInterceptor2 implements HandlerInterceptor {


    /*
       preHandle：在目标方法执行之前  进行拦截
                return false:不放行，终止方法    也就是执行到preHandle 方法，如果return false，那后面的代码就不执行了
                                        也就是不会再去执行对应的目标方法，也不会再去执行后续的拦截方法了
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("preHandle2...");

        return true;    // 表示执行完对应的拦截方法后，放行。接着再去执行目标方法，以及后续的拦截方法
    }


    /*
        postHandle：在目标方法执行之后，视图对象返回之前，执行的方法
             执行时机：   就是在Controller里对应的方法代码执行完毕后，所执行的方法，然后再去视图跳转
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("postHandle2...");
    }



    /*
        afterCompletion：在流程都执行完成之后，执行的方法
            就是当Controller里对应的方法执行完成后，postHandle 这些方法执行完毕后，最终也跳转了页面后，流程都走完了
            最终执行的方法
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        System.out.println("afterCompletion2...");
    }
}
