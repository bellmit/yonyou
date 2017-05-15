package com.infoeai.eai.action.k4;

import java.text.DateFormat;
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
import com.infoeai.eai.po.TiK4DealerAccountDetailPO;
import com.infoeai.eai.po.TtDealerAccountDtlPO;
import com.infoeai.eai.po.TtDealerAccountPO;
import com.infoeai.eai.vo.S0011VO;
import com.infoeai.eai.vo.returnVO;
import com.infoservice.infox.commons.httpclient.util.DateUtil;
import com.yonyou.dms.common.Util.CheckUtil;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.CommonUtils;

@Service
public class S0011Impl extends BaseService implements S0011 {
	private static final Logger logger = LoggerFactory.getLogger(S0011Impl.class);
	@Autowired
	K4SICommonDao dao;

	@Override
	public List<returnVO> execute(List<S0011VO> volist) throws Throwable {

		logger.info("S0011 is begin");

		List<returnVO> revolist = new ArrayList<returnVO>();

		String[] returnVO = null;

		// 开启事务
		beginDbService();

		try {

			for (int i = 0; i < volist.size(); i++) {

				returnVO = S0011Check(volist.get(i)); // 验证S0011VO数据

				if (returnVO == null) {

					K4CheckThroughData(volist.get(i)); // 验证通过数据处理
					inserttable(volist.get(i));

				} else {

					logger.info("==========S0011校验未通过数据处理开始==========");

					TiK4DealerAccountDetailPO po = new TiK4DealerAccountDetailPO();
					// po.setIfId(Long.valueOf(SequenceManager.getSequence("")));
					// // 主键ID
					po.setString("Select_Date", volist.get(i).getSelectDate()); // 查询日期
					po.setString("Select_Time", volist.get(i).getSelectTime()); // 查询时间
					po.setString("Dealer_Code", volist.get(i).getDealerCode()); // 经销商代码
					po.setString("Pay_Date", volist.get(i).getPayDate()); //// 打款日期
					po.setString("Pay_Amount", volist.get(i).getPayAmount());
					// 打款金额
					po.setString("Currency_Type", volist.get(i).getCurrencyType());
					//
					// 货币
					po.setString("Company_Code", volist.get(i).getCompanyCode()); //
					// 公司代码
					po.setString("Year", volist.get(i).getYear()); // 年度
					po.setString("Evidence_No", volist.get(i).getEvidenceNo()); //
					// SAP凭证号
					po.setString("Borrow_Lend", volist.get(i).getBorrowLend()); //
					// 借贷方向
					po.setString("Payment_Type", volist.get(i).getPaymentType()); //
					// 资金方式
					po.setString("Row_Id", volist.get(i).getRowId()); // ROWID
					po.setString("Remark", volist.get(i).getRemark()); // 备注
					po.setInteger("Create_By", OemDictCodeConstants.K4_S0011); // 创建人
					po.setTimestamp("Create_Date", new Date()); // 创建时间
					po.setInteger("Is_Result", OemDictCodeConstants.IF_TYPE_NO.toString());
					po.setInteger("Is_Del", 0);
					po.setString("Is_Message", returnVO[1]);
					po.saveIt();

					logger.info("==========S0011校验未通过数据处理结束==========");

					// 返回错误信息
					returnVO revo = new returnVO();
					revo.setOutput(returnVO[0]);
					revo.setMessage(returnVO[1]);
					revolist.add(revo);

					logger.info("==========S0011返回不合格数据==========ROWId为==========" + returnVO[0]);
					logger.info("==========S0011返回不合格数据==========Message为==========" + returnVO[1]);

				}

			}

			dbService.endTxn(true);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
			dbService.endTxn(false);
			throw new Exception("S0011业务处理异常！" + e);
		} finally {
			logger.info("==========S0011 is finish==========");
			dbService.clean();
		}
		return revolist;

	}

	private boolean inserttable(S0011VO vo) {

		logger.info("==========S0011逻辑数据处理开始==========");

		Date time = null;

		try {

			if ("00000000".equals(vo.getPayDate())) {
				vo.setPayDate(null);
			} else {
				String formatDate = DateUtil.formatDate(new Date());

				time = yyyyMMdd2Date(vo.getPayDate());
			}

			Long accountID = null;
			TtDealerAccountPO acountPo = new TtDealerAccountPO();
			Map<String, Object> map = dao.getDealerByFCACode(vo.getDealerCode());
			LazyList<TtDealerAccountPO> ttlist = TtDealerAccountPO.find("DEALER_ID=? and Acc_Type=?",
					map.get("DEALER_ID"), vo.getPaymentType());
			if (ttlist != null && ttlist.size() > 0) {

				accountID = (Long) ttlist.get(0).get("Acc_Id");

			} else {

				// accountID = new Long(SequenceManager.getSequence(""));
				acountPo.setInteger("Acc_Id", accountID);
				acountPo.setInteger("Oem_Company_Id", Long.valueOf(OemDictCodeConstants.OEM_ACTIVITIES));
				acountPo.setTimestamp("Create_Date", DateUtil.formatDate(new Date()));
				acountPo.setInteger("Create_By", OemDictCodeConstants.K4_S0011);
				acountPo.setInteger("Is_Del", OemDictCodeConstants.IS_DEL_00);
				acountPo.setInteger("Status", OemDictCodeConstants.STATUS_ENABLE);
				acountPo.saveIt();
			}
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String format = df.format(new Date());
			TtDealerAccountDtlPO po = new TtDealerAccountDtlPO();
			// po.setAccDtlId(new Long(SequenceManager.getSequence("")));
			po.setInteger("Acc_Id", accountID);
			po.setInteger("Opera_Type", OemDictCodeConstants.ACCOUNT_OPPER_TYPE_01);
			po.setString("Evidence_No", vo.getCompanyCode() + vo.getYear() + vo.getEvidenceNo());

			if (OemDictCodeConstants.BORROW_LEND_H.equals(vo.getBorrowLend())) {
				po.setInteger("Amount", Double.valueOf((vo.getPayAmount())) * -1);// 借款
			} else {
				po.setDouble("Amount", Double.valueOf((vo.getPayAmount())));// 贷款
			}

			po.setString("Remark", vo.getRemark());
			po.setTimestamp("Opera_Date", time);
			po.setInteger("Is_Del", OemDictCodeConstants.IS_DEL_00);
			po.setInteger("Status", OemDictCodeConstants.STATUS_ENABLE);
			po.setTimestamp("Create_Date", format);
			po.setInteger("Create_by", OemDictCodeConstants.K4_S0011);
			po.saveIt();

			logger.info("==========S0011逻辑数据处理结束==========");

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;

	}

	/**
	 * 将8位数字年月日转换为Date类型
	 * 
	 * @param yyyyMMdd
	 * @return
	 */
	private Date yyyyMMdd2Date(String yyyyMMdd) {

		if (null == yyyyMMdd || yyyyMMdd.trim().equals("") || yyyyMMdd.toLowerCase().trim().equals("null")) {
			return null;
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date date = null;
		try {
			date = sdf.parse(yyyyMMdd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;

	}

	private void K4CheckThroughData(S0011VO vo) {

		logger.info("==========S0011校验通过数据处理开始==========");

		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String format = df.format(new Date());
			TiK4DealerAccountDetailPO po = new TiK4DealerAccountDetailPO();
			// po.setIfId(Long.valueOf(SequenceManager.getSequence(""))); //
			// 主键ID
			po.setString("Select_Date", vo.getSelectDate()); // 查询日期
			po.setString("Select_Time", vo.getSelectTime()); // 查询时间
			po.setString("Dealer_Code", vo.getDealerCode()); // 经销商代码
			po.setString("PAY_DATE", vo.getPayDate()); // 打款日期
			po.setString("PAY_AMOUNT", vo.getPayAmount()); // 打款金额
			po.setString("CURRENCY_TYPE", vo.getCurrencyType()); // 货币
			po.setString("COMPANY_CODE", vo.getCompanyCode()); // 公司代码
			po.setString("YEAR", vo.getYear()); // 年度
			po.setString("EVIDENCE_NO", vo.getEvidenceNo()); // SAP凭证号
			po.setString("BORROW_LEND", vo.getBorrowLend()); // 借贷方向
			po.setInteger("PAYMENT_TYPE", vo.getPaymentType()); // 资金方式
			po.setString("ROW_ID", vo.getRowId()); // ROWID
			po.setString("REMARK", vo.getRemark()); // 备注
			po.setInteger("PAYMENT_TYPE", OemDictCodeConstants.K4_S0011); // 创建人
			po.setTimestamp("CREATE_DATE", format); // 创建时间
			po.setString("IS_RESULT", OemDictCodeConstants.IF_TYPE_YES.toString());
			po.setInteger("IS_DEL", 0);
			po.saveIt();

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("==========S0011校验通过数据处理结束==========");

	}

	private String[] S0011Check(S0011VO vo) {

		logger.info("==========S0011逻辑校验开始==========");

		String[] returnVO = new String[2];

		// 查询日期
		if (CommonUtils.checkIsNull(vo.getSelectDate())) {
			returnVO[0] = vo.getRowId();
			returnVO[1] = "查询日期长度为空";
			return returnVO;

		}
		if (vo.getSelectDate().length() != 8) {
			returnVO[0] = vo.getRowId();
			returnVO[1] = "查询日期长度与接口定义不一致：" + vo.getSelectDate();
			return returnVO;

		}
		// 查询时间
		if (CommonUtils.checkIsNull(vo.getSelectTime())) {
			returnVO[0] = vo.getRowId();
			returnVO[1] = "查询时间长度为空";
			return returnVO;

		}
		if (vo.getSelectTime().length() != 6) {
			returnVO[0] = vo.getRowId();
			returnVO[1] = "查询时间长度与接口定义不一致：" + vo.getSelectTime();
			return returnVO;

		}
		if (CommonUtils.checkIsNull(vo.getDealerCode())) {
			returnVO[0] = vo.getRowId();
			returnVO[1] = "经销商代码为空";
			return returnVO;
		}
		// 经销商代码
		if (dodealerCheck(vo.getDealerCode()) == OemDictCodeConstants.IF_TYPE_NO) {
			returnVO[0] = vo.getRowId();
			returnVO[1] = "经销商代码不存在";
			return returnVO;
		}

		// 打款日期
		System.out.println(vo.getPayDate());
		if (CheckUtil.checkNull(vo.getPayDate())) {
			returnVO[0] = vo.getRowId();
			returnVO[1] = "打款日期长度为空";
			return returnVO;

		}
		if (vo.getPayDate().length() != 8) {
			returnVO[0] = vo.getRowId();
			returnVO[1] = "打款日期长度与接口定义不一致：" + vo.getPayDate();
			return returnVO;

		}
		// 打款金额
		if (CheckUtil.checkNull(vo.getPayAmount())) {
			returnVO[0] = vo.getRowId();
			returnVO[1] = "打款金额长度为空";
			return returnVO;

		}
		if (vo.getPayAmount().length() > 15) {
			returnVO[0] = vo.getRowId();
			returnVO[1] = "打款金额长度与接口定义不一致：" + vo.getPayAmount();
			return returnVO;

		}
		// 借贷方向

		if (CheckUtil.checkNull(vo.getBorrowLend())) {
			returnVO[0] = vo.getRowId();
			returnVO[1] = "借贷方向为空";
			return returnVO;
		}

		// 校验资金方式是否有效
		if (!vo.getPaymentType().equals(OemDictCodeConstants.K4_PAYMENT_01.toString())
				&& !vo.getPaymentType().equals(OemDictCodeConstants.K4_PAYMENT_02.toString())
				&& !vo.getPaymentType().equals(OemDictCodeConstants.K4_PAYMENT_03.toString())
				&& !vo.getPaymentType().equals(OemDictCodeConstants.K4_PAYMENT_04.toString())
				&& !vo.getPaymentType().equals(OemDictCodeConstants.K4_PAYMENT_05.toString())
				&& !vo.getPaymentType().equals(OemDictCodeConstants.K4_PAYMENT_06.toString())
				&& !vo.getPaymentType().equals(OemDictCodeConstants.K4_PAYMENT_07.toString())) {
			returnVO[0] = vo.getRowId();
			returnVO[1] = "资金方式无效" + vo.getPaymentType();
			return returnVO;
		}

		// ROW_ID
		if (CheckUtil.checkNull(vo.getRowId())) {
			returnVO[0] = vo.getRowId();
			returnVO[1] = "ROWID为空";
			return returnVO;
		}
		if (CheckUtil.checkNull(vo.getCompanyCode())) {
			returnVO[0] = vo.getRowId();
			returnVO[1] = "公司代码为空";
			return returnVO;
		}

		if (vo.getCompanyCode().length() > 4) {
			returnVO[0] = vo.getRowId();
			returnVO[1] = "公司代码长度与接口定义不一致：" + vo.getCompanyCode();
			return returnVO;

		}
		if (CheckUtil.checkNull(vo.getYear())) {
			returnVO[0] = vo.getRowId();
			returnVO[1] = "年度为空";
			return returnVO;
		}

		if (vo.getYear().length() > 4) {
			returnVO[0] = vo.getRowId();
			returnVO[1] = "年度长度与接口定义不一致：" + vo.getYear();
			return returnVO;

		}

		logger.info("==========S0011逻辑校验完成==========");

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

	public List<S0011VO> setXMLToVO(List<Map<String, String>> xmlList) throws Exception {

		List<S0011VO> volist = new ArrayList<S0011VO>();

		try {

			logger.info("==========S0011 getXMLToVO() is START==========");

			logger.info("==========XML赋值到VO==========");

			logger.info("==========XMLSIZE:==========" + xmlList.size());

			if (xmlList != null && xmlList.size() > 0) {

				for (int i = 0; i < xmlList.size(); i++) {

					Map<String, String> map = xmlList.get(i);

					if (map != null && map.size() > 0) {

						S0011VO vo = new S0011VO();

						vo.setSelectDate(map.get("SELECT_DATE")); // 查询日期
						vo.setSelectTime(map.get("SELECT_TIME")); // 查询时间
						vo.setDealerCode(map.get("DEALER_CODE")); // 经销商代码
						vo.setPayDate(map.get("PAY_DATE")); // 打款日期
						vo.setPayAmount(map.get("PAY_AMOUNT")); // 打款金额
						vo.setCurrencyType(map.get("CURRENCY_TYPE")); // 货币
						vo.setCompanyCode(map.get("COMPANY_CODE")); // 公司代码
						vo.setYear(map.get("YEAR")); // 年度
						vo.setEvidenceNo(map.get("EVIDENCE_NO")); // SAP凭证号
						vo.setBorrowLend(map.get("BORROW_LEND")); // 借贷方向
						vo.setPaymentType(map.get("PAYMENT_TYPE")); // 资金方式
						vo.setRowId(map.get("ROW_ID")); // ROWID
						vo.setRemark(map.get("REMARK")); // 备注
						volist.add(vo);

						logger.info("==========outVo:==========" + vo);

					}
				}
			}
			logger.info("==========S0011 getXMLToVO() is END==========");

		} catch (Exception e) {
			logger.info("==========XML赋值VO失败==========");
			logger.error(e.getMessage(), e);
			throw new Exception("S0011 XML转换处理异常！" + e);
		} finally {
			logger.info("==========S0011 getXMLToVO() is finish==========");
		}
		return volist;

	}
}
