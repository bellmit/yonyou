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

import com.infoservice.dms.cgcsl.vo.SADMS052VO;
import com.yonyou.dcs.de.SADCS052;
import com.yonyou.dcs.de.impl.BaseImpl;
import com.yonyou.dcs.gacfca.SADCS052Cloud;
import com.yonyou.dms.DTO.gacfca.SADMS052DTO;
import com.yonyou.f4.de.DEException;
import com.yonyou.f4.de.DEMessage;
import com.yonyou.f4.de.executer.DEAction;

/**
 * @author Administrator
 *
 */
@Service
public class SADCS052 extends BaseImpl implements DEAction {

	@Autowired SADCS052Cloud Cloud;
    
	private static final Logger logger = LoggerFactory.getLogger(SADCS052.class);
	@Override
	public DEMessage execute(DEMessage deMsg) throws DEException {
		logger.info("========吸收率数据上报接收开始(SADCS052Impl)========");
		try {
			Map<String, Serializable> bodys = deMsg.getBody();
			List<SADMS052DTO> dtoList = new ArrayList<SADMS052DTO>();
			setDTO(dtoList,bodys);
			Cloud.handleExecutor(dtoList);
		} catch (Throwable t) {
			logger.info("========吸收率数据上报接收异常(SADCS052Impl)========");
			t.printStackTrace();
		}finally {
		}
		logger.info("========吸收率数据上报接收结束(SADCS052Impl)========");
		return null;
	}
	private void setDTO(List<SADMS052DTO> dtoList, Map<String, Serializable> bodys){
		for (Entry<String, Serializable> entry : bodys.entrySet()) {
			SADMS052VO vo = new SADMS052VO();
			SADMS052DTO dto = new SADMS052DTO();
			vo = (SADMS052VO)entry.getValue();
			dto.setDealerCode(vo.getEntityCode());
			BeanUtils.copyProperties(vo, dto);
			dtoList.add(dto);
		}
	}

}
