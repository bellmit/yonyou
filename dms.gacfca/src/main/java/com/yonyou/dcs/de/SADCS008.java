package com.yonyou.dcs.de;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoservice.dms.cgcsl.vo.SalesOrderVO;
import com.yonyou.dcs.de.impl.BaseImpl;
import com.yonyou.dcs.gacfca.SADCS008Cloud;
import com.yonyou.dms.DTO.gacfca.SalesOrderDto;
import com.yonyou.f4.de.DEException;
import com.yonyou.f4.de.DEMessage;
import com.yonyou.f4.de.executer.DEAction;
/**
 * 经销商车辆实销数据上报
 * @author Benzc
 * @date 2017年5月16日
 */
@Service
public class SADCS008 extends BaseImpl  implements DEAction {
	
	@Autowired
	SADCS008Cloud Could;
	
	private static final Logger logger = LoggerFactory.getLogger(SADCS008.class);
	@Override
	public DEMessage execute(DEMessage deMsg) throws DEException {
		logger.info("===============经销商车辆实销数据上报接收开始(SADCS008)==============");
		try {
			Map<String, Serializable> bodys = deMsg.getBody();
			LinkedList<SalesOrderDto> dtoList = new LinkedList<>();
			setDTO(dtoList,bodys);
			Could.receiveDate(dtoList);
		} catch(Throwable t) {
			logger.info("===============经销商车辆实销数据上报接收异常(SADCS008)==============");
			t.printStackTrace();
		} finally {
		}
		logger.info("===============经销商车辆实销数据上报接收结束(SADCS008)==============");
		return null;
	}
	
	/**
	 * 数据转换
	 * @param dto
	 * @param bodys
	 */
	private void setDTO(List<SalesOrderDto> dtoList, Map<String, Serializable> bodys){
		for (Entry<String, Serializable> entry : bodys.entrySet()) {
			SalesOrderVO vo = new SalesOrderVO();
			SalesOrderDto dto = new SalesOrderDto();
			vo = (SalesOrderVO)entry.getValue();
			dto.setDealerCode(vo.getEntityCode());
			BeanUtils.copyProperties(vo, dto);
			dtoList.add(dto);
		}
	}
	
	

}
