package com.infoeai.eai.action.wx;

import java.net.SocketTimeoutException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.infoeai.eai.action.BaseService;
import com.infoeai.eai.common.wx.WxUtil;
import com.infoeai.eai.dao.wx.WX02Dao;
import com.infoeai.eai.utils.HttpUtil;
import com.yonyou.dms.common.domains.PO.basedata.TiWxCustomerManagerPO;
import com.yonyou.dms.function.utils.common.CommonUtils;



/**
 * 功能说明：交车客户、客户经理重绑 
 */
@Service
public class WX02uImpl extends BaseService implements WX02u {
	private static Logger logger = LoggerFactory.getLogger(WX02uImpl.class);
	public static final String WX_ADDCUSMGRRELATION_ACTION_URL = WxUtil.WX_ACTION_BASE_URL
			+ "modifyclientaccountmgr.action";

	
	protected boolean updateIsScan(Long id, String resultValue)
			throws Exception {
		return true;
	}
	public boolean updateIsScan(Long id, String resultValue, int sendTimes)
			throws Exception {
		
		TiWxCustomerManagerPO desCustomer = TiWxCustomerManagerPO.findById(id);
		desCustomer.setString("Is_Scan", "1");
		desCustomer.setString("Result_Value", resultValue);
		desCustomer.setInteger("Send_Times", sendTimes);
		desCustomer.setLong("Update_By", new Long(80000003));
		desCustomer.setTimestamp("Update_Date", new Date());
		desCustomer.saveIt();
		return true;
	}


	public String handleExecute() throws Exception {
		logger.info("====WX02u is begin====");
		// 查找结果集
		WX02Dao dao = new WX02Dao();
		Map tcmp = null;
		int sendTimes = 0;
		dbService();
		List<Map> resultList = dao.getWX02uInfo();
		logger.info("resultList....SIZE : "+resultList.size());
		for (int i = 0; i < resultList.size(); i++) {
			try {
				//
				
				tcmp = resultList.get(i);
				logger.info("====发送前次数===="+tcmp.get("SEND_TIMES"));
				sendTimes = CommonUtils.checkNull(tcmp.get("SEND_TIMES"))==""?0:((int)tcmp.get("SEND_TIMES"));
				sendTimes++;
				logger.info("====发送后次数===="+sendTimes);
				
				Map<String, String> params = bulidParam4Send(resultList.get(i));
				String resultXml = HttpUtil.httpPost(WX_ADDCUSMGRRELATION_ACTION_URL,
						params);

				logger.info("---------------对方返回结果" + resultXml
						+ "----------------");

				Map<String, String> result = WxUtil.readWxActionResult(resultXml);
				String stateCode = result.get("StateCode");
				updateIsScan((long)resultList.get(i).get("MANAGER_ID"), stateCode, sendTimes);
			}
			catch(SocketTimeoutException ex)
			{
				ex.printStackTrace();
				logger.error(ex.getMessage(), ex);
				//若出现java.net.SocketTimeoutException: connect timed out 则停止本次传送
				logger.info("====WX02u SocketTimeout break====");
				break;
			} catch (Exception ex) {
				logger.error(ex.getMessage(), ex);
				updateIsScan((long)resultList.get(i).get("MANAGER_ID"), "POST_ERROR", sendTimes);
				ex.printStackTrace();
				continue;
			}
			finally{
				dbService.endTxn(true);
				Base.detach();
				dbService.clean();
				beginDbService();
			}
		}
		dbService.endTxn(true);
		dbService.clean();
		logger.info("====WX02u is finish====");
		return null;
	}


	public Map<String, String> bulidParam4Send(Map po)
			throws Exception {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("vin", CommonUtils.checkNull(po.get("Vin")));
		paramMap.put("dealerCode", CommonUtils.checkNull(po.get("Dealer_Code")));
		paramMap.put("dmsAccountMgrId", CommonUtils.checkNull(po.get("Service_Advisor")));
		paramMap.put("accountMgrName", CommonUtils.checkNull(po.get("Employee_Name")));
		paramMap.put("phoneNum", CommonUtils.checkNull(po.get("Mobile")));
		// new add  by wangjian 2015-5-28 
		paramMap.put("dmsOwnerId", CommonUtils.checkNull(po.get("Dms_Owner_Id")));//车主编号
		paramMap.put("name", CommonUtils.checkNull(po.get("NAME")));//车主姓名
		paramMap.put("cellphone", CommonUtils.checkNull(po.get("Cell_phone")));//车主电话
		
		paramMap.put("changeTime", CommonUtils.checkNull(po.get("Dispatch_Time")));//分配时间
		return paramMap;
	}

	public static void main(String[] args) {
		
		WX02uImpl wx02u=new WX02uImpl();
		//wx02u.execute();
	}
}
