package service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import service.HelloService;

/**
 * @BelongsProject dubbo-server
 * @Author lengy
 * @CreateTime 2022/7/19 15:54
 * @Description 服务器实现类
 */
@Service
public class HelloServiceImpl01 implements HelloService {
    @Override
    public String sayHello(String name) {
        System.out.println("------1.0 被调用1次----");
        try {
            Thread.sleep(3000); // 模拟网络延迟
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Hello," + name + "!!!!!";
    }

    @Override
    public String sayNo() {
        System.out.println("1.0  ====no被调用了1次");
        try {
            Thread.sleep(3000); // 模拟网络延迟
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "no";
    }
}
