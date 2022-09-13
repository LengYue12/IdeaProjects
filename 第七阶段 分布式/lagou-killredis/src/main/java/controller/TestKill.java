package controller;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @BelongsProject lagou-killredis
 * @Author lengy
 * @CreateTime 2022/7/24 23:34
 * @Description 测试秒杀
 */
@RestController
public class TestKill {

    @Autowired
    private Redisson redisson;


    @Autowired  // 自动注入Spring的Redis的工具模板类
    private StringRedisTemplate stringRedisTemplate;

    @RequestMapping("kill")
    // 只能解决一个Tomcat的并发问题:synchronized锁的是一个进程下的多线程并发，如果分布式环境，多个进程并发，这种方案就失效了！
    public synchronized String kill(){

        // 定义商品id
        String productKey = "HUAWEI-P40";
        // 通过Redisson 获得锁
        RLock rlock = redisson.getLock(productKey); // 底层源码就是集成了setnx，过期时间等操作


        // 上锁(过期时间为30秒)
        rlock.lock(30, TimeUnit.SECONDS);


        try {
            // 1.从Redis中获取手机的库存数量
            int phoneCount = Integer.parseInt(stringRedisTemplate.opsForValue().get("phone"));

            // 2.判断手机的数量是否够秒杀的
            if (phoneCount >0 ){
                phoneCount--;
                // 库存减少之后再将库存的值保存回Redis
                stringRedisTemplate.opsForValue().set("phone",phoneCount +"");
                System.out.println("库存-1，剩余" + phoneCount);
            }else {
                System.out.println("库存不足！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 释放锁
            rlock.unlock();
        }


        return "over";
    }





    @Bean
    public Redisson redisson(){
        Config config = new Config();
        // 使用单个Redis服务器
        config.useSingleServer().setAddress("redis://192.168.114.128:6379").setDatabase(0);
        // 使用集群Redis
        // config.useClusterServers().setScanInterval(200).addNodeAddress("","","");


        RedissonClient redissonClient = Redisson.create(config);
        return (Redisson) redissonClient;
    }
}
