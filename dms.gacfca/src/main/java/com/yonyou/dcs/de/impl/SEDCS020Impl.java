package com.yonyou.dcs.de.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.dao.SEDCS020Dao;
import com.yonyou.dcs.de.SEDCS020;
import com.yonyou.dcs.util.DEUtil;
import com.yonyou.dms.DTO.gacfca.RecallServiceClearDTO;
import com.yonyou.dms.function.utils.common.CommonUtils;
@Service
public class SEDCS020Impl  extends BaseImpl  implements  SEDCS020 {
	private static final Logger logger = LoggerFactory.getLogger(SEDCS020Impl.class);
	
	@Autowired
	SEDCS020Dao dao;

	@Override
	public String doSend(String recallId){
		String re = "1";
		try {
			logger.info("====SEDCS020      召回活动取消下发开始====");
			//下发数据查询
			List<RecallServiceClearDTO> datalist = dao.queryAllInfo(recallId);
			//要发数据的经销商列表
			List<Map<String, Object>> listdealer = dao.queryDealer(recallId);
			List<String> dealerCodes = new ArrayList<String>();
			
			for (Map<String, Object> dealer : listdealer) {
				if (dealer!=null) {
					String dealerCode="";
					try {
						dealerCode=CommonUtils.checkNull(dealer.get("DEALER_CODE"));
						if (!"".equals(dealerCode)) {
							//可下发的经销商列表
							dealerCodes.add(dealerCode);
						}
					} catch (Exception e) {
						logger.error("Cann't send to " + dealerCode, e);
					}
				}
			}
			
			if (null == dealerCodes || dealerCodes.size() == 0) {
				logger.info("====SEDCS020   召回活动取消下发结束====,无数据");
			}else{
				//获取下架经销商
				for(int i =0;i<dealerCodes.size();i++){
					List <String> dmsCodes = new ArrayList<String>();
					Map<String, Object> dmsDealer = dao.getDmsDealerCode(dealerCodes.get(i));
					if (null==dmsDealer || dmsDealer.size() == 0) {
						logger.info("DMS端不存在编码"+dealerCodes.get(i)+"为 的经销商记录");
						continue;
					} else {
						dmsCodes.add(CommonUtils.checkNull(dmsDealer.get("DMS_CODE")));
					}
					
					send(datalist, dmsCodes);
					
				}
				
				//更新下发状态,下发时间
				dao.updateSend(recallId);
				
				logger.info("====SEDCS020 召回活动取消下发结束====,下发了(" + datalist.size() + ")条数据");
			}
			
		}catch (Exception e) {
			re="2";
			logger.error(e.getMessage(), e);
		}
		return re;
	}
	/**
	 * DE消息发送
	 * @param array
	 * @throws Exception 
	 */
	private String send(List<RecallServiceClearDTO> dataList,List<String> dmsCodes) throws Exception {
		try {
			if(null!=dataList && dataList.size()>0){
				Map<String, Serializable> body = DEUtil.assembleBody(dataList);
					
				sendMsg("SEDMS020",dmsCodes, body);
				logger.info("SEDCS020  召回活动取消发送成功======size："+dataList.size());
			}else{
				logger.info("SEDCS020  召回活动取消发送数据为空=====");
			}
		} catch (Throwable t) {	
			logger.info("SEDCS020 召回活动取消下发失败======size："+dataList.size());
			logger.error(t.getMessage(), t);
		} 
		return null;
	}
}