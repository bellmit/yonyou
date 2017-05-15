package com.yonyou.dms.part.service.basedata;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.part.domains.DTO.basedata.PartDeliverDTO;

/**
* 交货单service
* @author ZhaoZ
* @date 2017年3月27日
*/
public interface PartDeliverManageService {

	//直发交货单查询
	public PageInfoDto checkOrderPartInfo(Map<String, String> queryParams)throws  ServiceBizException;

	//直发交货单修改回显信息
	public Map<String, Object> findDeliverInfoByDeliverId(BigDecimal id)throws  ServiceBizException;
	//接货清单信息查询
	public PageInfoDto findAcceptInfoByDeliverId(BigDecimal id)throws  ServiceBizException;
	//保存
	public void saveDeliveryOrder(PartDeliverDTO dto, BigDecimal id,LoginInfoDto loginUser)throws  ServiceBizException;
	//交货单明细查询
	public PageInfoDto queryOrderInfo(Map<String, String> queryParams)throws  ServiceBizException;
	//货运单管查詢
	public PageInfoDto queryDeliverInit(Map<String, String> queryParams)throws  ServiceBizException;
	//接货清单信息查询
	public PageInfoDto findDelivertInfoByDeliverId(BigDecimal id)throws  ServiceBizException;
	//货运单导出
	public List<Map> exeDeliverInfo( String deliverId, String code,Map<String, String> queryParams)throws  ServiceBizException;

}
