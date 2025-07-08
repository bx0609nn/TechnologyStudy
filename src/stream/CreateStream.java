package stream;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author lili
 * @version 1.0
 * @date 2024/9/11 14:01
 */
public class CreateStream {
    public static void main(String[] args) {
        //1、通过数组创建，Arrays.stream(T[] array)
        int[] arr = {1,2,3,4,5};
        IntStream stream1 = Arrays.stream(arr);

        //2、通过集合创建，Collection.stream()

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("zhangsan");
        arrayList.add("lisi");
        arrayList.add("wangwu");
        arrayList.add("zhaoliu");
        arrayList.add("qianqi");
        Stream<String> stream2 = arrayList.stream();


        //3、通过Stream的静态方法：of()、iterate()、generate()
        Stream<Integer> stream3 = Stream.of(1,2,3,4,5);
        Stream<Integer> stream4 = Stream.iterate(1, (x) -> x + 2).limit(3);
        Stream<Double> stream5 = Stream.generate(Math::random).limit(5);

        //使用
        //1、筛选 filter()
        List<Employee> list = Employee.getEmployeeArrayList();

        //筛选出工资大于6000的
        list.stream().filter(emp -> emp.getSalary() > 6000).forEach(System.out::println);

        //2、截断，只取前n个 limit(n)
        list.stream().limit(3).forEach(System.out::println);

        //2、跳过，跳过前n个取后面的 skip(n)
        list.stream().skip(7).forEach(System.out::println);

        //4、去重，通过流所生成的hashCode()和equals()方法实现 distinct()
        list.add(new Employee(12,"马斯克",36,500000.00));
        list.add(new Employee(12,"马斯克",36,500000.00));
        list.add(new Employee(12,"马斯克",36,500000.00));
        list.add(new Employee(12,"马斯克",36,500000.00));

        list.stream().distinct().forEach(System.out::println);

        //5、映射 map()
        //将姓名全部转为大写
        arrayList.stream().map(name ->name.toUpperCase()).forEach(System.out::println);
        //查询工资大于6000的员工姓名
        list.stream().filter(emp -> emp.getSalary() > 6000).map(emp-> emp.getName()).forEach(System.out::println);

        //5、排序 sorted()
        list.stream().sorted((e1,e2)->e1.getAge()-e2.getAge()).forEach(System.out::println);

        //1、匹配与查找
//        allMatch():所有都匹配
        System.out.println(list.stream().allMatch(emp -> emp.getAge() > 22));

//        anyMatch():至少有一个匹配
        System.out.println(list.stream().anyMatch(emp -> emp.getSalary() > 30000.00));

//        findFirst():返回第一个元素
        System.out.println(list.stream().findFirst());
//        count():返回流中元素个数
        System.out.println(list.stream().filter(emp -> emp.getSalary() > 7000.00).count());

//        max(Comparator c):返回流中最大值
        System.out.println(list.stream().max((Comparator.comparingDouble(Employee::getSalary))));
//        min(Comparator c):返回流中最小值
//        forEach(Consumer c):内部迭代

        //2、归约
//        reduce(T identity,BinaryOperator):将流中元素反复结合起来，得到一个值
//        reduce(BinaryOperator):将流中元素反复结合起来，得到一个值

        //3、收集
//        collect(Collector c)
        List<Double> collect = list.stream().filter(emp -> emp.getSalary() > 7000.00).map(emp -> emp.getSalary()).collect(Collectors.toList());
        collect.forEach(System.out::println);
    }
}
