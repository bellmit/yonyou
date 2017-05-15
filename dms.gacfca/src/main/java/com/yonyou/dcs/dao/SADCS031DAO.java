package com.yonyou.dcs.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.DTO.gacfca.SADCS031Dto;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.utils.common.CommonUtils;

@Repository
public class SADCS031DAO extends OemBaseDAO {
	/**
	 * 下发的车系
	 * 
	 * @return
	 */
	public List<SADCS031Dto> queryPolicySeriesInfo() {
		StringBuffer sql = new StringBuffer();
		sql.append("select   \n");
		sql.append("  TBCPS.BIG_CUSTOMER_POLICY_ID,  \n");
		sql.append("  TBCPS.PS_TYPE,    \n");
		sql.append("  TBCPS.BRAND_CODE,  \n");
		sql.append("  TBCPS.SERIES_CODE,  \n");
		sql.append("  TBCPS.STATUS,  \n");
		sql.append("  TBCPS.IS_DELETE  \n");
		sql.append("FROM TT_BIG_CUSTOMER_POLICY_SERIES TBCPS  \n");
		sql.append("WHERE TBCPS.IS_SCAN = 0  \n");
		List<Map> findAll = OemDAOUtil.findAll(sql.toString(), null);
		List<SADCS031Dto> list = new ArrayList<SADCS031Dto>();
		for (Map map : findAll) {

			SADCS031Dto dto = new SADCS031Dto();
			String brand_code = CommonUtils.checkNull(map.get("BRAND_CODE"));
			if (!"".equals(brand_code)) {
				dto.setBrandCode(brand_code);
			}
			if (!"".equals(CommonUtils.checkNull(map.get("IS_DELETE")))) {
				dto.setIsDelete(Integer.parseInt(map.get("IS_DELETE").toString()));
			}
			if (!"".equals(CommonUtils.checkNull(map.get("BIG_CUSTOMER_POLICY_ID")))) {
				dto.setBigCustomerPolicyId(Long.parseLong(map.get("BIG_CUSTOMER_POLICY_ID").toString()));
			}
			if (!"".equals(CommonUtils.checkNull(map.get("PS_TYPE")))) {
				dto.setPsType(Integer.parseInt(map.get("PS_TYPE").toString()));
			}
			if (!"".equals(CommonUtils.checkNull(map.get("STATUS")))) {
				dto.setStatus(Integer.parseInt(map.get("STATUS").toString()));
			}
			if (!"".equals(CommonUtils.checkNull(map.get("SERIES_CODE")))) {
				dto.setSeriesCode(map.get("SERIES_CODE").toString());
			}

			list.add(dto);

		}

		return list;
	}

}
