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

import com.infoservice.dms.cgcsl.vo.OrderCarVO;
import com.yonyou.dcs.de.impl.BaseImpl;
import com.yonyou.dcs.gacfca.SADCS061Cloud;
import com.yonyou.dms.DTO.gacfca.OrderCarDTO;
import com.yonyou.f4.de.DEException;
import com.yonyou.f4.de.DEMessage;
import com.yonyou.f4.de.executer.DEAction;

/**
 * 订车接口  接收
 * @author Benzc
 * @date 2017年5月15日
 */
@Service
public class SADCS061 extends BaseImpl implements DEAction{
    
	@Autowired
	SADCS061Cloud Cloud;
	
	private static final Logger logger = LoggerFactory.getLogger(SADCS061.class);
	
	@Override
	public DEMessage execute(DEMessage deMsg) throws DEException {
		logger.info("====订车接口接收开始(SADCS061)====");
		try {
			Map<String, Serializable> bodys = deMsg.getBody();
			List<OrderCarDTO> dtoList = new ArrayList<>();
			setDTO(dtoList,bodys);
			Cloud.handleExecutor(dtoList);
		} catch(Throwable t) {
			logger.info("====订车接口接收异常(SADCS061)====");
			t.printStackTrace();
		} finally {
		}
		logger.info("====订车接口接收结束(SADCS061)====");
		return null;
	}
	
	private void setDTO(List<OrderCarDTO> dtoList, Map<String, Serializable> bodys) {
		for (Entry<String, Serializable> entry : bodys.entrySet()) {
			OrderCarVO vo = new OrderCarVO();
			OrderCarDTO dto = new OrderCarDTO();
			vo = (OrderCarVO) entry.getValue();
			dto.setEntityCode(vo.getEntityCode());
			BeanUtils.copyProperties(vo, dto);
			dtoList.add(dto);
		}
		
	}

}
