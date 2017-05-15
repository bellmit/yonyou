
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.retail
*
* @File name : SettleCollectionServiceImpl.java
*
* @Author : xukl
*
* @Date : 2016年10月14日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年10月14日    xukl    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.retail.service.ordermanage;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.PotentialCusPO;
import com.yonyou.dms.common.domains.PO.basedata.TtCustomerGatheringPO;
import com.yonyou.dms.commonSales.domains.PO.stockmanage.SalesOrderPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.domains.PO.TmDefaultParaPO;
import com.yonyou.dms.framework.service.CommonNoService;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.DateUtil;
import com.yonyou.dms.function.utils.common.NumberUtil;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.retail.domains.DTO.ordermanage.CustomerGatheringDTO;
import com.yonyou.dms.retail.domains.DTO.ordermanage.GathringMaintainDTO;

/**
 * 结算收款实现
 * 
 * @author xukl
 * @date 2016年10月14日
 */
@Service
@SuppressWarnings("rawtypes")
public class SettleCollectionServiceImpl implements SettleCollectionService {
	@Autowired
	private CommonNoService commonNoService;

	/**
	 * 查询收款登记记录
	 * 
	 * @author xukl
	 * @date 2016年10月14日
	 * @param id
	 * @return
	 */
	@Override
	public PageInfoDto qryCustomerGathering(Map<String, String> queryParam) throws ServiceBizException {
		StringBuffer sql = new StringBuffer("");
		List<Object> cus = new ArrayList<Object>();
		cus.add(FrameworkUtil.getLoginInfo().getDealerCode());
		List<TmDefaultParaPO> listapo = TmDefaultParaPO
				.findBySQL("select * from Tm_Default_Para where DEALER_CODE= ? AND item_code=8036 ", cus.toArray());
		List<Object> params = new ArrayList<Object>();
		String seBusinessType = "";
		String seSoNO = "";
		String seName = "";
		String sePayOff = "";
		String seCerNo = "";
		String seLicense = "";
		String seVin = "";
		String user = "";
		String sheetCreatedDateStart = "";
		String sheetCreatedDateEnd = "";
		// if (byUser != 0) {
		// user = " and a.RE_AUDITED_BY = " + byUser + " ";
		// } else {
		// user = " and 1 =1 ";
		// }
		if (!StringUtils.isNullOrEmpty(queryParam.get("payOff1"))) {
			if (queryParam.get("payOff1").trim().toString().equals(DictCodeConstants.DICT_IS_YES))
				sePayOff = " and a.PAY_OFF = " + DictCodeConstants.DICT_IS_YES + " ";
			else
				sePayOff = " and (a.PAY_OFF != " + DictCodeConstants.DICT_IS_YES + " OR a.PAY_OFF IS NULL) ";
		} else {
			sePayOff = " and 1 =1 ";
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("businessType1"))) {
			seBusinessType = " and  a.BUSINESS_TYPE =  " + queryParam.get("businessType1") + "  ";

		} else {
			seBusinessType = " and  1=1 ";
		}

		if (!StringUtils.isNullOrEmpty(queryParam.get("soNo1"))) {
			seSoNO = " and A.SO_NO like ?";
			params.add("%" + queryParam.get("soNo1") + "%");
		} else {
			seSoNO = " and  1=1 ";
		}

		if (!StringUtils.isNullOrEmpty(queryParam.get("customerName1"))) {
			seName = " and A.customer_name like ?";
			params.add("%" + queryParam.get("customerName1") + "%");
		} else {
			seName = " and  1=1 ";
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("certificateNo1"))) {
			seCerNo = " and A.CERTIFICATE_NO like ?";
			params.add("%" + queryParam.get("certificateNo1") + "%");

		} else {
			seCerNo = " and  1=1 ";
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("vin1"))) {
			seName = "and A.VIN like ?";
			params.add("%" + queryParam.get("vin1") + "%");
		} else {
			seVin = " and  1=1 ";
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("license1"))) {
			seLicense = " and A.LICENSE like ?";
			params.add("%" + queryParam.get("license1") + "%");
		} else {
			seLicense = " and  1=1 ";
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("sheetCreatedDateStart"))) {
			sheetCreatedDateStart = " AND A.SHEET_CREATE_DATE >= ? ";
			params.add(DateUtil.parseDefaultDate(queryParam.get("sheetCreatedDateStart")));
		} else {
			sheetCreatedDateStart = " and  1=1 ";
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("sheetCreatedDateEnd"))) {
			sheetCreatedDateEnd = " AND A.SHEET_CREATE_DATE < ? ";
			params.add(DateUtil.addOneDay(queryParam.get("sheetCreatedDateEnd")));
		} else {
			sheetCreatedDateEnd = "and  1=1 ";
		}
		sql.append(
				" select  USER.USER_NAME AS RE_AUDITED_NAME,US.USER_NAME AS SOLD_NAME,MO.MODEL_NAME,CON.CONFIG_NAME,COL.COLOR_NAME,A.DEALER_CODE,a.SO_NO, a.BUSINESS_TYPE, CASE WHEN A.BUSINESS_TYPE=13001002 THEN T.CUSTOMER_NO ELSE A.CUSTOMER_NO END AS CUSTOMER_NO,"
						+ "a.OWNER_NO,CASE  WHEN A.BUSINESS_TYPE=13001002 THEN T.CUSTOMER_NAME ELSE A.CUSTOMER_NAME END AS CUSTOMER_NAME,a.LOCK_USER, "
						+ " a.CUSTOMER_TYPE, a.CONTACTOR_NAME, a.PHONE, a.ADDRESS, a.DELIVERY_ADDRESS, "
						+ " a.CT_CODE, a.CERTIFICATE_NO, a.SO_STATUS, a.SHEET_CREATE_DATE, "
						+ " a.SHEET_CREATED_BY, a.CONTRACT_NO, CASE  WHEN A.BUSINESS_TYPE=13001002 THEN T.CONTRACT_DATE ELSE A.CONTRACT_DATE END AS CONTRACT_DATE,"
						+ " a.CONTRACT_EARNEST,CASE WHEN A.BUSINESS_TYPE=13001002 THEN T.CONTRACT_MATURITY ELSE A.CONTRACT_MATURITY END AS CONTRACT_MATURITY,"
						+ " a.PAY_MODE, a.LOAN_ORG, a.DELIVERY_MODE, a.CONSIGNEE_CODE, "
						+ " a.CONSIGNEE_NAME, a.DELIVERING_DATE, a.SOLD_BY, a.AUDITED_BY, a.RE_AUDITED_BY, "
						+ " a.REMARK, a.PRODUCT_CODE, a.PRE_INVOICE_AMOUNT, a.INVOICE_MODE, a.LICENSE, "
						+ " a.STORAGE_CODE, a.STORAGE_POSITION_CODE, a.DISPATCHED_DATE, a.DISPATCHED_BY, a.VIN, "
						+ " a.CONFIRMED_DATE, a.VEHICLE_PURPOSE, a.DIRECTIVE_PRICE, a.CONFIRMED_BY, "
						+ " a.STOCK_OUT_DATE, a.STOCK_OUT_BY, a.RETURN_IN_DATE, a.RETURN_IN_BY, a.IS_PERMUTED, "
						+ " a.PERMUTED_VIN, a.PERMUTED_DESC, a.VEHICLE_PRICE, a.PRESENT_SUM, a.OFFSET_AMOUNT, "
						+ " a.GARNITURE_SUM, a.UPHOLSTER_SUM, a.ORDER_RECEIVABLE_SUM, a.ABORTING_FLAG, "
						+ " a.ABORTING_DATE, a.ABORTING_BY, a.ABORTING_REASON, a.PENALTY_AMOUNT, a.ABORTED_DATE, "
						+ " a.ABORTED_BY, a.INSURANCE_SUM, a.ORDER_PAYED_AMOUNT, a.TAX_SUM, a.ORDER_DERATED_SUM, "
						+ " a.PLATE_SUM, a.ORDER_ARREARAGE_AMOUNT, a.LOAN_SUM, a.CON_RECEIVABLE_SUM, "
						+ " a.CON_PAYED_AMOUNT, a.ORDER_SUM, a.CON_ARREARAGE_AMOUNT, a.PAY_OFF,P.GATHERED_SUM,P.ORDER_PAYED_SUM,P.CON_PAYED_SUM,P.USABLE_AMOUNT AS CALC_USABLE_AMOUNT,P.UN_WRITEOFF_SUM, "
						+ " a.ALLOCATING_TYPE, a.CONSIGNER_CODE, a.CONSIGNER_NAME, a.CONSIGNED_SUM, a.OWNED_BY,a.VER, "
						+ " b.MODEL_CODE, b.CONFIG_CODE, COALESCE(a.COLOR_CODE,b.COLOR_CODE) as COLOR_CODE  from TT_SALES_ORDER a "
						+ " LEFT JOIN TM_POTENTIAL_CUSTOMER P on P.DEALER_CODE=a.DEALER_CODE AND P.CUSTOMER_NO=a.CUSTOMER_NO "
						+ " LEFT JOIN TM_USER US ON A.SOLD_BY = US.USER_ID AND A.DEALER_CODE = US.DEALER_CODE"
						+ " LEFT JOIN ( SELECT DEALER_CODE, CUSTOMER_CODE AS CUSTOMER_NO, CUSTOMER_NAME,CONTRACT_NO,"
						+ " AGREEMENT_BEGIN_DATE AS CONTRACT_DATE, AGREEMENT_END_DATE   AS CONTRACT_MATURITY FROM TM_PART_CUSTOMER) T "
						+ " ON A.DEALER_CODE=T.DEALER_CODE AND A.CONSIGNEE_CODE=T.CUSTOMER_NO" + " left join ("
						+ CommonConstants.VM_VS_PRODUCT
						+ ") b on (a.PRODUCT_CODE = b.PRODUCT_CODE) and (a.DEALER_CODE = b.DEALER_CODE) and (a.D_KEY = b.D_KEY) "
						+ " " + " LEFT JOIN TM_MODEL MO ON B.MODEL_CODE = MO.MODEL_CODE AND B.DEALER_CODE = MO.DEALER_CODE AND MO.IS_VALID = 12781001 "
						+ " LEFT JOIN TM_CONFIGURATION CON ON B.CONFIG_CODE = CON.CONFIG_CODE AND  B.DEALER_CODE = CON.DEALER_CODE"
						+ " LEFT JOIN TM_COLOR COL ON COALESCE(a.COLOR_CODE, b.COLOR_CODE) = COL.COLOR_CODE AND  B.DEALER_CODE = COL.DEALER_CODE"
						+ " LEFT JOIN TM_USER USER ON A.RE_AUDITED_BY = USER.USER_ID AND A.DEALER_CODE = USER.DEALER_CODE "
						+ "  WHERE a.DEALER_CODE = '" + FrameworkUtil.getLoginInfo().getDealerCode() + "' "
						+ " and a.D_KEY = " + DictCodeConstants.D_KEY + " "
						// (产品逻辑)除业务类型为[销售退回]的订单外，可以查询到除了[未提交、已退回]状态外的所有类型的销售订单
						+ " AND ((a.BUSINESS_TYPE <> " + DictCodeConstants.DICT_SO_TYPE_RERURN + " and a.SO_STATUS <> "
						+ DictCodeConstants.DICT_SO_STATUS_NOT_AUDIT + " " + " and a.SO_STATUS <> ");
		// (长久改造后逻辑)必须审核通过后才能结算收款
		if (listapo.get(0).get("Default_Value") != null
				&& listapo.get(0).getString("Default_Value").trim().equals(DictCodeConstants.DICT_IS_YES.trim()))
			sql.append(DictCodeConstants.DICT_SO_STATUS_MANAGER_AUDITING + " " + " and a.SO_STATUS <> "
					+ DictCodeConstants.DICT_SO_STATUS_ACCOUNTANT_AUDITING + " " + " and a.SO_STATUS <> "
					+ DictCodeConstants.DICT_SO_STATUS_MANAGER_REJECT + " " + " and a.SO_STATUS <> "
					+ DictCodeConstants.DICT_SO_STATUS_ACCOUNTANT_REJECT + " " + " and a.SO_STATUS <> "
					+ DictCodeConstants.DICT_SO_STATUS_SYSTEM_REJECT + " " + " and a.SO_STATUS <> ");
		sql.append(DictCodeConstants.DICT_SO_STATUS_HAVE_UNTREAD + ") "
		// 对于业务类型为[销售退回]的订单，只能查询到[退回已入库]状态下的订单
				+ " OR (a.BUSINESS_TYPE = " + DictCodeConstants.DICT_SO_TYPE_RERURN + " and (a.SO_STATUS = "
				+ DictCodeConstants.DICT_SO_STATUS_HAVE_OUT_STOCK + " or a.SO_STATUS = "
				+ DictCodeConstants.DICT_SO_STATUS_CLOSED

				+ "))) " + user + seBusinessType + seSoNO + seName + sheetCreatedDateStart + sheetCreatedDateEnd
				+ sePayOff + seLicense + seVin + seCerNo);
		sql.append(" and IFNULL(a.ORDER_SORT, 0)" +
		/* "coalesce(a.ORDER_SORT,0)" */

				"<>" + DictCodeConstants.FDICT_SALES_ORDER_SORT_PRE + " ");
		System.err.println(sql.toString());
		PageInfoDto pageInfoDto = DAOUtil.pageQuery(sql.toString(), params);
		return pageInfoDto;
	}

	public List<Map> qryCustomer(String wsNo, String dealerCode) throws ServiceBizException {
		StringBuffer sql = new StringBuffer();
		sql.append(
				" select a.DEALER_CODE,'' AS CUSTOMER_NO,OWNER_NAME AS CUSTOMER_NAME,OWNER_PROPERTY AS CUSTOMER_TYPE,PHONE AS CONTACTOR_PHONE,"
						+ " MOBILE AS CONTACTOR_MOBILE,0 AS INTENT_LEVEL,0 AS CUS_SOURCE,0 AS GATHERED_SUM,0 AS ORDER_PAYED_SUM,0 AS CON_PAYED_SUM,"
						+ " 0 AS USABLE_AMOUNT,0 AS UN_WRITEOFF_SUM,ADDRESS,CT_CODE,CERTIFICATE_NO,PROVINCE,CITY,DISTRICT,"
						+ " GENDER,ZIP_CODE,E_MAIL,CONTACTOR_FAX AS FAX,CONTACTOR_HOBBY_CONTACT AS BEST_CONTACT_TYPE,BIRTHDAY from TM_OWNER a where 1=1 "
						+ " and a.dealer_code='" + FrameworkUtil.getLoginInfo().getDealerCode()
						+ "' and a.OWNER_NO LIKE '%" + wsNo + "%'  ");
		List queryParam = new ArrayList();
		List<Map> result = DAOUtil.findAll(sql.toString(), queryParam);
		System.err.println("tm_owner"+sql.toString());
		return result;
	}
	
	public List<Map> qryCustomer2(String cusNo, String dealerCode) throws ServiceBizException {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT a.*,a.ADDRESS AS FADDRESS,a.BIRTHDAY as FBIRTHDAY FROM TM_POTENTIAL_CUSTOMER a WHERE a.DEALER_CODE = '"+FrameworkUtil.getLoginInfo().getDealerCode()+"' AND a.CUSTOMER_NO = '"+cusNo+"'");
		List queryParam = new ArrayList();
		List<Map> result = DAOUtil.findAll(sql.toString(), queryParam);
		System.err.println("tm_owner2"+sql.toString());
		return result;
	}
	

	public List<Map> qrySupple(String vin, String license) throws ServiceBizException {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append(" select distinct d.* from tm_member_card b left join  tm_member a on "
				+ " b.dealer_code = a.dealer_code and b.member_no=a.member_no left join  "
				+ " tm_member_vehicle c on b.dealer_code = c.dealer_code and b.card_id = c.card_id "
				+ " left join tm_vehicle f on c.dealer_code = f.dealer_code and c.vin=f.vin "
				+ " left join tM_MEMBER_ACCOUNT d on d.CARD_ID=b.CARD_ID and d.dealer_CODE=b.dealer_CODE "
				+ " where a.dealer_code='" + FrameworkUtil.getLoginInfo().getDealerCode() + "' "
				+ " and a.member_status=" + DictCodeConstants.DICT_MEMBER_STATUS_NORMAL + " and b.card_status= "
				+ DictCodeConstants.DICT_CARD_STATUS_NORMAL + "" + " and 1=1  ");
		if (!StringUtils.isNullOrEmpty(vin)) {
			sql.append(" and c.vin='" + vin + "' ");
		}

		if (!StringUtils.isNullOrEmpty(license)) {
			sql.append(" and f.LICENSE = '" + license + "' ");
		}
		List queryParam = new ArrayList();
		List<Map> result = DAOUtil.findAll(sql.toString(), queryParam);
		return result;
	}

	public List<Map> qrySales(String soNo, String dealerCode) throws ServiceBizException {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append(
				" SELECT DISTINCT ef.PRODUCTING_AREA, ef.ENGINE_NO, ef.CERTIFICATE_NUMBER,A.*,   B.WHOLESALE_DIRECTIVE_PRICE,B.BRAND_CODE,B.SERIES_CODE,B.MODEL_CODE,0 AS OLD_VEHICLE_PRICE,0  AS OLD_ORDER_PAYED_AMOUNT, "
						+ " B.CONFIG_CODE, B.PRODUCT_NAME AS PRODUCT_NAME,O.ORG_NAME ");
		sql.append(" FROM TT_SALES_ORDER A " + " LEFT JOIN (" + CommonConstants.VM_VS_PRODUCT
				+ ") B  ON A.PRODUCT_CODE = B.PRODUCT_CODE AND A.dealer_code = B.dealer_code AND A.D_KEY= B.D_KEY "
				+ " LEFT JOIN TM_USER U ON A.dealer_code = U.dealer_code AND A.SOLD_BY= U.USER_ID "
				+ " LEFT JOIN TM_ORGANIZATION O ON A.dealer_code = O.dealer_code AND U.ORG_CODE=O.ORG_CODE ");
		sql.append(" left join TT_VS_STOCK_ENTRY_ITEM ef on (ef.dealer_code = a.dealer_code and ef.vin = a.vin) ");
		sql.append(" WHERE 1=1 AND  A.SO_NO='" + soNo + "' and a.dealer_code='"
				+ FrameworkUtil.getLoginInfo().getDealerCode() + "' and A.D_KEY = " + DictCodeConstants.D_KEY);

		List<Map> result = DAOUtil.findAll(sql.toString(), params);
		
		System.err.println("TT_SALES_ORDER"+sql.toString());
		return result;
	}

	public PageInfoDto qryCustomerGa(Map<String, String> queryParam, String cusNo) throws ServiceBizException {
		StringBuffer sql = new StringBuffer("");		
		sql.append(
				"select a.*,b.PAY_TYPE_NAME,C.USER_NAME from Tt_Customer_Gathering a left join TM_PAY_TYPE b on a.PAY_TYPE_CODE = b.PAY_TYPE_CODE "
				+ " LEFT JOIN TM_USER C ON A.WRITEOFF_BY = C.USER_ID AND A.DEALER_CODE = C.DEALER_CODE where a.so_no='"
				+ cusNo + "' ");
		List<Object> params = new ArrayList<Object>();
		PageInfoDto pageInfoDto = DAOUtil.pageQuery(sql.toString(), params);
		System.err.println(sql.toString());
		return pageInfoDto;
	}

	/**
	 * @author LiGaoqi
	 * @date 2017年3月21日
	 * @param soNo
	 * @param receiveNo
	 * @return
	 * @throws ServiceBizException
	 *             (non-Javadoc)
	 * @see com.yonyou.dms.retail.service.ordermanage.SettleCollectionService#queryreceiveNo(java.lang.String,
	 *      java.lang.String)
	 */

	@Override
	public List<Map> queryreceiveNo(String soNo, String receiveNo) throws ServiceBizException {
		StringBuffer sql = new StringBuffer("");
		sql.append(
				"select A.*,B.PAY_TYPE_NAME from Tt_Customer_Gathering A LEFT JOIN TM_PAY_TYPE B ON A.PAY_TYPE_CODE = B.PAY_TYPE_CODE where A.SO_NO='"
						+ soNo + "' and A.RECEIVE_NO='" + receiveNo + "' and A.DEALER_CODE='"
						+ FrameworkUtil.getLoginInfo().getDealerCode() + "'");
		List<Object> params = new ArrayList<Object>();
		List<Map> list = DAOUtil.findAll(sql.toString(), params);
		return list;
	}

	/**
	 * 通过id查询单条收款记录
	 * 
	 * @author xukl
	 * @date 2016年10月17日
	 * @param id
	 * @return
	 * @throws ServiceBizException
	 *             (non-Javadoc)
	 * @see com.yonyou.dms.retail.service.ordermanage.SettleCollectionService#qryCustGathering(java.lang.Long)
	 */
	public static List queryOrderISReturn(String dealerCode, String SONO) throws ServiceBizException {

		StringBuffer sql = new StringBuffer("");
		sql.append("SELECT A.CUSTOMER_NAME,\n" + "       B.BRAND_CODE,\n" + "       B.SERIES_CODE,\n"
				+ "       B.MODEL_CODE,\n" + "       A.IS_SPEEDINESS,\n" + "       B.CONFIG_CODE,\n"
				+ "       COALESCE(A.COLOR_CODE,B.COLOR_CODE) as COLOR_CODE,\n" + "       A.dealer_code,\n"
				+ "       A.VIN,\n" + "       A.SOLD_BY,\n" + "       A.SO_NO,\n" + "       A.SHEET_CREATE_DATE,\n"
				+ "       A.SO_STATUS,\n" + "       A.CUSTOMER_NO,\n" + "       A.CONTRACT_NO,\n"
				+ "       A.CONTRACT_DATE,\n" + "       A.OWNED_BY\n" + "  FROM    TT_SALES_ORDER A\n"
				+ "       LEFT JOIN\n" + "          (" + CommonConstants.VM_VS_PRODUCT + ") B\n"
				+ "       ON A.PRODUCT_CODE = B.PRODUCT_CODE\n" + "      AND A.dealer_code = B.dealer_code\n"
				+ "      AND A.D_KEY = B.D_KEY\n" + " WHERE A.D_KEY = 0  AND A.BUSINESS_TYPE = 13001001\n"
				+ "   AND A.SO_NO NOT IN\n" + "             (SELECT OLD_SO_NO\n"
				+ "                FROM TT_SALES_ORDER\n" + "               WHERE BUSINESS_TYPE = 13001005\n"
				+ "                 AND dealer_code = '" + dealerCode + "'\n" + "                 AND D_KEY = 0\n"
				+ "                 AND SO_STATUS != 13011055)\n" + "   AND A.dealer_code = '" + dealerCode + "'\n"
				+ "   AND A.SO_NO LIKE '%" + SONO + "%'"

		);
		List<Object> params = new ArrayList<Object>();
		List<Map> list = DAOUtil.findAll(sql.toString(), params);
		return list;
	}

	@Override
	public Map qryCustGathering(Long id) throws ServiceBizException {
		StringBuilder sb = new StringBuilder(
				"SELECT tcg.RECEIVE_ID, tcg.DEALER_CODE, tcg.RECEIVE_NO, tcg.PAY_TYPE_CODE, tcg.RECEIVE_AMOUNT, tcg.RECEIVE_DATE, tcg.GATHERING_TYPE, tcg.BILL_NO, tcg.CUSTOMER_NO, tcg.WRITEOFF_TAG, tcg.WRITEOFF_BY, tcg.WRITEOFF_DATE, tcg.REMARK, tcg.RECORDER, tcg.RECORD_DATE, tso.SO_NO, tcg.SO_NO_ID, tso.ORDER_RECEIVABLE_SUM as ORDER_RECEIVABLE_SUMT FROM TT_CUSTOMER_GATHERING tcg LEFT JOIN tt_sales_order tso ON tso.SO_NO_ID = tcg.SO_NO_ID and tso.DEALER_CODE = tcg.DEALER_CODE WHERE tcg.RECEIVE_ID = ?");
		List<Object> params = new ArrayList<Object>();
		params.add(id);
		List<Map> list = DAOUtil.findAll(sb.toString(), params);
		return list.get(0);
	}

	/**
	 * 新增收款登记记录
	 * 
	 * @author xukl
	 * @date 2016年10月14日
	 * @param customerGatheringDTO
	 * @param receiveNo
	 * @return
	 * @throws ServiceBizException
	 *             (non-Javadoc)
	 * @see com.yonyou.dms.retail.service.ordermanage.SettleCollectionService#addCustomerGathering(com.yonyou.dms.retail.domains.DTO.ordermanage.CustomerGatheringDTO,
	 *      java.lang.String)
	 */
	@Override
	public Long addCustomerGathering(CustomerGatheringDTO customerGatheringDTO, String receiveNo)
			throws ServiceBizException {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		// 更新销售订单已收金额
		SalesOrderPO salesOrderpo = SalesOrderPO.findById(customerGatheringDTO.getSoNoId());
		Double orderPayedAmount = salesOrderpo.getDouble("ORDER_PAYED_AMOUNT") != null
				? salesOrderpo.getDouble("ORDER_PAYED_AMOUNT") : 0;

		salesOrderpo.setDouble("ORDER_PAYED_AMOUNT", orderPayedAmount + customerGatheringDTO.getReceiveAmount());
		salesOrderpo.saveIt();

		// 生成收款记录
		TtCustomerGatheringPO cstGatherPO = new TtCustomerGatheringPO();
		setCustomerGatheringPO(customerGatheringDTO, cstGatherPO);
		cstGatherPO.setString("RECEIVE_NO", receiveNo);// 收款单号
		// cstGatherPO.setString("WRITEOFF_BY",loginInfo.getDealerName());//销账人
		cstGatherPO.setTimestamp("RECORD_DATE", new Date());// 登记时间
		cstGatherPO.saveIt();

		// 重新计算潜客表里面已收款金额 订单支出 可用余额 未销账金额
		updateCustomerMoney(salesOrderpo, customerGatheringDTO);

		return cstGatherPO.getLongId();
	}

	@Override
	public List<Map> addCustomerGatheringMain(GathringMaintainDTO dto) throws ServiceBizException {
		List<Map> id;
		System.out.println(dto.getWriteoffTag());
		System.out.println(dto.getReceiveNo());
		StringBuilder sql = new StringBuilder();
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		if (StringUtils.isNullOrEmpty(dto.getReceiveNo())) {

			TtCustomerGatheringPO gatheringPO = new TtCustomerGatheringPO();
			String NO = commonNoService.getSystemOrderNo(CommonConstants.SRV_SKDH); // 收款单号
			// 更新销售订单已收金额
			String receiveNo = dto.getReceiveNo();
			String customerNo = dto.getCustomerNo();
			Date receiveDate = dto.getReceiveDate();
			Integer writeoffTag = dto.getWriteoffTag();
			Date writeoffDate = dto.getWriteoffDate();
			// Long ownedBy = actionContext
			// .getLongValue("TT_CUSTOMER_GATHERING.OWNED_BY");
			String payTypeCode = dto.getPayTypeCode();
			String billNo = dto.getBillNo();
			Double receiveAmount = dto.getReceiveAmount();
			String remark = dto.getRemark();
			String transactor = dto.getTransactor();
			Long writeoffBy = dto.getWriteoffBy();
			Integer gatheringType = dto.getGatheringType();

			String soNo = dto.getSoNo();
			String sFLAG = dto.getFbiaoz();
			// String isNoPay = actionContext.getStringValue("IS_NO_PAY");
			Double ORDER_RETUR_PRICE = dto.getFdorderReturnPrice();

			Double PENALTY_AMOUNT = dto.getFdweiyuePrice();
			Integer isBalance = dto.getIsblance();
			// String cardId=actionContext.getStringValue("CARD_ID");
			// String useStore=actionContext.getStringValue("USE_STORE");
			// String useCredit=actionContext.getStringValue("USE_CREDIT");
			// String
			// creditAmount=actionContext.getStringValue("CREDIT_AMOUNT");
			String empNo = FrameworkUtil.getLoginInfo().getEmployeeNo();
			Long userId = FrameworkUtil.getLoginInfo().getUserId();
			if (soNo != null && !"".equals(soNo) && sFLAG != null && !"".equals(sFLAG) && sFLAG.equals("2")
					&& receiveAmount != null && receiveAmount < 0 && ORDER_RETUR_PRICE != null
					&& !"".equals(ORDER_RETUR_PRICE) && ORDER_RETUR_PRICE > 0) {// 退回且在做退款时
				double sjytprice = 0;// 实际已经应退金额
				List<Object> cus = new ArrayList<Object>();
				cus.add(FrameworkUtil.getLoginInfo().getDealerCode());
				cus.add(DictCodeConstants.D_KEY);
				cus.add(soNo);
				cus.add(DictCodeConstants.DICT_IS_YES);
				List<TtCustomerGatheringPO> OrderSKPOConlist = TtCustomerGatheringPO.findBySQL(
						"select * from TT_CUSTOMER_GATHERING where DEALER_CODE= ? AND D_KEY=? and SO_NO=? AND WRITEOFF_TAG=?  ",
						cus.toArray());
				TtCustomerGatheringPO OrderSKPOCon = new TtCustomerGatheringPO();
				if (OrderSKPOConlist != null && OrderSKPOConlist.size() > 0) {
					for (int i = 0; i < OrderSKPOConlist.size(); i++) {
						OrderSKPOCon = (TtCustomerGatheringPO) OrderSKPOConlist.get(i);
						if (OrderSKPOCon.get("RECEIVE_AMOUNT") != null
								&& OrderSKPOCon.getDouble("RECEIVE_AMOUNT") < 0) {
							sjytprice = sjytprice + OrderSKPOCon.getDouble("RECEIVE_AMOUNT"); // 只加应退金额
						}

					}

				}
				sjytprice = sjytprice + receiveAmount;
				sjytprice = Math.abs(sjytprice);
				if (ORDER_RETUR_PRICE < sjytprice) {// 应退金额小于实际已退金额要提示

					throw new ServiceBizException("实际退款金额不能大于应退金额");
				}
			}
			// 做挂账的判断

			if (receiveNo == null || receiveNo.trim().length() == 0) { // 预收款登记新增

				gatheringPO.setString("Bill_No", billNo);

				gatheringPO.setString("Customer_No", customerNo);
				gatheringPO.setString("So_No", soNo);

				gatheringPO.setInteger("Gathering_Type", gatheringType);
				// gatheringPO.setOwnedBy(ownedBy);
				gatheringPO.setString("Pay_Type_Code", payTypeCode);
				gatheringPO.setDouble("Receive_Amount", receiveAmount);
				gatheringPO.setDate("Receive_Date", receiveDate);
				gatheringPO.setString("Receive_No", NO);
				gatheringPO.setDate("Record_Date", new Date());
				gatheringPO.setLong("Recorder", userId);
				gatheringPO.setString("Remark", remark);
				gatheringPO.setString("Transactor", transactor);
				gatheringPO.setDouble("Losses_Amount", receiveAmount);
				// if(isNoPay != null &&
				// isNoPay.equals(DictDataConstant.DICT_IS_YES)){
				//
				// }
				gatheringPO.setString("Writeoff_By", writeoffBy);
				gatheringPO.setTimestamp("writeoff_Date", writeoffDate);
				gatheringPO.setInteger("Writeoff_Tag", writeoffTag);
				// if(Utility.testString(useStore)){
				// gatheringPO.setStoreAmount(Utility.getDouble(useStore));
				// }
				// if(Utility.testString(useCredit)){
				// gatheringPO.setThisUseCredit(Utility.getDouble(useCredit));
				// }
				// if(Utility.testString(creditAmount)){
				// gatheringPO.setThisCreditAmount(Utility.getDouble(creditAmount));
				// }
				gatheringPO.saveIt();
			}

			// actionContext.setStringValue("RECEIVE_NO", NO.toString());
			if (customerNo != null && customerNo.length() > 0
					&& (sFLAG != null && !"".equals(sFLAG) && sFLAG.equals("1") ||
					// PENALTY_AMOUNT!=null&&!"".equals(PENALTY_AMOUNT)&&Utility.getDouble(PENALTY_AMOUNT)>0
					// receiveAmount!=null && receiveAmount >0
					// writeoffTag!=null &&
					// writeoffTag.toString().equals(DictDataConstant.DICT_IS_NO)&&
							sFLAG != null && !"".equals(sFLAG) && sFLAG.equals("2")
							|| sFLAG != null && !"".equals(sFLAG) && sFLAG.equals("3"))) {
				PotentialCusPO customerPO = PotentialCusPO
						.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(), customerNo);

				List<Object> cus1 = new ArrayList<Object>();
				cus1.add(FrameworkUtil.getLoginInfo().getDealerCode());
				cus1.add(DictCodeConstants.D_KEY);
				cus1.add(customerNo);
				List<PotentialCusPO> list = PotentialCusPO.findBySQL(
						"select * from tm_potential_customer where DEALER_CODE= ? AND D_KEY=? and customer_no=?  ",
						cus1.toArray());
				if (list.size() > 0) {
					PotentialCusPO customerPOselect = new PotentialCusPO();
					customerPOselect = (PotentialCusPO) list.get(0);

					if (receiveAmount < 0) { // 判断收款金额在为负数的情况下
						// 作结算收款时和订单总支出+代办总支出比较
						if (isBalance != null && isBalance.equals(DictCodeConstants.DICT_IS_YES)) {
							if (customerPOselect.get("Order_Payed_Sum") != null
									&& customerPOselect.get("Con_Payed_Sum") != null
									&& (customerPOselect.getDouble("Order_Payed_Sum")
											+ customerPOselect.getDouble("Con_Payed_Sum") + receiveAmount) < 0) {
								throw new IllegalArgumentException();

							}
						} else // 作预收款时和账户可用余额比较
						if (customerPOselect.getDouble("UsableAmount") != null
								&& (customerPOselect.getDouble("UsableAmount") + receiveAmount) < 0) {
							throw new IllegalArgumentException();

						}
						if (customerPOselect.getDouble("Gathered_Sum") == null) {
							throw new IllegalArgumentException();

						}
					}
					System.out.println("123");
					NumberFormat nt = NumberFormat.getPercentInstance();
					// 设置百分数精确度2即保留两位小数
					nt.setMinimumFractionDigits(2);
					Double gatheredSum = 0D;
					Double usableAmount = 0D;
					Double unWriteoffSum = 0D;
					Double orderPayedSum = 0D;
					if (customerPOselect.getDouble("Gathered_Sum") != null) {
						gatheredSum = customerPOselect.getDouble("Gathered_Sum") + receiveAmount;
					} else {
						gatheredSum = receiveAmount;
					}
					if (gatheredSum != null) {
						// customerPO.setString("Gathered_Sum",
						// nt.format(gatheredSum));
						customerPO.setString("Gathered_Sum", gatheredSum);
					}

					System.out.println("1234");

					// 未销账的,更新未销账金额
					if ((DictCodeConstants.DICT_IS_NO).equals(writeoffTag.toString())) {
						if (customerPOselect.getDouble("ORDER_PAYED_SUM") != null) {
							orderPayedSum = customerPOselect.getDouble("ORDER_PAYED_SUM");
						}
						customerPO.setDouble("ORDER_PAYED_SUM", orderPayedSum);
						if (customerPOselect.getDouble("Un_Writeoff_Sum") != null) {
							unWriteoffSum = customerPOselect.getDouble("Un_Writeoff_Sum") + receiveAmount;
						} else {
							unWriteoffSum = receiveAmount;
						}

						customerPO.setDouble("Un_Writeoff_Sum", unWriteoffSum);
						System.out.println("125");
					}
					// 销账的更新可用余额
					else {
						if (customerPOselect.getDouble("ORDER_PAYED_SUM") != null) {
							orderPayedSum = customerPOselect.getDouble("ORDER_PAYED_SUM") + receiveAmount;
						} else {
							orderPayedSum = receiveAmount;
						}
						customerPO.setDouble("ORDER_PAYED_SUM", orderPayedSum);
						/*
						 * if (customerPOselect.getDouble("Usable_Amount") !=
						 * null) { usableAmount = customerPOselect
						 * .getDouble("Usable_Amount") + receiveAmount; } else {
						 * usableAmount = receiveAmount; }
						 * customerPO.setDouble("Usable_Amount",usableAmount);
						 */
						System.out.println("1236");
					}
					customerPO.saveIt();
				}
			}
			if (!StringUtils.isNullOrEmpty(writeoffTag) && writeoffTag == 12781001) {

			} else {
				// addBlance(dto);
			}

			sql.append("select * from tm_potential_customer where DEALER_CODE= '"
					+ FrameworkUtil.getLoginInfo().getDealerCode() + "' AND D_KEY=0 and customer_no='" + customerNo
					+ "' ");
			id = Base.findAll(sql.toString());
		} else {
			String customerNo = dto.getCustomerNo();
			TtCustomerGatheringPO gather = TtCustomerGatheringPO.findByCompositeKeys(dto.getReceiveNo(),
					loginInfo.getDealerCode());
			System.out.println(dto.getReceiveNo());
			System.out.println(gather);
			System.out.println(dto.getWriteoffTag());
			if (!StringUtils.isNullOrEmpty(gather.getString("WRITEOFF_TAG"))
					&& !StringUtils.isNullOrEmpty(dto.getWriteoffTag().toString())
					&& gather.getString("WRITEOFF_TAG").equals(dto.getWriteoffTag().toString())) {
				gather.setString("REMARK", dto.getRemark());
				gather.saveIt();
			} else {
				if (gather.getString("WRITEOFF_TAG").equals(DictCodeConstants.DICT_IS_NO)
						&& dto.getWriteoffTag().toString().equals(DictCodeConstants.DICT_IS_YES)) {
					gather.setString("Writeoff_By", dto.getWriteoffBy());
					gather.setTimestamp("writeoff_Date", dto.getWriteoffDate());
					gather.setInteger("Writeoff_Tag", dto.getWriteoffTag());
					gather.setString("REMARK", dto.getRemark());
					gather.saveIt();
					if (!StringUtils.isNullOrEmpty(customerNo)) {
						List<Object> cus1 = new ArrayList<Object>();
						cus1.add(FrameworkUtil.getLoginInfo().getDealerCode());
						cus1.add(DictCodeConstants.D_KEY);
						cus1.add(customerNo);
						List<PotentialCusPO> list = PotentialCusPO.findBySQL(
								"select * from tm_potential_customer where DEALER_CODE= ? AND D_KEY=? and customer_no=?  ",
								cus1.toArray());
						PotentialCusPO customerPO = PotentialCusPO
								.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(), customerNo);

						if (list.size() > 0) {
							PotentialCusPO customerPOselect = new PotentialCusPO();
							customerPOselect = (PotentialCusPO) list.get(0);
							System.out.println("123");
							NumberFormat nt = NumberFormat.getPercentInstance();
							// 设置百分数精确度2即保留两位小数
							nt.setMinimumFractionDigits(2);
							Double gatheredSum = 0D;
							Double usableAmount = 0D;
							Double unWriteoffSum = 0D;
							Double orderPayedSum = 0D;

							System.out.println("1234");
							if (dto.getReceiveAmount() > 0) {
								if (dto.getArragementSum() >= dto.getReceiveAmount()) {
									orderPayedSum = customerPOselect.getDouble("ORDER_PAYED_SUM")
											+ dto.getReceiveAmount();
									unWriteoffSum = customerPOselect.getDouble("UN_WRITEOFF_SUM")
											- dto.getReceiveAmount();
								} else {
									orderPayedSum = customerPOselect.getDouble("ORDER_PAYED_SUM")
											+ dto.getArragementSum();
									unWriteoffSum = customerPOselect.getDouble("UN_WRITEOFF_SUM")
											- dto.getReceiveAmount();
									if (customerPOselect.getDouble("Usable_Amount") != null) {
										usableAmount = customerPOselect.getDouble("Usable_Amount")
												+ dto.getReceiveAmount() - dto.getArragementSum();
									} else {
										usableAmount = dto.getReceiveAmount() - dto.getArragementSum();
										;
									}
									customerPO.setDouble("Usable_Amount", usableAmount);
								}
							}
							customerPO.setDouble("Un_Writeoff_Sum", unWriteoffSum);
							customerPO.setDouble("ORDER_PAYED_SUM", orderPayedSum);
							System.out.println("125");

							customerPO.saveIt();
						}
					}

				}
			}
			sql.append("select * from tm_potential_customer where DEALER_CODE= '"
					+ FrameworkUtil.getLoginInfo().getDealerCode() + "' AND D_KEY=0 and customer_no='" + customerNo
					+ "' ");
			id = Base.findAll(sql.toString());
		}

		return id;
	}

	private void addBlance(GathringMaintainDTO dto) {

		System.out.println("_____________________________________________________1");
		String soNo = dto.getSoNo();
		Double orderPayedAmount = dto.getOrderPayedAmount();
		Double conReceivableSum = dto.getConReceivableSum();
		Double conPayedAmount = dto.getConPayedAmount();
		Double conArrearageAmount = dto.getConArrearageAmount();
		Integer payOff = dto.getPayOff();
		Double commissionbalance = dto.getCommissionbalance();
		Integer lossesPayOff = dto.getLossesPayOff();
		Double orderArrearageAmount = dto.getOrderArrearageAmount();
		String customerNo = dto.getCustomerNo();
		Double orderPayedSum = dto.getOrderPayedSum();
		Double conPayedSum = dto.getConPayedSum();
		Double usableAmount = dto.getUsableAmount();
		Double gatherdSum = dto.getGatherdSum();
		Double unWriteoffSum = dto.getUnWriteoffSum();
		Integer ver = dto.getVer();
		String empNo = FrameworkUtil.getLoginInfo().getEmployeeNo();

		long userId = FrameworkUtil.getLoginInfo().getUserId();
		// String groupCode=Utility.getGroupEntity(conn, entityCode,
		// "TM_MEMBER");
		List<Object> cus = new ArrayList<Object>();
		cus.add(FrameworkUtil.getLoginInfo().getDealerCode());
		cus.add(DictCodeConstants.D_KEY);
		cus.add(soNo);
		List<SalesOrderPO> orderMember = SalesOrderPO.findBySQL(
				"select * from Tt_Sales_Order where DEALER_CODE= ? AND D_KEY=? and so_No=?  ", cus.toArray());
		if (orderMember.size() > 0) {
			String payofforder = orderMember.get(0).getString("PAY_OFF");
		}

		SalesOrderPO orderPO = SalesOrderPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(), soNo);
		SalesOrderPO orderSalesPO = SalesOrderPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),
				soNo);

		SalesOrderPO orderTypePO = new SalesOrderPO();
		// TmPotentialCustomerPO customerPO = new TmPotentialCustomerPO();
		// TmPotentialCustomerPO customerPOCon = new TmPotentialCustomerPO();
		List<Object> cus1 = new ArrayList<Object>();
		cus1.add(FrameworkUtil.getLoginInfo().getDealerCode());
		cus1.add(DictCodeConstants.D_KEY);
		cus1.add(soNo);
		List<SalesOrderPO> rsList = SalesOrderPO.findBySQL(
				"select * from Tt_Sales_Order where DEALER_CODE= ? AND D_KEY=? and so_No=?  ", cus1.toArray());
		// 订单类型的判断-----------------开始
		if (rsList != null && rsList.size() > 0) {
			orderTypePO = (SalesOrderPO) rsList.get(0);
			// 对一般订单进行检验
			if (orderTypePO.get("BUSINESS_TYPE") != null && orderTypePO.getInteger("BUSINESS_TYPE") != 0 && orderTypePO
					.getInteger("BUSINESS_TYPE") == Integer.parseInt(DictCodeConstants.DICT_SO_TYPE_GENERAL)) {

				// 建议是否有退回单，如有该订单就不能编辑
				List list = queryOrderISReturn(FrameworkUtil.getLoginInfo().getDealerCode(), soNo);
				if (list == null || list.size() == 0) {
					// actionContext.setErrorContext(
					// "该销售订单有退回单，不能进行编辑",
					// "该销售订单有退回单，不能进行编辑", null);
					throw new ServiceBizException("该销售订单有退回单，不能进行编辑");
				}
			}
		}
		// 订单类型的判断-----------------结束

		System.out.println(orderPayedAmount);
		System.out.println("_____________________________________________________");
		orderPO.setDouble("Order_Payed_Amount", orderPayedAmount);
		orderPO.setDouble("Con_Receivable_Sum", conReceivableSum);
		orderPO.setDouble("Commission_Balance", commissionbalance);
		orderPO.setDouble("Con_Payed_Amount", conPayedAmount);
		orderPO.setDouble("Con_Arrearage_Amount", conArrearageAmount);

		// Update by AndyTain 2013-2-17 begin 记录结清时间，如果已经挂账结清现在状态变为结清，则时间不改变。
		// 获取当前sono对应的结清日期，看是否为null。
		boolean hasBalanceTime = false;
		// List TimeList = new ArrayList();
		SalesOrderPO TimeOrderPO = new SalesOrderPO();
		List<Object> cus2 = new ArrayList<Object>();
		cus2.add(FrameworkUtil.getLoginInfo().getDealerCode());
		cus2.add(DictCodeConstants.D_KEY);
		cus2.add(soNo);
		List<SalesOrderPO> TimeList = SalesOrderPO.findBySQL(
				"select * from Tt_Sales_Order where DEALER_CODE= ? AND D_KEY=? and so_No=?  ", cus2.toArray());
		if (TimeList.size() > 0) {
			TimeOrderPO = (SalesOrderPO) TimeList.get(0);
			hasBalanceTime = (TimeOrderPO.get("BALANCE_TIME") != null
					&& TimeOrderPO.get("BALANCE_TIME").toString() != "");
		}
		if ((!StringUtils.isNullOrEmpty(payOff) && payOff == 12781002)
				&& (!StringUtils.isNullOrEmpty(lossesPayOff) && lossesPayOff == 12781001) && !hasBalanceTime) {
			// 如果挂账结清为是，结清为否，而且本来的balanceTime是空，那么直接填上结清时间
			orderPO.setDate("Balance_Time", new Date());
		} else if ((!StringUtils.isNullOrEmpty(payOff) && payOff == 12781002)
				&& (!StringUtils.isNullOrEmpty(lossesPayOff) && lossesPayOff == 12781002) && hasBalanceTime) {
			// 如果挂账结清为否,结清为否，而且本来的balanceTime不为空,那么直接清空结清时间
			orderSalesPO.setDate("BALANCE_TIME", null);
			orderSalesPO.setInteger("PAY_OFF", 12781002);
			orderSalesPO.setInteger("LOSSES_PAY_OFF", 12781002);
			orderSalesPO.saveIt();
		} else if ((!StringUtils.isNullOrEmpty(payOff) && payOff == 12781001)
				&& (!StringUtils.isNullOrEmpty(lossesPayOff) && lossesPayOff == 12781002) && !hasBalanceTime) {
			// 如果挂账结清为否,结清为是，而且本来的balanceTime是空，那么填上结清时间否则不改变。
			orderPO.setDate("Balance_Time", new Date());
		}
		// Update by AndyTain 2013-2-17 end

		orderPO.setInteger("Pay_Off", payOff);
		orderPO.setInteger("Losses_Pay_Off", lossesPayOff);
		orderPO.setDouble("Order_Arrearage_Amount", orderArrearageAmount);
		orderPO.saveIt();
		if (customerNo != null) {
			List<Object> customer = new ArrayList<Object>();
			customer.add(FrameworkUtil.getLoginInfo().getDealerCode());
			customer.add(DictCodeConstants.D_KEY);
			customer.add(customerNo);
			List<PotentialCusPO> customerlist = PotentialCusPO.findBySQL(
					"select * from tm_potential_customer where DEALER_CODE= ? AND D_KEY=? and customer_no=?  ",
					customer.toArray());
			// 2013-3-27 在更新账户之前 先获取账户的最新数据 TmPotentialCustomerPO
			if (customerlist != null && customerlist.size() > 0) {
				PotentialCusPO cusoPO = customerlist.get(0);
			}
		}

	}

	@Override
	public String addCustomerGatheringWriteoffTag(GathringMaintainDTO dto) throws ServiceBizException {
		System.out.println("_____________________________________________________1");
		String soNo = dto.getSoNo();
		System.out.println("no" + soNo);
		if (!StringUtils.isNullOrEmpty(dto.getReceiveNo())) {
			TtCustomerGatheringPO gather = TtCustomerGatheringPO.findByCompositeKeys(dto.getReceiveNo(),
					FrameworkUtil.getLoginInfo().getDealerCode());
			soNo = gather.getString("SO_NO");
		}
		Double orderPayedAmount = dto.getOrderPayedAmount();
		Double conReceivableSum = dto.getConReceivableSum();
		Double conPayedAmount = dto.getConPayedAmount();
		Double conArrearageAmount = dto.getConArrearageAmount();
		Integer payOff = dto.getPayOff();
		Double commissionbalance = dto.getCommissionbalance();
		Integer lossesPayOff = dto.getLossesPayOff();
		Double orderArrearageAmount = dto.getOrderArrearageAmount();
		String customerNo = dto.getCustomerNo();
		Double orderPayedSum = dto.getOrderPayedSum();
		Double conPayedSum = dto.getConPayedSum();
		Double usableAmount = dto.getUsableAmount();
		Double gatherdSum = dto.getGatherdSum();
		Double unWriteoffSum = dto.getUnWriteoffSum();
		Integer ver = dto.getVer();
		String empNo = FrameworkUtil.getLoginInfo().getEmployeeNo();

		long userId = FrameworkUtil.getLoginInfo().getUserId();
		// String groupCode=Utility.getGroupEntity(conn, entityCode,
		// "TM_MEMBER");
		List<Object> cus = new ArrayList<Object>();
		cus.add(FrameworkUtil.getLoginInfo().getDealerCode());
		cus.add(DictCodeConstants.D_KEY);
		cus.add(soNo);
		List<SalesOrderPO> orderMember = SalesOrderPO.findBySQL(
				"select * from Tt_Sales_Order where DEALER_CODE= ? AND D_KEY=? and so_No=?  ", cus.toArray());
		if (orderMember.size() > 0) {
			String payofforder = orderMember.get(0).getString("PAY_OFF");
		}

		SalesOrderPO orderPO = SalesOrderPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(), soNo);
		SalesOrderPO orderSalesPO = SalesOrderPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),
				soNo);

		SalesOrderPO orderTypePO = new SalesOrderPO();
		// TmPotentialCustomerPO customerPO = new TmPotentialCustomerPO();
		// TmPotentialCustomerPO customerPOCon = new TmPotentialCustomerPO();
		List<Object> cus1 = new ArrayList<Object>();
		cus1.add(FrameworkUtil.getLoginInfo().getDealerCode());
		cus1.add(DictCodeConstants.D_KEY);
		cus1.add(soNo);
		List<SalesOrderPO> rsList = SalesOrderPO.findBySQL(
				"select * from Tt_Sales_Order where DEALER_CODE= ? AND D_KEY=? and so_No=?  ", cus1.toArray());
		// 订单类型的判断-----------------开始
		if (rsList != null && rsList.size() > 0) {
			orderTypePO = (SalesOrderPO) rsList.get(0);
			// 对一般订单进行检验
			if (orderTypePO.get("BUSINESS_TYPE") != null && orderTypePO.getInteger("BUSINESS_TYPE") != 0 && orderTypePO
					.getInteger("BUSINESS_TYPE") == Integer.parseInt(DictCodeConstants.DICT_SO_TYPE_GENERAL)) {

				// 建议是否有退回单，如有该订单就不能编辑
				List list = queryOrderISReturn(FrameworkUtil.getLoginInfo().getDealerCode(), soNo);
				if (list == null || list.size() == 0) {
					// actionContext.setErrorContext(
					// "该销售订单有退回单，不能进行编辑",
					// "该销售订单有退回单，不能进行编辑", null);
					throw new ServiceBizException("该销售订单有退回单，不能进行编辑");
				}
			}
		}
		// 订单类型的判断-----------------结束

		System.out.println(orderPayedAmount);
		System.out.println("_____________________________________________________");
		orderPO.setDouble("ORDER_PAYED_AMOUNT", orderPayedAmount);
		orderPO.setDouble("CON_RECEIVABLE_SUM", conReceivableSum);
		orderPO.setDouble("COMMISSION_BALANCE", commissionbalance);
		orderPO.setDouble("CON_PAYED_AMOUNT", conPayedAmount);
		orderPO.setDouble("CON_ARREARAGE_AMOUNT", conArrearageAmount);

		// Update by AndyTain 2013-2-17 begin 记录结清时间，如果已经挂账结清现在状态变为结清，则时间不改变。
		// 获取当前sono对应的结清日期，看是否为null。
		boolean hasBalanceTime = false;
		// List TimeList = new ArrayList();
		SalesOrderPO TimeOrderPO = new SalesOrderPO();
		List<Object> cus2 = new ArrayList<Object>();
		cus2.add(FrameworkUtil.getLoginInfo().getDealerCode());
		cus2.add(DictCodeConstants.D_KEY);
		cus2.add(soNo);
		List<SalesOrderPO> TimeList = SalesOrderPO.findBySQL(
				"select * from Tt_Sales_Order where DEALER_CODE= ? AND D_KEY=? and so_No=?  ", cus2.toArray());
		if (TimeList.size() > 0) {
			TimeOrderPO = (SalesOrderPO) TimeList.get(0);
			hasBalanceTime = (TimeOrderPO.get("BALANCE_TIME") != null
					&& TimeOrderPO.get("BALANCE_TIME").toString() != "");
		}
		if ((!StringUtils.isNullOrEmpty(payOff) && payOff == 12781002)
				&& (!StringUtils.isNullOrEmpty(lossesPayOff) && lossesPayOff == 12781001) && !hasBalanceTime) {
			// 如果挂账结清为是，结清为否，而且本来的balanceTime是空，那么直接填上结清时间
			orderPO.setDate("Balance_Time", new Date());
		} else if ((!StringUtils.isNullOrEmpty(payOff) && payOff == 12781002)
				&& (!StringUtils.isNullOrEmpty(lossesPayOff) && lossesPayOff == 12781002) && hasBalanceTime) {
			// 如果挂账结清为否,结清为否，而且本来的balanceTime不为空,那么直接清空结清时间
			orderSalesPO.setDate("BALANCE_TIME", null);
			orderSalesPO.setInteger("PAY_OFF", 12781002);
			orderSalesPO.setInteger("LOSSES_PAY_OFF", 12781002);
			orderSalesPO.saveIt();
		} else if ((!StringUtils.isNullOrEmpty(payOff) && payOff == 12781001)
				&& (!StringUtils.isNullOrEmpty(lossesPayOff) && lossesPayOff == 12781002) && !hasBalanceTime) {
			// 如果挂账结清为否,结清为是，而且本来的balanceTime是空，那么填上结清时间否则不改变。
			orderPO.setDate("Balance_Time", new Date());
		}
		// Update by AndyTain 2013-2-17 end

		orderPO.setInteger("Pay_Off", payOff);
		orderPO.setInteger("Losses_Pay_Off", lossesPayOff);
		orderPO.setDouble("Order_Arrearage_Amount", orderArrearageAmount);
		orderPO.saveIt();
		PotentialCusPO customerPO = PotentialCusPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),dto.getCustomerNo());
		customerPO.setString("USABLE_AMOUNT", dto.getCalcUsableAmount());
		customerPO.setString("ORDER_PAYED_SUM", dto.getOrderPayedSum());
		customerPO.saveIt();
		System.err.println("客户编号:   "+dto.getCustomerNo());
		
		// if (customerNo != null) {
		// // update Tm_customer
		// customerPOCon.setEntityCode(entityCode);
		// customerPOCon.setDKey(CommonConstant.D_KEY);
		// customerPOCon.setCustomerNo(customerNo);
		//
		//// 2013-3-27 在更新账户之前 先获取账户的最新数据 TmPotentialCustomerPO
		// List pclist =POFactory.select(conn, customerPOCon);
		// if (pclist!=null && pclist.size()>0){
		// TmPotentialCustomerPO pc = (TmPotentialCustomerPO)pclist.get(0);
		// if (pc.getGatheredSum()!=null){
		// gatherdSum=pc.getGatheredSum().toString();
		// }
		// if (gatherdSum == null || gatherdSum.equals(""))
		// gatherdSum = "0.00";
		// customerPO.setGatheredSum(Utility.round(gatherdSum,2));
		//
		// if (orderPayedSum == null || orderPayedSum.equals(""))
		// orderPayedSum = "0.00";
		// customerPO.setOrderPayedSum(Utility.round(orderPayedSum,2));
		// if (conPayedSum == null || conPayedSum.equals(""))
		// conPayedSum = "0.00";
		// customerPO.setConPayedSum(Utility.round(conPayedSum,2));
		// if (usableAmount == null || usableAmount.equals(""))
		// usableAmount = "0.00";
		// if (unWriteoffSum == null || unWriteoffSum.equals(""))
		// unWriteoffSum = "0.00";
		// customerPO.setUsableAmount(Utility.round(gatherdSum,2)-Utility.round(orderPayedSum,2)
		// -Utility.round(conPayedSum,2)-Utility.round(unWriteoffSum,2));
		// }
		/// * if(Utility.testString(unWriteoffSum))
		// {
		// TmPotentialCustomerPO customerPO2=new TmPotentialCustomerPO();
		// customerPO2.setEntityCode(entityCode);
		// customerPO2.setDKey(CommonConstant.D_KEY);
		// customerPO2.setCustomerNo(customerNo);
		// List list=POFactory.select(conn, customerPO2);
		// if(list.size()>0)
		// {
		// customerPO2=(TmPotentialCustomerPO)list.get(0);
		// customerPO.setUnWriteoffSum(
		// customerPO2.getUnWriteoffSum()-Utility.getDouble(unWriteoffSum));
		// }
		// }*/
		//
		// customerPO.setUpdateBy(userId);
		// customerPO.setUpdateDate(Utility.getCurrentDateTime());
		// POFactory.update(conn, customerPOCon, customerPO);
		// }
		//
		// //add by hlc 2013-03-01
		// if(Utility.testString(orderTypePO.getVin())){
		// TtMemberCardInfoPOFactory MemberCardInfoPOFactory = new
		// TtMemberCardInfoPOFactory();
		// List sList = MemberCardInfoPOFactory.QueryMemberCardInfoByVin(conn,
		// entityCode, orderTypePO.getVin());
		//
		// //销售产生积分
		// if ((sList != null && sList.size() > 0)){
		// DynaBean dynaBean = (DynaBean) sList.get(0);
		// Long id = dynaBean.getLong("CARD_ID");
		// String accountId = String.valueOf(dynaBean.getLong("ACCOUNT_ID"));
		// String isGroupMem = BusinessUtility.getDefalutPara(conn, entityCode,
		// CommonConstant.DEFAULT_IS_GROUP_MEMBER);
		// String transTid = "";
		// // 条件满足则开始产生维修积分
		// // 根据会员账号ID产生事务编号
		// if (Utility.testString(isGroupMem) &&
		// isGroupMem.equals(DictDataConstant.DICT_IS_YES)){
		// TmMemberTransRecordPOFactory trf = new
		// TmMemberTransRecordPOFactory();
		// SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
		// String date = sf.format(Utility.getCurrentDateTime());
		// /**
		// * 1 代表使用积分或者储值产生的事物 0
		// * 代表赠送积分产生的事务
		// */
		// transTid = "T" + "0" + entityCode.substring(2) + accountId + date;
		// trf.memberTransCreated(conn, groupCode, transTid, accountId, userId);
		// logger.debug(" 维修产生DMS唯一事务编号： " + transTid);
		// actionContext.setStringValue("TRANS_TID", transTid);
		// }
		// if (orderPO.getPayOff()!=null &&
		// orderPO.getPayOff().toString().equals(DictDataConstant.DICT_IS_YES)){
		// givepoints(conn,entityCode, soNo, id,userId, empNo, transTid);
		// }
		//
		// if (payofforder.equals(DictDataConstant.DICT_IS_YES) &&
		// orderPO.getPayOff().toString().equals(DictDataConstant.DICT_IS_NO)){
		// returnpoints( conn , entityCode, soNo, id, userId, empNo,
		// transTid);//反结算退积分
		//
		// }
		// }
		// }
		// //老客户转介绍赠送积分
		// if(orderTypePO.getRecommendCardId()!=null){
		// TtCreditDetailPOFactory cpf=new TtCreditDetailPOFactory();
		// List slist=cpf.queryCreditDetail(conn, entityCode,
		// soNo,orderTypePO.getRecommendCardId(),DictDataConstant.DICT_MEMBER_CREDIT_BUSINESS_TYPE_OLD_MEMBER);
		// if (slist==null || slist.size() == 0){
		// TmMemberCardPO card2 = new TmMemberCardPO();
		// card2.setEntityCode(groupCode);
		// card2.setCardId(orderTypePO.getRecommendCardId());
		// card2=POFactory.getByPriKey(conn, card2);
		// if (card2!=null && card2.getCardStatus()!=null &&
		// card2.getCardStatus()==50051002){
		// TmMemberAccountPO card = new TmMemberAccountPO();
		// card.setEntityCode(groupCode);
		// card.setCardId(orderTypePO.getRecommendCardId());
		// card=POFactory.getByPriKey(conn, card);
		// Long id = orderTypePO.getRecommendCardId();
		// String accountId = card.getItemId().toString();
		// String isGroupMem = BusinessUtility.getDefalutPara(conn, entityCode,
		// CommonConstant.DEFAULT_IS_GROUP_MEMBER);
		// String transTid = "";
		// // 条件满足则开始产生维修积分
		// // 根据会员账号ID产生事务编号
		// if (Utility.testString(isGroupMem) &&
		// isGroupMem.equals(DictDataConstant.DICT_IS_YES)){
		// TmMemberTransRecordPOFactory trf = new
		// TmMemberTransRecordPOFactory();
		// SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
		// String date = sf.format(Utility.getCurrentDateTime());
		// /**
		// * 1 代表使用积分或者储值产生的事物 0
		// * 代表赠送积分产生的事务
		// */
		// transTid = "T" + "0" + entityCode.substring(2) + accountId + date;
		// trf.memberTransCreated(conn, groupCode, transTid, accountId, userId);
		// logger.debug(" 维修产生DMS唯一事务编号： " + transTid);
		// actionContext.setStringValue("T_ID", transTid);
		// }
		// if (orderPO.getPayOff()!=null &&
		// orderPO.getPayOff().toString().equals(DictDataConstant.DICT_IS_YES)){
		// giveoldpoints(conn,entityCode, soNo, id,userId, empNo, transTid);
		// }
		// }
		//
		// }
		// }

		return null;
	}

	/**
	 * 更新客户已收款金额 订单支出 可用余额 未销账金额
	 * 
	 * @author xukl
	 * @date 2016年11月1日
	 * @param customerGatheringDTO
	 */
	private void updateCustomerMoney(SalesOrderPO salesOrderpo, CustomerGatheringDTO customerGatheringDTO)
			throws ServiceBizException {
		String customerNo = salesOrderpo.getString("CUSTOMER_NO");

		// 查询该客户所有销售订单收款记录按是否已销账分组
		List<Map> list = qrySalesOrders(customerNo);
		Double sumAmount = 0.0;// 已收款总额 已收款金额=Sum（收款金额）
		Double payAmount = 0.0;// 订单支出总额 订单支出=Sum（收款金额）（销售单号不为空且已勾选销账标识的收款记录）
		Double writeoffnotAmount = 0.0;// 未勾选销账标识的收款记录）
		for (int i = 0; i < list.size(); i++) {
			Map map = list.get(i);
			Double receiveAmount = Double.valueOf(map.get("RECEIVE_AMOUNT").toString());// 应收总额
			Integer triteoffTag = Integer.valueOf(map.get("WRITEOFF_TAG").toString());// 销账标识
			sumAmount += receiveAmount;
			if (triteoffTag == DictCodeConstants.STATUS_IS_YES) {
				payAmount = receiveAmount;
			}
			if (triteoffTag == DictCodeConstants.STATUS_IS_NOT) {
				writeoffnotAmount = receiveAmount;
			}
		}

		// 可用余额
		BigDecimal balanceAmount = NumberUtil.sub(new BigDecimal(sumAmount.toString()),
				new BigDecimal(payAmount.toString()));
		// 更新销售订单已收金额
		Double orderPayedAmount = salesOrderpo.getDouble("ORDER_PAYED_AMOUNT") != null
				? salesOrderpo.getDouble("ORDER_PAYED_AMOUNT") : 0;
		// salesOrderpo.setDouble("ORDER_PAYED_AMOUNT", sumAmount);
		salesOrderpo.saveIt();
		PotentialCusPO.update("GATHERED_SUM = ? ,ORDER_PAYED_SUM = ? ,USABLE_AMOUNT = ? ,UN_WRITEOFF_SUM = ?",
				"POTENTIAL_CUSTOMER_NO = ?", sumAmount, payAmount, balanceAmount, writeoffnotAmount, customerNo);
	}

	public List<Map> qryReturn(String soNo, String dealerCode) throws ServiceBizException {

		List<Object> cus = new ArrayList<Object>();
		cus.add(soNo);
		cus.add(FrameworkUtil.getLoginInfo().getDealerCode());
		List<SalesOrderPO> rsList = SalesOrderPO
				.findBySQL("select * from tt_sales_order where so_no=? and DEALER_CODE= ?  ", cus.toArray());
		if (rsList != null && rsList.size() > 0) {
			if (!StringUtils.isNullOrEmpty(rsList.get(0).getString("business_type"))
					&& rsList.get(0).getString("business_type").equals(DictCodeConstants.DICT_SO_TYPE_GENERAL)) {
				List list = queryOrderISReturn(dealerCode, soNo);
				if (list == null || list.size() == 0) {
					throw new ServiceBizException("该销售订单有退回单，不能进行编辑");
				}
			}
		}
		return null;
	}

	/**
	 * 查询某一客户所有销售订单已收金额
	 * 
	 * @author xukl
	 * @date 2016年11月1日
	 * @param customerNo
	 * @return
	 */
	private List<Map> qrySalesOrders(String customerNo) {
		StringBuffer str = new StringBuffer(
				"SELECT tcg.DEALER_CODE,SUM(tcg.RECEIVE_AMOUNT) as RECEIVE_AMOUNT,tcg.WRITEOFF_TAG FROM tt_customer_gathering tcg left join tt_sales_order tso on tso.SO_NO_ID = tcg.SO_NO_ID and tcg.DEALER_CODE = tso.DEALER_CODE where tso.CUSTOMER_NO = ? GROUP BY tcg.WRITEOFF_TAG");
		List<Object> params = new ArrayList<Object>();
		params.add(customerNo);
		List<Map> list = DAOUtil.findAll(str.toString(), params);
		return list;
	}

	/**
	 * 设置CustomerGatheringPO属性
	 * 
	 * @author xukl
	 * @date 2016年10月14日
	 * @param customerGatheringDTO
	 * @param cstGatherPO
	 */
	private void setCustomerGatheringPO(CustomerGatheringDTO customerGatheringDTO, TtCustomerGatheringPO cstGatherPO) {
		cstGatherPO.setInteger("SO_NO_ID", customerGatheringDTO.getSoNoId());// 销售单id
		cstGatherPO.setInteger("PAY_TYPE_CODE", customerGatheringDTO.getPayTypeCode());// 付款方式
		cstGatherPO.setDouble("RECEIVE_AMOUNT", customerGatheringDTO.getReceiveAmount());// 收款金额
		cstGatherPO.setDate("RECEIVE_DATE", customerGatheringDTO.getReceiveDate());// 收款日期
		cstGatherPO.setInteger("GATHERING_TYPE", customerGatheringDTO.getGatheringType());// 收款类型
		cstGatherPO.setString("BILL_NO", customerGatheringDTO.getBillNo());// 票据单号
		cstGatherPO.setString("CUSTOMER_NO", customerGatheringDTO.getCustomerNo());// 客户编号
		cstGatherPO.setInteger("WRITEOFF_TAG", customerGatheringDTO.getWriteoffTag());// 销账标志
		cstGatherPO.setString("WRITEOFF_BY", customerGatheringDTO.getWriteoffBy());// 销账人
		cstGatherPO.setString("RECORDER", customerGatheringDTO.getRecorder());// 登记人呢
		cstGatherPO.setDate("WRITEOFF_DATE", customerGatheringDTO.getWriteoffDate());// 销账日期
		cstGatherPO.setString("REMARK", customerGatheringDTO.getRemark());// 备注
	}

	/**
	 * 修改收款记录
	 * 
	 * @author xukl
	 * @date 2016年10月16日
	 * @param id
	 * @param customerGatheringDTO
	 * @throws ServiceBizException
	 *             (non-Javadoc)
	 * @see com.yonyou.dms.retail.service.ordermanage.SettleCollectionService#editCustomerGathering(java.lang.Long,
	 *      com.yonyou.dms.retail.domains.DTO.ordermanage.CustomerGatheringDTO)
	 */

	@Override
	public void editCustomerGathering(Long id, CustomerGatheringDTO customerGatheringDTO) throws ServiceBizException {
		TtCustomerGatheringPO cstGatherPO = TtCustomerGatheringPO.findById(id);
		SalesOrderPO salesOrderpo = SalesOrderPO.findById(customerGatheringDTO.getSoNoId());
		setCustomerGatheringPO(customerGatheringDTO, cstGatherPO);
		cstGatherPO.saveIt();

		// 重新计算潜客表收款金额
		updateCustomerMoney(salesOrderpo, customerGatheringDTO);
	}

	/**
	 * 查询收款记录根据销售单
	 * 
	 * @author wantao
	 * @date 2017年5月8日
	 * @param queryParam
	 * @return
	 */
	@Override
	public PageInfoDto queryAllVehiclePayManage(Map<String, String> queryParam) throws ServiceBizException {
		// TODO Auto-generated method stub
		return null;
	}

}
