package com.bx.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.bx.entity.DeliveryHead;
import com.bx.entity.QDeliveryHead;
import com.bx.exception.BsException;
import com.bx.repository.DeliveryHeadRepository;
import com.bx.service.DeliveryHeadBo;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author lili
 * @version 1.0
 * @date 2026/2/2 13:56
 * @description 入库明细单Service
 */
@Service("deliveryHeadBo")
public class DeliveryHeadBoImpl implements DeliveryHeadBo {

    @Autowired
    private DeliveryHeadRepository deliveryHeadRepository;

    @Autowired
    private EntityManager entityManager;

    /**
     * @param deliveryHead 入库明细单
     * @return {@link Long } ID
     * @description 保存入库明细单
     */
    @Override
    public Long saveDeliveryHead(DeliveryHead deliveryHead) {
        if (deliveryHead.getId() == null) {
            initializeData(deliveryHead);
        }
        deliveryHeadRepository.save(deliveryHead);
        return deliveryHead.getId();
    }

    /**
     * @param id ID
     * @description 删除入库明细单
     */
    @Override
    public void deleteDeliveryHead(Long id) {
        if (id == null) {
            return;
        }
        deliveryHeadRepository.deleteById(id);
    }

    /**
     * @param map 分页查询条件
     * @return {@link Map<String, Object> } 入库明细单列表
     * @description 分页查询入库明细单
     */
    @Override
    public Map<String, Object> getDeliveryHeadList(Map<String, Object> map) {
        QDeliveryHead qDeliveryHead = QDeliveryHead.deliveryHead;
        BooleanBuilder builder = new BooleanBuilder();
        //分页条件
        Integer pageNum = Optional.ofNullable((Integer) map.get("pageNum")).orElse(1);
        Integer pageSize = Optional.ofNullable((Integer) map.get("pageSize")).orElse(10);
        Map<String, Object> condition = (Map) map.get("condition");
        //查询条件
        if (CollUtil.isEmpty(condition)) {
            String appStatus = (String) condition.get("appStatus");
            String beginDate = (String) condition.get("beginDate");
            String endDate = (String) condition.get("endDate");

            if (StrUtil.isNotBlank(appStatus)) {
                builder.and(qDeliveryHead.appStatus.eq(appStatus));
            }
            if (StrUtil.isNotBlank(beginDate) && StrUtil.isNotBlank(endDate)) {
                beginDate = beginDate.split("T")[0];
                String endTime = endDate.split("T")[0];
                String endHour = endDate.split("T")[1];
                long dayDiff = DateUtil.between(DateUtil.parseDate(beginDate), DateUtil.parseDate(endTime), DateUnit.DAY);
                if (dayDiff == 1 && !endHour.startsWith("00:00:00")) {
                    endTime = beginDate;
                }
                endDate = endTime + " 23:59:59";
                builder.and(qDeliveryHead.appTime.stringValue().between(beginDate, endDate));
            }
        }

        QueryResults<DeliveryHead> page = new JPAQuery<DeliveryHead>(entityManager)
                .from(qDeliveryHead)
                .select(Projections.bean(DeliveryHead.class, qDeliveryHead.id, qDeliveryHead.appStatus, qDeliveryHead.appTime, qDeliveryHead.customsCode, qDeliveryHead.copCode, qDeliveryHead.copName))
                .where(builder)
                .offset((pageNum - 1) * pageSize)
                .limit(pageSize)
                .orderBy(qDeliveryHead.appTime.desc())
                .fetchResults();

        long count = page.getTotal();//总条数
        long totalPages = (count + pageSize - 1) / pageSize;//总页数
        Map<String, Object> result = new HashMap();
        result.put("list", page.getResults());
        result.put("recordCount", count);
        result.put("totalPages", totalPages);
        return result;
    }

    /**
     * @param id ID
     * @return {@link DeliveryHead } 入库明细单
     * @description 根据ID查询入库明细单
     */
    @Override
    public DeliveryHead getDeliveryHeadById(Long id) {
        if (id == null) {
            return null;
        }
        DeliveryHead deliveryHead = deliveryHeadRepository.findById(id).orElse(null);
        return deliveryHead;
    }

    /**
     * @param deliveryHead
     * @description 初始化数据
     */
    private void initializeData(DeliveryHead deliveryHead) {
        deliveryHead.setAppStatus("1");
        deliveryHead.setAppTime(new Date());
        deliveryHead.setDeclareFlag("1");
    }
}