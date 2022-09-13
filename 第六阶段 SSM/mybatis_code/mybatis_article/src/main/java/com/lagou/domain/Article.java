package com.lagou.domain;

import java.util.List;

/**
 * @author zs
 * @date 2022/6/9 19:58
 * @description
 */

/*
    实体类
        文章表
 */
public class Article {

    private int id; // 文章id

    private String title;   // 文章标题

    private String content; // 文章内容

    // 文章表和评论表是一对多关系，一个文章有多个评论，所以在一的一方创建一个集合，泛型是多的一方的类型
    private List<Comment> commentList;

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", commentList=" + commentList +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }
}
