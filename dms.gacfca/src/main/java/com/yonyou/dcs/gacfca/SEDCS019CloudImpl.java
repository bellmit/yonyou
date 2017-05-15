package com.yonyou.dcs.gacfca;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.dao.SEDCS019Dao;
import com.yonyou.dms.DTO.gacfca.RecallServiceDTO;
import com.yonyou.dms.function.utils.common.CommonUtils;
@Service
public class SEDCS019CloudImpl extends BaseCloudImpl implements SEDCS019Cloud {
	
	private static final Logger logger = LoggerFactory.getLogger(SEDCS019CloudImpl.class);
	
	@Autowired
	SEDCS019Dao dao;
	
	@Override
	public String doSend(String recallId){
		String re = "1";
		try {
			logger.info("====SEDCS019Cloud   召回活动下发开始====");
			//下发数据查询
			List<RecallServiceDTO> datalist = dao.queryAllInfo(recallId);
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
				logger.info("====SEDCS019Cloud   召回活动下发结束====,无数据");
			}else{
				//获取下架经销商
				for(int i =0;i<dealerCodes.size();i++){
					List <String> dealerList = new ArrayList<String>();
					Map<String, Object> dmsDealer = dao.getDmsDealerCode(dealerCodes.get(i));
					if (null==dmsDealer || dmsDealer.size() == 0) {
						logger.info("DMS端不存在编码"+dealerCodes.get(i)+"为 的经销商记录");
						continue;
					} else {
						dealerList.add(CommonUtils.checkNull(dmsDealer.get("DMS_CODE")));
					}
					
					send(datalist, dealerList);
					
				}
				
				//更新下发状态,下发时间
				dao.updateSend(recallId);
				
				logger.info("====SEDCS019Cloud  召回活动下发结束====,下发了(" + datalist.size() + ")条数据");
			}
		}catch (Exception e) {
			re="2";
			logger.error(e.getMessage(), e);
		}
		return re;
	}
	/**
	 * 数据发送
	 */
	public void send(List<RecallServiceDTO> datalist,List<String> dealerList){
		if(null!=datalist && datalist.size()>0&&null!=dealerList && dealerList.size()>0){
			for(int i=0;i<dealerList.size();i++){
				//下发操作
//				int flag = (datalist,dealerList.get(i));
//				if(flag==1){
//					logger.info("====================SEDCS019Cloud  召回活动下发成功========================");
//				}else{
//					logger.info("================SEDCS019Cloud  召回活动下发失败====================");
//				}
			}

		}else{
			logger.info("====SEDCS019Cloud  召回活动下发结束====,无数据！ ");
		}
	}
}
