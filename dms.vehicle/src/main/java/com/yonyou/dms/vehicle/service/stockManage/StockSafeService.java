
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.vehicle
*
* @File name : StockSafeService.java
*
* @Author : yangjie
*
* @Date : 2016年12月28日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年12月28日    yangjie    1.0
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

/**
* TODO description
* @author yangjie
* @date 2016年12月28日
*/
@SuppressWarnings("rawtypes")
public interface StockSafeService {

    PageInfoDto findAll(Map<String, String> queryParam) throws ServiceBizException;//遍历所有查询结果集
    
    List<Map> findDealerInfo() throws ServiceBizException;//查询经销商简称

    List<Map> findStorageInfo(String id) throws ServiceBizException;//查询仓库名称
    
    List<Map> findColor() throws ServiceBizException;//查询颜色名称
    
    Integer batchModify(List<Map<String, String>> list) throws ServiceBizException;//批量修改

    Map<String, Object> findByVin(String vin) throws ServiceBizException;//根据vin查询详细信息

    List<Map> findAllTree(Map query) throws ServiceBizException;//查询品牌

    PageInfoDto findAllProduct(Map<String, String> queryParam) throws ServiceBizException;//查询产品信息
    
    List<Map> querySafeToExport(Map<String, String> queryParam) throws ServiceBizException;//使用excel导出
    
    void editConfig(Map<String, String> queryParam) throws ServiceBizException;//修改配置
}
