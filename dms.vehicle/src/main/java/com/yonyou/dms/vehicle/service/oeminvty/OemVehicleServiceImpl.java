package com.yonyou.dms.vehicle.service.oeminvty;

import java.text.DateFormat;
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

import com.yonyou.dms.common.domains.PO.basedata.TmOrgPO;
import com.yonyou.dms.common.domains.PO.basedata.TmVehiclePO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelDataType;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.vehicle.dao.oeminvty.OemVehicleDao;

@Service
public class OemVehicleServiceImpl implements OemVehicleService {
	@Autowired
	private OemVehicleDao dao;
	@Autowired
	private ExcelGenerator excelService;

	@Override
	public PageInfoDto oemVehicleDetailedQuery(Map<String, String> queryParam) {

		return dao.getVehicleQueryList(queryParam);
	}

	@Override
	public void areaVehicleDownload(Map<String, String> queryParam, HttpServletResponse response,
			HttpServletRequest request) {
		Map<String, List<Map>> excelData = new HashMap<>();
		List<Map> list = dao.download(queryParam);
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		String format = df.format(new Date());
		excelData.put("车辆综合查询查询下载", list);
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		TmOrgPO t = TmOrgPO.findFirst("ORG_ID=?", loginInfo.getOrgId());
		String string = t.get("ORG_LEVEL").toString();
		int parseInt = Integer.parseInt(string);
		int flag = parseInt == 1 ? 1 : 0;
		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
		exportColumnList.add(new ExcelExportColumn("RESOURCE", "资源权属"));
		exportColumnList.add(new ExcelExportColumn("ORDER_TYPE", "分配类型", ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("RESOURCE_TYPE", "资源池类型"));
		exportColumnList.add(new ExcelExportColumn("BIG_AREA", "大区"));
		exportColumnList.add(new ExcelExportColumn("SMALL_AREA", "小区"));
		exportColumnList.add(new ExcelExportColumn("DEALER_CODE", "经销商代码"));
		exportColumnList.add(new ExcelExportColumn("DEALER_CODE", "经销商编号"));
		exportColumnList.add(new ExcelExportColumn("DEALER_NAME", "经销商名称"));
		exportColumnList.add(new ExcelExportColumn("VIN", "VIN"));
		if (flag == 1) {
			exportColumnList.add(new ExcelExportColumn("VPC_PORT", "VPC港口", ExcelDataType.Oem_Dict));
		}
		exportColumnList.add(new ExcelExportColumn("MODEL_YEAR", "年款"));
		exportColumnList.add(new ExcelExportColumn("BRAND_CODE", "品牌"));
		exportColumnList.add(new ExcelExportColumn("SERIES_NAME", "车系"));
		exportColumnList.add(new ExcelExportColumn("GROUP_NAME", "车款"));
		exportColumnList.add(new ExcelExportColumn("COLOR_CODE", "外饰代码"));
		exportColumnList.add(new ExcelExportColumn("COLOR_NAME", "外饰颜色"));
		exportColumnList.add(new ExcelExportColumn("TRIM_CODE", "内饰代码"));
		exportColumnList.add(new ExcelExportColumn("TRIM_NAME", "内饰颜色"));
		exportColumnList.add(new ExcelExportColumn("VEHICLE_USAGE", "车辆用途"));
		if (flag == 1) {
			exportColumnList.add(new ExcelExportColumn("IS_SUPPORT", "任务内/外"));
			exportColumnList.add(new ExcelExportColumn("SPECIAL_REMARK", "OTD备注", ExcelDataType.Oem_Dict));
		}
		exportColumnList.add(new ExcelExportColumn("REMARK", "车辆分配备注", ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("OTHER_REMARK", "其它备注"));
		exportColumnList.add(new ExcelExportColumn("PAYMENT_TYPE", "付款方式", ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("NODE_STATUS", "车辆节点", ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("ORDER_STATUSK", "订单状态"));
		exportColumnList.add(new ExcelExportColumn("CG_ORDER_NO", "承兑/融资订单编号"));
		exportColumnList.add(new ExcelExportColumn("INVOICE_NO", "发票号"));
		if (flag == 1) {
			exportColumnList.add(new ExcelExportColumn("ARRIVE_PORT_DATE", "到港日期"));
			exportColumnList.add(new ExcelExportColumn("ORG_STORAGE_DATE", "合格资源"));
			exportColumnList.add(new ExcelExportColumn("ZBIL_DATE", "一级批发"));

		}
		exportColumnList.add(new ExcelExportColumn("UNORDER_EVIDENCE_DATE", "关单出证日期"));
		exportColumnList.add(new ExcelExportColumn("MACHECK_EVIDENCE_DATE", "商检单出证日期"));
		exportColumnList.add(new ExcelExportColumn("COMMON_APPOINT_DATE", "资源池上传日期"));
		if (flag == 1) {
			exportColumnList.add(new ExcelExportColumn("WHOLESALE_PRICE", "中进采购价"));
		}
		exportColumnList.add(new ExcelExportColumn("IS_ADVANCE", "是否垫付"));
		exportColumnList.add(new ExcelExportColumn("STANDART_CAR_PRICE", "标准车价"));
		exportColumnList.add(new ExcelExportColumn("USE_REBATE", "使用返利"));
		exportColumnList.add(new ExcelExportColumn("FINAL_CAR_REBATE", "最终应付车价"));
		exportColumnList.add(new ExcelExportColumn("BALANCE", "现金余额"));
		exportColumnList.add(new ExcelExportColumn("CTCAI_ACCEPTANCES_BALANCE", "中进承兑余额"));
		exportColumnList.add(new ExcelExportColumn("DEALER_ACCEPTANCES_BALANCE", "经销商承兑余"));
		exportColumnList.add(new ExcelExportColumn("CTCAI_ACCEPTANCES_DISCOUNT_BALANCE", "中进承兑贴息余额"));
		exportColumnList.add(new ExcelExportColumn("DEALER_ACCEPTANCES_DISCOUNT_BALANCE", "经销商承兑贴息余额"));
		exportColumnList.add(new ExcelExportColumn("DEAL_ORDER_AFFIRM_DATE", "订单确认日期"));
		exportColumnList.add(new ExcelExportColumn("PAYMENG_DATE", "付款日期"));
		exportColumnList.add(new ExcelExportColumn("START_SHIPMENT_DATE", "起运时间"));
		exportColumnList.add(new ExcelExportColumn("ARRIVE_DATE", "经销商收车日期"));
		exportColumnList.add(new ExcelExportColumn("NVDR_DATE", "零售交车日期"));
		exportColumnList.add(new ExcelExportColumn("CLORDER_SCANNING_NO", "关单扫描件"));
		exportColumnList.add(new ExcelExportColumn("SCORDER_SCANNING_NO", "商检单扫描件"));
		excelService.generateExcel(excelData, exportColumnList, "综合订单查询" + format + ".xls", request, response);

	}

	@Override
	public void download(Map<String, String> queryParam, HttpServletResponse response, HttpServletRequest request) {

		Map<String, List<Map>> excelData = new HashMap<>();
		List<Map> list = dao.download(queryParam);
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		String format = df.format(new Date());
		excelData.put("综合订单查询下载", list);

		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		TmOrgPO t = TmOrgPO.findFirst("ORG_ID=?", loginInfo.getOrgId());
		String string = t.get("ORG_LEVEL").toString();
		int parseInt = Integer.parseInt(string);
		int flag = parseInt == 1 ? 1 : 0;
		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
		exportColumnList.add(new ExcelExportColumn("RESOURCE", "资源权属"));
		exportColumnList.add(new ExcelExportColumn("ORDER_TYPE", "分配类型", ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("RESOURCE_TYPE", "资源池类型"));
		exportColumnList.add(new ExcelExportColumn("BIG_AREA", "大区"));
		exportColumnList.add(new ExcelExportColumn("SMALL_AREA", "小区"));
		exportColumnList.add(new ExcelExportColumn("DEALER_CODE", "批售经销商代码"));
		exportColumnList.add(new ExcelExportColumn("DEALER_CODE", "批售经销商编号"));
		exportColumnList.add(new ExcelExportColumn("DEALER_NAME", "批售经销商名称"));
		exportColumnList.add(new ExcelExportColumn("VIN", "VIN"));
		if (flag == 1) {
			exportColumnList.add(new ExcelExportColumn("VPC_PORT", "VPC港口", ExcelDataType.Oem_Dict));
		}
		exportColumnList.add(new ExcelExportColumn("MODEL_YEAR", "年款"));
		exportColumnList.add(new ExcelExportColumn("BRAND_CODE", "品牌"));
		exportColumnList.add(new ExcelExportColumn("SERIES_NAME", "车系"));
		exportColumnList.add(new ExcelExportColumn("MODEL_CODE", "CPOS"));
		exportColumnList.add(new ExcelExportColumn("GROUP_NAME", "车款"));
		exportColumnList.add(new ExcelExportColumn("COLOR_CODE", "外饰代码"));
		exportColumnList.add(new ExcelExportColumn("COLOR_NAME", "外饰颜色"));
		exportColumnList.add(new ExcelExportColumn("TRIM_CODE", "内饰代码"));
		exportColumnList.add(new ExcelExportColumn("TRIM_NAME", "内饰颜色"));
		exportColumnList.add(new ExcelExportColumn("FACTORY_OPTIONS", "工厂选装"));
		exportColumnList.add(new ExcelExportColumn("VEHICLE_USAGE", "车辆用途"));
		if (flag == 1) {
			exportColumnList.add(new ExcelExportColumn("IS_SUPPORT", "任务内/外"));
			exportColumnList.add(new ExcelExportColumn("SPECIAL_REMARK", "OTD备注", ExcelDataType.Oem_Dict));
		}
		exportColumnList.add(new ExcelExportColumn("REMARK", "车辆分配备注", ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("OTHER_REMARK", "其它备注"));
		exportColumnList.add(new ExcelExportColumn("IS_LOCK", "锁定（是/否）"));

		exportColumnList.add(new ExcelExportColumn("PAYMENT_TYPE", "付款方式", ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("NODE_STATUS", "车辆节点", ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("ORDER_STATUSK", "订单状态"));
		exportColumnList.add(new ExcelExportColumn("CG_ORDER_NO", "承兑/融资订单编号"));
		exportColumnList.add(new ExcelExportColumn("INVOICE_NO", "发票号"));
		if (flag == 1) {
			exportColumnList.add(new ExcelExportColumn("ARRIVE_PORT_DATE", "到港日期"));
			exportColumnList.add(new ExcelExportColumn("ORG_STORAGE_DATE", "合格资源"));
			exportColumnList.add(new ExcelExportColumn("ZBIL_DATE", "一级批发"));

		}
		exportColumnList.add(new ExcelExportColumn("UNORDER_EVIDENCE_DATE", "关单出证日期"));
		exportColumnList.add(new ExcelExportColumn("MACHECK_EVIDENCE_DATE", "商检单出证日期"));
		exportColumnList.add(new ExcelExportColumn("COMMON_APPOINT_DATE", "资源池上传日期"));
		exportColumnList.add(new ExcelExportColumn("DEAL_ORDER_AFFIRM_DATE", "订单确认日期"));
		exportColumnList.add(new ExcelExportColumn("PAYMENG_DATE", "付款日期"));
		exportColumnList.add(new ExcelExportColumn("PLANCE_ORDER_DATE", "发货单日期"));

		// exportColumnList.add(new ExcelExportColumn("PLANCE_ORDER_DATE",
		// "发货单日期"));
		exportColumnList.add(new ExcelExportColumn("STOCKOUT_DATE", "出库日期"));
		exportColumnList.add(new ExcelExportColumn("START_SHIPMENT_DATE", "起运时间"));
		exportColumnList.add(new ExcelExportColumn("ONTHEWAY_POSITION", "在途位置"));
		exportColumnList.add(new ExcelExportColumn("ARRIVE_DATE", "车辆到店日期（物流上报）"));
		exportColumnList.add(new ExcelExportColumn("ARRIVE_DATE", "经销商收车日期"));
		exportColumnList.add(new ExcelExportColumn("NVDR_DATE", "零售交车日期"));
		exportColumnList.add(new ExcelExportColumn("RETAIL_DEALER_NAME", "零售经销商"));
		exportColumnList.add(new ExcelExportColumn("UPDATE_DATE", "零售上报审批日期"));
		exportColumnList.add(new ExcelExportColumn("SINGLE_POST_DATE", "单证邮寄日期"));
		exportColumnList.add(new ExcelExportColumn("SINGLE_POST_EMS", "单证邮寄单号"));
		exportColumnList.add(new ExcelExportColumn("CLORDER_SCANNING_NO", "关单扫描件"));
		exportColumnList.add(new ExcelExportColumn("SCORDER_SCANNING_NO", "商检单扫描件"));
		exportColumnList.add(new ExcelExportColumn("IS_ADVANCE", "是否垫付"));
		exportColumnList.add(new ExcelExportColumn("STANDART_CAR_PRICE", "标准车价"));
		exportColumnList.add(new ExcelExportColumn("USE_REBATE", "使用返利"));
		exportColumnList.add(new ExcelExportColumn("FINAL_CAR_REBATE", "最终应付车价"));
		exportColumnList.add(new ExcelExportColumn("BALANCE", "现金余额"));
		exportColumnList.add(new ExcelExportColumn("CTCAI_ACCEPTANCES_BALANCE", "中进承兑余额"));
		exportColumnList.add(new ExcelExportColumn("DEALER_ACCEPTANCES_BALANCE", "经销商承兑余"));
		exportColumnList.add(new ExcelExportColumn("CTCAI_ACCEPTANCES_DISCOUNT_BALANCE", "中进承兑贴息余额"));
		exportColumnList.add(new ExcelExportColumn("DEALER_ACCEPTANCES_DISCOUNT_BALANCE", "经销商承兑贴息余额"));
		exportColumnList.add(new ExcelExportColumn("REBATES_BALANCE", "返利余额"));
		if (flag == 1) {
			exportColumnList.add(new ExcelExportColumn("WHOLESALE_PRICE", "中进采购价"));
		}

		exportColumnList.add(new ExcelExportColumn("RETAIL_PRICE", "建议零售价"));
		excelService.generateExcel(excelData, exportColumnList, "综合订单查询下载" + format + ".xls", request, response);
	}

	@Override
	public Map<String, Object> oemVehicleVinDetailQuery(Long id) {
		Map<String, Object> map = new HashMap();
		String vehicleId = CommonUtils.checkNull(id);
		TmVehiclePO tvPo = new TmVehiclePO();
		TmVehiclePO tvP = tvPo.findById(vehicleId);
		String vin = (String) tvP.get("VIN");
		List<Map> list = dao.oemVehicleVinDetailQuery(vin);
		for (Map map2 : list) {
			map.put("VIN", tvP.get("VIN"));
			map.put("VEHICLE_ID", map2.get("VEHICLE_ID"));
			map.put("ENGINE_NO", map2.get("ENGINE_NO"));
			map.put("REMARK", map2.get("REMARK"));
			map.put("BRAND_NAME", map2.get("BRAND_NAME"));
			map.put("SERIES_NAME", map2.get("SERIES_NAME"));
			map.put("MODEL_NAME", map2.get("MODEL_NAME"));
			map.put("COLOR_NAME", map2.get("COLOR_NAME"));
			map.put("TRIM_NAME", map2.get("TRIM_NAME"));
		}
		return map;
	}

	@Override
	public PageInfoDto oemVehicleDetailed(Map<String, String> queryParam, Long id) {
		String vehicleId = CommonUtils.checkNull(id);
		TmVehiclePO tvP = TmVehiclePO.findById(vehicleId);

		String vin = (String) tvP.get("VIN");
		return dao.oemVehicleDetailed(vin);
	}

	@Override
	public PageInfoDto oemVehicleDetailed(Map<String, String> queryParam) {

		String vehicleId = CommonUtils.checkNull(queryParam.get("id"));

		TmVehiclePO tvP = TmVehiclePO.findById(vehicleId);

		String vin = (String) tvP.get("VIN");
		return dao.oemVehicleDetailed(vin);
	}

	@Override
	public PageInfoDto oemVehicleQuery(Map<String, String> queryParam) {

		return dao.oemVehicleQuery(queryParam);
	}

	@Override
	public void oemVehicleQueryDownload(Map<String, String> queryParam, HttpServletResponse response,
			HttpServletRequest request) {

		Map<String, List<Map>> excelData = new HashMap<>();
		List<Map> list = dao.oemVehicleQueryDownload(queryParam);

		excelData.put("生产订单跟踪下载", list);

		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
		exportColumnList.add(new ExcelExportColumn("VIN", "VIN"));
		exportColumnList.add(new ExcelExportColumn("BRAND_NAME", "品牌"));
		exportColumnList.add(new ExcelExportColumn("SERIES_CODE", "车系代码"));
		exportColumnList.add(new ExcelExportColumn("SERIES_NAME", "车系"));
		exportColumnList.add(new ExcelExportColumn("MODEL_CODE", "CPOS"));
		exportColumnList.add(new ExcelExportColumn("MODEL_NAME", "CPOS描述"));
		exportColumnList.add(new ExcelExportColumn("MODEL_YEAR", "年款"));
		// exportColumnList.add(new ExcelExportColumn("VPC_PORT", "VPC港口",
		// ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("STANDARD_OPTION", "标准配置"));
		exportColumnList.add(new ExcelExportColumn("FACTORY_OPTIONS", "工厂配置"));
		exportColumnList.add(new ExcelExportColumn("LOCAL_OPTION", "本地配置"));
		exportColumnList.add(new ExcelExportColumn("COLOR_NAME", "颜色"));
		exportColumnList.add(new ExcelExportColumn("TRIM_NAME", "内饰"));
		exportColumnList.add(new ExcelExportColumn("ENGINE_NO", "发动机号"));
		exportColumnList.add(new ExcelExportColumn("NODE_STATUS", "车辆节点", ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("PREDICT_OFFLINE_DATE", "预计下线日期"));
		exportColumnList.add(new ExcelExportColumn("UPDATE_DATE", "更新日期"));
		exportColumnList.add(new ExcelExportColumn("PREDICT_STORAGE_DATE", "预计入库日期"));
		excelService.generateExcel(excelData, exportColumnList, "生产订单跟踪下载.xls", request, response);
	}

	@Override
	public PageInfoDto vehicleDetailQuery(Map<String, String> queryParam) {

		return dao.vehicleDetailQuery(queryParam);
	}

	@Override
	public void doDownload(Map<String, String> queryParam, HttpServletResponse response, HttpServletRequest request) {
		Map<String, List<Map>> excelData = new HashMap<>();
		List<Map> list = dao.doDownload(queryParam);

		excelData.put("详细车籍查询", list);

		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
		exportColumnList.add(new ExcelExportColumn("DEALER_CODE", "经销商代码"));
		exportColumnList.add(new ExcelExportColumn("DEALER_SHORTNAME", "经销商名称"));
		exportColumnList.add(new ExcelExportColumn("BRAND_NAME", "品牌"));
		exportColumnList.add(new ExcelExportColumn("SERIES_NAME", "车系"));
		exportColumnList.add(new ExcelExportColumn("MODEL_CODE", "CPOS"));
		exportColumnList.add(new ExcelExportColumn("MODEL_YEAR", "年款"));
		exportColumnList.add(new ExcelExportColumn("GROUP_NAME", "车款"));
		exportColumnList.add(new ExcelExportColumn("COLOR_NAME", "颜色"));
		exportColumnList.add(new ExcelExportColumn("TRIM_NAME", "内饰"));
		exportColumnList.add(new ExcelExportColumn("VIN", "VIN"));
		exportColumnList.add(new ExcelExportColumn("ENGINE_NO", "发动机号"));
		exportColumnList.add(new ExcelExportColumn("KEY_NO", "钥匙号"));
		exportColumnList.add(new ExcelExportColumn("QUALIFIED_NO", "合格证号"));
		exportColumnList.add(new ExcelExportColumn("NODE_STATUS", "车辆节点", ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("VEHICLE_USAGE", "车辆用途"));
		exportColumnList.add(new ExcelExportColumn("WHOLESALE_PRICE", "车辆批发价格"));
		exportColumnList.add(new ExcelExportColumn("RETAIL_PRICE", "MSRP价格"));
		exportColumnList.add(new ExcelExportColumn("ZASS_DATE", "总装上线日期"));
		exportColumnList.add(new ExcelExportColumn("ZVGO_DATE", "总装下线日期 "));
		exportColumnList.add(new ExcelExportColumn("ZVQP_DATE", "质检完成日期"));
		exportColumnList.add(new ExcelExportColumn("ZVGR_DATE", "入工厂仓库日期"));
		exportColumnList.add(new ExcelExportColumn("ZBBU_DATE", "工厂质量扣留日期"));
		exportColumnList.add(new ExcelExportColumn("ZINV_DATE", "内部结算完成日期"));
		exportColumnList.add(new ExcelExportColumn("ZMBL_DATE", "销售公司质量扣留日期"));
		exportColumnList.add(new ExcelExportColumn("YSOR_DATE", "付款日期"));
		exportColumnList.add(new ExcelExportColumn("ZVHC_DATE", "发运日期"));
		exportColumnList.add(new ExcelExportColumn("ZPD2_DATE", "物流到店日期"));
		exportColumnList.add(new ExcelExportColumn("ZPOD_DATE", "经销商收车日期"));
		exportColumnList.add(new ExcelExportColumn("RETAIL_DATE", "零售交车日期"));
		exportColumnList.add(new ExcelExportColumn("RETAIL_CHECK_DATE", "零售审核日期"));
		exportColumnList.add(new ExcelExportColumn("SCRAP_DATE", "报废日期"));
		exportColumnList.add(new ExcelExportColumn("PREDICT_STORAGE_DATE", "预计入库日期"));
		excelService.generateExcel(excelData, exportColumnList, "详细车籍查询.xls", request, response);
	}

	// jV车场库存管理-----详细车籍明细查询
	@Override
	public Map<String, Object> doQueryDetail(Long id) {
		// TODO Auto-generated method stub
		return dao.doQueryDetail(id);
	}

	@Override
	public PageInfoDto vehicleNodeChangeQuery(Long id) {
		Map<String, Object> map = new HashMap();
		//PageInfoDto m = dao.vehicleNodeChangeQuery(id);
		// for (Map map2 : m) {
		// map.put("CHANGE_CODE", map2.get("CHANGE_CODE"));
		// map.put("CHANGE_DATE", map2.get("CHANGE_DATE"));
		// map.put("CHANGE_DESC", map2.get("CHANGE_DESC"));
		//
		// }
		return null;
	}

	@Override
	public PageInfoDto doQuery(Map<String, String> queryParam) {

		return dao.doQuery(queryParam);
	}

	@Override
	public void depotInventorydoDownload(Map<String, String> queryParam, HttpServletResponse response,
			HttpServletRequest request) {
		Map<String, List<Map>> excelData = new HashMap<>();
		List<Map> list = dao.depotInventorydoDownload(queryParam);

		excelData.put("车厂库存查询", list);

		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
		exportColumnList.add(new ExcelExportColumn("BRAND_NAME", "品牌"));
		exportColumnList.add(new ExcelExportColumn("SERIES_NAME", "车系"));
		exportColumnList.add(new ExcelExportColumn("MODEL_CODE", "CPOS"));
		exportColumnList.add(new ExcelExportColumn("MODEL_YEAR", "年款"));
		exportColumnList.add(new ExcelExportColumn("GROUP_NAME", "车款"));
		exportColumnList.add(new ExcelExportColumn("COLOR_NAME", "颜色"));
		exportColumnList.add(new ExcelExportColumn("TRIM_NAME", "内饰"));
		exportColumnList.add(new ExcelExportColumn("VIN", "VIN"));
		exportColumnList.add(new ExcelExportColumn("OFFLINE_DATE", "下线日期"));
		exportColumnList.add(new ExcelExportColumn("NODE_STATUS", "车辆节点", ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("REMARK", "备注"));
		excelService.generateExcel(excelData, exportColumnList, "车厂库存查询.xls", request, response);

	}

	@Override
	public void downloadDetailsl(Map<String, String> queryParam, HttpServletResponse response,
			HttpServletRequest request) {
		Map<String, List<Map>> excelData = new HashMap<>();
		List<Map> list = dao.downloadDetailsl(queryParam);
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		String format = df.format(new Date());
		excelData.put("车辆节点日志", list);

		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
		exportColumnList.add(new ExcelExportColumn("VIN", "VIN"));
		exportColumnList.add(new ExcelExportColumn("CHANGE_CODE", "变更类型", ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("CHANGE_DATE", "变更时间"));
		exportColumnList.add(new ExcelExportColumn("CHANGE_DESC", "变更描述"));
		exportColumnList.add(new ExcelExportColumn("RESOURCE", "资源范围"));
		exportColumnList.add(new ExcelExportColumn("NAME", "操作人"));
		exportColumnList.add(new ExcelExportColumn("CREATE_DATE", "操作时间"));
		excelService.generateExcel(excelData, exportColumnList, "车辆节点日志" + format + ".xls", request, response);

	}

}
