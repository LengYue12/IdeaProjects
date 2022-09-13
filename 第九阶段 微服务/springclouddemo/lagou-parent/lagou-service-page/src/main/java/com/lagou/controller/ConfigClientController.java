package com.lagou.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @BelongsProject lagou-parent
 * @Author lengy
 * @CreateTime 2022/8/24 18:29
 * @Description
 */
@RestController
@RequestMapping("/config")
@RefreshScope
public class ConfigClientController {

    @Value("${lagou.message}")
    private String message;

    @Value("${pagea}")
    private String pagea;

    @Value("${pageb}")
    private String pageb;


    @RequestMapping("/query")
    public String getRemoteConfig(){
        return message + "-" + pagea + "-" + pageb;
    }
}
