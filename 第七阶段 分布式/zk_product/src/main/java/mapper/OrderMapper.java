package mapper;

import models.Order;
import org.apache.ibatis.annotations.Insert;

/**
 * @author lengy
 * @date 2022/7/16 22:11
 * @description 订单操作类
 */
public interface OrderMapper {

    // 添加订单
    @Insert("insert into `order` values(#{id},#{pid},#{userid})")
    int insert(Order order);
}
