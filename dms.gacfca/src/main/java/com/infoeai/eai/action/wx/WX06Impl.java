package com.infoeai.eai.action.wx;

import java.util.Date;

import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.infoeai.eai.action.BaseService;
import com.infoeai.eai.wsServer.wx.service4wx.CustomerFeedbackPO;
import com.yonyou.dms.common.domains.PO.customer.TiWxCustomerFeedbackPO;
@Service
public class WX06Impl extends BaseService implements WX06 {

	private static Logger logger = LoggerFactory.getLogger(WX06Impl.class);
	
	@Override
	public String execute(Object request) throws Exception {
		//对通过的数据解析
		String returnResult = "0";   //返回1表示成功,0表示失败
		try {
			logger.info("====WX06 is begin====");
			beginDbService();
			
			CustomerFeedbackPO[] wx_rq = (CustomerFeedbackPO[])request;
			logger.info("wx_rq....SIZE : "+wx_rq.length);
			for(int i=0;i<wx_rq.length;i++){
				//创建一个新的对象
				TiWxCustomerFeedbackPO customerFeedback = new TiWxCustomerFeedbackPO();
				customerFeedback.setString("VIN",wx_rq[i].getVin());
				customerFeedback.setString("CUSTOMER_NO",wx_rq[i].getCustomerNo());
				customerFeedback.setString("OWNER_NAME",wx_rq[i].getOwnerName());
				customerFeedback.setString("MOBILE",wx_rq[i].getMobile());
				customerFeedback.setInteger("GENDER",wx_rq[i].getGender());//10061001 男	10061002 女
				customerFeedback.setString("ADDRESS",wx_rq[i].getAddress());
				customerFeedback.setString("E_MAIL",wx_rq[i].getEmail());
				customerFeedback.setString("ZIP_CODE",wx_rq[i].getZipcode());
				customerFeedback.setString("DEALER_CODE",wx_rq[i].getDealerCode());
				
				
				customerFeedback.setString("IS_SCAN","0");
				customerFeedback.setLong("CREATE_BY",new Long(80000001));
				customerFeedback.setTimestamp("CREATE_DATE",new Date());
				
				customerFeedback.saveIt();
				returnResult = "1";
				
				//手动提交事务
				if(i%100==0){
					dbService.endTxn(true);
					Base.detach();
					dbService.clean();
	        		/******************************结束并清空事物*********************/
	                /******************************开启事物*********************/
					beginDbService();
				}
			}
			dbService.endTxn(true);
    		/******************************结束事物*********************/
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
			dbService.endTxn(false);
		}finally{
			logger.info("====WX06 is finish====");
			dbService.clean();
		}
		//调用下端：下发DCC潜在客户信息
//		SADCS003 osc = new SADCS003();
//		osc.execute();
		return returnResult;
	}
	
}
