
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.vehicle
*
* @File name : InvtrMaTnceService.java
*
* @Author : xukl
*
* @Date : 2016年9月14日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年9月14日    xukl    1.0
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
import com.yonyou.dms.vehicle.domains.DTO.basedata.InvtrMaTnceDTO;

/**
* InvtrMaTnceService
* @author xukl
* @date 2016年9月14日
*/

public interface InvtrMaTnceService {

    PageInfoDto qryVIN(Map<String, String> queryParam);
    
    public PageInfoDto QueryInvtrMaTnceData(Map<String, String> queryParam) throws ServiceBizException;

    public Map getInvtrMaTnceById(Long id)throws ServiceBizException;
    
    public void modifyPDI(String vin,InvtrMaTnceDTO InvtrMaTnceDTO);
    
    public void modifyInvtrMaTnce(Long id,InvtrMaTnceDTO InvtrMaTnceDTO);
    
    public void modifyConfiguration(Long id,InvtrMaTnceDTO InvtrMaTnceDTO);//修改配置
    
    List<Map> queryInvtrMaTnceForExport(Map<String, String> queryParam) throws ServiceBizException;



}
