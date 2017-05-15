package com.yonyou.dms.vehicle.domains.DTO.stockManage;
/**
 * pdi检查DTO 
 * @author wangliang
 * @date 2016年01月12日
 */

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.yonyou.dms.function.utils.validate.define.Required;

/**
 * @author Administrator
 *
 */
public class PDICheckedDTO {

    private String dealerCode; //经销商代码
    
    private String vin;
    
    private String thReportNo; //技术报告号
    
    @Required
    private Integer pdiResult; //PDI检查结果
    
    @Required
    private Date   pdiCheckDate; //PDI检查时间
    
    @Required
    private double mileAge; //公里数
    
    
    private Date   pdiSubmitDate; //PDI检查提交时间
    
    //private String pdiRemark; //故障描述

    private String pdiId; //附件ID
    
    private String pdiUrl; //附件URL
    
    private double dKey; //D_KEY

    private List<Map> dms_table3; //故障描述

	public String getDealerCode() {
        return dealerCode;
    }

    



	public List<Map> getDms_table3() {
		return dms_table3;
	}





	public void setDms_table3(List<Map> dms_table3) {
		this.dms_table3 = dms_table3;
	}





	public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode;
    }

    
    public String getVin() {
        return vin;
    }

    
    public void setVin(String vin) {
        this.vin = vin;
    }

    
    public String getThReportNo() {
        return thReportNo;
    }

    
    public void setThReportNo(String thReportNo) {
        this.thReportNo = thReportNo;
    }
    
    public Integer getPdiResult() {
		return pdiResult;
	}


	public void setPdiResult(Integer pdiResult) {
		this.pdiResult = pdiResult;
	}


	public Date getPdiCheckDate() {
        return pdiCheckDate;
    }

    
    public void setPdiCheckDate(Date pdiCheckDate) {
        this.pdiCheckDate = pdiCheckDate;
    }

    
    public double getMileAge() {
        return mileAge;
    }

    
    public void setMileAge(double mileAge) {
        this.mileAge = mileAge;
    }

    
    public Date getPdiSubmitDate() {
        return pdiSubmitDate;
    }

    
    public void setPdiSubmitDate(Date pdiSubmitDate) {
        this.pdiSubmitDate = pdiSubmitDate;
    }


    public String getPdiId() {
        return pdiId;
    }

    
    public void setPdiId(String pdiId) {
        this.pdiId = pdiId;
    }

    
    public String getPdiUrl() {
        return pdiUrl;
    }

    
    public void setPdiUrl(String pdiUrl) {
        this.pdiUrl = pdiUrl;
    }

    
    public double getdKey() {
        return dKey;
    }

    
    public void setdKey(double dKey) {
        this.dKey = dKey;
    }

}
