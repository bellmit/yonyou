package com.yonyou.dms.vehicle.service.activityRecallManage;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.domains.DTO.activityManage.RecallActivityImportDTO;
import com.yonyou.dms.vehicle.domains.DTO.activityManage.RecallServiceActDTO;
import com.yonyou.dms.vehicle.domains.DTO.activityManage.TtRecallServiceDcsDTO;

/**
* @author liujiming
* @date 2017年4月10日
*/
@SuppressWarnings("all")

public interface RecallActivityBuildService {
		
	//召回活动建立 主页面查询
	public PageInfoDto getRecallInitQuery(Map<String,String> queryParam) throws ServiceBizException;
	
	//召回活动建立 主页面下载
	public void getRecallInitQueryDownload(Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) throws ServiceBizException;
	
	//召回活动建立 主页面新增
	public Map  recallActivityAddSave(TtRecallServiceDcsDTO trsdDto) throws ServiceBizException;
	
	//召回活动建立 修改查询
	public Map queryEditRecallServiceMap(Long recallId) throws ServiceBizException;
	
	//召回活动建立 主页面新增
	public void editRecallServiceSave(TtRecallServiceDcsDTO trsdDto) throws ServiceBizException;
		
	//召回活动建立 主页面删除
	public void deleteRecallService(Long recallId) throws ServiceBizException;
	//召回活动建立  发布/取消发布
	public void sendRecallService(Long recallId,Integer recallStatus) throws ServiceBizException;
	
	
	//召回活动建立 经销商查询
	public PageInfoDto dealerRecallQuery(Map<String,String> queryParam) throws ServiceBizException;
	//召回活动建立  经销商新增查询
	public PageInfoDto dealerRecallAddQuery(Map<String,String> queryParam) throws ServiceBizException;
		
	//召回活动建立  经销商新增批量确定
	public void dealerRecallAddSave(RecallServiceActDTO rsDto) throws ServiceBizException;
	
	//召回活动建立  经销商批量删除
	public void dealerRecallDelete(RecallServiceActDTO rsDto) throws ServiceBizException;
	
	
	//召回活动建立 VIN下载
	public void recallActivityVinDownLoad(Long recallId, HttpServletRequest request,
				HttpServletResponse response) throws ServiceBizException;
	//召回活动建立 召回项目下载
	public void recallActivityProjectDownLoad(Long recallId, HttpServletRequest request,
				HttpServletResponse response) throws ServiceBizException;
	//召回活动建立 经销商下载
	public void recallActivityDealerDownLoad(Long recallId, HttpServletRequest request,
				HttpServletResponse response) throws ServiceBizException;
	
	//导入清空临时表数据
	public void deleteTmpRecallVehicleDcs() throws ServiceBizException;
	//导入临时表
	public void saveTmpRecallVehicleDcs(RecallActivityImportDTO rowDto) throws ServiceBizException;
	//校验临时表数据
	public List<RecallActivityImportDTO> checkData(Integer inportType) throws ServiceBizException;
	//查询临时表数据
	public List<Map> queryTmpRecallVehicleDcsList(Map<String, String> queryParam) throws ServiceBizException;
	//导入业务表，删除临时表
	public void  saveAndDeleteData(Map<String, String> queryParam)throws ServiceBizException;
			
}




