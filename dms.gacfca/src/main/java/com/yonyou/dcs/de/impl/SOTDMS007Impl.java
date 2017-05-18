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

import com.infoservice.dms.cgcsl.vo.TiAppNCultivateVO;
import com.yonyou.dcs.de.SOTDMS007;
import com.yonyou.dcs.gacfca.SOTDMS007Cloud;
import com.yonyou.dcs.util.DEUtil;
import com.yonyou.dms.DTO.gacfca.TiAppNCultivateDto;

/**
 * 创建客户信息（客户培育）(APP新增)下发
 * @author Benzc
 * @date 2017年5月17日
 */
@Service
public class SOTDMS007Impl extends BaseImpl implements SOTDMS007{

	private static final Logger logger = LoggerFactory.getLogger(SOTDMS007Impl.class);
	
	@Autowired SOTDMS007Cloud Cloud;
	
	@Override
	public String sendData() throws Exception {
		logger.info("==========创建客户信息（客户培育）(APP新增)下发执行开始(SOTDMS004Impl)============");
		try {
			//下发的数据
			LinkedList<TiAppNCultivateDto> dataList = Cloud.getDataList();
			send(dataList);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		logger.info("==========创建客户信息（客户培育）(APP新增)下发执行结束(SOTDMS004Impl)============");
		return null;
	}
	
	/**
	 * DE消息发送
	 * @param dataList
	 * @param dealerCode
	 * @throws Exception 
	 */
	private String send(LinkedList<TiAppNCultivateDto> dataList) throws Exception {
		try {
			if(dataList != null && dataList.size() > 0){
				LinkedList<TiAppNCultivateVO> vos = new LinkedList<>();
				setVos(vos,dataList);
				Map<String, Serializable> body = DEUtil.assembleBody(vos);
				sendAllMsg("SOTDMS007", body);
				logger.info("SOTDMS007 创建客户信息（客户培育）(APP新增)下发发送成功======size："+dataList.size());
			}else{
				logger.info("SOTDMS007 创建客户信息（客户培育）(APP新增)信息为空========");
			}
		} catch (Throwable t) {
			logger.info("SOTDMS007 创建客户信息（客户培育）(APP新增)下发失败======size："+dataList.size());
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
	private void setVos(LinkedList<TiAppNCultivateVO> vos, LinkedList<TiAppNCultivateDto> dataList) throws IllegalAccessException, InvocationTargetException {
		for (int i = 0; i < dataList.size(); i++) {
			TiAppNCultivateDto dto = dataList.get(i);
			TiAppNCultivateVO vo = new TiAppNCultivateVO();
			vo.setEntityCode(dto.getDealerCode());
			BeanUtils.copyProperties(dto, vo);
			vos.add(vo);
		}
	}

}
