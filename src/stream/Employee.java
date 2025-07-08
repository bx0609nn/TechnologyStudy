package stream;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author lili
 * @version 1.0
 * @date 2024/9/11 15:03
 */

public class Employee {
    private Integer id;
    private String name;
    private Integer age;
    private Double salary;

    public Employee() {
    }

    public Employee(Integer id, String name, Integer age, Double salary) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.salary = salary;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", salary=" + salary +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee)) return false;
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id) && Objects.equals(name, employee.name) && Objects.equals(age, employee.age) && Objects.equals(salary, employee.salary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age, salary);
    }

    public static List getEmployeeArrayList() {
        ArrayList<Employee> employeeArrayList = new ArrayList<>();
        employeeArrayList.add(new Employee(1, "张三", 20, 10000.0));
        employeeArrayList.add(new Employee(2, "李四", 32, 8000.0));
        employeeArrayList.add(new Employee(3, "王五", 22, 5500.0));
        employeeArrayList.add(new Employee(4, "赵六", 23, 58000.0));
        employeeArrayList.add(new Employee(5, "钱七", 28, 6000.0));
        employeeArrayList.add(new Employee(6, "孙八", 25, 6500.0));
        employeeArrayList.add(new Employee(7, "周九", 30, 10000.0));
        employeeArrayList.add(new Employee(8, "吴十",25 , 80000.0));
        employeeArrayList.add(new Employee(9, "郑十一", 28, 90000.0));
        employeeArrayList.add(new Employee(10, "扎克伯德", 27, 15000.0));
        return employeeArrayList;

    }
}
