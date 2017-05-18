package com.yonyou.dms.repair.domains.DTO.basedata;

import java.util.Date;

/**
 * 
* TODO description
* @author yangjie
* @date 2017年4月20日
 */
public class ToolBuyItemDTO {
//    ITEM_ID DECIMAL(14,0) NOT NULL, -- item_id
    
//    BUY_NO VARCHAR(12) NOT NULL,    -- buy_no
//    DEALER_CODE VARCHAR(8) NOT NULL,    -- dealer_code
//    TOOL_CODE VARCHAR(15),          -- tool_code    工具代码
//    TOOL_NAME VARCHAR(60),          -- tool_name    工具名称
//    UNIT_CODE VARCHAR(4),       -- unit_code        计量单位代码
    
//    UNIT_PRICE DECIMAL(12,2),   -- unit_price       单价
//    QUANTITY DECIMAL(10,2),     -- quantity     数量
//    AMOUNT DECIMAL(12,2),       -- amount       金额
    private Integer itemId;
    private String dealerCode;
    private String buyNo;
    private String toolCode;
    private String toolName;
    private String unitCode;
    
    private Double unitPrice;
    private Double quantity;
    private Double amount;
    
    @Override
    public String toString() {
        return "ToolBuyItemDTO [dealerCode=" + dealerCode + ", buyNo=" + buyNo + ", toolCode=" + toolCode
               + ", toolName=" + toolName + ", unitCode=" + unitCode + ", unitPrice=" + unitPrice + ", quantity="
               + quantity + ", amount=" + amount + "]";
    }

    
    public Integer getItemId() {
        return itemId;
    }

    
    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getDealerCode() {
        return dealerCode;
    }
    
    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode;
    }
    
    public String getBuyNo() {
        return buyNo;
    }
    
    public void setBuyNo(String buyNo) {
        this.buyNo = buyNo;
    }
    
    public String getToolCode() {
        return toolCode;
    }
    
    public void setToolCode(String toolCode) {
        this.toolCode = toolCode;
    }
    
    public String getToolName() {
        return toolName;
    }
    
    public void setToolName(String toolName) {
        this.toolName = toolName;
    }
    
    public String getUnitCode() {
        return unitCode;
    }
    
    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }


    
    public Double getUnitPrice() {
        return unitPrice;
    }


    
    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }


    
    public Double getQuantity() {
        return quantity;
    }


    
    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }


    
    public Double getAmount() {
        return amount;
    }


    
    public void setAmount(Double amount) {
        this.amount = amount;
    }
    
    
    
    
}
