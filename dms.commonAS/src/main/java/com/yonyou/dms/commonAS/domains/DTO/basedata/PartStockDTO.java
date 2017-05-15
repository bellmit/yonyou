
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : PartStockDTO.java
*
* @Author : xukl
*
* @Date : 2016年7月15日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年7月15日    xukl    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.commonAS.domains.DTO.basedata;

import java.util.Date;


import com.yonyou.dms.framework.service.excel.ExcelColumnDefine;
import com.yonyou.dms.framework.service.excel.ExcelDataType;
import com.yonyou.dms.function.domains.DTO.DataImportDto;

/**
* PartStockDTO
* @author xukl
* @date 2016年7月15日
*/

public class PartStockDTO extends DataImportDto{
    
    
    @ExcelColumnDefine(value=1)
    private String DEALER_NAME;
    
    @ExcelColumnDefine(value=2)
    private String STORAGE_CODE;
    
    @ExcelColumnDefine(value=3)
    private String STORAGE_CODE_NAME;
    
    @ExcelColumnDefine(value=4)
    private String PART_NO;
    
    @ExcelColumnDefine(value=5)
    private String PART_NAME;
    
    @ExcelColumnDefine(value=6)
    private String BIG_CATEGORY_CODE;
    
    @ExcelColumnDefine(value=7)
    private String SUB_CATEGORY_CODE;
    
    @ExcelColumnDefine(value=8)
    private String THD_CATEGORY_CODE;
    
    @ExcelColumnDefine(value=9)
    private String STOCK_QUANTITY;
    
    @ExcelColumnDefine(value=10)
    private String USEABLE_STOCK;

    @ExcelColumnDefine(value=11,dataType = ExcelDataType.Dict, dataCode = 1278)
    private Integer IS_ACC;
    
    @ExcelColumnDefine(value=12)
    private Double SALES_PRICE;
    
    @ExcelColumnDefine(value=13)
    private Double LATEST_PRICE;
    
    @ExcelColumnDefine(value=14)
    private Double COST_PRICE;
    
    @ExcelColumnDefine(value=15)
    private Integer PART_GROUP_CODE;
    
    @ExcelColumnDefine(value=16)
    private String SPELL_CODE;
    
    @ExcelColumnDefine(value=17)
    private String UNIT_CODE;
    
    @ExcelColumnDefine(value=18)
    private Double INSURANCE_PRICE;
    
    @ExcelColumnDefine(value=19)
    private Double LIMIT_PRICE;
    
    @ExcelColumnDefine(value=20)
    private Double BORROW_QUANTITY;
    
    @ExcelColumnDefine(value=21)
    private Double LEND_QUANTITY;
    
    @ExcelColumnDefine(value=22)
    private String LOCKED_QUANTITY;
    
    @ExcelColumnDefine(value=23)
    private Integer MAX_STOCK;
    
    @ExcelColumnDefine(value=24)
    private Integer SAFE_STOCK;
    
    @ExcelColumnDefine(value=25)
    private Integer MIN_STOCK;
    
    @ExcelColumnDefine(value=26)
    private Double MIN_PACKAGE;
    
    @ExcelColumnDefine(value=27)
    private String PRODUCTING_AREA;
    
    @ExcelColumnDefine(value=28)
    private String COST_AMOUNT;

    @ExcelColumnDefine(value=29)
    private Date LAST_STOCK_IN;
    
    @ExcelColumnDefine(value=30)
    private String OPTION_NO;
    
    @ExcelColumnDefine(value=31)
    private String BRAND;
    
    @ExcelColumnDefine(value=32,dataType = ExcelDataType.Dict, dataCode = 1278)
    private Integer PART_STATUS;
    
    @ExcelColumnDefine(value=33)
    private String REMARK;
    
    @ExcelColumnDefine(value=34)
    private String PART_MODEL;
    
    @ExcelColumnDefine(value=35)
    private Integer PART_SPE_TYPE;
    
    @ExcelColumnDefine(value=36)
    private Integer IS_SUGGEST_ORDER;
    
    @ExcelColumnDefine(value=37)
    private Integer PART_MAIN_TYPE;
    
    @ExcelColumnDefine(value=38)
    private Double NET_COST_PRICE;
    
    @ExcelColumnDefine(value=39)
    private String NET_COST_AMOUNT;
    
    @ExcelColumnDefine(value=40,dataType = ExcelDataType.Dict, dataCode = 1278)
    private Integer DOWN_TAG;
    
    @ExcelColumnDefine(value=42)
    private String PROVIDER_CODE;
    
    @ExcelColumnDefine(value=41,dataType = ExcelDataType.Dict, dataCode = 1278)
    private Integer IS_STORAGE_SALE;
    
    @ExcelColumnDefine(value=43)
    private String PROVIDER_NAME;
    
    private Date SLOW_MOVING_DATE;
    
    private String dealerCode;//经销商代码
    
    private Integer D_KEY;
    
    public Integer getD_KEY() {
        return D_KEY;
    }

    
    public void setD_KEY(Integer d_KEY) {
        D_KEY = d_KEY;
    }

    public Date getSLOW_MOVING_DATE() {
        return SLOW_MOVING_DATE;
    }

    public void setSLOW_MOVING_DATE(Date sLOW_MOVING_DATE) {
        SLOW_MOVING_DATE = sLOW_MOVING_DATE;
    }

    public String getDealerCode() {
        return dealerCode;
    }




    
    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode;
    }




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




    public String getPART_NAME() {
        return PART_NAME;
    }



    
    public void setPART_NAME(String pART_NAME) {
        PART_NAME = pART_NAME;
    }



    
    public String getBIG_CATEGORY_CODE() {
        return BIG_CATEGORY_CODE;
    }



    
    public void setBIG_CATEGORY_CODE(String bIG_CATEGORY_CODE) {
        BIG_CATEGORY_CODE = bIG_CATEGORY_CODE;
    }



    
    public String getSUB_CATEGORY_CODE() {
        return SUB_CATEGORY_CODE;
    }



    
    public void setSUB_CATEGORY_CODE(String sUB_CATEGORY_CODE) {
        SUB_CATEGORY_CODE = sUB_CATEGORY_CODE;
    }



    
    public String getTHD_CATEGORY_CODE() {
        return THD_CATEGORY_CODE;
    }



    
    public void setTHD_CATEGORY_CODE(String tHD_CATEGORY_CODE) {
        THD_CATEGORY_CODE = tHD_CATEGORY_CODE;
    }



    
    public String getSTOCK_QUANTITY() {
        return STOCK_QUANTITY;
    }



    
    public void setSTOCK_QUANTITY(String sTOCK_QUANTITY) {
        STOCK_QUANTITY = sTOCK_QUANTITY;
    }



    
    public String getUSEABLE_STOCK() {
        return USEABLE_STOCK;
    }



    
    public void setUSEABLE_STOCK(String uSEABLE_STOCK) {
        USEABLE_STOCK = uSEABLE_STOCK;
    }



    
    public Integer getIS_ACC() {
        return IS_ACC;
    }



    
    public void setIS_ACC(Integer iS_ACC) {
        IS_ACC = iS_ACC;
    }



    
    public Double getSALES_PRICE() {
        return SALES_PRICE;
    }



    
    public void setSALES_PRICE(Double sALES_PRICE) {
        SALES_PRICE = sALES_PRICE;
    }



    
    public Double getLATEST_PRICE() {
        return LATEST_PRICE;
    }



    
    public void setLATEST_PRICE(Double lATEST_PRICE) {
        LATEST_PRICE = lATEST_PRICE;
    }



    
    public Double getCOST_PRICE() {
        return COST_PRICE;
    }



    
    public void setCOST_PRICE(Double cOST_PRICE) {
        COST_PRICE = cOST_PRICE;
    }



    
    public Integer getPART_GROUP_CODE() {
        return PART_GROUP_CODE;
    }



    
    public void setPART_GROUP_CODE(Integer pART_GROUP_CODE) {
        PART_GROUP_CODE = pART_GROUP_CODE;
    }



    
    public String getSPELL_CODE() {
        return SPELL_CODE;
    }



    
    public void setSPELL_CODE(String sPELL_CODE) {
        SPELL_CODE = sPELL_CODE;
    }



    
    public String getUNIT_CODE() {
        return UNIT_CODE;
    }



    
    public void setUNIT_CODE(String uNIT_CODE) {
        UNIT_CODE = uNIT_CODE;
    }



    
    public Double getINSURANCE_PRICE() {
        return INSURANCE_PRICE;
    }



    
    public void setINSURANCE_PRICE(Double iNSURANCE_PRICE) {
        INSURANCE_PRICE = iNSURANCE_PRICE;
    }



    
    public Double getLIMIT_PRICE() {
        return LIMIT_PRICE;
    }



    
    public void setLIMIT_PRICE(Double lIMIT_PRICE) {
        LIMIT_PRICE = lIMIT_PRICE;
    }



    
    public Double getBORROW_QUANTITY() {
        return BORROW_QUANTITY;
    }



    
    public void setBORROW_QUANTITY(Double bORROW_QUANTITY) {
        BORROW_QUANTITY = bORROW_QUANTITY;
    }



    
    public Double getLEND_QUANTITY() {
        return LEND_QUANTITY;
    }



    
    public void setLEND_QUANTITY(Double lEND_QUANTITY) {
        LEND_QUANTITY = lEND_QUANTITY;
    }



    
    public String getLOCKED_QUANTITY() {
        return LOCKED_QUANTITY;
    }



    
    public void setLOCKED_QUANTITY(String lOCKED_QUANTITY) {
        LOCKED_QUANTITY = lOCKED_QUANTITY;
    }



    
    public Integer getMAX_STOCK() {
        return MAX_STOCK;
    }



    
    public void setMAX_STOCK(Integer mAX_STOCK) {
        MAX_STOCK = mAX_STOCK;
    }



    
    public Integer getSAFE_STOCK() {
        return SAFE_STOCK;
    }



    
    public void setSAFE_STOCK(Integer sAFE_STOCK) {
        SAFE_STOCK = sAFE_STOCK;
    }



    
    public Integer getMIN_STOCK() {
        return MIN_STOCK;
    }



    
    public void setMIN_STOCK(Integer mIN_STOCK) {
        MIN_STOCK = mIN_STOCK;
    }



    
    public Double getMIN_PACKAGE() {
        return MIN_PACKAGE;
    }



    
    public void setMIN_PACKAGE(Double mIN_PACKAGE) {
        MIN_PACKAGE = mIN_PACKAGE;
    }



    
    public String getPRODUCTING_AREA() {
        return PRODUCTING_AREA;
    }



    
    public void setPRODUCTING_AREA(String pRODUCTING_AREA) {
        PRODUCTING_AREA = pRODUCTING_AREA;
    }



    
    public String getCOST_AMOUNT() {
        return COST_AMOUNT;
    }



    
    public void setCOST_AMOUNT(String cOST_AMOUNT) {
        COST_AMOUNT = cOST_AMOUNT;
    }



    
    public Date getLAST_STOCK_IN() {
        return LAST_STOCK_IN;
    }



    
    public void setLAST_STOCK_IN(Date lAST_STOCK_IN) {
        LAST_STOCK_IN = lAST_STOCK_IN;
    }



    
    public String getOPTION_NO() {
        return OPTION_NO;
    }



    
    public void setOPTION_NO(String oPTION_NO) {
        OPTION_NO = oPTION_NO;
    }



    
    public String getBRAND() {
        return BRAND;
    }



    
    public void setBRAND(String bRAND) {
        BRAND = bRAND;
    }



    
    public Integer getPART_STATUS() {
        return PART_STATUS;
    }



    
    public void setPART_STATUS(Integer pART_STATUS) {
        PART_STATUS = pART_STATUS;
    }



    
    public String getREMARK() {
        return REMARK;
    }



    
    public void setREMARK(String rEMARK) {
        REMARK = rEMARK;
    }



    
    public String getPART_MODEL() {
        return PART_MODEL;
    }



    
    public void setPART_MODEL(String pART_MODEL) {
        PART_MODEL = pART_MODEL;
    }



    
    public Integer getPART_SPE_TYPE() {
        return PART_SPE_TYPE;
    }



    
    public void setPART_SPE_TYPE(Integer pART_SPE_TYPE) {
        PART_SPE_TYPE = pART_SPE_TYPE;
    }



    
    public Integer getIS_SUGGEST_ORDER() {
        return IS_SUGGEST_ORDER;
    }



    
    public void setIS_SUGGEST_ORDER(Integer iS_SUGGEST_ORDER) {
        IS_SUGGEST_ORDER = iS_SUGGEST_ORDER;
    }



    
    public Integer getPART_MAIN_TYPE() {
        return PART_MAIN_TYPE;
    }



    
    public void setPART_MAIN_TYPE(Integer pART_MAIN_TYPE) {
        PART_MAIN_TYPE = pART_MAIN_TYPE;
    }



    
    public Double getNET_COST_PRICE() {
        return NET_COST_PRICE;
    }



    
    public void setNET_COST_PRICE(Double nET_COST_PRICE) {
        NET_COST_PRICE = nET_COST_PRICE;
    }



    
    public String getNET_COST_AMOUNT() {
        return NET_COST_AMOUNT;
    }



    
    public void setNET_COST_AMOUNT(String nET_COST_AMOUNT) {
        NET_COST_AMOUNT = nET_COST_AMOUNT;
    }



    
    public Integer getDOWN_TAG() {
        return DOWN_TAG;
    }



    
    public void setDOWN_TAG(Integer dOWN_TAG) {
        DOWN_TAG = dOWN_TAG;
    }



    
    public String getPROVIDER_CODE() {
        return PROVIDER_CODE;
    }



    
    public void setPROVIDER_CODE(String pROVIDER_CODE) {
        PROVIDER_CODE = pROVIDER_CODE;
    }



    
    public Integer getIS_STORAGE_SALE() {
        return IS_STORAGE_SALE;
    }



    
    public void setIS_STORAGE_SALE(Integer iS_STORAGE_SALE) {
        IS_STORAGE_SALE = iS_STORAGE_SALE;
    }



    
    public String getPROVIDER_NAME() {
        return PROVIDER_NAME;
    }



    
    public void setPROVIDER_NAME(String pROVIDER_NAME) {
        PROVIDER_NAME = pROVIDER_NAME;
    }



    private Double CLAIM_PRICE;
    
    private Date LAST_STOCK_OUT;


    
    public Double getCLAIM_PRICE() {
        return CLAIM_PRICE;
    }


    
    public void setCLAIM_PRICE(Double cLAIM_PRICE) {
        CLAIM_PRICE = cLAIM_PRICE;
    }


    
    public Date getLAST_STOCK_OUT() {
        return LAST_STOCK_OUT;
    }


    
    public void setLAST_STOCK_OUT(Date lAST_STOCK_OUT) {
        LAST_STOCK_OUT = lAST_STOCK_OUT;
    }
}
