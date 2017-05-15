package com.yonyou.dms.manage.service.basedata.personInfoManager;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.manage.dao.personInfoManager.DealerSwitchDao;


/**
 * 经销商权限切换
 * @author Administrator
 *
 */
@Service
public class DealerSwitchServiceImpl  implements DealerSwitchService{
	@Autowired
	DealerSwitchDao dealerSwitchDao;
	
	
	
	public PageInfoDto dealerSwitchQuery(Map<String, String> queryParam) {
		return dealerSwitchDao.dealerSwitchQuery(queryParam);
	}
}
