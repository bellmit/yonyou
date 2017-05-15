package com.yonyou.dms.repair.domains.DTO.basedata;

/**
 * 其他成本
* TODO description
* @author yujiangheng
* @date 2017年4月10日
 */
public class OtherCostDefineDTO {
    
    private  String dealerCode;
    private String otherCostName;
    private String otherCostCode;
    public String getDealerCode() {
        return dealerCode;
    }
    
    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode;
    }
    
    
    public String getOtherCostName() {
        return otherCostName;
    }
    
    public void setOtherCostName(String otherCostName) {
        this.otherCostName = otherCostName;
    }
    
    public String getOtherCostCode() {
        return otherCostCode;
    }
    
    public void setOtherCostCode(String otherCostCode) {
        this.otherCostCode = otherCostCode;
    }
}
