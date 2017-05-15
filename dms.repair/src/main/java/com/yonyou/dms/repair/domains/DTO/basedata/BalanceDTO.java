package com.yonyou.dms.repair.domains.DTO.basedata;

import java.util.List;
import java.util.Map;

/**
 * 费用结算
 * @author wangxin
 *
 */
@SuppressWarnings("rawtypes")
public class BalanceDTO {
	// 接收结算单表的参数
	private String  contractNo;//CONTRACT_NO   合约编号
	private String contractCard;//CONTRACT_CARD   合同证书
	private String labourAmount;//LABOUR_AMOUNT  工时费
	private String derateAmount;//DERATE_AMOUNT  减免金额
	private String salesPartAmount;//SALES_PART_AMOUNT  销售材料费
	private String addItemAmount;//ADD_ITEM_AMOUNT  附加项目费
	private String payTypeCode;//PAY_TYPE_CODE  付款方式代码
	private String productionValue;//PRODUCTION_VALUE   产值
	private String balanceModeCode;//BALANCE_MODE_CODE   结算模式代码
	private String overItemAmount;//OVER_ITEM_AMOUNT  辅料管理费
	private String receiveAmount;//RECEIVE_AMOUNT  收款金额
	private String repairPartAmount;//REPAIR_PART_AMOUNT  维修材料费
	private String tax;//TAX  税率
	private String taxAmountBalance;//TAX_AMOUNT   税额
	private String netAmountBalance;//NET_AMOUNT    不含税金额
	private String invoiceNo;//INVOICE_NO  发票编号
	private String salesPartNo;//SALES_PART_NO   配件销售单号
	private String labourCost;//LABOUR_COST  人工成本
	private String invoiceTypeCode;//INVOICE_TYPE_CODE  发票类型代码
	private String salesPartCost;//SALES_PART_COST  销售材料成本
	private String discountModeCode;//DISCOUNT_MODE_CODE  优惠模式代码
	private String repairPartCost;//REPAIR_PART_COST  维修材料成本
	private String roNo;//RO_NO  工单号
	private String totalAmount;// TOTAL_AMOUNT  总金额
	private String vin;
	private String subObbAmount;//SUB_OBB_AMOUNT 去零金额
	private String cardsAmount;//CARDS_AMOUNT 积分金额
	private String giftAmount;//GIFT_AMOUNT  礼券抵扣金额
	private String sumAmount;//SUM_AMOUNT  汇总金额
	private String payOff;//PAY_OFF  是否结清
	private String memberNo;//MEMBER_NO  会员编号
	private String arrBalance;//ARR_BALANCE  是否欠款结算
	private String insurationCode;//INSURATION_CODE  保险公司代码
	private String insurationNo;//INSURATION_NO  理赔单号
	private String accId;// ACC_ID  电子礼券账户
	private String remarkBalance;//REMARK  备注
	private String remark1Balance;//REMARK1 备注1
	
	private String sBALANCE_TIME;//BALANCE_TIME  结算时间
	private String sSQUARE_DATE;//SQUARE_DATE  结清日期
	private String sDELIVERY_DATE;//DELIVERY_DATE 交车日期
	
	private List<Map> bLDtoList;//结算单维修项目明细参数
	private List<Map> hiddenList1;//结算单维修项目收费对象参数
	
	private List<Map> bRPDtoList;//结算单维修材料明细参数
	private List<Map> hiddenList2;//结算单维修材料收费对象参数
	
	private List<Map> bSPDtoList;//结算单销售材料明细参数
	
	private List<Map> bAIDtoList;//结算单附加项目明细参数
	private List<Map> hiddenList4;//附加项目收费对象参数

	private List<Map> receivableList;//应收
	private List<Map> receivedList;//实收
	
	private List<Map> hiddenList5;//其他成本
	
	private String cardId;//CARD_ID  卡ID
	private String useCredit;//USE_CREDIT 所用积分
	private String cardTypeCode;//CARD_TYPE_CODE 会员卡类型代码
	private String tId;//T_ID 事务编号
	

	public String getBalanceModeCode() {
		return balanceModeCode;
	}

	public void setBalanceModeCode(String balanceModeCode) {
		this.balanceModeCode = balanceModeCode;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getUseCredit() {
		return useCredit;
	}

	public void setUseCredit(String useCredit) {
		this.useCredit = useCredit;
	}

	public String getCardTypeCode() {
		return cardTypeCode;
	}

	public void setCardTypeCode(String cardTypeCode) {
		this.cardTypeCode = cardTypeCode;
	}

	public String gettId() {
		return tId;
	}

	public void settId(String tId) {
		this.tId = tId;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getContractCard() {
		return contractCard;
	}

	public void setContractCard(String contractCard) {
		this.contractCard = contractCard;
	}

	public String getLabourAmount() {
		return labourAmount;
	}

	public void setLabourAmount(String labourAmount) {
		this.labourAmount = labourAmount;
	}

	public String getDerateAmount() {
		return derateAmount;
	}

	public void setDerateAmount(String derateAmount) {
		this.derateAmount = derateAmount;
	}

	public String getSalesPartAmount() {
		return salesPartAmount;
	}

	public void setSalesPartAmount(String salesPartAmount) {
		this.salesPartAmount = salesPartAmount;
	}

	public String getAddItemAmount() {
		return addItemAmount;
	}

	public void setAddItemAmount(String addItemAmount) {
		this.addItemAmount = addItemAmount;
	}

	public String getPayTypeCode() {
		return payTypeCode;
	}

	public void setPayTypeCode(String payTypeCode) {
		this.payTypeCode = payTypeCode;
	}

	public String getProductionValue() {
		return productionValue;
	}

	public void setProductionValue(String productionValue) {
		this.productionValue = productionValue;
	}

	public String getOverItemAmount() {
		return overItemAmount;
	}

	public void setOverItemAmount(String overItemAmount) {
		this.overItemAmount = overItemAmount;
	}

	public String getReceiveAmount() {
		return receiveAmount;
	}

	public void setReceiveAmount(String receiveAmount) {
		this.receiveAmount = receiveAmount;
	}

	public String getRepairPartAmount() {
		return repairPartAmount;
	}

	public void setRepairPartAmount(String repairPartAmount) {
		this.repairPartAmount = repairPartAmount;
	}

	public String getTax() {
		return tax;
	}

	public void setTax(String tax) {
		this.tax = tax;
	}

	public String getTaxAmountBalance() {
		return taxAmountBalance;
	}

	public void setTaxAmountBalance(String taxAmountBalance) {
		this.taxAmountBalance = taxAmountBalance;
	}

	public String getNetAmountBalance() {
		return netAmountBalance;
	}

	public void setNetAmountBalance(String netAmountBalance) {
		this.netAmountBalance = netAmountBalance;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getSalesPartNo() {
		return salesPartNo;
	}

	public void setSalesPartNo(String salesPartNo) {
		this.salesPartNo = salesPartNo;
	}

	public String getLabourCost() {
		return labourCost;
	}

	public void setLabourCost(String labourCost) {
		this.labourCost = labourCost;
	}

	public String getInvoiceTypeCode() {
		return invoiceTypeCode;
	}

	public void setInvoiceTypeCode(String invoiceTypeCode) {
		this.invoiceTypeCode = invoiceTypeCode;
	}

	public String getSalesPartCost() {
		return salesPartCost;
	}

	public void setSalesPartCost(String salesPartCost) {
		this.salesPartCost = salesPartCost;
	}

	public String getDiscountModeCode() {
		return discountModeCode;
	}

	public void setDiscountModeCode(String discountModeCode) {
		this.discountModeCode = discountModeCode;
	}

	public String getRepairPartCost() {
		return repairPartCost;
	}

	public void setRepairPartCost(String repairPartCost) {
		this.repairPartCost = repairPartCost;
	}

	public String getRoNo() {
		return roNo;
	}

	public void setRoNo(String roNo) {
		this.roNo = roNo;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public String getSubObbAmount() {
		return subObbAmount;
	}

	public void setSubObbAmount(String subObbAmount) {
		this.subObbAmount = subObbAmount;
	}

	public String getCardsAmount() {
		return cardsAmount;
	}

	public void setCardsAmount(String cardsAmount) {
		this.cardsAmount = cardsAmount;
	}

	public String getGiftAmount() {
		return giftAmount;
	}

	public void setGiftAmount(String giftAmount) {
		this.giftAmount = giftAmount;
	}

	public String getSumAmount() {
		return sumAmount;
	}

	public void setSumAmount(String sumAmount) {
		this.sumAmount = sumAmount;
	}

	public String getPayOff() {
		return payOff;
	}

	public void setPayOff(String payOff) {
		this.payOff = payOff;
	}

	public String getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}

	public String getArrBalance() {
		return arrBalance;
	}

	public void setArrBalance(String arrBalance) {
		this.arrBalance = arrBalance;
	}

	public String getInsurationCode() {
		return insurationCode;
	}

	public void setInsurationCode(String insurationCode) {
		this.insurationCode = insurationCode;
	}

	public String getInsurationNo() {
		return insurationNo;
	}

	public void setInsurationNo(String insurationNo) {
		this.insurationNo = insurationNo;
	}

	public String getAccId() {
		return accId;
	}

	public void setAccId(String accId) {
		this.accId = accId;
	}

	public String getRemarkBalance() {
		return remarkBalance;
	}

	public void setRemarkBalance(String remarkBalance) {
		this.remarkBalance = remarkBalance;
	}

	public String getRemark1Balance() {
		return remark1Balance;
	}

	public void setRemark1Balance(String remark1Balance) {
		this.remark1Balance = remark1Balance;
	}

	public String getsBALANCE_TIME() {
		return sBALANCE_TIME;
	}

	public void setsBALANCE_TIME(String sBALANCE_TIME) {
		this.sBALANCE_TIME = sBALANCE_TIME;
	}

	public String getsSQUARE_DATE() {
		return sSQUARE_DATE;
	}

	public void setsSQUARE_DATE(String sSQUARE_DATE) {
		this.sSQUARE_DATE = sSQUARE_DATE;
	}

	public String getsDELIVERY_DATE() {
		return sDELIVERY_DATE;
	}

	public void setsDELIVERY_DATE(String sDELIVERY_DATE) {
		this.sDELIVERY_DATE = sDELIVERY_DATE;
	}

	public List<Map> getbLDtoList() {
		return bLDtoList;
	}

	public void setbLDtoList(List<Map> bLDtoList) {
		this.bLDtoList = bLDtoList;
	}

	public List<Map> getHiddenList1() {
		return hiddenList1;
	}

	public void setHiddenList1(List<Map> hiddenList1) {
		this.hiddenList1 = hiddenList1;
	}

	public List<Map> getbRPDtoList() {
		return bRPDtoList;
	}

	public void setbRPDtoList(List<Map> bRPDtoList) {
		this.bRPDtoList = bRPDtoList;
	}

	public List<Map> getHiddenList2() {
		return hiddenList2;
	}

	public void setHiddenList2(List<Map> hiddenList2) {
		this.hiddenList2 = hiddenList2;
	}

	public List<Map> getbSPDtoList() {
		return bSPDtoList;
	}

	public void setbSPDtoList(List<Map> bSPDtoList) {
		this.bSPDtoList = bSPDtoList;
	}

	public List<Map> getbAIDtoList() {
		return bAIDtoList;
	}

	public void setbAIDtoList(List<Map> bAIDtoList) {
		this.bAIDtoList = bAIDtoList;
	}

	public List<Map> getHiddenList4() {
		return hiddenList4;
	}

	public void setHiddenList4(List<Map> hiddenList4) {
		this.hiddenList4 = hiddenList4;
	}

	public List<Map> getReceivableList() {
		return receivableList;
	}

	public void setReceivableList(List<Map> receivableList) {
		this.receivableList = receivableList;
	}

	public List<Map> getReceivedList() {
		return receivedList;
	}

	public void setReceivedList(List<Map> receivedList) {
		this.receivedList = receivedList;
	}

	public List<Map> getHiddenList5() {
		return hiddenList5;
	}

	public void setHiddenList5(List<Map> hiddenList5) {
		this.hiddenList5 = hiddenList5;
	}
	
	
	
	
}
