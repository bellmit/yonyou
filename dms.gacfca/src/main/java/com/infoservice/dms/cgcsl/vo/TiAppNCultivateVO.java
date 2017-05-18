package com.infoservice.dms.cgcsl.vo;

import java.util.Date;

public class TiAppNCultivateVO extends BaseVO{
	
    private String entityCode;
    private Long fcaId;
    private String uniquenessID;//DMS客户唯一ID
    private Date commDate;//沟通日期
    private String commContent;//沟通内容
    private String dealerCode;//经销商代码
    private String nextCommContent;//下次沟通内容
    private Date nextCommDate;//下次沟通日期
    private String commType;//沟通方式
    private Date createDate;//创建时间
    private String followOppLevelId;//跟进后客户级别
    private String dealerUserId;//销售人员的ID
    private String dormantType;//休眠类型
//  private String compareBrandID;//竞品的品牌
//  private String compareModelID;//竞品车型
    private String giveUpReason;//放弃原有
    private Date giveUpDate;//放弃日期
    private Long cultivateId;
    
    public String getEntityCode() {
        return entityCode;
    }
    
    public void setEntityCode(String entityCode) {
        this.entityCode = entityCode;
    }
    
    public Long getFcaId() {
        return fcaId;
    }
    
    public void setFcaId(Long fcaId) {
        this.fcaId = fcaId;
    }
    
    public String getUniquenessID() {
        return uniquenessID;
    }
    
    public void setUniquenessID(String uniquenessID) {
        this.uniquenessID = uniquenessID;
    }
    
    public Date getCommDate() {
        return commDate;
    }
    
    public void setCommDate(Date commDate) {
        this.commDate = commDate;
    }
    
    public String getCommContent() {
        return commContent;
    }
    
    public void setCommContent(String commContent) {
        this.commContent = commContent;
    }
    
    public String getDealerCode() {
        return dealerCode;
    }
    
    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode;
    }
    
    public String getNextCommContent() {
        return nextCommContent;
    }
    
    public void setNextCommContent(String nextCommContent) {
        this.nextCommContent = nextCommContent;
    }
    
    public Date getNextCommDate() {
        return nextCommDate;
    }
    
    public void setNextCommDate(Date nextCommDate) {
        this.nextCommDate = nextCommDate;
    }
    
    public String getCommType() {
        return commType;
    }
    
    public void setCommType(String commType) {
        this.commType = commType;
    }
    
    public Date getCreateDate() {
        return createDate;
    }
    
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    
    public String getFollowOppLevelId() {
        return followOppLevelId;
    }
    
    public void setFollowOppLevelId(String followOppLevelId) {
        this.followOppLevelId = followOppLevelId;
    }
    
    public String getDealerUserId() {
        return dealerUserId;
    }
    
    public void setDealerUserId(String dealerUserId) {
        this.dealerUserId = dealerUserId;
    }
    
    public String getDormantType() {
        return dormantType;
    }
    
    public void setDormantType(String dormantType) {
        this.dormantType = dormantType;
    }
    
    public String getGiveUpReason() {
        return giveUpReason;
    }
    
    public void setGiveUpReason(String giveUpReason) {
        this.giveUpReason = giveUpReason;
    }
    
    public Date getGiveUpDate() {
        return giveUpDate;
    }
    
    public void setGiveUpDate(Date giveUpDate) {
        this.giveUpDate = giveUpDate;
    }

	public Long getCultivateId() {
		return cultivateId;
	}

	public void setCultivateId(Long cultivateId) {
		this.cultivateId = cultivateId;
	}
    
    
}
