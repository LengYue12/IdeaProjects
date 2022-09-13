/*
    老式手机 OldPhone
    新式手机 NewPhone
    需求：新式手机添加玩游戏功能

    分析：
    1.提取一个抽象手机公共类，可以打电话，发短信
    2.定义一个IPlay接口
    3.新手机继承接口，并且实现IPlayGame 添加玩游戏的功能
    4.测试
 */
public abstract class Phone {
    // 打电话
    public abstract void call();
    // 发短信
    public abstract void sendMessage();
}
