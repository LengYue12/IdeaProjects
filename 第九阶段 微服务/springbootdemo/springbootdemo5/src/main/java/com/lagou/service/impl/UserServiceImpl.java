package com.lagou.service.impl;

import com.lagou.mapper.UserMapper;
import com.lagou.pojo.User;
import com.lagou.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @BelongsProject springbootdemo
 * @Author lengy
 * @CreateTime 2022/8/15 21:45
 * @Description
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> queryAll() {
        return userMapper.queryAll();
    }

    @Override
    public User findById(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public void insert(User user) {
        //userMapper.insert(user);    // 将除id所有的列都拼sql
        userMapper.insertSelective(user);   // 只是将不为空的列才拼sql
    }

    @Override
    public void delete(Integer id) {
        userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(User user) {
        userMapper.updateByPrimaryKeySelective(user);   // 只有不为空的列才拼sql
    }
}
