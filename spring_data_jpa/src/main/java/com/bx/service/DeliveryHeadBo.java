package com.bx.service;

import com.bx.entity.DeliveryHead;

import java.util.Map;

/**
 * @author lili
 * @version 1.0
 * @date 2026/2/2 13:56
 * @description 入库明细单Service
 */
public interface DeliveryHeadBo {
    /**
     * @param deliveryHead 入库明细单
     * @return {@link Long } ID
     * @description 保存入库明细单
     */
    Long saveDeliveryHead(DeliveryHead deliveryHead);

    /**
     * @param id ID
     * @description 删除入库明细单
     */
    void deleteDeliveryHead(Long id);

    /**
     * @param map 分页查询条件
     * @return {@link Map<String, Object> } 入库明细单列表
     * @description 分页查询入库明细单
     */
    Map<String, Object> getDeliveryHeadList(Map<String, Object> map);

    /**
     * @param id ID
     * @return {@link DeliveryHead } 入库明细单
     * @description 根据ID查询入库明细单
     */
    DeliveryHead getDeliveryHeadById(Long id);
}