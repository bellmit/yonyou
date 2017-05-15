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
import com.infoeai.eai.po.TiK4DealerAccount2PO;
import com.infoeai.eai.po.TtDealerAccountPO;
import com.infoeai.eai.vo.S0012VO;
import com.infoeai.eai.vo.returnVO;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.CommonUtils;

@Service
public class S0012Iml extends BaseService implements S0012 {
	private static final Logger logger = LoggerFactory.getLogger(S0012Iml.class);
	@Autowired
	K4SICommonDao dao;

	/**
	 * 接口执行入口
	 * 
	 * @param volist
	 * @return
	 * @throws Throwable
	 */
	@Override
	public List<returnVO> execute(List<S0012VO> volist) throws Throwable {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String format = df.format(new Date());
		logger.info("S0012 is begin");

		List<returnVO> revolist = new ArrayList<returnVO>();

		String[] returnvo = null;

		beginDbService();
		try {

			for (int i = 0; i < volist.size(); i++) {

				returnvo = S0012Chcek(volist.get(i));

				if (null == returnvo) {

					K4CheckThroughData(volist.get(i)); // 接口表数据处理
					inserttable(volist.get(i)); // 业务表数据处理

				} else {

					logger.info("==========S0012未通过校验数据处理开始==========");

					TiK4DealerAccount2PO po = new TiK4DealerAccount2PO();
					po.setString("Select_Date", volist.get(i).getSelectDate()); // 查询日期
					po.setString("Select_Time", volist.get(i).getSelectTime()); // 查询时间
					po.setString("Finance_Company_Code", volist.get(i).getFinanceCompanyCode()); // 金融公司编号
					po.setString("Finance_Brand_Code", volist.get(i).getFinanceBrandCode()); // 品牌编号
					po.setString("Dealer_Code", volist.get(i).getDealerCode()); // 经销商代码
					po.setString("Financing_Amount", volist.get(i).getFinancingAmount()); // 融资额度
					po.setString("Used_Amount", volist.get(i).getUsedAmount()); // 已使用额度
					po.setString("Availabel_Amount", volist.get(i).getAvailabelAmount()); // 可用额度
					po.setString("Advance_Amount", volist.get(i).getAdvanceAmount()); // 预扣额度
					po.setString("Financing_Status", volist.get(i).getFinancingStatus()); // 额度状态
					po.setString("Row_Id", volist.get(i).getRowId()); // ROW_ID
					po.setInteger("Create_By", OemDictCodeConstants.K4_S0012); // 创建人
					po.setTimestamp("Create_Date", format); // 创建时间
					po.setString("Is_Result", OemDictCodeConstants.IF_TYPE_NO.toString());
					po.setInteger("Is_Del", 0);
					// po.setIfId(Long.valueOf("",SequenceManager.getSequence("")));
					po.setString("Is_Message", returnvo[1]);
					po.saveIt();

					logger.info("==========S0012未通过校验数据处理结束==========");

					// 返回错误信息
					returnVO revo = new returnVO();
					revo.setOutput(returnvo[0]);
					revo.setMessage(returnvo[1]);
					revolist.add(revo);

					logger.info("==========S0012返回不合格数据==========ROWId为==========" + returnvo[0]);

					logger.info("==========S0012返回不合格数据==========Message为==========" + returnvo[1]);

				}

			}
			dbService.endTxn(true);
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
			e.printStackTrace();
			dbService.endTxn(false);
			throw new Exception("S0012业务处理异常！" + e);
		} finally {
			logger.info("==========S0012 is finish==========");
			dbService.clean();
		}
		return revolist;
	}

	/**
	 * 业务表数据处理
	 * 
	 * @param vo
	 * @throws Throwable
	 */
	private void inserttable(S0012VO vo) {

		try {

			Date time = parseString2DateTime(vo.getSelectDate() + " " + vo.getSelectTime(), "yyyyMMdd HHmmss");

			Long accountID = null;

			// 根据FCA经销商代码取出相应的DCS经销商代码
			Map<String, Object> map = dao.getDealerByFCACode(vo.getDealerCode());

			// TtDealerAccountPO accountpo = new TtDealerAccountPO();
			// accountpo.setDealerId(Long.valueOf(map.get("DEALER_ID").toString()));

			Integer accType = 0;

			if ("1000000000".equals(vo.getFinanceCompanyCode())) {

				// 广汽汇理汽车金融有限公司
				accType = OemDictCodeConstants.K4_PAYMENT_02;

			} else if ("1000000001".equals(vo.getFinanceCompanyCode())) {

				// 菲亚特汽车金融有限责任公司
				accType = OemDictCodeConstants.K4_PAYMENT_03;

			} else if ("1000000002".equals(vo.getFinanceCompanyCode())) {

				// 兴业银行
				accType = OemDictCodeConstants.K4_PAYMENT_04;

			} else if ("1000000003".equals(vo.getFinanceCompanyCode())) {

				// 交通银行
				accType = OemDictCodeConstants.K4_PAYMENT_05;

			} else if ("1000000004".equals(vo.getFinanceCompanyCode())) {

				// 建行融资
				accType = OemDictCodeConstants.K4_PAYMENT_07;

			}

			// accountpo.setAccType(accType);
			List<TtDealerAccountPO> ttlist = TtDealerAccountPO.find("DEALER_ID=? and Acc_Type=?", map.get("DEALER_ID"),
					accType);
			if (ttlist != null && ttlist.size() > 0) {
				for (TtDealerAccountPO updAccountpo : ttlist) {

					accountID = (Long) ttlist.get(0).get("Acc_Id");
					updAccountpo.setString("FINANCE_COMPANY_CODE", vo.getFinanceCompanyCode()); // 
					updAccountpo.setString("FINANCE_BRAND_CODE", vo.getFinanceBrandCode()); // 品牌编号
					updAccountpo.setDouble("Amount", Double.valueOf(vo.getFinancingAmount())); // 融资额度
					updAccountpo.setDouble("USED_AMOUNT", Double.valueOf(vo.getUsedAmount())); // 已使用额度
					updAccountpo.setDouble("Usable_Amount", Double.valueOf(vo.getAvailabelAmount())); // 可用额度
					updAccountpo.setDouble("Disable_Amount", Double.valueOf(vo.getAdvanceAmount())); // 预扣额度
					updAccountpo.setString("Financing_Status", vo.getFinancingStatus()); // 额度状态
					updAccountpo.setInteger("Status", OemDictCodeConstants.STATUS_ENABLE); // 是否有效
					updAccountpo.setInteger("Is_Del", OemDictCodeConstants.IS_DEL_00); // 逻辑删除
					updAccountpo.setInteger("Update_By", OemDictCodeConstants.K4_S0012); // 更新ID
					updAccountpo.setTimestamp("Update_Date", time); // 更新日期
				}

			} else {
				TtDealerAccountPO accountpo = new TtDealerAccountPO();
				// accountID = new Long(SequenceManager.getSequence(""));
				// accountpo.setAccId(accountID); // 主键ID
				accountpo.setString("FINANCE_COMPANY_CODE", vo.getFinanceCompanyCode()); // 
				accountpo.setString("FINANCE_BRAND_CODE", vo.getFinanceBrandCode()); // 品牌编号
				accountpo.setInteger("Oem_Company_Id", Long.valueOf(OemDictCodeConstants.OEM_ACTIVITIES)); // OME公司ID
				accountpo.setDouble("Amount", Double.valueOf(vo.getFinancingAmount())); // 融资额度
				accountpo.setDouble("Used_Amount", Double.valueOf(vo.getUsedAmount())); // 已使用额度
				accountpo.setDouble("Usable_Amount", Double.valueOf(vo.getAvailabelAmount())); // 可用额度
				accountpo.setDouble("Disable_Amount", Double.valueOf(vo.getAdvanceAmount())); // 预扣额度

				accountpo.setString("Financing_Status", vo.getFinancingStatus()); // 额度状态

				accountpo.setInteger("Status", OemDictCodeConstants.STATUS_ENABLE); // 是否有效
				accountpo.setInteger("Is_Del", OemDictCodeConstants.IS_DEL_00); // 逻辑删除
				accountpo.setInteger("Create_By", OemDictCodeConstants.K4_S0012); // 创建ID
				accountpo.setTimestamp("Create_Date", time); // 创建日期
				accountpo.saveIt();

			}

		} catch (Exception e) {
			e.printStackTrace();

		}
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
	 * 接口表数据处理
	 * 
	 * @param vo
	 */
	private void K4CheckThroughData(S0012VO vo) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String format = df.format(new Date());
		try {

			logger.info("==========S0012通过校验数据处理开始==========");

			TiK4DealerAccount2PO po = new TiK4DealerAccount2PO();
			po.setString("SELECT_DATE", vo.getSelectDate()); // 查询日期
			po.setString("SELECT_TIME", vo.getSelectTime()); // 查询时间
			po.setString("FINANCE_COMPANY_CODE", vo.getFinanceCompanyCode()); // 金融公司编号
			po.setString("FINANCE_BRAND_CODE", vo.getFinanceBrandCode()); // 品牌编号
			po.setString("DEALER_CODE", vo.getDealerCode()); // 经销商代码
			po.setString("FINANCING_AMOUNT", vo.getFinancingAmount()); // 融资额度
			po.setString("USED_AMOUNT", vo.getUsedAmount()); // 已使用额度
			po.setString("AVAILABEL_AMOUNT", vo.getAvailabelAmount()); // 可用额度
			po.setString("ADVANCE_AMOUNT", vo.getAdvanceAmount()); // 预扣额度
			po.setString("FINANCING_STATUS", vo.getFinancingStatus()); // 额度状态
			po.setString("ROW_ID", vo.getRowId());
			po.setInteger("CREATE_BY", OemDictCodeConstants.K4_S0012); // 创建人
			po.setTimestamp("CREATE_DATE", format); // 创建时间
			po.setInteger("IS_RESULT", OemDictCodeConstants.IF_TYPE_YES.toString());
			po.setInteger("IS_DEL", 0);
			// po.setIfId("",Long.valueOf(SequenceManager.getSequence("")));
			po.saveIt();

			logger.info("==========S0012通过校验数据处理结束==========");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 数据校验
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	private String[] S0012Chcek(S0012VO vo) {

		String[] returnvo = new String[2];

		// 查询日期非空校验
		if (CommonUtils.checkIsNull(vo.getSelectDate())) {
			returnvo[0] = vo.getRowId();
			returnvo[1] = "查询日期长度为空";
			return returnvo;
		}

		// 查询日期长度校验
		if (vo.getSelectDate().length() != 8) {
			returnvo[0] = vo.getRowId();
			returnvo[1] = "查询日期长度与接口定义不一致：" + vo.getSelectDate();
			return returnvo;
		}

		// 查询时间非空校验
		if (CommonUtils.checkIsNull(vo.getSelectTime())) {
			returnvo[0] = vo.getRowId();
			returnvo[1] = "查询时间长度为空";
			return returnvo;
		}

		// 查询时间长度校验
		if (vo.getSelectTime().length() != 6) {
			returnvo[0] = vo.getRowId();
			returnvo[1] = "查询时间长度与接口定义不一致：" + vo.getSelectTime();
			return returnvo;
		}

		// 经销商代码非空校验
		if (CommonUtils.checkIsNull(vo.getDealerCode())) {
			returnvo[0] = vo.getRowId();
			returnvo[1] = "经销商代码为空";
			return returnvo;
		}

		// 经销商代码是否存在
		if (dodealerCheck(vo.getDealerCode()) == OemDictCodeConstants.IF_TYPE_NO) {
			returnvo[0] = vo.getRowId();
			returnvo[1] = "经销商代码不存在";
			return returnvo;
		}

		// 经融公司编号非空校验
		if (CommonUtils.checkIsNull(vo.getFinanceCompanyCode())) {
			returnvo[0] = vo.getRowId();
			returnvo[1] = "经融公司编号长度为空";
			return returnvo;
		}

		// 经融公司编号长度校验
		if (vo.getFinanceCompanyCode().length() > 10) {
			returnvo[0] = vo.getRowId();
			returnvo[1] = "经融公司编号长度与接口定义不一致：" + vo.getFinanceCompanyCode();
			return returnvo;
		}

		// 品牌编号非空校验
		if (CommonUtils.checkIsNull(vo.getFinanceBrandCode())) {
			returnvo[0] = vo.getRowId();
			returnvo[1] = "品牌编号长度为空";
			return returnvo;
		}

		// 品牌编号长度校验
		if (vo.getFinanceBrandCode().length() > 8) {
			returnvo[0] = vo.getRowId();
			returnvo[1] = "品牌编号长度与接口定义不一致：" + vo.getFinanceBrandCode();
			return returnvo;
		}

		// 融资额度非空校验
		if (CommonUtils.checkIsNull(vo.getFinancingAmount())) {
			returnvo[0] = vo.getRowId();
			returnvo[1] = "融资额度长度为空";
			return returnvo;
		}

		// 融资额度长度校验
		if (vo.getFinancingAmount().length() > 15) {
			returnvo[0] = vo.getRowId();
			returnvo[1] = "融资额度长度与接口定义不一致：" + vo.getFinancingAmount();
			return returnvo;
		}

		// 已使用额度非空校验
		if (CommonUtils.checkIsNull(vo.getUsedAmount())) {
			returnvo[0] = vo.getRowId();
			returnvo[1] = "已使用额度长度为空";
			return returnvo;
		}

		// 已使用额度长度校验
		if (vo.getUsedAmount().length() > 15) {
			returnvo[0] = vo.getRowId();
			returnvo[1] = "已使用额度长度与接口定义不一致：" + vo.getUsedAmount();
			return returnvo;
		}

		// 可用额度非空校验
		if (CommonUtils.checkIsNull(vo.getAvailabelAmount())) {
			returnvo[0] = vo.getRowId();
			returnvo[1] = "可用额度长度为空";
			return returnvo;
		}

		// 可用额度长度校验
		if (vo.getAvailabelAmount().length() > 15) {
			returnvo[0] = vo.getRowId();
			returnvo[1] = "可用额度长度与接口定义不一致：" + vo.getAvailabelAmount();
			return returnvo;
		}

		// 预扣额度非空校验
		if (CommonUtils.checkIsNull(vo.getAdvanceAmount())) {
			returnvo[0] = vo.getRowId();
			returnvo[1] = "预扣额度长度为空";
			return returnvo;
		}

		// 预扣额度长度校验
		if (vo.getAdvanceAmount().length() > 15) {
			returnvo[0] = vo.getRowId();
			returnvo[1] = "预扣额度长度与接口定义不一致：" + vo.getAdvanceAmount();
			return returnvo;
		}

		// 额度状态非空校验
		if (CommonUtils.checkIsNull(vo.getFinancingStatus())) {
			returnvo[0] = vo.getRowId();
			returnvo[1] = "额度状态长度为空";
			return returnvo;
		}

		// 额度状态长度校验
		if (vo.getFinancingStatus().length() != 1) {
			returnvo[0] = vo.getRowId();
			returnvo[1] = "额度状态长度与接口定义不一致：" + vo.getFinancingStatus();
			return returnvo;
		}

		// ROWID非空校验
		if (CommonUtils.checkIsNull(vo.getRowId())) {
			returnvo[0] = vo.getRowId();
			returnvo[1] = "ROWID不能为空";
			return returnvo;
		}
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

	public List<S0012VO> setXMLToVO(List<Map<String, String>> xmlList) throws Exception {

		List<S0012VO> volist = new ArrayList<S0012VO>();

		try {

			logger.info("==========S0012 getXMLToVO() is START==========");

			logger.info("==========XML赋值到VO==========");

			logger.info("==========XMLSIZE:==========" + xmlList.size());

			if (xmlList != null && xmlList.size() > 0) {

				for (int i = 0; i < xmlList.size(); i++) {

					Map<String, String> map = xmlList.get(i);

					if (map != null && map.size() > 0) {

						S0012VO vo = new S0012VO();
						vo.setSelectDate(map.get("SELECT_DATE")); // 查询日期
						vo.setSelectTime(map.get("SELECT_TIME")); // 查询时间
						vo.setFinanceCompanyCode(map.get("FINANCE_COMPANY_CODE")); // 金融公司编号
						vo.setFinanceBrandCode(map.get("FINANCE_BRAND_CODE")); // 品牌编号
						vo.setDealerCode(map.get("DEALER_CODE")); // 经销商代码
						vo.setFinancingAmount(map.get("FINANCING_AMOUNT")); // 融资额度
						vo.setUsedAmount(map.get("USED_AMOUNT")); // 已使用额度
						vo.setAvailabelAmount(map.get("AVAILABEL_AMOUNT")); // 可用额度
						vo.setAdvanceAmount(map.get("ADVANCE_AMOUNT")); // 预扣额度
						vo.setFinancingStatus(map.get("FINANCING_STATUS")); // 额度状态
						vo.setRowId(map.get("ROW_ID"));
						volist.add(vo);
						logger.info("==========outVo:==========" + vo);
					}
				}
			}
			logger.info("==========S0012 getXMLToVO() is END==========");

		} catch (Exception e) {
			logger.info("==========XML赋值VO失败==========");
			logger.error(e.getMessage(), e);
			throw new Exception("S0012 XML转换处理异常！" + e);
		} finally {
			logger.info("==========S0012 getXMLToVO() is finish==========");
		}
		return volist;

	}

}
