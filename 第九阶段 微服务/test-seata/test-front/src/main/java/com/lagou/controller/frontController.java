package com.lagou.controller;

import com.lagou.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @BelongsProject test-seata
 * @Author lengy
 * @CreateTime 2022/9/21 14:03
 * @Description
 */
@RestController
@RequestMapping("front")
public class frontController {
    @Autowired
    private BusinessService businessService;

    @GetMapping("business")
    public boolean business(){
        return businessService.business();
    }
}
