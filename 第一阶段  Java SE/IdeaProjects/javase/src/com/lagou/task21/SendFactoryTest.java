package com.lagou.task21;

public class SendFactoryTest {

    public static void main(String[] args) {

        // 1.声明工厂类类型的引用指向工厂类类型的对象
//        SendFactory sendFactory = new SendFactory();
        // 2.调用工厂类里的创建对象方法
//        Sender sender = sendFactory.produce("mail");
//        Sender sender = sendFactory.produce("sms");
//        Sender sender = sendFactory.produce("");
//        Sender sender = sendFactory.produceMail();
        // 3.使用创建的对象调用发送方法
//        Sender sender = SendFactory.produceMail();
          Provider provider = new PacketSendFactory();
        Sender sender = provider.produce();
        sender.send();
    }

}
