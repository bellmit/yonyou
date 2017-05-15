package com.yonyou.dms.customer.service.basedata;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.customer.domains.DTO.basedata.TtMysteriousTempExceDTO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.domains.DTO.ImportResultDto;
import com.yonyou.dms.function.exception.ServiceBizException;

@SuppressWarnings("rawtypes")
public interface CustomerService {

	PageInfoDto findCustomer(Map<String, String> queryParam);
	
	List<Map> queryEmpInfoforExport(Map<String, String> queryParam)throws Exception;
	
	public List<Map> selecttype(Map<String, String>  params) ;

	ImportResultDto<TtMysteriousTempExceDTO> checkData(TtMysteriousTempExceDTO rowDto);

	List<Map> oemSelectTmpYearPlan(Map<String, String> queryParam);

	List<Map> getQueryValidatorDataFail() throws ServiceBizException;

}
