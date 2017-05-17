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

import com.infoservice.dms.cgcsl.vo.SADCS050VO;
import com.yonyou.dcs.de.impl.BaseImpl;
import com.yonyou.dcs.gacfca.DMSTODCS050Cloud;
import com.yonyou.dms.common.domains.DTO.basedata.SADCS050Dto;
import com.yonyou.f4.de.DEMessage;
import com.yonyou.f4.de.executer.DEAction;

/**
 * @Description:二手车置换率月报（周报）数据接收接口
 * @author xuqinqin 
 */
@Service
public class DMSTODCS050  extends BaseImpl  implements DEAction {
	private static final Logger logger = LoggerFactory.getLogger(DMSTODCS050.class);
	@Autowired
	DMSTODCS050Cloud cloud050 ;
	
	@Override
	public DEMessage execute(DEMessage deMsg) {
		logger.info("*************************** 开始获取上报的二手车置换率月报（周报）数据 ******************************");
		try {
			Map<String, Serializable> bodys = deMsg.getBody();
			LinkedList<SADCS050Dto> dtoList = new LinkedList<>();
			setDTO(dtoList,bodys);
			cloud050.handleExecutor(dtoList);
		}  catch(Throwable t) {
			logger.info("*************************** 获取上报的二手车置换率月报（周报）数据 出错******************************");
			t.printStackTrace();
			try {
			} catch(Exception e) {
				e.printStackTrace();
			}
		} finally {
		}
		logger.info("*************************** 成功获取上报的二手车置换意向明细数据******************************");
		return null;
	}
	private void setDTO(LinkedList<SADCS050Dto> dtoList, Map<String, Serializable> bodys){
		for (Entry<String, Serializable> entry : bodys.entrySet()) {
			SADCS050VO vo = new SADCS050VO();
			SADCS050Dto dto = new SADCS050Dto();
			vo = (SADCS050VO)entry.getValue();
			dto.setDealerCode(vo.getEntityCode());
			BeanUtils.copyProperties(vo, dto);
			dtoList.add(dto);
		}
	}
}
