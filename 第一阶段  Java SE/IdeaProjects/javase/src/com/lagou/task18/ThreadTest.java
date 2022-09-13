package com.lagou.task18;

public class ThreadTest {

    public static void main(String[] args) {

        // 1.使用无参方式构造Thread类型的对象
        // 由源码可知：Thread类中的成员变量Target的数值为null
        Thread thread = new Thread();
        // 2.调用run方法进行测试
        // 由源码可知：由于成员变量target的数值为null，因此条件if(target != null)不成立，跳过不执行
        // 而run方法中除了上述代码再无代码，因此证明run方法确实啥也不干
        thread.run();
        // 3.打印一句话
        System.out.println("我想看看你是否真的啥也不干！");
    }
}
