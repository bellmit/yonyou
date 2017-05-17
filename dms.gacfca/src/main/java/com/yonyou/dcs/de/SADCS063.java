package com.yonyou.dcs.de;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoservice.dms.cgcsl.vo.SADMS063VO;
import com.yonyou.dcs.de.impl.BaseImpl;
import com.yonyou.dcs.gacfca.SADCS063Cloud;
import com.yonyou.dms.common.domains.DTO.basedata.SADMS063Dto;
import com.yonyou.f4.de.DEMessage;
import com.yonyou.f4.de.executer.DEAction;

/**
 * @Description:留存订单上报接口
 * @author xuqinqin 
 */
@Service
public class SADCS063  extends BaseImpl  implements DEAction {
	private static final Logger logger = LoggerFactory.getLogger(SADCS063.class);
	@Autowired
	SADCS063Cloud cloud ;
	
	@Override
	public DEMessage execute(DEMessage deMsg) {
		logger.info("*************************** SADCS063  开始获取留存订单上报数据******************************");
		try {
			Map<String, Serializable> bodys = deMsg.getBody();
			LinkedList<SADMS063Dto> dtoList = new LinkedList<>();
			setDTO(dtoList,bodys);
			cloud.handleExecutor(dtoList);
		}  catch(Throwable t) {
			logger.info("*************************** SADCS063  留存订单上报数据上传出错******************************");
			t.printStackTrace();
		} finally {
		}
		logger.info("*************************** SADCS063  成功获取留存订单上报数据******************************");
		return null;
	}
	private void setDTO(LinkedList<SADMS063Dto> dtoList, Map<String, Serializable> bodys){
		for (Entry<String, Serializable> entry : bodys.entrySet()) {
			SADMS063VO vo = new SADMS063VO();
			SADMS063Dto dto = new SADMS063Dto();
			vo = (SADMS063VO)entry.getValue();
			dto.setDealerCode(vo.getEntityCode());
			BeanUtils.copyProperties(vo, dto);
			dtoList.add(dto);
		}
	}
}
