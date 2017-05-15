/**
 * 
 */
package com.infoeai.eai.action.bsuv.dccmApp;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.axis.encoding.Base64;
import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.infoeai.eai.action.BaseService;
import com.infoeai.eai.action.bsuv.common.CommonBSUV;
import com.infoeai.eai.wsServer.DccmAppService.PotentialCustomerVo;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.CommonUtils;

/**
 * @author Administrator
 ** 潜客信息数据交互接口
 * DCS →CRM
 * 30M/次
 */
@Service
public class DCSTODCCM004Impl extends BaseService implements DCSTODCCM004 {
	private static final Logger logger = LoggerFactory.getLogger(DCSTODCCM004Impl.class);
	
	@SuppressWarnings("unused")
	@Override
	public PotentialCustomerVo[] getThePotentialCustomerList(String from, String to) throws Exception {
		PotentialCustomerVo[] arrayVos=null;
		Integer  flag = OemDictCodeConstants.IF_TYPE_YES;
		Date startTime = new Date();
		String excString="";
		String exceptionMsg = "";
		Integer ifType=1;   // 默认成功
		Integer dataSize = 0;	// 数据数量
		
		//开启事务
		beginDbService();
		logger.info("============潜客信息数据交互接口：开始=========");
		logger.info("-----开始时间："+from+"-------结束时间："+to+"----------------");
		try {
		arrayVos=GetRelevantInformation(from,to);
		dataSize = arrayVos.length;
		dbService.endTxn(true);	// 提交事务
		logger.info("============潜客信息数据交互接口：成功======"+dataSize+":条==");
		} catch (Exception e) {
			e.printStackTrace();
			excString=CommonBSUV.getErrorInfoFromException(e);
			ifType=2;
			dbService.endTxn(false);	// 回滚事务
			logger.info("============潜客信息数据交互接口：异常=========" + e);
		} finally {
			Base.detach();
			dbService.clean();
			beginDbService();
			CommonBSUV.insertTtEcOrderHistory(this.getClass().getName(), "潜客信息数据交互接口：DCS->DCCM", startTime, dataSize, flag,
					exceptionMsg, "", "", new Date());
			dbService.endTxn(true);	
			Base.detach();
			dbService.clean();	// 清理事务
			logger.info("============潜客信息数据交互接口：结束=========");
		}
		return arrayVos;
	}

	/* (non-Javadoc)
	 * @see com.infoeai.eai.action.bsuv.dccmApp.DCSTODCCM004#GetRelevantInformation(java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public PotentialCustomerVo[] GetRelevantInformation(String from, String to) throws UnsupportedEncodingException{
		PotentialCustomerVo[] ps = null;
		StringBuffer sql = new StringBuffer("\n");
		sql.append("     SELECT  \n");
		sql.append("		    ifnull(TNCI.UNIQUENESS_ID,'') as UniquenessID ,   ##DMS客户唯一ID\n");
		sql.append("		    ifnull(TNCI.CLIENT_TYPE,'10181001') as  ClientType ,   ##客户类型\n");
		sql.append("		    ifnull(TNCI.NAME,'') as  Name ,   ##客户的姓名\n");
		sql.append("		    ifnull(TNCI.GENDER,'') as  Gender ,  ##性别\n");
		sql.append("		    ifnull(TNCI.PHOME,'') as  Phone ,    ##手机号\n");
		sql.append("		    ifnull(TNCI.TELEPHONE,'') as  Telephone ,   ##固定电话\n");
		sql.append("		    ifnull((select LMS_ID from TM_REGION_dcs where region_code=TNCI.PROVINCE_ID limit 0,1),0) as  ProvinceID ,    ##省份ID\n");
		sql.append("		    ifnull((select LMS_ID from TM_REGION_dcs where region_code=TNCI.CITY_ID limit 0,1),0) as  CityID ,  ##城市ID\n");
		sql.append("		    ifnull(date_format(TNCI.BIRTHDAY,'yyyy-mm-dd HH:mm:ss'),'') as  Birthday ,   ##生日\n");
		sql.append("		    ifnull(TNCI.OPP_LEVEL_ID,'') as  OppLevelID , ##客户级别ID\n");
		sql.append("		    ifnull(TNCI.SOURCE_TYPE,'') as  SourceType ,  ##客户来源\n");
		sql.append("		    ifnull(TC.LMS_CODE,'') as  DealerCode ,  ##经销商代码\n");
		sql.append("		    ifnull(TNCI.DEALER_USER_ID,'') as  DealerUserID , ##销售人员的ID\n");
		sql.append("		    ifnull(TNCI.BUY_CAR_BUGGET,'') as  BuyCarBudget ,  ##购车预算\n");
		sql.append("		    ifnull((select tree_code from TM_VHCL_MATERIAL_GROUP where group_code = TNCI.BRAND_ID limit 0,1),'') as  BrandID ,  ##品牌ID\n");
		sql.append("		    ifnull((select tree_code from TM_VHCL_MATERIAL_GROUP where group_code = TNCI.MODEL_ID limit 0,1),'') as  ModelID ,   ##车型ID\n");
		sql.append("		 	ifnull((select tree_code from TM_VHCL_MATERIAL_GROUP where group_code = TNCI.CAR_STYLE_ID limit 0,1),'') as  CarStyleID ,      ##车款ID\n");
		sql.append("		    ifnull(TNCI.INTENT_CAR_COLOR,'') as  IntentCarColor ,  ##车辆颜色ID\n");
		sql.append("		    ifnull(TNCI.BUY_CARCONDITION,0) as  BuyCarcondition ,  ##购车类型\n");
		sql.append("		    ifnull(TNCI.SECOND_SOURCE_TYPE,'') as  SecondSourceType ,  ##客户二级来源\n");
		sql.append("		    ifnull(date_format(TNCI.CREATE_DATE,'%y-%m-%d %H:%i:%s'),'') as  CreateDate,  ##创建时间\n");
		sql.append("		    ifnull(date_format(TNCI.UPDATE_DATE,'%y-%m-%d %H:%i:%s'),'') as  UpdateDate, ##更新时间\n");
		sql.append("		    ifnull(TNCI.IS_TO_SHOP,'') as IsToShop, ##是否到店\n");
		sql.append("		    ifnull(date_format(TNCI.TIME_TO_SHOP,'%y-%m-%d %H:%i:%s'),'') as  TimeToShope ##到店时间\n");
		sql.append("		 	FROM TI_DMS_N_CUSTOMER_INFO_dcs TNCI left join TI_DEALER_RELATION DR on DR.dms_code=TNCI.dealer_code \n");
		sql.append("		    left join TM_COMPANY TC on TC.company_code=DR.dcs_code where 1=1  \n");
		sql.append("			and (((unix_timestamp(TNCI.CREATE_DATE) >= unix_timestamp('"+from+"')) and (unix_timestamp(TNCI.CREATE_DATE)<= unix_timestamp('"+to+"') )) \n");
		sql.append("   			or ((unix_timestamp(TNCI.UPDATE_DATE)>= unix_timestamp('"+from+"'))and (unix_timestamp(TNCI.UPDATE_DATE)<= unix_timestamp('"+to+"') )))  \n");
		List<Map> list=OemDAOUtil.findAll(sql.toString(), null);
		if(null!= list && list.size()>0){
			ps = new PotentialCustomerVo[list.size()];
			Map map = null;
			PotentialCustomerVo vo = null;
			for (int i = 0; i < list.size(); i++) {
				map = list.get(i);
				vo=new PotentialCustomerVo();
				
				vo.setBirthday(CommonUtils.checkNull(map.get("Birthday")));
				vo.setBrandID(CommonUtils.checkNull(map.get("Brand_ID")));
				vo.setBuyCarBudget(CommonUtils.checkNull(map.get("Buy_CarBudget")));
				vo.setBuyCarcondition(Integer.parseInt(CommonUtils.checkNull(map.get("Buy_Carcondition"),"0")));
				vo.setCityID(Integer.parseInt(CommonUtils.checkNull(map.get("City_ID"),"0")));
				vo.setClientType(CommonUtils.checkNull(map.get("Client_Type")));
				vo.setCreateDate(CommonUtils.checkNull(map.get("Create_Date")));
				vo.setDealerCode(CommonUtils.checkNull(map.get("Dealer_Code")));
				vo.setDealerUserID(CommonUtils.checkNull(map.get("Dealer_User_ID")));
				vo.setGender(CommonUtils.checkNull(map.get("Gender")));
				vo.setModelID(CommonUtils.checkNull(map.get("Model_ID")));
				vo.setIntentCarColor(CommonUtils.checkNull(map.get("Intent_Car_Color")));
				vo.setName(Base64.encode(CommonUtils.checkNull(map.get("Name")).getBytes("UTF-8")));
				vo.setOppLevelID(CommonUtils.checkNull(map.get("Opp_Level_ID")));
				vo.setPhone(CommonUtils.checkNull(map.get("Phone")));
				vo.setProvinceID(Integer.parseInt(CommonUtils.checkNull(map.get("Province_ID"),"0")));
				vo.setSecondSourceType(CommonUtils.checkNull(map.get("Second_Source_Type")));
				vo.setSourceType(CommonUtils.checkNull(map.get("Source_Type")));
				vo.setTelephone(CommonUtils.checkNull(map.get("Telephone")));
				vo.setTestCarStyleID(CommonUtils.checkNull(map.get("CarStyle_ID")));
				vo.setUniquenessID(CommonUtils.checkNull(map.get("Uniqueness_ID")));
				vo.setUpdateDate(CommonUtils.checkNull(map.get("Update_Date")));
				vo.setIsToShop(CommonUtils.checkNull(map.get("Is_To_Shop")));
				vo.setTimeToShop(CommonUtils.checkNull(map.get("Time_To_Shope")));
				ps[i] = vo;
			}
		}
		
		return ps;
	}

}
