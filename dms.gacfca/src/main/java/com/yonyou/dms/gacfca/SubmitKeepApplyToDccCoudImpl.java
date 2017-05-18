package com.yonyou.dms.gacfca;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yonyou.dcs.gacfca.SADCS004Cloud;
import com.yonyou.dms.DTO.gacfca.SameToDccDto;
import com.yonyou.dms.common.Util.Utility;
import com.yonyou.dms.common.domains.PO.basedata.PotentialCusPO;
import com.yonyou.dms.common.domains.PO.basedata.TtCustomerIntentDetailPO;
import com.yonyou.dms.common.domains.PO.basedata.UserPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.common.CommonConstants;

/**
 * 客户保留申请的审批 之后上报
 * 
 * @author Benzc
 * @date 2017年1月10日
 */
@Service
public class SubmitKeepApplyToDccCoudImpl implements SubmitKeepApplyToDccCoud {

	private static final Logger logger = LoggerFactory.getLogger(SubmitKeepApplyToDccCoudImpl.class);

	@Autowired
	SADCS004Cloud SADCS004;

	@Override
	public int performExecute(String customerNo, String auditResult) {

		try {
			String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
			if (customerNo != null && !"".equals(customerNo) && auditResult != null && "Y".equals(auditResult)) {
				PotentialCusPO po = PotentialCusPO.findFirst("DEALER_CODE=? and CUSTOMER_NO=? and D_KEY=?", dealerCode,
						customerNo, CommonConstants.D_KEY);
				if (po != null) {
					deleteFcustomerAction(dealerCode, customerNo);// 改成F级而且上报的客户要把未跟进的跟进记录删掉才行
					if (po.getInteger("IS_SAME_DCC") != null && po.getInteger("IS_SAME_DCC").equals(12781001)) {
						return 1;
					}
					if (po.getInteger("IS_UPLOAD") != null && po.getInteger("IS_UPLOAD") == 12781001) {
						return 1;
					}
					LinkedList<SameToDccDto> resultList = new LinkedList<SameToDccDto>();
					SameToDccDto vo = new SameToDccDto();
					vo.setDealerCode(dealerCode);
					vo.setNid(Utility.getLong(po.getString("OEM_CUSTOMER_NO")));
					vo.setCustomerNo(customerNo);
					vo.setStatus("3");
					vo.setName(po.getString("CUSTOMER_NAME"));
					if(po.getInteger("GENDER")!= null){
						if (po.getInteger("GENDER").equals(10061001)) {
							vo.setGender(1);// 性别 男
						} else if (po.getInteger("GENDER").equals(10061002)) {
							vo.setGender(2);// 女
						} else {
							vo.setGender(po.getInteger("GENDER"));
						}
					}else{
						vo.setGender(null);
					}
					vo.setPhone(po.getString("CONTACTER_MOBILE"));// 手机
					vo.setTelephone(po.getString("CONTACTOR_PHONE"));// 固话
					vo.setProvinceID(po.getInteger("PROVINCE"));
					vo.setCityID(po.getInteger("CITY"));
					vo.setCreatedAt(po.getDate("CREATED_AT"));
					vo.setSleepTime(po.getDate("DCC_DATE"));
					vo.setSleepReason(po.getString("KEEP_APPLY_REASION"));// 休眠原因
					vo.setOpportunityLevelID(13101007);// 客户级别
					if (po.getInteger("SOLD_BY") != null && po.getInteger("SOLD_BY") != 0) {
						UserPO userpo = UserPO.findFirst("DEALER_CODE = ? AND USER_ID = ? ", dealerCode,po.getInteger("SOLD_BY"));
						if (userpo != null) {
							vo.setSalesConsultant(userpo.getString("USER_NAME"));
						}
					}
					if (po.getInteger("INTENT_ID") != null && !po.getInteger("INTENT_ID").equals(0)) {
						TtCustomerIntentDetailPO intent2 = new TtCustomerIntentDetailPO();
						List<TtCustomerIntentDetailPO> inList = TtCustomerIntentDetailPO.find("DEALER_CODE = ? AND INTENT_ID = ? AND D_KEY = ? AND IS_MAIN_MODEL = ?"
								, dealerCode,po.getInteger("INTENT_ID"),CommonConstants.D_KEY,12781001);
						if (inList != null && inList.size() > 0) {
							intent2 = inList.get(0);
							vo.setSeriasCode(intent2.getString("INTENT_SERIES"));
							vo.setModelCode(intent2.getString("INTENT_MODEL"));
							vo.setConsiderationID(intent2.getString("CHOOSE_REASON"));// 选择车型原因
							vo.setBrandCode(intent2.getString("INTENT_BRAND"));
							vo.setConfigerCode(intent2.getString("INTENT_CONFIG"));
						}
					}
					resultList.add(vo);
					po = null;
					vo = null;
					SADCS004.receiveDate(resultList);
					PotentialCusPO po2 = PotentialCusPO.findFirst("DEALER_CODE = ? AND CUSTOMER_NO = ? AND D_KEY = ? ", dealerCode,customerNo,CommonConstants.D_KEY);
					po2.setInteger("IS_UPLOAD", 12781001);
					po2.saveIt();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		} finally {
		}
		return 1;
	}

	// 删除要上报的客户的未跟进的跟进记录deleteActionCustomerToF
	public void deleteFcustomerAction(String dealerCode, String customerNo) throws Exception {
		List<Object> params = new ArrayList<>();
		String sql = " delete from TT_SALES_PROMOTION_PLAN where (PROM_RESULT IS NULL OR PROM_RESULT=0)"
				+ " and DEALER_CODE= ? and customer_no= ? ";
		params.add(dealerCode);
		params.add(customerNo);
		logger.debug(sql);
		DAOUtil.execBatchPreparement(sql, params);
	}

}
