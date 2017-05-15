/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : LabourPriceService1.java
*
 * @Author : zhengcong
 *
 * @Date : 2017年3月30日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2017年3月30日    zhengcong    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.part.service.basedata;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.yonyou.dms.common.domains.DTO.basedata.ChargeTypeDTO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.part.domains.DTO.basedata.PartOrderSignDTO;

/**
 * 
*签收货运单service 
 * @author zhengcong
 * @date 2017年4月5日
 */

public interface PartOrderSignService {
	
	
	public PageInfoDto queryPartOrder(Map<String, String> queryParam)throws ServiceBizException; //查询
	
	public List<Map> queryPartOrderDetail(String ORDER_REGEDIT_NO,String DELIVERY_TIME) throws ServiceBizException;//查询明细
	
	List<Map> queryToExport(Map<String, String> queryParam) throws ServiceBizException;//使用excel导出
	
	public void cancelByCode(String ORDER_REGEDIT_NO,String DELIVERY_TIME,PartOrderSignDTO ctdto) throws ServiceBizException;///根据CODE作废
	
	Map<String, String> findByCode(String ORDER_REGEDIT_NO,String DELIVERY_TIME) throws ServiceBizException;//根据CODE查询详细信息
	
	public void signByCode(String ORDER_REGEDIT_NO,String DELIVERY_TIME,PartOrderSignDTO ctdto) throws ServiceBizException;///根据CODE签收

	public PageInfoDto queryVerification(String ORDER_REGEDIT_NO, String DELIVERY_ORDER_NO)throws ServiceBizException;//根据CODE查询核销明细


	
	
//    public Map findById(Long id) throws ServiceBizException;
//    
//    public List<Map> queryUsersForExport(Long id,Map<String,String> queryParam) throws ServiceBizException;
//    
//    public PageInfoDto partOrderSignSQL(Map<String, String> queryParam) throws ServiceBizException;///查询货运单表
//    
//    public PageInfoDto partOrderSignItem(Long id) throws ServiceBizException;///查询货运单明细表
//    
//    public void updatePartOrderSign(Long id,PartOrderSignDTO partOrderSignDTO) throws ServiceBizException;///修改
//
//    public void updateOrderStatus(Long id,PartOrderSignDTO partOrderSignDTO) throws ServiceBizException;///修改发运单状态
//    
//    public void updateHXStatu(Long id, PartOrderSignDTO partOrderSigndto) throws ServiceBizException;///修改发运单核销状态
//    
//    public List<Map> qryOrderSignItemBuy(Long id) throws ServiceBizException;
//
//    public List<Map> partOrderSignItem2(Long id) throws ServiceBizException;
//
//    public PageInfoDto qryPartOrderSign(Map<String, String> queryParam) throws ServiceBizException;

}
