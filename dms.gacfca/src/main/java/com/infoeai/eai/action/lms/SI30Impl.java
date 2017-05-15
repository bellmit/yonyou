package com.infoeai.eai.action.lms;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoeai.eai.action.BaseService;
import com.infoeai.eai.wsClient.lms.dmswebservice.DCC_DMSStatus;
import com.infoeai.eai.wsClient.lms.singlequery.SingleQuery;
import com.infoeai.eai.wsClient.lms.singlequery.SingleQueryLocator;
import com.infoeai.eai.wsClient.lms.singlequery.SingleQuerySoapStub;
import com.yonyou.dcs.dao.SI30Dao;
import com.yonyou.dms.common.domains.PO.basedata.TiSalesLeadsFeedbackCreateDcsPO;

//建档DMS反馈
@Service
public class SI30Impl extends BaseService implements SI30{

	private static Logger logger = LoggerFactory.getLogger(SI30Impl.class);
	
	@Autowired
	SI30Dao dao;

	@Override
	@SuppressWarnings("unused")
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		try {
			logger.info("====SI30 is begin====");
			/****************************** 开启事物 *********************/
			dbService();

			SingleQuerySoapStub stub = new SingleQuerySoapStub();
			SingleQuery singleQuery = new SingleQueryLocator();
			stub = (SingleQuerySoapStub) singleQuery.getSingleQuerySoap();
			logger.info("----------------调用对方服务地址：" + singleQuery.getSingleQuerySoapAddress() + "----------------");

			// 查找结果集
			List<TiSalesLeadsFeedbackCreateDcsPO> resultList = new ArrayList<TiSalesLeadsFeedbackCreateDcsPO>();
			resultList = dao.getSI30Info();

			// 由于对方方法只能接受单个对象传递，只能用循环
			for (int i = 0; i < resultList.size(); i++) {
				TiSalesLeadsFeedbackCreateDcsPO tempSales = resultList.get(i);
				// 接口赋值
				DCC_DMSStatus dmsSales = new DCC_DMSStatus();

				dmsSales.setID(Integer.parseInt(tempSales.getString("Nid")));// tempSales.getNid().toString()

				Integer returnFlag = stub.reSingleQuery(
						tempSales.getLong("Nid").intValue(),	//lms ID
						Integer.valueOf(tempSales.getString("Conflicted_Type")),	 //1：撞单 	 0：不撞单
						tempSales.getString("Dms_Customer_No"),		//dms id
						tempSales.getString("Opportunity_Level_Id"),	//H A B C N
						tempSales.getString("Sales_Consultant"));	//销售顾问

				logger.info("---------------对方返回结果" + returnFlag + "----------------");
				if (null == returnFlag) {
					logger.info("----------------返回参数为空,调用服务方法失败----------------");
					continue;
				} else {
					if (returnFlag == 0) {
						logger.info("----------------对方操做失败，重新传递本次信息----------------");
						continue;
					} else {
						logger.info("----------------本次信息传递成功----------------");
						 updateIsScan(tempSales);
						// 手动提交事务
						if (i % 100 == 0) {
							dbService.endTxn(true);
							dbService.clean();
							
							/****************************** 结束并清空事物 *********************/
							/****************************** 开启事物 *********************/
							dbService.beginTxn();
							
						}
						continue;
					}
				}
			}
			dbService.endTxn(true);
			
			/****************************** 结束事物 *********************/
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			dbService.endTxn(true);
			
		} finally {
			logger.info("====SI30 is finish====");
			dbService.endTxn(true);
			dbService.clean();
			
		}
		return null;
	}

	/***
	 * 功能说明：如果返回的信息表示无需重新传递本次信息，则修改修改记录扫描标识。
	 * 
	 * @param resultList
	 *            create by baojie create date 2014-02-14
	 */
	public void updateIsScan(TiSalesLeadsFeedbackCreateDcsPO tempSales) {
		

		TiSalesLeadsFeedbackCreateDcsPO conPO = TiSalesLeadsFeedbackCreateDcsPO.findByCompositeKeys(tempSales.getLong("Sequence_Id"));
		
		conPO.setString("Is_Scan", "1");
		conPO.setLong("Update_By", new Long(80000002));
		conPO.setDate("Update_Date", Calendar.getInstance().getTime());
		conPO.saveIt();
		
	}

	/**
	 * 功能说明:手动发送消息包 创建人: baojie 最后修改日期: 2014-02-14
	 */
	public static void main(String[] args) throws Throwable {
		// TODO Auto-generated method stub
		
		SI30Impl action = new SI30Impl();
		action.execute();
	}

}
