package com.lagou.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @BelongsProject springbootdemo
 * @Author lengy
 * @CreateTime 2022/8/15 18:15
 * @Description
 */
@Component
public class RedisUtils {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 读取缓存
     * @param key
     * @return
     */
    public Object get(final String key){
        return redisTemplate.opsForValue().get(key);
    }


    /**
     * 写入缓存
     * @param key
     * @param value
     * @return
     */
    public boolean set(String key,Object value){
        boolean result = false;
        try {
            redisTemplate.opsForValue().set(key,value,1, TimeUnit.DAYS);
            result = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 更新缓存
     * @param key
     * @param value
     * @return
     */
    public boolean getAndSet(final String key,String value){
        boolean result = false;
        try {
            redisTemplate.opsForValue().getAndSet(key,value);
            result = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 删除缓存
     * @param key
     * @return
     */
    public boolean delete(final String key){
        boolean result = false;
        try {
            redisTemplate.delete(key);
            result = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
}
