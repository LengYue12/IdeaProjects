package com.lagou.controller;

import com.github.pagehelper.PageInfo;
import com.lagou.entity.PromotionAd;
import com.lagou.entity.PromotionAdPageVO;
import com.lagou.entity.ResponseResult;
import com.lagou.service.PromotionAdService;
import com.lagou.utils.FileUploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

/**
 * @author zs
 * @date 2022/6/30 20:56
 * @description
 */
@RestController
@RequestMapping("/PromotionAd")
public class PromotionAdController {

    @Autowired
    private PromotionAdService promotionAdService;


    /*
        广告分页查询
     */
    @RequestMapping("/findAllPromotionAdByPage")
    // 使用 PromotionAdVO 这个对象来获取前台传递过来的请求参数
    public ResponseResult findAllPromotionAdByPage(PromotionAdPageVO promotionAdPageVO){

        PageInfo<PromotionAd> pageInfo = promotionAdService.findAllPromotionAdByPage(promotionAdPageVO);

        return new ResponseResult(true,200,"广告分页查询成功",pageInfo);
    }


    /*
        图片上传
     */
    @RequestMapping("/PromotionAdUpload")
    // 参数名要与 前台请求参数 的key 保持一致
    public ResponseResult fileUpload(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {

        // 调用图片上传工具类
        Map<String, String> map = FileUploadUtils.fileUploadUtils(file,request);

        // 对象转为JSON响应给前台
        return new ResponseResult(true,200,"图片上传成功",map);
    }


    /*
        新建&修改广告
     */
    @RequestMapping("/saveOrUpdatePromotionAd")
    public ResponseResult saveOrUpdatePromotionAd(@RequestBody PromotionAd promotionAd){
        if (promotionAd.getId() == null) {
            // 新建
            promotionAdService.savePromotionAd(promotionAd);
            return new ResponseResult(true,200,"新建广告成功",null);
        } else {
            // 修改
            promotionAdService.updatePromotionAd(promotionAd);
            return new ResponseResult(true,200,"修改广告成功",null);
        }
    }


    /*
        根据ID查询广告信息
     */
    @RequestMapping("/findPromotionAdById")
    public ResponseResult findPromotionAdById(int id){

        PromotionAd promotionAd = promotionAdService.findPromotionAdById(id);

        return new ResponseResult(true,200,"查询具体广告成功",promotionAd);
    }



    /*
        广告动态上下线
     */
    @RequestMapping("/updatePromotionAdStatus")
    public ResponseResult updatePromotionAdStatus(int id,int status){

        promotionAdService.updatePromotionAdStatus(id,status);

        return new ResponseResult(true,200,"广告动态上下线成功",null);
    }
}
