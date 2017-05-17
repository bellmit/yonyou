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

import com.infoservice.dms.cgcsl.vo.InsProposalVO;
import com.yonyou.dcs.de.impl.BaseImpl;
import com.yonyou.dcs.gacfca.SADCS058Cloud;
import com.yonyou.dms.DTO.gacfca.InsProposalDTO;
import com.yonyou.f4.de.DEException;
import com.yonyou.f4.de.DEMessage;
import com.yonyou.f4.de.executer.DEAction;

/**
 * 投保单上端接口
 * @author Benzc
 * @date 2017年5月15日
 */
@Service
public class SADCS058 extends BaseImpl implements DEAction{
	
	@Autowired
	SADCS058Cloud Cloud;
	
	private static final Logger logger = LoggerFactory.getLogger(SADCS058.class);

	@Override
	public DEMessage execute(DEMessage deMsg) throws DEException {
		logger.info("====投保单上报接收开始(SADCS058Impl)====");
		try {
			Map<String, Serializable> bodys = deMsg.getBody();
			List<InsProposalDTO> dtoList = new ArrayList<>();
			setDTO(dtoList,bodys);
			Cloud.handleExecutor(dtoList);
		} catch (Throwable t) {
			logger.info("====投保单上报接收异常(SADCS058Impl)====");
			t.printStackTrace();
		}
		logger.info("====投保单上报接收结束(SADCS058Impl)====");
		return null;
	}
	
	private void setDTO(List<InsProposalDTO> dtoList, Map<String, Serializable> bodys) {
		for (Entry<String, Serializable> entry : bodys.entrySet()) {
			InsProposalVO vo = new InsProposalVO();
			InsProposalDTO dto = new InsProposalDTO();
			vo = (InsProposalVO) entry.getValue();
			dto.setDealerCode(vo.getEntityCode());
			BeanUtils.copyProperties(vo, dto);
			dtoList.add(dto);
		}
	}

}
