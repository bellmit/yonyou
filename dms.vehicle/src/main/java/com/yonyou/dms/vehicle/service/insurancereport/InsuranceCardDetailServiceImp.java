package com.yonyou.dms.vehicle.service.insurancereport;

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
import com.yonyou.dms.vehicle.dao.insurancereport.InsuranceCardDetailDao;
import com.yonyou.dms.vehicle.dao.insurancereport.PolicyDetailManageDao;
import com.yonyou.dms.vehicle.domains.DTO.orderManager.RemarkImpDto;

/**
 *保险种类维护
 * @author zhiahongmiao 
 *
 */

@Service
public class InsuranceCardDetailServiceImp implements InsuranceCardDetailService{
	@Autowired
	InsuranceCardDetailDao insuranceCardDetailDao;
	@Autowired
	private ExcelGenerator excelService;
	/**
	 * 查询
	 */
	@Override
	public PageInfoDto InsuranceCardDetailQuery(Map<String, String> queryParam) throws ServiceBizException {
		// TODO Auto-generated method stub
		PageInfoDto pageInfoDto = insuranceCardDetailDao.InsuranceCardDetailQuery(queryParam);
		return pageInfoDto;
	}
	/**
	 * 下载
	 */
	@Override
	public void InsuranceCardDetailDownload(Map<String, String> queryParam, HttpServletRequest request,HttpServletResponse response) throws ServiceBizException {
		// TODO Auto-generated method stub
		List<Map> orderList = insuranceCardDetailDao.InsuranceCardDetailDownload(queryParam);   	
		Map<String, List<Map>> excelData = new HashMap<>();
		excelData.put("保险卡券发放查新下载", orderList);
		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
		exportColumnList.add(new ExcelExportColumn("BIG_AREA_NAME", "大区"));
		exportColumnList.add(new ExcelExportColumn("SMALL_AREA_NAME", "小区"));
		exportColumnList.add(new ExcelExportColumn("DEALER_CODE", "经销商代码"));	
		exportColumnList.add(new ExcelExportColumn("DEALER_SHORTNAME", "经销商名称"));
		exportColumnList.add(new ExcelExportColumn("PROVINCE", "省份"));	
		exportColumnList.add(new ExcelExportColumn("CITY", "城市"));
		exportColumnList.add(new ExcelExportColumn("INSURE_ORDER_CODE", "投保单号"));
		exportColumnList.add(new ExcelExportColumn("INSURE_ORDER_TYPE", "新续保",ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("IS_INS_LOCAL", "是否本地投保",ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("INS_CHANNELS", "投保渠道",ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("INSURANCE_COMPANY_CODE", "保险公司代码"));
		exportColumnList.add(new ExcelExportColumn("INS_COMPANY_SHORT_NAME", "保险公司简称"));
		exportColumnList.add(new ExcelExportColumn("BRAND_NAME", "品牌"));
		exportColumnList.add(new ExcelExportColumn("SERIES_NAME", "车系"));
		exportColumnList.add(new ExcelExportColumn("MODEL_NAME", "车型"));
		exportColumnList.add(new ExcelExportColumn("GROUP_NAME", "车款"));
		exportColumnList.add(new ExcelExportColumn("MODEL_YEAR", "年款"));
		exportColumnList.add(new ExcelExportColumn("VIN", "VIN"));
		exportColumnList.add(new ExcelExportColumn("ACTIVITY_CODE", "营销活动ID"));
		exportColumnList.add(new ExcelExportColumn("ACTIVITY_NAME", "营销活动名称"));
		exportColumnList.add(new ExcelExportColumn("ACTIVITY_START_DATE", "活动开始日期"));
		exportColumnList.add(new ExcelExportColumn("ACTIVITY_END_DATE", "活动结束日期"));
		exportColumnList.add(new ExcelExportColumn("CARD_NO", "卡券类型ID"));
		exportColumnList.add(new ExcelExportColumn("VOUCHER_NAME", "卡券类型名称"));
		exportColumnList.add(new ExcelExportColumn("SINGLE_AMOUNT", "单张金额"));
		exportColumnList.add(new ExcelExportColumn("VOUCHER_STANDARD", "发券标准",ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("USE_RANGE", "使用范围",ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("REPAIR_TYPE_NAME", "维修类型"));
		exportColumnList.add(new ExcelExportColumn("VOUCHER_ID", "卡券ID"));
		exportColumnList.add(new ExcelExportColumn("USE_STATUS", "卡券状态",ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("ISSUE_DATE", "卡券发放日期"));
		excelService.generateExcel(excelData, exportColumnList, "保险卡券发放查新下载.xls", request, response);

	}
	/**
	 *卡券类型名称
	 */
	@Override
	public List<Map> getvoucherName(Map<String, String> queryParams) throws ServiceBizException {
		// TODO Auto-generated method stub
		return insuranceCardDetailDao.getvoucherName(queryParams);
	}
}
