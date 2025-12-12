package com.bx.repository;

import com.bx.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    /**
     * 根据ID修改密码
     */
    @Modifying
    @Query("update Account set password = :newPassword, updateTime = NOW() where id = :id")
    void updatePasswordById(@Param("newPassword") String newPassword, @Param("id") Long id);
}