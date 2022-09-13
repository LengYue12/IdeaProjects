package com.lagou.service;

import com.lagou.entity.Test;

import java.util.List;

/**
 * @author zs
 * @date 2022/6/28 20:30
 * @description
 */
public interface TestService {

    /*
        对test表进行查询所有
     */
    public List<Test> findAllTest();
}
