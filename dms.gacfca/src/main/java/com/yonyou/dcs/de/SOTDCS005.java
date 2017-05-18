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

import com.infoservice.dms.cgcsl.vo.TiDmsNSwapVO;
import com.yonyou.dcs.de.impl.BaseImpl;
import com.yonyou.dcs.gacfca.SOTDCS005Cloud;
import com.yonyou.dms.DTO.gacfca.TiDmsNSwapDto;
import com.yonyou.f4.de.DEException;
import com.yonyou.f4.de.DEMessage;
import com.yonyou.f4.de.executer.DEAction;

/**
 * 创建客户信息（置换需求）(DMS新增)接收
 * @author Benzc
 * @date 2017年5月16日
 */
@Service
public class SOTDCS005 extends BaseImpl implements DEAction{
	
	@Autowired SOTDCS005Cloud Cloud;
	
	private static final Logger logger = LoggerFactory.getLogger(SOTDCS005.class);
	
	@Override
	public DEMessage execute(DEMessage deMsg) throws DEException {
		logger.info("========创建客户信息（置换需求）(DMS新增)接收开始(SOTDCS005Impl)========");
		try {
			Map<String, Serializable> bodys = deMsg.getBody();
			List<TiDmsNSwapDto> dtoList = new ArrayList<TiDmsNSwapDto>();
			setDTO(dtoList,bodys);
			Cloud.handleExecutor(dtoList);
		} catch (Throwable t) {
			logger.info("========创建客户信息（置换需求）(DMS新增)接收异常(SOTDCS005Impl)========");
			t.printStackTrace();
		}finally {
		}
		logger.info("========创建客户信息（置换需求）(DMS新增)接收结束(SOTDCS005Impl)========");
		return null;
	}
	
	private void setDTO(List<TiDmsNSwapDto> dtoList, Map<String, Serializable> bodys){
		for (Entry<String, Serializable> entry : bodys.entrySet()) {
			TiDmsNSwapVO vo = (TiDmsNSwapVO)entry.getValue();
			TiDmsNSwapDto dto = new TiDmsNSwapDto();
			BeanUtils.copyProperties(vo, dto);
			dtoList.add(dto);
		}
	}

}
