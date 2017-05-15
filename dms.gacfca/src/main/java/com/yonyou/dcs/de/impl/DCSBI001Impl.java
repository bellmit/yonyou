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

import com.infoservice.dms.cgcsl.vo.BI001VO;
import com.yonyou.dcs.de.DCSBI001;
import com.yonyou.dcs.gacfca.DCSBI001Cloud;
import com.yonyou.dms.DTO.gacfca.TtSalesOrderDTO;
import com.yonyou.f4.de.DEException;
import com.yonyou.f4.de.DEMessage;
import com.yonyou.f4.de.executer.DEAction;
/**
 * 
* @ClassName: DCSBI001Impl 
* @Description: 销售订单上报数据
* @author zhengzengliang 
* @date 2017年4月6日 下午3:29:08 
*
 */
@Service
public class DCSBI001Impl extends BaseImpl  implements DEAction,DCSBI001 {
	
	private static final Logger logger = LoggerFactory.getLogger(DCSBI001Impl.class);

	@Autowired
	DCSBI001Cloud dcsbi001Cloud ;
	
	@Override
	public DEMessage execute(DEMessage deMsg) throws DEException {
		logger.info("*************************** 开始获取销售订单数据 ******************************");
		try {
			Map<String, Serializable> bodys = deMsg.getBody();
			LinkedList<TtSalesOrderDTO> dtoList = new LinkedList<>();
			setDTO(dtoList,bodys);
			dcsbi001Cloud.receiveDate(dtoList);
		} catch(Throwable t) {
			t.printStackTrace();
			try {
			} catch(Exception e) {
				e.printStackTrace();
			}
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
	private void setDTO(LinkedList<TtSalesOrderDTO> dtoList, Map<String, Serializable> bodys) {
		for (Entry<String, Serializable> entry : bodys.entrySet()) {
			BI001VO vo = new BI001VO();
			TtSalesOrderDTO dto = new TtSalesOrderDTO();
			vo = (BI001VO) entry.getValue();
			dto.setDealerCode(vo.getEntityCode());
			dto = new TtSalesOrderDTO();
			BeanUtils.copyProperties(vo, dto);
			dtoList.add(dto);
		}
	}
	
	

}
