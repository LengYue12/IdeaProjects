package com.lagou.controller;

import org.springframework.web.bind.annotation.*;

/**
 * @author zs
 * @date 2022/6/23 16:54
 * @description
 */
/*
    Restful编程风格
 */
@RestController     // 组合注解：组合了@Controller + @ResponseBody
//@Controller // 生成当前类的实例存到容器中
@RequestMapping("/restful")
public class RestfulController {


    /*
        根据ID进行查询
        Localhost:8080/项目名/user/2 + get请求方式   404 findById:2     就可以定位到这个方法
     */
    // 占位符的值要和参数名保持一致
    //@RequestMapping(value = "/user/{id}",method = RequestMethod.GET)
    //@ResponseBody       // 如果当前方法返回的是对象或集合，那么该注解会转为JSON串响应给前台
    // 但如果返回的是字符串，借助这个注解可以直接进行数据响应，响应给浏览器，不会走视图解析器了

    // 设置了value的值，设置了请求方式get。 可以替换掉@RequestMapping这个注解
    @GetMapping("/user/{id}")       // 相当于 @RequestMapping(value = "/user/{id}",method = RequestMethod.GET)
    // 借助这个注解 @PathVariable 获取到了占位符的值，并且赋值给了参数
    public String findById(@PathVariable Integer id){
        // 调用service方法完成对id为2的这条记录的查询
        // findById方法中怎么才能获取到restful编程风格中url里面占位符的值？
        // 因为前台传递过来的请求参数的值就赋值给了占位符

        return "findById:" + id;
    }




    /*
        新增方法
     */
    // 指定该方法请求方式是post，post请求+/user  路径 就定位到了这个方法
    @PostMapping("/user")   // 相当于 @RequestMapping(value = "/user",method = RequestMethod.POST)
    public String post(){

        // 新增

        // 因为在当前类上已经用到了@RestController 这个注解，相当于组合了@Controller + @ResponseBody
        // 所以就等同于在这个方法上添加了 @ResponseBody 这个注解。那么直接return post 是不会进行页面跳转的  直接把post输出在浏览器了
        return "post";
    }



    /*
        更新方法
     */
    @PutMapping("/user")
    public String put(){
        // 更新操作
        return "put";
    }


    /*
        根据ID删除方法
     */
    @DeleteMapping("/user/{id}")
    // @PathVariable :获取占位符里id的值，赋值给参数。参数名和占位符名保持一致
    public String delete(@PathVariable Integer id){

        return "delete" + id;
    }

}
