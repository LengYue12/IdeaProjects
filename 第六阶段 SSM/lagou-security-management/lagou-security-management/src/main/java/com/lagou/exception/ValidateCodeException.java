package com.lagou.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @BelongsProject lagou-security-management
 * @Author lengy
 * @CreateTime 2022/8/18 16:28
 * @Description 验证码异常类
 */
public class ValidateCodeException extends AuthenticationException {


    public ValidateCodeException(String msg) {
        super(msg);
    }
}
