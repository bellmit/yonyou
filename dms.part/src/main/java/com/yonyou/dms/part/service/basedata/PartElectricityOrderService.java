package com.yonyou.dms.part.service.basedata;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
* 电商订单确认service
* @author ZhaoZ
* @date 2017年4月10日
*/
public interface PartElectricityOrderService {

	//订单查询
	public PageInfoDto queryECOrderInfo(Map<String, String> queryParams)throws  ServiceBizException;
	//导出查询
	public List<Map> queryDownLoad(Map<String, String> queryParams)throws  ServiceBizException;
	//配件订单回显信息
	public Map<String, Object> findDealerInfoByOrderId(BigDecimal id)throws  ServiceBizException;
	//配件信息查询
	public PageInfoDto queryPartInfo(BigDecimal id)throws  ServiceBizException;
	//审核历史
	public PageInfoDto checkHidtoryInfo(BigDecimal id)throws  ServiceBizException;
	//确认状态
	public void confirmOrder(BigDecimal id)throws  ServiceBizException;
	//配件订单历史记录
	public void insertPtEcOrderHistory(String no, String operatMessage, String reamrk)throws  ServiceBizException;

	
}
