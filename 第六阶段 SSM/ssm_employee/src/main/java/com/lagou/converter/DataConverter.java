package com.lagou.converter;

import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author zs
 * @date 2022/6/27 12:13
 * @description
 */
/*  自定义类型转换器
        解决日期格式转换
 */
public class DataConverter implements Converter<String, Date> {

    // s参数就是前台传递过来的日期
    @Override
    public Date convert(String s) {


        // 解决日期格式转换
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date date = null;
        try {
            date = simpleDateFormat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
