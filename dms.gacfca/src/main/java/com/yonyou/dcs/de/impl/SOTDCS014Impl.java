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

import com.infoservice.dms.cgcsl.vo.TiDmsNTestDriveCarVO;
import com.yonyou.dcs.de.SOTDCS014;
import com.yonyou.dcs.gacfca.SOTDCS014Cloud;
import com.yonyou.dms.DTO.gacfca.TiDmsNTestDriveCarDto;
import com.yonyou.f4.de.DEException;
import com.yonyou.f4.de.DEMessage;
import com.yonyou.f4.de.executer.DEAction;

/**
 * 创建客户信息（试驾车辆信息）(DMS新增)接收
 * @author Benzc
 * @date 2017年5月16日
 */
@Service
public class SOTDCS014Impl extends BaseImpl implements DEAction,SOTDCS014{

	@Autowired SOTDCS014Cloud Cloud;
	
	private static final Logger logger = LoggerFactory.getLogger(SOTDCS014Impl.class);
	
	@Override
	public DEMessage execute(DEMessage deMsg) throws DEException {
		logger.info("========创建客户信息（试驾车辆信息）(DMS新增)接收开始(SOTDCS014Impl)========");
		try {
			Map<String, Serializable> bodys = deMsg.getBody();
			List<TiDmsNTestDriveCarDto> dtoList = new ArrayList<TiDmsNTestDriveCarDto>();
			setDTO(dtoList,bodys);
			Cloud.handleExecutor(dtoList);
		} catch (Throwable t) {
			logger.info("========创建客户信息（试驾车辆信息）(DMS新增)接收异常(SOTDCS014Impl)========");
			t.printStackTrace();
		}finally {
		}
		logger.info("========创建客户信息（试驾车辆信息）(DMS新增)接收结束(SOTDCS014Impl)========");
		return null;
	}
	
	private void setDTO(List<TiDmsNTestDriveCarDto> dtoList, Map<String, Serializable> bodys){
		for (Entry<String, Serializable> entry : bodys.entrySet()) {
			TiDmsNTestDriveCarVO vo = (TiDmsNTestDriveCarVO)entry.getValue();
			TiDmsNTestDriveCarDto dto = new TiDmsNTestDriveCarDto();
			BeanUtils.copyProperties(vo, dto);
			dtoList.add(dto);
		}
	}

}
