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
import com.yonyou.dcs.gacfca.SEDCSP03Cloud;
import com.yonyou.dms.DTO.gacfca.SEDCSP03DTO;
import com.yonyou.f4.de.DEMessage;
import com.yonyou.f4.de.executer.DEAction;

/**
 * @Description:配件订货上报接口 
 * @author xuqinqin 
 */
@Service
public class SEDCSP03  extends BaseImpl  implements DEAction{
	private static final Logger logger = LoggerFactory.getLogger(SEDCSP03.class);
	
	@Autowired
	SEDCSP03Cloud cloud ;
	
	@Override
	public DEMessage execute(DEMessage deMsg) {
		logger.info("*************************** 开始获取上报的配件订货数据******************************");
		try {
			Map<String, Serializable> bodys = deMsg.getBody();
			LinkedList<SEDCSP03DTO> voList = new LinkedList<>();
			for (Entry<String, Serializable> entry : bodys.entrySet()) {
				SEDCSP03DTO vo = new SEDCSP03DTO();
				vo = (SEDCSP03DTO) entry.getValue();
				voList.add(vo);
			}
			cloud.receiveData(voList);
		}  catch(Throwable t) {
			logger.info("*************************** 获取上报的配件订货数据出错******************************");
			t.printStackTrace();
		} finally {
		}
		logger.info("*************************** 成功获取上报的配件订货数据******************************");
		return null;
	}
}
