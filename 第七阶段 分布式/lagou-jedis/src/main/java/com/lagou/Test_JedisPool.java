package com.lagou;

import redis.clients.jedis.Jedis;

/**
 * @BelongsProject lagou-jedis
 * @Author lengy
 * @CreateTime 2022/7/24 23:15
 * @Description 测试jedis连接池
 */
public class Test_JedisPool {
    public static void main(String[] args) {

        Jedis jedis1 = JedisPoolUtil.getJedis();
        Jedis jedis2 = JedisPoolUtil.getJedis();

        System.out.println(jedis1==jedis2);

    }
}
