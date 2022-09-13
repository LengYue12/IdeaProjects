package service;

/**
 * @author lengy
 * @date 2022/7/16 22:13
 * @description 商品服务
 */
public interface ProductService {

    // 减库存接口
    void reduceStock(int id) throws Exception;
}
