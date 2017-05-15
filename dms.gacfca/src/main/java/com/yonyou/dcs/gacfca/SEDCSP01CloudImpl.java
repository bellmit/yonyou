package com.yonyou.dcs.gacfca;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.dao.SEDCSP01Dao;
import com.yonyou.dms.DTO.gacfca.SEDCSP01DTO;
import com.yonyou.dms.function.exception.ServiceBizException;

@Service
public class SEDCSP01CloudImpl  extends BaseCloudImpl implements SEDCSP01Cloud {

	private static final Logger logger = LoggerFactory.getLogger(SEDCSP01CloudImpl.class);
	
	@Autowired
	SEDCSP01Dao dao;
	
	@Override
	public List<SEDCSP01DTO> receiveData(List<SEDCSP01DTO> dtos) throws Exception {
		logger.info("***************************SEDCSP01Cloud 同步查询获取配件清单信息开始******************************");
		List<SEDCSP01DTO> retdtos=new ArrayList<SEDCSP01DTO>();
		try {
			for (SEDCSP01DTO dto : dtos) {

				List<SEDCSP01DTO> list = dao.querySedcsP01DTO(dto);
				retdtos.addAll(list);
			}
		} catch (Exception e) {
			logger.error("同步查询获取配件清单信息失败", e);
			throw new ServiceBizException(e);
		}

		logger.info("***************************SEDCSP01Cloud  成功获取同步查询获取配件清单信息 ******************************");
		return retdtos;
	}


}
