
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : PartBuyservice.java
*
* @Author : zhengcong
*
* @Date : 2017年4月7日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年4月7日   zhengcong    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.part.service.stockmanage;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.part.domains.DTO.stockmanage.PartBuyDTO;
import com.yonyou.dms.part.domains.PO.stockmanage.PartBuyPO;

/**
* PartBuyservice
* @author xukl
* @date 2016年7月26日
*/
@SuppressWarnings("rawtypes")
public interface PartBuyService {
	
	
	public PageInfoDto partBuyFindByNo(String stockInNo)throws ServiceBizException;//根据No查询出采购入库单

	public PageInfoDto partBuyItemFindByNo(String stockInNo)throws ServiceBizException;//根据查询出的入库单，选择后将信息带到主界面后，根据NO查询出详细信息
	
	public PageInfoDto findNotBackItem(String STORAGE_CODE, String PART_NO)throws ServiceBizException;//点修改，查询出非退货单配件信息

	public PageInfoDto findBackItem(String PART_NO, String STOCK_IN_NO)throws ServiceBizException;//点修改，查询出退货单配件信息
	

	
//   public PageInfoDto queryPurchaseOrders(Map<String, String> queryParam) throws ServiceBizException;
//
//   public PartBuyPO addPartBuy(String stockinno,PartBuyDTO partBuyDto) throws ServiceBizException;
//
//   public void doInWarehouse(Long id) throws ServiceBizException;
//
//   public PartBuyPO getPartBuyById(Long id) throws ServiceBizException;
//
//   public void updatePartBuy(Long id, PartBuyDTO partBuyDto) throws ServiceBizException;
//
//   public List<Map> getPartBuyItemsById(Long masterid) throws ServiceBizException;
//
//   public List<Map> qryPartBuyReturnItems(Long id) throws ServiceBizException;
//
//   public void backGoods(PartBuyDTO partBuyDto,Long id)throws ServiceBizException;
//
//   public void deletePartBuybyId(Long id)throws ServiceBizException;
//
//   public List<Map> getPartBuyPrintById(Long id)throws ServiceBizException;

	


}
