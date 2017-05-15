package com.yonyou.dms.DTO.gacfca;

import java.util.Date;

public class SADMS056Dto {
	private static final long serialVersionUID = 1L;
	
	private String dealerCode; //经销商代码
	
	private String questionnaireCode; //问卷编号
	
	private Date submitDate; //反馈日期
	
	private String seriesCode; //车系代码
	
	private String questionCode; //问题编号
	
	private String answerNo; //答案编号
	
	private Long sumDb000; //答案数量
	
	public String getDealerCode() {
		return dealerCode;
	}
	
	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}
	
	public String getQuestionnaireCode() {
		return questionnaireCode;
	}
	
	public void setQuestionnaireCode(String questionnaireCode) {
		this.questionnaireCode = questionnaireCode;
	}

	public Date getSubmitDate() {
		return submitDate;
	}
	
	public void setSubmitDate(Date submitDate) {
		this.submitDate = submitDate;
	}
	
	public String getSeriesCode() {
		return seriesCode;
	}
	
	public void setSeriesCode(String seriesCode) {
		this.seriesCode = seriesCode;
	}
	
	public String getQuestionCode() {
		return questionCode;
	}
	
	public void setQuestionCode(String questionCode) {
		this.questionCode = questionCode;
	}
	
	public String getAnswerNo() {
		return answerNo;
	}
	
	public void setAnswerNo(String answerNo) {
		this.answerNo = answerNo;
	}
	
	public Long getSumDb000() {
		return sumDb000;
	}
	
	public void setSumDb000(Long sumDb000) {
		this.sumDb000 = sumDb000;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
