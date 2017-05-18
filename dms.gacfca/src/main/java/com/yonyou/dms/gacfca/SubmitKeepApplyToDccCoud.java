package com.yonyou.dms.gacfca;

import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 客户保留申请的审批   之后上报
 * @author Benzc
 * @date 2017年1月10日
 */
public interface SubmitKeepApplyToDccCoud {
	
	public int performExecute(String customerNo, String auditResult)throws ServiceBizException;

}
