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

import com.infoservice.dms.cgcsl.vo.TiAppNCustomerInfoVO;
import com.yonyou.dcs.de.SOTDMS003;
import com.yonyou.dcs.gacfca.SOTDMS003Cloud;
import com.yonyou.dcs.util.DEUtil;
import com.yonyou.dms.DTO.gacfca.TiAppNCustomerInfoDto;

/**
 * 客户接待信息/需求分析(APP新增)下发
 * @author Benzc
 * @date 2017年5月16日
 */
@Service
public class SOTDMS003Impl extends BaseImpl implements SOTDMS003{

	private static final Logger logger = LoggerFactory.getLogger(SOTDMS003Impl.class);
	
	@Autowired SOTDMS003Cloud Cloud;
	
	@Override
	public String sendData() throws Exception {
		logger.info("==========客户接待信息/需求分析(APP新增)下发执行开始(SOTDMS003Impl)============");
		try {
			//下发的数据
			LinkedList<TiAppNCustomerInfoDto> dataList = Cloud.getDataList();
			send(dataList);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		logger.info("==========客户接待信息/需求分析(APP新增)下发执行结束(SOTDMS003Impl)============");
		return null;
	}
	
	/**
	 * DE消息发送
	 * @param dataList
	 * @param dealerCode
	 * @throws Exception 
	 */
	private String send(LinkedList<TiAppNCustomerInfoDto> dataList) throws Exception {
		try {
			if(dataList != null && dataList.size() > 0){
				LinkedList<TiAppNCustomerInfoVO> vos = new LinkedList<>();
				setVos(vos,dataList);
				Map<String, Serializable> body = DEUtil.assembleBody(vos);
				sendAllMsg("SOTDMS003", body);
				logger.info("SOTDMS003 客户接待信息/需求分析(APP新增)下发发送成功======size："+dataList.size());
			}else{
				logger.info("SOTDMS003 客户接待信息/需求分析(APP新增)信息为空========");
			}
		} catch (Throwable t) {
			logger.info("SOTDMS003 客户接待信息/需求分析(APP新增)下发失败======size："+dataList.size());
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
	private void setVos(LinkedList<TiAppNCustomerInfoVO> vos, LinkedList<TiAppNCustomerInfoDto> dataList) throws IllegalAccessException, InvocationTargetException {
		for (int i = 0; i < dataList.size(); i++) {
			TiAppNCustomerInfoDto dto = dataList.get(i);
			TiAppNCustomerInfoVO vo = new TiAppNCustomerInfoVO();
			vo.setEntityCode(dto.getDealerCode());
			BeanUtils.copyProperties(dto, vo);
			vos.add(vo);
		}
	}

}
