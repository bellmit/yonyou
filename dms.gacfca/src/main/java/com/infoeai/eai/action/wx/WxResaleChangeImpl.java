package com.infoeai.eai.action.wx;

import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoeai.eai.action.BaseService;
import com.infoeai.eai.common.wx.WxUtil;
import com.infoeai.eai.dao.wx.WxResaleChangeDao;
import com.infoeai.eai.utils.HttpUtil;
import com.yonyou.dms.common.Util.Utility;
import com.yonyou.dms.common.domains.PO.basedata.TmDealerPO;
import com.yonyou.dms.common.domains.PO.basedata.TmVehiclePO;
import com.yonyou.dms.common.domains.PO.basedata.TtVsCustomerPO;
import com.yonyou.dms.common.domains.PO.basedata.TtVsSalesReportPO;
import com.yonyou.dms.function.utils.common.CommonUtils;

@Service
public class WxResaleChangeImpl extends BaseService implements WxResaleChange {

	private static Logger logger = LoggerFactory.getLogger(WxResaleChangeImpl.class);
	public static final String WX_ADDCUSMGRRELATION_ACTION_URL = WxUtil.WX_ACTION_BASE_URL
			+ "addowner.action";

	@Autowired
	WxResaleChangeDao dao;
	
	public boolean updateIsScan(Long id, String resultValue)
			throws Exception {
		TtVsSalesReportPO sReport = TtVsSalesReportPO.findById(id);
		sReport.setInteger("IS_SCAN",1);
		sReport.setString("RESULT_VALUE",resultValue);
		sReport.setLong("UPDATE_BY",new Long(80000003));
		sReport.setTimestamp("UPDATE_DATE",new Date());
		sReport.saveIt();
		return true;
	}

	@Override
	public String handleExecute() throws Exception {
		logger.info("====零售信息变更 is begin====");
		// 查找结果集
		List<Map> resultList = dao.getWxRcInfo();
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
				updateIsScan(Utility.getLong(resultList.get(i).get("REPORT_ID").toString()), stateCode);
			}catch(SocketTimeoutException ex)
			{
				ex.printStackTrace();
				logger.error(ex.getMessage(), ex);
				logger.info("====零售信息变更 SocketTimeout break====");
				break;
			} catch (Exception ex) {
				logger.error(ex.getMessage(), ex);
				updateIsScan(Utility.getLong(resultList.get(i).get("REPORT_ID").toString()), "POST_ERROR");
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
		logger.info("====零售信息变更 is finish====");
		return null;
	}

	public Map<String, String> bulidParam4Send(Map po)
			throws Exception {
		//查询经销商代码
		TmDealerPO dealer = TmDealerPO.findById(po.get("DEALER_ID"));
		
		//查询车辆ID对应VIN
		TmVehiclePO vehicle = TmVehiclePO.findById(po.get("VEHICLE_ID"));
		
		//查询客户信息
		TtVsCustomerPO ctm = TtVsCustomerPO.findById(po.get("CTM_ID"));
		
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("dealerCode", dealer.getString("DEALER_CODE"));//经销商代码
		paramMap.put("vin", vehicle.getString("VIN"));//车架号
		paramMap.put("dmsOwnerId", ctm.getString("CTM_NO"));//车主编号
		paramMap.put("clientType", ctm.getString("CTM_TYPE"));//客户类型（1：客户，2：公司）
		paramMap.put("name", ctm.getString("CTM_NAME"));//车主姓名
		paramMap.put("cellphone", ctm.getString("MAIN_PHONE"));//车主电话
		paramMap.put("gender", ctm.getString("SEX"));//车主性别（1：男， 2：女）
		//车主所在省
		if(Utility.testIsNull(ctm.getString("PROVINCE"))){
			paramMap.put("provinceId","");
		}else{
			paramMap.put("provinceId", ctm.getString("PROVINCE").toString());
		}
		//车主所在市
		if(Utility.testIsNull(ctm.getString("CITY"))){
			paramMap.put("cityId", "");
		}else{
			paramMap.put("cityId", ctm.getString("CITY").toString());
		}
		//车主所在县
		if(Utility.testIsNull(ctm.getString("TOWN"))){
			paramMap.put("district", "");
		}else{
			paramMap.put("district", ctm.getString("TOWN").toString());
		}
		//车主地址
		if(Utility.testIsNull(ctm.getString("ADDRESS"))){
			paramMap.put("address", "");
		}else{
			paramMap.put("address", ctm.getString("ADDRESS"));
		}
		//车主所在地邮编
		if(Utility.testIsNull(ctm.getString("POST_CODE"))){
			paramMap.put("postCode", "");
		}else{
			paramMap.put("postCode", ctm.getString("POST_CODE"));
		}
		//电子邮箱
		if(Utility.testIsNull(ctm.getString("EMAIL"))){
			paramMap.put("email", "");
		}else{
			paramMap.put("email", ctm.getString("EMAIL"));//电子邮箱
		}
		
		if(Utility.checkNull(ctm.getString("BIRTHDAY")).equals("")){
			paramMap.put("birthday", "1970-01-01");//默认生日
		}else{
			String birthday = ctm.getString("BIRTHDAY");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String ss = sdf.format(sdf.parse(birthday));
			paramMap.put("birthday", ss);//生日
		}
		
		paramMap.put("identificationType", ctm.getString("CARD_TYPE").toString());//证件类型
		paramMap.put("identificationNo", ctm.getString("CARD_NUM"));//证件号码
		//是否结婚
		if(Utility.testIsNull(ctm.getString("IS_MARRIED"))){
			paramMap.put("marrage", "");
		}else{
			paramMap.put("marrage", ctm.getString("IS_MARRIED").toString());
		}
		//家庭月收入
		if(Utility.testIsNull(ctm.getString("INCOME"))){
			paramMap.put("familyIncome", "");
		}else{
			paramMap.put("familyIncome", ctm.getString("INCOME").toString());
		}
		//教育程度
		if(Utility.testIsNull(ctm.getString("EDUCATION"))){
			paramMap.put("educationLevel", "");
		}else{
			paramMap.put("educationLevel", ctm.getString("EDUCATION").toString());
		}
		//最佳联系时间
		if(Utility.testIsNull(ctm.getString("BEST_CONTACT_TIME"))){
			paramMap.put("bestContactTime", "");
		}else{
			paramMap.put("bestContactTime", ctm.getString("BEST_CONTACT_TIME"));
		}
		//爱好
		if(Utility.testIsNull(ctm.getString("HOBBY"))){
			paramMap.put("Hobby", "");
		}else{
			paramMap.put("Hobby", ctm.getString("HOBBY"));
		}
		//所在行业大类
		if(Utility.testIsNull(ctm.getString("INDUSTRY_FIRST"))){
			paramMap.put("industryFrist", "");
		}else{
			paramMap.put("industryFrist", ctm.getString("INDUSTRY_FIRST"));
		}
		//所在行业二类
		if(Utility.testIsNull(ctm.getString("INDUSTRY_SECOND"))){
			paramMap.put("industrySecond", "");
		}else{
			paramMap.put("industrySecond", ctm.getString("INDUSTRY_SECOND"));
		}
		//职业类别
		if(Utility.testIsNull(ctm.getString("VOCATION_TYPE"))){
			paramMap.put("vocationType", "");
		}else{
			paramMap.put("vocationType", ctm.getString("VOCATION_TYPE"));
		}
		//职务名称
		if(Utility.testIsNull(ctm.getString("POSITION_NAME"))){
			paramMap.put("positionName", "");
		}else{
			paramMap.put("positionName", ctm.getString("POSITION_NAME"));
		}
		
		paramMap.put("salesAdviser", ctm.getString("SALES_ADVISER"));//销售顾问
		
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		String salesDate = ft.format(po.get("CONSIGNATION_DATE"));
		paramMap.put("salesDate", salesDate);//销售日期
		
		paramMap.put("salesMileage", CommonUtils.checkNull(po.get("MILES")));//车辆交付公里数
		paramMap.put("isModify",CommonUtils.checkNull(po.get("IS_MODIFY")));//是否更改（1是0否）
		return paramMap;
	}
	
}
