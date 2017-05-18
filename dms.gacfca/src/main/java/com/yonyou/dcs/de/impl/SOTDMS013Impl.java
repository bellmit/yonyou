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

import com.infoservice.dms.cgcsl.vo.TiAppUTestDriveVO;
import com.yonyou.dcs.de.SOTDMS013;
import com.yonyou.dcs.gacfca.SOTDMS013Cloud;
import com.yonyou.dcs.util.DEUtil;
import com.yonyou.dms.DTO.gacfca.TiAppUTestDriveDto;

/**
 * 更新客户信息（试乘试驾）(APP更新)下发
 * @author Benzc
 * @date 2017年5月17日
 */
@Service
public class SOTDMS013Impl extends BaseImpl implements SOTDMS013{

	private static final Logger logger = LoggerFactory.getLogger(SOTDMS013Impl.class);
	
	@Autowired SOTDMS013Cloud Cloud;
	
	@Override
	public String sendData() throws Exception {
		logger.info("==========更新客户信息（试乘试驾）(APP更新)下发执行开始(SOTDMS013Impl)============");
		try {
			//下发的数据
			LinkedList<TiAppUTestDriveDto> dataList = Cloud.getDataList();
			send(dataList);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		logger.info("==========更新客户信息（试乘试驾）(APP更新)下发执行结束(SOTDMS013Impl)============");
		return null;
	}
	
	/**
	 * DE消息发送
	 * @param dataList
	 * @param dealerCode
	 * @throws Exception 
	 */
	private String send(LinkedList<TiAppUTestDriveDto> dataList) throws Exception {
		try {
			if(dataList != null && dataList.size() > 0){
				LinkedList<TiAppUTestDriveVO> vos = new LinkedList<TiAppUTestDriveVO>();
				setVos(vos,dataList);
				Map<String, Serializable> body = DEUtil.assembleBody(vos);
				sendAllMsg("SOTDMS013", body);
				logger.info("SOTDMS013 更新客户信息（试乘试驾）(APP更新)下发发送成功======size："+dataList.size());
			}else{
				logger.info("SOTDMS013 更新客户信息（试乘试驾）(APP更新)信息为空========");
			}
		} catch (Throwable t) {
			logger.info("SOTDMS013更新客户信息（试乘试驾）(APP更新)下发失败======size："+dataList.size());
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
	private void setVos(LinkedList<TiAppUTestDriveVO> vos, LinkedList<TiAppUTestDriveDto> dataList) throws IllegalAccessException, InvocationTargetException {
		for (int i = 0; i < dataList.size(); i++) {
			TiAppUTestDriveDto dto = dataList.get(i);
			TiAppUTestDriveVO vo = new TiAppUTestDriveVO();
			vo.setEntityCode(dto.getDealerCode());
			BeanUtils.copyProperties(dto, vo);
			vos.add(vo);
		}
	}

}
