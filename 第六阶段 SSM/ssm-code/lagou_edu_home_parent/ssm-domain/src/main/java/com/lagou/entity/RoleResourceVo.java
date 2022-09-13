package com.lagou.entity;

import java.util.List;

/**
 * @author zs
 * @date 2022/7/4 22:11
 * @description
 */
/*
    用来接收前台传递过来的 角色ID 与 资源ID集合
 */
public class RoleResourceVo {

    private Integer roleId;
    private List<Integer> resourceIdList;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public List<Integer> getResourceIdList() {
        return resourceIdList;
    }

    public void setResourceIdList(List<Integer> resourceIdList) {
        this.resourceIdList = resourceIdList;
    }
}
