
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.vehicle
 *
 * @File name : StockOutService.java
 *
 * @Author : DuPengXin
 *
 * @Date : 2016年9月21日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年9月21日    DuPengXin   1.0
 *
 *
 *
 *
----------------------------------------------------------------------------------
 */

package com.yonyou.dms.vehicle.service.basedata;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.domains.DTO.basedata.StockOutListDTO;

/**
 * 整车出库接口
 * @author DuPengXin
 * @date 2016年9月21日
 */
@SuppressWarnings("rawtypes")
public interface StockOutService {
    /**
     * 查询出库主表信息
    * TODO description
    * @author yangjie
    * @date 2017年2月13日
    * @param queryParam
    * @return
     */
    PageInfoDto findAllOutItems(Map<String, String> queryParam);
    
    /**
     * 查询出库子表信息
    * TODO description
    * @author yangjie
    * @date 2017年2月14日
    * @param sdNo
    * @return
     */
    List<Map> findAllOutDetails(String sdNo, String vin, String productCode);
    
    PageInfoDto findAllOutDetails2(String sdNo, String vin, String productCode);
    
    /**
     * 查询出库车辆信息
    * TODO description
    * @author yangjie
    * @date 2017年2月13日
    * @param queryParam
    * @return
     */
    PageInfoDto findAllVehicle(Map<String, String> queryParam);
    
    /**
     * 保存操作
    * TODO description
    * @author yangjie
    * @date 2017年2月13日
    * @param outListDTO
    * @throws ServiceBizException
     */
    String btnSave(StockOutListDTO outListDTO) throws ServiceBizException;
    
    /**
     * 删除操作
    * TODO description
    * @author yangjie
    * @date 2017年2月13日
    * @param map
    * @throws ServiceBizException
     */
    void deleteOutItems(Map map) throws ServiceBizException;
    
    /**
     * 出库操作
    * TODO description
    * @author yangjie
    * @date 2017年2月13日
    * @param list
    * @param sdNo
    * @param outType
    * @throws ServiceBizException
     */
    void btnStockOut(List<Map> list, String sdNo, String outType) throws ServiceBizException;
    
    /**
     * 查询行业大类
    * TODO description
    * @author yangjie
    * @date 2017年2月14日
    * @return
     */
    List<Map> findIndustryFirst();
    
    /**
     * 查询行业小类
    * TODO description
    * @author yangjie
    * @date 2017年2月14日
    * @param firsh
    * @return
     */
    List<Map> findIndustrySecond(String firsh);
    
    /**
     * 查询保有客户信息
    * TODO description
    * @author yangjie
    * @date 2017年2月14日
    * @param Vin
    * @param SoNo
    * @return
     */
    Map findCustomerInfo(String Vin,String SoNo);

    /**
     * 查询车辆信息	批量出库用
     * @param sdNo
     * @param vin
     * @param object
     * @return
     */
	List<Map> findAllOutDetailsForBatch(String sdNo, String vin, Object object);

	/**
	 * 打印的查询条件
	 * @param vins
	 * @return
	 */
	List<Map> findPrintAbout(String vins);
}
