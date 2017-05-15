package com.yonyou.dms.vehicle.dao.oldPart;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.StringUtils;
@Repository
public class OldPartListManageDao extends OemBaseDAO{

	
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);

	public PageInfoDto findListManage(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam, params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;
	}
	/**
	 * 下载
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<Map> queryEmpInfoforExport(Map<String, String> queryParam) throws Exception {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam, params);
		return OemDAOUtil.findAll(sql.toString(), params);

	}

	private String getQuerySql(Map<String, String> queryParam, List<Object> params) {
		StringBuffer sql = new StringBuffer();
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		sql.append(" SELECT \n");
		sql.append(" 		TM.PURCHASE_DATE,   VM.MODEL_CODE, \n");
		sql.append("		VM.BRAND_CODE,           \n");
		sql.append("      VM.SERIES_CODE,          \n");
		sql.append("      VM.MODEL_CODE,           \n");
		sql.append("      TOO.DEALER_CODE,         \n");
		sql.append("      TOO.DEALER_NAME,          \n");
		sql.append("      TOO.OLDPART_ID,            \n");
		sql.append(" 		TOO.OLDPART_NO,            \n");
		sql.append(" 		TOO.OLDPART_NAME,          \n");
		sql.append(" 		TOO.OLDPART_STATUS,        \n");
		sql.append(" 		TOO.OLDPART_TYPE,          \n");
		sql.append(" 		TOO.RETURN_BILL_TYPE,     \n");
		sql.append(" 		TOO.BALANCE_NO,           \n");
		sql.append(" 		TOO.BALLANCE_TIME,       \n");
		sql.append(" 		TOO.REPAIR_NO,            \n");
		sql.append(" 		TOO.CLAIM_NUMBER,TOO.VIN,TOO.PART_FEE,TOO.RETURN_BILL_NO,TOO.OUT_MILEAGE, \n");
		sql.append(" 		TOO.MSV,TOO.REPAIR_DATE,TOO.CLAIM_APPLY_DATE,TOO.CLAIM_AUDIT_DATE,TOO.REPAIR_TYPE,TOO.REPAIR_REMARK ,TOO.RECEIVE_REMARK\n");
		sql.append(" FROM TT_OP_OLDPART_DCS TOO,TM_VEHICLE_DEC TM,("+getVwMaterialSql()+") VM \n");
		sql.append(" where 1=1");
		sql.append(" AND TOO.VIN = TM.VIN \n");
		sql.append(" AND TM.MATERIAL_ID = VM.MATERIAL_ID \n");
		sql.append(" AND TOO.OEM_COMPANY_ID="+loginInfo.getCompanyId()+" \n");
//		sql.append(" AND (TOO.RETURN_BILL_NO IS NULL or TOO.RETURN_BILL_NO= '' )\n");//未生成回运单的数据
		//旧件类型
		if(queryParam.get("ifType").equals("10041001")){
			 sql.append(" AND TOO.OLDPART_TYPE='"+ OemDictCodeConstants.OP_TYPE_DESTROY+"' \n");//显示自销毁
		}else{
			 sql.append(" AND TOO.OLDPART_TYPE<>'"+ OemDictCodeConstants.OP_TYPE_DESTROY+"' \n");//不显示自销毁
		}
		//索赔单号
		if (!StringUtils.isNullOrEmpty(queryParam.get("claimNumber"))) {
			sql.append("   AND TOO.CLAIM_NUMBER =? ");
			params.add(queryParam.get("claimNumber"));
		}
		//旧件代码
		if (!StringUtils.isNullOrEmpty(queryParam.get("oldpPartNo"))) {
			sql.append("   AND TOO.OLDPART_NO =? ");
			params.add(queryParam.get("oldpPartNo"));
		}
		//旧件状态
		if (!StringUtils.isNullOrEmpty(queryParam.get("oldpartStatus"))) {
			sql.append(" and  TOO.OLDPART_STATUS =? \n");
			params.add(queryParam.get("oldpartStatus"));
		}else{
			sql.append(" AND TOO.OLDPART_STATUS='"+ OemDictCodeConstants.OP_STATUS_NO_DESPATCH+"' \n");//旧件状态，未发运
		}
		//审核日期
		if (!StringUtils.isNullOrEmpty(queryParam.get("beginDate"))) {
			sql.append("   AND DATE(TOO.CLAIM_APPLY_DATE) >= ? \n");
			params.add(queryParam.get("beginDate"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("endDate"))) {
			sql.append("   AND DATE(TOO.CLAIM_APPLY_DATE) <= ? \n");
			params.add(queryParam.get("endDate"));
		}
		//VIN
		if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
			sql.append("   AND TOO.VIN =?");
			params.add(queryParam.get("vin"));
		}
		//经销商代码
    	if (!StringUtils.isNullOrEmpty(queryParam.get("dealerCode"))) {
    		sql.append("   AND TOO.DEALER_CODE =?");
    		params.add(queryParam.get("dealerCode"));
        }
        //是否返还
		if (!StringUtils.isNullOrEmpty(queryParam.get("returnState"))) {
			if(queryParam.get("returnState").equals("10041001")){
				sql.append(" and TOO.OLDPART_TYPE='91111003'\n");
			}else if(queryParam.get("returnState").equals("10041002")){
				sql.append(" and TOO.OLDPART_TYPE in('91111001','91111002')\n");
			}
		}
		
		//是否为国产
    	if (!StringUtils.isNullOrEmpty(queryParam.get("groupType"))) {
    		sql.append("   AND  VM.GROUP_TYPE =?");
    		params.add(queryParam.get("groupType"));
        }
		//品牌
    	if (!StringUtils.isNullOrEmpty(queryParam.get("brandId"))) {
    		sql.append("   AND  vm.BRAND_CODE =?");
    		params.add(queryParam.get("brandId"));
        }
		//车系
	   	if (!StringUtils.isNullOrEmpty(queryParam.get("seriesId"))) {
    		sql.append("   AND  vm.SERIES_CODE =?");
    		params.add(queryParam.get("seriesId"));
        }
		return sql.toString();
		
	}
	
	/**
	 * 车厂端   新增回运端     旧件清单查询
	 * @param claimNumber
	 * @param oldpartStatus
	 * @param oldpPartNo
	 * @param dealerCode
	 * @param beginDate
	 * @param endDate
	 * @param vin
	 * @param returnState
	 * @param logonUser
	 * @param pageSize
	 * @param curPage
	 * @return
	 */
	public PageInfoDto queryOldPartList1(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer pasql = new StringBuffer();
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		pasql.append(" SELECT \n");
		pasql.append(" 		TM.PURCHASE_DATE,   VM.MODEL_CODE, \n");
		pasql.append("		VM.BRAND_CODE,           \n");
		pasql.append("      VM.SERIES_CODE,          \n");
		pasql.append("      VM.MODEL_CODE,           \n");
		pasql.append("      TOO.DEALER_CODE,         \n");
		pasql.append("      TOO.DEALER_NAME,          \n");
		pasql.append("      TOO.OLDPART_ID,            \n");
		pasql.append(" 		TOO.OLDPART_NO,            \n");
		pasql.append(" 		TOO.OLDPART_NAME,          \n");
		pasql.append(" 		TOO.OLDPART_STATUS,        \n");
		pasql.append(" 		TOO.OLDPART_TYPE,          \n");
		pasql.append(" 		TOO.RETURN_BILL_TYPE,     \n");
		pasql.append(" 		TOO.BALANCE_NO,           \n");
		pasql.append(" 		TOO.BALLANCE_TIME,       \n");
		pasql.append(" 		TOO.REPAIR_NO,            \n");
		pasql.append(" 		TOO.CLAIM_NUMBER,TOO.VIN,TOO.PART_FEE,TOO.RETURN_BILL_NO,TOO.OUT_MILEAGE, \n");
		pasql.append(" 		TOO.MSV,TOO.REPAIR_DATE,TOO.CLAIM_APPLY_DATE,TOO.CLAIM_AUDIT_DATE,TOO.REPAIR_TYPE,TOO.REPAIR_REMARK,TOO.RECEIVE_REMARK \n");
		pasql.append(" FROM TT_OP_OLDPART TOO,TM_VEHICLE TM,VW_MATERIAL VM \n");
		pasql.append(" where 1=1");
		pasql.append(" AND TOO.VIN = TM.VIN \n");
		pasql.append(" AND TM.MATERIAL_ID = VM.MATERIAL_ID \n");
		pasql.append(" AND TOO.OEM_COMPANY_ID="+loginInfo.getCompanyId()+" \n");
		pasql.append(" and  TOO.DEALER_CODE  = '" + queryParam.get("dealerCode") +"'\n");
		pasql.append(" AND (TOO.RETURN_BILL_NO IS NULL  or TOO.RETURN_BILL_NO= '')  \n");//未生成回运单的数据
		//排除已经选择
		String oldPartId=queryParam.get("oldPartIds").replaceAll(",''", "");
		
		pasql.append(" and too.OLDPART_ID not in ("+oldPartId+")");
		//旧件类型
		if(queryParam.get("ifType").equals("10041001")){
			 pasql.append(" AND TOO.OLDPART_TYPE='"+ OemDictCodeConstants.OP_TYPE_DESTROY+"' \n");//显示自销毁
		}else{
			 pasql.append(" AND TOO.OLDPART_TYPE<>'"+ OemDictCodeConstants.OP_TYPE_DESTROY+"' \n");//不显示自销毁
		}
		//索赔单号
	  	if (!StringUtils.isNullOrEmpty(queryParam.get("claimNumber"))) {
    		pasql.append("   AND  TOO.CLAIM_NUMBER =?");
    		params.add(queryParam.get("claimNumber"));
        }
		//旧件代码
		if (!StringUtils.isNullOrEmpty(queryParam.get("oldpPartNo"))) {
    		pasql.append("   AND  TOO.OLDPART_NO =?");
    		params.add(queryParam.get("oldpPartNo"));
        }
		//旧件状态
		if (!StringUtils.isNullOrEmpty(queryParam.get("oldpartStatus"))) {
    		pasql.append("   AND  TOO.OLDPART_STATUS =?");
    		params.add(queryParam.get("oldpartStatus"));
        }
		//审核日期
		if (!StringUtils.isNullOrEmpty(queryParam.get("beginDate"))) {
			pasql.append("   AND DATE(TOO.CLAIM_APPLY_DATE) >= ? \n");
			params.add(queryParam.get("beginDate"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("endDate"))) {
			pasql.append("   AND DATE(TOO.CLAIM_APPLY_DATE) <= ? \n");
			params.add(queryParam.get("endDate"));
		}
		//VIN
		if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
			pasql.append("   AND vin = ? \n");
			params.add(queryParam.get("vin"));
		}
        //是否返还
		if (!StringUtils.isNullOrEmpty(queryParam.get("returnState"))) {
			if(queryParam.get("returnState").equals("10041001")){
				pasql.append(" and TOO.OLDPART_TYPE='91111003'\n");
			}else if(queryParam.get("returnState").equals("10041002")){
				pasql.append(" and TOO.OLDPART_TYPE in('91111001','91111002')\n");
			}
		}
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(pasql.toString(), params);
		return pageInfoDto;
	}
}
