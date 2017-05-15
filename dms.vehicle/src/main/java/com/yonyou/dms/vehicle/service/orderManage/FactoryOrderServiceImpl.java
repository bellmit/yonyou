package com.yonyou.dms.vehicle.service.orderManage;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.dao.factoryOrderDao.FactoryOrderDao;

@Service
public class FactoryOrderServiceImpl implements FactoryOrderService {
	@Autowired
	private FactoryOrderDao factoryOrderDao;

	@Override
	public PageInfoDto orderQuery(Map<String, String> queryParam) {
		// TODO Auto-generated method stub
		return factoryOrderDao.orderQuery(queryParam);
	}

	@Override
	public List<Map> findFactroyOrderDownload(Map<String, String> queryParam) {

		return factoryOrderDao.findFactroyOrderDownload(queryParam);
	}

}
