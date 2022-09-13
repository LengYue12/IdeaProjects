package com.lagou;

import redis.clients.jedis.Jedis;

/**
 * @BelongsProject lagou-jedis
 * @Author lengy
 * @CreateTime 2022/7/24 21:45
 * @Description 测试连接Redis
 */
public class Test1 {

    public static void main(String[] args) {
        Jedis jedis = new Jedis("192.168.114.128",6379);
        String pong = jedis.ping();
        System.out.println("pong = " + pong);
    }
}
