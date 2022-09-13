package com.lagou.task16;

import com.lagou.task10.MyAnnotation;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileTest {
    // 自定义成员方法实现指定目录以及子目录中所有内容打印
    public static void show(File file){
        File[] files = file.listFiles();
        for (File ff : files){
            String name = ff.getName();
            // 判断是否为文件，若是则打印文件名称
            if (ff.isFile()) {
                System.out.println(name);
            }
            // 判断是否为目录，若是则使用[]将目录名称括起来
            if (ff.isDirectory()){
                System.out.println("[" + name + "]");
                show(ff);
            }
        }
    }

    public static void main(String[] args) throws IOException {

        // 1. 构造File类型的对象与d:/a.txt文件关联
        File file = new File("d:/a.txt");
        // 2. 若文件存在则获取文件的相关特征信息并打印删除文件
        if (file.exists()){
            System.out.println("文件的名称是：" + file.getName());
            System.out.println("文件的大小是：" + file.length());
            Date d1 = new Date(file.lastModified());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            System.out.println("文件的最后一次修改时间：" + sdf.format(d1));
            System.out.println("文件的绝对路径信息：" + file.getAbsolutePath());
            System.out.println(file.delete() ? "文件删除成功！": "文件删除失败！");
        } else {
            // 3. 若文件不存在则创建新的空文件
            System.out.println(file.createNewFile() ? "文件创建成功！": "文件创建失败！");
        }

        System.out.println("------------------------------------------------");
        // 4.实现目录的删除和创建
        File file1 = new File("d:/捣乱/猜猜我是谁/你猜我不猜/死鬼");
        if (file1.exists()){
            System.out.println("目录名称是：" + file1.getName());
            System.out.println(file1.delete() ? "目录删除成功！" : "目录删除失败！");
        } else {
            System.out.println(file1.mkdir() ? "目录创建成功！": "目录创建失败！");
            System.out.println(file1.mkdirs() ? "目录创建成功！": "目录创建失败！");
        }

        System.out.println("------------------------------------------------");
        // 5.实现将指定目录中的所有内容打印出来
        File file2 = new File("d:/捣乱");
        File[] files = file2.listFiles();
        for (File ff : files){
            String str = ff.getName();
            // 判断是否为文件，若是则直接打印文件名称
            if (ff.isFile()){
                System.out.println(str);
            }
            // 若是目录则使用[]将目录名称括起来
            if (ff.isDirectory()){
                System.out.println("[" + str + "]");
            }
        }
        System.out.println("------------------------------------------------");
        // 6.实现目录中所有内容获取的同时进行过滤
        // 匿名内部类的语法格式：接口/父类类型   引用变量名 = new 接口/父类类型(){ 方法的重写};
        /*FileFilter ff = new FileFilter(){
            @Override
            public boolean accept(File pathname) {
                return pathname.getName().endsWith(".av");
            }
        };*/
        // Lambda表达式的格式：(参数列表) -> { 方法体}
        FileFilter fileFilter = (pathname) -> { return pathname.getName().endsWith(".av");};
        File[] files1 = file2.listFiles(fileFilter);
        for (File ft : files1){
            System.out.println(ft);
        }
        System.out.println("------------------------------------------------");
        // 7.使用递归的思想获取目录以及子目录中的内容
        show(new File("d:/捣乱"));
    }
}
