package stub;

import org.springframework.util.StringUtils;
import service.HelloService;

/**
 * @BelongsProject dubbo-consumer
 * @Author lengy
 * @CreateTime 2022/7/19 20:43
 * @Description 本地存根
 */
public class HelloServiceStub implements HelloService {

    private HelloService helloService;  // helloService 的代理对象

    // Stub必须有可传入 Proxy的构造函数
    // 本地存根必须以构造方法的形式注入
    public HelloServiceStub(HelloService helloService) {
        this.helloService = helloService;
    }

    @Override
    public String sayHello(String name) {
        // 在处理远程业务逻辑时加判断
        if (!StringUtils.isEmpty(name)){
            return helloService.sayHello(name);
        }
        return "i am sorry!";

    }

    @Override
    public String sayNo() {
        return helloService.sayNo();
    }
}
