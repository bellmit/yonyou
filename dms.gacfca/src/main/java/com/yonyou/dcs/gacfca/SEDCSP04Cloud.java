package com.yonyou.dcs.gacfca;

/**
 * @Description:配件订单推送SAP (DCS -> SAP)
 * @author xuqinqin 
 */
public interface SEDCSP04Cloud  extends BaseCloud{
	
	public String sendDateSAP() throws Exception;
	
}
