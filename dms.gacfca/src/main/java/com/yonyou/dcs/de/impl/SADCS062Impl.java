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

import com.infoservice.dms.cgcsl.vo.RenewalFailedVO;
import com.yonyou.dcs.de.SADCS062;
import com.yonyou.dcs.gacfca.SADCS062Cloud;
import com.yonyou.dms.DTO.gacfca.RenewalFailedDTO;
import com.yonyou.f4.de.DEException;
import com.yonyou.f4.de.DEMessage;
/**
 * 续保战败接口   接收
 * @author Benzc
 * @date 2017年5月15日
 */
@Service
public class SADCS062Impl extends BaseImpl implements SADCS062{
    
	@Autowired
	SADCS062Cloud Cloud;
	
	private static final Logger logger = LoggerFactory.getLogger(SADCS062Impl.class);
	
	@Override
	public DEMessage execute(DEMessage deMsg) throws DEException {
		logger.info("====续保战败接口接收开始(SADCS062)====");
		try {
			Map<String, Serializable> bodys = deMsg.getBody();
			List<RenewalFailedDTO> dtoList = new ArrayList<>();
			setDTO(dtoList,bodys);
			Cloud.handleExecutor(dtoList);
		} catch(Throwable t) {
			logger.info("====续保战败接口接收异常(SADCS062)====");
			t.printStackTrace();
		} finally {
		}
		logger.info("====续保战败接口接收结束(SADCS062)====");
		return null;
	}
	
	private void setDTO(List<RenewalFailedDTO> dtoList, Map<String, Serializable> bodys) {
		for (Entry<String, Serializable> entry : bodys.entrySet()) {
			RenewalFailedVO vo = new RenewalFailedVO();
			RenewalFailedDTO dto = new RenewalFailedDTO();
			vo = (RenewalFailedVO) entry.getValue();
			dto.setEntityCode(vo.getEntityCode());
			dto = new RenewalFailedDTO();
			BeanUtils.copyProperties(vo, dto);
			dtoList.add(dto);
		}
		
	}

}
