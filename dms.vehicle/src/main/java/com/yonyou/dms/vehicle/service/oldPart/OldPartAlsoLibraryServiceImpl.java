package com.yonyou.dms.vehicle.service.oldPart;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.dao.oldPart.OldPartAlsoLibraryDao;
import com.yonyou.dms.vehicle.domains.DTO.oldPart.TtOpStoreDTO;

@Service
public class OldPartAlsoLibraryServiceImpl implements OldPartAlsoLibraryService {
	@Autowired
	OldPartAlsoLibraryDao dao;

	@Override
	public PageInfoDto findOutStorage(Map<String, String> queryParam) {
		
		return dao.findGcs(queryParam);
	}

	@Override
	public PageInfoDto findOutById(Long id) throws Exception {
		
		return dao.outSotrageApproval(id);
	}

	@Override
	public void modifyTmOldpartStor(Long id, TtOpStoreDTO tcDto) {
		dao.modifyoldParth(id, tcDto);
		
	}
}
