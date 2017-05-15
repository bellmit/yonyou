package com.yonyou.dms.vehicle.service.insurancemanage;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.domains.DTO.ImportResultDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.domains.DTO.insurancemanage.TtInsuranceCompanyExcelTempDcsDTO;
import com.yonyou.dms.vehicle.domains.DTO.insurancemanage.TtInsuranceCompanyMainDcsDTO;
import com.yonyou.dms.vehicle.domains.DTO.oldPart.TtOpGcsImpDTO;

/**
 * 保险公司维护
 * @author zhiahongmiao 
 *
 */
public interface InsuranceCompanyManageService {
//查询
	public PageInfoDto InsuranceCompanyManageQuery(Map<String,String> queryParam) throws ServiceBizException;
//下载
	public void InsuranceCompanyManageDownload(Map<String, String> queryParam, HttpServletRequest request,HttpServletResponse response) throws ServiceBizException;
//导入模板下载
	public void downloadTemple(HttpServletRequest request,HttpServletResponse response) throws ServiceBizException;
//临时表插入数据
	public void TtInsuranceCompanyExcelTempImp(TtInsuranceCompanyExcelTempDcsDTO rowDto);
//校验导入数据
	public ImportResultDto<TtInsuranceCompanyExcelTempDcsDTO> checkData(TtInsuranceCompanyExcelTempDcsDTO rowDto) throws Exception ;
//保险公司维护（查询待插入数据）
	public List<Map> oemSelectTmpYearPlan(Map<String, String> queryParam);
	
	public void setImpPO(TtInsuranceCompanyMainDcsDTO po);
	

}