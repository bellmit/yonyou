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

import com.infoservice.dms.cgcsl.vo.VsStockEntryItemVO;
import com.yonyou.dcs.de.impl.BaseImpl;
import com.yonyou.dcs.gacfca.SADCS002Cloud;
import com.yonyou.dms.DTO.gacfca.VsStockEntryItemDto;
import com.yonyou.f4.de.DEException;
import com.yonyou.f4.de.DEMessage;
import com.yonyou.f4.de.executer.DEAction;
/**
 * 经销商车辆验收数据上报
 * @author Benzc
 * @date 2017年5月15日
 */
@Service
public class SADCS002 extends BaseImpl implements DEAction{
	
	@Autowired SADCS002Cloud SADCS002Cloud;
    
	private static final Logger logger = LoggerFactory.getLogger(SADCS002.class);
	
	@Override
	public DEMessage execute(DEMessage deMsg) throws DEException {
		logger.info("========经销商车辆验收数据上报接收开始(SADCS002Impl)========");
		try {
			Map<String, Serializable> bodys = deMsg.getBody();
			List<VsStockEntryItemDto> dtoList = new ArrayList<VsStockEntryItemDto>();
			setDTO(dtoList,bodys);
			SADCS002Cloud.receiveDate(dtoList);
		} catch (Throwable t) {
			logger.info("========经销商车辆验收数据上报接收异常(SADCS002Impl)========");
			t.printStackTrace();
		}finally {
		}
		logger.info("========经销商车辆验收数据上报接收结束(SADCS002Impl)========");
		return null;
	}
	
	private void setDTO(List<VsStockEntryItemDto> dtoList, Map<String, Serializable> bodys){
		for (Entry<String, Serializable> entry : bodys.entrySet()) {
			VsStockEntryItemVO vo = new VsStockEntryItemVO();
			VsStockEntryItemDto dto = new VsStockEntryItemDto();
			vo = (VsStockEntryItemVO)entry.getValue();
			dto.setDealerCode(vo.getEntityCode());
			BeanUtils.copyProperties(vo, dto);
			dtoList.add(dto);
		}
	}

}
