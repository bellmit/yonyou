package com.infoeai.eai.action.wx;

import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.javalite.activejdbc.Base;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoeai.eai.action.BaseService;
import com.infoeai.eai.common.wx.WxUtil;
import com.infoeai.eai.dao.wx.WX01Dao;
import com.infoeai.eai.po.wx.WX01PO;
import com.infoeai.eai.utils.HttpUtil;
import com.yonyou.dms.common.domains.PO.basedata.TiWxCustomerPO;

/**
 * 功能说明：车主信息
 */
@Service
public class WX01Impl extends  BaseService implements WX01 {
	private static Logger logger = Logger.getLogger(WX01Impl.class);
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	public static final String WX_ADDOWNER_ACTION_URL = WxUtil.WX_ACTION_BASE_URL
			+ "addowner.action";
	@Autowired
	WX01Dao dao;

	public boolean updateIsScan(Long id, String resultValue)
			throws Exception {
	
		TiWxCustomerPO desCustomer = TiWxCustomerPO.findByCompositeKeys(id);
	
		desCustomer.setString("Is_Scan", "1");
		desCustomer.setString("Result_Value", resultValue);
		desCustomer.setLong("Update_By",new Long(80000003));
		desCustomer.setDate("UpdateDate", new Date());
		desCustomer.saveIt();
		return true;
	}
	
	@Override
	public String handleExecute() throws Exception {
		logger.info("====WX01 is begin====");
		// 查找结果集
		List<Map> resultList = dao.getWX01Info();
		logger.info("resultList....SIZE : "+resultList.size());
		dbService();
		for (int i = 0; i < resultList.size(); i++) {
			try {
				
				Map<String, String> params = bulidParam4Send(resultList.get(i));
				String resultXml = HttpUtil.httpPost(WX_ADDOWNER_ACTION_URL,params);

				Map<String, String> result = WxUtil.readWxActionResult(resultXml);
				String stateCode = result.get("StateCode");
				updateIsScan( Long.parseLong(resultList.get(i).get("Business_Id").toString()), stateCode);
			}
			catch(SocketTimeoutException ex)
			{
				ex.printStackTrace();
				logger.error(ex.getMessage(), ex);
				//若出现java.net.SocketTimeoutException: connect timed out 则停止本次传送
				logger.info("====WX01 SocketTimeout break====");
				break;
			}catch (Exception ex) {
				
				ex.printStackTrace();
				logger.error(ex.getMessage(), ex);
				updateIsScan(Long.parseLong(resultList.get(i).get("Business_Id").toString()), "POST_ERROR");
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
		logger.info("====WX01 is finish====");
		return null;
	}


	public Map<String, String> bulidParam4Send(Map po)
			throws Exception {
		WX01PO wx01Po = (WX01PO) po;
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("dmsOwnerId", wx01Po.getCustomerNo());
		paramMap.put("name", wx01Po.getCustomerName());
		paramMap.put("cellphone", wx01Po.getPhone());
		paramMap.put("provinceId", String.valueOf(wx01Po.getProvince()));
		paramMap.put("cityId", String.valueOf(wx01Po.getCity()));
		paramMap.put("district", String.valueOf(wx01Po.getDistrict()));
		paramMap.put("address", wx01Po.getAddress());
		paramMap.put("gender", String.valueOf(wx01Po.getGender()));
		paramMap.put("postCode", wx01Po.getZipCode());
		paramMap.put("email", wx01Po.getEmail());
		if (wx01Po.getBirthday() != null) {
			paramMap.put("birthday", sdf.format(wx01Po.getBirthday()));
		}
		paramMap.put("dealerCode", wx01Po.getDealerCode());
		paramMap.put("vin", wx01Po.getVin());
		if (wx01Po.getSalesDate() != null) {
			paramMap.put("salesDate", sdf.format(wx01Po.getSalesDate()));
		}
		paramMap.put("salesMileage", String.valueOf(wx01Po.getSalesMileage()));
		paramMap.put("clientType", String.valueOf(wx01Po.getOwnerProperty()));
		paramMap.put("identificationType", String.valueOf(wx01Po.getCtCode()));
		paramMap.put("identificationNo", wx01Po.getCertificateNo());
		paramMap.put("marrage", String.valueOf(wx01Po.getOwnerMarriage()));
		paramMap.put("familyIncome", String.valueOf(wx01Po.getFamilyIncome()));
		paramMap.put("educationLevel",
				String.valueOf(wx01Po.getEducationLevel()));
		paramMap.put("bestContactTime", wx01Po.getBestContactTime());
		paramMap.put("hobby", wx01Po.getHobby());
		paramMap.put("industryFrist", wx01Po.getIndustryFirst());
		paramMap.put("industrySecond", wx01Po.getIndustrySecond());
		paramMap.put("vocationType", wx01Po.getVocationType());
		paramMap.put("positionName", wx01Po.getPositionName());
		paramMap.put("salesAdviser", wx01Po.getSalesAdviser());
		paramMap.put("isModify", wx01Po.getIsModify());
		return paramMap;
	}

	public static void main(String[] args) {
		//ContextUtil.loadConf();
		WX01Impl wx01=new WX01Impl();
		//wx01.execute();
	}
}
