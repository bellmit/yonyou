package com.yonyou.dms.vehicle.service.vehicleAllot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelDataType;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.vehicle.dao.vehicleAllotDao.VehicleAllotQueryDao;

@Service
public class VehicleAllotQueryServiceImpl implements VehicleAllotQueryService {
	
	@Autowired
	private VehicleAllotQueryDao queryDao;
	
    @Autowired
    private ExcelGenerator excelService;

	@Override
	public PageInfoDto searchVehicleAllotQuery(Map<String, String> param) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		PageInfoDto pageInfoDto = queryDao.searchVehicleAllotQuery(param,loginInfo);
		return pageInfoDto;
	}

	@Override
	public Map<String, Object> findDetailById(String transferId) {
		List<Map> list = queryDao.findDetailById(transferId);
		return list.get(0);
	}

	@Override
	public void exportQueryInfo(Map<String, String> queryParam,HttpServletRequest request,HttpServletResponse response) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		List<Map> resultList = queryDao.exportVehicleAllotQuery(queryParam,loginInfo);
		Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String date = sdf.format(new Date());
		String excelName = "车辆调拨下载" + date;
		excelData.put(excelName, resultList);
		
		List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
		exportColumnList.add(new ExcelExportColumn("VIN", "VIN号"));
		exportColumnList.add(new ExcelExportColumn("SERIES_NAME", "车系"));
		exportColumnList.add(new ExcelExportColumn("GROUP_NAME", "车款"));
		exportColumnList.add(new ExcelExportColumn("COLOR_NAME", "颜色"));
		exportColumnList.add(new ExcelExportColumn("OUT_BIG_AREA", "调出大区"));
		exportColumnList.add(new ExcelExportColumn("OUT_SMALL_AREA", "调出小区"));
		exportColumnList.add(new ExcelExportColumn("OUT_DEALER_CODE", "调出经销商代码"));
		exportColumnList.add(new ExcelExportColumn("OUT_DEALER_NAME", "调出经销商"));
		exportColumnList.add(new ExcelExportColumn("OUT_SMALL_CHK_DATE", "小区审核日期"));
		exportColumnList.add(new ExcelExportColumn("OUT_BIG_CHK_DATE", "大区审核日期"));
		exportColumnList.add(new ExcelExportColumn("IN_BIG_AREA", "调入大区"));
		exportColumnList.add(new ExcelExportColumn("IN_SMALL_AREA", "调入小区"));
		exportColumnList.add(new ExcelExportColumn("IN_DEALER_CODE", "调入经销商代码"));
		exportColumnList.add(new ExcelExportColumn("IN_DEALER_NAME", "调入经销商名称"));
		exportColumnList.add(new ExcelExportColumn("IN_SMALL_CHK_DATE", "小区审核日期"));
		exportColumnList.add(new ExcelExportColumn("IN_BIG_CHK_DATE", "大区审核日期"));
		exportColumnList.add(new ExcelExportColumn("APPLY_DATE", "申请日期"));
		exportColumnList.add(new ExcelExportColumn("CHECK_STATUS", "审批状态",ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("THROUGH_DATE", "审批通过日期"));
		excelService.generateExcel(excelData, exportColumnList, excelName + ".xls", request, response);
		
	}

}
