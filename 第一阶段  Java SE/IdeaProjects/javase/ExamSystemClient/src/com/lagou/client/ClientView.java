package com.lagou.client;

import com.lagou.model.User;
import com.lagou.model.UserMessage;

import java.io.IOException;
import java.util.Scanner;

/**
 * 编程实现客户端的主界面绘制和相应功能的实现
 */
public class ClientView {
    /**
     * 为了可以使用输入输出流，采用合成复用原则实现
     */
    private ClientInitClose cic;

    /**
     * 通过构造方法实现成员变量的初始化
     * @param cic
     */
    public ClientView(ClientInitClose cic){
        this.cic = cic;
    }
    /**
     * 自定义成员方法实现客户端主界面的绘制
     */
    public void clientMainPage() throws IOException, ClassNotFoundException {
        while (true) {
            System.out.println("  \n\n\t\t   在线考试系统");
            System.out.println("-------------------------------------");
            System.out.print("   [1] 学员系统");
            System.out.println("     [2] 管理员系统");
            System.out.println("   [0] 退出系统");
            System.out.println("-------------------------------------");
            System.out.println("请选择要进行的业务编号:");
//            Scanner sc = new Scanner(System.in);
//            int choose = sc.nextInt();
            int choose = ClientScanner.getScanner().nextInt();
            switch (choose) {
                case 1:
                    System.out.println("正在进入学员系统...");
                    break;
                case 2:
                    clientManagerLogin();
                    break;
                case 0:
                    System.out.println("正在退出系统...");
                    return;
                default:
                    System.out.println("输入错误，请重新选择！");
            }
        }
    }

    /**
     * 自定义成员方法实现客户端管理员登录的功能
     */
    private void clientManagerLogin() throws IOException, ClassNotFoundException {
        // 1.准备管理员登录的相关数据
        System.out.println("请输入管理员的账户信息：");
        String userName = ClientScanner.getScanner().next();
        System.out.println("请输入管理员的密码信息：");
        String password = ClientScanner.getScanner().next();
        UserMessage tum = new UserMessage("managerCheck", new User(userName, password));
        // 2.将UserMessage类型的对象通过对象输出流发送给服务器
        cic.getOos().writeObject(tum);
        System.out.println("客户端发送管理员账户信息成功！");
        // 3.接收服务器的处理结果并给出提示
        tum = (UserMessage) cic.getOis().readObject();
        if ("success".equals(tum.getType())) {
            System.out.println("登录成功，欢迎使用！");
        } else {
            System.out.println("用户名或密码错误！");
        }
    }
}
