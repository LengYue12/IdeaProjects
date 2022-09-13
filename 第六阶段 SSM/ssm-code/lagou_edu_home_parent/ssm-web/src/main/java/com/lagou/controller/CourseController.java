package com.lagou.controller;

import com.lagou.entity.Course;
import com.lagou.entity.CourseVO;
import com.lagou.entity.ResponseResult;
import com.lagou.service.CourseService;
import com.lagou.utils.FileUploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zs
 * @date 2022/6/29 13:57
 * @description
 */
@RestController     // 组合注解，组合了 @Controller 和 @ResponseBody
@RequestMapping("/course")  // 一级目录
public class CourseController {

    @Autowired
    private CourseService courseService;



    /*
        多条件课程列表查询
     */
    @RequestMapping("/findCourseByCondition")    // 二级目录
    // 把前台传递过来的JSON解析封装到CourseVO
    public ResponseResult findCourseByCondition(@RequestBody CourseVO courseVO){

        // 调用service
        List<Course> list = courseService.findCourseByCondition(courseVO);

        // 把封装好的ResponseResult 对象返回给前台
        return new ResponseResult(true, 200, "响应成功", list);
    }


    /*
        课程图片上传
     */

    @RequestMapping("/courseUpload")
    // 参数名要与 前台请求参数 的key 保持一致
    public ResponseResult fileUpload(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {

        // 调用图片上传工具类
        Map<String, String> map = FileUploadUtils.fileUploadUtils(file,request);

        // 对象转为JSON响应给前台
        return new ResponseResult(true,200,"图片上传成功",map);
    }



    /*
        新增课程信息及讲师信息
        新增课程信息和修改课程信息要写在同一个方法中
     */
    @RequestMapping("/saveOrUpdateCourse")
    // 把前台发来的JSON转为courseVo 实体
    public ResponseResult saveOrUpdateCourse(@RequestBody CourseVO courseVO) throws InvocationTargetException, IllegalAccessException {

        ResponseResult responseResult = null;

        // 判断courseVO 里面的id值为不为空，从而决定当前执行的是更新操作还是新增操作
        // 更新操作，前台会传递id值的
        if (courseVO.getId() == null){
            // 调用service    把从前台接收并封装好的courseVo 进行传递
            courseService.saveCourseOrTeacher(courseVO);
            responseResult = new ResponseResult(true, 200, "新增成功", null);
        } else {
            // 修改
            courseService.updateCourseOrTeacher(courseVO);
            responseResult = new ResponseResult(true, 200, "修改成功", null);
        }

        // 给前台 ResponseResult 转为JSON的响应
        return responseResult;
    }


    /*
        根据id查询具体的课程信息及关联的讲师信息
     */
    @RequestMapping("/findCourseById")
    public ResponseResult findCourseById(Integer id){

        CourseVO courseVO = courseService.findCourseById(id);

        return new ResponseResult(true,200,"根据ID查询课程信息成功",courseVO);
    }


    /*
        修改课程状态
     */
    @RequestMapping("/updateCourseStatus")
    public ResponseResult updateCourseStatus(Integer id,Integer status){

        // 调用service，传递参数，完成课程状态的修改
        courseService.updateCourseStatus(id,status);

        // 响应数据
        Map<String, Object> map = new HashMap<>();
        map.put("status",status);

        return new ResponseResult(true,200,"课程状态修改成功",map);
    }
}
