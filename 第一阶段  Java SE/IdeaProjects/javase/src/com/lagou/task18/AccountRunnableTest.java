package com.lagou.task18;

public class AccountRunnableTest implements Runnable{
    private int balance; // 用于描述是账户的余额

    public AccountRunnableTest(int balance) {
        this.balance = balance;
    }

    public AccountRunnableTest() {
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    @Override
    public void run() {
        synchronized (this) {
            // 1.模拟从后台查询账户余额的过程
            int temp = getBalance();
            // 2.模拟取款200元的过程
            if (temp >= 200){
                System.out.println("正在出钞，请稍后...");
                temp -= 200;
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("请取走您的钞票！");
            } else {
                System.out.println("余额不足，请核对您的账户余额!");
            }
            // 3.模拟将最新的账户余额写入到后台
            setBalance(temp);
        }
    }

    public static void main(String[] args) {

        AccountRunnableTest account = new AccountRunnableTest(1000);
        Thread thread = new Thread(account);
        Thread thread2 = new Thread(account);
        thread.start();
        thread2.start();

        System.out.println("主线程开始等待...");
        try {
            thread.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println( "最终的账户余额为：" + account.getBalance());
    }
}
