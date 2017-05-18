package com.yonyou.dcs.gacfca;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.dao.SEDCSP07Dao;
import com.yonyou.dms.DTO.gacfca.SEDCSP07DTO;
import com.yonyou.dms.function.exception.ServiceBizException;

@Service
public class SEDCSP07CloudImpl  extends BaseCloudImpl implements SEDCSP07Cloud {

	private static final Logger logger = LoggerFactory.getLogger(SEDCSP07CloudImpl.class);
	
	@Autowired
	SEDCSP07Dao dao;
	
	@Override
	public List<SEDCSP07DTO> handleExecutor(List<SEDCSP07DTO> dtos) throws Exception {
		logger.info("***************************SEDCSP07Cloud 同步查询获取配件开票接货状态开始******************************");
		List<SEDCSP07DTO> retdtos=new ArrayList<SEDCSP07DTO>();
		try {
			for (SEDCSP07DTO dto : dtos) {
				List<SEDCSP07DTO> list = dao.querySedcsP07DTO(dto);
				retdtos.addAll(list);
			}
		} catch (Exception e) {
			logger.error("同步查询获取配件开票接货状态失败", e);
			throw new ServiceBizException(e);
		}

		logger.info("***************************SEDCSP07Cloud  成功获取同步查询获取配件开票接货状态 ******************************");
		return retdtos;
	}



}
