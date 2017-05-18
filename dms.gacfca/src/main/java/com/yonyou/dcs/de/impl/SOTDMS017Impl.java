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

import com.infoservice.dms.cgcsl.vo.TiAppUSalesQuotasVO;
import com.yonyou.dcs.de.SOTDMS017;
import com.yonyou.dcs.gacfca.SOTDMS017Cloud;
import com.yonyou.dcs.util.DEUtil;
import com.yonyou.dms.DTO.gacfca.TiAppUSalesQuotasDto;

/**
 * 销售人员分配信息下发(App更新)
 * @author Benzc
 * @date 2017年5月17日
 */
@Service
public class SOTDMS017Impl extends BaseImpl implements SOTDMS017{

	private static final Logger logger = LoggerFactory.getLogger(SOTDMS017Impl.class);
	
	@Autowired SOTDMS017Cloud Cloud;
	
	@Override
	public String sendData() throws Exception {
		logger.info("==========销售人员分配信息下发(App更新)执行开始(SOTDMS017Impl)============");
		try {
			//下发的数据
			LinkedList<TiAppUSalesQuotasDto> dataList = Cloud.getDataList();
			send(dataList);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		logger.info("==========销售人员分配信息下发(App更新)执行结束(SOTDMS017Impl)============");
		return null;
	}
	
	/**
	 * DE消息发送
	 * @param dataList
	 * @param dealerCode
	 * @throws Exception 
	 */
	private String send(LinkedList<TiAppUSalesQuotasDto> dataList) throws Exception {
		try {
			if(dataList != null && dataList.size() > 0){
				LinkedList<TiAppUSalesQuotasVO> vos = new LinkedList<>();
				setVos(vos,dataList);
				Map<String, Serializable> body = DEUtil.assembleBody(vos);
				sendAllMsg("SOTDMS017", body);
				logger.info("SOTDMS017 销售人员分配信息下发(App更新)发送成功======size："+dataList.size());
			}else{
				logger.info("SOTDMS017 销售人员分配信息(App更新)为空========");
			}
		} catch (Throwable t) {
			logger.info("SOTDMS017 销售人员分配信息下发(App更新)失败======size："+dataList.size());
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
	private void setVos(LinkedList<TiAppUSalesQuotasVO> vos, LinkedList<TiAppUSalesQuotasDto> dataList) throws IllegalAccessException, InvocationTargetException {
		for (int i = 0; i < dataList.size(); i++) {
			TiAppUSalesQuotasDto dto = dataList.get(i);
			TiAppUSalesQuotasVO vo = new TiAppUSalesQuotasVO();
			vo.setEntityCode(dto.getDealerCode());
			BeanUtils.copyProperties(dto, vo);
			vos.add(vo);
		}
	}

}
