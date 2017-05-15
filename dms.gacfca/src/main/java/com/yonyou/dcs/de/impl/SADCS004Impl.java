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

import com.infoservice.dms.cgcsl.vo.SameToDccVO;
import com.yonyou.dcs.de.SADCS004;
import com.yonyou.dcs.gacfca.SADCS004Cloud;
import com.yonyou.dms.DTO.gacfca.SameToDccDto;
import com.yonyou.f4.de.DEException;
import com.yonyou.f4.de.DEMessage;
import com.yonyou.f4.de.executer.DEAction;

/**
 * 销售信息撞单上报
 * @author Benzc
 * @date 2017年5月15日
 */
@Service
public class SADCS004Impl extends BaseImpl implements DEAction,SADCS004{
    
	@Autowired SADCS004Cloud SADCS004Cloud;
	
	private static final Logger logger = LoggerFactory.getLogger(SADCS004Impl.class);
	
	@Override
	public DEMessage execute(DEMessage deMsg) throws DEException {
		logger.info("=======销售信息撞单上报接收开始(SADCS004Impl)=======");
		try {
			Map<String, Serializable> bodys = deMsg.getBody();
			List<SameToDccDto> dtoList = new ArrayList<SameToDccDto>();
			setDTO(dtoList,bodys);
			SADCS004Cloud.receiveDate(dtoList);
		} catch (Throwable t) {
			logger.info("=======销售信息撞单上报接收异常(SADCS004Impl)=======");
			t.printStackTrace();
		}
		logger.info("=======销售信息撞单上报接收结束(SADCS004Impl)=======");
		return null;
	}
	
	private void setDTO(List<SameToDccDto> dtoList, Map<String, Serializable> bodys){
		for (Entry<String, Serializable> entry : bodys.entrySet()) {
			SameToDccVO vo = new SameToDccVO();
			SameToDccDto dto = new SameToDccDto();
			vo = (SameToDccVO)entry.getValue();
			dto.setDealerCode(vo.getEntityCode());
			dto = new SameToDccDto();
			BeanUtils.copyProperties(vo, dto);
			dtoList.add(dto);
		}
	}

}
