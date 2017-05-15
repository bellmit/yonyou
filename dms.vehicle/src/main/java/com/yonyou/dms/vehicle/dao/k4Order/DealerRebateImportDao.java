package com.yonyou.dms.vehicle.dao.k4Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.vehicle.domains.DTO.k4Order.TmpVsRebateImpDTO;
import com.yonyou.dms.vehicle.domains.PO.k4Order.TmpVsRebateImpPO;
import com.yonyou.dms.vehicle.domains.PO.k4Order.TtVsRebateTypePO;

/**
 * @author liujiming
 * @date 2017年3月21日
 */
@Repository
public class DealerRebateImportDao {
	/**
	 * 
	 * @Title: deleteTmpVsRebateImp @Description: 清空临时表中返利上传的数据 @return
	 *         void @throws
	 */
	public void deleteTmpVsRebateImp() {
		// 获取当前用户
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		TmpVsRebateImpPO.delete(" USER_ID = ?", loginInfo.getUserId().toString());
	}

	/**
	 * 
	 * @Title: insertTmpVsRebateImp @Description: 年度目标上传(导入临时表) @param rowDto
	 *         设定文件 @return void 返回类型 @throws
	 */
	public void insertTmpVsRebateImp(TmpVsRebateImpDTO tvriDto) {

		TmpVsRebateImpPO tvriPO = new TmpVsRebateImpPO();
		// 设置对象属性
		TtVsRebateTypePO tvrtPo = TtVsRebateTypePO.findFirst("  CODE_ID = ? ", tvriDto.getRebateType());
		tvriPO.setString("REBATE_CODE", tvrtPo.get("TYPE_CODE"));
		tvriPO.setString("ROW_DECIMAL", tvriDto.getRowNO());

		tvriPO.setString("DEALER_CODE", tvriDto.getDealerCode());

		tvriPO.setString("REBATE_TYPE", tvriDto.getRebateType());
		tvriPO.setString("REBATE_AMOUNT", tvriDto.getRebateAmount());
		tvriPO.setString("REMARK", tvriDto.getRemark());
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		tvriPO.setString("USER_ID", loginInfo.getUserId().toString());

		tvriPO.setString("VIN", tvriDto.getVin());

		tvriPO.insert();
	}

	/**
	 * 经销商返利上传 临时表查询
	 */
	public List<Map> dealerRebateImportQuery() {
		List<Object> params = new ArrayList<Object>();
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		StringBuilder sql = new StringBuilder();
		sql.append(
				"		SELECT DEALER_CODE,VIN,(SELECT code_desc AS REBATE_TYPE FROM TT_VS_REBATE_TYPE WHERE  code_id = REBATE_TYPE) CODE_DESC, \n");
		sql.append("				REBATE_AMOUNT,REBATE_CODE,REBATE_TYPE,REMARK,USER_ID,ROW_DECIMAL \n");
		sql.append("		  FROM TMP_VS_REBATE_IMP  \n");
		sql.append("		   WHERE USER_ID= " + loginInfo.getUserId().toString() + " \n");

		List<Map> orderList = OemDAOUtil.findAll(sql.toString(), params);
		return orderList;
	}

	public List<Map> findTempDealerList(LoginInfoDto loginInfo) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("\n");
		sql.append("		SELECT t1.ROW_DECIMAL \n");
		sql.append("		FROM TMP_VS_REBATE_IMP t1  \n");
		sql.append("		WHERE 1 = 1  \n");
		sql.append("		AND t1.USER_ID =  " + loginInfo.getUserId() + "  \n");
		sql.append("		AND NOT EXISTS (SELECT 1  \n");
		sql.append("		FROM TM_DEALER T2   \n");
		sql.append("		WHERE T2.DEALER_CODE=t1.DEALER_CODE   \n");

		if (String.valueOf(OemDictCodeConstants.DUTY_TYPE_LARGEREGION).equals(loginInfo.getDutyType())
				|| String.valueOf(OemDictCodeConstants.DUTY_TYPE_SMALLREGION).equals(loginInfo.getDutyType())) {
			sql.append("   AND T2.DEALER_ID IN(SELECT DEALER_ID FROM TM_DEALER_ORG_RELATION WHERE ORG_ID="
					+ loginInfo.getDutyType() + ")\n"); // 组织DUTY_TYPE
		}

		sql.append("            and T2.OEM_COMPANY_ID =  " + loginInfo.getCompanyId() + "\n");
		sql.append("		  AND  T2.STATUS = " + OemDictCodeConstants.STATUS_ENABLE + " \n"); // 有效
		sql.append("		) \n");
		sql.append("		  ORDER BY  CAST(t1.ROW_DECIMAL AS SIGNED) asc \n");
		List<Map> orderList = OemDAOUtil.findAll(sql.toString(), params);
		return orderList;
	}

}
