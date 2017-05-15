package com.yonyou.dcs.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.DTO.gacfca.SADCS032Dto;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.common.OemDictCodeConstants;

@Repository
public class SADCS032DAO extends OemBaseDAO {
	/**
	 * 下发的车系数据
	 * 
	 * @return
	 */
	public List<SADCS032Dto> queryPolicyApplyDateInfo() {
		StringBuffer sql = new StringBuffer();
		sql.append("select     \n");
		sql.append("    TBCAD.BIG_CUSTOMER_APPLY_ID,    \n");
		sql.append("    TBCAD.PS_TYPE,    \n");
		sql.append("    TBCAD.EMPLOYEE_TYPE,    \n");
		sql.append("    TBCAD.NUMBER,    \n");
		sql.append("    TBCAD.STATUS,    \n");
		sql.append("    TBCAD.IS_DELETE    \n");
		sql.append("FROM TT_BIG_CUSTOMER_APPLY_DATA TBCAD    \n");
		sql.append("WHERE TBCAD.IS_SCAN = 0    \n");
		List<Map> findAll = OemDAOUtil.findAll(sql.toString(), null);
		List<SADCS032Dto> list = new ArrayList<>();
		for (Map map : findAll) {
			SADCS032Dto dto = new SADCS032Dto();
			// if
			// (!"".equals(CommonUtils.checkNull(map.get("BIG_CUSTOMER_APPLY_ID"))))
			// {
			// dto.setBigCustomerApplyId(Long.parseLong(map.get("BIG_CUSTOMER_APPLY_ID").toString()));
			// }
			// if (!"".equals(CommonUtils.checkNull(map.get("EMPLOYEE_TYPE"))))
			// {
			// dto.setEmployeeType(Integer.parseInt(map.get("EMPLOYEE_TYPE").toString()));
			// }
			// if (!"".equals(CommonUtils.checkNull(map.get("IS_DELETE")))) {
			// dto.setIsDelete(Integer.parseInt(map.get("IS_DELETE").toString()));
			// }
			// if (!"".equals(CommonUtils.checkNull(map.get("NUMBER")))) {
			// dto.setNumber(Integer.parseInt(map.get("NUMBER").toString()));
			// }
			// if (!"".equals(CommonUtils.checkNull(map.get("PS_TYPE")))) {
			// dto.setPsType(Integer.parseInt(map.get("PS_TYPE").toString()));
			// }
			// if (!"".equals(CommonUtils.checkNull(map.get("STATUS")))) {
			// dto.setStatus(Integer.parseInt(map.get("STATUS").toString()));
			// }

			dto.setBigCustomerApplyId(Long.parseLong(map.get("BIG_CUSTOMER_APPLY_ID").toString()));
			int psType = Integer.parseInt(map.get("PS_TYPE").toString());
			dto.setPsType(Integer.parseInt(map.get("PS_TYPE").toString()));
			int employeeType = Integer.parseInt(map.get("EMPLOYEE_TYPE").toString());
			if (psType == OemDictCodeConstants.BIG_CUSTOMER_BATCH_SALE_TYPE_GROUP_BUY) {// 团购
				if (employeeType == OemDictCodeConstants.EMPLOYEE_TYPE_01) {
					dto.setEmployeeType(61201005);// 一般大客户
				} else if (employeeType == OemDictCodeConstants.EMPLOYEE_TYPE_02) {
					dto.setEmployeeType(61201002);// 知名企业
				} else if (employeeType == OemDictCodeConstants.EMPLOYEE_TYPE_03) {
					dto.setEmployeeType(61201010);// 平安集团
				} else if (employeeType == OemDictCodeConstants.EMPLOYEE_TYPE_04) {
					dto.setEmployeeType(61201011);// 公务员
				} else if (employeeType == OemDictCodeConstants.EMPLOYEE_TYPE_05) {
					dto.setEmployeeType(61201008);// 知名院校、医院
				} else if (employeeType == OemDictCodeConstants.EMPLOYEE_TYPE_06) {
					dto.setEmployeeType(61201009);// 合作银行
				}
			} else if (psType == OemDictCodeConstants.BIG_CUSTOMER_BATCH_SALE_TYPE_GROUP) {// 集团销售
				if (employeeType == OemDictCodeConstants.EMPLOYEE_TYPE1_01) {
					dto.setEmployeeType(61201002);// 知名企业
				} else if (employeeType == OemDictCodeConstants.EMPLOYEE_TYPE1_02) {
					dto.setEmployeeType(61201003);// 酒店/航空
				} else if (employeeType == OemDictCodeConstants.EMPLOYEE_TYPE1_03) {
					dto.setEmployeeType(61201004);// 租赁
				} else if (employeeType == OemDictCodeConstants.EMPLOYEE_TYPE1_04) {
					dto.setEmployeeType(61201012);// 一般企业
				} else if (employeeType == OemDictCodeConstants.EMPLOYEE_TYPE1_05) {
					dto.setEmployeeType(61201013);// 政府
				}
			}
			dto.setNumber(Integer.parseInt(map.get("NUMBER").toString()));
			dto.setStatus(Integer.parseInt(map.get("STATUS").toString()));
			dto.setIsDelete(Integer.parseInt(map.get("IS_DELETE").toString()));

			list.add(dto);

		}

		return list;

	}

}
