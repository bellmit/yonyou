package com.infoeai.eai.action.wx;

import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
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
import com.infoeai.eai.dao.wx.WX05Dao;
import com.infoeai.eai.utils.HttpUtil;
import com.yonyou.dms.common.Util.Utility;
import com.yonyou.dms.common.domains.PO.customer.TiWxMaintainPO;
import com.yonyou.dms.function.utils.common.CommonUtils;
@Service
public class WX05Impl extends BaseService implements WX05 {

	private static Logger logger = LoggerFactory.getLogger(WX05Impl.class);
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	public static final String WX_ADDMAINTAIN_ACTION_URL = WxUtil.WX_ACTION_BASE_URL
			+ "createmaintain.action";
	@Autowired
	WX05Dao dao;
	
	public boolean updateIsScan(Long id, String resultValue)
			throws Exception {
		TiWxMaintainPO desWxMaintain = TiWxMaintainPO.findById(id);
		desWxMaintain.setString("IS_SCAN","1");
		desWxMaintain.setString("RESULT_VALUE",resultValue);
		desWxMaintain.setLong("UPDATE_BY",new Long(80000003));
		desWxMaintain.setTimestamp("UPDATE_DATE",new Date());
		desWxMaintain.saveIt();
		return true;
	}

	@Override
	public String handleExecute() throws Exception {
		logger.info("====WX05 is begin====");
		// 查找结果集
		WX05Dao dao = new WX05Dao();
		List<Map> resultList = dao.getWX05Info();
		logger.info("resultList....SIZE : "+resultList.size());
		dbService();
		for (int i = 0; i < resultList.size(); i++) {
			try {
				Map<String, String> params = bulidParam4Send(resultList.get(i));
				
				logger.info("WX_ADDMAINTAIN_ACTION_URL>"+WX_ADDMAINTAIN_ACTION_URL);
				
				String resultXml = HttpUtil.httpPost(WX_ADDMAINTAIN_ACTION_URL, params);

				logger.info("---------------对方返回结果" + resultXml
						+ "----------------");

				Map<String, String> result = WxUtil
						.readWxActionResult(resultXml);
				String stateCode = result.get("StateCode");
				updateIsScan(Utility.getLong(resultList.get(i).get("MAINTAIN_ID").toString()), stateCode);
			} catch (SocketTimeoutException ex) {
				ex.printStackTrace();
				logger.error(ex.getMessage(), ex);
				// 若出现java.net.SocketTimeoutException: connect timed out 则停止本次传送
				logger.info("====WX05 SocketTimeout break====");
				break;
			} catch (Exception ex) {
				logger.error(ex.getMessage(), ex);
				updateIsScan((long)resultList.get(i).get("MAINTAIN_ID"), "POST_ERROR");
				ex.printStackTrace();
				continue;
			} finally {
				dbService.endTxn(true);
				Base.detach();
				dbService.clean();
				beginDbService();
			}
		}
		dbService.endTxn(true);
		dbService.clean();
		logger.info("====WX05 is finish====");
		return null;
	}

	public Map<String, String> bulidParam4Send(Map po)
			throws Exception {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("vin", CommonUtils.checkNull(po.get("VIN")));
		paramMap.put("dealerCode", CommonUtils.checkNull(po.get("COMPANY_CODE")));
		paramMap.put("workOrderNo", CommonUtils.checkNull(po.get("REPAIR_NO")));
		paramMap.put("accountMgrName", CommonUtils.checkNull(po.get("REPORT_MAN")));
		if (po.get("MAKE_DATE") != null) {
			paramMap.put("workOrderDate", sdf.format(po.get("MAKE_DATE")));
		}
		paramMap.put("mileage", CommonUtils.checkNull(po.get("OUT_MILEAGE")));
		paramMap.put("maintainType", CommonUtils.checkNull(po.get("ORDER_TYPE")));
		paramMap.put("repairType", CommonUtils.checkNull(po.get("REPAIR_TYPE")));
		return paramMap;
	}

	public static void main(String[] args) {
//		ContextUtil.loadConf();
//		WX05 wx05 = new WX05();
//		wx05.execute();
	}
}
