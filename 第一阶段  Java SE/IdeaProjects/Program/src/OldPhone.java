/*
    旧手机
 */
public class OldPhone extends Phone{
    // 重写打电话方法
    @Override
    public void call() {
        System.out.println("老式手机按键打电话");
    }

    // 重写发短信方法
    @Override
    public void sendMessage() {
        System.out.println("老式手机按键发短信");
    }
}
