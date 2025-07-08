package lambda;

import java.util.Comparator;

/**
 * @author lili
 * @version 1.0
 * @date 2024/9/11 22:19
 */
public class LambdaCase {
    public static void main(String[] args) {

        //匿名内部类
        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                System.out.println("你好，我是匿名内部类");
            }
        };
        runnable.run();


        //使用lambda表达式简化匿名内部类
        Runnable runnable1 = () -> {
            System.out.println("你好，我是Lambda");
        };
        runnable1.run();


        //方法引用
        Runnable runnable2 = System.out::println;


        Comparator<Integer> comparator = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return Integer.compare(o1, o2);
            }
        };

        //lambda表达式
        Comparator<Integer> com= (o1,o2) -> Integer.compare(o1, o2);
        System.out.println(com.compare(2, 4));

        //方法引用
        Comparator<Integer> compare = Integer::compare;
    }
}
