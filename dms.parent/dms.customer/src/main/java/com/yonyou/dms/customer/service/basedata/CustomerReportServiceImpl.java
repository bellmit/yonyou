package com.yonyou.dms.customer.service.basedata;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yonyou.dms.customer.dao.bigCustomerManage.CustomerDao;
import com.yonyou.dms.customer.domains.DTO.basedata.TtMysteriousTempExceDTO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.domains.DTO.ImportResultDto;
import com.yonyou.dms.function.exception.ServiceBizException;
@SuppressWarnings("rawtypes")
@Service
public class CustomerReportServiceImpl implements CustomerService {
	@Autowired
	CustomerDao dao;

	@Override
	public PageInfoDto findCustomer(Map<String, String> queryParam) {
		return dao.findCustomer(queryParam);
	}

	@Override
	public List<Map> queryEmpInfoforExport(Map<String, String> queryParam) throws Exception {
		return dao.queryEmpInfoforExport(queryParam);
	}

	@Override
	public List<Map> selecttype(Map<String, String> params) {
		return dao.selecttype(params);
	}

	@Override
	public ImportResultDto<TtMysteriousTempExceDTO> checkData(TtMysteriousTempExceDTO rowDto) {
	
		return dao.check(rowDto);
	}

	@Override
	public List<Map> oemSelectTmpYearPlan(Map<String, String> queryParam) {
	
		return dao.oemSelectTmpYearPlan(queryParam);
	}

	@Override
	public List<Map> getQueryValidatorDataFail() throws ServiceBizException {
		
		return dao.getQueryValidatorDataFail();
	}

}