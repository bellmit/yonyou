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

import com.infoservice.dms.cgcsl.vo.SA010DayVO;
import com.yonyou.dcs.de.SADCS010Day;
import com.yonyou.dcs.gacfca.SADCS010DayCloud;
import com.yonyou.dms.DTO.gacfca.SA010DayDTO;
import com.yonyou.f4.de.DEException;
import com.yonyou.f4.de.DEMessage;
import com.yonyou.f4.de.executer.DEAction;

/**
 * 展厅日预测报告数据上报
 * @author Benzc
 * @date 2017年5月15年
 */
public class SADCS010DayImpl extends BaseImpl implements DEAction,SADCS010Day{
    
	@Autowired SADCS010DayCloud Cloud;
	
	private static final Logger logger = LoggerFactory.getLogger(SADCS010DayImpl.class);
	
	@Override
	public DEMessage execute(DEMessage deMsg) throws DEException {
		logger.info("=======展厅日预测报告数据上报接收开始(SADCS010DayImpl)=======");
		try {
			Map<String, Serializable> bodys = deMsg.getBody();
			List<SA010DayDTO> dtoList = new ArrayList<SA010DayDTO>();
			setDTO(dtoList,bodys);
			Cloud.receiveDate(dtoList);
		} catch (Throwable t) {
			logger.info("=======展厅日预测报告数据上报接收异常(SADCS010DayImpl)=======");
			t.printStackTrace();
		}
		logger.info("=======展厅日预测报告数据上报接收结束(SADCS010DayImpl)=======");
		return null;
	}
	
	private void setDTO(List<SA010DayDTO> dtoList, Map<String, Serializable> bodys){
		for (Entry<String, Serializable> entry : bodys.entrySet()) {
			SA010DayVO vo = new SA010DayVO();
			SA010DayDTO dto = new SA010DayDTO();
			vo = (SA010DayVO)entry.getValue();
			dto.setDealerCode(vo.getEntityCode());
			dto = new SA010DayDTO();
			BeanUtils.copyProperties(vo, dto);
			dtoList.add(dto);
		}
	}

}
