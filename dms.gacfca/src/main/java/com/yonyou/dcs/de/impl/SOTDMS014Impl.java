package com.yonyou.dcs.de.impl;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoservice.dms.cgcsl.vo.TiAppUSwapVO;
import com.yonyou.dcs.de.SOTDMS014;
import com.yonyou.dcs.gacfca.SOTDMS014Cloud;
import com.yonyou.dcs.util.DEUtil;
import com.yonyou.dms.DTO.gacfca.TiAppUSwapDto;

/**
 * 更新客户信息（置换需求）(APP更新)下发
 * @author Benzc
 * @date 2017年5月17日
 */
@Service
public class SOTDMS014Impl extends BaseImpl implements SOTDMS014{

	private static final Logger logger = LoggerFactory.getLogger(SOTDMS014Impl.class);
	
	@Autowired SOTDMS014Cloud Cloud;
	
	@Override
	public String sendData() throws Exception {
		logger.info("==========更新客户信息（置换需求）(APP更新)下发执行开始(SOTDMS014Impl)============");
		try {
			//下发的数据
			LinkedList<TiAppUSwapDto> dataList = Cloud.getDataList();
			send(dataList);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		logger.info("==========更新客户信息（置换需求）(APP更新)下发执行结束(SOTDMS014Impl)============");
		return null;
	}
	
	/**
	 * DE消息发送
	 * @param dataList
	 * @param dealerCode
	 * @throws Exception 
	 */
	private String send(LinkedList<TiAppUSwapDto> dataList) throws Exception {
		try {
			if(dataList != null && dataList.size() > 0){
				LinkedList<TiAppUSwapVO> vos = new LinkedList<>();
				setVos(vos,dataList);
				Map<String, Serializable> body = DEUtil.assembleBody(vos);
				sendAllMsg("SOTDMS014", body);
				logger.info("SOTDMS014 更新客户信息（置换需求）(APP更新)下发发送成功======size："+dataList.size());
			}else{
				logger.info("SOTDMS014 更新客户信息（置换需求）(APP更新)信息为空========");
			}
		} catch (Throwable t) {
			logger.info("SOTDMS014 更新客户信息（置换需求）(APP更新)下发失败======size："+dataList.size());
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
	private void setVos(LinkedList<TiAppUSwapVO> vos, LinkedList<TiAppUSwapDto> dataList) throws IllegalAccessException, InvocationTargetException {
		for (int i = 0; i < dataList.size(); i++) {
			TiAppUSwapDto dto = dataList.get(i);
			TiAppUSwapVO vo = new TiAppUSwapVO();
			vo.setEntityCode(dto.getDealerCode());
			BeanUtils.copyProperties(dto, vo);
			vos.add(vo);
		}
	}

}
