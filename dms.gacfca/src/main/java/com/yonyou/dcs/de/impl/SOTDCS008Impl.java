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

import com.infoservice.dms.cgcsl.vo.TiDmsUCustomerInfoVO;
import com.yonyou.dcs.de.SOTDCS008;
import com.yonyou.dcs.gacfca.SOTDCS008Cloud;
import com.yonyou.dms.DTO.gacfca.TiDmsUCustomerInfoDto;
import com.yonyou.f4.de.DEException;
import com.yonyou.f4.de.DEMessage;
import com.yonyou.f4.de.executer.DEAction;
/**
 * 更新客户信息（客户接待信息/需求分析）(DMS更新)接收
 * @author Benzc
 * @date 2017年5月16日
 */
@Service
public class SOTDCS008Impl extends BaseImpl implements DEAction,SOTDCS008{

	@Autowired SOTDCS008Cloud Cloud;
	
	private static final Logger logger = LoggerFactory.getLogger(SOTDCS008Impl.class);
	
	@Override
	public DEMessage execute(DEMessage deMsg) throws DEException {
		logger.info("========更新客户信息（客户接待信息/需求分析）(DMS更新)接收开始(SOTDCS008Impl)========");
		try {
			Map<String, Serializable> bodys = deMsg.getBody();
			List<TiDmsUCustomerInfoDto> dtoList = new ArrayList<TiDmsUCustomerInfoDto>();
			setDTO(dtoList,bodys);
			Cloud.handleExecutor(dtoList);
		} catch (Throwable t) {
			logger.info("========更新客户信息（客户接待信息/需求分析）(DMS更新)接收异常(SOTDCS008Impl)========");
			t.printStackTrace();
		}finally {
		}
		logger.info("========更新客户信息（客户接待信息/需求分析）(DMS更新)接收结束(SOTDCS008Impl)========");
		return null;
	}
	
	private void setDTO(List<TiDmsUCustomerInfoDto> dtoList, Map<String, Serializable> bodys){
		for (Entry<String, Serializable> entry : bodys.entrySet()) {
			TiDmsUCustomerInfoVO vo = (TiDmsUCustomerInfoVO)entry.getValue();
			TiDmsUCustomerInfoDto dto = new TiDmsUCustomerInfoDto();
			BeanUtils.copyProperties(vo, dto);
			dtoList.add(dto);
		}
	}

}
