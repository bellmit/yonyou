package com.yonyou.dms.gacfca;

import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 业务描述：二手车返利申请上报
 * 
 * @author Benzc
 * @date 2017年1月12日
 *
 */
public interface SADMS016 {
	
	//public LinkedList<UcReplaceRebateApplyDto> getSADMS016(String soNo) throws ServiceBizException;
	public int getSADMS016(String soNo) throws ServiceBizException;

}
