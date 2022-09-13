package com.lagou.filter;

import com.lagou.controller.ValidateCodeController;
import com.lagou.exception.ValidateCodeException;
import com.lagou.service.impl.MyAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @BelongsProject lagou-security-management
 * @Author lengy
 * @CreateTime 2022/8/18 16:04
 * @Description 验证码过滤器  一次请求只会经过一次过滤器
 */
@Component
public class ValidateCodeFilter extends OncePerRequestFilter {

    @Autowired
    MyAuthenticationService myAuthenticationService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        // 判断是否是登录请求
        if(httpServletRequest.getRequestURI().equals("/login") && httpServletRequest.getMethod().equalsIgnoreCase("post")){
            String imageCode = httpServletRequest.getParameter("imageCode");
            System.out.println("imageCode = " + imageCode);
            // 具体的验证流程
            try {
                validate(httpServletRequest,imageCode);
            } catch (ValidateCodeException e) {
                myAuthenticationService.onAuthenticationFailure(httpServletRequest,httpServletResponse,e);
                return;
            }
        }
        // 如果不是登录请求直接放行
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }


    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private void validate(HttpServletRequest httpServletRequest, String imageCode) {
        // 从Redis中获取验证码
        String redisKey = ValidateCodeController.REDIS_KEY_IMAGE_CODE + "-" + httpServletRequest.getRemoteAddr();
        String redisImageCode = stringRedisTemplate.boundValueOps(redisKey).get();
        // 验证码的判断
        if(!StringUtils.hasText(imageCode)){    // 验证码的值不为空
            throw new ValidateCodeException("验证码的值不能为空！");
        }
        if (redisImageCode == null) {   // 验证码已过期
            throw new ValidateCodeException("验证码已过期！");
        }
        if (!redisImageCode.equals(imageCode)){
            throw new ValidateCodeException("验证码不正确！");
        }

        // 从redis中删除验证码
        stringRedisTemplate.delete(redisKey);
    }
}
