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
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.function.domains.DTO.ImportResultDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.dao.insurancemanage.InsuranceCompanyManageDao;
import com.yonyou.dms.vehicle.domains.DTO.insurancemanage.TtInsuranceCompanyExcelTempDcsDTO;
import com.yonyou.dms.vehicle.domains.DTO.insurancemanage.TtInsuranceCompanyMainDcsDTO;
import com.yonyou.dms.vehicle.domains.DTO.oldPart.TmpGcsImpDTO;
import com.yonyou.dms.vehicle.domains.DTO.oldPart.TtOpGcsImpDTO;

/**
 * MVS 家族维护
 * @author zhiahongmiao 
 *
 */

@Service
public class InsuranceCompanyManageServiceImp implements InsuranceCompanyManageService{
	@Autowired
	InsuranceCompanyManageDao InsuranceCompanyManageDao;
	@Autowired
	private ExcelGenerator excelService;
	/**
	 * 查询
	 */
	@Override
	public PageInfoDto InsuranceCompanyManageQuery(Map<String, String> queryParam) throws ServiceBizException {
		// TODO Auto-generated method stub
		PageInfoDto pageInfoDto = InsuranceCompanyManageDao.InsuranceCompanyManageQuery(queryParam);
		return pageInfoDto;
	}
	/**
	 * 下载
	 */
	@Override
	public void InsuranceCompanyManageDownload(Map<String, String> queryParam, HttpServletRequest request,HttpServletResponse response) throws ServiceBizException {
		// TODO Auto-generated method stub
		List<Map> orderList = InsuranceCompanyManageDao.InsuranceCompanyManageDownload(queryParam);   	
		Map<String, List<Map>> excelData = new HashMap<>();
		excelData.put("保险公司维护", orderList);
		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
		exportColumnList.add(new ExcelExportColumn("CDate", "上传日期"));
		exportColumnList.add(new ExcelExportColumn("INSURANCE_COMPANY_CODE", "保险公司代码"));
		exportColumnList.add(new ExcelExportColumn("INSURANCE_COMPANY_NAME", "保险公司名称"));
		exportColumnList.add(new ExcelExportColumn("INS_COMPANY_SHORT_NAME", "保险公司简称"));		
		exportColumnList.add(new ExcelExportColumn("IS_CO_INSURANCE_COMPANY", "合作保险公司"));	
		exportColumnList.add(new ExcelExportColumn("IS_DOMN", "是否已下发"));	
		excelService.generateExcel(excelData, exportColumnList, "保险公司维护.xls", request, response);

	}
	/**
	 * 导入模板下载
	 */
	@Override
	public void downloadTemple(HttpServletRequest request, HttpServletResponse response) throws ServiceBizException {
		// TODO Auto-generated method stub
		Map<String, List<Map>> excelData = new HashMap<>();
		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
		excelData.put("保险公司维护导入模板",null);
		exportColumnList.add(new ExcelExportColumn("", "保险公司代码"));
		exportColumnList.add(new ExcelExportColumn("", "保险公司名称"));
		exportColumnList.add(new ExcelExportColumn("", "保险公司简称"));
		exportColumnList.add(new ExcelExportColumn("", "合作保险公司"));
		excelService.generateExcel(excelData, exportColumnList, "保险公司维护导入模板.xls", request, response);
	}
	/**
	 * 插入数据到临时表
	 */
	@Override
	public void TtInsuranceCompanyExcelTempImp(TtInsuranceCompanyExcelTempDcsDTO rowDto) {
		// TODO Auto-generated method stub
		InsuranceCompanyManageDao.TtInsuranceCompanyExcelTempImp(rowDto);
	}
	/**
	 * 校验导入数据
	 */
	@Override
	public ImportResultDto<TtInsuranceCompanyExcelTempDcsDTO> checkData(TtInsuranceCompanyExcelTempDcsDTO rowDto) throws Exception {
		// TODO Auto-generated method stub
		return InsuranceCompanyManageDao.checkData(rowDto);
	}
	/**
	 * 保险公司维护（查询待插入数据）
	 */
	@Override
	public List<Map> oemSelectTmpYearPlan(Map<String, String> queryParam) {
		// TODO Auto-generated method stub
		return InsuranceCompanyManageDao.selectTemp();
	}
	/**
	 * 导入
	 */
	@Override
	public void setImpPO(TtInsuranceCompanyMainDcsDTO po) {
		
		InsuranceCompanyManageDao.setImpPO(po);
		
	}

}
