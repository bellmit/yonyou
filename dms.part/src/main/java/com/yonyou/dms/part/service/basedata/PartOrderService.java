package com.yonyou.dms.part.service.basedata;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.part.domains.DTO.basedata.TtPtOrderDcsDTO;
/**
* 配件订单管理service
* @author ZhaoZ
* @date 2017年3月22日
*/
public interface PartOrderService {

	//配件订单审核查询
	public PageInfoDto checkOrderPartInfo(Map<String, String> queryParams)throws  ServiceBizException;
	// 配件订单审核回显信息
	public Map<String, Object> findDealerInfoByOrderId(BigDecimal id)throws  ServiceBizException;
	//审核历史
	public PageInfoDto checkHidtoryInfo(BigDecimal id)throws  ServiceBizException;
	//审核
	public void checkAgreeService(Long id,String type,TtPtOrderDcsDTO dto)throws  ServiceBizException;
	//信息导出
	public List<Map> queryDownLoad(Map<String, String> queryParams)throws  ServiceBizException;
	//查询经销商
	public PageInfoDto getDealerList(Map<String, String> queryParams)throws  ServiceBizException;
	//配件订单查询
	public PageInfoDto getOrderInfo(Map<String, String> queryParams)throws  ServiceBizException;
	//配件订单异常监控查询
	public PageInfoDto orderInterMonitorQuary(Map<String, String> queryParams)throws  ServiceBizException;
	//重置
	public void reset(BigDecimal id)throws  ServiceBizException;
	//发票信息查询
	public PageInfoDto queryInvoices(Map<String, String> queryParams)throws  ServiceBizException;
	//配件订单查询dlr
	public PageInfoDto dlrQueryOrderInfo(Map<String, String> queryParams)throws  ServiceBizException;
	//配件订单查询下载
	public List<Map> queryOrderDownLoad(Map<String, String> queryParams)throws  ServiceBizException;
	

}
