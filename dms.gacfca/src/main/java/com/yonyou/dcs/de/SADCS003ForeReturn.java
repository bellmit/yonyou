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

import com.infoservice.dms.cgcsl.vo.SADMS003ForeReturnVO;
import com.yonyou.dcs.de.impl.BaseImpl;
import com.yonyou.dcs.gacfca.SADCS003ForeReturnCloud;
import com.yonyou.dms.DTO.gacfca.SADMS003ForeReturnDTO;
import com.yonyou.f4.de.DEException;
import com.yonyou.f4.de.DEMessage;
import com.yonyou.f4.de.executer.DEAction;
/**
 * DCC建档客户信息反馈接收
 * @author Benzc
 * @date 2017年5月15日
 */
@Service
public class SADCS003ForeReturn extends BaseImpl implements DEAction{
    
	private static final Logger logger = LoggerFactory.getLogger(SADCS003ForeReturn.class);
	
	@Autowired SADCS003ForeReturnCloud Cloud;

	@Override
	public DEMessage execute(DEMessage deMsg) throws DEException {
		logger.info("========DCC建档客户信息反馈接收开始(SADCS003ForeReturnImpl)=======");
		try {
			Map<String, Serializable> bodys = deMsg.getBody();
			List<SADMS003ForeReturnDTO> dtoList = new ArrayList<SADMS003ForeReturnDTO>();
			setDTO(dtoList,bodys);
			Cloud.receiveDate(dtoList);
		} catch (Throwable t) {
			logger.info("========DCC建档客户信息反馈接收异常(SADCS003ForeReturnImpl)=======");
			t.printStackTrace();
		}
		logger.info("========DCC建档客户信息反馈接收结束(SADCS003ForeReturnImpl)=======");
		return null;
	}
	
	private void setDTO(List<SADMS003ForeReturnDTO> dtoList, Map<String, Serializable> bodys){
		for (Entry<String, Serializable> entry : bodys.entrySet()) {
			SADMS003ForeReturnVO vo = new SADMS003ForeReturnVO();
			SADMS003ForeReturnDTO dto = new SADMS003ForeReturnDTO();
			vo = (SADMS003ForeReturnVO)entry.getValue();
			dto.setDealerCode(vo.getEntityCode());
			BeanUtils.copyProperties(vo, dto);
			dtoList.add(dto);
		}
	}
	

}
