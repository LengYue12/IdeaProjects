package com.lagou.controller;

import com.lagou.pojo.JobInfo;
import com.lagou.service.JobInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @BelongsProject lucene
 * @Author lengy
 * @CreateTime 2022/8/30 20:23
 * @Description
 */
@RestController
@RequestMapping("/jobInfo")
public class JobInfoController {

    @Autowired
    private JobInfoService jobInfoService;


    @GetMapping("/query/{id}")
    public JobInfo findById(@PathVariable Long id){

      return jobInfoService.findById(id);
    }

    @GetMapping("/query")
    public List<JobInfo> query(){
        return jobInfoService.selectAll();
    }


}
