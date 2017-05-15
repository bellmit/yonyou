package com.yonyou.dms.part.service.basedata;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.part.dao.PartAllotAaDao;

/**
* 配件调拨单查询service
* @author ZhaoZ
* @date 2017年3月30日
*/
@Service
public class PartAccountServiceImpl implements PartAccountService{

	@Autowired
	private  PartAllotAaDao partDao;

	/**
	 * 配件往来账查询
	 */
	@Override
	public PageInfoDto queryCurrentList(Map<String, String> queryParams) throws ServiceBizException {
		// TODO Auto-generated method stub
		return new PageInfoDto();
	}
	/**
	 * 配件往来账查询
	 */
	@Override
	public PageInfoDto getCurrentList(Map<String, String> queryParams) throws ServiceBizException {
		// TODO Auto-generated method stub
		return new PageInfoDto();
	}
	/**
	 * 配件返利发放查询
	 */
	@Override
	public PageInfoDto queryRebateList(Map<String, String> queryParams) throws ServiceBizException {
		// TODO Auto-generated method stub
		return new PageInfoDto();
	}
	/**
	 * 配件返利使用查询
	 */
	@Override
	public PageInfoDto queryRebateUseList(Map<String, String> queryParams) throws ServiceBizException {
		// TODO Auto-generated method stub
		return new PageInfoDto();
	}
	
}
