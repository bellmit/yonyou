package com.infoeai.eai.action.jec;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.javalite.activejdbc.Base;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoeai.eai.DTO.Dcs2Jec01DTO;
import com.infoeai.eai.action.BaseService;
import com.infoeai.eai.common.parsexml.JecxmlParse;
import com.infoeai.eai.dao.ctcai.SI18Dao;
import com.infoeai.eai.wsClient.jec.DmsJecService;
import com.infoeai.eai.wsClient.jec.DmsJecServiceHttpBindingStub;
import com.infoeai.eai.wsClient.jec.DmsJecServiceLocator;
import com.yonyou.dms.common.domains.PO.basedata.TiVehiclesJecDcsPO;

/**
 * DCS->JEC
 * 车辆下线信息导入
 * @author luoyang
 *
 */
@Service
public class SI18Impl extends BaseService implements SI18 {
	
	private static Logger logger = Logger.getLogger(SI18Impl.class);
	
	@Autowired
	SI18Dao dao;

	@Override
	public String execute() throws Exception {
		logger.info("====SI18 is begin====");
		beginDbService();
		try {
			DmsJecServiceHttpBindingStub stub = new DmsJecServiceHttpBindingStub();
			DmsJecService jecService = new DmsJecServiceLocator();
			stub = (DmsJecServiceHttpBindingStub)jecService.getDmsJecServiceHttpPort();
			logger.info("----------------调用对方服务地址："+jecService.getDmsJecServiceHttpPortAddress()+"----------------");
	        //查找结果集
			List<Dcs2Jec01DTO> resultList = new ArrayList<Dcs2Jec01DTO>();
			resultList = dao.getSI18Info();
			for(int i=0;i<resultList.size();i++){
				//每条记录单独下发
				List<Dcs2Jec01DTO> tempList = new ArrayList<Dcs2Jec01DTO>();
				tempList.add(resultList.get(i));
				
				//将查找的结果集封装成类似XML文件的字符串
				JecxmlParse parse = new JecxmlParse();
			    String passXml = parse.addNewVehicleXMLParse(tempList);
			    //调用对方的方法并接受返回信息
			    String returnFlag = stub.addNewVehicle(passXml)+"</Root>";
			    logger.info("---------------对方返回结果"+returnFlag+"----------------");
			    Map<String, String> returnValue = parse.readXml(returnFlag);
			    
			    if(null==returnValue.get("StateCode")){
			      logger.info("----------------返回参数为空,调用服务方法失败----------------");
			      logger.info("----------------失败原因:"+returnValue.get("ErrorInfo")+"----------------");
			      return null;
			    }else{
			    	if("-1".equals(returnValue.get("StateCode"))){
			    		logger.info("----------------对方操做失败，无需重新传递本次信息----------------");
			    		logger.info("----------------失败原因："+returnValue.get("ErrorInfo")+"----------------");
			    		updateIsScan(tempList);
			    		//手动提交事务
						if(i%100==0){
							dbService.endTxn(true);
							dbService.clean();
			        		/******************************结束并清空事物*********************/
			                /******************************开启事物*********************/
			    			dbService();
						}
					    continue;
			    	}else if("0".equals(returnValue.get("StateCode"))){
			    		logger.info("----------------对方操做失败，需重新传递本次信息------------------");
			    		logger.info("----------------失败原因："+returnValue.get("ErrorInfo")+"----------------");
			    		continue;
			    	}else{
			    		logger.info("----------------本次信息传递成功----------------");
			    		updateIsScan(tempList);
			    		//手动提交事务
						if(i%100==0){
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
			logger.info("====SI18 is finish====");
			Base.detach();
			dbService.clean();
		}
		return null;
	}

	private void updateIsScan(List<Dcs2Jec01DTO> resultList) {
		if(resultList != null && !resultList.isEmpty()){
			for(int i = 0 ; i < resultList.size(); i++){
				Dcs2Jec01DTO dto = resultList.get(i);
				TiVehiclesJecDcsPO.update("IS_SCAN = ?", "SEQUENCE_ID = ?", "1",dto.getSequenceId());
			}
		}
		
	}

}
