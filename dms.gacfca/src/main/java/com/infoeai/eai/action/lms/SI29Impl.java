package com.infoeai.eai.action.lms;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoeai.eai.action.BaseService;
import com.infoeai.eai.wsServer.SI12.CreateInfoPO;
import com.yonyou.dcs.dao.SI12Dao;
import com.yonyou.dms.common.domains.PO.basedata.TiSalesLeadsCustomerCreateDcsPO;

/***
 * 功能说明：LMS建档校验.改功能主要是对用webService传过来的结果集进行解析
 * @author baojie
 *create_date:2013-05-28
 */

@Service
public class SI29Impl extends BaseService implements SI29 {
	private static Logger logger = LoggerFactory.getLogger(SI29Impl.class);
	@Autowired
	SI12Dao dao;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	public String execute(Object request) throws Exception {
		//对通过的数据解析
		String returnResult = "0";   //返回1表示成功,0表示失败
		try {
			logger.info("====SI29 is begin====");
			beginDbService();
			CreateInfoPO[] si_rq = (CreateInfoPO[])request;
			for(int i=0;i<si_rq.length;i++){
				//创建一个新的对象
				TiSalesLeadsCustomerCreateDcsPO salesLeadCreate = new TiSalesLeadsCustomerCreateDcsPO();
				salesLeadCreate.setLong("ID",new Long(si_rq[i].getId()));
				
				//校验数据类型是否符合
				if(null!=si_rq[i].getPhone() && !"".equals(si_rq[i].getPhone()) && !"0".equals(si_rq[i].getPhone())
						 && !"Null".equals(si_rq[i].getPhone())){
					salesLeadCreate.setString("Phone", si_rq[i].getPhone());
				}
				if(null==si_rq[i].getDealerCode() || "".equals(si_rq[i].getDealerCode()) || "0".equals(si_rq[i].getDealerCode())
						 || "Null".equals(si_rq[i].getDealerCode())){
					returnResult = "lms传递的dealerCode为空";
					logger.info("lms传递的dealerCode为空");
					continue;
				}else{
					String dealerCode = dao.getLocalDealerCode(si_rq[i].getDealerCode());
					if(null!=dealerCode){
						salesLeadCreate.setString("Dealer_Code", dealerCode);
					}else{
						returnResult = "lms传递的dealerCode在DCS找不到对应的dealerCode";
						logger.info("lms传递的dealerCode在DCS找不到对应的dealerCode");
						continue;
					}
					salesLeadCreate.setString("Dealer_Code", si_rq[i].getDealerCode());
				}
				
				salesLeadCreate.setString("Is_Scan", "0");
				salesLeadCreate.setLong("Create_By", new Long(80000001));
				salesLeadCreate.setTimestamp("Create_Date", new Date());
				salesLeadCreate.saveIt();
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
			dbService.endTxn(false);
			
		}finally{
			logger.info("====SI29 is finish====");
			Base.detach();
			dbService.clean();
			
		}
		//调用下端：下发DCC建档客户信息
		//SADCS003Fore osc = new SADCS003Fore();
		//osc.execute();
		return returnResult;
	}
}
