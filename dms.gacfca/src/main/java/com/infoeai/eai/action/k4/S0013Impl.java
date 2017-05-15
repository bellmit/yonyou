package com.infoeai.eai.action.k4;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoeai.eai.action.BaseService;
import com.infoeai.eai.dao.k4.K4SICommonDao;
import com.infoeai.eai.po.TiK4DealerRebateDtlPO;
import com.infoeai.eai.po.TtDealerRebateDtlPO;
import com.infoeai.eai.po.TtDealerRebatePO;
import com.infoeai.eai.po.TtDealerRebateTypePO;
import com.infoeai.eai.vo.S0013VO;
import com.infoeai.eai.vo.returnVO;
import com.yonyou.dms.common.Util.CheckUtil;
import com.yonyou.dms.function.common.OemDictCodeConstants;

@Service
public class S0013Impl extends BaseService implements S0013 {
	private static final Logger logger = LoggerFactory.getLogger(S0013Impl.class);
	@Autowired
	private K4SICommonDao dao;
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	String format = df.format(new Date());

	@Override
	public List<returnVO> execute(List<S0013VO> volist) throws Throwable {

		logger.info("==========S0013 is begin==========");

		List<returnVO> returnvolist = new ArrayList<returnVO>();
		String returnvo[] = null;
		beginDbService();

		try {

			for (int i = 0; i < volist.size(); i++) {

				returnvo = S0013Check(volist.get(i)); // 校验数据

				if (null == returnvo) {

					/*
					 * 数据通过验证
					 */

					this.insertTiK4DealerRebateDtl(volist.get(i), OemDictCodeConstants.IF_TYPE_YES.toString(),
							returnvo); // 插入接口表
					this.insertBusinessData(volist.get(i)); // 插入或更新业务表

				} else {

					/*
					 * 数据未通过验证
					 */

					this.insertTiK4DealerRebateDtl(volist.get(i), OemDictCodeConstants.IF_TYPE_NO.toString(), returnvo); // 插入接口表

					returnVO revo = new returnVO();
					revo.setOutput(returnvo[0]);
					revo.setMessage(returnvo[1]);
					returnvolist.add(revo);

					logger.info("==========S00013返回不合格数据========== ROWId为==========" + returnvo[0]);
					logger.info("==========S00013返回不合格数据========== Message为==========" + returnvo[1]);
				}
			}

			dbService.endTxn(true); // 关闭事务

		} catch (Exception e) {
			logger.info(e.getMessage(), e);
			dbService.endTxn(false);
			throw new Exception("S0013业务处理异常！" + e);
		} finally {
			logger.info("==========S0013 is filsh==========");
			dbService.clean(); // 清除事务
		}

		return returnvolist;

	}

	/**
	 * 插入或更新业务数据
	 * 
	 * @param vo
	 * @throws Throwable
	 */
	private void insertBusinessData(S0013VO vo) {

		try {

			logger.info("==========S0013业务数据处理开始==========");

			Long rebateid = null;

			Long typeid = 0l;

			Date time = parseString2DateTime(vo.getSelectDate() + " " + vo.getSelectTime(), "yyyyMMdd HHmmss");

			Date dealingtime = parseString2DateTime(vo.getDealingsDate() + " " + vo.getDealingsTime(),
					"yyyyMMdd HHmmss");

			// 操作经销商返利表

			Map<String, Object> map = dao.getDealerByFCACode(vo.getDealerCode());
			List<TtDealerRebatePO> list = TtDealerRebatePO.find("DEALER_ID=?", map.get("DEALER_ID"));

			if (list != null && list.size() > 0) {
				rebateid = (Long) list.get(0).get("Rebate_Id");
				for (TtDealerRebatePO tpo : list) {

					tpo.setDouble("Rebate_Pool_Amount", getDouble(vo.getRebatePoolAmount()));
					tpo.setDouble("Used_Amount", getDouble(vo.getUsedAmount()));
					tpo.setDouble("Advance_Amount", getDouble(vo.getAdvanceAmount()));
					tpo.setDouble("Frozen_Amount", getDouble(vo.getFrozenAmount()));
					tpo.setDouble("Availabel_Amount", getDouble(vo.getAvailabelAmount()));
					tpo.setDouble("Entry_Amount", getDouble(vo.getEntryAmount()));
					tpo.setDouble("Deduction_Amount", getDouble(vo.getDeductionAmount()));
					tpo.setDouble("B_Period_Amount", getDouble(vo.getBPeriodAmount()));
					tpo.setDouble("E_Period_Amount", getDouble(vo.getEPeriodAmount()));
					tpo.setTimestamp("Update_Date", time);
					tpo.setInteger("Update_By", OemDictCodeConstants.K4_S0013);
					tpo.setInteger("Is_Del", OemDictCodeConstants.IS_DEL_00);
					tpo.setInteger("Status", OemDictCodeConstants.STATUS_ENABLE);
					tpo.saveIt();
				}

			} else {

				// rebateid = new Long(SequenceManager.getSequence(""));
				TtDealerRebatePO po = new TtDealerRebatePO();
				// po.setRebateId(rebateid);
				po.setDouble("Rebate_Pool_Amount", getDouble(vo.getRebatePoolAmount()));
				po.setDouble("Used_Amount", getDouble(vo.getUsedAmount()));
				po.setDouble("Advance_Amount", getDouble(vo.getAdvanceAmount()));
				po.setDouble("Frozen_Amount", getDouble(vo.getFrozenAmount()));
				po.setDouble("Availabel_Amount", getDouble(vo.getAvailabelAmount()));
				po.setDouble("Entry_Amount", getDouble(vo.getEntryAmount()));
				po.setDouble("Deduction_Amount", getDouble(vo.getDeductionAmount()));
				po.setDouble("B_Period_Amount", getDouble(vo.getBPeriodAmount())); // 期初数
				po.setDouble("E_Period_Amount", getDouble(vo.getEPeriodAmount())); // 期末数
				po.setTimestamp("Create_Date", time);
				po.setInteger("Create_By", OemDictCodeConstants.K4_S0013);
				po.setInteger("Is_Del", OemDictCodeConstants.IS_DEL_00);
				po.setInteger("Status", OemDictCodeConstants.STATUS_ENABLE);
				po.saveIt();

			}

			// 操作经销商返利类型表
			TtDealerRebateTypePO type = new TtDealerRebateTypePO();
			List<TtDealerRebateTypePO> typelist = null;
			if (OemDictCodeConstants.REBATE_TYPE_1.equals(vo.getDealingsType())) { // 判断是否是来款
				// 0来款,
				type.setInteger("Dealings_Type", OemDictCodeConstants.REBATE_DIRACT_01);
				typelist = TtDealerRebateTypePO.find("REBATE_TYPE_CODE=? and DEALINGS_TYPE=?", vo.getRebateType(),
						OemDictCodeConstants.REBATE_DIRACT_01);// 1扣款 // 发放
			} else {
				type.setInteger("Dealings_Type", OemDictCodeConstants.REBATE_DIRACT_02); // 使用
				typelist = TtDealerRebateTypePO.find("REBATE_TYPE_CODE=? and DEALINGS_TYPE=?", vo.getRebateType(),
						OemDictCodeConstants.REBATE_DIRACT_02);// 1扣款 // 发放
			}

			type.setString("Rebate_Type_Desc", vo.getRebateDescription());

			if (typelist != null && typelist.size() > 0) {

				typeid = (Long) typelist.get(0).get("Rebate_Type_Id");

				type.saveIt();

			} else {

				// typeid = new Long(SequenceManager.getSequence(""));
				type.setString("Rebate_Type_Desc", vo.getRebateDescription());
				type.setInteger("Oem_Company_Id", Long.valueOf(OemDictCodeConstants.OEM_ACTIVITIES));
				type.setLong("Rebate_Type_Id", typeid);
				type.setInteger("Create_By", OemDictCodeConstants.K4_S0013);
				type.setTimestamp("Create_Date", format);
				type.setInteger("Status", OemDictCodeConstants.STATUS_ENABLE);
				type.saveIt();

			}

			// 操作经销商返利明细表
			TtDealerRebateDtlPO tpod = new TtDealerRebateDtlPO();
			tpod.setLong("Rebate_Id", rebateid);
			// tpod.setRebateDtlId(new Long(SequenceManager.getSequence("")));

			// if (StringUtil.notNull(vo.getSoNo()) && doSONOCheck(vo.getSoNo())
			// == Constant.IF_TYPE_YES) {
			// // 查询订单主数据表
			// TtVsOrderPO spo = new TtVsOrderPO();
			// spo.setSoNo(vo.getSoNo());
			// List<TtVsOrderPO> spolist = factory.select(spo);
			// tpod.setOrderNo(spolist.get(0).getSoNo());
			// }

			tpod.setString("Order_No", vo.getSoNo());

			tpod.setString("Vin", vo.getVin());
			tpod.setString("Remark", vo.getRemark());
			tpod.setDouble("Amount", getDouble(vo.getAmount()));
			tpod.setString("Rebate_Mark", vo.getRebateMark());
			tpod.setString("Rebate_Month", vo.getRebateMonth());
			tpod.setString("Channel", vo.getChannel());

			if (OemDictCodeConstants.REBATE_TYPE_1.equals(vo.getDealingsType())) { // 判断是否是来款
				// 0来款,
				// 1扣款
				tpod.setInteger("Rebate_Direction", OemDictCodeConstants.REBATE_DIRACT_01); // 发放
			} else {
				tpod.setInteger("Rebate_Direction", OemDictCodeConstants.REBATE_DIRACT_02); // 使用
			}

			tpod.setLong("Type_Id", typeid);
			tpod.setTimestamp("Create_Date", dealingtime);
			tpod.setInteger("Create_By", OemDictCodeConstants.K4_S0013);
			tpod.setInteger("Is_Del", OemDictCodeConstants.IS_DEL_00);
			tpod.setInteger("Status", OemDictCodeConstants.STATUS_ENABLE);

			tpod.saveIt();

			logger.info("==========S0013业务数据处理结束==========");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private double getDouble(String index) {
		double dRtn;
		if (index == null || index.trim().equals("") || "null".equalsIgnoreCase(index)) {
			dRtn = new Double(0);
			return dRtn;
		}
		return Double.parseDouble(index);
	}

	private Date parseString2DateTime(String dateStr, String dateFormat) throws ParseException {

		if (null == dateStr || dateStr.length() <= 0)
			return null;

		if (null == dateFormat || dateFormat.length() <= 0)
			dateFormat = "yyyy-MM-dd HH:mm:ss";

		DateFormat formatter = new SimpleDateFormat(dateFormat);
		Date date = formatter.parse(dateStr);
		return date;
	}

	/**
	 * 校验通过数据写入接口表
	 * 
	 * @param vo
	 */
	private void insertTiK4DealerRebateDtl(S0013VO vo, String isResult, String returnvo[]) {

		logger.info("==========S0013 接口表数据写入开始==========");

		TiK4DealerRebateDtlPO po = new TiK4DealerRebateDtlPO();

		// po.setIfId(Long.valueOf(SequenceManager.getSequence(""))); // 主键ID

		po.setString("SELECT_DATE", vo.getSelectDate()); // 查询日期
		po.setString("Select_Time", vo.getSelectTime()); // 查询时间
		po.setString("Dealer_Code", vo.getDealerCode()); // 经销商代码
		po.setString("Rebate_Pool_Amount", vo.getRebatePoolAmount()); // 返利池金额
		po.setString("Used_Amount", vo.getUsedAmount()); // 已用金额
		po.setString("Advance_Amount", vo.getAdvanceAmount()); // 占用金额
		po.setString("Frozen_Amount", vo.getFrozenAmount()); // 红票兑付冻结金额
		po.setString("Availabel_Amount", vo.getAvailabelAmount()); // 可用余额
		po.setString("Entry_Amount", vo.getEntryAmount()); // 本期进入返利池合计
		po.setString("Deduction_Amount", vo.getDeductionAmount()); // 本期扣除返利合计
		po.setString("B_Period_Amount", vo.getBPeriodAmount()); // 期初数
		po.setString("E_Period_Amount", vo.getEPeriodAmount()); // 期末数
		po.setString("So_No", vo.getSoNo()); // SAP销售订单号
		po.setString("Vin", vo.getVin()); // 车架号
		po.setString("Rebate_Type", vo.getRebateType()); // 返利类型编码
		po.setString("Rebate_Description", vo.getRebateDescription()); // 返利类型描述
		po.setString("Dealings_Date", vo.getDealingsDate()); // 往来日期
		po.setString("Dealings_Time", vo.getDealingsTime()); // 往来时间
		po.setString("Remark", vo.getRemark()); // 摘要
		po.setString("Amount", vo.getAmount()); // 金额
		po.setString("Dealings_Type", vo.getDealingsType()); // 往来类型
		po.setString("Rebate_Mark", vo.getRebateMark()); // 返利说明
		po.setString("Rebate_Month", vo.getRebateMonth()); // 返利产生月份
		po.setString("Channel", vo.getChannel()); // 渠道
		po.setString("Row_Id", vo.getRowId()); // ROW_ID
		po.setString("Is_Result", isResult); // 结果标识(是：10041001；否：10041002)
		po.setInteger("Is_Del", 0); // 逻辑删除
		po.setInteger("Create_By", OemDictCodeConstants.K4_S0013); // 创建人ID
		po.setTimestamp("Create_Date", format); // 创建日期

		if (isResult.equals(OemDictCodeConstants.IF_TYPE_NO.toString())) {
			po.setString("Is_Message", returnvo[1]); // 错误信息描述
		}

		po.saveIt();

		logger.info("==========S0013 接口表数据写入结束==========");

	}

	private String[] S0013Check(S0013VO vo) {

		logger.info("==========S0013逻辑校验开始==========");

		String[] returnvo = new String[2];

		// 查询日期
		if (CheckUtil.checkNull(vo.getSelectDate())) {
			returnvo[0] = vo.getRowId();
			returnvo[1] = "查询日期长度为空";
			return returnvo;
		}

		if (vo.getSelectDate().length() != 8) {
			returnvo[0] = vo.getRowId();
			returnvo[1] = "查询日期长度与接口定义不一致：" + vo.getSelectDate();
			return returnvo;
		}

		// 查询时间
		if (CheckUtil.checkNull(vo.getSelectTime())) {
			returnvo[0] = vo.getRowId();
			returnvo[1] = "查询时间长度为空";
			return returnvo;
		}

		if (vo.getSelectTime().length() != 6) {
			returnvo[0] = vo.getRowId();
			returnvo[1] = "查询时间长度与接口定义不一致：" + vo.getSelectTime();
			return returnvo;
		}

		// 经销商代码
		if (CheckUtil.checkNull(vo.getDealerCode())) {
			returnvo[0] = vo.getRowId();
			returnvo[1] = "经销商代码为空";
			return returnvo;
		}

		if (dodealerCheck(vo.getDealerCode()) == OemDictCodeConstants.IF_TYPE_NO) {
			returnvo[0] = vo.getRowId();
			returnvo[1] = "经销商代码不存在";
			return returnvo;
		}

		// 返利池金额
		if (CheckUtil.checkNull(vo.getRebatePoolAmount())) {
			returnvo[0] = vo.getRowId();
			returnvo[1] = "返利池金额为空";
			return returnvo;
		}

		if (vo.getRebatePoolAmount().length() > 15) {
			returnvo[0] = vo.getRowId();
			returnvo[1] = "返利池金额不得大于15位" + vo.getRebatePoolAmount();
			return returnvo;
		}

		// 已使用额度
		if (CheckUtil.checkNull(vo.getUsedAmount())) {
			returnvo[0] = vo.getRowId();
			returnvo[1] = "已使用额度为空";
			return returnvo;
		}

		if (vo.getUsedAmount().length() > 15) {
			returnvo[0] = vo.getRowId();
			returnvo[1] = "已使用额度不得大于15位" + vo.getUsedAmount();
			return returnvo;
		}

		// 占用金额
		if (CheckUtil.checkNull(vo.getAdvanceAmount())) {
			returnvo[0] = vo.getRowId();
			returnvo[1] = "占用金额为空";
			return returnvo;
		}

		if (vo.getAdvanceAmount().length() > 15) {
			returnvo[0] = vo.getRowId();
			returnvo[1] = "占用金额不得大于15位" + vo.getAdvanceAmount();
			return returnvo;
		}

		// 红票兑付冻结金额
		if (CheckUtil.checkNull(vo.getFrozenAmount())) {
			returnvo[0] = vo.getRowId();
			returnvo[1] = "红票兑付冻结金额为空";
			return returnvo;
		}

		if (vo.getFrozenAmount().length() > 15) {
			returnvo[0] = vo.getRowId();
			returnvo[1] = "红票兑付冻结金额不得大于15位" + vo.getFrozenAmount();
			return returnvo;
		}

		// 可用余额
		if (CheckUtil.checkNull(vo.getAvailabelAmount())) {
			returnvo[0] = vo.getRowId();
			returnvo[1] = "可用余额为空";
			return returnvo;
		}

		if (vo.getAvailabelAmount().length() > 15) {
			returnvo[0] = vo.getRowId();
			returnvo[1] = "可用余额不得大于15位" + vo.getAvailabelAmount();
			return returnvo;
		}

		// 本期进入返利池合计
		if (CheckUtil.checkNull(vo.getEntryAmount())) {
			returnvo[0] = vo.getRowId();
			returnvo[1] = "本期进入返利池合计为空";
			return returnvo;
		}

		if (vo.getEntryAmount().length() > 15) {
			returnvo[0] = vo.getRowId();
			returnvo[1] = "本期进入返利池合计不得大于15位" + vo.getEntryAmount();
			return returnvo;
		}

		// 本期扣除返利合计
		if (CheckUtil.checkNull(vo.getDeductionAmount())) {
			returnvo[0] = vo.getRowId();
			returnvo[1] = "期扣除返利合计为空";
			return returnvo;
		}

		if (vo.getDeductionAmount().length() > 15) {
			returnvo[0] = vo.getRowId();
			returnvo[1] = "期扣除返利合计不得大于15位" + vo.getDeductionAmount();
			return returnvo;
		}

		/*
		 * // 期初数 if (CheckUtil.checkNull(vo.getBPERIODAMOUNT())) { returnvo[0]
		 * = vo.getRowId(); returnvo[1] = "期初数为空"; return returnvo; }
		 * 
		 * if (vo.getBPERIODAMOUNT().length() > 15) { returnvo[0] =
		 * vo.getRowId(); returnvo[1] = "期初数不得大于15位" + vo.getBPERIODAMOUNT();
		 * return returnvo; }
		 * 
		 * // 期末数 System.out.println(vo.getEPERIODAMOUNT()); if
		 * (CheckUtil.checkNull(vo.getEPERIODAMOUNT())) { returnvo[0] =
		 * vo.getRowId(); returnvo[1] = "期末数为空"; return returnvo; }
		 * 
		 * if (vo.getEPERIODAMOUNT().length() > 15) { returnvo[0] =
		 * vo.getRowId(); returnvo[1] = "期末数不得大于15位" + vo.getEPERIODAMOUNT();
		 * return returnvo; }
		 */

		// 往来日期
		if (CheckUtil.checkNull(vo.getDealingsDate())) {
			returnvo[0] = vo.getRowId();
			returnvo[1] = "往来日期为空";
			return returnvo;
		}

		if (vo.getDealingsDate().length() != 8) {
			returnvo[0] = vo.getRowId();
			returnvo[1] = "往来日期长度与接口定义不一致：" + vo.getDealingsDate();
			return returnvo;
		}

		// 往来时间
		if (CheckUtil.checkNull(vo.getDealingsTime())) {
			returnvo[0] = vo.getRowId();
			returnvo[1] = "往来时间为空";
			return returnvo;
		}

		if (vo.getDealingsTime().length() != 6) {
			returnvo[0] = vo.getRowId();
			returnvo[1] = "往来时间长度与接口定义不一致：" + vo.getDealingsTime();
			return returnvo;
		}

		// 往来类型
		if (CheckUtil.checkNull(vo.getDealingsType())) {
			returnvo[0] = vo.getRowId();
			returnvo[1] = "往来类型为空";
			return returnvo;
		}

		// 金额
		if (CheckUtil.checkNull(vo.getAmount())) {
			returnvo[0] = vo.getRowId();
			returnvo[1] = "金额为空";
			return returnvo;
		}

		if (vo.getAmount().length() > 15) {
			returnvo[0] = vo.getRowId();
			returnvo[1] = "金额不得大于15位" + vo.getAmount();
			return returnvo;
		}

		// ROWID
		if (CheckUtil.checkNull(vo.getRowId())) {
			returnvo[0] = vo.getRowId();
			returnvo[1] = "ROWID为空";
			return returnvo;
		}

		// // 车架号
		// if (!CheckUtil.checkNull(vo.getVin())) {
		// if (vo.getVin().length() != 17) {
		// returnvo[0] = vo.getRowId();
		// returnvo[1] = "车架号无效：" + vo.getVin();
		// return returnvo;
		// }
		// TmVehiclePO vehiclePo = new TmVehiclePO();
		// vehiclePo.setVin(vo.getVin());
		// List<TmVehiclePO> list = factory.select(vehiclePo);
		// if (null == list || list.size() <= 0) {
		// returnvo[0] = vo.getRowId();
		// returnvo[1] = "车架号无效：" + vo.getVin();
		// return returnvo;
		// }
		// }

		logger.info("==========S0013逻辑校验结束==========");

		return null;
	}

	private Object dodealerCheck(String dealer) {

		Map<String, Object> map = dao.getDealerByFCACode(dealer);

		/*
		 * 校验经销商是否已存在
		 */
		if (map != null && map.size() > 0) {
			return OemDictCodeConstants.IF_TYPE_YES; // 是
		} else {
			return OemDictCodeConstants.IF_TYPE_NO; // 否
		}

	}

	/**
	 * 设置数据接收字段
	 * 
	 * @param xmlList
	 * @return
	 * @throws Exception
	 */
	public List<S0013VO> setXMLToVO(List<Map<String, String>> xmlList) throws Exception {

		List<S0013VO> volist = new ArrayList<S0013VO>();

		try {

			logger.info("==========S0001 getXMLToVO() is START==========");

			logger.info("==========XML赋值到VO==========");

			logger.info("==========XMLSIZE:==========" + xmlList.size());

			if (xmlList != null && xmlList.size() > 0) {

				for (int i = 0; i < xmlList.size(); i++) {

					Map<String, String> map = xmlList.get(i);

					if (map != null && map.size() > 0) {

						S0013VO vo = new S0013VO();

						vo.setSelectDate(map.get("SELECT_DATE")); // 查询日期
						vo.setSelectTime(map.get("SELECT_TIME")); // 查询时间
						vo.setDealerCode(map.get("DEALER_CODE")); // 经销商代码
						vo.setRebatePoolAmount(map.get("REBATE_POOL_AMOUNT")); // 返利池金额
						vo.setUsedAmount(map.get("USED_AMOUNT")); // 已用金额
						vo.setAdvanceAmount(map.get("ADVANCE_AMOUNT")); // 占用金额
						vo.setFrozenAmount(map.get("FROZEN_AMOUNT")); // 红票兑付冻结金额
						vo.setAvailabelAmount(map.get("AVAILABEL_AMOUNT")); // 可用余额
						vo.setEntryAmount(map.get("ENTRY_AMOUNT")); // 本期进入返利池合计
						vo.setDeductionAmount(map.get("DEDUCTION_AMOUNT")); // 本期扣除返利合计
						vo.setBPeriodAmount(map.get("B_PERIOD_AMOUNT")); // 期初数
						vo.setEPeriodAmount(map.get("E_PERIOD_AMOUNT")); // 期末数
						vo.setSoNo(map.get("SO_NO")); // SAP销售订单号
						vo.setVin(map.get("VIN")); // 车架号
						vo.setRebateType(map.get("REBATE_TYPE")); // 返利类型编码
						vo.setRebateDescription(map.get("REBATE_DESCRIPTION")); // 返利类型描述
						vo.setDealingsDate(map.get("DEALINGS_DATE")); // 往来日期
						vo.setDealingsTime(map.get("DEALINGS_TIME")); // 往来时间
						vo.setRemark(map.get("REMARK")); // 摘要
						vo.setAmount(map.get("AMOUNT")); // 金额
						vo.setDealingsType(map.get("DEALINGS_TYPE")); // 往来类型
						vo.setRebateMark(map.get("REBATE_MARK")); // 返利说明
						vo.setRebateMonth(map.get("REBATE_MONTH")); // 返利产生月份
						vo.setChannel(map.get("CHANNEL")); // 渠道
						vo.setRowId(map.get("ROW_ID"));
						volist.add(vo);
						logger.info("==========outVo:==========" + vo);
					}
				}
			}

			logger.info("==========S00013 getXMLToVO() is END==========");

		} catch (Exception e) {
			logger.info("==========XML赋值VO失败==========");
			logger.error(e.getMessage(), e);
			throw new Exception("S0013 XML转换处理异常！" + e);
		} finally {
			logger.info("==========S00013 getXMLToVO() is finish==========");
		}

		return volist;

	}

}
