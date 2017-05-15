package com.yonyou.dms.vehicle.service.afterSales.preAuthorization;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.dao.afterSales.preAuthorization.PreclaimPreQueryOemDao;

/**
 * 索赔预授权状态查询
 * @author Administrator
 *
 */
@Service
public class PreclaimPreQueryOemServiceImpl implements PreclaimPreQueryOemService{
	@Autowired 
	PreclaimPreQueryOemDao  preclaimPreQueryOemDao;

	/**
	 * 索赔预授权状态查询
	 */
	@Override
	public PageInfoDto PreclaimPreQueryOemQuery(Map<String, String> queryParam) {
		// TODO Auto-generated method stub
		return preclaimPreQueryOemDao.PreclaimPreQueryOemQuery(queryParam);
	}
	
}
