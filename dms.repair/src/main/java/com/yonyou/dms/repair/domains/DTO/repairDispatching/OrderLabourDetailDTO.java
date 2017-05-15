
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : repairDispatchingDTO.java
*
* @Author : rongzoujie
*
* @Date : 2016年9月20日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年9月20日    rongzoujie    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.repair.domains.DTO.repairDispatching;



/**
* TODO description
* @author rongzoujie
* @date 2016年9月20日
*/

public class OrderLabourDetailDTO {
    private Long RO_LABOUR_ID;
    private Long RO_ID;//工单id
    private String RO_NO;//工单号
    private Long REPAIR_TYPE_CODE2;//维修分类
    private String CHARGE_PARTITION_CODE;//收费区分
    private String LABOUR_CODE;//维修项目代码
    private String LABOUR_NAME;//维修项目名称
    private String LOCAL_LABOUR_CODE;//行管项目代码
    private String LOCAL_LABOUR_NAME;//行管项目名称
    private Double STD_LABOUR_HOUR;//标准工时
    private Double ASSIGN_LABOUR_HOUR;//派工工时
    private Double LABOUR_PRICE;//工时单价
    private Double LABOUR_AMOUNT;//工时费
    private Double DISCOUNT;//折扣率
    private Double AFTER_DISCOUNT_AMOUNT;//折后总金额
    private String TROUBLE_DESC;//故障描述
    private String TECHNICIAN;//责任技师
    private String WORKER_TYPE_CODE;//工种
    private String REMARK;//备注
    private Long ASSIGN_TAG;//是否派工
    private String ACTIVITY_CODE;//活动编号
    private String PACKAGE_CODE;//组合代码
    private String REPAIR_TYPE_CODE;//维修类型代码
    private String MODEL_LABOUR_CODE;//维修车型分组代码
    private Long RECORD_VERSION;
    
    public Long getRO_LABOUR_ID() {
        return RO_LABOUR_ID;
    }
    
    public void setRO_LABOUR_ID(Long rO_LABOUR_ID) {
        RO_LABOUR_ID = rO_LABOUR_ID;
    }
    
    public Long getRO_ID() {
        return RO_ID;
    }
    
    public void setRO_ID(Long rO_ID) {
        RO_ID = rO_ID;
    }
    
    public String getRO_NO() {
        return RO_NO;
    }
    
    public void setRO_NO(String rO_NO) {
        RO_NO = rO_NO;
    }
    
    public Long getREPAIR_TYPE_CODE2() {
        return REPAIR_TYPE_CODE2;
    }
    
    public void setREPAIR_TYPE_CODE2(Long rEPAIR_TYPE_CODE2) {
        REPAIR_TYPE_CODE2 = rEPAIR_TYPE_CODE2;
    }
    
    public String getCHARGE_PARTITION_CODE() {
        return CHARGE_PARTITION_CODE;
    }
    
    public void setCHARGE_PARTITION_CODE(String cHARGE_PARTITION_CODE) {
        CHARGE_PARTITION_CODE = cHARGE_PARTITION_CODE;
    }
    
    public String getLABOUR_CODE() {
        return LABOUR_CODE;
    }
    
    public void setLABOUR_CODE(String lABOUR_CODE) {
        LABOUR_CODE = lABOUR_CODE;
    }
    
    public String getLABOUR_NAME() {
        return LABOUR_NAME;
    }
    
    public void setLABOUR_NAME(String lABOUR_NAME) {
        LABOUR_NAME = lABOUR_NAME;
    }
    
    public String getLOCAL_LABOUR_CODE() {
        return LOCAL_LABOUR_CODE;
    }
    
    public void setLOCAL_LABOUR_CODE(String lOCAL_LABOUR_CODE) {
        LOCAL_LABOUR_CODE = lOCAL_LABOUR_CODE;
    }
    
    public String getLOCAL_LABOUR_NAME() {
        return LOCAL_LABOUR_NAME;
    }
    
    public void setLOCAL_LABOUR_NAME(String lOCAL_LABOUR_NAME) {
        LOCAL_LABOUR_NAME = lOCAL_LABOUR_NAME;
    }
    
    public Double getSTD_LABOUR_HOUR() {
        return STD_LABOUR_HOUR;
    }
    
    public void setSTD_LABOUR_HOUR(Double sTD_LABOUR_HOUR) {
        STD_LABOUR_HOUR = sTD_LABOUR_HOUR;
    }
    
    public Double getASSIGN_LABOUR_HOUR() {
        return ASSIGN_LABOUR_HOUR;
    }
    
    public void setASSIGN_LABOUR_HOUR(Double aSSIGN_LABOUR_HOUR) {
        ASSIGN_LABOUR_HOUR = aSSIGN_LABOUR_HOUR;
    }
    
    public Double getLABOUR_PRICE() {
        return LABOUR_PRICE;
    }
    
    public void setLABOUR_PRICE(Double lABOUR_PRICE) {
        LABOUR_PRICE = lABOUR_PRICE;
    }
    
    public Double getLABOUR_AMOUNT() {
        return LABOUR_AMOUNT;
    }
    
    public void setLABOUR_AMOUNT(Double lABOUR_AMOUNT) {
        LABOUR_AMOUNT = lABOUR_AMOUNT;
    }
    
    public Double getDISCOUNT() {
        return DISCOUNT;
    }
    
    public void setDISCOUNT(Double dISCOUNT) {
        DISCOUNT = dISCOUNT;
    }
    
    public Double getAFTER_DISCOUNT_AMOUNT() {
        return AFTER_DISCOUNT_AMOUNT;
    }
    
    public void setAFTER_DISCOUNT_AMOUNT(Double aFTER_DISCOUNT_AMOUNT) {
        AFTER_DISCOUNT_AMOUNT = aFTER_DISCOUNT_AMOUNT;
    }
    
    public String getTROUBLE_DESC() {
        return TROUBLE_DESC;
    }
    
    public void setTROUBLE_DESC(String tROUBLE_DESC) {
        TROUBLE_DESC = tROUBLE_DESC;
    }
    
    public String getTECHNICIAN() {
        return TECHNICIAN;
    }
    
    public void setTECHNICIAN(String tECHNICIAN) {
        TECHNICIAN = tECHNICIAN;
    }
    
    public String getWORKER_TYPE_CODE() {
        return WORKER_TYPE_CODE;
    }
    
    public void setWORKER_TYPE_CODE(String wORKER_TYPE_CODE) {
        WORKER_TYPE_CODE = wORKER_TYPE_CODE;
    }
    
    public String getREMARK() {
        return REMARK;
    }
    
    public void setREMARK(String rEMARK) {
        REMARK = rEMARK;
    }
    
    public Long getASSIGN_TAG() {
        return ASSIGN_TAG;
    }
    
    public void setASSIGN_TAG(Long aSSIGN_TAG) {
        ASSIGN_TAG = aSSIGN_TAG;
    }
    
    public String getACTIVITY_CODE() {
        return ACTIVITY_CODE;
    }
    
    public void setACTIVITY_CODE(String aCTIVITY_CODE) {
        ACTIVITY_CODE = aCTIVITY_CODE;
    }
    
    public String getPACKAGE_CODE() {
        return PACKAGE_CODE;
    }
    
    public void setPACKAGE_CODE(String pACKAGE_CODE) {
        PACKAGE_CODE = pACKAGE_CODE;
    }
    
    public String getREPAIR_TYPE_CODE() {
        return REPAIR_TYPE_CODE;
    }
    
    public void setREPAIR_TYPE_CODE(String rEPAIR_TYPE_CODE) {
        REPAIR_TYPE_CODE = rEPAIR_TYPE_CODE;
    }
    
    public String getMODEL_LABOUR_CODE() {
        return MODEL_LABOUR_CODE;
    }
    
    public void setMODEL_LABOUR_CODE(String mODEL_LABOUR_CODE) {
        MODEL_LABOUR_CODE = mODEL_LABOUR_CODE;
    }
    
    public Long getRECORD_VERSION() {
        return RECORD_VERSION;
    }
    
    public void setRECORD_VERSION(Long rECORD_VERSION) {
        RECORD_VERSION = rECORD_VERSION;
    }
    
    
}
