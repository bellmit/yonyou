
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.retail
*
* @File name : SalesOrderSubmitServiceImpl.java
*
* @Author : Administrator
*
* @Date : 2017年2月14日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年2月14日    Administrator    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.retail.service.ordermanage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.PotentialCusPO;
import com.yonyou.dms.common.domains.PO.basedata.SoAuditingPO;
import com.yonyou.dms.common.domains.PO.basedata.TtCustomerGatheringPO;
import com.yonyou.dms.common.domains.PO.basedata.TtIntentReceiveMoneyPO;
import com.yonyou.dms.common.domains.PO.basedata.TtIntentSalesOrderPO;
import com.yonyou.dms.common.domains.PO.basedata.TtOrderStatusUpdatePO;
import com.yonyou.dms.commonSales.domains.PO.stockmanage.SalesOrderPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.DAOUtilGF;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.service.CommonNoService;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.DateUtil;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.retail.domains.DTO.ordermanage.FinanceOrderDTO;
import com.yonyou.dms.retail.domains.DTO.ordermanage.FinanceShutOrderDTO;

/**
 * TODO description
 * 
 * @author Administrator
 * @date 2017年2月14日
 */
@Service
@SuppressWarnings("rawtypes")
public class SalesOrderSubmitServiceImpl implements SalesOrderSubmitService {

	@Autowired
	private CommonNoService commonNoService;

	/**
	 * 财务审核查询销售订单
	 * 
	 * @author xukl
	 * @date 2016年11月1日
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 *             (non-Javadoc)
	 * @see com.yonyou.dms.retail.service.ordermanage.SalesOrderService#qrySRSForMangAudit(java.util.Map)
	 */
	@Override
	public PageInfoDto qrySRSForFincAudit(Map<String, String> queryParam) throws ServiceBizException {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT AAA.*,BBB.MODEL_NAME,CCC.CONFIG_NAME,DDD.COLOR_NAME,EEE.EMPLOYEE_NAME,FFF.USER_NAME FROM (");
		sql.append(
				" SELECT * FROM (SELECT 0 as IS_SELECTALL,a.OBLIGATED_OPERATOR,a.BALANCE_CLOSE_TIME  , A.DEALER_CODE,A.BUSINESS_TYPE,A.ORDER_SORT,A.SO_STATUS,A.SO_NO,A.SHEET_CREATE_DATE,A.SOLD_BY,A.DELIVERY_MODE_ELEC,A.EC_ORDER_NO,A.ESC_TYPE,A.ESC_ORDER_STATUS,"
						+ " A.VER,A.IS_SPEEDINESS,A.RE_AUDITED_BY,A.CONSIGNEE_CODE," + " CASE WHEN A.BUSINESS_TYPE="
						+ DictCodeConstants.DICT_SO_TYPE_ALLOCATION
						+ " THEN D.CUSTOMER_NO ELSE A.CUSTOMER_NO END AS CUSTOMER_NO," + " CASE WHEN A.BUSINESS_TYPE="
						+ DictCodeConstants.DICT_SO_TYPE_ALLOCATION
						+ " THEN D.CUSTOMER_NAME ELSE A.CUSTOMER_NAME END AS CUSTOMER_NAME,"
						+ " CASE WHEN A.BUSINESS_TYPE=" + DictCodeConstants.DICT_SO_TYPE_ALLOCATION
						+ " THEN D.CONTRACT_NO ELSE A.CONTRACT_NO END AS CONTRACT_NO," + " CASE WHEN A.BUSINESS_TYPE="
						+ DictCodeConstants.DICT_SO_TYPE_ALLOCATION
						+ " THEN D.CONTRACT_DATE ELSE A.CONTRACT_DATE END AS CONTRACT_DATE, "
						+ " A.VIN,B.MODEL_CODE, B.CONFIG_CODE,COALESCE(A.COLOR_CODE,B.COLOR_CODE) as COLOR_CODE,A.CUSTOMER_TYPE,A.MOBILE,A.CONTACTOR_NAME,A.PAY_OFF,"
						+ " A.PHONE,A.CT_CODE,A.CERTIFICATE_NO,A.REMARK ,A.ORDER_RECEIVABLE_SUM,A.ORDER_PAYED_AMOUNT,A.OTHER_PAY_OFF, A.OTHER_AMOUNT_OBJECT, a.OTHER_AMOUNT,A.LOCK_USER,A.APPLY_NO  "
						+ " FROM TT_SALES_ORDER A" + " LEFT JOIN (" + CommonConstants.VM_VS_PRODUCT
						+ ") B  ON A.PRODUCT_CODE = B.PRODUCT_CODE AND A.dealer_code = B.dealer_code"
						+ " LEFT JOIN (SELECT dealer_code,CUSTOMER_CODE AS CUSTOMER_NO,CUSTOMER_NAME,CONTRACT_NO,AGREEMENT_BEGIN_DATE AS CONTRACT_DATE FROM ("
						+ CommonConstants.VM_PART_CUSTOMER + ") s) D "
						+ " ON A.dealer_code=D.dealer_code AND A.CONSIGNEE_CODE=D.CUSTOMER_NO" + " WHERE 1=1  "
						+ " AND NOT EXISTS(SELECT * FROM TT_SALES_ORDER C WHERE C.dealer_code = A.dealer_code AND C.VIN = A.VIN "
						+ " AND C.SO_NO != A.SO_NO AND A.BUSINESS_TYPE = 13001004 AND A.ORDER_SORT = 13861002 AND C.BUSINESS_TYPE = 13001001 "
						+ " AND (C.SO_STATUS != 13011055 AND C.SO_STATUS != 13011060))" + " AND A.D_KEY = "
						+ DictCodeConstants.D_KEY + " AND A.dealer_code = '"
						+ FrameworkUtil.getLoginInfo().getDealerCode() + "'");
		if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
			sql.append(" and A.VIN like ?");
			params.add("%" + queryParam.get("vin") + "%");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("soNo"))) {
			sql.append(" and A.SO_NO like ?");
			params.add("%" + queryParam.get("soNo") + "%");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("ecOrderNo"))) {
			sql.append(" and A.EC_ORDER_NO like ?");
			params.add("%" + queryParam.get("ecOrderNo") + "%");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("otherPayOff"))) {
			sql.append(" and A.OTHER_PAY_OFF= ? ");
			params.add(queryParam.get("otherPayOff"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("businessType"))) {
			sql.append(" and A.BUSINESS_TYPE= ? ");
			params.add(queryParam.get("businessType"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("soStatus"))) {
			sql.append(" and A.SO_STATUS= ? ");
			params.add(queryParam.get("soStatus"));
		}
		if (StringUtils.isNullOrEmpty(queryParam.get("soStatus"))) {
			sql.append("  AND (A.SO_STATUS = " + DictCodeConstants.DICT_SO_STATUS_ACCOUNTANT_AUDITING
					+ " OR A.SO_STATUS = " + DictCodeConstants.DICT_SO_STATUS_DELIVERY_CONFIRMING + " OR A.SO_STATUS = "
					+ DictCodeConstants.DICT_SO_STATUS_HAVE_OUT_STOCK + " OR A.SO_STATUS = "
					+ DictCodeConstants.DICT_SO_STATUS_ACCOUNTANT_REJECT + " OR A.SO_STATUS = "
					+ DictCodeConstants.DICT_SO_STATUS_CLOSED + ")");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("deBegin"))) {
			sql.append(" and A.SHEET_CREATE_DATE>= ?");
			params.add(DateUtil.parseDefaultDate(queryParam.get("deBegin")));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("deEnd"))) {
			sql.append(" and A.SHEET_CREATE_DATE<?");
			params.add(DateUtil.addOneDay(queryParam.get("deEnd")));
		}

		Utility.sqlToDate(sql, queryParam.get("deBegins"), queryParam.get("deEndS"), "BALANCE_CLOSE_TIME", "A");
		

		if (!StringUtils.isNullOrEmpty(queryParam.get("consultant"))) {
			sql.append(" and A.OBLIGATED_OPERATOR= ? ");
			params.add(queryParam.get("consultant"));
		}

		sql.append(" ORDER  BY A.SO_STATUS,A.SHEET_CREATE_DATE");
		sql.append(") P WHERE 1=1");
		if (!StringUtils.isNullOrEmpty(queryParam.get("customerName"))) {
			sql.append(" and P.CUSTOMER_NAME like ?");
			params.add("%" + queryParam.get("customerName") + "%");
		}
		sql.append(DAOUtilGF.getFunRangeByStr("P", "SOLD_BY", loginInfo.getUserId(), loginInfo.getOrgCode(), "70101300",
				loginInfo.getDealerCode()));
		sql.append(" ) AAA LEFT JOIN TM_MODEL BBB ON AAA.MODEL_CODE = BBB.MODEL_CODE AND AAA.DEALER_CODE = BBB.DEALER_CODE AND "
				+ "BBB.IS_VALID = 12781001 LEFT JOIN TM_CONFIGURATION CCC ON AAA.MODEL_CODE = CCC.MODEL_CODE AND AAA.CONFIG_CODE "
				+ " = CCC.CONFIG_CODE AND AAA.DEALER_CODE = CCC.DEALER_CODE LEFT JOIN TM_COLOR DDD ON AAA.COLOR_CODE = DDD.COLOR_CODE "
				+ " AND AAA.DEALER_CODE = DDD.DEALER_CODE LEFT JOIN TM_EMPLOYEE EEE ON AAA.OBLIGATED_OPERATOR = EEE.EMPLOYEE_NO AND "
				+ " AAA.DEALER_CODE = EEE.DEALER_CODE LEFT JOIN TM_USER FFF ON AAA.SOLD_BY = FFF.USER_ID AND AAA.DEALER_CODE = FFF.DEALER_CODE");
		
		System.err.println(sql.toString());
		PageInfoDto pageInfoDto = DAOUtil.pageQuery(sql.toString(), params);
		return pageInfoDto;
	}

	@Override
	public void auditSalesOrder(FinanceOrderDTO auditDto) throws ServiceBizException {
		boolean isConfirmed = false;// 控制自动交车确认
		String auditingType = DictCodeConstants.DICT_AUDITING_TYPE_FINANCE;
		Long userId = FrameworkUtil.getLoginInfo().getUserId();
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);

		if (!StringUtils.isNullOrEmpty(auditDto)) {

			SalesOrderPO pagePO = SalesOrderPO.findByCompositeKeys(loginInfo.getDealerCode(), auditDto.getAuditList());

			if (pagePO != null && !StringUtils.isNullOrEmpty(pagePO.getInteger("DEAL_STATUS"))
					&& pagePO.getInteger("DEAL_STATUS") == Integer
							.parseInt(DictCodeConstants.DICT_COMPLAINT_DEAL_STATUS_DEALED)) {
				throw new ServiceBizException("该订单在处理状态中，暂不能处理！");
			}
			if (auditingType.equals(DictCodeConstants.DICT_AUDITING_TYPE_FINANCE)) {

				// 财务审核
				if (auditDto.getAuditingResult() == Integer.parseInt(DictCodeConstants.DICT_AUDITING_RESULT_REJECT)) {
					// 驳回 订单改为财务驳回
					pagePO.setInteger("SO_STATUS",
							Integer.parseInt(DictCodeConstants.DICT_SO_STATUS_ACCOUNTANT_REJECT));
					pagePO.setInteger("IS_UPLOAD", Integer.parseInt(DictCodeConstants.DICT_IS_NO));
					pagePO.setString("AUDITED_BY", "0");
					// 新增订单审核历史
					this.setOrderStatusPO(auditDto.getAuditList(), DictCodeConstants.DICT_SO_STATUS_ACCOUNTANT_REJECT,
							FrameworkUtil.getLoginInfo().getUserId());
				} else {
					// 如果是销售退回的订单,财务审核同意后变为'已退回'

					if (null != pagePO) {
						if (pagePO.get("Business_Type").toString().equals(DictCodeConstants.DICT_SO_TYPE_RERURN)) {
							pagePO.setInteger("So_Status",
									Integer.parseInt(DictCodeConstants.DICT_SO_STATUS_HAVE_UNTREAD));
						} else {
							// 财务审核通过
							// 一般订单和调拨订单
							if (pagePO.get("Business_Type").toString().equals(DictCodeConstants.DICT_SO_TYPE_GENERAL)
									|| pagePO.get("Business_Type").toString()
											.equals(DictCodeConstants.DICT_SO_TYPE_ALLOCATION)) {
								if (pagePO.get("Delivery_Mode").toString()
										.equals(DictCodeConstants.DICT_DELIVERY_MODE_LOCAL)) {
									// 1.交车方式为[本地交车]的一般销售订单和调拨订单，状态变为[交车确认中]
									pagePO.setInteger("So_Status",
											Integer.parseInt(DictCodeConstants.DICT_SO_STATUS_DELIVERY_CONFIRMING));
									pagePO.setInteger("Is_Upload", Integer.parseInt(DictCodeConstants.DICT_IS_NO));
									// 一般订单要记一下
									if (pagePO.get("Business_Type").toString()
											.equals(DictCodeConstants.DICT_SO_TYPE_GENERAL)) {
										this.setOrderStatusPO(auditDto.getAuditList(),
												DictCodeConstants.DICT_SO_STATUS_DELIVERY_CONFIRMING, userId);
									}
									isConfirmed = true;
								} else {
									// 2）交车方式为[委托交车]的一般销售订单和调拨订单，状态变为[交车确认中]
									pagePO.setInteger("So_Status",
											Integer.parseInt(DictCodeConstants.DICT_SO_STATUS_DELIVERY_CONFIRMING));
									pagePO.setInteger("Is_Upload", Integer.parseInt(DictCodeConstants.DICT_IS_NO));
									// 一般订单要记一下
									if (pagePO.get("Business_Type").toString()
											.equals(DictCodeConstants.DICT_SO_TYPE_GENERAL)) {
										this.setOrderStatusPO(auditDto.getAuditList(),
												DictCodeConstants.DICT_SO_STATUS_HAVE_OUT_STOCK, userId);
									}
									pagePO.setDate("Stock_Out_Date", new Date());
									pagePO.setLong("Stock_Out_By", userId);
								}
							}
							// 服务订单
							if (pagePO.get("Business_Type").toString().equals(DictCodeConstants.DICT_SO_TYPE_SERVICE)) {
								// 3）服务订单，售前装潢状态变为[已交车出库]（已完成），普通订单状态变为交车确认中
								/*
								 * if
								 * (poResult.getOrderSort().toString().equals(
								 * DictDataConstant.FDICT_SALES_ORDER_SORT_PRE))
								 * opo.setSoStatus(Utility.getInt(
								 * DictDataConstant.
								 * DICT_SO_STATUS_HAVE_OUT_STOCK)); else
								 */ // DMS-4952 调整售前订单审核通过后的状态为“交车确认中”
								if (pagePO.get("Order_Sort").toString()
										.equals(DictCodeConstants.FDICT_SALES_ORDER_SORT_PRE)) {
									List<Object> cus = new ArrayList<Object>();
									cus.add(FrameworkUtil.getLoginInfo().getDealerCode());
									cus.add(auditDto.getAuditList());
									List<SalesOrderPO> listSalesOrder = SalesOrderPO.findBySQL(
											"select * from tt_sales_order where DEALER_CODE= ? AND SO_NO= ? and d_key=0 ",
											cus.toArray());
									if (listSalesOrder != null && listSalesOrder.size() > 0) {
										SalesOrderPO salesOrder = (SalesOrderPO) listSalesOrder.get(0);
										if (salesOrder.get("Complete_Tag").toString()
												.equals(DictCodeConstants.DICT_IS_YES)) {
											pagePO.setInteger("So_Status",
													Integer.parseInt(DictCodeConstants.DICT_SO_STATUS_HAVE_OUT_STOCK));
											pagePO.setInteger("Is_Upload",
													Integer.parseInt(DictCodeConstants.DICT_IS_NO));
										} else {
											List listPart = this.querySoPart(
													FrameworkUtil.getLoginInfo().getDealerCode(),
													auditDto.getAuditList());

											List listUpholster = this.queryUpholster(
													FrameworkUtil.getLoginInfo().getDealerCode(),
													auditDto.getAuditList());

											if ((listPart == null || listPart.size() <= 0)
													&& (listUpholster == null || listUpholster.size() <= 0)) {
												pagePO.setInteger("So_Status", Integer
														.parseInt(DictCodeConstants.DICT_SO_STATUS_HAVE_OUT_STOCK));
												pagePO.setInteger("Is_Upload",
														Integer.parseInt(DictCodeConstants.DICT_IS_NO));
											} else {
												pagePO.setInteger("So_Status", Integer.parseInt(
														DictCodeConstants.DICT_SO_STATUS_DELIVERY_CONFIRMING));
												pagePO.setInteger("Is_Upload",
														Integer.parseInt(DictCodeConstants.DICT_IS_NO));
											}
										}
									}
								} else {
									pagePO.setInteger("So_Status",
											Integer.parseInt(DictCodeConstants.DICT_SO_STATUS_DELIVERY_CONFIRMING));
									pagePO.setInteger("Is_Upload", Integer.parseInt(DictCodeConstants.DICT_IS_NO));
								}
								pagePO.setDate("Stock_Out_Date", new Date());
								pagePO.setLong("Stock_Out_By", userId);
							}
							// 受托交车订单
							if (pagePO.get("Business_Type").toString()
									.equals(DictCodeConstants.DICT_SO_TYPE_ENTRUST_DELIVERY)) {
								// 4）受托交车订单，状态变为[交车确认中]
								pagePO.setInteger("So_Status",
										Integer.parseInt(DictCodeConstants.DICT_SO_STATUS_DELIVERY_CONFIRMING));
								this.setOrderStatusPO(auditDto.getAuditList(),
										DictCodeConstants.DICT_SO_STATUS_DELIVERY_CONFIRMING, userId);
								isConfirmed = true;
							}
							// 销售退回单
							if (pagePO.get("Business_Type").toString().equals(DictCodeConstants.DICT_SO_TYPE_RERURN)) {
								// 5）销售退回单，状态变为[已交车出库]（已完成）
								pagePO.setInteger("So_Status",
										Integer.parseInt(DictCodeConstants.DICT_SO_STATUS_HAVE_OUT_STOCK));
								pagePO.setInteger("Is_Upload", Integer.parseInt(DictCodeConstants.DICT_IS_NO));
							}
						}
					}

				}
				if (setUpdateAuditing(loginInfo.getDealerCode(), auditDto, loginInfo.getUserId()) == 0) {
					setOrderStatusPO(auditDto.getAuditList(), loginInfo.getDealerCode(), loginInfo.getUserId())
							.saveIt();

				}

			}
			pagePO.setInteger("D_KEY", Integer.parseInt(DictCodeConstants.D_KEY));

			pagePO.saveIt();

			SalesOrderPO sOrderPo = SalesOrderPO.findByCompositeKeys(loginInfo.getDealerCode(),
					auditDto.getAuditList());
			if (sOrderPo != null) {
				if (!StringUtils.isNullOrEmpty(sOrderPo.getString("INTENT_SO_NO"))
						&& (!commonNoService.getDefalutPara("8036").equals("12781001")
								&& sOrderPo.getInteger("SO_STATUS") > Integer
										.parseInt(DictCodeConstants.DICT_SO_STATUS_NOT_AUDIT)
								|| commonNoService.getDefalutPara("8036").equals("12781001")
										&& sOrderPo.getInteger("SO_STATUS") > Integer
												.parseInt(DictCodeConstants.DICT_SO_STATUS_ACCOUNTANT_AUDITING))) {
					List<Object> intentReceiveMoneyList = new ArrayList<Object>();
					intentReceiveMoneyList.add(loginInfo.getDealerCode());
					intentReceiveMoneyList.add(sOrderPo.getString("INTENT_SO_NO"));
					List<TtIntentReceiveMoneyPO> receivePo = TtIntentReceiveMoneyPO.findBySQL(
							"select * from TT_INTENT_RECEIVE_MONEY where DEALER_CODE= ? AND INTENT_SO_NO= ? ",
							intentReceiveMoneyList.toArray());
					if (receivePo != null && receivePo.size() > 0) {
						TtCustomerGatheringPO gatheringPO = new TtCustomerGatheringPO();
						for (int j = 0; j < receivePo.size(); j++) {
							TtIntentReceiveMoneyPO receive = receivePo.get(j);
							if (!StringUtils.isNullOrEmpty(receive.getInteger("IS_ADVANCE_PAYMENTS"))
									&& receive.getInteger("IS_ADVANCE_PAYMENTS") != 12781001) {
								String No = commonNoService.getSystemOrderNo(CommonConstants.SRV_SKDH);
								gatheringPO.setString("BILL_NO", receive.getString("BILL_NO"));
								gatheringPO.setString("CUSTOMER_NO", sOrderPo.getString("CUSTOMER_NO"));
								gatheringPO.setString("SO_NO", receive.getString("SO_NO"));
								gatheringPO.setInteger("GATHERING_TYPE", receive.getInteger("GATHERING_TYPE"));
								gatheringPO.setString("OWNED_BY", String.valueOf(loginInfo.getUserId()));
								gatheringPO.setString("PAY_TYPE_CODE", receive.getString("0004"));
								gatheringPO.setDouble("RECEIVE_AMOUNT", receive.getDouble("RECEIVE_AMOUNT"));
								gatheringPO.setDate("RECEIVE_DATE", receive.getDate("RECEIVE_DATE"));
								gatheringPO.setString("RECEIVE_NO", No);
								gatheringPO.setDate("RECORD_DATE", new Date());
								gatheringPO.setString("RECORDER", receive.getString("RECORDER"));
								gatheringPO.setString("TRANSACTOR", receive.getString("TRANSACTOR"));
								gatheringPO.setString("WRITEOFF_BY", receive.getString("WRITEOFF_BY"));
								gatheringPO.setDate("WRITEOFF_DATE", new Date());
								gatheringPO.setInteger("WRITEOFF_TAG", DictCodeConstants.STATUS_IS_YES);
								gatheringPO.setString("REMARK", receive.getString("REMARK"));
								gatheringPO.saveIt();
								// 更新客户资料表中收款总金额和帐户可用余额
								if (!StringUtils.isNullOrEmpty(sOrderPo.getString("CUSTOMER_NO"))) {
									PotentialCusPO cusPo = PotentialCusPO.findByCompositeKeys(loginInfo.getDealerCode(),
											sOrderPo.getString("CUSTOMER_NO"));
									if (cusPo != null) {
										Double receiveAmount = receive.getDouble("RECEIVE_AMOUNT");
										Double gatheredSum = 0D;
										Double usableAmount = 0D;
										if (cusPo.getDouble("GATHERED_SUM") != null) {
											gatheredSum = cusPo.getDouble("GATHERED_SUM") + receiveAmount;
										} else {
											gatheredSum = receiveAmount;
										}
										cusPo.setDouble("GATHERED_SUM", gatheredSum);
										if (cusPo.getDouble("USABLE_AMOUNT") != null) {
											usableAmount = cusPo.getDouble("USABLE_AMOUNT") + receiveAmount;
										} else {
											usableAmount = receiveAmount;
										}
										cusPo.setDouble("USABLE_AMOUNT", usableAmount);
										cusPo.saveIt();
									}
								}
								receive.setInteger("IS_ADVANCE_PAYMENTS", 12781001);
								receive.saveIt();
							}

						}
						List<Object> intentReceiveList = new ArrayList<Object>();
						intentReceiveList.add(loginInfo.getDealerCode());
						intentReceiveList.add(sOrderPo.getString("INTENT_SO_NO"));
						List<TtIntentReceiveMoneyPO> intentReceivePo = TtIntentReceiveMoneyPO.findBySQL(
								"select * from TT_INTENT_RECEIVE_MONEY where DEALER_CODE= ? AND INTENT_SO_NO= ? AND (IS_ADVANCE_PAYMENTS != 12781001 OR IS_ADVANCE_PAYMENTS IS NULL) ",
								intentReceiveList.toArray());
						if (!StringUtils.isNullOrEmpty(intentReceivePo) && intentReceivePo.size() > 0) {
							TtIntentSalesOrderPO intentOrderPO = TtIntentSalesOrderPO
									.findByCompositeKeys(loginInfo.getDealerCode(), sOrderPo.getString("INTENT_SO_NO"));
							if (intentOrderPO != null) {
								intentOrderPO.setInteger("SO_STATUS",
										Integer.parseInt(DictCodeConstants.DICT_INTENT_SO_STATUS_HAVE_CLOSED));
								intentOrderPO.saveIt();
							}
						}
					}

				}
				if (!StringUtils.isNullOrEmpty(sOrderPo.getInteger("BUSINESS_TYPE"))
						&& sOrderPo.getInteger("BUSINESS_TYPE") == Integer
								.parseInt(DictCodeConstants.DICT_SO_TYPE_RERURN)
						&& sOrderPo.getInteger("BUSINESS_TYPE") > Integer
								.parseInt(DictCodeConstants.DICT_SO_STATUS_ACCOUNTANT_AUDITING)) {
					SalesOrderPO ssOrderPo = SalesOrderPO.findByCompositeKeys(loginInfo.getDealerCode(),
							sOrderPo.getString("OLD_SO_NO"));
					if (ssOrderPo != null && !StringUtils.isNullOrEmpty(ssOrderPo.getString("INTENT_SO_NO"))) {
						TtIntentSalesOrderPO intentSalesOrder = TtIntentSalesOrderPO
								.findByCompositeKeys(loginInfo.getDealerCode(), ssOrderPo.getString("INTENT_SO_NO"));
						intentSalesOrder.setInteger("SO_STATUS",
								Integer.parseInt(DictCodeConstants.DICT_INTENT_SO_STATUS_FAIL));
						intentSalesOrder.saveIt();
					}
				} else if (sOrderPo.getInteger("BUSINESS_TYPE") == Integer
						.parseInt(DictCodeConstants.DICT_SO_STATUS_HAVE_CANCEL)) {
					TtIntentSalesOrderPO intentSalesOrder = TtIntentSalesOrderPO
							.findByCompositeKeys(loginInfo.getDealerCode(), sOrderPo.getString("INTENT_SO_NO"));
					intentSalesOrder.setInteger("SO_STATUS",
							Integer.parseInt(DictCodeConstants.DICT_INTENT_SO_STATUS_HAVE_BALANCE));
					intentSalesOrder.setInteger("IS_SALES_ORDER", Integer.parseInt(DictCodeConstants.DICT_IS_NO));
					intentSalesOrder.saveIt();
				}
				if ((sOrderPo.getInteger("PAY_OFF") == 12781001
						|| (sOrderPo.getInteger("LOSSES_PAY_OFF") != null
								&& sOrderPo.getInteger("LOSSES_PAY_OFF") == 12781001)
						|| (sOrderPo.getInteger("ORDER_SORT") != null && sOrderPo.getInteger("ORDER_SORT") == 13861002)
						|| !commonNoService.getDefalutPara("8036").equals("12781001"))
						&& (commonNoService.getDefalutPara("8003").equals("12781001")
								|| (sOrderPo.getInteger("ORDER_SORT") != null
										&& sOrderPo.getInteger("ORDER_SORT") == 13861002))
						&& (sOrderPo.getInteger("SO_STATUS") == 13011025
								|| sOrderPo.getInteger("SO_STATUS") == 13011030)) {
					if (sOrderPo.getInteger("BUSINESS_TYPE") == Integer
							.parseInt(DictCodeConstants.DICT_SO_TYPE_SERVICE)) {
						sOrderPo.setInteger("SO_STATUS", 13011035);
						sOrderPo.saveIt();
					}
				}

				//
			}
		}

	}

	@Override
	public void auditSalesOrderShut(FinanceShutOrderDTO financeShutDTO) throws ServiceBizException {
		System.err.println(financeShutDTO.getSoNos());
		String soNos = "";
		String[] soNo = null;
		if (!StringUtils.isNullOrEmpty(financeShutDTO.getSoNos())) {
			soNos = financeShutDTO.getSoNos().toString();
			soNo = soNos.split(",");
		}
		if (soNo != null && soNo.length > 0) {
			for (int i = 0; i < soNo.length; i++) {
				getsoNo(soNo[i]).saveIt();
				this.setOrderStatusPO(soNo[i], DictCodeConstants.DICT_SO_STATUS_CLOSED,
						FrameworkUtil.getLoginInfo().getUserId());
			}
		}
		/*
		 * if(financeShutDTO.getIntentList().size()>0 &&
		 * financeShutDTO.getIntentList() !=null){ for(FinanceShutTableDTO
		 * shutDto : financeShutDTO.getIntentList()){
		 * System.out.println(shutDto.getSoNo()); getsoNo(shutDto).saveIt();
		 * this.setOrderStatusPO(shutDto.getSoNos(),
		 * DictCodeConstants.DICT_SO_STATUS_CLOSED,
		 * FrameworkUtil.getLoginInfo().getUserId()); }
		 * 
		 * }
		 */

	}

	public SalesOrderPO getsoNo(String soNo) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		// String nowDate = df.format(new Date());// new Date()为获取当前系统时间
		SalesOrderPO so = SalesOrderPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(), soNo);
		so.setInteger("SO_STATUS", Integer.parseInt(DictCodeConstants.DICT_SO_STATUS_CLOSED));
		so.setTimestamp("BALANCE_CLOSE_TIME", df.format(new Date()));
		so.setString("OBLIGATED_OPERATOR", FrameworkUtil.getLoginInfo().getEmployeeNo());

		return so;
	}

	@Override
	public void auditSalesOrderMoney(FinanceShutOrderDTO financeShutDTO) throws ServiceBizException {
		String soNos = "";
		String[] soNo = null;
		if (!StringUtils.isNullOrEmpty(financeShutDTO.getSoNos())) {
			soNos = financeShutDTO.getSoNos().toString();
			soNo = soNos.split(",");
		}
		if (soNo != null && soNo.length > 0) {
			for (int i = 0; i < soNo.length; i++) {
				getsoNoRe(soNo[i]).saveIt();
			}
		}
		/*
		 * if(financeShutDTO.getIntentList().size()>0 &&
		 * financeShutDTO.getIntentList() !=null){ for(FinanceShutTableDTO
		 * shutDto : financeShutDTO.getIntentList()){
		 * System.out.println(shutDto.getSoNos()); getsoNoRe(shutDto).saveIt();
		 * 
		 * }
		 * 
		 * }
		 */

	}

	public SalesOrderPO getsoNoRe(String soNo) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		// String nowDate = df.format(new Date());// new Date()为获取当前系统时间
		SalesOrderPO so = SalesOrderPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(), soNo);
		so.setString("OTHER_PAY_OFF", DictCodeConstants.DICT_IS_YES);
		return so;
	}

	public TtOrderStatusUpdatePO setOrderStatusPO(String soNo, String soStatus, long userId) {
		TtOrderStatusUpdatePO apo = new TtOrderStatusUpdatePO();
		SalesOrderPO apoquery = new SalesOrderPO();
		List<Object> cus = new ArrayList<Object>();
		cus.add(FrameworkUtil.getLoginInfo().getDealerCode());
		cus.add(soNo);
		List<SalesOrderPO> listapo = SalesOrderPO.findBySQL(
				"select * from tt_sales_order where DEALER_CODE= ? AND SO_NO= ? and d_key=0 ", cus.toArray());
		if (listapo != null && listapo.size() > 0) {
			apoquery = (SalesOrderPO) listapo.get(0);
			if (apoquery.getInteger("Business_Type") != null
					&& (apoquery.get("Business_Type").toString().equals(DictCodeConstants.DICT_SO_TYPE_ENTRUST_DELIVERY)
							|| apoquery.get("Business_Type").toString().equals(DictCodeConstants.DICT_SO_TYPE_GENERAL))

			) {
				// apo.setItemId(POFactory.getLongPriKey(atxc.getConnection(),
				// apo));
				// apo.setEntityCode(ENTITY_CODE);
				apo.setString("SO_NO", soNo);
				apo.setInteger("So_Status", soStatus);
				apo.setLong("Owned_By", userId);
				apo.setInteger("Is_Upload", 12781002);
				apo.setDate("Alteration_Time", new Date());
				apo.setInteger("D_Key", DictCodeConstants.D_KEY);
				apo.saveIt();
			}
		}
		return apo;
	}

	public int setUpdateAuditing(String dealerCode, FinanceOrderDTO auditDto, long userId) {
		int i = 0;
		List<Object> wsItemList = new ArrayList<Object>();
		wsItemList.add(dealerCode);
		wsItemList.add(auditDto.getAuditList());
		List<SoAuditingPO> wsItemPo = SoAuditingPO.findBySQL(
				"select * from TT_SO_AUDITING where DEALER_CODE= ? AND SO_NO= ? order by CREATED_AT desc ",
				wsItemList.toArray());
		if (wsItemPo != null && wsItemPo.size() > 0) {
			SoAuditingPO aPo = wsItemPo.get(0);
			aPo.setInteger("AUDITING_RESULT", auditDto.getAuditingResult());
			aPo.setString("AUDITING_POSTIL", auditDto.getAuditingPostil());
			aPo.setLong("AUDITED_BY", userId);
			aPo.setDate("AUDITING_DATE", new Date());
			aPo.saveIt();
			i = 1;
		}
		return i;
	}

	public List<Map> queryAuditingUser(String auditingType, String DefaultPara1, String DefaultPara2, String entityCode,
			Long userId) {
		StringBuffer sql = new StringBuffer("");
		sql.append(" SELECT A.USER_ID,A.USER_CODE,A.USER_NAME,A.EMPLOYEE_NO,A.ORG_CODE,B.EMPLOYEE_NAME, "
				+ " CASE WHEN MENU_ID='70105000' THEN '经理审核' ELSE '财务审核' END AS FUNCTION_NAME"
				+ " ,CASE WHEN MENU_ID = '70101300' THEN '70302000' ELSE MENU_ID END AS FUNCTION_CODE  "
				+ " FROM TM_USER A ,TM_EMPLOYEE B,TM_USER_MENU C " + " WHERE A.DEALER_CODE = B.DEALER_CODE   " + // --A.ORG_CODE
																													// =
																													// B.ORG_CODE
																													// "
																													// +
				" AND A.DEALER_CODE = C.DEALER_CODE AND A.EMPLOYEE_NO = B.EMPLOYEE_NO " +

				" AND C.USER_ID = A.USER_ID  " + // AND A.D_KEY =" +
													// CommonConstant.D_KEY +
				" AND A.DEALER_CODE = '" + entityCode + "'" + " AND A.USER_STATUS = "
				+ DictCodeConstants.DICT_IN_USE_START + " AND A.USER_ID != " + userId);

		if (null != auditingType && auditingType.equals(DictCodeConstants.DICT_AUDITING_TYPE_FINANCE)) {
			sql.append("  AND MENU_ID = '70101300' ");
		} else if (null != auditingType && auditingType.equals(DictCodeConstants.DICT_AUDITING_TYPE_MANAGE)) {
			if ((DefaultPara1 != null && DefaultPara1.trim().equals(DictCodeConstants.DICT_IS_YES.trim()))
					&& (DefaultPara2 == null || !DefaultPara2.trim().equals(DictCodeConstants.DICT_IS_YES.trim()))) {
				sql.append("  AND MENU_ID = '70105000' ");
			} else if ((DefaultPara2 != null && DefaultPara2.trim().equals(DictCodeConstants.DICT_IS_YES.trim()))
					&& (DefaultPara1 == null || !DefaultPara1.trim().equals(DictCodeConstants.DICT_IS_YES.trim()))) {
				sql.append("  AND MENU_ID = '70101300' ");
			} else {
				sql.append("  AND (MENU_ID = '70105000' OR MENU_ID = '70101300' )");
			}
		} else {
			sql.append("  AND MENU_ID = '70105000' ");
		}
		List<Object> queryParams = new ArrayList<Object>();
		System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%" + sql.toString());
		return Base.findAll(sql.toString(), queryParams.toArray());
	}

	public List<Map> querySoPart(String dealerCode, String soNo) {
		String sql = null;
		List<Object> queryParams = new ArrayList<Object>();

		sql = "SELECT * FROM TT_SO_UPHOLSTER WHERE DEALER_CODE = '" + dealerCode + "' AND SO_NO = '" + soNo + "' ";
		return Base.findAll(sql.toString(), queryParams.toArray());

	}

	public List<Map> queryUpholster(String dealerCode, String soNo) {

		String sql = null;
		List<Object> queryParams = new ArrayList<Object>();

		sql = "SELECT * FROM TT_SO_PART WHERE DEALER_CODE = '" + dealerCode + "' AND SO_NO = '" + soNo + "' "
				+ " AND IS_FINISHED != 12781001";
		return Base.findAll(sql.toString(), queryParams.toArray());

	}

}
