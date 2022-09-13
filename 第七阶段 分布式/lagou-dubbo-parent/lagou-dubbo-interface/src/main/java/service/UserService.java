package service;

import entity.Users;

import java.util.List;

/**
 * @BelongsProject lagou-dubbo
 * @Author lengy
 * @CreateTime 2022/7/22 13:30
 * @Description 用户服务的接口
 */
public interface UserService {

    void register(Users users);

    /*
        查询所有用户
     */
    List<Users> findAll();

    Users findByUid(Integer Uid);

    void update(Users users);


    List<Users> findByUsername(String username);

    void deleteBatch(Integer[] ids);
}
