package com.lagou.testDBUtils;

import com.lagou.utils.DruidUtils;
import org.apache.commons.dbutils.QueryRunner;

public class DBUtilsDemo01 {

    // QueryRunner 核心类的创建方式
    public static void main(String[] args) {

        // 方式1 手动模式
        QueryRunner qr = new QueryRunner();

        // 方式2 自动模式 提供数据库连接池对象  DBUtils会自动的维护连接
        QueryRunner qr2 = new QueryRunner(DruidUtils.getDataSource());
    }
}
