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

import com.infoservice.dms.cgcsl.SADCS025VO;
import com.infoservice.dms.cgcsl.vo.SADMS054VO;
import com.yonyou.dcs.de.impl.BaseImpl;
import com.yonyou.dcs.gacfca.SADCS025Cloud;
import com.yonyou.dcs.gacfca.SADCS054Cloud;
import com.yonyou.dms.DTO.gacfca.SADCS025Dto;
import com.yonyou.dms.DTO.gacfca.SADMS054DTO;
import com.yonyou.f4.de.DEException;
import com.yonyou.f4.de.DEMessage;
import com.yonyou.f4.de.executer.DEAction;

/**
 * @author Administrator
 *
 */
public class SADCS025 extends BaseImpl implements DEAction {
@Autowired SADCS025Cloud Cloud;
    
	private static final Logger logger = LoggerFactory.getLogger(SADCS025.class);
	@Override
	public DEMessage execute(DEMessage deMsg) throws DEException {
		logger.info("========PAD建档率报表上报接收开始(SADCS025)========");
		try {
			Map<String, Serializable> bodys = deMsg.getBody();
			List<SADCS025Dto> dtoList = new ArrayList<SADCS025Dto>();
			setDTO(dtoList,bodys);
			Cloud.handleExecutor(dtoList);
		} catch (Throwable t) {
			logger.info("========PAD建档率报表上报接收异常(SADCS025)========");
			t.printStackTrace();
		}finally {
		}
		logger.info("========PAD建档率报表上报接收结束(SADCS025)========");
		return null;
	}
	
	private void setDTO(List<SADCS025Dto> dtoList, Map<String, Serializable> bodys){
		for (Entry<String, Serializable> entry : bodys.entrySet()) {
			SADCS025VO vo = new SADCS025VO();
			SADCS025Dto dto = new SADCS025Dto();
			vo = (SADCS025VO)entry.getValue();
			dto.setDealerCode(vo.getEntityCode());
			BeanUtils.copyProperties(vo, dto);
			dtoList.add(dto);
		}
	}
}
