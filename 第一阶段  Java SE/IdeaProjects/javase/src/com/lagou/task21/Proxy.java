package com.lagou.task21;

public class Proxy implements Sourceable {
    private Source source;
    public Proxy(){
        source = new Source();
    }
    @Override
    public void method() {
        source.method();
        System.out.println("我和装饰器模式其实是不一样的！");
    }
}
