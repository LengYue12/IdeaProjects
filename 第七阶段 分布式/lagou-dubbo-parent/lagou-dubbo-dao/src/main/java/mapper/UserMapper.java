package mapper;

import entity.Users;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @BelongsProject lagou-dubbo
 * @Author lengy
 * @CreateTime 2022/7/22 13:15
 * @Description 用户操作mapper
 */
@Service
public interface UserMapper {

    void register(Users users);

    /*
        查询所有用户
     */
    List<Users> findAll();

    /*
        根据uid 回显用户
     */
    Users findByUid(Integer Uid);


    /*
        更新
     */
    void update(Users users);


    /*
        模糊查询
     */
    List<Users> findByUsername(String username);

    /*
        批量删除
     */
    void deleteBatch(Integer[] ids);
}
