package com.bx.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.Date;

/**
 * @author lili
 * @version 1.0
 * @date 2025/12/3 14:59
 * @description 用户
 */
@Table
@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false,unique = true)
    private Long id;

    //用户名
    @Column(nullable = false, unique = true)
    @Length(min = 2, max = 16)
    private String userName;

    //密码
    @Column(nullable = false)
    @Length(min = 6, max = 24)
    private String password;

    //姓名
    private String name;

    //年龄
    private Integer age;

    //性别
    @Column(length = 1)
    private String gender;

    //邮箱
    private String email;

    //电话
    private String phone;

    //生日 格式:yyyy-MM-dd
    @Temporal(TemporalType.DATE)
    @Column
    private Date birthday;

    //创建时间
    @Column(nullable = false)
    private Date createTime;

    //更新时间
    @Column(nullable = false)
    private Date updateTime;

}
