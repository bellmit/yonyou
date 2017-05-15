package com.yonyou.dms.vehicle.service.afterSales.basicDataMgr;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.dao.afterSales.basicDataMgr.ClaimTypeDao;

/**
 * 索赔类型维护
 * @author Administrator
 *
 */
@Service
public class ClaimTypeServiceImpl implements ClaimTypeService{
	@Autowired
	ClaimTypeDao   claimTypeDao;

	/**
	 * 索赔维护类型查询
	 */
	@Override
	public PageInfoDto ClaimTypeQuery(Map<String, String> queryParam) {
		// TODO Auto-generated method stub
		return claimTypeDao.ClaimTypeQuery(queryParam);
	}
	
	

}
