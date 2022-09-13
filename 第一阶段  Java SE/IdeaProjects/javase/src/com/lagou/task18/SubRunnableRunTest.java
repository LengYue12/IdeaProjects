package com.lagou.task18;

public class SubRunnableRunTest {

    public static void main(String[] args) {

        // 1.创建自定义类的对象，也就是实现Runnable接口类的对象
        SubRunnableRun subRunnableRun = new SubRunnableRun();
        // 2.适应该对象作为实参来构造Thread类型的对象
        Thread thread = new Thread(subRunnableRun);
        // 3.使用Thread类型的对象调用start方法
        thread.start();

        // 打印1 ~ 20之间的所有整数
        for (int i = 1; i <= 20; i++){
            System.out.println("-------main方法中：i = " + i);
        }
    }
}
