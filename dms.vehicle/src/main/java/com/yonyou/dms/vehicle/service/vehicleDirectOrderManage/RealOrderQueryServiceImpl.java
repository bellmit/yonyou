package com.yonyou.dms.vehicle.service.vehicleDirectOrderManage;

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
import com.yonyou.dms.vehicle.dao.vehicleDirectOrderManage.RealOrderQueryDao;

/**
 * 直销车实销查询
 * @author Administrator
 *
 */
@Service
public class RealOrderQueryServiceImpl implements RealOrderQueryService {
	@Autowired
	RealOrderQueryDao  queryDao;
	@Autowired
	private ExcelGenerator excelService;

	/**
	 * 查询
	 */
	@Override
	public PageInfoDto realOrderQuery(Map<String, String> queryParam) {
		return queryDao.realOrderQuery(queryParam);
	}
	
	/**
	 * 下载
	 */
	public void download(Map<String, String> queryParam, HttpServletResponse response, HttpServletRequest request) {
		Map<String, List<Map>> excelData = new HashMap<>();
		List<Map> list = queryDao.download(queryParam);
		// TODO
		excelData.put("资源分配备注下载", (List<Map>) list);

		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
		exportColumnList.add(new ExcelExportColumn("ORG_DESC2", "大区"));
		exportColumnList.add(new ExcelExportColumn("ORG_DESC3", "小区"));
		exportColumnList.add(new ExcelExportColumn("SWI_CODE", "SAP代码"));
		exportColumnList.add(new ExcelExportColumn("DEALER_CODE", "经销商代码"));
		exportColumnList.add(new ExcelExportColumn("DEALER_NAME", "经销商"));
		exportColumnList.add(new ExcelExportColumn("CTM_TYPE", "客户类型"));
		exportColumnList.add(new ExcelExportColumn("BIG_CUSTOMER_CODE", "大客户编码"));
		exportColumnList.add(new ExcelExportColumn("BIG_CUSTOMER_TYPE", "大客户名称"));
		exportColumnList.add(new ExcelExportColumn("CUSTOMER_NAME", "直销客户名称"));
		exportColumnList.add(new ExcelExportColumn("BRAND_CODE", "品牌"));
		exportColumnList.add(new ExcelExportColumn("SERIES_NAME", "车系"));
		exportColumnList.add(new ExcelExportColumn("MODEL_NAME", "车型"));
		exportColumnList.add(new ExcelExportColumn("GROUP_NAME", "车款"));
		exportColumnList.add(new ExcelExportColumn("MODEL_YEAR", "年款"));
		
		exportColumnList.add(new ExcelExportColumn("TRIM_NAME", "颜色内饰"));
		exportColumnList.add(new ExcelExportColumn("VIN", "VIN"));
		exportColumnList.add(new ExcelExportColumn("VEHICLE_USAGE", "车辆用途"));
		exportColumnList.add(new ExcelExportColumn("INVOICE_DATE1", "开票日期"));
		exportColumnList.add(new ExcelExportColumn("CREATE_DATE", "零售上报日期"));
		exportColumnList.add(new ExcelExportColumn("STORE_STATUS", "库存状态",ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("INVOICE_TYPE", "开票类型",ExcelDataType.Oem_Dict));
		
		excelService.generateExcel(excelData, exportColumnList, "资源分配备注下载.xls", request, response);
	}

	/**
	 * 明细
	 */
	@Override
	public List<Map> getXiangxi(String orderId) throws ServiceBizException {
		// TODO Auto-generated method stub
		return queryDao.getXiangxi(orderId);
	}
	
}
