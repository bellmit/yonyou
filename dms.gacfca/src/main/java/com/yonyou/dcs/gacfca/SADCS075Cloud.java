package com.yonyou.dcs.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.VoucherDTO;
import com.yonyou.dms.function.exception.ServiceBizException;
/**
 * @Title: SADCS074Cloud
 * @Description:保险营销活动下发接口（DCS -> DMS）
 * @author xuqinqin 
 */
public interface SADCS075Cloud {

	public String sendData(String actId) throws ServiceBizException;
	
	public List<VoucherDTO> getSendData(String actId) throws ServiceBizException;
}
