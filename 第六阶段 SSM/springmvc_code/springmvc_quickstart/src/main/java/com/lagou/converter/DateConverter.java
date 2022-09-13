package com.lagou.converter;

import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author zs
 * @date 2022/6/21 21:25
 * @description
 */
/**
 * 当前台传递过来的参数是日期类型的字符串
 * 就会经过自定义类型转换器，把日期类型的字符串转换成日期对象并返回
 */
// 表示接收到的String类型转换为Date类型
public class DateConverter implements Converter<String, Date> {

    // s参数就是表单传递过来的请求参数 前台页面写的 2012-12-12 就会赋值给s
    @Override
    public Date convert(String s) {

        // 将日期字符串转换成日期对象并返回
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
