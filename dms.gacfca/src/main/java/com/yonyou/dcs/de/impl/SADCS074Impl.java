package com.yonyou.dcs.de.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.dao.SADCS074Dao;
import com.yonyou.dcs.de.SADCS074;
import com.yonyou.dcs.util.DEUtil;
import com.yonyou.dms.DTO.gacfca.SADCS074DTO;
import com.yonyou.dms.function.exception.ServiceBizException;
@Service
public class SADCS074Impl  extends BaseImpl  implements  SADCS074 {
	private static final Logger logger = LoggerFactory.getLogger(SADCS074Impl.class);
	
	@Autowired
	SADCS074Dao dao;
	
	@Override
	public String sendData(String vin,String dealerCode){
		try {
			List<String> dmsCodes = new ArrayList<String>();
			//下发的经销商
			List<String> dealerList= dao.queryIDealerCode(vin);
			//下发数据
			List<SADCS074DTO> dataList=dao.queryInvoiceDateSend(vin,dealerCode);
			for (int i=0;i<dealerList.size();i++) {
				// 可下发的经销商列表
				dmsCodes.add(dealerList.get(i));
			}
			send(dataList,dmsCodes);
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
	private String send(List<SADCS074DTO> dataList, List<String> dmsCodes) throws Exception {
		try {
			if(null!=dataList && dataList.size()>0){
				Map<String, Serializable> body = DEUtil.assembleBody(dataList);
				if(!"".equals(dmsCodes)){
					sendMsg("SEDMS074", dmsCodes, body);
					logger.info("SADCS074  车主资料发送成功======size："+dataList.size());
				}else{
					logger.info("SADCS074  车主资料下发失败======size："+dataList.size());
					throw new ServiceBizException("经销商没有维护对应关系");
				}
			}else{
				logger.info("SADCS074  发送数据为空=====");
			}
		} catch (Throwable t) {	
			logger.error(t.getMessage(), t);
		} 
		return null;
	}
}
