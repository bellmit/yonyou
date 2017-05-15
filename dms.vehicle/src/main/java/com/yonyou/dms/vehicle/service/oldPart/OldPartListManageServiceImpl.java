package com.yonyou.dms.vehicle.service.oldPart;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.dao.oldPart.OldPartListManageDao;

@Service
public class OldPartListManageServiceImpl implements OldPartListManageService{

	@Autowired
	OldPartListManageDao dao;

	@Override
	public PageInfoDto findListManage(Map<String, String> queryParam) {
		
		return dao.findListManage(queryParam);
	}

	@Override
	public List<Map> queryEmpInfoforExport(Map<String, String> queryParam) throws Exception {

		return dao.queryEmpInfoforExport(queryParam);
	}
}
