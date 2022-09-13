package com.lagou.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @BelongsProject lagou-parent
 * @Author lengy
 * @CreateTime 2022/8/22 19:13
 * @Description
 */
@RestController
@RequestMapping("/service")
public class ServiceInfoController {

    @Value("${server.port}")
    private String port;

    @GetMapping("/port")
    public String getPort(){

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return port;
    }
}
