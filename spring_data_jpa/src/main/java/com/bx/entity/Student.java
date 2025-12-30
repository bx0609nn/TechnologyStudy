package com.bx.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.List;

/**
 * @author lili
 * @version 1.0
 * @date 2025/12/9 15:20
 * @description 学生表
 * 学生和账户1:1
 * 学生和课程1:N
 * 学生和老师N:N
 */
@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // 姓名
    @Column(nullable = false)
    private String name;
    // 年龄
    @Range(min = 0, max = 200, message = "年龄必须在0-200之间")
    private Integer age;
    // 性别
    @Pattern(regexp = "^[男女]$", message = "性别只能为男或女")
    private String gender;
    // 邮箱
    @Column(unique = true)
    private String email;
    // 创建时间
    @Column(nullable = false)
    private Date createTime;
    // 更新时间
    @Column(nullable = false)
    private Date updateTime;

    //一对一-------------------------------------------------------------------------------------------------------------
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "accountId", referencedColumnName = "id")
    @Valid
    private Account account;

    //@OneToOne 一对一
    //属性：
    //1.cascade：级联操作类型（枚举）。ALL-所有操作，PERSIST-插入，MERGE-修改，REMOVE-删除，DETACH-分离。查询不需要配置，默认支持
    //2.fetch：加载策略。EAGER-立即加载（默认），LAZY-懒加载，用到才加载（可能需要事务支持）
    //3.orphanRemoval：关联移除（默认false）。false-移除关联关系，但子表数据不删除，true-移除关联关系，同时删除子表数据
    //4.optional：关联对象是否可为null（默认true）

    //@JoinColumn：关联字段
    //属性：
    //1.name：主表(当前表Student)中的外键名
    //2.referencedColumnName：子表(对方表Account)中被引用的主键名



    //一对多-------------------------------------------------------------------------------------------------------------
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = Subject.class)
    @JoinColumn(name = "studentId", referencedColumnName = "id")
    @Fetch(value = FetchMode.SUBSELECT)
    @Valid
    private List<Subject> subjects;

    //@OneToMany：标识一对多关系
    //属性：
    //1.cascade：级联操作类型（枚举）。ALL-所有操作，PERSIST-插入，MERGE-修改，REMOVE-删除，DETACH-分离。查询不需要配置，默认支持
    //2.fetch：加载策略。EAGER-立即加载（默认），LAZY-懒加载，用到才加载（可能需要事务支持）
    //3.targetEntity：指定关联对象类型(多方的实体类)

    //@JoinColumn：关联字段
    //属性：
    //1.name：子表(多方Subject)中的外键名
    //2.referencedColumnName：主表(一方Student)中被引用的主键名

    //@Fetch：集合加载策略（即查询子表数据的策略）
    //属性：
    //1.value：加载策略。(FetchMode.SELECT-使用多个select语句分别加载（默认），查询多条主表数据时，每个主表对应的子表数据都单独执行一条SQL。   FetchMode.JOIN-使用join语句加载，使用左外连接：left join 子表 on 子表外键=主表主键。   FetchMode.SUBSELECT-使用子查询一次性加载，使用 in 子查询：子表外键 in (主表主键集合)。)



    //多对多-------------------------------------------------------------------------------------------------------------
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = Teacher.class)
    @JoinTable(name = "student_teacher",
            joinColumns = @JoinColumn(name = "studentId", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "teacherId", referencedColumnName = "id"))
    @Fetch(value = FetchMode.SUBSELECT)
    @Valid
    private List<Teacher> teachers;

    //@ManyToMany：标识多对多关系
    //属性：
    //1.cascade：级联操作类型（枚举）。ALL-所有操作，PERSIST-插入，MERGE-修改，REMOVE-删除，DETACH-分离。查询不需要配置，默认支持
    //2.fetch：加载策略。EAGER-立即加载（默认），LAZY-懒加载，用到才加载（可能需要事务支持）
    //3.targetEntity：指定关联对象类型(多方的实体类)

    //@JoinTable：关联表
    //属性：
    //1.name：中间表的名字
    //2.joinColumns：中间表中指向当前表(Student)的外键字段，(即中间表中用哪个字段来关联当前表(Student))
    //3.inverseJoinColumns：中间表中指向对方表(Teacher)的外键字段，(即中间表中用哪个字段来关联对方表(Teacher))

    //@Fetch：集合加载策略（即查询子表数据的策略）
    //属性：
    //1.value：加载策略。(FetchMode.SELECT-使用多个select语句分别加载（默认），查询多条主表数据时，每个主表对应的子表数据都单独执行一条SQL。   FetchMode.JOIN-使用join语句加载，使用左外连接：left join 子表 on 子表外键=主表主键。   FetchMode.SUBSELECT-使用子查询一次性加载，使用 in 子查询：子表外键 in (主表主键集合)。)

}
