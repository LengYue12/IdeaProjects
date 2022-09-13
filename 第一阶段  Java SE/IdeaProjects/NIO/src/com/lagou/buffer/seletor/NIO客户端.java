package com.lagou.buffer.seletor;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class NIO客户端 {

    public static void main(String[] args) throws IOException {

        /**
         *         Socket socket = new Socket(InetAddress.getLocalHost(), 9999);
         *         OutputStream outputStream = socket.getOutputStream();
         *         outputStream.write("你好呀".getBytes());
         *         outputStream.close();
         *         socket.close();
         */

        // 1.创建对象
        SocketChannel socketChannel1 = SocketChannel.open();
        socketChannel1.connect(new InetSocketAddress(InetAddress.getLocalHost(), 7777));

        // 创建缓冲区数组
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        // 设置数据
        byteBuffer.put("哈哈哈".getBytes());

        byteBuffer.flip();
        // 输出数据
        socketChannel1.write(byteBuffer);

        socketChannel1.close();


    }
}
