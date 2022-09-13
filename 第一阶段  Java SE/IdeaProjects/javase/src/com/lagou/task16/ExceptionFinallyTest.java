package com.lagou.task16;

public class ExceptionFinallyTest {

    public static void main(String[] args) {

        try {
            int ia = 10;
            int ib = 0;
            System.out.println(ia / ib);
        } catch (ArithmeticException e) {
            e.printStackTrace();

            String str1 = null;
            str1.length();  // 会发生空指针异常

        } finally {
            System.out.println("无论是否发生异常都记得来执行我哦！");    // 依然是执行
        }

        System.out.println("Over！"); // 不执行了
    }
}
