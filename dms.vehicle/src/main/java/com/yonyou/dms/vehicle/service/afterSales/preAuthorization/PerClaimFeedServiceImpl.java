package com.yonyou.dms.vehicle.service.afterSales.preAuthorization;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.dao.afterSales.preAuthorization.PerClaimFeedDao;

/**
 * 索赔预授权反馈
 * @author Administrator
 *
 */
@Service
public class PerClaimFeedServiceImpl implements PerClaimFeedService{
	@Autowired 
	PerClaimFeedDao  perClaimFeedDao;
	 // 索陪预授权反馈信息查询
	@Override
	public PageInfoDto PerClaimFeedQuery(Map<String, String> queryParam) {
		// TODO Auto-generated method stub
		return perClaimFeedDao.PerClaimFeedQuery(queryParam);
	}
	

}
