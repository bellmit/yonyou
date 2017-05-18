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

import com.infoservice.dms.cgcsl.vo.SA010VO;
import com.infoservice.dms.cgcsl.vo.SADCS029VO;
import com.yonyou.dcs.de.impl.BaseImpl;
import com.yonyou.dcs.gacfca.SADCS029Cloud;
import com.yonyou.dms.DTO.gacfca.SADCS029DTO;
import com.yonyou.f4.de.DEException;
import com.yonyou.f4.de.DEMessage;
import com.yonyou.f4.de.executer.DEAction;

/**
 * @author Administrator
 *
 */
public class SADCS029 extends BaseImpl implements DEAction{
@Autowired SADCS029Cloud Cloud;
    
	private static final Logger logger = LoggerFactory.getLogger(SADCS029.class);
	@Override
	public DEMessage execute(DEMessage deMsg) throws DEException {
		logger.info("========总部监控经销商调价报表上报接收开始(SADCS029)========");
		try {
			Map<String, Serializable> bodys = deMsg.getBody();
			List<SADCS029DTO> dtoList = new ArrayList<SADCS029DTO>();
			setDTO(dtoList,bodys);
			Cloud.handleExecutor(dtoList);
		} catch (Throwable t) {
			logger.info("========总部监控经销商调价报表上报接收异常(SADCS029)========");
			t.printStackTrace();
		}finally {
		}
		logger.info("========总部监控经销商调价报表上报接收结束(SADCS029)========");
		return null;
	}
	
	private void setDTO(List<SADCS029DTO> dtoList, Map<String, Serializable> bodys){
		for (Entry<String, Serializable> entry : bodys.entrySet()) {
			SADCS029VO vo = new SADCS029VO();
			SADCS029DTO dto = new SADCS029DTO();
			vo = (SADCS029VO)entry.getValue();
			dto.setDealerCode(vo.getEntityCode());
			BeanUtils.copyProperties(vo, dto);
			dtoList.add(dto);
		}
	}
}
