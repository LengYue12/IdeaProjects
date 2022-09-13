package com.lagou.task18;

public class RunnableIdNameTest implements Runnable{
    @Override
    public void run() {
        System.out.println("子线程的编号是：" + Thread.currentThread().getId() + "，名称是：" + Thread.currentThread().getName());
    }

    public static void main(String[] args) {

        /*RunnableIdNameTest rint = new RunnableIdNameTest();
        Thread t1 = new Thread(rint);
        t1.start();*/

        new Thread(new RunnableIdNameTest(), "zhangfei").start();

        System.out.println("主线程的编号是：" + Thread.currentThread().getId() + "主线程的名称是：" + Thread.currentThread().getName());
    }
}
