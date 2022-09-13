package com.lagou.xml05;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCUtils {

    // 1.定义字符串变量 保存连接信息
    public static String DRIVERNAME;
    public static String URL;
    public static String USERNAME;
    public static String PASSWORD;

    // 2.静态代码块
    static {

        // 使用XPath语法对XML中的数据进行读取
        try {
            Document document = new SAXReader().read("C:\\Users\\zs\\IdeaProjects\\jdbc_work\\xml_task03\\src\\com\\lagou\\xml05\\jdbc-config.xml");

            // 1.获取驱动名称
            Node driver = document.selectSingleNode("/jdbc/property[@name='driverClass']");
            DRIVERNAME = driver.getText();

            // 2.获取URL
            Node url = document.selectSingleNode("jdbc/property[@name='jdbcUrl']");
            URL = url.getText();

            // 3.获取用户名
            Node user = document.selectSingleNode("jdbc/property[@name='user']");
            USERNAME = user.getText();

            // 4.获取密码
            Node pass = document.selectSingleNode("jdbc/property[@name='password']");
            PASSWORD = pass.getText();

            // 注册驱动
            Class.forName(DRIVERNAME);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 获取连接
    public static Connection getConnection(){
        try {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
