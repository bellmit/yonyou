package com.yonyou.dcs.de.impl;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoservice.dms.cgcsl.vo.SADMS056VO;
import com.yonyou.dcs.de.SADCS056;
import com.yonyou.dcs.gacfca.SADCS056Cloud;
import com.yonyou.dms.DTO.gacfca.SADMS056Dto;
import com.yonyou.f4.de.DEMessage;

/**
 * 
 * Title:SADCS056Impl
 * Description: 试乘试驾分析数据上报
 * @author DC
 * @date 2017年4月7日 上午10:51:13
 */
@Service
public class SADCS056Impl  extends BaseImpl implements SADCS056{
	
	@Autowired
	SADCS056Cloud sadcs056Cloud;
	
	private static final Logger logger = LoggerFactory.getLogger(SADCS056Impl.class);
	
	public DEMessage execute(DEMessage deMsg) {
		logger.info("====试乘试驾分析数据上报接收开始(SADCS056)====");
		try {
			Map<String, Serializable> bodys = deMsg.getBody();
			LinkedList<SADMS056Dto> dtoList = new LinkedList<>();
			setDTO(dtoList,bodys);
			sadcs056Cloud.handleExecutor(dtoList);
		} catch(Throwable t) {
			t.printStackTrace();
		} finally {
		}
		logger.info("====试乘试驾分析数据上报接收结束(SADCS056)====");
		return null;
	}
	
	private void setDTO(LinkedList<SADMS056Dto> dtoList, Map<String, Serializable> bodys) {
		for (Entry<String, Serializable> entry : bodys.entrySet()) {
			SADMS056VO vo = new SADMS056VO();
			SADMS056Dto dto = new SADMS056Dto();
			vo = (SADMS056VO) entry.getValue();
			dto.setDealerCode(vo.getEntityCode());
			dto = new SADMS056Dto();
			BeanUtils.copyProperties(vo, dto);
			dtoList.add(dto);
		}
		
	}

}
