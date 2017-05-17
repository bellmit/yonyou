package com.yonyou.dcs.de;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.infoservice.dms.cgcsl.vo.TiDmsUCustomerStatusVO;
import com.yonyou.dcs.de.impl.BaseImpl;
import com.yonyou.dcs.gacfca.SOTDCS015Cloud;
import com.yonyou.dms.DTO.gacfca.TiDmsUCustomerStatusDto;
import com.yonyou.f4.de.DEException;
import com.yonyou.f4.de.DEMessage;
import com.yonyou.f4.de.executer.DEAction;

public class SOTDCS015 extends BaseImpl implements DEAction {
	
	@Autowired
	SOTDCS015Cloud sotdcd015Cloud;
	
	private static final Logger logger = LoggerFactory.getLogger(SOTDCS015.class);

	@Override
//	@TxnConn
	public DEMessage execute(DEMessage deMsg) throws DEException {
		logger.info("===============更新客户信息(休眠，订单，交车客户状态数据传递)DMS更新接收开始(SOTDCS015)==============");
		try {
			Map<String, Serializable> bodys = deMsg.getBody();
			LinkedList<TiDmsUCustomerStatusDto> dtoList = new LinkedList<TiDmsUCustomerStatusDto>();
			setDTO(dtoList,bodys);
			sotdcd015Cloud.handleExecutor(dtoList);
		}catch (Throwable t) {
			logger.info("===============更新客户信息(休眠，订单，交车客户状态数据传递)DMS更新接异常(SOTDCS015)==============");
		}finally {
		}
		logger.info("===============更新客户信息(休眠，订单，交车客户状态数据传递)DMS更新接收结束(SOTDCS015)==============");
		return null;
	}
	
	private void setDTO(LinkedList<TiDmsUCustomerStatusDto> dtoList, Map<String, Serializable> bodys){
		for (Entry<String, Serializable> entry : bodys.entrySet()) {
			TiDmsUCustomerStatusVO vo = (TiDmsUCustomerStatusVO)entry.getValue();
			TiDmsUCustomerStatusDto dto = new TiDmsUCustomerStatusDto();
			dto.setDealerCode(vo.getEntityCode());
			BeanUtils.copyProperties(vo, dto);
			dtoList.add(dto);
		}
	}

}
