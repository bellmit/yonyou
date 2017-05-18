/**
 * 
 */
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

import com.infoservice.dms.cgcsl.vo.PoCusWholeClryslerVO;
import com.yonyou.dcs.de.impl.BaseImpl;
import com.yonyou.dcs.gacfca.SADCS012Cloud;
import com.yonyou.dms.DTO.gacfca.PoCusWholeClryslerDto;
import com.yonyou.f4.de.DEException;
import com.yonyou.f4.de.DEMessage;
import com.yonyou.f4.de.executer.DEAction;

/**
 * @author Administrator
 *
 */
@Service
public class SADCS012 extends BaseImpl implements DEAction {
	@Autowired SADCS012Cloud sadcs012Cloud;
    
	private static final Logger logger = LoggerFactory.getLogger(SADCS012.class);
	@Override
	public DEMessage execute(DEMessage deMsg) throws DEException {
		logger.info("========大客户报备数据上报接收开始(SADCS012)========");
		try {
			Map<String, Serializable> bodys = deMsg.getBody();
			List<PoCusWholeClryslerDto> dtoList = new ArrayList<PoCusWholeClryslerDto>();
			setDTO(dtoList,bodys);
			sadcs012Cloud.receiveDate(dtoList);
		} catch (Throwable t) {
			logger.info("========大客户报备数据上报接收异常(SADCS012)========");
			t.printStackTrace();
		}finally {
		}
		logger.info("========大客户报备数据上报接收结束(SADCS012)========");
		return null;
	}
	
	private void setDTO(List<PoCusWholeClryslerDto> dtoList, Map<String, Serializable> bodys){
		for (Entry<String, Serializable> entry : bodys.entrySet()) {
			PoCusWholeClryslerVO vo = new PoCusWholeClryslerVO();
			PoCusWholeClryslerDto dto = new PoCusWholeClryslerDto();
			vo = (PoCusWholeClryslerVO)entry.getValue();
			dto.setDealerCode(vo.getEntityCode());
			BeanUtils.copyProperties(vo, dto);
			dtoList.add(dto);
		}
	}

}
