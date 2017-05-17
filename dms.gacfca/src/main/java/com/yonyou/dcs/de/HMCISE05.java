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

import com.infoservice.dms.cgcsl.vo.ActivityResultVO;
import com.yonyou.dcs.de.impl.BaseImpl;
import com.yonyou.dcs.gacfca.HMCISE05Cloud;
import com.yonyou.dms.DTO.gacfca.ActivityResultDTO;
import com.yonyou.f4.de.DEMessage;
import com.yonyou.f4.de.executer.DEAction;
/**
 * @Description:活动车辆完工上报接口
 * @author xuqinqin 
 */
@Service
public class HMCISE05  extends BaseImpl  implements DEAction {
	private static final Logger logger = LoggerFactory.getLogger(HMCISE05.class);
	@Autowired
	HMCISE05Cloud cloud ;
	
	@Override
	public DEMessage execute(DEMessage deMsg) {
		logger.info("*************************** HMCISE05开始获取上报的活动车辆完工数据******************************");
		try {
			Map<String, Serializable> bodys = deMsg.getBody();
			LinkedList<ActivityResultDTO> dtoList = new LinkedList<>();
			setDTO(dtoList,bodys);
			cloud.handleExecutor(dtoList);
		}  catch(Throwable t) {
			logger.info("*************************** HMCISE05获取上报的活动车辆完工数据出错******************************");
			t.printStackTrace();
		} finally {
		}
		logger.info("*************************** HMCISE05成功获取上报的活动车辆完工数据******************************");
		return null;
	}
	private void setDTO(LinkedList<ActivityResultDTO> dtoList, Map<String, Serializable> bodys){
		for (Entry<String, Serializable> entry : bodys.entrySet()) {
			ActivityResultVO vo = new ActivityResultVO();
			ActivityResultDTO dto = new ActivityResultDTO();
			vo = (ActivityResultVO)entry.getValue();
			BeanUtils.copyProperties(vo, dto);
			dtoList.add(dto);
		}
	}
}
