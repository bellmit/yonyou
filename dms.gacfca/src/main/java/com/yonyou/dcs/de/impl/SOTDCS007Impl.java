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

import com.infoservice.dms.cgcsl.vo.TiDmsNCultivateVO;
import com.yonyou.dcs.de.SOTDCS007;
import com.yonyou.dcs.gacfca.SOTDCS007Cloud;
import com.yonyou.dms.DTO.gacfca.TiDmsNCultivateDto;
import com.yonyou.f4.de.DEException;
import com.yonyou.f4.de.DEMessage;
import com.yonyou.f4.de.executer.DEAction;

/**
 * 创建客户信息（客户培育）(DMS新增)接收
 * @author Benzc
 * @date 2017年5月16日
 */
@Service
public class SOTDCS007Impl extends BaseImpl implements DEAction,SOTDCS007{

	@Autowired SOTDCS007Cloud Cloud;
	
	private static final Logger logger = LoggerFactory.getLogger(SOTDCS007Impl.class);
	
	@Override
	public DEMessage execute(DEMessage deMsg) throws DEException {
		logger.info("========创建客户信息（客户培育）(DMS新增)接收开始(SOTDCS007Impl)========");
		try {
			Map<String, Serializable> bodys = deMsg.getBody();
			List<TiDmsNCultivateDto> dtoList = new ArrayList<TiDmsNCultivateDto>();
			setDTO(dtoList,bodys);
			Cloud.handleExecutor(dtoList);
		} catch (Throwable t) {
			logger.info("========创建客户信息（客户培育）(DMS新增)接收异常(SOTDCS007Impl)========");
			t.printStackTrace();
		}finally {
		}
		logger.info("========创建客户信息（客户培育）(DMS新增)接收结束(SOTDCS007Impl)========");
		return null;
	}
	
	private void setDTO(List<TiDmsNCultivateDto> dtoList, Map<String, Serializable> bodys){
		for (Entry<String, Serializable> entry : bodys.entrySet()) {
			TiDmsNCultivateVO vo = (TiDmsNCultivateVO)entry.getValue();
			TiDmsNCultivateDto dto = new TiDmsNCultivateDto();
			dto.setDealerCode(vo.getEntityCode());
			BeanUtils.copyProperties(vo, dto);
			dtoList.add(dto);
		}
	}

}
