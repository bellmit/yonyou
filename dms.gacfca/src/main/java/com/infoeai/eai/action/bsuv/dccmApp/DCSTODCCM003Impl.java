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
import com.infoeai.eai.wsServer.DccmAppService.SalesConsultantVo;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.CommonUtils;

/**
 * @author Administrator
 * * 销售顾问数据获取接口 
 * DCS →CRM
 * 日/次
 */
@Service
public class DCSTODCCM003Impl extends BaseService implements DCSTODCCM003 {
	private static final Logger logger = LoggerFactory.getLogger(DCSTODCCM003Impl.class);
	
	@SuppressWarnings("unused")
	@Override
	public SalesConsultantVo[] getSalesConsultantList(String from, String to) throws Exception {
		SalesConsultantVo[] arrayVos=null;
		Integer  flag = OemDictCodeConstants.IF_TYPE_YES;
		Date startTime = new Date();
		String excString="";
		String exceptionMsg = "";
		Integer ifType=1;   // 默认成功
		Integer dataSize = 0;	// 数据数量
		
		//开启事务
		beginDbService();
		logger.info("============销售顾问数据获取接口：开始=========");
		logger.info("-----开始时间："+from+"-------结束时间："+to+"----------------");
		try {
			arrayVos = GetRelevantInformation(from,to);
			dataSize = arrayVos.length;
			dbService.endTxn(true);	// 提交事务
			logger.info("============销售顾问数据获取接口：成功======"+dataSize+":条==");
		} catch (Exception e) {
			e.printStackTrace();
			excString=CommonBSUV.getErrorInfoFromException(e);
			ifType=2;
			dbService.endTxn(false);//回滚事务
			logger.info("============销售顾问数据获取接口：异常=========" + e);
		} finally {
			Base.detach();
			dbService.clean();
			beginDbService();
			CommonBSUV.insertTtEcOrderHistory(this.getClass().getName(), "销售顾问数据获取接口：DCS->DCCM", startTime, dataSize, flag,
					exceptionMsg, "", "", new Date());
			dbService.endTxn(true);	
			Base.detach();
			dbService.clean();//清除事务
			logger.info("============销售顾问数据获取接口：结束=========");
		}
		return arrayVos;
	}

	/* (non-Javadoc)
	 * @see com.infoeai.eai.action.bsuv.dccmApp.DCSTODCCM003#GetRelevantInformation(java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public SalesConsultantVo[] GetRelevantInformation(String from, String to) throws UnsupportedEncodingException {
		SalesConsultantVo[] ps = null;
		StringBuffer sql = new StringBuffer("\n");
		sql.append(" SELECT \n");
		sql.append("    DNSP.USER_ID as UserID,   ##销售人员ID\n");
		sql.append("    ifnull(TC.LMS_CODE,'') as DealerCode,    ##经销商代码\n");
		sql.append("    ifnull(DNSP.USER_NAME,'') as UserName,    ##销售人员名称\n");
		sql.append("    ifnull(DNSP.ROLE_ID,'') as RoleID,    ##销售人员角色ID\n");
		sql.append("    ifnull(DNSP.EMAIL,'') as EMAIL,   ##销售人员邮箱\n");
		sql.append("    ifnull(DNSP.MOBILE,'') as Mobile,    ##销售人员手机\n");
		sql.append("    ifnull(DNSP.ROLE_NAME,'') as RoleName,   ##销售人员角色名称\n");
		sql.append("    ifnull(date_format(Max(DNSP.create_date),'%y-%m-%d %H:%i:%s'),'') as createDate,   ##创建时间 \n");
		sql.append("    USER_STATUS as UserStatus      ##销售人员状态\n");
		sql.append(" FROM TI_DMS_N_SALES_PERSONNEL_dcs DNSP  left join TI_DEALER_RELATION DR \n");
		sql.append("  on DR.dms_code=DNSP.dealer_code  \n");
		sql.append(" left join TM_COMPANY TC \n");
		sql.append("  on TC.company_code=DR.dcs_code \n");
		sql.append(" where 1=1  \n");
		sql.append("    and DNSP.MOBILE is not null \n");
		sql.append("    and DNSP.ROLE_ID is not null \n");
		sql.append("    and TC.LMS_CODE is not null \n");
		sql.append("    and DNSP.ROLE_NAME is not null \n");
		sql.append(" 	and (((unix_timestamp(DNSP.CREATE_DATE)>=unix_timestamp('"+from+"')) and (unix_timestamp(DNSP.CREATE_DATE)<=unix_timestamp('"+to+"'))) \n");
		sql.append(" 	 or ((unix_timestamp(DNSP.UPDATE_DATE)>=unix_timestamp('"+from+"')) and (unix_timestamp(DNSP.UPDATE_DATE)<=unix_timestamp('"+to+"'))))\n");
		sql.append(" 	group by DNSP.USER_ID,ifnull(TC.LMS_CODE,''),ifnull(DNSP.USER_NAME,''),ifnull(DNSP.ROLE_ID,'') , ifnull(DNSP.EMAIL,''),ifnull(DNSP.MOBILE,''),ifnull(DNSP.ROLE_NAME,''),USER_STATUS   \n");
		List<Map> list=OemDAOUtil.findAll(sql.toString(), null);
		if(null!= list && list.size()>0){
			ps = new SalesConsultantVo[list.size()];
			Map map = null;
			SalesConsultantVo vo = null;
			for (int i = 0; i < list.size(); i++) {
				map = list.get(i);
				vo = new SalesConsultantVo();
				
				vo.setUserID(CommonUtils.checkNull(map.get("User_ID")));
				vo.setDealerCode(CommonUtils.checkNull(map.get("Dealer_Code")));
				vo.setUserName(Base64.encode(CommonUtils.checkNull(map.get("User_Name")).getBytes("UTF-8")));
				vo.setRoleID(CommonUtils.checkNull(map.get("Role_ID")));
				vo.setEmail(CommonUtils.checkNull(map.get("EMAIL")));
				vo.setMobile(CommonUtils.checkNull(map.get("Mobile")));
				vo.setRoleName(Base64.encode(CommonUtils.checkNull(map.get("Role_Name")).getBytes("UTF-8")));
				vo.setUserStatus(Integer.parseInt(CommonUtils.checkNull(map.get("User_Status"),"0")));//车款状态
				vo.setCreateDate(CommonUtils.checkNull(map.get("create_Date")));
				ps[i] = vo;
			}
		}
		return ps;
	}

}
