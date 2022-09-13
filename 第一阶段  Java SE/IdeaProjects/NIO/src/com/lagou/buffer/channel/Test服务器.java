package com.lagou.buffer.channel;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * BIO：同步阻塞     NIO:同步非阻塞(并发支持高)
 */
public class Test服务器 {

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(9999);
        Socket socket = serverSocket.accept();
        InputStream inputStream = socket.getInputStream();
        byte[] bytes = new byte[1024];
        int len = inputStream.read(bytes);
        System.out.println(new String(bytes, 0, len));
        socket.close();
    }
}
