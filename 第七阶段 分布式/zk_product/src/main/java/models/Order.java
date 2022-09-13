package models;

import java.io.Serializable;

/**
 * @author lengy
 * @date 2022/7/16 22:12
 * @description 商品表
 */
public class Order implements Serializable{

    private static final long serialVersionUID = 335968617583919881L;
    private String id;
    private Integer pid;
    private Integer userid;

    public Order() {
    }

    public Order(String id, Integer pid, Integer userid) {
        this.id = id;
        this.pid = pid;
        this.userid = userid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }
}
