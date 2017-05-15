package com.yonyou.dms.vehicle.service.afterSales.warranty;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.warranty.TtWrOperateDTO;
/**
 * 特殊操作代码维护
 * @author zhonghongyi 
 *
 */
public interface SpecialOperateService {
	//查询
	public PageInfoDto query(Map<String,String> queryParam) throws ServiceBizException;
	
	//新增
	public void add(TtWrOperateDTO dto)throws  ServiceBizException;
	
	//修改
	public void update(TtWrOperateDTO dto)throws  ServiceBizException;
	
	//下载
	public void download( Map<String, String> queryParam, HttpServletRequest request,HttpServletResponse response) throws ServiceBizException;
}
