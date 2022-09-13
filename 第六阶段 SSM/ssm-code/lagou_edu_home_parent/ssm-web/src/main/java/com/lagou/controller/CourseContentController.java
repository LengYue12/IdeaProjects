package com.lagou.controller;

import com.lagou.entity.Course;
import com.lagou.entity.CourseLesson;
import com.lagou.entity.CourseSection;
import com.lagou.entity.ResponseResult;
import com.lagou.service.CourseContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zs
 * @date 2022/6/29 22:17
 * @description
 */
@RestController
@RequestMapping("/courseContent")
public class CourseContentController {

    @Autowired
    private CourseContentService courseContentService;


    @RequestMapping("/findSectionAndLesson")
    public ResponseResult findSectionAndLessonByCourseId(int courseId){

        // 调用service
        List<CourseSection> list = courseContentService.findSectionAndLessonByCourseId(courseId);

        return new ResponseResult(true,200,"章节及课时内容查询成功",list);
    }


    /*
        回显章节对应的课程信息
     */
    @RequestMapping("/findCourseByCourseId")
    public ResponseResult findCourseByCourseId(int courseId){

        // 查询出来的具体的course信息
        Course course = courseContentService.findCourseByCourseId(courseId);

        // 转为JSON响应给前台
        return new ResponseResult(true,200,"查询课程信息成功",course);

    }


    /*
        新增&更新章节信息
     */
    @RequestMapping("/saveOrUpdateSection")
    public ResponseResult saveOrUpdateSection(@RequestBody CourseSection courseSection){

        ResponseResult responseResult= null;
        // 判断是否携带了章节id
        if (courseSection.getId() == null) {
            // 新增
            courseContentService.saveSection(courseSection);
            responseResult = new ResponseResult(true,200,"新增章节成功",null);
        }else {
            // 更新
            courseContentService.updateSection(courseSection);
            responseResult =  new ResponseResult(true,200,"新增章节成功",null);
        }
        return responseResult;
    }



    /*
        修改章节状态
     */
    @RequestMapping("/updateSectionStatus")
    public ResponseResult updateSectionStatus(int id,int status){

        courseContentService.updateSectionStatus(id,status);

        // 数据响应
        Map<String,Object> map = new HashMap<>();
        map.put("status",status);

        return new ResponseResult(true,200,"修改章节状态成功",map);
    }




    /*
        新增&修改课时信息
     */
    @RequestMapping("/saveOrUpdateLesson")
    public ResponseResult saveOrUpdateLesson(@RequestBody CourseLesson courseLesson){

        ResponseResult responseResult = null;
        // 判断是否携带了课时id
        if (courseLesson.getId() == null) {
            // 新增
            courseContentService.saveLesson(courseLesson);
            responseResult = new ResponseResult(true,200,"新增课时成功",null);
        }else {
            // 修改
            courseContentService.updateLesson(courseLesson);
            responseResult = new ResponseResult(true,200,"修改课时成功",null);
        }
        return responseResult;
    }



    /*
        修改课时状态
     */
    @RequestMapping("/updateLessonStatus")
    public ResponseResult updateLessonStatus(int id,int status){

        courseContentService.updateLessonStatus(id,status);

        // 数据响应
        Map<String,Object> map = new HashMap<>();
        map.put("status",status);

        return new ResponseResult(true,200,"修改课时状态成功",map);
    }
}
