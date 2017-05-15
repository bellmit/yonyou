package com.yonyou.dms.vehicle.service.threePack;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.domains.DTO.ImportResultDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.dao.threePack.TtThreepackItemMinclassDao;
import com.yonyou.dms.vehicle.domains.DTO.threePack.TtThreepackItemMinclassDTO;

@Service
public class ThreepackItemMinclassServiceImpl implements ThreepackItemMinclassService{

	@Autowired
	TtThreepackItemMinclassDao dao;
	
	@Override
	public PageInfoDto findthreePack(Map<String, String> queryParam) throws Exception {
		
		return dao.findthreePack(queryParam);
	}

	@Override
	public void modifyMinclass(Long id, TtThreepackItemMinclassDTO tcdto) throws ServiceBizException {
		dao.modifyMinclass(id, tcdto);
		
	}

	@Override
	public long addMinclass(TtThreepackItemMinclassDTO tcdto) throws ServiceBizException {
		
		return dao.addMinclass(tcdto);
	}

	@Override
	public void deleteChargeById(Long id) throws ServiceBizException {
		dao.deleteChargeById(id);
		
	}

	@Override
	public List<Map> selectItem(Map<String, String> params) {
		
		return dao.selectItem(params);
	}

	@Override
	public void insert(TtThreepackItemMinclassDTO rowDto) {
		dao.insert(rowDto);
		
	}

	@Override
	public ImportResultDto<TtThreepackItemMinclassDTO> checkData(TtThreepackItemMinclassDTO rowDto) throws Exception {
		
		return dao.checkData(rowDto);
	}

	@Override
	public List<Map> findById(Long id) {
		
		return dao.findById(id);
	}

}
