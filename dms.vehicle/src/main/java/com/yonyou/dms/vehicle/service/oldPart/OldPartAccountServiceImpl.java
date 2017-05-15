package com.yonyou.dms.vehicle.service.oldPart;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.dao.oldPart.OldPartAccountDao;
@Service
public class OldPartAccountServiceImpl implements OldPartAccountService{

	@Autowired
	OldPartAccountDao dao;

	@Override
	public PageInfoDto findOutStorage(Map<String, String> queryParam) {
		// TODO Auto-generated method stub
		return dao.findGcs(queryParam);
	}

	@Override
	public List<Map> queryEmpInfoforExport(Map<String, String> queryParam) throws Exception {
		// TODO Auto-generated method stub
		return dao.queryEmpInfoforExport(queryParam);
	}
}
