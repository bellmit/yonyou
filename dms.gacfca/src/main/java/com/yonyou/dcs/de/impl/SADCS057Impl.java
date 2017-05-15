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

import com.infoservice.dms.cgcsl.vo.SA057VO;
import com.yonyou.dcs.de.SADCS057;
import com.yonyou.dcs.gacfca.SADCS057Cloud;
import com.yonyou.dms.DTO.gacfca.SA057DTO;
import com.yonyou.f4.de.DEException;
import com.yonyou.f4.de.DEMessage;
/**
 * 试乘试驾统计表上端接口
 * @author Benzc
 * @date 2017年5月15日
 */
@Service
public class SADCS057Impl extends BaseImpl implements SADCS057{
	
	@Autowired
	SADCS057Cloud Cloud;
	
	private static final Logger logger = LoggerFactory.getLogger(SADCS057Impl.class);

	@Override
	public DEMessage execute(DEMessage deMsg) throws DEException {
		logger.info("====试乘试驾统计报表上报接收开始(SADCS057)====");
		try {
			Map<String, Serializable> bodys = deMsg.getBody();
			List<SA057DTO> dtoList = new ArrayList<>();
			setDTO(dtoList,bodys);
			Cloud.handleExecutor(dtoList);
		} catch(Throwable t) {
			logger.info("====试乘试驾统计报表上报接收异常(SADCS057)====");
			t.printStackTrace();
		} finally {
		}
		logger.info("====试乘试驾统计报表上报接收结束(SADCS057)====");
		return null;
	}
	
	private void setDTO(List<SA057DTO> dtoList, Map<String, Serializable> bodys) {
		for (Entry<String, Serializable> entry : bodys.entrySet()) {
			SA057VO vo = new SA057VO();
			SA057DTO dto = new SA057DTO();
			vo = (SA057VO) entry.getValue();
			dto.setDealerCode(vo.getEntityCode());
			dto = new SA057DTO();
			BeanUtils.copyProperties(vo, dto);
			dtoList.add(dto);
		}
		
	}

}
