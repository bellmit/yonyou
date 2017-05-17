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
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.dao.k4Order.OrderRepealQueryDao;
import com.yonyou.dms.vehicle.domains.DTO.k4Order.K4OrderDTO;

/**
 * @author liujiming
 * @date 2017年2月28日
 */
@Service
public class OrderRepealQueryServiceImpl implements OrderRepealQueryService {

	@Autowired
	private ExcelGenerator excelService;

	@Autowired
	private OrderRepealQueryDao orderReQuDao;
	// @Autowired
	// SI25 si25;

	/**
	 * 整车撤单撤单下载
	 */
	@Override
	public void findOrderRepealQueryDownload(Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) throws ServiceBizException {
		List<Map> orderList = orderReQuDao.findOrderRepealQueryDownload(queryParam);
		Map<String, List<Map>> excelData = new HashMap<>();
		excelData.put("整车订单撤单", orderList);
		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
		exportColumnList.add(new ExcelExportColumn("ORG_NAME", "销售区域"));
		exportColumnList.add(new ExcelExportColumn("DEALER_CODE", "经销商代码"));
		exportColumnList.add(new ExcelExportColumn("DEALER_NAME", "经销商名称"));
		exportColumnList.add(new ExcelExportColumn("ORDER_NO", "整车订单号"));
		exportColumnList.add(new ExcelExportColumn("ORDER_DATE", "订单提报日期"));
		exportColumnList.add(new ExcelExportColumn("ORDER_TYPE", "订单类型", ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("ORDER_STATUS", "订单状态", ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("VIN", "VIN"));
		exportColumnList.add(new ExcelExportColumn("VPC_PORT", "VPC港口", ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("NODE_STATUS", "车辆节点状态", ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("MODEL_CODE", "车型代码"));
		exportColumnList.add(new ExcelExportColumn("MODEL_NAME", "车型描述"));
		exportColumnList.add(new ExcelExportColumn("IS_LOCK", "是否锁定"));
		exportColumnList.add(new ExcelExportColumn("PAYMENT_TYPE",
				"付款方式"/* , ExcelDataType.Oem_Dict */));
		exportColumnList.add(new ExcelExportColumn("IS_SUC", "撤单状态"));
		exportColumnList.add(new ExcelExportColumn("ERROR_INFO", "失败原因"));
		excelService.generateExcel(excelData, exportColumnList, "整车订单撤单.xls", request, response);

	}

	/**
	 * 整车撤单明细查询
	 */
	@Override
	public Map<String, Object> findOrderRepealById(long id) throws ServiceBizException {
		List<Map> orderList = orderReQuDao.findOrderRepealById(id);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		for (Map map : orderList) {
			resultMap.put("DEALER_CODE", map.get("DEALER_CODE"));
			resultMap.put("DEALER_NAME", map.get("DEALER_NAME"));
			String orderYyp = map.get("ORDER_TYPE").toString();
			if (orderYyp != null) {
				int orderYype = Integer.parseInt(map.get("ORDER_TYPE").toString());
				if (OemDictCodeConstants.ORDER_TYPE_01.equals(orderYype)) {
					orderYyp = "现货";
				}
				if (OemDictCodeConstants.ORDER_TYPE_02.equals(orderYype)) {
					orderYyp = "期货";
				}
				if (OemDictCodeConstants.ORDER_TYPE_03.equals(orderYype)) {
					orderYyp = "指派";
				}
				if (OemDictCodeConstants.ORDER_TYPE_04.equals(orderYype)) {
					orderYyp = "天津爆炸港资源池";
				}
			}
			resultMap.put("ORDER_TYPE", orderYyp);
			String orderStatus = map.get("ORDER_STATUS").toString();
			int parseInt = Integer.parseInt(orderStatus);
			if (OemDictCodeConstants.ORDER_STATUS_01.equals(parseInt)) {
				orderStatus = "未提报(期货)";
			}
			if (OemDictCodeConstants.ORDER_STATUS_02.equals(parseInt)) {
				orderStatus = "待审核(期货)";
			}
			if (OemDictCodeConstants.ORDER_STATUS_03.equals(parseInt)) {
				orderStatus = "审批中(期货)";
			}
			if (OemDictCodeConstants.ORDER_STATUS_04.equals(parseInt)) {
				orderStatus = "定金未确认";
			}
			if (OemDictCodeConstants.ORDER_STATUS_05.equals(parseInt)) {
				orderStatus = "定金已确认";
			}
			if (OemDictCodeConstants.ORDER_STATUS_06.equals(parseInt)) {
				orderStatus = "订单已确认";
			}
			if (OemDictCodeConstants.ORDER_STATUS_07.equals(parseInt)) {
				orderStatus = "资源已分配";
			}
			if (OemDictCodeConstants.ORDER_STATUS_08.equals(parseInt)) {
				orderStatus = "已取消";
			}
			resultMap.put("ORDER_STATUS", orderStatus);
			resultMap.put("ORDER_NO", map.get("ORDER_NO"));
			resultMap.put("ORDER_DATE", map.get("ORDER_DATE"));
			resultMap.put("DEAL_ORDER_AFFIRM_DATE", map.get("DEAL_ORDER_AFFIRM_DATE"));
			resultMap.put("EXPECT_PORT_DATE", map.get("EXPECT_PORT_DATE"));
			resultMap.put("VIN", map.get("VIN"));
			resultMap.put("SERIES_CODE", map.get("SERIES_CODE"));
			resultMap.put("MODEL_CODE", map.get("MODEL_CODE"));
			resultMap.put("MODEL_NAME", map.get("MODEL_NAME"));
			resultMap.put("COLOR_CODE", map.get("COLOR_CODE"));
			resultMap.put("COLOR_NAME", map.get("COLOR_NAME"));
			resultMap.put("TRIM_CODE", map.get("TRIM_CODE"));
			resultMap.put("TRIM_NAME", map.get("TRIM_NAME"));
			resultMap.put("MODEL_YEAR", map.get("MODEL_YEAR"));
			String nodeStatus = map.get("NODE_STATUS").toString();
			int nodeStatu = Integer.parseInt(nodeStatus);
			if (OemDictCodeConstants.VEHICLE_NODE_01.equals(nodeStatu)) {
				nodeStatus = "ZFSC-工厂订单冻结";
			}
			if (OemDictCodeConstants.VEHICLE_NODE_02.equals(nodeStatu)) {
				nodeStatus = "ZVDU-工厂订单车辆数据更新";
			}
			if (OemDictCodeConstants.VEHICLE_NODE_03.equals(nodeStatu)) {
				nodeStatus = "ZSHP-海运在途";
			}
			if (OemDictCodeConstants.VEHICLE_NODE_04.equals(nodeStatu)) {
				nodeStatus = "ZGOR-车辆到港";
			}
			if (OemDictCodeConstants.VEHICLE_NODE_05.equals(nodeStatu)) {
				nodeStatus = "ZCBC-车辆清关";
			}
			if (OemDictCodeConstants.VEHICLE_NODE_06.equals(nodeStatu)) {
				nodeStatus = "ZVP8-入VPC仓库";
			}
			if (OemDictCodeConstants.VEHICLE_NODE_07.equals(nodeStatu)) {
				nodeStatus = "ZPDP-PDI检查通过";
			}
			if (OemDictCodeConstants.VEHICLE_NODE_08.equals(nodeStatu)) {
				nodeStatus = "ZBIL-一次开票";
			}
			if (OemDictCodeConstants.VEHICLE_NODE_09.equals(nodeStatu)) {
				nodeStatus = "ZRL2-订单SAP审核通过";
			}
			if (OemDictCodeConstants.VEHICLE_NODE_10.equals(nodeStatu)) {
				nodeStatus = "ZDRL-中进车款确认";
			}
			if (OemDictCodeConstants.VEHICLE_NODE_11.equals(nodeStatu)) {
				nodeStatus = "ZDRQ-换车入库";
			}
			if (OemDictCodeConstants.VEHICLE_NODE_12.equals(nodeStatu)) {
				nodeStatus = "ZPDU-发车出库";
			}
			if (OemDictCodeConstants.VEHICLE_NODE_13.equals(nodeStatu)) {
				nodeStatus = "ZPDF-PDI检查失败";
			}
			if (OemDictCodeConstants.VEHICLE_NODE_14.equals(nodeStatu)) {
				nodeStatus = "ZTPR-退车入库";
			}
			if (OemDictCodeConstants.VEHICLE_NODE_15.equals(nodeStatu)) {
				nodeStatus = "ZDLD-经销商验收";
			}
			if (OemDictCodeConstants.VEHICLE_NODE_16.equals(nodeStatu)) {
				nodeStatus = "已实销";
			}
			if (OemDictCodeConstants.VEHICLE_NODE_17.equals(nodeStatu)) {
				nodeStatus = "ZFSN-工厂订单冻结";
			}
			if (OemDictCodeConstants.VEHICLE_NODE_18.equals(nodeStatu)) {
				nodeStatus = "ZRL1-资源已分配";
			}
			if (OemDictCodeConstants.VEHICLE_NODE_19.equals(nodeStatu)) {
				nodeStatus = "ZDRR-经销商订单确认";
			}
			if (OemDictCodeConstants.VEHICLE_NODE_20.equals(nodeStatu)) {
				nodeStatus = "ZDRV-中进车款取消";
			}

			resultMap.put("NODE_STATUS", nodeStatus);
			resultMap.put("INVOICE_NO", map.get("INVOICE_NO"));
			resultMap.put("SOLD_TO", map.get("SOLD_TO"));

		}

		return resultMap;
	}

	@Override
	public void queryPass(K4OrderDTO k4OrderDTO) {
		orderReQuDao.pass(k4OrderDTO);
	}

}
