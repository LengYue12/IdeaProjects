package com.lagou.web.servlet;

import com.lagou.utils.UUIDUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * @author 张舒
 * @date 2022/5/12 20:26
 * @description
 */
@WebServlet("/upload")
public class FileUploadServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 使用FileUpload完成文件上传到后台
        try {
            // 1.创建磁盘文件项工厂
            DiskFileItemFactory factory = new DiskFileItemFactory();

            // 2.创建文件上传核心类
            ServletFileUpload fileUpload = new ServletFileUpload(factory);

            // 2.1 设置上传文件名的编码
            fileUpload.setHeaderEncoding("utf-8");
            // 2.2 判断表单是否为文件上传表单
            boolean multipartContent = ServletFileUpload.isMultipartContent(req);
            // 2.3 是文件上传表单
            if (multipartContent) {

                // 3.解析request，获取文件项集合
                List<FileItem> list = fileUpload.parseRequest(req);

                if (list != null) {

                    // 4.遍历获取表单项
                    for (FileItem item : list) {
                        // 5.判断是不是一个普通表单项
                        boolean formField = item.isFormField();
                        if (formField) {
                            // 普通表单项
                            String fieldName = item.getFieldName();
                            // 设置编码
                            String value = item.getString("utf-8");

                            System.out.println(fieldName + "=" + value);
                        } else {
                            // 文件上传项
                            // 文件名
                            String name = item.getName();
                            // 避免图片名重复，拼接UUID
                            String newFileName = UUIDUtils.getUUID() + "_" + name;

                            // 获取文件上传的内容，指向文件
                            InputStream inputStream = item.getInputStream();

                            // 获取项目的运行目录
                            String realPath = this.getServletContext().getRealPath("/");

                            // 截取到webapps目录路径
                            String webapps = realPath.substring(0, realPath.indexOf("lagou_edu_home"));

                            // 拼接输出路径，将图片保存到 upload
                            OutputStream outputStream = new FileOutputStream(webapps + "/upload/" + newFileName);

                            // 拷贝文件
                            IOUtils.copy(inputStream,outputStream);

                            // 关闭流
                            outputStream.close();
                            inputStream.close();
                        }
                    }

                }
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
