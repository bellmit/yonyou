package com.yonyou.dms.vehicle.service.threePack;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.domains.DTO.ImportResultDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.dao.threePack.TtThreepackPtitemRelationDao;
import com.yonyou.dms.vehicle.domains.DTO.threePack.TtThreepackPtitemRelationDTO;
@Service
public class TtThreepackPtitemRelationServiceImpl implements TtThreepackPtitemRelationService{

	
	@Autowired
	TtThreepackPtitemRelationDao dao;
	
	@Override
	public PageInfoDto findthreePack(Map<String, String> queryParam) throws Exception {
		
		return dao.findthreePack(queryParam);
	}

	@Override
	public void modifyMinclass(Long id, TtThreepackPtitemRelationDTO tcdto) throws ServiceBizException {
		dao.modifyMinclass(id, tcdto);
		
	}

	@Override
	public long addMinclass(TtThreepackPtitemRelationDTO tcdto) throws ServiceBizException {
		
		return dao.addMinclass(tcdto);
	}

	@Override
	public void deleteChargeById(Long id) throws ServiceBizException {
		dao.deleteById(id);
		
	}

	@Override
	public List<Map> selectItem(Map<String, String> params) {
		
		return dao.selectItem(params);
	}

	@Override
	public void insert(TtThreepackPtitemRelationDTO rowDto) {
		dao.insert(rowDto);
		
	}

	@Override
	public ImportResultDto<TtThreepackPtitemRelationDTO> checkData(TtThreepackPtitemRelationDTO rowDto)
			throws Exception {
		
		return dao.checkData(rowDto);
	}

	@Override
	public List<Map> findById(Long id) {
		
		return dao.findById(id);
	}

	@Override
	public List<Map> selectItemMin(String itemNo) {
		
		return dao.selectItemMin(itemNo);
	}

	@Override
	public List<Map> findAllRelation(Map<String, String> queryParam) {
		
		return dao.findAllRelation(queryParam);
	}

	@Override
	public List<Map> selectmMinName(String minclassNo) {
		
		return dao.selectMinName(minclassNo);
	}

	@Override
	public List<Map> selectitemName(String itemNo) {
		
		return dao.selectItemName(itemNo);
	}

	@Override
	public List<Map> selectpartName(String partCode) {
		
		return dao.selectPartName(partCode);
	}

}
