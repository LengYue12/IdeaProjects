package com.lagou.task18;

public class ConsumerThread extends Thread {
    private StoreHouse storeHouse;

    // 为了确保两个线程共用同一个仓库
    public ConsumerThread(StoreHouse storeHouse){
        this.storeHouse = storeHouse;
    }
    @Override
    public void run(){
        while (true){
            storeHouse.consumerProduct();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
