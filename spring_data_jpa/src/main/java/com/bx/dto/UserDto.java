package com.bx.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author lili
 * @version 1.0
 * @date 2025/12/4 9:26
 * @description 用户dto
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String userName;
    private String name;
    private Integer age;
    private String gender;
    private String email;
    private String phone;
    private Date birthday;
    private Date createTime;
    private Date updateTime;
}
