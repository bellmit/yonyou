package com.yonyou.dms.vehicle.service.oldPart;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.dao.oldPart.OldPartCountDao;
import com.yonyou.dms.vehicle.domains.DTO.oldPart.TtOpOldpartDTO;

@Service
public class OldPartCountServiceImpl implements OldPartCountService{

	@Autowired
	OldPartCountDao dao;

	@Override
	public PageInfoDto findCount(Map<String, String> queryParam) {
		
		return dao.findCount(queryParam);
	}

	@Override
	public List<Map> findreturnAddr(Map<String, String> queryParam) {
		
		return dao.getReturnAddr(queryParam);
	}

	@Override
	public PageInfoDto findCountById(Long id) {
		
		return dao.findCountmx(id);
	}



	@Override
	public void modifyTmOldpartStor(Long id, TtOpOldpartDTO tcDto) {
		dao.modifyoldParth(id, tcDto);
		
	}
}
