package com.yonyou.dms.customer.service.suggestQuery;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;

@Service
public class suggestQueryServiceImpl implements suggestRepairService {

	@Override
	public PageInfoDto querySuggestRepair(Map<String, String> queryParam) throws ServiceBizException, SQLException {
		List<Object> queryList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("");
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		sql.append(
				" SELECT A.DEALER_CODE,C.ADDRESS,B.LICENSE ,A.RO_NO,A.IS_VALID,A.SUGGEST_MAINTAIN_PART_ID,A.PART_NO,A.PART_NAME,"
						+ " A.SUGGEST_DATE,A.SALES_PRICE,A.QUANTITY,A.REASON,B.SERVICE_ADVISOR,A.VIN,B.ENGINE_NO,db.dealer_shortname FROM TT_SUGGEST_MAINTAIN_PART A "
						+ " LEFT JOIN TT_REPAIR_ORDER B ON A.DEALER_CODE = B.DEALER_CODE AND A.RO_NO = B.RO_NO AND B.D_KEY = "
						+ CommonConstants.D_KEY + " LEFT JOIN (" + CommonConstants.VM_OWNER
						+ ") C ON A.DEALER_CODE = C.DEALER_CODE AND B.OWNER_NO = C.OWNER_NO "
						+ " LEFT JOIN TM_DEALER_BASICINFO db ON A.dealer_code=db.dealer_code  "
						+ " WHERE A.DEALER_CODE ='" + dealerCode + "'  AND A.D_KEY = " + CommonConstants.D_KEY);

		sql.append(Utility.getLikeCond("B", "LICENSE", queryParam.get("license"), "AND"));
		sql.append(Utility.getLikeCond("C", "ADDRESS", queryParam.get("address"), "AND"));
		sql.append(Utility.getLikeCond("B", "ENGINE_NO", queryParam.get("engineNo"), "AND"));

		if (!StringUtils.isNullOrEmpty(queryParam.get("applicant"))) {
			sql.append(" AND B.SERVICE_ADVISOR = '" + queryParam.get("applicant") + "'");
		}

		if (!StringUtils.isNullOrEmpty(queryParam.get("ownerNo"))) {
			sql.append(" AND B.OWNER_NO = '" + (queryParam.get("ownerNo") + "'"));
		}

		if (!StringUtils.isNullOrEmpty(queryParam.get("suggestBeginDate"))) {
			sql.append(" AND A.SUGGEST_DATE >= ?");
			queryList.add(queryParam.get("suggestBeginDate"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("suggestEndDate"))) {
			sql.append(" AND A.SUGGEST_DATE <= ?");
			queryList.add(queryParam.get("suggestEndDate"));
		}

		sql.append(Utility.getLikeCond("A", "VIN", queryParam.get("vin"), "AND"));

		PageInfoDto pageInfoDto = DAOUtil.pageQuery(sql.toString(), queryList);
		return pageInfoDto;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Map> exportSuggestRepair(Map<String, String> queryParam) throws ServiceBizException, SQLException {
		List<Object> queryList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("");
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		sql.append(
				" SELECT C.DEALER_CODE,C.ADDRESS,B.LICENSE ,A.RO_NO,A.IS_VALID,A.SUGGEST_MAINTAIN_PART_ID,A.PART_NO,A.PART_NAME,"
						+ " A.SUGGEST_DATE,A.SALES_PRICE,A.QUANTITY,A.REASON,B.SERVICE_ADVISOR,A.VIN,B.ENGINE_NO FROM TT_SUGGEST_MAINTAIN_PART A "
						+ " LEFT JOIN TT_REPAIR_ORDER B ON A.DEALER_CODE = B.DEALER_CODE AND A.RO_NO = B.RO_NO AND B.D_KEY = "
						+ CommonConstants.D_KEY + " LEFT JOIN (" + CommonConstants.VM_OWNER
						+ ") C ON A.DEALER_CODE = C.DEALER_CODE AND B.OWNER_NO = C.OWNER_NO "
						+ " WHERE A.DEALER_CODE ='" + dealerCode + "'  AND A.D_KEY = " + CommonConstants.D_KEY);

		sql.append(Utility.getLikeCond("B", "LICENSE", queryParam.get("license"), "AND"));
		sql.append(Utility.getLikeCond("C", "ADDRESS", queryParam.get("address"), "AND"));
		sql.append(Utility.getLikeCond("B", "ENGINE_NO", queryParam.get("engineNo"), "AND"));

		if (!StringUtils.isNullOrEmpty(queryParam.get("applicant"))) {
			sql.append(" AND B.SERVICE_ADVISOR = '" + queryParam.get("applicant") + "'");
		}

		if (!StringUtils.isNullOrEmpty(queryParam.get("ownerNo"))) {
			sql.append(" AND B.OWNER_NO = '" + (queryParam.get("ownerNo") + "'"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("suggestBeginDate"))) {
			sql.append(" AND A.SUGGEST_DATE >= ?");
			queryList.add(queryParam.get("suggestBeginDate"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("suggestEndDate"))) {
			sql.append(" AND A.SUGGEST_DATE <= ?");
			queryList.add(queryParam.get("suggestEndDate"));
		}

		sql.append(Utility.getLikeCond("A", "VIN", queryParam.get("vin"), "AND"));
		List<Map> list = DAOUtil.findAll(sql.toString(), queryList);
		for (Map map : list) {
			if (map.get("REASON") != null && map.get("REASON") != "") {
				if (Integer
						.parseInt(map.get("REASON").toString()) == DictCodeConstants.DICT_SUGGEST_REASON_PRICE_HIGH) {
					map.put("REASON", "价格太高");
				} else if (Integer
						.parseInt(map.get("REASON").toString()) == DictCodeConstants.DICT_SUGGEST_REASON_SHORT_PART) {
					map.put("REASON", "缺件/料");
				} else if (Integer
						.parseInt(map.get("REASON").toString()) == DictCodeConstants.DICT_SUGGEST_REASON_CLIENT) {
					map.put("REASON", "客户不修");
				} else if (Integer
						.parseInt(map.get("REASON").toString()) == DictCodeConstants.DICT_SUGGEST_REASON_OTHER) {
					map.put("REASON", "其他");
				}
			}

		}
		return list;
	}

}
