package com.yonyou.dcs.de.impl;


import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoservice.dms.cgcsl.vo.dccVO;
import com.yonyou.dcs.de.SADCS003;
import com.yonyou.dcs.gacfca.SADCS003Cloud;
import com.yonyou.dcs.util.DEUtil;
import com.yonyou.dms.DTO.gacfca.dccDto;

/**
 * DCC潜在客户信息下发
 * @author Benzc
 * @date 2017年5月15日
 */
@Service
public class SADCS003Impl extends BaseImpl implements SADCS003{
	
	private static final Logger logger = LoggerFactory.getLogger(SADCS003Impl.class);
	
	@Autowired SADCS003Cloud Cloud;

	@Override
	public String sendData() throws Exception {
		logger.info("==========DCC潜在客户信息下发执行开始(SADCS003Impl)============");
		try {
			//下发的数据
			List<dccDto> dataList = Cloud.getDataList();
			send(dataList);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		logger.info("==========DCC潜在客户信息下发执行结束(SADCS003Impl)============");
		return null;
	}
	
	/**
	 * DE消息发送
	 * @param dataList
	 * @param dealerCode
	 * @throws Exception 
	 */
	private String send(List<dccDto> dataList) throws Exception {
		try {
			if(dataList != null && dataList.size() > 0){
				List<dccVO> vos = new ArrayList<>();
				setVos(vos,dataList);
				Map<String, Serializable> body = DEUtil.assembleBody(vos);
				sendAllMsg("SADMS003", body);
				logger.info("SADCS003 DCC潜在客户信息下发发送成功======size："+dataList.size());
			}else{
				logger.info("SADCS003 DCC潜在客户信息为空========");
			}
		} catch (Throwable t) {
			logger.info("SADCS003 DCC潜在客户信息下发失败======size："+dataList.size());
			logger.error(t.getMessage(), t);
		}
		return null;
	}
	
	/**
	 * 数据转换
	 * @param vos
	 * @param dataList
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	private void setVos(List<dccVO> vos, List<dccDto> dataList) throws IllegalAccessException, InvocationTargetException {
		for (int i = 0; i < dataList.size(); i++) {
			dccDto dto = dataList.get(i);
			dccVO vo = new dccVO();
			vo.setEntityCode(dto.getDealerCode());
			BeanUtils.copyProperties(dto, vo);
			vos.add(vo);
		}
	}

}
