package com.infoeai.eai.wsServer.bsuv.lms;

import java.io.Serializable;
import java.util.Date;


/**
 * （DCS-LMS）零售订单提交、取车、交车推送接口VO
 * @author wangJian
 * date 2016-05-05
 */
public class ResaleOrderDcsToLmsVO implements Serializable  {
	private static final long serialVersionUID = 1L;
	
	private String EC_ORDER_NO;//官网订单号
	private String DEALER_CODE;//经销商代码(DCS5位代码)
	private String BRAND_CODE;//品牌代码 
	private String SERIES_CODE;//车系代码
	private String MODEL_CODE;//车型代码 
	private String GROUP_CODE;//车款代码
	private String TRIM_CODE;//内饰代码
	private String COLOR_CODE;//颜色代码
	private String CUSTOMER_NAME;//客户姓名
	private String TEL;//客户手机
	private String ID_CRAD;//身份证
	private String DEPOSIT_DATE;//下定日期
	private String RETAIL_FINANCE;//零售金融
	private Float DEPOSIT_AMOUNT;//定金金额
	private String REVOKE_DATE;//取消时间
	private String SUBMIT_DATE;//提交日期
	private String DELIVER_DATE;//交车日期
	private String SALE_DATE;//实销日期
	private Integer ORDER_STATUS;//订单类型(提交的状态："13011015"; //经理审核中 ;"13011020"; //财务审核中;"13011025"; 
								//交车确认中取消的状态："13011055"; //已取消交车确认："13011030"; //已交车确认	实销完成："13011035"; //已完成 )
	private Integer ESC_COMFIRM_TYPE;//官网订单类型(现车 ：16001001 CALL车：16001002)
	
	public String getID_CRAD() {
		return ID_CRAD;
	}
	public void setID_CRAD(String iD_CRAD) {
		ID_CRAD = iD_CRAD;
	}
	public String getEC_ORDER_NO() {
		return EC_ORDER_NO;
	}
	public void setEC_ORDER_NO(String eC_ORDER_NO) {
		EC_ORDER_NO = eC_ORDER_NO;
	}
	public String getDEALER_CODE() {
		return DEALER_CODE;
	}
	public void setDEALER_CODE(String dEALER_CODE) {
		DEALER_CODE = dEALER_CODE;
	}
	public String getBRAND_CODE() {
		return BRAND_CODE;
	}
	public void setBRAND_CODE(String bRAND_CODE) {
		BRAND_CODE = bRAND_CODE;
	}
	public String getSERIES_CODE() {
		return SERIES_CODE;
	}
	public void setSERIES_CODE(String sERIES_CODE) {
		SERIES_CODE = sERIES_CODE;
	}
	public String getMODEL_CODE() {
		return MODEL_CODE;
	}
	public void setMODEL_CODE(String mODEL_CODE) {
		MODEL_CODE = mODEL_CODE;
	}
	public String getGROUP_CODE() {
		return GROUP_CODE;
	}
	public void setGROUP_CODE(String gROUP_CODE) {
		GROUP_CODE = gROUP_CODE;
	}
	public String getTRIM_CODE() {
		return TRIM_CODE;
	}
	public void setTRIM_CODE(String tRIM_CODE) {
		TRIM_CODE = tRIM_CODE;
	}
	public String getCOLOR_CODE() {
		return COLOR_CODE;
	}
	public void setCOLOR_CODE(String cOLOR_CODE) {
		COLOR_CODE = cOLOR_CODE;
	}
	public String getCUSTOMER_NAME() {
		return CUSTOMER_NAME;
	}
	public void setCUSTOMER_NAME(String cUSTOMER_NAME) {
		CUSTOMER_NAME = cUSTOMER_NAME;
	}
	public String getTEL() {
		return TEL;
	}
	public void setTEL(String tEL) {
		TEL = tEL;
	}
	public String getRETAIL_FINANCE() {
		return RETAIL_FINANCE;
	}
	public void setRETAIL_FINANCE(String rETAIL_FINANCE) {
		RETAIL_FINANCE = rETAIL_FINANCE;
	}
	public Float getDEPOSIT_AMOUNT() {
		return DEPOSIT_AMOUNT;
	}
	public void setDEPOSIT_AMOUNT(Float dEPOSIT_AMOUNT) {
		DEPOSIT_AMOUNT = dEPOSIT_AMOUNT;
	}
	public String getSUBMIT_DATE() {
		return SUBMIT_DATE;
	}
	public void setSUBMIT_DATE(String sUBMIT_DATE) {
		SUBMIT_DATE = sUBMIT_DATE;
	}
	public Integer getORDER_STATUS() {
		return ORDER_STATUS;
	}
	public void setORDER_STATUS(Integer oRDER_STATUS) {
		ORDER_STATUS = oRDER_STATUS;
	}
	public Integer getESC_COMFIRM_TYPE() {
		return ESC_COMFIRM_TYPE;
	}
	public void setESC_COMFIRM_TYPE(Integer eSC_COMFIRM_TYPE) {
		ESC_COMFIRM_TYPE = eSC_COMFIRM_TYPE;
	}
	public String getDEPOSIT_DATE() {
		return DEPOSIT_DATE;
	}
	public void setDEPOSIT_DATE(String dEPOSIT_DATE) {
		DEPOSIT_DATE = dEPOSIT_DATE;
	}
	public String getREVOKE_DATE() {
		return REVOKE_DATE;
	}
	public void setREVOKE_DATE(String rEVOKE_DATE) {
		REVOKE_DATE = rEVOKE_DATE;
	}
	public String getDELIVER_DATE() {
		return DELIVER_DATE;
	}
	public void setDELIVER_DATE(String dELIVER_DATE) {
		DELIVER_DATE = dELIVER_DATE;
	}
	public String getSALE_DATE() {
		return SALE_DATE;
	}
	public void setSALE_DATE(String sALE_DATE) {
		SALE_DATE = sALE_DATE;
	}
}
