package com.yonyou.dcs.gacfca;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.dao.SEDCSP02Dao;
import com.yonyou.dms.DTO.gacfca.SEDCSP02DTO;
import com.yonyou.dms.function.exception.ServiceBizException;

@Service
public class SEDCSP02CloudImpl  extends BaseCloudImpl implements SEDCSP02Cloud {

	private static final Logger logger = LoggerFactory.getLogger(SEDCSP02CloudImpl.class);
	
	@Autowired
	SEDCSP02Dao dao;
	
	@Override
	public List<SEDCSP02DTO> handleExecutor(List<SEDCSP02DTO> dtos) throws Exception {
		logger.info("***************************SEDCSP02Cloud 同步查询获取经销商可用额度开始******************************");
		List<SEDCSP02DTO> retdtos=new ArrayList<SEDCSP02DTO>();
		try {
			for (SEDCSP02DTO dto : dtos) {
				List<SEDCSP02DTO> list = dao.querySedcsP02DTO(dto);
				retdtos.addAll(list);
			}
		} catch (Exception e) {
			logger.error("同步查询获取经销商可用额度失败", e);
			throw new ServiceBizException(e);
		}

		logger.info("***************************SEDCSP02Cloud  成功获取同步查询获取经销商可用额度 ******************************");
		return retdtos;
	}


}
