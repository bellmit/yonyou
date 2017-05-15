package com.yonyou.dms.gacfca;

import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 业务描述： 更新客户信息（客户接待信息/需求分析）更新
 * 
 * @author
 *
 */
public interface SOTDCS008 {
	public int getSOTDCS008(String status,String cusNo) throws ServiceBizException;
}
