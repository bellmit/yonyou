package com.yonyou.dms.vehicle.service.afterSales.basicDataMgr;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.basicDataMgr.TtWrForeapprovallabDTO;

/**
 * 预授权维修项目维护
 * @author Administrator
 *
 */
public interface  LabourMaintainService {
	//预授权维修项目维护
	public PageInfoDto  labourMaintainQuery(Map<String, String> queryParam) ;
	//查询所有车系
	public List<Map> getAllCheXing(Map<String, String> queryParams) throws ServiceBizException;
	//删除预授权维修项目维护
	public void delete(Long id);
	//通过查询工时信息新增
	public List<Map> getAll(Map<String, String> queryParam);
	//预授权维修项目新增
	public Long add(TtWrForeapprovallabDTO ptdto);
}
