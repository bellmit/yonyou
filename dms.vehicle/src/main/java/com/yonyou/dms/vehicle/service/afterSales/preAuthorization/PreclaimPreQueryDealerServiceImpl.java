package com.yonyou.dms.vehicle.service.afterSales.preAuthorization;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.dao.afterSales.preAuthorization.PreclaimPreQueryDealerDao;

/**
 * 经销商端索赔预授权状态查询
 * @author Administrator
 *
 */
@Service
public class PreclaimPreQueryDealerServiceImpl implements PreclaimPreQueryDealerService{
	@Autowired
	PreclaimPreQueryDealerDao  preclaimPreQueryDealerDao;

	/**
	 * 经销商端索赔预授权状态查询
	 */
	@Override
	public PageInfoDto PreclaimPreQueryDealerQuery(Map<String, String> queryParam) {
		// TODO Auto-generated method stub
		return preclaimPreQueryDealerDao.PreclaimPreQueryDealerQuery(queryParam);
	}

}
