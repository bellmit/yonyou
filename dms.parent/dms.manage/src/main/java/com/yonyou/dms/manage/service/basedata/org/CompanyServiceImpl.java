package com.yonyou.dms.manage.service.basedata.org;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.manage.dao.organization.CompanyDao;

@Service
public class CompanyServiceImpl implements CompanyService {
	
	@Autowired
	private CompanyDao dao;


	@Override
	public PageInfoDto getCompanyList(Map<String, String> queryParams, int i) throws ServiceBizException {
		return dao.getCompanyList(queryParams,i);
	}

}
