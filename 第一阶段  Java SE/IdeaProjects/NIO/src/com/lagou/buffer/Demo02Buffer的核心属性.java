package com.lagou.buffer;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class Demo02Buffer的核心属性 {

    public static void main(String[] args) {

        // 1.创建Buffer对象
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        System.out.println("初始化------------->capacity----->" + byteBuffer.capacity());
        System.out.println("初始化------------->limit----->" + byteBuffer.limit());
        System.out.println("初始化------------->position----->" + byteBuffer.position());

        System.out.println("--------------------------------");

        // 添加一些数据到缓冲区
        String s = "JavaEE";
        byteBuffer.put(s.getBytes());

        System.out.println("put后------------->capacity----->" + byteBuffer.capacity());
        System.out.println("put后------------->limit----->" + byteBuffer.limit());
        System.out.println("put后------------->position----->" + byteBuffer.position());

        System.out.println("--------------------------------");
        byteBuffer.flip();
        System.out.println("flip()后------------->capacity----->" + byteBuffer.capacity());
        System.out.println("flip()后------------->limit----->" + byteBuffer.limit());
        System.out.println("flip()后------------->position----->" + byteBuffer.position());
        System.out.println("--------------------------------");

        // (1)创建一个limit()大小的字节数组（因为就只有limit这么多个数据可读）
        byte[] bytes = new byte[byteBuffer.limit()];

        // (2)将读取出来的数据装进字节数组中
        byteBuffer.get(bytes);

        System.out.println("get()后------------->capacity----->" + byteBuffer.capacity());
        System.out.println("get()后------------->limit----->" + byteBuffer.limit());
        System.out.println("get()后------------->position----->" + byteBuffer.position());

        // (3)输出数据
        System.out.println(new String(bytes, 0, bytes.length));

        byteBuffer.clear();
        System.out.println("clear()后------------->capacity----->" + byteBuffer.capacity());
        System.out.println("clear()后------------->limit----->" + byteBuffer.limit());
        System.out.println("clear()后------------->position----->" + byteBuffer.position());

        byteBuffer.put("lagou".getBytes());


    }
}
