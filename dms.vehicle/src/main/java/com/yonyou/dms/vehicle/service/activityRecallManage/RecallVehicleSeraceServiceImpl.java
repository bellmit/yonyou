package com.yonyou.dms.vehicle.service.activityRecallManage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.TtRecallVehicleDcsPO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelDataType;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.dao.activityRecallManage.RecallVehicleSeraceDao;
import com.yonyou.dms.vehicle.domains.DTO.activityManage.ActivityManageDTO;
/**
* @author liujm
* @date 2017年4月24日
*/
@SuppressWarnings("all")

@Service
public class RecallVehicleSeraceServiceImpl implements RecallVehicleSeraceService{
	
	@Autowired
	private ExcelGenerator excelService;
	
	
	@Autowired
	private RecallVehicleSeraceDao rvsDao;
	
	
	/**
	 * 查询
	 */
	@Override
	public PageInfoDto recallVehicleSeraceQuery(Map<String, String> queryParam) throws ServiceBizException {
		PageInfoDto pageInfoDto = rvsDao.getRecallVehicleSeraceQuery(queryParam);
		return pageInfoDto;
	}
	/**
	 * 下载
	 */
	@Override
	public void recallVehicleSeraceDownload(Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) throws ServiceBizException {
		List<Map> orderList = rvsDao.getRecallVehicleSeraceDownload(queryParam);   	
		Map<String, List<Map>> excelData = new HashMap<>();
		excelData.put("召回车辆下载", orderList);
		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
		exportColumnList.add(new ExcelExportColumn("VIN", "VIN"));
		exportColumnList.add(new ExcelExportColumn("RECALL_NO", "召回编号"));
		exportColumnList.add(new ExcelExportColumn("RECALL_NAME", "召回名称"));
		exportColumnList.add(new ExcelExportColumn("RECALL_START_DATE", "召回开始时间"));		
		exportColumnList.add(new ExcelExportColumn("RECALL_END_DATE", "召回结束时间"));		
		exportColumnList.add(new ExcelExportColumn("BIG_AREA", "大区"));
		exportColumnList.add(new ExcelExportColumn("SMALL_AREA", "小区"));
		exportColumnList.add(new ExcelExportColumn("DEALER_CODE", "责任经销商代码"));
		exportColumnList.add(new ExcelExportColumn("DEALER_SHORTNAME", "责任经销商简称"));
		exportColumnList.add(new ExcelExportColumn("CTM_NAME", "车主姓名"));
		exportColumnList.add(new ExcelExportColumn("MAIN_PHONE", "车主电话"));			
		exportColumnList.add(new ExcelExportColumn("RECALL_STATUS", "完成状态", ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("EXPECT_RECALL_DATE_TMP", "预计召回时间"));
		exportColumnList.add(new ExcelExportColumn("UPDATE_COUNT", "修改次数"));			
		excelService.generateExcel(excelData, exportColumnList, "召回车辆下载.xls", request, response);
		
	}
	/**
	 * 查询召回活动信息
	 */
	@Override
	public Map queryRecallDateil(String vin, String recallId) throws ServiceBizException {
		Map map = rvsDao.queryRecallDateilMap(vin, recallId);
		return map;
	}
	/**
	 * 查询客户信息
	 */
	@Override
	public Map queryCustomerDateil(String vin, String recallId) throws ServiceBizException {
		Map map = rvsDao.queryRecallDateilMap(vin, recallId);
		return map;
	}
	/**
	 * 查询车辆信息
	 */
	@Override
	public Map queryVehicleDateil(String vin, String recallId) throws ServiceBizException {
		Map map = rvsDao.queryVehicleDateilMap(vin, recallId);
		return map;
	}
	/**
	 * 修改预计召回时间
	 */
	@Override
	public void updateRecallTime(ActivityManageDTO amDto) throws ServiceBizException {
		
		if(amDto.getVins() == null || amDto.getVins().equals("") ){
			throw new ServiceBizException("请在父表格中选择数据！");
		}
		String[]  vinAndNO = amDto.getVins().split(",");
		for(int i=0; i<vinAndNO.length; i++){
			String[] array = vinAndNO[i].split("#");
			TtRecallVehicleDcsPO savePo = TtRecallVehicleDcsPO.findFirst(" RECALL_ID = ? AND VIN = ? ", Long.parseLong(array[1].toString()),array[0].toString());			
			savePo.setTimestamp("EXPECT_RECALL_DATE_TMP", amDto.getRecallDate());
			savePo.set("UPLOAD_STATUS", 10041001);
			savePo.saveIt();
			
		}
		
	}

}



