package com.lagou.domain;

/**
 * @author zs
 * @date 2022/6/4 14:33
 * @description
 */

/**
 * 在多方实体中怎么表示一方？
 *      在多方实体中创建一个一方实体的对象作为属性
 *          在多方实体表示多对一关系时，用到实体对象。
 *          那么在映射配置文件中就使用<resultMap>和<association>标签来做配置
 */
public class Orders {

    private Integer id;
    private String ordertime;
    private Double total;
    private Integer uid;

    // 在多方实体中如果想表示一方关系  就用一方的实体对象作为属性

    // 表示当前订单属于哪个用户 association
    private User user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(String ordertime) {
        this.ordertime = ordertime;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "id=" + id +
                ", ordertime='" + ordertime + '\'' +
                ", total=" + total +
                ", uid=" + uid +
                ", user=" + user +
                '}';
    }
}
