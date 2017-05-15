package com.yonyou.dms.repair.service.basedata;

import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

public interface InsuranceTypeService {
	public PageInfoDto queryInsuranceType(Map<String, String> queryParam) throws ServiceBizException;
}
