package com.yonyou.dms.vehicle.service.threePack;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.dao.threePack.ThreePackRepairAuditQueryDao;

@Service
public class ThreePackRepairAuditQueryServiceImpl implements ThreePackRepairAuditQueryService{

	@Autowired
	ThreePackRepairAuditQueryDao dao;
	@Override
	public PageInfoDto findthreePack(Map<String, String> queryParam) throws Exception {
		
		return dao.findItem(queryParam);
	}
	@Override
	public PageInfoDto queryList(Long id) {
		return dao.threePackPartList(id);
	}
	@Override
	public  PageInfoDto queryInfo(Long id) {
		return dao.threePackAuditInfo(id);
	}
	@Override
	public PageInfoDto queryLabourList(Long id) {
		return dao.threePackLabourList(id);
	}
	@Override
	public List<Map> query(Long id) {
	
		return dao.threePackAudit(id);
	}

}
