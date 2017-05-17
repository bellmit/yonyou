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

import com.infoservice.dms.cgcsl.vo.SA010VO;
import com.yonyou.dcs.de.impl.BaseImpl;
import com.yonyou.dcs.gacfca.SADCS010Cloud;
import com.yonyou.dms.DTO.gacfca.SA010DTO;
import com.yonyou.f4.de.DEException;
import com.yonyou.f4.de.DEMessage;
import com.yonyou.f4.de.executer.DEAction;

/**
 * 展厅预测报告数据上报
 * @author Benzc
 * @date 2017年5月15日
 */
@Service
public class SADCS010 extends BaseImpl implements DEAction{
	
	@Autowired SADCS010Cloud Cloud;
	
	private static final Logger logger = LoggerFactory.getLogger(SADCS010.class);

	@Override
	public DEMessage execute(DEMessage deMsg) throws DEException {
		logger.info("========展厅预测报告数据上报接收开始(SADCS010Impl)========");
		try {
			Map<String, Serializable> bodys = deMsg.getBody();
			List<SA010DTO> dtoList = new ArrayList<SA010DTO>();
			setDTO(dtoList,bodys);
			Cloud.receiveDate(dtoList);
		} catch (Throwable t) {
			logger.info("========展厅预测报告数据上报接收异常(SADCS010Impl)========");
			t.printStackTrace();
		}finally {
		}
		logger.info("========展厅预测报告数据上报接收结束(SADCS010Impl)========");
		return null;
	}
	
	private void setDTO(List<SA010DTO> dtoList, Map<String, Serializable> bodys){
		for (Entry<String, Serializable> entry : bodys.entrySet()) {
			SA010VO vo = (SA010VO)entry.getValue();
			SA010DTO dto = new SA010DTO();
			dto.setDealerCode(vo.getEntityCode());
			BeanUtils.copyProperties(vo, dto);
			dtoList.add(dto);
		}
	}

}
