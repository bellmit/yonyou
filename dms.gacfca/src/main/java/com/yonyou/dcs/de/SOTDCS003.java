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

import com.infoservice.dms.cgcsl.vo.TiDmsNCustomerInfoVO;
import com.yonyou.dcs.de.impl.BaseImpl;
import com.yonyou.dcs.gacfca.SOTDCS003Cloud;
import com.yonyou.dms.DTO.gacfca.TiDmsNCustomerInfoDto;
import com.yonyou.f4.de.DEException;
import com.yonyou.f4.de.DEMessage;
import com.yonyou.f4.de.executer.DEAction;

/**
 * 客户接待信息/需求分析(DMS新增)上报接收
 * @author Benzc
 * @date 2017年5月16日
 */
@Service
public class SOTDCS003 extends BaseImpl implements DEAction{
    
	@Autowired SOTDCS003Cloud Cloud;
    
	private static final Logger logger = LoggerFactory.getLogger(SOTDCS003.class);
	
	@Override
	public DEMessage execute(DEMessage deMsg) throws DEException {
		logger.info("========客户接待信息/需求分析(DMS新增)上报接收开始(SOTDCS003Impl)========");
		try {
			Map<String, Serializable> bodys = deMsg.getBody();
			List<TiDmsNCustomerInfoDto> dtoList = new ArrayList<TiDmsNCustomerInfoDto>();
			setDTO(dtoList,bodys);
			Cloud.handleExecutor(dtoList);
		} catch (Throwable t) {
			logger.info("========客户接待信息/需求分析(DMS新增)上报接收异常(SOTDCS003Impl)========");
			t.printStackTrace();
		}finally {
		}
		logger.info("========客户接待信息/需求分析(DMS新增)上报接收结束(SOTDCS003Impl)========");
		return null;
	}
	
	private void setDTO(List<TiDmsNCustomerInfoDto> dtoList, Map<String, Serializable> bodys){
		for (Entry<String, Serializable> entry : bodys.entrySet()) {
			TiDmsNCustomerInfoVO vo = new TiDmsNCustomerInfoVO();
			TiDmsNCustomerInfoDto dto = new TiDmsNCustomerInfoDto();
			vo = (TiDmsNCustomerInfoVO)entry.getValue();
			dto.setDealerCode(vo.getEntityCode());
			BeanUtils.copyProperties(vo, dto);
			dtoList.add(dto);
		}
	}

}
