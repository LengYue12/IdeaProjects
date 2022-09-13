package com.lagou.base;

/**
 * @author 张舒
 * @date 2022/5/12 19:10
 * @description
 */

import com.alibaba.fastjson.JSONObject;

/**
 * 编写枚举类，设置响应状态码
 */
public enum StatusCode {

    // 设置两个枚举实例，来响应状态码
    SUCCESS(0,"success"),FAIL(1,"fail");

    // 定义属性
    private int code; // 状态码
    private String message; // 信息

    StatusCode() {
    }

    StatusCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    // 重写toString，将枚举对象转为JSON
    @Override
    public String toString() {
        JSONObject object = new JSONObject();
        object.put("status",code);
        object.put("msg",message);
        return object.toString();
    }
}
