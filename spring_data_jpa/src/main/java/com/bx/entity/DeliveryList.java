package com.bx.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author lili
 * @version 1.0
 * @date 2026/2/2 13:56
 * @description 入库明细单表体
 */
@Data
@Entity
@Table
public class DeliveryList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false, precision = 64, scale = 0)
    private Long id;

    //序号 从 1 开始的递增序号
    private String gnum;

    //物流运单编号
    private String logisticsNo;

    //备注
    private String note;

    public DeliveryList() {
    }

    public DeliveryList(Long id, String gnum, String logisticsNo, String note) {
        this.id = id;
        this.gnum = gnum;
        this.logisticsNo = logisticsNo;
        this.note = note;
    }
}
