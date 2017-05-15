package com.yonyou.dms.vehicle.service.threePack;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.dao.threePack.TtThreepackWarnParaDao;
import com.yonyou.dms.vehicle.domains.DTO.threePack.TtThreepackWarnParaDTO;

@Service
public class TtThreepackWarnParaServiceImpl implements TtThreepackWarnParaService{
	@Autowired
	TtThreepackWarnParaDao dao;

	@Override
	public PageInfoDto findthreePack(Map<String, String> queryParam) throws Exception {
		return dao.findthreePack(queryParam);
	}

	@Override
	public void modifyMinclass(Long id, TtThreepackWarnParaDTO tcdto) throws ServiceBizException {
		dao.modifyMinclass(id, tcdto);
		
	}

	@Override
	public long addMinclass(TtThreepackWarnParaDTO tcdto) throws ServiceBizException {
		return dao.addMinclass(tcdto);
	}


	@Override
	public List<Map> findById(Long id) {
		
		return dao.findById(id);
	}

	@Override
	public void deleteById(Long id) {
		dao.deleteChargeById(id);
		
	}
	

}
