package com.lagou.task17;

import java.io.FileWriter;
import java.io.IOException;

public class FileWriterTest {

    public static void main(String[] args) {
        FileWriter fw = null;

        try {
            // 1.构造FileWriter类型的对象与d:/a.txt文件关联
            fw = new FileWriter("d:/a.txt");
            //fw = new FileWriter("d:/a.txt", true);
            // 2.通过流对象写入数据内容
            fw.write('a');

            // 准备一个字符数组
            char[] cArr = new char[]{'h', 'e', 'l', 'l', 'o'};
            // 将字符数组中的一部分内容写入进去
            fw.write(cArr, 1, 3); // ell
            // 将整个字符数组写进去
            fw.write(cArr); // hello

            System.out.println("写入数据成功！");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 3.关闭流对象并释放有关的资源
            if (null != fw) {
                try {
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
