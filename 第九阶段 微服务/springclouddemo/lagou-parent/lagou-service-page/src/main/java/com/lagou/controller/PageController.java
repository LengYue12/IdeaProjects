package com.lagou.controller;

import com.lagou.common.pojo.Products;
import com.lagou.fegin.ProductFeign;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @BelongsProject springclouddemo
 * @Author lengy
 * @CreateTime 2022/8/21 21:20
 * @Description 通过restTemplate 去访问了 服务提供方（商品薇服务） 本质上就是HTTP的远程调用 
 */
@RestController
@RequestMapping("/page")
public class PageController {

//    @Autowired
//    private RestTemplate restTemplate;
//
//    // 服务注册中心的客户端对象
//    @Autowired
//    private DiscoveryClient discoveryClient;

    // 注入Feign接口
    @Autowired
    private ProductFeign productFeign;


    @GetMapping("/getProduct/{id}")
    public Products getProduct(@PathVariable Integer id){
//        // 获得lagou-service-product在服务注册中心注册的服务列表
//        List<ServiceInstance> instances = discoveryClient.getInstances("lagou-service-product");
//        // 获得商品服务列表中的第一个
//        ServiceInstance serviceInstance = instances.get(0);
//        // 获得元数据
//        //Map<String, String> metadata = serviceInstance.getMetadata();
//
//        // 获得商品微服务的主机地址
//        String host = serviceInstance.getHost();
//        // 获得商品微服务的端口号
//        int port = serviceInstance.getPort();

        // 拼url
//        String url ="http://lagou-service-product/product/find/" + id;
//        // 发送Http请求给商品微服务，将id传递过去，获取到id所对应的product对象
//        Products products = restTemplate.getForObject(url, Products.class);
//        return products;

       return   productFeign.findById(id);
    }


    @GetMapping("/loadProductServicePort")
    public String getProductServerPort(){
//        String url = "http://lagou-service-product/service/port";
//
//        return restTemplate.getForObject(url, String.class);
        return productFeign.getPort();
    }

    /**
     * 模拟服务超时，熔断处理
     * 针对于熔断处理，Hystrix会默认维护一个线程池，默认大小为10。
     * @return
     */
    @HystrixCommand(
        threadPoolKey = "getProductServerPort2", // 默认所有的请求共同维护一个线程池，实际开发中：每个方法维护一个线程池
        // 每个属性对应的都是一个HystrixProperty
        threadPoolProperties = {
            @HystrixProperty(name = "coreSize",value = "1"),  // 并发的线程数
            @HystrixProperty(name = "maxQueueSize",value = "20")// 默认线程队列值是-1，默认不开启
        },
        // 超时时间的设置
        commandProperties = {
        // 每个属性对应的都是一个HystrixProperty
        // 设置请求的超时时间，一旦请求超过此时间，那么都按照超时处理，默认超时时间是1s
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "2000")
        }
    )
    @GetMapping("/loadProductServicePort2")
    public String getProductServerPort2(){
//        String url = "http://lagou-service-product/service/port";
//
//        return restTemplate.getForObject(url, String.class);
        return productFeign.getPort();
    }


    /**
     * 服务降级的演示：是在服务熔断之后的兜底操作
     */
    @HystrixCommand(// 超时时间的设置
        commandProperties = {
            // 每个属性对应的都是一个HystrixProperty
            // 设置请求的超时时间，一旦请求超过此时间，那么都按照超时处理，默认超时时间是1s
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
            // 统计出现错误之后的时间窗口的设置
            @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "8000"),
            // 统计窗口内的最小的请求数
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "2"),
            // 统计窗口内错误请求阈值的设置   50%  只要有一半的请求达到了阈值，那就意味着要熔断了
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
            // 自我修复的活动窗口时间
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "3000")
        },// 设置回退方法
        fallbackMethod = "getProductServerPortFallBack")
    @GetMapping("/loadProductServicePort3")
    public String getProductServerPort3(){
//        String url = "http://lagou-service-product/service/port";
//
//        return restTemplate.getForObject(url, String.class);
        return productFeign.getPort();
    }


    /**
     * 定义回退方法：当请求触发熔断后执行，补救措施
     * 注意：
     *  1.方法形参和原方法保持一致
     *  2.方法的返回值与原方法保持一致
     */
    public String getProductServerPortFallBack(){

        return "-1";
    }
}
