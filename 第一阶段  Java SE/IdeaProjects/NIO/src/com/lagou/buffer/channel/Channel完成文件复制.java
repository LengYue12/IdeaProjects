package com.lagou.buffer.channel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class Channel完成文件复制 {
    /**
     * 需求：将D:\IO文件夹中的03 IO流的框架图.png复制到工程中
     * @param args
     */
    public static void main(String[] args) throws IOException {

        // 创建输入流和输出流(依赖IO流获取channel)
        FileInputStream fileInputStream = new FileInputStream("D:\\主播\\宛妹心语\\火箭全套\\自拍\\女仆\\女仆07.jpg");
        FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\zs\\IdeaProjects\\NIO\\复制.png");

        // 通过IO流获取channel通道
        FileChannel channel1 = fileInputStream.getChannel();
        FileChannel channel2 = fileOutputStream.getChannel();

        // 创建缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        // 循环读写
        while (channel1.read(byteBuffer) != -1){
            byteBuffer.flip();
            channel2.write(byteBuffer);
            byteBuffer.clear();
        }

        // 关流
        fileInputStream.close();
        fileOutputStream.close();

    }
}
