package com.lagou.dao;

import com.lagou.entity.Test;

import java.util.List;

/**
 * @author zs
 * @date 2022/6/28 19:56
 * @description
 */
public interface TestMapper {

    /*
        对test表进行查询所有操作
     */
    public List<Test> findAllTest();
}
