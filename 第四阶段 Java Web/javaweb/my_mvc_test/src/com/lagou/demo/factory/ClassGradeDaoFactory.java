package com.lagou.demo.factory;

import com.lagou.demo.dao.ClassGradeDao;
import com.lagou.demo.dao.ClassGradeDaoImpl;

public class ClassGradeDaoFactory {

    /**
     * 通过静态工厂方法模式来实现ClassGradeDao实现类对象的创建并返回
     * @return
     */
    public static ClassGradeDao getClassGradeDao(){return new ClassGradeDaoImpl();}
}
