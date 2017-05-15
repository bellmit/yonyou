/**
 * 
 */
package com.infoeai.eai.action.bsuv.dccmApp;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.axis.encoding.Base64;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.infoeai.eai.action.BaseService;
import com.infoeai.eai.action.bsuv.common.LonPropertiesLoad;
import com.infoeai.eai.common.HttpConnectionManager;
import com.yonyou.dms.common.Util.DateTimeUtil;
import com.yonyou.dms.common.Util.Utility;
import com.yonyou.dms.common.domains.PO.basedata.TiAppNCustomerInfoPO;
import com.yonyou.dms.common.domains.PO.basedata.TiDmsNCustomerInfoPO;
import com.yonyou.dms.function.utils.common.CommonUtils;

/**
 * @author Administrator
 ** 功能描述  潜客信息数据交互接口
 * 传输方向 DCCM->DCS
 */
@Service
public class DCCMTODCS001Impl extends BaseService implements DCCMTODCS001 {
	private static final Logger logger = LoggerFactory.getLogger(DCCMTODCS001Impl.class);
	private LonPropertiesLoad lonLeader=LonPropertiesLoad.getInstance();
	
	
	/**
	 * 接口执行入口
	 * @throws Exception 
	 */ 
	@Override
	public String execute() throws Exception {
		try {
			logger.info("================潜客信息数据交互接口========开始=========");
			//事务开启
			dbService();
			
			Calendar beforeTime = Calendar.getInstance();
			beforeTime.add(Calendar.MINUTE, -35);// 35分钟之前的时间
			Date beforeD = beforeTime.getTime();
			
			Calendar endTime = Calendar.getInstance();
			endTime.add(Calendar.MINUTE, 0);// 当前时间
			Date endD = endTime.getTime();
			
			SimpleDateFormat formate = new SimpleDateFormat("yyyyMMddHHmmss");
			String from = formate.format(beforeD);
			String to = formate.format(endD);
			
			
			String URL=lonLeader.getValue("DCCM_TO_DCS_001");
			String fetchNewData = "1";//是否获取新增数据，为1表示获取新增数据，为2表示获取指定时间内有更新的数据
			JSONObject json = HttpConnectionManager.httpsGet(URL+fetchNewData+"/"+from+"/"+to);
//			JSONObject json = HttpConnectionManager.httpsGet("https://dccmtest.boldseas.com/api/sps/dataSync/selectOpportunityInfo/1/20180302115326/20170302115329");
//			JSONObject json = HttpConnectionManager.httpsGet(URL+fetchNewData+"/20170405233000/20170406000000");
//			JSONObject json = HttpConnectionManager.httpsGet(URL+fetchNewData+"/20170410100000/20170410103000");
//			JSONObject json = HttpConnectionManager.httpsGet(URL+fetchNewData+"/20170910100000/20170410103000");
//			JSONObject json = HttpConnectionManager.httpsGet(URL+fetchNewData+"/20170413173000/2017041318000");

			if(Utility.testIsNotNull(Utility.checkNull(json))){
				JSONArray date = json.getJSONArray("Data");
				int iSize1 = date.length();  
				logger.info("json中数组Size:" + iSize1);  
			    for (int i = 0; i < iSize1; i++) {  
			    	json= date.getJSONObject(i);
			    	logger.info("================获取潜客信息数据详细必填项====================");
			    	logger.info("[" + i + "]uniquenessID=" + CommonUtils.checkNull(json.get("uniquenessID")));
			    	logger.info("[" + i + "]clientType=" + CommonUtils.checkNull(json.get("clientType")));
			    	logger.info("[" + i + "]name=" + CommonUtils.checkNull(json.get("name")));
			    	logger.info("[" + i + "]gender=" + CommonUtils.checkNull(json.get("gender")));
			    	logger.info("[" + i + "]phone=" + CommonUtils.checkNull(json.get("phone")));
			    	logger.info("[" + i + "]provinceID=" + CommonUtils.checkNull(json.get("provinceID")));
			    	logger.info("[" + i + "]cityID=" + CommonUtils.checkNull(json.get("cityID")));
			    	logger.info("[" + i + "]oppLevelID=" + CommonUtils.checkNull(json.get("oppLevelID")));
			    	logger.info("[" + i + "]sourceType=" + CommonUtils.checkNull(json.get("sourceType")));
			    	logger.info("[" + i + "]secondSourceType=" + CommonUtils.checkNull(json.get("secondSourceType")));
			    	logger.info("[" + i + "]dealerCode=" + CommonUtils.checkNull(json.get("dealerCode")));
			    	logger.info("[" + i + "]dealerUserID=" + CommonUtils.checkNull(json.get("dealerUserID")));
			    	logger.info("[" + i + "]brandID=" + CommonUtils.checkNull(json.get("brandID")));
			    	logger.info("[" + i + "]modelID=" + CommonUtils.checkNull(json.get("modelID")));
			    	logger.info("[" + i + "]carStyleID=" + CommonUtils.checkNull(json.get("carStyleID")));
			    	logger.info("[" + i + "]intentCarColor=" + CommonUtils.checkNull(json.get("intentCarColor")));
			    	logger.info("[" + i + "]createDate=" + CommonUtils.checkNull(json.get("createDate")));
			    	logger.info("[" + i + "]updateDate=" + CommonUtils.checkNull(json.get("updateDate")));
			    	logger.info("[" + i + "]dccmId=" + CommonUtils.checkNull(json.get("dccmId")));
			    	
			    	//接受售中传过来的数据到业务表
			    	TiAppNCustomerInfoPO tInfo = new TiAppNCustomerInfoPO();
					tInfo.setString("Uniqueness_Id",CommonUtils.checkNull(json.get("uniquenessID")));//DMS客户唯一ID
					tInfo.setString("Client_Type",CommonUtils.checkNull(json.get("clientType")));//客户类型
					String cuName = CommonUtils.checkNull(json.get("name"));
					String name = new String(Base64.decode(new String(cuName.getBytes("UTF-8"),"UTF-8")));
					tInfo.setString("Name",name);//客户的姓名（Base64转码）
					tInfo.setInteger("Gender",CommonUtils.checkNull(json.get("gender")));//性别
					tInfo.setString("Phome",CommonUtils.checkNull(json.get("phone")));//手机号
					
					tInfo.setString("Telephone",CommonUtils.checkNull(json.get("telephone")));//固定电话 非必填
	
					String provinceID = CommonUtils.checkNull(json.get("provinceID"));
					if(Utility.testIsNotNull(provinceID)){
						tInfo.setInteger("Province_Id",Integer.parseInt(provinceID));//省份ID
					}
					String cityID = CommonUtils.checkNull(json.get("cityID"));
					if(Utility.testIsNotNull(cityID)){
						tInfo.setInteger("City_Id",Integer.parseInt(cityID));//城市ID
					}
					
					String birthday = CommonUtils.checkNull(json.get("birthday"));
					if(Utility.testIsNotNull(birthday)){
						tInfo.setTimestamp("Birthday",DateTimeUtil.getStringFormat(json.get("birthday").toString()));//生日（YYYY-MM-DD HH:mm:ss）非必填
					}
					
					tInfo.setString("Opp_Level_Id",CommonUtils.checkNull(json.get("oppLevelID")));//客户级别ID
					tInfo.setString("Source_Type",CommonUtils.checkNull(json.get("sourceType")));//客户来源
					tInfo.setString("Second_Source_Type",CommonUtils.checkNull(json.get("secondSourceType")));//客户二级来源
					tInfo.setString("Dealer_Code",CommonUtils.checkNull(json.get("dealerCode")));//经销商代码
					tInfo.setString("Dealer_User_Id",CommonUtils.checkNull(json.get("dealerUserID")));//销售人员的ID
					
					tInfo.setString("Buy_Car_Bugget",CommonUtils.checkNull(json.get("buyCarBudget")));//购车预算 非必填
					
					tInfo.setString("Brand_Id",CommonUtils.checkNull(json.get("brandID")));//品牌ID
					tInfo.setString("Model_Id",CommonUtils.checkNull(json.get("modelID")));//车型ID
					tInfo.setString("Car_Style_Id",CommonUtils.checkNull(json.get("carStyleID")));//车款ID
					tInfo.setString("Intent_Car_Color",CommonUtils.checkNull(json.get("intentCarColor")));//车辆颜色ID
					
					String buyCarcondition = CommonUtils.checkNull(json.get("buyCarcondition"));
					if(Utility.testIsNotNull(buyCarcondition)){
						tInfo.setInteger("Buy_Carcondition",Integer.parseInt(json.get("buyCarcondition").toString()));//购车类型 非必填
					}
					String isToShop = CommonUtils.checkNull(json.get("isToShop"));//是否到店
					tInfo.setInteger("Is_To_Shop",Integer.parseInt(isToShop));
					String timeToShop = CommonUtils.checkNull(json.get("timeToShop"));//到店日期
					if(Utility.testIsNotNull(timeToShop)){
						tInfo.setTimestamp("Time_To_Shop",DateTimeUtil.getStringFormat(timeToShop));
					}
		    		tInfo.setLong("Update_By",222222L);
		    		String updateDate = CommonUtils.checkNull(json.get("updateDate"));
					if(Utility.testIsNotNull(updateDate)){
						tInfo.setTimestamp("Update_Date",DateTimeUtil.getStringFormat(updateDate));
					}
		    		tInfo.setLong("Create_By",222222L);
		    		String createDate = CommonUtils.checkNull(json.get("createDate"));
					if(Utility.testIsNotNull(createDate)){
						tInfo.setTimestamp("Create_Date",DateTimeUtil.getStringFormat(createDate));//创建时间（YYYY-MM-DD HH:mm:ss）
					}
					tInfo.saveIt();
			    }	
			}		
		    dbService.endTxn(true);
			logger.info("================潜客信息数据交互接口========成功=========");
		} catch (Exception e) {
			e.printStackTrace();
			dbService.endTxn(false);	// 回滚事务
			logger.info("================潜客信息数据交互接口========异常=========" + e);
		} finally {
			dbService.clean();	// 清理事务
			logger.info("================潜客信息数据交互接口========结束=========");
		}
		return null;

	}
	public static void main(String[] args) throws Exception {
		//ContextUtil.loadConf();
		DCCMTODCS001 dcc = new DCCMTODCS001Impl();
		dcc.execute();
	}

}
