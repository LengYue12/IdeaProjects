package com.lagou.task18;

import com.lagou.task09.Account;

public class AccountThreadTest extends Thread {

    // 用于描述账户余额
    private int balance;

    public AccountThreadTest() {
    }

    public AccountThreadTest(int balance) {
        this.balance = balance;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    @Override
    public void run() {
        synchronized (AccountThreadTest.class) {
            // 1.模拟后台取款操作
            int temp = getBalance();
            // 2.模拟取款200元的过程
            if (temp >= 200) {
                System.out.println("正在出钞，请稍后...");
                temp -= 200;
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("请取走您的钞票！");
            } else {
                System.out.println("余额不足，请核对您的账户余额！");
            }
            // 3.模拟将最新的账户余额写入到后台
            setBalance(temp);
        }
    }

    public static void main(String[] args) {

        AccountThreadTest att1 = new AccountThreadTest(1000);
        AccountThreadTest att2 = new AccountThreadTest(1000);
        att1.start();
        att2.start();

        try {
            att1.join();
            att2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("最终的账户余额为：" + att1.getBalance());
    }
}
