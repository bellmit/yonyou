/**
 * 
 */
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

import com.infoservice.dms.cgcsl.vo.SA010VO;
import com.yonyou.dcs.de.impl.BaseImpl;
import com.yonyou.dcs.gacfca.SADCS011Cloud;
import com.yonyou.dms.DTO.gacfca.SA010DTO;
import com.yonyou.f4.de.DEException;
import com.yonyou.f4.de.DEMessage;
import com.yonyou.f4.de.executer.DEAction;

/**
 * @author Administrator
 *
 */
public class SADCS011 extends BaseImpl implements DEAction{
@Autowired SADCS011Cloud Cloud;
    
	private static final Logger logger = LoggerFactory.getLogger(SADCS011.class);
	@Override
	public DEMessage execute(DEMessage deMsg) throws DEException {
		logger.info("========展厅预测报告(每天)上报接收开始(SADCS011)========");
		try {
			Map<String, Serializable> bodys = deMsg.getBody();
			List<SA010DTO> dtoList = new ArrayList<SA010DTO>();
			setDTO(dtoList,bodys);
			Cloud.receiveDate(dtoList);
		} catch (Throwable t) {
			logger.info("========展厅预测报告(每天)上报接收异常(SADCS011)========");
			t.printStackTrace();
		}finally {
		}
		logger.info("========展厅预测报告(每天)上报接收结束(SADCS011)========");
		return null;
	}
	
	private void setDTO(List<SA010DTO> dtoList, Map<String, Serializable> bodys){
		for (Entry<String, Serializable> entry : bodys.entrySet()) {
			SA010VO vo = new SA010VO();
			SA010DTO dto = new SA010DTO();
			vo = (SA010VO)entry.getValue();
			dto.setDealerCode(vo.getEntityCode());
			BeanUtils.copyProperties(vo, dto);
			dtoList.add(dto);
		}
	}
}
