package com.yonyou.dms.DTO.gacfca;

import java.util.Date;
import java.util.LinkedList;

public class UcReplaceRebateApplyDto {
	
	/** ************************** 車主信息(旧车车主与新车车主) ******************************** */
	private LinkedList<UcReplaceRebateOwnerDto> ownerList;

	/** ************************** 置换信息(新车VIN通过实销上报查询) ******************************** */
	private Integer replaceWay; // 置换方式
	private String dealerCode; // 置换申请单号 (销售订单号soNo)
	private String replaceApplyNo; // 置换申请单号 (销售订单号soNo)
	private Date replaceSubmitApplyDate; // 提交申请置换日期
	private Date replaceDate; // 置换日期
	private Date  oldVhclTransactionDate; // 旧车成交日期
	private String oldVhclTransactionPrice; // 旧车成交价格
	private Integer oldVhclTransactionWay; // 旧车成交方式
	private String  subsidyMoney; // 补贴金额

	/** ************************** (旧车)车辆信息 ******************************** */
	private String oldVhclBrand; // 品牌
	private String oldVhclSeries; // 车系
	private String oldVhclModel; // 车型
	private String oldVin; // 旧车VIN
	private Integer replaceIntentCount; // 置换意向数(用于报表)

	/** ************************** 申请材料 ******************************** */
	private String newVhclInvoicePic; // 新车发票图片
	private String oldVhclTravelCardPic; // 旧车行驶证图片
	private String oldVhclTransferMembershipPic; // 旧车转籍单图片
	private String cardPic; // 身份证明
	private String oldVhclCertificatesOfTitlePic; // 旧车产权证图片
	private String marriageCertificatesPic; // 婚姻状况图片
	private String finaPic; // 财务证明图片
	private String otherPic; // 其它证明图片
	
	private String newVhclInvoicePicId; // 新车发票图片ID
	private String oldVhclTravelCardPicId; // 旧车行驶证图片ID
	private String oldVhclTransferMembershipPicId; // 旧车转籍单图片ID
	private String cardPicId; // 身份证明ID
	private String oldVhclCertificatesOfTitlePicId; // 旧车产权证图片ID
	private String marriageCertificatesPicId; // 婚姻状况图片ID
	private String finaPicId; // 财务证明图片ID
	private String otherPicId; // 其它证明图片ID
	public LinkedList<UcReplaceRebateOwnerDto> getOwnerList() {
		return ownerList;
	}
	public void setOwnerList(LinkedList<UcReplaceRebateOwnerDto> ownerList) {
		this.ownerList = ownerList;
	}
	public Integer getReplaceWay() {
		return replaceWay;
	}
	public void setReplaceWay(Integer replaceWay) {
		this.replaceWay = replaceWay;
	}
	public String getDealerCode() {
		return dealerCode;
	}
	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}
	public String getReplaceApplyNo() {
		return replaceApplyNo;
	}
	public void setReplaceApplyNo(String replaceApplyNo) {
		this.replaceApplyNo = replaceApplyNo;
	}
	public Date getReplaceSubmitApplyDate() {
		return replaceSubmitApplyDate;
	}
	public void setReplaceSubmitApplyDate(Date replaceSubmitApplyDate) {
		this.replaceSubmitApplyDate = replaceSubmitApplyDate;
	}
	public Date getReplaceDate() {
		return replaceDate;
	}
	public void setReplaceDate(Date replaceDate) {
		this.replaceDate = replaceDate;
	}
	public Date getOldVhclTransactionDate() {
		return oldVhclTransactionDate;
	}
	public void setOldVhclTransactionDate(Date oldVhclTransactionDate) {
		this.oldVhclTransactionDate = oldVhclTransactionDate;
	}
	public String getOldVhclTransactionPrice() {
		return oldVhclTransactionPrice;
	}
	public void setOldVhclTransactionPrice(String oldVhclTransactionPrice) {
		this.oldVhclTransactionPrice = oldVhclTransactionPrice;
	}
	public Integer getOldVhclTransactionWay() {
		return oldVhclTransactionWay;
	}
	public void setOldVhclTransactionWay(Integer oldVhclTransactionWay) {
		this.oldVhclTransactionWay = oldVhclTransactionWay;
	}
	public String getSubsidyMoney() {
		return subsidyMoney;
	}
	public void setSubsidyMoney(String subsidyMoney) {
		this.subsidyMoney = subsidyMoney;
	}
	public String getOldVhclBrand() {
		return oldVhclBrand;
	}
	public void setOldVhclBrand(String oldVhclBrand) {
		this.oldVhclBrand = oldVhclBrand;
	}
	public String getOldVhclSeries() {
		return oldVhclSeries;
	}
	public void setOldVhclSeries(String oldVhclSeries) {
		this.oldVhclSeries = oldVhclSeries;
	}
	public String getOldVhclModel() {
		return oldVhclModel;
	}
	public void setOldVhclModel(String oldVhclModel) {
		this.oldVhclModel = oldVhclModel;
	}
	public String getOldVin() {
		return oldVin;
	}
	public void setOldVin(String oldVin) {
		this.oldVin = oldVin;
	}
	public Integer getReplaceIntentCount() {
		return replaceIntentCount;
	}
	public void setReplaceIntentCount(Integer replaceIntentCount) {
		this.replaceIntentCount = replaceIntentCount;
	}
	public String getNewVhclInvoicePic() {
		return newVhclInvoicePic;
	}
	public void setNewVhclInvoicePic(String newVhclInvoicePic) {
		this.newVhclInvoicePic = newVhclInvoicePic;
	}
	public String getOldVhclTravelCardPic() {
		return oldVhclTravelCardPic;
	}
	public void setOldVhclTravelCardPic(String oldVhclTravelCardPic) {
		this.oldVhclTravelCardPic = oldVhclTravelCardPic;
	}
	public String getOldVhclTransferMembershipPic() {
		return oldVhclTransferMembershipPic;
	}
	public void setOldVhclTransferMembershipPic(String oldVhclTransferMembershipPic) {
		this.oldVhclTransferMembershipPic = oldVhclTransferMembershipPic;
	}
	public String getCardPic() {
		return cardPic;
	}
	public void setCardPic(String cardPic) {
		this.cardPic = cardPic;
	}
	public String getOldVhclCertificatesOfTitlePic() {
		return oldVhclCertificatesOfTitlePic;
	}
	public void setOldVhclCertificatesOfTitlePic(String oldVhclCertificatesOfTitlePic) {
		this.oldVhclCertificatesOfTitlePic = oldVhclCertificatesOfTitlePic;
	}
	public String getMarriageCertificatesPic() {
		return marriageCertificatesPic;
	}
	public void setMarriageCertificatesPic(String marriageCertificatesPic) {
		this.marriageCertificatesPic = marriageCertificatesPic;
	}
	public String getFinaPic() {
		return finaPic;
	}
	public void setFinaPic(String finaPic) {
		this.finaPic = finaPic;
	}
	public String getOtherPic() {
		return otherPic;
	}
	public void setOtherPic(String otherPic) {
		this.otherPic = otherPic;
	}
	public String getNewVhclInvoicePicId() {
		return newVhclInvoicePicId;
	}
	public void setNewVhclInvoicePicId(String newVhclInvoicePicId) {
		this.newVhclInvoicePicId = newVhclInvoicePicId;
	}
	public String getOldVhclTravelCardPicId() {
		return oldVhclTravelCardPicId;
	}
	public void setOldVhclTravelCardPicId(String oldVhclTravelCardPicId) {
		this.oldVhclTravelCardPicId = oldVhclTravelCardPicId;
	}
	public String getOldVhclTransferMembershipPicId() {
		return oldVhclTransferMembershipPicId;
	}
	public void setOldVhclTransferMembershipPicId(String oldVhclTransferMembershipPicId) {
		this.oldVhclTransferMembershipPicId = oldVhclTransferMembershipPicId;
	}
	public String getCardPicId() {
		return cardPicId;
	}
	public void setCardPicId(String cardPicId) {
		this.cardPicId = cardPicId;
	}
	public String getOldVhclCertificatesOfTitlePicId() {
		return oldVhclCertificatesOfTitlePicId;
	}
	public void setOldVhclCertificatesOfTitlePicId(String oldVhclCertificatesOfTitlePicId) {
		this.oldVhclCertificatesOfTitlePicId = oldVhclCertificatesOfTitlePicId;
	}
	public String getMarriageCertificatesPicId() {
		return marriageCertificatesPicId;
	}
	public void setMarriageCertificatesPicId(String marriageCertificatesPicId) {
		this.marriageCertificatesPicId = marriageCertificatesPicId;
	}
	public String getFinaPicId() {
		return finaPicId;
	}
	public void setFinaPicId(String finaPicId) {
		this.finaPicId = finaPicId;
	}
	public String getOtherPicId() {
		return otherPicId;
	}
	public void setOtherPicId(String otherPicId) {
		this.otherPicId = otherPicId;
	}

}
