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

import com.infoservice.dms.cgcsl.vo.OwnerVehicleVO;
import com.yonyou.dcs.de.SADMS065;
import com.yonyou.dcs.gacfca.SADMS065Cloud;
import com.yonyou.dcs.util.DEUtil;
import com.yonyou.dms.DTO.gacfca.OwnerVehicleDto;

/**
 * 直销车售后资料生成下发
 * @author Benzc
 * @date 2017年5月17日
 */
@Service
public class SADMS065Impl extends BaseImpl implements SADMS065{

	private static final Logger logger = LoggerFactory.getLogger(SADMS065Impl.class);
	
	@Autowired SADMS065Cloud Cloud;
	
	@Override
	public String sendData(String vinList) throws Exception {
		logger.info("==========直销车售后资料生成下发执行开始(SADMS065Impl)============");
		try {
			//下发的数据
			LinkedList<OwnerVehicleDto> dataList = Cloud.getDataList(vinList);
			send(dataList);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		logger.info("==========直销车售后资料生成下发执行结束(SADMS065Impl)============");
		return null;
	}
	
	/**
	 * DE消息发送
	 * @param dataList
	 * @param dealerCode
	 * @throws Exception 
	 */
	private String send(LinkedList<OwnerVehicleDto> dataList) throws Exception {
		try {
			if(dataList != null && dataList.size() > 0){
				LinkedList<OwnerVehicleVO> vos = new LinkedList<OwnerVehicleVO>();
				setVos(vos,dataList);
				Map<String, Serializable> body = DEUtil.assembleBody(vos);
				sendAllMsg("SADMS065", body);
				logger.info("SADMS065 直销车售后资料生成下发发送成功======size："+dataList.size());
			}else{
				logger.info("SADMS065 直销车售后资料生成信息为空========");
			}
		} catch (Throwable t) {
			logger.info("SADMS065 直销车售后资料生成下发失败======size："+dataList.size());
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
	private void setVos(LinkedList<OwnerVehicleVO> vos, LinkedList<OwnerVehicleDto> dataList) throws IllegalAccessException, InvocationTargetException {
		for (int i = 0; i < dataList.size(); i++) {
			OwnerVehicleDto dto = dataList.get(i);
			OwnerVehicleVO vo = new OwnerVehicleVO();
			vo.setEntityCode(dto.getDealerCode());
			BeanUtils.copyProperties(dto, vo);
			vos.add(vo);
		}
	}

}
