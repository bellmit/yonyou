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

import com.infoservice.dms.cgcsl.vo.SEDMS054VO;
import com.yonyou.dcs.de.SEDCS054;
import com.yonyou.dcs.gacfca.SEDCS054Cloud;
import com.yonyou.dcs.util.DEUtil;
import com.yonyou.dms.DTO.gacfca.SEDMS054DTO;
/**
 * 克莱斯勒明检和神秘客下发
 * @author Benzc
 * @date 2017年5月16日
 */
@Service
public class SEDCS054Impl extends BaseImpl implements SEDCS054{
    
	private static final Logger logger = LoggerFactory.getLogger(SEDCS054Impl.class);
	
	@Autowired SEDCS054Cloud Cloud;
	
	@Override
	public String sendData() throws Exception {
		logger.info("==========克莱斯勒明检和神秘客下发开始(SEDCS054Impl)============");
		try {
			//下发的数据
			List<SEDMS054DTO> dataList = Cloud.getDataList();
			send(dataList);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		logger.info("==========克莱斯勒明检和神秘客下发结束(SEDCS054Impl)============");		
		return null;
	}
	
	/**
	 * DE消息发送
	 * @param dataList
	 * @param dealerCode
	 * @throws Exception 
	 */
	private String send(List<SEDMS054DTO> dataList) throws Exception {
		try {
			if(dataList != null && dataList.size() > 0){
				List<SEDMS054VO> vos = new ArrayList<>();
				setVos(vos,dataList);
				Map<String, Serializable> body = DEUtil.assembleBody(vos);
				sendAllMsg("SEDMS054", body);
				logger.info("SEDCS054 克莱斯勒明检和神秘客下发发送成功======size："+dataList.size());
			}else{
				logger.info("SEDCS054 克莱斯勒明检和神秘客信息为空========");
			}
		} catch (Throwable t) {
			logger.info("SEDCS054 克莱斯勒明检和神秘客下发失败======size："+dataList.size());
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
	private void setVos(List<SEDMS054VO> vos, List<SEDMS054DTO> dataList) throws IllegalAccessException, InvocationTargetException {
		for (int i = 0; i < dataList.size(); i++) {
			SEDMS054DTO dto = dataList.get(i);
			SEDMS054VO vo = new SEDMS054VO();
			vo.setEntityCode(dto.getDealerCode());
			BeanUtils.copyProperties(dto, vo);
			vos.add(vo);
		}
	}

}
