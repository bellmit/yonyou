
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.vehicle
 *
 * @File name : StockInOutDto.java
 *
 * @Author : yll
 *
 * @Date : 2016年12月8日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年12月8日    yll    1.0
 *
 *
 *
 *
----------------------------------------------------------------------------------
 */

package com.yonyou.dms.vehicle.domains.DTO.stockManage;



/**
 * 整车出入库
 * @author yll
 * @date 2016年12月8日
 */

public class StockInOutDto {

    private Long inOutStockId;
    
    private Integer inDeliveryType;

    private String inOutStockNo;

    private String storagePositionCode;//车位

    private String storageCode;//仓库


    private String Ids;
    public Integer getInDeliveryType() {
        return inDeliveryType;
    }


    public void setInDeliveryType(Integer inDeliveryType) {
        this.inDeliveryType = inDeliveryType;
    }


    public String getInOutStockNo() {
        return inOutStockNo;
    }


    public void setInOutStockNo(String inOutStockNo) {
        this.inOutStockNo = inOutStockNo;
    }


    public String getStoragePositionCode() {
        return storagePositionCode;
    }


    public void setStoragePositionCode(String storagePositionCode) {
        this.storagePositionCode = storagePositionCode;
    }


    public String getStorageCode() {
        return storageCode;
    }



    public Long getInOutStockId() {
        return inOutStockId;
    }



    public void setInOutStockId(Long inOutStockId) {
        this.inOutStockId = inOutStockId;
    }


    public void setStorageCode(String storageCode) {
        this.storageCode = storageCode;
    }


    
    public String getIds() {
        return Ids;
    }


    
    public void setIds(String ids) {
        Ids = ids;
    }






}
