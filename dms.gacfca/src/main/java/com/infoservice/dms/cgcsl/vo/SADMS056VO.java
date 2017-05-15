package com.infoservice.dms.cgcsl.vo;

import java.util.Date;

public class SADMS056VO extends BaseVO {
	
	private static final long serialVersionUID = 1L;
	private String entityCode;//下端经销code
	private String questionnaireCode;//问卷编号
	private Date submitDate;//反馈日期
	private String seriesCode;//车系代码
	private String questionCode; //问题编号
	private String answerNO;//答案编号
	private Long sumDb000; //答案数量
	public String getEntityCode() {
		return entityCode;
	}
	public void setEntityCode(String entityCode) {
		this.entityCode = entityCode;
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
	public String getAnswerNO() {
		return answerNO;
	}
	public void setAnswerNO(String answerNO) {
		this.answerNO = answerNO;
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
