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

import com.infoservice.dms.cgcsl.vo.TiAppNSwapVO;
import com.yonyou.dcs.de.SOTDMS005;
import com.yonyou.dcs.gacfca.SOTDMS005Cloud;
import com.yonyou.dcs.util.DEUtil;
import com.yonyou.dms.DTO.gacfca.TiAppNSwapDto;

/**
 *  创建客户信息（置换需求）(APP新增)下发
 * @author Benzc
 * @date 2017年5月17日
 */
@Service
public class SOTDMS005Impl extends BaseImpl implements SOTDMS005{

	private static final Logger logger = LoggerFactory.getLogger(SOTDMS005Impl.class);
	
	@Autowired SOTDMS005Cloud Cloud;
	
	@Override
	public String sendData() throws Exception {
		logger.info("==========创建客户信息（置换需求）(APP新增)下发执行开始(SOTDMS005Impl)============");
		try {
			//下发的数据
			LinkedList<TiAppNSwapDto> dataList = Cloud.getDataList();
			send(dataList);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		logger.info("==========创建客户信息（置换需求）(APP新增)下发执行结束(SOTDMS005Impl)============");
		return null;
	}
	
	/**
	 * DE消息发送
	 * @param dataList
	 * @param dealerCode
	 * @throws Exception 
	 */
	private String send(LinkedList<TiAppNSwapDto> dataList) throws Exception {
		try {
			if(dataList != null && dataList.size() > 0){
				LinkedList<TiAppNSwapVO> vos = new LinkedList<>();
				setVos(vos,dataList);
				Map<String, Serializable> body = DEUtil.assembleBody(vos);
				sendAllMsg("SOTDMS005", body);
				logger.info("SOTDMS005 创建客户信息（置换需求）(APP新增)下发发送成功======size："+dataList.size());
			}else{
				logger.info("SOTDMS005 创建客户信息（置换需求）(APP新增)信息为空========");
			}
		} catch (Throwable t) {
			logger.info("SOTDMS005 创建客户信息（置换需求）(APP新增)下发失败======size："+dataList.size());
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
	private void setVos(LinkedList<TiAppNSwapVO> vos, LinkedList<TiAppNSwapDto> dataList) throws IllegalAccessException, InvocationTargetException {
		for (int i = 0; i < dataList.size(); i++) {
			TiAppNSwapDto dto = dataList.get(i);
			TiAppNSwapVO vo = new TiAppNSwapVO();
			vo.setEntityCode(dto.getDealerCode());
			BeanUtils.copyProperties(dto, vo);
			vos.add(vo);
		}
	}

}
