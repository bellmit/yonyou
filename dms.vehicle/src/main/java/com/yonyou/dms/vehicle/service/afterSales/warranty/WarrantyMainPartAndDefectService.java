package com.yonyou.dms.vehicle.service.afterSales.warranty;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 主因件与缺陷码维护
 * @author zhanghongyi
 *
 */
public interface WarrantyMainPartAndDefectService {
	//查询
	public PageInfoDto search(Map<String, String> queryParam);
	
	//下载
	public void download( Map<String, String> queryParam, HttpServletRequest request,HttpServletResponse response) throws ServiceBizException;
	
	
}
