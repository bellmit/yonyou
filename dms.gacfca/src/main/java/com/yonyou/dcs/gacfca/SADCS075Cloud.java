package com.yonyou.dcs.gacfca;

import java.util.List;

import com.infoservice.dms.cgcsl.vo.VoucherVO;
import com.yonyou.dms.function.exception.ServiceBizException;
/**
 * @Title: SADCS075Cloud
 * @Description:保险营销活动下发接口（DCS -> DMS）
 * @author xuqinqin 
 */
public interface SADCS075Cloud  extends BaseCloud{

	public String sendData(String actId) throws ServiceBizException;
	
	public List<VoucherVO> getSendData(String actId) throws ServiceBizException;
}
