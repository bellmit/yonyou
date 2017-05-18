package com.yonyou.dms.customer.service.customerManage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.PotentialCusPO;
import com.yonyou.dms.common.domains.PO.basedata.TtCustomerGatheringPO;
import com.yonyou.dms.customer.domains.DTO.customerManage.SalesGatheringDTO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.DAOUtilGF;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.CommonNoService;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.exception.ServiceBizException;

@Service
public class SalesGatheringServiceImpl implements SalesGatheringService {
	@Autowired
	private CommonNoService commonNoService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yonyou.dms.customer.service.customerManage.SalesGatheringService#
	 * queryCusInfo(java.util.Map)
	 */
	@Override
	public PageInfoDto queryCusInfo(Map<String, String> queryParam) throws ServiceBizException {
		Long userid = FrameworkUtil.getLoginInfo().getUserId();
		String orgCode = FrameworkUtil.getLoginInfo().getOrgCode();
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		List<Map> result = this.queryOppolist();
		StringBuffer sb = new StringBuffer();
		sb.append(
				"SELECT AA.*,BB.BRAND_NAME,CC.SERIES_NAME,DD.MODEL_NAME,EE.CONFIG_NAME,FF.COLOR_NAME,GG.MEDIA_DETAIL_NAME FROM ( ");
		sb.append(" SELECT A.* ");
		sb.append("  ,c.INTENT_BRAND,c.INTENT_SERIES,c.INTENT_MODEL,c.INTENT_CONFIG,c.INTENT_COLOR,");
		sb.append("d.DEPOSIT_AMOUNT,c.IS_MAIN_MODEL, 'CUSTOMER' AS TAG   ");
		sb.append(" from TM_POTENTIAL_CUSTOMER A  ");
		sb.append(
				" left join  TT_CUSTOMER_INTENT_DETAIL c on c.dealer_code = A.dealer_code and c.INTENT_ID=A.INTENT_ID   ");
		sb.append(
				" left join  TT_ES_CUSTOMER_ORDER d on d.EC_ORDER_NO = A.EC_ORDER_NO  and a.dealer_code=d.dealer_code ");
		sb.append(" and a.customer_no=d.customer_no ");
		sb.append(" WHERE 1=1 ");
		sb.append(" and A.INTENT_LEVEL!= 13101007 and  A.INTENT_LEVEL!= 13101006 ");
		sb.append(" and a.dealer_code='"+dealerCode+"' and a.d_key = " + CommonConstants.D_KEY + "");
		Utility.sqlToLike(sb, queryParam.get("name"), "CUSTOMER_NAME", "A");
		Utility.sqlToLike(sb, queryParam.get("no"), "CUSTOMER_NO", "A");
		Utility.sqlToEquals(sb, queryParam.get("type"), "CUSTOMER_TYPE", null);
		Utility.sqlToEquals(sb, queryParam.get("status"), "INTENT_LEVEL", null);
		Utility.sqlToEquals(sb, "", "SOLD_BY", null);
		DAOUtilGF.getOwnedByStr("A", userid, orgCode, "45701500", dealerCode);
		if (result != null && result.size() > 0) {

		} else {
			Utility.sqlToEquals(sb, "12781002", "IS_BIG_CUSTOMER", "A");
		}
		Utility.sqlToEquals(sb, queryParam.get("IS_MAIN_MODEL"), "IS_MAIN_MODEL", "C");
		Utility.sqlToLike(sb, queryParam.get("phone"), "CONTACTOR_PHONE", "A");
		Utility.sqlToLike(sb, queryParam.get("mobile"), "CONTACTOR_MOBILE", "A");
		sb.append(" ) AA  ");

		sb.append(" LEFT JOIN tm_brand bb ON AA.INTENT_BRAND = bb.BRAND_CODE AND AA.dealer_code = bb.dealer_code ");
		sb.append(
				" LEFT JOIN tm_series cc ON AA.INTENT_BRAND = cc.BRAND_CODE AND AA.dealer_code = CC.dealer_code AND AA.INTENT_SERIES = cc.`SERIES_CODE`   ");
		sb.append(" LEFT JOIN tm_model dd ON AA.INTENT_BRAND = dd.BRAND_CODE AND AA.dealer_code = DD.dealer_code AND INTENT_SERIES = dd.`SERIES_CODE`  ");
		sb.append(" AND AA.INTENT_MODEL = dd.`MODEL_CODE`  ");
		sb.append(
				" LEFT JOIN tm_configuration EE ON  AA.INTENT_BRAND = EE.BRAND_CODE AND AA.dealer_code = EE.dealer_code AND INTENT_SERIES = EE.`SERIES_CODE`  ");
		sb.append(" AND AA.INTENT_MODEL = EE.`MODEL_CODE` AND AA.INTENT_CONFIG = EE.CONFIG_CODE ");
		sb.append(" LEFT JOIN tm_color FF ON AA.INTENT_COLOR = FF.COLOR_CODE AND AA.dealer_code = FF.dealer_code ");
		sb.append(" LEFT JOIN TT_MEDIA_DETAIL GG ON AA.MEDIA_DETAIL = GG.MEDIA_DETAIL AND AA.dealer_code = GG.dealer_code");

		List<Object> queryList = new ArrayList<Object>();
		System.err.println(sb.toString());
		PageInfoDto id = DAOUtil.pageQuery(sb.toString(), queryList);
		return id;
	}

	@Override
	public List<Map> salesGatheringbyId(String id) throws ServiceBizException {
		StringBuilder sb = new StringBuilder();
		sb.append(
				"Select a.*,b.PAY_TYPE_NAME,c.USER_NAME as USER_NAME from Tt_Customer_Gathering a left join TM_PAY_TYPE b on ");
		sb.append(
				"a.PAY_TYPE_CODE = b.PAY_TYPE_CODE left join TM_USER c on a.WRITEOFF_BY = c.USER_ID where CUSTOMER_NO = '"
						+ id + "'");
		System.out.println(sb.toString());
		List<Map> list = DAOUtil.findAll(sb.toString(), null);
		return list;
	}

	@Override
	public List<Map> salesAmountbyId(String id) throws ServiceBizException {
		StringBuilder sb = new StringBuilder();
		sb.append(
				"Select DEALER_CODE, CASE WHEN GATHERED_SUM IS NULL THEN 0 WHEN GATHERED_SUM IS NOT NULL THEN GATHERED_SUM ELSE '' END AS GATHERED_SUM,  ");
		sb.append(
				"CASE WHEN ORDER_PAYED_SUM IS NULL THEN 0 WHEN ORDER_PAYED_SUM IS NOT NULL THEN ORDER_PAYED_SUM ELSE '' END AS ORDER_PAYED_SUM,  ");
		sb.append(
				"CASE WHEN CON_PAYED_SUM IS NULL THEN 0 WHEN CON_PAYED_SUM IS NOT NULL THEN CON_PAYED_SUM ELSE '' END AS CON_PAYED_SUM,  ");
		sb.append(
				"CASE WHEN USABLE_AMOUNT IS NULL THEN 0 WHEN USABLE_AMOUNT IS NOT NULL THEN USABLE_AMOUNT ELSE '' END AS USABLE_AMOUNT,  ");
		sb.append(
				"CASE WHEN UN_WRITEOFF_SUM IS NULL THEN 0 WHEN UN_WRITEOFF_SUM IS NOT NULL THEN UN_WRITEOFF_SUM ELSE '' END AS UN_WRITEOFF_SUM  ");
		sb.append(" from TM_POTENTIAL_CUSTOMER where CUSTOMER_NO = '" + id + "'");
		System.out.println(sb.toString());
		List<Map> list = DAOUtil.findAll(sb.toString(), null);
		return list;
	}

	@Override
	public Map salesEditGatheringbyId(String id) throws ServiceBizException {
		StringBuilder sb = new StringBuilder();
		sb.append(
				"Select a.*,b.PAY_TYPE_NAME,c.EMPLOYEE_NAME as USER_NAME from Tt_Customer_Gathering a left join TM_PAY_TYPE b on ");
		sb.append(
				"a.PAY_TYPE_CODE = b.PAY_TYPE_CODE left join TM_EMPLOYEE c on a.WRITEOFF_BY = c.EMPLOYEE_NO where RECEIVE_NO = '"
						+ id + "'");
		System.out.println(sb.toString());
		List<Map> list = DAOUtil.findAll(sb.toString(), null);
		return list.get(0);
	}

	@Override
	public void addSalesGathering(SalesGatheringDTO salesGatheringDto) throws ServiceBizException {
		String receiveNo = salesGatheringDto.getReceiveNo();
		if (receiveNo == null || receiveNo.trim().length() == 0) {// 预收款登记新增
			TtCustomerGatheringPO gatheringPO = new TtCustomerGatheringPO();
			String NO = commonNoService.getSystemOrderNo(CommonConstants.SRV_SKDH);
			gatheringPO.setString("RECEIVE_NO", NO);
			this.setSalesGathering(gatheringPO, salesGatheringDto);
			gatheringPO.saveIt();
		}
		String customerNo = salesGatheringDto.getCustomerNo();
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		Double receiveAmount = Double.parseDouble(salesGatheringDto.getReceiveAmount());
		// 更新客户资料表中收款总金额和帐户可用余额 如果是退款就不影响客户账户的操作，而是直接把退的钱给客户,但是退款中若有违约金，还是要收款的
		if (customerNo != null && customerNo.length() > 0) {
			PotentialCusPO potCusPo = PotentialCusPO.findByCompositeKeys(dealerCode, customerNo);
			if (potCusPo != null) {
				if (receiveAmount < 0) { // 判断收款金额在为负数的情况下
					// 作预收款时和账户可用余额比较
					if (potCusPo.get("USABLE_AMOUNT") != null
							&& Double.parseDouble(potCusPo.get("USABLE_AMOUNT").toString()) + receiveAmount < 0) {
						throw new ServiceBizException("账户可用余额加收款金额之和不能小于0! ");
					}
					if (potCusPo.get("GatheredSum") == null) {
						throw new ServiceBizException("已收款总金额不能为0! ");
					}
				}
			}
			Double gatheredSum = 0D;
			Double usableAmount = 0D;
			Double unWriteoffSum = 0D;
			if (potCusPo.get("Gathered_Sum") != null) {
				gatheredSum = Double.parseDouble(potCusPo.get("Gathered_Sum").toString()) + receiveAmount;
			} else {
				gatheredSum = receiveAmount;
			}
			potCusPo.setString("GATHERED_SUM", Utility.round(String.valueOf(gatheredSum), 2));
			String writeoffTag = salesGatheringDto.getWriteoffTag();
			if ("12781002".equals(writeoffTag)) {
				if (potCusPo.get("UN_WRITEOFF_SUM") != null) {
					unWriteoffSum = Double.parseDouble(potCusPo.get("UN_WRITEOFF_SUM").toString()) + receiveAmount;
				} else {
					unWriteoffSum = receiveAmount;
				}
				potCusPo.setString("UN_WRITEOFF_SUM", Utility.round(String.valueOf(unWriteoffSum), 2));
			} else {
				if (potCusPo.getString("USABLE_AMOUNT") != null) {
					usableAmount = Double.parseDouble(potCusPo.getString("USABLE_AMOUNT").toString()) + receiveAmount;
				} else {
					usableAmount = receiveAmount;
				}
				potCusPo.setString("USABLE_AMOUNT", Utility.round(String.valueOf(usableAmount), 2));

			}
			potCusPo.saveIt();
		}
		// String transTid="";
		// String isGroupMem = Utility.getDefaultValue("1891");//是否集团会员

	}

	@Override
	public Map<String, Object> editSalesGathering(String cus_no, SalesGatheringDTO salesGatheringDto, String customerNo)
			throws ServiceBizException {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		System.out.println(cus_no);
		PotentialCusPO potCusPo = PotentialCusPO.findByCompositeKeys(dealerCode, cus_no);
		List<Map> list = this.querybyreceiveNo(salesGatheringDto);
		for (Map map : list) {
			if (!map.get("WRITEOFF_TAG").equals(salesGatheringDto.getWriteoffTag())) {
				if (salesGatheringDto.getCustomerNo() != null && salesGatheringDto.getCustomerNo().length() > 0) {
					List<Map> cusList = this.querybyCustomerNo(salesGatheringDto);
					for (Map cusMap : cusList) {
						if (cusMap != null && cusMap.size() > 0) {
							if ("12781001".equals(salesGatheringDto.getWriteoffTag())) {
								potCusPo.setString("UN_WRITEOFF_SUM",
										cusMap.get("UN_WRITEOFF_SUM") == null ? 0
												: Double.parseDouble(cusMap.get("UN_WRITEOFF_SUM").toString())
														- Double.parseDouble(salesGatheringDto.getReceiveAmount()));

								potCusPo.setString("USABLE_AMOUNT",
										cusMap.get("USABLE_AMOUNT") == null ? 0
												: Double.parseDouble(cusMap.get("USABLE_AMOUNT").toString())
														+ Double.parseDouble(salesGatheringDto.getReceiveAmount()));
							} else if ("12781002".equals(salesGatheringDto.getWriteoffTag())) {
								if (cusMap.get("USABLE_AMOUNT") == null) {
									potCusPo.setString("USABLE_AMOUNT", 0);
								}
								if (Double.parseDouble(cusMap.get("USABLE_AMOUNT").toString())
										- Double.parseDouble(salesGatheringDto.getReceiveAmount()) >= 0) {
									potCusPo.setString("UN_WRITEOFF_SUM",
											cusMap.get("UN_WRITEOFF_SUM") == null ? 0
													: Double.parseDouble(cusMap.get("UN_WRITEOFF_SUM").toString())
															+ Double.parseDouble(salesGatheringDto.getReceiveAmount()));
									potCusPo.setString("USABLE_AMOUNT",
											cusMap.get("USABLE_AMOUNT") == null ? 0
													: Double.parseDouble(cusMap.get("USABLE_AMOUNT").toString())
															- Double.parseDouble(salesGatheringDto.getReceiveAmount()));
								} else {
									throw new ServiceBizException("可用余额不足！");
								}
							}
							potCusPo.saveIt();
						}
					}
				}
			}
		}
		String receiveNo = salesGatheringDto.getReceiveNo();
		Long userid = FrameworkUtil.getLoginInfo().getUserId();
		TtCustomerGatheringPO gatheringPO = TtCustomerGatheringPO.findByCompositeKeys(receiveNo, dealerCode);
		gatheringPO.setString("BILL_NO", salesGatheringDto.getBillNo());

		gatheringPO.setString("SO_NO", salesGatheringDto.getSoNo());
		gatheringPO.setString("GATHERING_TYPE", salesGatheringDto.getGatheringType());
		gatheringPO.setString("PAY_TYPE_CODE", salesGatheringDto.getPayTypeCode());
		gatheringPO.setString("RECEIVE_DATE", salesGatheringDto.getReceiveDate());
		gatheringPO.setString("RECEIVE_AMOUNT", salesGatheringDto.getReceiveAmount());
		gatheringPO.setDate("RECORD_DATE", new Date());
		gatheringPO.setString("RECORDER", userid);
		gatheringPO.setString("REMARK", salesGatheringDto.getRemark());
		gatheringPO.setString("TRANSACTOR", salesGatheringDto.getTransactor());
		gatheringPO.setString("WRITEOFF_BY", salesGatheringDto.getWriteoffBy());
		gatheringPO.setString("WRITEOFF_DATE", salesGatheringDto.getWriteoffDate());
		gatheringPO.setString("WRITEOFF_TAG", salesGatheringDto.getWriteoffTag());
		gatheringPO.saveIt();
		return null;
	}

	public void setSalesGathering(TtCustomerGatheringPO gatheringPO, SalesGatheringDTO salesGatheringDto) {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		Long userid = FrameworkUtil.getLoginInfo().getUserId();
		gatheringPO.setString("BILL_NO", salesGatheringDto.getBillNo());
		gatheringPO.setString("CUSTOMER_NO", salesGatheringDto.getCustomerNo());
		gatheringPO.setString("SO_NO", salesGatheringDto.getSoNo());
		gatheringPO.setString("DEALER_CODE", dealerCode);
		gatheringPO.setString("GATHERING_TYPE", salesGatheringDto.getGatheringType());
		gatheringPO.setString("PAY_TYPE_CODE", salesGatheringDto.getPayTypeCode());
		gatheringPO.setString("RECEIVE_AMOUNT", salesGatheringDto.getReceiveAmount());
		gatheringPO.setString("RECEIVE_DATE", salesGatheringDto.getReceiveDate());
		gatheringPO.setDate("RECORD_DATE", new Date());
		gatheringPO.setString("RECORDER", userid);
		gatheringPO.setString("REMARK", salesGatheringDto.getRemark());
		gatheringPO.setString("TRANSACTOR", salesGatheringDto.getTransactor());
		gatheringPO.setString("WRITEOFF_BY", salesGatheringDto.getWriteoffBy());
		gatheringPO.setString("WRITEOFF_DATE", salesGatheringDto.getWriteoffDate());
		gatheringPO.setString("WRITEOFF_TAG", salesGatheringDto.getWriteoffTag());
		gatheringPO.setString("STORE_AMOUNT", salesGatheringDto.getUseStore());
		gatheringPO.setString("THIS_USE_CREDIT", salesGatheringDto.getUseCredit());
		gatheringPO.setString("THIS_CREDIT_AMOUNT", salesGatheringDto.getCreditAmount());
	}

	public List<Map> queryOppolist() {
		Long userid = FrameworkUtil.getLoginInfo().getUserId();
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT * FROM tm_user_ctrl WHERE DEALER_CODE = '" + dealerCode + "' AND USER_ID = '"
				+ userid + "'" + " AND CTRL_CODE = 80900000 ");
		List<Object> queryList = new ArrayList<Object>();
		System.err.println(sb.toString());
		return Base.findAll(sb.toString(), queryList.toArray());
	}

	public List<Map> querybyreceiveNo(SalesGatheringDTO salesGatheringDto) {

		String receiveNo = salesGatheringDto.getReceiveNo();
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT * FROM TT_CUSTOMER_GATHERING WHERE RECEIVE_NO ='" + receiveNo + "'");
		List<Object> queryList = new ArrayList<Object>();
		return Base.findAll(sb.toString(), queryList.toArray());
	}

	public List<Map> querybyCustomerNo(SalesGatheringDTO salesGatheringDto) {

		String customerNo = salesGatheringDto.getCustomerNo();
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT * FROM TM_POTENTIAL_CUSTOMER WHERE CUSTOMER_NO = '" + customerNo + "'");
		List<Object> queryList = new ArrayList<Object>();
		return Base.findAll(sb.toString(), queryList.toArray());
	}

	@Override
	public List<Map> qryPayTypeCode(String orgCode) throws ServiceBizException {
		StringBuilder sqlSb = new StringBuilder("select PAY_TYPE_CODE,PAY_TYPE_NAME,DEALER_CODE from TM_PAY_TYPE");
		List<Object> params = new ArrayList<Object>();

		List<Map> list = DAOUtil.findAll(sqlSb.toString(), params);
		return list;
	}
}
