package com.lagou.controller;

import com.github.pagehelper.PageInfo;
import com.lagou.entity.ResponseResult;
import com.lagou.entity.Role;
import com.lagou.entity.User;
import com.lagou.entity.UserVo;
import com.lagou.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author zs
 * @date 2022/7/1 16:56
 * @description
 */
@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserService userService;

    /*
        用户分页&多条件查询方法
     */
    @RequestMapping("/findAllUserByPage")
    // 接收到请求体中的参数并封装到 UserVo对象中
    public ResponseResult findAllUserByPage(@RequestBody UserVo userVo){


        PageInfo pageInfo = userService.findAllUserByPage(userVo);


        return new ResponseResult(true,200,"分页多条件查询成功",pageInfo);

    }




    /*
        修改用户状态
            ENABLE能登录，DISABLE不能登录
     */
    @RequestMapping("/updateUserStatus")
    public ResponseResult updateUserStatus(int id,String status){

        if("ENABLE".equalsIgnoreCase(status)){
            status = "DISABLE";
        } else {
            status = "ENABLE";
        }

        userService.updateUserStatus(id,status);

        return new ResponseResult(true,200,"修改用户状态成功",status);

    }




    /*
        用户登录
     */
    @RequestMapping("/login")
    public ResponseResult login(User user, HttpServletRequest request) throws Exception {

        User user1 = userService.login(user);

        if (user1 != null) {

            // 保存用户id及access_token 到 session 中
            // 做登录，就是把用户id和access_token 存到session中
            // 当用户下一次向后台发送请求时，会携带一个请求头，请求头的值就是 access_token 的值
            // 进行和session 中的 access_token 的值进行对比。如果一致就进行后续操作
            HttpSession session = request.getSession();
            String access_token = UUID.randomUUID().toString();
            session.setAttribute("access_token",access_token);
            session.setAttribute("user_id",user1.getId());

            // 将查询出来的信息响应给前台
            Map<String, Object> map = new HashMap<>();
            map.put("access_token",access_token);
            map.put("user_id",user1.getId());

            // 将查询出来的user，也存到map中
            map.put("user",user1);

            return new ResponseResult(true,1,"登录成功",map);

        } else {
            return new ResponseResult(true,400,"用户名密码错误",null);
        }
    }



    /*
        分配角色（回显）
     */
    @RequestMapping("/findUserRoleById")
    public ResponseResult findUserRelationRoleById(int id){

        List<Role> roleList = userService.findUserRelationRoleById(id);

        return new ResponseResult(true,200,"分配角色回显成功",roleList);
    }



    /*
        分配角色
     */
    @RequestMapping("/userContextRole")
    public ResponseResult userContextRole(@RequestBody UserVo userVo){

        userService.userContextRole(userVo);

        return new ResponseResult(true,200,"分配角色成功",null);
    }


    /*
        获取用户拥有的菜单权限，进行菜单动态展示
     */
    @RequestMapping("/getUserPermissions")
    public ResponseResult getUserPermissions(HttpServletRequest request){

        // 1.获取请求头中的token信息
        String header_token = request.getHeader("Authorization");

        // 2.获取之前在登录方法中存到session中的token信息
        String session_token = (String) request.getSession().getAttribute("access_token");

        // 3.判断token是否一致
        if (header_token.equalsIgnoreCase(session_token)){
            // 一样的话，表示登录的是同一个用户且在登录状态
            // 1.从session中获取到之前登录方法中存到session中的 用户id
            Integer user_id = (Integer) request.getSession().getAttribute("user_id");

            // 2.调用service，进行菜单信息查询
            return userService.getUserPermissions(user_id);
        } else {
            // 表示token不一致
            return new ResponseResult(false,400,"获取菜单信息失败",null);
        }
    }
}
