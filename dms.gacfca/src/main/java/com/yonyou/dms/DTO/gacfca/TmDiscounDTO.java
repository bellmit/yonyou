package com.yonyou.dms.DTO.gacfca;

/**
 * @description 优惠模式Dto
 * @author Administrator
 *
 */
public class TmDiscounDTO {
	private String labourDiscount;	//工时费优惠率
	private Integer isDown;			//下发状态
	private Integer isDel;			//删除标志
	private String partDiscount;	//维修材料费优惠率
	private Integer ver;			//版本控制
	private String discountName;	//优惠模式说明
	private Integer isArc;			//归档标志
	private String discountCode;	//优惠模式代码
	public String getLabourDiscount() {
		return labourDiscount;
	}
	public void setLabourDiscount(String labourDiscount) {
		this.labourDiscount = labourDiscount;
	}
	public Integer getIsDown() {
		return isDown;
	}
	public void setIsDown(Integer isDown) {
		this.isDown = isDown;
	}
	public Integer getIsDel() {
		return isDel;
	}
	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}
	public String getPartDiscount() {
		return partDiscount;
	}
	public void setPartDiscount(String partDiscount) {
		this.partDiscount = partDiscount;
	}
	public Integer getVer() {
		return ver;
	}
	public void setVer(Integer ver) {
		this.ver = ver;
	}
	public String getDiscountName() {
		return discountName;
	}
	public void setDiscountName(String discountName) {
		this.discountName = discountName;
	}
	public Integer getIsArc() {
		return isArc;
	}
	public void setIsArc(Integer isArc) {
		this.isArc = isArc;
	}
	public String getDiscountCode() {
		return discountCode;
	}
	public void setDiscountCode(String discountCode) {
		this.discountCode = discountCode;
	}
}
