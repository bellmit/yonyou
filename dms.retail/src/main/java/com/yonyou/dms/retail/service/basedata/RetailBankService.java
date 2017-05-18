package com.yonyou.dms.retail.service.basedata;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.function.domains.DTO.ImportResultDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.retail.domains.DTO.basedata.TmRetailDiscountBankImportTempDTO;
import com.yonyou.dms.retail.domains.PO.basedata.TmRetailDiscountBankImportPO;

@SuppressWarnings("rawtypes")
public interface RetailBankService {
	public PageInfoDto findRetailDiscountBank(Map<String, String> queryParam);

	public List<Map> queryEmpInfoforExport(Map<String, String> param) throws ServiceBizException;

	public TmRetailDiscountBankImportPO findById(Long id) throws ServiceBizException;

	public void insertTmpVsProImpAudit(TmRetailDiscountBankImportTempDTO forecast) throws ServiceBizException;

	public List<Map> oemSelectTmpYearPlan(Map<String, String> queryParam) throws ServiceBizException;

	public List<Map> selectTmRetailDiscountBankImportTempList(LoginInfoDto loginInfo);

	public void insertTmRetailDiscountBankImportTemp(List<TmRetailDiscountBankImportTempDTO> dataList,LoginInfoDto loginInfo)throws ServiceBizException;

	public ImportResultDto<TmRetailDiscountBankImportTempDTO> checkData()throws ServiceBizException;

}
