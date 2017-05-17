/**
 * 
 */
package com.yonyou.dms.part.service.basedata;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.DB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.AccountPeriodPO;
import com.yonyou.dms.common.domains.PO.basedata.TmPartStockItemPO;
import com.yonyou.dms.common.domains.PO.basedata.TmPartStockPO;
import com.yonyou.dms.common.domains.PO.basedata.TtAccountsTransFlowPO;
import com.yonyou.dms.common.domains.PO.basedata.TtPartInventoryPO;
import com.yonyou.dms.common.domains.PO.basedata.TtPartMonthReportPO;
import com.yonyou.dms.common.domains.PO.basedata.TtPartPeriodReportPO;
import com.yonyou.dms.common.domains.PO.basedata.TtPartProfitItemPO;
import com.yonyou.dms.common.domains.PO.basedata.TtPartProfitPO;
import com.yonyou.dms.common.domains.PO.monitor.OperateLogPO;
import com.yonyou.dms.commonAS.domains.PO.basedata.PartFlowPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.CommonNoService;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.part.domains.DTO.basedata.ReportPayOffDTO;

/**
 * @author yangjie
 *
 */
@Service
@SuppressWarnings({ "unchecked", "rawtypes" })
public class ReportPayOffServiceImpl implements ReportPayOffService {

	@Autowired
	private CommonNoService commonNoService;

	@Override
	public PageInfoDto findAllList(Map<String, String> queryParam) {
		StringBuffer sb = new StringBuffer();
		sb.append(
				"select A.LOCK_USER as LOCK_USERU,A.DEALER_CODE, A.PROFIT_NO, A.INVENTORY_NO, tua.USER_ID, tua.USER_NAME as HANDLER, A.TOTAL_AMOUNT,A.PROFIT_DATE, ")
				.append("A.IS_FINISHED, A.FINISHED_DATE, tu.USER_NAME as LOCK_USER, A.D_KEY, A.CREATED_BY,A.CREATED_AT, A.UPDATED_BY, ")
				.append("A.UPDATED_AT, A.VER from TT_PART_PROFIT A LEFT JOIN TM_USER tu ON tu.USER_ID = A.LOCK_USER AND tu.DEALER_CODE = A.DEALER_CODE LEFT JOIN TM_USER tua ON tua.USER_ID = A.HANDLER AND tua.DEALER_CODE = A.DEALER_CODE where")
				.append(" A.IS_FINISHED=").append(DictCodeConstants.IS_NOT).append(" and A.D_KEY=")
				.append(CommonConstants.D_KEY).append(" and A.DEALER_CODE='")
				.append(FrameworkUtil.getLoginInfo().getDealerCode()).append("'");
		Utility.sqlToLike(sb, queryParam.get("profitNo"), "PROFIT_NO", "A");
		Utility.sqlToDate(sb, queryParam.get("orderDateFrom"), queryParam.get("orderDateTo"), "PROFIT_DATE", "A");
		return DAOUtil.pageQuery(sb.toString(), null);
	}

	@Override
	public PageInfoDto findAllInventroy(Map<String, String> queryParam) {
		StringBuffer sb = new StringBuffer();
		sb.append(
				"select A.LOCK_USER as LOCK_USERU,'' as SQL_CON ,'' as  SQL_TAG,'' as BEGIN_DATE_OUT ,'' as END_DATE_OUT ,'' as BEGIN_DATE_IN ,'' as  END_DATE_IN,'' as PART_MODEL_GROUP_CODE_SET,A.REMARK,A.DEALER_CODE, A.INVENTORY_NO, A.INVENTORY_DATE, A.PROFIT_AMOUNT, A.LOSS_COUNT, ")
				.append(" A.LOSS_AMOUNT,tus.USER_NAME as HANDLER1,A.HANDLER as HANDLER, A.PROFIT_COUNT, A.IS_CONFIRMED, A.IS_FINISHED, A.PROFIT_TAG, ")
				.append(" A.LOSS_TAG,tu.USER_NAME as LOCK_USER  from  TT_PART_INVENTORY A LEFT JOIN TM_USER tu ON tu.USER_ID=A.LOCK_USER AND tu.DEALER_CODE=A.DEALER_CODE LEFT JOIN TM_USER tus ON tus.USER_ID=A.HANDLER AND tus.DEALER_CODE=A.DEALER_CODE LEFT JOIN ( SELECT B.PROFIT_NO,B.INVENTORY_NO,B.DEALER_CODE FROM TT_PART_PROFIT B")
				.append(" WHERE 1=1  ");
		Utility.sqlToLike(sb, queryParam.get("INVENTORY_NO"), "INVENTORY_NO", "B");
		sb.append(") C ON A.DEALER_CODE = C.DEALER_CODE AND A.INVENTORY_NO = C.INVENTORY_NO WHERE ")
				.append("A.DEALER_CODE = ? ").append("AND A.PROFIT_TAG = ").append(DictCodeConstants.IS_NOT)
				.append(" AND A.D_KEY = ").append(CommonConstants.D_KEY);
		Utility.sqlToLike(sb, queryParam.get("INVENTORY_NO"), "INVENTORY_NO", "A");
		sb.append(" and A.IS_FINISHED=").append(DictCodeConstants.IS_YES).append(" AND C.PROFIT_NO IS  NULL");
		Utility.sqlToDate(sb, queryParam.get("BENGIN_DATE"), queryParam.get("END_DATE"), "INVENTORY_DATE", "A");
		List param = new ArrayList();
		param.add(FrameworkUtil.getLoginInfo().getDealerCode());
		return DAOUtil.pageQuery(sb.toString(), param);
	}

	@Override
	public List<Map> findItemByInventroy(String id) {
		// 根据盘点单号查询锁定用户
		String lockerName = Utility.selLockerName("LOCK_USER", "tt_part_inventory", "INVENTORY_NO", id);
		if (!StringUtils.isNullOrEmpty(lockerName)
				&& lockerName.equals(FrameworkUtil.getLoginInfo().getUserId().toString())) {// 表示锁定用户为本人
			// 锁定用户
			int locker = Utility.updateByLocker("tt_part_inventory",
					FrameworkUtil.getLoginInfo().getUserId().toString(), "INVENTORY_NO", id, "LOCK_USER");
			if (locker > 0) {// 表示锁定成功
				return queryInventoryProfitItem(id);
			} else {
				return new ArrayList<Map>();
			}
		} else {// 表示锁定不为本人
			return new ArrayList<Map>();
		}
	}

	/**
	 * 根据盘点单查询报溢单
	 * 
	 * @param id
	 * @return
	 */
	public List<Map> queryInventoryProfitItem(String id) {
		StringBuffer sb = new StringBuffer();
		sb.append(
				"SELECT CASE WHEN PROFIT_LOSS_QUANTITY=0 THEN 0 ELSE PROFIT_LOSS_AMOUNT/PROFIT_LOSS_QUANTITY END as PROFIT_PRICE,ABS(A.PROFIT_LOSS_QUANTITY) as PROFIT_QUANTITY,A.ITEM_ID,A.INVENTORY_NO,A.DEALER_CODE,ts.STORAGE_NAME,A.STORAGE_CODE,A.PART_BATCH_NO,A.PART_NO,A.PART_NAME, ")
				.append("A.UNIT_CODE,tun.UNIT_NAME,A.CURRENT_STOCK,A.BORROW_QUANTITY,A.STORAGE_POSITION_CODE, ")
				.append("A.LEND_QUANTITY,A.REAL_STOCK,A.CHECK_QUANTITY,ABS(A.PROFIT_LOSS_QUANTITY) PROFIT_LOSS_QUANTITY,A.COST_PRICE, ")
				.append("A.PROFIT_LOSS_AMOUNT as PROFIT_AMOUNT,B.IS_BACK ")
				.append("FROM TT_PART_INVENTORY_ITEM A LEFT JOIN TM_UNIT tun ON tun.DEALER_CODE = A.DEALER_CODE AND tun.UNIT_CODE = A.UNIT_CODE LEFT JOIN TM_STORAGE ts ON ts.STORAGE_CODE = A.STORAGE_CODE AND ts.DEALER_CODE = A.DEALER_CODE ")
				.append("LEFT JOIN TM_PART_INFO B ON A.DEALER_CODE=B.DEALER_CODE AND A.PART_NO=B.PART_NO ")
				.append("WHERE A.DEALER_CODE=?").append(" AND A.PROFIT_LOSS_QUANTITY>0 ");
		Utility.sqlToLike(sb, id, "INVENTORY_NO", "A");
		List queryParam = new ArrayList();
		queryParam.add(FrameworkUtil.getLoginInfo().getDealerCode());
		return DAOUtil.findAll(sb.toString(), queryParam);
	}

	@Override
	public PageInfoDto findAllPartInfo(Map<String, String> queryParam) {
		String a = Utility.getDefaultValue(CommonConstants.DEFAULT_PARA_PART_RATE + "");// 配件税率
		String value = Utility.getDefaultValue("1180");// 三包预警
		String value2 = Utility.getDefaultValue("5433");// 是否允许查询仓库固化前的仓库中配件
		Double rate = 1 + Double.parseDouble(a);
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT TS.STORAGE_NAME,B.OPTION_NO,A.COST_PRICE*").append(rate)
				.append(" AS NET_COST_PRICE,A.COST_AMOUNT*").append(rate)
				.append(" AS NET_COST_AMOUNT,B.ORI_PRO_CODE,A.IS_SUGGEST_ORDER,A.PART_MODEL_GROUP_CODE_SET,A.PART_MAIN_TYPE,A.PART_SPE_TYPE,A.DEALER_CODE, A.PART_NO, A.FOUND_DATE, A.STORAGE_CODE, A.STORAGE_POSITION_CODE, A.PART_NAME, ")
				.append(" A.SPELL_CODE, A.PART_GROUP_CODE, A.UNIT_CODE, A.STOCK_QUANTITY, A.SALES_PRICE, ")
				.append(" A.CLAIM_PRICE, A.LIMIT_PRICE, A.LATEST_PRICE, A.COST_PRICE*1.0000 COST_PRICE, A.COST_AMOUNT*1.0000 COST_AMOUNT, A.MAX_STOCK, ")
				.append(" A.MIN_STOCK, A.BORROW_QUANTITY, A.LEND_QUANTITY, A.LOCKED_QUANTITY,TS.CJ_TAG, A.PART_STATUS, ")
				.append(" A.LAST_STOCK_IN, A.LAST_STOCK_OUT, A.REMARK, A.VER, ")
				.append(" A.JAN_MODULUS,A.FEB_MODULUS,A.MAR_MODULUS,A.APR_MODULUS,A.MAY_MODULUS,A.JUN_MODULUS,A.JUL_MODULUS,A.AUG_MODULUS,")
				.append(" A. SEP_MODULUS,A.OCT_MODULUS,A.NOV_MODULUS,A.DEC_MODULUS,A.MONTHLY_QTY_FORMULA,")
				.append(" B.PRODUCTING_AREA, B.BRAND, B.MIN_PACKAGE,B.DOWN_TAG,B.FROM_ENTITY, B.IS_BACK,")
				.append(" D.OPTION_STOCK,A.INSURANCE_PRICE,B.INSTRUCT_PRICE, (A.STOCK_QUANTITY + A.BORROW_QUANTITY - A.LEND_QUANTITY - A.LOCKED_QUANTITY) AS USEABLE_STOCK,A.NODE_PRICE ");
		if (!StringUtils.isNullOrEmpty(value) && value.equals(DictCodeConstants.DICT_IS_YES)) {
			sb.append(",B.PART_INFIX,F.POS_CODE,E.POS_NAME");
		} else {
			sb.append(",'' as PART_INFIX,'' as POS_CODE,'' as POS_NAME");
		}
		sb.append(" FROM TM_PART_STOCK A LEFT OUTER JOIN (").append(CommonConstants.VM_PART_INFO).append(") B")
				.append(" ON ( A.DEALER_CODE = B.DEALER_CODE AND A.PART_NO= B.PART_NO  ) ")
				.append(" LEFT OUTER JOIN (select DEALER_CODE, part_no, sum(C.STOCK_QUANTITY) AS OPTION_STOCK FROM TM_PART_STOCK C")
				.append(" WHERE DEALER_CODE='").append(FrameworkUtil.getLoginInfo().getDealerCode())
				.append("' AND D_KEY=").append(CommonConstants.D_KEY).append(" GROUP BY DEALER_CODE,PART_NO ) D ")
				.append(" ON ( A.DEALER_CODE = D.DEALER_CODE AND A.PART_NO= D.PART_NO AND B.PART_NO = D.PART_NO )");
		if (!StringUtils.isNullOrEmpty(value) && value.equals(DictCodeConstants.DICT_IS_YES)) {
			sb.append(
					" LEFT JOIN TW_POS_INFIX_RELATION F ON A.DEALER_CODE = F.DEALER_CODE AND B.PART_INFIX  = F.PART_INFIX AND F.IS_VALID = ")
					.append(DictCodeConstants.DICT_IS_YES).append(" LEFT JOIN TW_MALFUNCTION_POSITION E ON e.is_valid=")
					.append(DictCodeConstants.DICT_IS_YES)
					.append(" and A.DEALER_CODE = E.DEALER_CODE AND F.POS_CODE = E.POS_CODE ")
					.append(" LEFT JOIN TM_STORAGE TS ON A.DEALER_CODE = TS.DEALER_CODE AND A.STORAGE_CODE=TS.STORAGE_CODE ")
					.append(" WHERE A.DEALER_CODE = '").append(FrameworkUtil.getLoginInfo().getDealerCode())
					.append("'  AND A.D_KEY = ").append(CommonConstants.D_KEY);
			Utility.sqlToLike(sb, queryParam.get("PART_MODEL_GROUP_CODE_SET"), "PART_MODEL_GROUP_CODE_SET", "A");
			if (!StringUtils.isNullOrEmpty(queryParam.get("STORAGE_CODE"))) {
				Utility.sqlToEquals(sb, queryParam.get("STORAGE_CODE"), "STORAGE_CODE", "A");
			} else {
				sb.append(" and  1 = 1 ");
			}
			Utility.sqlToLike(sb, queryParam.get("PART_NO"), "PART_NO", "A");
			Utility.sqlToLike(sb, queryParam.get("PART_NAME"), "PART_NAME", "A");
			if (DictCodeConstants.DICT_IS_NO.equals(value2)) {
				sb.append(" and TS.CJ_TAG=").append(DictCodeConstants.DICT_IS_YES);
			} else {
				sb.append(" and  1 = 1 ");
			}
			if (!StringUtils.isNullOrEmpty(queryParam.get("PART_GROUP_CODE"))) {
				Utility.sqlToEquals(sb, queryParam.get("PART_GROUP_CODE"), "PART_GROUP_CODE", "B");
			} else {
				sb.append(" and  1 = 1 ");
			}
			Utility.sqlToLike(sb, queryParam.get("STORAGE_POSITION_CODE"), "STORAGE_POSITION_CODE", "A");
			Utility.sqlToLike(sb, queryParam.get("SPELL_CODE"), "SPELL_CODE", "A");
			if (!StringUtils.isNullOrEmpty(queryParam.get("SALE")) && queryParam.get("SALE").trim().length() > 0) {
				sb.append(" and A.SALES_PRICE > 0  ");
			} else {
				sb.append(" and  1 = 1 ");
			}
			Utility.sqlToLike(sb, queryParam.get("PART_NO"), "PART_NO", "B");
			if (!StringUtils.isNullOrEmpty(queryParam.get("STOCK")) && queryParam.get("STOCK").trim().length() > 0) {
				sb.append(" and (A.STOCK_QUANTITY + A.BORROW_QUANTITY - A.LEND_QUANTITY - A.LOCKED_QUANTITY)>0 ");
			} else {
				sb.append(" and  1 = 1 ");
			}
			if (!StringUtils.isNullOrEmpty(queryParam.get("BRAND")) && queryParam.get("BRAND").trim().length() > 0) {
				sb.append(" and B.BRAND =  '").append(queryParam.get("BRAND")).append("' ");
			} else {
				sb.append(" and  1 = 1 ");
			}
			if (!StringUtils.isNullOrEmpty(queryParam.get("PART_INFIX_NAME"))
					&& queryParam.get("PART_INFIX_NAME").trim().length() > 0) {
				sb.append(" and B.PART_INFIX_NAME like '").append(queryParam.get("PART_INFIX_NAME")).append("%' ");
			}
			Utility.sqlToLike(sb, queryParam.get("REMARK"), "REMARK", "A");
			if (DictCodeConstants.DICT_IS_YES.equals(queryParam.get("JUDGE_REPAIR_PARTS"))) {
				sb.append(" AND A.PART_STATUS<>").append(queryParam.get("JUDGE_REPAIR_PARTS")).append(" ");
			}
			if (!StringUtils.isNullOrEmpty(queryParam.get("PART_STATUS"))) {
				if (DictCodeConstants.DICT_IS_YES.equals(queryParam.get("PART_STATUS").trim())) {
					// 查詢停用的配件
					sb.append(" AND A.PART_STATUS=").append(queryParam.get("PART_STATUS").trim()).append(" ");
				} else {
					// 查沒有停用的
					sb.append(" AND A.PART_STATUS<>").append(DictCodeConstants.DICT_IS_YES).append(" ");
				}
			}
			if (!StringUtils.isNullOrEmpty(queryParam.get("IS_STOP_AND_ZERO"))) {
				if (DictCodeConstants.DICT_IS_YES.equals(queryParam.get("IS_STOP_AND_ZERO"))) {
					// 主数据停用本地库存为零的
					sb.append(" AND (B.PART_STATUS=").append(DictCodeConstants.DICT_IS_YES)
							.append(" AND  (A.STOCK_QUANTITY=0 OR A.STOCK_QUANTITY is null )) ");
				}
			}
			// 增加条件是否获取所有仓库配件
			if (!StringUtils.isNullOrEmpty(queryParam.get("ALL_PART"))
					|| DictCodeConstants.DICT_IS_NO.equals(queryParam.get("ALL_PART"))) {
				String[] stoC = Utility.getStorageByUserId(FrameworkUtil.getLoginInfo().getUserId()).split(",");
				sb.append(" AND ( 1=2 ");
				for (int i = 0; i < stoC.length; i++) {
					if (stoC[i] != null && !"".equals(stoC[i].trim())) {
						sb.append(" OR A.STORAGE_CODE=").append(stoC[i]).append(" ");
					}
				}
				sb.append(" ) ");
			}
			if (!StringUtils.isNullOrEmpty(queryParam.get("PART_MAIN_TYPE"))) {
				sb.append(" AND A.PART_MAIN_TYPE=").append(queryParam.get("PART_MAIN_TYPE")).append(" ");
			}
		}
		return DAOUtil.pageQuery(sb.toString(), null);
	}

	@Override
	public PageInfoDto findAllPartInfoC(Map<String, String> queryParam) {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT '' as POS_CODE,'' as POS_NAME,A.PART_MODEL_GROUP_CODE_SET as '舍弃的', 0 ")
				.append(" as PART_QUANTITY,  A.DEALER_CODE, A.PART_NO, A.STORAGE_CODE, A.STORAGE_POSITION_CODE, A.PART_NAME, ")
				.append(" A.SPELL_CODE, A.UNIT_CODE, A.STOCK_QUANTITY, A.SALES_PRICE, A.CLAIM_PRICE, A.PART_GROUP_CODE, ")
				.append(" B.LIMIT_PRICE, A.LATEST_PRICE, A.COST_PRICE AS COST_PRICE, A.COST_AMOUNT, A.MAX_STOCK,B.DOWN_TAG, ")
				.append(" A.BORROW_QUANTITY, A.LEND_QUANTITY, A.LOCKED_QUANTITY, A.LAST_STOCK_IN, A.PART_STATUS, ")
				.append(" A.LAST_STOCK_OUT, A.REMARK, A.CREATED_BY, A.CREATED_AT, A.UPDATED_BY, A.UPDATED_AT, A.VER, A.MIN_STOCK, ")
				.append(" B.OPTION_NO, B.PART_MODEL_GROUP_CODE_SET, A.NODE_PRICE, B.PLAN_PRICE, B.OEM_LIMIT_PRICE, B.URGENT_PRICE, ")
				.append(" B.INSTRUCT_PRICE,A.INSURANCE_PRICE, D.OPTION_STOCK, (A.STOCK_QUANTITY + A.BORROW_QUANTITY - A.LEND_QUANTITY - A.LOCKED_QUANTITY) AS USEABLE_STOCK,B.IS_BACK,B.PART_INFIX, ")
				.append(" B.MIN_LIMIT_PRICE ").append(" FROM TM_PART_STOCK A  LEFT OUTER JOIN (")
				.append(CommonConstants.VM_PART_INFO).append(") B")
				.append(" ON ( A.DEALER_CODE = B.DEALER_CODE AND A.PART_NO= B.PART_NO ) ")
				.append(" LEFT OUTER JOIN (select DEALER_CODE, part_no, sum(C.STOCK_QUANTITY) AS OPTION_STOCK FROM TM_PART_STOCK C GROUP BY DEALER_CODE,PART_NO ) D ")
				.append(" ON ( A.DEALER_CODE = D.DEALER_CODE AND A.PART_NO= D.PART_NO AND B.OPTION_NO = D.PART_NO )")
				.append(" WHERE A.DEALER_CODE = '").append(FrameworkUtil.getLoginInfo().getDealerCode())
				.append("' AND A.D_KEY = ").append(CommonConstants.D_KEY);
		Utility.sqlToEquals(sb, queryParam.get("PART_NO"), "PART_NO", "A");
		Utility.sqlToEquals(sb, queryParam.get("STORAGE_CODE"), "STORAGE_CODE", "A");
		return DAOUtil.pageQuery(sb.toString(), null);
	}

	@Override
	public List<Map> findPartModelGroup() {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT DEALER_CODE,PART_MODEL_GROUP_CODE,PART_MODEL_GROUP_NAME FROM TM_PART_MODEL_GROUP");
		return DAOUtil.findAll(sb.toString(), null);
	}

	@Override
	public List<Map> findStorageCode() {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT DISTINCT A.* FROM  (").append(CommonConstants.VM_STORAGE)
				.append(") A LEFT JOIN tm_user_ctrl B ").append(" ON B.DEALER_CODE = A.DEALER_CODE ")
				.append(" AND '4010'||A.STORAGE_CODE = B.CTRL_CODE")
				.append(" WHERE A.DEALER_CODE = ? AND B.USER_ID = ? and A.STORAGE_NAME is not null and A.STORAGE_NAME !='' ");
		List queryParam = new ArrayList();
		queryParam.add(FrameworkUtil.getLoginInfo().getDealerCode());
		queryParam.add(FrameworkUtil.getLoginInfo().getUserId());
		return DAOUtil.findAll(sb.toString(), queryParam);
	}

	@Override
	public String btnSave(ReportPayOffDTO dto) {
		Double amount = 0.0;// 获取总金额
		for (Map map : dto.getPartProfitItemList()) {
			amount += Double.parseDouble(map.get("PROFIT_AMOUNT").toString());
		}
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		String id = "";
		if (StringUtils.isNullOrEmpty(dto.getProfitNo())) {// 表示新增
			// 对报溢登记单操作
			id = commonNoService.getSystemOrderNo(CommonConstants.PART_Profit_PREFIX);
			TtPartProfitPO partProfitPO = new TtPartProfitPO();
			partProfitPO.setString("DEALER_CODE", dealerCode);
			partProfitPO.setString("PROFIT_NO", id);
			partProfitPO.setString("INVENTORY_NO", dto.getInventoryNo());
			partProfitPO.setString("HANDLER", dto.getHandler());
			partProfitPO.set("PROFIT_DATE", dto.getOrderDate());
			partProfitPO.setDouble("TOTAL_AMOUNT", amount);
			/**
			 * 保存时不记入帐时间和是否入帐字段
			 */
			partProfitPO.setString("LOCK_USER", FrameworkUtil.getLoginInfo().getUserId().toString());
			partProfitPO.saveIt();

			dto.setProfitNo(id);

			// 新增子表
			addPartProfitItem(dto);
		} else {// 表示修改
				// 修改主表记录
			TtPartProfitPO partProfitPO = TtPartProfitPO.findByCompositeKeys(dealerCode, dto.getProfitNo());
			if (!StringUtils.isNullOrEmpty(partProfitPO) && partProfitPO.getInteger("D_KEY") == CommonConstants.D_KEY) {
				partProfitPO.setString("INVENTORY_NO", dto.getInventoryNo());
				partProfitPO.setString("HANDLER", dto.getHandler());
				partProfitPO.setDouble("TOTAL_AMOUNT", amount);
				partProfitPO.set("PROFIT_DATE", dto.getOrderDate());
				partProfitPO.setString("LOCK_USER", FrameworkUtil.getLoginInfo().getUserId().toString());
				partProfitPO.saveIt();
			}
			id = dto.getProfitNo();

			// 删除子表
			delPartProfitItem(dto,false);
			// 新增子表
			addPartProfitItem(dto);
		}
		return id;
	}

	/**
	 * 新增子表方法
	 */
	public void addPartProfitItem(ReportPayOffDTO dto) {
		for (Map map : dto.getPartProfitItemList()) {
			map.put("STORAGE_CODE", map.get("STORAGE_NAME").toString());
			TtPartProfitItemPO partProfitItemPO = new TtPartProfitItemPO();
			partProfitItemPO.setString("DEALER_CODE", FrameworkUtil.getLoginInfo().getDealerCode());
			partProfitItemPO.setString("PROFIT_NO", dto.getProfitNo());
			if (!StringUtils.isNullOrEmpty(map.get("STORAGE_POSITION_CODE"))) {
				partProfitItemPO.setString("STORAGE_POSITION_CODE", map.get("STORAGE_POSITION_CODE").toString());
			}
			if (!StringUtils.isNullOrEmpty(map.get("STORAGE_CODE"))) {
				partProfitItemPO.setString("STORAGE_CODE", map.get("STORAGE_CODE").toString());
			}
			if (!StringUtils.isNullOrEmpty(map.get("PART_BATCH_NO"))) {
				partProfitItemPO.setString("PART_BATCH_NO", map.get("PART_BATCH_NO").toString());
			}
			if (!StringUtils.isNullOrEmpty(map.get("PART_NO"))) {
				partProfitItemPO.setString("PART_NO", map.get("PART_NO").toString());
			}
			if (!StringUtils.isNullOrEmpty(map.get("PART_NAME"))) {
				partProfitItemPO.setString("PART_NAME", map.get("PART_NAME").toString());
			}
			if (!StringUtils.isNullOrEmpty(map.get("UNIT_CODE"))) {
				partProfitItemPO.setString("UNIT_CODE", map.get("UNIT_CODE").toString());
			}
			if (!StringUtils.isNullOrEmpty(map.get("PROFIT_QUANTITY"))) {
				partProfitItemPO.setString("PROFIT_QUANTITY", map.get("PROFIT_QUANTITY").toString());
			}
			if (!StringUtils.isNullOrEmpty(map.get("PROFIT_PRICE"))) {
				partProfitItemPO.setString("PROFIT_PRICE", map.get("PROFIT_PRICE").toString());
			}
			if (!StringUtils.isNullOrEmpty(map.get("PROFIT_AMOUNT"))) {
				partProfitItemPO.setString("PROFIT_AMOUNT", map.get("PROFIT_AMOUNT").toString());
			}

			// 查询成本金额,成本单价
			TmPartStockPO model = TmPartStockPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),
					map.get("PART_NO"), map.get("STORAGE_CODE"));
			if (!StringUtils.isNullOrEmpty(model)) {
				if (!StringUtils.isNullOrEmpty(model.getDouble("COST_PRICE"))) {
					partProfitItemPO.setString("COST_PRICE", model.getDouble("COST_PRICE"));
				}
				if (!StringUtils.isNullOrEmpty(model.getDouble("COST_AMOUNT"))) {
					partProfitItemPO.setString("COST_AMOUNT", model.getDouble("COST_AMOUNT"));
				}
			}
			partProfitItemPO.saveIt();
		}

	}

	/**
	 * 删除子表方法
	 * 
	 * @param dto
	 */
	public void delPartProfitItem(ReportPayOffDTO dto,Boolean flag) {
		TtPartProfitItemPO.delete("DEALER_CODE=? AND PROFIT_NO=?", FrameworkUtil.getLoginInfo().getDealerCode(),
				dto.getProfitNo());
		List<TtPartProfitItemPO> list = TtPartProfitItemPO.findBySQL(
				"SELECT * FROM TT_PART_PROFIT_ITEM WHERE DEALER_CODE = ? AND PROFIT_NO = ?",
				FrameworkUtil.getLoginInfo().getDealerCode(), dto.getProfitNo());
		if (list.size() == 0&&flag) {// 表示子表没有数据,删除主表
			TtPartProfitPO.delete("DEALER_CODE=? AND PROFIT_NO=?", FrameworkUtil.getLoginInfo().getDealerCode(),
					dto.getProfitNo());
			// 记录日志
			handleOperateLog("配件报溢单删除：配件报溢单号[" + dto.getProfitNo() + "]",
					"TT_PART_PROFIT,PROFIT_NO=" + dto.getProfitNo(),
					Integer.parseInt(DictCodeConstants.DICT_ASCLOG_PART_MANAGE),
					FrameworkUtil.getLoginInfo().getEmployeeNo());
		}
	}

	/**
	 * 配件发料价格修改进行的日志操作
	 * 
	 * @param remark
	 * @param operateType
	 * @param operator
	 */
	public void handleOperateLog(String content, String remark, int operateType, String operator) {
		OperateLogPO operateLogPO = new OperateLogPO();
		operateLogPO.setString("OPERATE_CONTENT", content);
		operateLogPO.setInteger("OPERATE_TYPE", operateType);
		operateLogPO.setString("OPERATOR", operator);
		operateLogPO.setString("DEALER_CODE", FrameworkUtil.getLoginInfo().getDealerCode());
		operateLogPO.setString("REMARK", remark);
		operateLogPO.saveIt();
	}

	@Override
	public String btnAccount(ReportPayOffDTO dto) throws ServiceBizException {
		String[] users = {dto.getProfitNo()};
		//解锁
		Utility.updateByUnLock("Tt_Part_Profit", FrameworkUtil.getLoginInfo().getUserId().toString(), "PROFIT_NO", users, "LOCK_USER");
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		Map<String, String> para = new HashMap<String, String>();
		for (Map map : dto.getPartProfitItemList()) {
			map.put("STORAGE_CODE", map.get("STORAGE_NAME"));
			// 查询配件销售是否形成负库存
			TmPartStockPO partStockPO = TmPartStockPO.findByCompositeKeys(dealerCode, map.get("PART_NO"),
					map.get("STORAGE_CODE"));
			if (!StringUtils.isNullOrEmpty(partStockPO) && partStockPO.getInteger("D_KEY") == CommonConstants.D_KEY) {
				List<Map> stockItem = queryStockItem(-Float.parseFloat(objToStr(map.get("PROFIT_QUANTITY"))),
						objToStr(map.get("PART_NO")), objToStr(map.get("STORAGE_CODE")));
				if (stockItem.size() > 0) {
					for (Map map2 : stockItem) {
						map2.put("PART_QUANTITY", -Float.parseFloat(objToStr(map2.get("PART_QUANTITY"))));
					}
				}
				// 查询本月的报表是否完成
				List<Map> thisMonth = Utility.isFinishedThisMonth();
				if (thisMonth.size() > 0) {
					// 查询当前时间的会计周期是否做过月结
					List<Map> isFinished = Utility.getIsFinished();
					if (isFinished.size() <= 0) {
						throw new ServiceBizException("当前时间的会计周期未做过月结");
					}
				} else {
					throw new ServiceBizException("本月的报表未完成");
				}
			} else {
				throw new ServiceBizException(
						"配件库存中没有该配件信息[" + map.get("PART_NO") + "],仓库代码为:" + map.get("STORAGE_CODE"));
			}
		}
		// 配件报溢入账
		double costAmountBeforeA = 0; // 批次表入账前成本
		double costAmountBeforeB = 0; // 库存表入账前成本
		double costAmountAfterA = 0; // 批次表入账后成本
		double costAmountAfterB = 0; // 库存表入账后成本
		String empNo = FrameworkUtil.getLoginInfo().getEmployeeNo();
		if (!StringUtils.isNullOrEmpty(dto.getProfitNo())) {
			List<Map> listcheck = getNonOemPartList("TT_PART_PROFIT_ITEM", "PROFIT_NO",
					dto.getProfitNo());
			String errPart = "";
			if (listcheck.size() > 0) {
				for (int i = 0; i < listcheck.size(); i++) {
					Map map1 = listcheck.get(i);
					if (!StringUtils.isNullOrEmpty(map1.get("PART_NO"))) {
						errPart = errPart + "," + objToStr(map1.get("PART_NO"));
					}
				}
			}
			if (!errPart.equals("")) {
				throw new ServiceBizException("保存错误:" + errPart + " 非OEM配件不允许入OEM库,请重新操作!");
			} else {
				// 相同账号分别调出同一张未入账的调拨入库单，点入账造成流水账中该入库单，有2条重复的流水账记录,
				// 判断该入库单是否已经入账
				String sheetNoType = "PROFIT_NO";
				String sheetType = "TT_PART_PROFIT";
				String flag = checkIsFinished(dto.getProfitNo(), sheetType, sheetNoType);
				if (flag.equals(DictCodeConstants.DICT_IS_YES)) {
					throw new ServiceBizException("报溢单号:" + dto.getProfitNo() + "已经入账，不能重复入账");
				}
				para.put("STOCK_IN_NO", dto.getProfitNo());
				para.put("FLAG", "3");// 配件报溢入账上报DCS
				// 1、修改报溢单里是否入账字段为是
				TtPartProfitPO partProfitPO = TtPartProfitPO.findByCompositeKeys(dealerCode,
						dto.getProfitNo());
				partProfitPO.setString("IS_FINISHED", DictCodeConstants.DICT_IS_YES);
				partProfitPO.set("FINISHED_DATE", new Timestamp(System.currentTimeMillis()));
				partProfitPO.saveIt();
				// 2、 如果盘点单号不为空修改盘点单里报溢标志为做过报溢
				if (!StringUtils.isNullOrEmpty(dto.getInventoryNo())) {
					TtPartInventoryPO partInventoryPO = TtPartInventoryPO
							.findByCompositeKeys(dealerCode, dto.getInventoryNo());
					if (!StringUtils.isNullOrEmpty(partInventoryPO)
							&& partInventoryPO.getInteger("D_KEY") == CommonConstants.D_KEY) {
						partInventoryPO.setLong("PROFIT_TAG", DictCodeConstants.IS_YES);
						partInventoryPO.saveIt();
					}
				}
				// 查询报溢明细里信息
				List<TtPartProfitItemPO> findBySQL = TtPartProfitItemPO.findBySQL(
						"SELECT * FROM TT_PART_PROFIT_ITEM WHERE DEALER_CODE = ? AND PROFIT_NO = ? AND D_KEY = ?",
						dealerCode, dto.getProfitNo(), CommonConstants.D_KEY);
				if (findBySQL.size() > 0) {
					for (TtPartProfitItemPO ttPartProfitItemPO : findBySQL) {
						double costPriceStock = 0;// 库存成本单价
						double costAmountStock = 0;// 库存成本金额
						float stockQuantity = 0;// 库存数量
						float stockQuantityNew = 0;// 入帐修改后的库存数量
						double costPriceNew = 0;// 入帐修改后的库存成本单价
						double itemCostAmount = 0;// 业务单据领料出库成本金额
						double itemCostPrice = 0;// 业务单句出库成本单价
						costAmountBeforeA = 0; // 批次表入账前成本
						costAmountBeforeB = 0; // 库存表入账前成本
						costAmountAfterA = 0; // 批次表入账后成本
						costAmountAfterB = 0; // 库存表入账后成本

						List<TmPartStockItemPO> list = TmPartStockItemPO.find(
								"DEALER_CODE = ? AND D_KEY = ? AND PART_NO = ? AND STORAGE_CODE = ?",
								dealerCode, CommonConstants.D_KEY,
								ttPartProfitItemPO.getString("PART_NO"),
								ttPartProfitItemPO.getString("STORAGE_CODE"));
						if (list.size() > 0) {
							for (TmPartStockItemPO tmPartStockItemPO : list) {
								if (!StringUtils
										.isNullOrEmpty(tmPartStockItemPO.getDouble("COST_PRICE"))) {
									costPriceStock = tmPartStockItemPO.getDouble("COST_PRICE");// 入帐前库存成本单价
									itemCostPrice = costPriceStock;
								}
								if (!StringUtils
										.isNullOrEmpty(tmPartStockItemPO.getDouble("COST_AMOUNT"))) {
									costAmountStock = tmPartStockItemPO.getDouble("COST_AMOUNT");// 入帐前库存成本金额
									costAmountBeforeA = tmPartStockItemPO.getDouble("COST_AMOUNT");// 批次表入帐前成本金额
									itemCostAmount = tmPartStockItemPO.getDouble("PROFIT_QUANTITY")
											* tmPartStockItemPO.getDouble("PROFIT_PRICE");// 本次出入库成本金额
									itemCostAmount = Utility.round(Double.toString(itemCostAmount), 2);
								}
								if (!StringUtils
										.isNullOrEmpty(tmPartStockItemPO.getDouble("COST_PRICE"))) {
									stockQuantity = tmPartStockItemPO.getFloat("STOCK_QUANTITY");
								}
							}
						}

						TmPartStockPO stockPO = TmPartStockPO.findByCompositeKeys(dealerCode,
								ttPartProfitItemPO.getString("PART_NO"),
								ttPartProfitItemPO.getString("STORAGE_CODE"));
						if (!StringUtils.isNullOrEmpty(stockPO)
								&& stockPO.getInteger("D_KEY") == CommonConstants.D_KEY) {
							if (!StringUtils.isNullOrEmpty(stockPO.getDouble("COST_AMOUNT"))) {
								costAmountBeforeB = stockPO.getDouble("COST_AMOUNT");// 库存表入账前成本金额
							}
						}

						// 修改报溢单明细中成本单价成本金额-
						TtPartProfitItemPO partProfitItemPO = TtPartProfitItemPO.findFirst(
								"DEALER_CODE = ? AND D_KEY = ? AND ITEM_ID = ?", dealerCode,
								CommonConstants.D_KEY, ttPartProfitItemPO.getString("ITEM_ID"));
						partProfitItemPO.setDouble("COST_PRICE", itemCostPrice);
						partProfitItemPO.setDouble("COST_AMOUNT", itemCostAmount);
						partProfitItemPO.saveIt();

						// 更新库存批次表和库存表中库存数量成本单价成本金额

						TmPartStockItemPO partStockItemPO = new TmPartStockItemPO();
						partStockItemPO.setString("PART_NO", ttPartProfitItemPO.getString("PART_NO"));
						if (!StringUtils.isNullOrEmpty(ttPartProfitItemPO.getString("PART_BATCH_NO"))) {
							partStockItemPO.setString("PART_BATCH_NO",
									ttPartProfitItemPO.getString("PART_BATCH_NO"));
						} else {
							partStockItemPO.setString("PART_BATCH_NO",
									CommonConstants.defaultBatchName);
						}
						partStockItemPO.setString("STORAGE_CODE",
								ttPartProfitItemPO.getString("STORAGE_CODE"));
						partStockItemPO.setFloat("STOCK_QUANTITY",
								ttPartProfitItemPO.getFloat("STOCK_QUANTITY"));
						partStockItemPO.setDouble("COST_PRICE",
								ttPartProfitItemPO.getDouble("COST_PRICE"));
						partStockItemPO.setDouble("COST_AMOUNT",
								ttPartProfitItemPO.getDouble("COST_AMOUNT"));
						partStockItemPO.setDouble("LATEST_PRICE",
								ttPartProfitItemPO.getDouble("LATEST_PRICE"));// 入库成本价:报溢单价(使用批次)
						// 328
						int ret = calCostPriceOut(partStockItemPO);
						if (ret > 0) {
							TmPartStockPO StockAfter = TmPartStockPO.findByCompositeKeys(dealerCode,
									ttPartProfitItemPO.getString("PART_NO"),
									ttPartProfitItemPO.getString("STORAGE_CODE"));
							if (!StringUtils.isNullOrEmpty(StockAfter)
									&& StockAfter.getInteger("D_KEY") == CommonConstants.D_KEY) {
								if (!StringUtils.isNullOrEmpty(StockAfter.getDouble("COST_AMOUNT"))) {
									costAmountAfterB = StockAfter.getDouble("COST_AMOUNT"); // 库存表入账后成本金额
								}
							}
							List<TmPartStockItemPO> listItemAfter = TmPartStockItemPO.find(
									"DEALER_CODE = ? AND D_KEY = ? AND PART_NO = ? AND STORAGE_CODE = ?",
									dealerCode, CommonConstants.D_KEY,
									ttPartProfitItemPO.getString("PART_NO"),
									ttPartProfitItemPO.getString("STORAGE_CODE"));
							if (listItemAfter.size() > 0) {
								TmPartStockItemPO itemAfter = listItemAfter.get(0);
								if (!StringUtils.isNullOrEmpty(itemAfter.getDouble("COST_AMOUNT"))) {
									costAmountAfterA = itemAfter.getDouble("COST_AMOUNT"); // 批次表入账后成本金额
								}
							}
							// 4、向配件流水账增加一条记录
							PartFlowPO partFlowPO = new PartFlowPO();
							partFlowPO.setString("DEALER_CODE", dealerCode);
							partFlowPO.setString("STORAGE_CODE",
									ttPartProfitItemPO.getString("STORAGE_CODE"));
							partFlowPO.setString("PART_NO", ttPartProfitItemPO.getString("PART_NO"));
							partFlowPO.setString("PART_BATCH_NO",
									ttPartProfitItemPO.getString("PART_BATCH_NO"));
							partFlowPO.setString("PART_NAME",
									ttPartProfitItemPO.getString("PART_NAME"));
							partFlowPO.setString("SHEET_NO", dto.getProfitNo());
							partFlowPO.setInteger("IN_OUT_TYPE",
									Integer.parseInt(DictCodeConstants.DICT_IN_OUT_TYPE_PART_PROFIT));
							partFlowPO.setLong("IN_OUT_TAG", DictCodeConstants.IS_NOT);
							partFlowPO.setFloat("STOCK_IN_QUANTITY",
									ttPartProfitItemPO.getFloat("PROFIT_QUANTITY"));
							partFlowPO.setString("OPERATOR", empNo);
							double amount = Utility.add("1", Utility.getDefaultValue("2034"));
							String rate = Double.toString(amount);
							partFlowPO.set("OPERATE_DATE", new Timestamp(System.currentTimeMillis()));
							partFlowPO.setDouble("IN_OUT_TAXED_PRICE",
									Utility.mul(
											Double.toString(
													ttPartProfitItemPO.getDouble("PROFIT_PRICE")),
											rate));// 出入库含税单价
							partFlowPO.setDouble("IN_OUT_TAXED_AMOUNT",
									Utility.mul(
											Double.toString(
													ttPartProfitItemPO.getDouble("PROFIT_AMOUNT")),
											rate));// 出入库含税金额
							partFlowPO.setDouble("IN_OUT_NET_PRICE",
									ttPartProfitItemPO.getDouble("PROFIT_PRICE"));// 出入库不含税单价
							partFlowPO.setDouble("IN_OUT_NET_AMOUNT",
									ttPartProfitItemPO.getDouble("PROFIT_AMOUNT"));// 出入库不含税金额
							partFlowPO.setFloat("STOCK_IN_QUANTITY",
									ttPartProfitItemPO.getFloat("PROFIT_QUANTITY"));// 进数量
							partFlowPO.setDouble("COST_PRICE", itemCostPrice);
							partFlowPO.setDouble("COST_AMOUNT", itemCostAmount);

							String bno = "";
							if (!StringUtils
									.isNullOrEmpty(ttPartProfitItemPO.getString("PART_BATCH_NO"))) {
								bno = ttPartProfitItemPO.getString("PART_BATCH_NO");
							} else {
								bno = CommonConstants.defaultBatchName;
							}
							TmPartStockItemPO itemPO = TmPartStockItemPO.findByCompositeKeys(dealerCode,
									ttPartProfitItemPO.getString("PART_NO"),
									ttPartProfitItemPO.getString("STORAGE_CODE"), bno);
							if (!StringUtils.isNullOrEmpty(itemPO)) {
								stockQuantityNew = itemPO.getFloat("STOCK_QUANTITY");
								partFlowPO.setFloat("STOCK_QUANTITY", stockQuantityNew);
								costPriceNew = itemPO.getDouble("COST_PRICE");
							}
							partFlowPO.setDouble("COST_AMOUNT_BEFORE_A", costAmountBeforeA);
							partFlowPO.setDouble("COST_AMOUNT_BEFORE_B", costAmountBeforeB);
							partFlowPO.setDouble("COST_AMOUNT_AFTER_A", costAmountAfterA);
							partFlowPO.setDouble("COST_AMOUNT_AFTER_B", costAmountAfterB);
							partFlowPO.saveIt();

							// 自然月结报表
							TtPartMonthReportPO partMonthReportPO = TtPartMonthReportPO
									.findByCompositeKeys(dealerCode, Utility.getYear(),
											Utility.getMonth(),
											ttPartProfitItemPO.getString("STORAGE_CODE"), bno,
											ttPartProfitItemPO.getString("PART_NO"));
							if (StringUtils.isNullOrEmpty(partMonthReportPO)) {// 新增
								partMonthReportPO = new TtPartMonthReportPO();
								partMonthReportPO.setString("DEALER_CODE", dealerCode);
								partMonthReportPO.setString("REPORT_YEAR", Utility.getYear());
								partMonthReportPO.setString("REPORT_MONTH", Utility.getMonth());
								partMonthReportPO.setString("STORAGE_CODE",
										ttPartProfitItemPO.getString("STORAGE_CODE"));
								partMonthReportPO.setString("PART_BATCH_NO", bno);
								partMonthReportPO.setString("PART_NO",
										ttPartProfitItemPO.getString("PART_NO"));
								partMonthReportPO.setString("PART_NAME",
										ttPartProfitItemPO.getString("PART_NAME"));
								partMonthReportPO.setDouble("OPEN_QUANTITY", stockQuantity);// 入帐前库存数量
								partMonthReportPO.setDouble("OPEN_PRICE", costPriceStock);// 入帐钱库存成本单价
								partMonthReportPO.setDouble("OPEN_AMOUNT", costAmountStock);// 入帐前成本金额
								// partMonthReportPO.setInteger("D_KEY",
								// CommonConstants.D_KEY);
							}
							partMonthReportPO.setFloat("IN_QUANTITY",
									!StringUtils.isNullOrEmpty(partMonthReportPO.getFloat("IN_QUANTITY"))?partMonthReportPO.getFloat("IN_QUANTITY"):0 + Math
											.round(ttPartProfitItemPO.getFloat("PROFIT_QUANTITY") * 100)
											* 0.01);
							partMonthReportPO.setDouble("STOCK_IN_AMOUNT",
									!StringUtils.isNullOrEmpty(partMonthReportPO.getDouble("STOCK_IN_AMOUNT"))?partMonthReportPO.getDouble("STOCK_IN_AMOUNT"):0
											+ Math.round(itemCostAmount * 100) * 0.01);// 本次入库成本金额
							partMonthReportPO.setDouble("INVENTORY_QUANTITY",
									partMonthReportPO.getDouble("INVENTORY_QUANTITY"));
							partMonthReportPO.setDouble("INVENTORY_AMOUNT",
									partMonthReportPO.getDouble("INVENTORY_AMOUNT"));
							partMonthReportPO.setFloat("OUT_QUANTITY",
									partMonthReportPO.getFloat("OUT_QUANTITY"));
							partMonthReportPO.setDouble("OUT_AMOUNT",
									partMonthReportPO.getDouble("OUT_AMOUNT"));
							partMonthReportPO.setDouble("CLOSE_QUANTITY",
									!StringUtils.isNullOrEmpty(partMonthReportPO.getDouble("CLOSE_QUANTITY"))?partMonthReportPO.getDouble("CLOSE_QUANTITY"):0 + Math
											.round(ttPartProfitItemPO.getFloat("PROFIT_QUANTITY") * 100)
											* 0.01);
							partMonthReportPO.setDouble("CLOSE_PRICE", costPriceNew);// 入帐后成本单价
							partMonthReportPO.setDouble("CLOSE_AMOUNT",
									!StringUtils.isNullOrEmpty(partMonthReportPO.getDouble("CLOSE_AMOUNT"))?partMonthReportPO.getDouble("CLOSE_AMOUNT"):0
											+ Math.round(itemCostAmount * 100) * 0.01);
							partMonthReportPO.saveIt();

							// 会计月报表
							AccountPeriodPO cycle = AccountPeriodPO
									.findFirst("SYSDATE() BETWEEN BEGIN_DATE AND END_DATE");
							TtPartPeriodReportPO db = new TtPartPeriodReportPO();
							db.setString("PART_NAME", ttPartProfitItemPO.getString("PART_NAME"));
							db.setFloat("IN_QUANTITY", ttPartProfitItemPO.getFloat("PROFIT_QUANTITY"));
							db.setDouble("STOCK_IN_AMOUNT", itemCostAmount);
							db.setDouble("PROFIT_IN_AMOUNT",
									ttPartProfitItemPO.getDouble("PROFIT_AMOUNT"));
							db.setFloat("PROFIT_IN_COUNT",
									ttPartProfitItemPO.getFloat("PROFIT_QUANTITY"));
							db.setDouble("OPEN_PRICE", costPriceStock);// 入帐前成本单价
							db.setFloat("OPEN_QUANTITY", stockQuantity);// 入帐前库存数量
							db.setDouble("OPEN_AMOUNT", costAmountStock);// 入帐前成本金额
							db.setDouble("CLOSE_PRICE", costPriceNew);// 入帐后成本单价
							db.setString("STORAGE_CODE", ttPartProfitItemPO.getString("STORAGE_CODE"));
							db.setString("PART_BATCH_NO", bno);
							db.setString("PART_NO", ttPartProfitItemPO.getString("PART_NO"));
							addOrUpdateReport(bno, ttPartProfitItemPO.getString("PART_NO"),
									ttPartProfitItemPO.getString("STORAGE_CODE"), db, cycle);

							/**
							 * 配件报溢单凭证
							 */
							String defaultpo = Utility.getDefaultValue("8901");
							if (!StringUtils.isNullOrEmpty(defaultpo)
									&& defaultpo.equals(DictCodeConstants.DICT_IS_YES)) {// 获取开关设置
								Float cess = Float.parseFloat(Utility.getDefaultValue("1003"));
								TtPartProfitPO pp = TtPartProfitPO.findByCompositeKeys(dealerCode,
										dto.getProfitNo());
								if (!StringUtils.isNullOrEmpty(pp)
										&& pp.getInteger("D_KEY") == CommonConstants.D_KEY) {
									TtAccountsTransFlowPO po = new TtAccountsTransFlowPO();
									po.setString("ORG_CODE", dealerCode);
									po.set("TRANS_DATE", new Timestamp(System.currentTimeMillis()));
									po.setInteger("TRANS_TYPE", Integer.parseInt(
											DictCodeConstants.DICT_BUSINESS_TYPE_PART_PROFIT));
									po.setDouble("TAX_AMOUNT",
											pp.getDouble("TOTAL_AMOUNT") * (1F - cess));
									po.setDouble("NET_AMOUNT", pp.getDouble("TOTAL_AMOUNT"));// 总金额
																								// （不含税的）
									pp.setString("BUSINESS_NO", pp.getString("PROFIT_NO"));
									po.setString("SUB_BUSINESS_NO", pp.getString("INVENTORY_NO"));
									po.setLong("IS_VALID", DictCodeConstants.IS_YES);
									po.setInteger("EXEC_NUM", 0);
									po.saveIt();
								}
							}

							/**
							 * 追溯件报溢出 PartBackProfit
							 */
							// if
							// (objToStr(map.get("IS_BACK")).equals(DictCodeConstants.DICT_IS_YES))
							// {//表示追溯件
							// String isStockOut =
							// DictCodeConstants.DICT_IS_NO;
							// String partNo =
							// objToStr(map.get("PART_NO"));
							// String storageCode =
							// objToStr(map.get("STORAGE_CODE"));
							//
							// }
							/**
							 * SADMS027
							 */
						} else {
							throw new ServiceBizException("更新库存批次表和库存表失败");
						}
					}
				}
			}
		}
		return dto.getProfitNo();
	}

	/**
	 * 会计月报表
	 * 
	 * @param partPeriodReportPO
	 * @param cycle
	 */
	public void addOrUpdateReport(String partBatchNo, String partNo, String storageCode, TtPartPeriodReportPO db,
			AccountPeriodPO account) {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		TtPartPeriodReportPO dbd = TtPartPeriodReportPO.findByCompositeKeys(dealerCode, Utility.getMonth(),
				Utility.getYear(), storageCode, partBatchNo, partNo);
		if (StringUtils.isNullOrEmpty(dbd)) {// 新增
			StringBuffer sb = new StringBuffer();
			sb.append(
					" INSERT INTO TT_PART_PERIOD_REPORT (REPORT_YEAR, REPORT_MONTH,STORAGE_CODE, PART_BATCH_NO,PART_NO,DEALER_CODE,PART_NAME,  IN_QUANTITY, STOCK_IN_AMOUNT, BUY_IN_COUNT")
					.append(", BUY_IN_AMOUNT,ALLOCATE_IN_COUNT, ALLOCATE_IN_AMOUNT, OTHER_IN_COUNT,OTHER_IN_AMOUNT, PROFIT_IN_COUNT")
					.append(", PROFIT_IN_AMOUNT, OUT_QUANTITY, STOCK_OUT_COST_AMOUNT, OUT_AMOUNT, REPAIR_OUT_COUNT, REPAIR_OUT_COST_AMOUNT")
					.append(",REPAIR_OUT_SALE_AMOUNT, SALE_OUT_COUNT, SALE_OUT_COST_AMOUNT,SALE_OUT_SALE_AMOUNT, INNER_OUT_COUNT")
					.append(", INNER_OUT_COST_AMOUNT,INNER_OUT_SALE_AMOUNT, ALLOCATE_OUT_COUNT,ALLOCATE_OUT_COST_AMOUNT, ALLOCATE_OUT_SALE_AMOUNT, OTHER_OUT_COUNT, OTHER_OUT_COST_AMOUNT, OTHER_OUT_SALE_AMOUNT")
					.append(", LOSS_OUT_COUNT, LOSS_OUT_AMOUNT,TRANSFER_IN_COUNT,TRANSFER_IN_AMOUNT,TRANSFER_OUT_COUNT,TRANSFER_OUT_COST_AMOUNT, OPEN_QUANTITY, OPEN_PRICE")
					.append(", OPEN_AMOUNT,UPHOLSTER_OUT_COUNT,UPHOLSTER_OUT_COST_AMOUNT,UPHOLSTER_OUT_SALE_AMOUNT, CLOSE_QUANTITY,CLOSE_PRICE,CLOSE_AMOUNT ) ")
					.append("VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?, ?, ?,  ?, ?, ?,  ?, ?, ?,   ?, ?, ?,  ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,  ?)");
			new DB(TtPartPeriodReportPO.getMetaModel().getDbName()).exec(sb.toString(),
					Utility.fullSpaceBuffer2(account.getString("B_YEAR"), 4),
					Utility.fullSpaceBuffer2(account.getString("PERIODS"), 2), db.getString("STORAGE_CODE"),
					db.getString("PART_BATCH_NO"), db.getString("PART_NO"), dealerCode, db.getString("PART_NAME"),
					Math.round(checkIsNotNull(db.getFloat("IN_QUANTITY")) * 100) * 0.01,
					Math.round(checkIsNotNull(db.getDouble("STOCK_IN_AMOUNT")) * 100) * 0.01,
					Math.round(checkIsNotNull(db.getFloat("BUY_IN_COUNT")) * 100) * 0.01,
					Math.round(checkIsNotNull(db.getDouble("BUY_IN_AMOUNT")) * 100) * 0.01,
					Math.round(checkIsNotNull(db.getFloat("ALLOCATE_IN_COUNT")) * 100) * 0.01,
					Math.round(checkIsNotNull(db.getDouble("ALLOCATE_IN_AMOUNT")) * 100) * 0.01,
					Math.round(checkIsNotNull(db.getFloat("OTHER_IN_COUNT")) * 100) * 0.01,
					Math.round(checkIsNotNull(db.getDouble("OTHER_IN_AMOUNT")) * 100) * 0.01,
					Math.round(checkIsNotNull(db.getFloat("PROFIT_IN_COUNT")) * 100) * 0.01,
					Math.round(checkIsNotNull(db.getDouble("PROFIT_IN_AMOUNT")) * 100) * 0.01,
					Math.round(checkIsNotNull(db.getFloat("OUT_QUANTITY")) * 100) * 0.01,
					Math.round(checkIsNotNull(db.getDouble("STOCK_OUT_COST_AMOUNT")) * 100) * 0.01,
					Math.round(checkIsNotNull(db.getDouble("OUT_AMOUNT")) * 100) * 0.01,
					Math.round(checkIsNotNull(db.getFloat("REPAIR_OUT_COUNT")) * 100) * 0.01,
					Math.round(checkIsNotNull(db.getDouble("REPAIR_OUT_COST_AMOUNT")) * 100) * 0.01,
					Math.round(checkIsNotNull(db.getDouble("REPAIR_OUT_SALE_AMOUNT")) * 100) * 0.01,
					Math.round(checkIsNotNull(db.getFloat("SALE_OUT_COUNT")) * 100) * 0.01,
					Math.round(checkIsNotNull(db.getDouble("SALE_OUT_COST_AMOUNT")) * 100) * 0.01,
					Math.round(checkIsNotNull(db.getDouble("SALE_OUT_SALE_AMOUNT")) * 100) * 0.01,
					Math.round(checkIsNotNull(db.getFloat("INNER_OUT_COUNT")) * 100) * 0.01,
					Math.round(checkIsNotNull(db.getDouble("INNER_OUT_COST_AMOUNT")) * 100) * 0.01,
					Math.round(checkIsNotNull(db.getDouble("INNER_OUT_SALE_AMOUNT")) * 100) * 0.01,
					Math.round(checkIsNotNull(db.getFloat("ALLOCATE_OUT_COUNT")) * 100) * 0.01,
					Math.round(checkIsNotNull(db.getDouble("ALLOCATE_OUT_COST_AMOUNT")) * 100) * 0.01,
					Math.round(checkIsNotNull(db.getDouble("ALLOCATE_OUT_SALE_AMOUNT")) * 100) * 0.01,
					Math.round(checkIsNotNull(db.getFloat("OTHER_OUT_COUNT")) * 100) * 0.01,
					Math.round(checkIsNotNull(db.getDouble("OTHER_OUT_COST_AMOUNT")) * 100) * 0.01,
					Math.round(checkIsNotNull(db.getDouble("OTHER_OUT_SALE_AMOUNT")) * 100) * 0.01,
					Math.round(checkIsNotNull(db.getFloat("LOSS_OUT_COUNT")) * 100) * 0.01,
					Math.round(checkIsNotNull(db.getDouble("LOSS_OUT_AMOUNT")) * 100) * 0.01,
					Math.round(checkIsNotNull(db.getFloat("TRANSFER_IN_COUNT")) * 100) * 0.01,
					Math.round(checkIsNotNull(db.getDouble("TRANSFER_IN_AMOUNT")) * 100) * 0.01,
					Math.round(checkIsNotNull(db.getFloat("TRANSFER_OUT_COUNT")) * 100) * 0.01,
					Math.round(checkIsNotNull(db.getDouble("TRANSFER_OUT_COST_AMOUNT")) * 100) * 0.01,
					Math.round(checkIsNotNull(db.getFloat("OPEN_QUANTITY")) * 100) * 0.01,
					Math.round(checkIsNotNull(db.getDouble("OPEN_PRICE")) * 10000) * 0.0001,
					Math.round(checkIsNotNull(db.getDouble("OPEN_AMOUNT")) * 100) * 0.01,
					Math.round(checkIsNotNull(db.getFloat("UPHOLSTER_OUT_COUNT")) * 100) * 0.01,
					Math.round(checkIsNotNull(db.getDouble("UPHOLSTER_OUT_COST_AMOUNT")) * 100) * 0.01,
					Math.round(checkIsNotNull(db.getDouble("UPHOLSTER_OUT_SALE_AMOUNT")) * 100) * 0.01,
					getSubF(getAddF(db.getFloat("OPEN_QUANTITY"), db.getFloat("IN_QUANTITY")),
							db.getFloat("OUT_QUANTITY")),
					Math.round(checkIsNotNull(db.getDouble("CLOSE_PRICE")) * 10000) * 0.0001,
					getSubD(getAddD(db.getDouble("OPEN_AMOUNT"), db.getDouble("STOCK_IN_AMOUNT")),
							db.getDouble("STOCK_OUT_COST_AMOUNT")));
		} else {// 更新
			StringBuffer updates = new StringBuffer();
			updates.append(" IN_QUANTITY = CASE WHEN IN_QUANTITY IS NULL THEN 0 ELSE IN_QUANTITY END + ?,")
					.append(" STOCK_IN_AMOUNT = CASE WHEN STOCK_IN_AMOUNT IS NULL THEN 0 ELSE STOCK_IN_AMOUNT END + ?,")
					.append(" BUY_IN_COUNT = CASE WHEN BUY_IN_COUNT IS NULL THEN 0 ELSE BUY_IN_COUNT END + ?,")
					.append(" BUY_IN_AMOUNT = CASE WHEN BUY_IN_AMOUNT IS NULL THEN 0 ELSE BUY_IN_AMOUNT END + ?,")
					.append(" ALLOCATE_IN_COUNT = CASE WHEN ALLOCATE_IN_COUNT IS NULL THEN 0 ELSE ALLOCATE_IN_COUNT END + ?,")
					.append(" ALLOCATE_IN_AMOUNT = CASE WHEN ALLOCATE_IN_AMOUNT IS NULL THEN 0 ELSE ALLOCATE_IN_AMOUNT END + ?,")
					.append(" OTHER_IN_COUNT = CASE WHEN OTHER_IN_COUNT IS NULL THEN 0 ELSE OTHER_IN_COUNT END + ?,")
					.append(" OTHER_IN_AMOUNT = CASE WHEN OTHER_IN_AMOUNT IS NULL THEN 0 ELSE OTHER_IN_AMOUNT END + ?,")
					.append(" PROFIT_IN_COUNT = CASE WHEN PROFIT_IN_COUNT IS NULL THEN 0 ELSE PROFIT_IN_COUNT END + ?,")
					.append(" PROFIT_IN_AMOUNT = CASE WHEN PROFIT_IN_AMOUNT IS NULL THEN 0 ELSE PROFIT_IN_AMOUNT END + ?,")
					.append(" OUT_QUANTITY = CASE WHEN OUT_QUANTITY IS NULL THEN 0 ELSE OUT_QUANTITY END + ?,")
					.append(" STOCK_OUT_COST_AMOUNT = CASE WHEN STOCK_OUT_COST_AMOUNT IS NULL THEN 0 ELSE STOCK_OUT_COST_AMOUNT END + ?,")
					.append(" OUT_AMOUNT = CASE WHEN OUT_AMOUNT IS NULL THEN 0 ELSE OUT_AMOUNT END + ?,")
					.append(" REPAIR_OUT_COUNT = CASE WHEN REPAIR_OUT_COUNT IS NULL THEN 0 ELSE REPAIR_OUT_COUNT END + ?,")
					.append(" REPAIR_OUT_COST_AMOUNT = CASE WHEN REPAIR_OUT_COST_AMOUNT IS NULL THEN 0 ELSE REPAIR_OUT_COST_AMOUNT END + ?,")
					.append(" REPAIR_OUT_SALE_AMOUNT = CASE WHEN REPAIR_OUT_SALE_AMOUNT IS NULL THEN 0 ELSE REPAIR_OUT_SALE_AMOUNT END + ?,")
					.append(" SALE_OUT_COUNT = CASE WHEN SALE_OUT_COUNT IS NULL THEN 0 ELSE SALE_OUT_COUNT END + ?,")
					.append(" SALE_OUT_COST_AMOUNT = CASE WHEN SALE_OUT_COST_AMOUNT IS NULL THEN 0 ELSE SALE_OUT_COST_AMOUNT END + ?,")
					.append(" SALE_OUT_SALE_AMOUNT = CASE WHEN SALE_OUT_SALE_AMOUNT IS NULL THEN 0 ELSE SALE_OUT_SALE_AMOUNT END + ?,")
					.append(" INNER_OUT_COUNT = CASE WHEN INNER_OUT_COUNT IS NULL THEN 0 ELSE INNER_OUT_COUNT END + ?,")
					.append(" INNER_OUT_COST_AMOUNT = CASE WHEN INNER_OUT_COST_AMOUNT IS NULL THEN 0 ELSE INNER_OUT_COST_AMOUNT END + ?,")
					.append(" INNER_OUT_SALE_AMOUNT = CASE WHEN INNER_OUT_SALE_AMOUNT IS NULL THEN 0 ELSE INNER_OUT_SALE_AMOUNT END + ?,")
					.append(" ALLOCATE_OUT_COUNT = CASE WHEN ALLOCATE_OUT_COUNT IS NULL THEN 0 ELSE ALLOCATE_OUT_COUNT END + ?,")
					.append(" ALLOCATE_OUT_COST_AMOUNT = CASE WHEN ALLOCATE_OUT_COST_AMOUNT IS NULL THEN 0 ELSE ALLOCATE_OUT_COST_AMOUNT END + ?,")
					.append(" ALLOCATE_OUT_SALE_AMOUNT = CASE WHEN ALLOCATE_OUT_SALE_AMOUNT IS NULL THEN 0 ELSE ALLOCATE_OUT_SALE_AMOUNT END + ?,")
					.append(" OTHER_OUT_COUNT = CASE WHEN OTHER_OUT_COUNT IS NULL THEN 0 ELSE OTHER_OUT_COUNT END + ?,")
					.append(" OTHER_OUT_COST_AMOUNT = CASE WHEN OTHER_OUT_COST_AMOUNT IS NULL THEN 0 ELSE OTHER_OUT_COST_AMOUNT END + ?,")
					.append(" OTHER_OUT_SALE_AMOUNT = CASE WHEN OTHER_OUT_SALE_AMOUNT IS NULL THEN 0 ELSE OTHER_OUT_SALE_AMOUNT END + ?,")
					.append(" LOSS_OUT_COUNT = CASE WHEN LOSS_OUT_COUNT IS NULL THEN 0 ELSE LOSS_OUT_COUNT END + ?,")
					.append(" LOSS_OUT_AMOUNT = CASE WHEN LOSS_OUT_AMOUNT IS NULL THEN 0 ELSE LOSS_OUT_AMOUNT END + ?,")
					.append(" TRANSFER_IN_COUNT = CASE WHEN TRANSFER_IN_COUNT IS NULL THEN 0 ELSE TRANSFER_IN_COUNT END + ?,")
					.append(" TRANSFER_IN_AMOUNT = CASE WHEN TRANSFER_IN_AMOUNT IS NULL THEN 0 ELSE TRANSFER_IN_AMOUNT END + ?,")
					.append(" TRANSFER_OUT_COUNT = CASE WHEN TRANSFER_OUT_COUNT IS NULL THEN 0 ELSE TRANSFER_OUT_COUNT END + ?,")
					.append(" TRANSFER_OUT_COST_AMOUNT = CASE WHEN TRANSFER_OUT_COST_AMOUNT IS NULL THEN 0 ELSE TRANSFER_OUT_COST_AMOUNT END + ?,")
					.append(" UPHOLSTER_OUT_COUNT = CASE WHEN UPHOLSTER_OUT_COUNT IS NULL THEN 0 ELSE UPHOLSTER_OUT_COUNT END + ?,")
					.append(" UPHOLSTER_OUT_COST_AMOUNT = CASE WHEN UPHOLSTER_OUT_COST_AMOUNT IS NULL THEN 0 ELSE UPHOLSTER_OUT_COST_AMOUNT END + ?,")
					.append(" UPHOLSTER_OUT_SALE_AMOUNT = CASE WHEN UPHOLSTER_OUT_SALE_AMOUNT IS NULL THEN 0 ELSE UPHOLSTER_OUT_SALE_AMOUNT END + ?,")
					.append(" CLOSE_QUANTITY =  CASE WHEN CLOSE_QUANTITY IS NULL THEN 0 ELSE CLOSE_QUANTITY  END + ?-?,")
					.append(" CLOSE_PRICE = ?,")
					.append(" CLOSE_AMOUNT =  CASE WHEN CLOSE_AMOUNT IS NULL THEN 0 ELSE CLOSE_AMOUNT  END + ?-?");
			StringBuffer conditions = new StringBuffer();
			conditions.append(" DEALER_CODE = ?").append(" AND REPORT_YEAR = ?").append(" AND REPORT_MONTH = ?")
					.append(" AND STORAGE_CODE = ?").append(" AND PART_BATCH_NO = ?").append(" AND PART_NO = ?")
					.append(" AND D_KEY = ").append(CommonConstants.D_KEY);
			TtPartPeriodReportPO.update(updates.toString(), conditions.toString(),
					Math.round(checkIsNotNull(db.getFloat("IN_QUANTITY")) * 100) * 0.01,
					Math.round(checkIsNotNull(db.getDouble("STOCK_IN_AMOUNT")) * 100) * 0.01,
					Math.round(checkIsNotNull(db.getFloat("BUY_IN_COUNT")) * 100) * 0.01,
					Math.round(checkIsNotNull(db.getDouble("BUY_IN_AMOUNT")) * 100) * 0.01,
					Math.round(checkIsNotNull(db.getFloat("ALLOCATE_IN_COUNT")) * 100) * 0.01,
					Math.round(checkIsNotNull(db.getDouble("ALLOCATE_IN_AMOUNT")) * 100) * 0.01,
					Math.round(checkIsNotNull(db.getFloat("OTHER_IN_COUNT")) * 100) * 0.01,
					Math.round(checkIsNotNull(db.getDouble("OTHER_IN_AMOUNT")) * 100) * 0.01,
					Math.round(checkIsNotNull(db.getFloat("PROFIT_IN_COUNT")) * 100) * 0.01,
					Math.round(checkIsNotNull(db.getDouble("PROFIT_IN_AMOUNT")) * 100) * 0.01,
					Math.round(checkIsNotNull(db.getFloat("OUT_QUANTITY")) * 100) * 0.01,
					Math.round(checkIsNotNull(db.getDouble("STOCK_OUT_COST_AMOUNT")) * 100) * 0.01,
					Math.round(checkIsNotNull(db.getDouble("OUT_AMOUNT")) * 100) * 0.01,
					Math.round(checkIsNotNull(db.getFloat("REPAIR_OUT_COUNT")) * 100) * 0.01,
					Math.round(checkIsNotNull(db.getDouble("REPAIR_OUT_COST_AMOUNT")) * 100) * 0.01,
					Math.round(checkIsNotNull(db.getDouble("REPAIR_OUT_SALE_AMOUNT")) * 100) * 0.01,
					Math.round(checkIsNotNull(db.getFloat("SALE_OUT_COUNT")) * 100) * 0.01,
					Math.round(checkIsNotNull(db.getDouble("SALE_OUT_COST_AMOUNT")) * 100) * 0.01,
					Math.round(checkIsNotNull(db.getDouble("SALE_OUT_SALE_AMOUNT")) * 100) * 0.01,
					Math.round(checkIsNotNull(db.getFloat("INNER_OUT_COUNT")) * 100) * 0.01,
					Math.round(checkIsNotNull(db.getDouble("INNER_OUT_COST_AMOUNT")) * 100) * 0.01,
					Math.round(checkIsNotNull(db.getDouble("INNER_OUT_SALE_AMOUNT")) * 100) * 0.01,
					Math.round(checkIsNotNull(db.getFloat("ALLOCATE_OUT_COUNT")) * 100) * 0.01,
					Math.round(checkIsNotNull(db.getDouble("ALLOCATE_OUT_COST_AMOUNT")) * 100) * 0.01,
					Math.round(checkIsNotNull(db.getDouble("ALLOCATE_OUT_SALE_AMOUNT")) * 100) * 0.01,
					Math.round(checkIsNotNull(db.getFloat("OTHER_OUT_COUNT")) * 100) * 0.01,
					Math.round(checkIsNotNull(db.getDouble("OTHER_OUT_COST_AMOUNT")) * 100) * 0.01,
					Math.round(checkIsNotNull(db.getDouble("OTHER_OUT_SALE_AMOUNT")) * 100) * 0.01,
					Math.round(checkIsNotNull(db.getFloat("LOSS_OUT_COUNT")) * 100) * 0.01,
					Math.round(checkIsNotNull(db.getDouble("LOSS_OUT_AMOUNT")) * 100) * 0.01,
					Math.round(checkIsNotNull(db.getFloat("TRANSFER_IN_COUNT")) * 100) * 0.01,
					Math.round(checkIsNotNull(db.getDouble("TRANSFER_IN_AMOUNT")) * 100) * 0.01,
					Math.round(checkIsNotNull(db.getFloat("TRANSFER_OUT_COUNT")) * 100) * 0.01,
					Math.round(checkIsNotNull(db.getDouble("TRANSFER_OUT_COST_AMOUNT")) * 100) * 0.01,
					Math.round(checkIsNotNull(db.getFloat("UPHOLSTER_OUT_COUNT")) * 100) * 0.01,
					Math.round(checkIsNotNull(db.getDouble("UPHOLSTER_OUT_COST_AMOUNT")) * 100) * 0.01,
					Math.round(checkIsNotNull(db.getDouble("UPHOLSTER_OUT_SALE_AMOUNT")) * 100) * 0.01,
					Math.round(checkIsNotNull(db.getFloat("IN_QUANTITY")) * 100) * 0.01,
					Math.round(checkIsNotNull(db.getFloat("OUT_QUANTITY")) * 100) * 0.01,
					Math.round(checkIsNotNull(db.getDouble("CLOSE_PRICE")) * 10000) * 0.0001,
					Math.round(checkIsNotNull(db.getDouble("STOCK_IN_AMOUNT")) * 100) * 0.01,
					Math.round(checkIsNotNull(db.getDouble("STOCK_OUT_COST_AMOUNT")) * 100) * 0.01, dealerCode,
					Utility.fullSpaceBuffer2(account.getString("B_YEAR"), 4),
					Utility.fullSpaceBuffer2(account.getString("PERIODS"), 2), storageCode, partBatchNo, partNo);
		}
	}
	
	public Float checkIsNotNull(Float str){
		return !StringUtils.isNullOrEmpty(str)?str:0;
	}
	
	public Double checkIsNotNull(Double str){
		return !StringUtils.isNullOrEmpty(str)?str:0;
	}

	public int calCostPriceOut(TmPartStockItemPO stockItemPO) throws ServiceBizException {
		String defaultValue = Utility.getDefaultValue("2035");// 是否使用批次
		StringBuffer sql = new StringBuffer("");
		StringBuffer sqlItem = new StringBuffer("");
		if (DictCodeConstants.DICT_IS_NO.equals(defaultValue)) {// 不使用批次的时候
			sql.append(" UPDATE TM_PART_STOCK SET STOCK_QUANTITY =  COALESCE(STOCK_QUANTITY,0)").append("+")
					.append(!StringUtils.isNullOrEmpty(stockItemPO.getFloat("STOCK_QUANTITY"))?stockItemPO.getFloat("STOCK_QUANTITY"):0).append(" , COST_PRICE = CASE WHEN (")
					.append("COALESCE(STOCK_QUANTITY,0)+").append(!StringUtils.isNullOrEmpty(stockItemPO.getFloat("STOCK_QUANTITY"))?stockItemPO.getFloat("STOCK_QUANTITY"):0)
					.append(" ) > 0 AND (COALESCE(COST_AMOUNT,0)+ ").append(stockItemPO.getDouble("COST_AMOUNT"))
					.append(" )>= 0  THEN round((").append("COALESCE(COST_AMOUNT,0)+ ")
					.append(stockItemPO.getDouble("COST_AMOUNT")).append(" )/(COALESCE(STOCK_QUANTITY,0)+ ")
					.append(!StringUtils.isNullOrEmpty(stockItemPO.getFloat("STOCK_QUANTITY"))?stockItemPO.getFloat("STOCK_QUANTITY"):0).append(" ),4) ELSE (CASE WHEN ")
					.append("COALESCE(LATEST_PRICE,0)")
					.append("=0 THEN COST_PRICE ")
					.append(" ELSE LATEST_PRICE/").append(Utility.add("1", Utility.getDefaultValue("2034")))
					.append(" END) END ,  COST_AMOUNT = COALESCE(COST_AMOUNT,0) +  ")
					.append(stockItemPO.getDouble("COST_AMOUNT")).append(" ");
			sqlItem.append(" UPDATE TM_PART_STOCK_ITEM SET STOCK_QUANTITY = ").append("COALESCE(STOCK_QUANTITY,0)+")
					.append(!StringUtils.isNullOrEmpty(stockItemPO.getFloat("STOCK_QUANTITY"))?stockItemPO.getFloat("STOCK_QUANTITY"):0).append(" , COST_PRICE = CASE WHEN (")
					.append("COALESCE(STOCK_QUANTITY,0)+").append(!StringUtils.isNullOrEmpty(stockItemPO.getFloat("STOCK_QUANTITY"))?stockItemPO.getFloat("STOCK_QUANTITY"):0)
					.append(" ) > 0 AND (").append("COALESCE(COST_AMOUNT,0) +  ")
					.append(stockItemPO.getDouble("COST_AMOUNT")).append(" )>= 0  THEN round((")
					.append("COALESCE(COST_AMOUNT,0)+").append(stockItemPO.getDouble("COST_AMOUNT")).append(" ) /( ")
					.append("COALESCE(STOCK_QUANTITY,0) + ").append(!StringUtils.isNullOrEmpty(stockItemPO.getFloat("STOCK_QUANTITY"))?stockItemPO.getFloat("STOCK_QUANTITY"):0)
					.append(" ),4) ELSE (CASE WHEN ").append("COALESCE(LATEST_PRICE,0)")
					.append("=0 THEN COST_PRICE ")
					.append(" ELSE LATEST_PRICE/").append(Utility.add("1", Utility.getDefaultValue("2034")))
					.append(" END) END ,  COST_AMOUNT = COALESCE(COST_AMOUNT,0)").append(" +  ")
					.append(stockItemPO.getDouble("COST_AMOUNT"));
		} else {
			// 使用批次的情况下
			sql.append(" UPDATE TM_PART_STOCK SET STOCK_QUANTITY = ").append("COALESCE(STOCK_QUANTITY,0)+")
					.append(!StringUtils.isNullOrEmpty(stockItemPO.getFloat("STOCK_QUANTITY"))?stockItemPO.getFloat("STOCK_QUANTITY"):0)
					.append(" , COST_PRICE = LATEST_PRICE , COST_AMOUNT = COALESCE(COST_AMOUNT,0) -  ")
					.append(stockItemPO.getDouble("COST_AMOUNT")).append(" ");
			sqlItem.append(" UPDATE TM_PART_STOCK_ITEM SET STOCK_QUANTITY = COALESCE(STOCK_QUANTITY,0)+")
					.append(!StringUtils.isNullOrEmpty(stockItemPO.getFloat("STOCK_QUANTITY"))?stockItemPO.getFloat("STOCK_QUANTITY"):0)
					.append(" , COST_PRICE = LATEST_PRICE ,  COST_AMOUNT = COALESCE(COST_AMOUNT,0) - ")
					.append(stockItemPO.getDouble("COST_AMOUNT")).append(" ");
		}
		sql.append(" WHERE DEALER_CODE = '").append(FrameworkUtil.getLoginInfo().getDealerCode())
				.append("'  AND PART_NO = '").append(stockItemPO.getString("PART_NO")).append("'  AND STORAGE_CODE = '")
				.append(stockItemPO.getString("STORAGE_CODE")).append("'  AND D_KEY = ").append(CommonConstants.D_KEY);
		sqlItem.append(" WHERE DEALER_CODE = '").append(FrameworkUtil.getLoginInfo().getDealerCode())
				.append("'  AND PART_NO = '").append(stockItemPO.getString("PART_NO")).append("'  AND STORAGE_CODE = '")
				.append(stockItemPO.getString("STORAGE_CODE")).append("'  AND PART_BATCH_NO = '")
				.append(stockItemPO.getString("PART_BATCH_NO")).append("'  AND D_KEY = ").append(CommonConstants.D_KEY);
		int i = new DB(TmPartStockPO.getMetaModel().getDbName()).exec(sql.toString());// 更新主表
		int j = new DB(TmPartStockItemPO.getMetaModel().getDbName()).exec(sqlItem.toString());// 更新子表
		if (i > 0 || j > 0) {
			return 1;// 表示修改成功
		} else {
			return 0;// 表示修改失败
		}
	}

	/**
	 * 精确查询配件批次库存信息
	 * 
	 * @return
	 */
	public List<Map> queryStockItem(float partQuantity, String partNo, String storageCode) {
		StringBuffer sb = new StringBuffer();
		sb.append(" select ").append(partQuantity)
				.append(" as PART_QUANTITY,DEALER_CODE, PART_NO, STORAGE_CODE, STORAGE_POSITION_CODE, PART_NAME, ")
				.append(" SPELL_CODE, PART_GROUP_CODE, UNIT_CODE, STOCK_QUANTITY, SALES_PRICE,STOCK_QUANTITY AS STOCK_QUANTITY_STOCK,COST_PRICE,COST_AMOUNT ")
				.append(" CLAIM_PRICE, LIMIT_PRICE, LATEST_PRICE, COST_AMOUNT, MAX_STOCK, ")
				.append(" MIN_STOCK, BORROW_QUANTITY, LEND_QUANTITY, LOCKED_QUANTITY, PART_STATUS, ")
				.append(" LAST_STOCK_IN, LAST_STOCK_OUT, FOUND_DATE, REMARK, D_KEY, CREATED_BY,(STOCK_QUANTITY+BORROW_QUANTITY-LEND_QUANTITY) AS USEABLE_STOCK, ")
				.append(" ( CASE WHEN (STOCK_QUANTITY+BORROW_QUANTITY-LEND_QUANTITY-(").append(partQuantity)
				.append(")-LOCKED_QUANTITY)<-0.00000001 THEN 12781001 ELSE 12781002 END )   AS ISNEGATIVE, ")
				.append(" (CASE WHEN ( (STOCK_QUANTITY*COST_PRICE<>COST_AMOUNT AND (STOCK_QUANTITY=0 OR COST_AMOUNT=0)) OR COST_PRICE=0)  THEN 12781001 ELSE 12781002 END)  AS ISNORMAL ")
				.append(" from  TM_PART_STOCK  WHERE DEALER_CODE = ? ").append(" AND D_KEY = ")
				.append(CommonConstants.D_KEY);
		Utility.sqlToEquals(sb, partNo, "PART_NO", null);
		Utility.sqlToEquals(sb, storageCode, "STORAGE_CODE", null);
		sb.append(" AND ( ( (STOCK_QUANTITY+BORROW_QUANTITY-LEND_QUANTITY-(").append(partQuantity).append(
				")-LOCKED_QUANTITY<-0.00000001) OR ( (STOCK_QUANTITY*COST_PRICE<>COST_AMOUNT) AND (STOCK_QUANTITY=0 OR COST_AMOUNT=0 ) )) OR  COST_PRICE=0 ) ");
		List query = new ArrayList();
		query.add(FrameworkUtil.getLoginInfo().getDealerCode());
		return DAOUtil.findAll(sb.toString(), query);
	}

	/**
	 * 查询业务表 sheetTable 关联Tm_part_info
	 * 
	 * @param sheetTable
	 * @param sheetName
	 * @param sheetNo
	 * @return
	 */
	public List<Map> getNonOemPartList(String sheetTable, String sheetName, String sheetNo) {
		String defaultValue = "12781002";
		defaultValue = Utility.getDefaultValue("8573");
		if (!StringUtils.isNullOrEmpty(defaultValue) && !(defaultValue.equals(DictCodeConstants.DICT_IS_YES))) {
			return new ArrayList<Map>();
		} else {
			StringBuffer sb = new StringBuffer();
			sb.append(" select A.PART_NO,A.PART_NAME,A.STORAGE_CODE from  ").append(sheetTable)
					.append(" A  left join TM_PART_INFO B on (A.part_NO = B.PART_NO AND A.DEALER_CODE = B.DEALER_CODE)  WHERE A.DEALER_CODE = '")
					.append(FrameworkUtil.getLoginInfo().getDealerCode()).append("'  AND B.DOWN_TAG = ")
					.append(DictCodeConstants.DICT_IS_NO).append("  AND A.STORAGE_CODE ='OEMK' AND A.")
					.append(sheetName).append(" = '").append(sheetNo).append("' ");
			return DAOUtil.findAll(sb.toString(), null);
		}
	}

	/**
	 * 校验配件是否已经入账
	 * 
	 * @param sheetNo
	 * @param sheetType
	 * @param sheetNoType
	 * @return
	 */
	public String checkIsFinished(String sheetNo, String sheetType, String sheetNoType) {
		String flag = DictCodeConstants.DICT_IS_YES;
		StringBuffer sql = new StringBuffer("");
		sql.append(" select * from ").append(sheetType).append(" where DEALER_CODE='")
				.append(FrameworkUtil.getLoginInfo().getDealerCode()).append("' and d_key=")
				.append(CommonConstants.D_KEY).append(" and ").append(sheetNoType).append("='").append(sheetNo)
				.append("' and IS_FINISHED=").append(DictCodeConstants.DICT_IS_NO).append(" ");
		List<Map> list = DAOUtil.findAll(sql.toString(), null);
		if (list.size() > 0) {
			flag = DictCodeConstants.DICT_IS_NO;
		}
		return flag;
	}

	/**
	 * obj转str类型
	 * 
	 * @param obj
	 * @return
	 */
	public String objToStr(Object obj) {
		return StringUtils.isNullOrEmpty(obj) ? "" : obj.toString();
	}

	private static Double getAddD(Double v1, Double v2) {
		String s1 = "0";
		String s2 = "0";
		if (v1 != null)
			s1 = v1.toString();
		if (v2 != null)
			s2 = v2.toString();
		return Utility.round(new Double(Utility.add(s1, s2)).toString(), 2);
	}

	private static Double getSubD(Double v1, Double v2) {
		String s1 = "0";
		String s2 = "0";
		if (v1 != null)
			s1 = v1.toString();
		if (v2 != null)
			s2 = v2.toString();
		return Utility.round(new Double(Utility.sub(s1, s2)).toString(), 2);
	}

	private static Double getAddF(Float v1, Float v2) {
		String s1 = "0";
		String s2 = "0";
		if (v1 != null)
			s1 = v1.toString();
		if (v2 != null)
			s2 = v2.toString();
		double d = Utility.round(new Double(Utility.add(s1, s2)).toString(), 2);
		return d;
	}

	private static Double getSubF(Double v1, Float v2) {
		String s1 = "0";
		String s2 = "0";
		if (v1 != null)
			s1 = v1.toString();
		if (v2 != null)
			s2 = v2.toString();
		double d = Utility.round(new Double(Utility.sub(s1, s2)).toString(), 2);
		return d;
	}

	@Override
	public void btnDelete(String id) {
		// 删除子表
		TtPartProfitItemPO.delete("PROFIT_NO = ? AND DEALER_CODE = ? AND D_KEY = ?", id,
				FrameworkUtil.getLoginInfo().getDealerCode(), CommonConstants.D_KEY);
		// 再删主表
		TtPartProfitPO.delete("PROFIT_NO = ? AND DEALER_CODE = ? AND D_KEY = ?", id,
				FrameworkUtil.getLoginInfo().getDealerCode(), CommonConstants.D_KEY);
		handleOperateLog("配件报溢单删除：配件报溢单号[" + id + "]", "TT_PART_PROFIT,PROFIT_NO=" + id,
				Integer.parseInt(DictCodeConstants.DICT_ASCLOG_PART_MANAGE),
				FrameworkUtil.getLoginInfo().getEmployeeNo());
	}

	@Override
	public Map findById(String id) {
		String sql = "SELECT A.*,tu.USER_NAME FROM TT_PART_PROFIT A LEFT JOIN TM_USER tu ON tu.DEALER_CODE = A.DEALER_CODE AND tu.USER_ID = A.HANDLER WHERE A.DEALER_CODE = ? AND A.PROFIT_NO = ?";
		List queryParam = new ArrayList();
		queryParam.add(FrameworkUtil.getLoginInfo().getDealerCode());
		queryParam.add(id);
		return DAOUtil.findFirst(sql, queryParam);
	}

	@Override
	public List<Map> findItemByPartProfit(String id) {
		StringBuffer sb = new StringBuffer();
		sb.append("select A.ITEM_ID, A.DEALER_CODE, A.PROFIT_NO, A.STORAGE_POSITION_CODE, ")
				.append("A.STORAGE_CODE,ts.STORAGE_NAME,A.PART_BATCH_NO, A.PART_NO, A.PART_NAME, A.UNIT_CODE, A.PROFIT_QUANTITY,")
				.append(" A.COST_PRICE,A.COST_AMOUNT, A.PROFIT_PRICE, A.PROFIT_AMOUNT, A.D_KEY, A.CREATED_BY,")
				.append(" A.CREATED_AT,A.UPDATED_BY, A.UPDATED_AT, A.VER,B.DOWN_TAG, B.IS_BACK from TT_PART_PROFIT_ITEM A LEFT JOIN TM_STORAGE ts ON ts.STORAGE_CODE = A.STORAGE_CODE AND ts.DEALER_CODE = A.DEALER_CODE LEFT JOIN TM_PART_INFO B ON A.DEALER_CODE = B.DEALER_CODE AND A.PART_NO = B.PART_NO where ")
				.append(" A.D_KEY=").append(CommonConstants.D_KEY).append(" and A.DEALER_CODE='")
				.append(FrameworkUtil.getLoginInfo().getDealerCode()).append("' ");
		Utility.sqlToEquals(sb, id, "PROFIT_NO", "A");
		return DAOUtil.findAll(sb.toString(), null);
	}

}
