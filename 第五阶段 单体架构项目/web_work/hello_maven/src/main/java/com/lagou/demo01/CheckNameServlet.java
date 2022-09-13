package com.lagou.demo01;

import com.alibaba.fastjson.JSON;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author 张舒
 * @date 2022/5/14 22:26
 * @description
 */
@WebServlet("/checkName")
public class CheckNameServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 设置编码，防止乱码
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");

        /**
         * 获取前台输入的用户名
         * 判断用户名是否存在
         * 不管存不存在，都把信息封装到map里
         * 最终把map转换成JSON格式并响应回去
         */

        // 1.获取用户输入的用户名
        String userName = req.getParameter("username");


        // 2.Map封装数据
        HashMap<String,Object> map = new HashMap<>();


        // 3.判断用户是否存在
        if ("蔡徐坤".equals(userName)){
            // 蔡徐坤用户名已经存在
            map.put("flag",true);
            map.put("msg","用户名已经被占用，请更换！");

            // 响应JSON格式的数据
            String param = JSON.toJSONString(map);
            resp.getWriter().print(param);

        } else {
            // 用户名不存在
            map.put("flag",false);
            map.put("msg","用户名可以使用！");

            // 响应JSON格式的数据
            String param = JSON.toJSONString(map);
            resp.getWriter().print(param);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
