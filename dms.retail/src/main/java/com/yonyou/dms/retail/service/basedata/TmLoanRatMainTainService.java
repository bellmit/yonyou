package com.yonyou.dms.retail.service.basedata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.retail.domains.DTO.basedata.TmLoanRatMaintainDTO;


public interface TmLoanRatMainTainService {
	
	public PageInfoDto getTmLoanlist(Map<String, String> queryParam) throws Exception ;
	
	public void addTmLoan(TmLoanRatMaintainDTO tldto,LoginInfoDto loginInfo) throws ServiceBizException;

	public void deleteChargeById(Long id) throws ServiceBizException;

	public void doSendEach(Long id,TmLoanRatMaintainDTO tcdto) throws ServiceBizException ;

	@SuppressWarnings("rawtypes")
	public List<Map> queryEmpInfoforExport(Map<String, String> queryParam) throws Exception;

	public boolean checkData(ArrayList<TmLoanRatMaintainDTO> dataList,LoginInfoDto loginInfo) throws ServiceBizException;

	public void delAll(TmLoanRatMaintainDTO tldto) throws ServiceBizException ;

	public void sentDiscountRateInfo(TmLoanRatMaintainDTO tldto)throws ServiceBizException ;


}
