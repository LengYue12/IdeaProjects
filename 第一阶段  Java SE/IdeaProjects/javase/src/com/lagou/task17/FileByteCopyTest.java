package com.lagou.task17;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileByteCopyTest {

    public static void main(String[] args) {
        FileInputStream fis = null;
        FileOutputStream fos = null;

        try {
            // 1.创建FileInputStream类型的对象与d:/03 IO流的框架图.png文件关联
            fis = new FileInputStream("d:/03 IO流的框架图.png");
            // 2.创建FileOutputStream类型的对象与d:/IO流的框架图.png文件关联
            fos = new FileOutputStream("d:/IO流的框架图.png");
            // 3.不断从输入流中读取数据内容并写入到输出流中
            System.out.println("正在玩命地拷贝...");
            int res = 0;
            while ((res = fis.read()) != -1) {
                fos.write(res);
            }
            System.out.println("拷贝文件成功！");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 4.关闭流对象并释放有关的资源
            if (null != fos) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != fis) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
