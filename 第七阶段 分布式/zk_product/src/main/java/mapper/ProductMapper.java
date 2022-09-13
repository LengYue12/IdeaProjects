package mapper;

import models.Product;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author lengy
 * @date 2022/7/16 22:11
 * @description 商品操作类
 */
public interface ProductMapper {

    // 查询商品（目的查询库存）
    @Select("select * from product where id = #{id}")
    Product getProduct(@Param("id") int id);


    // 减库存
    @Update("update product set stock = stock -1 where id = #{id}")
    int reduceStock(@Param("id") int id);
}
