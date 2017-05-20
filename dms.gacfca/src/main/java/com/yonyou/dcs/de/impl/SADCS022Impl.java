/**
 * 
 */
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

import com.infoservice.dms.cgcsl.vo.SADMS022VO;
import com.yonyou.dcs.de.SADCS022;
import com.yonyou.dcs.gacfca.SADCS022Cluod;
import com.yonyou.dcs.util.DEUtil;
import com.yonyou.dms.DTO.gacfca.SADMS022Dto;

/**
 * @author Administrator
 *
 */
public class SADCS022Impl extends BaseImpl implements SADCS022 {
	private static final Logger logger = LoggerFactory.getLogger(SADCS022Impl.class);
	
	
	@Autowired
	SADCS022Cluod  Cloud;
	@Override
	public String sendData(String param) throws Exception {
		logger.info("================贴息利率信息下发开始（SADCS022）====================");
		try {
			send(Cloud.getDataList(param));
			logger.info("================贴息利率信息下发成功（SADCS022）====================");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		logger.info("================贴息利率信息下发结束（SADCS022）====================");
		return null;
	}
	
	/**
	 * DE消息发送
	 * @param dataList
	 * @param dealerCode
	 * @throws Exception 
	 */
	private void send(List<SADMS022Dto> dataList) throws Exception {
		try {

			if(null!=dataList && dataList.size()>0){
				for(int i=0;i<dataList.size();i++){
					List<SADMS022VO> vos = new ArrayList<>();
					setVos(vos,dataList);
					Map<String, Serializable> body = DEUtil.assembleBody(vos);
					sendAllMsg("SADMS022", body);
				}
				
			}else{
				logger.info("SADCS022发送数据为空=====");
			}
		} catch (Throwable t) {	
			logger.error(t.getMessage(), t);
		} finally {
		}
	}
	
	/**
	 * 数据转换
	 * @param vos
	 * @param dataList
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	private void setVos(List<SADMS022VO> vos, List<SADMS022Dto> dataList) throws IllegalAccessException, InvocationTargetException {
		for (int i = 0; i < dataList.size(); i++) {
			SADMS022Dto dto = dataList.get(i);
			SADMS022VO vo = new SADMS022VO();
			vo.setEntityCode(dto.getDealerCode());
			BeanUtils.copyProperties(dto, vo);
			vos.add(vo);
		}
	}

}
