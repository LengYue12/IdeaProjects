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
        ids.add(774712035991617537L);
        ids.add(774712036343939073L);


        ids.add(774711637683732480L);  // lg_order_2 库 的pay_order_1
        ids.add(774711637297856513L);  // lg_order_2 库 的pay_order_2



//        // order_1
//        ids.add(774694205535551488L);
//        ids.add(774694205569105920L);
//
//        // order_2
//        ids.add(774689818159349761L);
//        ids.add(774690132740538369L);

        List<Map> orderByIds = payOrderDao.findOrderByIds(ids);

        System.out.println(orderByIds);
    }
}
