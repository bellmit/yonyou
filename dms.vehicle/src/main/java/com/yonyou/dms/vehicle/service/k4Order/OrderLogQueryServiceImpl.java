package com.yonyou.dms.vehicle.service.k4Order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.service.excel.ExcelDataType;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.dao.k4Order.OrderLogQueryDao;

/**
 * @author liujiming
 * @date 2017年3月6日
 */
@Service
public class OrderLogQueryServiceImpl implements OrderLogQueryService {

	@Autowired
	private ExcelGenerator excelService;
	@Autowired
	private OrderLogQueryDao orderLogDao;

	@Override
	public void findOrderLogQueryDownload(Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) throws ServiceBizException {
		List<Map> orderList = orderLogDao.getOrderLogDownloadList(queryParam);
		Map<String, List<Map>> excelData = new HashMap<>();
		excelData.put("撤单日志下载", orderList);
		List<ExcelExportColumn> exportColumnList = new ArrayList<>();

		exportColumnList.add(new ExcelExportColumn("SWT_CODE", "SAP代码"));

		exportColumnList.add(new ExcelExportColumn("DEALER_CODE", "经销商代码"));
		exportColumnList.add(new ExcelExportColumn("DEALER_SHORTNAME", "经销商名称"));
		exportColumnList.add(new ExcelExportColumn("ORDER_NO", "订单号"));
		exportColumnList.add(new ExcelExportColumn("ORDER_TYPE", "订单类型", ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("BRAND_NAME", "品牌"));
		exportColumnList.add(new ExcelExportColumn("SERIES_NAME", "车系"));
		exportColumnList.add(new ExcelExportColumn("MODEL_NAME", "车型"));
		exportColumnList.add(new ExcelExportColumn("VIN", "VIN"));
		exportColumnList.add(new ExcelExportColumn("VPC_PORT", "VPC港口", ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("CANCEL_TYPE", "操作类型", ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("UPDATE_BY", "操作人"));
		exportColumnList.add(new ExcelExportColumn("UPDATE_DATE", "操作日期"));
		exportColumnList.add(new ExcelExportColumn("UNORDER_STATUS", "撤单状态", ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("ERROR_INFO", "失败原因"));
		excelService.generateExcel(excelData, exportColumnList, "撤单日志下载.xls", request, response);

	}

}
