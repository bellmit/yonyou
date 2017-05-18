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

import com.infoservice.dms.cgcsl.vo.TiDmsNTestDriveVO;
import com.yonyou.dcs.de.impl.BaseImpl;
import com.yonyou.dcs.gacfca.SOTDCS004Cloud;
import com.yonyou.dms.DTO.gacfca.TiDmsNTestDriveDTO;
import com.yonyou.f4.de.DEException;
import com.yonyou.f4.de.DEMessage;
import com.yonyou.f4.de.executer.DEAction;

/**
 * 创建客户信息（试乘试驾）(DMS新增)接收
 * @author Benzc
 * @date 2017年5月16日
 */
@Service
public class SOTDCS004 extends BaseImpl implements DEAction{
    
	@Autowired SOTDCS004Cloud Cloud;
	
	private static final Logger logger = LoggerFactory.getLogger(SOTDCS004.class);
	
	@Override
	public DEMessage execute(DEMessage deMsg) throws DEException {
		logger.info("========创建客户信息（试乘试驾）(DMS新增)接收开始(SOTDCS004Impl)========");
		try {
			Map<String, Serializable> bodys = deMsg.getBody();
			List<TiDmsNTestDriveDTO> dtoList = new ArrayList<TiDmsNTestDriveDTO>();
			setDTO(dtoList,bodys);
			Cloud.handleExecutor(dtoList);
		} catch (Throwable t) {
			logger.info("========创建客户信息（试乘试驾）(DMS新增)接收异常(SOTDCS004Impl)========");
			t.printStackTrace();
		}finally {
		}
		logger.info("========创建客户信息（试乘试驾）(DMS新增)接收结束(SOTDCS004Impl)========");
		return null;
	}
	
	private void setDTO(List<TiDmsNTestDriveDTO> dtoList, Map<String, Serializable> bodys){
		for (Entry<String, Serializable> entry : bodys.entrySet()) {
			TiDmsNTestDriveVO vo = (TiDmsNTestDriveVO)entry.getValue();
			TiDmsNTestDriveDTO dto = new TiDmsNTestDriveDTO();
			BeanUtils.copyProperties(vo, dto);
			dtoList.add(dto);
		}
	}

}
