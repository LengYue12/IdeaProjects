package com.lagou.task18;

public class SubRunnableTest {

    public static void main(String[] args) {

        SubThread1 st1 = new SubThread1();
        SubThread2 st2 = new SubThread2();

        Thread t1 = new Thread(st1);
        Thread t2 = new Thread(st2);

        t1.start();
        t2.start();

        System.out.println("主线程开始等待...");
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("主线程等待结束！");
    }
}
