package com.lagou.utils;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zs
 * @date 2022/6/30 21:48
 * @description
 */
/*
    图片上传工具类
 */
public class FileUploadUtils {
    public static final String LOCAL_URL = "http://192.168.114.128:8080";
    // 参数名要与 前台请求参数 的key 保持一致
    public static Map<String, String> fileUploadUtils(MultipartFile file, HttpServletRequest request) throws IOException {
        // 1.判断接收到的上传文件是否为空
        if (file.isEmpty()) {
            throw new RuntimeException();
        }

        // 2.获取项目部署路径
        // F:\software\apache-tomcat-8.5.55\webapps\ssm_web
        String realPath = request.getServletContext().getRealPath("/");
        // F:\software\apache-tomcat-8.5.55\webapps\
        String webappsPath = realPath.substring(0, realPath.indexOf("ssm-web"));

        // 3.获取原文件名
        //lagou.jpg
        String originalFilename = file.getOriginalFilename();

        // 4.生成新文件名
        // 1234.jpg
        String newFileName = System.currentTimeMillis() + originalFilename.substring(originalFilename.lastIndexOf("."));

        // 5.文件上传

        String uploadPath = webappsPath + "upload\\";
        File filePath = new File(uploadPath, newFileName);

        // 如果目录不存在就创建目录
        if (!filePath.getParentFile().exists()) {
            filePath.getParentFile().mkdirs();
            System.out.println("创建目录:" + filePath);
        }

        // 图片就进行了上传
        file.transferTo(filePath);

        // 6.将文件名和文件路径返回，进行响应
        Map<String, String> map = new HashMap<>();
        map.put("fileName", newFileName);
        // 根据路径可以在Tomcat 的webapps 目录下的 upload目录下 找到文件，并回显
        map.put("filePath", LOCAL_URL+"/upload/" + newFileName);

        return map;
    }
}
