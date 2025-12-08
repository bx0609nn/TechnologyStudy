package com.bx.service;

import com.bx.dto.UserDto;
import com.bx.entity.User;

import java.util.HashMap;

/**
 * @author lili
 * @version 1.0
 * @date 2025/12/3 15:14
 * @description 用户业务层
 */
public interface UserService {

    /**
     * @param user 用户
     * @return Long 用户ID
     * @description 新增用户
     */
    Long addUser(User user);

    /**
     * @param id 用户ID
     * @return void
     * @description 删除用户
     */
    void deleteUser(Long id);

    /**
     * @param map 分页查询条件
     * @return HashMap<String, Object> 用户列表
     * @description 分页查询用户列表
     */
    HashMap<String, Object> getUserList(HashMap<String, Object> map);

    /**
     * @param id 用户ID
     * @return UserDto 用户
     * @description 根据ID查询用户
     */
    UserDto getUserById(Long id);

    /**
     * @param user 用户
     * @return Long 用户ID
     * @description 修改用户
     */
    Long updateUser(User user);

    /**
     * @param map 用户ID,原密码,新密码
     * @return Long 用户ID
     * @description 修改用户密码
     */
    Long updatePassword(HashMap<String, Object> map);
}
