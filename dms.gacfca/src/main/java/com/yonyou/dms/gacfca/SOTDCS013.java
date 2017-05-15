package com.yonyou.dms.gacfca;

import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 业务描述：上报：销售人员分配信息DMS更新===对应DMS潜客再分配功能
 * 
 * @author
 *
 */
public interface SOTDCS013 {
	public int getSOTDCS013(String[] cusno) throws ServiceBizException;

}
