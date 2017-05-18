package com.yonyou.dms.customer.service.customerManage;

import java.util.Map;

import com.yonyou.dms.customer.domains.DTO.customerManage.TestDriverDTO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

public interface TestDriverService {

	public PageInfoDto queryTestDriver(Map<String, String> queryParam) throws ServiceBizException;

	public Map<String, Object> queryTestDriverByid(String id) throws ServiceBizException;

	public Map<String, Object> queryTestDriverDetailByid(String id) throws ServiceBizException;

	public void updateTestDriver(String id, TestDriverDTO testDriverDTO) throws ServiceBizException;

	public Long addTestDriver(TestDriverDTO testDriverDTO) throws ServiceBizException;

	public PageInfoDto queryCustomerAndIntent(Map<String, String> queryParam) throws ServiceBizException;
}
