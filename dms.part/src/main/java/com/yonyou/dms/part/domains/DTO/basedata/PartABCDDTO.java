
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : PartABCDDTO.java
*
* @Author : dingchaoyu
*
* @Date : 2017年4月17日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年4月17日    dingchaoyu    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.part.domains.DTO.basedata;



/**
* TODO 配件ABCDDTO
* @author dingchaoyu
* @date 2017年4月17日
*/

public class PartABCDDTO {
    
    private String ABC_TYPE;
    
    private String CALC_ABC_TYPE;
    
    private String PART_NO;
    
    private String PART_NAME;
    
    private Double SALES_PRICE;
    
    private Double PART_OUT_COUNT;
    
    
    public String getABC_TYPE() {
        return ABC_TYPE;
    }

    
    public void setABC_TYPE(String aBC_TYPE) {
        ABC_TYPE = aBC_TYPE;
    }

    
    public String getCALC_ABC_TYPE() {
        return CALC_ABC_TYPE;
    }

    
    public void setCALC_ABC_TYPE(String cALC_ABC_TYPE) {
        CALC_ABC_TYPE = cALC_ABC_TYPE;
    }

    
    public String getPART_NO() {
        return PART_NO;
    }

    
    public void setPART_NO(String pART_NO) {
        PART_NO = pART_NO;
    }

    
    public String getPART_NAME() {
        return PART_NAME;
    }

    
    public void setPART_NAME(String pART_NAME) {
        PART_NAME = pART_NAME;
    }

    
    public Double getSALES_PRICE() {
        return SALES_PRICE;
    }

    
    public void setSALES_PRICE(Double sALES_PRICE) {
        SALES_PRICE = sALES_PRICE;
    }

    
    public Double getPART_OUT_COUNT() {
        return PART_OUT_COUNT;
    }

    
    public void setPART_OUT_COUNT(Double pART_OUT_COUNT) {
        PART_OUT_COUNT = pART_OUT_COUNT;
    }

    
    public Double getPART_OUT_AMOUNT() {
        return PART_OUT_AMOUNT;
    }

    
    public void setPART_OUT_AMOUNT(Double pART_OUT_AMOUNT) {
        PART_OUT_AMOUNT = pART_OUT_AMOUNT;
    }

    
    public Double getADD_PART_AMOUNT() {
        return ADD_PART_AMOUNT;
    }

    
    public void setADD_PART_AMOUNT(Double aDD_PART_AMOUNT) {
        ADD_PART_AMOUNT = aDD_PART_AMOUNT;
    }

    
    public Double getADD_PART_AMOUNT_RATE() {
        return ADD_PART_AMOUNT_RATE;
    }

    
    public void setADD_PART_AMOUNT_RATE(Double aDD_PART_AMOUNT_RATE) {
        ADD_PART_AMOUNT_RATE = aDD_PART_AMOUNT_RATE;
    }

    
    public Double getCOST_PRICE() {
        return COST_PRICE;
    }

    
    public void setCOST_PRICE(Double cOST_PRICE) {
        COST_PRICE = cOST_PRICE;
    }

    
    public String getPART_MODEL_GROUP_CODE_SET() {
        return PART_MODEL_GROUP_CODE_SET;
    }

    
    public void setPART_MODEL_GROUP_CODE_SET(String pART_MODEL_GROUP_CODE_SET) {
        PART_MODEL_GROUP_CODE_SET = pART_MODEL_GROUP_CODE_SET;
    }

    
    public String getUNIT_CODE() {
        return UNIT_CODE;
    }

    
    public void setUNIT_CODE(String uNIT_CODE) {
        UNIT_CODE = uNIT_CODE;
    }

    private Double PART_OUT_AMOUNT;

    private Double ADD_PART_AMOUNT;
    
    private Double ADD_PART_AMOUNT_RATE;
    
    private Double COST_PRICE;
    
    private String PART_MODEL_GROUP_CODE_SET;
    
    private String UNIT_CODE;
    

}
