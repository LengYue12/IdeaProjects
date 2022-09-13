/*
    新手机
 */
public class NewPhone extends Phone implements IPlay{
    // 重写打电话方法
    @Override
    public void call() {
        System.out.println("新手机视频打电话");
    }

    // 重写发短信方法
    @Override
    public void sendMessage() {
        System.out.println("新手机语音发短信");
    }

    // 添加玩游戏功能
    @Override
    public void playGame() {
        System.out.println("新手机可以玩游戏");
    }
}
