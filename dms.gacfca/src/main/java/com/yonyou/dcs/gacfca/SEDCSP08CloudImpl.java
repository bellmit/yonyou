package com.yonyou.dcs.gacfca;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.dao.SEDCSP08Dao;
import com.yonyou.dms.DTO.gacfca.SEDCSP08DTO;
import com.yonyou.dms.function.exception.ServiceBizException;

@Service
public class SEDCSP08CloudImpl  extends BaseCloudImpl implements SEDCSP08Cloud {

	private static final Logger logger = LoggerFactory.getLogger(SEDCSP08CloudImpl.class);
	
	@Autowired
	SEDCSP08Dao dao;
	
	@Override
	public List<SEDCSP08DTO> handleExecutor(List<SEDCSP08DTO> dtos) throws Exception {
		logger.info("***************************SEDCSP08Cloud 同步查询获取配件订单开票接货清单开始******************************");
		List<SEDCSP08DTO> retdtos=new ArrayList<SEDCSP08DTO>();
		try {
			for (SEDCSP08DTO dto : dtos) {
				List<SEDCSP08DTO> list = dao.querySEDCSP08DTO(dto);
				retdtos.addAll(list);
			}
		} catch (Exception e) {
			logger.error("同步查询获取配件订单开票接货清单失败", e);
			throw new ServiceBizException(e);
		}

		logger.info("***************************SEDCSP08Cloud  成功获取同步查询获取配件订单开票接货清单 ******************************");
		return retdtos;
	}




}
