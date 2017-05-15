
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : BoAddItemPO.java
*
* @Author : jcsi
*
* @Date : 2016年10月14日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年10月14日    jcsi    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.repair.domains.DTO.order;



/**
* 预约单-附加项目
* @author jcsi
* @date 2016年10月14日
*/

public class BoAddItemDTO{
    
    private Long boAddItemId;
    
    private Long boId;  //预约单id
    
    private String chargePartitionCode; //收费区分
    
    private String addItemCode;  //附加项目代码
    
    private String addItemName;  //附加项目名称
    
    private Double addItemAmount;  //附加项目费
    
    private Double receivableAmount;  //应收金额
    
    private String remark;  //备注

  
    
    public Long getBoAddItemId() {
        return boAddItemId;
    }


    
    public void setBoAddItemId(Long boAddItemId) {
        this.boAddItemId = boAddItemId;
    }


    public Long getBoId() {
        return boId;
    }

    
    public void setBoId(Long boId) {
        this.boId = boId;
    }

    
  

    
    
    public String getChargePartitionCode() {
        return chargePartitionCode;
    }


    
    public void setChargePartitionCode(String chargePartitionCode) {
        this.chargePartitionCode = chargePartitionCode;
    }


    public String getAddItemCode() {
        return addItemCode;
    }

    
    public void setAddItemCode(String addItemCode) {
        this.addItemCode = addItemCode;
    }

    
    public String getAddItemName() {
        return addItemName;
    }

    
    public void setAddItemName(String addItemName) {
        this.addItemName = addItemName;
    }

    
    public Double getAddItemAmount() {
        return addItemAmount;
    }

    
    public void setAddItemAmount(Double addItemAmount) {
        this.addItemAmount = addItemAmount;
    }

    
    public Double getReceivableAmount() {
        return receivableAmount;
    }

    
    public void setReceivableAmount(Double receivableAmount) {
        this.receivableAmount = receivableAmount;
    }

    
    public String getRemark() {
        return remark;
    }

    
    public void setRemark(String remark) {
        this.remark = remark;
    }

    
}
