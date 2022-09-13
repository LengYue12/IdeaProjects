package com.lagou.buffer;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class Demo02Buffer的方法 {

    public static void main(String[] args) {

        // 1.创建Buffer对象
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);

        // put(byte b):给数组添加元素
        byteBuffer.put((byte) 10);
        byteBuffer.put((byte) 20);
        byteBuffer.put((byte) 30);

        // 把缓冲区数组转换成普通数组
        byte[] array = byteBuffer.array();

        // 打印
        System.out.println(Arrays.toString(array));

        // get():获取一个元素
        byte b = byteBuffer.get(1);
        System.out.println(b);
    }
}
