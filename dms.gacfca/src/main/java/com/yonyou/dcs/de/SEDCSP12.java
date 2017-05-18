package com.yonyou.dcs.de;

import java.util.List;

import com.infoeai.eai.wsClient.parts.eai.P12ReturnVO;

/**
 * @Description:SAP配件主数据接受 (DCS -> SAP)
 * @author xuqinqin 
 */
public interface SEDCSP12 {
	
	public String dealerDCSDatdaBySap(List<P12ReturnVO> list) throws Exception;
	
}
