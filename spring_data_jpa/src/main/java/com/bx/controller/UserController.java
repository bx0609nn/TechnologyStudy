package com.bx.controller;

import com.bx.config.Result;
import com.bx.constant.MessageConstant;
import com.bx.dto.UserDto;
import com.bx.entity.User;
import com.bx.service.UserService;
import com.bx.utils.ExcelUtil;
import com.bx.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
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
     * @return Result
     * @description 修改用户密码
     */
    @PostMapping("/updatePassword")
    public Result updatePassword(@RequestBody HashMap<String, Object> map) {
        userService.updatePassword(map);
        return Result.success("修改密码成功");
    }

    @GetMapping("/exportExcel")
    public void updatePassword(HttpServletResponse response) throws IOException {
        //导出表的列名
        String[] headers = new String[]{"工作单号", "入园核注清单号", "核注清单号", "状态", "客户", "结算单位", "类型", "进出口方式"};
        //导出表的字段
        String[] fieldList = new String[]{"workOrderNo", "preDocNo", "clientSeqNo", "review", "custoerCode", "accountUnit", "type", "iEFlag"};
        ExcelUtil excelUtil = new ExcelUtil(20, "title", headers, fieldList);
        excelUtil.export("第1页", null);
        InputStream inputStream = excelUtil.getWorkbook();
        FileUtil.downloadFileWithInputStream(response, "报表示例.xlsx", inputStream);
    }
}
