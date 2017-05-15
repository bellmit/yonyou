package com.yonyou.dms.vehicle.service.stockLog;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.common.domains.PO.basedata.VsStockLogPO;
import com.yonyou.dms.common.domains.PO.stockmanage.VsInspectionMarPO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;


/**
 * 库存日志接口
 * @author Benzc
 * @date 2017年2月13日
 */
public interface StockLogService {
	
	//车辆日志信息列表分页查询
	public PageInfoDto QueryStockLog(Map<String, String> queryParam);
	
	//车辆信息列表关联分页查询
	public PageInfoDto QueryStockLogVehicle(Map<String, String> queryParam,String id);
	
	//根据ID查询
	public VsStockLogPO getStockLogById(String id) throws ServiceBizException;
	
	//根据VIN查询车辆信息明细
	public List<Map> queryVehicleByVin(Map<String, String> queryParam,String id);
	
	//车辆日志信息列表分页查询
	public PageInfoDto queryInspectionMar(Map<String, String> queryParam,String id);
	
	//根据VIN查询整车质损明细
	public PageInfoDto queryMar(Map<String, String> queryParam,String id);
	
	//根据ID查询质损信息
	public VsInspectionMarPO getInspectionMarById(String itemId) throws ServiceBizException;
	
	//根据ITEM_ID查询质损附件信息
	public PageInfoDto queryMarAttachment(Map<String, String> queryParam,String itemId);

}
