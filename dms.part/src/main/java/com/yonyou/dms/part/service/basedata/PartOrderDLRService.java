package com.yonyou.dms.part.service.basedata;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.LazyList;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.part.domains.DTO.basedata.OrderRegisterDTO;
import com.yonyou.dms.part.domains.DTO.basedata.TtPtPartBaseDcsDTO;
import com.yonyou.dms.part.domains.DTO.partOrder.TtPtOrderDetailDcsDTO;
import com.yonyou.dms.part.domains.PO.partBaseInfoManage.TmpPtOrderDetailDcsPO;

/**
* 配件订货计划service
* @author ZhaoZ
* @date 2017年4月5日
*/
public interface PartOrderDLRService {

	//配件订货计划查询信
	public PageInfoDto queryOrderPlanInfo(Map<String, String> queryParams) throws ServiceBizException;
	//删除
	public void delete(BigDecimal id)throws ServiceBizException;
	//查询配件清单
	public PageInfoDto queryOrderList(Map<String, String> queryParams,String partCodes)throws ServiceBizException;
	//配件订单补登记查询
	public PageInfoDto queryInvoiceList(Map<String, String> queryParams)throws ServiceBizException;
	//配件订单补登记 
	public void register(OrderRegisterDTO dto)throws ServiceBizException;
	//导入临时表
	public void insertTmpPtOrderDetailDcs(TtPtOrderDetailDcsDTO rowDto)throws ServiceBizException;
	//数据校验
	public List<TtPtOrderDetailDcsDTO> checkData(LazyList<TmpPtOrderDetailDcsPO> poList, String partModel,String partCodes)throws ServiceBizException;

}
