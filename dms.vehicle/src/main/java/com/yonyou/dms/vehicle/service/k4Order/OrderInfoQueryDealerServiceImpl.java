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
import com.yonyou.dms.vehicle.dao.k4Order.OrderInfoQueryDealerDao;

/**
 * @author liujiming
 * @date 2017年3月8日
 */
@Service
public class OrderInfoQueryDealerServiceImpl implements OrderInfoQueryDealerService {

	@Autowired
	private ExcelGenerator excelService;

	@Autowired
	private OrderInfoQueryDealerDao orderDealerDao;

	@Override
	public void findOrderInfoQueryDealerDownload(Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) throws ServiceBizException {
		List<Map> orderList = orderDealerDao.getOrderInfoDealerDownloadList(queryParam);

		Map<String, List<Map>> excelData = new HashMap<>();
		excelData.put("整车订单下载(经销商)", orderList);
		List<ExcelExportColumn> exportColumnList = new ArrayList<>();

		exportColumnList.add(new ExcelExportColumn("ORDER_TYPE", "资源权属", ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("BIG_AREA", "销售大区"));
		exportColumnList.add(new ExcelExportColumn("SMALL_AREA", "销售小区"));
		exportColumnList.add(new ExcelExportColumn("CTCAI_CODE", "经销商编号"));
		exportColumnList.add(new ExcelExportColumn("DEALER_CODE", "经销商代码"));
		exportColumnList.add(new ExcelExportColumn("DEALER_NAME", "经销商名称"));
		exportColumnList.add(new ExcelExportColumn("VIN", "VIN"));
		exportColumnList.add(new ExcelExportColumn("BRAND_CODE", "品牌"));
		exportColumnList.add(new ExcelExportColumn("SERIES_NAME", "车系"));
		exportColumnList.add(new ExcelExportColumn("GROUP_NAME", "车款"));
		exportColumnList.add(new ExcelExportColumn("MODEL_YEAR", "年款"));
		exportColumnList.add(new ExcelExportColumn("COLOR_CODE", "外饰代码"));
		exportColumnList.add(new ExcelExportColumn("COLOR_NAME", "外饰颜色"));
		exportColumnList.add(new ExcelExportColumn("TRIM_CODE", "内饰代码"));
		exportColumnList.add(new ExcelExportColumn("TRIM_NAME", "内饰颜色"));
		exportColumnList.add(new ExcelExportColumn("FACTORY_OPTIONS", "工厂选装"));
		exportColumnList.add(new ExcelExportColumn("VEHICLE_USAGE", "车辆用途"));
		exportColumnList.add(new ExcelExportColumn("REMARK", "车辆分配备注", ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("OTHER_REMARK", "其它备注"));
		exportColumnList.add(new ExcelExportColumn("IS_LOCK", "是否锁定"));
		exportColumnList.add(new ExcelExportColumn("PAYMENT_TYPE", "付款方式", ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("NODE_STATUS", "节点状态", ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("ORDER_STATUS", "订单状态", ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("CG_ORDER_NO", "承兑/融资订单编号"));
		exportColumnList.add(new ExcelExportColumn("INVOICE_NO", "发票号"));
		exportColumnList.add(new ExcelExportColumn("ARRIVE_PORT_DATE", "到港日期"));
		exportColumnList.add(new ExcelExportColumn("UNORDER_EVIDENCE_DATE", "关单出证日期"));
		exportColumnList.add(new ExcelExportColumn("MACHECK_EVIDENCE_DATE", "商检单出证日期"));
		exportColumnList.add(new ExcelExportColumn("COMMON_APPOINT_DATE", "资源池上传日期"));
		exportColumnList.add(new ExcelExportColumn("DEAL_ORDER_AFFIRM_DATE", "订单确认日期"));
		exportColumnList.add(new ExcelExportColumn("PAYMENG_DATE", "付款日期"));
		exportColumnList.add(new ExcelExportColumn("PLANCE_ORDER_DATE", "下单日期"));
		exportColumnList.add(new ExcelExportColumn("STOCKOUT_DATE", "出库日期"));
		exportColumnList.add(new ExcelExportColumn("ONTHEWAY_POSITION", "在途位置"));
		exportColumnList.add(new ExcelExportColumn("ARRIVE_DATE", "车辆到店日期（物流上报）"));
		exportColumnList.add(new ExcelExportColumn("ARRIVE_DATE2", "经销商收车日期"));
		exportColumnList.add(new ExcelExportColumn("NVDR_DATE", "零售交车日期"));
		exportColumnList.add(new ExcelExportColumn("SINGLE_POST_DATE", "单证邮寄日期"));
		exportColumnList.add(new ExcelExportColumn("SINGLE_POST_EMS", "单证邮寄单号"));
		exportColumnList.add(new ExcelExportColumn("CLORDER_SCANNING_NO", "关单扫描件"));
		exportColumnList.add(new ExcelExportColumn("SCORDER_SCANNING_NO", "商检单扫描件"));
		exportColumnList.add(new ExcelExportColumn("STANDART_CAR_PRICE", "标准车价"));
		exportColumnList.add(new ExcelExportColumn("USE_REBATE", "使用返利"));
		exportColumnList.add(new ExcelExportColumn("FINAL_CAR_REBATE", "最终应付车价"));
		exportColumnList.add(new ExcelExportColumn("BALANCE", "现金余额"));
		exportColumnList.add(new ExcelExportColumn("CTCAI_ACCEPTANCES_BALANCE", "中进承兑余额"));
		exportColumnList.add(new ExcelExportColumn("DEALER_ACCEPTANCES_BALANCE", "经销商承兑余额"));
		exportColumnList.add(new ExcelExportColumn("CTCAI_ACCEPTANCES_DISCOUNT_BALANCE", "中进承兑贴息余额"));
		exportColumnList.add(new ExcelExportColumn("DEALER_ACCEPTANCES_DISCOUNT_BALANCE", "经销商承兑贴息余额"));
		exportColumnList.add(new ExcelExportColumn("REBATES_BALANCE", "返利余额"));
		exportColumnList.add(new ExcelExportColumn("WHOLESALE_PRICE", "中进采购价"));
		exportColumnList.add(new ExcelExportColumn("RETAIL_PRICE", "建议零售价"));

		excelService.generateExcel(excelData, exportColumnList, "整车订单下载(经销商).xls", request, response);

	}

	/**
	 * 整车撤单(经销商) 详细车籍查询
	 */
	@Override
	public Map<String, Object> findOrderVehicleByVIN(String vin) throws ServiceBizException {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		List<Map> orderVehicleList = orderDealerDao.getDealerVehicleDetailListByVIN(vin);
		List<Map> orderChangeList = orderDealerDao.getDealerVehicleChangeDetailListByVIN(vin);
		for (Map map : orderVehicleList) {
			resultMap.put("VIN", map.get("VIN"));
			resultMap.put("ENGINE_NO", map.get("ENGINE_NO"));
			resultMap.put("REMARK", map.get("REMARK"));
			resultMap.put("BRAND_NAME", map.get("BRAND_NAME"));
			resultMap.put("SERIES_NAME", map.get("SERIES_NAME"));
			resultMap.put("MODEL_NAME", map.get("MODEL_NAME"));
			resultMap.put("COLOR_NAME", map.get("COLOR_NAME"));
			resultMap.put("TRIM_NAME", map.get("TRIM_NAME"));
		}
		for (Map map : orderChangeList) {
			String changeCode = map.get("CHANGE_CODE").toString();
			int changeCod = Integer.parseInt(changeCode);
			if (OemDictCodeConstants.VEHICLE_CHANGE_TYPE_01.equals(changeCod)) {
				changeCode = "装船登记";
			}
			if (OemDictCodeConstants.VEHICLE_CHANGE_TYPE_02.equals(changeCod)) {
				changeCode = "ZGOR-车辆到港";
			}
			if (OemDictCodeConstants.VEHICLE_CHANGE_TYPE_03.equals(changeCod)) {
				changeCode = "点检";
			}
			if (OemDictCodeConstants.VEHICLE_CHANGE_TYPE_04.equals(changeCod)) {
				changeCode = "通关完成";
			}
			if (OemDictCodeConstants.VEHICLE_CHANGE_TYPE_05.equals(changeCod)) {
				changeCode = "ZVP8-入VPC仓库";
			}
			if (OemDictCodeConstants.VEHICLE_CHANGE_TYPE_06.equals(changeCod)) {
				changeCode = "ZPDP-PDI检查通过";
			}
			if (OemDictCodeConstants.VEHICLE_CHANGE_TYPE_07.equals(changeCod)) {
				changeCode = "ZPDU-发车出库";
			}
			if (OemDictCodeConstants.VEHICLE_CHANGE_TYPE_09.equals(changeCod)) {
				changeCode = "经销商库存";
			}
			if (OemDictCodeConstants.VEHICLE_CHANGE_TYPE_10.equals(changeCod)) {
				changeCode = "已实销";
			}
			if (OemDictCodeConstants.VEHICLE_CHANGE_TYPE_11.equals(changeCod)) {
				changeCode = "退车出库";
			}
			if (OemDictCodeConstants.VEHICLE_CHANGE_TYPE_12.equals(changeCod)) {
				changeCode = "ZTPR-退车入库";
			}
			if (OemDictCodeConstants.VEHICLE_CHANGE_TYPE_13.equals(changeCod)) {
				changeCode = "";
			}
			if (OemDictCodeConstants.VEHICLE_CHANGE_TYPE_14.equals(changeCod)) {
				changeCode = "调拨出库";
			}
			if (OemDictCodeConstants.VEHICLE_CHANGE_TYPE_15.equals(changeCod)) {
				changeCode = "ZFSC-工厂订单冻结";
			}
			if (OemDictCodeConstants.VEHICLE_CHANGE_TYPE_16.equals(changeCod)) {
				changeCode = "ZVDU-工厂订单车辆数据更新";
			}
			if (OemDictCodeConstants.VEHICLE_CHANGE_TYPE_17.equals(changeCod)) {
				changeCode = "ZSHP-海运在途";
			}
			if (OemDictCodeConstants.VEHICLE_CHANGE_TYPE_18.equals(changeCod)) {
				changeCode = "ZCBC-车辆清关";
			}
			if (OemDictCodeConstants.VEHICLE_CHANGE_TYPE_19.equals(changeCod)) {
				changeCode = "ZBIL-一次开票";
			}
			if (OemDictCodeConstants.VEHICLE_CHANGE_TYPE_20.equals(changeCod)) {
				changeCode = "ZDRL-中进车款确认";
			}
			if (OemDictCodeConstants.VEHICLE_CHANGE_TYPE_21.equals(changeCod)) {
				changeCode = "ZDRQ-换车入库";
			}
			if (OemDictCodeConstants.VEHICLE_CHANGE_TYPE_22.equals(changeCod)) {
				changeCode = "ZPDF-PDI检查失败";
			}
			if (OemDictCodeConstants.VEHICLE_CHANGE_TYPE_23.equals(changeCod)) {
				changeCode = "ZFSN-工厂订单冻结";
			}
			if (OemDictCodeConstants.VEHICLE_CHANGE_TYPE_24.equals(changeCod)) {
				changeCode = "ZRL1-资源已分配";
			}
			if (OemDictCodeConstants.VEHICLE_CHANGE_TYPE_25.equals(changeCod)) {
				changeCode = "ZDRR-经销商订单确认";
			}
			if (OemDictCodeConstants.VEHICLE_CHANGE_TYPE_26.equals(changeCod)) {
				changeCode = "ZDRV-中进车款取消";
			}
			if (OemDictCodeConstants.VEHICLE_CHANGE_TYPE_27.equals(changeCod)) {
				changeCode = "经销商订单撤单";
			}
			resultMap.put("CHANGE_CODE", changeCode);
			resultMap.put("CHANGE_DATE", map.get("CHANGE_DATE"));
			resultMap.put("CHANGE_DESC", map.get("CHANGE_DESC"));
			resultMap.put("DEALER_NAME", map.get("DEALER_NAME"));
		}
		return resultMap;
	}

}
