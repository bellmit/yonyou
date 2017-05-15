package com.infoeai.eai.action.jec;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.javalite.activejdbc.Base;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoeai.eai.DTO.Dcs2Jec02DTO;
import com.infoeai.eai.DTO.JECAddNVhclMaintainDTO;
import com.infoeai.eai.DTO.JECCarDTO;
import com.infoeai.eai.action.BaseService;
import com.infoeai.eai.common.parsexml.JecxmlParse;
import com.infoeai.eai.dao.ctcai.SI18Dao;
import com.infoeai.eai.wsClient.jec.DmsJecService;
import com.infoeai.eai.wsClient.jec.DmsJecServiceHttpBindingStub;
import com.infoeai.eai.wsClient.jec.DmsJecServiceLocator;
import com.yonyou.dms.common.domains.PO.basedata.TiSalesJecCustomerPO;

/**
 * DCS->JEC
 * 车辆实销信息导入
 * @author luoyang
 *
 */
@Service
public class SI19Impl extends BaseService implements SI19 {
	
	private static Logger logger = Logger.getLogger(SI19Impl.class);
	
	@Autowired
	SI18Dao dao;

	@Override
	public String execute() throws Exception {
		logger.info("====SI19 is begin====");
		beginDbService();
		try {
			DmsJecServiceHttpBindingStub stub = new DmsJecServiceHttpBindingStub();
			DmsJecService jecService = new DmsJecServiceLocator();
			stub = (DmsJecServiceHttpBindingStub)jecService.getDmsJecServiceHttpPort();
			logger.info("----------------调用对方服务地址："+jecService.getDmsJecServiceHttpPortAddress()+"----------------");
	        //查找结果集_客户结果集
			List<Dcs2Jec02DTO> resultList = new ArrayList<Dcs2Jec02DTO>();
			resultList = dao.getSI19Info();
			
			//每个信息单独传递
			for(int j=0;j<resultList.size();j++){
				//定义传递信息的变量
				List<Dcs2Jec02DTO> passList = new ArrayList<Dcs2Jec02DTO>();
				//根据查找的客户信息补充其实销信息和维修信息
				Dcs2Jec02DTO tempPO = resultList.get(j);
				//根据jecCustomerId查找所有的实销车辆信息
				List<JECCarDTO> salesVehicleList = dao.getVehiclesByCustInfo(tempPO.getSequenceId());
				tempPO.setCars(salesVehicleList);
				
				//根据jecCustomerId查找所有的实销车维修信息
				List<JECAddNVhclMaintainDTO> salesMaintainsList = dao.getMaintainsByCustInfo(tempPO.getSequenceId());
				tempPO.setMaintains(salesMaintainsList);	
				
				passList.add(tempPO);
				
				//将查找的结果集封装成类似XML文件的字符串
				JecxmlParse parse = new JecxmlParse();
			    String passXml = parse.addOwnerVehicleXMLParse(passList);
			    logger.info("---------------封装的XML文件:"+passXml+"----------------");
			    //调用对方的方法并接受返回信息
			    String returnFlag = stub.addOwnerVehicle(passXml)+"</Root>";
			    logger.info("---------------返回结果状态:"+returnFlag+"----------------");
			    Map<String, String> returnValue = parse.readXml(returnFlag);
			    
			    if(null==returnValue.get("StateCode")){
			      logger.info("----------------返回参数为空,调用服务方法失败----------------");
			      logger.info("----------------"+returnValue.get("ErrorInfo")+"----------------");
			      continue;
			    }else{
			    	if("-1".equals(returnValue.get("StateCode"))){
			    		logger.info("----------------对方操做失败，无需重新传递本次信息----------------");
			    		logger.info("----------------"+returnValue.get("ErrorInfo")+"----------------");
			    		updateIsScan(passList);
			    		//手动提交事务
						if(j%100==0){
							dbService.endTxn(true);
							dbService.clean();
			        		/******************************结束并清空事物*********************/
			                /******************************开启事物*********************/
			    			dbService();
						}
			    		continue;
			    	}else if("0".equals(returnValue.get("StateCode"))){
			    		logger.info("----------------对方操做失败，需重新传递本次信息----------------");
			    		logger.info("----------------"+returnValue.get("ErrorInfo")+"----------------");
			    		continue;
			    	}else{
			    		logger.info("----------------本次信息传递成功----------------");
			    		updateIsScan(passList);
			    		//手动提交事务
						if(j%100==0){
							dbService.endTxn(true);
							dbService.clean();
			        		/******************************结束并清空事物*********************/
			                /******************************开启事物*********************/
							dbService();
						}
			    		continue;
			    	}
			    }
			}
			dbService.endTxn(true);
    		/******************************结束事物*********************/
		}catch(Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			dbService.endTxn(false);
		}finally{
			logger.info("====SI19 is finish====");
			Base.detach();
			dbService.clean();
		}
		return null;
	}

	/***
	 * 功能说明：如果返回的信息表示无需重新传递本次信息，则修改修改记录扫描标识。
	 * @param resultList
	 */
	private void updateIsScan(List<Dcs2Jec02DTO> resultList) {
		if(resultList != null && !resultList.isEmpty()){			
			for(int i = 0; i < resultList.size(); i++){		
				TiSalesJecCustomerPO.update("IS_SCAN = ?", "SEQUENCE_ID = ?", "1",resultList.get(i).getSequenceId());
			}
		}
		
	}

}
