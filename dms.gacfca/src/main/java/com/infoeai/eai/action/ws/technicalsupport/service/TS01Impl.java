package com.infoeai.eai.action.ws.technicalsupport.service;

import java.util.List;

import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoeai.eai.action.BaseService;
import com.infoeai.eai.dao.ws.technicalsupport.service.TS01Dao;
import com.infoeai.eai.wsServer.tsdealer.TsDealerVO;

/**
 * DCS->DMSS
 * 经销商基本信息SOAP
 * @author luoyang
 *
 */
@Service
public class TS01Impl extends BaseService implements TS01 {
	
	private static final Logger logger = LoggerFactory.getLogger(TS01Impl.class);
	
	@Autowired
	TS01Dao dao;

	@Override
	public TsDealerVO[] getTmDealerService(String updateDate) throws Exception {
		TsDealerVO[] dealerArray = null;
		try {
			beginDbService();
			List<TsDealerVO> list = dao.getTS01VO(updateDate);
			if(list != null && list.size() > 0 ){
				logger.info("TS01Dao.getTS01VO===============>>list长度: " + list.size());
				dealerArray = new TsDealerVO[list.size()];
				dealerArray = list.toArray(dealerArray);
			}
			dbService.endTxn(true);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
			dbService.endTxn(false);
		} finally {
			Base.detach();
			dbService.clean();
		}
		return dealerArray;
	}

}
