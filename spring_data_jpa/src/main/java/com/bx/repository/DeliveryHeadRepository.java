package com.bx.repository;

import com.bx.entity.DeliveryHead;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author lili
 * @version 1.0
 * @date 2026/2/2 13:56
 * @description
 */
@Repository
public interface DeliveryHeadRepository extends JpaRepository<DeliveryHead, Long> {
}
