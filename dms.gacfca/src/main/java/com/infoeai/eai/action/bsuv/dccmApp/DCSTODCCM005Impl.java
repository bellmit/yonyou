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
import com.infoeai.eai.wsServer.DccmAppService.CustomerReceptionVo;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.CommonUtils;

/**
 * @author Administrator
 *客户接待/沟通记录数据交互接口(DCS->DCCM)
 */
@Service
public class DCSTODCCM005Impl extends BaseService implements DCSTODCCM005 {
	private static final Logger logger = LoggerFactory.getLogger(DCSTODCCM005Impl.class);
	@Override
	public CustomerReceptionVo[] getCustomerReceptionList(String from, String to) throws Exception {
		logger.info("====客户接待/沟通记录数据交互接口开始====");
		Date startTime = new Date();
		CustomerReceptionVo[] customerReceptionVoList = null; 
		String exceptionMsg = "";
		Integer  flag = OemDictCodeConstants.IF_TYPE_YES;
		int dataSize = 0;
		try {
			//开启事务
			beginDbService();
			//获取要发送数据
			logger.info("====开始时间:"+from+",结束时间:"+to+"====");
			customerReceptionVoList = getList(from,to);
			dataSize = customerReceptionVoList.length;
			dbService.endTxn(true);	// 提交事务
			logger.info("====客户接待/沟通记录数据交互接口成功====共"+dataSize+"条数据====");
		} catch(Exception e) {
			exceptionMsg = CommonBSUV.getErrorInfoFromException(e);
			flag = OemDictCodeConstants.IF_TYPE_NO;
			logger.info("====客户接待/沟通记录数据交互接口异常====");
			logger.error(e.getMessage(), e);
			dbService.endTxn(false);
		}finally{
			Base.detach();
			dbService.clean();
			beginDbService();
			CommonBSUV.insertTtEcOrderHistory(this.getClass().getName(), "客户接待/沟通记录数据交互接口：DCS->DCCM", startTime, dataSize, flag,
					exceptionMsg, "", "", new Date());
			dbService.endTxn(true);	
			Base.detach();
			logger.info("====客户接待/沟通记录数据交互接口结束====");
			dbService.clean();
		}
		return customerReceptionVoList;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public CustomerReceptionVo[] getList(String from, String to) throws UnsupportedEncodingException {
		CustomerReceptionVo[] ps = null;
		StringBuffer sql = new StringBuffer("\n");
		sql.append("  SELECT TDNC.UNIQUENESS_ID UNIQUENESSID,##DMS客户唯一ID \n");
		sql.append("         ifnull(date_format(TDNC.COMM_DATE,'%y-%m-%d %H:%i:%s'),'') COMMDATA, ##沟通日期 \n");
		sql.append("         ifnull(TDNC.COMM_TYPE,'') COMMTYPE, ##沟通方式 \n");
		sql.append("         ifnull(TDNC.COMM_CONTENT,'') COMMCONTENT, ##沟通内容 \n");
		sql.append("         ifnull(TDNC.FOLLOW_OPP_LEVEL_ID,'') FOLLOWOPPLEVELID,##跟进后客户级别 \n");
		sql.append("         ifnull(date_format(TDNC.NEXT_COMM_DATE,'%y-%m-%d %H:%i:%s'),'') NEXTCOMMDATE, ##下次沟通日期 \n");
		sql.append("         ifnull(TDNC.NEXT_COMM_CONTENT,'') NEXTCOMMCONTENT,##下次沟通内容 \n");
		sql.append("         ifnull(TDUCS.GIVE_UP_TYPE,'') DORMANTTYPE, ##休眠类型 \n");
		sql.append("         ifnull(date_format(TDUCS.GIVE_UP_DATE,'%y-%m-%d %H:%i:%s'),'') GIVEUPDATE, ##放弃、休眠时间 \n");
		sql.append("         ifnull(TDUCS.GIVE_UP_REASON,'') GIVEUPREASON, ##放弃原因 \n");
		sql.append("         ifnull(TC.LMS_CODE,'') DEALERCODE, ##经销商代码 \n");
		sql.append("         ifnull(date_format(TDNC.CREATE_DATE,'%y-%m-%d %H:%i:%s'),'') CREATEDATE, ##创建时间 \n");
		sql.append("         ifnull(date_format(TDNC.UPDATE_DATE,'%y-%m-%d %H:%i:%s'),'') UPDATEDATE ##更新时间 \n");
		sql.append("    FROM TI_DMS_N_CULTIVATE_dcs TDNC \n");
		sql.append("    LEFT JOIN TI_DMS_U_CUSTOMER_STATUS TDUCS ON TDNC.UNIQUENESS_ID = TDUCS.UNIQUENESS_ID AND TDNC.FCA_ID = TDUCS.FCA_ID \n");
		sql.append("    LEFT JOIN TI_DEALER_RELATION DR ON DR.DMS_CODE = TDNC.DEALER_CODE  \n");
		sql.append("    LEFT JOIN TM_COMPANY TC ON TC.COMPANY_CODE = DR.DCS_CODE \n");
		sql.append("   WHERE 1=1 ##AND TDNC.IS_SEND='0' \n");
		sql.append("     AND ((unix_timestamp(TDNC.CREATE_DATE) >= unix_timestamp('"+from+"') AND unix_timestamp(TDNC.CREATE_DATE) <= unix_timestamp('"+to+"')) OR (unix_timestamp(TDNC.UPDATE_DATE) >= unix_timestamp('"+from+"') AND unix_timestamp(TDNC.CREATE_DATE) <= unix_timestamp('"+to+"'))) \n");
		List<Map> list=OemDAOUtil.findAll(sql.toString(), null);
		if(null!= list && list.size()>0){
			ps = new CustomerReceptionVo[list.size()];
			Map map = null;
			CustomerReceptionVo vo = null;
			for (int i = 0; i < list.size(); i++) {
				 map = list.get(i);
				 vo=new CustomerReceptionVo();
				 
				 vo.setUniquenessID(CommonUtils.checkNull(map.get("UNIQUENESS_ID")));//DMS客户唯一ID
	        	 vo.setCommData(CommonUtils.checkNull(map.get("COMM_DATE")));//沟通日期
	        	 vo.setCommType(CommonUtils.checkNull(map.get("COMM_TYPE")));//沟通方式
	        	 vo.setCommContent(Base64.encode(CommonUtils.checkNull(map.get("COMM_CONTENT")).getBytes("UTF-8")));//沟通内容
	        	 vo.setFollowOppLevelID(CommonUtils.checkNull(map.get("FOLLOW_OPP_LEVEL_ID")));//跟进后客户级别
	        	 vo.setNextCommDate(CommonUtils.checkNull(map.get("NEXT_COMM_DATE")));//下次沟通日期
	        	 vo.setNextCommContent(Base64.encode(CommonUtils.checkNull(map.get("NEXT_COMM_CONTENT")).getBytes("UTF-8")));//下次沟通内容
	        	 vo.setDormantType(CommonUtils.checkNull(map.get("DORMANT_TYPE")));//休眠类型
	        	 vo.setGiveUpDate(CommonUtils.checkNull(map.get("GIVE_UP_DATE")));//放弃、休眠时间
	        	 vo.setGiveUpReason(CommonUtils.checkNull(map.get("GIVE_UP_REASON")));//放弃原因
	        	 vo.setDealerCode(CommonUtils.checkNull(map.get("DEALER_CODE")));//经销商代码
	        	 vo.setCreateDate(CommonUtils.checkNull(map.get("CREATE_DATE")));//创建时间
	        	 vo.setUpdateDate(CommonUtils.checkNull(map.get("UPDATE_DATE")));//更新时间
	        	 ps[i] = vo;
			}
		}
		return ps;
	}

}
