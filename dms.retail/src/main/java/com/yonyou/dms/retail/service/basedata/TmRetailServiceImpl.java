package com.yonyou.dms.retail.service.basedata;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.retail.dao.basedata.TmRetailDiscountImportDao;
import com.yonyou.dms.retail.domains.DTO.basedata.TmRetailDiscountOfferDTO;
@Service
public class TmRetailServiceImpl implements TmRetailService{
	@Autowired
	TmRetailDiscountImportDao dao;

	@Override
	public PageInfoDto getTmRetaillist(Map<String, String> queryParam) throws Exception {
	
		return dao.getlist(queryParam);
	}
	@SuppressWarnings("rawtypes")
	@Override
	public List<Map> queryEmpInfoforExport(Map<String, String> queryParam) throws Exception {
	
		return dao.queryEmpInfoforExport(queryParam);
	}
	/**
	 * 根据ID查询
	 */
	@Override
	public Map<String, Object> findById(Long id) throws Exception {
		return dao.findById(id);
	}
	/**
	 * 修改
	 */
	@Override
	public void modifyBanking(TmRetailDiscountOfferDTO tcDto, LoginInfoDto loginInfo) throws Exception {
		dao.doSave(tcDto,loginInfo);
	}
}
