package entity;

import java.io.Serializable;

/**
 * @BelongsProject lagou-dubbo
 * @Author lengy
 * @CreateTime 2022/7/22 13:12
 * @Description 用户实体类
 */
public class Users implements Serializable {

    private Integer uid;

    private String username;

    private String password;

    private String phone;

    private String createtime;



    public Users() {
    }

    @Override
    public String toString() {
        return "Users{" +
            "uid=" + uid +
            ", username='" + username + '\'' +
            ", password='" + password + '\'' +
            ", phone='" + phone + '\'' +
            ", createtime='" + createtime + '\'' +
            '}';
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public Users(Integer uid, String username, String password, String phone, String createtime) {
        this.uid = uid;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.createtime = createtime;
    }
}
