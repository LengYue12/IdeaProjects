package com.lagou.entity;

/**
 * @author zs
 * @date 2022/6/30 20:48
 * @description
 */
/*
    VO:View Object  表现层对象：在表现层接收前台参数的
        封装由浏览器端发送给表现层的请求参数的对象
 */
public class PromotionAdPageVO {

    // 当前页
    private int currentPage;

    // 每页显示的条数
    private int pageSize;

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
