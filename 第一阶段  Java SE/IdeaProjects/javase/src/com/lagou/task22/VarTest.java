package com.lagou.task22;

import java.util.LinkedList;
import java.util.List;

public class VarTest {

    public static void main(String[] args) {

        // 由初始值可以推断出变量的类型，因此可以使用var取代
//        int num = 10;
        var num = 10;

//        List<Integer> list = new LinkedList<>();
        var list = new LinkedList<Integer>();
        list.add(10);

        for (var v: list){
            System.out.println(v);
        }

        for (var i = 0; i < 10; i++){
            System.out.println(i);
        }
    }
}
