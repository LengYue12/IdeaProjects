package com.lagou.task21;

public class AbstractCalculatorTest {

    public static void main(String[] args) {

        AbstractCalculator abstractCalculator = new Plus();
        int res = abstractCalculator.splitExpression("1+1", "\\+");
        System.out.println("最终的运算结果是：" + res); // 2
    }
}
