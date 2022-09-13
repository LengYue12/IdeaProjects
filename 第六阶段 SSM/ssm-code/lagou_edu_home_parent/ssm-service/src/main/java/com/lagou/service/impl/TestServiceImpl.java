package com.lagou.service.impl;

import com.lagou.dao.TestMapper;
import com.lagou.entity.Test;
import com.lagou.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zs
 * @date 2022/6/28 20:32
 * @description
 */
@Service        // 生成该类实例对象存到IOC
public class TestServiceImpl implements TestService {

    @Autowired
    private TestMapper testMapper;

    @Override
    public List<Test> findAllTest() {
        return testMapper.findAllTest();
    }
}
