package com.lagou.task17;

import java.io.FileReader;
import java.io.IOException;

public class FileReaderTest {

    public static void main(String[] args) {
        FileReader fr = null;

        try {
            // 1.构造FileReader类型的对象与d:/a.txt文件关联
            fr = new FileReader("d:a.txt");
            // 2.读取数据内容并打印
            /*
            int res = fr.read();
            System.out.println("读取到的单个字符是：" + (char)res);
             */
            /*int res = 0;
            while ((res = fr.read()) != -1) {
                System.out.println("读取到的单个字符是：" + (char)res);
            }*/

            // 准备一个字符数组来保存读取到的数据内容
            char[] cArr = new char[5];
            /*// 期望读满字符数组中的一部分空间
            int res = fr.read(cArr, 1, 3);
            System.out.println("实际读取到的字符个数是：" + res); //
            for (char cv: cArr) {
                System.out.println("读取到的单个字符是：" + (char)cv);
            }*/
            // 期望读满整个字符数组
            int res = fr.read(cArr);
            System.out.println("实际读取到的字符个数是：" + res); //
            for (char cv: cArr) {
                System.out.println("读取到的单个字符是：" + (char)cv);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 3.关闭流对象并释放有关的资源
            if (null != fr) {
                try {
                    fr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
