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

import com.infoservice.dms.cgcsl.vo.SADMS054VO;
import com.yonyou.dcs.de.SADCS054;
import com.yonyou.dcs.gacfca.SADCS054Cloud;
import com.yonyou.dms.DTO.gacfca.SADMS054DTO;
import com.yonyou.f4.de.DEException;
import com.yonyou.f4.de.DEMessage;

/**
 * @author Administrator
 *
 */
@Service
public class SADCS054Impl extends BaseImpl implements SADCS054 {

	@Autowired SADCS054Cloud Cloud;
    
	private static final Logger logger = LoggerFactory.getLogger(SADCS054Impl.class);
	@Override
	public DEMessage execute(DEMessage deMsg) throws DEException {
		logger.info("========克莱斯勒明检和神秘上报接收开始(SADCS054Impl)========");
		try {
			Map<String, Serializable> bodys = deMsg.getBody();
			List<SADMS054DTO> dtoList = new ArrayList<SADMS054DTO>();
			setDTO(dtoList,bodys);
			Cloud.handleExecutor(dtoList);
		} catch (Throwable t) {
			logger.info("========克莱斯勒明检和神秘上报接收异常(SADCS054Impl)========");
			t.printStackTrace();
		}finally {
		}
		logger.info("========克莱斯勒明检和神秘上报接收结束(SADCS054Impl)========");
		return null;
	}
	
	private void setDTO(List<SADMS054DTO> dtoList, Map<String, Serializable> bodys){
		for (Entry<String, Serializable> entry : bodys.entrySet()) {
			SADMS054VO vo = new SADMS054VO();
			SADMS054DTO dto = new SADMS054DTO();
			vo = (SADMS054VO)entry.getValue();
			dto.setEntityCode(vo.getEntityCode());
			BeanUtils.copyProperties(vo, dto);
			dtoList.add(dto);
		}
	}

}
