package com.yonyou.dms.retail.service.rebate;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.retail.dao.rebate.RebateDetailDao;
@SuppressWarnings("rawtypes")
@Service
public class RebateDetailServiceImpl implements RebateDetailService{
	@Autowired
	RebateDetailDao dao;

	@Override
	public PageInfoDto getRebateDetail(String logId,String dealerCode,Map<String, String> queryParam) {
		
		return dao.findRebateDetail(logId,dealerCode,queryParam);
	}

	@Override
	public List<Map> queryEmpInfoforExport(Map<String, String> queryParam) throws Exception {
		return dao.queryEmpInfoforExport(queryParam);
	}
	
	
	/**
	 * 返利核算明细查询
	 */
	@Override
	public PageInfoDto getRebateDetail1(Map<String, String> queryParam) {
		return dao.findRebateDetail1(queryParam);
	}
	
	/**
	 * 返利核算明细导出
	 */
	@Override
	public List<Map> queryEmpInfoforExport1(Map<String, String> queryParam) throws Exception {
		return dao.queryEmpInfoforExport1(queryParam);
	}
    
	/**
	 * 返利核算汇总查询(DLR)明细
	 */
	@Override
	public PageInfoDto getRebateDetail2(String logId,String dealerCode,Map<String, String> queryParam) {
		
		return dao.findRebateDetail2(logId,dealerCode,queryParam);
	}
}
