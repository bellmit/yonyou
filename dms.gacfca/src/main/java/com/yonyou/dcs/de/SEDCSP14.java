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

import com.infoservice.dms.cgcsl.vo.SEDCSP14VO;
import com.yonyou.dcs.de.impl.BaseImpl;
import com.yonyou.dcs.gacfca.SEDCSP14Cloud;
import com.yonyou.dms.DTO.gacfca.SEDCSP14DTO;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.f4.de.DEMessage;
import com.yonyou.f4.de.executer.DEAction;

@Service
public class SEDCSP14 extends BaseImpl  implements DEAction {

	private static final Logger logger = LoggerFactory.getLogger(SEDCSP14.class);
	
	@Autowired
	SEDCSP14Cloud cloud ;
	
	@Override
	public DEMessage execute(DEMessage deMsg){
		logger.info("***************************SEDCSP14 同步运单收货管理回执信息开始******************************");
		try {
			Map<String, Serializable> bodys = deMsg.getBody();
			LinkedList<SEDCSP14DTO> dtoList = new LinkedList<>();
			setDTO(dtoList,bodys);
			cloud.handleExecutor(dtoList);
		} catch (Exception e) {
			logger.error("运单收货管理回执执行失败", e);
			throw new ServiceBizException(e);
		}

		logger.info("***************************SEDCSP14  运单收货管理回执执行成功 ******************************");
		return null;
	}
	/**
	 * 数据转换
	 * @param dtoList
	 * @param bodys
	 */
	private void setDTO(LinkedList<SEDCSP14DTO> dtoList, Map<String, Serializable> bodys){
		for (Entry<String, Serializable> entry : bodys.entrySet()) {
			SEDCSP14VO vo = new SEDCSP14VO();
			SEDCSP14DTO dto = new SEDCSP14DTO();
			vo = (SEDCSP14VO)entry.getValue();
			BeanUtils.copyProperties(vo, dto);
			dtoList.add(dto);
		}
	}
}
