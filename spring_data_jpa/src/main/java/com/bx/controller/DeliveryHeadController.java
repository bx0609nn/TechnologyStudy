package com.bx.controller;

import com.bx.config.ErrorMsg;
import com.bx.entity.DeliveryHead;
import com.bx.service.DeliveryHeadBo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lili
 * @version 1.0
 * @date 2026/2/2 13:56
 * @description
 */
//@Api(tags = "入库明细单Controller")
@Slf4j
@RestController
public class DeliveryHeadController {

    @Autowired
    private DeliveryHeadBo deliveryHeadBo;

    /**
     * @author lili
     * @date 2026/02/03 09:29
     * @param deliveryHead 入库明细单
     * @return {@link ErrorMsg }
     * @description 保存入库明细单
     */
//    @ApiOperation("保存入库明细单")
    @PostMapping("/saveDeliveryHead")
    public ErrorMsg saveDeliveryHead(@RequestBody DeliveryHead deliveryHead) {
        Long id = deliveryHeadBo.saveDeliveryHead(deliveryHead);
        Map<String, Long> data = new HashMap<>();
        data.put("id", id);
        return ErrorMsg.success("保存成功", data);
    }

    /**
     * @author lili
     * @date 2026/02/03 09:29
     * @param map ID
     * @return {@link ErrorMsg }
     * @description 删除入库明细单
     */
//    @ApiOperation("删除入库明细单")
    @PostMapping("/deleteDeliveryHead")
    public ErrorMsg deleteDeliveryHead(@RequestBody Map<String, Long> map) {
        Long id = map.get("id");
        deliveryHeadBo.deleteDeliveryHead(id);
        return ErrorMsg.success("删除成功");
    }

    /**
     * @author lili
     * @date 2026/02/03 09:29
     * @param map 分页查询条件
     * @return {@link ErrorMsg }
     * @description 分页查询入库明细单
     */
//    @ApiOperation("分页查询入库明细单")
    @PostMapping("/getDeliveryHeadList")
    public ErrorMsg getDeliveryHeadList(@RequestBody Map<String, Object> map) {
        Map<String, Object> data = deliveryHeadBo.getDeliveryHeadList(map);
        return ErrorMsg.success("查询成功", data);
    }

    /**
     * @author lili
     * @date 2026/02/03 09:29
     * @param map ID
     * @return {@link ErrorMsg }
     * @description 根据ID查询入库明细单
     */
//    @ApiOperation("根据ID查询入库明细单")
    @PostMapping("/getDeliveryHeadById")
    public ErrorMsg getDeliveryHeadById(@RequestBody Map<String, Long> map) {
        Long id = map.get("id");
        DeliveryHead deliveryHead = deliveryHeadBo.getDeliveryHeadById(id);
        Map<String, DeliveryHead> data = new HashMap<>();
        data.put("deliveryHead", deliveryHead);
        return ErrorMsg.success("查询成功", data);
    }
}
