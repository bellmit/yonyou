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
import com.infoeai.eai.dao.wx.WXHiringTaxiesDao;
import com.yonyou.dms.common.Util.Utility;
import com.yonyou.dms.common.domains.PO.basedata.TtHiringTaxiesPO;
import com.yonyou.dms.function.utils.common.CommonUtils;

@Service
public class WxHiringTaxiesImpl extends BaseService implements WxHiringTaxies {

	private static Logger logger = LoggerFactory.getLogger(WxHiringTaxiesImpl.class);
	public static final String WX_ADDCUSMGRRELATION_ACTION_URL = WxUtil.WX_ACTION_BASE_URL
			+ "recieveDmsCarOrder.action";
	@Autowired
	WXHiringTaxiesDao dao;
	
	public boolean updateIsScan(Long id, String resultValue)
			throws Exception {
		TtHiringTaxiesPO hTexies = TtHiringTaxiesPO.findById(id);
		hTexies.setString("IS_SCAN","1");
		hTexies.setString("RESULT_VALUE",resultValue);
		hTexies.setLong("UPDATE_BY",new Long(80000003));
		hTexies.setTimestamp("UPDATE_DATE",new Date());
		hTexies.saveIt();
		return true;
	}

	@Override
	public String handleExecute() throws Exception {
		logger.info("====订车 is begin====");
		// 查找结果集
		List<Map> resultList = dao.getWxHtInfo();
		logger.info("resultList....SIZE : "+resultList.size());
		dbService();
		for (int i = 0; i < resultList.size(); i++) {
			try {
				Map<String, String> params = bulidParam4Send(resultList.get(i));
				String resultXml = HttpUtil.httpPost(WX_ADDCUSMGRRELATION_ACTION_URL,
						params);

				logger.info("---------------对方返回结果" + resultXml
						+ "----------------");

				Map<String, String> result = WxUtil
						.readWxActionResult(resultXml);
				String stateCode = result.get("StateCode");
				updateIsScan(Utility.getLong(resultList.get(i).get("HIRING_TAXIES_ID").toString()), stateCode);
			}catch(SocketTimeoutException ex)
			{
				ex.printStackTrace();
				logger.error(ex.getMessage(), ex);
				logger.info("====订车 SocketTimeout break====");
				break;
			} catch (Exception ex) {
				logger.error(ex.getMessage(), ex);
				updateIsScan(Utility.getLong(resultList.get(i).get("HIRING_TAXIES_ID").toString()), "POST_ERROR");
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
		logger.info("====订车 is finish====");
		return null;
	}

	public Map<String, String> bulidParam4Send(Map po)
			throws Exception {
		
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("dmsid", CommonUtils.checkNull(po.get("HIRING_TAXIES_ID")));//dms 系统的主键
		paramMap.put("dealerCode", CommonUtils.checkNull(po.get("DEALER_CODE")));//经销商代码
		paramMap.put("saleNo", CommonUtils.checkNull(po.get("SO_NO")));//销售订单号
		paramMap.put("customerName", CommonUtils.checkNull(po.get("CUSTOMER_NAME")));//车主姓名
		paramMap.put("customerMobile", CommonUtils.checkNull(po.get("CUSTOMER_MOBILE")));//车主手机号
		paramMap.put("soldBy", CommonUtils.checkNull(po.get("SOLD_BY")));//销售顾问
		paramMap.put("phone", CommonUtils.checkNull(po.get("PHONE")));//联系电话
		paramMap.put("cardType", CommonUtils.checkNull(po.get("CARD_TYPE")));//证件类型
		paramMap.put("cardNo", CommonUtils.checkNull(po.get("CARD_NO")));//证件号码
		paramMap.put("brand", CommonUtils.checkNull(po.get("BRAND")));//汽车品牌
		paramMap.put("carModel", CommonUtils.checkNull(po.get("CAR_MOBILE")));//车型
		paramMap.put("firstColor", CommonUtils.checkNull(po.get("FIRST_COLOR")));//车身颜色（首选）
		paramMap.put("deliverMode", CommonUtils.checkNull(po.get("DELIVER_MODE")));//交车方式
		paramMap.put("appDeliverDate", CommonUtils.checkNull(po.get("APP_DELIVER_DATE")));//预约交车时间
		paramMap.put("stockDate", CommonUtils.checkNull(po.get("STOCK_DATE")));//车辆到库时间
		paramMap.put("dealDate", CommonUtils.checkNull(po.get("DEAL_DATE")));//订车成功时间
		paramMap.put("vin", CommonUtils.checkNull(po.get("VIN")));//车辆代码
		
		return paramMap;
	}
}
