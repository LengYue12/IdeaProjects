package com.lagou.app;

import com.lagou.dao.OrdersDao;
import com.lagou.entity.Orders;
import com.lagou.entity.Product;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

public class TestOrdersDao {

    OrdersDao ordersDao = new OrdersDao();

    @Test
    public void testFindAllOrders() throws SQLException {

        List<Orders> allOrders = ordersDao.findAllOrders("001");

        for (Orders allOrder : allOrders) {
            System.out.println(allOrder);
        }
    }

    // 测试 获取订单编号为 order001的订单中的所有商品信息
    @Test
    public void testFindOrderById() throws SQLException {

        List<Product> order001 = ordersDao.findOrderById("order001");

        for (Product product : order001) {
            System.out.println(product);
        }


    }
}
