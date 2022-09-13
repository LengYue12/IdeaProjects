package com.lagou.entity;

import java.util.ArrayList;
import java.util.List;

/*  orders表
* oid` varchar(32) NOT NULL,
  `ordertime` datetime DEFAULT NULL,
  `total` double DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `telephone` varchar(20) DEFAULT NULL,
  `address` varchar(30) DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `uid` varchar(32) DEFAULT NULL
*
* */
public class Orders {

    private String oid; // 订单ID

    private String ordertime;   // 下单的时间

    private double total;   // 订单的总金额

    private String name;    // 收货人姓名

    private String telephone;   // 收货人电话

    private String address;     // 收货人地址

    private int state;          // 订单状态 1 代表已支付， 0 代表未支付

    // 外键uid
    private String uid;

    // 保存订单对应的 用户的详细信息
    private User user;

    // 描述 多以多对一关系 一个订单中 包含了多个订单项信息
    List<OrderItem> list = new ArrayList<>();

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(String ordertime) {
        this.ordertime = ordertime;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "oid='" + oid + '\'' +
                ", ordertime='" + ordertime + '\'' +
                ", total=" + total +
                ", name='" + name + '\'' +
                ", telephone='" + telephone + '\'' +
                ", address='" + address + '\'' +
                ", state=" + state +
                ", uid='" + uid + '\'' +
                '}';
    }
}
