package com.yonyou.dms.vehicle.service.threePack;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.dao.threePack.ThreePackWarnLackQueryDao;
@Service
public class ThreePackWarnLackQueryServiceImpl implements ThreePackWarnLackQueryService{
@Autowired
ThreePackWarnLackQueryDao dao;
	@Override
	public PageInfoDto getTmRetaillist(Map<String, String> queryParam) throws Exception {
		// TODO Auto-generated method stub
		return dao.findItem(queryParam);
	}

	@Override
	public List<Map> queryEmpInfoforExport(Map<String, String> queryParam) throws Exception {
		// TODO Auto-generated method stub
		return dao.queryEmpInfoforExport(queryParam);
	}

}
