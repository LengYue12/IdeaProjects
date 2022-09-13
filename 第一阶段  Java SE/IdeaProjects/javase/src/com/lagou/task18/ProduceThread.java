package com.lagou.task18;

/**
 * 编程实现生产者线程，不断地生产产品
 */
public class ProduceThread extends Thread {
    private StoreHouse storeHouse;

    // 为了确保两个线程共用同一个仓库
    public ProduceThread(StoreHouse storeHouse){
        this.storeHouse = storeHouse;
    }
    @Override
    public void run(){
        while (true){
            storeHouse.produceProduct();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
