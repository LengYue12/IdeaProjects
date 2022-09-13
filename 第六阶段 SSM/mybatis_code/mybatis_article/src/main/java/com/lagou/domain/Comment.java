package com.lagou.domain;

/**
 * @author zs
 * @date 2022/6/9 19:58
 * @description
 */

/*
    实体类
        评论表
 */
public class Comment {

    private int id; // 评论id

    private String content; // 评论内容

    private String author;  // 评论作者

    private int a_id;       // 外键字段

    // 由于文章表和评论表是一对多关系。所以在评论表的实体中想表示和文章表的关系时 就创建一个一的一方的对象作为属性，来保存文章表的信息
    private Article article;

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", author='" + author + '\'' +
                ", a_id=" + a_id +
                ", article=" + article +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getA_id() {
        return a_id;
    }

    public void setA_id(int a_id) {
        this.a_id = a_id;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }
}
