package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.HelloService;

/**
 * @BelongsProject dubbo-consumer
 * @Author lengy
 * @CreateTime 2022/7/19 17:43
 * @Description 控制层
 */
@Controller
public class HelloAction {

    // service实现类要远程调用服务提供方的service
    // 远程去服务方将service的实现类注入进来
    // 远程引用来的，因为本项目里没有具体的实现类，所以HelloService一定是去Zookeeper找的
    //@Reference  // 使用service远程连接另一个项目中的service接口的实现类
    @Autowired
    private HelloService helloService;


    @GetMapping("hello")
    @ResponseBody
    public String sayHi(String name){

       return helloService.sayHello(name);
    }

    @GetMapping("no")
    @ResponseBody
    public String sayNo(){

        return helloService.sayNo();
    }
}
