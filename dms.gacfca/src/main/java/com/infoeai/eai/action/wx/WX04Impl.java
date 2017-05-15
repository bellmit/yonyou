package com.infoeai.eai.action.wx;

import java.net.SocketTimeoutException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoeai.eai.action.BaseService;
import com.infoeai.eai.common.wx.WxUtil;
import com.infoeai.eai.dao.wx.WX04Dao;
import com.infoeai.eai.utils.HttpUtil;
import com.yonyou.dms.common.domains.PO.customer.TiWxCustomerManagerBindingPO;
import com.yonyou.dms.function.utils.common.CommonUtils;
@Service
public class WX04Impl extends BaseService implements WX04 {

	private static Logger logger = LoggerFactory.getLogger(WX04Impl.class);
	public static final String WX_ADDCUSMGRRELATION_ACTION_URL = WxUtil.WX_ACTION_BASE_URL
			+ "createaccountmgr.action";
	@Autowired
	WX04Dao dao;
	
	public boolean updateIsScan(Long id, String resultValue)
			throws Exception {
		return true;
	}
	
	public boolean updateIsScan(Long id, String resultValue, int sendTimes)
			throws Exception {
		TiWxCustomerManagerBindingPO desWxManagerBinding = TiWxCustomerManagerBindingPO.findById(id);
		desWxManagerBinding.setString("IS_SCAN","1");
		desWxManagerBinding.setString("RESULT_VALUE",resultValue);
		desWxManagerBinding.setInteger("SEND_TIMES",sendTimes);
		desWxManagerBinding.setLong("UPDATE_BY",new Long(80000003));
		desWxManagerBinding.setTimestamp("UPDATE_DATE",new Date());
		desWxManagerBinding.saveIt();
		return true;
	}
	
	@Override
	public String handleExecute() throws Exception {
		logger.info("====WX04 is begin====");
		// 查找结果集
		Map tcmp = null;
		int sendTimes = 0;
		
		List<Map> resultList = dao.getWX04Info();
		logger.info("resultList....SIZE : "+resultList.size());
		dbService();
		for (int i = 0; i < resultList.size(); i++) {
			try {
				
				tcmp = resultList.get(i);
				logger.info("====发送前次数===="+tcmp.get("SEND_TIMES"));
				sendTimes = CommonUtils.checkNull(tcmp.get("SEND_TIMES"))==""?0:((int)tcmp.get("SEND_TIMES"));
				sendTimes++;
				logger.info("====发送后次数===="+sendTimes);
				
				Map<String, String> params = bulidParam4Send(resultList.get(i));
				String resultXml = HttpUtil.httpPost(WX_ADDCUSMGRRELATION_ACTION_URL, params);

				logger.info("---------------对方返回结果" + resultXml + "----------------");

				Map<String, String> result = WxUtil.readWxActionResult(resultXml);
				String stateCode = result.get("StateCode");
				updateIsScan((long)resultList.get(i).get("MANAGER_ID"), stateCode, sendTimes);
			}catch(SocketTimeoutException ex)
			{
				ex.printStackTrace();
				logger.error(ex.getMessage(), ex);
				//若出现java.net.SocketTimeoutException: connect timed out 则停止本次传送
				logger.info("====WX04 SocketTimeout break====");
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
		logger.info("====WX04 is finish====");
		return null;
	}

	public Map<String, String> bulidParam4Send(Map po)
			throws Exception {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("dmsAccountMgrId", CommonUtils.checkNull(po.get("SERVICE_ADVISOR")));
		paramMap.put("dealerCode", CommonUtils.checkNull(po.get("DEALER_CODE")));
		paramMap.put("accountMgrName", CommonUtils.checkNull(po.get("EMPLOYEE_NAME")));
		paramMap.put("deskPhone", CommonUtils.checkNull(po.get("MOBILE")));
		paramMap.put("isDefaultMgr", CommonUtils.checkNull(po.get("IS_DEFAULT_WX_SA")));//是否微信默认客户经理
		return paramMap;
	}

	
	public static void main(String[] args) {
//		WX04 wx04=new WX04();
//		wx04.execute();
	}
	
}
