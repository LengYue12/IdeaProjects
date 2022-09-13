package com.lagou.dao;

import com.lagou.entity.Category;
import com.lagou.entity.Product;
import com.lagou.utils.DruidUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;

public class ProductDao {

    // 需求1：根据商品ID 获取商品名称，商品价格 以及商品所属分类的名称
    public Product findProductById(String pid) throws SQLException {

        // 1.创建QueryRunner
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());

        // 2.编写SQL
        String sql = "select * from product where pid = ?";

        // 3.执行查询
        Product product = qr.query(sql, new BeanHandler<Product>(Product.class), pid);

        // 4.获取外键的值 对应分类的ID
        String cid = product.getCid();
        // 商品对应的分类信息
        Category category = findCategoryById(cid);
        product.setCategory(category);

        return product;
    }

    // 需求2：根据分类ID 查询分类的详细信息
    public Category findCategoryById(String cid) throws SQLException {

        // 1.创建QueryRunner
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());

        // 2.编写SQL
        String sql = "select * from category where cid = ?";

        Category category = qr.query(sql, new BeanHandler<Category>(Category.class), cid);

        return category;
    }

    // 需求3：查询指定分类ID 下的商品个数
    public int getCount(String cid) throws SQLException {

        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());

        String sql = "select count(*) from product where cid = ?";

        // 获取单列数据   使用ScalarHandler 来封装
        Long count = qr.query(sql, new ScalarHandler<>(), cid);

        // 将Long类型的包装类 转换为int类型 并返回
        return count.intValue();
    }

    // 需求4：查询指定分类ID 下所有的商品信息
    public List<Product> findProductByCid(String cid) throws SQLException {

        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());

        String sql = "select * from product where cid = ?";

        // 查询结果是一个List集合，使用BeanListHandler对象来封装
        List<Product> list = qr.query(sql, new BeanListHandler<Product>(Product.class), cid);

        return list;
    }
}
