package com.yonyou.dms.retail.service.basedata;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.retail.dao.basedata.TmRetailDiscountImportSumDao;

@Service
public class TmRetailSumServiceImpl implements TmRetailSumService{
	@Autowired
	TmRetailDiscountImportSumDao dao;

	@Override
	public PageInfoDto getTmRetaillist(Map<String, String> queryParam) throws Exception {
		
		return dao.getlist(queryParam);
	}

	@Override
	public List<Map> queryEmpInfoforExport(Map<String, String> queryParam) throws Exception {
		
		return dao.queryEmpInfoforExport(queryParam);
	}

}
