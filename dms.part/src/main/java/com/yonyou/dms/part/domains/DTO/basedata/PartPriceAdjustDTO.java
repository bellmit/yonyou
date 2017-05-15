
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : PartPriceAdjustDTO.java
*
* @Author : zhongshiwei
*
* @Date : 2016年7月17日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年7月17日    zhongshiwei    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.part.domains.DTO.basedata;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.service.excel.ExcelColumnDefine;
import com.yonyou.dms.function.domains.DTO.DataImportDto;

/**
*  配件价格
* @author zhongshiwei 
* @date 2016年7月17日
*/

public class PartPriceAdjustDTO extends DataImportDto{
    //经销商
    private String DEALER_NAME;
    @ExcelColumnDefine(value = 1)//仓库代码
    private String STORAGE_CODE;
    //库位代码
    private String STORAGE_CODE_NAME;
    @ExcelColumnDefine(value = 2)//配件代码
    private String PART_NO;
    @ExcelColumnDefine(value = 3)//现销售限价
    private Double SALES_PRICE;
    //保险价
    private Double INSURANCE_PRICE;
    //网点价
    private Double NODE_PRICE;
    //最新进货价
    private Double LATEST_PRICE;
    //销售限价
    private Double LIMIT_PRICE;
    //建议销售价
    private Double INSTRUCT_PRICE;
    //成本价
    private Double COST_PRICE;
    //车型组集
    private String CLAIM_PRICE;
    //加价率(%)
    private Double RATE;
    
    private Double ROND;
    
    private List<Map> dms_table;
    
    
    
    public List<Map> getDms_table() {
        return dms_table;
    }


    
    public void setDms_table(List<Map> dms_table) {
        this.dms_table = dms_table;
    }


    public Double getROND() {
        return ROND;
    }

    
    public void setROND(Double rOND) {
        ROND = rOND;
    }

    private Double SALES_PRICES;
    
    
    public Double getSALES_PRICES() {
        return SALES_PRICES;
    }


    
    public void setSALES_PRICES(Double sALES_PRICES) {
        SALES_PRICES = sALES_PRICES;
    }


    public Integer getBASE_COST_TYPE() {
        return BASE_COST_TYPE;
    }

    
    public void setBASE_COST_TYPE(Integer bASE_COST_TYPE) {
        BASE_COST_TYPE = bASE_COST_TYPE;
    }

    
    public Integer getIS_COST_TYPE() {
        return IS_COST_TYPE;
    }

    
    public void setIS_COST_TYPE(Integer iS_COST_TYPE) {
        IS_COST_TYPE = iS_COST_TYPE;
    }

    private Integer BASE_COST_TYPE;
    
    private Integer IS_COST_TYPE;
    
    public String getDEALER_NAME() {
        return DEALER_NAME;
    }
    
    public void setDEALER_NAME(String dEALER_NAME) {
        DEALER_NAME = dEALER_NAME;
    }
    
    public String getSTORAGE_CODE() {
        return STORAGE_CODE;
    }
    
    public void setSTORAGE_CODE(String sTORAGE_CODE) {
        STORAGE_CODE = sTORAGE_CODE;
    }
    
    public String getSTORAGE_CODE_NAME() {
        return STORAGE_CODE_NAME;
    }
    
    public void setSTORAGE_CODE_NAME(String sTORAGE_CODE_NAME) {
        STORAGE_CODE_NAME = sTORAGE_CODE_NAME;
    }
    
    public String getPART_NO() {
        return PART_NO;
    }
    
    public void setPART_NO(String pART_NO) {
        PART_NO = pART_NO;
    }
    
    public Double getSALES_PRICE() {
        return SALES_PRICE;
    }
    
    public void setSALES_PRICE(Double sALES_PRICE) {
        SALES_PRICE = sALES_PRICE;
    }
    
    public Double getINSURANCE_PRICE() {
        return INSURANCE_PRICE;
    }
    
    public void setINSURANCE_PRICE(Double iNSURANCE_PRICE) {
        INSURANCE_PRICE = iNSURANCE_PRICE;
    }
    
    public Double getNODE_PRICE() {
        return NODE_PRICE;
    }
    
    public void setNODE_PRICE(Double nODE_PRICE) {
        NODE_PRICE = nODE_PRICE;
    }
    
    public Double getLATEST_PRICE() {
        return LATEST_PRICE;
    }
    
    public void setLATEST_PRICE(Double lATEST_PRICE) {
        LATEST_PRICE = lATEST_PRICE;
    }
    
    public Double getLIMIT_PRICE() {
        return LIMIT_PRICE;
    }
    
    public void setLIMIT_PRICE(Double lIMIT_PRICE) {
        LIMIT_PRICE = lIMIT_PRICE;
    }
    
    public Double getINSTRUCT_PRICE() {
        return INSTRUCT_PRICE;
    }
    
    public void setINSTRUCT_PRICE(Double iNSTRUCT_PRICE) {
        INSTRUCT_PRICE = iNSTRUCT_PRICE;
    }
    
    public Double getCOST_PRICE() {
        return COST_PRICE;
    }
    
    public void setCOST_PRICE(Double cOST_PRICE) {
        COST_PRICE = cOST_PRICE;
    }
    
    public String getCLAIM_PRICE() {
        return CLAIM_PRICE;
    }
    
    public void setCLAIM_PRICE(String cLAIM_PRICE) {
        CLAIM_PRICE = cLAIM_PRICE;
    }
    
    public Double getRATE() {
        return RATE;
    }
    
    public void setRATE(Double rATE) {
        RATE = rATE;
    }
}
