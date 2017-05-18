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

import com.infoservice.dms.cgcsl.vo.SADCS033VO;
import com.infoservice.dms.cgcsl.vo.SADMS051VO;
import com.yonyou.dcs.dao.SADCS033DTO;
import com.yonyou.dcs.de.SADCS051;
import com.yonyou.dcs.gacfca.SADCS051Cloud;
import com.yonyou.dms.DTO.gacfca.SADMS051DTO;
import com.yonyou.f4.de.DEException;
import com.yonyou.f4.de.DEMessage;

/**
 * @author Administrator
 *
 */
@Service
public class SADCS051Impl extends BaseImpl implements SADCS051 {

	@Autowired SADCS051Cloud Cloud;
    
	private static final Logger logger = LoggerFactory.getLogger(SADCS051Impl.class);
	@Override
	public DEMessage execute(DEMessage deMsg) throws DEException {
		logger.info("========经营月报数据上报接收开始(SADCS051Impl)========");
		try {
			Map<String, Serializable> bodys = deMsg.getBody();
			List<SADMS051DTO> dtoList = new ArrayList<SADMS051DTO>();
			setDTO(dtoList,bodys);
			Cloud.handleExecutor(dtoList);
		} catch (Throwable t) {
			logger.info("========经营月报数据上报接收异常(SADCS051Impl)========");
			t.printStackTrace();
		}finally {
		}
		logger.info("========经营月报数据上报接收结束(SADCS051Impl)========");
		return null;
	}
	
	
	private void setDTO(List<SADMS051DTO> dtoList, Map<String, Serializable> bodys){
		for (Entry<String, Serializable> entry : bodys.entrySet()) {
			SADMS051VO vo = new SADMS051VO();
			SADMS051DTO dto = new SADMS051DTO();
			vo = (SADMS051VO)entry.getValue();
			dto.setEntityCode(vo.getEntityCode());
			BeanUtils.copyProperties(vo, dto);
			dtoList.add(dto);
		}
	}

}
