package com.yonyou.dcs.gacfca;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.dao.SADCS072Dao;
import com.yonyou.dms.DTO.gacfca.VehicleCustomerDTO;
@Service
public class SADCS072CloudImpl extends BaseCloudImpl implements SADCS072Cloud {
	
	private static final Logger logger = LoggerFactory.getLogger(SADCS072CloudImpl.class);
	
	@Autowired
	SADCS072Dao dao;

	/**
	 * 下发数据方法
	 * @param list
	 * @param dealerCode
	 */
	@Override
	public String sendData(String vin,String dealerCode){
		try {
			logger.info("==============SADCS072Cloud  车主资料下发开始================");
			//下发的经销商
			List<Map> dealerList= dao.getSendDealer(vin,dealerCode);
			//下发数据
			List<VehicleCustomerDTO> dtolist=dao.getDataList(vin);
			if(null!=dtolist && dtolist.size()>0){
				for(int i=0;i<dealerList.size();i++){
					//下发操作
//					int flag = (dtolist,dealerList.get(i).get("DMS_CODE"));
//					if(flag==1){
//						logger.info("====================SADCS072Cloud  车主资料下发成功========================");
//					}else{
//						logger.info("================SADCS072Cloud  车主资料下发失败====================");
//					}
				}

			}else{
				logger.info("====SADCS072Cloud  车主资料下发结束====,无数据！ ");
			}
		} catch (Exception e) {
			logger.info("================SADCS072Cloud  车主资料下发异常====================");
		}
		return null;
	}

}
