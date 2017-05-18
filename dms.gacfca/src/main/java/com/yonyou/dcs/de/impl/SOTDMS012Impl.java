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

import com.infoservice.dms.cgcsl.vo.TiAppUCustomerInfoVO;
import com.yonyou.dcs.de.SOTDMS012;
import com.yonyou.dcs.gacfca.SOTDMS012Cloud;
import com.yonyou.dcs.util.DEUtil;
import com.yonyou.dms.DTO.gacfca.TiAppUCustomerInfoDto;

/**
 * 更新客户信息（客户接待信息/需求分析）(APP更新)下发
 * @author Benzc
 * @date 2017年5月16日
 */
@Service
public class SOTDMS012Impl extends BaseImpl implements SOTDMS012{

	private static final Logger logger = LoggerFactory.getLogger(SOTDMS012Impl.class);
	
	@Autowired SOTDMS012Cloud Cloud;
	
	@Override
	public String sendData() throws Exception {
		logger.info("==========更新客户信息（客户接待信息/需求分析）(APP更新)下发执行开始(SOTDMS012Impl)============");
		try {
			//下发的数据
			LinkedList<TiAppUCustomerInfoDto> dataList = Cloud.getDataList();
			send(dataList);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		logger.info("==========更新客户信息（客户接待信息/需求分析）(APP更新)下发执行结束(SOTDMS012Impl)============");
		return null;
	}
	
	/**
	 * DE消息发送
	 * @param dataList
	 * @param dealerCode
	 * @throws Exception 
	 */
	private String send(LinkedList<TiAppUCustomerInfoDto> dataList) throws Exception {
		try {
			if(dataList != null && dataList.size() > 0){
				LinkedList<TiAppUCustomerInfoVO> vos = new LinkedList<TiAppUCustomerInfoVO>();
				setVos(vos,dataList);
				Map<String, Serializable> body = DEUtil.assembleBody(vos);
				sendAllMsg("SOTDMS012", body);
				logger.info("SOTDMS012 更新客户信息（客户接待信息/需求分析）(APP更新)下发发送成功======size："+dataList.size());
			}else{
				logger.info("SOTDMS012 更新客户信息（客户接待信息/需求分析）(APP更新)信息为空========");
			}
		} catch (Throwable t) {
			logger.info("SOTDMS012 更新客户信息（客户接待信息/需求分析）(APP更新)下发失败======size："+dataList.size());
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
	private void setVos(LinkedList<TiAppUCustomerInfoVO> vos, LinkedList<TiAppUCustomerInfoDto> dataList) throws IllegalAccessException, InvocationTargetException {
		for (int i = 0; i < dataList.size(); i++) {
			TiAppUCustomerInfoDto dto = dataList.get(i);
			TiAppUCustomerInfoVO vo = new TiAppUCustomerInfoVO();
			vo.setEntityCode(dto.getDealerCode());
			BeanUtils.copyProperties(dto, vo);
			vos.add(vo);
		}
	}

}
