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
import com.yonyou.dms.vehicle.dao.insurancereport.PolicyDetailManageDlrDao;

/**
 *保险种类维护
 * @author zhiahongmiao 
 *
 */

@Service
public class PolicyDetailManageDlrServiceImp implements PolicyDetailManageDlrService{
	@Autowired
	PolicyDetailManageDlrDao policyDetailManageDao;
	@Autowired
	private ExcelGenerator excelService;
	/**
	 * 查询
	 */
	@Override
	public PageInfoDto PolilcyDetailManageQuery(Map<String, String> queryParam) throws ServiceBizException {
		// TODO Auto-generated method stub
		PageInfoDto pageInfoDto = policyDetailManageDao.PolilcyDetailManageQuery(queryParam);
		return pageInfoDto;
	}
	/**
	 * 下载
	 */
	@Override
	public void PolilcyDetailManageDownload(Map<String, String> queryParam, HttpServletRequest request,HttpServletResponse response) throws ServiceBizException {
		// TODO Auto-generated method stub
		List<Map> orderList = policyDetailManageDao.PolilcyDetailManageDownload(queryParam);   	
		Map<String, List<Map>> excelData = new HashMap<>();
		excelData.put("保单明细查新下载", orderList);
		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
		exportColumnList.add(new ExcelExportColumn("BIG_AREA_NAME", "大区"));
		exportColumnList.add(new ExcelExportColumn("SMALL_AREA_NAME", "小区"));
		exportColumnList.add(new ExcelExportColumn("DEALER_CODE", "经销商代码"));
		exportColumnList.add(new ExcelExportColumn("DEALER_NAME", "经销商全称"));		
		exportColumnList.add(new ExcelExportColumn("DEALER_SHORTNAME", "经销商名称"));
		exportColumnList.add(new ExcelExportColumn("PROVINCE", "省份"));	
		exportColumnList.add(new ExcelExportColumn("CITY", "城市"));
		exportColumnList.add(new ExcelExportColumn("INSURE_ORDER_CODE", "投保单号"));
		exportColumnList.add(new ExcelExportColumn("INS_SALES_DATE", "保险销售日期"));
		exportColumnList.add(new ExcelExportColumn("INSURE_ORDER_TYPE", "投保类型",ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("CTM_NAME", "车主姓名"));
		exportColumnList.add(new ExcelExportColumn("LICENSE", "车牌号"));
		exportColumnList.add(new ExcelExportColumn("VIN", "VIN码"));
		exportColumnList.add(new ExcelExportColumn("ENGINE_NO", "发动机号"));
		exportColumnList.add(new ExcelExportColumn("SALES_MODEL", "销售模式"));
		exportColumnList.add(new ExcelExportColumn("IS_INS_CREDIT", "是否信贷投保",ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("INSURED_NAME", "投保人"));
		exportColumnList.add(new ExcelExportColumn("INSURE_ORDER_NAME", "被保险人"));
		exportColumnList.add(new ExcelExportColumn("FIRST_REGISTER_DATE", "初登日期"));
		exportColumnList.add(new ExcelExportColumn("COM_INS_CODE", "交强险保单号"));
		exportColumnList.add(new ExcelExportColumn("BUSI_INS_CODE", "商业险保单号"));
		exportColumnList.add(new ExcelExportColumn("IS_INS_LOCAL", "是否本地投保",ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("INS_CHANNELS", "投保渠道",ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("INSURANCE_COMPANY_CODE", "保险公司代码"));
		exportColumnList.add(new ExcelExportColumn("INS_COMPANY_SHORT_NAME", "保险公司简称"));
		exportColumnList.add(new ExcelExportColumn("BRAND_NAME", "品牌"));
		exportColumnList.add(new ExcelExportColumn("SERIES_NAME", "车系"));
		exportColumnList.add(new ExcelExportColumn("MODEL_NAME", "车型"));
		exportColumnList.add(new ExcelExportColumn("GROUP_NAME", "车款"));
		exportColumnList.add(new ExcelExportColumn("MODEL_YEAR", "年款"));
		exportColumnList.add(new ExcelExportColumn("BUY_DEALER", "购车经销商"));
		exportColumnList.add(new ExcelExportColumn("INVOICE_DATE", "开票日期"));
		exportColumnList.add(new ExcelExportColumn("FORM_STATUS", "保单状态",ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("INSURANCE_SORT_CODE", "险种代码"));
		exportColumnList.add(new ExcelExportColumn("INSURANCE_SORT_NAME", "险种名称"));
		exportColumnList.add(new ExcelExportColumn("INSURE_ORDER_AMOUNT", "保额"));
		exportColumnList.add(new ExcelExportColumn("TOTAL_PREFERENTIAL", "应收金额"));
		exportColumnList.add(new ExcelExportColumn("BEGIN_DATE", "开始日期"));
		exportColumnList.add(new ExcelExportColumn("END_DATE", "结束日期"));
		exportColumnList.add(new ExcelExportColumn("IS_PRESENTED", "是否赠送",ExcelDataType.Oem_Dict));
		excelService.generateExcel(excelData, exportColumnList, "保单明细查新下载.xls", request, response);

	}
	/**
	 * 车型
	 */
	@Override
	public List<Map> getModel(Long series) throws ServiceBizException {
		// TODO Auto-generated method stub
		List<Map> list = new ArrayList<>();
		List<Map> map = policyDetailManageDao.getModel(series);
		if (null != map && map.size() > 0) {
			for (int i = 0; i < map.size(); i++) {
				Map<String,Object> model = new HashMap<>();
				model.put("MODEL_ID", map.get(i).get("MODEL_ID"));
				model.put("MODEL_CODE",  map.get(i).get("MODEL_NAME"));
				list.add(model);
			}
		}
			return list;
	}
	/**
	 * 车款
	 */
	@Override
	public List<Map> getGroup(Long model) throws ServiceBizException {
		// TODO Auto-generated method stub
		List<Map> list = new ArrayList<>();
		List<Map>  map = policyDetailManageDao.getGroup(model);
		if (null != map && map.size() > 0) {
			for (int i = 0; i < map.size(); i++) {
				Map<String,Object> model1 = new HashMap<>();
				model1.put("GROUP_ID", map.get(i).get("GROUP_ID"));
				model1.put("GROUP_NAME",  map.get(i).get("GROUP_NAME"));
				list.add(model1);
			}
		}
			return list;
	}
	/**
	 *保险公司简称
	 */
	@Override
	public List<Map> getcompanyCode(Map<String, String> queryParams) throws ServiceBizException {
		// TODO Auto-generated method stub
		return policyDetailManageDao.getcompanyCode(queryParams);
	}
	/**
	 *险种
	 */
	@Override
	public List<Map> getsortCode(Map<String, String> queryParams) throws ServiceBizException {
		// TODO Auto-generated method stub
		return policyDetailManageDao.getsortCode(queryParams);
	}
}
