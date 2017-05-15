package com.yonyou.dms.vehicle.service.activityRecallManage;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
* @author liujm
* @date 2017年4月20日
*/
public interface RecallFinishTotalService {
	
	//召回活动统计 查询
	public PageInfoDto recallFinishTotalQuery(Map<String,String> queryParam) throws ServiceBizException;
	//主页面 下载
	public void recallFinishTotalDownload(Map<String,String> queryParam, HttpServletRequest request,HttpServletResponse response) throws ServiceBizException;
	//主页面 明细下载
	public void recallFinishTotalDownloadDetail(Map<String,String> queryParam, HttpServletRequest request,HttpServletResponse response) throws ServiceBizException;
	//明细
	public PageInfoDto recallFinishQueryDetail(Long recallId, String dealerCode) throws ServiceBizException;
	//表格 明细下载
	public void recallFinishQueryDetailDownload(Long recallId, String dealerCode, HttpServletRequest request,HttpServletResponse response) throws ServiceBizException;
	
}
