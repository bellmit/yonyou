package com.yonyou.dms.part.domains.DTO.basedata;


public class ToolsTypeDTO {
//    <!-- DEALER_CODE VARCHAR(30) NOT NULL,
//    TOOL_TYPE_CODE VARCHAR(4) NOT NULL,
//    TOOL_TYPE_NAME VARCHAR(30),   
//-->
    private String dealerCode;
    private String toolTypeCode;
    private String toolTypeName;
    
    public String getDealerCode() {
        return dealerCode;
    }
    
    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode;
    }
    
    public String getToolTypeCode() {
        return toolTypeCode;
    }
    
    public void setToolTypeCode(String toolTypeCode) {
        this.toolTypeCode = toolTypeCode;
    }
    
    public String getToolTypeName() {
        return toolTypeName;
    }
    
    public void setToolTypeName(String toolTypeName) {
        this.toolTypeName = toolTypeName;
    }
    
}
