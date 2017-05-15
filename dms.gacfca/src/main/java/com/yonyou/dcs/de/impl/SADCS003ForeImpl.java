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

import com.infoservice.dms.cgcsl.vo.SADMS003ForeVO;
import com.yonyou.dcs.de.SADCS003Fore;
import com.yonyou.dcs.gacfca.SADCS003ForeCloud;
import com.yonyou.dcs.util.DEUtil;
import com.yonyou.dms.DTO.gacfca.SADMS003ForeDTO;

/**
 * DCC建档客户信息下发
 * @author Benzc
 * @date 2017年5月15日
 */
@Service
public class SADCS003ForeImpl extends BaseImpl implements SADCS003Fore{
    
	private static final Logger logger = LoggerFactory.getLogger(SADCS003ForeImpl.class);
	
	@Autowired SADCS003ForeCloud Cloud;
	
	@Override
	public String sendData() throws Exception {
		logger.info("==========DCC建档客户信息下发执行开始(SADCS003ForeImpl)===========");
		try {
			//下发的数据
			List<SADMS003ForeDTO> dataList = Cloud.getDataList();
			send(dataList);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		logger.info("==========DCC建档客户信息下发执行结束(SADCS003Impl)============");
		return null;
	}
	
	/**
	 * DE消息发送
	 * @param dataList
	 * @param dealerCode
	 * @throws Exception 
	 */
	private String send(List<SADMS003ForeDTO> dataList) throws Exception {
		try {
			if(dataList != null && dataList.size() > 0){
				List<SADMS003ForeVO> vos = new ArrayList<>();
				setVos(vos,dataList);
				Map<String, Serializable> body = DEUtil.assembleBody(vos);
				sendAllMsg("SADMS003Fore", body);
				logger.info("SADMS003Fore DCC建档客户信息下发发送成功======size："+dataList.size());
			}else{
				logger.info("SADCS003Fore DCC建档客户信息为空========");
			}
		} catch (Throwable t) {
			logger.info("SADCS003Fore DCC建档客户信息下发失败======size："+dataList.size());
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
	private void setVos(List<SADMS003ForeVO> vos, List<SADMS003ForeDTO> dataList) throws IllegalAccessException, InvocationTargetException {
		for (int i = 0; i < dataList.size(); i++) {
			SADMS003ForeDTO dto = dataList.get(i);
			SADMS003ForeVO vo = new SADMS003ForeVO();
			BeanUtils.copyProperties(dto, vo);
			vos.add(vo);
		}
	}

}
