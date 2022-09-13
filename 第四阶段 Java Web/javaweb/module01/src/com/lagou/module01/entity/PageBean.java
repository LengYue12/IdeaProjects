package com.lagou.module01.entity;

/**
 * 编程实现分页功能类的封装
 */
public class PageBean {
    private int page;   // 用于描述前端页面中第几页信息的成员变量
    private int row;    // 用于描述前端页面中每页记录数的成员变量
    private int start;  // 用于描述起始记录下标的成员变量

    public PageBean() {
    }

    public PageBean(int page, int row) {
        this.page = page;
        this.row = row;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getStart() {
        // 使用第几页信息-1再乘以每页的记录数就可以得到接下来从下标为多少的记录开始查询了
        return (page-1)*row;
    }
}
