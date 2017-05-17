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

import com.infoservice.dms.cgcsl.vo.SADMS095VO;
import com.yonyou.dcs.de.impl.BaseImpl;
import com.yonyou.dcs.gacfca.SADCS095Cloud;
import com.yonyou.dms.DTO.gacfca.SADMS095Dto;
import com.yonyou.f4.de.DEException;
import com.yonyou.f4.de.DEMessage;
import com.yonyou.f4.de.executer.DEAction;
/**
 * 经销商零售信息变更上报
 * @author Benzc
 * @date 2017年5月15日
 */
@Service
public class SADCS095 extends BaseImpl implements DEAction{
    
	@Autowired
	SADCS095Cloud Cloud;
	
	private static final Logger logger = LoggerFactory.getLogger(SADCS095.class);
	
	@Override
	public DEMessage execute(DEMessage deMsg) throws DEException {
		logger.info("====经销商零售信息变更上报接收开始(SADCS095)====");
		try {
			Map<String, Serializable> bodys = deMsg.getBody();
			List<SADMS095Dto> dtoList = new ArrayList<>();
			setDTO(dtoList,bodys);
			Cloud.handleExecutor(dtoList);
		} catch(Throwable t) {
			logger.info("====经销商零售信息变更上报接收异常(SADCS095)====");
			t.printStackTrace();
		} finally {
		}
		logger.info("====经销商零售信息变更上报接收结束(SADCS095)====");
		return null;
	}
	
	private void setDTO(List<SADMS095Dto> dtoList, Map<String, Serializable> bodys) {
		for (Entry<String, Serializable> entry : bodys.entrySet()) {
			SADMS095VO vo = new SADMS095VO();
			SADMS095Dto dto = new SADMS095Dto();
			vo = (SADMS095VO) entry.getValue();
			dto.setDealerCode(vo.getEntityCode());
			BeanUtils.copyProperties(vo, dto);
			dtoList.add(dto);
		}
		
	}

}
