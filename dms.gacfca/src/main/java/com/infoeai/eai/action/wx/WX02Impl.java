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
import com.infoeai.eai.common.HttpUtil;
import com.infoeai.eai.common.wx.WxUtil;
import com.infoeai.eai.dao.wx.WX02Dao;
import com.yonyou.dms.common.domains.PO.basedata.TiWxCustomerManagerPO;
import com.yonyou.dms.function.utils.common.CommonUtils;

@Service
public class WX02Impl extends BaseService implements WX02 {

	private static Logger logger = LoggerFactory.getLogger(WX02Impl.class);
	public static final String WX_ADDCUSMGRRELATION_ACTION_URL = WxUtil.WX_ACTION_BASE_URL
			+ "onetoonecustomermanager.action";

	@Autowired
	WX02Dao dao;
	
	public boolean updateIsScan(Long id, String resultValue)
			throws Exception {
		return true;
	}
	
	public boolean updateIsScan(Long id, String resultValue, int sendTimes)
			throws Exception {
		TiWxCustomerManagerPO desCustomer = TiWxCustomerManagerPO.findById(id);
		desCustomer.setString("IS_SCAN","1");
		desCustomer.setString("RESULT_VALUE",resultValue);
		desCustomer.setInteger("SEND_TIMES",sendTimes);
		desCustomer.setLong("UPDATE_BY",new Long(80000003));
		desCustomer.setTimestamp("UPDATE_DATE",new Date());
		desCustomer.saveIt();
		return true;
	}

	@Override
	public String handleExecute() throws Exception {
		logger.info("====WX02 is begin====");
		// 查找结果集
		Map tcmp = null;
		int sendTimes = 0;
	
		List<Map> resultList = dao.getWX02Info();
		dbService();
		logger.info("resultList....SIZE : "+resultList.size());
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
			}catch(SocketTimeoutException ex) {
				ex.printStackTrace();
				logger.error(ex.getMessage(), ex);
				//若出现java.net.SocketTimeoutException: connect timed out 则停止本次传送
				logger.info("====WX02 SocketTimeout break====");
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
		logger.info("====WX02 is finish====");
		return null;
	}
	
	@Override
	public String execute() throws Exception {
		handleExecute();
		return null;
	}
	
	public Map<String, String> bulidParam4Send(Map po)
			throws Exception {
		
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("vin", CommonUtils.checkNull(po.get("VIN")));//车架号
		paramMap.put("dealerCode", CommonUtils.checkNull(po.get("DEALER_CODE")));//经销商代码
		paramMap.put("dmsAccountMgrId", CommonUtils.checkNull(po.get("SERVICE_ADVISOR")));//客户经理ID
		paramMap.put("accountMgrName", CommonUtils.checkNull(po.get("EMPLOYEE_NAME")));//客户经理姓名
		paramMap.put("mgrPhone", CommonUtils.checkNull(po.get("MOBILE")));//客户经理电话
		// new add  by wangjian 2015-5-27 
//		paramMap.put("dispatchTime", po.getDispatchTime());//分配时间
		paramMap.put("clientType", CommonUtils.checkNull(po.get("CLIENT_TYPE")));//客户类型（1：客户，2：公司）
		paramMap.put("name", CommonUtils.checkNull(po.get("NAME")));//车主姓名
		paramMap.put("cellphone", CommonUtils.checkNull(po.get("CELLPHONE")));//车主电话
		paramMap.put("gender", CommonUtils.checkNull(po.get("GENDER")));//车主性别（1：男， 2：女）
		paramMap.put("provinceId", CommonUtils.checkNull(po.get("PROVINCE_ID")));//车主所在省
		paramMap.put("cityId", CommonUtils.checkNull(po.get("CITY_ID")));//车主所在市
		paramMap.put("district", CommonUtils.checkNull(po.get("DISTRICT")));//车主所在县
		paramMap.put("address", CommonUtils.checkNull(po.get("ADDRESS")));//车主地址
		paramMap.put("postCode", CommonUtils.checkNull(po.get("POST_CODE")));//车主所在地邮编
		paramMap.put("idOrCompCode", CommonUtils.checkNull(po.get("ID_OR_COMP_CODE")));//身份证号
		paramMap.put("email", CommonUtils.checkNull(po.get("EMAIL")));//电子邮箱
		paramMap.put("dmsOwnerId", CommonUtils.checkNull(po.get("DMS_OWNER_ID")));//车主编号
		
		paramMap.put("buyTime", CommonUtils.checkNull(po.get("BUY_TIME")));//交车时间
		paramMap.put("bindingTime", CommonUtils.checkNull(po.get("WX_BIND_TIME")));//绑定时间
		
		return paramMap;
	}
	
	public static void main(String[] args) {
//		WX02Impl wx02=new WX02Impl();
//		wx02.execute();
	}

}
