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

import com.infoservice.dms.cgcsl.vo.TiDmsNSalesPersonnelVO;
import com.yonyou.dcs.de.SOTDCS002;
import com.yonyou.dcs.gacfca.SOTDCS002Cloud;
import com.yonyou.dms.DTO.gacfca.TiDmsNSalesPersonnelDto;
import com.yonyou.f4.de.DEException;
import com.yonyou.f4.de.DEMessage;
import com.yonyou.f4.de.executer.DEAction;

/**
 * 展厅销售人员信息同步(DMS新增)上报
 * @author Benzc
 * @date 2017年5月16日
 */
@Service
public class SOTDCS002Impl extends BaseImpl implements DEAction,SOTDCS002{
    
    @Autowired SOTDCS002Cloud Cloud;
	
	private static final Logger logger = LoggerFactory.getLogger(SOTDCS002Impl.class);
	
	@Override
	public DEMessage execute(DEMessage deMsg) throws DEException {
		logger.info("=====展厅销售人员信息同步(DMS新增)上报开始(SOTDCS002Impl)=====");
		try {
			Map<String, Serializable> bodys = deMsg.getBody();
			List<TiDmsNSalesPersonnelDto> dtoList = new ArrayList<TiDmsNSalesPersonnelDto>();
			setDTO(dtoList,bodys);
			Cloud.handleExecutor(dtoList);
		} catch (Throwable t) {
			logger.info("========展厅销售人员信息同步(DMS新增)上报异常(SOTDCS002Impl)========");
			t.printStackTrace();
		}finally {
		}
		logger.info("========展厅销售人员信息同步(DMS新增)上报结束(SOTDCS002Impl)========");
		return null;
	}
	
	private void setDTO(List<TiDmsNSalesPersonnelDto> dtoList, Map<String, Serializable> bodys){
		for (Entry<String, Serializable> entry : bodys.entrySet()) {
			TiDmsNSalesPersonnelVO vo = (TiDmsNSalesPersonnelVO)entry.getValue();
			TiDmsNSalesPersonnelDto dto = new TiDmsNSalesPersonnelDto();
			BeanUtils.copyProperties(vo, dto);
			dtoList.add(dto);
		}
	}

}
