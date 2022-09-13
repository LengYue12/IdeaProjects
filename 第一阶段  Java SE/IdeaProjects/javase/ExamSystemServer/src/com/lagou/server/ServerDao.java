package com.lagou.server;

import com.lagou.model.User;

/**
 * 编程实现数据的存取
 */
public class ServerDao {

    /**
     * 编程实现管理员账号和密码的校验并将结果返回出去
     * @param user
     * @return
     */
    public boolean serverManagerCheck(User user){
        if ("admin".equals(user.getUserName()) && "123456".equals(user.getPassword())) {
            return true;
        }
        return false;
    }
}
