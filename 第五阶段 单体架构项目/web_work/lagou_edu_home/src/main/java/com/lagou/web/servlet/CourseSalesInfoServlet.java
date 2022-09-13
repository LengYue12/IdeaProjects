package com.lagou.web.servlet;

import com.lagou.base.Constants;
import com.lagou.pojo.Course;
import com.lagou.service.CourseService;
import com.lagou.service.impl.CourseServiceImpl;
import com.lagou.utils.DateUtils;
import com.lagou.utils.UUIDUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 张舒
 * @date 2022/5/12 22:18
 * @description
 */
@WebServlet("/courseSalesInfo")
public class CourseSalesInfoServlet extends HttpServlet {

    /**
     * 保存课程营销信息
     *      收集表单数据，封装到course对象中，将图片上传到Tomcat服务器中
     * 修改课程营销信息
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            // 1.创建Course对象  使用BeanUtils往Course对象中封装数据
            Course course = new Course();

            // 2.创建Map集合，用来收集数据
            Map<String,Object> map = new HashMap<>();

            // 3.编写文件上传代码
            // 通过FileUpload进行文件上传，创建磁盘工厂对象
            DiskFileItemFactory factory = new DiskFileItemFactory();

            // 4.创建文件上传核心对象
            ServletFileUpload fileUpload = new ServletFileUpload(factory);

            // 5.解析request对象，获取表单项集合
            List<FileItem> list = fileUpload.parseRequest(req);

            // 6.遍历集合，判断哪些是普通的表单项，哪些文件表单项
            for (FileItem item : list) {

                boolean formField = item.isFormField();
                if (formField){
                    // 是普通表单项，获取表单项中的数据，并保存到map集合
                    String fieldName = item.getFieldName();
                    String value = item.getString("UTF-8");
                    System.out.println(fieldName + " " + value);

                    // 保存到map中,使用map收集数据
                    map.put(fieldName,value);
                } else {
                    // 文件上传项
                    // 获取文件名
                    String fileName = item.getName();
                    // 避免重复，通过UUID作拼接
                    String newFileName = UUIDUtils.getUUID() + "_" + fileName;

                    // 获取输入流
                    InputStream inputStream = item.getInputStream();

                    // 获取项目运行的路径
                    String realPath = this.getServletContext().getRealPath("/");

                    // 切割，获取webapps的目录的路径
                    String webapps = realPath.substring(0, realPath.indexOf("lagou_edu_home"));

                    // 创建输出流
                    OutputStream outputStream = new FileOutputStream(webapps + "/upload/" + newFileName);

                    // 通过IOUtils拷贝
                    IOUtils.copy(inputStream,outputStream);

                    // 关闭流
                    outputStream.close();
                    inputStream.close();

                    // 将图片路径进行保存
                    map.put("course_img_url", Constants.LOCAL_URL + "/upload/" + newFileName);
                }
            }

            // 使用BeanUtils 将map中的数据封装到course对象
            BeanUtils.populate(course,map);
            // 业务处理
            CourseService courseService = new CourseServiceImpl();
            // 将表单项的数据都放到map中后，判断是否有id，有id就是修改操作，否则是新增
            // 若id不为空，就证明表单项有id，就是修改操作，否则就是新增操作,
            // 修改调用update，新增调用save
            if (map.get("id") != null){
                // 修改操作
                // 补全信息
                course.setUpdate_time(DateUtils.getDateFormart());
                String result = courseService.updateCourseSalesInfo(course);
                // 响应结果
                resp.getWriter().print(result);
            } else {
                // 新建操作

                String result = courseService.courseSalesInfo(course);

                // 响应结果
                resp.getWriter().print(result);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
