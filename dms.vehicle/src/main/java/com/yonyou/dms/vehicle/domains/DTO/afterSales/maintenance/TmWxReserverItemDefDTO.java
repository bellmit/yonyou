package com.yonyou.dms.vehicle.domains.DTO.afterSales.maintenance;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class TmWxReserverItemDefDTO {
	private String reserveDesc;
	private Integer isVaild;
	private String reserveId;
	private Integer reserveType;
	private String dealerCode;
	private Date updateDate;
	private Date belongDate;
	private Long createBy;
	private Integer reserveNumLimit;
	private Integer isDel;
	private Long updateBy;
	private Long id;
	private Integer ver;
	private Date createDate;
	private Integer isArc;
	private List<Map> dms_table;

	public List<Map> getDms_table() {
		return dms_table;
	}

	public void setDms_table(List<Map> dms_table) {
		this.dms_table = dms_table;
	}

	public void setReserveDesc(String reserveDesc){
		this.reserveDesc=reserveDesc;
	}

	public String getReserveDesc(){
		return this.reserveDesc;
	}

	public void setIsVaild(Integer isVaild){
		this.isVaild=isVaild;
	}

	public Integer getIsVaild(){
		return this.isVaild;
	}

	public void setReserveId(String reserveId){
		this.reserveId=reserveId;
	}

	public String getReserveId(){
		return this.reserveId;
	}

	public void setReserveType(Integer reserveType){
		this.reserveType=reserveType;
	}

	public Integer getReserveType(){
		return this.reserveType;
	}

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

	public void setBelongDate(Date belongDate){
		this.belongDate=belongDate;
	}

	public Date getBelongDate(){
		return this.belongDate;
	}

	public void setCreateBy(Long createBy){
		this.createBy=createBy;
	}

	public Long getCreateBy(){
		return this.createBy;
	}

	public void setReserveNumLimit(Integer reserveNumLimit){
		this.reserveNumLimit=reserveNumLimit;
	}

	public Integer getReserveNumLimit(){
		return this.reserveNumLimit;
	}

	public void setIsDel(Integer isDel){
		this.isDel=isDel;
	}

	public Integer getIsDel(){
		return this.isDel;
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

	public void setVer(Integer ver){
		this.ver=ver;
	}

	public Integer getVer(){
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
