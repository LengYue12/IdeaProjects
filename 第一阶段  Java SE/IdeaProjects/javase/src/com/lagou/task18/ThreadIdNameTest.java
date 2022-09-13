package com.lagou.task18;

public class ThreadIdNameTest extends Thread{

    public ThreadIdNameTest(String name) {
        super(name);
    }

    @Override
    public void run(){
        System.out.println("子线程的编号是：" + getId() + "，名称是：" + getName()); // 13
        // 修改名称为"zhangfei"
        setName("zhangfei");
        System.out.println("修改后子线程的编号是：" + getId() + "，名称是：" + getName()); // 13
    }
    public static void main(String[] args) {

        ThreadIdNameTest tint = new ThreadIdNameTest("guanyu");
        tint.start();

        System.out.println("主线程的编号是：" + Thread.currentThread().getId() + "，名称是：" + Thread.currentThread().getName());
    }
}
