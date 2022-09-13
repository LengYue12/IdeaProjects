package com.lagou.demo02;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.util.List;

/*
*  解析出所有图书信息
* */
public class TestBook {

    public static void main(String[] args) throws DocumentException {

        // 1.创建XML解析对象
        SAXReader reader = new SAXReader();

        // 2.解析XML  获取文档对象
        Document document = reader.read("C:\\Users\\zs\\IdeaProjects\\jdbc_work\\jdbc_task05\\src\\com\\lagou\\demo02\\book.xml");

        // 3.获取 出版社为传智出版社的节点的所有内容
        List<Node> list = document.selectNodes("/books/book[@出版社='传智出版社']//*");
        for (Node node : list) {
            System.out.println(node.getName() + " = " + node.getText());
        }
    }
}
