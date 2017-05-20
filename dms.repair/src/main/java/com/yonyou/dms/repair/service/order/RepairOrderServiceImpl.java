
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.repair
 *
 * @File name : RepairOrderServiceImpl.java
 *
 * @Author : ZhengHe
 *
 * @Date : 2016年8月10日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年8月10日    ZhengHe    1.0
 *
 *
 *
 *
----------------------------------------------------------------------------------
 */

package com.yonyou.dms.repair.service.order;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.RepairOrderPO;
import com.yonyou.dms.common.domains.PO.basedata.TmPartInfoPO;
import com.yonyou.dms.common.domains.PO.basedata.TtShortPartPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * 工单接口实现
 * 
 * @author ZhengHe
 * @date 2016年8月10日
 */
@Service
@SuppressWarnings({ "unchecked", "rawtypes", "static-access" })
public class RepairOrderServiceImpl implements RepairOrderService {
	/**
	 * 根据条件查询
	 * 
	 * @author zhengcong
	 * @date 2017年4月24日
	 */
	@Override
	public PageInfoDto newQueryPart(Map<String, String> qParam) throws ServiceBizException {
		double rate = 1 + Utility.getDouble(Utility.getDefaultValue("2034"));
		List<Object> params = new ArrayList<Object>();
		StringBuffer sqlsb = new StringBuffer("SELECT B.OPTION_NO,A.COST_PRICE*" + rate
				+ "  AS NET_COST_PRICE,A.COST_AMOUNT*" + rate + " AS NET_COST_AMOUNT, ");
		sqlsb.append(
				" B.ORI_PRO_CODE,A.IS_SUGGEST_ORDER,A.PART_MODEL_GROUP_CODE_SET,A.PART_MAIN_TYPE,A.PART_SPE_TYPE, ");
		sqlsb.append(
				" A.DEALER_CODE, A.PART_NO, A.FOUND_DATE, A.STORAGE_CODE, A.STORAGE_POSITION_CODE, A.PART_NAME,   ");
		sqlsb.append(
				" A.SPELL_CODE, A.PART_GROUP_CODE, A.UNIT_CODE, A.STOCK_QUANTITY, A.SALES_PRICE,  A.CLAIM_PRICE,  ");
		sqlsb.append(
				" A.LIMIT_PRICE, A.LATEST_PRICE, A.COST_PRICE*1.0000 COST_PRICE, A.COST_AMOUNT*1.0000 COST_AMOUNT,  ");
		sqlsb.append(" A.MAX_STOCK,  A.MIN_STOCK, A.BORROW_QUANTITY, A.LEND_QUANTITY, A.LOCKED_QUANTITY,TS.CJ_TAG,  ");
		sqlsb.append(
				" A.PART_STATUS,  A.LAST_STOCK_IN, A.LAST_STOCK_OUT, A.REMARK, A.VER,  A.JAN_MODULUS,A.FEB_MODULUS, ");
		sqlsb.append(
				" A.MAR_MODULUS,A.APR_MODULUS,A.MAY_MODULUS,A.JUN_MODULUS,A.JUL_MODULUS,A.AUG_MODULUS, A. SEP_MODULUS, ");
		sqlsb.append(
				" A.OCT_MODULUS,A.NOV_MODULUS,A.DEC_MODULUS,A.MONTHLY_QTY_FORMULA,   B.PRODUCTING_AREA, B.BRAND,  ");
		sqlsb.append(
				" B.MIN_PACKAGE,B.DOWN_TAG,B.FROM_ENTITY,    D.OPTION_STOCK,A.INSURANCE_PRICE,G.ALL_STOCK_QUANTITY, ");
		sqlsb.append(
				" B.INSTRUCT_PRICE, (A.STOCK_QUANTITY + A.BORROW_QUANTITY - A.LEND_QUANTITY - A.LOCKED_QUANTITY) AS USEABLE_STOCK, ");
		sqlsb.append(" A.NODE_PRICE ,B.PART_INFIX,F.POS_CODE,E.POS_NAME FROM TM_PART_STOCK A  ");
		sqlsb.append(" LEFT OUTER JOIN (" + CommonConstants.VM_PART_INFO + ") B  ");
		sqlsb.append(" ON ( A.DEALER_CODE = B.DEALER_CODE AND A.PART_NO= B.PART_NO  )  LEFT OUTER JOIN  ");
		sqlsb.append(" (select DEALER_CODE, part_no, sum(C.STOCK_QUANTITY) AS OPTION_STOCK FROM TM_PART_STOCK C  ");
		sqlsb.append(" WHERE DEALER_CODE='" + FrameworkUtil.getLoginInfo().getDealerCode() + "'");
		sqlsb.append(" AND D_KEY=" + DictCodeConstants.D_KEY);
		sqlsb.append(" GROUP BY DEALER_CODE,PART_NO ) D   ");
		sqlsb.append(" ON ( A.DEALER_CODE = D.DEALER_CODE AND A.PART_NO= D.PART_NO AND B.PART_NO = D.PART_NO )  ");
		sqlsb.append(
				" LEFT JOIN(SELECT  H.DEALER_CODE,H.PART_NO,SUM(H.STOCK_QUANTITY + H.BORROW_QUANTITY - H.LEND_QUANTITY - H.LOCKED_QUANTITY) ALL_STOCK_QUANTITY  ");
		sqlsb.append(" FROM  TM_PART_STOCK H LEFT JOIN TM_STORAGE I  ON  H.DEALER_CODE=I.DEALER_CODE   ");
		sqlsb.append("  AND H.STORAGE_CODE=I.STORAGE_CODE  WHERE I.STORAGE_TYPE = 70041001");
		sqlsb.append("  GROUP BY   H.DEALER_CODE,H.PART_NO) G  ");
		sqlsb.append(" ON  A.DEALER_CODE=G.DEALER_CODE AND A.PART_NO=G.PART_NO LEFT JOIN TW_POS_INFIX_RELATION F ON  ");
		sqlsb.append(" A.DEALER_CODE = F.DEALER_CODE AND B.PART_INFIX  = F.PART_INFIX AND F.IS_VALID ="
				+ DictCodeConstants.DICT_IS_YES);
		sqlsb.append(" LEFT JOIN TW_MALFUNCTION_POSITION E ON e.is_valid=" + DictCodeConstants.DICT_IS_YES
				+ " and A.DEALER_CODE = E.DEALER_CODE  ");
		sqlsb.append(" AND F.POS_CODE = E.POS_CODE  LEFT JOIN TM_STORAGE TS ON A.DEALER_CODE = TS.DEALER_CODE ");
		sqlsb.append("  AND A.STORAGE_CODE=TS.STORAGE_CODE   ");
		sqlsb.append(
				" WHERE ( 1=2  OR A.STORAGE_CODE='CB10'   OR A.STORAGE_CODE='CKA1'   OR A.STORAGE_CODE='CKA2'   OR A.STORAGE_CODE='CKA3'    ");
		sqlsb.append(
				" OR A.STORAGE_CODE='CKA4'   OR A.STORAGE_CODE='CKA5'   OR A.STORAGE_CODE='CKA6'   OR A.STORAGE_CODE='CKB1'    ");
		sqlsb.append(
				" OR A.STORAGE_CODE='CKB2'   OR A.STORAGE_CODE='CKB3'   OR A.STORAGE_CODE='CKB4'   OR A.STORAGE_CODE='CKB5'    ");
		sqlsb.append(
				" OR A.STORAGE_CODE='CKB6'   OR A.STORAGE_CODE='CKB7'   OR A.STORAGE_CODE='CKB8'   OR A.STORAGE_CODE='CKB9'    ");
		sqlsb.append(
				" OR A.STORAGE_CODE='CKF1'   OR A.STORAGE_CODE='JPCK'   OR A.STORAGE_CODE='OEMK'   OR A.STORAGE_CODE='QTCK'    ");
		sqlsb.append(
				" OR A.STORAGE_CODE='SBCK'   OR A.STORAGE_CODE='WJGK'   OR A.STORAGE_CODE='YLCK'   OR A.STORAGE_CODE='ZCCK'    ");
		sqlsb.append(" OR A.STORAGE_CODE='ZSCK'   )  ");
		sqlsb.append(" AND A.DEALER_CODE = '" + FrameworkUtil.getLoginInfo().getDealerCode() + "'   ");
		sqlsb.append(" AND A.D_KEY =" + DictCodeConstants.D_KEY);
		sqlsb.append(" AND A.PART_STATUS<>" + DictCodeConstants.DICT_IS_YES);
		sqlsb.append(" and TS.CJ_TAG=" + DictCodeConstants.DICT_IS_YES);
		if (!StringUtils.isNullOrEmpty(qParam.get("partModelGroupCodeSet"))) {
			sqlsb.append(" and A.PART_MODEL_GROUP_CODE_SET LIKE  ?");
			params.add("%" + qParam.get("partModelGroupCodeSet") + "%");
		}

		if (!StringUtils.isNullOrEmpty(qParam.get("storageCode"))) {
			sqlsb.append(" and A.STORAGE_CODE = ?");
			params.add(qParam.get("storageCode"));
		}

		if (!StringUtils.isNullOrEmpty(qParam.get("partNo"))) {
			sqlsb.append(" and A.PART_NO LIKE  ?");
			params.add("%" + qParam.get("partNo") + "%");
		}

		if (!StringUtils.isNullOrEmpty(qParam.get("partName"))) {
			sqlsb.append(" and A.PART_NAME LIKE  ?");
			params.add("%" + qParam.get("partName") + "%");
		}

		if (!StringUtils.isNullOrEmpty(qParam.get("partGroupCode"))) {
			sqlsb.append(" and B.PART_GROUP_CODE = ?");
			params.add(qParam.get("partGroupCode"));
		}

		if (!StringUtils.isNullOrEmpty(qParam.get("storagePositionCode"))) {
			sqlsb.append(" and A.STORAGE_POSITION_CODE LIKE  ?");
			params.add("%" + qParam.get("storagePositionCode") + "%");
		}

		if (!StringUtils.isNullOrEmpty(qParam.get("spellCode"))) {
			sqlsb.append(" and A.SPELL_CODE LIKE  ?");
			params.add("%" + qParam.get("spellCode") + "%");
		}

		if (!StringUtils.isNullOrEmpty(qParam.get("priceOverZero"))) {
			if (qParam.get("priceOverZero").equals(DictCodeConstants.DICT_IS_YES)) {
				sqlsb.append(" and A.SALES_PRICE > 0 ");
			}
		}

		if (!StringUtils.isNullOrEmpty(qParam.get("partNo"))) {
			sqlsb.append(" and B.part_no LIKE  ?");
			params.add("%" + qParam.get("partNo") + "%");
		}

		if (!StringUtils.isNullOrEmpty(qParam.get("stockOverZero"))) {
			if (qParam.get("stockOverZero").equals(DictCodeConstants.DICT_IS_YES)) {
				sqlsb.append(" and (A.STOCK_QUANTITY + A.BORROW_QUANTITY - A.LEND_QUANTITY - A.LOCKED_QUANTITY)>0   ");
			}
		}

		if (!StringUtils.isNullOrEmpty(qParam.get("brand"))) {
			sqlsb.append(" and B.BRAND = ?");
			params.add(qParam.get("brand"));
		}

		if (!StringUtils.isNullOrEmpty(qParam.get("partInfixName"))) {
			sqlsb.append(" and B.PART_INFIX_NAME LIKE  ?");
			params.add("%" + qParam.get("partInfixName") + "%");
		}

		if (!StringUtils.isNullOrEmpty(qParam.get("remark"))) {
			sqlsb.append(" and A.REMARK LIKE  ?");
			params.add("%" + qParam.get("remark") + "%");
		}

		PageInfoDto pageInfoDto = DAOUtil.pageQuery(sqlsb.toString(), params);
		return pageInfoDto;

	}

	/**
	 * 工单新增配件根据条件查询出配件后，双击配件，带出配件库存信息
	 * 
	 * @author zhengcong
	 * @date 2017年4月25日
	 */
	@Override
	public PageInfoDto newQueryPartStock(String PART_NO, String STORAGE_CODE) throws ServiceBizException {
		double rate = 1 + Utility.getDouble(Utility.getDefaultValue("2034"));
		StringBuffer sqlsb = new StringBuffer(
				"SELECT '' as POS_CODE,'' as POS_NAME,A.PART_MODEL_GROUP_CODE_SET A_PART_MODEL_GROUP_CODE_SET, 0.0 as PART_QUANTITY,  ");
		sqlsb.append(
				" A.dealer_code, A.PART_NO, A.STORAGE_CODE, A.STORAGE_POSITION_CODE, A.PART_NAME,  A.SPELL_CODE, ");
		sqlsb.append(" A.UNIT_CODE, A.STOCK_QUANTITY, A.SALES_PRICE, A.CLAIM_PRICE, ");
		sqlsb.append(" A.PART_GROUP_CODE,  B.LIMIT_PRICE, A.LATEST_PRICE, A.COST_PRICE AS COST_PRICE,A.COST_PRICE*"
				+ rate + " as NET_COST_PRICE, ");
		sqlsb.append(
				" A.COST_AMOUNT, A.MAX_STOCK,B.DOWN_TAG,  A.BORROW_QUANTITY, A.LEND_QUANTITY, A.LOCKED_QUANTITY, ");
		sqlsb.append(" A.LAST_STOCK_IN, A.PART_STATUS,  A.LAST_STOCK_OUT, A.REMARK, A.VER, A.MIN_STOCK,   ");
		sqlsb.append(" B.OPTION_NO, B.PART_MODEL_GROUP_CODE_SET , A.NODE_PRICE, B.PLAN_PRICE, B.OEM_LIMIT_PRICE,  ");
		sqlsb.append(" B.URGENT_PRICE,  B.INSTRUCT_PRICE,A.INSURANCE_PRICE, D.OPTION_STOCK,  ");
		sqlsb.append(
				" (A.STOCK_QUANTITY + A.BORROW_QUANTITY - A.LEND_QUANTITY - A.LOCKED_QUANTITY) AS USEABLE_STOCK, ");
		sqlsb.append(" B.IS_BACK,B.PART_INFIX,  B.MIN_LIMIT_PRICE  FROM TM_PART_STOCK A  LEFT OUTER JOIN ("
				+ CommonConstants.VM_PART_INFO + ") B  ");
		sqlsb.append(" ON ( A.dealer_code = B.dealer_code AND A.PART_NO= B.PART_NO )  LEFT OUTER JOIN  ");
		sqlsb.append(
				" (select dealer_code, part_no, sum(C.STOCK_QUANTITY) AS OPTION_STOCK FROM TM_PART_STOCK C GROUP BY dealer_code,PART_NO ) D   ");
		sqlsb.append(" ON ( A.dealer_code = D.dealer_code AND A.PART_NO= D.PART_NO AND B.OPTION_NO = D.PART_NO )  ");
		sqlsb.append(" WHERE A.dealer_code = '" + FrameworkUtil.getLoginInfo().getDealerCode() + "'   ");
		sqlsb.append(" AND A.D_KEY =" + DictCodeConstants.D_KEY);
		sqlsb.append(" and A.PART_NO =  '" + PART_NO + "'    ");
		sqlsb.append(" and A.STORAGE_CODE = '" + STORAGE_CODE + "'  ");

		PageInfoDto pageInfoDto = DAOUtil.pageQuery(sqlsb.toString(), null);
		return pageInfoDto;

	}

	/**
	 * 查询替代配件
	 * 
	 * @author zhengcong
	 * @date 2017年4月26日
	 */
	@Override
	public PageInfoDto queryInsteadPart(String OPTION_NO) throws ServiceBizException {
		double rate = 1 + Utility.getDouble(Utility.getDefaultValue("2034"));
		StringBuffer sqlsb = new StringBuffer("SELECT B.OPTION_NO,A.COST_PRICE*" + rate
				+ "  AS NET_COST_PRICE,A.COST_AMOUNT*" + rate + " AS NET_COST_AMOUNT,  ");
		sqlsb.append(
				" B.ORI_PRO_CODE,A.IS_SUGGEST_ORDER,A.PART_MODEL_GROUP_CODE_SET,A.PART_MAIN_TYPE,A.PART_SPE_TYPE, ");
		sqlsb.append(" A.dealer_code, A.PART_NO, A.FOUND_DATE, A.STORAGE_CODE,  ");
		sqlsb.append(" A.STORAGE_POSITION_CODE, A.PART_NAME,  A.SPELL_CODE, A.PART_GROUP_CODE, A.UNIT_CODE,  ");
		sqlsb.append(" A.STOCK_QUANTITY, A.SALES_PRICE,  A.CLAIM_PRICE, A.LIMIT_PRICE, A.LATEST_PRICE,  ");
		sqlsb.append(" A.COST_PRICE*1.0000 COST_PRICE, A.COST_AMOUNT*1.0000 COST_AMOUNT, A.MAX_STOCK,   ");
		sqlsb.append(" A.MIN_STOCK, A.BORROW_QUANTITY, A.LEND_QUANTITY, A.LOCKED_QUANTITY, A.PART_STATUS,   ");
		sqlsb.append(" A.LAST_STOCK_IN, A.LAST_STOCK_OUT, A.REMARK, A.VER,  A.JAN_MODULUS,A.FEB_MODULUS, ");
		sqlsb.append(" A.MAR_MODULUS,A.APR_MODULUS,A.MAY_MODULUS,A.JUN_MODULUS,A.JUL_MODULUS,A.AUG_MODULUS,  ");
		sqlsb.append(" A.SEP_MODULUS,A.OCT_MODULUS,A.NOV_MODULUS,A.DEC_MODULUS,A.MONTHLY_QTY_FORMULA, ");
		sqlsb.append(" B.PRODUCTING_AREA, B.BRAND, B.MIN_PACKAGE,B.DOWN_TAG,B.FROM_ENTITY, ");
		sqlsb.append(" b.part_infix,'' pos_code,'' pos_name,    D.OPTION_STOCK,A.INSURANCE_PRICE,  B.INSTRUCT_PRICE, ");
		sqlsb.append(
				" (A.STOCK_QUANTITY + A.BORROW_QUANTITY - A.LEND_QUANTITY - A.LOCKED_QUANTITY) AS USEABLE_STOCK ,  ");
		sqlsb.append(
				" (A.STOCK_QUANTITY + A.BORROW_QUANTITY - A.LEND_QUANTITY - A.LOCKED_QUANTITY) AS USEABLE_QUANTITY , ");
		sqlsb.append(" '' as PART_BATCH_NO,    CASE WHEN ( SELECT 1 FROM TM_MAINTAIN_PART CC   ");
		sqlsb.append(" WHERE CC.PART_NO = B.PART_NO AND CC.dealer_code = B.dealer_code) >0  ");
		sqlsb.append(" THEN " + DictCodeConstants.DICT_IS_YES + " ELSE " + DictCodeConstants.DICT_IS_NO
				+ " END AS IS_MAINTAIN ,A.NODE_PRICE   ");
		sqlsb.append(" FROM TM_PART_STOCK A  LEFT OUTER JOIN (" + CommonConstants.VM_PART_INFO + ") B  ");
		sqlsb.append(" ON ( A.dealer_code = B.dealer_code AND A.PART_NO= B.PART_NO  )   ");
		sqlsb.append(" LEFT OUTER JOIN (select dealer_code, part_no, sum(C.STOCK_QUANTITY) AS OPTION_STOCK  ");
		sqlsb.append(" FROM TM_PART_STOCK C WHERE  c.part_status<>" + DictCodeConstants.DICT_IS_YES
				+ "  AND  dealer_code='" + FrameworkUtil.getLoginInfo().getDealerCode() + "'  ");
		sqlsb.append(" AND D_KEY=" + DictCodeConstants.D_KEY + " GROUP BY dealer_code,PART_NO ) D   ");
		sqlsb.append(" ON ( A.dealer_code = D.dealer_code AND A.PART_NO= D.PART_NO AND B.PART_NO = D.PART_NO ) ");
		sqlsb.append(" WHERE A.dealer_code = '" + FrameworkUtil.getLoginInfo().getDealerCode() + "'  ");
		sqlsb.append(" AND A.D_KEY =" + DictCodeConstants.D_KEY);
		sqlsb.append(" and a.part_status<>" + DictCodeConstants.DICT_IS_YES);
		sqlsb.append(" AND (  A.PART_NO='" + OPTION_NO + "'  )   ");

		PageInfoDto pageInfoDto = DAOUtil.pageQuery(sqlsb.toString(), null);
		return pageInfoDto;
	}

	@Override
	public String recordInDetail(List<Map> repairPart, Map param) {
		if (repairPart.size() > 0) {
			String sheetNos = "";
			if (param.get("RO_NO") != null && !"".equals(param.get("RO_NO"))) {
				sheetNos = param.get("RO_NO").toString();
			} else if (param.get("ALLOCATE_OUT_NO") != null && !"".equals(param.get("ALLOCATE_OUT_NO"))) {
				sheetNos = param.get("ALLOCATE_OUT_NO").toString();
			} else if (param.get("SALES_PART_NO") != null && !"".equals(param.get("SALES_PART_NO"))) {
				sheetNos = param.get("SALES_PART_NO").toString();
			} else if (param.get("RECEIPT_NO") != null && !"".equals(param.get("RECEIPT_NO"))) {
				sheetNos = param.get("RECEIPT_NO").toString();
			} else if (param.get("LEND_NO") != null && !"".equals(param.get("LEND_NO"))) {
				sheetNos = param.get("LEND_NO").toString();
			} else if (param.get("LOSS_NO") != null && !"".equals(param.get("LOSS_NO"))) {
				sheetNos = param.get("LOSS_NO").toString();
			} else if (param.get("SALES_NO") != null && !"".equals(param.get("SALES_NO"))) {
				sheetNos = param.get("SALES_NO").toString();
			} else if (param.get("SHEET_NO") != null && !"".equals(param.get("SHEET_NO"))) {
				sheetNos = param.get("SHEET_NO").toString();
			}
			List<TtShortPartPO> find = TtShortPartPO.find("SHEET_NO = ? AND D_KEY = ?", sheetNos,
					CommonConstants.D_KEY);
			if (find.size() > 0) {
				for (TtShortPartPO ttShortPartPO : find) {
					if (!DictCodeConstants.DICT_IS_YES.equals(ttShortPartPO.getString("IS_BO"))) {
						ttShortPartPO.delete();
					}
				}
			}
			String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
			TtShortPartPO shortPart = new TtShortPartPO();
			for (Map map : repairPart) {
				List<TtShortPartPO> find2 = new ArrayList<TtShortPartPO>();
				// 判断该工单的配件在缺料明细表中，是否已做BO，否则删除再插入。是则直接插入
				if (StringUtils.isNullOrEmpty(map.get("STORAGE_POSITION_CODE"))) {
					find2 = TtShortPartPO.find(
							"SHEET_NO = ? AND PART_NO = ? AND STORAGE_CODE = ? AND D_KEY = ? AND IS_BO = ?", sheetNos,
							objToStr(map.get("PART_NO")), objToStr(map.get("STORAGE_CODE")), CommonConstants.D_KEY,
							objToStr(map.get("IS_BO")));
				} else {
					find2 = TtShortPartPO.find(
							"SHEET_NO = ? AND PART_NO = ? AND STORAGE_CODE = ? AND D_KEY = ? AND IS_BO = ?", sheetNos,
							objToStr(map.get("PART_NO")), objToStr(map.get("STORAGE_CODE")), CommonConstants.D_KEY,
							objToStr(map.get("IS_BO")));
				}
				if (find2.size() > 0) {
					for (TtShortPartPO ttShortPartPO : find2) {
						ttShortPartPO.delete();
					}
				}

				shortPart.setString("DEALER_CODE", dealerCode);
				shortPart.setInteger("CLOSE_STATUS",
						Integer.parseInt(DictCodeConstants.DICT_PART_SHORT_CLOSE_STATUS_TYPE_NOT_END_A_CASE));
				if (!StringUtils.isNullOrEmpty(map.get("CUSTOMER_NAME"))) {
					shortPart.setString("CUSTOMER_NAME", map.get("CUSTOMER_NAME").toString());
				}
				if (!StringUtils.isNullOrEmpty(map.get("HANDLER"))) {
					shortPart.setString("HANDLER", map.get("HANDLER").toString());
				}
				if (!StringUtils.isNullOrEmpty(map.get("IN_OUT_TYPE"))) {
					shortPart.setInteger("IN_OUT_TYPE", Integer.parseInt(objToStr(map.get("IN_OUT_TYPE"))));
				}
				if (!StringUtils.isNullOrEmpty(map.get("IS_URGENT"))) {
					shortPart.setInteger("IS_URGENT", Integer.parseInt(objToStr(map.get("IS_URGENT"))));
				}
				if (!StringUtils.isNullOrEmpty(map.get("LICENSE"))) {
					shortPart.setString("LICENSE", map.get("LICENSE").toString());
				}
				if (!StringUtils.isNullOrEmpty(map.get("PART_NAME"))) {
					shortPart.setString("PART_NAME", map.get("PART_NAME").toString());
				}
				shortPart.setString("PART_NO", map.get("PART_NO").toString());
				if (!StringUtils.isNullOrEmpty(map.get("PHONE"))) {
					shortPart.setString("PHONE", map.get("PHONE").toString());
				}
				if (!StringUtils.isNullOrEmpty(map.get("SEND_TIME"))) {
					shortPart.set("SEND_TIME", map.get("SEND_TIME"));
				}
				if (!StringUtils.isNullOrEmpty(param.get("SHEET_NO"))) {
					shortPart.setString("SHEET_NO", param.get("SHEET_NO").toString());
				} else if (!StringUtils.isNullOrEmpty(param.get("roNo"))) {
					shortPart.setString("SHEET_NO", param.get("roNo").toString());
				}
				if (!StringUtils.isNullOrEmpty(map.get("SHORT_QUANTITY"))) {
					shortPart.setString("SHORT_QUANTITY", map.get("SHORT_QUANTITY").toString());
				}
				if (!StringUtils.isNullOrEmpty(map.get("SHORT_TYPE"))) {
					shortPart.setInteger("SHORT_TYPE", Integer.parseInt(objToStr(map.get("SHORT_TYPE"))));
				}
				if (!StringUtils.isNullOrEmpty(map.get("STORAGE_CODE"))) {
					shortPart.setString("STORAGE_CODE", map.get("STORAGE_CODE").toString());
				}
				if (!StringUtils.isNullOrEmpty(map.get("STORAGE_POSITION_CODE"))) {
					shortPart.setString("STORAGE_POSITION_CODE", map.get("STORAGE_POSITION_CODE").toString());
				}
				// 三包订单如果有配件缺料的话，配件缺料单中的订货状为未订货
				if (!StringUtils.isNullOrEmpty(shortPart.get("SHEET_NO"))) {
					RepairOrderPO tpoPO = RepairOrderPO.findByCompositeKeys(dealerCode, param.get("RO_NO").toString());
					if (StringUtils.isNullOrEmpty(tpoPO) && tpoPO.getInteger("D_KEY") == CommonConstants.D_KEY) {
						if (tpoPO.getInteger("SCHEME_STATUS") != 0) {
							List<TmPartInfoPO> tmpiPOlist = TmPartInfoPO.find(
									"PART_NO = ? AND D_KEY = ? AND OEM_TAG = ?", map.get("PART_NO").toString(),
									CommonConstants.D_KEY, DictCodeConstants.DICT_IS_YES);
							if (tmpiPOlist.size() > 0) {
								shortPart.setInteger("ORDER_GOODS_STATUS",
										Integer.parseInt(DictCodeConstants.DICT_ORDER_GOODS_STATUS_NO));// 初始订单状态为未订货
							} else {
								shortPart.setInteger("ORDER_GOODS_STATUS",
										Integer.parseInt(DictCodeConstants.DICT_ORDER_GOODS_STATUS_MAKE_YES));// 初始订单状态自采件为强制完成
							}
						}
					}
				}
				shortPart.saveIt();
				return "1";
			}
		}
		return "0";
	}

	/**
	 * obj对象转换成str类型
	 * 
	 * @param obj
	 * @return
	 */
	private String objToStr(Object obj) {
		return StringUtils.isNullOrEmpty(obj) ? "" : obj.toString();
	}

	@Override
	public String getIsMaintenance(Map param) {
		StringBuffer sb = new StringBuffer(" select * from TM_MAINTAIN_PART WHERE 1=1 ");
		if (!StringUtils.isNullOrEmpty(param.get("partNo"))) {
			sb.append(" and part_no in (").append(objToStr(param.get("partno"))).append(")");
		}
		List<Map> findAll = DAOUtil.findAll(sb.toString(), null);
		if (findAll.size() > 0) {
			return "1";
		} else {
			return "0";
		}
	}

	@Override
	public Map queryVehicleforactivity(Map param) {
		String defaultValue = Utility.getDefaultValue("1314");// 缓存开关是否打开
		if ((!StringUtils.isNullOrEmpty(defaultValue) && defaultValue.equals(DictCodeConstants.DICT_IS_YES))
				|| "1".equals("1")) {
			StringBuffer sb = new StringBuffer("SELECT * FROM ").append(CommonConstants.VM_VEHICLE)
					.append(" WHERE 1=1");
			Utility.sqlToLike(sb, objToStr(param.get("vin")), "VIN", null);
			List<Map> findAll = DAOUtil.findAll(sb.toString(), null);
			return findAll.get(0);
		} else {
			return null;
		}
	}

	@Override
	public PageInfoDto selectEmployee(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT C.DEALER_CODE, E.EMPLOYEE_NO,E.EMPLOYEE_NAME,E.LABOUR_FACTOR,E.WORKER_TYPE_CODE, ");
		sql.append(" E.LABOUR_POSITION_CODE,C.ASSIGN_LABOUR_HOUR  FROM TM_EMPLOYEE E  LEFT JOIN ( ");
		sql.append(" SELECT A.DEALER_CODE,A.TECHNICIAN,SUM(A.ASSIGN_LABOUR_HOUR) ASSIGN_LABOUR_HOUR ");
		sql.append(" FROM TT_RO_ASSIGN A ");
		sql.append(" LEFT JOIN TT_REPAIR_ORDER B ON A.RO_NO = B.RO_NO AND A.DEALER_CODE = B.DEALER_CODE ");
		sql.append(" AND A.D_KEY = B.D_KEY AND B.RO_STATUS = (" + CommonConstants.DICT_RO_STATUS_TYPE_ON_REPAIR + ")");
		sql.append(" AND B.COMPLETE_TAG <> (" + CommonConstants.DICT_IS_YES + ") WHERE A.DEALER_CODE =?");
		sql.append(" AND A.D_KEY = (" + CommonConstants.D_KEY + ") AND A.FINISHED_TAG <> ("
				+ CommonConstants.DICT_IS_YES + ")");
		sql.append(" GROUP BY A.TECHNICIAN,A.DEALER_CODE ");
		sql.append(" ) C ON E.DEALER_CODE = C.DEALER_CODE AND E.EMPLOYEE_NO = C.TECHNICIAN "
				+ " WHERE E.IS_TECHNICIAN =(" + CommonConstants.DICT_IS_YES + ") ");
		sql.append(" AND E.IS_VALID = (" + CommonConstants.DICT_IS_YES + ") AND E.DEALER_CODE = ?");
		params.add(dealerCode);
		params.add(dealerCode);
		if (!StringUtils.isNullOrEmpty(queryParam.get("techniCode"))) {
			sql.append("  AND  C.TECHNICIAN like ? ");
			params.add(queryParam.get("techniCode"));
		}
		PageInfoDto pageinfoDto = DAOUtil.pageQuery(sql.toString(), params);
		return pageinfoDto;
	}

	@Override
	public List<Map> querytechnician(Map<String, String> queryParam) throws ServiceBizException {
		StringBuffer sql = new StringBuffer();

		sql.append(
				" SELECT DISTINCT  A.WORKER_TYPE_CODE,C.ORG_CODE,A.DEALER_CODE,A.E_MAIL,A.DOWN_TAG,A.PHONE,A.TECHNICIAN_GRADE,A.EMPLOYEE_NO,A.EMPLOYEE_NAME,A.POSITION_CODE,A.LABOUR_POSITION_CODE,A.IS_VALID,A.BIRTHDAY,A.IS_TAKE_PART,A.IS_SERVICE_ADVISOR, ");
		sql.append(
				" A.MOBILE,A.WORKGROUP_CODE,A.RESUME,A.TRAINING,A.ZIP_CODE,A.LABOUR_FACTOR,A.IS_TRACER,A.FOUND_DATE,A.IS_TEST_DRIVER,A.GENDER,A.CERTIFICATE_ID,A.IS_TECHNICIAN,A.IS_CHECKER,A.IS_CONSULTANT,A.ADDRESS,A.IS_INSURANCE_ADVISOR,A.IS_MAINTAIN_ADVISOR,B.ORG_NAME AS DEPT_NAME ");
		sql.append(" from TM_EMPLOYEE  A ");
		sql.append(" left join tm_organization b on  a.DEALER_CODE = b.DEALER_CODE AND a.ORG_CODE = b.ORG_CODE ");
		sql.append(
				" left join TM_USER C on  a.DEALER_CODE = C.DEALER_CODE AND a.EMPLOYEE_NO = C.EMPLOYEE_NO AND C.USER_STATUS =? ");
		sql.append(
				" WHERE A.IS_VALID = ?  AND A.DEALER_CODE in ( select PARENT_ENTITY from TM_ENTITY_RELATIONSHIP where PARENT_ENTITY = ? and biz_code ='UNIFIED_VIEW' ) ");
		sql.append(" AND (( DOWN_TAG = ? and FROM_ENTITY <> ? or ( DOWN_TAG =?  and A.FROM_ENTITY IS NULL))) ");
		List<Object> params = new ArrayList<Object>();
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		params.add(DictCodeConstants.DICT_IS_YES);
		params.add(DictCodeConstants.DICT_IS_YES);
		params.add(dealerCode);
		params.add(DictCodeConstants.DICT_IS_YES);
		params.add(dealerCode);
		params.add(DictCodeConstants.DICT_IS_NO);
		System.out.println(sql.toString());
		// 执行查询操作
		List<Map> result = DAOUtil.findAll(sql.toString(), params);
		return result;
	}

	@Override
	public List<Map> queryrepairPosition(Map<String, String> queryParam) throws ServiceBizException {
		StringBuffer sql = new StringBuffer();
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		List<Object> params = new ArrayList<Object>();
		sql.append(
				" SELECT E.LABOUR_POSITION_NAME,A.*,C.USER_ID,C.USER_CODE,C.USER_NAME  FROM TM_EMPLOYEE A  LEFT JOIN TM_USER C ON C.DEALER_CODE = A.DEALER_CODE AND C.EMPLOYEE_NO = A.EMPLOYEE_NO  ");
		sql.append(
				" left join tm_repair_position  E  ON E.DEALER_CODE = A.DEALER_CODE  AND A.LABOUR_POSITION_CODE = E.LABOUR_POSITION_CODE WHERE a.DEALER_CODE = ? ");
		params.add(dealerCode);
		if (!StringUtils.isNullOrEmpty(queryParam.get("code"))) {
			sql.append(" AND A.EMPLOYEE_NO = ?");
			params.add(queryParam.get("code"));
		}

		List<Map> map = DAOUtil.findAll(sql.toString(), params);
		return map;
	}

	// 派工技师变化时，改变CODE 工位 工种名称 工时系数
	@Override
	public Map findtechnicianItem(String techniciancode) {
		StringBuffer sql = new StringBuffer();
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		List<Object> params = new ArrayList<Object>();
		sql.append(
				" SELECT D.WORKER_TYPE_NAME ,	E.LABOUR_POSITION_NAME,A.*,C.USER_ID,C.USER_CODE,C.USER_NAME  FROM TM_EMPLOYEE A  LEFT JOIN TM_USER C ON C.DEALER_CODE = A.DEALER_CODE AND C.EMPLOYEE_NO = A.EMPLOYEE_NO ");
		sql.append(
				"left join tm_worker_type  D  ON D.DEALER_CODE = A.DEALER_CODE AND A.WORKER_TYPE_CODE = D.WORKER_TYPE_CODE left join tm_repair_position  E  ON E.DEALER_CODE = A.DEALER_CODE  AND A.LABOUR_POSITION_CODE = E.LABOUR_POSITION_CODE  WHERE a.DEALER_CODE = ? ");
		params.add(dealerCode);
		if (!StringUtils.isNullOrEmpty(techniciancode)) {
			sql.append(" AND A.EMPLOYEE_NO = ?");
			params.add(techniciancode);
		}

		Map map = DAOUtil.findFirst(sql.toString(), params);
		return map;
	}

	// 工种名称下拉
	@Override
	public List<Map> querytechnicianWorkType() throws ServiceBizException {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT * from tm_worker_type");
		List<Map> map = DAOUtil.findAll(sql.toString(), null);
		return map;
	}

	@Override
	public Map findOrderDetails(String roNo) {
		StringBuffer sb = new StringBuffer();
		sb.append("select 1 SEL_FLAG,1 IS_DXP,'' SOURCE_NO,'' DXP_UPDATE_STATUS, A.IS_CLOSE_RO,'' BOOKING_COME_TIME,")
				.append("'' SOURCE_ITEM,A.FIRST_ESTIMATE_AMOUNT,A.IS_TAKE_PART_OLD,A.ESTIMATE_BEGIN_TIME,A.CREATED_AT,A.MODIFY_NUM,")
				.append("A.RO_SPLIT_STATUS,A.SALES_PART_NO,A.DEALER_CODE, A.RO_NO, A.BOOKING_ORDER_NO,COALESCE(A.ESTIMATE_NO,C.ESTIMATE_NO) AS ESTIMATE_NO, A.RO_TYPE, ")
				.append("A.REPAIR_TYPE_CODE, A.OTHER_REPAIR_TYPE, A.VEHICLE_TOP_DESC, A.SEQUENCE_NO, ")
				.append("A.PRIMARY_RO_NO, A.INSURATION_NO, A.INSURATION_CODE, A.IS_CUSTOMER_IN_ASC, A.IS_SEASON_CHECK,")
				.append(" A.OIL_REMAIN, A.IS_WASH, A.IS_TRACE, A.TRACE_TIME, A.NO_TRACE_REASON, A.NEED_ROAD_TEST,")
				.append(" A.RECOMMEND_EMP_NAME, A.RECOMMEND_CUSTOMER_NAME, A.SERVICE_ADVISOR, A.SERVICE_ADVISOR_ASS, ")
				.append("A.RO_STATUS, A.RO_CREATE_DATE, A.END_TIME_SUPPOSED, A.CHIEF_TECHNICIAN, A.OWNER_NO, ")
				.append("A.OWNER_NAME,A.OWNER_PROPERTY, A.LICENSE, A.VIN, A.ENGINE_NO, A.BRAND, A.SERIES,")
				.append(" A.MODEL,A.CONFIG_CODE, A.IN_MILEAGE,OUT_MILEAGE, A.IS_CHANGE_ODOGRAPH, A.CHANGE_MILEAGE,A.NOT_INTEGRAL, ")
				.append("CAST(A.TOTAL_CHANGE_MILEAGE  AS SIGNED)")
				.append(" as TOTAL_CHANGE_MILEAGE , A.TOTAL_MILEAGE, A.DELIVERER, A.DELIVERER_GENDER, ")
				.append("A.DELIVERER_PHONE, A.DELIVERER_MOBILE, A.FINISH_USER, A.COMPLETE_TAG,")
				.append(" A.WAIT_INFO_TAG, A.WAIT_PART_TAG,COMPLETE_TIME, A.FOR_BALANCE_TIME, ")
				.append("A.LABOUR_PRICE, A.LABOUR_AMOUNT, A.REPAIR_PART_AMOUNT, A.SALES_PART_AMOUNT,")
				.append(" A.ADD_ITEM_AMOUNT, A.OVER_ITEM_AMOUNT, A.REPAIR_AMOUNT, A.ESTIMATE_AMOUNT, ")
				.append("A.BALANCE_AMOUNT, A.RECEIVE_AMOUNT, A.SUB_OBB_AMOUNT, A.DERATE_AMOUNT,")
				.append(" A.TRACE_TAG, A.REMARK, A.REMARK1, A.REMARK2,A.TEST_DRIVER, A.PRINT_RO_TIME, A.PRINT_RP_TIME,")
				.append(" A.IS_ACTIVITY, A.CUSTOMER_DESC, A.DELIVERY_TAG, A.DELIVERY_DATE, A.LOCK_USER, ")
				.append("A.RO_CHARGE_TYPE,B.NEXT_MAINTAIN_DATE,B.NEXT_MAINTAIN_MILEAGE,")
				.append(" A.MEMBER_NO,A.IN_REASON,A.VER, A.IS_MAINTAIN, A.RO_TROUBLE_DESC, ")
				.append("	B.LAST_MAINTAIN_DATE,B.SALES_DATE,B.LICENSE_DATE,B.FIRST_IN_DATE,B.LAST_MAINTAIN_MILEAGE, ")
				.append("B.COLOR,B.CHANGE_DATE,B.WRT_BEGIN_DATE ,B.MODEL_YEAR,B.PRODUCT_CODE,B.VIP_NO,A.IS_LARGESS_MAINTAIN,A.IS_PURCHASE_MAINTENANCE,A.TROUBLE_OCCUR_DATE,A.RO_CLAIM_STATUS, ")
				.append("A.IS_TRIPLE_GUARANTEE,A.WORKSHOP_STATUS ")
				.append(",A.OCCUR_INSURANCE_NO,A.IS_TRIPLE_GUARANTEE_BEF, A.IS_ABANDON_ACTIVITY, ")
				.append(" A.SCHEME_STATUS,A.SERIOUS_SAFETY_STATUS ")
				.append(" from TT_REPAIR_ORDER A LEFT JOIN (" + CommonConstants.VM_VEHICLE + ") B")
				.append(" ON A.VIN=B.VIN AND A.DEALER_CODE=B.DEALER_CODE LEFT JOIN TT_ESTIMATE_ORDER C ON A.RO_NO=C.RO_NO AND A.DEALER_CODE=C.DEALER_CODE WHERE ")
				.append(" A.RO_NO=? AND A.D_KEY=").append(CommonConstants.D_KEY);
		List queryParam = new ArrayList();
		queryParam.add(roNo);
		return DAOUtil.findFirst(sb.toString(), queryParam);
	}

	@Override
	public Map queryMemberCardExist(Map param) {
		StringBuffer sql = new StringBuffer();
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		List<Object> params = new ArrayList<Object>();
		sql.append(" select distinct b.*,e.CARD_TYPE_NAME from (" + CommonConstants.VM_MEMBER_CARD + ") b left join  ("
				+ CommonConstants.VM_MEMBER
				+ ") a on b.DEALER_CODE = a.DEALER_CODE and b.member_no=a.member_no left join  ");
		sql.append(" (" + CommonConstants.VM_MEMBER_VEHICLE
				+ ") c on b.DEALER_CODE = c.DEALER_CODE and b.card_id = c.card_id left join ("
				+ CommonConstants.VM_MEMBER_ACCOUNT + ") d on d.CARD_ID=b.CARD_ID and d.DEALER_CODE=b.DEALER_CODE ");
		sql.append(" LEFT JOIN (" + CommonConstants.VM_CARD_TYPE
				+ ") e ON b.card_type_code=e.card_type_code and b.DEALER_CODE=e.DEALER_CODE where a.DEALER_CODE= ? ");
		params.add(dealerCode);
		sql.append(" and a.member_status= ? ");
		params.add(DictCodeConstants.DICT_MEMBER_STATUS_NORMAL);
		sql.append(" and b.card_status= ? ");
		params.add(DictCodeConstants.DICT_CARD_STATUS_NORMAL);
		sql.append(" and 1=1 and e.STATUS= ? ");
		params.add(DictCodeConstants.DICT_IS_YES);
		if (!StringUtils.isNullOrEmpty(param.get("vin"))) {
			sql.append(" and c.vin = ?");
			params.add(param.get("vin"));
		}
		List<Map> map = DAOUtil.findAll(sql.toString(), params);
		return map.get(0);
	}

	@Override
	public Map checkVehicleInvoice(Map param) {
		StringBuffer sql = new StringBuffer();
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		List<Object> params = new ArrayList<Object>();
		sql.append(" select vin as RO_NO,DEALER_CODE from TM_VS_STOCK where DEALER_CODE= ? ");
		params.add(dealerCode);
		sql.append(" and vin= ? ");
		params.add(param.get("vin"));
		sql.append(" AND NOT EXISTS (SELECT 1 FROM TT_SO_INVOICE WHERE DEALER_CODE= ? ");
		params.add(dealerCode);
		sql.append(" and vin= ? ");
		params.add(param.get("vin"));
		sql.append(" AND INVOICE_CHARGE_TYPE= ? ");
		params.add(DictCodeConstants.DICT_INVOICE_FEE_VEHICLE);
		sql.append(" ) ");
		List<Map> map = DAOUtil.findAll(sql.toString(), params);
		if (map.size() > 0) {
			return map.get(0);
		} else {
			return new HashMap();
		}
	}

	@Override
	public Map queryRepairOrderExists(Map param) {
		StringBuffer sql = new StringBuffer();
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		sql.append(" select * from TT_REPAIR_ORDER where RO_STATUS = 12251001 and DEALER_CODE = " + dealerCode
				+ " and vin = '" + param.get("vin") + "'");
		List<Map> map = DAOUtil.findAll(sql.toString(), null);
		if (map.size() > 0) {
			return map.get(0);
		} else {
			return new HashMap();
		}
	}

	// @Override
	// public Map checkIsHaveAduitingOrder(Map param) {
	// StringBuffer sql = new StringBuffer();
	// String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
	// List<Object> params = new ArrayList<Object>();
	// String defautParam = Utility.getDefaultValue("1314");
	// sql.append(" ");
	// sql.append(" ");
	// sql.append(" ");
	// sql.append("");
	// return null;
	// }
	// 车主车辆监控(所有)
	@SuppressWarnings("deprecation")
	@Override
	public List<Map> retriveByControl(Map<String, String> queryParam) throws Exception {
		StringBuffer sql = new StringBuffer();
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		String groupCodeVehicle = Utility.getGroupEntity(loginInfo.getDealerCode(), "TM_VEHICLE");
		String vin = null;
		String license = null;
		if ((!StringUtils.isNullOrEmpty(queryParam.get("vin")))) {
			vin = queryParam.get("vin").toString();
		}
		if ((!StringUtils.isNullOrEmpty(queryParam.get("LICENSE")))) {
			license = queryParam.get("LICENSE").toString();
		}
		List<Map> monitorVehicleList = null;
		// 监控车主车辆
		if ((!StringUtils.isNullOrEmpty(queryParam.get("vin")))
				|| (!StringUtils.isNullOrEmpty(queryParam.get("engineNo")))
				|| (!StringUtils.isNullOrEmpty(queryParam.get("ownerNo")))
				|| (!StringUtils.isNullOrEmpty(queryParam.get("LICENSE")))) {
			sql.append("SELECT '监控车主车辆' Flag,AA.MESSAGE,AA.MONITOR_ID,AA.IS_VALID, AA.OWNER_CONSUME_AMOUNT_MIN,");
			sql.append(
					" AA.OWNER_CONSUME_AMOUNT_MAX,Case when (VIN='')or(VIN is null) then '_________________' else VIN end as  VIN, 'no'  as num");
			sql.append("  FROM (" + CommonConstants.VT_MONITOR_VEHICLE + ") AA WHERE d_key = ? AND DEALER_CODE =? ");
			sql.append(
					" and (ENGINE_NO='' or ENGINE_NO=? and (OWNER_NO='' or OWNER_NO=?  ) and (LICENSE='' or LICENSE=?) and REPAIR_ORDER_TAG=?  ");
			sql.append(" and ? between begin_date and end_date ");
			if (!StringUtils.isNullOrEmpty(queryParam.get("SALES_DATE"))) {
				sql.append("  and ((SALES_DATE_BEGIN is null) ");
				Utility.getDateCondsOR("", "SALES_DATE_BEGIN", queryParam.get("SALES_DATE"), "<=");
				sql.append(" and ((SALES_DATE_END is null) ");
				Utility.getDateCondsOR("", "SALES_DATE_END", queryParam.get("SALES_DATE"), ">=");

			}
			sql.append(" and IS_VALID=? ");
			List<Object> params = new ArrayList<Object>();

			params.add(DictCodeConstants.D_KEY);
			params.add(dealerCode);
			params.add(queryParam.get("engineNo"));
			params.add(queryParam.get("ownerNo"));
			params.add(queryParam.get("LICENSE"));
			params.add(DictCodeConstants.DICT_IS_YES);
			params.add(new Timestamp(System.currentTimeMillis()));
			params.add(DictCodeConstants.DICT_IS_YES);
			monitorVehicleList = DAOUtil.findAll(sql.toString(), params);

			String filterStr = " 1=2 ";
			double amountMin = 0;
			double amountMax = 0;
			StringBuffer sql1 = new StringBuffer();
			sql1.append(
					"SELECT sum(A.REAL_TOTAL_AMOUNT) FROM  TT_BALANCE_PAYOBJ A  LEFT JOIN TT_BALANCE_ACCOUNTS B ON B.BALANCE_NO = A.BALANCE_NO AND B.DEALER_CODE = A.DEALER_CODE");
			sql1.append(" AND B.D_KEY = A.D_KEY LEFT JOIN TT_REPAIR_ORDER C ON C.DEALER_CODE = A.DEALER_CODE");
			sql1.append(" AND C.RO_NO = A.RO_NO  AND C.D_KEY = A.D_KEY ");
			sql1.append(" LEFT JOIN ( SELECT DEALER_CODE, OWNER_NO AS CUSTOMER_CODE,");
			sql1.append(" CUS_RECEIVE_SORT FROM (" + CommonConstants.VM_OWNER + ") UNION SELECT DEALER_CODE,");
			sql1.append(" CUSTOMER_CODE, CUS_RECEIVE_SORT FROM (" + CommonConstants.VM_PART_CUSTOMER + ") ");
			sql1.append(" ) E ON E.DEALER_CODE = A.DEALER_CODE AND E.CUSTOMER_CODE = A.PAYMENT_OBJECT_CODE");
			sql1.append(" WHERE A.DEALER_CODE = ? and vin = ? AND E.CUS_RECEIVE_SORT =?");
			List<Object> params1 = new ArrayList<Object>();

			params1.add(dealerCode);
			params1.add(queryParam.get("vin"));
			params1.add(DictCodeConstants.DICT_CUS_RECEIVE_SORT_CUSTOMER_PAY);
			// MOD BY zhl 增加条件，只查询客户付费的总金额
			Double totalAmount = (Double) DAOUtil.findAll(sql1.toString(), params1).get(0).get("real_total_amount");
			for (int i = 0; i < monitorVehicleList.size(); i++) {
				Map db = monitorVehicleList.get(i);
				amountMax = (double) db.get("OWNER_CONSUME_AMOUNT_MAX");
				amountMin = (double) db.get("OWNER_CONSUME_AMOUNT_MIN");
				// add by zhl 增加条件总消费金额在监控范围内
				if ((totalAmount == null) || ((amountMax + amountMin) == 0)
						|| (amountMax + amountMin) > 0 && (totalAmount >= amountMin) && (totalAmount <= amountMax))
					if (("no".equals(db.get("NUM")) && strsame(db.get("VIN").toString(), queryParam.get("vin")))
							|| "pass".equals(db.get("NUM"))) {
						filterStr = filterStr + " or MONITOR_ID=" + db.get("MONITOR_ID");// 监控ID有可能数据类型溢出,所以改为get方法
					}
			}
			StringBuffer sql2 = new StringBuffer();
			List<Object> params2 = new ArrayList<Object>();
			sql2.append(" select '监控车主车辆' Flag,MESSAGE,MONITOR_ID,IS_VALID from (" + CommonConstants.VT_MONITOR_VEHICLE
					+ ") where DEALER_CODE=? and (" + filterStr + ")");
			sql2.append("  and IS_VALID=?");
			params2.add(dealerCode);
			params2.add(DictCodeConstants.DICT_IS_YES);
			System.out.println(sql2.toString());
			monitorVehicleList = DAOUtil.findAll(sql.toString(), params2);

		}

		// 监控保修起始日期
		int aOpenDays = -1; // 建档日期或者上牌日期没有
		Date createDate = new Date();
		Date wrtBeginDate = new Date();
		if ((!StringUtils.isNullOrEmpty(queryParam.get("createDate")))) {
			createDate = new Date(queryParam.get("createDate"));
		}
		if ((!StringUtils.isNullOrEmpty(queryParam.get("wrtBeginDate")))) {
			wrtBeginDate = new Date(queryParam.get("wrtBeginDate"));
		}
		if ((!StringUtils.isNullOrEmpty(createDate))) {
			aOpenDays = (int) ((createDate.getTime() - wrtBeginDate.getTime()) / 3600000 / 24);
		}
		if ((aOpenDays < 1) || (aOpenDays > 9999)) {
			aOpenDays = 3;
		}
		List<Map> monitorWarrantyDateList = null;
		StringBuffer monitorWarrantyDate = new StringBuffer();
		monitorWarrantyDate.append("SELECT '监控保修起始日期' Flag,AA.MESSAGE,AA.MONITOR_ID,AA.IS_VALID FROM ("
				+ CommonConstants.VT_MONITOR_WARRANTY_DATE + ") AA ");
		monitorWarrantyDate
				.append("WHERE d_key = ? and AA.DEALER_CODE =? AND (?  between AA.OPEN_DAYS and AA.CLOSE_DAYS)  ");
		monitorWarrantyDate.append(" and AA.REPAIR_ORDER_TAG=?");
		monitorWarrantyDate.append(" and ? between AA.begin_date and AA.end_date ");
		monitorWarrantyDate.append(" and AA.IS_VALID=? ");
		List<Object> params4 = new ArrayList<Object>();
		params4.add(DictCodeConstants.D_KEY);
		params4.add(dealerCode);
		params4.add(aOpenDays);
		params4.add(DictCodeConstants.DICT_IS_YES);
		params4.add(new Timestamp(System.currentTimeMillis()));
		params4.add(DictCodeConstants.DICT_IS_YES);
		monitorWarrantyDateList = DAOUtil.findAll(monitorWarrantyDate.toString(), params4);

		// 监控行驶里程
		List<Map> monitorMileageList = null;
		StringBuffer monitorMileage = new StringBuffer();
		monitorMileage.append("SELECT '监控行驶里程' Flag,AA.MESSAGE,AA.MONITOR_ID,AA.IS_VALID FROM ("
				+ CommonConstants.VT_MONITOR_MILEAGE + ") AA ");
		monitorMileage.append(
				" WHERE (? between AA.BEGIN_MILEAGE and AA.END_MILEAGE) and AA.DEALER_CODE =? AND AA.D_KEY = ? ");
		monitorMileage.append(" and AA.REPAIR_ORDER_TAG=?");
		monitorMileage.append(" and ? between AA.begin_date and AA.end_date ");
		monitorMileage.append(" and AA.IS_VALID=? ");
		List<Object> params5 = new ArrayList<Object>();
		params5.add(queryParam.get("OPEN_DAYS"));
		params5.add(dealerCode);
		params5.add(DictCodeConstants.D_KEY);
		params5.add(DictCodeConstants.DICT_IS_YES);
		params5.add(new Timestamp(System.currentTimeMillis()));
		params5.add(DictCodeConstants.DICT_IS_YES);
		monitorMileageList = DAOUtil.findAll(monitorMileage.toString(), params5);

		// 监控工单号
		List monitorRoList = null;
		StringBuffer monitorRo = new StringBuffer();
		if ((!StringUtils.isNullOrEmpty(queryParam.get("RO_NO")))) {
			String roNO = queryParam.get("RO_NO").toString();
			monitorRo.append("SELECT '监控工单' Flag,AA.MESSAGE,AA.MONITOR_ID,AA.IS_VALID FROM ("
					+ CommonConstants.VT_MONITOR_RO + ") AA ");
			monitorRo.append("WHERE AA.d_key = ? AND AA.DEALER_CODE =?  ");
			monitorRo.append(" and last_number=SubStr(?,?-Length(Last_number)+1,Length(Last_number))");
			monitorRo.append(" and AA.REPAIR_ORDER_TAG=?");
			monitorRo.append(" and ? between AA.begin_date and AA.end_date ");
			monitorRo.append(" and AA.IS_VALID=? ");
			List<Object> params6 = new ArrayList<Object>();
			params6.add(DictCodeConstants.D_KEY);
			params6.add(dealerCode);
			params6.add(roNO);
			params6.add(roNO.length());
			params6.add(DictCodeConstants.DICT_IS_YES);
			params6.add(new Timestamp(System.currentTimeMillis()));
			params6.add(DictCodeConstants.DICT_IS_YES);
			monitorRoList = DAOUtil.findAll(monitorRo.toString(), params6);
		}

		// 车辆欠款
		List vehicleList = null;
		StringBuffer vehicle = new StringBuffer();
		vehicle.append(" SELECT AA.ARREARAGE_AMOUNT MESSAGE FROM (" + CommonConstants.VM_VEHICLE + ") AA WHERE ");
		vehicle.append(" AA.DEALER_CODE = ? AND AA.LICENSE = ? AND AA.VIN = ? AND AA.ARREARAGE_AMOUNT <> 0");

		List<Object> params7 = new ArrayList<Object>();
		params7.add(dealerCode);
		params7.add(queryParam.get("license"));
		params7.add(queryParam.get("vin"));
		vehicleList = DAOUtil.findAll(vehicle.toString(), params7);

		// 车主欠款
		List ownerList = null;
		StringBuffer owner = new StringBuffer();
		owner.append("SELECT A.ARREARAGE_AMOUNT MESSAGE FROM (" + CommonConstants.VM_OWNER + ") A ");
		owner.append(" LEFT JOIN (" + CommonConstants.VM_VEHICLE
				+ ") B ON A.DEALER_CODE = B.DEALER_CODE AND A.OWNER_NO = B.OWNER_NO ");
		owner.append(" WHERE A.DEALER_CODE = ? AND B.LICENSE = ? AND B.VIN = ? AND A.ARREARAGE_AMOUNT <> 0");
		List<Object> params8 = new ArrayList<Object>();
		params8.add(dealerCode);
		params8.add(queryParam.get("license"));
		params8.add(queryParam.get("vin"));
		ownerList = DAOUtil.findAll(owner.toString(), params8);

		// XXX天未进场提醒,是否接受回访,保险到期提醒
		String insDate = null;
		int days = -1;
		int isTrace = 0;
		if ((!StringUtils.isNullOrEmpty(queryParam.get("vin")))) {

			List<Map> allMonitorVehicle = new LinkedList();
			StringBuilder sql1111 = new StringBuilder("SELECT A.* FROM  tm_vehicle  A WHERE A.DEALER_CODE = ?  ");
			List<Object> queryList111 = new ArrayList<Object>();
			queryList111.add(dealerCode);
			if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {// 车系
				sql.append(" and A.VIN = ?");
				queryList111.add(queryParam.get("vin"));
			}
			allMonitorVehicle = DAOUtil.findAll(sql1111.toString(), queryList111);
			allMonitorVehicle = Utility.getVehicleSubclassList(dealerCode, allMonitorVehicle);
			insDate = queryByInsVin(queryParam.get("vin"), dealerCode);
			if (allMonitorVehicle.size() > 0) {
				Map vpo = allMonitorVehicle.get(0);
				isTrace = (int) vpo.get("IS_TRACE");
				Date lastInDate = (Date) vpo.get("LAST_MAINTAIN_DATE");
				if (lastInDate != null) {
					days = getDaysBetween(lastInDate, new Date());
				}
			}
		}
		// 车主是否绑定微信
		int isBinding = 0;
		if ((!StringUtils.isNullOrEmpty(queryParam.get("vin")))) {
			List<Map> allMonitorVehicle = new LinkedList();
			StringBuilder sql1111 = new StringBuilder("SELECT A.* FROM  tm_vehicle  A WHERE  A.DEALER_CODE = ?  ");
			List<Object> queryList111 = new ArrayList<Object>();
			queryList111.add(dealerCode);
			if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {// 车系
				sql.append(" and A.VIN = ?");
				queryList111.add(queryParam.get("vin"));
			}
			allMonitorVehicle = DAOUtil.findAll(sql1111.toString(), queryList111);
			allMonitorVehicle = Utility.getVehicleSubclassList(dealerCode, allMonitorVehicle);
			if (allMonitorVehicle.size() > 0) {
				Map vpo = allMonitorVehicle.get(0);
				isBinding = (int) vpo.get("IS_BINDING");
			}
		}

		// 建议维修项目、配件
		List suggestMaintainLabourList = null;
		if ((!StringUtils.isNullOrEmpty(queryParam.get("vin")))) {
			StringBuffer suggestMaintainLabour = new StringBuffer();
			suggestMaintainLabour.append(
					"select SUGGEST_MAINTAIN_LABOUR_ID, DEALER_CODE, VIN, RO_NO, LABOUR_CODE, LABOUR_NAME, STD_LABOUR_HOUR, LABOUR_PRICE, LABOUR_AMOUNT, REASON, ");
			suggestMaintainLabour.append(
					" SUGGEST_DATE, IS_VALID, REMARK, D_KEY, VER,MODEL_LABOUR_CODE from TT_SUGGEST_MAINTAIN_LABOUR where DEALER_CODE= ? ");
			suggestMaintainLabour.append("  and d_key = ?  AND IS_VALID = ?   AND VIN =? ");
			List<Object> params9 = new ArrayList<Object>();
			params9.add(dealerCode);
			params9.add(DictCodeConstants.D_KEY);
			params9.add(DictCodeConstants.DICT_IS_YES);
			params9.add(queryParam.get("vin"));
			suggestMaintainLabourList = DAOUtil.findAll(suggestMaintainLabour.toString(), params9);
		}

		// 车主是否在本店投保
		int isInsProposal = 0;
		if ((!StringUtils.isNullOrEmpty(queryParam.get("vin")))) {

			List<Map> allProposalq = new LinkedList();
			StringBuilder sql1111 = new StringBuilder(
					"SELECT A.* FROM  Tm_Ins_Proposal  A WHERE A.DEALER_CODE = ?   and A.FORM_STATUS= ? ");
			List<Object> queryList111 = new ArrayList<Object>();
			queryList111.add(dealerCode);
			queryList111.add(DictCodeConstants.DICT_INS_FORMS_STATUS_CLOSED);
			if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {// 车系
				sql1111.append(" and A.VIN = ?");
				queryList111.add(queryParam.get("vin"));
			}
			allProposalq = DAOUtil.findAll(sql1111.toString(), queryList111);
			allProposalq = Utility.getVehicleSubclassList(dealerCode, allProposalq);
			if (allProposalq != null && allProposalq.size() > 0) {
				isInsProposal = Integer.parseInt(CommonConstants.DICT_IS_YES);
			}
		}

		// 建议维修配件
		List suggestMaintainPartList = null;
		if ((!StringUtils.isNullOrEmpty(queryParam.get("vin")))) {
			StringBuffer suggestMaintainPart = new StringBuffer();
			suggestMaintainPart.append(
					"select SUGGEST_MAINTAIN_PART_ID, DEALER_CODE, VIN, RO_NO, PART_NO, PART_NAME, SUGGEST_DATE, SALES_PRICE, QUANTITY, AMOUNT, REASON, IS_VALID, REMARK, ");
			suggestMaintainPart.append(" D_KEY, VER  from TT_SUGGEST_MAINTAIN_PART where   DEALER_CODE= ? ");
			suggestMaintainPart.append("  and d_key = ?  AND IS_VALID = ?   AND VIN =? ");
			List<Object> params10 = new ArrayList<Object>();
			params10.add(dealerCode);
			params10.add(DictCodeConstants.D_KEY);
			params10.add(DictCodeConstants.DICT_IS_YES);
			params10.add(queryParam.get("vin"));
			suggestMaintainPartList = DAOUtil.findAll(suggestMaintainPart.toString(), params10);
		}

		// 特殊保修
		List specialWarrantyList = null;
		if ((!StringUtils.isNullOrEmpty(queryParam.get("vin")))) {
			StringBuffer specialWarranty = new StringBuffer();
			specialWarranty.append(
					"SELECT VIN,BEGIN_DATE,END_DATE,special_warranty_type as TYPE,GIVE_TIMES,USED_TIMES,remark FROM tm_special_warranty  ");
			specialWarranty.append(" where   DEALER_CODE= ?     AND VIN =? ");
			List<Object> params11 = new ArrayList<Object>();
			params11.add(dealerCode);
			params11.add(queryParam.get("vin"));
			specialWarrantyList = DAOUtil.findAll(specialWarranty.toString(), params11);
		}

		// 最近一次回访结果
		List traceTaskList = null;
		if ((!StringUtils.isNullOrEmpty(queryParam.get("vin")))) {
			StringBuffer traceTask = new StringBuffer();
			traceTask.append(
					"	SELECT IS_SELF_COMPANY,SALES_AGENT_NAME,RO_NO,RO_CREATE_DATE,DELIVERY_DATE,OWNER_NO,VIN,LICENSE,BRAND,SERIES,MODEL,COLOR,OWNER_NAME,DELIVERER, ");
			traceTask.append(
					" DELIVERER_PHONE,TRANCER,REMARK,OPINION,INPUTER,INPUT_DATE,TRACE_STATUS,CHAR_DELIVERY_DATE,CYCLE_NO,RO_TYPE,REPAIR_TYPE_CODE,RO_CHARGE_TYPE,BALANCE_NO, ");
			traceTask.append(
					" SERVICE_ADVISOR,DELIVERER_MOBILE,TRACE_GROUP_ID,IN_MILEAGE,CHIEF_TECHNICIAN,IS_ACTIVITY,BOOKING_ORDER_NO,TASK_REMARK,SALES_DATE,ADDRESS,is_customer_in_asc,VER,IS_TRACE_VEHICLE, ");
			traceTask.append(
					" TRACE_COUNT,DELIVERER_GENDER,NEXT_REPAIR_REMIND,TRACE_TIME,TECHNICIAN_LIST,LABOUR_UNITE, IS_BOOKING_CUSTOMER,IS_CSI FROM ( SELECT VE.IS_SELF_COMPANY,VE.SALES_AGENT_NAME,a.RO_NO, ");
			traceTask.append("  a.RO_CREATE_DATE,  a.DELIVERY_DATE,  TRO.OWNER_NO, ");
			traceTask.append(
					" a.VIN,  a.LICENSE,  a.BRAND, a.SERIES,  a.MODEL,  A.COLOR, a.OWNER_NAME,  a.DELIVERER,  a.DELIVERER_PHONE, A.TRANCER,  ");
			traceTask.append(
					" a.REMARK,  OPINION,  A.INPUTER,  case when a.INPUT_DATE is null then '1899-1-1 00:00:00' else a.INPUT_DATE end INPUT_DATE,  ");
			traceTask.append(" A.TRACE_STATUS, a.CHAR_DELIVERY_DATE, a.CYCLE_NO, a.RO_TYPE,  a.REPAIR_TYPE_CODE,  ");
			traceTask.append(
					" a.RO_CHARGE_TYPE, a.BALANCE_NO,  A.SERVICE_ADVISOR, A.DELIVERER_MOBILE,A.TRACE_GROUP_ID, TRO.IN_MILEAGE,TRO.CHIEF_TECHNICIAN, ");
			traceTask.append(
					" TRO.IS_ACTIVITY , TRO.BOOKING_ORDER_NO, A.TASK_REMARK,VE.SALES_DATE, ORR.ADDRESS,tro.is_customer_in_asc, A.VER, ");
			traceTask.append(
					" VE.IS_TRACE AS IS_TRACE_VEHICLE, TRACE_COUNT.TRACE_COUNT,  A.DELIVERER_GENDER,  A.NEXT_REPAIR_REMIND,(CASE WHEN TRO.IS_TRACE = 12781001 ");
			traceTask.append(
					" THEN  TRO.TRACE_TIME ELSE NULL END) AS TRACE_TIME ,TECH.TECHNICIAN AS TECHNICIAN_LIST,TECH.LABOUR_NAME AS LABOUR_UNITE ,");
			traceTask.append(
					" (CASE WHEN (TRO.BOOKING_ORDER_NO IS NOT NULL AND TRO.BOOKING_ORDER_NO !='') THEN  12781001 ELSE 12781002 END) AS IS_BOOKING_CUSTOMER, ");
			traceTask.append(
					" CASE WHEN M.VIN IS NOT NULL THEN 12781001 ELSE 12781002 END IS_CSI  FROM   TT_TRACE_TASK A  ");
			traceTask
					.append(" LEFT JOIN TT_REPAIR_ORDER  TRO ON A.DEALER_CODE = TRO.DEALER_CODE AND TRO.RO_NO = A.RO_NO LEFT JOIN ("
							+ CommonConstants.VM_VEHICLE + ") VE ON VE.VIN=A.VIN AND VE.DEALER_CODE=A.DEALER_CODE ");
			traceTask.append(
					" LEFT JOIN Tt_Technician_I TECH ON TECH.DEALER_CODE=TRO.DEALER_CODE AND TECH.RO_NO=TRO.RO_NO AND TECH.D_KEY= ? ");
			traceTask.append(" LEFT JOIN (" + CommonConstants.VM_OWNER
					+ ")  ORR ON A.DEALER_CODE = ORR.DEALER_CODE AND TRO.OWNER_NO = ORR.OWNER_NO LEFT JOIN (   SELECT DEALER_CODE,RO_NO,D_KEY,COUNT(1) AS TRACE_COUNT FROM TT_TRACE_TASK_LOG GROUP BY DEALER_CODE,RO_NO,D_KEY  ");
			traceTask.append(
					" ) TRACE_COUNT ON TRACE_COUNT.DEALER_CODE = A.DEALER_CODE AND TRACE_COUNT.RO_NO = A.RO_NO AND TRACE_COUNT.D_KEY = A.D_KEY ");
			traceTask.append(
					"  LEFT JOIN (SELECT DISTINCT CQV1.VIN FROM TM_VEHICLE_MESSAGE CQV1 LEFT JOIN TM_VEHICLE_ACTIVITY_MESSAGE CQ1 ON CQV1.ACTIVITY_CODE = CQ1.ACTIVITY_CODE  ");
			traceTask.append(
					"  AND CQV1.DEALER_CODE = CQ1.DEALER_CODE WHERE CURRENT TIMESTAMP BETWEEN CQ1.BEGIN_DATE AND CQ1.END_DATE) M ON A.VIN=M.VIN  ");
			traceTask.append(
					"  WHERE A.D_KEY =? AND A.DEALER_CODE = ?  AND NOT EXISTS( SELECT 1 FROM TT_REPAIR_ORDER TROR WHERE TROR.DEALER_CODE = A.DEALER_CODE AND TROR.RO_NO = A.RO_NO AND TROR.RO_TYPE= ? AND TROR.IS_CUSTOMER_IN_ASC =? AND TROR.RO_STATUS =? )");
			Utility.getLikeCond("A", "LICENSE", license, "AND");
			Utility.getLikeCond("VE", "LICENSE", license, "AND");
			Utility.getLikeCond("VE", "VIN", vin, "AND");
			Utility.getLikeCond("A", "VIN", vin, "AND");
			traceTask.append("  ORDER BY A.DELIVERY_DATE ) SA  ");

			List<Object> params11 = new ArrayList<Object>();
			params11.add(DictCodeConstants.D_KEY);
			params11.add(DictCodeConstants.D_KEY);
			params11.add(dealerCode);
			params11.add(DictCodeConstants.DICT_RPT_REPAIR);
			params11.add(DictCodeConstants.DICT_IS_NO);
			params11.add(DictCodeConstants.DICT_RO_STATUS_TYPE_ON_REPAIR);
			specialWarrantyList = DAOUtil.findAll(traceTask.toString(), params11);
		}

		// 代金券
		List vouchersTaskList = null;
		List couponList = null;

		if ((!StringUtils.isNullOrEmpty(queryParam.get("vin")))) {
			String today = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			StringBuffer vouchersTask = new StringBuffer();
			vouchersTask.append("SELECT VIN,BEGIN_DATE,END_DATE,AMOUNT,REMARK FROM TM_ACTIVITY_VOUCHERS   ");
			vouchersTask.append(" where   DEALER_CODE= ?    AND IS_VALID = ?  AND VIN =? ");
			Utility.getDateConds("", "BEGIN_DATE", today, "<=");
			Utility.getDateConds("", "END_DATE", today, ">=");
			List<Object> params12 = new ArrayList<Object>();
			params12.add(dealerCode);
			params12.add(DictCodeConstants.DICT_IS_YES);
			params12.add(queryParam.get("vin"));
			specialWarrantyList = DAOUtil.findAll(vouchersTask.toString(), params12);
		}

		if ((!StringUtils.isNullOrEmpty(queryParam.get("vin")))) {
			String roNO = queryParam.get("RO_NO").toString();
			StringBuffer coupon = new StringBuffer();
			coupon.append(
					"select a.card_no from TM_VEHICLE_INSURANCE_COUPON A left join TT_REPAIR_ORDER B ON A.DEALER_CODE=B.DEALER_CODE AND ");
			coupon.append(
					" A.REPAIR_TYPE_CODE=B.REPAIR_TYPE_CODE and a.vin=b.vin WHERE A.DEALER_CODE =?  AND A.VIN=? AND A.USE_STATUS=? and b.ro_No=? and  A.START_DATE< CURRENT TIMESTAMP AND CURRENT TIMESTAMP<A.END_DATE ");
			coupon.append(" union all ");
			coupon.append("select a.card_no from  TM_WECHAT_BOOKING_CARD_MESSAGE a ");
			coupon.append(
					" LEFT JOIN Tt_Booking_Order C ON A.DEALER_CODE=C.DEALER_CODE AND A.RESERVE_ID=C.RESERVATION_ID");
			coupon.append(
					" left join tt_repair_order b on C.DEALER_CODE=b.DEALER_CODE and C.booking_order_no=b.booking_order_no ");
			coupon.append(" where b.ro_no=? and  a.DEALER_CODE=?  and b.booking_order_no is not null");

			List<Object> params12 = new ArrayList<Object>();
			params12.add(dealerCode);
			params12.add(queryParam.get("vin"));
			params12.add(DictCodeConstants.DICT_OLD_PART_STATUS_ASC_HOLD);
			params12.add(roNO);
			params12.add(roNO);
			params12.add(dealerCode);
			couponList = DAOUtil.findAll(coupon.toString(), params12);
		}

		List monitorAll = new ArrayList();

		List remindAll = new ArrayList();
		// 构造右边
		addToMonitor(monitorAll, monitorVehicleList);
		addToMonitor(monitorAll, monitorWarrantyDateList);
		addToMonitor(monitorAll, monitorMileageList);
		addToMonitor(monitorAll, monitorRoList);
		// 提醒，界面左边那块
		addArrearageToRemind(remindAll, vehicleList, ownerList); // 欠款
		addVehicleNotInToRemind(remindAll, days); // XXX天未进场提醒
		addIsTraceToRemind(remindAll, isTrace); // 是否接受回访
		addIsBindingToRemind(remindAll, isBinding);// 是否绑定微信
		addIsInsProposal(remindAll, isInsProposal);// 是否有保单信息
		addTraceTaskToRemind(remindAll, traceTaskList); // 最近一次回访结果
		addInsDateToRemind(remindAll, insDate); // 保险到期提醒
		addSpecialToRemind(remindAll, specialWarrantyList); // 特殊保修
		addVourchersToRemind(remindAll, vouchersTaskList);
		addSuggestToRemind(remindAll, suggestMaintainLabourList, suggestMaintainPartList); // 建议维修项目和配件
		addCouponRemind(remindAll, couponList); // 最近一次回访结果
		/* return monitorAll; */
		return remindAll;

	}

	private void addArrearageToRemind(List remindAll, List vehicleList, List ownerList) {
		String message = "";
		if (ownerList != null && ownerList.size() > 0) {
			List<Map> dynaBean = (List<Map>) ownerList.get(0);
			message += "车主欠款:[" + dynaBean.get(0).get("MESSAGE") + "]";
			if (vehicleList != null && vehicleList.size() > 0) {
				List<Map> dynaBean2 = (List<Map>) vehicleList.get(0);
				message += "，其中该车欠款:[" + dynaBean2.get(0).get("MESSAGE") + "]";
			}
		} else if (vehicleList != null && vehicleList.size() > 0) {
			List<Map> dynaBean = (List<Map>) vehicleList.get(0);
			message += "该车欠款:[" + dynaBean.get(0).get("MESSAGE") + "]";
		}
		if (!"".equals(message)) {
			addToRemind(remindAll, message);
		}
	}

	private void addVehicleNotInToRemind(List remindAll, int days) {
		String message = "";
		if (days > 90) {
			message += "该车已经" + days + "天未进厂！";
		} else if (days == -1) {
			message += "该车首次进厂！";
		}
		if (!"".equals(message)) {
			addToRemind(remindAll, message);
		}
	}

	private void addIsBindingToRemind(List remindAll, int isBinding) {
		String message = "";
		if (isBinding == Integer.parseInt(DictCodeConstants.DICT_IS_YES)) {
			message += "已绑定微信！";
		} else {
			message += "未绑定微信！";
		}
		if (!"".equals(message)) {
			addToRemind(remindAll, message);
		}
	}

	private void addIsTraceToRemind(List remindAll, int isTrace) {
		String message = "";
		if (isTrace == Integer.parseInt(DictCodeConstants.DICT_IS_NO)) {
			message += "车主不接受回访！";
		}
		if (!"".equals(message)) {
			addToRemind(remindAll, message);
		}
	}

	private void addIsInsProposal(List remindAll, int isBinding) {
		String message = "";
		if (isBinding == Integer.parseInt(DictCodeConstants.DICT_IS_YES)) {
			message += "已有保单信息！";
		} else {
			message += "无保单信息 ！";
		}
		if (!"".equals(message)) {
			addToRemind(remindAll, message);
		}

	}

	private void addTraceTaskToRemind(List remindAll, List traceTaskList) {
		String message = "";
		if (traceTaskList != null && traceTaskList.size() > 0) {
			Date inputDate = null;
			String nextRepairRemind = null;
			for (int i = 0; i < traceTaskList.size(); i++) {
				List<Map> dynaBean = (List<Map>) traceTaskList.get(i);
				if (inputDate == null || inputDate.before((Date) dynaBean.get(0).get("INPUT_DATE"))) {
					inputDate = (Date) dynaBean.get(0).get("INPUT_DATE");
					nextRepairRemind = dynaBean.get(0).get("NEXT_REPAIR_REMIND").toString();
				}
			}
			if (nextRepairRemind != null && !"".equals(nextRepairRemind.trim())) {
				message += "最近一次回访结果:" + nextRepairRemind;
			}
		}
		if (!"".equals(message)) {
			addToRemind(remindAll, message);
		}
	}

	private void addCouponRemind(List remindAll, List couponList) {
		String message = "";
		if (couponList != null && couponList.size() > 0) {
			message += "该车已有可以使用的优惠券";
			if (!"".equals(message)) {
				addToRemind(remindAll, message);
			}
		}
	}

	private void addInsDateToRemind(List remindAll, String insDate) throws Exception {
		String message = "";
		if (insDate != null && insDate.length() > 9) {
			insDate = insDate.substring(0, 10);
			Date date = new SimpleDateFormat("yyyy-MM-dd").parse(insDate);
			int days = getDaysBetween(new Date(), date);
			if (days > 0 && days < 91) {
				message += "注意：该车主保险到期日期为:" + insDate;
			}
		}
		if (!"".equals(message)) {
			addToRemind(remindAll, message);
		}
	}

	private void addSpecialToRemind(List remindAll, List specialWarranty) {
		String message = "";
		if (specialWarranty != null && specialWarranty.size() > 0) {
			for (int i = 0; i < specialWarranty.size(); i++) {
				List<Map> dynaBean = (List<Map>) specialWarranty.get(i);
				int type = (int) dynaBean.get(0).get("TYPE");
				if (type == Integer.parseInt(DictCodeConstants.DICT_ESPECIAL_MAINTAIN_TERM)) {
					message += "延长保修：" + dynaBean.get(0).get("REMARK");
				} else if (type == Integer.parseInt(DictCodeConstants.DICT_LARGESS_MAINTAIN)) {
					message += "赠送的保养次数：" + dynaBean.get(0).get("GIVE_TIMES") + "  已使用的保养次数："
							+ dynaBean.get(0).get("USED_TIMES");
				} else if (type == Integer.parseInt(DictCodeConstants.DICT_CANCEL_THREE_CONTRACT)) {
					message += "取消三包索赔：" + dynaBean.get(0).get("REMARK");
				} else if (type == Integer.parseInt(DictCodeConstants.DICT_CANCEL_PURCHASE_MAINTENANCE)) {
					message += "购买保养：" + dynaBean.get(0).get("GIVE_TIMES") + "  已使用的保养次数："
							+ dynaBean.get(0).get("USED_TIMES") + "  " + dynaBean.get(0).get("REMARK");
				}
			}
		}
		if (!"".equals(message)) {
			addToRemind(remindAll, message);
		}
	}

	private void addVourchersToRemind(List remindAll, List vouchersTaskList) {
		String message = "";
		if (vouchersTaskList != null && vouchersTaskList.size() > 0) {
			for (int i = 0; i < vouchersTaskList.size(); i++) {
				List<Map> dynaBean = (List<Map>) vouchersTaskList.get(i);
				message += "您有" + dynaBean.get(0).get("AMOUNT") + "元代金卷可使用";
			}
		}
		if (!"".equals(message)) {
			addToRemind(remindAll, message);
		}
	}

	private void addSuggestToRemind(List remindAll, List suggestMaintainLabourList, List suggestMaintainPartList) {
		String message = "";
		if (suggestMaintainLabourList != null && suggestMaintainLabourList.size() > 0) {
			message += "建议维修项目：";
			for (int i = 0; i < suggestMaintainLabourList.size(); i++) {
				List<Map> dynaBean = (List<Map>) suggestMaintainLabourList.get(i);
				message += dynaBean.get(0).get("LABOUR_NAME");
				if (i != suggestMaintainLabourList.size() - 1) {
					message += "，";
				}
			}
		}
		if (!"".equals(message)) {
			addToRemind(remindAll, message);
		}
		message = "";
		if (suggestMaintainPartList != null && suggestMaintainPartList.size() > 0) {
			message += "建议配件：";
			for (int i = 0; i < suggestMaintainPartList.size(); i++) {
				List<Map> dynaBean = (List<Map>) suggestMaintainPartList.get(i);
				message += dynaBean.get(0).get("PART_NAME");
				if (i != suggestMaintainPartList.size() - 1) {
					message += "，";
				}
			}
		}
		if (!"".equals(message)) {
			addToRemind(remindAll, message);
		}
	}

	private void addToMonitor(List monitorAll, List list) throws Exception {
		if (list == null || list.size() == 0) {
			return;
		}
		for (int i = 0; i < list.size(); i++) {
			List<Map> dynaBean2 = (List<Map>) list.get(i);
			Map<String, Object> dynaBean = new HashMap<String, Object>();
			dynaBean.put("MESSAGE", dynaBean2.get(0).get("MESSAGE"));
			dynaBean.put("MONITOR_ID", dynaBean2.get(0).get("MONITOR_ID"));
			dynaBean.put("IS_VALID", dynaBean2.get(0).get("IS_VALID"));
			dynaBean.put("FLAG", dynaBean2.get(0).get("FLAG"));
			monitorAll.add(dynaBean);
		}
	}

	private void addToRemind(List remindAll, String message) {
		int i = remindAll.size() + 1;
		Map<String, Object> dynaBean = new HashMap<String, Object>();
		dynaBean.put("REMIND_ID", i);
		dynaBean.put("MESSAGE", message);
		remindAll.add(dynaBean);
	}

	private int getDaysBetween(Date lastInDate, Date date) {
		long milliseconds = date.getTime() - lastInDate.getTime();
		int dayMillisecond = 1000 * 60 * 60 * 24;
		int days = (int) (milliseconds % dayMillisecond == 0 ? milliseconds / dayMillisecond
				: milliseconds / dayMillisecond + 1);
		return days;
	}

	/**
	 * 查询该vin的车 保险到期日期
	 * 
	 * @param ZHL
	 * @return
	 * @throws Exception
	 */

	private String queryByInsVin(String vin, String dealerCode) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT max(s) as END_DATE FROM (select max(END_DATE) as s from TM_INS_PROPOSAL_LIST a  ");
		sql.append(
				" left join  TM_INS_PROPOSAL b on a.PROPOSAL_CODE = b.PROPOSAL_CODE and a.DEALER_CODE = b.DEALER_CODE");
		sql.append(" where b.vin =? AND a.DEALER_CODE = ?  and b.FORM_STATUS= ?)");
		List<Object> params = new ArrayList<Object>();
		params.add(vin);
		params.add(dealerCode);
		params.add(DictCodeConstants.DICT_INS_FORMS_STATUS_CLOSED);
		String endDate = null;
		if (DAOUtil.findAll(sql.toString(), params).size() > 0) {
			endDate = DAOUtil.findAll(sql.toString(), params).get(0).get("END_DATE").toString();
		}
		return endDate;
	}

	/**
	 * 
	 * 功能描述：比较字符串
	 * 
	 * @param str1
	 * @param str2
	 * 
	 */
	public boolean strsame(String str1, String str2) throws Exception {
		String strtemp = "";
		if ((str1 == null) || (str2 == null))
			return false;
		if ((str1.length() == 0) || (str2.length() == 0)) {
			return false;
		}
		int j = str2.length();
		if (j > str1.length())
			j = str1.length();
		for (int i = 0; i < j; i++) {
			strtemp = str1.substring(i, i + 1);
			if (!"_".equals(strtemp)) {
				if (!strtemp.equals(str2.substring(i, i + 1)))
					return false;
			}
		}
		return true;

	}

	@Override
	public PageInfoDto queryBookingOrder(Map<String, String> queryParam) throws Exception {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append(
				"SELECT VIN,DEALER_CODE,  BOOKING_ORDER_NO, CONTACTOR_NAME,  BOOKING_TYPE_CODE,BOOKING_ORDER_STATUS,RO_TROUBLE_DESC,"); // add
																																		// by
																																		// sf
																																		// 2010-08-16
																																		// RO_TROUBLE_DESC
		sql.append(
				"BOOKING_COME_TIME,LOCK_USER,CREATED_AT ,REMARK,CHIEF_TECHNICIAN,RECOMMEND_EMP_NAME,RECOMMEND_CUSTOMER_NAME  FROM TT_BOOKING_ORDER  ");
		sql.append("WHERE DEALER_CODE=? AND D_KEY=?");
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		params.add(dealerCode);
		params.add(DictCodeConstants.D_KEY);
		if ((!StringUtils.isNullOrEmpty(queryParam.get("vin")))
				|| (!StringUtils.isNullOrEmpty(queryParam.get("LICENSE")))) {
			sql.append(" AND(");
			if ((!StringUtils.isNullOrEmpty(queryParam.get("vin")))) {
				sql.append("  VIN=?  or ");
				params.add(queryParam.get("vin"));
			}
			if ((!StringUtils.isNullOrEmpty(queryParam.get("LICENSE")))
					&& !DictCodeConstants.CON_LICENSE_NULL.equals(queryParam.get("LICENSE"))) {
				sql.append("  LICENSE=? )");
				params.add(queryParam.get("LICENSE"));
			}
		}
		sql.append(" AND BOOKING_ORDER_STATUS=?");
		params.add(DictCodeConstants.DICT_BOS_NOT_ENTER);
		PageInfoDto pageInfoDto = DAOUtil.pageQuery(sql.toString(), params);
		return pageInfoDto;

	}

	@Override
	public List<Map> queryActivityValid(Map<String, String> queryParam) throws Exception {
		StringBuffer sql = new StringBuffer();
		List list = null;
		String vinSix = null, vin = null, model = null, license = null, brand = null, series = null, configCode = null,
				salesdate = null, mileage = null, cardTypeCode = null, isRecallActivity = null;
		if ((!StringUtils.isNullOrEmpty(queryParam.get("vin")))) {
			vin = queryParam.get("vin").toString();
		}
		if ((!StringUtils.isNullOrEmpty(queryParam.get("MODEL")))) {
			model = queryParam.get("MODEL").toString();
		}
		if ((!StringUtils.isNullOrEmpty(queryParam.get("LICENSE")))) {
			license = queryParam.get("LICENSE").toString();
		}
		if ((!StringUtils.isNullOrEmpty(queryParam.get("BRAND")))) {
			brand = queryParam.get("BRAND").toString();
		}
		if ((!StringUtils.isNullOrEmpty(queryParam.get("SERIES")))) {
			series = queryParam.get("SERIES").toString();
		}
		if ((!StringUtils.isNullOrEmpty(queryParam.get("CONFIG_CODE")))) {
			configCode = queryParam.get("CONFIG_CODE").toString();
		}
		if ((!StringUtils.isNullOrEmpty(queryParam.get("SALES_DATE")))) {
			salesdate = queryParam.get("SALES_DATE").toString();
		}
		if ((!StringUtils.isNullOrEmpty(queryParam.get("MILEAGE")))) {
			mileage = queryParam.get("MILEAGE").toString();
		}
		if ((!StringUtils.isNullOrEmpty(queryParam.get("CARD_TYPE_CODE")))) {
			cardTypeCode = queryParam.get("CARD_TYPE_CODE").toString();
		}
		if ((!StringUtils.isNullOrEmpty(queryParam.get("IS_RECALL_ACTIVITY")))) {
			isRecallActivity = queryParam.get("IS_RECALL_ACTIVITY").toString();
		}

		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		if ((!StringUtils.isNullOrEmpty(queryParam.get("vin")))) {
			if (vin.length() == 17) {
				vinSix = vin.substring(11, 17);
			}
		}
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		String groupCodeVehicle = Utility.getGroupEntity(loginInfo.getDealerCode(), "TM_VEHICLE");
		List listVehicle = null;
		if ((!StringUtils.isNullOrEmpty(queryParam.get("vin")))) {
			StringBuffer Vehicle = new StringBuffer();
			Vehicle.append("SELECT DEALER_CODE,1 FROM  (" + CommonConstants.VT_ACTIVITY_VEHICLE
					+ ") AA  WHERE AA.DEALER_CODE = ? and AA.D_Key=?");
			Vehicle.append(" and aa.vin=?");
			Vehicle.append("  LIMIT 1 ");
			List<Object> params = new ArrayList<Object>();
			params.add(dealerCode);
			params.add(DictCodeConstants.D_KEY);
			params.add(queryParam.get("vin"));
			listVehicle = DAOUtil.findAll(Vehicle.toString(), params);
		}

		if (listVehicle != null && listVehicle.size() > 0) {
			StringBuffer sql2 = new StringBuffer();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String sqlSalesdate = "";
			if (salesdate != null && !salesdate.equals("")) {
				Date date = formatter.parse(salesdate);
				String dateString = formatter.format(date);
				sqlSalesdate = " or (SALES_DATE_BEGIN<='" + dateString + " 00:00:00' and SALES_DATE_END>='" + dateString
						+ " 00:00:00') ";
			}
			sql.append("select a.IS_CLAIM,");
			sql.append(
					" a.IS_REPEAT_ATTEND,a.IS_PART_ACTIVITY,A.ACTIVITY_KIND,A.PARTS_IS_MODIFY,a.IS_RECALL_ACTIVITY,a.DEALER_CODE, a.ACTIVITY_CODE, a.ACTIVITY_NAME, a.ACTIVITY_TYPE, a.BEGIN_DATE, a.END_DATE, a.RELEASE_DATE,  ");
			sql.append(
					"a.RELEASE_TAG, a.LABOUR_AMOUNT, a.ADD_ITEM_AMOUNT,a.FROM_ENTITY, a.DOWN_TAG, a.DOWN_TIMESTAMP, a.REPAIR_PART_AMOUNT, a.ACTIVITY_AMOUNT, a.IS_VALID,a.VER , ");
			sql.append(
					"a.BRAND,a.SERIES,a.MODEL,a.MILEAGE_BEGIN,a.MILEAGE_END,a.MEMBER_LEVEL_ALLOWED,'' AS LABOUR_CODE,'' AS PART_CODE, A.ACTIVITY_PROPERTY   from( select distinct ");
			sql.append(
					"a.IS_REPEAT_ATTEND,a.IS_PART_ACTIVITY,A.ACTIVITY_KIND,A.PARTS_IS_MODIFY,a.DEALER_CODE, a.ACTIVITY_CODE, a.ACTIVITY_NAME, a.ACTIVITY_TYPE, a.BEGIN_DATE, a.END_DATE, a.RELEASE_DATE,  ");
			sql.append(
					"a.RELEASE_TAG, a.LABOUR_AMOUNT, a.ADD_ITEM_AMOUNT,a.FROM_ENTITY, a.DOWN_TAG, a.DOWN_TIMESTAMP, a.REPAIR_PART_AMOUNT, a.ACTIVITY_AMOUNT, a.IS_VALID,a.VER , ");
			sql.append(
					"coalesce(a.BRAND,'') as BRAND,a.IS_RECALL_ACTIVITY ,coalesce(a.SERIES,'') as SERIES,coalesce(a.MODEL,'') as MODEL,coalesce(a.MILEAGE_BEGIN,0) as MILEAGE_BEGIN,coalesce(a.MILEAGE_END,0) as MILEAGE_END, ");
			sql.append(
					"a.SALES_DATE_BEGIN,a.SALES_DATE_END,A.MEMBER_LEVEL_ALLOWED,A.MODEL_YEAR,A.MAINTAIN_BEGIN_DAY,A.MAINTAIN_END_DAY,A.CONFIG_CODE, A.ACTIVITY_PROPERTY,A.IS_CLAIM ");
			sql.append("from (" + CommonConstants.VT_ACTIVITY
					+ ") A  where  a.DEALER_CODE=? and IS_PART_ACTIVITY= ? AND RELEASE_TAG = ?  AND IS_VALID =? ");
			sql.append(
					" AND ? BETWEEN BEGIN_DATE AND END_DATE  AND ( a.VEHICLE_PURPOSE is null OR a.VEHICLE_PURPOSE = 0");
			sql.append(" OR   EXISTS ( SELECT 1 FROM (" + CommonConstants.VM_VEHICLE
					+ ") v WHERE VIN = ? AND v.VEHICLE_PURPOSE = a.VEHICLE_PURPOSE ) ) ");
			sql.append(" and ( not exists (select 1 from (" + CommonConstants.VT_ACTIVITY_VEHICLE
					+ ") c where a.DEALER_CODE = c.DEALER_CODE and   a.activity_Code = c.activity_code ");
			sql.append(" and c.DEALER_CODE=?  or exists (select 1 from (" + CommonConstants.VT_ACTIVITY_VEHICLE
					+ ") c where a.DEALER_CODE = c.DEALER_CODE and  a.activity_Code = c.activity_code ");
			sql.append(" and c.DEALER_CODE=? and c.VIN = ?) )");
			sql.append(" and (A.MODEL_YEAR='' or A.MODEL_YEAR IS NULL OR EXISTS(SELECT 1 FROM ("
					+ CommonConstants.VM_VEHICLE + ") V WHERE VIN=? AND V.MODEL_YEAR=A.MODEL_YEAR)) ");
			sql.append(
					" )a where ((BRAND ='' or BRAND=?)and( SERIES ='' or SERIES=? )and( CONFIG_CODE =''  OR CONFIG_CODE IS NULL  or CONFIG_CODE=?)and(MODEL ='' or MODEL=? ')) and (((MILEAGE_BEGIN  =0 and MILEAGE_END  =0) or ( ");
			if (Utility.testString(mileage)) {
				sql.append(" MILEAGE_BEGIN<=" + mileage + " and MILEAGE_END>=" + mileage + " ");
			} else {
				sql.append(" 1=1 ");
			}
			sql.append(" ))");
			sql.append(
					" and (((A.MAINTAIN_BEGIN_DAY IS NULL OR A.MAINTAIN_BEGIN_DAY=0.00) AND (A.MAINTAIN_END_DAY IS NULL OR A.MAINTAIN_END_DAY=0.00)) OR ");
			sql.append(" EXISTS(SELECT 1 FROM (" + CommonConstants.VM_VEHICLE
					+ ") V WHERE VIN=? AND V.SALES_DATE IS NOT NULL AND DAYS(CURRENT DATE)-DAYS(V.SALES_DATE)>=A.MAINTAIN_BEGIN_DAY AND DAYS(CURRENT DATE)-DAYS(V.SALES_DATE)<=A.MAINTAIN_END_DAY))) ");
			sql.append(" and(SALES_DATE_BEGIN is null or SALES_DATE_END is null ");
			sql.append(" ?  ) and ( ( IS_REPEAT_ATTEND=?) or (not exists( select 1 from TT_REPAIR_ORDER AA");
			sql.append(
					" left join TT_RO_LABOUR BB on AA.DEALER_CODE=BB.DEALER_CODE and AA.RO_NO=BB.RO_NO and BB.activity_code<>'' ");
			sql.append(
					"  where bb.ACTIVITY_CODE=a.ACTIVITY_CODE  and AA.DEALER_CODE=? and VIN=? and IS_ACTIVITY =? UNION ALL ");
			sql.append(
					"select 1 from TT_REPAIR_ORDER  AA  left join TT_RO_REPAIR_PART BB on AA.DEALER_CODE=BB.DEALER_CODE and AA.RO_NO=BB.RO_NO and BB.activity_code<>'' ");
			sql.append(
					" where bb.ACTIVITY_CODE=a.ACTIVITY_CODE and  AA.DEALER_CODE=? and VIN=?  and IS_ACTIVITY =?  UNION ALL");
			sql.append(
					" select 1 from TT_REPAIR_ORDER  AA  left join TT_RO_ADD_ITEM BB on AA.DEALER_CODE=BB.DEALER_CODE and AA.RO_NO=BB.RO_NO and BB.activity_code<>'' ");
			sql.append(" where bb.ACTIVITY_CODE=a.ACTIVITY_CODE and  AA.DEALER_CODE=?  and VIN=?  ) ) )");
			sql.append(" and NOT EXISTS( select 1 from (" + CommonConstants.VT_ACTIVITY_RESULT
					+ ") WHERE VIN=? and activity_code=a.activity_code) ");
			if (Utility.testString(cardTypeCode)) {
				sql.append(
						" and  ( (a.MEMBER_LEVEL_ALLOWED IS NOT NULL AND a.MEMBER_LEVEL_ALLOWED!='' AND a.MEMBER_LEVEL_ALLOWED LIKE '%"
								+ cardTypeCode
								+ "%' OR (a.MEMBER_LEVEL_ALLOWED IS NULL OR a.MEMBER_LEVEL_ALLOWED='') )  ");
			} else {
				sql.append(" and (a.MEMBER_LEVEL_ALLOWED IS NULL OR a.MEMBER_LEVEL_ALLOWED='') ");
			}
			if (Utility.testString(isRecallActivity)) {
				sql.append(" and a.IS_RECALL_ACTIVITY=?");
			}
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			String currentDate = df.format(Utility.getCurrentDateTime());
			List<Object> params = new ArrayList<Object>();
			params.add(dealerCode);
			params.add(DictCodeConstants.DICT_IS_NO);
			params.add(DictCodeConstants.DICT_ACTIVITY_RELEASE_TAG_RELEASED);
			params.add(DictCodeConstants.DICT_IS_YES);
			params.add(Utility.getTimeStamp(currentDate));
			params.add(vin);
			params.add(dealerCode);
			params.add(dealerCode);
			params.add(vin);
			params.add(brand);
			params.add(series);
			params.add(configCode);
			params.add(model);
			params.add(vin);
			params.add(sqlSalesdate);
			params.add(DictCodeConstants.DICT_IS_YES);
			params.add(dealerCode);
			params.add(vin);
			params.add(DictCodeConstants.DICT_IS_YES);
			params.add(dealerCode);
			params.add(vin);
			params.add(DictCodeConstants.DICT_IS_YES);
			params.add(dealerCode);
			params.add(vin);
			params.add(vin);
			params.add(isRecallActivity);
			list = DAOUtil.findAll(sql.toString(), params);
		} else {
			StringBuffer sqlmodel = new StringBuffer();
			sqlmodel.append(
					"SELECT 1 FROM (" + CommonConstants.VT_ACTIVITY_MODEL + ")  WHERE DEALER_CODE = ? and D_Key=?");
			sqlmodel.append(
					" and MODEL_CODE=? AND SERIES_CODE =?  AND CONFIG_CODE =?  AND ?  BETWEEN BEGIN_VIN AND END_VIN ");
			sqlmodel.append(" AND CURRENT TIMESTAMP  BETWEEN MANUFACTURE_DATE_BEGIN AND MANUFACTURE_DATE_END ");
			sqlmodel.append(" fetch first 1 rows only ");
			List<Object> params = new ArrayList<Object>();
			params.add(dealerCode);
			params.add(DictCodeConstants.D_KEY);
			params.add(queryParam.get("vin"));
			params.add(model);
			params.add(series);
			params.add(configCode);
			params.add(vinSix);
			List listModel = DAOUtil.findAll(sqlmodel.toString(), params);

			if (listModel != null && listModel.size() > 0) {
				StringBuffer sql2 = new StringBuffer();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				String sqlSalesdate = "";
				if (salesdate != null && !salesdate.equals("")) {
					Date date = formatter.parse(salesdate);
					String dateString = formatter.format(date);
					sqlSalesdate = " or (SALES_DATE_BEGIN<='" + dateString + " 00:00:00' and SALES_DATE_END>='"
							+ dateString + " 00:00:00') ";
				}
				sql.append("select a.IS_CLAIM,");
				sql.append(
						" a.IS_REPEAT_ATTEND,a.IS_PART_ACTIVITY,A.ACTIVITY_KIND,A.PARTS_IS_MODIFY,a.IS_RECALL_ACTIVITY,a.DEALER_CODE, a.ACTIVITY_CODE, a.ACTIVITY_NAME, a.ACTIVITY_TYPE, a.BEGIN_DATE, a.END_DATE, a.RELEASE_DATE,  ");
				sql.append(
						"a.RELEASE_TAG, a.LABOUR_AMOUNT, a.ADD_ITEM_AMOUNT,a.FROM_ENTITY, a.DOWN_TAG, a.DOWN_TIMESTAMP, a.REPAIR_PART_AMOUNT, a.ACTIVITY_AMOUNT, a.IS_VALID,a.VER , ");
				sql.append(
						"a.BRAND,a.SERIES,a.MODEL,a.MILEAGE_BEGIN,a.MILEAGE_END,a.MEMBER_LEVEL_ALLOWED,'' AS LABOUR_CODE,'' AS PART_CODE, A.ACTIVITY_PROPERTY   from( select distinct ");
				sql.append(
						"a.IS_REPEAT_ATTEND,a.IS_PART_ACTIVITY,A.ACTIVITY_KIND,A.PARTS_IS_MODIFY,a.DEALER_CODE, a.ACTIVITY_CODE, a.ACTIVITY_NAME, a.ACTIVITY_TYPE, a.BEGIN_DATE, a.END_DATE, a.RELEASE_DATE,  ");
				sql.append(
						"a.RELEASE_TAG, a.LABOUR_AMOUNT, a.ADD_ITEM_AMOUNT,a.FROM_ENTITY, a.DOWN_TAG, a.DOWN_TIMESTAMP, a.REPAIR_PART_AMOUNT, a.ACTIVITY_AMOUNT, a.IS_VALID,a.VER , ");
				sql.append(
						"coalesce(a.BRAND,'') as BRAND,a.IS_RECALL_ACTIVITY ,coalesce(a.SERIES,'') as SERIES,coalesce(a.MODEL,'') as MODEL,coalesce(a.MILEAGE_BEGIN,0) as MILEAGE_BEGIN,coalesce(a.MILEAGE_END,0) as MILEAGE_END, ");
				sql.append(
						"a.SALES_DATE_BEGIN,a.SALES_DATE_END,A.MEMBER_LEVEL_ALLOWED,A.MODEL_YEAR,A.MAINTAIN_BEGIN_DAY,A.MAINTAIN_END_DAY,A.CONFIG_CODE, A.ACTIVITY_PROPERTY,A.IS_CLAIM ");
				sql.append("from (" + CommonConstants.VT_ACTIVITY
						+ ")  A  where  a.DEALER_CODE=? and IS_PART_ACTIVITY= ? AND RELEASE_TAG = ?  AND IS_VALID =? ");
				sql.append(
						" AND ? BETWEEN BEGIN_DATE AND END_DATE  AND ( a.VEHICLE_PURPOSE is null OR a.VEHICLE_PURPOSE = 0");
				sql.append(" OR   EXISTS ( SELECT 1 FROM (" + CommonConstants.VM_VEHICLE
						+ ") v WHERE VIN = ? AND v.VEHICLE_PURPOSE = a.VEHICLE_PURPOSE ) ) ");
				sql.append(" and ( not exists (select 1 from (" + CommonConstants.VT_ACTIVITY_MODEL
						+ ")  b where a.DEALER_CODE = b.DEALER_CODE and a.activity_Code = b.activity_code ");
				sql.append(" and b.DEALER_CODE=?) or exists (select 1 from TT_ACTIVITY_MODEL b,("
						+ CommonConstants.VT_ACTIVITY
						+ ")  A where a.entity_code = b.entity_code and  a.activity_Code = b.activity_code ");
				sql.append("and b.DEALER_CODE=? and b.MODEL_CODE=? AND B.SERIES_CODE  = ?");
				sql.append(
						" AND (? BETWEEN B.BEGIN_VIN AND B.END_VIN OR (B.BEGIN_VIN IS NULL AND B.END_VIN IS NULL) )");
				sql.append(
						" AND (CURRENT TIMESTAMP  BETWEEN B.MANUFACTURE_DATE_BEGIN AND B.MANUFACTURE_DATE_END OR (B.MANUFACTURE_DATE_BEGIN IS NULL AND B.MANUFACTURE_DATE_END IS NULL))  ))");

				sql.append(" and (A.MODEL_YEAR='' or A.MODEL_YEAR IS NULL OR EXISTS(SELECT 1 FROM ("
						+ CommonConstants.VM_VEHICLE + ") V WHERE VIN=? AND V.MODEL_YEAR=A.MODEL_YEAR)) ");
				sql.append(
						" )a where ((BRAND ='' or BRAND=?)and( SERIES ='' or SERIES=? )and( CONFIG_CODE =''  OR CONFIG_CODE IS NULL  or CONFIG_CODE=?)and(MODEL ='' or MODEL=? )) and (((MILEAGE_BEGIN  =0 and MILEAGE_END  =0) or ( ");
				if (Utility.testString(mileage)) {
					sql.append(" MILEAGE_BEGIN<=" + mileage + " and MILEAGE_END>=" + mileage + " ");
				} else {
					sql.append(" 1=1 ");
				}
				sql.append(" ))");
				sql.append(
						" and (((A.MAINTAIN_BEGIN_DAY IS NULL OR A.MAINTAIN_BEGIN_DAY=0.00) AND (A.MAINTAIN_END_DAY IS NULL OR A.MAINTAIN_END_DAY=0.00)) OR ");
				sql.append(" EXISTS(SELECT 1 FROM (" + CommonConstants.VM_VEHICLE
						+ ") V WHERE VIN=? AND V.SALES_DATE IS NOT NULL AND DAYS(CURRENT DATE)-DAYS(V.SALES_DATE)>=A.MAINTAIN_BEGIN_DAY AND DAYS(CURRENT DATE)-DAYS(V.SALES_DATE)<=A.MAINTAIN_END_DAY))) ");
				sql.append(" and(SALES_DATE_BEGIN is null or SALES_DATE_END is null ");
				sql.append(" ?  ) and ( ( IS_REPEAT_ATTEND=?) or (not exists( select 1 from TT_REPAIR_ORDER AA");
				sql.append(
						" left join TT_RO_LABOUR BB on AA.DEALER_CODE=BB.DEALER_CODE and AA.RO_NO=BB.RO_NO and BB.activity_code<>'' ");
				sql.append(
						"  where bb.ACTIVITY_CODE=a.ACTIVITY_CODE  and AA.DEALER_CODE=? and VIN=? and IS_ACTIVITY =? UNION ALL ");
				sql.append(
						"select 1 from TT_REPAIR_ORDER  AA  left join TT_RO_REPAIR_PART BB on AA.DEALER_CODE=BB.DEALER_CODE and AA.RO_NO=BB.RO_NO and BB.activity_code<>'' ");
				sql.append(
						" where bb.ACTIVITY_CODE=a.ACTIVITY_CODE and  AA.DEALER_CODE=? and VIN=?  and IS_ACTIVITY =?  UNION ALL");
				sql.append(
						" select 1 from TT_REPAIR_ORDER  AA  left join TT_RO_ADD_ITEM BB on AA.DEALER_CODE=BB.DEALER_CODE and AA.RO_NO=BB.RO_NO and BB.activity_code<>'' ");
				sql.append(" where bb.ACTIVITY_CODE=a.ACTIVITY_CODE and  AA.DEALER_CODE=?  and VIN=?  ) ) )");
				sql.append(" and NOT EXISTS( select 1 from (" + CommonConstants.VT_ACTIVITY_RESULT
						+ ") WHERE VIN=? and activity_code=a.activity_code) ");
				if (Utility.testString(cardTypeCode)) {
					sql.append(
							" and  ( (a.MEMBER_LEVEL_ALLOWED IS NOT NULL AND a.MEMBER_LEVEL_ALLOWED!='' AND a.MEMBER_LEVEL_ALLOWED LIKE '%"
									+ cardTypeCode
									+ "%' OR (a.MEMBER_LEVEL_ALLOWED IS NULL OR a.MEMBER_LEVEL_ALLOWED='') )  ");
				} else {
					sql.append(" and (a.MEMBER_LEVEL_ALLOWED IS NULL OR a.MEMBER_LEVEL_ALLOWED='') ");
				}
				if (Utility.testString(isRecallActivity)) {
					sql.append(" and a.IS_RECALL_ACTIVITY=?");
				}
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				String currentDate = df.format(Utility.getCurrentDateTime());
				List<Object> params1 = new ArrayList<Object>();
				params1.add(dealerCode);
				params1.add(DictCodeConstants.DICT_IS_NO);
				params1.add(DictCodeConstants.DICT_ACTIVITY_RELEASE_TAG_RELEASED);
				params1.add(DictCodeConstants.DICT_IS_YES);
				params1.add(Utility.getTimeStamp(currentDate));
				params1.add(vin);
				params1.add(dealerCode);
				params1.add(dealerCode);
				params1.add(model);
				params1.add(series);
				params1.add(vinSix);
				params.add(brand);
				params1.add(series);
				params1.add(configCode);
				params1.add(model);
				params1.add(vin);
				params1.add(sqlSalesdate);
				params1.add(DictCodeConstants.DICT_IS_YES);
				params1.add(dealerCode);
				params1.add(vin);
				params1.add(DictCodeConstants.DICT_IS_YES);
				params1.add(dealerCode);
				params1.add(vin);
				params1.add(DictCodeConstants.DICT_IS_YES);
				params1.add(dealerCode);
				params1.add(vin);
				params1.add(vin);
				params1.add(isRecallActivity);
				list = DAOUtil.findAll(sql.toString(), params1);
			} else {
				StringBuffer sql2 = new StringBuffer();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				String sqlSalesdate = "";
				if (salesdate != null && !salesdate.equals("")) {
					Date date = formatter.parse(salesdate);
					String dateString = formatter.format(date);
					sqlSalesdate = " or (SALES_DATE_BEGIN<='" + dateString + " 00:00:00' and SALES_DATE_END>='"
							+ dateString + " 00:00:00') ";
				}
				sql.append("select a.IS_CLAIM,");
				sql.append(
						" a.IS_REPEAT_ATTEND,a.IS_PART_ACTIVITY,A.ACTIVITY_KIND,A.PARTS_IS_MODIFY,a.IS_RECALL_ACTIVITY,a.DEALER_CODE, a.ACTIVITY_CODE, a.ACTIVITY_NAME, a.ACTIVITY_TYPE, a.BEGIN_DATE, a.END_DATE, a.RELEASE_DATE,  ");
				sql.append(
						"a.RELEASE_TAG, a.LABOUR_AMOUNT, a.ADD_ITEM_AMOUNT,a.FROM_ENTITY, a.DOWN_TAG, a.DOWN_TIMESTAMP, a.REPAIR_PART_AMOUNT, a.ACTIVITY_AMOUNT, a.IS_VALID,a.VER , ");
				sql.append(
						"a.BRAND,a.SERIES,a.MODEL,a.MILEAGE_BEGIN,a.MILEAGE_END,a.MEMBER_LEVEL_ALLOWED,'' AS LABOUR_CODE,'' AS PART_CODE, A.ACTIVITY_PROPERTY   from( select distinct ");
				sql.append(
						"a.IS_REPEAT_ATTEND,a.IS_PART_ACTIVITY,A.ACTIVITY_KIND,A.PARTS_IS_MODIFY,a.DEALER_CODE, a.ACTIVITY_CODE, a.ACTIVITY_NAME, a.ACTIVITY_TYPE, a.BEGIN_DATE, a.END_DATE, a.RELEASE_DATE,  ");
				sql.append(
						"a.RELEASE_TAG, a.LABOUR_AMOUNT, a.ADD_ITEM_AMOUNT,a.FROM_ENTITY, a.DOWN_TAG, a.DOWN_TIMESTAMP, a.REPAIR_PART_AMOUNT, a.ACTIVITY_AMOUNT, a.IS_VALID,a.VER , ");
				sql.append(
						"coalesce(a.BRAND,'') as BRAND,a.IS_RECALL_ACTIVITY ,coalesce(a.SERIES,'') as SERIES,coalesce(a.MODEL,'') as MODEL,coalesce(a.MILEAGE_BEGIN,0) as MILEAGE_BEGIN,coalesce(a.MILEAGE_END,0) as MILEAGE_END, ");
				sql.append(
						"a.SALES_DATE_BEGIN,a.SALES_DATE_END,A.MEMBER_LEVEL_ALLOWED,A.MODEL_YEAR,A.MAINTAIN_BEGIN_DAY,A.MAINTAIN_END_DAY,A.CONFIG_CODE, A.ACTIVITY_PROPERTY,A.IS_CLAIM ");
				sql.append("from (" + CommonConstants.VT_ACTIVITY
						+ ") A  where  a.DEALER_CODE=? and IS_PART_ACTIVITY= ? AND RELEASE_TAG = ?  AND IS_VALID =? ");
				sql.append(
						" AND ? BETWEEN BEGIN_DATE AND END_DATE  AND ( a.VEHICLE_PURPOSE is null OR a.VEHICLE_PURPOSE = 0");
				sql.append(" OR   EXISTS ( SELECT 1 FROM (" + CommonConstants.VM_VEHICLE
						+ ") v WHERE VIN = ? AND v.VEHICLE_PURPOSE = a.VEHICLE_PURPOSE ) ) ");

				sql.append(" and ( not exists (select 1 from (" + CommonConstants.VT_ACTIVITY_MODEL
						+ ")   b where a.DEALER_CODE = b.DEALER_CODE and a.activity_Code = b.activity_code ");
				sql.append(" and b.DEALER_CODE=?) or exists (select 1 from TT_ACTIVITY_MODEL b,("
						+ CommonConstants.VT_ACTIVITY
						+ ") A where a.entity_code = b.entity_code and  a.activity_Code = b.activity_code ");
				sql.append("and b.DEALER_CODE=? and b.MODEL_CODE=? AND B.SERIES_CODE  = ?");
				sql.append(
						" AND (? BETWEEN B.BEGIN_VIN AND B.END_VIN OR (B.BEGIN_VIN IS NULL AND B.END_VIN IS NULL) )");
				sql.append(
						" AND (CURRENT TIMESTAMP  BETWEEN B.MANUFACTURE_DATE_BEGIN AND B.MANUFACTURE_DATE_END OR (B.MANUFACTURE_DATE_BEGIN IS NULL AND B.MANUFACTURE_DATE_END IS NULL))  ))");

				sql.append(" and ( not exists (select 1 from (" + CommonConstants.VT_ACTIVITY_VEHICLE
						+ ") c where a.DEALER_CODE = c.DEALER_CODE and   a.activity_Code = c.activity_code ");
				sql.append(" and c.DEALER_CODE=?  or exists (select 1 from (" + CommonConstants.VT_ACTIVITY_VEHICLE
						+ ") c where a.DEALER_CODE = c.DEALER_CODE and  a.activity_Code = c.activity_code ");
				sql.append(" and c.DEALER_CODE=? and c.VIN = ?) )");

				sql.append(" and (A.MODEL_YEAR='' or A.MODEL_YEAR IS NULL OR EXISTS(SELECT 1 FROM ("
						+ CommonConstants.VM_VEHICLE + ") V WHERE VIN=? AND V.MODEL_YEAR=A.MODEL_YEAR)) ");
				sql.append(
						" )a where ((BRAND ='' or BRAND=?)and( SERIES ='' or SERIES=? )and( CONFIG_CODE =''  OR CONFIG_CODE IS NULL  or CONFIG_CODE=?)and(MODEL ='' or MODEL=? ')) and (((MILEAGE_BEGIN  =0 and MILEAGE_END  =0) or ( ");
				if (Utility.testString(mileage)) {
					sql.append(" MILEAGE_BEGIN<=" + mileage + " and MILEAGE_END>=" + mileage + " ");
				} else {
					sql.append(" 1=1 ");
				}
				sql.append(" ))");
				sql.append(
						" and (((A.MAINTAIN_BEGIN_DAY IS NULL OR A.MAINTAIN_BEGIN_DAY=0.00) AND (A.MAINTAIN_END_DAY IS NULL OR A.MAINTAIN_END_DAY=0.00)) OR ");
				sql.append(" EXISTS(SELECT 1 FROM (" + CommonConstants.VM_VEHICLE
						+ ") V WHERE VIN=? AND V.SALES_DATE IS NOT NULL AND DAYS(CURRENT DATE)-DAYS(V.SALES_DATE)>=A.MAINTAIN_BEGIN_DAY AND DAYS(CURRENT DATE)-DAYS(V.SALES_DATE)<=A.MAINTAIN_END_DAY))) ");
				sql.append(" and(SALES_DATE_BEGIN is null or SALES_DATE_END is null ");
				sql.append(" ?  ) and ( ( IS_REPEAT_ATTEND=?) or (not exists( select 1 from TT_REPAIR_ORDER AA");
				sql.append(
						" left join TT_RO_LABOUR BB on AA.DEALER_CODE=BB.DEALER_CODE and AA.RO_NO=BB.RO_NO and BB.activity_code<>'' ");
				sql.append(
						"  where bb.ACTIVITY_CODE=a.ACTIVITY_CODE  and AA.DEALER_CODE=? and VIN=? and IS_ACTIVITY =? UNION ALL ");
				sql.append(
						"select 1 from TT_REPAIR_ORDER  AA  left join TT_RO_REPAIR_PART BB on AA.DEALER_CODE=BB.DEALER_CODE and AA.RO_NO=BB.RO_NO and BB.activity_code<>'' ");
				sql.append(
						" where bb.ACTIVITY_CODE=a.ACTIVITY_CODE and  AA.DEALER_CODE=? and VIN=?  and IS_ACTIVITY =?  UNION ALL");
				sql.append(
						" select 1 from TT_REPAIR_ORDER  AA  left join TT_RO_ADD_ITEM BB on AA.DEALER_CODE=BB.DEALER_CODE and AA.RO_NO=BB.RO_NO and BB.activity_code<>'' ");
				sql.append(" where bb.ACTIVITY_CODE=a.ACTIVITY_CODE and  AA.DEALER_CODE=?  and VIN=?  ) ) )");
				sql.append(" and NOT EXISTS( select 1 from (" + CommonConstants.VT_ACTIVITY_RESULT
						+ ") WHERE VIN=? and activity_code=a.activity_code) ");
				if (Utility.testString(cardTypeCode)) {
					sql.append(
							" and  ( (a.MEMBER_LEVEL_ALLOWED IS NOT NULL AND a.MEMBER_LEVEL_ALLOWED!='' AND a.MEMBER_LEVEL_ALLOWED LIKE '%"
									+ cardTypeCode
									+ "%' OR (a.MEMBER_LEVEL_ALLOWED IS NULL OR a.MEMBER_LEVEL_ALLOWED='') )  ");
				} else {
					sql.append(" and (a.MEMBER_LEVEL_ALLOWED IS NULL OR a.MEMBER_LEVEL_ALLOWED='') ");
				}
				if (Utility.testString(isRecallActivity)) {
					sql.append(" and a.IS_RECALL_ACTIVITY=?");
				}
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				String currentDate = df.format(Utility.getCurrentDateTime());
				List<Object> params1 = new ArrayList<Object>();
				params1.add(dealerCode);
				params1.add(DictCodeConstants.DICT_IS_NO);
				params1.add(DictCodeConstants.DICT_ACTIVITY_RELEASE_TAG_RELEASED);
				params1.add(DictCodeConstants.DICT_IS_YES);
				params1.add(Utility.getTimeStamp(currentDate));
				params1.add(vin);
				params1.add(dealerCode);
				params1.add(dealerCode);
				params1.add(model);
				params1.add(series);
				params1.add(vinSix);
				params1.add(dealerCode);
				params1.add(dealerCode);
				params1.add(vin);
				params1.add(brand);
				params1.add(series);
				params1.add(configCode);
				params1.add(model);
				params1.add(vin);
				params1.add(sqlSalesdate);
				params1.add(DictCodeConstants.DICT_IS_YES);
				params1.add(dealerCode);
				params1.add(vin);
				params1.add(DictCodeConstants.DICT_IS_YES);
				params1.add(dealerCode);
				params1.add(vin);
				params1.add(DictCodeConstants.DICT_IS_YES);
				params1.add(dealerCode);
				params1.add(vin);
				params1.add(vin);
				params1.add(isRecallActivity);
				list = DAOUtil.findAll(sql.toString(), params1);
			}
		}
		// 1、判断list中的服务活动中是否有活动车辆和活动车型，都没则该活动适用所有车；
		// 2、有活动车辆而建工单的车辆不属于该活动，则该车辆不适用这个活动；
		// 3、没有活动车辆，但有活动车型，则判断建工单的车的车型，车型，配置，vinsix是否满足该活动及当前时间是否在该活动车辆中的生产日期范围
		// 是则适用，否则不适用。
		List<Map> list1 = new ArrayList<Map>();
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				List<Map> bean = (List<Map>) list.get(i);
				String activityCode = (String) bean.get(0).get("ACTIVITY_CODE");
				String sqla = " and activity_code='" + activityCode + "' ";
				List vehicleList = this.queryVehicleStyle(dealerCode, "(" + CommonConstants.VT_ACTIVITY_VEHICLE + ")",
						sqla);
				List modelList = this.queryVehicleStyle(dealerCode, "(" + CommonConstants.VT_ACTIVITY_MODEL + ")",
						sqla);
				// 是否有活动车辆，有则：
				if (vehicleList != null && vehicleList.size() > 0) {
					// 判断车辆是否属于该活动车辆中
					sqla = " and activity_code='" + activityCode + "' and vin='" + vin + "' ";
					List flag = this.queryVehicleStyle(dealerCode, "(" + CommonConstants.VT_ACTIVITY_VEHICLE + ")",
							sqla);
					if (flag != null && flag.size() > 0) {
						list1.add((Map) bean);
					}
					// 判断是否有活动车型，有则：
				} else if (modelList != null && modelList.size() > 0) {
					// 判断该车是否满足该活动的车型
					sqla = " and activity_code='" + activityCode + "' and MODEL_CODE='" + model + "' AND SERIES_CODE ='"
							+ series + "' " + " AND '" + vinSix
							+ "' BETWEEN value(BEGIN_VIN,'000000') AND VALUE(END_VIN,'999999')  ";
					List listModel = this.queryVehicleStyle(dealerCode, "(" + CommonConstants.VT_ACTIVITY_MODEL + ")",
							sqla);
					if (listModel != null && listModel.size() > 0) {
						list1.add((Map) bean);
					}
				} else {
					list1.add((Map) bean);
				}
			}
		}
		return list1;
	}

	private List queryVehicleStyle(String dealerCode, String tableName, String sqla) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT 1 FROM ? WHERE DEALER_CODE = ?and D_Key= ?");
		sql.append(sqla);
		sql.append(" fetch first 1 rows onlyf ");
		List<Object> params = new ArrayList<Object>();
		params.add(tableName);
		params.add(dealerCode);
		params.add(DictCodeConstants.D_KEY);
		return DAOUtil.findAll(sql.toString(), params);
	}

	/**
	 * 查询参于活动的记录
	 * 
	 * @param conn
	 * @param entityCode
	 * @param VIN
	 */
	@Override
	public PageInfoDto queryActivityResult(Map<String, String> queryParam) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(
				"select aa.ACTIVITY_CODE,AA.ACTIVITY_NAME,AA.VIN,AA.DEALER_NAME,AA.DEALER_CODE,AA.CAMPAIGN_DATE  from   ("
						+ CommonConstants.VT_ACTIVITY_RESULT + ") AA where AA.DEALER_CODE=? and AA.VIN=?");

		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		List<Object> params = new ArrayList<Object>();
		params.add(dealerCode);
		params.add(queryParam.get("vin"));
		PageInfoDto pageInfoDto = DAOUtil.pageQuery(sql.toString(), params);
		return pageInfoDto;
	}

	/**
	 * 根据车牌号或者VIN，查询出出险信息，用于开工单时，选择出险信息
	 * 
	 * @param conn
	 * @param entityCode
	 * @param occurInsuranceNo
	 * @return
	 * @throws Exception
	 */
	@Override
	public PageInfoDto QueryOccurInsuranceByLicenseOrVin(Map<String, String> queryParam) throws Exception {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append(" select A.* from TT_OCCUR_INSURANCE_REGISTER A where A.DEALER_CODE=? ");
		sql.append(" and A.VIN=?"); // 需求变了 要都查出来，已关联工单的是默认勾上
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		params.add(dealerCode);
		params.add(queryParam.get("vin"));
		if ((!StringUtils.isNullOrEmpty(queryParam.get("RONO")))) {
			sql.append(" and (A.RO_NO=? or A.ro_no is null or a.ro_no='' )");
			params.add(queryParam.get("RONO"));
		} else {
			sql.append(" and (A.ro_no is null or a.ro_no='' )");
		}

		PageInfoDto pageInfoDto = DAOUtil.pageQuery(sql.toString(), params);
		return pageInfoDto;
	}

	@Override
	public List<Map> queryVinByVin(Map<String, String> query) {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT A.*,B.OWNER_NAME,B.OWNER_PROPERTY,C.MEMO_INFO ")
				.append(" FROM (" + CommonConstants.VM_VEHICLE + ") A")
				.append(" LEFT JOIN (" + CommonConstants.VM_OWNER
						+ ") B ON A.DEALER_CODE = B. DEALER_CODE AND A.OWNER_NO = B.OWNER_NO ")
				.append(" LEFT JOIN TM_VEHICLE_MEMO C ON C.DEALER_CODE = A.DEALER_CODE AND C.VIN = A.VIN")
				.append(" WHERE A.VIN = '").append(query.get("vin")).append("' AND A.DEALER_CODE = '")
				.append(FrameworkUtil.getLoginInfo().getDealerCode()).append("' ");
		List<Map> findAll = DAOUtil.findAll(sb.toString(), null);
		String vipNo = null;
		String insuranceBillNo = null;
		String zipCode = null;
		List<Map> result = new ArrayList<Map>();
		for (Map db : findAll) {
			vipNo = db.get("VIP_NO") == null ? "" : db.get("VIP_NO").toString();
			db.put("VIP_NO", vipNo);
			insuranceBillNo = db.get("INSURANCE_BILL_NO") == null ? "" : db.get("INSURANCE_BILL_NO").toString();
			db.put("INSURANCE_BILL_NO", insuranceBillNo);
			zipCode = db.get("ZIP_CODE") == null ? "" : db.get("ZIP_CODE").toString();
			db.put("ZIP_CODE", zipCode);
			result.add(db);
		}
		return result;
	}

	@Override
	public String findServiceAdvisor(String str) {
		String sql = "SELECT DEALER_CODE,SERVICE_ADVISOR FROM TM_EMPLOYEE WHERE EMPLOYEE_NO = ?";
		List queryParam = new ArrayList();
		queryParam.add(str);
		List<Map> findAll = DAOUtil.findAll(sql, queryParam);
		if (!CommonUtils.isNullOrEmpty(findAll) && findAll.size() == 1) {
			Map map = findAll.get(0);
			if (DictCodeConstants.DICT_IS_YES.equals(map.get("SERVICE_ADVISOR"))) {
				return "1";
			}
		}
		return "0";
	}

	@Override
	public String findLabourPriceByRepairTypeCode(String string) {
		String sql = "SELECT DEALER_CODE,LABOUR_PRICE FROM TM_REPAIR_TYPE WHERE REPAIR_TYPE_CODE = ? AND DEALER_CODE = ?";
		List queryParam = new ArrayList();
		queryParam.add(string);
		queryParam.add(FrameworkUtil.getLoginInfo().getDealerCode());
		List<Map> findAll = DAOUtil.findAll(sql, queryParam);
		if(!CommonUtils.isNullOrEmpty(findAll)&&findAll.size()>0){
			if(!StringUtils.isNullOrEmpty(findAll.get(0).get("LABOUR_PRICE"))){
				return findAll.get(0).get("LABOUR_PRICE").toString();
			}
		}
		return "";
	}

	@Override
	public String findLabourPriceByModelCode(Map<String, String> param) {
		String sql = "SELECT DEALER_CODE,LABOUR_PRICE FROM TM_MODEL WHERE BRAND_CODE = ? AND SERIES_CODE = ? AND MODEL_CODE = ? AND DEALER_CODE = ?";
		List queryParam = new ArrayList();
		queryParam.add(param.get("brand"));
		queryParam.add(param.get("series"));
		queryParam.add(param.get("model"));
		queryParam.add(FrameworkUtil.getLoginInfo().getDealerCode());
		List<Map> findAll = DAOUtil.findAll(sql, queryParam);
		if(!CommonUtils.isNullOrEmpty(findAll)&&findAll.size()>0){
			if(!StringUtils.isNullOrEmpty(findAll.get(0).get("LABOUR_PRICE"))){
				return findAll.get(0).get("LABOUR_PRICE").toString();
			}
		}
		return "";
	}

}
