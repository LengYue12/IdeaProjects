package com.lagou.task21;

public abstract class AbstractCalculator {

    // 自定义成员方法实现将参数指定的表达式按照参数指定的规则进行切割并返回计算结果
    public int splitExpression(String exp, String op){
        String[] sArr = exp.split(op);
        return calculate(Integer.parseInt(sArr[0]), Integer.parseInt(sArr[1]));
    }
    // 自定义抽象方法实现运算
    public abstract int calculate(int ia, int ib);
}
