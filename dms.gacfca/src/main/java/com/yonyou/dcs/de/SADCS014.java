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

import com.infoservice.dms.cgcsl.vo.PoCusWholeRepayClryslerVO;
import com.yonyou.dcs.de.impl.BaseImpl;
import com.yonyou.dcs.gacfca.SADCS014Cloud;
import com.yonyou.dms.DTO.gacfca.PoCusWholeRepayClryslerDto;
import com.yonyou.f4.de.DEException;
import com.yonyou.f4.de.DEMessage;
import com.yonyou.f4.de.executer.DEAction;

/**
 * @author Administrator
 *
 */
@Service
public class SADCS014 extends BaseImpl implements DEAction {
	@Autowired SADCS014Cloud sadcs014Cloud;
    
	private static final Logger logger = LoggerFactory.getLogger(SADCS014.class);
	@Override
	public DEMessage execute(DEMessage deMsg) throws DEException {
		logger.info("========大客户报备返利数据上报接收开始(SADCS014)========");
		try {
			Map<String, Serializable> bodys = deMsg.getBody();
			List<PoCusWholeRepayClryslerDto> dtoList = new ArrayList<PoCusWholeRepayClryslerDto>();
			setDTO(dtoList,bodys);
			sadcs014Cloud.handleExecutor(dtoList);
		} catch (Throwable t) {
			logger.info("========大客户报备返利数据上报接收异常(SADCS014)========");
			t.printStackTrace();
		}finally {
		}
		logger.info("========大客户报备返利数据上报接收结束(SADCS014)========");
		return null;
	}
	
	private void setDTO(List<PoCusWholeRepayClryslerDto> dtoList, Map<String, Serializable> bodys){
		for (Entry<String, Serializable> entry : bodys.entrySet()) {
			PoCusWholeRepayClryslerVO vo = new PoCusWholeRepayClryslerVO();
			PoCusWholeRepayClryslerDto dto = new PoCusWholeRepayClryslerDto();
			vo = (PoCusWholeRepayClryslerVO)entry.getValue();
			dto.setDealerCode(vo.getEntityCode());
			BeanUtils.copyProperties(vo, dto);
			dtoList.add(dto);
		}
	}

}
