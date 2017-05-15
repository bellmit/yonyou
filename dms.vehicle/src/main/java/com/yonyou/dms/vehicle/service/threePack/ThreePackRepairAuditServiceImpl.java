package com.yonyou.dms.vehicle.service.threePack;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.dao.threePack.ThreePackRepairAuditDao;
import com.yonyou.dms.vehicle.domains.DTO.threePack.TtThreepackRepairDTO;
@Service
public class ThreePackRepairAuditServiceImpl implements ThreePackRepairAuditService{

	
	@Autowired
	ThreePackRepairAuditDao dao;
	@Override
	public PageInfoDto threePackLabourList(Long id) {
	
		return dao.threePackLabourList(id);
	}

	@Override
	public Map<String, Object> threePackAuditInfo(Long id) {
		
		return dao.threePackAuditInfo(id);
	}

	@Override
	public PageInfoDto findAudit(Map<String, String> queryParam) {
		
		return dao.findAudit(queryParam);
	}

	@Override
	public PageInfoDto threePackPartList(Long id) {
	
		return dao.threePackPartList(id);
	}

	@Override
	public Map<String ,Object> find(String vin) {

		return dao.find(vin);
	}

	@Override
	public void modifyNopart(Long id, TtThreepackRepairDTO tcDto) {
		dao.modifyMinclass(id, tcDto);
		
	}

}
