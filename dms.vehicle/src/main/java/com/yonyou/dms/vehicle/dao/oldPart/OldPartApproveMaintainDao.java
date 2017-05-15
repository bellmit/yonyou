package com.yonyou.dms.vehicle.dao.oldPart;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.vehicle.domains.DTO.oldPart.TtOpOldpartDTO;
import com.yonyou.dms.vehicle.domains.PO.oldPart.TtOpOldpartPO;
import com.yonyou.dms.vehicle.domains.PO.oldPart.TtOpReturnBillPO;
import com.yonyou.dms.vehicle.domains.PO.oldPart.TtOpStoreDetailPO;
import com.yonyou.dms.vehicle.domains.PO.oldPart.TtOpStorePO;
@Repository
public class OldPartApproveMaintainDao extends OemBaseDAO{

	public PageInfoDto findCount(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam, params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;
	}

	private String getQuerySql(Map<String, String> queryParam, List<Object> params) {
		StringBuffer sql = new StringBuffer();
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		sql.append("select too.DEALER_CODE#经销商代码	\n");
		sql.append("	  ,too.DEALER_NAME#经销商名称	\n");
		sql.append("	  ,too.RETURN_BILL_NO#回运单号	\n");
		sql.append("	  ,too.BALANCE_NO#结算单号	\n");
		sql.append("	  ,too.REPAIR_NO#工单号	\n");
		sql.append("	  ,too.CLAIM_NUMBER#索赔单号	\n");
		sql.append("	  ,too.VIN#VIN码	\n");
		sql.append("	  ,too.OLDPART_ID#旧件ID	\n");
		sql.append("	  ,too.OLDPART_NO#旧件代码	\n");
		sql.append("	  ,too.OLDPART_NAME#旧件名称	\n");
		sql.append("	  ,too.OLDPART_ORDER#旧件序号	\n");
		sql.append("	  ,too.PART_FEE#含税索赔单价	\n");
		sql.append("	  ,vm.BRAND_NAME #品牌	\n");
		sql.append("	  ,vm.SERIES_NAME #车系	\n");
		sql.append("	  ,vm.GROUP_NAME #车型	\n");
		sql.append("	  ,too.OLDPART_TYPE#旧件类型	\n");
		sql.append("	  ,too.RETURN_BILL_TYPE#回运类型	\n");
		sql.append("	  ,DATE_FORMAT(too.CLAIM_APPLY_DATE,'%Y-%m-%d') CLAIM_APPLY_DATE#索赔申请日期	\n");
		sql.append("	  ,DATE_FORMAT(too.CLAIM_AUDIT_DATE,'%Y-%m-%d') CLAIM_AUDIT_DATE#索赔审核通过日期	\n");
		sql.append("	  ,too.MSV#MSV码	\n");
		sql.append("	  ,DATE_FORMAT(tv.PURCHASE_DATE,'%Y-%m-%d') PURCHASE_DATE#购车日期	\n");
		sql.append("	  ,DATE_FORMAT(too.REPAIR_DATE,'%Y-%m-%d') REPAIR_DATE#维修日期	\n");
		sql.append("	  ,too.REPAIR_TYPE#保修类型	\n");
		sql.append("	  ,too.OUT_MILEAGE#行驶里程	\n");
		sql.append("	  ,too.REPAIR_REMARK#维修备注	\n");
		sql.append("	  ,too.OLDPART_STATUS#旧件状态	\n");
		sql.append("	  ,DATE_FORMAT(too.RECEIVE_DATE,'%Y-%m-%d') RECEIVE_DATE #清点日期	\n");
		sql.append("	  ,tora.RETURNADDR_CODE #返还地	\n");
		sql.append("	  ,torb.CARRIAGE#承运商	\n");
		sql.append("	  ,DATE_FORMAT(too.DESPATCH_DATE,'%Y-%m-%d') DESPATCH_DATE #发运日期	\n");
		sql.append("	from TT_OP_OLDPART_DCS too#旧件清单表	\n");
		sql.append("	  LEFT JOIN TT_OP_RETURN_BILL_DCS TORB ON TOO.RETURN_BILL_NO = TORB.RETURN_BILL_NO#回运单表	\n");
		sql.append("	  LEFT JOIN TM_OLDPART_RETURNADDR_DCS TORA ON TORB.RETURNADDR_ID = TORA.RETURNADDR_ID #旧件返还地维护表	\n");
		sql.append("	  LEFT JOIN TM_VEHICLE_DEC TV ON TOO.VIN = TV.VIN	\n");
		sql.append("	  LEFT JOIN ("+getVwMaterialSql()+") VM ON VM.MATERIAL_ID = TV.MATERIAL_ID	\n");
		sql.append("	  where too.OEM_COMPANY_ID="+loginInfo.getCompanyId()+"	\n");
		if (!StringUtils.isNullOrEmpty(queryParam.get("oldpartStatus"))) {
			sql.append(" and too.OLDPART_STATUS =? \n");
			params.add(queryParam.get("oldpartStatus"));
		}else{
			sql.append(" and  too.OLDPART_STATUS in ('"+OemDictCodeConstants.OP_STATUS_RECEIVE+"','"+OemDictCodeConstants.OP_STATUS_REGECT+"','"+OemDictCodeConstants.OP_STATUS_IN_STORE+"') \n");
		}
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
			sql.append("   AND  TOO.RETURN_BILL_NO  like (?) ");
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
		if (!StringUtils.isNullOrEmpty(queryParam.get("returnaddrCode"))) {
			sql.append("   AND  tora.RETURNADDR_CODE  =? ");
			params.add(queryParam.get("returnaddrCode"));
		}
		return sql.toString();
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
		tc.setString("AUDIT_REMARK", tcdto.getAuditRemark());
		tc.setLong("UPDATE_BY", loginInfo.getUserId());
		tc.setDate("UPDATE_DATE",new Date());
		
		if(tcdto.getOldpartStatus().toString().equals("91121003")){
			//审核通过，将旧件清单信息保存到库存表
			List<TtOpOldpartPO> opOldpartList = TtOpOldpartPO.findAll();
			TtOpOldpartPO	opOldpartPO = opOldpartList.get(0);
			TtOpStorePO tos = new TtOpStorePO();
			tos.set("Stor_Address_Id",tcdto.getStorAddressId());
			tos.set("Oldpart_No",opOldpartPO.get("Oldpart_No"));
			tos.set("Oldpart_Name",opOldpartPO.get("Oldpart_Name"));
			tos.set("Oldpart_Order",opOldpartPO.get("Oldpart_Order"));
			tos.set("Store_Status",OemDictCodeConstants.OP_STORE_STATUS_IN);
			tos.set("Dealer_Code",opOldpartPO.get("Dealer_Code"));
			tos.set("Dealer_Name",opOldpartPO.get("Dealer_Name"));
			tos.set("Return_Bill_No",opOldpartPO.get("Return_Bill_No"));
			tos.set("Balance_No",opOldpartPO.get("Balance_No"));
			tos.set("Repair_No",opOldpartPO.get("Repair_No"));
			tos.set("Claim_Number",opOldpartPO.get("Claim_Number"));
			tos.set("vin",opOldpartPO.get("vin"));
			tos.set("Oem_Company_Id",loginInfo.getCompanyId());
			tos.set("Create_Date",new Date());
			tos.set("Create_By",loginInfo.getUserId());
			tos.saveIt();

			//将旧件清单信息保存到库存明细表
			TtOpStoreDetailPO tosd = new TtOpStoreDetailPO();
			tosd.set("Store_Id",tos.get("Store_Id"));
			tosd.set("Store_Type",OemDictCodeConstants.OP_STORE_STATUS_IN);
			tosd.set("Stor_Address_Id",tcdto.getStorAddressId());
			tosd.set("Operation_Date",new Date());
			tosd.set("Oem_Company_Id",loginInfo.getCompanyId());
			tosd.set("Create_Date",new Date());
			tosd.set("Create_By",loginInfo.getUserId());
			tosd.saveIt();
			
			//更新回运单表接收总数
			List<TtOpReturnBillPO> po = TtOpReturnBillPO.find("return_bill_no=?",opOldpartPO.getString("Return_Bill_No"));
			Long oldReceptionTotal =null;
			if(po.size()>0){
			 oldReceptionTotal = po.get(0).getLong("Reception_Total");
			}else{
				oldReceptionTotal =(long) 0;
			}
			TtOpReturnBillPO torbValue = new TtOpReturnBillPO();
			torbValue.set("Reception_Total",Long.parseLong((Integer.parseInt(oldReceptionTotal+"")+1)+""));
			torbValue.saveIt();
		}else
		if(tcdto.getOldpartStatus().toString().equals("91121004")){
			List<TtOpOldpartPO> opOldpartList = TtOpOldpartPO.findAll();
			TtOpOldpartPO opOldpartPO = opOldpartList.get(0);
			//更新回运单表接收总数和拒绝总数(只有拒绝时才更新接收总数和拒绝总数)
			List<TtOpReturnBillPO> po = TtOpReturnBillPO.find("return_bill_no=?",opOldpartPO.getString("Return_Bill_No"));
			Long repulseTotal =null;
			Long oldpartTotal =null;
			if(po.size()>0){
				repulseTotal = po.get(0).getLong("Reception_Total");
				oldpartTotal = po.get(0).getLong("Oldpart_Total");//旧件总数
			}else{
				repulseTotal =(long) 0;
				oldpartTotal=(long) 0;
			}
			TtOpReturnBillPO torbValue = new TtOpReturnBillPO();
			torbValue.setLong("Repulse_Total",Long.parseLong((Integer.parseInt(repulseTotal+"")+1)+""));//当前拒绝总数+1
			torbValue.setLong("Reception_Total",Long.parseLong(Integer.parseInt(oldpartTotal+"")-Long.parseLong((Integer.parseInt(repulseTotal+"")+1)+"")+""));//旧件总数-拒绝后总数
			torbValue.saveIt();
		}
	}
	
	public List<Map> getHouse(Map<String, String> queryParam) {
		StringBuffer sql = new StringBuffer();
		    sql.append("SELECT 	 \n");
	        sql.append("  TOR.STOR_ID, 	 \n");
	        sql.append("  TOR.STOR_CODE, 	 \n");
	        sql.append("  TOR.STOR_NAME	 \n");
	        sql.append("FROM 	 \n");
	        sql.append("   TM_OLDPART_STOR_DCS TOR	 \n");
	        sql.append("WHERE 1=1 \n");
	        List<Map> map=OemDAOUtil.findAll(sql.toString(), null);
	        return map;
	}
	public PageInfoDto find(Long id) {
		List<Object> params = new ArrayList<Object>();
		String sql = getSql(id, params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;
	}

	private String getSql(Long id, List<Object> params) {
		StringBuffer sql = new StringBuffer();
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		sql.append("select too.DEALER_CODE#经销商代码	\n");
		sql.append("	  ,too.DEALER_NAME#经销商名称	\n");
		sql.append("	  ,too.RETURN_BILL_NO#回运单号	\n");
		sql.append("	  ,too.BALANCE_NO#结算单号	\n");
		sql.append("	  ,too.REPAIR_NO#工单号	\n");
		sql.append("	  ,too.CLAIM_NUMBER#索赔单号	\n");
		sql.append("	  ,too.VIN#VIN码	\n");
		sql.append("	  ,too.OLDPART_ID#旧件ID	\n");
		sql.append("	  ,too.OLDPART_NO#旧件代码	\n");
		sql.append("	  ,too.OLDPART_NAME#旧件名称	\n");
		sql.append("	  ,too.OLDPART_ORDER#旧件序号	\n");
		sql.append("	  ,too.PART_FEE#含税索赔单价	\n");
		sql.append("	  ,vm.BRAND_NAME #品牌	\n");
		sql.append("	  ,vm.SERIES_NAME #车系	\n");
		sql.append("	  ,vm.GROUP_NAME #车型	\n");
		sql.append("	  ,too.OLDPART_TYPE#旧件类型	\n");
		sql.append("	  ,too.RETURN_BILL_TYPE#回运类型	\n");
		sql.append("	  ,DATE_FORMAT(too.CLAIM_APPLY_DATE,'%Y-%m-%d') CLAIM_APPLY_DATE#索赔申请日期	\n");
		sql.append("	  ,DATE_FORMAT(too.CLAIM_AUDIT_DATE,'%Y-%m-%d') CLAIM_AUDIT_DATE#索赔审核通过日期	\n");
		sql.append("	  ,too.MSV#MSV码	\n");
		sql.append("	  ,DATE_FORMAT(tv.PURCHASE_DATE,'%Y-%m-%d') PURCHASE_DATE#购车日期	\n");
		sql.append("	  ,DATE_FORMAT(too.REPAIR_DATE,'%Y-%m-%d') REPAIR_DATE#维修日期	\n");
		sql.append("	  ,too.REPAIR_TYPE#保修类型	\n");
		sql.append("	  ,too.OUT_MILEAGE#行驶里程	\n");
		sql.append("	  ,too.REPAIR_REMARK#维修备注	\n");
		sql.append("	  ,too.OLDPART_STATUS#旧件状态	\n");
		sql.append("	  ,DATE_FORMAT(too.RECEIVE_DATE,'%Y-%m-%d') RECEIVE_DATE #清点日期	\n");
		sql.append("	  ,tora.RETURNADDR_CODE #返还地	\n");
		sql.append("	  ,torb.CARRIAGE#承运商	\n");
		sql.append("	  ,DATE_FORMAT(too.DESPATCH_DATE,'%Y-%m-%d') DESPATCH_DATE #发运日期	\n");
		sql.append("	from TT_OP_OLDPART_DCS too#旧件清单表	\n");
		sql.append("	  LEFT JOIN TT_OP_RETURN_BILL_DCS TORB ON TOO.RETURN_BILL_NO = TORB.RETURN_BILL_NO#回运单表	\n");
		sql.append("	  LEFT JOIN TM_OLDPART_RETURNADDR_DCS TORA ON TORB.RETURNADDR_ID = TORA.RETURNADDR_ID #旧件返还地维护表	\n");
		sql.append("	  LEFT JOIN TM_VEHICLE_DEC TV ON TOO.VIN = TV.VIN	\n");
		sql.append("	  LEFT JOIN ("+getVwMaterialSql()+") VM ON VM.MATERIAL_ID = TV.MATERIAL_ID	\n");
		sql.append("	  where too.OEM_COMPANY_ID="+loginInfo.getCompanyId()+"	\n");
		if (!StringUtils.isNullOrEmpty(id)) {
			sql.append("   AND  TOO.OLDPART_ID  =? ");
			params.add(id);
		}
		return sql.toString();
	}
}