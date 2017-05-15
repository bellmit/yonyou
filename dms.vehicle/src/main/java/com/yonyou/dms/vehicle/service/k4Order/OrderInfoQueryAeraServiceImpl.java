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
import com.yonyou.dms.vehicle.dao.k4Order.OrderInfoQueryAeraDao;

/**
* @author liujiming
* @date 2017年3月2日
*/
@Service
public class OrderInfoQueryAeraServiceImpl implements OrderInfoQueryAeraService{
	
	@Autowired
	private OrderInfoQueryAeraDao orderInfoDao;
	@Autowired
	private ExcelGenerator excelService;
	
	
	
	
	
	
	
	/**
	 * 整车订单明细查询下载
	 */
	@Override
	public void findOrderDetailQueryDownload(Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) throws ServiceBizException {
		List<Map> orderList = orderInfoDao.getOrderInfoDetailDownloadList(queryParam);
    	
		Map<String, List<Map>> excelData = new HashMap<>();
		excelData.put("整车订单汇总下载", orderList);
		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
		
		
		exportColumnList.add(new ExcelExportColumn("ORG_NAME","销售大区"));
		exportColumnList.add(new ExcelExportColumn("ORG_NAME_SMALL","销售小区"));
		exportColumnList.add(new ExcelExportColumn("SWT_CODE","SAP代码"));
		exportColumnList.add(new ExcelExportColumn("DEALER_CODE","经销商代码"));
		exportColumnList.add(new ExcelExportColumn("DEALER_NAME","经销商名称"));
		exportColumnList.add(new ExcelExportColumn("CREATE_DATE","资源池上传日期"));
		exportColumnList.add(new ExcelExportColumn("CREATE_DATE2","指派分配日期"));
		exportColumnList.add(new ExcelExportColumn("DEAL_ORDER_AFFIRM_DATE","订单提报日期"));
		exportColumnList.add(new ExcelExportColumn("ORDER_TYPE", "订单类型", ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("ORDER_STATUS","订单审批状态", ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("PAYMENT_TYPE","付款方式"/*, ExcelDataType.Oem_Dict*/));
		exportColumnList.add(new ExcelExportColumn("VIN","VIN"));
		exportColumnList.add(new ExcelExportColumn("VPC_PORT","VPC港口", ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("NODE_STATUS","车辆节点状态", ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("REMARK","车辆备注"));
		exportColumnList.add(new ExcelExportColumn("VEHICLE_USAGE","车辆用途"));
		exportColumnList.add(new ExcelExportColumn("MODEL_CODE","车型代码"));
		exportColumnList.add(new ExcelExportColumn("GROUP_NAME","车型描述"));
		exportColumnList.add(new ExcelExportColumn("COLOR_NAME","颜色"));
		exportColumnList.add(new ExcelExportColumn("TRIM_NAME","内饰"));
		exportColumnList.add(new ExcelExportColumn("ORDER_NO","整车订单号"));
		exportColumnList.add(new ExcelExportColumn("ETA","预计交付日期"));
		exportColumnList.add(new ExcelExportColumn("SWT_AFFIRM_DATE","中进收款时间"));
		exportColumnList.add(new ExcelExportColumn("COMMERCIAL_NO","商检单号"));
		exportColumnList.add(new ExcelExportColumn("CERTIFICATION_NO","进出口货物证明书号"));		
		excelService.generateExcel(excelData, exportColumnList, "整车订单明细下载.xls", request, response);
	
		
	}

}
