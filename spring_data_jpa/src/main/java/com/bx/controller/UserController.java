package com.bx.controller;

import com.bx.config.Result;
import com.bx.constant.MessageConstant;
import com.bx.dto.UserDto;
import com.bx.entity.User;
import com.bx.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @author lili
 * @version 1.0
 * @date 2025/12/3 15:12
 * @description 用户控制层
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * @author lili
     * @date 2025/12/3 15:40
     * @param user 用户
     * @return Result 用户ID
     * @description 用户注册
     */
    @PostMapping("/addUser")
    public Result addUser(@RequestBody User user) {
        Long id = userService.addUser(user);
        HashMap<String, Long> data = new HashMap<>();
        data.put("id", id);
        return Result.success(MessageConstant.ADD_SUCCESS, data);
    }

    /**
     * @author lili
     * @date 2025/12/3 16:04
     * @param map 用户ID
     * @return Result
     * @description 删除用户
     */
    @PostMapping("/deleteUser")
    public Result deleteUser(@RequestBody HashMap<String, Long> map) {
        Long id = map.get("id");
        userService.deleteUser(id);
        return Result.success(MessageConstant.DELETING_SUCCESS);
    }

    /**
     * @author lili
     * @date 2025/12/3 16:34
     * @param map 分页查询条件
     * @return Result 用户列表
     * @description 分页查询用户列表
     */
    @PostMapping("/getUserList")
    public Result getUserList(@RequestBody HashMap<String, Object> map) {
        HashMap<String, Object> data = userService.getUserList(map);
        return Result.success(MessageConstant.GET_SUCCESS, data);
    }

    /**
     * @author lili
     * @date 2025/12/4 9:42
     * @param map 用户ID
     * @return Result 用户
     * @description 根据ID查询用户
     */
    @PostMapping("/getUserById")
    public Result getUserById(@RequestBody HashMap<String, Long> map) {
        Long id = map.get("id");
        UserDto user = userService.getUserById(id);
        HashMap<String, UserDto> data = new HashMap<>();
        data.put("user", user);
        return Result.success(MessageConstant.GET_SUCCESS, data);
    }

    /**
     * @author lili
     * @date 2025/12/4 10:37
     * @param user 用户
     * @return Result 用户ID
     * @description 更新用户
     */
    @PostMapping("/updateUser")
    public Result updateUser(@RequestBody User user) {
        Long id = userService.updateUser(user);
        HashMap<String, Long> data = new HashMap<>();
        data.put("id", id);
        return Result.success(MessageConstant.UPDATE_SUCCESS, data);
    }

    /**
     * @author lili
     * @date 2025/12/4 13:48
     * @param map 用户ID,原密码,新密码
     * @return Result 用户ID
     * @description 修改用户密码
     */
    @PostMapping("/updatePassword")
    public Result updatePassword(@RequestBody HashMap<String, Object> map) {
        Long id = userService.updatePassword(map);
        HashMap<String, Long> data = new HashMap<>();
        data.put("id", id);
        return Result.success("修改密码成功", data);
    }
}
