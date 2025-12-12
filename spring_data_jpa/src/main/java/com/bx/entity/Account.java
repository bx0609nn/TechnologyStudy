package com.bx.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.Date;

/**
 * @author lili
 * @version 1.0
 * @date 2025/12/9 15:21
 * @description 账户表
 */
@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // 用户名
    @Column(nullable = false, unique = true)
    @Length(min = 2, max = 16)
    private String username;
    // 密码
    @Column(nullable = false)
    @Length(min = 6, max = 24)
    private String password;
    // 权限 0-无任何权限 1-查 2-增 3-改 4-删 5-所有
    @Column(nullable = false)
    private String role;
    // 创建时间
    @Column(nullable = false)
    private Date createTime;
    // 更新时间
    @Column(nullable = false)
    private Date updateTime;
}
