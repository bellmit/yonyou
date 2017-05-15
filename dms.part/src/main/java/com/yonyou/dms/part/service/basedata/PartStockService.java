
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : PartStockService.java
*
* @Author : xukl
*
* @Date : 2016年7月15日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年7月15日    xukl    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.part.service.basedata;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.commonAS.domains.DTO.basedata.PartStockDTO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
* 配件库存接口
* @author xukl
* @date 2016年7月15日
*/

@SuppressWarnings("rawtypes")
public interface PartStockService {

    public PageInfoDto queryPartStocks(Map<String, String> queryParam) throws ServiceBizException;

    public Map<String, Object> getPartStockById(String id,String ids) throws ServiceBizException;

    public void modifyPartStock(String id,String ids, Map partStockDTO) throws ServiceBizException;
    
    public String addPartStock(Map partInfoDTO) throws ServiceBizException;

    public  List<Map> queryPartStockForExport(Map<String, String> queryParam) throws ServiceBizException;

    public PageInfoDto pageInfoList(Map<String, String> param) throws ServiceBizException;
    
    public PageInfoDto pageInfoLists(String id,String ids) throws ServiceBizException;

    public void importPartStock(PartStockDTO partStockDTO) throws ServiceBizException;
    
    public boolean partNoOrStorageExist(String partNo,String logo)throws ServiceBizException;

    public PageInfoDto queryPartInfoByLabour(Map<String, String> queryParam)throws ServiceBizException;
    
    public  List<Map> getStockCodeById(Map<String, String> queryParam) throws ServiceBizException;
    
    public  List<Map> getDealertCode(Map<String, String> queryParam) throws ServiceBizException;
    
    public PageInfoDto queryPartSn(String id,String ids) throws ServiceBizException;

}
