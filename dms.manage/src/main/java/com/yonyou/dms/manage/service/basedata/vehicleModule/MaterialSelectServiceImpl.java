package com.yonyou.dms.manage.service.basedata.vehicleModule;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.manage.dao.material.MaterialSelectDao;

@Service
public class MaterialSelectServiceImpl implements MaterialSelectService {
	
	
	@Autowired
	MaterialSelectDao dao;
	
	/**
	 * 获取品牌
	 */
	@Override
	public List<Map> getBrandList(Integer type) throws ServiceBizException {
		return dao.getBrandList(type);
	}

	@Override
	public List<Map> getSeriesList(Integer type, String brand) throws ServiceBizException {
		return dao.getSeriesList(type,brand);
	}

	@Override
	public List<Map> getGroupList(Integer type, String series) throws ServiceBizException {
		return dao.getGroupList(type,series);
	}

	@Override
	public List<Map> getModelYearList(Integer type, String group)
			throws ServiceBizException {
		return dao.getModelYearList(type,group);
	}

	@Override
	public List<Map> getColorList(Integer type, String group, String modelYear)
			throws ServiceBizException {
		return dao.getColorList(type,group,modelYear);
	}

	@Override
	public List<Map> getTrimList(Integer type,String group, String modelYear,
			String color) throws ServiceBizException {
		return dao.getTrimList(type,group,modelYear,color);
	}

}
