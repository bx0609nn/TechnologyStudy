package com.bx.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import com.bx.annotation.AutoFill;
import com.bx.constant.FieldConstant;
import com.bx.constant.PageConstant;
import com.bx.dto.UserDto;
import com.bx.entity.QUser;
import com.bx.entity.User;
import com.bx.enumtype.OperationType;
import com.bx.exception.BsException;
import com.bx.repository.UserRepository;
import com.bx.service.UserService;
import com.bx.utils.NameUtil;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

/**
 * @author lili
 * @version 1.0
 * @date 2025/12/3 15:15
 * @description 用户业务层实现类
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager entityManager;

    /**
     * @param user 用户
     * @return Long 用户ID
     * @description 新增用户
     */
    @Override
    @AutoFill(OperationType.ADD)
    public Long addUser(User user) {
        //校验用户名和密码是否不为空
        if (StrUtil.isBlank(user.getUserName()) || StrUtil.isBlank(user.getPassword())) {
            throw new BsException("用户名和密码不能为空，请重新输入！");
        }
        //校验用户名是否重复
        boolean flag = userRepository.existsByUserName(user.getUserName());
        if (flag) {
            throw new BsException("用户名重复，请重新输入！");
        }
        //设置默认值
        user.setName(NameUtil.generateName());
        userRepository.save(user);
        return user.getId();
    }

    /**
     * @param id 用户ID
     * @return void
     * @description 删除用户
     */
    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    /**
     * @param map 分页查询条件
     * @return HashMap<String, Object> 用户列表
     * @description 分页查询用户列表
     */
    @Override
    public HashMap<String, Object> getUserList(HashMap<String, Object> map) {

        QUser qUser = QUser.user;
        BooleanBuilder builder = new BooleanBuilder();

        Integer pageNum = Optional.ofNullable((Integer) map.get("pageNum")).orElse(PageConstant.PAGENUM);
        Integer pageSize = Optional.ofNullable((Integer) map.get("pageSize")).orElse(PageConstant.PAGESIZE);

        HashMap<String, Object> condition = (HashMap) map.get("condition");
        if (CollUtil.isNotEmpty(condition)) {
            Integer age = (Integer) condition.get("age");
            String gender = (String) condition.get("gender");
            String beginDate = (String) condition.get("beginDate");
            String endDate = (String) condition.get("endDate");

            if (age != null) {
                builder.and(qUser.age.eq(age));
            }
            if (StrUtil.isNotBlank(gender)) {
                builder.and(qUser.gender.eq(gender));
            }
            if (StrUtil.isNotBlank(beginDate) && StrUtil.isNotBlank(endDate)) {
                beginDate = beginDate.split("T")[0];
                endDate = endDate.split("T")[0] + FieldConstant.END_OF_DAY;
                builder.and(qUser.createTime.stringValue().between(beginDate, endDate));
            }
        }
        QueryResults<UserDto> results = new JPAQuery<User>(entityManager)
                .select(Projections.bean(UserDto.class, qUser.id, qUser.userName, qUser.name, qUser.age,
                        qUser.gender, qUser.email, qUser.phone, qUser.birthday, qUser.createTime, qUser.updateTime))
                .from(qUser)
                .where(builder)
                .offset((pageNum - 1) * pageSize)
                .limit(pageSize)
                .orderBy(qUser.id.desc())
                .fetchResults();

        long count = results.getTotal();
        long totalPages = (count + pageSize - 1) / pageSize;

        HashMap<String, Object> result = new HashMap<>();
        result.put("list", results.getResults());
        result.put("totalPages", totalPages);
        result.put("recordCount", count);
        return result;
    }

    /**
     * @param id 用户ID
     * @return UserDto 用户
     * @description 根据ID查询用户
     */
    @Override
    public UserDto getUserById(Long id) {
        UserDto user = userRepository.findUserById(id).orElse(null);
        return user;
    }

    /**
     * @param user 用户
     * @return Long 用户ID
     * @description 修改用户
     */
    @Override
    public Long updateUser(User user) {
        Long id = user.getId();
        if (id == null) {
            throw new BsException("用户不存在，修改失败！");
        }
        User byUser = userRepository.findById(id).orElse(null);
        if (byUser == null) {
            throw new BsException("用户不存在，修改失败！");
        }
        byUser.setName(user.getName());
        byUser.setAge(user.getAge());
        byUser.setGender(user.getGender());
        byUser.setEmail(user.getEmail());
        byUser.setPhone(user.getPhone());
        byUser.setBirthday(user.getBirthday());
        byUser.setUpdateTime(new Date());
        userRepository.save(byUser);
        return byUser.getId();
    }

    /**
     * @param map 用户ID,原密码,新密码
     * @return void
     * @description 修改用户密码
     */
    @Transactional
    @Override
    public void updatePassword(HashMap<String, Object> map) {
        //获取参数
        Long id = Convert.toLong(map.get("id"), null);
        String password = (String) map.get("password");
        String newPassword = (String) map.get("newPassword");
        //校验参数
        if (id == null || StrUtil.isBlank(password) || StrUtil.isBlank(newPassword)) {
            throw new BsException("参数不能为空，请检查输入的信息");
        }
        //校验新密码长度
        if (newPassword.length() < 6 || newPassword.length() > 24) {
            throw new BsException("新密码长度必须在6-24位之间");
        }
        //校验原密码是否正确
        String passwordById = userRepository.findPasswordById(id);
        if (!password.equals(passwordById)) {
            throw new BsException("原密码错误，请重新输入");
        }
        userRepository.updatePasswordById(newPassword, id);
    }

}
