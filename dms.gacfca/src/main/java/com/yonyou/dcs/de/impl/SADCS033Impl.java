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
import com.yonyou.dcs.dao.SADCS033DTO;
import com.yonyou.dcs.de.SADCS033;
import com.yonyou.dcs.gacfca.SADCS033Cloud;
import com.yonyou.f4.de.DEException;
import com.yonyou.f4.de.DEMessage;

/**
 * @author Administrator
 *
 */
@Service
public class SADCS033Impl extends BaseImpl implements SADCS033 {
	@Autowired SADCS033Cloud Cloud;
    
	private static final Logger logger = LoggerFactory.getLogger(SADCS033Impl.class);
	
	@Override
	public DEMessage execute(DEMessage deMsg) throws DEException {
		logger.info("========车主信息核实固化月报表上报接收开始(SADCS033Impl)========");
		try {
			Map<String, Serializable> bodys = deMsg.getBody();
			List<SADCS033DTO> dtoList = new ArrayList<SADCS033DTO>();
			setDTO(dtoList,bodys);
			Cloud.handleExecutor(dtoList);
		} catch (Throwable t) {
			logger.info("========车主信息核实固化月报表上报接收异常(SADCS033Impl)========");
			t.printStackTrace();
		}finally {
		}
		logger.info("========车主信息核实固化月报表上报接收结束(SADCS033Impl)========");
		return null;
	}
	
	private void setDTO(List<SADCS033DTO> dtoList, Map<String, Serializable> bodys){
		for (Entry<String, Serializable> entry : bodys.entrySet()) {
			SADCS033VO vo = new SADCS033VO();
			SADCS033DTO dto = new SADCS033DTO();
			vo = (SADCS033VO)entry.getValue();
			dto.setDealerCode(vo.getDealerCode());
			BeanUtils.copyProperties(vo, dto);
			dtoList.add(dto);
		}
	}

}
