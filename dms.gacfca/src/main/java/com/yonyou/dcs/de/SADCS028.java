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

import com.infoservice.dms.cgcsl.vo.WXBindingVO;
import com.yonyou.dcs.de.impl.BaseImpl;
import com.yonyou.dcs.gacfca.SADCS028Cloud;
import com.yonyou.dms.DTO.gacfca.WXBindingDTO;
import com.yonyou.f4.de.DEException;
import com.yonyou.f4.de.DEMessage;
import com.yonyou.f4.de.executer.DEAction;

/**
 * @author Administrator
 *
 */
public class SADCS028 extends BaseImpl implements DEAction{
@Autowired SADCS028Cloud Cloud;
    
	private static final Logger logger = LoggerFactory.getLogger(SADCS028.class);
	@Override
	public DEMessage execute(DEMessage deMsg) throws DEException {
		logger.info("========当月进厂客户微信绑定率统计报表数据上报接收开始(SADCS028)========");
		try {
			Map<String, Serializable> bodys = deMsg.getBody();
			List<WXBindingDTO> dtoList = new ArrayList<WXBindingDTO>();
			setDTO(dtoList,bodys);
			Cloud.handleExecutor(dtoList);
		} catch (Throwable t) {
			logger.info("========当月进厂客户微信绑定率统计报表数据上报接收异常(SADCS028)========");
			t.printStackTrace();
		}finally {
		}
		logger.info("========当月进厂客户微信绑定率统计报表数据上报接收结束(SADCS028)========");
		return null;
	}
	
	private void setDTO(List<WXBindingDTO> dtoList, Map<String, Serializable> bodys){
		for (Entry<String, Serializable> entry : bodys.entrySet()) {
			WXBindingVO vo = new WXBindingVO();
			WXBindingDTO dto = new WXBindingDTO();
			vo = (WXBindingVO)entry.getValue();
			dto.setDealerCode(vo.getEntityCode());
			BeanUtils.copyProperties(vo, dto);
			dtoList.add(dto);
		}
	}
}
