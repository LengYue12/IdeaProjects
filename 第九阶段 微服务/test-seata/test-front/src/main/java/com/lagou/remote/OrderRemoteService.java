package com.lagou.remote;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @BelongsProject test-seata
 * @Author lengy
 * @CreateTime 2022/9/21 14:09
 * @Description
 */
@FeignClient(name = "test-order",path = "order")
public interface OrderRemoteService {

    @GetMapping("save")
    int save();
}
