/**
 * 
 */
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

import com.infoservice.dms.cgcsl.vo.PoCusWholeClryslerVO;
import com.infoservice.dms.cgcsl.vo.PoCusWholeRepayClryslerVO;
import com.yonyou.dcs.de.SADCS014;
import com.yonyou.dcs.gacfca.SADCS012Cloud;
import com.yonyou.dcs.gacfca.SADCS014Cloud;
import com.yonyou.dms.DTO.gacfca.PoCusWholeClryslerDto;
import com.yonyou.dms.DTO.gacfca.PoCusWholeRepayClryslerDto;
import com.yonyou.f4.de.DEException;
import com.yonyou.f4.de.DEMessage;

/**
 * @author Administrator
 *
 */
@Service
public class SADCS014Impl extends BaseImpl implements SADCS014 {
	@Autowired SADCS014Cloud sadcs014Cloud;
    
	private static final Logger logger = LoggerFactory.getLogger(SADCS014Impl.class);
	@Override
	public DEMessage execute(DEMessage deMsg) throws DEException {
		logger.info("========大客户报备返利数据上报接收开始(SADCS014Impl)========");
		try {
			Map<String, Serializable> bodys = deMsg.getBody();
			List<PoCusWholeRepayClryslerDto> dtoList = new ArrayList<PoCusWholeRepayClryslerDto>();
			setDTO(dtoList,bodys);
			sadcs014Cloud.handleExecutor(dtoList);
		} catch (Throwable t) {
			logger.info("========大客户报备返利数据上报接收异常(SADCS014Impl)========");
			t.printStackTrace();
		}finally {
		}
		logger.info("========大客户报备返利数据上报接收结束(SADCS014Impl)========");
		return null;
	}
	
	private void setDTO(List<PoCusWholeRepayClryslerDto> dtoList, Map<String, Serializable> bodys){
		for (Entry<String, Serializable> entry : bodys.entrySet()) {
			PoCusWholeRepayClryslerVO vo = new PoCusWholeRepayClryslerVO();
			PoCusWholeRepayClryslerDto dto = new PoCusWholeRepayClryslerDto();
			vo = (PoCusWholeRepayClryslerVO)entry.getValue();
			dto.setDealerCode(vo.getDealerCode());
			dto = new PoCusWholeRepayClryslerDto();
			BeanUtils.copyProperties(vo, dto);
			dtoList.add(dto);
		}
	}

}
