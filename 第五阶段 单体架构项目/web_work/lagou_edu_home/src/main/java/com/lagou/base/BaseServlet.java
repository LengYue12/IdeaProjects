package com.lagou.base;

/**
 * @author 张舒
 * @date 2022/5/12 15:28
 * @description
 */

import com.alibaba.fastjson.JSON;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * 把反射相关的代码抽取到BaseServlet中
 */
public class BaseServlet extends HttpServlet {

    /**
     * doGet()方法作为调度器使用，控制器，根据请求的功能不同，调用对应的方法
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {

            // 1.获取参数
            // 获取要调用的方法名
            //String methodName = req.getParameter("methodName");
            String methodName = null;

            // 过来请求时，判断Content-Type类型，如果不是JSON格式，是普通模式，还是之前的getParameter获取methodName

            // 如果是JSON格式的话，先调用getPostJSON获取到JSON字符串，然后转为map
            // 再从map里获取到methodName，再把map放到request属性中

            // 1.获取POST请求的 Content-Type 类型
            String contentType = req.getHeader("Content-Type");

            // 2.判断传递的数据是不是JSON格式的  忽略大小写
            if ("application/json;charset=utf-8".equalsIgnoreCase(contentType)){
                // 传过来的是JSON格式的,调用getPostJSON
                String postJSON = getPostJSON(req);

                // 将JSON格式的字符串转换为map集合
                Map<String,Object> map = JSON.parseObject(postJSON, Map.class);

                // 从map集合中获取 methodName
                methodName = (String)map.get("methodName");

                // 将获取到的数据，保存到request域对象中
                req.setAttribute("map",map);

            } else {
                methodName = req.getParameter("methodName");
            }

            // 2.业务处理
            if (methodName != null) {
                // 通过反射优化代码，提升代码的可维护性，可扩展性
                // 1.获取字节码对象  就是本类的Class实例对象    this=TestServlet对象
                Class c = this.getClass();

                // 2.根据传入的方法名，获取对应的方法对象，执行方法即可
                Method method = c.getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);

                // 3.调用method对象的 invoke方法，执行对应的功能
                method.invoke(this, req, resp);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

//        // 2.业务处理
//        // 判断 执行对应的方法
//        if ("addCourse".equals(methodName)) {
//            addCourse(req,resp);
//        } else if ("findByName".equals(methodName)){
//            findByName(req,resp);
//        } else if ("findByStatus".equals(methodName)){
//            findByStatus(req,resp);
//        } else {
//            System.out.println("请求的功能不存在！");
//        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    /**
     * POST请求格式为： application/json;charset=utf-8
     * 使用该方法进行读取
     * 当post请求格式为JSON时，去读取JSON格式的数据并返回
     */
    public String getPostJSON(HttpServletRequest request){

        try {
            // 1.从request中 获取缓冲输入流对象，用流对象去JSON数据
            BufferedReader reader = request.getReader();

            // 2.创建StringBuffer 保存读取出的数据
            StringBuffer sb = new StringBuffer();

            // 3.循环读取
            String line = null;
            while ((line = reader.readLine()) != null) {
                // 将每次读取的数据追加到 StringBuffer中
                sb.append(line);
            }

            // 4.返回结果
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
