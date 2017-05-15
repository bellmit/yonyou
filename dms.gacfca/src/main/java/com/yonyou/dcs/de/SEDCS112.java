package com.yonyou.dcs.de;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.de.impl.BaseImpl;
import com.yonyou.dcs.gacfca.SEDCS112Cloud;
import com.yonyou.dms.DTO.gacfca.OpRepairOrderDTO;
import com.yonyou.f4.de.DEMessage;
import com.yonyou.f4.de.executer.DEAction;

/**
 * @Description:未结算工单接收dms上报数据接口
 * @author xuqinqin 
 */
@Service
public class SEDCS112  extends BaseImpl  implements DEAction {
	private static final Logger logger = LoggerFactory.getLogger(SEDCS112.class);
	@Autowired
	SEDCS112Cloud cloud ;
	
	@Override
	public DEMessage execute(DEMessage deMsg) {
		logger.info("*************************** 开始获取上报的未结算工单数据******************************");
		try {
			Map<String, Serializable> bodys = deMsg.getBody();
			LinkedList<OpRepairOrderDTO> voList = new LinkedList<>();
			for (Entry<String, Serializable> entry : bodys.entrySet()) {
				OpRepairOrderDTO vo = new OpRepairOrderDTO();
				vo = (OpRepairOrderDTO) entry.getValue();
				voList.add(vo);
			}
			cloud.receiveData(voList);
		}  catch(Throwable t) {
			logger.info("*************************** 获取上报的未结算工单数据出错******************************");
			t.printStackTrace();
		} finally {
		}
		logger.info("*************************** 成功获取上报的未结算工单数据******************************");
		return null;
	}
	
}
