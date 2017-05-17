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

import com.infoservice.dms.cgcsl.vo.SADCS049VO;
import com.yonyou.dcs.de.impl.BaseImpl;
import com.yonyou.dcs.gacfca.DMSTODCS049BCloud;
import com.yonyou.dms.DTO.gacfca.SADCS049Dto;
import com.yonyou.f4.de.DEMessage;
import com.yonyou.f4.de.executer.DEAction;
/**
 * @Description:二手车置换意向明细数据接口
 * @author xuqinqin 
 */
@Service
public class DMSTODCS049  extends BaseImpl  implements DEAction {
	private static final Logger logger = LoggerFactory.getLogger(DMSTODCS049.class);
	@Autowired
	DMSTODCS049BCloud cloud049 ;
	
	@Override
	public DEMessage execute(DEMessage deMsg) {
		logger.info("*************************** 开始获取上报的二手车置换意向明细数据******************************");
		try {
			Map<String, Serializable> bodys = deMsg.getBody();
			LinkedList<SADCS049Dto> dtoList = new LinkedList<>();
			setDTO(dtoList,bodys);
			cloud049.handleExecutor(dtoList);
		}  catch(Throwable t) {
			logger.info("*************************** 获取上报的二手车置换意向明细数据出错******************************");
			t.printStackTrace();
		} finally {
		}
		logger.info("*************************** 成功获取上报的二手车置换意向明细数据******************************");
		return null;
	}
	private void setDTO(LinkedList<SADCS049Dto> dtoList, Map<String, Serializable> bodys){
		for (Entry<String, Serializable> entry : bodys.entrySet()) {
			SADCS049VO vo = new SADCS049VO();
			SADCS049Dto dto = new SADCS049Dto();
			vo = (SADCS049VO)entry.getValue();
			dto.setDealerCode(vo.getEntityCode());
			BeanUtils.copyProperties(vo, dto);
			dtoList.add(dto);
		}
	}
}
