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

import com.infoservice.dms.cgcsl.vo.WarrantyRegistVO;
import com.yonyou.dcs.de.impl.BaseImpl;
import com.yonyou.dcs.gacfca.SendDmsMsgToBoldCloud;
import com.yonyou.dms.DTO.gacfca.WarrantyRegistDTO;
import com.yonyou.f4.de.DEException;
import com.yonyou.f4.de.DEMessage;
import com.yonyou.f4.de.executer.DEAction;

/**
 * 处理保修登记数据
 * @author Benzc
 * @date 2017年5月16日
 */
@Service
public class SendDmsMsgToBold extends BaseImpl implements DEAction{
    
	@Autowired SendDmsMsgToBoldCloud Cloud;
    
	private static final Logger logger = LoggerFactory.getLogger(SendDmsMsgToBold.class);
	
	@Override
	public DEMessage execute(DEMessage deMsg) throws DEException {
		logger.info("========处理保修登记数据上报接收开始(SADCS002Impl)========");
		try {
			Map<String, Serializable> bodys = deMsg.getBody();
			List<WarrantyRegistDTO> dtoList = new ArrayList<WarrantyRegistDTO>();
			setDTO(dtoList,bodys);
			Cloud.handleExecutor(dtoList);
		} catch (Throwable t) {
			logger.info("========处理保修登记数据上报接收异常(SADCS002Impl)========");
			t.printStackTrace();
		}finally {
		}
		logger.info("========处理保修登记数据上报接收结束(SADCS002Impl)========");
		return null;
	}
	
	private void setDTO(List<WarrantyRegistDTO> dtoList, Map<String, Serializable> bodys){
		for (Entry<String, Serializable> entry : bodys.entrySet()) {
			WarrantyRegistVO vo = new WarrantyRegistVO();
			WarrantyRegistDTO dto = new WarrantyRegistDTO();
			vo = (WarrantyRegistVO)entry.getValue();
			dto.setDealerCode(vo.getEntityCode());
			BeanUtils.copyProperties(vo, dto);
			dtoList.add(dto);
		}
	}

}
