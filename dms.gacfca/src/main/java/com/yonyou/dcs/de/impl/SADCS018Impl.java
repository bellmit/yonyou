/**
 * 
 */
package com.yonyou.dcs.de.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoservice.dms.cgcsl.vo.ProperServiceManageVO;
import com.yonyou.dcs.de.SADCS018;
import com.yonyou.dcs.gacfca.SADCS018Cloud;
import com.yonyou.dms.DTO.gacfca.ProperServiceManageDto;
import com.yonyou.f4.de.DEException;
import com.yonyou.f4.de.DEMessage;

/**
 * @author Administrator
 *
 */
@Service
public class SADCS018Impl implements SADCS018 {
	private static final Logger logger = LoggerFactory.getLogger(SADCS018Impl.class);
	
	@Autowired
	SADCS018Cloud cloud;
	@Override
	public DEMessage execute(DEMessage deMsg) throws DEException {
		logger.info("========一对一客户经理(重绑)开始(SADCS018Impl)========");
		try {
			Map<String, Serializable> bodys = deMsg.getBody();
			List<ProperServiceManageDto> dtoList = new ArrayList<ProperServiceManageDto>();
			setDTO(dtoList,bodys);
			cloud.handleExecutor(dtoList);
		} catch (Throwable t) {
			logger.info("========一对一客户经理(重绑)异常(SADCS018Impl)========");
			t.printStackTrace();
		}finally {
		}
		logger.info("========一对一客户经理(重绑)结束(SADCS018Impl)========");
		return null;
	}
	
	private void setDTO(List<ProperServiceManageDto> dtoList, Map<String, Serializable> bodys){
		for (Entry<String, Serializable> entry : bodys.entrySet()) {
			ProperServiceManageVO vo = new ProperServiceManageVO();
			ProperServiceManageDto dto = new ProperServiceManageDto();
			vo = (ProperServiceManageVO)entry.getValue();
			dto.setDealerCode(vo.getDealerCode());
			BeanUtils.copyProperties(vo, dto);
			dtoList.add(dto);
		}
	}

}
