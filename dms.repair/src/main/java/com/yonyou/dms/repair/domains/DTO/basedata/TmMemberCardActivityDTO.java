package com.yonyou.dms.repair.domains.DTO.basedata;

import java.util.Date;

public class TmMemberCardActivityDTO {

	 private String dealerCode;
	 private Long itemId;
	 private String memberActivityCode;
	 private Integer purchaseCount;
	 private Integer usedTicketCount;
	 private Integer cardId;
	 private Integer oemTag;
	 private Double memberActivityAmount;
	 private Date expDate;
	 private Date lastUpdateDate;
	 private Integer isUseSpecialFund;
	 private Integer businessType;
    
    public String getDealerCode() {
        return dealerCode;
    }
    
    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode;
    }
    
    public Long getItemId() {
        return itemId;
    }
    
    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }
    
    public String getMemberActivityCode() {
        return memberActivityCode;
    }
    
    public void setMemberActivityCode(String memberActivityCode) {
        this.memberActivityCode = memberActivityCode;
    }
    
    public Integer getPurchaseCount() {
        return purchaseCount;
    }
    
    public void setPurchaseCount(Integer purchaseCount) {
        this.purchaseCount = purchaseCount;
    }
    
    public Integer getUsedTicketCount() {
        return usedTicketCount;
    }
    
    public void setUsedTicketCount(Integer usedTicketCount) {
        this.usedTicketCount = usedTicketCount;
    }
    
    public Integer getCardId() {
        return cardId;
    }
    
    public void setCardId(Integer cardId) {
        this.cardId = cardId;
    }
    
    public Integer getOemTag() {
        return oemTag;
    }
    
    public void setOemTag(Integer oemTag) {
        this.oemTag = oemTag;
    }
    
    public Double getMemberActivityAmount() {
        return memberActivityAmount;
    }
    
    public void setMemberActivityAmount(Double memberActivityAmount) {
        this.memberActivityAmount = memberActivityAmount;
    }
    
    public Date getExpDate() {
        return expDate;
    }
    
    public void setExpDate(Date expDate) {
        this.expDate = expDate;
    }
    
    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }
    
    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }
    
    public Integer getIsUseSpecialFund() {
        return isUseSpecialFund;
    }
    
    public void setIsUseSpecialFund(Integer isUseSpecialFund) {
        this.isUseSpecialFund = isUseSpecialFund;
    }
    
    public Integer getBusinessType() {
        return businessType;
    }
    
    public void setBusinessType(Integer businessType) {
        this.businessType = businessType;
    }

    @Override
    public String toString() {
        return "MemberCardActivityDTO [dealerCode=" + dealerCode + ", itemId=" + itemId + ", memberActivityCode="
               + memberActivityCode + ", purchaseCount=" + purchaseCount + ", usedTicketCount=" + usedTicketCount
               + ", cardId=" + cardId + ", oemTag=" + oemTag + ", memberActivityAmount=" + memberActivityAmount
               + ", expDate=" + expDate + ", lastUpdateDate=" + lastUpdateDate + ", isUseSpecialFund="
               + isUseSpecialFund + ", businessType=" + businessType + "]";
    }
	 
}
