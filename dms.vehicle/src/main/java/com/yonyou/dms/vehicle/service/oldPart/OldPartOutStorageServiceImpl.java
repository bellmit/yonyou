package com.yonyou.dms.vehicle.service.oldPart;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.dao.oldPart.OldPartOutStorageDao;
import com.yonyou.dms.vehicle.domains.DTO.oldPart.TtOpStoreDTO;

@Service
public class OldPartOutStorageServiceImpl implements OldPartOutStorageService{

	@Autowired
	OldPartOutStorageDao dao;

	@Override
	public PageInfoDto findOutStorage(Map<String, String> queryParam) {
		return dao.findGcs(queryParam);
	}

	@Override
	public PageInfoDto findOutById(Long id) {
		return dao.outSotrageApproval(id);
	}

	@Override
	public List<Map> queryEmpInfoforExport(Map<String, String> queryParam) throws Exception {
		
		return dao.queryEmpInfoforExport(queryParam);
	}

	@Override
	public void modifyTmOldpartStor(Long id, TtOpStoreDTO tcDto) {
		dao.modifyoldParth(id, tcDto);
		
	}
}
