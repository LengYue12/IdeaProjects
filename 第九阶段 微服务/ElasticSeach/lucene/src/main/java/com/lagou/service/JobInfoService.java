package com.lagou.service;

import com.lagou.pojo.JobInfo;

import java.util.List;

/**
 * @BelongsProject lucene
 * @Author lengy
 * @CreateTime 2022/8/30 20:12
 * @Description
 */
public interface JobInfoService {

    /**
     * 根据id查询
     * @param id
     * @return
     */
    JobInfo findById(long id);


    /**
     * 查询所有
     * @return
     */
    List<JobInfo> selectAll();

}
