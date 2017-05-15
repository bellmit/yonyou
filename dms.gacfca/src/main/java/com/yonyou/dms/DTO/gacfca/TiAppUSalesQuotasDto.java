package com.yonyou.dms.DTO.gacfca;

import java.util.Date;

public class TiAppUSalesQuotasDto {
	
	private Long salesQuotasId;
    private String uniquenessID;//DMS客户唯一ID
    private Integer FCAID;//售中客户唯一标识
    private String oldDealerUserID;//销售人员的ID//原销售人员ID
    private String dealerUserID;//销售人员的ID
    private String dealerCode;
    private Date updateDate;//更新时间
        
    public Long getSalesQuotasId() {
		return salesQuotasId;
	}


	public void setSalesQuotasId(Long salesQuotasId) {
		this.salesQuotasId = salesQuotasId;
	}


	public Date getUpdateDate() {
        return updateDate;
    }

    
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getUniquenessID() {
        return uniquenessID;
    }
    
    public void setUniquenessID(String uniquenessID) {
        this.uniquenessID = uniquenessID;
    }
    
    public Integer getFCAID() {
        return FCAID;
    }
    
    public void setFCAID(Integer fCAID) {
        FCAID = fCAID;
    }
    
    public String getOldDealerUserID() {
        return oldDealerUserID;
    }
    
    public void setOldDealerUserID(String oldDealerUserID) {
        this.oldDealerUserID = oldDealerUserID;
    }
    
    public String getDealerUserID() {
        return dealerUserID;
    }
    
    public void setDealerUserID(String dealerUserID) {
        this.dealerUserID = dealerUserID;
    }
    
    public String getDealerCode() {
        return dealerCode;
    }
    
    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode;
    }
    
    
}
