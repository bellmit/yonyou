package com.yonyou.dms.vehicle.service.oldPart;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.dao.oldPart.OldPartInventoryDao;
@Service
public class OldPartInventoryServiceImpl implements OldPartInventoryService{

	@Autowired
	OldPartInventoryDao dao;

	@Override
	public PageInfoDto findOutStorage(Map<String, String> queryParam) {
		
		return dao.findCount(queryParam);
	}

	@Override
	public PageInfoDto findOne(String oldpartNo) throws Exception {
		
		return dao.findCount(oldpartNo);
	}

	@Override
	public List<Map> queryEmpInfoforExport(Map<String, String> queryParam) throws Exception {
		
		return dao.queryEmpInfoforExport(queryParam);
	}

	@Override
	public List<Map> queryEmpInfoforExportMX(String oldpartNo) throws Exception {
		// TODO Auto-generated method stub
		return dao.queryEmpInfoforExport(oldpartNo);
	}
}
