package com.lagou.controller;

import com.lagou.entity.PromotionSpace;
import com.lagou.entity.ResponseResult;
import com.lagou.service.PromotionSpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zs
 * @date 2022/6/30 18:46
 * @description
 */
@RestController     // 组合注解
@RequestMapping("/PromotionSpace")
public class PromotionSpaceController {


    @Autowired
    private PromotionSpaceService promotionSpaceService;

    /*
        查询广告位列表
     */
    @RequestMapping("/findAllPromotionSpace")
    public ResponseResult findAllPromotionSpace(){
        List<PromotionSpace> list = promotionSpaceService.findAllPromotionSpace();

        // 转为JSON响应给前台
        return new ResponseResult(true,200,"查询所有广告位信息成功",list);
    }


    /*
        添加&修改广告位
     */
    @RequestMapping("/saveOrUpdatePromotionSpace")
    // 前台传递的是JSON，所以使用RequestBody 注解，来获取到请求体中的内容并封装到实体中
    public ResponseResult saveOrUpdatePromotionSpace(@RequestBody PromotionSpace promotionSpace){

        if (promotionSpace.getId() == null) {
            // 新增
            promotionSpaceService.savePromotionSpace(promotionSpace);
            return new ResponseResult(true,200,"新增广告位成功",null);
        } else {
            promotionSpaceService.updatePromotionSpace(promotionSpace);
            return new ResponseResult(true,200,"更新广告位名称成功",null);

        }

    }



    /*
        根据ID查询广告位
     */
    @RequestMapping("/findPromotionSpaceById")
    public ResponseResult findPromotionSpaceById(int id){

        PromotionSpace promotionSpace = promotionSpaceService.findPromotionSpaceById(id);

        return new ResponseResult(true,200,"查询具体广告位成功",promotionSpace);
    }



}
