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
import com.yonyou.dms.vehicle.dao.activityRecallManage.RecallVehiclePartDao;

/**
* @author liujm
* @date 2017年4月13日
*/
@SuppressWarnings("all")

@Service
public class RecallVehiclePartServiceImpl implements RecallVehiclePartService{
	
	@Autowired
	private ExcelGenerator excelService;
	
	@Autowired
	private RecallVehiclePartDao rvpDao;
	
	
	
	/**
	 * 召回车辆配件满足率     查询
	 */
	@Override
	public PageInfoDto recallVehiclePartQuery(Map<String, String> queryParam) throws ServiceBizException {
		PageInfoDto pageInfoDto = rvpDao.getRecallVehiclePartQuery(queryParam);
		return pageInfoDto;
	}
	/**
	 * 召回车辆配件满足率     下载
	 */
	@Override
	public void recallVehiclePartDownload(Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) throws ServiceBizException {
		List<Map> orderList = rvpDao.getRecallVehiclePartDownload(queryParam);   	
		Map<String, List<Map>> excelData = new HashMap<>();
		excelData.put("召回车辆配件满足率下载", orderList);
		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
		exportColumnList.add(new ExcelExportColumn("RECALL_NO", "召回编号"));
		exportColumnList.add(new ExcelExportColumn("RECALL_NAME", "召回名称"));
		exportColumnList.add(new ExcelExportColumn("RECALL_START_DATE", "召回开始时间"));		
		exportColumnList.add(new ExcelExportColumn("RECALL_END_DATE", "召回结束时间"));
		exportColumnList.add(new ExcelExportColumn("GROUP_NO", "召回组合"));
		exportColumnList.add(new ExcelExportColumn("BIG_AREA", "大区"));
		exportColumnList.add(new ExcelExportColumn("SMALL_AREA", "小区"));
		exportColumnList.add(new ExcelExportColumn("DUTY_DEALER_CODE", "责任经销商代码"));
		exportColumnList.add(new ExcelExportColumn("DEALER_SHORTNAME", "责任经销商简称"));		
		exportColumnList.add(new ExcelExportColumn("CTWN", "完成召回车辆"));
		exportColumnList.add(new ExcelExportColumn("SATISFY", "满足配件"));
		exportColumnList.add(new ExcelExportColumn("DISSATISFY", "不满足配件"));
		excelService.generateExcel(excelData, exportColumnList, "召回车辆配件满足率下载.xls", request, response);

		
	}
	/**
	 * 召回车辆配件满足率   明细下载
	 */
	@Override
	public void recallVehiclePartDownloadDetail(Map<String, String> queryParam,
			HttpServletRequest request, HttpServletResponse response) throws ServiceBizException {
		List<Map> orderList = rvpDao.getRecallVehiclePartDownloadDetail(queryParam);   	
		Map<String, List<Map>> excelData = new HashMap<>();
		excelData.put("召回车辆配件满足率明细下载", orderList);
		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
		exportColumnList.add(new ExcelExportColumn("RECALL_NO", "召回编号"));
		exportColumnList.add(new ExcelExportColumn("RECALL_NAME", "召回名称"));
		exportColumnList.add(new ExcelExportColumn("RECALL_START_DATE", "召回开始时间"));		
		exportColumnList.add(new ExcelExportColumn("RECALL_END_DATE", "召回结束时间"));
		exportColumnList.add(new ExcelExportColumn("GROUP_NO", "召回组合"));
		exportColumnList.add(new ExcelExportColumn("BIG_AREA", "大区"));
		exportColumnList.add(new ExcelExportColumn("SMALL_AREA", "小区"));
		exportColumnList.add(new ExcelExportColumn("DUTY_DEALER_CODE", "责任经销商代码"));
		exportColumnList.add(new ExcelExportColumn("DEALER_SHORTNAME", "责任经销商简称"));	
		exportColumnList.add(new ExcelExportColumn("PART_CODE", "配件代码"));
		exportColumnList.add(new ExcelExportColumn("PART_NAME", "配件名称"));
		exportColumnList.add(new ExcelExportColumn("TOTAL_PART_NUM", "需备配件数"));
		exportColumnList.add(new ExcelExportColumn("STOCK_PART_NUM", "当前库存数"));
		exportColumnList.add(new ExcelExportColumn("IFSATISFY", "是否满足"));			
		excelService.generateExcel(excelData, exportColumnList, "召回车辆配件满足率明细下载.xls", request, response);

		
	}
	/**
	 * 明细页面查询
	 */
	@Override
	public PageInfoDto recallDetailVehiclePartQuery(String dealerCode, Long recallId, String groupNo)
			throws ServiceBizException {
		PageInfoDto pageInfoDto = rvpDao.getDetailRecallVehiclePartQuery(dealerCode, recallId, groupNo);
		return pageInfoDto;
	}
	/**
	 * 明细页面  明细下载
	 */
	@Override
	public void recallDetailVehiclePartDownload(String dealerCode, Long recallId, String groupNo,
			HttpServletRequest request, HttpServletResponse response) throws ServiceBizException {
		List<Map> orderList = rvpDao.getDetailRecallVehiclePartDownload(dealerCode, recallId, groupNo);   	
		Map<String, List<Map>> excelData = new HashMap<>();
		excelData.put("召回车辆配件满足率明细(限)下载", orderList);
		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
		exportColumnList.add(new ExcelExportColumn("RECALL_NO", "召回编号"));
		exportColumnList.add(new ExcelExportColumn("RECALL_NAME", "召回名称"));
		exportColumnList.add(new ExcelExportColumn("RECALL_START_DATE", "召回开始时间"));		
		exportColumnList.add(new ExcelExportColumn("RECALL_END_DATE", "召回结束时间"));
		exportColumnList.add(new ExcelExportColumn("GROUP_NO", "召回组合"));
		exportColumnList.add(new ExcelExportColumn("BIG_AREA", "大区"));
		exportColumnList.add(new ExcelExportColumn("SMALL_AREA", "小区"));
		exportColumnList.add(new ExcelExportColumn("DUTY_DEALER_CODE", "责任经销商代码"));
		exportColumnList.add(new ExcelExportColumn("DEALER_SHORTNAME", "责任经销商简称"));	
		exportColumnList.add(new ExcelExportColumn("PART_CODE", "配件代码"));
		exportColumnList.add(new ExcelExportColumn("PART_NAME", "配件名称"));
		exportColumnList.add(new ExcelExportColumn("TOTAL_PART_NUM", "需备配件数"));
		exportColumnList.add(new ExcelExportColumn("STOCK_PART_NUM", "当前库存数"));
		exportColumnList.add(new ExcelExportColumn("IFSATISFY", "是否满足"));				
		excelService.generateExcel(excelData, exportColumnList, "召回车辆配件满足率明细(限)下载.xls", request, response);		
	}

}
