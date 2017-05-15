package com.yonyou.dcs.de.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.de.SADCS075;
import com.yonyou.dcs.gacfca.SADCS075Cloud;
import com.yonyou.dcs.util.DEUtil;
import com.yonyou.dms.DTO.gacfca.VoucherDTO;
@Service
public class SADCS075Impl  extends BaseImpl  implements  SADCS075 {
	private static final Logger logger = LoggerFactory.getLogger(SADCS075Impl.class);
	
	@Autowired
	SADCS075Cloud cloud;
	
	@Override
	public String sendData(String actId){
		try {
			//下发的数据
			List<VoucherDTO> dataList=cloud.getSendData(actId);
			send(dataList);
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	/**
	 * DE消息发送
	 * @param dataList
	 * @param dealerCode
	 * @throws Exception 
	 */
	private String send(List<VoucherDTO> dataList) throws Exception {
		try {
			if(null!=dataList && dataList.size()>0){
				Map<String, Serializable> body = DEUtil.assembleBody(dataList);
				sendAllMsg("SEDMS075", body);
				logger.info("SADCS075 保险营销活动发送成功======size："+dataList.size());
			}else{
				logger.info("SADCS075  发送数据为空=====");
			}
		} catch (Throwable t) {	
			logger.info("SADCS075 保险营销活动下发失败======size："+dataList.size());
			logger.error(t.getMessage(), t);
		} 
		return null;
	}
}
