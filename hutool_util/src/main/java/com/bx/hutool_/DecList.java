package com.bx.hutool_;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author lili
 * @version 1.0
 * @date 2025/8/1 14:13
 * @description
 */
@Data
public class DecList implements Serializable,Comparable<DecList> ,Cloneable {
    private BigDecimal declTotal;//申报总价

    private String gUnit;//申报计量单位（成交计量单位）

    private Integer gNo;//商品序号

    private Double gQty;//申报数量（成交计量单位）



    public BigDecimal getDeclTotal() {
        return declTotal;
    }

    public void setDeclTotal(BigDecimal declTotal) {
        this.declTotal = declTotal;
    }


    public String getgUnit() {
        return gUnit;
    }

    public void setgUnit(String gUnit) {
        this.gUnit = gUnit;
    }



    public Integer getgNo() {
        return gNo;
    }

    public void setgNo(Integer gNo) {
        this.gNo = gNo;
    }

    public Double getgQty() {
        return gQty;
    }

    public void setgQty(Double gQty) {
        this.gQty = gQty;
    }


    @Override
    public int compareTo(DecList o) {
        try {
            return Integer.valueOf(this.gNo) - Integer.valueOf(o.gNo);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

}

