package com.infoeai.eai.action.lms;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoeai.eai.action.BaseService;

import com.infoeai.eai.wsClient.lms.dmswebservice.DCC_DMSProduct;
import com.infoeai.eai.wsClient.lms.dmswebservice.DMSWebservice;
import com.infoeai.eai.wsClient.lms.dmswebservice.DMSWebserviceLocator;
import com.infoeai.eai.wsClient.lms.dmswebservice.DMSWebserviceSoapStub;
import com.yonyou.dcs.dao.SI10Dao;
import com.yonyou.dms.common.domains.PO.basedata.MaterialInPO;
import com.yonyou.dms.function.utils.common.StringUtils;

@Service
public class SI10Impl extends BaseService implements SI10 {
	
	private static Logger logger = LoggerFactory.getLogger(SI10Impl.class);	
	@Autowired
	SI10Dao dao;
	
	@Override
	@SuppressWarnings("unused")
	public String execute() throws Exception {
		
		try {
			logger.info("====SI10 is begin====");
			/******************************开启事物*********************/
			//开启事务
			dbService();
			DMSWebserviceSoapStub stub = new DMSWebserviceSoapStub();
			DMSWebservice lmsService = new DMSWebserviceLocator();
			stub = (DMSWebserviceSoapStub)lmsService.getDMSWebserviceSoap();
			logger.info("----------------调用对方服务地址："+lmsService.getDMSWebserviceSoapAddress()+"----------------");
			//查找结果集
			List<MaterialInPO> resultList = new ArrayList<MaterialInPO>();
			resultList = dao.getSI10Info();
			
			//由于对方方法只能接受单个对象传递，只能用循环
			for(int i=0;i<resultList.size();i++){
				MaterialInPO tempProduct = resultList.get(i);
				//接口赋值
				DCC_DMSProduct dmsProduct = new DCC_DMSProduct();
				if(StringUtils.isNullOrEmpty(tempProduct.getString("Brand_Id"))){
					updateIsScan(tempProduct,"11");
					continue;
				}
				if(StringUtils.isNullOrEmpty(tempProduct.getString("Brand_Code"))){
					updateIsScan(tempProduct,"12");
					continue;
				}
				
				
				if(StringUtils.isNullOrEmpty(tempProduct.getString("Brand_NAME"))){
					updateIsScan(tempProduct,"13");
					continue;
				}
				
				if(StringUtils.isNullOrEmpty(tempProduct.getString("Brand_Level"))){
					updateIsScan(tempProduct,"14");
					continue;
				}
				
				if(StringUtils.isNullOrEmpty(tempProduct.getString("Series_Id"))){
					updateIsScan(tempProduct,"15");
					continue;
				}
				if(StringUtils.isNullOrEmpty(tempProduct.getString("Series_Code"))){
					updateIsScan(tempProduct,"16");
					continue;
				}
				if(StringUtils.isNullOrEmpty(tempProduct.getString("Series_Name"))){
					updateIsScan(tempProduct,"17");
					continue;
				}
				if(StringUtils.isNullOrEmpty(tempProduct.getString("Series_Level"))){
					updateIsScan(tempProduct,"18");
					continue;
				}
				if(StringUtils.isNullOrEmpty(tempProduct.getString("Model_Id"))){
					updateIsScan(tempProduct,"19");
					continue;
				}
				if(StringUtils.isNullOrEmpty(tempProduct.getString("Model_Code"))){
					updateIsScan(tempProduct,"20");
					continue;
				}
				if(StringUtils.isNullOrEmpty(tempProduct.getString("Model_name"))){
					updateIsScan(tempProduct,"21");
					continue;
				}
				if(StringUtils.isNullOrEmpty(tempProduct.getString("Model_Level"))){
					updateIsScan(tempProduct,"22");
					continue;
				}
				if(StringUtils.isNullOrEmpty(tempProduct.getString("Carstyle_Id"))){
					updateIsScan(tempProduct,"23");
					continue;
				}
				if(StringUtils.isNullOrEmpty(tempProduct.getString("Carstyle_Name"))){
					updateIsScan(tempProduct,"24");
					continue;
				}
				if(StringUtils.isNullOrEmpty(tempProduct.getString("Carstyle_Level"))){
					updateIsScan(tempProduct,"25");
					continue;
				}
				if(StringUtils.isNullOrEmpty(tempProduct.getString("Colour_Code"))){
					updateIsScan(tempProduct,"26");
					continue;
				}
				if(StringUtils.isNullOrEmpty(tempProduct.getString("Colour_Name"))){
					updateIsScan(tempProduct,"27");
					continue;
				}
				if(StringUtils.isNullOrEmpty(tempProduct.getString("Status"))){
					updateIsScan(tempProduct,"28");
					continue;
				}
				
				
				dmsProduct.setBrandID(tempProduct.getString("Brand_Id"));       
				dmsProduct.setBrandCode(tempProduct.getString("Brand_Code"));
				dmsProduct.setBrandName(tempProduct.getString("Brand_Name")); 
				dmsProduct.setBrandLevel(Integer.parseInt(tempProduct.getString("Brand_Level")));
				dmsProduct.setSeriesID(tempProduct.getString("Series_Id")); 
				dmsProduct.setSeriesCode(tempProduct.getString("Series_Code"));
				dmsProduct.setSeriesName(tempProduct.getString("Series_Name")); 
				dmsProduct.setSeriesLevel(Integer.parseInt(tempProduct.getString("Series_Level"))); 
				dmsProduct.setModelID(tempProduct.getString("Model_Id")); 
				dmsProduct.setModelCode(tempProduct.getString("Model_Code"));
				dmsProduct.setModelName(tempProduct.getString("Model_Name")); 
				dmsProduct.setModelLevel(Integer.parseInt(tempProduct.getString("Model_Level")));
				dmsProduct.setCarStyleID(tempProduct.getString("Carstyle_Id")); 
				dmsProduct.setCarStyleName(tempProduct.getString("Carstyle_Name"));
				dmsProduct.setCarStyleLevel(Integer.parseInt(tempProduct.getString("Carstyle_Level"))); 
				dmsProduct.setColourCode(tempProduct.getString("Colour_Code")); 
				dmsProduct.setColourName(tempProduct.getString("Colour_Name"));
				dmsProduct.setStatus(Integer.parseInt(tempProduct.getString("Status")));
				
				Integer returnFlag = stub.DMSProductInfo(dmsProduct);
				
				tempProduct.setString("Return_Flag","returnFlag+''" );
				
			    if(null==returnFlag){
			      logger.info("----------------返回参数为空,调用服务方法失败----------------");
			      updateIsScan(tempProduct,"0");
			      continue;
			    }else{
			    	if(returnFlag==0){
			    		logger.info("----------------对方操做失败，重新传递本次信息----------------");
			    		updateIsScan(tempProduct,"0");
			    		continue;
			    	}else{
			    		logger.info("----------------本次信息传递成功----------------");
			    		updateIsScan(tempProduct,"1");
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
			dbService.endTxn(true);
		}finally{
			logger.info("====SI10 is finish====");
			dbService.endTxn(true);
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
	public void updateIsScan( MaterialInPO tempProduct,String isScan) {
		
		MaterialInPO conMaterial = MaterialInPO.findByCompositeKeys(tempProduct.getString("Sequence_Id"));
		conMaterial.setString("Is_Scan", isScan);
		conMaterial.setLong("UPDATED_AT", new Long(80000002));
		conMaterial.setDate("UPDATED_BY",new Date());
		conMaterial.setString("Return_Flag", tempProduct.getString("Return_Flag"));
		conMaterial.saveIt();
	}
	
	/**
	 * 功能说明:手动发送消息包
	 * 创建人: ZRM
	 * 最后修改日期: 2013-06-26
	 */
	public static void main(String[] args) throws Throwable{
		// TODO Auto-generated method stub
		
		SI10Impl action = new SI10Impl();
		action.execute();
	}

}
