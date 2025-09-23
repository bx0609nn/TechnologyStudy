package optional;

import java.util.Optional;

/**
 * @author lili
 * @version 1.0
 * @date 2025/2/5 14:26
 * @description Optional
 */
public class OptionalMethod {
    public static void main(String[] args) {
        //1.Optional：为了解决空指针异常，可以看做是一个容器，和数组、集合一样，容器里面可以存放元素

        //2.实例化
        //2.1
        Optional<Object> empty = Optional.empty();

        //2.2
        String string = new String("张三");
        //该方法中的参数不能为null
        Optional<String> optional = Optional.of(string);
        string = null;
        //该方法中的参数可以为null，也可以不为null
        Optional<String> optional1 = Optional.ofNullable(string);

        String string1 = new String("李四");

        //3.判断optional对象中存储的对象是否不为null，不为null返回true
        boolean present = optional.isPresent();
        System.out.println("present = " + present);

        //3.取值
        //3.1
        /**
         * public T get() {
         *     if (value == null) {
         *         throw new NoSuchElementException("No value present");
         *     }
         *     return value;
         * }
         */
        //该方法用于取出optional对象中存储的对象，前提对象不为空，否则报异常
        String s = optional.get();

        //3.2
        /**
         * public T orElse(T other) {
         *     return value != null ? value : other;
         * }
         */
        // 该方法用于取出optional1对象中存储的对象，如果存储的对象为null，则会返回参数
        String optionalString = optional1.orElse("string1");
        System.out.println("optionalString = " + optionalString);

        //总结：
        //1.创建Optional容器：Optional.empty()
        //2.创建Optional容器：Optional.ofNullable(string)
        //3.创建非空Optional容器，参数不能为null：Optional.of(string)
        //4.判断optional对象中存储的对象是否不为null：optional.isPresent()
        //5.获取optional对象中存储的对象，前提对象不为null，否则报异常：optional.get()
        //6.获取出optional1对象中存储的对象，若存储的对象为null，则返回传入的参数：optional1.orElse("string1")
    }
}
