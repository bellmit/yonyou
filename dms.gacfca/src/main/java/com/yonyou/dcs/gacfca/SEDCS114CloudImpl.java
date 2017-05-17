package com.yonyou.dcs.gacfca;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.dao.SEDCS114Dao;
import com.yonyou.dms.DTO.gacfca.OwnerEntityDTO;
import com.yonyou.dms.function.exception.ServiceBizException;

@Service
public class SEDCS114CloudImpl  extends BaseCloudImpl implements SEDCS114Cloud {

	private static final Logger logger = LoggerFactory.getLogger(SEDCS114CloudImpl.class);
	
	@Autowired
	SEDCS114Dao dao ;
	
	@Override
	public List<OwnerEntityDTO> handleExecutor(List<OwnerEntityDTO> dtos) throws Exception {
		logger.info("***************************SEDCS114Cloud 开始获取车辆实销上报私自调拨验收信息******************************");
		List<OwnerEntityDTO> retdtos=new ArrayList<OwnerEntityDTO>();
		try {
			for (OwnerEntityDTO dto : dtos) {
				logger.info("SEDCS114Cloud 同步接口接收到VIN：" + dto.getVin() + "entityCode：" + dto.getEntityCode());

				OwnerEntityDTO oedto = dao.queryOwnerVehicleVO(dto);
				retdtos.add(oedto);
			}
		} catch (Exception e) {
			logger.error("SEDCS114Cloud 车辆实销上报私自调拨验收失败", e);
			throw new ServiceBizException(e);
		}

		logger.info("***************************SEDCS114Cloud 成功获取车辆实销上报私自调拨验收信息 ******************************");
		return retdtos;
	}


}
