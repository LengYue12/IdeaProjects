package com.lagou.domain;

/**
 * @author zs
 * @date 2022/6/4 17:31
 * @description
 */

/**
 * 多对多配置：
 *  就是两个一对多
 *      所以在这两个多对多任何一个实体中表示对方关系，都用集合
 *          而在映射配置文件中，使用<resultMap>和<collection>标签来做配置
 *
 *  多对多配置和一对多很相似，就是sql区别。
 *      一对多可能两张表作关联查询
 *      而多对多是要有中间表，进行三表联查
 */
public class Role {

    private Integer id;
    private String rolename;
    private String roleDesc;

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", rolename='" + rolename + '\'' +
                ", roleDesc='" + roleDesc + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }
}
