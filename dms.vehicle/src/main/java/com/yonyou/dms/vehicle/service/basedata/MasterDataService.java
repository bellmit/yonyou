
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.vehicle
*
* @File name : MasterDataService.java
*
* @Author : DuPengXin
*
* @Date : 2016年9月8日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年9月8日    DuPengXin   1.0
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
import com.yonyou.dms.vehicle.domains.DTO.basedata.MasterDataDTO;

/**
* 整车产品信息接口
* @author DuPengXin
* @date 2016年9月8日
*/

public interface MasterDataService {
    public PageInfoDto QueryMasterData(Map<String, String> queryParam) throws ServiceBizException;
    public Long addMasterData(MasterDataDTO masterdatadto) throws ServiceBizException;
    public void modifyMasterData(String id,MasterDataDTO masterdatadto) throws ServiceBizException;
    @SuppressWarnings("rawtypes")
	public List<Map> queryMasterDataExport(Map<String, String> queryParam) throws ServiceBizException;
    public void salesPrice(MasterDataDTO masterdatadto)throws ServiceBizException;
    @SuppressWarnings("rawtypes")
	public List<Map> findById(String id) throws ServiceBizException;

}
