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
import com.yonyou.dms.vehicle.dao.insurancemanage.InsuranceSortManangeDao;
import com.yonyou.dms.vehicle.domains.DTO.insurancemanage.TtInsuranceCompanyExcelTempDcsDTO;
import com.yonyou.dms.vehicle.domains.DTO.insurancemanage.TtInsuranceCompanyMainDcsDTO;
import com.yonyou.dms.vehicle.domains.DTO.insurancemanage.TtInsuranceSortDcsDTO;
import com.yonyou.dms.vehicle.domains.DTO.insurancemanage.TtInsuranceSortExcelErrorDcsDTO;

/**
 *保险种类维护
 * @author zhiahongmiao 
 *
 */

@Service
public class InsuranceSortManangeServiceImp implements InsuranceSortManangeService{
	@Autowired
	InsuranceSortManangeDao insuranceSortManangeDao;
	@Autowired
	private ExcelGenerator excelService;
	/**
	 * 查询
	 */
	@Override
	public PageInfoDto InsuranceSortManangeQuery(Map<String, String> queryParam) throws ServiceBizException {
		// TODO Auto-generated method stub
		PageInfoDto pageInfoDto = insuranceSortManangeDao.InsuranceSortManangeQuery(queryParam);
		return pageInfoDto;
	}
	/**
	 * 下载
	 */
	@Override
	public void InsuranceSortManangeDownload(Map<String, String> queryParam, HttpServletRequest request,HttpServletResponse response) throws ServiceBizException {
		// TODO Auto-generated method stub
		List<Map> orderList = insuranceSortManangeDao.InsuranceSortManangeDownload(queryParam);   	
		Map<String, List<Map>> excelData = new HashMap<>();
		excelData.put("保险种类维护", orderList);
		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
		exportColumnList.add(new ExcelExportColumn("CREATE_DATE", "上传日期"));
		exportColumnList.add(new ExcelExportColumn("INSURANCE_SORT_CODE", "险种代码"));
		exportColumnList.add(new ExcelExportColumn("INSURANCE_SORT_NAME", "险种名称"));
		exportColumnList.add(new ExcelExportColumn("IS_COM_INSURANCE", "是否交强险"));		
		exportColumnList.add(new ExcelExportColumn("IS_DOWN", "是否已下发"));	
		excelService.generateExcel(excelData, exportColumnList, "保险种类维护.xls", request, response);

	}
	/**
	 * 导入模板下载
	 */
	@Override
	public void downloadTemple(HttpServletRequest request, HttpServletResponse response) throws ServiceBizException {
		// TODO Auto-generated method stub
		Map<String, List<Map>> excelData = new HashMap<>();
		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
		excelData.put("保险种类维护导入模板",null);
		exportColumnList.add(new ExcelExportColumn("", "险种代码"));
		exportColumnList.add(new ExcelExportColumn("", "险种名称"));
		exportColumnList.add(new ExcelExportColumn("", "是否交强险"));
		excelService.generateExcel(excelData, exportColumnList, "保险种类维护导入模板.xls", request, response);
	}
	/**
	 * 插入数据到临时表
	 */
	@Override
	public void InsuranceSortManangeImp(TtInsuranceSortExcelErrorDcsDTO rowDto) {
		// TODO Auto-generated method stub
		insuranceSortManangeDao.InsuranceSortManangeImp(rowDto);
	}
	/**
	 * 校验导入数据
	 */
	@Override
	public ImportResultDto<TtInsuranceSortExcelErrorDcsDTO> checkData(TtInsuranceSortExcelErrorDcsDTO rowDto) throws Exception {
		// TODO Auto-generated method stub
		return insuranceSortManangeDao.checkData(rowDto);
	}
	/**
	 * 保险种类维护（查询待插入数据）
	 */
	@Override
	public List<Map> oemSelectTmpYearPlan(Map<String, String> queryParam) {
		// TODO Auto-generated method stub
		return insuranceSortManangeDao.selectTemp();
	}
	/**
	 * 导入
	 */
	@Override
	public void setImpPO(TtInsuranceSortDcsDTO po) {
		
		insuranceSortManangeDao.setImpPO(po);
		
	}

}
