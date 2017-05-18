package com.yonyou.dms.manage.service.basedata.org;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.manage.dao.organization.OemDeptDao;

@Service
public class OemDeptServiceImpl implements OemDeptService {
	
	@Autowired
	private OemDeptDao dao;
	
	/**
	 * 厂端树形结构初始化加载
	 */
	@Override
	public List<Map> getOemDeptTree() throws ServiceBizException {
		return dao.getOemDeptTree();
	}
	
	@Override
	public Map getOrgByCode(String orgCode) throws ServiceBizException {
		return dao.getOrgByCode(orgCode);
	}
	
}
