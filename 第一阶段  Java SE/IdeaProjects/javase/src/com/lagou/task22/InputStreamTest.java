package com.lagou.task22;

import java.io.*;

public class InputStreamTest {

    public static void main(String[] args) {

        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = new FileInputStream("d:/a.txt");
            outputStream = new FileOutputStream("d:/b.txt");
            inputStream.transferTo(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != outputStream) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
