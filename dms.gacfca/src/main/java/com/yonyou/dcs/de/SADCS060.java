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

import com.infoservice.dms.cgcsl.vo.SADCS060VO;
import com.yonyou.dcs.de.impl.BaseImpl;
import com.yonyou.dcs.gacfca.SADCS060Cloud;
import com.yonyou.dms.DTO.gacfca.SADCS060Dto;
import com.yonyou.f4.de.DEException;
import com.yonyou.f4.de.DEMessage;
import com.yonyou.f4.de.executer.DEAction;

/**
 * 一对一客户经理接口  接收
 * @author Benzc
 * @date 2017年5月15日
 */
@Service
public class SADCS060 extends BaseImpl implements DEAction{
	
	@Autowired
	SADCS060Cloud Cloud;
	
	private static final Logger logger = LoggerFactory.getLogger(SADCS060.class);

	@Override
	public DEMessage execute(DEMessage deMsg) throws DEException {
		logger.info("====一对一客户经理接口接收开始(SADCS060)====");
		try {
			Map<String, Serializable> bodys = deMsg.getBody();
			List<SADCS060Dto> dtoList = new ArrayList<>();
			setDTO(dtoList,bodys);
			Cloud.handleExecutor(dtoList);
		} catch(Throwable t) {
			logger.info("====一对一客户经理接口接收异常(SADCS060)====");
			t.printStackTrace();
		} finally {
		}
		logger.info("====一对一客户经理接口接收结束(SADCS060)====");
		return null;
	}
	
	private void setDTO(List<SADCS060Dto> dtoList, Map<String, Serializable> bodys) {
		for (Entry<String, Serializable> entry : bodys.entrySet()) {
			SADCS060VO vo = new SADCS060VO();
			SADCS060Dto dto = new SADCS060Dto();
			vo = (SADCS060VO) entry.getValue();
			dto.setDealerCode(vo.getEntityCode());
			BeanUtils.copyProperties(vo, dto);
			dtoList.add(dto);
		}
		
	}

}
