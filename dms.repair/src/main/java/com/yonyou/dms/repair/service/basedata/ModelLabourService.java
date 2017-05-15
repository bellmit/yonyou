package com.yonyou.dms.repair.service.basedata;

import java.util.Map;

import com.yonyou.dms.common.domains.PO.basedata.TmModelLabourPO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;


public interface  ModelLabourService {

	PageInfoDto QueryModelLabour(Map<String, String> queryParam)throws ServiceBizException;

	void addCarTypes(Map<String, String> map)  throws ServiceBizException;

	void updateCarType(Map<String, String> map)throws ServiceBizException;
	
	public TmModelLabourPO findByCode(String id) throws ServiceBizException;
}
