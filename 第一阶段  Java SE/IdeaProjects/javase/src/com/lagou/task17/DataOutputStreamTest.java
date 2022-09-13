package com.lagou.task17;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class DataOutputStreamTest {

    public static void main(String[] args) {
        DataOutputStream dos = null;

        try {
            // 1.创建DataOutputStream类型的对象与d:/a.txt文件关联
            dos = new DataOutputStream(new FileOutputStream("d:/a.txt"));
            // 2.准备一个整数数据66并写入输出流
            int num = 66;
            dos.writeInt(num);
            System.out.println("写入数据成功！");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 3.关闭流对象并释放有关的资源
            if (null != dos) {
                try {
                    dos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
