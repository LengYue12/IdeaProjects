package controller;

import com.alibaba.dubbo.config.annotation.Reference;
import entity.Users;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import service.UserService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @BelongsProject lagou-dubbo
 * @Author lengy
 * @CreateTime 2022/7/22 13:45
 * @Description 控制层
 */
@Controller
public class UserAction {


    @Reference  // 远程调用服务提供方的实现类
    private UserService userService;

    @RequestMapping("/register")
    public String register(Users user) {


            String nowTime = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            user.setCreatetime(nowTime);
            userService.register(user);
            return "redirect:/findAll";
    }


    @RequestMapping("/findAll")
    public String findAll(Model model){

        List<Users> list = userService.findAll();


        // 把封装好的list存到Model中
        model.addAttribute("list",list);

        // 跳转页面
        return "list";

    }




    /*
        根据ID查询账户信息，完成账户回显
     */
    @RequestMapping("/findByUid")
    public String findById(Integer uid,Model model){

        Users users = userService.findByUid(uid);

        // 存到model中，进行数据回显
        model.addAttribute("users",users);

        // 视图跳转
        return "update";
    }


    /*
        更新账户
     */
    @RequestMapping("/update")
    public String update(Users users){

        userService.update(users);

        // 跳转到findAll方法进行账户信息的更新查询
        return "redirect:/findAll";
    }


    /*
        批量删除
     */

    @RequestMapping("/deleteBatch")
    public String deleteBatch(Integer[] ids){

        userService.deleteBatch(ids);

        // 重新发起请求再去查询findAll方法，重新查询数据库的记录，进行展示
        return "redirect:/findAll";
    }



    // 根据姓名关键字 模糊查询用户
    @RequestMapping("/findByUsername")
    public String findByUsername(String username, Model model){

        List<Users> list1 = userService.findByUsername(username);

        model.addAttribute("list1",list1);

        return "mohu";
    }
}
