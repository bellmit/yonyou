/**
 * 
 */
package com.yonyou.dms.part.service.basedata;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.TmPartStockItemPO;
import com.yonyou.dms.common.domains.PO.basedata.TmPartStockPO;
import com.yonyou.dms.common.domains.PO.basedata.TtAccountsTransFlowPO;
import com.yonyou.dms.common.domains.PO.basedata.TtPartLendItemPO;
import com.yonyou.dms.common.domains.PO.basedata.TtPartLendPO;
import com.yonyou.dms.common.domains.PO.basedata.TtPartLendReturnItemPO;
import com.yonyou.dms.common.domains.PO.basedata.TtPartLendReturnPO;
import com.yonyou.dms.commonAS.domains.PO.basedata.PartFlowPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.BaseModel;
import com.yonyou.dms.framework.service.CommonNoService;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.part.domains.DTO.basedata.LendToReturnDTO;

/**
 * @author yangjie
 *
 */
@Service
@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
public class LendToReturnServiceImpl implements LendToReturnService {

	@Autowired
	private CommonNoService commonNoService;

	@Override
	public PageInfoDto findAllList(Map<String, String> query) {
		StringBuffer sb = new StringBuffer();
		sb.append("select A.DEALER_CODE,A.LEND_NO,A.CUSTOMER_CODE,A.CUSTOMER_NAME,A.COST_AMOUNT,");
		sb.append("A.OUT_AMOUNT,A.HANDLER,A.LEND_DATE,A.IS_FINISHED,A.FINISHED_DATE,A.PAY_OFF,A.BORROWER_TAG,");
		sb.append("tu.USER_NAME as LOCK_USER,tu.USER_ID ,A.SHEET_CREATE_DATE,B.SO_NO,B.VIN from TT_PART_LEND A left join TT_SALES_ORDER B on");
		sb.append(" A.SO_NO=B.SO_NO AND A.DEALER_CODE=B.DEALER_CODE LEFT JOIN TM_USER tu ON A.DEALER_CODE=tu.DEALER_CODE AND A.LOCK_USER = tu.USER_ID ");
		sb.append(" where A.PAY_OFF=" + DictCodeConstants.IS_NOT);
		sb.append(" and A.D_KEY=" + CommonConstants.D_KEY);
		sb.append(" and A.IS_FINISHED=" + DictCodeConstants.IS_YES);
		Utility.sqlToLike(sb, query.get("LEND_NO"), "LEND_NO", "A");
		Utility.sqlToLike(sb, query.get("CUSTOMER_CODE"), "CUSTOMER_CODE", "A");
		return DAOUtil.pageQuery(sb.toString(), null);
	}

	@Override
	public PageInfoDto findAllDetails(String id,Map<String, String> queryParam) throws ServiceBizException {
		// 先锁定用户
		TtPartLendPO ttPartLendPO = new TtPartLendPO();
		int flag = Utility.updateByLocker("TT_PART_LEND", FrameworkUtil.getLoginInfo().getUserId().toString(), "LEND_NO", id, "LOCK_USER");
		if (flag>0) {
			StringBuffer sb = new StringBuffer();
			sb.append(
					"select A.VER,A.ITEM_ID,A.LEND_NO,A.DEALER_CODE,ts.STORAGE_NAME as STORAGE_CODE,A.STORAGE_POSITION_CODE,A.PART_BATCH_NO,A.PART_NO,A.PART_NAME,");
			sb.append(" A.UNIT_CODE,A.OUT_QUANTITY,A.WRITE_OFF_QUANTITY,A.COST_PRICE,A.COST_AMOUNT,A.OUT_PRICE,");
			sb.append(
					" A.OUT_AMOUNT,B.DOWN_TAG from TT_PART_LEND_ITEM  A LEFT JOIN TM_STORAGE ts ON ts.DEALER_CODE=A.DEALER_CODE AND ts.STORAGE_CODE = A.STORAGE_CODE LEFT JOIN TM_PART_INFO B ON A.DEALER_CODE = B.DEALER_CODE AND A.PART_NO = B.PART_NO where A.DEALER_CODE= '"
							+ FrameworkUtil.getLoginInfo().getDealerCode());
			sb.append("' and A.d_key= " + CommonConstants.D_KEY);
			Utility.sqlToLike(sb, id, "LEND_NO", "A");
			return DAOUtil.pageQuery(sb.toString(), null);
		} else {
			throw new ServiceBizException("单号[" + id + "]加锁失败!");
		}
	}

	@Override
	public String operate(LendToReturnDTO dto) throws ServiceBizException {
		double costAmountBeforeA = 0; // 批次表入账前成本
		double costAmountBeforeB = 0; // 库存表入账前成本
		double costAmountAfterA = 0; // 批次表入账后成本
		double costAmountAfterB = 0; // 库存表入账后成本
		String entityCode = FrameworkUtil.getLoginInfo().getDealerCode();
		String empNo = FrameworkUtil.getLoginInfo().getEmployeeNo();
		Long userId = FrameworkUtil.getLoginInfo().getUserId();
		String id = commonNoService.getSystemOrderNo(CommonConstants.SRV_JCGHDH);// 借出归还单Return_No
		String treturnNo = "";
		// 1.查询本月的报表是否完成
		if (isFinishedThisMonth().size() > 0) {
			// 2.查询当前时间的会计周期是否做过月结
			if (getIsFinished().size() > 0) {
				// 3.配件入库借出归还
				// 主表
				TtPartLendReturnPO lendReturnPO = new TtPartLendReturnPO();
				lendReturnPO.setString("DEALER_CODE", entityCode);
				lendReturnPO.setString("RETURN_NO", id);
				lendReturnPO.setString("CUSTOMER_CODE", dto.getCusNo());
				lendReturnPO.setString("CUSTOMER_NAME", dto.getCusName());
				lendReturnPO.set("RETURN_DATE", new Timestamp(System.currentTimeMillis()));
				lendReturnPO.setLong("IS_FINISHED", DictCodeConstants.IS_NOT);
				lendReturnPO.set("FINISHED_DATE", new Timestamp(System.currentTimeMillis()));
				lendReturnPO.setString("HANDLER", empNo);
				lendReturnPO.setString("LOCK_USER", "");
				lendReturnPO.saveIt();
				treturnNo = id;
				// 子表
				if (dto.getDms_show().size() > 0) {
					for (Map<String, String> map : dto.getDms_show()) {
						map.put("STORAGE_CODE", map.get("storageCode"));
						costAmountBeforeA = 0; // 批次表入账前成本
						costAmountBeforeB = 0; // 库存表入账前成本
						costAmountAfterA = 0; // 批次表入账后成本
						costAmountAfterB = 0; // 库存表入账后成本
						// 如果归还数量为0则不记录借出归还明细
						if (Double.parseDouble(map.get("RETURN_QUANTITY")) != 0) {
							// 新增借出归还明细信息
							TtPartLendReturnItemPO lendReturnItemPO = new TtPartLendReturnItemPO();
							lendReturnItemPO.setString("DEALER_CODE", entityCode);
							lendReturnItemPO.setString("RETURN_NO", id);
							if (!StringUtils.isNullOrEmpty(map.get("STORAGE_POSITION_CODE"))) {
								lendReturnItemPO.setString("STORAGE_POSITION_CODE", map.get("STORAGE_POSITION_CODE"));
							}
							if (!StringUtils.isNullOrEmpty(map.get("lendNo").toString())) {
								lendReturnItemPO.setString("LEND_NO", map.get("lendNo").toString());
							}
							if (!StringUtils.isNullOrEmpty(map.get("STORAGE_CODE"))) {
								lendReturnItemPO.setString("STORAGE_CODE", map.get("STORAGE_CODE"));
							}
							if (!StringUtils.isNullOrEmpty(map.get("PART_BATCH_NO"))) {
								lendReturnItemPO.setString("PART_BATCH_NO", map.get("PART_BATCH_NO"));
							}
							if (!StringUtils.isNullOrEmpty(map.get("PART_NO"))) {
								lendReturnItemPO.setString("PART_NO", map.get("PART_NO"));
							}
							if (!StringUtils.isNullOrEmpty(map.get("PART_NAME"))) {
								lendReturnItemPO.setString("PART_NAME", map.get("PART_NAME"));
							}
							if (!StringUtils.isNullOrEmpty(map.get("UNIT_CODE"))) {
								lendReturnItemPO.setString("UNIT_CODE", map.get("UNIT_CODE"));
							}
							if (!StringUtils.isNullOrEmpty(map.get("RETURN_QUANTITY"))) {
								lendReturnItemPO.setDouble("RETURN_QUANTITY",
										Double.parseDouble(map.get("RETURN_QUANTITY")));
							}
							if (!StringUtils.isNullOrEmpty(map.get("COST_PRICE"))) {
								lendReturnItemPO.setDouble("LEND_COST_PRICE",
										Double.parseDouble(map.get("COST_PRICE")));
							}
							if (!StringUtils.isNullOrEmpty(map.get("OUT_PRICE"))) {
								lendReturnItemPO.setDouble("LEND_PRICE", Double.parseDouble(map.get("OUT_PRICE")));
							}
							lendReturnItemPO.saveIt();
						}
						// 根据借出单号、配件代码、仓库代码查询借出登记明细核销数量 判断出库数量和核销数量
						StringBuffer sb = new StringBuffer();
						sb.append("SELECT * FROM TT_PART_LEND_ITEM WHERE 1=1");
						sb.append(" AND D_KEY = ? ");
						Utility.sqlToEquals(sb, map.get("lendNo").toString(), "LEND_NO", null);
						Utility.sqlToEquals(sb, map.get("PART_NO"), "PART_NO", null);
						Utility.sqlToEquals(sb, map.get("STORAGE_CODE"), "STORAGE_CODE", null);
						List queryParam = new ArrayList();
						queryParam.add(CommonConstants.D_KEY);
						List<Map> all = DAOUtil.findAll(sb.toString(), queryParam);
						float outquantity = 0;// 出库数量
						float writeoffquantity = 0;// 核销数量
						if (all.size() > 0) {
							Map map2 = all.get(0);
							if (!StringUtils.isNullOrEmpty(map2.get("OUT_QUANTITY"))) {
								outquantity = Float.parseFloat(map2.get("OUT_QUANTITY").toString());
							}
							if (!StringUtils.isNullOrEmpty(map2.get("WRITE_OFF_QUANTITY"))) {
								writeoffquantity = Float.parseFloat(map2.get("WRITE_OFF_QUANTITY").toString());
							}
							writeoffquantity = writeoffquantity + Float.parseFloat(map.get("RETURN_QUANTITY"));
							// 更新借出登记单明细里核销数量
							// 根据借出单号.配件代码.仓库更新明细里核销数量字段
							List<TtPartLendItemPO> list = new ArrayList<TtPartLendItemPO>();
							if (StringUtils.isNullOrEmpty(map.get("lendNo").toString())
									&& StringUtils.isNullOrEmpty(map.get("STORAGE_CODE"))
									&& StringUtils.isNullOrEmpty(map.get("PART_NO"))) {
								list = TtPartLendItemPO.findBySQL(
										"SELECT * FROM TT_PART_LEND_ITEM WHERE DEALER_CODE = ? AND D_KEY = ?",
										entityCode, CommonConstants.D_KEY);
							} else {
								list = TtPartLendItemPO.findBySQL(
										"SELECT * FROM TT_PART_LEND_ITEM WHERE DEALER_CODE = ? AND D_KEY = ? AND LEND_NO = ? AND STORAGE_CODE = ? AND PART_NO = ?",
										entityCode, CommonConstants.D_KEY, map.get("lendNo").toString(), map.get("STORAGE_CODE"),
										map.get("PART_NO"));
							}
							for (TtPartLendItemPO ttPartLendItemPO : list) {
								ttPartLendItemPO.setFloat("WRITE_OFF_QUANTITY", writeoffquantity);
								ttPartLendItemPO.saveIt();
							}
						}
						List<TmPartStockItemPO> listitemnow = TmPartStockItemPO.findBySQL(
								"SELECT * FROM TM_PART_STOCK_ITEM WHERE DEALER_CODE = ? AND D_KEY = ? AND PART_NO = ? AND STORAGE_CODE = ?",
								entityCode, CommonConstants.D_KEY, map.get("PART_NO"), map.get("STORAGE_CODE"));
						if (listitemnow.size() > 0) {
							TmPartStockItemPO itemPOb = listitemnow.get(0);
							if (!StringUtils.isNullOrEmpty(itemPOb.getDouble("COST_AMOUNT"))) {
								costAmountBeforeA = itemPOb.getDouble("COST_AMOUNT");// 批次表入帐前成本金额
							}
						}
						TmPartStockPO partStockPO = TmPartStockPO.findByCompositeKeys(entityCode, map.get("PART_NO"),
								map.get("STORAGE_CODE"));
						if (partStockPO != null && partStockPO.getInteger("D_KEY") == CommonConstants.D_KEY) {
							if (!StringUtils.isNullOrEmpty(partStockPO.getDouble("COST_AMOUNT"))) {
								costAmountBeforeB = partStockPO.getDouble("COST_AMOUNT");// 库存表入账前成本金额
							}
						}
						// 更新配件库存批次表和配件库存表借出数量
						String partBatchNo = "";
						if (StringUtils.isNullOrEmpty(map.get("PART_BATCH_NO"))) {
							partBatchNo = CommonConstants.defaultBatchName;
						} else {
							partBatchNo = map.get("PART_BATCH_NO");
						}
						List<TmPartStockItemPO> list = TmPartStockItemPO.findBySQL(
								"SELECT * FROM TM_PART_STOCK_ITEM WHERE DEALER_CODE = ? AND D_KEY = ? AND PART_NO = ? AND STORAGE_CODE = ? AND PART_BATCH_NO = ?",
								entityCode, CommonConstants.D_KEY, map.get("PART_NO"), map.get("STORAGE_CODE"),
								map.get("PART_BATCH_NO"));
						for (TmPartStockItemPO tmPartStockItemPO : list) {
							tmPartStockItemPO.setFloat("LEND_QUANTITY", tmPartStockItemPO.getFloat("LEND_QUANTITY")
									- Float.parseFloat(map.get("RETURN_QUANTITY")));
							tmPartStockItemPO.saveIt();
						}
						List<TmPartStockPO> list2 = TmPartStockPO.findBySQL(
								"SELECT * FROM TM_PART_STOCK WHERE DEALER_CODE = ? AND D_KEY = ? AND PART_NO = ? AND STORAGE_CODE = ?",
								entityCode, CommonConstants.D_KEY, map.get("PART_NO"), map.get("STORAGE_CODE"));
						for (TmPartStockPO tmPartStockPO : list2) {
							tmPartStockPO.setFloat("LEND_QUANTITY", tmPartStockPO.getFloat("LEND_QUANTITY")
									- Float.parseFloat(map.get("RETURN_QUANTITY")));
							tmPartStockPO.saveIt();
						}
						// 向配件流水账增加一条记录
						Double amount = Utility.add("1", Utility.getDefaultValue("2034"));
						String rate = Double.toString(amount);
						PartFlowPO partFlowPO = new PartFlowPO();
						partFlowPO.setString("DEALER_CODE", entityCode);
						partFlowPO.setString("STORAGE_CODE", map.get("STORAGE_CODE"));
						partFlowPO.setString("PART_NO", map.get("PART_NO"));
						partFlowPO.setString("PART_BATCH_NO", map.get("PART_BATCH_NO"));
						partFlowPO.setString("PART_NAME", map.get("PART_NAME"));
						partFlowPO.setString("SHEET_NO", map.get("lendNo").toString());
						partFlowPO.setInteger("IN_OUT_TYPE",
								Integer.parseInt(DictCodeConstants.DICT_IN_OUT_TYPE_LEND_RETURN));// 出入库类型
						partFlowPO.setLong("IN_OUT_TAG", DictCodeConstants.IS_NOT);
						partFlowPO.setFloat("STOCK_IN_QUANTITY", Float.parseFloat(map.get("RETURN_QUANTITY")));// 进数量

						// 出入库含税单价-借出单价
						partFlowPO.setDouble("IN_OUT_TAXED_PRICE", Double.parseDouble(map.get("OUT_PRICE")));// 出入库含税单价
						partFlowPO.setDouble("IN_OUT_NET_PRICE", Utility.div(map.get("OUT_PRICE"), rate));// 出入库不含税单价
						partFlowPO.setDouble("IN_OUT_NET_AMOUNT", Utility.mul(partFlowPO.getString("IN_OUT_NET_PRICE"),
								String.valueOf(Float.parseFloat(map.get("RETURN_QUANTITY")))));// 出入库不含税金额
						partFlowPO.setDouble("IN_OUT_TAXED_AMOUNT",
								Utility.mul(String.valueOf(Float.parseFloat(map.get("RETURN_QUANTITY"))),
										String.valueOf(map.get("OUT_PRICE"))));// 出入库含税金额

						// 查询库存数量
						String partBatchNoA = "";
						if (StringUtils.isNullOrEmpty(map.get("PART_BATCH_NO"))) {
							partBatchNoA = CommonConstants.defaultBatchName;
						} else {
							partBatchNoA = map.get("PART_BATCH_NO");
						}
						TmPartStockItemPO stockItemPO = TmPartStockItemPO.findByCompositeKeys(entityCode,
								map.get("PART_NO"), map.get("STORAGE_CODE"), map.get("PART_BATCH_NO"));
						if (!StringUtils.isNullOrEmpty(stockItemPO)
								&& stockItemPO.getInteger("D_KEY") == CommonConstants.D_KEY) {
							// 成本单价-借出单价 成本金额=成本单价*归还数量
							partFlowPO.setDouble("COST_PRICE", stockItemPO.getDouble("COST_PRICE"));// 成本单价
							partFlowPO.setDouble("COST_AMOUNT", stockItemPO.getDouble("COST_AMOUNT"));// 成本金额
						}
						partFlowPO.setString("CUSTOMER_CODE", dto.getCusNo());
						partFlowPO.setString("CUSTOMER_NAME", dto.getCusName());
						partFlowPO.setString("OPERATOR", empNo);
						partFlowPO.set("OPERATE_DATE", new Timestamp(System.currentTimeMillis()));
						partFlowPO.setDouble("COST_AMOUNT_BEFORE_A", costAmountBeforeA);
						partFlowPO.setDouble("COST_AMOUNT_BEFORE_A", costAmountBeforeB);
						partFlowPO.setDouble("COST_AMOUNT_AFTER_A", costAmountAfterA);
						partFlowPO.setDouble("COST_AMOUNT_AFTER_B", costAmountAfterB);
						partFlowPO.saveIt();
					}
					List<Map> listcheck = getNonOemPartList(entityCode, "TT_PART_LEND_RETURN_ITEM", "RETURN_NO",
							treturnNo);
					String errPart = "";
					if (listcheck.size() > 0) {
						for (Map map2 : listcheck) {
							if (errPart.equals("")) {
								errPart = StringUtils.isNullOrEmpty(map2.get("PART_NO")) ? ""
										: map2.get("PART_NO").toString();
							} else {
								errPart = errPart + ", " + (StringUtils.isNullOrEmpty(map2.get("PART_NO")) ? ""
										: map2.get("PART_NO").toString());
							}
						}
					}
					if (!errPart.equals("")) {
						throw new ServiceBizException(errPart + " 非OEM配件不允许入OEM库,请重新操作!");
					}
					// 更新借出登记主表是否结清字段
					checkIsPayOf(entityCode, dto, userId);
				}
				/**
				 * 借出归还登记生成凭证
				 */
				List<TtPartLendReturnItemPO> plric = TtPartLendReturnItemPO.findBySQL(
						"SELECT * FROM TT_PART_LEND_RETURN_ITEM WHERE DEALER_CODE = ? AND D_KEY = ? RETURN_NO = ?",
						entityCode, CommonConstants.D_KEY, treturnNo);
				String defaultValue = Utility.getDefaultValue("8901");
				if(!StringUtils.isNullOrEmpty(defaultValue)&&DictCodeConstants.DICT_IS_YES.equals(defaultValue)){//获取开关设置
					Float cess = Float.parseFloat(Utility.getDefaultValue("1003"));
					for (TtPartLendReturnItemPO plr : plric) {
						TtAccountsTransFlowPO  po  = new  TtAccountsTransFlowPO();
						 po.setString("ORG_CODE",entityCode);
						 po.setString("DEALER_CODE",entityCode);			
						 po.set("TRANS_DATE", new Timestamp(System.currentTimeMillis()));					
						 po.setInteger("TRANS_TYPE",Integer.parseInt(DictCodeConstants.DICT_BUSINESS_TYPE_PART_BORROW_RETURN));
						 po.setDouble("TAX_AMOUNT",plr.getDouble("OUT_PRICE"));
						 po.setFloat("NET_AMOUNT",plr.getFloat("OUT_PRICE")*(1F-cess));
						 po.setString("BUSINESS_NO",plr.getString("RETURN_NO"));
						 po.setString("SUB_BUSINESS_NO",plr.getString("LEND_NO"));
						 po.setLong("IS_VALID",DictCodeConstants.IS_YES);
						 po.setInteger("EXEC_NUM",0);
						 po.setInteger("EXEC_STATUS",Integer.parseInt(DictCodeConstants.DICT_EXEC_STATUS_NOT_EXEC));//未生产
						 po.saveIt();
					}
				}
				//解锁
				String[] noValue = dto.getLendNo().split(",");
				Utility.updateByUnLock("TT_PART_LEND", FrameworkUtil.getLoginInfo().getUserId().toString(), "LEND_NO", noValue , "LOCK_USER");
				return id;
			} else {
				throw new ServiceBizException("当前会计月报没有完成");
			}
		} else {
			throw new ServiceBizException("当前配件月报没有完成");
		}
	}

	/**
	 * 查询本月的报表是否完成
	 * 
	 * @return
	 */
	private List<Map> isFinishedThisMonth() {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT DEALER_CODE,REPORT_YEAR FROM TT_MONTH_CYCLE ");
		sb.append("WHERE REPORT_YEAR = '" + Utility.getYear() + "' ");
		sb.append("AND REPORT_MONTH = '" + Utility.getMonth() + "' ");
		return DAOUtil.findAll(sb.toString(), null);
	}

	/**
	 * 查询当前时间的会计周期是否做过月结
	 * 
	 * @return
	 */
	private List<Map> getIsFinished() {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT DEALER_CODE,B_YEAR,PERIODS,BEGIN_DATE,END_DATE,IS_EXECUTED ");
		sb.append(" FROM TM_ACCOUNTING_CYCLE WHERE ");
		sb.append("CURRENT_TIMESTAMP BETWEEN BEGIN_DATE AND END_DATE");
		return DAOUtil.findAll(sb.toString(), null);
	}

	/**
	 * 查询业务表 sheetTable 关联Tm_part_info
	 * 
	 * @param entityCode
	 * @param sheetTable
	 * @param sheetName
	 * @param sheetNo
	 * @return
	 */
	public List<Map> getNonOemPartList(String entityCode, String sheetTable, String sheetName, String sheetNo) {
		StringBuffer sb = new StringBuffer();
		sb.append(" select A.DEALER_CODE,A.PART_NO,A.PART_NAME,A.STORAGE_CODE from ").append(sheetTable);
		sb.append(" A  left join TM_PART_INFO B on (A.part_NO = B.PART_NO AND A.DEALER_CODE = B.DEALER_CODE) ");
		sb.append(" WHERE A.DEALER_CODE = ? AND B.DOWN_TAG = ? ");
		sb.append(" AND A.STORAGE_CODE ='OEMK' AND A.").append(sheetName).append(" = ?");
		List queryParam = new ArrayList();
		queryParam.add(entityCode);
		queryParam.add(DictCodeConstants.IS_NOT);
		queryParam.add(sheetNo);
		return DAOUtil.findAll(sb.toString(), queryParam);
	}

	private static void checkIsPayOf(String entityCode, LendToReturnDTO dto, long userId) {
		// 在借出登记明细里判断出库数量和核销数量是否一致
		for (Map<String, String> map : dto.getDms_show()) {
			// 该单结清
			String mark = "1";
			List<TtPartLendItemPO> itemPOCon = TtPartLendItemPO.findBySQL(
					"SELECT * FROM TT_PART_LEND_ITEM WHERE DEALER_CODE = ? AND D_KEY = ? AND LEND_NO = ?", entityCode,
					CommonConstants.D_KEY, map.get("LEND_NO"));
			if (itemPOCon.size() > 0) {
				for (TtPartLendItemPO itemPO : itemPOCon) {
					String writeOffQuantity = "";
					if (!StringUtils.isNullOrEmpty(itemPO)) {
						if (StringUtils.isNullOrEmpty(itemPO.getFloat("WRITE_OFF_QUANTITY"))) {
							writeOffQuantity = "0.0";
						} else {
							writeOffQuantity = itemPO.getString("WRITE_OFF_QUANTITY").trim();
						}
					}
					String outQuantity = itemPO.getString("OUT_QUANTITY").trim();
					if (!(outQuantity.equals(writeOffQuantity))) {
						mark = "0";// 没有结清
					}
				}
				if ("1".equals(mark.trim())) {
					TtPartLendPO lendPO = TtPartLendPO.findByCompositeKeys(entityCode, map.get("LEND_NO"));
					if (!StringUtils.isNullOrEmpty(lendPO) && lendPO.getInteger("D_KEY") == CommonConstants.D_KEY) {
						lendPO.setLong("PAY_OFF", DictCodeConstants.IS_YES);
					}
					if (map.get("LEND_NO") != null && !"".equals(map.get("LEND_NO")) && entityCode != null
							&& !"".equals(entityCode.trim()))
						lendPO.saveIt();
				}
			}
		}
	}

	@Override
	public List<Map> findAllDetailsForLocale(String id, Map<String, String> queryParam) {
		// 先锁定用户
		TtPartLendPO ttPartLendPO = new TtPartLendPO();
//		int flag = Utility.updateByLocker(TtPartLendPO.getMetaModel(), FrameworkUtil.getLoginInfo().getUserId().toString(), "LEND_NO", id, "LOCK_USER");
//		if (flag>0) {
			List<Map> fullList = new ArrayList<Map>();
			String[] split = id.split(",");
			for (int i = 0; i < split.length; i++) {
				StringBuffer sb = new StringBuffer();
				sb.append(
						"select A.VER,A.ITEM_ID,A.LEND_NO,A.DEALER_CODE,A.STORAGE_CODE,ts.STORAGE_NAME as STORAGE_NAME,A.STORAGE_POSITION_CODE,A.PART_BATCH_NO,A.PART_NO,A.PART_NAME,");
				sb.append(" tun.UNIT_NAME as UNIT_CODE,A.OUT_QUANTITY,A.WRITE_OFF_QUANTITY,A.COST_PRICE,A.COST_AMOUNT,A.OUT_PRICE,OUT_QUANTITY-WRITE_OFF_QUANTITY AS NOTIN_QUANTITY,");
				sb.append(
						" A.OUT_AMOUNT,B.DOWN_TAG from TT_PART_LEND_ITEM  A LEFT JOIN TM_UNIT tun ON tun.UNIT_CODE=A.UNIT_CODE AND tun.DEALER_CODE=A.DEALER_CODE LEFT JOIN TM_STORAGE ts ON ts.DEALER_CODE=A.DEALER_CODE AND ts.STORAGE_CODE = A.STORAGE_CODE LEFT JOIN TM_PART_INFO B ON A.DEALER_CODE = B.DEALER_CODE AND A.PART_NO = B.PART_NO where A.DEALER_CODE= '"
								+ FrameworkUtil.getLoginInfo().getDealerCode());
				sb.append("' and A.d_key= " + CommonConstants.D_KEY);
				Utility.sqlToLike(sb, split[i], "LEND_NO", "A");
				List<Map> findAll = DAOUtil.findAll(sb.toString(), null);
				if(!CommonUtils.isNullOrEmpty(findAll)){
					fullList.addAll(findAll);
				}
			}
			return fullList;
//		} else {
//			throw new ServiceBizException("单号[" + id + "]加锁失败!");
//		}
	}

	@Override
	public List<Map> findStorageCode() {
		String sql = "SELECT DEALER_CODE,STORAGE_CODE,STORAGE_NAME FROM TM_STORAGE";
		return DAOUtil.findAll(sql, null);
	}
}
