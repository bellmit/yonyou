package com.yonyou.dms.vehicle.service.insurancemanage;

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
import com.yonyou.dms.vehicle.dao.insurancemanage.InsurancePolicyManageDao;
import com.yonyou.dms.vehicle.domains.DTO.insurancemanage.CardCouponsDTO;

/**
 * MVS 家族维护
 * @author zhiahongmiao 
 *
 */

@Service
public class InsurancePolicyManageServiceImp implements InsurancePolicyManageService{
	@Autowired
	InsurancePolicyManageDao insurancePolicyManangeDao;
	@Autowired
	private ExcelGenerator excelService;
	/**
	 * 查询
	 */
	@Override
	public PageInfoDto InsurancePolicyManageQuery(Map<String, String> queryParam) throws ServiceBizException {
		// TODO Auto-generated method stub
		PageInfoDto pageInfoDto =  insurancePolicyManangeDao.InsurancePolicyManageQuery(queryParam);
		return pageInfoDto;
	}
	/**
	 * 下载
	 */
	@Override
	public void insurancePolicyManangeDownload(Map<String, String> queryParam, HttpServletRequest request,HttpServletResponse response) throws ServiceBizException {
		// TODO Auto-generated method stub
		List<Map> orderList = insurancePolicyManangeDao.insurancePolicyManangeDownload(queryParam);   	
		Map<String, List<Map>> excelData = new HashMap<>();
		excelData.put("保险营销活动管理", orderList);
		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
		exportColumnList.add(new ExcelExportColumn("INSURANCE_COMPANY_CODE", "保险公司代码"));
		exportColumnList.add(new ExcelExportColumn("INSURANCE_COMPANY_NAME", "保险公司名称"));
		exportColumnList.add(new ExcelExportColumn("ACTIVITY_CODE", "活动编号"));
		exportColumnList.add(new ExcelExportColumn("ACTIVITY_NAME", "活动名称"));		
		exportColumnList.add(new ExcelExportColumn("ACTIVITY_START_DATE", "活动开始日期"));	
		exportColumnList.add(new ExcelExportColumn("ACTIVITY_END_DATE", "活动结束日期"));	
		exportColumnList.add(new ExcelExportColumn("ACTIVITY_ISSUE_NUMBER", "活动发放卡券数量上限"));		
		exportColumnList.add(new ExcelExportColumn("RELEASE_STATUS", "状态",ExcelDataType.Oem_Dict));	
		exportColumnList.add(new ExcelExportColumn("RELEASE_DATE", "下发时间"));
		excelService.generateExcel(excelData, exportColumnList, "保险营销活动管理.xls", request, response);
	}
	/**
	 * 查询维修类型
	 */
	@Override
	public PageInfoDto codeDescQuery(Map<String, String> queryParam) throws ServiceBizException {
		// TODO Auto-generated method stub
		PageInfoDto pageInfoDto =  insurancePolicyManangeDao.codeDescQuery(queryParam);
		return pageInfoDto;
	}
	/**
	 * 查询保险公司
	 */
	@Override
	public PageInfoDto insurancecompanyQuery(Map<String, String> queryParam) throws ServiceBizException {
		// TODO Auto-generated method stub
		PageInfoDto pageInfoDto =  insurancePolicyManangeDao.insurancecompanyQuery(queryParam);
		return pageInfoDto;
	}
	/**
	 * 新增
	 */
	@Override
	public long Add(CardCouponsDTO ccDTO) throws ServiceBizException {
		// TODO Auto-generated method stub
		return insurancePolicyManangeDao.Add(ccDTO);
	}

}
