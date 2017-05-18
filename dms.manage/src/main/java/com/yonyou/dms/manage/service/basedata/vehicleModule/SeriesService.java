
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.manage
*
* @File name : SeriesService.java
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

import com.yonyou.dms.common.domains.PO.basedata.TmSeriesPo;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.manage.domains.DTO.basedata.vehicleModule.SeriesDto;

/**
* 车系接口
* @author yll
* @date 2016年7月6日
*/

@SuppressWarnings("rawtypes")
public interface SeriesService {

	public PageInfoDto querySeries(Map<String, String> queryParam) throws ServiceBizException;

	public TmSeriesPo getSeriesById(String scode,String bcode) throws ServiceBizException;
	
	public String addSeries(SeriesDto seriesDto) throws ServiceBizException;
	
	public void modifySeries(String scode,String bcode, SeriesDto seriesDto) throws ServiceBizException;
	
	public void deleteSeriesById(String scode,String bcode) throws ServiceBizException;
	
	public List<Map> querySeries2(Map<String, String> queryParam) throws ServiceBizException;
	
	public List<Map> querySeriesOEM(Map<String, String> queryParam) throws ServiceBizException;
	
	public List<Map> querySeriesC(Map<String, String> queryParam) throws ServiceBizException;
	
	public List<Map> querySeriesS(Map<String, String> queryParam) throws ServiceBizException;
	
	public List<Map> querySeriesCSr(Map<String, String> queryParam) throws ServiceBizException;
	
	public String selectSeriesIdByCode(String seriesCode);
	
	public List<Map> querySeriessForExport(Map<String,String> queryParam) throws ServiceBizException;
}
