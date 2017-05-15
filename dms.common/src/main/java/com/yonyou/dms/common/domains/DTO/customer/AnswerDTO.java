package com.yonyou.dms.common.domains.DTO.customer;


public class AnswerDTO {
    private String  dealerCode;
    private String fromEntity;
    private Integer isValid;
    private Integer ver;
    private Integer downTag;
    private String answerNo;
    private String answerGroupNo;
    private String answerDesc;
    private String answer;
    
    public String getDealerCode() {
        return dealerCode;
    }
    
    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode;
    }
    
    public String getFromEntity() {
        return fromEntity;
    }
    
    public void setFromEntity(String fromEntity) {
        this.fromEntity = fromEntity;
    }
    
    public Integer getIsValid() {
        return isValid;
    }
    
    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }
    
    public Integer getVer() {
        return ver;
    }
    
    public void setVer(Integer ver) {
        this.ver = ver;
    }
    
    public Integer getDownTag() {
        return downTag;
    }
    
    public void setDownTag(Integer downTag) {
        this.downTag = downTag;
    }
    
    public String getAnswerNo() {
        return answerNo;
    }
    
    public void setAnswerNo(String answerNo) {
        this.answerNo = answerNo;
    }
    
    public String getAnswerGroupNo() {
        return answerGroupNo;
    }
    
    public void setAnswerGroupNo(String answerGroupNo) {
        this.answerGroupNo = answerGroupNo;
    }
    
    public String getAnswerDesc() {
        return answerDesc;
    }
    
    public void setAnswerDesc(String answerDesc) {
        this.answerDesc = answerDesc;
    }
    
    public String getAnswer() {
        return answer;
    }
    
    public void setAnswer(String answer) {
        this.answer = answer;
    }

}
