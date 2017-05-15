package com.yonyou.dms.repair.dao.k4RebateManage;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * 
 * @author lianxinglu
 *
 */
@Repository
@SuppressWarnings("all")
public class AccountDao extends OemBaseDAO {
	// 经销商返利余额查询
	public PageInfoDto rebateBalanceQuery(Map<String, String> queryParam) {
		String sql = getQuerySql(queryParam, null);
		return OemDAOUtil.pageQuery(sql, null);
	}

	// 经销商返利余额查询下载
	public List<Map> doDownload(Map<String, String> queryParam) {
		String sql = getQuerySql(queryParam, null);
		return OemDAOUtil.downloadPageQuery(sql, null);
	}

	//
	public List<Map> rebateBalanceDealerQuery(Map<String, String> queryParam) {
		String sql = getQuerySql(queryParam, null);
		return OemDAOUtil.findAll(sql, null);
	}

	// 返利使用明细查询
	public PageInfoDto rebateUseDetailQuery(Map<String, String> queryParam) {
		String sql3 = getQuerySql3(queryParam, null);
		System.out.println(sql3);
		return OemDAOUtil.pageQuery(sql3, null);
	}

	// 返利使用明细查询下载
	public List<Map> rebateUseDetailDownLoad(Map<String, String> queryParam) {
		String sql3 = getQuerySql3(queryParam, null);
		System.out.println(sql3);
		return OemDAOUtil.downloadPageQuery(sql3, null);
	}

	private String getQuerySql3(Map<String, String> queryParam, List<Object> params) {

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT D.DEALER_CODE, -- 经销商代码 \n");
		sql.append("       D.DEALER_SHORTNAME, -- 经销商简称 \n");
		sql.append("       DRT.REBATE_TYPE_DESC, -- 返利使用类型 \n");
		sql.append("       DRD.ORDER_NO, -- 订单号 \n");
		sql.append("       M.SERIES_NAME, -- 车系 \n");
		sql.append("       DRD.VIN, -- 车架号 \n");
		sql.append("       DRD.AMOUNT, -- 返利使用金额 \n");
		sql.append("      (DRD.CREATE_DATE) AS OPERA_DATE, -- 返利使用日期 \n");
		sql.append("       DRD.REMARK -- 备注 \n");
		sql.append("  FROM TM_DEALER D \n");
		sql.append(" INNER JOIN TT_DEALER_REBATE DR ON DR.DEALER_ID = D.DEALER_ID \n");
		sql.append(" INNER JOIN TT_DEALER_REBATE_DTL DRD ON DRD.REBATE_ID = DR.REBATE_ID \n");
		sql.append(" INNER JOIN TT_DEALER_REBATE_TYPE DRT ON DRT.REBATE_TYPE_ID = DRD.TYPE_ID \n");
		sql.append("  LEFT JOIN TM_VEHICLE_DEC V ON V.VIN = DRD.VIN \n");
		sql.append("  LEFT JOIN (" + getVwMaterialSql() + ")  M ON M.MATERIAL_ID = V.MATERIAL_ID \n");
		sql.append(" WHERE DRD.REBATE_DIRECTION = '" + OemDictCodeConstants.REBATE_DIRACT_02 + "' \n");
		sql.append("   AND D.DEALER_TYPE = '" + OemDictCodeConstants.DEALER_TYPE_DVS + "' \n");
		sql.append("   AND D.STATUS = '" + OemDictCodeConstants.STATUS_ENABLE + "' \n");

		// 订单号
		if (!StringUtils.isNullOrEmpty(queryParam.get("orderNo"))) {
			sql.append("   AND DRD.ORDER_NO LIKE '%" + queryParam.get("orderNo") + "%' \n");
		}

		// 经销商代码
		if (!StringUtils.isNullOrEmpty(queryParam.get("dealerCode"))) {

			String dealerCode = "";
			String[] dealerCodeArray = queryParam.get("dealerCode").split(",");

			for (int i = 0; i < dealerCodeArray.length; i++) {

				if (dealerCodeArray.length == (i + 1)) {
					dealerCode += "'" + dealerCodeArray[i] + "'";
				} else {
					dealerCode += "'" + dealerCodeArray[i] + "', ";
				}
			}
			sql.append("   AND D.DEALER_CODE IN (" + dealerCode + ") \n");
		}

		// 返利使用日期 Begin
		if (!StringUtils.isNullOrEmpty(queryParam.get("beginDate"))) {
			sql.append("   AND    DRD.CREATE_DATE >= '" + queryParam.get("beginDate") + "' \n");
		}

		// 返利使用日期 End
		if (!StringUtils.isNullOrEmpty(queryParam.get("endDate"))) {
			sql.append("   AND DRD.CREATE_DATE <= '" + queryParam.get("endDate") + "' \n");
		}

		// 车架号
		if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
			sql.append("   AND DRD.VIN LIKE '%" + queryParam.get("vin") + "%' \n");
		}
		String string = queryParam.get("typeId");
		// 返利使用类型
		if (!StringUtils.isNullOrEmpty(queryParam.get("typeId"))) {
			sql.append("   AND DRT.REBATE_TYPE_CODE = '" + queryParam.get("typeId") + "' \n");
		}

		return sql.toString();
	}

	private String getQuerySql(Map<String, String> queryParam, List<Object> params) {
		StringBuffer sql = new StringBuffer();
		sql.append("  SELECT d.DEALER_ID,d.DEALER_CODE  ,D.DEALER_SHORTNAME, \n");
		sql.append("  (case when t.REBATE_POOL_AMOUNT=null then '0' else t.REBATE_POOL_AMOUNT end)  AS CANUSE, \n");
		sql.append("  t.USED_AMOUNT  AS USED ,\n");
		sql.append("  t.AVAILABEL_AMOUNT  AS ACCOUNT \n");
		sql.append("  from TT_DEALER_REBATE t right join TM_DEALER d on t.DEALER_ID = d.DEALER_ID \n");
		sql.append(" WHERE 1=1 ");
		// 经销商
		if (!StringUtils.isNullOrEmpty(queryParam.get("dealerCode"))) {
			String dealerCode = queryParam.get("dealerCode");
			String s = "";
			if (dealerCode.indexOf(",") > 0) {
				String[] str = dealerCode.split(",");
				for (int i = 0; i < str.length; i++) {
					s += "'" + str[i] + "'";
					if (i < str.length - 1)
						s += ",";
				}
			} else {
				s = "'" + dealerCode + "'";
			}
			sql.append("   AND DEALER_CODE IN (" + s + ") \n");
		}

		sql.append(" AND D.STATUS =  " + OemDictCodeConstants.STATUS_ENABLE);
		sql.append(" AND D.IS_DEL = " + OemDictCodeConstants.IS_DEL_00);
		sql.append(" AND D.DEALER_TYPE =  " + OemDictCodeConstants.DEALER_TYPE_DVS);
		// sql.append(" ORDER BY D.DEALER_CODE,D.DEALER_SHORTNAME \n");
		System.out.println(sql.toString());
		return sql.toString();
	}

}
