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

import com.infoservice.dms.cgcsl.vo.ProperServManInfoVO;
import com.infoservice.dms.cgcsl.vo.SADCS029VO;
import com.yonyou.dcs.de.impl.BaseImpl;
import com.yonyou.dcs.gacfca.SADCS019Cloud;
import com.yonyou.dcs.gacfca.SADCS029Cloud;
import com.yonyou.dms.DTO.gacfca.ProperServManInfoDTO;
import com.yonyou.dms.DTO.gacfca.SADCS029DTO;
import com.yonyou.f4.de.DEException;
import com.yonyou.f4.de.DEMessage;
import com.yonyou.f4.de.executer.DEAction;

/**
 * @author Administrator
 *
 */
public class SADCS019 extends BaseImpl implements DEAction {
@Autowired SADCS019Cloud Cloud;
    
	private static final Logger logger = LoggerFactory.getLogger(SADCS019.class);
	@Override
	public DEMessage execute(DEMessage deMsg) throws DEException {
		logger.info("========经销商专属客户经理列表上报接收开始(SADCS019)========");
		try {
			Map<String, Serializable> bodys = deMsg.getBody();
			List<ProperServManInfoDTO> dtoList = new ArrayList<ProperServManInfoDTO>();
			setDTO(dtoList,bodys);
			Cloud.handleExecutor(dtoList);
		} catch (Throwable t) {
			logger.info("========经销商专属客户经理列表上报接收异常(SADCS019)========");
			t.printStackTrace();
		}finally {
		}
		logger.info("========经销商专属客户经理列表上报接收结束(SADCS019)========");
		return null;
	}
	
	private void setDTO(List<ProperServManInfoDTO> dtoList, Map<String, Serializable> bodys){
		for (Entry<String, Serializable> entry : bodys.entrySet()) {
			ProperServManInfoVO vo = new ProperServManInfoVO();
			ProperServManInfoDTO dto = new ProperServManInfoDTO();
			vo = (ProperServManInfoVO)entry.getValue();
			dto.setDealerCode(vo.getEntityCode());
			BeanUtils.copyProperties(vo, dto);
			dtoList.add(dto);
		}
	}
}
