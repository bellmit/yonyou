package com.yonyou.dms.vehicle.service.oldPart;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.dao.oldPart.OldPartReturnAddrDao;
import com.yonyou.dms.vehicle.domains.DTO.oldPart.TmOldpartReturnaddrDTO;

@Service
public class OldPartReturnAddrServiceImpl implements OldPartReturnAddrService{

	@Autowired
	OldPartReturnAddrDao dao;
	@Override
	public void modifyTmOldpartStor(Long id, TmOldpartReturnaddrDTO tcDto) {
		dao.modifyoldParth(id, tcDto);
		
	}

	@Override
	public Long addTmOldpartStor(TmOldpartReturnaddrDTO tcDto) {
	
		return dao.addnoldPart(tcDto);
	}

	@Override
	public PageInfoDto getQuerySql(Map<String, String> queryParam) {
	
		return dao.findOldPartHouse(queryParam);
	}

	

}
