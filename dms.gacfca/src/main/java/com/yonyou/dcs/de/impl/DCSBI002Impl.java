package com.yonyou.dcs.de.impl;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoservice.dms.cgcsl.vo.BI002VO;
import com.yonyou.dcs.de.DCSBI002;
import com.yonyou.dcs.gacfca.DCSBI002Cloud;
import com.yonyou.dms.DTO.gacfca.TtVisitingRecordDTO;
import com.yonyou.f4.de.DEException;
import com.yonyou.f4.de.DEMessage;
import com.yonyou.f4.de.executer.DEAction;
/**
 * 
* @ClassName: DCSBI002Impl 
* @Description: 展厅客户数据上报
* @author zhengzengliang 
* @date 2017年4月6日 下午7:42:07 
*
 */
@Service
public class DCSBI002Impl extends BaseImpl  implements DEAction,DCSBI002 {
	
	private static final Logger logger = LoggerFactory.getLogger(DCSBI002Impl.class);

	@Autowired
	DCSBI002Cloud dcsi002Cloud ;
	
	@Override
	public DEMessage execute(DEMessage deMsg) throws DEException {
		logger.info("*************************** 开始获取展厅客户数据 ******************************");
		try {
			Map<String, Serializable> bodys = deMsg.getBody();
			LinkedList<TtVisitingRecordDTO> dtoList = new LinkedList<>();
			setDTO(dtoList,bodys);
			dcsi002Cloud.receiveDate(dtoList);
		} catch(Throwable t) {
			t.printStackTrace();
		} finally {
		}
		logger.info("*************************** 成功获取销售订单数据 ******************************");
		return null;
	}
	
	/**
	 * 数据转换
	 * @param dto
	 * @param bodys
	 */
	private void setDTO(LinkedList<TtVisitingRecordDTO> dtoList, Map<String, Serializable> bodys) {
		for (Entry<String, Serializable> entry : bodys.entrySet()) {
			BI002VO bi002Vo = new BI002VO();
			TtVisitingRecordDTO dto = new TtVisitingRecordDTO();
			bi002Vo = (BI002VO) entry.getValue();
			dto.setDealerCode(bi002Vo.getEntityCode());
			BeanUtils.copyProperties(bi002Vo, dto);
			dtoList.add(dto);
		}
	}
	

}
