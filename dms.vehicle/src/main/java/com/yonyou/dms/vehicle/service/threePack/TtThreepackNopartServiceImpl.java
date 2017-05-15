package com.yonyou.dms.vehicle.service.threePack;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.domains.DTO.ImportResultDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.dao.threePack.TtThreepackNopartDao;
import com.yonyou.dms.vehicle.domains.DTO.threePack.TtThreepackNopartDTO;

@Service
public class TtThreepackNopartServiceImpl implements TtThreepackNopartService{
	@Autowired
	TtThreepackNopartDao dao;

	@Override
	public PageInfoDto findthreePack(Map<String, String> queryParam) throws Exception {
		return dao.findthreePack(queryParam);
	}

	@Override
	public void modifyNopart(Long id, TtThreepackNopartDTO tcdto) throws ServiceBizException {
		dao.modifyMinclass(id, tcdto);
		
	}

	@Override
	public long addNopart(TtThreepackNopartDTO tcdto) throws ServiceBizException {
		return dao.addMinclass(tcdto);
	}

	@Override
	public void deleteById(Long id) throws ServiceBizException {
		dao.deleteById(id);
		
	}

	@Override
	public void insert(TtThreepackNopartDTO rowDto) {
		dao.insert(rowDto);
		
	}

	@Override
	public ImportResultDto<TtThreepackNopartDTO> checkData(TtThreepackNopartDTO rowDto) throws Exception {
		return dao.checkData(rowDto);
	}
	
}
