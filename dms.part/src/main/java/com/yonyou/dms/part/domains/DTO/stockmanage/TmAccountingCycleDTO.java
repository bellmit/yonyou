package com.yonyou.dms.part.domains.DTO.stockmanage;

import java.util.Date;

public class TmAccountingCycleDTO {
   
  //private Long createBy;
    // private Date createDate;
    //private Date updateDate;
   // private Long updateBy;
    private String bYear;
    private String dealerCode;
    private Date beginDate;
    private String periods;
    private Integer ver;
    private Date endDate;
    private Integer isExecuted;
    
    public Date getBeginDate() {
        return beginDate;
    }
    
    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }
    
    public String getPeriods() {
        return periods;
    }
    
    public void setPeriods(String periods) {
        this.periods = periods;
    }
    
    public String getbYear() {
        return bYear;
    }
    
    public void setbYear(String bYear) {
        this.bYear = bYear;
    }
    
    
    
    public String getDealerCode() {
        return dealerCode;
    }

    
    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode;
    }

    public Integer getVer() {
        return ver;
    }
    
    public void setVer(Integer ver) {
        this.ver = ver;
    }
    
    public Date getEndDate() {
        return endDate;
    }
    
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    
    public Integer getIsExecuted() {
        return isExecuted;
    }
    
    public void setIsExecuted(Integer isExecuted) {
        this.isExecuted = isExecuted;
    }
    
    
}
