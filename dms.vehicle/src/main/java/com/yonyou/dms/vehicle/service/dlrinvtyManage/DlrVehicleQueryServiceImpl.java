package com.yonyou.dms.vehicle.service.dlrinvtyManage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.TmVehiclePO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelDataType;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.vehicle.dao.dlrinvtyManage.DlrVehicleQueryDao;

@Service
public class DlrVehicleQueryServiceImpl implements DlrVehicleQueryService {
	
	@Autowired
	private DlrVehicleQueryDao dao;
	
	@Autowired
	private ExcelGenerator  excelService;

	/**
	 * 查询
	 */
	@Override
	public PageInfoDto query(Map<String, String> queryParam, LoginInfoDto loginInfo)
			throws ServiceBizException {
		PageInfoDto pgInfo = dao.queryList(queryParam,loginInfo);
		return pgInfo;
	}

	/**
	 * 详细查询
	 */
	@SuppressWarnings({ "static-access", "unchecked", "rawtypes" })
	@Override
	public Map<String, Object> detailQuery(Long id) throws ServiceBizException {
		Map<String, Object> map = new HashMap();
		String vehicleId = CommonUtils.checkNull(id);
		TmVehiclePO tvPo = new TmVehiclePO();
		TmVehiclePO tvP = tvPo.findById(vehicleId);
		String vin = (String) tvP.get("VIN");
		List<Map> list = dao.detailQuery(vin);
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

	/**
	 * 日志查询
	 */
	@Override
	public PageInfoDto oemVehicleDetailed(Map<String, String> queryParam) throws ServiceBizException {
		String vehicleId = CommonUtils.checkNull(queryParam.get("id"));
		TmVehiclePO tvP = TmVehiclePO.findById(vehicleId);
		String vin = (String) tvP.get("VIN");
		return dao.oemVehicleDetailed(vin);
	}

	/**
	 * 下载
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void findQuerySuccList(Map<String, String> queryParam, HttpServletResponse response,
			HttpServletRequest request) throws ServiceBizException {
		Map<String, List<Map>> excelData = new HashMap<>();
		List<Map> resultList = dao.download(queryParam);
		excelData.put("车辆综合查询信息", resultList);	
		
		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
		exportColumnList.add(new ExcelExportColumn("RESOURCE", "资源权属"));
		exportColumnList.add(new ExcelExportColumn("ORDER_TYPE", "分配类型"));
		exportColumnList.add(new ExcelExportColumn("BIG_AREA", "大区"));
		exportColumnList.add(new ExcelExportColumn("SMALL_AREA", "小区"));
		exportColumnList.add(new ExcelExportColumn("DEALER_CODE", "经销商代码"));
		exportColumnList.add(new ExcelExportColumn("DEALER_NAME", "经销商名称"));
		exportColumnList.add(new ExcelExportColumn("VIN", "VIN"));
		exportColumnList.add(new ExcelExportColumn("MODEL_YEAR", "年款"));
		exportColumnList.add(new ExcelExportColumn("BRAND_CODE", "品牌"));
		exportColumnList.add(new ExcelExportColumn("SERIES_NAME", "车系"));
		exportColumnList.add(new ExcelExportColumn("GROUP_NAME", "车款"));
		exportColumnList.add(new ExcelExportColumn("COLOR_CODE", "外饰代码"));
		exportColumnList.add(new ExcelExportColumn("COLOR_NAME", "外饰颜色"));
		exportColumnList.add(new ExcelExportColumn("TRIM_CODE", "内饰代码"));
		exportColumnList.add(new ExcelExportColumn("TRIM_NAME", "内饰颜色"));
		exportColumnList.add(new ExcelExportColumn("FACTORY_OPTIONS", "工厂选装"));
		exportColumnList.add(new ExcelExportColumn("VEHICLE_USAGE", "车辆用途"));
		exportColumnList.add(new ExcelExportColumn("REMARK", "车辆分配备注", ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("OTHER_REMARK", "其他备注"));
		exportColumnList.add(new ExcelExportColumn("IS_LOCK", "是否锁定"));
		exportColumnList.add(new ExcelExportColumn("PAYMENT_TYPE", "付款方式"));
		exportColumnList.add(new ExcelExportColumn("NODE_STATUS", "车辆节点", ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("ORDER_STATUSK", "订单状态"));
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
		exportColumnList.add(new ExcelExportColumn("START_SHIPMENT_DATE", "起运时间"));
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
		excelService.generateExcel(excelData, exportColumnList, "车辆综合查询信息.xls", request,response);
	}


}
