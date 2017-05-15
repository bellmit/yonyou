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
import com.yonyou.dms.function.utils.common.StringUtils;
@Repository
public class DealerOldPartClipPrintDao extends OemBaseDAO{

	
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	
	public PageInfoDto findClipPrint(Map<String, String> queryParam) {
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
		StringBuffer pasql = new StringBuffer();
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		pasql.append(" SELECT \n");
		pasql.append(" DATE_FORMAT(TM.PURCHASE_DATE,'%y-%m-%d') PURCHASE_DATE, VM.BRAND_CODE, VM.SERIES_NAME, VM.MODEL_NAME, \n");
		pasql.append(" TOO.OLDPART_ORDER,TOO.DEALER_CODE,TOO.DEALER_NAME,TOO.REPAIR_REMARK, \n");
		pasql.append(" TOO.OLDPART_ID, TOO.OLDPART_NO, TOO.OLDPART_NAME,TOO.OLDPART_STATUS,TOO.OLDPART_TYPE,TOO.RETURN_BILL_TYPE,\n");
		pasql.append(" TOO.BALANCE_NO,TOO.BALLANCE_TIME,TOO.REPAIR_NO,TOO.CLAIM_NUMBER,TOO.VIN,TOO.PART_FEE,TOO.RETURN_BILL_NO,TOO.OUT_MILEAGE, \n");
		pasql.append(" TOO.MSV,DATE_FORMAT(TOO.REPAIR_DATE,'%y-%m-%d') REPAIR_DATE,DATE_FORMAT(TOO.CLAIM_APPLY_DATE,'%y-%m-%d') CLAIM_APPLY_DATE,DATE_FORMAT(TOO.CLAIM_AUDIT_DATE,'%y-%m-%d') CLAIM_AUDIT_DATE,TOO.REPAIR_TYPE  \n");
		pasql.append(" FROM TT_OP_OLDPART_DCS TOO,TM_VEHICLE_DEC TM,("+getVwMaterialSql()+") VM \n");
		pasql.append(" WHERE 1=1 \n");
		pasql.append(" AND TOO.VIN = TM.VIN \n");
		pasql.append(" AND TM.MATERIAL_ID = VM.MATERIAL_ID \n");
		pasql.append(" AND TOO.OEM_COMPANY_ID="+loginInfo.getOemCompanyId()+" \n");
		//工单号查询
		if (!StringUtils.isNullOrEmpty(queryParam.get("repairNo"))) {
			pasql.append("   AND TOO.REPAIR_NO =?");
			params.add(queryParam.get("repairNo"));
		}
		//旧件状态
		if (!StringUtils.isNullOrEmpty(queryParam.get("status"))) {
			pasql.append("   AND TOO.OLDPART_STATUS =?");
			params.add(queryParam.get("status"));
		}
		//结算日期
		if (!StringUtils.isNullOrEmpty(queryParam.get("beginDate"))) {
			pasql.append("   AND DATE(TOO.BALLANCE_TIME) >=? \n");
			params.add(queryParam.get("beginDate"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("endDate"))) {
			pasql.append("   AND DATE(TOO.BALLANCE_TIME) <=? \n");
			params.add(queryParam.get("endDate"));
		}
		//VIN
		if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
			String vin=queryParam.get("vin");
			vin = vin.replaceAll("\\,", "\n");
			vin = vin.replaceAll("[\\t\\n\\r]", "','");
			vin = vin.replaceAll(",''", "");
			pasql.append("   AND TOO.VIN  in(?)");
			params.add(vin);
		}
		//经销商条件查询
		if (!StringUtils.isNullOrEmpty(queryParam.get("dealerCode"))) {
			String dealerCode=queryParam.get("dealerCode");
        	dealerCode = dealerCode.replaceAll("\\,", "\n");
    		dealerCode = dealerCode.replaceAll("[\\t\\n\\r]", "','");
    		dealerCode = dealerCode.replaceAll(",''", "");
    		pasql.append(" and  TOO.DEALER_CODE  in ('" + dealerCode +"' ) \n");
        }
        //旧件代码
		if (!StringUtils.isNullOrEmpty(queryParam.get("oldpPartNo"))) {
			pasql.append("   AND TOO.OLDPART_NO =?");
			params.add(queryParam.get("oldpPartNo"));
		}
		//当不是自销毁件
		if (queryParam.get("ifType").equals("10041002")) {
			//没有选择旧件类型时查询条件
			if (StringUtils.isNullOrEmpty(queryParam.get("opType"))) {
				pasql.append(" and TOO.OLDPART_TYPE in ('91111001','91111003') \n");
			}else{
				//旧件类型
					if (!StringUtils.isNullOrEmpty(queryParam.get("opType"))) {
					pasql.append(" and TOO.OLDPART_TYPE = ? \n");
					params.add(queryParam.get("opType"));
				}
			}
		}else if(queryParam.get("ifType").equals("10041001")){
			//当是自销毁件时查询条件
			pasql.append(" and TOO.OLDPART_TYPE = '91111002' \n");
		}

		if (!StringUtils.isNullOrEmpty(queryParam.get("opReturnType"))) {
			pasql.append("   AND TOO.RETURN_BILL_TYPE =?");
			params.add(queryParam.get("opReturnType"));
		}
		return pasql.toString();
	}

}
