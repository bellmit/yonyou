package com.infoeai.eai.action.ws.technicalsupport.service;

import java.util.List;

import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoeai.eai.action.BaseService;
import com.infoeai.eai.dao.ws.technicalsupport.service.TS05Dao;
import com.infoeai.eai.wsServer.material.TsMaterialVO;

/**
 * DCS->DMSS
 * 物料服务
 * @author luoyang
 *
 */
@Service
public class TS05Impl extends BaseService implements TS05 {
	
	private static final Logger logger = LoggerFactory.getLogger(TS05Impl.class);
	
	@Autowired
	TS05Dao dao;

	@Override
	public TsMaterialVO[] getTmMaterialService(String updateDate) throws Exception {
		TsMaterialVO[] materialArray = null;
		try {
			beginDbService();
			List<TsMaterialVO> list = dao.getTS05VO(updateDate);
			if(list != null && list.size() > 0 ){
				logger.info("TS05Dao.getTS05VO===============>>list长度: " + list.size());
				materialArray = new TsMaterialVO[list.size()];
				materialArray = list.toArray(materialArray);
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
		return materialArray;
	}

}
