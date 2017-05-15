package com.yonyou.dms.repair.domains.DTO.basedata;

import java.util.Date;

/**
 * 工单维修配件明细 TODO description
 * 
 * @author chenwei
 * @date 2017年4月7日
 */
public class TtRoRepairPartDTO {

    private Long ITEM_ID;
    private Integer D_KEY;
    private Integer ITEM_ID_LABOUR;
    private String  PART_NO;
    private String  PART_NAME;
    private String    STORAGE_CODE;
    private String  CHARGE_PARTITION_CODE;
    private String  STORAGE_POSITION_CODE;
    private String    UNIT_CODE;
    private String  PART_BATCH_NO;
    private String  MANAGE_SORT_CODE;
    private String    OUT_STOCK_NO;
    private Integer PRICE_TYPE;
    private Integer IS_MAIN_PART;
    private Double  PART_QUANTITY;
    private Double  PRICE_RATE;
    private Double  OEM_LIMIT_PRICE;
    private Double  PART_COST_PRICE;
    private Double  PART_SALES_PRICE;
    private Double  PART_COST_AMOUNT;
    private Double  PART_SALES_AMOUNT;
    private String    SENDER;
    private String    RECEIVER;
    private Date    SEND_TIME;
    private Integer IS_FINISHED;
    private Integer BATCH_NO;
    private String  ACTIVITY_CODE;
    private Integer PRE_CHECK;
    private Double  DISCOUNT;
    private Integer INTER_RETURN;
    private Integer NEEDLESS_REPAIR;
    private Integer CONSIGN_EXTERIOR;
    private Date    PRINT_RP_TIME;
    private Integer PRINT_BATCH_NO;
    private String  LABOUR_CODE;
    private String  MODEL_LABOUR_CODE;
    private String  PACKAGE_CODE;
    private Integer IS_DISCOUNT;
    private char    REPAIR_TYPE_CODE;
    private Integer NON_ONE_OFF;
    private Integer ADD_TAG;
    private Integer REASON;
    private Integer CARD_ID;
    private Long FROM_TYPE;
    private Date    DXP_DATE;
    private String  LABOUR_NAME;
    private String  DEALER_CODE;
    private String    RO_NO;
    private Double  OTHER_PART_COST_PRICE;
    private Double  OTHER_PART_COST_AMOUNT;
    private String  POS_CODE;
    private String  POS_NAME;
    private String  PART_INFIX;
    private Integer WAR_LEVEL;
    private Integer IS_OLDPART_TREAT;
    private Integer PART_CATEGORY;
    private String  MAINTAIN_PACKAGE_CODE;
    private Date    REPORT_B_I_DATETIME;
    
    //added by chenwei
    private String createdBy;
    private Date createdAt;
    private String updatedBy;
    private Date updatedAt;
    

    
    
    public Integer getD_KEY() {
        return D_KEY;
    }



    
    public void setD_KEY(Integer d_KEY) {
        D_KEY = d_KEY;
    }



    public String getUpdatedBy() {
        return updatedBy;
    }


    
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }


    
    public Date getUpdatedAt() {
        return updatedAt;
    }


    
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }


    public String getCreatedBy() {
        return createdBy;
    }

    
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    
    public Date getCreatedAt() {
        return createdAt;
    }

    
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Long getITEM_ID() {
        return ITEM_ID;
    }

    public void setITEM_ID(Long iTEM_ID) {
        ITEM_ID = iTEM_ID;
    }



    public Integer getITEM_ID_LABOUR() {
        return ITEM_ID_LABOUR;
    }

    public void setITEM_ID_LABOUR(Integer iTEM_ID_LABOUR) {
        ITEM_ID_LABOUR = iTEM_ID_LABOUR;
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



    
    public String getSTORAGE_CODE() {
        return STORAGE_CODE;
    }
    
    public void setSTORAGE_CODE(String sTORAGE_CODE) {
        STORAGE_CODE = sTORAGE_CODE;
    }



    public String getCHARGE_PARTITION_CODE() {
        return CHARGE_PARTITION_CODE;
    }

    public void setCHARGE_PARTITION_CODE(String cHARGE_PARTITION_CODE) {
        CHARGE_PARTITION_CODE = cHARGE_PARTITION_CODE;
    }

    public String getSTORAGE_POSITION_CODE() {
        return STORAGE_POSITION_CODE;
    }

    public void setSTORAGE_POSITION_CODE(String sTORAGE_POSITION_CODE) {
        STORAGE_POSITION_CODE = sTORAGE_POSITION_CODE;
    }

    public String getUNIT_CODE() {
        return UNIT_CODE;
    }

    public void setUNIT_CODE(String uNIT_CODE) {
        UNIT_CODE = uNIT_CODE;
    }

    public String getPART_BATCH_NO() {
        return PART_BATCH_NO;
    }

    public void setPART_BATCH_NO(String pART_BATCH_NO) {
        PART_BATCH_NO = pART_BATCH_NO;
    }

    public String getMANAGE_SORT_CODE() {
        return MANAGE_SORT_CODE;
    }

    public void setMANAGE_SORT_CODE(String mANAGE_SORT_CODE) {
        MANAGE_SORT_CODE = mANAGE_SORT_CODE;
    }

    public String getOUT_STOCK_NO() {
        return OUT_STOCK_NO;
    }
    
    public void setOUT_STOCK_NO(String oUT_STOCK_NO) {
        OUT_STOCK_NO = oUT_STOCK_NO;
    }

    public Integer getPRICE_TYPE() {
        return PRICE_TYPE;
    }

    public void setPRICE_TYPE(Integer pRICE_TYPE) {
        PRICE_TYPE = pRICE_TYPE;
    }

    public Integer getIS_MAIN_PART() {
        return IS_MAIN_PART;
    }

    public void setIS_MAIN_PART(Integer iS_MAIN_PART) {
        IS_MAIN_PART = iS_MAIN_PART;
    }

    public Double getPART_QUANTITY() {
        return PART_QUANTITY;
    }

    public void setPART_QUANTITY(Double pART_QUANTITY) {
        PART_QUANTITY = pART_QUANTITY;
    }

    public Double getPRICE_RATE() {
        return PRICE_RATE;
    }

    public void setPRICE_RATE(Double pRICE_RATE) {
        PRICE_RATE = pRICE_RATE;
    }

    public Double getOEM_LIMIT_PRICE() {
        return OEM_LIMIT_PRICE;
    }

    public void setOEM_LIMIT_PRICE(Double oEM_LIMIT_PRICE) {
        OEM_LIMIT_PRICE = oEM_LIMIT_PRICE;
    }

    public Double getPART_COST_PRICE() {
        return PART_COST_PRICE;
    }

    public void setPART_COST_PRICE(Double pART_COST_PRICE) {
        PART_COST_PRICE = pART_COST_PRICE;
    }

    public Double getPART_SALES_PRICE() {
        return PART_SALES_PRICE;
    }

    public void setPART_SALES_PRICE(Double pART_SALES_PRICE) {
        PART_SALES_PRICE = pART_SALES_PRICE;
    }

    public Double getPART_COST_AMOUNT() {
        return PART_COST_AMOUNT;
    }

    public void setPART_COST_AMOUNT(Double pART_COST_AMOUNT) {
        PART_COST_AMOUNT = pART_COST_AMOUNT;
    }

    public Double getPART_SALES_AMOUNT() {
        return PART_SALES_AMOUNT;
    }

    public void setPART_SALES_AMOUNT(Double pART_SALES_AMOUNT) {
        PART_SALES_AMOUNT = pART_SALES_AMOUNT;
    }

    public String getSENDER() {
        return SENDER;
    }

    public void setSENDER(String sENDER) {
        SENDER = sENDER;
    }
    
    public String getRECEIVER() {
        return RECEIVER;
    }

    
    public void setRECEIVER(String rECEIVER) {
        RECEIVER = rECEIVER;
    }



    public Date getSEND_TIME() {
        return SEND_TIME;
    }

    public void setSEND_TIME(Date sEND_TIME) {
        SEND_TIME = sEND_TIME;
    }

    public Integer getIS_FINISHED() {
        return IS_FINISHED;
    }

    public void setIS_FINISHED(Integer iS_FINISHED) {
        IS_FINISHED = iS_FINISHED;
    }

    public Integer getBATCH_NO() {
        return BATCH_NO;
    }

    public void setBATCH_NO(Integer bATCH_NO) {
        BATCH_NO = bATCH_NO;
    }

    public String getACTIVITY_CODE() {
        return ACTIVITY_CODE;
    }

    public void setACTIVITY_CODE(String aCTIVITY_CODE) {
        ACTIVITY_CODE = aCTIVITY_CODE;
    }

    public Integer getPRE_CHECK() {
        return PRE_CHECK;
    }

    public void setPRE_CHECK(Integer pRE_CHECK) {
        PRE_CHECK = pRE_CHECK;
    }

    public Double getDISCOUNT() {
        return DISCOUNT;
    }

    public void setDISCOUNT(Double dISCOUNT) {
        DISCOUNT = dISCOUNT;
    }

    public Integer getINTER_RETURN() {
        return INTER_RETURN;
    }

    public void setINTER_RETURN(Integer iNTER_RETURN) {
        INTER_RETURN = iNTER_RETURN;
    }

    public Integer getNEEDLESS_REPAIR() {
        return NEEDLESS_REPAIR;
    }

    public void setNEEDLESS_REPAIR(Integer nEEDLESS_REPAIR) {
        NEEDLESS_REPAIR = nEEDLESS_REPAIR;
    }

    public Integer getCONSIGN_EXTERIOR() {
        return CONSIGN_EXTERIOR;
    }

    public void setCONSIGN_EXTERIOR(Integer cONSIGN_EXTERIOR) {
        CONSIGN_EXTERIOR = cONSIGN_EXTERIOR;
    }

    public Date getPRINT_RP_TIME() {
        return PRINT_RP_TIME;
    }

    public void setPRINT_RP_TIME(Date pRINT_RP_TIME) {
        PRINT_RP_TIME = pRINT_RP_TIME;
    }

    public Integer getPRINT_BATCH_NO() {
        return PRINT_BATCH_NO;
    }

    public void setPRINT_BATCH_NO(Integer pRINT_BATCH_NO) {
        PRINT_BATCH_NO = pRINT_BATCH_NO;
    }

    public String getLABOUR_CODE() {
        return LABOUR_CODE;
    }

    public void setLABOUR_CODE(String lABOUR_CODE) {
        LABOUR_CODE = lABOUR_CODE;
    }

    public String getMODEL_LABOUR_CODE() {
        return MODEL_LABOUR_CODE;
    }

    public void setMODEL_LABOUR_CODE(String mODEL_LABOUR_CODE) {
        MODEL_LABOUR_CODE = mODEL_LABOUR_CODE;
    }

    public String getPACKAGE_CODE() {
        return PACKAGE_CODE;
    }

    public void setPACKAGE_CODE(String pACKAGE_CODE) {
        PACKAGE_CODE = pACKAGE_CODE;
    }

    public Integer getIS_DISCOUNT() {
        return IS_DISCOUNT;
    }

    public void setIS_DISCOUNT(Integer iS_DISCOUNT) {
        IS_DISCOUNT = iS_DISCOUNT;
    }

    public char getREPAIR_TYPE_CODE() {
        return REPAIR_TYPE_CODE;
    }

    public void setREPAIR_TYPE_CODE(char rEPAIR_TYPE_CODE) {
        REPAIR_TYPE_CODE = rEPAIR_TYPE_CODE;
    }

    public Integer getNON_ONE_OFF() {
        return NON_ONE_OFF;
    }

    public void setNON_ONE_OFF(Integer nON_ONE_OFF) {
        NON_ONE_OFF = nON_ONE_OFF;
    }

    public Integer getADD_TAG() {
        return ADD_TAG;
    }

    public void setADD_TAG(Integer aDD_TAG) {
        ADD_TAG = aDD_TAG;
    }

    public Integer getREASON() {
        return REASON;
    }

    public void setREASON(Integer rEASON) {
        REASON = rEASON;
    }

    public Integer getCARD_ID() {
        return CARD_ID;
    }

    public void setCARD_ID(Integer cARD_ID) {
        CARD_ID = cARD_ID;
    }

    

    
    public Long getFROM_TYPE() {
        return FROM_TYPE;
    }


    
    public void setFROM_TYPE(Long fROM_TYPE) {
        FROM_TYPE = fROM_TYPE;
    }


    public Date getDXP_DATE() {
        return DXP_DATE;
    }

    public void setDXP_DATE(Date dXP_DATE) {
        DXP_DATE = dXP_DATE;
    }

    public String getLABOUR_NAME() {
        return LABOUR_NAME;
    }

    public void setLABOUR_NAME(String lABOUR_NAME) {
        LABOUR_NAME = lABOUR_NAME;
    }

    public String getDEALER_CODE() {
        return DEALER_CODE;
    }

    public void setDEALER_CODE(String dEALER_CODE) {
        DEALER_CODE = dEALER_CODE;
    }

    public String getRO_NO() {
        return RO_NO;
    }

    public void setRO_NO(String rO_NO) {
        RO_NO = rO_NO;
    }

    public Double getOTHER_PART_COST_PRICE() {
        return OTHER_PART_COST_PRICE;
    }

    public void setOTHER_PART_COST_PRICE(Double oTHER_PART_COST_PRICE) {
        OTHER_PART_COST_PRICE = oTHER_PART_COST_PRICE;
    }

    public Double getOTHER_PART_COST_AMOUNT() {
        return OTHER_PART_COST_AMOUNT;
    }

    public void setOTHER_PART_COST_AMOUNT(Double oTHER_PART_COST_AMOUNT) {
        OTHER_PART_COST_AMOUNT = oTHER_PART_COST_AMOUNT;
    }

    public String getPOS_CODE() {
        return POS_CODE;
    }

    public void setPOS_CODE(String pOS_CODE) {
        POS_CODE = pOS_CODE;
    }

    public String getPOS_NAME() {
        return POS_NAME;
    }

    public void setPOS_NAME(String pOS_NAME) {
        POS_NAME = pOS_NAME;
    }

    public String getPART_INFIX() {
        return PART_INFIX;
    }

    public void setPART_INFIX(String pART_INFIX) {
        PART_INFIX = pART_INFIX;
    }

    public Integer getWAR_LEVEL() {
        return WAR_LEVEL;
    }

    public void setWAR_LEVEL(Integer wAR_LEVEL) {
        WAR_LEVEL = wAR_LEVEL;
    }

    public Integer getIS_OLDPART_TREAT() {
        return IS_OLDPART_TREAT;
    }

    public void setIS_OLDPART_TREAT(Integer iS_OLDPART_TREAT) {
        IS_OLDPART_TREAT = iS_OLDPART_TREAT;
    }

    public Integer getPART_CATEGORY() {
        return PART_CATEGORY;
    }

    public void setPART_CATEGORY(Integer pART_CATEGORY) {
        PART_CATEGORY = pART_CATEGORY;
    }

    public String getMAINTAIN_PACKAGE_CODE() {
        return MAINTAIN_PACKAGE_CODE;
    }

    public void setMAINTAIN_PACKAGE_CODE(String mAINTAIN_PACKAGE_CODE) {
        MAINTAIN_PACKAGE_CODE = mAINTAIN_PACKAGE_CODE;
    }

    public Date getREPORT_B_I_DATETIME() {
        return REPORT_B_I_DATETIME;
    }

    public void setREPORT_B_I_DATETIME(Date rEPORT_B_I_DATETIME) {
        REPORT_B_I_DATETIME = rEPORT_B_I_DATETIME;
    }



    @Override
    public String toString() {
        return "TtRoRepairPartDTO [ITEM_ID=" + ITEM_ID + ", ITEM_ID_LABOUR=" + ITEM_ID_LABOUR + ", PART_NO=" + PART_NO
               + ", PART_NAME=" + PART_NAME + ", STORAGE_CODE=" + STORAGE_CODE + ", CHARGE_PARTITION_CODE="
               + CHARGE_PARTITION_CODE + ", STORAGE_POSITION_CODE=" + STORAGE_POSITION_CODE + ", UNIT_CODE=" + UNIT_CODE
               + ", PART_BATCH_NO=" + PART_BATCH_NO + ", MANAGE_SORT_CODE=" + MANAGE_SORT_CODE + ", OUT_STOCK_NO="
               + OUT_STOCK_NO + ", PRICE_TYPE=" + PRICE_TYPE + ", IS_MAIN_PART=" + IS_MAIN_PART + ", PART_QUANTITY="
               + PART_QUANTITY + ", PRICE_RATE=" + PRICE_RATE + ", OEM_LIMIT_PRICE=" + OEM_LIMIT_PRICE
               + ", PART_COST_PRICE=" + PART_COST_PRICE + ", PART_SALES_PRICE=" + PART_SALES_PRICE
               + ", PART_COST_AMOUNT=" + PART_COST_AMOUNT + ", PART_SALES_AMOUNT=" + PART_SALES_AMOUNT + ", SENDER="
               + SENDER + ", RECEIVER=" + RECEIVER + ", SEND_TIME=" + SEND_TIME + ", IS_FINISHED=" + IS_FINISHED
               + ", BATCH_NO=" + BATCH_NO + ", ACTIVITY_CODE=" + ACTIVITY_CODE + ", PRE_CHECK=" + PRE_CHECK
               + ", DISCOUNT=" + DISCOUNT + ", INTER_RETURN=" + INTER_RETURN + ", NEEDLESS_REPAIR=" + NEEDLESS_REPAIR
               + ", CONSIGN_EXTERIOR=" + CONSIGN_EXTERIOR + ", PRINT_RP_TIME=" + PRINT_RP_TIME + ", PRINT_BATCH_NO="
               + PRINT_BATCH_NO + ", LABOUR_CODE=" + LABOUR_CODE + ", MODEL_LABOUR_CODE=" + MODEL_LABOUR_CODE
               + ", PACKAGE_CODE=" + PACKAGE_CODE + ", IS_DISCOUNT=" + IS_DISCOUNT + ", REPAIR_TYPE_CODE="
               + REPAIR_TYPE_CODE + ", NON_ONE_OFF=" + NON_ONE_OFF + ", ADD_TAG=" + ADD_TAG + ", REASON=" + REASON
               + ", CARD_ID=" + CARD_ID + ", FROM_TYPE=" + FROM_TYPE + ", DXP_DATE=" + DXP_DATE + ", LABOUR_NAME="
               + LABOUR_NAME + ", DEALER_CODE=" + DEALER_CODE + ", RO_NO=" + RO_NO + ", OTHER_PART_COST_PRICE="
               + OTHER_PART_COST_PRICE + ", OTHER_PART_COST_AMOUNT=" + OTHER_PART_COST_AMOUNT + ", POS_CODE=" + POS_CODE
               + ", POS_NAME=" + POS_NAME + ", PART_INFIX=" + PART_INFIX + ", WAR_LEVEL=" + WAR_LEVEL
               + ", IS_OLDPART_TREAT=" + IS_OLDPART_TREAT + ", PART_CATEGORY=" + PART_CATEGORY
               + ", MAINTAIN_PACKAGE_CODE=" + MAINTAIN_PACKAGE_CODE + ", REPORT_B_I_DATETIME=" + REPORT_B_I_DATETIME
               + ", createdBy=" + createdBy + ", createdAt=" + createdAt + ", updatedBy=" + updatedBy + ", updatedAt="
               + updatedAt + "]";
    }

}
