package com.lagou.resposisory;

import com.lagou.pojo.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * @BelongsProject ElasticSearchDemo
 * @Author lengy
 * @CreateTime 2022/9/5 0:09
 * @Description 当使用SDE访问索引库时，需要定义一个持久层的接口去继承ElasticsearchRepository接口即可，无需实现
 */
// 实体类，实体ID类型
public interface ProductRepository extends ElasticsearchRepository<Product,Long> {

    /**
     * 查询价格范围
     * @param from
     * @param to
     * @return
     */
    List<Product> findByPriceBetween(Double from,Double to);
}
