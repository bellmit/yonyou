package com.yonyou.dms.vehicle.service.oldPart;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.domains.DTO.ImportResultDto;
import com.yonyou.dms.vehicle.dao.oldPart.GcsBaoDanManageDao;
import com.yonyou.dms.vehicle.domains.DTO.oldPart.TmpGcsImpDTO;
import com.yonyou.dms.vehicle.domains.DTO.oldPart.TtOpGcsImpDTO;

@Service
public class GcsBaoDanManageServiceImpl implements GcsBaoDanManageService {
	@Autowired
	GcsBaoDanManageDao dao;

	@Override
	public void insertTmpVsProImpAudit(TmpGcsImpDTO rowDto) {
		
		dao.insertTmpVsYearlyPlan(rowDto);
		
	}

	@Override
	public ImportResultDto<TmpGcsImpDTO> checkData(TmpGcsImpDTO rowDto) throws Exception {
	
		return dao.checkDataVIN(rowDto);
	}

	@Override
	public List<Map> oemSelectTmpYearPlan(Map<String, String> queryParam) {
		
		return dao.selectTemp();
	}

	@Override
	public List<Map> mappingOldPart(String string, String claimNumber, String string2) {
	
		return dao.mappingOldPart(string, claimNumber, string2);
	}

	@Override
	public void setImpPO(TtOpGcsImpDTO po) {
		
		dao.setImpPO(po);
		
	}

	@Override
	public List<Map> queryEmpInfoforExport(Map<String, String> queryParam) throws Exception {
		// TODO Auto-generated method stub
		return dao.queryEmpInfoforExport(queryParam);
	}

	@Override
	public PageInfoDto findGcs(Map<String, String> queryParam) {
		// TODO Auto-generated method stub
		return dao.findGcs(queryParam);
	}
}
