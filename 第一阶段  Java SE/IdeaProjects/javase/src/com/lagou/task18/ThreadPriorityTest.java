package com.lagou.task18;

public class ThreadPriorityTest extends Thread{

    @Override
    public void run(){
//        System.out.println("子线程的优先级是：" + getPriority());
        for (int i = 0; i < 20; i++){
            System.out.println("子线程中i = " + i);
        }
    }

    public static void main(String[] args) {

        ThreadPriorityTest tpt = new ThreadPriorityTest();
        // 设置子线程的优先级
        tpt.setPriority(Thread.MAX_PRIORITY);
        tpt.start();


//        System.out.println("主线程的优先级是：" + Thread.currentThread().getPriority());
        for (int i = 0; i < 20; i++){
            System.out.println("-------主线程中i = " + i);
        }
    }
}
