package com.lagou.xml03;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;

import java.util.List;

public class TestDOM4j {

    // 获取XML文件中的所有元素(标签)

    @Test
    public void test1() throws DocumentException {

        // 1.获取XML解析对象
        SAXReader reader = new SAXReader();

        // 2.解析XML 获取 文档对象 Document
        Document document = reader.read("C:\\Users\\zs\\IdeaProjects\\jdbc_work\\xml_task03\\src\\com\\lagou\\xml03\\user.xml");

        // 3.获取元素
        Element rootElement = document.getRootElement();

        // 获取根元素名称
        System.out.println(rootElement.getName());

        // 获取根元素下的标签
        List<Element> elements = rootElement.elements();

        for (Element element : elements) {
            System.out.println("根标签下的子节点：" + element.getName());


            List<Element> eList = element.elements();
            for (Element e : eList) {
                System.out.println("user标签下的子节点" + e.getName());
            }
            break;
        }
    }

    // 获取XML中 标签的文本信息 和 属性信息
    @Test
    public void test2() throws DocumentException {

        // 1.获取解析XML的 SAXReader
        SAXReader reader = new SAXReader();

        // 2.获取文档对象
        Document document = reader.read("C:\\Users\\zs\\IdeaProjects\\jdbc_work\\xml_task03\\src\\com\\lagou\\xml03\\user.xml");

        // 3.获取根节点
        Element rootElement = document.getRootElement();

        // 4.获取子节点  user
        List<Element> elements = rootElement.elements();

        // 5.获取集合中的第一个子节点
        Element user = elements.get(0);

        // 6.获取节点中的文本信息
        String id = user.attributeValue("id"); // 获取属性 id的值
        String name = user.elementText("name");
        String age = user.elementText("age");
        String hobby = user.element("hobby").getText();

        // 打印
        System.out.println(id + " " + name + " " + age + " " + hobby);
    }
}
