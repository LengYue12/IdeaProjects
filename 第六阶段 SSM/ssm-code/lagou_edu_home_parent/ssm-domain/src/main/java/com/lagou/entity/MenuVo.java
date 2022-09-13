package com.lagou.entity;

/**
 * @author zs
 * @date 2022/7/7 15:01
 * @description
 */
public class MenuVo {

    // 当前页
    private Integer currentPage;
    // 每页显示条数
    private Integer pageSize;

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
}
