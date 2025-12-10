package com.bx.repository;

import com.bx.dto.UserDto;
import com.bx.entity.User;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author lili
 * @version 1.0
 * @date 2025/12/3 15:27
 * @description
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 根据用户名查询是否存在用户
     */
    boolean existsByUserName(String userName);

    /**
     * 根据ID查询用户
     */
    @Query("select new com.bx.dto.UserDto(u.id, u.userName, u.name, u.age, u.gender, u.email, u.phone, u.birthday, u.createTime, u.updateTime) from User u where u.id=:id")
    Optional<UserDto> findUserById(@Param("id") Long id);

    /**
     * 根据ID查询密码
     */
    @Query("select password from User where id = :id")
    String findPasswordById(@Param("id") Long id);

    /**
     * 根据ID修改密码
     */
    @Modifying
    @Query("update User set password = :password, updateTime = NOW() where id = :id")
    void updatePasswordById(@Param("password") String password, @Param("id") Long id);

}
