
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.manage
*
* @File name : BrandService.java
*
* @Author : yll
*
* @Date : 2016年7月6日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年7月6日    yll    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.manage.service.basedata.vehicleModule;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.common.domains.PO.basedata.TmBrandPo;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.manage.domains.DTO.basedata.vehicleModule.BrandDto;

/**
* 品牌的service 接口
* @author yll
* @date 2016年7月6日
*/

@SuppressWarnings("rawtypes")
public interface BrandService {
	public PageInfoDto queryBrand(Map<String, String> queryParam) throws ServiceBizException;
	
	public List<Map> queryBrandSr(Map<String, String> queryParam) throws ServiceBizException;

	public TmBrandPo getBrandById(String id) throws ServiceBizException;
	
	public String addBrand(BrandDto brandDto) throws ServiceBizException;
	
	public void modifyBrand(String id,BrandDto brandDto) throws ServiceBizException;
	
	public void deleteBrandById(String id) throws ServiceBizException;

	public List<Map> queryBrand2(Map<String, String> queryParam) throws ServiceBizException;
	
	public List<Map> queryBrand3(Map<String, String> queryParam) throws ServiceBizException;
	
	public List<Map> queryBrandOEM(Map<String, String> queryParam) throws ServiceBizException;
	
	public Integer selectBrandIdByCode(String brandCode);
	
	public List<Map> queryBrandsForExport(Map<String,String> queryParam) throws ServiceBizException;
	
	public List<Map> querySeries(Map<String, String> queryParam) throws ServiceBizException;
	
	public List<Map> querySeriesSr(Map<String, String> queryParam) throws ServiceBizException;
}
