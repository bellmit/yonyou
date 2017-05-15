package com.yonyou.dms.vehicle.service.threePack;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.dao.threePack.ThreePackProjectDao;
import com.yonyou.dms.vehicle.domains.DTO.threePack.TtThreepackItemDTO;
@Service
public class TtThreepackItemServiceImpl implements TtThreepackItemService{
	
	@Autowired
	ThreePackProjectDao dao;

	@Override
	public PageInfoDto findItem(Map<String, String> queryParam) throws Exception {
		
		return dao.findItem(queryParam);
	}

	@Override
	public void modifyItem(Long id, TtThreepackItemDTO tcdto) throws ServiceBizException {

		dao.modifyMinclass(id, tcdto);
	}

}
