package com.bx.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
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

}
