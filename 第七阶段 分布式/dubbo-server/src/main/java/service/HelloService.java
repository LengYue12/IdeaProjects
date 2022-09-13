package service;

/**
 * @BelongsProject dubbo-server
 * @Author lengy
 * @CreateTime 2022/7/19 15:53
 * @Description 服务接口
 */
public interface HelloService {

    String sayHello(String name);
    String sayNo();
}
