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

import com.infoservice.dms.cgcsl.vo.TiCoOverTotalReportVO;
import com.yonyou.dcs.de.impl.BaseImpl;
import com.yonyou.dcs.gacfca.SADCS034Cloud;
import com.yonyou.dms.DTO.gacfca.TiCoOverTotalReportDTO;
import com.yonyou.f4.de.DEMessage;
import com.yonyou.f4.de.executer.DEAction;

/**
 * @Description:接收DMS上报超过60天未交车订单且未交车原因为空的订单
 * @author xuqinqin 
 */
@Service
public class SADCS034  extends BaseImpl  implements DEAction {
	private static final Logger logger = LoggerFactory.getLogger(SADCS034.class);
	@Autowired
	SADCS034Cloud cloud ;
	
	@Override
	public DEMessage execute(DEMessage deMsg) {
		logger.info("*************************** SADCS034  开始获取超过90,60天未交车订单且未交车原因为空******************************");
		try {
			Map<String, Serializable> bodys = deMsg.getBody();
			LinkedList<TiCoOverTotalReportDTO> dtoList = new LinkedList<TiCoOverTotalReportDTO>();
			setDTO(dtoList,bodys);
			cloud.handleExecutor(dtoList);
		}  catch(Throwable t) {
			logger.info("*************************** SADCS034   超过90,60天未交车订单且未交车原因为空上传出错******************************");
			t.printStackTrace();
			try {
			} catch(Exception e) {
				e.printStackTrace();
			}
		} finally {
		}
		logger.info("*************************** SADCS034    成功获取超过90,60天未交车订单且未交车原因为空******************************");
		return null;
	}
	private void setDTO(LinkedList<TiCoOverTotalReportDTO> dtoList, Map<String, Serializable> bodys){
		for (Entry<String, Serializable> entry : bodys.entrySet()) {
			TiCoOverTotalReportVO vo = new TiCoOverTotalReportVO();
			TiCoOverTotalReportDTO dto = new TiCoOverTotalReportDTO();
			vo = (TiCoOverTotalReportVO)entry.getValue();
			dto.setDealerCode(vo.getEntityCode());
			BeanUtils.copyProperties(vo, dto);
			dtoList.add(dto);
		}
	}
}
