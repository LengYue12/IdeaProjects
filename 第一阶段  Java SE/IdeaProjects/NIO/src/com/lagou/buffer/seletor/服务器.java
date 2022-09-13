package com.lagou.buffer.seletor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class 服务器 {

    public static void main(String[] args) throws IOException {

        // 小目标：通道注册到选择器上

        // 1.获取selector选择器
        Selector selector = Selector.open();

        // 2.获取通道
        ServerSocketChannel serverSocketChannel1 = ServerSocketChannel.open();
        ServerSocketChannel serverSocketChannel2 = ServerSocketChannel.open();
        ServerSocketChannel serverSocketChannel3 = ServerSocketChannel.open();

        serverSocketChannel1.bind(new InetSocketAddress(9999));
        serverSocketChannel2.bind(new InetSocketAddress(8888));
        serverSocketChannel3.bind(new InetSocketAddress(7777));

        // 3.设置为非阻塞(与selector一起使用时，channel必须处于非阻塞模式下，如果是阻塞的，会抛出异常)
        serverSocketChannel1.configureBlocking(false);
        serverSocketChannel2.configureBlocking(false);
        serverSocketChannel3.configureBlocking(false);

        // 4.将通道注册到选择器上 表示制定 监听事件为‘接收’事件
        serverSocketChannel1.register(selector, SelectionKey.OP_ACCEPT);
        serverSocketChannel2.register(selector, SelectionKey.OP_ACCEPT);
        serverSocketChannel3.register(selector, SelectionKey.OP_ACCEPT);

        // select():查询已经就绪的通道操作   返回值：表示有多少通道已经就绪
        // 阻塞：阻塞到至少有一个通道上的事件就绪了
        /*System.out.println(1);
        int select = selector.select();
        System.out.println("就绪的通道操作：" + select);*/

        // 5.采用轮询的方式，查询准备就绪的事件
        while (selector.select() > 0){

            // 6.集合中就是所有已经准备就绪的操作
            Set<SelectionKey> set = selector.selectedKeys();
            Iterator<SelectionKey> selectionKeys = set.iterator();
            while (selectionKeys.hasNext()){

                // 7.已经准备就绪的操作
                SelectionKey selectionKey  = selectionKeys.next();

                // 8.判断事件的类型

                ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();

                // 9.接收客户端发送过来的数据
                SocketChannel socketChannel = serverSocketChannel.accept();

                // 10.读取数据
                ByteBuffer bytebuffer = ByteBuffer.allocate(1024);
                int len = socketChannel.read(bytebuffer);

                // 11.打印
                System.out.println(new String(bytebuffer.array(), 0, len));

                // 12.资源关闭
                socketChannel.close();
            }
            selectionKeys.remove();
        }
        serverSocketChannel1.close();
        serverSocketChannel2.close();
        serverSocketChannel3.close();
    }
}
