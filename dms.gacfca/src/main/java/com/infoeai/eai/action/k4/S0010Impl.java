package com.infoeai.eai.action.k4;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.LazyList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoeai.eai.action.BaseService;
import com.infoeai.eai.dao.k4.K4SICommonDao;
import com.infoeai.eai.po.TiK4DealerAccountPO;
import com.infoeai.eai.po.TtDealerAccountPO;
import com.infoeai.eai.vo.S0010VO;
import com.infoeai.eai.vo.returnVO;
import com.yonyou.dms.common.Util.CheckUtil;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.CommonUtils;

@Service
public class S0010Impl extends BaseService implements S0010 {
	private static final Logger logger = LoggerFactory.getLogger(S0010Impl.class);
	@Autowired
	private K4SICommonDao dao;
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	String format = df.format(new Date());

	@Override
	public List<returnVO> execute(List<S0010VO> volist) throws Throwable {

		logger.info("S00010 is begin");

		List<returnVO> revolist = new ArrayList<returnVO>();

		String returnvo[] = null;

		beginDbService();

		try {

			for (int i = 0; i < volist.size(); i++) {

				returnvo = S00010Check(volist.get(i)); // 数据校验

				if (null == returnvo) {

					// 校验通过，执行业务数据新增或更新
					K4CheckThroughData(volist.get(i));
					inserttable(volist.get(i));

				} else {

					// 校验未通过，只将数据写入接口表
					logger.info("==========S00010校验未通过数据处理开始==========");
					TiK4DealerAccountPO po = new TiK4DealerAccountPO();
					po.setString("Select_Date", CommonUtils.checkNull(volist.get(i).getSelectDate())); // 查询日期
					po.setString("Select_Time", CommonUtils.checkNull(volist.get(i).getSelectTime())); // 查询时间
					po.setString("Dealer_Code", CommonUtils.checkNull(volist.get(i).getDealerCode())); // 经销商代码
					po.setString("Cash_Balance", CommonUtils.checkNull(volist.get(i).getCashBalance())); // 现金信用余额
					po.setString("Currency_Type", CommonUtils.checkNull(volist.get(i).getCurrencyType())); // 货币
					po.setString("Payment_Type", CommonUtils.checkNull(volist.get(i).getPaymentType())); // 资金方式
					po.setString("Row_Id", CommonUtils.checkNull(volist.get(i).getRowId())); // ROW_ID
					po.setInteger("Create_By", OemDictCodeConstants.K4_S0010); // 创建人
					po.setTimestamp("Create_Date", format); // 创建时间
					po.setString("Is_Result", OemDictCodeConstants.IF_TYPE_NO);
					po.setInteger("Is_Del", 0);
					po.setString("Is_Message", returnvo[1]);
					po.saveIt();

					logger.info("==========S00010校验未通过数据处理结束==========");

					// 返回错误信息
					returnVO revo = new returnVO();
					revo.setOutput(returnvo[0]);
					revo.setMessage(returnvo[1]);
					revolist.add(revo);

					logger.info("==========S00010返回不合格数据==========ROWId为==========" + returnvo[0]);
					logger.info("==========S00010返回不合格数据==========Message为==========" + returnvo[1]);

				}

			}

			dbService.endTxn(true);

		} catch (Exception e) {
			logger.info(e.getMessage(), e);
			e.printStackTrace();
			dbService.endTxn(false);
			throw new Exception("==========S0010业务处理异常！ ==========" + e.getMessage(), e.getCause());
		} finally {
			logger.info("==========S00010 is finish==========");
			dbService.clean();
		}
		return revolist;

	}

	private void inserttable(S0010VO vo) {

		logger.info("==========S0010业务数据开始处理==========");

		try {

			Date time = parseString2DateTime(vo.getSelectDate() + " " + vo.getSelectTime(), "yyyyMMdd HHmmss");

			Long accountID = null;

			Map<String, Object> map = dao.getDealerByFCACode(vo.getDealerCode()); // 根据DealerCode匹配出FCACode
			LazyList<TtDealerAccountPO> ttlist = TtDealerAccountPO.find("Dealer_Id=? and Acc_Type=?",
					Long.valueOf(map.get("DEALER_ID").toString()), Integer.valueOf(vo.getPaymentType()));
			if (ttlist != null && ttlist.size() > 0) {
				for (TtDealerAccountPO updAccountpo : ttlist) {

					updAccountpo.setInteger("Is_Del", OemDictCodeConstants.IS_DEL_00);
					updAccountpo.setInteger("Status", OemDictCodeConstants.STATUS_ENABLE);
					updAccountpo.setInteger("Amount", Double.valueOf(vo.getCashBalance()));
					updAccountpo.setTimestamp("Update_Date", time);
					updAccountpo.setInteger("Update_By", OemDictCodeConstants.K4_S0010);
					updAccountpo.saveIt();
				}
				// updAccountpo.setAccId(accountID);

			} else {
				TtDealerAccountPO accountpo = new TtDealerAccountPO();
				accountpo.setInteger("OEM_COMPANY_ID", Long.valueOf(OemDictCodeConstants.OEM_ACTIVITIES));
				accountpo.setInteger("", OemDictCodeConstants.IS_DEL_00);
				accountpo.setInteger("", OemDictCodeConstants.STATUS_ENABLE);
				accountpo.setInteger("", Double.valueOf(vo.getCashBalance()));
				accountpo.setTimestamp("", time);
				accountpo.setInteger("", OemDictCodeConstants.K4_S0010);
				accountpo.saveIt();

			}

			logger.info("==========S0010业务数据结束处理==========");

		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

	}

	private Date parseString2DateTime(String dateStr, String dateFormat) {

		if (null == dateStr || dateStr.length() <= 0)
			return null;

		if (null == dateFormat || dateFormat.length() <= 0)
			dateFormat = "yyyy-MM-dd HH:mm:ss";

		DateFormat formatter = new SimpleDateFormat(dateFormat);
		Date date = null;
		try {
			date = formatter.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	private void K4CheckThroughData(S0010VO vo) {

		logger.info("==========S00010校验通过数据处理开始==========");

		try {

			TiK4DealerAccountPO po = new TiK4DealerAccountPO();
			po.setString("SELECT_DATE", CommonUtils.checkNull(vo.getSelectDate())); // 查询日期
			po.setString("SELECT_TIME", CommonUtils.checkNull(vo.getSelectTime())); // 查询时间
			po.setString("DEALER_CODE", CommonUtils.checkNull(vo.getDealerCode())); // 经销商代码
			po.setString("CASH_BALANCE", CommonUtils.checkNull(vo.getCashBalance())); // 现金信用余额
			po.setString("CURRENCY_TYPE", CommonUtils.checkNull(vo.getCurrencyType())); // 货币
			po.setString("PAYMENT_TYPE", CommonUtils.checkNull(vo.getPaymentType())); // 资金方式
			po.setString("ROW_ID", CommonUtils.checkNull(vo.getRowId())); // ROWID
			po.set("Create_By", OemDictCodeConstants.K4_S0010); // 创建人
			po.setTimestamp("Create_Date", format); // 创建时间
			po.setInteger("IS_RESULT", OemDictCodeConstants.IF_TYPE_YES);
			po.setInteger("Is_Del", 0);
			po.saveIt();

			logger.info("==========S00010校验通过数据处理结束==========");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private String[] S00010Check(S0010VO vo) {

		logger.info("==========S00010逻辑校验开始==========");

		String returnvo[] = new String[2];

		// 查询日期
		if (CommonUtils.checkIsNull(vo.getSelectDate())) {
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

		if (CheckUtil.checkNull(vo.getDealerCode())) {
			returnvo[0] = vo.getRowId();
			returnvo[1] = "经销商代码为空";
			return returnvo;

		}

		// 经销商代码
		if (dodealerCheck(vo.getDealerCode()) == OemDictCodeConstants.IF_TYPE_NO) {
			returnvo[0] = vo.getRowId();
			returnvo[1] = "经销商代码不存在" + vo.getDealerCode();
			return returnvo;
		}

		// 现金信用余额
		if (CheckUtil.checkNull(vo.getCashBalance())) {
			returnvo[0] = vo.getRowId();
			returnvo[1] = "现金信用余额长度为空";
			return returnvo;

		}

		if (vo.getCashBalance().length() > 15) {
			returnvo[0] = vo.getRowId();
			returnvo[1] = "现金信用余额长度与接口定义不一致：" + vo.getCashBalance();
			return returnvo;

		}

		// 资金方式非空校验
		if (CheckUtil.checkNull(vo.getPaymentType())) {
			returnvo[0] = vo.getRowId();
			returnvo[1] = "资金方式为空";
			return returnvo;
		}

		// 资金方式长度校验
		// if (vo.getPaymentType().length() != 8) {
		// returnvo[0] = vo.getRowId();
		// returnvo[1] = "资金方式长度与接口定义不一致：" + vo.getPaymentType();
		// return returnvo;
		// }

		// 校验资金方式是否有效
		if (!vo.getPaymentType().equals(OemDictCodeConstants.K4_PAYMENT_01.toString())
				&& !vo.getPaymentType().equals(OemDictCodeConstants.K4_PAYMENT_02.toString())
				&& !vo.getPaymentType().equals(OemDictCodeConstants.K4_PAYMENT_03.toString())
				&& !vo.getPaymentType().equals(OemDictCodeConstants.K4_PAYMENT_04.toString())
				&& !vo.getPaymentType().equals(OemDictCodeConstants.K4_PAYMENT_05.toString())
				&& !vo.getPaymentType().equals(OemDictCodeConstants.K4_PAYMENT_06.toString())
				&& !vo.getPaymentType().equals(OemDictCodeConstants.K4_PAYMENT_07.toString())) {
			returnvo[0] = vo.getRowId();
			returnvo[1] = "资金方式无效" + vo.getPaymentType();
			return returnvo;
		}

		// ROWID 非空校验
		if (CheckUtil.checkNull(vo.getRowId())) {
			returnvo[0] = vo.getRowId();
			returnvo[1] = "ROWID 为空";
			return returnvo;
		}

		logger.info("==========S00010逻辑校验完成==========");

		return null;

	}

	private Integer dodealerCheck(String dealer) {

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

	public List<S0010VO> setXMLToVO(List<Map<String, String>> xmlList) throws Exception {

		List<S0010VO> volist = new ArrayList<S0010VO>();

		try {

			logger.info("==========S00010 getXMLToVO() is START==========");

			logger.info("==========XML赋值到VO==========");

			logger.info("==========XMLSIZE:==========" + xmlList.size());

			if (xmlList != null && xmlList.size() > 0) {

				for (int i = 0; i < xmlList.size(); i++) {

					Map<String, String> map = xmlList.get(i);

					if (map != null && map.size() > 0) {

						S0010VO vo = new S0010VO();
						vo.setSelectDate(map.get("SELECT_DATE")); // 查询日期
						vo.setSelectTime(map.get("SELECT_TIME")); // 查询时间
						vo.setDealerCode(map.get("DEALER_CODE")); // 经销商代码
						vo.setCashBalance(map.get("CASH_BALANCE")); // 现金信用余额
						vo.setCurrencyType(map.get("CURRENCY_TYPE")); // 货币
						vo.setPaymentType(map.get("PAYMENT_TYPE")); // 资金方式
						vo.setRowId(map.get("ROW_ID"));
						volist.add(vo);

						logger.info("==========outVo:==========" + vo);

					}
				}
			}

			logger.info("==========S00010 getXMLToVO() is END==========");

		} catch (Exception e) {
			logger.info("==========XML赋值VO失败==========");
			logger.error(e.getMessage(), e);
			throw new Exception("S0010 XML转换处理异常！" + e);
		} finally {
			logger.info("==========S00010 getXMLToVO() is finish==========");
		}
		return volist;

	}
}
