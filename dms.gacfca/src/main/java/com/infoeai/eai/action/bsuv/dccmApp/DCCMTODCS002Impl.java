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
import com.yonyou.dms.common.domains.PO.basedata.TiAppNCultivatePO;
import com.yonyou.dms.common.domains.PO.basedata.TiDmsNCultivatePO;
import com.yonyou.dms.common.domains.PO.basedata.TiDmsUCustomerStatusPO;
import com.yonyou.dms.function.utils.common.CommonUtils;

/**
 * @author Administrator
 * 功能描述  客户接待/沟通记录数据交互接口
 * 传输方向 DCCM->DCS
 */
@Service
public class DCCMTODCS002Impl extends BaseService implements DCCMTODCS002 {
	private static final Logger logger = LoggerFactory.getLogger(DCCMTODCS002Impl.class);
	private LonPropertiesLoad lonLeader=LonPropertiesLoad.getInstance();
	@Override
	public String execute() throws Exception {
		try {
			logger.info("================客户接待/沟通记录数据交互接口(客户端)========开始=========");
			//开启事务
			dbService();
			Calendar beforeTime = Calendar.getInstance();
			beforeTime.add(Calendar.MINUTE, -35);// 35分钟之前的时间
			Date beforeD = beforeTime.getTime();
			
			Calendar endTime = Calendar.getInstance();
			endTime.add(Calendar.MINUTE, 0);//当前时间
			Date endD = endTime.getTime();
			
			SimpleDateFormat formate = new SimpleDateFormat("yyyyMMddHHmmss");
			String from = formate.format(beforeD);
			String to = formate.format(endD);
			
			String URL=lonLeader.getValue("DCCM_TO_DCS_002");
			JSONObject json = HttpConnectionManager.httpsGet(URL+from+"/"+to);
			if(Utility.testIsNotNull(Utility.checkNull(json))){
				logger.info("json中数组Size:" + json);
				JSONArray date = json.getJSONArray("Data");
				int iSize1 = date.length();  
				logger.info("json中数组Size:" + iSize1);  
			    for (int i = 0; i < iSize1; i++) {  
			    	json= date.getJSONObject(i);
			    	logger.info("================获取客户接待/沟通记录数据详细必填项====================");
			    	logger.info("[" + i + "]uniquenessID=" + CommonUtils.checkNull(json.get("uniquenessID")));
			    	logger.info("[" + i + "]commData=" + CommonUtils.checkNull(json.get("commData")));
			    	logger.info("[" + i + "]commType=" + CommonUtils.checkNull(json.get("commType")));
			    	logger.info("[" + i + "]followOppLevelID=" + CommonUtils.checkNull(json.get("followOppLevelID")));
			    	logger.info("[" + i + "]nextCommDate=" + CommonUtils.checkNull(json.get("nextCommDate")));
			    	logger.info("[" + i + "]giveUpDate=" + CommonUtils.checkNull(json.get("giveUpDate")));
			    	logger.info("[" + i + "]giveUpReason=" + CommonUtils.checkNull(json.get("giveUpReason")));
			    	logger.info("[" + i + "]dealerCode=" + CommonUtils.checkNull(json.get("dealerCode")));
			    	logger.info("[" + i + "]createDate=" + CommonUtils.checkNull(json.get("createDate")));
			    	logger.info("[" + i + "]updateDate=" + CommonUtils.checkNull(json.get("updateDate")));
			    	
					//客户接待/沟通记录主表
					TiAppNCultivatePO infoPo = new TiAppNCultivatePO();
					infoPo.setString("Uniqueness_Id",CommonUtils.checkNull(json.get("uniquenessID")));//DMS客户唯一ID
					String commData = CommonUtils.checkNull(json.get("commData"));
					if(Utility.testIsNotNull(commData)){
						infoPo.setTimestamp("Comm_Date",DateTimeUtil.getStringFormat(commData));//沟通日期（YYYY-MM-DD HH:mm:ss）
					}
					infoPo.setString("Comm_Type",CommonUtils.checkNull(json.get("commType")));//沟通方式
					
					String commContent = CommonUtils.checkNull(json.get("commContent"));
					if(Utility.testIsNotNull(commContent)){//沟通内容（Base64转码） 非必填
						infoPo.setString("Comm_Content",new String(Base64.decode(new String(CommonUtils.checkNull(json.get("commContent")).getBytes("UTF-8"),"UTF-8"))));
					}
					infoPo.setString("Follow_Opp_Level_Id",CommonUtils.checkNull(json.get("followOppLevelID")));//跟进后客户级别
					String nextCommDate = CommonUtils.checkNull(json.get("nextCommDate"));
					if(Utility.testIsNotNull(nextCommDate)){
						infoPo.setTimestamp("Next_Comm_Date",DateTimeUtil.getStringFormat(nextCommDate));//下次沟通日期
					}
					
					String nextCommContent = CommonUtils.checkNull(json.get("nextCommContent"));
					if(Utility.testIsNotNull(nextCommContent)){//下次沟通内容
						infoPo.setString("Next_Comm_Content",new String(Base64.decode(new String(CommonUtils.checkNull(json.get("nextCommContent")).getBytes("UTF-8"),"UTF-8"))));
					}
					infoPo.setString("DORMANT_TYPE",CommonUtils.checkNull(json.get("dormantType")));//休眠类型
					String giveUpDate = CommonUtils.checkNull(json.get("giveUpDate"));
					if(Utility.testIsNotNull(giveUpDate)){
						infoPo.setTimestamp("GIVE_UP_DATE",DateTimeUtil.getStringFormat(giveUpDate));//放弃、休眠时间
					}
					infoPo.setString("GIVE_UP_REASON",CommonUtils.checkNull(json.get("giveUpReason")));//放弃原因
					String followUpPersonId = CommonUtils.checkNull(json.get("followUpPersonId"));
					infoPo.setString("DEALER_USER_ID",followUpPersonId);//销售顾问ID
					infoPo.setString("Dealer_Code",CommonUtils.checkNull(json.get("dealerCode")));
					infoPo.setLong("FCA_ID",CommonUtils.checkNull(json.get("dccmId")));//DCCM唯一id
					
					String updateDate = CommonUtils.checkNull(json.get("updateDate"));
					if(Utility.testIsNotNull(updateDate)){
						infoPo.setTimestamp("UPDATE_DATE",DateTimeUtil.getStringFormat(updateDate));
					}
					infoPo.setLong("UPDATE_BY", 22222L);
					String createDate = CommonUtils.checkNull(json.get("createDate"));
					if(Utility.testIsNotNull(createDate)){
						infoPo.setTimestamp("CREATE_DATE",DateTimeUtil.getStringFormat(createDate));
					}
					infoPo.setLong("CREATE_BY",22222L);
					infoPo.saveIt();
			    }		
			}	
		    dbService.endTxn(true);
			logger.info("================客户接待/沟通记录数据交互接口(客户端)========成功=========");
		} catch (Exception e) {
			e.printStackTrace();
			dbService.endTxn(false);	// 回滚事务
			logger.info("================客户接待/沟通记录数据交互接口(客户端)========异常=========" + e);
		} finally {
			dbService.clean();	// 清理事务
			logger.info("================客户接待/沟通记录数据交互接口(客户端)========结束=========");
		}
		return null;
	}
	
	public static void main(String[] args) throws Exception {
		//ContextUtil.loadConf();
		DCCMTODCS002 dcc = new DCCMTODCS002Impl();
		dcc.execute();
	}

}
