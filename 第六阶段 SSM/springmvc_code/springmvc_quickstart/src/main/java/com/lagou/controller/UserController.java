package com.lagou.controller;

import com.lagou.domain.QueryVo;
import com.lagou.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author zs
 * @date 2022/6/21 15:20
 * @description
 */
@Controller     // 应用在web层的注解，生成该类的实例对象存到容器中
@RequestMapping("/user")        // 添加在类上，表示一级访问目录       就是为了使我们的url可以按照模块化管理
@SessionAttributes("username")  // 向request域中（model）中存入key为username时，会同步到session域中
public class UserController {

    // @RequestMapping 注解就是： 用于建立 URL 和 处理请求方法之间的对应关系
    // 当类上和方法上都进行了注解配置，那么访问地址就是     /一级访问目录/二级访问目录
    // http://localhost:8080/springmvc_quickstart/user/quick        /一级访问目录/二级访问目录

    /*      常用属性：
        path：作用等同于value，同样是设置方法的映射地址的
        method：用来限定请求的方式    RequestMethod.POST：只能以post的请求方式访问该方法，其他请求方式会报错
        params：用来限定请求参数的条件  params={"accountName"} 表示请求参数中必须有accountName    不携带会报错
            对当前这个quick方法进行请求的话，那么必须要携带请求参数，请求参数必须有accountName。
            就是请求中必须携带accountName 这个参数，才能成功访问quick方法
     */
    @RequestMapping(path = "/quick",method = RequestMethod.GET,params = {"accountName"})   // 添加在方法上，表示二级访问目录
    public String quick(){
        // 业务逻辑
        System.out.println("springmvc入门成功...");
        // 视图跳转     写要跳转的路径，执行完quick方法后，需要跳转到success.jsp 页面      返回逻辑视图名         请求转发
        // return "success"     返回给了前端控制器，前端控制器再调用视图解析器
        // 调用的就是显式配置的视图解析器
        return "success";
    }

    @RequestMapping("/quick2")
    public String quick2(){
        // 业务逻辑
        System.out.println("springmvc入门成功...");
        // 视图跳转             请求转发
        return "a";
    }

    @RequestMapping("/quick3")
    public String quick3(){
        // 业务逻辑
        System.out.println("springmvc入门成功...");
        // 视图跳转             请求转发
        return "b";
    }


    /*
        获取基本类型请求参数
     */
    // 添加注解，映射地址为二级目录，要和前台请求的二级访问目录一致
    @RequestMapping("/simpleParam")
    // 参数名要和前台请求参数名保持一致
    public String simpleParam(Integer id,String username){
        System.out.println(id);
        System.out.println(username);

        return "success";
    }


    /*
        获取对象类型请求参数
     */
    // 设置该方法的映射地址
    @RequestMapping("/pojoParam")
    public String pojoParam(User user){

        System.out.println(user);
        // 跳转到success页面
        return "success";
    }


    /*
        获取数组类型请求参数
     */
    @RequestMapping("/arrayParam")
    // 参数名要和前台的请求参数的name值 保持一致
    public String arrayParam(Integer[] ids){

        System.out.println(Arrays.toString(ids));
        // 跳转到成功页面
        return "success";
    }


    /*
        获取集合（复杂）类型请求参数
     */
    // 映射地址是前台表单action 中的二级 url目录  queryParam
    @RequestMapping("/queryParam")
    // 前台的请求参数最终要封装到QueryVo这个对象里面的属性上，所以参数写QueryVo
    public String queryParam(QueryVo queryVo){

        System.out.println(queryVo);
        // 返回视图
        return "success";
    }


    /*
        获取日期类型参数：（自定义类型转换器）
     */
    @RequestMapping("/converterParam")
    // 前台传递的是日期，参数名和表单中的name值 保持一致
    public String converterParam(Date birthday){

        System.out.println(birthday);
        return "success";
    }


    /*
        演示@RequestParam注解
            借助@RequestParam注解 去解决当前台请求参数name名称和业务方法参数名称不一致时的问题
            通过@RequestParam注解 进行显示的绑定，从而解决了绑定不上的问题
     */
    /*
        @RequestParam
            name：匹配页面传递参数的名称
            defaultValue：设置参数的默认值
                也就是前台在传递请求参数时，没传pageNo这个参数，那就让pageNum 这个值默认值为1
                前台传了的话，就把pageNo对应的值赋值给pageNum
            required：设置是否必须传递该参数，默认值为true，如果设置了默认值，值自动改为false
                就是只要配置了@RequestParam 这个注解，并且设置了name属性，如果前台请求参数，不携带pageNo的话 就会报错
                就是设置了defaultValue的话，required的值会改为false。前台传不传pageNo 都无所谓了
                因为设置了默认值，不传递走默认值，传递走传过来的值
     */
    @RequestMapping("findByPage")
    public String findByPage(@RequestParam(name = "pageNo",defaultValue = "1",required = false)Integer pageNum, @RequestParam(defaultValue = "5") Integer pageSize){

        System.out.println(pageNum);    // 2，虽然配置了defaultValue = "1"，但是前台请求参数传递了pageNo，那么以前台传递过来的请求参数值为准
        System.out.println(pageSize);   // 5，因为前台没有传递pageSize，所以走默认值 defaultValue = "5"
        return "success";
    }




    /*
        @RequestHeader注解的使用
            获取请求头数据
     */
    @RequestMapping("/requestHeader")
    // 当请求过来时，会携带cookie，所以可以来获取cookie这个请求头对应的数据
    // 借助该注解，获取cookie请求头对应的信息 赋值给 cookie参数
    public String requestHeader(@RequestHeader("cookie") String cookie){
        System.out.println(cookie);
        return "success";
    }


    /*
        @cookieValue 注解的使用
            获取cookie中的数据
     */
    @RequestMapping("/cookieValue")
    public String cookieValue(@CookieValue("JSESSIONID") String jsessionId){

        System.out.println(jsessionId);
        // 返回逻辑视图
        return "success";
    }




    /*
       小知识点：如何在controller方法中获取到关于Servlet的API
        原始servletAPI的获取
            如果在controller方法中 想用到request，response，session这样的Servlet API对象的话，可以直接在方法参数注入，声明一下。springmvc就会直接注入这些对象
     */
    @RequestMapping("/servletAPI")
    public String servletAPI(HttpServletRequest request, HttpServletResponse response, HttpSession session){
        System.out.println(request);
        System.out.println(response);
        System.out.println(session);

        // 请求转发：一次请求，且地址栏不会发送改变
        return "success";
    }




    /*
        通过原始servletAPI进行页面跳转
     */
    @RequestMapping("/returnVoid")
    // request对象和response对象由springmvc注入
    public void returnVoid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        /*直接返回数据*/
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write("拉勾网");



        // 借助request对象完成请求转发，一次请求，通过服务器内部进行请求转发
        //request.getRequestDispatcher("/WEB-INF/pages/success.jsp").forward(request,response);

        // 借助response对象完成重定向    两次请求，外部请求不能直接访问WEB-INF 这个目录下的页面的
        // WEB-INF：安全目录：不允许外部请求直接访问该目录资源，只可以进行服务器内部转发
        // 第二次请求不允许直接访问到安全目录下对应的页面
        //response.sendRedirect(request.getContextPath() + "/index.jsp");
    }




    /*
        演示forward 关键字进行请求转发
        当访问到forward方法时，就是向model中设置了username和value值，同时请求转发到 success.jsp

            forward:要写全路径
            如果想在模型中设置值，就可以注入Model对象，通过Model 调用addAttribute 来向模型中设置值
            若想取出模型中的值，那么在进行页面跳转时 要使用转发，才能取出该模型中的值
     */
    @RequestMapping("/forward")
    // 在模型中设置值时，就在参数中设置 Model 对象，然后调用 addAttribute方法 进行值的设置
    public String forward(Model model){

        // 还想在模型中设置一些值怎么做？
        // 在访问该方法时，向模型中设置了值
        // 当访问forward方法，跳转到success.jsp 时
        // 就可以根据 username 这个key，来取出对应的value值 拉勾招聘 显示在success.jsp 页面中
        model.addAttribute("username","拉勾招聘");



        // 其实就是要往 product controller里面的findAll方法    一级目录/二级目录
        // 相当于执行完了该方法的业务逻辑后，还要再去调用product 这个controller里面的findAll方法
        //return "forward:/product/findAll";
        // 使用forward 请求转发：既可以转发到jsp，也可以转发到其他的控制器方法
        return "forward:/WEB-INF/pages/success.jsp";
    }


    /*
        @SessionAttributes的效果：多个请求之间共享数据
            想实现在访问returnString  这个方法时
            跳转到success 页面同样可以取到 拉勾招聘 这个值，那就要在当前类上添加 @SessionAttributes 这个注解

            在forward方法中，向model中存入的值 对应的 key就是 username
            所以在当前类上添加了 @SessionAttributes("username") 这个注解后
            就会把 存入的值 同步到session域中，而session域是一次会话，所以再去访问 returnString 这个方法时
            就会取到username所对应的value值 也就是 拉勾招聘  显示在success页面中
     */
    @RequestMapping("/returnString")
    public String returnString(){

        return "success";
    }






    /*
        演示Redirect关键字进行重定向
            当写了redirect或者forward关键字，是不会再走视图解析器的，也就不会进行前缀和后缀拼接了
     */
    @RequestMapping("/redirect")
    public String redirect(Model model){
        // 底层使用的还是request.setAttribute("username","拉勾教育")
        // request域范围是一次请求，而重定向是两次请求已经超过域范围了，所以在index.jsp 中取不出模型中的值
        // model的范围是一次请求，重定向时两次请求，大过域范围了，所以取不出
        model.addAttribute("username","拉勾教育");

        // 重定向 ，springmvc会自动拼接前面的目录
        return "redirect:/index.jsp";
    }



    /*
        modelAndView进行页面跳转：方式一
     */

    @RequestMapping("/returnModelAndView")
    public ModelAndView returnModelAndView(){

        /*
            model：模型：作用：封装存放数据的
            view:视图：作用：用来展示数据的      也就是要用到哪个页面来进行数据的展示，那就在view上设置上对应的页面路径
         */
        ModelAndView modelAndView = new ModelAndView();
        // 设置模型数据
        modelAndView.addObject("username","modelAndView方式一");

        // 设置视图名称       跳转的视图名称，直接写逻辑视图名
        // 视图解析器，解析modelAndView 这个对象，就会去拼接前缀和后缀
        // 所以直接给逻辑视图名，再由视图解析器拼接上前缀和后缀，最终进行页面跳转
        modelAndView.setViewName("success");

        // 把modelAndView 这个对象返回给处理器适配器，而处理器适配器再modelAndView交给前端控制器
        // 前端控制器拿到modelAndView 对象后，找视图解析器，解析modelAndView 这个对象，就会去拼接前缀和后缀
        return modelAndView;
    }




    /*
        modelAndView进行页面跳转：方式二      常用
            ModelAndView  既可以设置模型数据，同时也可以设置视图名称
     */

    @RequestMapping("/returnModelAndView2")
    // 在当前方法参数中声明 ModelAndView 对象，在方法体中直接用到 注入进来的 ModelAndView 这个对象
    public ModelAndView returnModelAndView2(ModelAndView modelAndView){

        /*
            model：模型：作用：封装存放数据的
            view:视图：作用：用来展示数据的      也就是要用到哪个页面来进行数据的展示，那就在view上设置上对应的页面路径
         */

        // 设置模型数据
        modelAndView.addObject("username","modelAndView方式二");

        // 设置视图名称       跳转的视图名称，直接写逻辑视图名
        // 视图解析器，解析modelAndView 这个对象，就会去拼接前缀和后缀
        // 所以直接给逻辑视图名，再由视图解析器拼接上前缀和后缀，最终进行页面跳转
        modelAndView.setViewName("success");

        // 把modelAndView 这个对象返回给处理器适配器，而处理器适配器再modelAndView交给前端控制器
        // 前端控制器拿到modelAndView 对象后，找视图解析器，解析modelAndView 这个对象，就会去拼接前缀和后缀
        return modelAndView;
    }





    /*
        ajax异步交互   前台发送的JSON串： [{"id":1,"username":"张三"},{"id":2,"username":"李四"}]
            @RequestBody    把前台传过来的JSON串转为集合，就要用到@RequestBody 这个注解，写在方法的形参上
     */
    @RequestMapping("/ajaxRequest")
    // @RequestBody这个注解是把JSON串封装成集合或pojo对象
    // @ResponseBody这个注解是在响应集合或响应某一个实体时，由SpringMVC把该对象或集合转为JSON串响应给前台的


    // 在方法的形参上添加了@RequestBody这个注解后，后台接收到前台传过来的JSON串时，就可以封装成List集合了。
    // 前提是引入了jackson 依赖，并且配置了mvc:annotation-driven 这个标签

    // 把对象转换为JSON串，响应给前台可以添加@ResponseBody注解
    // 把list返回时，因为添加了@ResponseBody注解，SpringMVC就会把list集合转为JSON串响应给前台
    @ResponseBody
    public List<User> ajaxRequest(@RequestBody List<User> list){
        System.out.println(list);

        return list;
    }

}
