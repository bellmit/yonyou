package com.yonyou.dcs.de.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.dao.RepairOrderResultStatusDao;
import com.yonyou.dcs.de.SADCS015_1;
import com.yonyou.dcs.gacfca.SADCS015_1Cloud;
import com.yonyou.dcs.util.DEUtil;
import com.yonyou.dms.DTO.gacfca.PoCusWholeRepayClryslerDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
@Service
public class SADCS015_1Impl  extends BaseImpl  implements  SADCS015_1 {
	private static final Logger logger = LoggerFactory.getLogger(SADCS015_1Impl.class);
	
	@Autowired
	SADCS015_1Cloud cloud;
	
	@Autowired
	RepairOrderResultStatusDao dao ;
	
	@Override
	public String sendData(String wsNo, String dealerCode){
		try {
			List<PoCusWholeRepayClryslerDto> dataListlist=cloud.getDataList(wsNo, dealerCode);
			
			send(dataListlist,dealerCode);
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
	private String send(List<PoCusWholeRepayClryslerDto> dataList, String dealerCode) throws Exception {
		try {
			if(null!=dataList && dataList.size()>0){
				Map<String, Serializable> body = DEUtil.assembleBody(dataList);
				String entityCode = CommonUtils.checkNull(dao.getDmsDealerCode(dealerCode).get("DMS_CODE"));
				if(!"".equals(entityCode)){
					sendAMsg("OSD0402", entityCode, body);
					logger.info("SADCS015_1大客户报备返利审批数据下发发送成功=====entityCode"+entityCode+"===size："+dataList.size());
				}else{
					logger.info("SADCS015_1大客户报备返利审批数据下发下发失败=====entityCode"+entityCode+"===size："+dataList.size());
					throw new ServiceBizException("经销商没有维护对应关系");
				}
			}else{
				logger.info("SADCS015_1发送数据为空=====");
			}
		} catch (Throwable t) {	
			logger.error(t.getMessage(), t);
		} finally {
		}
		return null;
	}
}
