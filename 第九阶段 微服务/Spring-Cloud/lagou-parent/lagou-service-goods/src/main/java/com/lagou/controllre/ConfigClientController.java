package com.lagou.controllre;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @BelongsProject Spring-Cloud
 * @Author lengy
 * @CreateTime 2022/8/29 20:34
 * @Description
 */
@RestController
@RequestMapping("/config")
@RefreshScope
public class ConfigClientController {

    @Value("${lagou.slogan}")
    private String slogan;

    @GetMapping("/query")
    public String query(){
        return slogan;
    }
}
