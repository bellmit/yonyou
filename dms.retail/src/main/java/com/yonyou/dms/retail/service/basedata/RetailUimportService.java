package com.yonyou.dms.retail.service.basedata;



import com.yonyou.dms.function.domains.DTO.ImportResultDto;
import com.yonyou.dms.retail.domains.DTO.basedata.TmRetalDiscountImportTempDTO;

public interface RetailUimportService {

	public void insertTmpVsYearlyPlan(TmRetalDiscountImportTempDTO rowDto);

	public ImportResultDto<TmRetalDiscountImportTempDTO> checkData();
	
	public boolean save();
}
