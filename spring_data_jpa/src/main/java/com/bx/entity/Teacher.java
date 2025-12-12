package com.bx.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * @author lili
 * @version 1.0
 * @date 2025/12/9 15:21
 * @description 教师表
 */
@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // 姓名
    @Column(nullable = false)
    private String name;
    // 年龄
    private Integer age;
    // 性别
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

}
