package com.lagou.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @BelongsProject Sharding-JDBC
 * @Author lengy
 * @CreateTime 2022/9/8 17:33
 * @Description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PayOrderDaoTest {

    @Autowired
    private PayOrderDao payOrderDao;

    @Test
    public void testInsertOrder(){

        for (int i = 0; i < 10; i++) {
            payOrderDao.insertPayOrder(100+i,"小米手机",10);
        }
    }


    @Test
    public void testFindOrderByIds(){

        List<Long> ids = new ArrayList<>();


        // lg_order_1 库 的pay_order_2
        ids.add(781977071319515137L);
        ids.add(781977071684419585L);


        ids.add(781977071663448064L);  // lg_order_2 库 的pay_order_1
        ids.add(781978013574103041L);  // lg_order_2 库 的pay_order_2



//        // order_1
//        ids.add(781960232984117248L);
//        ids.add(781960233026060290L);
//
////        // order_2
//        ids.add(781960232984117249L);
//        ids.add(781960233026060289L);

        List<Map> orderByIds = payOrderDao.findOrderByIds(ids);

        System.out.println(orderByIds);
    }
}
