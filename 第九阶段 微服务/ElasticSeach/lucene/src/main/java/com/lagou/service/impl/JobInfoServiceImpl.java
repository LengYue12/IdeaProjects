package com.lagou.service.impl;

import com.lagou.mapper.JobInfoMapper;
import com.lagou.pojo.JobInfo;
import com.lagou.service.JobInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @BelongsProject lucene
 * @Author lengy
 * @CreateTime 2022/8/30 20:14
 * @Description
 */
@Service
public class JobInfoServiceImpl implements JobInfoService {

    @Autowired
    private JobInfoMapper jobInfoMapper;

    @Override
    public JobInfo findById(long id) {
        return jobInfoMapper.selectById(id);
    }

    @Override
    public List<JobInfo> selectAll() {

        return jobInfoMapper.selectList(null);
    }
}
