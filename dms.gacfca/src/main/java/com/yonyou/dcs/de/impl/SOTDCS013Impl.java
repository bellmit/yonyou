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

import com.infoservice.dms.cgcsl.vo.TiDmsUSalesQuotasVO;
import com.yonyou.dcs.de.SOTDCS013;
import com.yonyou.dcs.gacfca.SOTDCS013Cloud;
import com.yonyou.dms.DTO.gacfca.TiDmsUSalesQuotasDto;
import com.yonyou.f4.de.DEException;
import com.yonyou.f4.de.DEMessage;
import com.yonyou.f4.de.executer.DEAction;

/**
 * 销售人员分配信息接收
 * @author Benzc
 * @date 2017年5月16日
 */
@Service
public class SOTDCS013Impl extends BaseImpl implements DEAction,SOTDCS013{

	@Autowired SOTDCS013Cloud Cloud;
	
	private static final Logger logger = LoggerFactory.getLogger(SOTDCS013Impl.class);
	
	@Override
	public DEMessage execute(DEMessage deMsg) throws DEException {
		logger.info("========销售人员分配信息接收开始(SOTDCS013Impl)========");
		try {
			Map<String, Serializable> bodys = deMsg.getBody();
			List<TiDmsUSalesQuotasDto> dtoList = new ArrayList<TiDmsUSalesQuotasDto>();
			setDTO(dtoList,bodys);
			Cloud.handleExecutor(dtoList);
		} catch (Throwable t) {
			logger.info("========销售人员分配信息接收异常(SOTDCS013Impl)========");
			t.printStackTrace();
		}finally {
		}
		logger.info("========销售人员分配信息接收结束(SOTDCS013Impl)========");
		return null;
	}
	
	private void setDTO(List<TiDmsUSalesQuotasDto> dtoList, Map<String, Serializable> bodys){
		for (Entry<String, Serializable> entry : bodys.entrySet()) {
			TiDmsUSalesQuotasVO vo = (TiDmsUSalesQuotasVO)entry.getValue();
			TiDmsUSalesQuotasDto dto = new TiDmsUSalesQuotasDto();
			BeanUtils.copyProperties(vo, dto);
			dtoList.add(dto);
		}
	}

}
