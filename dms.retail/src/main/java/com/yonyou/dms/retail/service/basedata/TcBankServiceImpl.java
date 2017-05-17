package com.yonyou.dms.retail.service.basedata;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.gacfca.SADMS064Cloud;
import com.yonyou.dms.common.domains.PO.basedata.TcBankPO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.retail.controller.basedata.TcBankController;
import com.yonyou.dms.retail.dao.basedata.TcBankDao;
import com.yonyou.dms.retail.domains.DTO.basedata.TcBankDTO;

@Service
public class TcBankServiceImpl implements TcBankService{

	
	private static final Logger logger = LoggerFactory.getLogger(TcBankController.class);
	
	@Autowired
	TcBankDao dao;
	
	@Autowired
	SADMS064Cloud osc;
	
	@Override
	public PageInfoDto getQuerySql(Map<String, String> queryParam) {
		PageInfoDto pgInfo = dao.getBanklist(queryParam);
		return pgInfo;
	}

	@Override
	public Long addTcBank(TcBankDTO tcdto) throws ServiceBizException {
		long add = dao.addTcBank(tcdto);
		return add;
	}

	@Override
	public void modifyTcBank(Long id, TcBankDTO tcdto) throws ServiceBizException {
		dao.modifyTcBank(id, tcdto);
		
	}

	@Override
	public int doSendEach(Long id,String dealerCode) throws ServiceBizException {
		int msg = 1;
		try {
			// 调用下发接口 Start //接口为完成，作者:王鑫
			msg = osc.sendData(id,dealerCode);
		} catch (Exception e) {
			throw new ServiceBizException("下发异常，请联系管理员");
		}
		return msg;
	}

	@Override
	public TcBankPO findById(Long id) throws ServiceBizException {
		TcBankPO dr = TcBankPO.findById(id);
	    return dr;
		
	}



	

}
