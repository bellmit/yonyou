
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.vehicle
*
* @File name : TransferServiceImpl.java
*
* @Author : yangjie
*
* @Date : 2017年1月8日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年1月8日    yangjie    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.vehicle.service.stockManage;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

@SuppressWarnings("rawtypes")
public interface TransferService {

    /**
     * 查询车辆移库单信息 TODO description
     * 
     * @author yangjie
     * @date 2017年1月9日
     * @param queryParam
     * @return
     * @throws ServiceBizException
     */
    PageInfoDto findAllRepository(Map<String, String> queryParam) throws ServiceBizException;

    /**
     * 查询移库单明细信息 TODO description
     * 
     * @author yangjie
     * @date 2017年1月9日
     * @param stNo
     * @return
     * @throws ServiceBizException
     */
    List<Map> findAllRepositoryDetails(String stNo) throws ServiceBizException;

    /**
     * 批量移库移位方法 TODO description
     * 
     * @author yangjie
     * @date 2017年1月9日
     * @param stNo
     * @param vin
     * @param map
     */
    void batchEditRepository(String stNo, String vin, Map<String, Object> map);

    /**
     * 查询车辆信息 TODO description
     * 
     * @author yangjie
     * @date 2017年1月9日
     * @param queryParam
     * @return
     */
    PageInfoDto findAllVehicleInfo(Map<String, String> queryParam);
    
    /**
     * 保存操作(从车辆信息中添加信息到移库单中) TODO description
     * 
     * @author yangjie
     * @date 2017年1月9日
     * @param map
     */
    void addInfoToList(Map<String, Object> map);

    /**
     * 保存操作(从车辆信息中添加信息到移库单明细中) TODO description
     * 
     * @author yangjie
     * @date 2017年1月9日
     * @param map
     */
    void addInfoToItem(Map<String, Object> map);

    /**
     * 删除操作 TODO description
     * 
     * @author yangjie
     * @date 2017年1月9日
     * @param stNo
     * @param vin
     */
    void deleteItem(String stNo, String vin);

    /**
     * 作废操作 TODO description
     * 
     * @author yangjie
     * @date 2017年1月9日
     * @param stNo
     */
    void deleteTransfer(String stNo);
    
    /**
     * 查询所有VIN
    * TODO description
    * @author yangjie
    * @date 2017年1月10日
    * @return
     */
    List<Map> findAllVIN(String[] vins) throws ServiceBizException;

    /**
     * 移库操作 TODO description
     * 
     * @author yangjie
     * @date 2017年1月9日
     * @param map
     */
    void btnTransfer(Map<String, Object> map);

    /**
     * 根据仓库名查询仓库代码 TODO description
     * 
     * @author yangjie
     * @date 2017年1月9日
     * @param storageName
     * @return
     */
    Integer findStorageCode(String storageName);

    /**
     * 下拉框查询经办人
    * TODO description
    * @author yangjie
    * @date 2017年1月10日
    * @return
     */
    List<Map> findAllEmp();
    
    /**
     * 根据ST_NO查询所有VIN
    * TODO description
    * @author yangjie
    * @date 2017年1月10日
    * @return
     */
    List<Map> findAllItem(String stNo);
    
    /**
     * 修改明细单(新增)
    * TODO description
    * @author yangjie
    * @date 2017年1月11日
    * @param map
     */
    void addItemByIn(Map<String, Object> map);
    
    /**
     * 修改明细单(修改)
    * TODO description
    * @author yangjie
    * @date 2017年1月11日
    * @param map
     */
    void editItemByIn(Map<String, Object> map);
    
    /**
     * 查询一条vin
    * TODO description
    * @author yangjie
    * @date 2017年1月11日
    * @param vin
    * @return
     */
    Map findAllVIN2(String vin);
    
    /**
     * 移位
    * TODO description
    * @author yangjie
    * @date 2017年1月12日
    * @param vins
    * @param location
     */
    void editLocation(String[] vins,String location);
    
    /**
     * 更新主表信息
     * @param stNo
     */
    void refreshMainInfo(String stNo);

    /**
     * 打印前显示集合信息
     * @param sNo
     * @return
     */
	Map printFirstShow(String sNo);

	/**
	 * 移位打印显示
	 * @param vins
	 * @return
	 */
	List<Map> findAllPrintLocation(String[] vins);

	/**
	 * 移位用查询车辆信息
	 * @param map
	 * @return
	 */
	PageInfoDto findAllVehicleInfoForLocal(Map<String, String> map);

	/**
	 * 修改主表是否入账
	 * @param string
	 */
	void editList(String string);
}
