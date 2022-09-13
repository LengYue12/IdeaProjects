package com.lagou.task16;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ExceptionThrowsTest {

    public static void show() throws IOException {
        FileInputStream fis = new FileInputStream("d:/a.txt");
        fis.close();
    }

    public static void main(String[] args) {
        try {
            show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
