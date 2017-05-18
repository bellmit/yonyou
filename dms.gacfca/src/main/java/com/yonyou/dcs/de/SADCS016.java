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

import com.infoservice.dms.cgcsl.vo.UcReplaceRebateApplyVO;
import com.yonyou.dcs.de.impl.BaseImpl;
import com.yonyou.dcs.gacfca.SADCS016Cloud;
import com.yonyou.dms.DTO.gacfca.UcReplaceRebateApplyDto;
import com.yonyou.f4.de.DEException;
import com.yonyou.f4.de.DEMessage;
import com.yonyou.f4.de.executer.DEAction;

/**
 * @author Administrator
 *
 */
@Service
public class SADCS016 extends BaseImpl implements DEAction {
	private static final Logger logger = LoggerFactory.getLogger(SADCS016.class);
	
	@Autowired
	SADCS016Cloud cloud;
	@Override
	public DEMessage execute(DEMessage deMsg) throws DEException {
		logger.info("========二手车置换返利数据上报接收开始(SADCS016)========");
		try {
			Map<String, Serializable> bodys = deMsg.getBody();
			List<UcReplaceRebateApplyDto> dtoList = new ArrayList<UcReplaceRebateApplyDto>();
			setDTO(dtoList,bodys);
			cloud.handleExecutor(dtoList);
		} catch (Throwable t) {
			logger.info("========二手车置换返利数据上报接收异常(SADCS016)========");
			t.printStackTrace();
		}finally {
		}
		logger.info("========二手车置换返利数据上报接收结束(SADCS016)========");
		return null;
	}
	
	private void setDTO(List<UcReplaceRebateApplyDto> dtoList, Map<String, Serializable> bodys){
		for (Entry<String, Serializable> entry : bodys.entrySet()) {
			UcReplaceRebateApplyVO vo = new UcReplaceRebateApplyVO();
			UcReplaceRebateApplyDto dto = new UcReplaceRebateApplyDto();
			vo = (UcReplaceRebateApplyVO)entry.getValue();
			dto.setDealerCode(vo.getEntityCode());
			BeanUtils.copyProperties(vo, dto);
			dtoList.add(dto);
		}
	}

}
