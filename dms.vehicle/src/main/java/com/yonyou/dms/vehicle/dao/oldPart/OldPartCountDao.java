package com.yonyou.dms.vehicle.dao.oldPart;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.common.domains.DTO.basedata.TtOpOldpartPO;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.vehicle.domains.DTO.oldPart.TtOpOldpartDTO;
import com.yonyou.dms.vehicle.domains.DTO.oldPart.TtOpReturnBillDTO;
import com.yonyou.dms.vehicle.domains.PO.oldPart.TtOpReturnBillPO;
/**
 * 旧件清点
 * @author Administrator
 *
 */
@Repository
public class OldPartCountDao extends OemBaseDAO{
	
	public PageInfoDto findCount(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam, params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;
	}

	private String getQuerySql(Map<String, String> queryParam, List<Object> params) {
		StringBuffer sql = new StringBuffer();
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		sql.append("SELECT  TOO.OLDPART_ID,                                          #主键\n");
		sql.append("		TOO.DEALER_CODE,                                         #经销商编号\n");
		sql.append("		TOO.OLDPART_ORDER,                                       #旧件序号\n");
		sql.append("        TOO.DEALER_NAME,                                         #经销商名称\n");
		sql.append("        TORB.RETURN_BILL_NO,                                     #回运单号\n");
		sql.append("        TOO.BALANCE_NO,                                          #结算单号\n");
		sql.append("        TOO.REPAIR_NO,   										 #工单号\n");
		sql.append("        TOO.CLAIM_NUMBER,									     # 索赔单号\n");
		sql.append("        TOO.VIN, 												 #VIN\n");
		sql.append("        TOO.OLDPART_NO,  										 #旧件代码\n");
		sql.append("        TOO.OLDPART_NAME, 										 #旧件名称\n");
		sql.append("        TOO.PART_FEE,     										 #税索赔单价\n");
		sql.append("        VM.BRAND_NAME,                                           #品牌\n");
		sql.append("        VM.SERIES_NAME,                                          #车系\n");
		sql.append("        VM.GROUP_NAME,                                           #车型\n");
		sql.append("        TOO.OLDPART_TYPE,                                        #旧件类型\n");
		sql.append("        TOO.RETURN_BILL_TYPE,                                    #回运类型\n");
		sql.append("        DATE_FORMAT(TOO.CLAIM_APPLY_DATE,'%y-%m-%d')as CLAIM_APPLY_DATE, #索赔申请日期\n");
		sql.append("        DATE_FORMAT(TOO.CLAIM_AUDIT_DATE,'%y-%m-%d')as CLAIM_AUDIT_DATE, #索赔审核通过日期 \n");
		sql.append("        TOO.MSV,                                                 #MSV码\n");
		sql.append("        DATE_FORMAT(TV.PURCHASE_DATE,'%y-%m-%d') PURCHASE_DATE,     #购车日期\n");
		sql.append("        DATE_FORMAT(TOO.REPAIR_DATE,'%y-%m-%d') REPAIR_DATE,		 #维修日期\n");
		sql.append("        TOO.REPAIR_TYPE, 										 #保修类型\n");
		sql.append("        TOO.OUT_MILEAGE,                                         #行驶里程\n");
		sql.append("        TOO.REPAIR_REMARK,										     #维修备注\n");
		sql.append("        TOO.OLDPART_STATUS, 									 #旧件状态\n");
		sql.append("        DATE_FORMAT(TOO.DESPATCH_DATE,'%y-%m-%d')as DESPATCH_DATE	 #发运日期\n");
		sql.append("FROM TT_OP_OLDPART_DCS TOO 														\n");
		sql.append("LEFT JOIN TT_OP_RETURN_BILL_DCS TORB ON TORB.RETURN_BILL_NO = TOO.RETURN_BILL_NO \n");
		sql.append("INNER JOIN TM_VEHICLE_DEC TV ON TOO.VIN = TV.VIN                                  \n");
		sql.append("INNER JOIN ("+getVwMaterialSql()+") VM ON TV.MATERIAL_ID = VM.MATERIAL_ID                  \n");  
		//放回地code
		if (!StringUtils.isNullOrEmpty(queryParam.get("returnaddrCode"))) {
			sql.append(" INNER JOIN TM_OLDPART_RETURNADDR_DCS TOR ON TORB.RETURNADDR_ID=TOR.RETURNADDR_ID \n");
			sql.append(" and TOR.RETURNADDR_CODE = ? \n");
			params.add(queryParam.get("returnaddrCode"));
		}
		sql.append(" WHERE 1=1  \n");  
		sql.append(" and TOO.OLDPART_STATUS = "+OemDictCodeConstants.OP_STATUS_DESPATCH+" \n");
		sql.append(" and TOO.OEM_COMPANY_ID = "+loginInfo.getCompanyId());
		//经销商条件查询
		if (!StringUtils.isNullOrEmpty(queryParam.get("dealerCode"))) {
			sql.append("   AND  TOO.DEALER_CODE  =? ");
			params.add(queryParam.get("dealerCode"));
		}
        //承运商
		if (!StringUtils.isNullOrEmpty(queryParam.get("carriage"))) {
			sql.append("   AND  torb.CARRIAGE  like (?) ");
			params.add("%"+queryParam.get("carriage")+"%");
		}
		//回运单号
		if (!StringUtils.isNullOrEmpty(queryParam.get("returnBillNo"))) {
			sql.append("   AND  TORB.RETURN_BILL_NO  like (?) ");
			params.add("%"+queryParam.get("returnBillNo")+"%");
		}
		//发送日期开始
		if (!StringUtils.isNullOrEmpty(queryParam.get("startdate"))) {
			sql.append("   AND DATE(TOO.DESPATCH_DATE) >= ? \n");
			params.add(queryParam.get("startdate"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("enddate"))) {
			sql.append("   AND DATE(TOO.DESPATCH_DATE) <= ? \n");
			params.add(queryParam.get("enddate"));
		}
		//建单日期
		if (!StringUtils.isNullOrEmpty(queryParam.get("billBeginDate"))) {
			sql.append("   AND DATE(TORB.BILL_DATE) >= ? \n");
			params.add(queryParam.get("billBeginDate"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("billEndDate"))) {
			sql.append("   AND DATE(TORB.BILL_DATE) <= ? \n");
			params.add(queryParam.get("billEndDate"));
		}
		return sql.toString();
	}
	
	public List<Map> getReturnAddr(Map<String, String> queryParam) {
		StringBuffer sql = new StringBuffer();
		    sql.append("SELECT 	 \n");
	        sql.append("  TOR.RETURNADDR_ID, 	 \n");
	        sql.append("  TOR.RETURNADDR_CODE, 	 \n");
	        sql.append("  TOR.RETURNADDR_NAME	 \n");
	        sql.append("FROM 	 \n");
	        sql.append("   TM_OLDPART_RETURNADDR_DCS TOR	 \n");
	        sql.append("WHERE 1=1 \n");
	        sql.append("AND TOR.STATUS='"+OemDictCodeConstants.STATUS_ENABLE+"'\n");
	        List<Map> map=OemDAOUtil.findAll(sql.toString(), null);
	        return map;
	}
	/**
	 * 修改
	 * 
	 * @param id
	 * @param user
	 * @throws ServiceBizException
	 */
	public void modifyoldParth(Long id, TtOpOldpartDTO tcdto) throws ServiceBizException {
		TtOpOldpartPO tc = TtOpOldpartPO.findById(id);
		setTmOldpartStor(tc, tcdto);
		tc.saveIt();
	}
	/**
	 * 设置对象属性
	 * 
	 * @param tc
	 * @param user
	 */
	private void setTmOldpartStor(TtOpOldpartPO tc, TtOpOldpartDTO tcdto) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		tc.setInteger("OLDPART_STATUS", tcdto.getOldpartStatus());
		tc.setString("RECEIVE_REMARK", tcdto.getReceiveRemark());
		tc.setDate("RECEIVE_DATE", new Date());
		tc.setLong("UPDATE_BY", 11111111);
		tc.setDate("UPDATE_DATE",new Date());
		if(tcdto.getOldpartStatus().toString().equals("91121004")){
			List<TtOpOldpartPO> opOldpartList = TtOpOldpartPO.findAll();
			TtOpOldpartPO opOldpartPO = opOldpartList.get(0);
			//更新回运单表接收总数和拒绝总数(只有拒绝时才更新接收总数和拒绝总数)
			TtOpReturnBillDTO torb = new TtOpReturnBillDTO();
			torb.setReturnBillNo(opOldpartPO.getString("Return_Bill_No"));
			List<TtOpReturnBillPO> po = TtOpReturnBillPO.findAll();
			Long repulseTotal = po.get(0).getLong("Repulse_Total");//拒绝总数
			Long oldpartTotal = po.get(0).getLong("Oldpart_Total");//旧件总数
			TtOpReturnBillPO torbValue = new TtOpReturnBillPO();
			torbValue.setLong("Repulse_Total",Long.parseLong((Integer.parseInt(repulseTotal+"")+1)+""));//当前拒绝总数+1
			torbValue.setLong("Reception_Total",Long.parseLong(Integer.parseInt(oldpartTotal+"")-Long.parseLong((Integer.parseInt(repulseTotal+"")+1)+"")+""));//旧件总数-拒绝后总数
			torbValue.saveIt();
		}
	}
	public PageInfoDto findCountmx(Long id) {
		List<Object> params = new ArrayList<Object>();
		String sql = find(id, params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;
	}
	private String find( Long id, List<Object> params) {
		StringBuffer sql = new StringBuffer();
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		sql.append("SELECT  TOO.OLDPART_ID,                                          #主键\n");
		sql.append("		TOO.DEALER_CODE,                                         #经销商编号\n");
		sql.append("		TOO.OLDPART_ORDER,                                       #旧件序号\n");
		sql.append("        TOO.DEALER_NAME,                                         #经销商名称\n");
		sql.append("        TORB.RETURN_BILL_NO,                                     #回运单号\n");
		sql.append("        TOO.BALANCE_NO,                                          #结算单号\n");
		sql.append("        TOO.REPAIR_NO,   										 #工单号\n");
		sql.append("        TOO.CLAIM_NUMBER,									     # 索赔单号\n");
		sql.append("        TOO.VIN, 												 #VIN\n");
		sql.append("        TOO.OLDPART_NO,  										 #旧件代码\n");
		sql.append("        TOO.OLDPART_NAME, 										 #旧件名称\n");
		sql.append("        TOO.PART_FEE,     										 #税索赔单价\n");
		sql.append("        VM.BRAND_NAME,                                           #品牌\n");
		sql.append("        VM.SERIES_NAME,                                          #车系\n");
		sql.append("        VM.GROUP_NAME,                                           #车型\n");
		sql.append("        TOO.OLDPART_TYPE,                                        #旧件类型\n");
		sql.append("        TOO.RETURN_BILL_TYPE,                                    #回运类型\n");
		sql.append("        DATE_FORMAT(TOO.CLAIM_APPLY_DATE,'%y-%m-%d')as CLAIM_APPLY_DATE, #索赔申请日期\n");
		sql.append("        DATE_FORMAT(TOO.CLAIM_AUDIT_DATE,'%y-%m-%d')as CLAIM_AUDIT_DATE, #索赔审核通过日期 \n");
		sql.append("        TOO.MSV,                                                 #MSV码\n");
		sql.append("        DATE_FORMAT(TV.PURCHASE_DATE,'%y-%m-%d')as PURCHASE_DATE,     #购车日期\n");
		sql.append("        DATE_FORMAT(TOO.REPAIR_DATE,'%y-%m-%d')as REPAIR_DATE,		 #维修日期\n");
		sql.append("        TOO.REPAIR_TYPE, 										 #保修类型\n");
		sql.append("        TOO.OUT_MILEAGE,                                         #行驶里程\n");
		sql.append("        TOO.REPAIR_REMARK,										     #维修备注\n");
		sql.append("        TOO.OLDPART_STATUS, 									 #旧件状态\n");
		sql.append("        DATE_FORMAT(TOO.DESPATCH_DATE,'%y-%m-%d')as DESPATCH_DATE	 #发运日期\n");
		sql.append("FROM TT_OP_OLDPART_DCS TOO 														\n");
		sql.append("LEFT JOIN TT_OP_RETURN_BILL_DCS TORB ON TORB.RETURN_BILL_NO = TOO.RETURN_BILL_NO \n");
		sql.append("INNER JOIN TM_VEHICLE_DEC TV ON TOO.VIN = TV.VIN                                  \n");
		sql.append("INNER JOIN ("+getVwMaterialSql()+") VM ON TV.MATERIAL_ID = VM.MATERIAL_ID                  \n"); 
		sql.append(" WHERE 1=1  \n");  
		sql.append(" and TOO.OLDPART_STATUS = "+OemDictCodeConstants.OP_STATUS_DESPATCH+" \n");
		sql.append(" and TOO.OEM_COMPANY_ID = "+loginInfo.getCompanyId());
		if (!StringUtils.isNullOrEmpty(id)) {
			sql.append("   AND  TOO.OLDPART_ID  =? ");
			params.add(id);
		}
		
		return sql.toString();
}
}
