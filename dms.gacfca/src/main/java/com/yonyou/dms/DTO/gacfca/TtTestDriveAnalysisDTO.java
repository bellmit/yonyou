package com.yonyou.dms.DTO.gacfca;

import java.util.Date;

/**
 * 试乘试驾分析数据上报
 * @author DC
 *
 */
public class TtTestDriveAnalysisDTO {
	
	private Long verySatisfied;
	private Long satisfied;
	private Long generally;
	private String dealerCode;
	private Long id;
	private Date feedbackDate;
	private Date createDate;
	private String entityCode;//下端经销code
	private String questionnaireCode;//问卷编号
	private Date submitDate;//反馈日期
	private String seriesCode;//车系代码
	private String questionCode; //问题编号
	private String answerNO;//答案编号
	private Long sumDb000; //答案数量

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setVerySatisfied(Long verySatisfied){
		this.verySatisfied=verySatisfied;
	}

	public Long getVerySatisfied(){
		return this.verySatisfied;
	}

	public void setSatisfied(Long satisfied){
		this.satisfied=satisfied;
	}

	public Long getSatisfied(){
		return this.satisfied;
	}

	public void setGenerally(Long generally){
		this.generally=generally;
	}

	public Long getGenerally(){
		return this.generally;
	}

	public void setDealerCode(String dealerCode){
		this.dealerCode=dealerCode;
	}

	public String getDealerCode(){
		return this.dealerCode;
	}

	public void setQuestionnaireCode(String questionnaireCode){
		this.questionnaireCode=questionnaireCode;
	}

	public String getQuestionnaireCode(){
		return this.questionnaireCode;
	}

	public void setId(Long id){
		this.id=id;
	}

	public Long getId(){
		return this.id;
	}

	public void setSeriesCode(String seriesCode){
		this.seriesCode=seriesCode;
	}

	public String getSeriesCode(){
		return this.seriesCode;
	}

	public void setQuestionCode(String questionCode){
		this.questionCode=questionCode;
	}

	public String getQuestionCode(){
		return this.questionCode;
	}

	public void setFeedbackDate(Date feedbackDate){
		this.feedbackDate=feedbackDate;
	}

	public Date getFeedbackDate(){
		return this.feedbackDate;
	}

	public String getEntityCode() {
		return entityCode;
	}

	public void setEntityCode(String entityCode) {
		this.entityCode = entityCode;
	}

	public Date getSubmitDate() {
		return submitDate;
	}

	public void setSubmitDate(Date submitDate) {
		this.submitDate = submitDate;
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

}
