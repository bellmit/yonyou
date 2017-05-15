package com.yonyou.dcs.gacfca;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.dao.SADCS074Dao;
import com.yonyou.dms.DTO.gacfca.SADCS074DTO;
@Service
public class SADCS074CloudImpl extends BaseCloudImpl implements SADCS074Cloud {
	
	private static final Logger logger = LoggerFactory.getLogger(SADCS074CloudImpl.class);
	
	@Autowired
	SADCS074Dao dao;

	/**
	 * 下发数据方法
	 * @param list
	 * @param dealerCode
	 */
	@Override
	public String sendData(String vin,String invoiceDae){
		try {
			logger.info("==============SADCS074Cloud  开票日期下发开始================");
			//下发的经销商
			List<String> dealerList= dao.queryIDealerCode(vin);
			//下发数据
			List<SADCS074DTO> dtolist=dao.queryInvoiceDateSend(vin,invoiceDae);
			if(null!=dtolist && dtolist.size()>0){
				for(int i=0;i<dealerList.size();i++){
					//下发操作
//					int flag = (dtolist,dealerList.get(i));
//					if(flag==1){
//						logger.info("====================SADCS074Cloud  开票日期下发成功========================");
//					}else{
//						logger.info("================SADCS074Cloud  开票日期下发失败====================");
//					}
				}

			}else{
				logger.info("====SADCS074Cloud  开票日期下发结束====,无数据！ ");
			}
		} catch (Exception e) {
			logger.info("================SADCS074Cloud  开票日期下发异常====================");
		}
		return null;
	}

}
