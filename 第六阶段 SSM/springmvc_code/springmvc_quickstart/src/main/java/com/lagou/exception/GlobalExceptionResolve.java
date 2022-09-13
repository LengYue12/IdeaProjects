package com.lagou.exception;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zs
 * @date 2022/6/23 20:35
 * @description
 */
/*
    自定义异常处理类
 */
public class GlobalExceptionResolve implements HandlerExceptionResolver {

    /*
        Exception e：实际抛出的异常对象
            通过e 获取到实际抛出的异常对象，通过异常对象获取到对应的异常信息
     */
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, Exception e) {

        // 具体的异常处理  产生异常后，跳转到一个最终的异常页面
        // 一旦执行到当前方法，那就意味着程序出现了异常
        // 借助ModelAndView 进行视图跳转
        ModelAndView modelAndView = new ModelAndView();
        // 向模型中设置值      取出当前的异常信息设置在模型中     获取到对应的异常信息
        modelAndView.addObject("error",e.getMessage());
        // 进行视图跳转       逻辑视图名
        modelAndView.setViewName("error");

        // 当返回modelAndView时，会经过视图解析器，而在视图解析器配置了前缀和后缀，所以可以定位 到 WEB-INF/pages/error.jsp 这个页面
        return modelAndView;
    }
}
