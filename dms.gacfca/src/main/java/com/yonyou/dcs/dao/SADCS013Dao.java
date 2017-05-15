package com.yonyou.dcs.dao;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.DTO.gacfca.PoCusWholeClryslerDto;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.common.OemDictCodeConstants;

@Repository
public class SADCS013Dao extends OemBaseDAO {

	/**
	 * 查询大客户报备审批信息
	 * 
	 * @throws ParseException
	 */
	public List<PoCusWholeClryslerDto> queryBigCustomerFilingApprovalInfo(String wsNo, String dealerCode)
			throws ParseException {
		List<Object> params = new ArrayList<Object>();
		StringBuffer strSql = new StringBuffer(" SELECT TU.NAME,TBCRA.DEALER_CODE,TBCRA.WS_NO,"
				+ " TBCRA.REPORT_APPROVAL_STATUS," + " TBCRA.REPORT_APPROVAL_DATE,TBCRA.REPORT_APPROVAL_REMARK "
				+ " FROM TT_BIG_CUSTOMER_REPORT_APPROVAL TBCRA INNER JOIN TC_USER TU "
				+ " ON TBCRA.REPORT_APPROVAL_USER_ID = TU.USER_ID" + " WHERE TBCRA.WS_NO = '" + wsNo
				+ "' AND TBCRA.DEALER_CODE = " + dealerCode + " ");

		List<PoCusWholeClryslerDto> lis = new ArrayList<>();
		System.out.println(strSql.toString());
		List<Map> list = OemDAOUtil.findAll(strSql.toString(), null);
		for (Map map : list) {
			PoCusWholeClryslerDto dato = new PoCusWholeClryslerDto();
			Map<String, Object> outDmsDealer = getDmsDealerCode(map.get("DEALER_CODE").toString());
			String dmsCode = outDmsDealer.get("DMS_CODE").toString();
			dato.setDealerCode(dmsCode);
			dato.setWsNo(map.get("WS_NO").toString());
			dato.setWsAuditor(map.get("NAME").toString());
			if (Integer.parseInt(map.get("REPORT_APPROVAL_STATUS")
					.toString()) == OemDictCodeConstants.BIG_CUSTOMER_FILING_APPROVAL_TYPE_PASS) {
				dato.setWsStatus(15981003); // 下端审核状态 -审核通过
			} else if (Integer.parseInt(map.get("REPORT_APPROVAL_STATUS")
					.toString()) == OemDictCodeConstants.BIG_CUSTOMER_FILING_APPROVAL_TYPE_OVER) {
				dato.setWsStatus(15981004); // 下端审核状态 -审核驳回
			} else if (Integer.parseInt(map.get("REPORT_APPROVAL_STATUS")
					.toString()) == OemDictCodeConstants.BIG_CUSTOMER_FILING_APPROVAL_TYPE_RUS) {
				dato.setWsStatus(15981005); // 下端审核状态 -审核拒绝
			} else if (Integer.parseInt(map.get("REPORT_APPROVAL_STATUS")
					.toString()) == OemDictCodeConstants.BIG_CUSTOMER_FILING_APPROVAL_TYPE_REPORT) {
				dato.setWsStatus(15981007); // 下端审核状态 -审核报备转拒绝
			}
			dato.setWsAuditingRemark(map.get("REPORT_APPROVAL_REMARK").toString());
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date parse = df.parse(map.get("REPORT_APPROVAL_DATE").toString());
			// DateUtil.ddMMyyyy2Date(ddMMyyyy, token)
			dato.setAuditingDate(parse);

			lis.add(dato);
		}
		return lis;
	}

}
