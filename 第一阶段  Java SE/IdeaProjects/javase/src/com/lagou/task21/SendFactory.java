package com.lagou.task21;

public class SendFactory {

    // 自定义方法实现对象的创建
    public Sender produce(String type){
        if ("mail".equals(type)) {
            return new MailSender();
        }
        if ("sms".equals(type)) {
            return new SmsSender();
        }
        return null;
    }

    public static Sender produceMail() {
        return new MailSender();
    }
    public static Sender produceSms(){
        return new SmsSender();
    }
}
