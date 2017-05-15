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
import com.yonyou.dms.framework.service.excel.ExcelRead;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.dao.k4Order.DealerCancelAuditDao;
import com.yonyou.dms.vehicle.domains.DTO.orderManager.ResourceOrderUploadDTO;

/**
 *
 * 
 * @author liujiming
 * @date 2017年2月24日
 */
@Service
public class DealerCancleAuditServiceImpl implements DealerCancleAuditService {

	@Autowired
	private ExcelRead<ResourceOrderUploadDTO> excelReadService;
	@Autowired
	private DealerCancelAuditDao deaCanDao;
	@Autowired
	private ExcelGenerator excelService;

	@Override
	public void findCancleAuditDownload(Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) throws ServiceBizException {
		List<Map> orderList = deaCanDao.findCancelAuditApplyDownloadList(queryParam);
		Map<String, List<Map>> excelData = new HashMap<>();
		excelData.put("经销商撤单审核（大区）", orderList);
		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
		exportColumnList.add(new ExcelExportColumn("ORG_NAME", "销售大区"));
		exportColumnList.add(new ExcelExportColumn("ORG_NAME_SMALL", "销售小区"));
		exportColumnList.add(new ExcelExportColumn("DEALER_CODE", "经销商代码"));
		exportColumnList.add(new ExcelExportColumn("DEALER_SHORTNAME", "经销商名称"));
		exportColumnList.add(new ExcelExportColumn("ORDER_TYPE", "订单类型", ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("PAYMENT_TYPE", "付款方式", ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("VIN", "VIN"));
		exportColumnList.add(new ExcelExportColumn("BRAND_CODE", "品牌"));
		exportColumnList.add(new ExcelExportColumn("SERIES_NAME", "车系"));
		exportColumnList.add(new ExcelExportColumn("GROUP_NAME", "车款"));
		exportColumnList.add(new ExcelExportColumn("COLOR_NAME", "外饰颜色"));
		exportColumnList.add(new ExcelExportColumn("TRIM_NAME", "内饰颜色"));
		exportColumnList.add(new ExcelExportColumn("REMARK", "车辆分配备注", ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("VEHICLE_USAGE", "车辆用途"));
		exportColumnList.add(new ExcelExportColumn("OTHER_REMARK", "其它备注"));
		exportColumnList.add(new ExcelExportColumn("IS_LOCK", "是否锁定", ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("CREATE_DATE", "申请日期"));
		exportColumnList.add(new ExcelExportColumn("IS_SUC", "撤单状态"));
		exportColumnList.add(new ExcelExportColumn("ERROR_INFO", "失败原因"));
		exportColumnList.add(new ExcelExportColumn("AUDIT_STATUS", "审核状态", ExcelDataType.Oem_Dict));
		excelService.generateExcel(excelData, exportColumnList, "经销商审核查询信息.xls", request, response);

	}

}
