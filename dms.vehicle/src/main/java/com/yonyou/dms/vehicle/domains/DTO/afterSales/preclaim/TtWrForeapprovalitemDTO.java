package com.yonyou.dms.vehicle.domains.DTO.afterSales.preclaim;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class TtWrForeapprovalitemDTO {
	private String itemDesc;
	private Integer itemType;
	private Date updateDate;
	private Long createBy;
	private Long itemId;
	private Integer isDel;
	private Long fid;
	private String checkRemark;
	private Long updateBy;
	private Long id;
	private String itemCode;
	private String authCode;
	private Date createDate;
	private List<Map> one_table;
	private List<Map> two_table;
	
	

	public List<Map> getOne_table() {
		return one_table;
	}

	public void setOne_table(List<Map> one_table) {
		this.one_table = one_table;
	}

	public List<Map> getTwo_table() {
		return two_table;
	}

	public void setTwo_table(List<Map> two_table) {
		this.two_table = two_table;
	}

	public void setItemDesc(String itemDesc){
		this.itemDesc=itemDesc;
	}

	public String getItemDesc(){
		return this.itemDesc;
	}

	public void setItemType(Integer itemType){
		this.itemType=itemType;
	}

	public Integer getItemType(){
		return this.itemType;
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

	public void setItemId(Long itemId){
		this.itemId=itemId;
	}

	public Long getItemId(){
		return this.itemId;
	}

	public void setIsDel(Integer isDel){
		this.isDel=isDel;
	}

	public Integer getIsDel(){
		return this.isDel;
	}

	public void setFid(Long fid){
		this.fid=fid;
	}

	public Long getFid(){
		return this.fid;
	}

	public void setCheckRemark(String checkRemark){
		this.checkRemark=checkRemark;
	}

	public String getCheckRemark(){
		return this.checkRemark;
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

	public void setItemCode(String itemCode){
		this.itemCode=itemCode;
	}

	public String getItemCode(){
		return this.itemCode;
	}

	public void setAuthCode(String authCode){
		this.authCode=authCode;
	}

	public String getAuthCode(){
		return this.authCode;
	}

	public void setCreateDate(Date createDate){
		this.createDate=createDate;
	}

	public Date getCreateDate(){
		return this.createDate;
	}
}
