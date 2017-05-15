package com.yonyou.dms.vehicle.service.afterSales.warranty;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 主因件维护
 * @author xuqinqin
 *
 */
public interface WarrantyMainPartService {
	//主因件维护查询
	public PageInfoDto mainPartQuery(Map<String, String> queryParam);
	
	//主因件维护下载
	public void mainPartDownload( Map<String, String> queryParam, HttpServletRequest request,HttpServletResponse response) throws ServiceBizException;
	
	
}
