package com.yonyou.dms.vehicle.service.afterSales.warranty;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.warranty.TtWrOptOldptDTO;

/**
 * 操作代码与主因件维护
 * @author xuqinqin
 *
 */
public interface OperateAndMainPartService {
	//操作代码与主因件维护查询
	public PageInfoDto optAndMpartQuery(Map<String, String> queryParam);
	
	//操作代码与主因件维护下载
	public void optAndMpartDownload( Map<String, String> queryParam, HttpServletRequest request,HttpServletResponse response) throws ServiceBizException;
	
	//操作代码与主因件维护新增
	public Long addOptAndMpart(TtWrOptOldptDTO ptdto) ;
	
	//更新操作代码与主因件(回显)
	Map editOptAndMpart(Long Id) throws ParseException;
	
	//更新操作代码与主因件(保存)
	public void saveOptAndMpart(TtWrOptOldptDTO dto, BigDecimal id,LoginInfoDto loginUser)throws  ServiceBizException;
	
}
