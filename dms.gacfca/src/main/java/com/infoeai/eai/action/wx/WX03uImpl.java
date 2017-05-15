package com.infoeai.eai.action.wx;

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
import com.infoeai.eai.dao.wx.WX03Dao;
import com.infoeai.eai.utils.HttpUtil;
import com.yonyou.dms.common.Util.Utility;
import com.yonyou.dms.common.domains.PO.basedata.TiWxVehiclePO;

@Service
public class WX03uImpl extends BaseService implements WX03u {

	private static Logger logger = LoggerFactory.getLogger(WX03uImpl.class);
	public static final String WX_MODIFYCAR_ACTION_URL = WxUtil.WX_ACTION_BASE_URL
			+ "modifycar.action";
	@Autowired
	WX03Dao dao;
	
	public boolean updateIsScan(Long id, String resultValue)
			throws Exception {
		TiWxVehiclePO desWxVehicle = TiWxVehiclePO.findById(id);
		desWxVehicle.setString("IS_SCAN","1");
		desWxVehicle.setString("RESULT_VALUE",resultValue);
		desWxVehicle.setLong("UPDATE_BY",new Long(80000003));
		desWxVehicle.setTimestamp("UPDATE_DATE",new Date());
		desWxVehicle.saveIt();
		return true;
	}

	@Override
	public String handleExecute() throws Exception {
		logger.info("====WX03u is begin====");
		// 查找结果集
		List<Map> resultList = dao.getWX03uInfo();
		logger.info("resultList....SIZE : "+resultList.size());
		dbService();
		for (int i = 0; i < resultList.size(); i++) {
			try {
				Map<String, String> params = bulidParam(resultList.get(i));
				String resultXml = HttpUtil.httpPost(WX_MODIFYCAR_ACTION_URL, params);

				logger.info("---------------对方返回结果" + resultXml + "----------------");

				Map<String, String> result = WxUtil.readWxActionResult(resultXml);
				String stateCode = result.get("StateCode");
				updateIsScan(Utility.getLong(resultList.get(i).get("VEHICLE_ID").toString()), stateCode);
			} catch (Exception ex) {
				logger.error(ex.getMessage(), ex);
				updateIsScan(Utility.getLong(resultList.get(i).get("VEHICLE_ID").toString()), "POST_ERROR");
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
		logger.info("====WX03u is finish====");
		return null;
	}

	public Map<String, String> bulidParam(Map po) 
				throws Exception {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("vin",po.get("VIN").toString());
		paramMap.put("dealerCode",po.get("DEALER_CODE")!=null?po.get("DEALER_CODE").toString():"");
		paramMap.put("brandId",po.get("BRAND_ID")!=null?po.get("BRAND_ID").toString():"");
		paramMap.put("modeId",po.get("SERIES_ID")!=null?po.get("SERIES_ID").toString():"");	//dms series 对应微信平台modleId
		paramMap.put("styleId",po.get("MODEL_ID")!=null?po.get("MODEL_ID").toString():"");	//dms carstyle 对应微信平台styleId
		paramMap.put("colorId",po.get("COLOR_ID")!=null?po.get("COLOR_ID").toString():"");
		paramMap.put("engineNO",po.get("ENGINE_NO")!=null?po.get("ENGINE_NO").toString():"");//发动机号
		return paramMap;
	}
	

	public static void main(String[] args) {
//		WX03u wx03u=new WX03u();
//		wx03u.execute();
	}
	
}
