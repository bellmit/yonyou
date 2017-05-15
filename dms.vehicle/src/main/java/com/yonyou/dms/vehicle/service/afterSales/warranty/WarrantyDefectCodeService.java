package com.yonyou.dms.vehicle.service.afterSales.warranty;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 缺陷代码维护
 * @author xuqinqin
 *
 */
public interface WarrantyDefectCodeService {
	//缺陷代码维护查询
	public PageInfoDto defectCodeQuery(Map<String, String> queryParam);
	
	//缺陷代码维护下载
	public void defectCodeDownload( Map<String, String> queryParam, HttpServletRequest request,HttpServletResponse response) throws ServiceBizException;
	
	
}
