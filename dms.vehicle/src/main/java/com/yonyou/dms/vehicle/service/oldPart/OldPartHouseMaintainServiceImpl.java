package com.yonyou.dms.vehicle.service.oldPart;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.dao.oldPart.OldPartHouseMaintainDao;
import com.yonyou.dms.vehicle.domains.DTO.oldPart.TmOldpartStorDTO;

@Service
public class OldPartHouseMaintainServiceImpl implements OldPartHouseMaintainService{

	@Autowired
	OldPartHouseMaintainDao dao;
	@Override
	public PageInfoDto getQuerySql(Map<String, String> queryParam) {
		return dao.findOldPartHouse(queryParam);
	}

	@Override
	public Long addTmOldpartStor(TmOldpartStorDTO tcDto) {
		return dao.addnoldPart(tcDto);
	}

	@Override
	public void modifyTmOldpartStor(Long id, TmOldpartStorDTO tcDto) {
		dao.modifyoldParth(id, tcDto);
	}

}
