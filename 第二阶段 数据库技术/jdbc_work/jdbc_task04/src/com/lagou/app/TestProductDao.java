package com.lagou.app;

import com.lagou.dao.ProductDao;
import com.lagou.entity.Category;
import com.lagou.entity.Product;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

public class TestProductDao {

    ProductDao productDao = new ProductDao();

    // 测试 根据商品ID 获取商品名称，商品价格 以及商品所属分类的名称
    @Test
    public void testFindProductById() throws SQLException {

        // 1.调用方法获取 商品对象
        Product product = productDao.findProductById("1");

        // 2.打印信息
        System.out.println(product.getPname() + " " + product.getPrice() + " "
                + product.getCategory().getCname());

    }

    // 测试 查询指定分类Id  下的商品个数
    @Test
    public void testgetCount() throws SQLException {

        // 查询分类ID为 3的分类下 有几个商品
        int count = productDao.getCount("3");

        System.out.println("分类ID为3的商品个数是：" + count);
    }

    // 测试 查询指定分类ID 下的所有商品信息
    @Test
    public void testFindProductByCid() throws SQLException {

        // 查询分类ID为2 的所有商品信息
        List<Product> list = productDao.findProductByCid("2");

        for (Product product : list) {
            System.out.println(product);
        }
    }
}
