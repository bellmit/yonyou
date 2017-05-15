package com.yonyou.dms.customer.service.customerManage;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.customer.domains.DTO.customerManage.BigCustomerOrgApplyDTO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

public interface BigCustomerOrgApplyService {

	PageInfoDto querybigCusApply(Map<String, String> queryParam) throws ServiceBizException;
	
	public void addBigCustomerOrg(BigCustomerOrgApplyDTO bigCustomerOrgApplyDto) throws ServiceBizException;
	
	 List<Map> querySafeToExport(Map<String, String> queryParam) throws ServiceBizException;//使用excel导出

}