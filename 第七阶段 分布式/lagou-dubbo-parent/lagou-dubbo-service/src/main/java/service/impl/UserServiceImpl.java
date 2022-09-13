package service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import entity.Users;
import mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import service.UserService;

import java.util.List;

/**
 * @BelongsProject lagou-dubbo
 * @Author lengy
 * @CreateTime 2022/7/22 13:32
 * @Description 服务实现类
 */
@Service    // 暴露服务（向Zookeeper注册服务）
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Override
    public void register(Users users) {
        userMapper.register(users);
    }

    @Override
    public List<Users> findAll() {
        return userMapper.findAll();
    }

    @Override
    public Users findByUid(Integer Uid) {
        return userMapper.findByUid(Uid);
    }

    @Override
    public void update(Users users) {
        userMapper.update(users);
    }

    @Override
    public List<Users> findByUsername(String username) {
         return userMapper.findByUsername(username);
    }


    @Override
    public void deleteBatch(Integer[] ids) {
        userMapper.deleteBatch(ids);
    }
}
