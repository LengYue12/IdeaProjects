package com.lagou.task21;

public class Decorator implements Sourceable {
    private Sourceable source;
    public Decorator(Sourceable source){
        this.source = source;
    }
    @Override
    public void method() {
        source.method();
        System.out.println("化妆之后更美！");
    }
}
