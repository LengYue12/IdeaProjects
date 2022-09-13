package service.impl;

import mapper.OrderMapper;
import mapper.ProductMapper;
import models.Order;
import models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.ProductService;

import java.util.UUID;

/**
 * @author lengy
 * @date 2022/7/16 22:14
 * @description 服务实现类
 */
@Service
public class ProductServiceImpl implements ProductService {


    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public void reduceStock(int id) throws Exception {
        // 1.获取库存(根据商品id查询商品)
        Product product = productMapper.getProduct(id);


        // 没有库存了
        if (product.getStock() <= 0) {
            throw new RuntimeException("已抢光！");
        }

        // 2.减库存
        int i = productMapper.reduceStock(id);
        // 减库存成功
        if (i == 1) {
            // 3.生成订单
            Order order = new Order();
            order.setId(UUID.randomUUID().toString());  // 使用UUID工具生成订单号
            order.setPid(id);
            order.setUserid(101);

            orderMapper.insert(order);
        } else {
            throw new RuntimeException("减库存失败！");
        }
    }
}
