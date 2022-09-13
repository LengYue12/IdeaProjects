package com.lagou.buffer;

import java.nio.ByteBuffer;

/**
 *  演示ByteBuffer创建的三种方式
 */
public class Demo01Buffer创建方式 {

    public static void main(String[] args) {

        // 1.第一种创建方式：在堆中创建缓冲区：allocate(int capacity)
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);

        // 2.第二种创建方式：在系统内存中创建缓冲区：allocateDirect(int capacity)
        ByteBuffer byteBuffer1 = ByteBuffer.allocateDirect(10);

        // 3.第三种创建方式：通过普通数组创建缓冲区：wrap(byte[] arr)
        byte[] arr = {97, 98, 99};
        ByteBuffer byteBuffer2 = ByteBuffer.wrap(arr);
    }
}
