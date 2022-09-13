package com.lagou.task17;

import java.io.*;

public class PrintStreamChatTest {

    public static void main(String[] args) {

        // 由手册可知：构造方法需要的是Reader类型的引用，但Reader类是个抽象类，实参只能传递子类的对象
        // 再由手册可知：System.in代表键盘输入，而且是InputStream类型的
        BufferedReader br = null;
        PrintStream ps = null;
        try {
            br = new BufferedReader(new InputStreamReader(System.in));
            ps = new PrintStream(new FileOutputStream("d:/a.txt"));
            while (true) {
                // 1.提示用户输入要发送的聊天内容并使用变量记录
                System.out.println("请输入要发送的聊天内容：");
                String str = br.readLine();
                // 2.判断用户输入的内容是否为"bye"，若是则聊天结束
                if ("bye".equalsIgnoreCase(str)) {
                    System.out.println("聊天结束！");
                    break;
                }
                // 3.若不是则将用户输入的内容写入到文件d:/a.txt中
                    ps.println(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 4.关闭流对象并释放有关的资源
            if (null != ps) {
                ps.close();
            }
            if (null != br) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
