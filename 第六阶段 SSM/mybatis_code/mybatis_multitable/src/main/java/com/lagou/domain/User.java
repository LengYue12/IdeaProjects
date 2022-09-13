package com.lagou.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author zs
 * @date 2022/6/4 14:32
 * @description
 */

/**
 * 在一方实体中怎么表示多方？
 *      在一方实体中创建一个集合，泛型是多方实体的类型
 *          在一方实体表示一对多关系时，用到集合。
 *          那么在映射配置文件中就使用<resultMap>和<collection>标签来做配置
 */

// 实体类需要实现Serializable      才能使这个实体对象的内容 实现二级缓存
public class User implements Serializable {

    private Integer id;
    private String username;
    private Date birthday;
    private String sex;
    private String address;

    // 在一方实体中想表示多方关系    就用集合，泛型是多方实体类型

    // 表示多方关系：集合:ordersList     代表了当前用户所具有的订单列表
    // 如果在实体中表示一对多用到集合      那么在映射配置文件中用的子标签是 collection
    private List<Orders> ordersList;

    // 表示多方关系：集合:roleList     代表了当前用户所具有的角色列表   collection
    private List<Role> roleList;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", birthday=" + birthday +
                ", sex='" + sex + '\'' +
                ", address='" + address + '\'' +
                ", ordersList=" + ordersList +
                ", roleList=" + roleList +
                '}';
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Orders> getOrdersList() {
        return ordersList;
    }

    public void setOrdersList(List<Orders> ordersList) {
        this.ordersList = ordersList;
    }
}
