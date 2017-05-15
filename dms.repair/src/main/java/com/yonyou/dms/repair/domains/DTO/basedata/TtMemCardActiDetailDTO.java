package com.yonyou.dms.repair.domains.DTO.basedata;

import java.util.Date;

public class TtMemCardActiDetailDTO {

	 private String dealerCode;
	 private Long itemId;
	 private String memberActivityCode;
	 private Integer useActiCount;
	 private Integer cardId;
	 private String memberActivityName;
	 private String operator;
	 private Date operateTime;
	 private String roNo;
	 private Integer dKey;
    
    
    public Integer getdKey() {
        return dKey;
    }

    
    public void setdKey(Integer dKey) {
        this.dKey = dKey;
    }

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
    
    public Integer getUseActiCount() {
        return useActiCount;
    }
    
    public void setUseActiCount(Integer useActiCount) {
        this.useActiCount = useActiCount;
    }
    
    public Integer getCardId() {
        return cardId;
    }
    
    public void setCardId(Integer cardId) {
        this.cardId = cardId;
    }
    
    public String getMemberActivityName() {
        return memberActivityName;
    }
    
    public void setMemberActivityName(String memberActivityName) {
        this.memberActivityName = memberActivityName;
    }
    
    public String getOperator() {
        return operator;
    }
    
    public void setOperator(String operator) {
        this.operator = operator;
    }
    
    public Date getOperateTime() {
        return operateTime;
    }
    
    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }
    
    public String getRoNo() {
        return roNo;
    }
    
    public void setRoNo(String roNo) {
        this.roNo = roNo;
    }

    @Override
    public String toString() {
        return "TtMemCardActiDetailDTO [dealerCode=" + dealerCode + ", itemId=" + itemId + ", memberActivityCode="
               + memberActivityCode + ", useActiCount=" + useActiCount + ", cardId=" + cardId + ", memberActivityName="
               + memberActivityName + ", operator=" + operator + ", operateTime=" + operateTime + ", roNo=" + roNo
               + "]";
    }
    
    
	 
}
