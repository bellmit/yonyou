package com.yonyou.dcs.dao;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.DTO.gacfca.SADMS017Dto;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.common.OemDictCodeConstants;

@Repository
public class SADCS017Dao extends OemBaseDAO {
	/**
	 * 查询二手车返利审批信息
	 * 
	 * @param dlr
	 * 
	 * @throws ParseException
	 */
	public List<SADMS017Dto> getVehicelOwnerInfo(String replaceApplyNo) throws ParseException {
		List<Object> params = new ArrayList<Object>();
		StringBuffer strSql = new StringBuffer(
				" SELECT TU.NAME,TURA.DEALER_CODE,TURA.REPLACE_APPLY_NO,TURA.REBATE_APPROVAL_STATUS,"
						+ " TURA.REBATE_APPROVAL_DATE,TURA.REBATE_APPROVAL_REMARK"
						+ " FROM TT_UC_REBATE_APPROVAL_DCS TURA INNER JOIN TC_USER TU "
						+ " ON TURA.REBATE_APPROVAL_USER_ID = TU.USER_ID WHERE TURA.REPLACE_APPLY_NO = '"
						+ replaceApplyNo + "'");
		// params.add(replaceApplyNo);
		System.out.println(strSql.toString());
		List<Map> list1 = OemDAOUtil.findAll(strSql.toString(), null);
		List<SADMS017Dto> list = new ArrayList<>();
		for (Map map : list1) {
			SADMS017Dto dto = new SADMS017Dto();
			Map<String, Object> outDmsDealer = getDmsDealerCode(map.get("DEALER_CODE").toString());
			String dmsCode = outDmsDealer.get("DMS_CODE").toString();
			dto.setDealerCode(dmsCode);
			dto.setSoNo(map.get("REPLACE_APPLY_NO").toString());
			dto.setAuditBy(map.get("NAME").toString());
			if (Integer.parseInt(map.get("REBATE_APPROVAL_STATUS")
					.toString()) == OemDictCodeConstants.UC_REBATE_APPROVAL_TYPE_PASS) {
				dto.setSoStatus(15951003); // 下端审核状态 -审核通过
			} else if (Integer.parseInt(map.get("REBATE_APPROVAL_STATUS")
					.toString()) == OemDictCodeConstants.UC_REBATE_APPROVAL_TYPE_OVER) {
				dto.setSoStatus(15951004); // 下端审核状态 -审核修改相当于上端驳回
			}
			dto.setAuditRemark(map.get("REBATE_APPROVAL_REMARK").toString());
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date rebate_approval_date = df.parse(map.get("REBATE_APPROVAL_DATE").toString());
			dto.setAuditDate(rebate_approval_date);
			dto.setSoNo(map.get("REPLACE_APPLY_NO").toString());
			map.get("REPLACE_APPLY_NO").toString();
			list.add(dto);
		}
		return list;
	}

}
