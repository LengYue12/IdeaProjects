package com.lagou.remote;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @BelongsProject test-seata
 * @Author lengy
 * @CreateTime 2022/9/21 14:06
 * @Description
 */
@FeignClient(name = "test-account",path = "account")
public interface AccountRemoteService {

    @GetMapping("update")
    int update();
}
