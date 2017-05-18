/**
 * 
 */
package com.yonyou.dcs.de.impl;

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

import com.infoservice.dms.cgcsl.vo.SEDMS022VO;
import com.infoservice.dms.cgcsl.vo.TmWxReserverInfoReportVO;
import com.yonyou.dcs.de.SADCS030;
import com.yonyou.dcs.gacfca.SADCS024Cloud;
import com.yonyou.dcs.gacfca.SADCS030Cloud;
import com.yonyou.dms.DTO.gacfca.SEDMS022DTO;
import com.yonyou.dms.DTO.gacfca.TmWxReserverInfoReportDTO;
import com.yonyou.f4.de.DEException;
import com.yonyou.f4.de.DEMessage;

/**
 * @author Administrator
 *
 */
@Service
public class SADCS030Impl implements SADCS030 {
	@Autowired SADCS030Cloud Cloud;
    
	private static final Logger logger = LoggerFactory.getLogger(SADCS030Impl.class);
	@Override
	public DEMessage execute(DEMessage deMsg) throws DEException {
		logger.info("========微信预约信息DMS上报接收开始(SADCS030Impl)========");
		try {
			Map<String, Serializable> bodys = deMsg.getBody();
			List<TmWxReserverInfoReportDTO> dtoList = new ArrayList<TmWxReserverInfoReportDTO>();
			setDTO(dtoList,bodys);
			Cloud.handleExecutor(dtoList);
		} catch (Throwable t) {
			logger.info("========微信预约信息DMS上报接收异常(SADCS030Impl)========");
			t.printStackTrace();
		}finally {
		}
		logger.info("========微信预约信息DMS上报接收结束(SADCS030Impl)========");
		return null;
	}
	
	private void setDTO(List<TmWxReserverInfoReportDTO> dtoList, Map<String, Serializable> bodys){
		for (Entry<String, Serializable> entry : bodys.entrySet()) {
			TmWxReserverInfoReportVO vo = new TmWxReserverInfoReportVO();
			TmWxReserverInfoReportDTO dto = new TmWxReserverInfoReportDTO();
			vo = (TmWxReserverInfoReportVO)entry.getValue();
			dto.setDealerCode(vo.getDealerCode());
			BeanUtils.copyProperties(vo, dto);
			dtoList.add(dto);
		}
	}

}
