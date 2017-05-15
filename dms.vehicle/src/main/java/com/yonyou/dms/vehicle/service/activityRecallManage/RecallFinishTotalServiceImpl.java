package com.yonyou.dms.vehicle.service.activityRecallManage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.dao.activityRecallManage.RecallFinishTotalDao;

/**
* @author liujm
* @date 2017年4月20日
*/
@SuppressWarnings("all")

@Service
public class RecallFinishTotalServiceImpl implements RecallFinishTotalService{
	
	@Autowired
	private ExcelGenerator excelService;
	
	@Autowired
	private RecallFinishTotalDao rftDao;
	
	
	/**
	 * 召回活动统计 查询
	 */
	@Override
	public PageInfoDto recallFinishTotalQuery(Map<String, String> queryParam) throws ServiceBizException {
		PageInfoDto pageInfoDto = rftDao.getRecallFinishTotalQuery(queryParam);
		return pageInfoDto;
	}

	/**
	 * 召回活动统计 主页面 下载
	 */
	@Override
	public void recallFinishTotalDownload(Map<String, String> queryParam, 
			HttpServletRequest request,HttpServletResponse response) throws ServiceBizException {
		List<Map> orderList = rftDao.getRecallFinishTotalDownload(queryParam);   	
		Map<String, List<Map>> excelData = new HashMap<>();
		excelData.put("召回完成统计下载", orderList);
		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
		exportColumnList.add(new ExcelExportColumn("RECALL_NO", "召回编号"));
		exportColumnList.add(new ExcelExportColumn("RECALL_NAME", "召回名称"));
		exportColumnList.add(new ExcelExportColumn("RECALL_START_DATE", "召回开始时间"));		
		exportColumnList.add(new ExcelExportColumn("RECALL_END_DATE", "召回结束时间"));		
		exportColumnList.add(new ExcelExportColumn("BIG_AREA", "大区"));
		exportColumnList.add(new ExcelExportColumn("SMALL_AREA", "小区"));
		exportColumnList.add(new ExcelExportColumn("DEALER_CODE", "责任经销商代码"));
		exportColumnList.add(new ExcelExportColumn("DEALER_SHORTNAME", "责任经销商简称"));
		exportColumnList.add(new ExcelExportColumn("RECALL_NUM", "召回目标"));
		exportColumnList.add(new ExcelExportColumn("RECALL_NUM1", "当前完成"));
		exportColumnList.add(new ExcelExportColumn("RECALL_RATE", "完成率"));
		excelService.generateExcel(excelData, exportColumnList, "召回完成统计下载.xls", request, response);

		
	}

	/**
 	* 主页面 明细下载
 	*/
	@Override
	public void recallFinishTotalDownloadDetail(Map<String, String> queryParam, 
			HttpServletRequest request,HttpServletResponse response) throws ServiceBizException {
		List<Map> orderList = rftDao.getRecallFinishTotalDownloadDetail(queryParam);   	
		Map<String, List<Map>> excelData = new HashMap<>();
		excelData.put("召回完成统计明细下载", orderList);
		List<ExcelExportColumn> exportColumnList = new ArrayList<>();

		exportColumnList.add(new ExcelExportColumn("RECALL_NO", "召回编号"));
		exportColumnList.add(new ExcelExportColumn("RECALL_NAME", "召回名称"));
		exportColumnList.add(new ExcelExportColumn("RECALL_START_DATE", "召回开始时间"));		
		exportColumnList.add(new ExcelExportColumn("RECALL_END_DATE", "召回结束时间"));		
		exportColumnList.add(new ExcelExportColumn("DEALER_CODE", "责任经销商代码"));
		exportColumnList.add(new ExcelExportColumn("VIN", "车辆VIN"));
		exportColumnList.add(new ExcelExportColumn("IN_REPAIR_DATE", "最近进厂时间"));
		exportColumnList.add(new ExcelExportColumn("RECALL_STATUS", "完成状态"));
		exportColumnList.add(new ExcelExportColumn("RECALL_REPAIR_DATE", "召回时间"));
		exportColumnList.add(new ExcelExportColumn("RECALL_CODE", "实际召回经销商"));
		exportColumnList.add(new ExcelExportColumn("REPAIR_NO", "工单号"));
		excelService.generateExcel(excelData, exportColumnList, "召回完成统计明细下载.xls", request, response);

		
	}

	/**
	 * 明细
	 */
	@Override
	public PageInfoDto recallFinishQueryDetail(Long recallId, String dealerCode) throws ServiceBizException {
		PageInfoDto pageInfoDto = rftDao.getDetailRecallFinishTotalQuery(recallId, dealerCode);
		return pageInfoDto;
	}

	/**
	 * 表格 明细下载
	 */
	@Override
	public void recallFinishQueryDetailDownload(Long recallId, String dealerCode, 
			HttpServletRequest request,HttpServletResponse response) throws ServiceBizException {
		List<Map> orderList = rftDao.getDetailQuerySql(recallId, dealerCode);   	
		Map<String, List<Map>> excelData = new HashMap<>();
		excelData.put("召回完成统计明细下载", orderList);
		List<ExcelExportColumn> exportColumnList = new ArrayList<>();

		exportColumnList.add(new ExcelExportColumn("RECALL_NO", "召回编号"));
		exportColumnList.add(new ExcelExportColumn("RECALL_NAME", "召回名称"));
		exportColumnList.add(new ExcelExportColumn("RECALL_START_DATE", "召回开始时间"));		
		exportColumnList.add(new ExcelExportColumn("RECALL_END_DATE", "召回结束时间"));		
		exportColumnList.add(new ExcelExportColumn("DEALER_CODE", "责任经销商代码"));
		exportColumnList.add(new ExcelExportColumn("VIN", "车辆VIN"));
		exportColumnList.add(new ExcelExportColumn("IN_REPAIR_DATE", "最近进厂时间"));
		exportColumnList.add(new ExcelExportColumn("RECALL_STATUS", "完成状态"));
		exportColumnList.add(new ExcelExportColumn("RECALL_REPAIR_DATE", "召回时间"));
		exportColumnList.add(new ExcelExportColumn("RECALL_CODE", "实际召回经销商"));
		exportColumnList.add(new ExcelExportColumn("REPAIR_NO", "工单号"));
		excelService.generateExcel(excelData, exportColumnList, "召回完成统计明细下载.xls", request, response);

			
		
	}

}
