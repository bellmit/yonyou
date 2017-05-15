/**
 * 
 */
package com.yonyou.dms.repair.service.basedata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * @author yangjie
 *
 */
@Service
@SuppressWarnings({ "rawtypes", "unchecked" })
public class RepairGroupServiceImpl implements RepairGroupService {

	@Override
	public PageInfoDto findGroupItem(Map<String, String> queryParam) {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT tpt.PACKAGE_TYPE_NAME,X.* FROM (" + CommonConstants.VM_PACKAGE + ") X LEFT JOIN TM_PACKAGE_TYPE tpt ON tpt.DEALER_CODE=X.DEALER_CODE AND tpt.PACKAGE_TYPE_CODE=X.PACKAGE_TYPE WHERE X.DEALER_CODE = '")
				.append(FrameworkUtil.getLoginInfo().getDealerCode()).append("' AND X.D_KEY=")
				.append(CommonConstants.D_KEY);
		Utility.sqlToLike(sb, queryParam.get("packageCode"), "PACKAGE_CODE", "X");
		Utility.sqlToLike(sb, queryParam.get("packageType"), "PACKAGE_TYPE", "X");
		Utility.sqlToLike(sb, queryParam.get("packageName"), "PACKAGE_NAME", "X");
		if (!StringUtils.isNullOrEmpty(queryParam.get("modelLabourCode"))) {
			sb.append(" AND X.MODEL_LABOUR_CODE = '").append(queryParam.get("modelLabourCode")).append("'");
		}
		return DAOUtil.pageQuery(sb.toString(), null);
	}

	@Override
	public List<Map> findRepairProject(Map<String, String> queryParam) {
		StringBuffer sb = new StringBuffer();
		sb.append(
				" SELECT B.CLAIM_LABOUR , COALESCE(A.REPAIR_TYPE_CODE,B.REPAIR_TYPE_CODE) as REPAIR_TYPE_CODE,A.MODEL_LABOUR_CODE,A.PACKAGE_LABOUR_ID,A.DEALER_CODE,A.PACKAGE_CODE,A.LABOUR_CODE, A.VER,")
				.append(" B.LABOUR_NAME,B.LOCAL_LABOUR_CODE,B.LOCAL_LABOUR_NAME,B.STD_LABOUR_HOUR,B.ASSIGN_LABOUR_HOUR,B.DOWN_TAG,B.OEM_LABOUR_HOUR ")
				.append(" FROM (" + CommonConstants.VM_PACKAGE_LABOUR + ") A ")
				.append(" LEFT JOIN (" + CommonConstants.VM_PACKAGE + ") C ON A.PACKAGE_CODE = C.PACKAGE_CODE ")
				.append(" AND A.DEALER_CODE = C.DEALER_CODE AND A.D_KEY = C.D_KEY ")
				.append(" LEFT JOIN (" + CommonConstants.VM_REPAIR_ITEM
						+ ") B ON A.DEALER_CODE = B.DEALER_CODE AND A.LABOUR_CODE = B.LABOUR_CODE")
				.append(" AND B.MODEL_LABOUR_CODE = A.MODEL_LABOUR_CODE ").append(" WHERE A.DEALER_CODE = '")
				.append(FrameworkUtil.getLoginInfo().getDealerCode()).append("' AND A.D_KEY = ")
				.append(CommonConstants.D_KEY);
		Utility.sqlToLike(sb, queryParam.get("packageNo"), "PACKAGE_CODE", "A");
		Utility.sqlToLike(sb, queryParam.get("packageName"), "PACKAGE_NAME", "C");
		if (!StringUtils.isNullOrEmpty(queryParam.get("modelLabourCode"))) {
			sb.append(" AND C.MODEL_LABOUR_CODE = '").append(queryParam.get("modelLabourCode")).append("'");
		}
		return DAOUtil.findAll(sb.toString(), null);
	}

	@Override
	public List<Map> findRepairPart(Map<String, String> queryParam) {
		String storageByUserId = Utility.getStorageByUserId(FrameworkUtil.getLoginInfo().getUserId());
		StringBuffer sb = new StringBuffer();
		sb.append(
				" select  X.DEALER_CODE,X.PACKAGE_PART_ID,X.PACKAGE_CODE,COALESCE(X.PART_NO,Y.PART_NO) as PART_NO,COALESCE(X.PART_NAME,Y.PART_NAME) as PART_NAME,X.PART_QUANTITY,COALESCE(X.D_KEY,Y.D_KEY) as D_KEY,COALESCE(X.PART_MODEL_GROUP_CODE_SET,Y.PART_MODEL_GROUP_CODE_SET) as PART_MODEL_GROUP_CODE_SET,X.LABOUR_CODE,X.MODEL_LABOUR_CODE,")
				.append("COALESCE(X.STORAGE_CODE,Y.STORAGE_CODE) as STORAGE_CODE ,Y.STORAGE_POSITION_CODE ,Y.SPELL_CODE ,Y.PART_GROUP_CODE,Y.UNIT_CODE ,Y.STOCK_QUANTITY ,Y.SALES_PRICE ,Y.CLAIM_PRICE ,Y.LIMIT_PRICE ,Y.LATEST_PRICE ,Y.INSURANCE_PRICE ,Y.COST_PRICE ,Y.COST_AMOUNT ,Y.MAX_STOCK ,Y.MIN_STOCK ,Y.BORROW_QUANTITY ,Y.LEND_QUANTITY ,Y.LOCKED_QUANTITY ,Y.")
				.append("PART_STATUS ,Y.JAN_MODULUS ,Y.FEB_MODULUS ,Y.MAR_MODULUS ,Y.APR_MODULUS ,Y.MAY_MODULUS ,Y.JUN_MODULUS ,Y.JUL_MODULUS ,Y.AUG_MODULUS ,Y.SEP_MODULUS ,Y.OCT_MODULUS ,Y.NOV_MODULUS ,Y.DEC_MODULUS ,Y.")
				.append("PART_SPE_TYPE ,Y.IS_SUGGEST_ORDER ,Y.MONTHLY_QTY_FORMULA ,Y.LAST_STOCK_IN ,Y.LAST_STOCK_OUT ,Y.FOUND_DATE ,Y.PART_MAIN_TYPE ,Y.ISAUTO_MAXMIN_STOCK ,Y.SAFE_STOCK ,Y.")
				.append("REMARK ,COALESCE(X.CREATED_BY,Y.CREATED_BY) as CREATED_BY ,COALESCE(X.CREATED_AT,Y.CREATED_AT) as CREATED_AT ,COALESCE(X.UPDATED_BY,Y.UPDATED_BY) as UPDATED_BY ,COALESCE(X.UPDATED_AT,Y.UPDATED_AT) as UPDATED_AT ,Y.VER ,Y.PROVIDER_CODE ,Y.PROVIDER_NAME ,Y.OTHER_PART_COST_PRICE ,Y.OTHER_PART_COST_AMOUNT ,Y.IS_BACK ,Y.DDCN_UPDATE_DATE ,Y.SLOW_MOVING_DATE ,Y.REPORT_B_I_DATETIME,")
				.append("y.SALES_PRICE PART_SALES_PRICE,COALESCE(Y.NODE_PRICE,z.NODE_PRICE) as NODE_PRICE,(y.STOCK_QUANTITY + y.BORROW_QUANTITY - y.LEND_QUANTITY - y.LOCKED_QUANTITY) AS USEABLE_STOCK,")
				.append(" COALESCE(x.PART_QUANTITY,0) * COALESCE(y.SALES_PRICE,0) PART_SALES_AMOUNT, COALESCE(x.PART_QUANTITY,0) * COALESCE(y.COST_PRICE,0) PART_COST_AMOUNT ")
				.append(" from ( select package_part_id,DEALER_CODE,package_code,part_no,part_name,part_quantity,d_key,CREATED_BY,CREATED_AT,UPDATED_BY,UPDATED_AT,part_model_group_code_set, ")
				.append(" labour_code,model_labour_code,case when (num is null and num2 is null) then 'OEMK'  when num is null then num2 else num end as storage_code ")
				.append(" from ( SELECT a.package_part_id,a.DEALER_CODE,a.package_code,a.part_no,a.part_name,a.part_quantity,a.d_key,a.CREATED_BY,a.CREATED_AT,a.UPDATED_BY,a.UPDATED_AT,a.part_model_group_code_set, ")
				.append(" a.labour_code,a.model_labour_code ,  case  when (a.storage_code is null) or (a.storage_code='') then   ( ")
				.append(" select  b.storage_code from TM_PART_STOCK B where  B.part_no=A.part_no  and a.DEALER_CODE=b.DEALER_CODE ")
				.append(" and ( b.storage_code='OEMK' and (b.STOCK_QUANTITY + b.BORROW_QUANTITY - b.LEND_QUANTITY - b.LOCKED_QUANTITY)>=a.PART_QUANTITY)) else  a.storage_code end as num ")
				.append(" ,	case  when (a.storage_code is null) or (a.storage_code='') then ( ")
				.append(" select  b.storage_code from TM_PART_STOCK B where  B.part_no=A.part_no  and a.DEALER_CODE=b.DEALER_CODE ")
				.append(" and b.storage_code in (").append(storageByUserId)
				.append(") and ((b.STOCK_QUANTITY + b.BORROW_QUANTITY - b.LEND_QUANTITY - b.LOCKED_QUANTITY)>=a.PART_QUANTITY) ")
				.append(" order by (b.STOCK_QUANTITY + b.BORROW_QUANTITY - b.LEND_QUANTITY - b.LOCKED_QUANTITY) desc LIMIT 1  ) else  a.storage_code end as num2  FROM ("
						+ CommonConstants.VM_PACKAGE_PART + ") A  LEFT JOIN (" + CommonConstants.VM_PACKAGE
						+ ") B ON A.PACKAGE_CODE = B.PACKAGE_CODE  AND A.DEALER_CODE = B.DEALER_CODE AND A.D_KEY = B.D_KEY ")
				.append(" where A.DEALER_CODE = ? and A.D_KEY = ? ")
				.append(" )c ) x   inner join TM_PART_STOCK y on x.DEALER_CODE=y.DEALER_CODE and x.part_no=y.part_no and x.storage_code=y.storage_code and X.D_KEY = Y.D_KEY")
				.append(" left join (" + CommonConstants.VM_PART_INFO
						+ ") z on z.DEALER_CODE=y.DEALER_CODE  and z.part_no=y.part_no and Z.D_KEY = Y.D_KEY")
				.append(" left join  (" + CommonConstants.VM_PACKAGE
						+ ") ZZ on ZZ.DEALER_CODE=X.DEALER_CODE and ZZ.package_code=x.package_code ")
				.append(" WHERE 1=1 ");
		Utility.sqlToLike(sb, queryParam.get("packageCode"), "PACKAGE_CODE", "ZZ");
		Utility.sqlToLike(sb, queryParam.get("packageName"), "PACKAGE_NAME", "ZZ");
		if (!StringUtils.isNullOrEmpty(queryParam.get("modelLabourCode"))) {
			sb.append(" AND ZZ.MODEL_LABOUR_CODE = '").append(queryParam.get("modelLabourCode")).append("'");
		}
//		//工单页面用于查询缺料明细相关信息
//		if (!StringUtils.isNullOrEmpty(queryParam.get("partNoBatch"))) {
//			sb.append(" AND X.PART_NO OR Y.PART_NO IN(").append(queryParam.get("partNoBatch")).append(")");
//		}
		List param = new ArrayList();
		param.add(FrameworkUtil.getLoginInfo().getDealerCode());
		param.add(CommonConstants.D_KEY);
		return DAOUtil.findAll(sb.toString(), param);
	}

}
