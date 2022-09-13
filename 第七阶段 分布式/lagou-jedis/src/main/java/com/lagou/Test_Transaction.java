package com.lagou;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

/**
 * @BelongsProject lagou-jedis
 * @Author lengy
 * @CreateTime 2022/7/24 22:38
 * @Description 测试事务
 */
public class Test_Transaction {

    public static void main(String[] args) throws InterruptedException {

        Jedis jedis = new Jedis("192.168.114.128",6379);;

        int yue = Integer.parseInt(jedis.get("yue"));
        int zhichu = 10;

        jedis.watch("yue");     // 监听余额，监听变更频繁值，进行监控，防止多线程问题

        Thread.sleep(5000); // 模拟的网络延迟


        if (yue < zhichu) {
            jedis.unwatch();    // 解除监控
            System.out.println("余额不足！");
        } else {
            Transaction transaction = jedis.multi();  // 开启事务
            transaction.decrBy("yue",zhichu);   // 余额减少
            transaction.incrBy("zhichu",zhichu);    // 累计消费增加
            transaction.exec();
            System.out.println("余额:" + jedis.get("yue"));
            System.out.println("累计支出：" + jedis.get("zhichu"));
        }
    }
}
