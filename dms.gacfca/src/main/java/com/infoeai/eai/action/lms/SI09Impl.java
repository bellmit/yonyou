package com.infoeai.eai.action.lms;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoeai.eai.action.BaseService;
import com.infoeai.eai.wsClient.lms.dmswebservice.DCC_DMSDealer;
import com.infoeai.eai.wsClient.lms.dmswebservice.DMSWebservice;
import com.infoeai.eai.wsClient.lms.dmswebservice.DMSWebserviceLocator;
import com.infoeai.eai.wsClient.lms.dmswebservice.DMSWebserviceSoapStub;
import com.yonyou.dcs.dao.SI10Dao;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.manage.domains.PO.dealerManager.TiDealerInfoPO;

@Service
public class SI09Impl extends BaseService implements SI09 {

	private static Logger logger = LoggerFactory.getLogger(SI09Impl.class);
	@Autowired
	SI10Dao dao;
	
	@Override
	@SuppressWarnings("unused")
	public String execute() throws Exception {
		
		try {
			logger.info("====SI09 is begin====");
			
			DMSWebserviceSoapStub stub = new DMSWebserviceSoapStub();
			DMSWebservice lmsService = new DMSWebserviceLocator();
			stub = (DMSWebserviceSoapStub)lmsService.getDMSWebserviceSoap();
			//开启事务
			dbService();
			logger.info("----------------调用对方服务地址："+lmsService.getDMSWebserviceSoapAddress()+"----------------");
	        //查找结果集
			List<TiDealerInfoPO> resultList = new ArrayList<TiDealerInfoPO>();
			resultList = dao.getDealersInfo();
			
		    //由于对方方法只能接受单个对象传递，只能用循环
			for(int i=0;i<resultList.size();i++){
				TiDealerInfoPO tempDealer = resultList.get(i);
				//接口赋值
				DCC_DMSDealer dmsDealer = new DCC_DMSDealer();
				
				if(tempDealer.getInteger("Dealer_Code")==0){
					tempDealer.setString("Is_Scan", "11");
		    		updateIsScan(tempDealer);
					continue;
				}
				
				if (StringUtils.isNullOrEmpty(tempDealer.getString("Dealerab_Cn"))) {
					tempDealer.setString("Is_Scan", "12");// 12 DealerabCn为空
		    		updateIsScan(tempDealer);
					continue;		
				}
				if (StringUtils.isNullOrEmpty(tempDealer.getString("Dealerab_EN"))) {
					tempDealer.setString("Is_Scan", "13");// 13 DealerabEn为空
					
		    		updateIsScan(tempDealer);
					continue;		
				}
				
				if(tempDealer.getInteger("CITY_ID")==0){
					tempDealer.setString("Is_Scan", "12");// 14 CityId为空
					updateIsScan(tempDealer);
					continue;
				}
				if(tempDealer.getInteger("Really_City_Id")==0){
					tempDealer.setString("IS_SCAN", "15");// 15 ReallyCityId为空
		    		updateIsScan(tempDealer);
					continue;
				}
				
				if (StringUtils.isNullOrEmpty(tempDealer.getString("Address"))) {
					tempDealer.setString("IS_SCAN", "16");// 16 Address为空
					updateIsScan(tempDealer);
					continue;		
				}
				
				if (StringUtils.isNullOrEmpty(tempDealer.getString("Service_Tel"))) {
					tempDealer.setString("IS_SCAN", "17"); // 17 ServiceTel为空
		    		updateIsScan(tempDealer);
					continue;		
				}
				
				if (StringUtils.isNullOrEmpty(tempDealer.getString("Status"))) {
					tempDealer.setString("Status", "18"); /// 18 Status为空
		    		updateIsScan(tempDealer);
					continue;		
				}
				
				dmsDealer.setDealerCode(tempDealer.getInteger("DEALER_CODE"));
				dmsDealer.setDealerName(tempDealer.getString("Dealer_Name"));
				dmsDealer.setDealerABCN(tempDealer.getString("setDealerAB_CN"));
				dmsDealer.setDealerABEN(tempDealer.getString("setDealerAB_EN"));
				dmsDealer.setCityID(tempDealer.getInteger("City_ID"));
				dmsDealer.setReallyCityID(tempDealer.getInteger("Really_City_Id"));
				dmsDealer.setAddress(tempDealer.getString("Address"));
				dmsDealer.setServiceTel(tempDealer.getString("Service_Tel"));
				dmsDealer.setStatus(tempDealer.getInteger("Status"));
				
				Integer returnFlag = stub.DMSDealerInfo(dmsDealer);
				tempDealer.setString("Return_Flag",returnFlag+"");
				logger.info("---------------对方返回结果"+returnFlag+"----------------");
			    
			    if(null==returnFlag){
			      logger.info("----------------返回参数为空,调用服务方法失败----------------");
			      
			      tempDealer.setString("Is_Scan", "0");// 0 从新调用
		    	  updateIsScan(tempDealer);
			      continue;
			    }else{
			    	if(returnFlag==0){
			    		logger.info("----------------对方操做失败，重新传递本次信息----------------");
			    		tempDealer.setString("Is_Scan", "0");// 0 从新调用
				    	updateIsScan(tempDealer);
			    		continue;
			    	}else{
			    		logger.info("----------------本次信息传递成功----------------");
			    		
			    		tempDealer.setString("Is_Scan", "1");// 1 is_scan 表示扫描成功
			    		updateIsScan(tempDealer);
			    		continue;
			    	}
			    }
			}
			dbService.endTxn(true);
			
		}catch(Exception e) {
			logger.error(e.getMessage(), e);
			dbService.endTxn(false);
		}finally{
			logger.info("====SI09 is finish====");
			dbService.clean();
			
		}
		return null;
	}
	
	/***
	 * 功能说明：如果返回的信息表示无需重新传递本次信息，则修改修改记录扫描标识。
	 * @param resultList
	 * create by ZRM
	 * create date 2013-06-05
	 */
	public void updateIsScan(TiDealerInfoPO tempDealer) {
		
		TiDealerInfoPO dalerInfo = TiDealerInfoPO.findById(tempDealer.getLong("Sequence_Id"));
		dalerInfo.setString("Is_Scan",tempDealer.getString("Is_Scan"));
		dalerInfo.setString("Return_Flag",tempDealer.getString("Return_Flag"));
		dalerInfo.saveIt();
	    
	}
	
	/**
	 * 功能说明:手动发送消息包
	 * 创建人: wangliang
	 * 最后修改日期: 2017-04-25 
	 */
	public static void main(String[] args) throws Throwable{
		
		SI09Impl action = new SI09Impl();
		action.execute();
	}

}
