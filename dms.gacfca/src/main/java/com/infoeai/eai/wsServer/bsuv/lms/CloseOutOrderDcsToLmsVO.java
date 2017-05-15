package com.infoeai.eai.wsServer.bsuv.lms;

import java.io.Serializable;


/**
 * （DCS-LMS）官网批售订单配车成功状态、发车出库状态、验收入库转太推送接口VO
 * @author wangJian
 * date 2016-05-05
 */
public class CloseOutOrderDcsToLmsVO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String EC_ORDER_NO;//官网订单号
	private Integer ORDER_STATUS;//状态
	private String DATE;//配车成功日期、出库日期、验收入库日期（时间格式YYYY-MM-DD HH24:MI:SS）,
	private String ETA_DATE;//预计到店日期（时间格式YYYY-MM-DD HH24:MI:SS）
	//新增字段 by wangJian 2016-07-05
	private String INSPECTION_RESULT;//入库验收结果 13351001：验收未通过 13351002：验收已通过
	private String PDI_RESULT;//PDI检查结果   70161001：通过 70161002：不通过
	private String DAMAGE_DESC;////质损说明
	

	public String getDAMAGE_DESC() {
		return DAMAGE_DESC;
	}
	public void setDAMAGE_DESC(String dAMAGE_DESC) {
		DAMAGE_DESC = dAMAGE_DESC;
	}
	public String getINSPECTION_RESULT() {
		return INSPECTION_RESULT;
	}
	public void setINSPECTION_RESULT(String iNSPECTION_RESULT) {
		INSPECTION_RESULT = iNSPECTION_RESULT;
	}
	public String getPDI_RESULT() {
		return PDI_RESULT;
	}
	public void setPDI_RESULT(String pDI_RESULT) {
		PDI_RESULT = pDI_RESULT;
	}
	public String getEC_ORDER_NO() {
		return EC_ORDER_NO;
	}
	public void setEC_ORDER_NO(String eC_ORDER_NO) {
		EC_ORDER_NO = eC_ORDER_NO;
	}
	public Integer getORDER_STATUS() {
		return ORDER_STATUS;
	}
	public void setORDER_STATUS(Integer oRDER_STATUS) {
		ORDER_STATUS = oRDER_STATUS;
	}
	public String getDATE() {
		return DATE;
	}
	public void setDATE(String dATE) {
		DATE = dATE;
	}
	public String getETA_DATE() {
		return ETA_DATE;
	}
	public void setETA_DATE(String eTA_DATE) {
		ETA_DATE = eTA_DATE;
	}
}
