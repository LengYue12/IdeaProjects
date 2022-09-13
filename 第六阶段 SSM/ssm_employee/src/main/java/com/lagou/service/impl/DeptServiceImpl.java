package com.lagou.service.impl;

import com.lagou.dao.DeptDao;
import com.lagou.domain.Dept;
import com.lagou.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zs
 * @date 2022/6/26 23:52
 * @description
 */
@Service("deptService")
public class DeptServiceImpl implements DeptService {

    @Autowired
    private DeptDao deptDao;

    @Override
    public Dept findById(Integer dept_id) {
        return deptDao.findById(dept_id);
    }


    @Override
    public List<Dept> find() {
        return deptDao.find();
    }
}
