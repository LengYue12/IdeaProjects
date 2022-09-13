package com.lagou.task18;

public class ThreadTest1 {

    public static void main(String[] args) {

        /*new SubThread1().start();
        new SubThread2().start();*/
       SubThread1 st1 = new SubThread1();
       SubThread2 st2 = new SubThread2();
       st1.start();
       st2.start();
        System.out.println("主线程开始等待...");
        try {
            st1.join();
            st2.join();
           /* new SubThread1().join();
            new SubThread2().join();*/
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("主线程等待结束！");

    }
}
