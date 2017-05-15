package com.yonyou.dms.vehicle.service.oldPart;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.domains.DTO.ImportResultDto;
import com.yonyou.dms.vehicle.dao.oldPart.OldPartRuleMaintainDao;
import com.yonyou.dms.vehicle.domains.DTO.oldPart.TmOldpartRuleDTO;
import com.yonyou.dms.vehicle.domains.DTO.oldPart.TmpOldpartImpDTO;
import com.yonyou.dms.vehicle.domains.DTO.oldPart.TmpUrgencyVinImpDTO;

@Service
public class OldPartRuleMaintainServiceImpl implements OldPartRuleMaintainService {
	@Autowired
	OldPartRuleMaintainDao dao;

	@Override
	public PageInfoDto getQuerySql(Map<String, String> queryParam) {
	
		return dao.findOldPartHouse(queryParam);
	}

	@Override
	public Long addTmOldpartRule(TmOldpartRuleDTO tcDto) {
		
		return dao.addnoldPart(tcDto);
	}

	@Override
	public void modifyTmOldpartRule(Long id, TmOldpartRuleDTO tcDto) throws Exception {
		dao.modifyoldParth(id, tcDto);
		
	}

	@Override
	public void insertTmpVsYearlyPlan(TmpOldpartImpDTO rowDto) {
		dao.insertTmpVsYearlyPlan(rowDto);
		
	}

	@Override
	public ImportResultDto<TmpOldpartImpDTO> checkData(TmpOldpartImpDTO rowDto) throws Exception {
		
		return dao.checkData(rowDto);
	}

	@Override
	public void save(Map<String, String> queryParam) {
	 dao.save(queryParam);
		
	}

	@Override
	public void insertvin(TmpUrgencyVinImpDTO rowDto) {
		dao.insertVIN(rowDto);
		
	}

	@Override
	public ImportResultDto<TmpUrgencyVinImpDTO> checkDatavin(TmpUrgencyVinImpDTO rowDto) throws Exception {
		
		return dao.checkDataVIN(rowDto);
	}

	@Override
	public void savevin(Map<String, String> queryParam) {
		dao.saveVIN(queryParam);
	}

	@Override
	public List<Map> oemSelect(Map<String, String> queryParam) {
		
		return dao.oemSelect(queryParam);
	}

	@Override
	public List<Map> oemSelectTmpYearPlan(Map<String, String> queryParam) {
		
		return dao.oemSelectvin(queryParam);
	}

	@Override
	public void deleteVinById(Long id) {
		dao.deleteVinById(id);
		
	}
	@Override
	public void deleteChargeById(Long id){
		dao.deleteChargeById(id);
	}
}
