package com.yonyou.dcs.gacfca;

import com.yonyou.dms.function.exception.ServiceBizException;
/**
 * @Title: SEDCS016Cloud
 * @Description:行管经销商下发 接口（DCS -> DMS）
 * 总部下发经销商是否有行管标签及它牌车维修控制标签 
 * @author xuqinqin 
 */
public interface SEDCS016Cloud {
	
	public String doSend() throws ServiceBizException;
	
}
