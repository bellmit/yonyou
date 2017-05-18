package com.yonyou.dms.gacfca;

import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 业务描述：接收展厅销售人员信息
 * 
 * @author
 *
 */
public interface SOTDCS002Coud {

	public int getSOTDCS002(String tiNo,String userid) throws ServiceBizException;
}
