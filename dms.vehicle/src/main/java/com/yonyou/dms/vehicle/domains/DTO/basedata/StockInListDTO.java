
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.vehicle
 *
 * @File name : StockInListDTO.java
 *
 * @Author : yll
 *
 * @Date : 2016年9月28日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年9月28日    yll    1.0
 *
 *
 *
 *
----------------------------------------------------------------------------------
 */

package com.yonyou.dms.vehicle.domains.DTO.basedata;

import java.util.List;

import com.yonyou.dms.vehicle.domains.DTO.stockManage.StockInOutDto;

/**
 * 入库列表
 * @author yll
 * @date 2016年9月28日
 */

public class StockInListDTO {

    private List<StockInOutDto> stockInOutlist;//接收选中的参数

    private String storagePositionCode;//车位

    private String storageCode;//仓库

    public List<StockInOutDto> getStockInOutlist() {
        return stockInOutlist;
    }


    public void setStockInOutlist(List<StockInOutDto> stockInOutlist) {
        this.stockInOutlist = stockInOutlist;
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



    public void setStorageCode(String storageCode) {
        this.storageCode = storageCode;
    }


}
