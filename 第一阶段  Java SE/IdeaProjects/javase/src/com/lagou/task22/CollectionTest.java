package com.lagou.task22;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class CollectionTest {

    public static void main(String[] args) {

        // 创建list类型的不可变实例
        List<Integer> list = List.of(1, 2, 3, 4, 5);
        //list.add(6);    // 编译ok，运行发生UnsupportedOperationException不支持此操作的异常
        System.out.println(list); // [1, 2, 3, 4, 5]

        Set<Integer> set = Set.of(6, 7, 8);
//        set.add(null);  // 编译ok，运行发生UnsupportedOperationException不支持此操作的异常
        System.out.println(set);

        Map<Integer, String> map = Map.of(1, "one", 2, "two", 3, "three");
//        map.put(4, "four");
        System.out.println(map);
    }
}
