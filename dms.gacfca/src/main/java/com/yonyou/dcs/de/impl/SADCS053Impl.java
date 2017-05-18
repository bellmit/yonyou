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

import com.infoservice.dms.cgcsl.vo.BigCustomerVisitItemVO;
import com.yonyou.dcs.de.SADCS053;
import com.yonyou.dcs.gacfca.SADCS053Cloud;
import com.yonyou.dms.DTO.gacfca.BigCustomerVisitItemDTO;
import com.yonyou.f4.de.DEException;
import com.yonyou.f4.de.DEMessage;

/**
 * @author Administrator
 *
 */
@Service
public class SADCS053Impl extends BaseImpl implements SADCS053 {

	@Autowired SADCS053Cloud Cloud;
    
	private static final Logger logger = LoggerFactory.getLogger(SADCS053Impl.class);	
	@Override
	public DEMessage execute(DEMessage deMsg) throws DEException {
		logger.info("========大客户周报上报接收开始(SADCS053Impl)========");
		try {
			Map<String, Serializable> bodys = deMsg.getBody();
			List<BigCustomerVisitItemDTO> dtoList = new ArrayList<BigCustomerVisitItemDTO>();
			setDTO(dtoList,bodys);
			Cloud.handleExecutor(dtoList);
		} catch (Throwable t) {
			logger.info("========大客户周报上报接收异常(SADCS053Impl)========");
			t.printStackTrace();
		}finally {
		}
		logger.info("========大客户周报上报接收结束(SADCS053Impl)========");
		return null;
	}
	
	private void setDTO(List<BigCustomerVisitItemDTO> dtoList, Map<String, Serializable> bodys){
		for (Entry<String, Serializable> entry : bodys.entrySet()) {
			BigCustomerVisitItemVO vo = new BigCustomerVisitItemVO();
			BigCustomerVisitItemDTO dto = new BigCustomerVisitItemDTO();
			vo = (BigCustomerVisitItemVO)entry.getValue();
			dto.setEntityCode(vo.getEntityCode());
			BeanUtils.copyProperties(vo, dto);
			dtoList.add(dto);
		}
	}

}
