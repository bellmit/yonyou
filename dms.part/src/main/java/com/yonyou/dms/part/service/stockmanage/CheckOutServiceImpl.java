package com.yonyou.dms.part.service.stockmanage;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.yonyou.dms.common.domains.DTO.stockmanage.TmPartStockDTO;
import com.yonyou.dms.common.domains.DTO.stockmanage.TmPartStockItemDTO;
import com.yonyou.dms.common.domains.DTO.stockmanage.TtPartFlowDTO;
import com.yonyou.dms.common.domains.DTO.stockmanage.TtpartLendDTO;
import com.yonyou.dms.common.domains.PO.basedata.TmPartInfoPO;
import com.yonyou.dms.common.domains.PO.basedata.TmPartStockItemPO;
import com.yonyou.dms.common.domains.PO.basedata.TmPartStockPO;
import com.yonyou.dms.common.domains.PO.basedata.TtAccountsTransFlowPO;
import com.yonyou.dms.common.domains.PO.basedata.TtPartLendItemPO;
import com.yonyou.dms.common.domains.PO.basedata.TtPartLendPO;
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

@Service
public class CheckOutServiceImpl implements CheckOutService {

	@Autowired
	private CommonNoService commonNoService;

	/**
	 * 查询借出登记单
	 */
	@Override
	public PageInfoDto findOrderChoose(Map<String, String> queryParam) {

		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		StringBuffer sb = new StringBuffer();
		// 只查询没有入帐的信息判断IS_Finshine字段

		sb.append(" SELECT A.VER,A.DEALER_CODE,A.LEND_NO,A.CUSTOMER_CODE,A.CUSTOMER_NAME,A.COST_AMOUNT,"
				+ " A.OUT_AMOUNT,A.HANDLER,A.LEND_DATE,A.IS_FINISHED,A.FINISHED_DATE,A.PAY_OFF,A.BORROWER_TAG,"
				+ " A.LOCK_USER ,A.SHEET_CREATE_DATE,pli.ITEM_ID from TT_PART_LEND A "
				+ " LEFT JOIN TT_PART_LEND_ITEM pli ON pli.DEALER_CODE=A.DEALER_CODE AND pli.LEND_NO = A.LEND_NO "
				+ "WHERE A.DEALER_CODE='" + dealerCode
				+ "' and A.D_KEY=" + CommonConstants.D_KEY + " and  IS_FINISHED=" + DictCodeConstants.DICT_IS_NO
				+ Utility.getLikeCond("A", "LEND_NO", queryParam.get("lendNo"), "AND"));
		PageInfoDto pageInfoDto = DAOUtil.pageQuery(sb.toString(), null);
		return pageInfoDto;

	}

	/**
	 * 查询借出单明细
	 */
	@Override
	public PageInfoDto lendDetail(String id, Map<String, String> queryParam) throws SerialException {
		List<Object> queryList = new ArrayList<Object>();
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		StringBuffer sb = new StringBuffer();
		// 先锁定用户
		// TtPartLendPO ttPartLendPO = new TtPartLendPO();
		int flag = Utility.updateByLocker("TT_PART_LEND", FrameworkUtil.getLoginInfo().getUserId().toString(),
				"LEND_NO", id, "LOCK_USER");
		if (flag > 0) {
			sb.append(
					" SELECT A.VER,A.ITEM_ID,A.LEND_NO,A.DEALER_CODE,A.STORAGE_CODE,A.STORAGE_POSITION_CODE,A.PART_BATCH_NO,A.PART_NO,A.PART_NAME,"
							+ " A.UNIT_CODE,A.OUT_QUANTITY,A.WRITE_OFF_QUANTITY,A.COST_PRICE,A.COST_AMOUNT,A.OUT_PRICE,"
							+ " A.OUT_AMOUNT,B.DOWN_TAG,c.IS_FINISHED from TT_PART_LEND_ITEM  A LEFT JOIN TM_PART_INFO B ON A.DEALER_CODE = B.DEALER_CODE AND A.PART_NO = B.PART_NO "
							+ " LEFT JOIN tt_part_lend c ON c.DEAler_code=a.`DEALER_CODE` AND c.lend_no =a.`LEND_NO` "
							+ "where A.DEALER_CODE= '" + dealerCode + "' and A.d_key= " + CommonConstants.D_KEY);
			sb.append(Utility.getLikeCond("A", "LEND_NO", queryParam.get("lendNo"), "AND"));
			PageInfoDto pageInfoDto = DAOUtil.pageQuery(sb.toString(), queryList);
			return pageInfoDto;
		} else {
			throw new ServiceBizException("单号[" + id + "]加锁失败!");
		}
	}

	@Override
	public List<Map> lendDetail2(String id) {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		StringBuffer sb = new StringBuffer();
		sb.append(
				" SELECT A.VER,A.ITEM_ID,A.LEND_NO,A.DEALER_CODE,A.STORAGE_CODE,A.STORAGE_POSITION_CODE,A.PART_BATCH_NO,A.PART_NO,A.PART_NAME,"
						+ " A.UNIT_CODE,A.OUT_QUANTITY,A.WRITE_OFF_QUANTITY,A.COST_PRICE,A.COST_AMOUNT,A.OUT_PRICE,"
						+ " A.OUT_AMOUNT,B.DOWN_TAG,c.IS_FINISHED,psi.STOCK_QUANTITY from TT_PART_LEND_ITEM  A LEFT JOIN TM_PART_INFO B ON A.DEALER_CODE = B.DEALER_CODE AND A.PART_NO = B.PART_NO "
						+ " LEFT JOIN tt_part_lend c ON c.DEAler_code=a.`DEALER_CODE` AND c.lend_no =a.`LEND_NO` "
						+ " LEFT JOIN tm_part_stock ps ON ps.`DEALER_CODE`=A.`DEALER_CODE` AND ps.`PART_NO`=A.`PART_NO`"
						+ " LEFT JOIN tm_part_stock_item psi ON psi.`DEALER_CODE`=A.`DEALER_CODE` AND psi.`PART_BATCH_NO`=A.`PART_BATCH_NO` "
						+ "where A.DEALER_CODE= '" + dealerCode + "' and A.d_key= " + CommonConstants.D_KEY);
		sb.append(Utility.getLikeCond("A", "LEND_NO", id, "AND"));
		return DAOUtil.findAll(sb.toString(), null);

	}

	/**
	 * 新增查询借出登记单明细
	 */
	@Override
	public PageInfoDto findLendRegisterItem(Map<String, String> queryParam) {
		List<Object> queryList = new ArrayList<Object>();
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		StringBuffer sql = new StringBuffer();

		/**
		 * 维修领料,配件销售,车间借料,内部领用,调拨出库,借出登记,配件移库,配件报损,配件预留界面新增查询配件，过滤掉已经停用的配件
		 */

		sql.append(
				" select A.NODE_PRICE,A.INSURANCE_PRICE, A.DEALER_CODE, A.PART_NO,TS.CJ_TAG, A.STORAGE_CODE, A.PART_BATCH_NO,A.STORAGE_POSITION_CODE, A.PART_NAME, "
						+ " A.SPELL_CODE, A.UNIT_CODE, A.STOCK_QUANTITY, A.SALES_PRICE, A.CLAIM_PRICE, B.LIMIT_PRICE, B.INSTRUCT_PRICE, A.LATEST_PRICE,"
						+ " ROUND(A.COST_PRICE,4) AS COST_PRICE, A.COST_AMOUNT, A.BORROW_QUANTITY, A.PART_STATUS,C.LOCKED_QUANTITY, A.LEND_QUANTITY, "
						+ " A.LAST_STOCK_IN, A.LAST_STOCK_OUT, A.REMARK, A.CREATED_BY, A.CREATED_AT, A.UPDATED_BY, A.UPDATED_AT,B.DOWN_TAG,B.OPTION_NO, "
						+ " A.VER, A.PART_GROUP_CODE,  C.PART_MODEL_GROUP_CODE_SET,pli.OUT_PRICE,pli.OUT_QUANTITY,pli.OUT_AMOUNT, spi.PRICE_RATE,pl.IS_FINISHED,"
						+ " (A.STOCK_QUANTITY + A.BORROW_QUANTITY - " + Utility.getChangeNull("A", "LEND_QUANTITY", 0)
						+ " ) AS USEABLE_QUANTITY, "
						+ " CASE WHEN (SELECT 1 FROM TM_MAINTAIN_PART CC WHERE  CC.PART_NO = B.PART_NO "// 增加是否保养
						+ " AND CC.DEALER_CODE = B.DEALER_CODE ) >0 THEN " + DictCodeConstants.DICT_IS_YES + " ELSE "
						+ DictCodeConstants.DICT_IS_NO + " END  AS IS_MAINTAIN "

						+ " from TM_PART_STOCK_ITEM  A  LEFT OUTER JOIN (" + CommonConstants.VM_PART_INFO
						+ ") B ON (A.DEALER_CODE = B.DEALER_CODE AND A.PART_NO = B.PART_NO ) "
						+ " LEFT OUTER JOIN TM_PART_STOCK C ON (A.DEALER_CODE = C.DEALER_CODE AND A.PART_NO = C.PART_NO AND A.STORAGE_CODE=C.STORAGE_CODE) "
						+ " LEFT JOIN TM_STORAGE TS ON  A.DEALER_CODE=TS.DEALER_CODE AND A.STORAGE_CODE=TS.STORAGE_CODE "
						+ " LEFT JOIN TT_PART_LEND_ITEM pli ON pli.dealer_code =A.dealer_code AND pli.STORAGE_CODE=A.STORAGE_CODE "
						+ " LEFT JOIN tt_sales_part_item spi ON spi.dealer_code=A.dealer_code AND spi.STORAGE_CODE=A.STORAGE_CODE  "
						+ " LEFT JOIN tt_part_lend pl ON pl.dealer_code=A.dealer_code AND pli.LEND_NO=pl.LEND_NO "
						+ " WHERE A.PART_STATUS <>" + DictCodeConstants.DICT_IS_YES + " AND A.DEALER_CODE = '"
						+ dealerCode + "' " + " AND C.D_KEY = " + CommonConstants.D_KEY);
		sql.append(Utility.getStrLikeCond("A", "REMARK", queryParam.get("remark")));
		sql.append(Utility.getLikeCond("C", "PART_MODEL_GROUP_CODE_SET", queryParam.get("model"), "AND"));

		this.setWhere(sql, queryParam, queryList);

		if ("12781002".equals(Utility.getDefaultValue(dealerCode))) {
			sql.append(" and TS.CJ_TAG=12781001 ");
		} else {
			sql.append(" and  1=1 ");
		}
		sql.append(" GROUP BY A.PART_NO ");
		PageInfoDto pageInfoDto = DAOUtil.pageQuery(sql.toString(), queryList);
		return pageInfoDto;
	}

	/**
	 * 设置查询条件
	 * 
	 * @param sql
	 * @param queryParam
	 * @param queryList
	 */
	private void setWhere(StringBuffer sql, Map<String, String> queryParam, List<Object> queryList) {
		
		if (!StringUtils.isNullOrEmpty(queryParam.get("stockName"))) {
			sql.append(" AND A.STORAGE_CODE like ? ");
			queryList.add("%" + queryParam.get("stockName") + "%");
		} else {
			sql.append(" and  1=1 ");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("brandCode"))) {
			sql.append(" AND B.BRAND like ? ");
			queryList.add("%" + queryParam.get("brandCode") + "%");
		} else {
			sql.append(" and  1=1 ");
		}
		sql.append(Utility.getLikeCond("A", "PART_NO", queryParam.get("partNo"), "AND"));
		sql.append(Utility.getLikeCond("A", "SPELL_CODE", queryParam.get("spell"), "AND"));
		sql.append(Utility.getLikeCond("A", "PART_NAME", queryParam.get("partName"), "AND"));
		if (!StringUtils.isNullOrEmpty(queryParam.get("groupCode"))) {
			sql.append(" and A.PART_GROUP_CODE = " + queryParam.get("groupCode") + " ");
			queryList.add("%" + queryParam.get("groupCode") + "%");
		} else {
			sql.append(" and  1 = 1 ");
		}
		sql.append(Utility.getLikeCond("A", "STORAGE_POSITION_CODE", queryParam.get("storagePositionCode"), "AND"));
		
		if (!StringUtils.isNullOrEmpty(queryParam.get("partModelGroupCodeSet"))) {
			sql.append(" AND C.PART_MODEL_GROUP_CODE_SET like ? ");
			queryList.add("%" + queryParam.get("partModelGroupCodeSet") + "%");
		} else {
			sql.append(" and  1=1 ");
		}
		
		if (!StringUtils.isNullOrEmpty(queryParam.get("remark"))) {
			sql.append(" AND A.REMARK like ? ");
			queryList.add("%" + queryParam.get("remark") + "%");
		} else {
			sql.append(" and  1=1 ");
		}
		
		if (!StringUtils.isNullOrEmpty(queryParam.get("isCheck"))) {
			sql.append(" AND  A.STOCK_QUANTITY > 0 ");
		} else {
			sql.append(" and  1=1 ");
		}
		
		if (!StringUtils.isNullOrEmpty(queryParam.get("isSalePriceBigger"))) {
			sql.append(" and  A.SALES_PRICE > 0 ");
		} else {
			sql.append(" and  1=1 ");
		}
		


	}

	/**
	 * 查询配件新增销售价
	 */
	@Override
	public PageInfoDto findLendPrice(String partNo, String storageCode, Map<String, String> queryParam) {
		List<Object> queryList = new ArrayList<Object>();
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		StringBuffer sql = new StringBuffer();

		sql.append(" SELECT '' as POS_CODE,'' as POS_NAME,"
				+ "A.PART_MODEL_GROUP_CODE_SET, '' as PART_QUANTITY, A.DEALER_CODE, A.PART_NO, A.STORAGE_CODE, A.STORAGE_POSITION_CODE, A.PART_NAME, "
				+ " A.SPELL_CODE, A.UNIT_CODE, A.STOCK_QUANTITY, A.SALES_PRICE, A.CLAIM_PRICE, A.PART_GROUP_CODE, "
				+ " B.LIMIT_PRICE, A.LATEST_PRICE, A.COST_PRICE AS COST_PRICE, A.COST_AMOUNT, A.MAX_STOCK,B.DOWN_TAG, "
				+ " A.BORROW_QUANTITY, A.LEND_QUANTITY, A.LOCKED_QUANTITY, A.LAST_STOCK_IN, A.PART_STATUS, "
				+ " A.LAST_STOCK_OUT, A.REMARK, A.CREATED_BY, A.CREATED_AT, A.UPDATED_BY, A.UPDATED_AT, A.VER, A.MIN_STOCK, "
				+ " B.OPTION_NO, A.NODE_PRICE, B.PLAN_PRICE, B.OEM_LIMIT_PRICE, B.URGENT_PRICE, "
				+ " B.INSTRUCT_PRICE,A.INSURANCE_PRICE, D.OPTION_STOCK, (A.STOCK_QUANTITY + A.BORROW_QUANTITY - A.LEND_QUANTITY - A.LOCKED_QUANTITY) AS USEABLE_STOCK,B.IS_BACK,B.PART_INFIX, "
				+ " B.MIN_LIMIT_PRICE FROM TM_PART_STOCK A  LEFT OUTER JOIN (" + CommonConstants.VM_PART_INFO + ") B"
				+ " ON ( A.DEALER_CODE = B.DEALER_CODE AND A.PART_NO= B.PART_NO ) "
				+ " LEFT OUTER JOIN (select DEALER_CODE, part_no, sum(C.STOCK_QUANTITY) AS OPTION_STOCK FROM TM_PART_STOCK C GROUP BY DEALER_CODE,PART_NO ) D "
				+ " ON ( A.DEALER_CODE = D.DEALER_CODE AND A.PART_NO= D.PART_NO AND B.OPTION_NO = D.PART_NO )"
				+ " WHERE A.DEALER_CODE = '" + dealerCode + "' " + " AND A.D_KEY =  " + CommonConstants.D_KEY);
		if (!StringUtils.isNullOrEmpty(storageCode)) {
			sql.append(" and A.STORAGE_CODE = '" + storageCode + "' ");
		} else {
			sql.append(" and  1 = 1 ");
		}
		if (!StringUtils.isNullOrEmpty(partNo) && partNo.trim().length() != 0) {
			sql.append(" and A.PART_NO =  '" + partNo + "'  ");
		} else {
			sql.append(" and 1 = 1 ");
		}
		PageInfoDto pageInfoDto = DAOUtil.pageQuery(sql.toString(), queryList);
		return pageInfoDto;
	}

	/**
	 * 新增借出登记单
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public String btnSave(TtpartLendDTO ttpartLendDTO) {
		Double amount = 0.0;// 获取出库金额
		Double amount1 = 0.0;// 获取成本金额
		for (Map map : ttpartLendDTO.getDms_checkout()) {
			amount += Double.parseDouble(map.get("OUT_AMOUNT").toString());
			amount1 += Double.parseDouble(map.get("COST_AMOUNT").toString());
		}
		String id = "";
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		if (StringUtils.isNullOrEmpty(ttpartLendDTO.getLendNo())) {

			id = commonNoService.getSystemOrderNo(CommonConstants.SRV_JCDJDH);
			TtPartLendPO ttPartLendPO = new TtPartLendPO();
			ttPartLendPO.setString("LEND_NO", id);
			ttPartLendPO.setString("DEALER_CODE", dealerCode);
			ttPartLendPO.setString("CUSTOMER_CODE", ttpartLendDTO.getCustomerCode());// 客户代码
			ttPartLendPO.setString("CUSTOMER_NAME", ttpartLendDTO.getCustomerName());// 客户名称
			ttPartLendPO.setDouble("COST_AMOUNT", amount1);// 成本金额
			ttPartLendPO.setDouble("OUT_AMOUNT", amount);// 出库金额
			ttPartLendPO.setString("HANDLER", FrameworkUtil.getLoginInfo().getUserId().toString());// 经手人
			ttPartLendPO.setDouble("IS_FINISHED", ttpartLendDTO.getIsFinished());// 是否入账
			ttPartLendPO.setInteger("PAY_OFF", ttpartLendDTO.getPayOff());// 是否结清
			ttPartLendPO.setInteger("BORROWER_TAG", ttpartLendDTO.getBorrowerTag());// 借用人标识
			ttPartLendPO.setDate("SHEET_CREATE_DATE", ttpartLendDTO.getSheetCreateDate());// 开单日期
			ttPartLendPO.setDate("LEND_DATE", ttpartLendDTO.getLendDate());// 借出日期
			ttPartLendPO.setDate("SO_NO", ttpartLendDTO.getSoNo());// 销售订单编号
			ttPartLendPO.setDate("DXP_DATE", ttpartLendDTO.getDxpDate());// 二次录入日期

			/**
			 * 保存时不记入帐时间和是否入帐字段
			 */
			ttPartLendPO.setString("LOCK_USER", FrameworkUtil.getLoginInfo().getUserId().toString());// 锁定人
			ttPartLendPO.saveIt();

			ttpartLendDTO.setLendNo(id);
			// 新增子表
			addPartProfitItem(ttpartLendDTO);
		} else {
			// 修改子表记录     ]
			for (Map map: ttpartLendDTO.getDms_checkout()) {
				List<TtPartLendItemPO> list = TtPartLendItemPO.findBySQL(
						" SELECT * FROM TT_PART_LEND_ITEM WHERE ITEM_ID = ?",map.get("ITEM_ID"));
				
				if (!StringUtils.isNullOrEmpty(list) && (list.size() > 0)) {
					
					TtPartLendItemPO ttPartLendItemPO = list.get(0);
					ttPartLendItemPO.setString("PART_NAME", map.get("PART_NAME"));
					ttPartLendItemPO.setDouble("OUT_PRICE", map.get("OUT_PRICE"));
					ttPartLendItemPO.setInteger("OUT_QUANTITY", map.get("OUT_QUANTITY"));
//					ttPartLendItemPO.setString("LOCK_USER", FrameworkUtil.getLoginInfo().getUserId().toString());
					ttPartLendItemPO.saveIt();
				}
			}
			id = ttpartLendDTO.getLendNo();

			// 删除借出登记明细子表
			delPartLendItem(id);
			// 新增子表
			addPartProfitItem(ttpartLendDTO);
		}
		return id;
	}

	/**
	 * 新增子表
	 * 
	 * @param ttpartLendDTO
	 */
	@SuppressWarnings("rawtypes")
	private void addPartProfitItem(TtpartLendDTO ttpartLendDTO) {
		String dealerCode=FrameworkUtil.getLoginInfo().getDealerCode();
		for (Map map : ttpartLendDTO.getDms_checkout()) {

			TtPartLendItemPO ttPartLendItemPO = new TtPartLendItemPO();
			
			ttPartLendItemPO.setString("LEND_NO", ttpartLendDTO.getLendNo());

			if (!StringUtils.isNullOrEmpty(map.get("STORAGE_CODE"))) {// 仓库代码
				ttPartLendItemPO.setString("STORAGE_CODE", map.get("STORAGE_CODE").toString());
			}
			if (!StringUtils.isNullOrEmpty(map.get("STORAGE_POSITION_CODE"))) {// 库位代码
				ttPartLendItemPO.setString("STORAGE_POSITION_CODE", map.get("STORAGE_POSITION_CODE").toString());
			}
			// 进货批号
				ttPartLendItemPO.setString("PART_BATCH_NO",  CommonConstants.defaultBatchName);
			
			if (!StringUtils.isNullOrEmpty(map.get("PART_NO"))) {// 配件代码
				ttPartLendItemPO.setString("PART_NO", map.get("PART_NO").toString());
			}
			if (!StringUtils.isNullOrEmpty(map.get("PART_NAME"))) {// 配件名称
				ttPartLendItemPO.setString("PART_NAME", map.get("PART_NAME"));
			}
			if (!StringUtils.isNullOrEmpty(map.get("UNIT_CODE"))) {// 计量单位代码
				ttPartLendItemPO.setString("UNIT_CODE", map.get("UNIT_CODE"));
			}
			if (!StringUtils.isNullOrEmpty(map.get("OUT_QUANTITY"))) {// 出库数量
				ttPartLendItemPO.setString("OUT_QUANTITY", map.get("OUT_QUANTITY"));
			}
			if (!StringUtils.isNullOrEmpty(map.get("WRITE_OFF_QUANTITY"))) {// 核销数量
				ttPartLendItemPO.setString("WRITE_OFF_QUANTITY", map.get("WRITE_OFF_QUANTITY"));
			}
			if (!StringUtils.isNullOrEmpty(map.get("COST_PRICE"))) {// 成本单价
				ttPartLendItemPO.setString("COST_PRICE", map.get("COST_PRICE").toString());
			}
			if (!StringUtils.isNullOrEmpty(map.get("OUT_PRICE"))) {// 出库单价
				ttPartLendItemPO.setString("OUT_PRICE", map.get("OUT_PRICE"));
			}

			// 查询成本金额,成本单价
			TtPartLendPO ttPartLendPO = TtPartLendPO.findByCompositeKeys(dealerCode,
					ttpartLendDTO.getLendNo());
			if (!StringUtils.isNullOrEmpty(ttPartLendPO)) {
				if (!StringUtils.isNullOrEmpty(ttPartLendPO.getDouble("OUT_AMOUNT"))) {// 出库金额
					ttPartLendItemPO.setString("OUT_AMOUNT", ttPartLendPO.getDouble("OUT_AMOUNT"));
				}
				if (!StringUtils.isNullOrEmpty(ttPartLendPO.getDouble("COST_AMOUNT"))) {// 成本金额
					ttPartLendItemPO.setString("COST_AMOUNT", ttPartLendPO.getDouble("COST_AMOUNT"));
				}
			}

			ttPartLendItemPO.saveIt();
		}

	}

	/**
	 * 删除子表
	 * 
	 * @param ttpartLendDTO
	 */
	private void delPartLendItem(String id) {
		
		String dealerCode=FrameworkUtil.getLoginInfo().getDealerCode();
		List list=TtPartLendItemPO.findBySQL(" SELECT * FROM TT_PART_LEND_ITEM WHERE LEND_NO =? ",id );
		//删除子表
		if (list!=null&&list.size()>0) {
			TtPartLendItemPO.delete("DEALER_CODE=? AND D_KEY=? AND LEND_NO=?",dealerCode,CommonConstants.D_KEY,id);
		}
		/*List list2=TtPartLendPO.findBySQL("  SELECT * FROM TT_PART_LEND WHERE LEND_NO =?  ", id);
		if (list2!=null&&list2.size()>0) {
			TtPartLendPO.delete("DEALER_CODE=? AND D_KEY=? AND LEND_NO=?",dealerCode,CommonConstants.D_KEY,id);
		}*/
	}

	/**
	 * 借出登记价格修改进行的日志操作
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

	/**
	 * 出库操作校验库存是否为负库存
	 */
	@SuppressWarnings({ "rawtypes", "static-access", "unused" })
	@Override
	public List<Map> btnOutter2(List<Map> list, String split, String split2, String split3, Float tag,
			TmPartStockItemDTO tmPartStockItemDTO) throws Exception {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		int partQuantity = split3.length();
		List rslistAll = new ArrayList<>();
		if (!StringUtils.isNullOrEmpty(split)) {
			for (int i = 0; i < split.length(); i++) {
				TmPartStockPO tmPartStockPO = new TmPartStockPO();
				tmPartStockPO.set("DEALER_CODE", FrameworkUtil.getLoginInfo().getDealerCode());
				tmPartStockPO.set("PART_NO", tmPartStockItemDTO.getPartNo2());
				tmPartStockPO.set("STORAGE_CODE", tmPartStockItemDTO.getStorageCode());
				tmPartStockPO.set("D_KEY", CommonConstants.D_KEY);
				List partlist = new ArrayList();
				partlist = tmPartStockPO.findBySQL(
						"SELECT * FROM TM_PART_STOCK WHERE DEALER_CODE = ? AND PART_NO = ? AND STORAGE_CODE = ?  AND D_KEY = ? ",
						dealerCode, split, split2, CommonConstants.D_KEY);
				if (partlist.size() == 0 || partlist == null) {
					throw new SerialException("配件库存中没有该配件信息 (配件代码为:" + split + "仓库代码为:" + split2);
				}
				if (tag != null) {
					rslistAll = queryStockItem(-Utility.getFloat(split3), dealerCode, split, split2);
				} else {
					rslistAll = queryStockItem(Utility.getFloat(split3), dealerCode, split, split2);
				}
				break;
			}
		}
		return rslistAll;
	}

	@SuppressWarnings("static-access")
	@Override
	public List<Map> btnOutter(List<Map> list, String partNo2s, String storageCodes, String outQuantitys, Float tag,
			TmPartStockItemDTO tmPartStockItemDTO) throws Exception {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		String[] partQuantity = outQuantitys.split(",");
		List rslistAll = new ArrayList<>();
		if (!StringUtils.isNullOrEmpty(partNo2s)) {
			for (int i = 0; i < partNo2s.length(); i++) {
				TmPartStockPO tmPartStockPO = new TmPartStockPO();
				tmPartStockPO.set("DEALER_CODE", FrameworkUtil.getLoginInfo().getDealerCode());
				tmPartStockPO.set("PART_NO", tmPartStockItemDTO.getPartNo2());
				tmPartStockPO.set("STORAGE_CODE", tmPartStockItemDTO.getStorageCode());
				tmPartStockPO.set("D_KEY", CommonConstants.D_KEY);
				List partlist = new ArrayList();
				partlist = tmPartStockPO.findBySQL(
						"SELECT * FROM TM_PART_STOCK WHERE DEALER_CODE = ? AND PART_NO = ? AND STORAGE_CODE = ? AND D_KEY = ? ",
						dealerCode, tmPartStockItemDTO.getPartNo2(), tmPartStockItemDTO.getStorageCode(),
						CommonConstants.D_KEY);
				if (partlist.size() == 0 || partlist == null) {
					throw new SerialException("配件库存中没有该配件信息 (配件代码为:" + partNo2s + "仓库代码为:" + storageCodes);
				}
				if (tag != null) {
					rslistAll = queryStockItem(-Utility.getFloat(partQuantity[i]), dealerCode, partNo2s, storageCodes);
				} else {
					rslistAll = queryStockItem(Utility.getFloat(partQuantity[i]), dealerCode, partNo2s, storageCodes);
				}
				break;
			}
		}
		return rslistAll;
	}

	/**
	 * 查询配件库存批次信息
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> queryStockItem(Float partQuantity, String dealerCode, String partNo2s, String storageCodes) {
		StringBuffer sql = new StringBuffer("");
		List<Map> queryList = new ArrayList<Map>();
		sql.append(" SELECT  " + partQuantity
				+ " AS PART_QUANTITY,ps.DEALER_CODE,ps.PART_NO,ps.STORAGE_CODE,ps.STORAGE_POSITION_CODE,ps.PART_NAME,ps.SPELL_CODE,ps.PART_GROUP_CODE,"
				+ " ps.UNIT_CODE,ps.STOCK_QUANTITY,ps.SALES_PRICE,ps.STOCK_QUANTITY AS STOCK_QUANTITY_STOCK,ps.COST_PRICE,ps.COST_AMOUNT,ps.CLAIM_PRICE,ps.LIMIT_PRICE,ps.LATEST_PRICE,"
				+ " ps.MAX_STOCK,ps.MIN_STOCK,ps.BORROW_QUANTITY,ps.LEND_QUANTITY,ps.LOCKED_QUANTITY,ps.PART_STATUS,ps.LAST_STOCK_IN,ps.LAST_STOCK_OUT,ps.FOUND_DATE,ps.REMARK,ps.D_KEY,ps.CREATED_BY,"
				+ " ( STOCK_QUANTITY + BORROW_QUANTITY - LEND_QUANTITY) AS USEABLE_STOCK,( CASE WHEN ( STOCK_QUANTITY + BORROW_QUANTITY - LEND_QUANTITY - ("
				+ partQuantity + ") - LOCKED_QUANTITY ) < - 0.00000001  THEN 12781001"
				+ "  ELSE 12781002 END ) AS ISNEGATIVE, ( CASE WHEN ( ( STOCK_QUANTITY * ps.COST_PRICE <> ps.COST_AMOUNT AND ( STOCK_QUANTITY = 0 OR ps.COST_AMOUNT = 0 ) ) OR ps.COST_PRICE = 0"
				+ " ) THEN 12781001 ELSE 12781002 END ) AS ISNORMAL,pl.LEND_NO FROM TM_PART_STOCK ps "
				+ "LEFT JOIN tt_part_lend_item pli ON pli.`DEALER_CODE` = ps.`DEALER_CODE` AND pli.`PART_NO`=ps.`PART_NO`"
				+ "LEFT JOIN tt_part_lend pl ON pl.`DEALER_CODE`=pli.`DEALER_CODE` AND pl.`LEND_NO`=pli.`LEND_NO`	"
				+ "WHERE ps.DEALER_CODE = '" + dealerCode + "' AND ps.D_KEY = " + CommonConstants.D_KEY);
		sql.append(" AND ( ( ( STOCK_QUANTITY + BORROW_QUANTITY - LEND_QUANTITY - (" + partQuantity + ") - "
				+ "LOCKED_QUANTITY < - 0.00000001 ) OR ( ( STOCK_QUANTITY * ps.COST_PRICE <> ps.COST_AMOUNT ) AND ( STOCK_QUANTITY = 0 OR ps.COST_AMOUNT = 0 )) ) OR ps.COST_PRICE = 0 ");
		if (!StringUtils.isNullOrEmpty(storageCodes)) {
			sql.append(" and ps.STORAGE_CODE = '" + storageCodes + "' ");
		} else {
			sql.append(" and  1 = 1 ");
		}
		if (!StringUtils.isNullOrEmpty(partNo2s)) {
			sql.append(" and ps.PART_NO =  '" + partNo2s + "'  ) ");
		} else {
			sql.append(" and 1 = 1 ");
		}
		queryList = DAOUtil.findAll(sql.toString(), null);
		System.err.println(sql + "*******************");
		return queryList;
	}

	/**
	 * 查询配件库存批次信息
	 * 
	 * @param partQuantity
	 * @param dealerCode
	 * @param partNo2
	 * @param storageCode
	 * @return
	 */
	public List queryStockItem(Float partQuantity, String dealerCode, String[] partNo2, String[] storageCode) {
		StringBuffer sql = new StringBuffer("");
		List<Object> queryList = new ArrayList<Object>();
		sql.append(" select " + partQuantity
				+ " as PART_QUANTITY,DEALER_CODE, PART_NO, STORAGE_CODE, STORAGE_POSITION_CODE, PART_NAME, "
				+ " SPELL_CODE, PART_GROUP_CODE, UNIT_CODE, STOCK_QUANTITY, SALES_PRICE,STOCK_QUANTITY AS STOCK_QUANTITY_STOCK,COST_PRICE,COST_AMOUNT, "
				+ " CLAIM_PRICE, LIMIT_PRICE, LATEST_PRICE,  COST_AMOUNT, MAX_STOCK, "
				+ " MIN_STOCK, BORROW_QUANTITY, LEND_QUANTITY, LOCKED_QUANTITY, PART_STATUS, "
				+ " LAST_STOCK_IN, LAST_STOCK_OUT, FOUND_DATE, REMARK, D_KEY, CREATED_BY,(STOCK_QUANTITY+BORROW_QUANTITY-LEND_QUANTITY) AS USEABLE_STOCK, "
				+ " ( CASE WHEN (STOCK_QUANTITY+BORROW_QUANTITY-LEND_QUANTITY-(" + partQuantity
				+ ")-LOCKED_QUANTITY)<-0.00000001 THEN 12781001 ELSE 12781002 END )   AS ISNEGATIVE, "
				+ " (CASE WHEN ( (STOCK_QUANTITY*COST_PRICE<>COST_AMOUNT AND (STOCK_QUANTITY=0 OR COST_AMOUNT=0)) OR COST_PRICE=0)  THEN 12781001 ELSE 12781002 END)  AS ISNORMAL "
				+ " from  TM_PART_STOCK " + " WHERE DEALER_CODE = '" + dealerCode + "' " + " AND D_KEY =  "
				+ CommonConstants.D_KEY);
		sql.append(" AND ( ( (STOCK_QUANTITY+BORROW_QUANTITY-LEND_QUANTITY-(" + partQuantity
				+ ")-LOCKED_QUANTITY<-0.00000001) OR ( (STOCK_QUANTITY*COST_PRICE<>COST_AMOUNT) AND (STOCK_QUANTITY=0 OR COST_AMOUNT=0 ) )) OR  COST_PRICE=0 ) ");
		if (!StringUtils.isNullOrEmpty(storageCode)) {
			sql.append(" and STORAGE_CODE = '" + storageCode + "' ");
		} else {
			sql.append(" and  1 = 1 ");
		}
		if (!StringUtils.isNullOrEmpty(partNo2)) {
			sql.append(" and PART_NO =  '" + partNo2 + "'  ");
		} else {
			sql.append(" and 1 = 1 ");
		}
		return queryList;
	}

	/**
	 * 是否入账
	 */
	@Override
	public PageInfoDto findAccount(Map<String, String> queryParam) throws SerialException {
		StringBuffer sql = new StringBuffer("");
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		String[] partQuantity = {};
		sql.append(" select " + partQuantity
				+ " as PART_QUANTITY,pt.DEALER_CODE,pt.PART_NO, pt.STORAGE_CODE, pt.STORAGE_POSITION_CODE, pt.PART_NAME, "
				+ " pt.SPELL_CODE, pt.PART_GROUP_CODE, pt.UNIT_CODE, pt.STOCK_QUANTITY, pt.SALES_PRICE,pt.STOCK_QUANTITY AS STOCK_QUANTITY_STOCK,pt.COST_PRICE,pt.COST_AMOUNT, "
				+ " pt.CLAIM_PRICE, pt.LIMIT_PRICE, pt.LATEST_PRICE, pt.COST_PRICE, pt.COST_AMOUNT, pt.MAX_STOCK, "
				+ " pt.MIN_STOCK, pt.BORROW_QUANTITY, pt.LEND_QUANTITY, pt.LOCKED_QUANTITY, pt.PART_STATUS, "
				+ " pt.LAST_STOCK_IN, pt.LAST_STOCK_OUT, pt.FOUND_DATE, pt.REMARK, pt.D_KEY, pt.CREATED_BY,(pt.STOCK_QUANTITY+pt.BORROW_QUANTITY-pt.LEND_QUANTITY) AS USEABLE_STOCK, "
				+ " ( CASE WHEN (pt.STOCK_QUANTITY+pt.BORROW_QUANTITY-pt.LEND_QUANTITY-(" + partQuantity
				+ ")-pt.LOCKED_QUANTITY)<-0.00000001 THEN 12781001 ELSE 12781002 END )   AS ISNEGATIVE, "
				+ " (CASE WHEN ( (pt.STOCK_QUANTITY*pt.COST_PRICE<>pt.COST_AMOUNT AND (pt.STOCK_QUANTITY=0 OR pt.COST_AMOUNT=0)) OR pt.COST_PRICE=0)  THEN 12781001 ELSE 12781002 END)  AS ISNORMAL "
				+ " from  TM_PART_STOCK pt" + " LEFT JOIN TM_PART_LEND pl WHERE pl.DEALER_CODE=pt.DEALER_CODE "
				+ " WHERE pt.DEALER_CODE = '" + dealerCode + "' " + " AND pt.D_KEY =  " + CommonConstants.D_KEY);
		sql.append(" AND ( ( (pt.STOCK_QUANTITY+pt.BORROW_QUANTITY-pt.LEND_QUANTITY-(" + partQuantity
				+ ")-pt.LOCKED_QUANTITY<-0.00000001) OR ( (pt.STOCK_QUANTITY*pt.COST_PRICE<>pt.COST_AMOUNT) AND (pt.STOCK_QUANTITY=0 OR pt.COST_AMOUNT=0 ) )) OR pt.COST_PRICE=0 ) ");
		if (!StringUtils.isNullOrEmpty(queryParam.get("storageCode"))) {
			sql.append(" and pt.STORAGE_CODE = '" + queryParam.get("storageCode") + "' ");
		} else {
			sql.append(" and  1 = 1 ");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("partNo2"))) {
			sql.append(" and pt.PART_NO =  '" + queryParam.get("partNo2") + "'  ");
		} else {
			sql.append(" and 1 = 1 ");
		}
		PageInfoDto pageInfoDto = DAOUtil.pageQuery(sql.toString(), null);
		return pageInfoDto;
	}

	/**
	 * 查询出库
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings({ "unused", "rawtypes" })
	@Override
	public String btnOut(String lendNo, List<Map> lendDetail2) throws Exception {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		String empNo = FrameworkUtil.getLoginInfo().getEmployeeNo();
		Long userId = FrameworkUtil.getLoginInfo().getUserId();
		String id = commonNoService.getSystemOrderNo(CommonConstants.SRV_JCDJDH);// 借出登记单LEND_NO
		double costAmountBeforeA = 0; // 批次表入账前成本
		double costAmountBeforeB = 0; // 库存表入账前成本
		double costAmountAfterA = 0; // 批次表入账后成本
		double costAmountAfterB = 0; // 库存表入账后成本
		// 1.查询本月的报表是否完成
		// if (isFinishedThisMonth().size() > 0) {
		// 2.查询当前时间的会计周期是否做过月结
		// if (getIsFinished().size() > 0) {

		TmPartInfoPO tmPartInfoPO = new TmPartInfoPO();
		// List listCheck = getNonOemPartListOut("TT_PART_LEND_ITEM", "LEND_NO",
		// lendNo);
		// String errPart = "";
		// if (listCheck != null && listCheck.size() > 0) {
		// for (int i = 0; i < listCheck.size(); i++) {
		// TtPartLendItemPO ttPartLendItemPO = (TtPartLendItemPO)
		// listCheck.get(i);
		// if (errPart.equals("")) {
		// errPart = ttPartLendItemPO.getString("PART_NO");
		// } else {
		// errPart = errPart + ", " + ttPartLendItemPO.getString("PART_NO");
		// }
		// }
		// } else {
		// throw new SerialException(errPart + " 非OEM配件不允许出OEM库,请重新操作!");
		// }

		/**
		 * 相同账号分别调出同一张未入账的调拨入库单，点入账造成流水账中该入库单，有2条重复的流水账记录, 判断该入库单是否已经入账
		 * flag==12781002没有入账；反之已经入账
		 */
		String sheetNoType = "LEND_NO";
		String sheetType = "TT_PART_LEND";
		// String flag = checkIsFinished(dealerCode, lendNo, sheetType,
		// sheetNoType);
		// if (flag.equals(DictCodeConstants.DICT_IS_YES)) {
		// throw new SerialException("入库单号:" + lendNo + "已经入账，不能重复入账！");
		// }
		// VER校验
		String verNow = "";
		List listi = TtPartLendPO.findBySQL(
				" SELECT * FROM TT_PART_LEND WHERE DEALER_CODE = ?  AND LEND_NO = ? ", dealerCode,lendDetail2.get(0).get("LEND_NO"));
		if (listi != null && listi.size() > 0) {
			TtPartLendPO ttPartLendPO2 = (TtPartLendPO) listi.get(0);
			verNow = ttPartLendPO2.getString("VER");
		}
		// if (!(ver.trim().equals(verNow.trim()))) {
		// // 校验未通过
		// throw new SerialException("单据状态已改变，请重新选择");
		// }
		// 借出单号不为空，根据借出单号查询借出单明细
		// 1、修改借出登记单 是否入帐字段为 是
		TtPartLendPO.update("IS_FINISHED=?", "IS_FINISHED=?", new Date(System.currentTimeMillis()), userId);
		if (!StringUtils.isNullOrEmpty(lendDetail2.get(0).get("LEND_NO"))) {
			List list = TtPartLendItemPO.findBySQL(
					"  SELECT * FROM TT_PART_LEND_ITEM WHERE DEALER_CODE = ? AND D_KEY = ? AND LEND_NO = ? ",
					dealerCode, CommonConstants.D_KEY, lendDetail2.get(0).get("LEND_NO"));
			if (list.size() > 0 && list != null) {
				int count = list.size();
				List listitemnow = new ArrayList<>();
				for (int i = 0; i < count; i++) {
					costAmountBeforeA = 0; // 批次表入账前成本
					costAmountBeforeB = 0; // 库存表入账前成本
					costAmountAfterA = 0; // 批次表入账后成本
					costAmountAfterB = 0; // 库存表入账后成本

					listitemnow = TmPartStockItemPO.findBySQL(
							"  SELECT * FROM TM_PART_STOCK_ITEM WHERE DEALER_CODE = ? AND D_KEY = ? AND PART_NO = ? AND STORAGE_CODE = ? ",
							dealerCode, CommonConstants.D_KEY, lendDetail2.get(0).get("PART_NO"),
							lendDetail2.get(0).get("STORAGE_CODE"));
					TmPartStockItemPO tmPartStockItemPO = (TmPartStockItemPO) listitemnow.get(i);
					if (listitemnow != null && listitemnow.size() > 0) {
						if (tmPartStockItemPO.getDouble("COST_AMOUNT") != 0
								&& (tmPartStockItemPO.getDouble("COST_AMOUNT") > 0)) {
							costAmountBeforeA = tmPartStockItemPO.getDouble("COST_AMOUNT");// 批次表入帐前成本金额
						}
					}

					List stocknow = TmPartStockPO.findBySQL(
							"   SELECT * FROM TM_PART_STOCK WHERE DEALER_CODE = ?  AND PART_NO = ? AND STORAGE_CODE = ? AND D_KEY = ? ",
							dealerCode, lendDetail2.get(0).get("PART_NO"), lendDetail2.get(0).get("STORAGE_CODE"),
							CommonConstants.D_KEY);
					TmPartStockPO stockPO = (TmPartStockPO) stocknow.get(0);
					if (stocknow != null && stocknow.size() > 0) {
						if (stockPO.getDouble("COST_AMOUNT") != null && (stockPO.getDouble("COST_AMOUNT") != 0)) {
							costAmountBeforeB = stockPO.getDouble("COST_AMOUNT"); // 库存表入账前成本金额
						}
					}

					// 更新配件库存批次表和配件库存表中借出数量
					TmPartStockItemPO stockItemPO = TmPartStockItemPO.findByCompositeKeys(dealerCode,
							lendDetail2.get(0).get("PART_NO"), lendDetail2.get(0).get("STORAGE_CODE"),
							lendDetail2.get(0).get("PART_BATCH_NO"));
					stockItemPO.setString("DEALER_CODE", dealerCode);
					stockItemPO.setString("PART_NO", lendDetail2.get(0).get("PART_NO"));
					stockItemPO.setString("STORAGE_CODE", lendDetail2.get(0).get("STORAGE_CODE"));
					if (lendDetail2.get(0).get("PART_BATCH_NO") != null
							&& !"".equals(lendDetail2.get(0).get("PART_BATCH_NO"))) {
						stockItemPO.setString("PART_BATCH_NO", (lendDetail2.get(0).get("PART_BATCH_NO")));
					} else {
						stockItemPO.setString("PART_BATCH_NO", CommonConstants.defaultBatchName);
					}
					stockItemPO.setDouble("STOCK_QUANTITY", (lendDetail2.get(0).get("OUT_QUANTITY")));
					calLendQuantity(dealerCode, stockItemPO, lendDetail2);
					List listItemAfter = TmPartStockItemPO.findBySQL(
							" SELECT * FROM TM_PART_STOCK_ITEM WHERE DEALER_CODE = ?  AND PART_NO = ? AND STORAGE_CODE = ? AND PART_BATCH_NO = ?",
							dealerCode, lendDetail2.get(0).get("PART_NO"), lendDetail2.get(0).get("STORAGE_CODE"),
							lendDetail2.get(0).get("PART_BATCH_NO"));
					TmPartStockItemPO itemAfter = (TmPartStockItemPO) listItemAfter.get(0);
					if (listItemAfter != null && listItemAfter.size() > 0) {
						if (itemAfter.getDouble("COST_AMOUNT") != null
								&& !"".equals(itemAfter.getDouble("COST_AMOUNT"))) {
							costAmountAfterA = itemAfter.getDouble("COST_AMOUNT"); // 批次表入账后成本金额
						}
					}
					// 更新库存最后出库日期 1.字段 2.条件 3.字段+条件的值
					int stockPO1 = TmPartStockPO.update("LAST_STOCK_OUT=?",
							"DEALER_CODE=? AND D_KEY=? AND PART_NO=? AND UPDATED_BY=? AND UPDATED_AT=?", dealerCode,
							CommonConstants.D_KEY, lendDetail2.get(0).get("PART_NO"), userId,
							new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()));
					List listStockAfter = TmPartStockPO.findBySQL(
							" SELECT * FROM TM_PART_STOCK_ITEM WHERE DEALER_CODE = ?  AND PART_NO = ? AND STORAGE_CODE = ? ",
							dealerCode, lendDetail2.get(0).get("PART_NO"), lendDetail2.get(0).get("STORAGE_CODE"));
					TmPartStockPO StockAfter = (TmPartStockPO) listStockAfter.get(0);
					if (listStockAfter != null && listStockAfter.size() > 0) {
						if (StockAfter.getDouble("COST_AMOUNT") != null
								&& !"".equals(StockAfter.getDouble("COST_AMOUNT"))) {
							costAmountAfterB = StockAfter.getDouble("COST_AMOUNT"); // 库存表入账后成本金额
						}
					}
					// 4、向配件流水帐填加一条记录
					PartFlowPO ttPartFlowPO = new PartFlowPO();
//					ttPartFlowPO.setString("DEALER_CODE ", dealerCode);
					ttPartFlowPO.setString("STORAGE_CODE", lendDetail2.get(0).get("STORAGE_CODE"));// 仓库代码
					ttPartFlowPO.setString("PART_NO", lendDetail2.get(0).get("PART_NO"));// 配件代码
					ttPartFlowPO.setString("PART_BATCH_NO", lendDetail2.get(0).get("PART_BTACH_NO"));// 进货批号
					ttPartFlowPO.setString("PART_NAME", lendDetail2.get(0).get("PART_NAME"));// 配件名称
					ttPartFlowPO.setDouble("COST_AMOUNT_AFTER_A", tmPartStockItemPO.getDouble("COST_AMOUNT"));
					ttPartFlowPO.setDouble("COST_AMOUNT_AFTER_B", stockPO.getDouble("COST_AMOUNT"));
					ttPartFlowPO.setDouble("COST_AMOUNT_BEFORE_A", itemAfter.getDouble("COST_AMOUNT"));
					ttPartFlowPO.setDouble("COST_AMOUNT_BEFORE_B", StockAfter.getDouble("COST_AMOUNT"));
					// 从借出登记主表里获取客户名称、客户代码
					List partlendlist = TtPartLendPO.findBySQL(
							" SELECT * FROM TT_PART_LEND WHERE DEALER_CODE=? AND D_KEY=? AND LEND_NO=?", dealerCode,
							CommonConstants.D_KEY, lendDetail2.get(0).get("LEND_NO"));
					TtPartLendPO partLendPO = (TtPartLendPO) partlendlist.get(0);
					ttPartFlowPO.setString("CUSTOMER_CODE", lendDetail2.get(0).get("CUSTOMER_CODE"));// 客户代码
					ttPartFlowPO.setString("CUSTOMER_NAME", lendDetail2.get(0).get("CUSTOMER_NAME")); // 客户名称
					TmPartStockItemPO partStockItemPO = TmPartStockItemPO.findByCompositeKeys(dealerCode,
							lendDetail2.get(0).get("PART_NO"), lendDetail2.get(0).get("STORAGE_CODE"),
							lendDetail2.get(0).get("PART_BATCH_NO"));
					partStockItemPO.setString("DEALER_CODE", dealerCode);
					partStockItemPO.setString("PART_NO", lendDetail2.get(0).get("PART_NO"));
					partStockItemPO.setString("STORAGE_CODE", lendDetail2.get(0).get("STORAGE_CODE"));
					if (lendDetail2.get(0).get("PART_BATCH_NO") != null
							&& !"".equals(lendDetail2.get(0).get("PART_BATCH_NO"))) {
						stockItemPO.setString("PART_BATCH_NO", (lendDetail2.get(0).get("PART_BATCH_NO")));
					} else {
						stockItemPO.setString("PART_BATCH_NO", CommonConstants.defaultBatchName);
					}
					if (listitemnow != null && listitemnow.size() > 0) {
						partStockItemPO = (TmPartStockItemPO) listitemnow.get(0);
						ttPartFlowPO.setDouble("STOCK_QUANTITY", partStockItemPO.getDouble("STOCK_QUANTITY"));
						ttPartFlowPO.setString("CREATED_BY", userId); // CreateBy
						ttPartFlowPO.setDouble("COST_PRICE", lendDetail2.get(0).get("COST_PRICE")); // 成本单价
						ttPartFlowPO.setDouble("COST_AMOUNT", lendDetail2.get(0).get("COST_AMOUNT")); // 成本金额
						ttPartFlowPO.setString("OPERATOR", empNo); // 操作员
						ttPartFlowPO.setDate("OPERATE_DATE",new Timestamp(System.currentTimeMillis()));// 操作日期
						double amount = Utility.add("1", Utility.getPartRate("2034"));
						// 销售价借出(销售价是含税的)
						String rate = Double.toString(amount);
						ttPartFlowPO.setDouble("IN_OUT_NET_PRICE",
								(Utility.div(lendDetail2.get(0).get("COST_PRICE").toString(), rate)));// 出入库不含税单价
						ttPartFlowPO.setDouble("IN_OUT_TAXED_PRICE", (lendDetail2.get(0).get("OUT_PRICE").toString())); // 出入库含税单价
						ttPartFlowPO.setDouble("IN_OUT_NET_AMOUNT",
								(Utility.div(lendDetail2.get(0).get("OUT_AMOUNT").toString(), rate)));// 出入库不含税金额
						ttPartFlowPO.setDouble("IN_OUT_TAXED_AMOUNT", lendDetail2.get(0).get("OUT_AMOUNT").toString()); // 出入库含税金额
						ttPartFlowPO.setDouble("STOCK_OUT_QUANTITY", lendDetail2.get(0).get("OUT_QUANTITY").toString()); // 借出数量
						ttPartFlowPO.setDouble("IN_OUT_TYPE",
								(Utility.getInt(DictCodeConstants.DICT_IN_OUT_TYPE_PART_LEND)));// 出入库类型
						ttPartFlowPO.setInteger("IN_OUT_TAG", (Utility.getInt(DictCodeConstants.DICT_IS_YES)));// 是否出库
						ttPartFlowPO.setString("SHEET_NO", lendNo); // 单据号码
						ttPartFlowPO.saveIt();
					}
				}
			}
		}
		// 配件借出登记生成凭证
		performExecute(lendNo, lendDetail2);
		// 解锁
		String[] noValue = { lendDetail2.get(0).get("LEND_NO").toString() };
		Utility.updateByUnLock("TT_PART_LEND", FrameworkUtil.getLoginInfo().getUserId().toString(), "LEND_NO", noValue,
				"LOCK_USER");
		return id;
		// }
		// else {
		// throw new ServiceBizException("当前会计月报没有完成");
		// }
		// } else {
		// throw new ServiceBizException("当前配件月报没有完成");

	}

	/**
	 * 查询本月的报表是否完成
	 * 
	 * @return
	 */
	// @SuppressWarnings("rawtypes")
	// private List<Map> isFinishedThisMonth() {
	// StringBuffer sb = new StringBuffer();
	// sb.append("SELECT DEALER_CODE,REPORT_YEAR FROM TT_MONTH_CYCLE ");
	// sb.append("WHERE REPORT_YEAR = '" + Utility.getYear() + "' ");
	// sb.append("AND REPORT_MONTH = '" + Utility.getMonth() + "' ");
	// return DAOUtil.findAll(sb.toString(), null);
	// }

	/**
	 * 查询当前时间的会计周期是否做过月结
	 * 
	 * @return
	 */
	// @SuppressWarnings("rawtypes")
	// private List<Map> getIsFinished() {
	// StringBuffer sb = new StringBuffer();
	// sb.append("SELECT
	// DEALER_CODE,B_YEAR,PERIODS,BEGIN_DATE,END_DATE,IS_EXECUTED ");
	// sb.append(" FROM TM_ACCOUNTING_CYCLE WHERE ");
	// sb.append("CURRENT_TIMESTAMP BETWEEN BEGIN_DATE AND END_DATE");
	// return DAOUtil.findAll(sb.toString(), null);
	// }

	/**
	 * 功能描述：描述当前方法所要实现的功能，可以简要描述操作的数据内容
	 * 
	 * @param conn
	 * @param sheetTable
	 * @param sheetName
	 * @param sheetNo
	 * @return
	 * @throws Exception
	 */
	// @SuppressWarnings({ "rawtypes", "unused" })
	// public List getNonOemPartListOut(String sheetTable, String sheetName,
	// String sheetNo) throws Exception {
	// if
	// (!Utility.getIsOEMPartOutCheck().equals(DictCodeConstants.DICT_IS_YES)) {
	// return null;
	// }
	// List returnList = null;
	// String sql = null;
	// String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
	// if (sheetTable == "TT_SALES_PART_ITEM" ||
	// "TT_SALES_PART_ITEM".equals(sheetTable)) {
	// String fieldName = "PART_QUANTITY";
	// sql = "select AA.PART_NO,AA.STORAGE_CODE,SUM(AA.PART_QUANTITY) as
	// PART_QUANTITY FROM"
	// + " (select A.PART_NO,A.PART_NAME,A.STORAGE_CODE," + " A." + fieldName +
	// " As PART_QUANTITY "
	// + " from " + sheetTable + " A "
	// + " left join TM_PART_INFO B on (A.part_NO = B.PART_NO AND A.DEALER_CODE
	// = B.DEALER_CODE) "
	// + " WHERE A.DEALER_CODE = '" + dealerCode + "' " + " AND B.DOWN_TAG = "
	// + DictCodeConstants.DICT_IS_NO + " " + " AND A.STORAGE_CODE ='OEMK'" + "
	// AND A." + sheetName
	// + " = '" + sheetNo + "'"
	// + " ) AA group by AA.PART_NO,AA.STORAGE_CODE having SUM(AA.PART_QUANTITY)
	// <> 0 ";
	// } else {
	// sql = " select A.PART_NO,A.PART_NAME,A.STORAGE_CODE from " + sheetTable +
	// " A "
	// + " left join TM_PART_INFO B on (A.part_NO = B.PART_NO AND A.DEALER_CODE
	// = B.DEALER_CODE) "
	// + " WHERE A.DEALER_CODE = '" + dealerCode + "' " + " AND B.DOWN_TAG = "
	// + DictCodeConstants.DICT_IS_NO + " " + " AND A.STORAGE_CODE ='OEMK'" + "
	// AND A." + sheetName
	// + " = '" + sheetNo + "'";
	// }
	// return returnList;
	// }

	/**
	 * 校验配件是否已经入账
	 * 
	 * @param sheetNo
	 * @param conn
	 * @param entityCode
	 * @param sheetType
	 * @param sheetNoType
	 * @return
	 * @throws Exception
	 */
	// public static String checkIsFinished(String sheetNo, String dealerCode,
	// String sheetType, String sheetNoType)
	// throws Exception {
	// String flag = DictCodeConstants.DICT_IS_YES;
	// if (sheetNo != null && !"".equals(sheetNo.trim()) && sheetType != null &&
	// !"".equals(sheetType.trim())) {
	// StringBuffer sql = new StringBuffer("");
	// sql.append(" select * from " + sheetType + " where DEALER_CODE='" +
	// dealerCode + "' and d_key="
	// + CommonConstants.D_KEY + " and " + sheetNoType + "='" + sheetNo + "' and
	// IS_FINISHED="
	// + DictCodeConstants.DICT_IS_NO + " ");
	// }
	// return flag;
	// }

	/**
	 * 更新配件库存批次表库存数量借出数量
	 * 
	 * @param conn
	 * @param entityCode
	 * @param itemPO
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	public void calLendQuantity(String dealerCode, TmPartStockItemPO stockItemPO, List<Map> lendDetail2)
			throws Exception {

		int tmPartStockItemPO = TmPartStockItemPO.update("LEND_QUANTITY=?",
				"DEALER_CODE=? AND D_KEY=? AND PART_NO=? AND STORAGE_CODE=? AND PART_BATCH_NO =? ",
				lendDetail2.get(0).get("STOCK_QUANTITY"), dealerCode, CommonConstants.D_KEY,
				lendDetail2.get(0).get("PART_NO"), lendDetail2.get(0).get("STORAGE_CODE"),
				lendDetail2.get(0).get("PART_BATCH_NO"));
		int tmPartStockPO = TmPartStockPO.update("LEND_QUANTITY=?",
				"DEALER_CODE=? AND D_KEY=? AND PART_NO=? AND STORAGE_CODE=? ", lendDetail2.get(0).get("STOCK_QUANTITY"),
				dealerCode, CommonConstants.D_KEY, lendDetail2.get(0).get("PART_NO"),
				lendDetail2.get(0).get("STORAGE_CODE"));
	}

	/**
	 * 配件借出登记生成凭证
	 * 
	 * @param lendNo
	 * @param dealerCode
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	private int performExecute(String lendNo, List<Map> lendDetail2) throws Exception {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		Long userId = FrameworkUtil.getLoginInfo().getUserId();
		String defaultValue = Utility.getDefaultValue("8901");
		if (!StringUtils.isNullOrEmpty(defaultValue) && DictCodeConstants.DICT_IS_YES.equals(defaultValue)) {// 获取开关设置
			float cess = Float.parseFloat(Utility.getDefaultValue("1003"));
			List list = TtPartLendPO.findBySQL(
					"SELECT * FROM TT_PART_LEND WHERE DEALER_CODE = ? AND D_KEY = ? RETURN_NO = ?", dealerCode,
					CommonConstants.D_KEY, lendDetail2.get(0).get("lendNo"));
			if (list != null && list.size() > 0) {
				TtAccountsTransFlowPO po = new TtAccountsTransFlowPO();
				po.setString("ORG_CODE", dealerCode);
				po.setString("DEALER_CODE", dealerCode);
				po.setDate("TRANS_DATE", Utility.getCurrentTimestamp());
				po.setInteger("TRANS_TYPE", Integer.parseInt(DictCodeConstants.DICT_BUSINESS_TYPE_STOCK_LEND));
				po.setDouble("TAX_AMOUNT", po.getDouble("OUT_AMOUNT"));
				po.setDouble("NET_AMOUNT", po.getDouble("OUT_AMOUNT") * (1F - cess));
				po.setString("BUSINESS_NO", lendDetail2.get(0).get("lendNo"));
				po.setInteger("IS_VALID", (Utility.getInt(DictCodeConstants.DICT_IS_YES)));
				po.setInteger("EXEC_NUM", 0);
				po.setInteger("EXEC_STATUS", Integer.parseInt(DictCodeConstants.DICT_EXEC_STATUS_NOT_EXEC));// 未生产
				po.setDate("CREATED_AT", Utility.getCurrentDateTime());
				po.setString("CREATED_AT", userId);
				po.saveIt();
			}
		}
		return 1;
	}

	/**
	 * 作废
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void deleteAllocateIn(String lendNo) throws ServiceBizException {
		// 对借出登记单操作
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		List list=TtPartLendItemPO.findBySQL(" SELECT * FROM TT_PART_LEND_ITEM WHERE LEND_NO =? ",lendNo );
		//删除子表
		if (list!=null&&list.size()>0) {
			TtPartLendItemPO.delete("DEALER_CODE=? AND D_KEY=? AND LEND_NO=?",dealerCode,CommonConstants.D_KEY,lendNo);
		}
		List list2=TtPartLendPO.findBySQL("  SELECT * FROM TT_PART_LEND WHERE LEND_NO =?  ", lendNo);
		if (list2!=null&&list2.size()>0) {
			TtPartLendPO.delete("DEALER_CODE=? AND D_KEY=? AND LEND_NO=?",dealerCode,CommonConstants.D_KEY,lendNo);
		}
	}

}