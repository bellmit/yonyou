package com.yonyou.dms.vehicle.dao.oldPart;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.yonyou.dms.common.domains.DTO.basedata.TtOpOldpartPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.vehicle.domains.PO.oldPart.TtOpReturnBillPO;

/**
 * 旧件回运管理
 * @author Administrator
 *
 */
@Repository
public class OldPartReturnManageDao  extends OemBaseDAO{
	
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);

	public PageInfoDto findReturnManage(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam, params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;
	}

	private String getQuerySql(Map<String, String> queryParam, List<Object> params) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		StringBuffer sql = new StringBuffer();
		sql.append("select distinct TORB.RETURN_BILL_NO  #回运单号	\n");
		sql.append("	  ,TORB.DEALER_CODE#经销商代码	\n");
		sql.append("	  ,TORB.DEALER_NAME#经销商名称	\n");
		sql.append("	  ,DATE_FORMAT(TORB.BILL_DATE,'%Y-%m-%d') BILL_DATE #建单日期	\n");
		sql.append("	  ,DATE_FORMAT(too.DESPATCH_DATE,'%Y-%m-%d') DESPATCH_DATE  #发运日期	\n");
		sql.append("	  ,TORB.OLDPART_TOTAL #回运旧件数量	\n");
		sql.append("	  ,TORB.RECEPTION_TOTAL #接收数量	\n");
		sql.append("	  ,IFNULL(TORB.REPULSE_TOTAL,0) REPULSE_TOTAL #拒绝数量	\n");
		sql.append("	  ,TORB.CARRIAGE  #承运商	\n");
		sql.append("	  ,TORB.FREIGHT_NO  #货运单号	\n");
		sql.append("	  ,TORB.RETURNADDR_ID	\n");
		sql.append("	  ,TORA.RETURNADDR_NAME #返还地	\n");
		sql.append("	  ,tora.RETURNADDR_CODE #返还地code	\n");
		sql.append("	  ,TORB.RETURN_BILL_STATUS #处理状态	\n");
		sql.append("	from TT_OP_RETURN_BILL_DCS TORB#回运单表	\n");
		sql.append("	  LEFT JOIN TT_OP_OLDPART_DCS too ON TOO.RETURN_BILL_NO = TORB.RETURN_BILL_NO#旧件清单表	\n");
		sql.append("	  LEFT JOIN TM_OLDPART_RETURNADDR_DCS TORA ON TORB.RETURNADDR_ID = TORA.RETURNADDR_ID#旧件返还地维护表	\n");
//		sql.append("	  where too.OEM_COMPANY_ID="+loginInfo.getOemCompanyId()+"	\n");
		sql.append("	  where 1=1	\n");
		if (!StringUtils.isNullOrEmpty(queryParam.get("carriage"))) {
			sql.append("   AND torb.CARRIAGE =?");
			params.add(queryParam.get("carriage"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("returnBillNo"))) {
			sql.append("   AND too.RETURN_BILL_NO like(?)");
			params.add("%"+queryParam.get("returnBillNo")+"%");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("despatchStartDate"))) {
			sql.append("   AND DATE((too.DESPATCH_DATE) >= ? \n");
			params.add(queryParam.get("despatchStartDate"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("despatchEndDate"))) {
			sql.append("   AND DATE((too.DESPATCH_DATE) <= ? \n");
			params.add(queryParam.get("despatchEndDate"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("billStartDate"))) {
			sql.append("   AND DATE((too.BILL_DATE) >= ? \n");
			params.add(queryParam.get("billStartDate"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("billEndDate"))) {
			sql.append("   AND DATE((too.BILL_DATE) <= ? \n");
			params.add(queryParam.get("billEndDate"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("returnBillStatus"))) {
			sql.append("   AND TORB.RETURN_BILL_STATUS =?");
			params.add(queryParam.get("returnBillStatus"));
		}
		System.out.println(sql.toString());
		return sql.toString();
	}
	/**
	 * 根据回运单ID查询回运单信息
	 * @param oldPartId
	 * @param companyId
	 * @return
	 */
	public  Map<String,Object> getReturnBillList(String returnBillNo){
		StringBuffer sql = new StringBuffer();
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		sql.append("select TORB.RETURN_BILL_ID	\n");
		sql.append("	  ,TORB.DEALER_CODE,TORB.DEALER_NAME	\n");
		sql.append("	  ,TORB.CARRIAGE#承运商	\n");
		sql.append("	  ,TORB.FREIGHT_NO#货运单号	\n");
		sql.append("	  ,TORB.RETURN_BILL_NO  #回运单号	\n");
		sql.append("	  ,DATE_FORMAT(TORB.BILL_DATE,'%Y-%m-%d') BILL_DATE #建单日期	\n");
		sql.append("	  ,TORB.OLDPART_TOTAL #回运旧件数量	\n");
		sql.append("	  ,TORB.RECEPTION_TOTAL #接收数量	\n");
		sql.append("	  ,TORB.REPULSE_TOTAL #拒绝数量	\n");
		sql.append("	  ,TORB.RETURNADDR_ID	\n");
		sql.append("	  ,TORA.RETURNADDR_NAME #返还地	\n");
		sql.append("	  ,TORA.RETURNADDR_CODE #返还地code	\n");
		sql.append("	  ,TORB.TRANSPORT_COSTS #运输费用	\n");
		sql.append("	  ,TORB.RETURN_BILL_STATUS #处理状态	\n");
		sql.append("	  ,TORB.REMARK #备注	\n");
		sql.append("	from TT_OP_RETURN_BILL_DCS TORB	#回运单表	\n");
		sql.append("	  LEFT JOIN TM_OLDPART_RETURNADDR_DCS TORA ON TORB.RETURNADDR_ID = TORA.RETURNADDR_ID	#旧件返还地维护表	\n");
	//	sql.append("	  where TORB.OEM_COMPANY_ID="+loginInfo.getOemCompanyId()+"	\n");
		sql.append("	  where 1=1	\n");
    	if (!StringUtils.isNullOrEmpty(returnBillNo)) {
    		  sql.append(" 	and TORB.RETURN_BILL_NO = '"+returnBillNo+"'");
		}
		List<Map> list = OemDAOUtil.findAll(sql.toString(),null);
		return list.get(0);
	}
	/**
	 * 查询旧件清单通过清单ID
	 * @param oldPartIds
	 * @return
	 */
	public PageInfoDto findOldPartById(String returnBillNo) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT \n");
		sql.append("	VM.MODEL_CODE, \n");
		sql.append("	VM.BRAND_CODE,           \n");
		sql.append("	VM.SERIES_CODE,          \n");
		sql.append("	VM.MODEL_CODE,           \n");
		sql.append("	TOO.DEALER_CODE,         \n");
		sql.append("    TOO.DEALER_NAME,          \n");
		sql.append("    TOO.OLDPART_ID,            \n");
		sql.append(" 	TOO.OLDPART_NO,            \n");
		sql.append(" 	TOO.OLDPART_NAME,          \n");
		sql.append(" 	TOO.OLDPART_ORDER,          \n");
		sql.append(" 	TOO.OLDPART_STATUS,        \n");
		sql.append(" 	TOO.OLDPART_TYPE,          \n");
		sql.append(" 	TOO.RETURN_BILL_TYPE,     \n");
		sql.append(" 	TOO.BALANCE_NO,           \n");
		sql.append(" 	TOO.BALLANCE_TIME,       \n");
		sql.append(" 	TOO.REPAIR_NO,            \n");
		sql.append(" 	TOO.CLAIM_NUMBER,TOO.VIN,TOO.PART_FEE,TOO.RETURN_BILL_NO,TOO.OUT_MILEAGE, \n");
		sql.append(" 	TOO.MSV,DATE_FORMAT(TOO.REPAIR_DATE,'%Y-%m-%d') REPAIR_DATE,  \n");
		sql.append("	DATE_FORMAT(TM.PURCHASE_DATE,'%Y-%m-%d') PURCHASE_DATE, \n");
		sql.append(" 	DATE_FORMAT(TOO.CLAIM_APPLY_DATE,'%Y-%m-%d') CLAIM_APPLY_DATE,	\n");
		sql.append(" 	DATE_FORMAT(TOO.CLAIM_AUDIT_DATE,'%Y-%m-%d') CLAIM_AUDIT_DATE,TOO.REPAIR_REMARK,TOO.REPAIR_TYPE \n");
		sql.append("		FROM TT_OP_OLDPART_DCS TOO,TM_VEHICLE_DEC TM,("+getVwMaterialSql()+") VM \n");
		sql.append(" 			WHERE 1=1 \n");
		sql.append(" 				AND TOO.VIN = TM.VIN \n");
		sql.append("				AND TM.MATERIAL_ID = VM.MATERIAL_ID \n");
        sql.append(" 		AND TOO.RETURN_BILL_NO = '"+returnBillNo+"'");
		PageInfoDto ps = OemDAOUtil.pageQuery(sql.toString(),null);
		return ps;
		
	}

	/**
	 * 根据回运单号删除
	 * 
	 * @param id
	 * @throws ServiceBizException
	 */
	public void deleteChargeById(String returnBillNo) throws ServiceBizException {
		TtOpReturnBillPO po = TtOpReturnBillPO.findFirst("Return_Bill_No=?",returnBillNo);
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		po.set("Is_Del",1);
		po.set("Update_By",loginInfo.getUserId());
		po.set("Update_Date",new Date());
		po.saveIt();
		StringBuffer sql = new StringBuffer();
		sql.append("select OLDPART_ID from TT_OP_OLDPART_DCS too	\n");
		sql.append("	where too.OEM_COMPANY_ID='"+loginInfo.getOemCompanyId()+"'	\n");
    	sql.append("		and too.RETURN_BILL_NO='"+returnBillNo+"'	\n");
		List<Map> oldPartIdList = OemDAOUtil.findAll(sql.toString(), null);
		if(oldPartIdList != null && oldPartIdList.size() > 0){
			String oldPartIds = "";
			for (int i = 0; i < oldPartIdList.size(); i++) {
				BigDecimal temp = (BigDecimal) oldPartIdList.get(i).get("OLDPART_ID");
				oldPartIds += temp;
				if (i != oldPartIdList.size() - 1) {
					oldPartIds += ",";
				}
			}		
			String[] oldPartId = oldPartIds.split(",");
			for(int i=0;i<oldPartId.length;i++){
				TtOpOldpartPO tooValue = TtOpOldpartPO.findFirst("Oldpart_Id=?",new Long(oldPartId[i]));
				tooValue.set("Return_Bill_No","");
				tooValue.set("Update_By",loginInfo.getUserId());
				tooValue.set("Update_Date",new Date());
				tooValue.saveIt();
			}
		}
	}
	/**
	 * 发运
	 * @param queryParams
	 */
		public void queryDealerPass(Map<String, String> queryParams)  {
			String id = queryParams.get("returnBillNo");
			String[] ids = null;
			if(!StringUtils.isNullOrEmpty(id)){				
				ids = id.split(",");
			}
			for(String returnBillNo : ids){	
				if(!StringUtils.isNullOrEmpty(returnBillNo)){		
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		boolean flag1 = false;
		TtOpReturnBillPO torbValue = TtOpReturnBillPO.findFirst("return_Bill_No=?",new String(returnBillNo));
		torbValue.set("Return_Bill_Status",OemDictCodeConstants.OP_RETURN_STATUS_DESPATCH);//已发运
		torbValue.set("Despatch_Date",new Date());//发运日期
		torbValue.set("Update_By",loginInfo.getUserId());
		torbValue.set("Update_Date",new Date());
		flag1=torbValue.saveIt();
		if(flag1){
		}else{
			throw new ServiceBizException("发运失败!");
		}
		boolean flag = false;
		TtOpOldpartPO tooValue = new TtOpOldpartPO();
		tooValue.set("Return_Bill_No",returnBillNo);
		tooValue.set("Despatch_Date",new Date());
		tooValue.set("Oldpart_Status",OemDictCodeConstants.OP_STATUS_DESPATCH);
		tooValue.set("Update_By",loginInfo.getUserId());
		tooValue.set("Update_Date",new Date());
		flag = tooValue.saveIt();
		if(flag){
		}else{
			throw new ServiceBizException("发运失败!");
		}
	}
	}
		}
}
