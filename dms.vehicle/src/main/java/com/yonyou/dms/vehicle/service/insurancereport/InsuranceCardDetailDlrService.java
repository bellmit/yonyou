package com.yonyou.dms.vehicle.service.insurancereport;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.domains.DTO.ImportResultDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.domains.DTO.insurancemanage.TtInsuranceCompanyExcelTempDcsDTO;
import com.yonyou.dms.vehicle.domains.DTO.insurancemanage.TtInsuranceCompanyMainDcsDTO;
import com.yonyou.dms.vehicle.domains.DTO.insurancemanage.TtInsuranceSortDcsDTO;
import com.yonyou.dms.vehicle.domains.DTO.insurancemanage.TtInsuranceSortExcelErrorDcsDTO;

/**
 * 保单明细查询
 * @author zhiahongmiao 
 *
 */
public interface InsuranceCardDetailDlrService {
//查询
	public PageInfoDto InsuranceCardDetailQuery(Map<String,String> queryParam) throws ServiceBizException;
//下载
	public void InsuranceCardDetailDownload(Map<String, String> queryParam, HttpServletRequest request,HttpServletResponse response) throws ServiceBizException;
//卡券类型名称
		public List<Map> getvoucherName(Map<String, String> queryParams) throws ServiceBizException;
}