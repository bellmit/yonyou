
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : PartAllocateInService.java
*
* @Author : xukl
*
* @Date : 2016年8月10日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年8月10日    xukl    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.part.service.stockmanage;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.part.domains.DTO.stockmanage.ListPartAllocateInItemDto;
import com.yonyou.dms.part.domains.DTO.stockmanage.PartAllocateInDto;

/**
* 调拨入库接口
* @author xukl
* @date 2016年8月10日
*/
@SuppressWarnings("rawtypes")
public interface PartAllocateInService {

    public   PageInfoDto  qryAllocateInOrders(Map<String, String> queryParam) throws ServiceBizException;
    public  List<Map> getAllocateInItemsByAllocateInNo(String allocateInNo) throws ServiceBizException;
    public List<Map> getStorageAllSelect()throws ServiceBizException;
    public PageInfoDto queryStoragePartForAdd(Map<String, String> queryParam)throws ServiceBizException;
   // public PageInfoDto queryStoragePartForAddC(Map<String, String> queryParam)throws ServiceBizException;
    public PageInfoDto findAllPartInfoC(Map<String, String> queryParam)throws ServiceBizException;
    public PageInfoDto QueryPartCustomer(Map<String, String> queryParam)throws ServiceBizException;
    public String savePartAllocateIn(ListPartAllocateInItemDto listPartAllocateInItemDto)throws ServiceBizException;
    public Map<String,Object>  queryStoragePartOne(Map<String, String> queryParam)throws ServiceBizException;
    public Map<String,Object> queryStoragePosition(Map<String, String> queryParam)throws ServiceBizException;
    public   PageInfoDto  queryAllocateOutNet(Map<String, String> queryParam) throws ServiceBizException;
    public   PageInfoDto  queryAllocateOutNetItem(Map<String, String> queryParam) throws ServiceBizException;
  //  public List<Map> QueryBranchFactoryAllocateItem(Map<String, String> queryParam)throws ServiceBizException;
    public PageInfoDto QueryBranchFactoryAllocate(Map<String, String> queryParam)throws ServiceBizException;
    public void deleteAllocateIn(String customerCode, String allocateInNo)throws ServiceBizException;
    public void accountPartAllocateIn(Map<String,String>queryparams)throws ServiceBizException,  Exception;
    public PageInfoDto findPartModelGroup()throws ServiceBizException,  Exception;
    public void addPartStock(Map partInfoDTO)throws ServiceBizException,Exception;
    public Map<String, Object> queryPartInfo(Map<String, String> queryParam)throws ServiceBizException;
    
}
