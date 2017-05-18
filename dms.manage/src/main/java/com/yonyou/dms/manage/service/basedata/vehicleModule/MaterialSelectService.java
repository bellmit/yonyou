package com.yonyou.dms.manage.service.basedata.vehicleModule;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.function.exception.ServiceBizException;

public interface MaterialSelectService {

	public List<Map> getBrandList(Integer type) throws ServiceBizException;

	public List<Map> getSeriesList(Integer type, String brand) throws ServiceBizException;

	public List<Map> getGroupList(Integer type, String series) throws ServiceBizException;

	public List<Map> getModelYearList(Integer type, String group) throws ServiceBizException;

	public List<Map> getColorList(Integer type, String group, String modelYear) throws ServiceBizException;

	public List<Map> getTrimList(Integer type,String group, String modelYear,
			String color) throws ServiceBizException;

}
