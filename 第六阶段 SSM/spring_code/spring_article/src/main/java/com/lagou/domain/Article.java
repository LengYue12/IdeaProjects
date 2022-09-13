package com.lagou.domain;

/**
 * @author zs
 * @date 2022/6/20 20:12
 * @description
 */
/*
    文章实体类
 */
public class Article {

    // 文章id
    private Integer id;
    // 文章标题
    private String title;
    // 文章内容
    private String content;

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
