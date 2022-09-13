package com.lagou.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author zs
 * @date 2022/6/23 18:23
 * @description
 */
/*
    文件上传后台代码
 */
@Controller // 生成实例存到IOC
public class FileUploadController {


    /*
        基于SpringMVC实现 单文件上传
     */
    @RequestMapping("/fileupload")  // 该方法的映射地址
    // 前台的文件上传项input框 里的name值和参数名filePic保持一致         MultipartFile 文件上传对应的类型，使用该类型的参数来接收前台所上传的文件内容
    // 当在前台选好了文件进行上传时，所上传的文件就会经过配置的文件上传解析器，解析该文件
    // 并把解析过后的内容封装到 MultipartFile 这个对象里，MultipartFile 这就是获取到的所上传的文件对象
    public String fileUpload(String username, MultipartFile filePic) throws IOException {

        // 获取表单的提交参数，完成文件上传
        System.out.println(username);

        // a.txt文件名   abc
        // 获取原始的文件上传名
        String originalFilename = filePic.getOriginalFilename();
        // 把当前接收到的上传文件存到哪个目录下       加上原始文件名
        filePic.transferTo(new File("F:/upload/" + originalFilename));


        // 跳转成功页面
        return "success";
    }




    /*
        多文件上传
            在方法中，接收到多个文件时，所用到的类型就是 MultipartFile[]  数组  表示接收多个文件
     */
    @RequestMapping("/filesupload")
    public String filesUpload(String username,MultipartFile[] filePic) throws IOException {

        // 获取表单的提交参数，完成文件上传
        System.out.println(username);

        // 对文件数组进行遍历
        // 取出每一个multipartFile 对象，进行操作
        // 获取到对应文件的原始文件名，再通过multipartFile 对象调用transferTo 方法保存到服务器 F:/upload 目录下
        for (MultipartFile multipartFile : filePic) {
            String originalFilename = multipartFile.getOriginalFilename();
            multipartFile.transferTo(new File("F:/upload/" + originalFilename));
        }

        return "success";
    }







}
