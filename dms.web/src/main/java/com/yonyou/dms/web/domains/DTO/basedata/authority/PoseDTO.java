package com.yonyou.dms.web.domains.DTO.basedata.authority;

import java.util.Date;

public class PoseDTO {
	private Long companyId;
	private String poseCode;
	private Date updateDate;
	private Long createBy;
	private Long orgId;
	private Integer poseType;
	private Integer outPoseType;
	private Long dcId;
	private Long poseId;
	private Date createDate;
	private Integer poseStatus;
	private Integer prePoseType;
	private Long updateBy;
	private Integer poseBusType;
	private String remark;
	private Integer anwserQuestionAbility;
	private String poseName;
	private Integer dealerFixPose;
	private Long dealerId;
	
	private String addIds;
	private String seriesIds;
	

	public String getAddIds() {
		return addIds;
	}

	public void setAddIds(String addIds) {
		this.addIds = addIds;
	}

	public String getSeriesIds() {
		return seriesIds;
	}

	public void setSeriesIds(String seriesIds) {
		this.seriesIds = seriesIds;
	}

	public Integer getDealerFixPose() {
		return dealerFixPose;
	}

	public void setDealerFixPose(Integer dealerFixPose) {
		this.dealerFixPose = dealerFixPose;
	}

	public void setCompanyId(Long companyId){
		this.companyId=companyId;
	}

	public Long getCompanyId(){
		return this.companyId;
	}

	public void setPoseCode(String poseCode){
		this.poseCode=poseCode;
	}

	public String getPoseCode(){
		return this.poseCode;
	}

	public void setUpdateDate(Date updateDate){
		this.updateDate=updateDate;
	}

	public Date getUpdateDate(){
		return this.updateDate;
	}

	public void setCreateBy(Long createBy){
		this.createBy=createBy;
	}

	public Long getCreateBy(){
		return this.createBy;
	}

	public void setOrgId(Long orgId){
		this.orgId=orgId;
	}

	public Long getOrgId(){
		return this.orgId;
	}

	public void setPoseType(Integer poseType){
		this.poseType=poseType;
	}

	public Integer getPoseType(){
		return this.poseType;
	}

	public void setOutPoseType(Integer outPoseType){
		this.outPoseType=outPoseType;
	}

	public Integer getOutPoseType(){
		return this.outPoseType;
	}

	public void setDcId(Long dcId){
		this.dcId=dcId;
	}

	public Long getDcId(){
		return this.dcId;
	}

	public void setPoseId(Long poseId){
		this.poseId=poseId;
	}

	public Long getPoseId(){
		return this.poseId;
	}

	public void setCreateDate(Date createDate){
		this.createDate=createDate;
	}

	public Date getCreateDate(){
		return this.createDate;
	}

	public void setPoseStatus(Integer poseStatus){
		this.poseStatus=poseStatus;
	}

	public Integer getPoseStatus(){
		return this.poseStatus;
	}

	public void setPrePoseType(Integer prePoseType){
		this.prePoseType=prePoseType;
	}

	public Integer getPrePoseType(){
		return this.prePoseType;
	}

	public void setUpdateBy(Long updateBy){
		this.updateBy=updateBy;
	}

	public Long getUpdateBy(){
		return this.updateBy;
	}

	public void setPoseBusType(Integer poseBusType){
		this.poseBusType=poseBusType;
	}

	public Integer getPoseBusType(){
		return this.poseBusType;
	}

	public void setRemark(String remark){
		this.remark=remark;
	}

	public String getRemark(){
		return this.remark;
	}

	public void setAnwserQuestionAbility(Integer anwserQuestionAbility){
		this.anwserQuestionAbility=anwserQuestionAbility;
	}

	public Integer getAnwserQuestionAbility(){
		return this.anwserQuestionAbility;
	}

	public void setPoseName(String poseName){
		this.poseName=poseName;
	}

	public String getPoseName(){
		return this.poseName;
	}

	public Long getDealerId() {
		return dealerId;
	}

	public void setDealerId(Long dealerId) {
		this.dealerId = dealerId;
	}
	
	
}
