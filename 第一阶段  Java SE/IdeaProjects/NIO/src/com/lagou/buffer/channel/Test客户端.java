package com.lagou.buffer.channel;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * BIO:同步阻塞
 */
public class Test客户端 {

    public static void main(String[] args) throws IOException {

        Socket socket = new Socket(InetAddress.getLocalHost(), 9999);
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write("你好呀".getBytes());
        outputStream.close();
        socket.close();
    }
}
