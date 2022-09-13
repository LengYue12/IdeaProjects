public class JIeChengTest {

    // 自定义成员方法实现将参数n的阶乘计算出来并返回
    // 1 ! = 1;     2 ! = 1 * 2;    3 ! = 1 * 2 * 3;    ... n ! = 1 * 2 * 3 * ... * n;
    int show(int n) {
        // 递推的方式
        /*int num = 1;
        for(int i = 1; i <= n; i ++) {
            num *= i;
        }
        return num;*/
        /*
               5! = 5 * 4 * 3 * 2 * 1;
               4! = 4 * 3 * 2 * 1;
               3! = 3 * 2 * 1;
               2! = 2 * 1;
               1! = 1;

               5！= 5 * 4！;
               4! = 4 * 3!;
               3! = 3 * 2!;
               2! = 2 * 1!;
               1! = 1;

               n! = n * (n - 1)!;

        */
        // 递归的方式
        // 当n的数值为1时，则阶乘的结果就是1
        /*if (1 == n) {
            return 1;
        }*/
        if (1 == n) return 1;
        // 否则阶乘的结果就是 n * (n - 1)!
        return n*show(n -1);
    }

    public static void main(String[] args) {

        // 1.声明JieChengTest类型的引用指向该类型的对象
        JIeChengTest jIeChengTest = new JIeChengTest();
        // 2.调用方法计算并打印
        int res = jIeChengTest.show(5);
        System.out.println("最终的计算结果是：" + res); // 120
    }
}
