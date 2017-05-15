package com.yonyou.dms.vehicle.service.orgright;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.dao.orgright.SysFunctionListDao;

@Service
public class SysFunctionListServiceImpl implements SysFunctionListService {
	
	@Autowired
	SysFunctionListDao dao;

	@Override
	public PageInfoDto queryList(Map<String, String> queryParam, LoginInfoDto loginInfo) throws ServiceBizException {
		PageInfoDto pgInfo = dao.queryList(queryParam,loginInfo);
		return pgInfo;
	}

}
