package com.bx.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * @author lili
 * @version 1.0
 * @date 2025/12/9 15:20
 * @description 课程表
 */
@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //课程名称
    @Column(nullable = false)
    private String name;
    //描述
    private String description;
    // 创建时间
    @Column(nullable = false)
    private Date createTime;
    // 更新时间
    @Column(nullable = false)
    private Date updateTime;

//    @ManyToOne
//    @JoinColumn(name = "studentId", referencedColumnName = "id")
//    private Student student;

    //@ManyToOne 标识多对一关系
    //属性：
    //1.cascade：级联操作类型（枚举）。ALL-所有操作，PERSIST-插入，MERGE-修改，REMOVE-删除，DETACH-分离。查询不需要配置，默认支持
    //2.fetch：加载策略。EAGER-立即加载（默认），LAZY-懒加载，用到才加载（可能需要事务支持）

    //@JoinColumn：关联对象
    //属性：
    //name：子表(多方Account)中的外键名
    //referencedColumnName：主表(一方Student)中被引用的主键名

}
