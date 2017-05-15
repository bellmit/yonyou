package com.infoeai.eai.action.lms;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.axis.encoding.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoeai.eai.action.BaseService;
import com.infoeai.eai.wsClient.lms.dmswebservice.DCC_DMSStatus;
import com.infoeai.eai.wsClient.lms.dmswebservice.DMSWebservice;
import com.infoeai.eai.wsClient.lms.dmswebservice.DMSWebserviceLocator;
import com.infoeai.eai.wsClient.lms.dmswebservice.DMSWebserviceSoapStub;
import com.yonyou.dcs.dao.SI10Dao;
import com.yonyou.dms.common.domains.PO.basedata.TiSalesLeadsFeedbackDcsPO;
import com.yonyou.dms.function.utils.common.StringUtils;

@Service
public class SI11Impl extends BaseService implements SI11 {
	
	private static Logger logger = LoggerFactory.getLogger(SI11Impl.class);
	@Autowired
	SI10Dao dao;
	
	@Override
	public String execute() throws Exception {
		
		// TODO Auto-generated method stub
		try {
			logger.info("====SI11 is begin====");
			/******************************开启事物*********************/
			dbService();
			
			DMSWebserviceSoapStub stub = new DMSWebserviceSoapStub();
			DMSWebservice lmsService = new DMSWebserviceLocator();
			stub = (DMSWebserviceSoapStub)lmsService.getDMSWebserviceSoap();
			logger.info("----------------调用对方服务地址："+lmsService.getDMSWebserviceSoapAddress()+"----------------");
			
			//查找结果集
			List<TiSalesLeadsFeedbackDcsPO> resultList = new ArrayList<TiSalesLeadsFeedbackDcsPO>();
			resultList = dao.getSI11Info();
			
			//由于对方方法只能接受单个对象传递，只能用循环
			for(int i=0;i<resultList.size();i++){
				TiSalesLeadsFeedbackDcsPO tempSales = resultList.get(i);
				//接口赋值
				DCC_DMSStatus dmsSales = new DCC_DMSStatus();
				
//				if (null == tempSales.getNid() || tempSales.getNid().toString().equals("0")) {
//					updateIsScan(tempSales,"11");
//					continue;
//				}
				if(StringUtils.isNullOrEmpty(tempSales.getString("Customer_Code"))) {
					updateIsScan(tempSales,"12");
					continue;
				}
				
				if(StringUtils.isNullOrEmpty(tempSales.getString("Sleep_Reason"))) {
					updateIsScan(tempSales,"13");
					continue;
				}

				if(StringUtils.isNullOrEmpty(tempSales.getString("Brand_Code"))) {
					updateIsScan(tempSales,"14");
					continue;
				}
				
				if(StringUtils.isNullOrEmpty(tempSales.getString("Serias_Code"))) {
					updateIsScan(tempSales,"15");
					continue;
				}
				
				if(StringUtils.isNullOrEmpty(tempSales.getString("Configer_Code"))) {
					updateIsScan(tempSales,"16");
					continue;
				}
				
				if(StringUtils.isNullOrEmpty(tempSales.getString("Name"))) {
					updateIsScan(tempSales,"17");
					continue;
				}
				
				dmsSales.setID(Integer.parseInt(tempSales.getString("Nid")));
				dmsSales.setDMSCustomerID(tempSales.getString("Customer_Code"));
				dmsSales.setStatus(Integer.valueOf(tempSales.getString("Status")));
				dmsSales.setDMSOpportunityLevelID(tempSales.getString("Opportunity_Level_Id"));
				dmsSales.setDMSSalesConsultant(tempSales.getString("Sales_Consultant"));
				if(!StringUtils.isNullOrEmpty(tempSales.getTimestamp("Sleep_Time"))) {
					Calendar sysdate = Calendar.getInstance();
					sysdate.setTime(tempSales.getTimestamp("Sleep_Time"));
					dmsSales.setGiveUpDate(sysdate);
					dmsSales.setOtherGiveUpReason(tempSales.getString("Sleep_Reason")); 
				}
				if(!StringUtils.isNullOrEmpty(tempSales.getDate("Purchase_Time"))) {
					Calendar sysdate1 = Calendar.getInstance();
					sysdate1.setTime(tempSales.getDate("Purchase_Time"));
					dmsSales.setWinOrderDate(sysdate1);
				}
				dmsSales.setBrandID(tempSales.getString("Brand_Code"));
				dmsSales.setModelID(tempSales.getString("Serias_Code"));
				dmsSales.setCarStyleID(tempSales.getString("Configer_Code"));
				//2015-1-12新增字段
				dmsSales.setDealerCode(tempSales.getString("Dealer_Code"));
				String encode=URLEncoder.encode(tempSales.getString("NAME"),"UTF-8");
				dmsSales.setName(Base64.encode(encode.getBytes()));
				dmsSales.setGender(tempSales.getString("Gender") ==null ? null : String.valueOf(tempSales.getString("Gender")));
				dmsSales.setPhone(tempSales.getString("Phone"));
				dmsSales.setTelephone(tempSales.getString("Telephone"));
				dmsSales.setProvinceID(Integer.parseInt(tempSales.getString("Province_Id")));
				dmsSales.setCityID(Integer.parseInt(tempSales.getString("city_id")));
				//tempSales.getRegisterDate(); 
				if(!StringUtils.isNullOrEmpty(tempSales.getDate("Register_Date"))){
				
					Calendar cal12 = Calendar.getInstance();
					cal12.setTime(tempSales.getDate("Register_Date"));//dmsSales中createDate对就tempSales中的注册时间
					dmsSales.setCreateDate(cal12);
				}
				//dmsSales.setConsiderationID(Integer.parseInt(tempSales.getConsiderationId().toString()));
				
				Integer returnFlag = stub.backSaleslead(dmsSales);
				tempSales.setString("Return_Flag", returnFlag+"");
				logger.info("---------------对方返回结果"+returnFlag+"----------------");
			    if(null==returnFlag){
			      logger.info("----------------返回参数为空,调用服务方法失败----------------");
			      updateIsScan(tempSales,"0");
			      continue;
			    }else{
			    	if(returnFlag==0){
			    		logger.info("----------------对方操做失败，重新传递本次信息----------------");
			    		updateIsScan(tempSales,"0");
			    		continue;
			    	}else{
			    		logger.info("----------------本次信息传递成功----------------");
			    		updateIsScan(tempSales,"1");
			    		//手动提交事务
						if(i%100==0){
							dbService.endTxn(true);
							dbService.clean();
			                
			        		/******************************结束并清空事物*********************/
			                /******************************开启事物*********************/
			    			
			    			dbService.beginTxn();
						}
			    		continue;
			    	}
			    }
			}
			dbService.endTxn(true);
			
			/******************************结束事物*********************/
		}catch(Exception e) {
			logger.error(e.getMessage(), e);
			logger.info("====SI11 is Exception===="+e.getMessage());
			dbService.endTxn(true);
			
		}finally{
			logger.info("====SI11 is finish====");
			
			dbService.endTxn(true);
			dbService.clean();
		}
		return null;
	}
	
	/***
	 * 功能说明：如果返回的信息表示无需重新传递本次信息，则修改修改记录扫描标识。
	 * @param resultList
	 * create by ZRM
	 * create date 2013-06-07
	 */
	public void updateIsScan(TiSalesLeadsFeedbackDcsPO tempSales,String IsScan) {
		TiSalesLeadsFeedbackDcsPO conPO = TiSalesLeadsFeedbackDcsPO.findById(tempSales.getString("Sequence_Id"));
			
			conPO.setString("Is_Scan", IsScan);
			conPO.setLong("Update_By", new Long(80000002));
			conPO.setDate("Update_Date", Calendar.getInstance().getTime());
			conPO.setString("Return_Flag", tempSales.getString("Return_Flag"));
			conPO.saveIt();
	}
	
	/**
	 * 功能说明:手动发送消息包
	 * 创建人: ZRM
	 * 最后修改日期: 2013-06-26
	 */
	public static void main(String[] args) throws Throwable{
		// TODO Auto-generated method stub
		//ContextUtil.loadConf();
		SI11Impl action = new SI11Impl();
		action.execute();
	}

}
