package com.lagou.task17;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileCharCopyTest {

    public static void main(String[] args) {
        FileReader fileReader = null;
        FileWriter fileWriter = null;

        try {
            // 1.创建FileReader类型的对象与d:/a.txt文件关联
            fileReader = new FileReader("d:/a.txt");
            // 2.创建FileWriter类型的对象与d:/b.txt文件关联
            fileWriter = new FileWriter("d:/b.txt");
            // 3.不断从输入流中读取数据内容并写入到输出流中
            System.out.println("正在玩命地拷贝...");
            int res = 0;
            while ((res = fileReader.read()) != -1){
                    fileWriter.write(res);

            }
            System.out.println("拷贝文件成功");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 4.关闭流对象并释放有关的资源
            if (null != fileWriter) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != fileReader) {
                try {
                    fileReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
