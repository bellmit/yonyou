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

import com.infoservice.dms.cgcsl.vo.TiDmsUSwapVO;
import com.yonyou.dcs.de.SOTDCS010;
import com.yonyou.dcs.gacfca.SOTDCS010Cloud;
import com.yonyou.dms.DTO.gacfca.TiDmsUSwapDto;
import com.yonyou.f4.de.DEException;
import com.yonyou.f4.de.DEMessage;
import com.yonyou.f4.de.executer.DEAction;

/**
 * 更新客户信息（置换需求）(DMS更新)接收
 * @author Benzc
 * @date 2017年5月16日
 */
@Service
public class SOTDCS010Impl extends BaseImpl implements DEAction,SOTDCS010{

	@Autowired SOTDCS010Cloud Cloud;
	
	private static final Logger logger = LoggerFactory.getLogger(SOTDCS010Impl.class);
	
	@Override
	public DEMessage execute(DEMessage deMsg) throws DEException {
		logger.info("========更新客户信息（置换需求）(DMS更新)接收开始(SOTDCS010Impl)========");
		try {
			Map<String, Serializable> bodys = deMsg.getBody();
			List<TiDmsUSwapDto> dtoList = new ArrayList<TiDmsUSwapDto>();
			setDTO(dtoList,bodys);
			Cloud.handleExecutor(dtoList);
		} catch (Throwable t) {
			logger.info("========更新客户信息（置换需求）(DMS更新)接收异常(SOTDCS010Impl)========");
			t.printStackTrace();
		}finally {
		}
		logger.info("========更新客户信息（置换需求）(DMS更新)接收结束(SOTDCS010Impl)========");
		return null;
	}
	
	private void setDTO(List<TiDmsUSwapDto> dtoList, Map<String, Serializable> bodys){
		for (Entry<String, Serializable> entry : bodys.entrySet()) {
			TiDmsUSwapVO vo = (TiDmsUSwapVO)entry.getValue();
			TiDmsUSwapDto dto = new TiDmsUSwapDto();
			BeanUtils.copyProperties(vo, dto);
			dtoList.add(dto);
		}
	}

}
