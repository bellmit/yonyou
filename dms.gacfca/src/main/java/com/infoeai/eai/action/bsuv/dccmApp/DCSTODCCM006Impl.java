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
import com.infoeai.eai.wsServer.DccmAppService.CustomerStatusVo;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.CommonUtils;

/**
 * @author Administrator
 *休眠，订单，交车客户状态数据传递(DCS->DCCM)
 */
@Service
public class DCSTODCCM006Impl extends BaseService implements DCSTODCCM006 {
	private static final Logger logger = LoggerFactory.getLogger(DCSTODCCM006Impl.class);
	@Override
	public CustomerStatusVo[] getCustomerStatusList(String from, String to) throws Exception {
		logger.info("====休眠，订单，交车客户状态数据传递开始====");
		Date startTime = new Date();
		CustomerStatusVo[] customerStatusVoList = null; 
		String exceptionMsg = "";
		Integer  flag = OemDictCodeConstants.IF_TYPE_YES;
		int dataSize = 0;
		try {
			//开启事务
			beginDbService();
			//获取要发送数据
			logger.info("====开始时间:"+from+",结束时间:"+to+"====");
			customerStatusVoList = getList(from,to);
			dataSize = customerStatusVoList.length;
			dbService.endTxn(true);
			logger.info("====休眠，订单，交车客户状态数据传递成功====共"+dataSize+"条数据====");
		} catch(Exception e) {
			exceptionMsg = CommonBSUV.getErrorInfoFromException(e);
			flag = OemDictCodeConstants.IF_TYPE_NO;
			logger.info("====休眠，订单，交车客户状态数据传递异常====");
			logger.error(e.getMessage(), e);
			dbService.endTxn(false);
		}finally{
			Base.detach();
			dbService.clean();
			beginDbService();
			CommonBSUV.insertTtEcOrderHistory(this.getClass().getName(), "休眠，订单，交车客户状态数据传递：DCS->DCCM", startTime, dataSize, flag,
					exceptionMsg, "", "", new Date());
			dbService.endTxn(true);	
			Base.detach();
			logger.info("====休眠，订单，交车客户状态数据传递结束====");
			dbService.clean();
		}
		return customerStatusVoList;
	}

	@Override
	public CustomerStatusVo[] getList(String from, String to) throws UnsupportedEncodingException {
		CustomerStatusVo[] ps = null;
		StringBuffer sql = new StringBuffer("\n");
		sql.append("  SELECT  TDUC.UNIQUENESS_ID UNIQUENESSID, ##DMS客户唯一ID \n");
		sql.append("          ifnull(TDUC.FCA_ID,0) FCAID, ##售中APP的客户ID \n");
		sql.append("          ifnull(TDUC.OPP_LEVEL_ID,'') OPPLEVELID, ##跟进后客户级别 \n");
		sql.append("          ifnull(TDUC.GIVE_UP_TYPE,'') GIVEUPTYPE, ##休眠类型 \n");
		sql.append("          ifnull(TDUC.COMPARE_CAR,'') COMPARECAR, ##竞品车辆 \n");
		sql.append("          ifnull(TDUC.GIVE_UP_REASON,'') GIVEUPREASON, ##休眠原因 \n");
		sql.append("          ifnull(date_format(TDUC.GIVE_UP_DATE,'%y-%m-%d %H:%i:%s'),'') GIVEUPDATE, ##休眠时间 \n");
		sql.append("          ifnull(date_format(TDUC.ORDER_DATE,'%y-%m-%d %H:%i:%s'),'') ORDERDATE,##订单时间 \n");
		sql.append("          ifnull(TC.LMS_CODE,'') DEALERCODE, ##经销商代码 \n");
		sql.append("          ifnull(date_format(TDUC.BUY_CAR_DATE,'%y-%m-%d %H:%i:%s'),'') BUYCARDATE,##交车时间 \n");
		sql.append("          ifnull(date_format(TDUC.UPDATE_DATE,'%y-%m-%d %H:%i:%s'),'') UPDATEDATE ##状态更新时间 \n");
		sql.append("     FROM TI_DMS_U_CUSTOMER_STATUS  TDUC \n");
		sql.append("     LEFT JOIN TI_DEALER_RELATION DR ON DR.DMS_CODE = TDUC.DEALER_CODE  \n");
		sql.append("     LEFT JOIN TM_COMPANY TC ON TC.COMPANY_CODE = DR.DCS_CODE \n");
		sql.append("    WHERE 1=1 ##AND TDUC.IS_SEND='0' \n");
		sql.append("      AND ((unix_timestamp(TDUC.CREATE_DATE) >= unix_timestamp('"+from+"') AND unix_timestamp(TDUC.CREATE_DATE) <= unix_timestamp('"+to+"')) OR (unix_timestamp(TDUC.UPDATE_DATE) >= unix_timestamp('"+from+"') AND unix_timestamp(TDUC.CREATE_DATE) <= unix_timestamp('"+to+"'))) \n");
		List<Map> list=OemDAOUtil.findAll(sql.toString(), null);
		if(null!= list && list.size()>0){
			ps = new CustomerStatusVo[list.size()];
			Map map = null;
			CustomerStatusVo vo= null;
			for (int i = 0; i < ps.length; i++) {
				 map = list.get(i);
				 vo=new CustomerStatusVo();
				 
				 vo.setUniquenessID(CommonUtils.checkNull(map.get("UNIQUENESS_ID")));//DMS客户唯一ID
	        	 vo.setFcaID(CommonUtils.checkNull(map.get("FCA_ID")));//售中APP的客户ID
	        	 vo.setOppLevelID(CommonUtils.checkNull(map.get("OPP_LEVEL_ID")));//跟进后客户级别
	        	 vo.setGiveUpType(CommonUtils.checkNull(map.get("GIVE_UP_TYPE")));//休眠类型
	        	 vo.setCompareCar(CommonUtils.checkNull(map.get("COMPARE_CAR")));//竞品车辆
	        	 vo.setGiveUpReason(Base64.encode(CommonUtils.checkNull(map.get("GIVE_UP_REASON")).getBytes("UTF-8")));//休眠原因
	        	 vo.setGiveUpDate(CommonUtils.checkNull(map.get("GIVE_UP_DATE")));//休眠时间
	        	 vo.setOrderDate(CommonUtils.checkNull(map.get("ORDER_DATE")));//订单时间
	        	 vo.setDealerCode(CommonUtils.checkNull(map.get("DEALER_CODE")));//经销商代码
	        	 vo.setBuyCarDate(CommonUtils.checkNull(map.get("BUY_CAR_DATE")));//交车时间
	        	 vo.setUpdateDate(CommonUtils.checkNull(map.get("UP_DATE_DATE")));//状态更新时间
	        	 ps[i] = vo;
			}
		}
		
		return ps;
	}

}
