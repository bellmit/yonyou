package com.yonyou.dms.vehicle.service.vehicleAllot;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.dao.vehicleAllotDao.DealerAllotQueryDao;

@Service
public class DealerAllotQueryServiceImpl implements DealerAllotQueryService {
	
	@Autowired
	private DealerAllotQueryDao dealerDao;

	@Override
	public PageInfoDto search(Map<String, String> param) {
		PageInfoDto dto = dealerDao.search(param);
		return dto;
	}

}
