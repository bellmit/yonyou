package com.yonyou.dms.vehicle.service.afterSales.warranty;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.warranty.TtWrPartPriceDTO;
/**
 * 零部件保修价维护
 * @author zhanghongyi 
 *
 */
public interface WarrantyPartPriceService {
	//查询
	public PageInfoDto query(Map<String,String> queryParam) throws ServiceBizException;
	
	//新增
	public Long add(TtWrPartPriceDTO dto)throws  ServiceBizException;
	
	//修改
	public void update(TtWrPartPriceDTO dto)throws  ServiceBizException;
	
	//查询MVS
	public List<Map> getMVS(Map<String, String> queryParams,String seriesCode) throws ServiceBizException;
	
	//下载
	public void download( Map<String, String> queryParam, HttpServletRequest request,HttpServletResponse response) throws ServiceBizException;
}
