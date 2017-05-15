package com.yonyou.dms.repair.domains.DTO.basedata;
import java.util.Date;
/**
 * 
* TODO description
* @author chenwei
* @date 2017年4月24日
 */
public class PartDailyReportDTO {
   private String dealerCode;
   private String bYear;
   private String periods;
   private Date beginDate;
   private Date endDate;
   private Integer isExecuted;
@Override
public String toString() {
    return "AccountPeriodDTO [dealerCode=" + dealerCode + ", bYear=" + bYear + ", periods=" + periods + ", beginDate="
           + beginDate + ", endDate=" + endDate + ", isExecuted=" + isExecuted + "]";
}

public String getDealerCode() {
    return dealerCode;
}

public void setDealerCode(String dealerCode) {
    this.dealerCode = dealerCode;
}

public String getbYear() {
    return bYear;
}

public void setbYear(String bYear) {
    this.bYear = bYear;
}

public String getPeriods() {
    return periods;
}

public void setPeriods(String periods) {
    this.periods = periods;
}

public Date getBeginDate() {
    return beginDate;
}

public void setBeginDate(Date beginDate) {
    this.beginDate = beginDate;
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
