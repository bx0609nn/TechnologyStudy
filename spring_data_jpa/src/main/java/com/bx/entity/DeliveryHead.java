package com.bx.entity;

import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author lili
 * @version 1.0
 * @date 2026/2/2 13:56
 * @description 入库明细单表头
 */
@Entity
@Table(indexes = {@Index(columnList = "appTime,appType,appStatus",name = "baseIndex")})
@Data
public class DeliveryHead implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false, precision = 64, scale = 0)
    private Long id;

    //系统唯一序列号
    private String guid;
    //申报类型 1-新增 2-变更
    private String appType;
    //申报时间
    private Date appTime;
    //申报状态 1：暂存，2申报
    private String appStatus;
    //申报海关
    private String customsCode;
    //企业内部编号
    private String copNo;
    //预录入编号
    private String preNo;
    //入库单编号
    private String rkdNo;
    //监管场所经营人代码
    private String operatorCode;
    //监管场所经营人名称
    private String operatorName;
    //进出口标记 I-进口 E-出口
    private String ieFlag;
    //运输方式
    private String trafMode;
    //运输工具编号
    private String trafNo;
    //航班航次号
    private String voyageNo;
    //提运单号
    private String billNo;
    //物流企业代码
    private String logisticsCode;
    //物流企业名称
    private String logisticsName;
    //卸货库位
    private String unloadLocation;
    //备注
    private String note;

    //传输企业代码
    private String copCode;
    //传输企业名称
    private String copName;
    //传输模式
    private String dxpMode;
    //传输编号
    private String dxpId;
    /**
     * 区分申报和查询区别
     * 1：申报
     * 0：查询
     */
    private String declareFlag;

    //cascade:级联操作，targetEntity:多方实体类，orphanRemoval:是否(f/t)开启关联移除*，fetch:立即(E)或懒加载(L)
    @OneToMany(cascade = CascadeType.ALL, targetEntity= DeliveryList.class, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name="deliveryHeadId",referencedColumnName = "id")//name:子表中的外键名*，referencedColumnName:主表中被引用的主键名*
    @Fetch(FetchMode.SUBSELECT)//查询子表数据的策略，SUBSELECT:子查询。
    //以上主键带*号的可根据具体变化，其他最好不动了
    private List<DeliveryList> deliveryListList;
}
