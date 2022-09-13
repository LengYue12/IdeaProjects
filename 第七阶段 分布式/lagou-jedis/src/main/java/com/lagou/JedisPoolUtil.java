package com.lagou;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @BelongsProject lagou-jedis
 * @Author lengy
 * @CreateTime 2022/7/24 23:00
 * @Description 单例模式优化Jedis连接池
 */
public class JedisPoolUtil {

    private JedisPoolUtil(){}

    private volatile static JedisPool jedisPool = null;
    private volatile static Jedis jedis = null;

    // 返回一个连接池
    private static JedisPool getInstance(){
        // 双层检测锁（企业中用的很频繁）
        if (jedisPool == null) {    // 第一层，检测体温
            synchronized (JedisPoolUtil.class){     // 排队进站
                if (jedisPool == null){ // 第二层，查看健康宝
                    JedisPoolConfig config = new JedisPoolConfig();
                    config.setMaxTotal(1000);
                    config.setMaxIdle(30);
                    config.setMaxWaitMillis(60*1000);
                    config.setTestOnBorrow(true);
                    jedisPool = new JedisPool(config,"192.168.114.128",6379);
                }
            }
        }
        return jedisPool;
    }

    // 返回jedis对象
    public static Jedis getJedis(){
        if (jedis == null) {
            Jedis jedis = getInstance().getResource();  // 获取连接对象
        }
        return jedis;
    }
}
