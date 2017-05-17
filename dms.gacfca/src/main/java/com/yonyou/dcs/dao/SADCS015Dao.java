package com.yonyou.dcs.dao;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.infoservice.dms.cgcsl.vo.PoCusWholeRepayClryslerVO;
import com.yonyou.dms.DTO.gacfca.PoCusWholeRepayClryslerDto;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.f4.filestore.FileStoreException;

@Repository
public class SADCS015Dao extends OemBaseDAO {
	/**
	 * 查询大客户报备返利审批信息
	 */
	private String remark;

	public void SetRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 查询下发数据 DTO
	 */
	public List<PoCusWholeRepayClryslerDto> queryBigCustomerRebateApprovalInfo(String wsNo, String dealerCode)
			throws ParseException, FileStoreException {
		List<Object> params = new ArrayList<Object>();
		StringBuffer strSql = new StringBuffer(" SELECT TU.NAME,TBCRA.DEALER_CODE,TBCRA.WS_NO,"
				+ " TBCRA.REBATE_APPROVAL_STATUS," + " TBCRA.REBATE_APPROVAL_DATE,TBCRA.REBATE_APPROVAL_REMARK, "
				+ " TBCRA.APPROVAL_FILE " + " ,TBCRA.ACTIVITY_DATE "
				+ " FROM TT_BIG_CUSTOMER_REBATE_APPROVAL TBCRA INNER JOIN TC_USER TU "
				+ " ON TBCRA.REBATE_APPROVAL_USER_ID = TU.USER_ID WHERE TBCRA.WS_NO = '" + wsNo
				+ "' AND TBCRA.DEALER_CODE = " + dealerCode + " ");
		// params.add(wsNo);
		// params.add(dealerCode);
		System.out.println(strSql.toString());
		List<PoCusWholeRepayClryslerDto> lis = new ArrayList<>();
		List<Map> list = OemDAOUtil.findAll(strSql.toString(), null);
		for (Map map : list) {
			PoCusWholeRepayClryslerDto dto = new PoCusWholeRepayClryslerDto();
			dto.setWsNo(map.get("WS_NO").toString());

			Map<String, Object> outDmsDealer = getDmsDealerCode(map.get("DEALER_CODE").toString());
			String dmsCode = outDmsDealer.get("DMS_CODE").toString();
			dto.setDealerCode(dmsCode);
			dto.setWsNo(map.get("WS_NO").toString());
			dto.setWsAuditor(map.get("NAME").toString());
			if (Integer.parseInt(map.get("REBATE_APPROVAL_STATUS")
					.toString()) == OemDictCodeConstants.BIG_CUSTOMER_REBATE_APPROVAL_TYPE_PASS) {
				dto.setSoStatus(16101003); // 下端审核状态 -审核通过
			} else if (Integer.parseInt(map.get("REBATE_APPROVAL_STATUS")
					.toString()) == OemDictCodeConstants.BIG_CUSTOMER_REBATE_APPROVAL_TYPE_OVER) {
				dto.setSoStatus(16101004); // 下端审核状态 -审核驳回
			} else if (Integer.parseInt(map.get("REBATE_APPROVAL_STATUS")
					.toString()) == OemDictCodeConstants.BIG_CUSTOMER_REBATE_APPROVAL_TYPE_RUS) {
				dto.setSoStatus(16101005); // 下端审核状态 -审核拒绝
			} else if (Integer.parseInt(map.get("REBATE_APPROVAL_STATUS")
					.toString()) == OemDictCodeConstants.BIG_CUSTOMER_REBATE_APPROVAL_TYPE_COMPLETE_INFORMATION) {
				dto.setSoStatus(16101006); // 下端审核状态 -资料完整待审批
			} else if (Integer.parseInt(map.get("REBATE_APPROVAL_STATUS")
					.toString()) == OemDictCodeConstants.BIG_CUSTOMER_REBATE_APPROVAL_TYPE_SYSTEM_RUS) {
				dto.setSoStatus(16101007); // 下端审核状态 -系统转拒绝
			} else if (Integer.parseInt(map.get("REBATE_APPROVAL_STATUS")
					.toString()) == OemDictCodeConstants.BIG_CUSTOMER_REBATE_APPROVAL_TYPE_OVER_RUS) {
				dto.setSoStatus(16101008); // 下端审核状态 -驳回转拒绝
			}
			if (!CommonUtils.checkIsNullStr(remark)) {
				dto.setWsAuditingRemark(map.get("REBATE_APPROVAL_REMARK") + "-" + remark);
			} else {
				dto.setWsAuditingRemark(map.get("REBATE_APPROVAL_REMARK").toString());
			}
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String rebate_approval_date = CommonUtils.checkNull(map.get("REBATE_APPROVAL_DATE").toString());
			if (!rebate_approval_date.equals("")) {
				Date parse = df.parse(map.get("REBATE_APPROVAL_DATE").toString());
				dto.setAuditingDate(parse);
			}
			String activity_date;
			try {
				activity_date = CommonUtils.checkNull(map.get("ACTIVITY_DATE"));
				if (!activity_date.equals("")) {
					Date parse1 = df.parse(map.get("ACTIVITY_DATE").toString());
					dto.setActivityDate(parse1);
				}
				dto.setHeadApprovalFileUrl(CommonUtils.checkNull(map.get("APPROVAL_FILE")));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			lis.add(dto);
		}

		return lis;
	}
	/**
	 * 查询下发数据 VO(供DE接口使用)
	 */
	public List<PoCusWholeRepayClryslerVO> queryBigCustomerRebateApprovalVoInfo(String wsNo, String dealerCode)
			throws ParseException, FileStoreException {
		List<Object> params = new ArrayList<Object>();
		StringBuffer strSql = new StringBuffer(" SELECT TU.NAME,TBCRA.DEALER_CODE,TBCRA.WS_NO,"
				+ " TBCRA.REBATE_APPROVAL_STATUS," + " TBCRA.REBATE_APPROVAL_DATE,TBCRA.REBATE_APPROVAL_REMARK, "
				+ " TBCRA.APPROVAL_FILE " + " ,TBCRA.ACTIVITY_DATE "
				+ " FROM TT_BIG_CUSTOMER_REBATE_APPROVAL TBCRA INNER JOIN TC_USER TU "
				+ " ON TBCRA.REBATE_APPROVAL_USER_ID = TU.USER_ID WHERE TBCRA.WS_NO = '" + wsNo
				+ "' AND TBCRA.DEALER_CODE = " + dealerCode + " ");
		// params.add(wsNo);
		// params.add(dealerCode);
		System.out.println(strSql.toString());
		List<PoCusWholeRepayClryslerVO> lis = new ArrayList<>();
		List<Map> list = OemDAOUtil.findAll(strSql.toString(), null);
		for (Map map : list) {
			PoCusWholeRepayClryslerVO vo = new PoCusWholeRepayClryslerVO();
			vo.setWsNo(map.get("WS_NO").toString());

			Map<String, Object> outDmsDealer = getDmsDealerCode(map.get("DEALER_CODE").toString());
			String dmsCode = outDmsDealer.get("DMS_CODE").toString();
			vo.setDealerCode(dmsCode);
			vo.setWsNo(map.get("WS_NO").toString());
			vo.setWsAuditor(map.get("NAME").toString());
			if (Integer.parseInt(map.get("REBATE_APPROVAL_STATUS")
					.toString()) == OemDictCodeConstants.BIG_CUSTOMER_REBATE_APPROVAL_TYPE_PASS) {
				vo.setSoStatus(16101003); // 下端审核状态 -审核通过
			} else if (Integer.parseInt(map.get("REBATE_APPROVAL_STATUS")
					.toString()) == OemDictCodeConstants.BIG_CUSTOMER_REBATE_APPROVAL_TYPE_OVER) {
				vo.setSoStatus(16101004); // 下端审核状态 -审核驳回
			} else if (Integer.parseInt(map.get("REBATE_APPROVAL_STATUS")
					.toString()) == OemDictCodeConstants.BIG_CUSTOMER_REBATE_APPROVAL_TYPE_RUS) {
				vo.setSoStatus(16101005); // 下端审核状态 -审核拒绝
			} else if (Integer.parseInt(map.get("REBATE_APPROVAL_STATUS")
					.toString()) == OemDictCodeConstants.BIG_CUSTOMER_REBATE_APPROVAL_TYPE_COMPLETE_INFORMATION) {
				vo.setSoStatus(16101006); // 下端审核状态 -资料完整待审批
			} else if (Integer.parseInt(map.get("REBATE_APPROVAL_STATUS")
					.toString()) == OemDictCodeConstants.BIG_CUSTOMER_REBATE_APPROVAL_TYPE_SYSTEM_RUS) {
				vo.setSoStatus(16101007); // 下端审核状态 -系统转拒绝
			} else if (Integer.parseInt(map.get("REBATE_APPROVAL_STATUS")
					.toString()) == OemDictCodeConstants.BIG_CUSTOMER_REBATE_APPROVAL_TYPE_OVER_RUS) {
				vo.setSoStatus(16101008); // 下端审核状态 -驳回转拒绝
			}
			if (!CommonUtils.checkIsNullStr(remark)) {
				vo.setWsAuditingRemark(map.get("REBATE_APPROVAL_REMARK") + "-" + remark);
			} else {
				vo.setWsAuditingRemark(map.get("REBATE_APPROVAL_REMARK").toString());
			}
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String rebate_approval_date = CommonUtils.checkNull(map.get("REBATE_APPROVAL_DATE").toString());
			if (!rebate_approval_date.equals("")) {
				Date parse = df.parse(map.get("REBATE_APPROVAL_DATE").toString());
				vo.setAuditingDate(parse);
			}
			String activity_date;
			try {
				activity_date = CommonUtils.checkNull(map.get("ACTIVITY_DATE"));
				if (!activity_date.equals("")) {
					Date parse1 = df.parse(map.get("ACTIVITY_DATE").toString());
					vo.setActivityDate(parse1);
				}
				vo.setHeadApprovalFileUrl(CommonUtils.checkNull(map.get("APPROVAL_FILE")));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			lis.add(vo);
		}

		return lis;
	}
	/**
	 * 查询大客户报备返利审批审核意见
	 */
	public String queryRemark(String wsNo, String dealerCode) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer strSql = new StringBuffer();
		strSql.append("  SELECT THIS.REBATE_APPROVAL_REMARK FROM TT_BIG_CUSTOMER_REBATE_APPROVAL_HIS THIS, (\n");
		strSql.append("  	  SELECT  MAX(APPROVAL_HIS_ID)AS APPROVAL_HIS_ID FROM TT_BIG_CUSTOMER_REBATE_APPROVAL_HIS RA\n");
		strSql.append("  		WHERE RA.ENABLE = 10011001  AND RA.BIG_CUSTOMER_CODE = ? AND RA.WS_NO = ?\n");
		strSql.append("  			AND RA.REBATE_APPROVAL_STATUS = 15950003\n");
		strSql.append("  			AND RA.ACTIVITY_DATE IS NULL\n");
		strSql.append("  			 ) HIS \n");
		strSql.append("  			WHERE THIS.APPROVAL_HIS_ID = HIS.APPROVAL_HIS_ID\n");
		params.add(wsNo);
		params.add(dealerCode);
		 String rebateApprovalRemark="";
		List<Map> map = OemDAOUtil.findAll(strSql.toString(), params);
		if(null!=map&&map.size()>0){
			 rebateApprovalRemark=CommonUtils.checkNull(map.get(0).get("REBATE_APPROVAL_REMARK"));
		}
	    return rebateApprovalRemark;
	}
}
