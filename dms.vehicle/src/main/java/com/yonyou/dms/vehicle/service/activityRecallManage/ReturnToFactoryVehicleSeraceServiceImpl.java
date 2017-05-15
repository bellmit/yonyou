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
import com.yonyou.dms.framework.service.excel.ExcelDataType;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.dao.activityRecallManage.ReturnToFactoryVehicleSeraceDao;

/**
* @author liujm
* @date 2017年4月22日
*/
@SuppressWarnings("all")

@Service
public class ReturnToFactoryVehicleSeraceServiceImpl implements ReturnToFactoryVehicleSeraceService{
	
	@Autowired
	private ExcelGenerator excelService;
	
	@Autowired
	private ReturnToFactoryVehicleSeraceDao  retoDao;

	/**
	 * 返厂未召回车辆查询
	 */
	@Override
	public PageInfoDto returnToFactoryVehicleSeraceQuery(Map<String, String> queryParam) throws ServiceBizException {
		PageInfoDto pageInfoDto = retoDao.getReturnToFactoryVehicleSeraceQuery(queryParam);
		return pageInfoDto;
	}
	/**
	 * 返厂未召回车辆下载
	 */
	@Override
	public void returnToFactoryVehicleSeraceDownload(Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) throws ServiceBizException {
		List<Map> orderList = retoDao.getReturnToFactoryVehicleSeraceDownload(queryParam);   	
		Map<String, List<Map>> excelData = new HashMap<>();
		excelData.put("返厂未召回车辆下载", orderList);
		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
		exportColumnList.add(new ExcelExportColumn("VIN", "VIN"));
		exportColumnList.add(new ExcelExportColumn("RECALL_NO", "召回编号"));
		exportColumnList.add(new ExcelExportColumn("RECALL_NAME", "召回名称"));
		exportColumnList.add(new ExcelExportColumn("RECALL_START_DATE", "召回开始时间"));		
		exportColumnList.add(new ExcelExportColumn("RECALL_END_DATE", "召回结束时间"));		
		exportColumnList.add(new ExcelExportColumn("BIG_AREA", "大区"));
		exportColumnList.add(new ExcelExportColumn("SMALL_AREA", "小区"));
		exportColumnList.add(new ExcelExportColumn("RETURM_DEALER_CODE", "返厂经销商代码"));
		exportColumnList.add(new ExcelExportColumn("RETURM_DEALER_NAME", "返厂经销商简称"));
		exportColumnList.add(new ExcelExportColumn("RESPONSIBILITY_DEALER_CODE", "责任经销商代码"));
		exportColumnList.add(new ExcelExportColumn("RESPONSIBILITY_DEALER_NAME", "责任经销商简称"));			
		exportColumnList.add(new ExcelExportColumn("RECALL_STATUS", "完成状态", ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("MAKE_DATE", "开单时间"));
		exportColumnList.add(new ExcelExportColumn("FINISH_DATE", "返厂时间"));			
		excelService.generateExcel(excelData, exportColumnList, "返厂未召回车辆下载.xls", request, response);

		
	}

}
