package controller;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.ProductService;

/**
 * @author lengy
 * @date 2022/7/16 22:08
 * @description 控制层
 */
@Controller
public class ProductAction {


    @Autowired
    private ProductService productService;

    private String connectString = "192.168.114.128:2181,192.168.114.129:2181,192.168.114.130:2181";

    @GetMapping("/product/reduce")
    @ResponseBody
    public Object reduceStock(int id) throws Exception {

    // 重试策略 （1000毫秒试1次，最多试3次）
    RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
    //1.创建curator工具对象
    CuratorFramework client = CuratorFrameworkFactory.newClient(connectString, retryPolicy);
    client.start();

    // 2.根据工具对象创建“内部互斥锁”
        InterProcessMutex lock = new InterProcessMutex(client, "/product_"+id);
        try {
            // 3.加锁
            lock.acquire();


            productService.reduceStock(id);
        } catch (Exception e){
            if (e instanceof RuntimeException){
                throw e;
            }
        }finally {
            // 3.释放锁
            lock.release();
        }
        return "ok";
    }
}
