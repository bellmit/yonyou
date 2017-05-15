package com.yonyou.dms.vehicle.domains.DTO.threePack;

import java.util.Date;

public class TtThreepackWarnDTO {
	private String remark1;
	private String remark2;
	private String vin;
	private Integer warnTimes;
	private String warnItemName;
	private Date updateDate;
	private Long createBy;
	private Integer isDel;
	private String warnItemNo;
	private Long updateBy;
	private Long id;
	private Long ver;
	private Date createDate;
	private Integer isArc;

	public void setRemark1(String remark1){
		this.remark1=remark1;
	}

	public String getRemark1(){
		return this.remark1;
	}

	public void setRemark2(String remark2){
		this.remark2=remark2;
	}

	public String getRemark2(){
		return this.remark2;
	}

	public void setVin(String vin){
		this.vin=vin;
	}

	public String getVin(){
		return this.vin;
	}

	public void setWarnTimes(Integer warnTimes){
		this.warnTimes=warnTimes;
	}

	public Integer getWarnTimes(){
		return this.warnTimes;
	}

	public void setWarnItemName(String warnItemName){
		this.warnItemName=warnItemName;
	}

	public String getWarnItemName(){
		return this.warnItemName;
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

	public void setIsDel(Integer isDel){
		this.isDel=isDel;
	}

	public Integer getIsDel(){
		return this.isDel;
	}

	public void setWarnItemNo(String warnItemNo){
		this.warnItemNo=warnItemNo;
	}

	public String getWarnItemNo(){
		return this.warnItemNo;
	}

	public void setUpdateBy(Long updateBy){
		this.updateBy=updateBy;
	}

	public Long getUpdateBy(){
		return this.updateBy;
	}

	public void setId(Long id){
		this.id=id;
	}

	public Long getId(){
		return this.id;
	}

	public void setVer(Long ver){
		this.ver=ver;
	}

	public Long getVer(){
		return this.ver;
	}

	public void setCreateDate(Date createDate){
		this.createDate=createDate;
	}

	public Date getCreateDate(){
		return this.createDate;
	}

	public void setIsArc(Integer isArc){
		this.isArc=isArc;
	}

	public Integer getIsArc(){
		return this.isArc;
	}

}
