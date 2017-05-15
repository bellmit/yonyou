package com.yonyou.dms.part.service.basedata;

import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
* 配件订单审核service
* @author ZhaoZ
* @date 2017年4月11日
*/
public interface PartAccountService {

	//配件往来账查询
	public PageInfoDto queryCurrentList(Map<String, String> queryParams)throws  ServiceBizException;
	//配件往来账查询
	public PageInfoDto getCurrentList(Map<String, String> queryParams)throws  ServiceBizException;
	//配件返利发放查询
	public PageInfoDto queryRebateList(Map<String, String> queryParams)throws  ServiceBizException;
	//配件返利使用查询
	public PageInfoDto queryRebateUseList(Map<String, String> queryParams)throws  ServiceBizException;

}
