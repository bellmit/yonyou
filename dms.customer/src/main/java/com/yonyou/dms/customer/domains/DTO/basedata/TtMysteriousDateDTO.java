package com.yonyou.dms.customer.domains.DTO.basedata;

import java.util.Date;

public class TtMysteriousDateDTO {

	private String dealerCode;
	private Date updateDate;
	private String fileId;
	private Long createBy;
	private String fileUrl;
	private Integer isDown;
	private String dealerName;
	private String phone;
	private String fileName;
	private String execAuthor;
	private Long updateBy;
	private Long mysteriousId;
	private Date createDate;

	public void setDealerCode(String dealerCode){
		this.dealerCode=dealerCode;
	}

	public String getDealerCode(){
		return this.dealerCode;
	}

	public void setUpdateDate(Date updateDate){
		this.updateDate=updateDate;
	}

	public Date getUpdateDate(){
		return this.updateDate;
	}

	public void setFileId(String fileId){
		this.fileId=fileId;
	}

	public String getFileId(){
		return this.fileId;
	}

	public void setCreateBy(Long createBy){
		this.createBy=createBy;
	}

	public Long getCreateBy(){
		return this.createBy;
	}

	public void setFileUrl(String fileUrl){
		this.fileUrl=fileUrl;
	}

	public String getFileUrl(){
		return this.fileUrl;
	}

	public void setIsDown(Integer isDown){
		this.isDown=isDown;
	}

	public Integer getIsDown(){
		return this.isDown;
	}

	public void setDealerName(String dealerName){
		this.dealerName=dealerName;
	}

	public String getDealerName(){
		return this.dealerName;
	}

	public void setPhone(String phone){
		this.phone=phone;
	}

	public String getPhone(){
		return this.phone;
	}

	public void setFileName(String fileName){
		this.fileName=fileName;
	}

	public String getFileName(){
		return this.fileName;
	}

	public void setExecAuthor(String execAuthor){
		this.execAuthor=execAuthor;
	}

	public String getExecAuthor(){
		return this.execAuthor;
	}

	public void setUpdateBy(Long updateBy){
		this.updateBy=updateBy;
	}

	public Long getUpdateBy(){
		return this.updateBy;
	}

	public void setMysteriousId(Long mysteriousId){
		this.mysteriousId=mysteriousId;
	}

	public Long getMysteriousId(){
		return this.mysteriousId;
	}

	public void setCreateDate(Date createDate){
		this.createDate=createDate;
	}

	public Date getCreateDate(){
		return this.createDate;
	}
}
