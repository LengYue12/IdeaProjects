package com.lagou.utils;


import org.apache.commons.codec.digest.DigestUtils;

public class Md5 {

    public final static  String md5key = "lagou";
    /**
     * MD5方法
     * @param text 明文
     * @param key 密钥
     * @return 密文
     * @throws Exception
     */
    public static String md5(String text, String key) throws Exception {
        //加密后的字符串
        String encodeStr= DigestUtils.md5Hex(text+key);
        System.out.println("MD5加密后的字符串为:encodeStr="+encodeStr);
        return encodeStr;
    }

    /**
     * MD5验证方法
     * @param text 明文
     * @param key 密钥
     * @param md5 密文
     * @return true/false
     * @throws Exception
     */
    public static boolean verify(String text, String key, String md5) throws Exception {
        //根据传入的密钥进行验证
        String md5Text = md5(text, key);
        if(md5Text.equalsIgnoreCase(md5))
        {
            System.out.println("MD5验证通过");
            return true;
        }
        return false;
    }


    public static void main(String[] args) throws Exception {

        // 注册   用户名：tom     密码：123456
        // 添加用户的时候，要将明文密码转换成密文密码
        String lagou = md5("123456", "lagou");
        System.out.println(lagou);


        // 登录：  用户名：tom     密码：123456
        // 1.根据前台传递过来的用户名tom 先在user表 中查询出对应的密文密码
        // select * from user where username = tom

        // 调用verify 方法进行密码的校验
        // tom用户的密文密码查询出来了，在前台接收到了明文密码，所以要把前台的明文密码进行相同的算法的加密，
        // 判断加密后的密文密码和user表中查询出来的密文密码是否一致
        boolean verify = verify("123456", "lagou", "f00485441dfb815c75a13f3c3389c0b9");

        System.out.println(verify);
    }
}
