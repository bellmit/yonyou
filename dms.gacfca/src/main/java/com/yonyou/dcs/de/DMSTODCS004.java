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

import com.infoservice.dms.cgcsl.vo.DMSTODCS004VO;
import com.yonyou.dcs.de.impl.BaseImpl;
import com.yonyou.dcs.gacfca.DMSTODCS004Cloud;
import com.yonyou.dms.DTO.gacfca.DMSTODCS004Dto;
import com.yonyou.f4.de.DEException;
import com.yonyou.f4.de.DEMessage;
import com.yonyou.f4.de.executer.DEAction;

/**
 * 零售订单CALL车（接收DMS接口）
 * @author Benzc
 * @date 2017年5月17日
 */
public class DMSTODCS004 extends BaseImpl implements DEAction{

	@Autowired
	DMSTODCS004Cloud Cloud;
	
	private static final Logger logger = LoggerFactory.getLogger(DMSTODCS004.class);
	
	@Override
	public DEMessage execute(DEMessage deMsg) throws DEException {
		logger.info("===============零售订单CALL车（接收DMS接口）接收开始(SOTDCS015)==============");
		try {
			Map<String, Serializable> bodys = deMsg.getBody();
			List<DMSTODCS004Dto> dtoList = new ArrayList<DMSTODCS004Dto>();
			setDTO(dtoList,bodys);
			Cloud.handleExecutor(dtoList);
		}catch (Throwable t) {
			logger.info("===============零售订单CALL车（接收DMS接口）接收异常(SOTDCS015)==============");
		}finally {
		}
		logger.info("===============零售订单CALL车（接收DMS接口）接收结束(SOTDCS015)==============");
		return null;
	}
	
	private void setDTO(List<DMSTODCS004Dto> dtoList, Map<String, Serializable> bodys){
		for (Entry<String, Serializable> entry : bodys.entrySet()) {
			DMSTODCS004VO vo = (DMSTODCS004VO)entry.getValue();
			DMSTODCS004Dto dto = new DMSTODCS004Dto();
			dto.setDealerCode(vo.getEntityCode());
			BeanUtils.copyProperties(vo, dto);
			dtoList.add(dto);
		}
	}

}
