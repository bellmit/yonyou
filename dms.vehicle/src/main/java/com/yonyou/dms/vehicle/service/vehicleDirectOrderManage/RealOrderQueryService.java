package com.yonyou.dms.vehicle.service.vehicleDirectOrderManage;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 直销车实销查询
 * @author Administrator
 *
 */
public interface RealOrderQueryService {
	//实销查询
	public PageInfoDto realOrderQuery(Map<String, String> queryParam) ;
	//下载
	public void download(Map<String, String> queryParam, HttpServletResponse response, HttpServletRequest request);
	//通过id进行明细查询
	public List<Map> getXiangxi(String orderId)  throws ServiceBizException;
	
}
