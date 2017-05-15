package com.yonyou.dms.vehicle.service.oldPart;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.dao.oldPart.OldPartReturnManageDao;

@Service
public class OldPartReturnManageServiceImpl implements OldPartReturnManageService{

	@Autowired
	OldPartReturnManageDao dao;

	@Override
	public PageInfoDto findReturnManage(Map<String, String> queryParam) {
		
		return dao.findReturnManage(queryParam);
	}

	@Override
	public Map<String,Object> findReturn(String returnBillNo) {
		
		return dao.getReturnBillList(returnBillNo);
	}

	@Override
	public PageInfoDto findOldPartById(String returnBillNo) {
	
		return dao.findOldPartById(returnBillNo);
	}

	@Override
	public void deleteById(String returnBillNo) {
		dao.deleteChargeById(returnBillNo);
		
	}

	@Override
	public void queryDealerPass(Map<String, String> queryParams) {
		dao.queryDealerPass(queryParams);
		
	}
}



