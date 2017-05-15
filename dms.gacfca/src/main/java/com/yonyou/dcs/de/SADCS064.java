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

import com.infoservice.dms.cgcsl.vo.TtVehiclePdiResultVO;
import com.yonyou.dcs.de.impl.BaseImpl;
import com.yonyou.dcs.gacfca.SADCS064Cloud;
import com.yonyou.dms.DTO.gacfca.TtVehiclePdiResultDTO;
import com.yonyou.f4.de.DEMessage;
import com.yonyou.f4.de.executer.DEAction;

/**
 * @Description:PDI检查上报接口
 * @author xuqinqin 
 */
@Service
public class SADCS064  extends BaseImpl  implements DEAction{
	private static final Logger logger = LoggerFactory.getLogger(SADCS064.class);
	@Autowired
	SADCS064Cloud cloud ;
	
	@Override
	public DEMessage execute(DEMessage deMsg) {
		logger.info("*************************** SADCS064  开始获取PDI检查上报数据******************************");
		try {
			Map<String, Serializable> bodys = deMsg.getBody();
			LinkedList<TtVehiclePdiResultDTO> dtoList = new LinkedList<>();
			setDTO(dtoList,bodys);
			cloud.receiveDate(dtoList);
		}  catch(Throwable t) {
			logger.info("*************************** SADCS064  PDI检查上报数据上传出错******************************");
			t.printStackTrace();
		} finally {
		}
		logger.info("*************************** SADCS064  成功获取PDI检查上报数据******************************");
		return null;
	}
	private void setDTO(LinkedList<TtVehiclePdiResultDTO> dtoList, Map<String, Serializable> bodys){
		for (Entry<String, Serializable> entry : bodys.entrySet()) {
			TtVehiclePdiResultVO vo = new TtVehiclePdiResultVO();
			TtVehiclePdiResultDTO dto = new TtVehiclePdiResultDTO();
			vo = (TtVehiclePdiResultVO)entry.getValue();
			dto.setDealerCode(vo.getEntityCode());
			BeanUtils.copyProperties(vo, dto);
			dtoList.add(dto);
		}
	}
}
