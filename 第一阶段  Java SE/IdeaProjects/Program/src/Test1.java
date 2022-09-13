/*
    测试老式手机和新式手机的方法
 */
public class Test1 {
    public static void main(String[] args) {
        /*
        测试老手机
     */
        OldPhone oldPhone = new OldPhone();
        oldPhone.call();
        oldPhone.sendMessage();

        /*
            测试新手机
         */
        NewPhone newPhone = new NewPhone();
        newPhone.call();
        newPhone.sendMessage();
        newPhone.playGame();
    }



}
