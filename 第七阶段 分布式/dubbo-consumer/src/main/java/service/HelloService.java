package service;

/**
 * @BelongsProject dubbo-consumer
 * @Author lengy
 * @CreateTime 2022/7/19 17:45
 * @Description 服务方接口（声明而已，具体实现会远程调用dubbo-server的service实现类）
 */
public interface HelloService {

    String sayHello(String name);
    String sayNo();
}
