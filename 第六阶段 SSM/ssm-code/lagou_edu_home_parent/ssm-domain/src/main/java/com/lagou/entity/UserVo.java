package com.lagou.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * @author zs
 * @date 2022/7/1 16:28
 * @description
 */
public class UserVo {

    // 当前页
    private Integer currentPage;
    // 每页显示条数
    private Integer pageSize;

    // 多条件查询：用户名（手机号）
    private String username;
    // 注册起始时间
    // springMVC 只能默认接收这种格式的字符串 2020/11/11 转为Date类型
    // 但前台传递的是 2020-11-11 这种格式，那么springMVC就不能自动类型转了
    // 所以要使用自定义类型转换器   或者注解

    // 指定前台传递过来的日期字符串对应的格式是 yyyy-MM-dd 这样的
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startCreateTime;
    // 注册结束时间

    // 前台传递过来的日期格式 的字符串 是以横杠 分隔的
    // 所以使用 注解
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endCreateTime;


    private List<Integer> roleIdList;
    private int userId;

    public List<Integer> getRoleIdList() {
        return roleIdList;
    }

    public void setRoleIdList(List<Integer> roleIdList) {
        this.roleIdList = roleIdList;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getStartCreateTime() {
        return startCreateTime;
    }

    public void setStartCreateTime(Date startCreateTime) {
        this.startCreateTime = startCreateTime;
    }

    public Date getEndCreateTime() {
        return endCreateTime;
    }

    public void setEndCreateTime(Date endCreateTime) {
        this.endCreateTime = endCreateTime;
    }
}
