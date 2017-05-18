package com.yonyou.dms.repair.domains.DTO.basedata;

import java.util.Date;
import java.util.List;
public class ListToolBuyItemDTO {
    private List<ToolBuyItemDTO> dms_table;
    private String buyNo;
    private String customerCode;
    private String customerName;
    private Date buyDate;
    private Integer isFinished;
    private String handler;
    
    @Override
    public String toString() {
        return "dms_tableDTO [dms_table=" + dms_table + ", buyNo=" + buyNo + ", customerCode="
               + customerCode + ", customerName=" + customerName + ", buyDate=" + buyDate + ", isFinished=" + isFinished
               + ", handler=" + handler + "]";
    }

    
    
    public List<ToolBuyItemDTO> getdms_table() {
        return dms_table;
    }


    
    public void setdms_table(List<ToolBuyItemDTO> dms_table) {
        this.dms_table = dms_table;
    }


    public String getBuyNo() {
        return buyNo;
    }
    
    public void setBuyNo(String buyNo) {
        this.buyNo = buyNo;
    }
    
    public String getCustomerCode() {
        return customerCode;
    }
    
    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }
    
    public String getCustomerName() {
        return customerName;
    }
    
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    
    public Date getBuyDate() {
        return buyDate;
    }
    
    public void setBuyDate(Date buyDate) {
        this.buyDate = buyDate;
    }
    
    public Integer getIsFinished() {
        return isFinished;
    }
    
    public void setIsFinished(Integer isFinished) {
        this.isFinished = isFinished;
    }
    
    public String getHandler() {
        return handler;
    }
    
    public void setHandler(String handler) {
        this.handler = handler;
    }
    
    
    
    
    
}
