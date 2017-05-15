package com.yonyou.dms.gacfca;

import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 销售退回时将消息上报
 * @author wangliang
 * @date 2017年4月19日
 */
public interface SalesReturnUpload {
	
	public int getSalesReturnUpload(String stockInType,String[] vin) throws ServiceBizException;

}
