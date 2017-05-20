
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.repair
 *
 * @File name : repairOrderService.java
 *
 * @Author : ZhengHe
 *
 * @Date : 2016年8月10日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年8月10日    ZhengHe    1.0
 *
 *
 *
 *
----------------------------------------------------------------------------------
 */

package com.yonyou.dms.repair.service.order;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 工单接口
 * @author ZhengHe
 * @date 2016年8月10日
 */
@SuppressWarnings("rawtypes")
public interface RepairOrderService {
	/**
	 * @author zhengcong
	 * @date 2017年4月24日
	 */
	public PageInfoDto newQueryPart(Map<String,String> qParam)throws ServiceBizException;//工单新增配件根据条件查询

	public PageInfoDto newQueryPartStock(String PART_NO, String STORAGE_CODE)throws ServiceBizException;//工单新增配件根据条件查询出配件后，双击配件，带出配件库存信息

	public PageInfoDto queryInsteadPart(String OPTION_NO)throws ServiceBizException;//查询替代配件
	
	/**
	 * 记录缺料信息
	 * @author yangjie
	 * @param repairPart
	 * @return
	 */
	String recordInDetail(List<Map> repairPart,Map param);

	/**
	 * 保养配件信息相关查询
	 * @author yangjie
	 * @param param
	 * @return
	 */
	public String getIsMaintenance(Map param);

	/**
	 * 日平均行驶里程相关查询
	 * @author yangjie
	 * @param param
	 * @return
	 */
	public Map queryVehicleforactivity(Map param);
	
	/**
	 * 查询特定vin的会员卡是否存在
	 * @author sqh
	 * @param param
	 * @return
	 */
	public Map queryMemberCardExist(Map param);
	
	/**
	 * 查询非4S站的车辆,在库存存在并且未开票的车
	 * @author sqh
	 * @param param
	 * @return
	 */
	public Map checkVehicleInvoice(Map param);
	
	/**
	 * 根据工单号，车牌号查询工单信息
	 * @author sqh
	 * @param param
	 * @return
	 */
	public Map queryRepairOrderExists(Map param);
	
//	/**
//	 * 查询车辆方案状态为”等待审核“的工单
//	 * @author sqh
//	 * @param param
//	 * @return
//	 */
//	public Map checkIsHaveAduitingOrder(Map param);
	
	PageInfoDto selectEmployee(Map<String, String> queryParam)throws ServiceBizException;

	List<Map> querytechnician(Map<String, String> queryParam)throws ServiceBizException;

	List<Map> queryrepairPosition(Map<String, String> queryParam)throws ServiceBizException;

	Map findtechnicianItem(String techniciancode)throws ServiceBizException;

	List<Map> querytechnicianWorkType()throws ServiceBizException;

	/**
	 * 查询工单明细
	 * @author yangjie
	 * @param reNo
	 * @return
	 */
	public Map findOrderDetails(String roNo);
	
	
	PageInfoDto queryActivityResult(Map<String, String> queryParam) throws Exception;

	PageInfoDto QueryOccurInsuranceByLicenseOrVin(Map<String, String> queryParam) throws Exception;
	
	PageInfoDto queryBookingOrder(Map<String, String> queryParam) throws Exception;
	
	List<Map> retriveByControl(Map<String, String> queryParam) throws Exception;

	List<Map> queryActivityValid(Map<String, String> queryParam) throws Exception;

	/**
	 * 通过VIN获取车辆信息
	 * @author yangjie
	 * @param query
	 * @return
	 */
	public List<Map> queryVinByVin(Map<String, String> query);

	
	/**
	 * 判断是否服务专员
	 * 1  表示是   0 表示不是
	 * @param string
	 * @return
	 */
	public String findServiceAdvisor(String string);

	/**
	 * 根据维修类型查询工时单价
	 * @param string
	 * @return
	 */
	String findLabourPriceByRepairTypeCode(String string);

	/**
	 * 根据车型代码查询工时单价
	 * @param string
	 * @return
	 */
	public String findLabourPriceByModelCode(Map<String, String> param);
	

}
