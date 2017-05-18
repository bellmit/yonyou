/**
 * 
 */
package com.yonyou.dcs.de;


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

import com.infoservice.dms.cgcsl.vo.SEDMS022VO;
import com.yonyou.dcs.de.SADCS024;
import com.yonyou.dcs.de.impl.BaseImpl;
import com.yonyou.dcs.gacfca.SADCS024Cloud;
import com.yonyou.dms.DTO.gacfca.SEDMS022DTO;
import com.yonyou.f4.de.DEException;
import com.yonyou.f4.de.DEMessage;
import com.yonyou.f4.de.executer.DEAction;

/**
 * @author Administrator
 *
 */
@Service
public class SADCS024 extends BaseImpl implements DEAction {
	@Autowired SADCS024Cloud Cloud;
    
	private static final Logger logger = LoggerFactory.getLogger(SADCS024.class);
	@Override
	public DEMessage execute(DEMessage deMsg) throws DEException {
		logger.info("========发票多次扫描上报接收开始(SADCS024)========");
		try {
			Map<String, Serializable> bodys = deMsg.getBody();
			List<SEDMS022DTO> dtoList = new ArrayList<SEDMS022DTO>();
			setDTO(dtoList,bodys);
			Cloud.handleExecutor(dtoList);
		} catch (Throwable t) {
			logger.info("========发票多次扫描上报接收异常(SADCS024)========");
			t.printStackTrace();
		}finally {
		}
		logger.info("========发票多次扫描上报接收结束(SADCS024)========");
		return null;
	}
	
	private void setDTO(List<SEDMS022DTO> dtoList, Map<String, Serializable> bodys){
		for (Entry<String, Serializable> entry : bodys.entrySet()) {
			SEDMS022VO vo = new SEDMS022VO();
			SEDMS022DTO dto = new SEDMS022DTO();
			vo = (SEDMS022VO)entry.getValue();
			dto.setDealerCode(vo.getEntityCode());
			BeanUtils.copyProperties(vo, dto);
			dtoList.add(dto);
		}
	}

}

