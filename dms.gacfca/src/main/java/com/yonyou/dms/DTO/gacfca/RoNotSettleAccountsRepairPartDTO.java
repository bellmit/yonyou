package com.yonyou.dms.DTO.gacfca;

/**
 * @description 工单未结算维修配件列表
 * @author Administrator
 *
 */
public class RoNotSettleAccountsRepairPartDTO {
	private static final long serialVersionUID = 1L;
	private String partNo; //下端：配件代码  VARCHAR(27)  上端：PART_CODE VARCHAR(30) 
	private String partName; //下端：配件名称  VARCHAR(120)  上端：PART_NAME VARCHAR(200) 
	private Float partQuantity; //下端：配件数量  NUMERIC(8,2)  上端：QUANTITY NUMERIC(12,2) 
	private Double partSalesPrice; //下端：配件销售单价  NUMERIC(10,2)  上端：PRICE NUMERIC(12,2) 零部件的零售价格
	private String activityCode; //下端：活动编号  VARCHAR(15)  上端：？  上端活动编号在工单主表中
	private String labourCode; //下端：维修项目代码  VARCHAR(30)  上端：  上端增加字段LON号
	private Integer isMain;// 是否主因
	//private LinkedList snList; //下端：附加项目列表
	private Double packageQuantity;//保养数量，下端直接传过来
	private String lackMaterial;//是否缺件  10041001是，10041002否
	private Integer oemTag;		//写入是否oem信息 。下端12781001 是集团 12781002 或者 其他情况都不是集团
	public String getPartNo() {
		return partNo;
	}
	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}
	public String getPartName() {
		return partName;
	}
	public void setPartName(String partName) {
		this.partName = partName;
	}
	public Float getPartQuantity() {
		return partQuantity;
	}
	public void setPartQuantity(Float partQuantity) {
		this.partQuantity = partQuantity;
	}
	public Double getPartSalesPrice() {
		return partSalesPrice;
	}
	public void setPartSalesPrice(Double partSalesPrice) {
		this.partSalesPrice = partSalesPrice;
	}
	public String getActivityCode() {
		return activityCode;
	}
	public void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
	}
	public String getLabourCode() {
		return labourCode;
	}
	public void setLabourCode(String labourCode) {
		this.labourCode = labourCode;
	}
	public Integer getIsMain() {
		return isMain;
	}
	public void setIsMain(Integer isMain) {
		this.isMain = isMain;
	}
	public Double getPackageQuantity() {
		return packageQuantity;
	}
	public void setPackageQuantity(Double packageQuantity) {
		this.packageQuantity = packageQuantity;
	}
	public String getLackMaterial() {
		return lackMaterial;
	}
	public void setLackMaterial(String lackMaterial) {
		this.lackMaterial = lackMaterial;
	}
	public Integer getOemTag() {
		return oemTag;
	}
	public void setOemTag(Integer oemTag) {
		this.oemTag = oemTag;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
