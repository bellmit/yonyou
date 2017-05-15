
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.retail
*
* @File name : DecorationPartDTO.java
*
* @Author : zsw
*
* @Date : 2016年9月5日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年9月5日    zsw    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.retail.domains.DTO.ordermanage;


/**装潢项目定义子表
* 
* @author zhongsw
* @date 2016年9月12日
*/
	
public class DecorationPartDTO {
    private Long decrodatePartId;

    private String dealerCode;

    private Long decrodateId;
    
    private Double number;

    private String storageCode;//仓库代码

    private String partNo;//配件代码

    private String partName;//配件名称

    //private Double partQuantity;//配件数量

    private Double partSalesPrice;//配件销售单价
    
    public Long getDecrodatePartId() {
        return decrodatePartId;
    }

    public void setDecrodatePartId(Long decrodatePartId) {
        this.decrodatePartId = decrodatePartId;
    }

    public String getDealerCode() {
        return dealerCode;
    }

    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode == null ? null : dealerCode.trim();
    }


    public String getStorageCode() {
        return storageCode;
    }

    public void setStorageCode(String storageCode) {
        this.storageCode = storageCode == null ? null : storageCode.trim();
    }

    public String getPartNo() {
        return partNo;
    }

    public void setPartNo(String partNo) {
        this.partNo = partNo == null ? null : partNo.trim();
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName == null ? null : partName.trim();
    }

    /*public Double getPartQuantity() {
        return partQuantity;
    }

    public void setPartQuantity(Double partQuantity) {
        this.partQuantity = partQuantity;
    }*/

    public Double getPartSalesPrice() {
        return partSalesPrice;
    }

    public void setPartSalesPrice(Double partSalesPrice) {
        this.partSalesPrice = partSalesPrice;
    }

    public Long getDecrodateId() {
        return decrodateId;
    }

    public void setDecrodateId(Long decrodateId) {
        this.decrodateId = decrodateId;
    }

    public Double getNumber() {
        return number;
    }

    public void setNumber(Double number) {
        this.number = number;
    }
}
