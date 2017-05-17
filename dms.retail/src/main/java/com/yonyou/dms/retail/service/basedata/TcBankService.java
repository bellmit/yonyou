package com.yonyou.dms.retail.service.basedata;

import java.util.Map;

import com.yonyou.dms.common.domains.PO.basedata.TcBankPO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.retail.domains.DTO.basedata.TcBankDTO;

public interface TcBankService {
	public PageInfoDto getQuerySql(Map<String, String> queryParam);

	public Long addTcBank(TcBankDTO tcdto) throws ServiceBizException;

	public void modifyTcBank(Long id, TcBankDTO tcdto) throws ServiceBizException;

	public int doSendEach(Long id,String dealerCode) throws ServiceBizException;
	
	public TcBankPO findById(Long id) throws ServiceBizException;
}
