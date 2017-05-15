package com.yonyou.dms.retail.service.basedata;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.ss.formula.functions.Replace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.gacfca.SADCS022Cluod;
import com.yonyou.dms.common.Util.OemDictCodeConstantsUtils;
import com.yonyou.dms.common.Util.Utility;
import com.yonyou.dms.common.domains.PO.basedata.TmLoanRatMaintainPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.domains.DTO.baseData.OemDictDto;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.retail.dao.basedata.TmLoanRatMainTainDao;
import com.yonyou.dms.retail.domains.DTO.basedata.TmLoanRatMaintainDTO;
import com.yonyou.f4.mvc.annotation.TxnConn;

@Service
public class TmLoanRatMainTainServiceImpl implements TmLoanRatMainTainService {

	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);

	@Autowired
	TmLoanRatMainTainDao dao;

	@Autowired
	SADCS022Cluod sadcs022;

	@Override
	public PageInfoDto getTmLoanlist(Map<String, String> queryParam) throws Exception {
		return dao.getlist(queryParam);
	}

	@TxnConn
	@Override
	public void addTmLoan(TmLoanRatMaintainDTO tldto, LoginInfoDto loginInfo) throws ServiceBizException {
		dao.addTmLoan(tldto, loginInfo);
	}

	@Override
	public void deleteChargeById(Long id) throws ServiceBizException {
		dao.deleteChargeById(id);
	}

	@Override
	public void doSendEach(Long id, TmLoanRatMaintainDTO tcdto) throws ServiceBizException {
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<Map> queryEmpInfoforExport(Map<String, String> queryParam) throws Exception {
		return dao.queryEmpInfoforExport(queryParam);
	}

	/**
	 * 验证数据
	 * 
	 * @param userId
	 *            用户
	 * @param excelData
	 *            EXCEL数据
	 * @param fid
	 *            文件ID
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public boolean checkData(ArrayList<TmLoanRatMaintainDTO> dataList, LoginInfoDto loginInfo) throws ServiceBizException {
		try {
			List<TmLoanRatMaintainPO> excelList = new ArrayList<TmLoanRatMaintainPO>(); // EXCEL
																						// list
			TmLoanRatMaintainDTO item = null;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			// TcCodePO[] bankList =
			// CodeDict.getDictArrayByType(Constant.PAY_BANK.toString());
			List<Map> bankList = dao.findBank();
			OemDictDto[] installmentNumList = OemDictCodeConstantsUtils.getDictArrayByType(OemDictCodeConstants.INSTALLMENT_NUMBER_TYPE.toString());
			// 首付比例正则0-100数字
			String pattern = "^(?:0|[1-9][0-9]?|100)$";
			// 贴息利率正则0-100，可以有两位小数
			String pattern1 = "^([1-9][0-9]?(\\.[0-9]{1,2})?)$|^(0\\.[1-9][0-9]?)$|^(0\\.[0-9][1-9])$|^100(\\.00)?$";
			Pattern p = Pattern.compile(pattern);
			Pattern p1 = Pattern.compile(pattern1);
			for (int k = 0, j = dataList.size(); k < j; k++) {
				// if(flag){
				item = dataList.get(k);
				// 写入保存EXCEL数据的贴息利率表
				TmLoanRatMaintainPO trmPO = new TmLoanRatMaintainPO();
				// 品牌CODE不能为空
				if (!Utility.testIsNotNull(item.getBrandGroupname().trim())) {
					return false;
				}
				trmPO.setString("BRAND_GROUP_ID", item.getBrandGroupcode().toString().trim());
				// 车系CODE不能为空
				if (!Utility.testIsNotNull(item.getSeriesGroupcode().trim())) {
					return false;
				} else {
					List<Map> list = dao.isLocalization(item.getSeriesGroupcode().trim());
					if (list.size() == 0) {
						return false;
					}
				}
				trmPO.setString("SERIES_GROUP_ID", item.getSeriesGroupcode().toString().trim());
				// 车款CODE不能为空
				if (!Utility.testIsNotNull(item.getStyleGroupcode().trim())) {
					return false;
				}
				trmPO.setString("STYLE_GROUP_ID", item.getStyleGroupcode().toString().trim());
				// 银行
				if (!Utility.testIsNotNull(item.getCodeId())) {
					return false;
				} else {
					for (Map<String, Object> po : bankList) {
						if (po.get("BANK_NAME").toString().equals(item.getCodeId().trim())) {
							trmPO.setString("CODE_ID", po.get("ID").toString());
						}
					}
					if (!Utility.testIsNotNull(trmPO.getString("CODE_ID"))) {
						return false;
					}
				}
				// 分期期数
				if (!Utility.testIsNotNull(item.getInstallmentNumber().toString())) {
					return false;
				} else {
					for (OemDictDto po : installmentNumList) {
						if (po.getCode_desc().equals(item.getInstallmentNumber().toString().trim())) {
							trmPO.setInteger("INSTALLMENT_NUMBER", po.getCode_id());
						}
					}
					if (!Utility.testIsNotNull(trmPO.getInteger("INSTALLMENT_NUMBER").toString())) {
						return false;
					}
				}
				// 首付比例开始
				if (!Utility.testIsNotNull(String.valueOf(item.getDpmS()))) {
					return false;
				} else {
					String dpmss = String.valueOf(item.getDpmS());
					String dpms = String.valueOf(Double.valueOf(dpmss)*100).replace(".0", "");
					Matcher m = p.matcher(dpms.trim());
					if (m.find()) {
						trmPO.setFloat("DPM_S", dpms);
					} else {
						return false;
					}
				}
				// 首付比例结束
				if (!Utility.testIsNotNull(String.valueOf(item.getDpmE()))) {
					return false;
				} else {
					String dpmee = String.valueOf(item.getDpmE());
					String dpme = String.valueOf(Double.valueOf(dpmee)*100).replace(".0", "");
					Matcher m = p.matcher(dpme.trim());
					if (m.find()) {
						trmPO.setFloat("DPM_E", dpme);
					} else {
						return false;
					}
				}
				// 贴息利率
				if (Utility.testIsNotNull(String.valueOf(item.getRate()))) {
					String ratee = String.valueOf(item.getRate());
					String rate = String.valueOf(Double.valueOf(ratee)*100).replace(".0", "");
					Matcher m = p1.matcher(rate.trim());
					if (!m.find()) {
						return false;
					}else{
						trmPO.setDouble("RATE", rate);
					}
				}
				// 有效日期开始
				if (!Utility.testIsNotNull(String.valueOf(item.getEffectiveDateS()))) {
					return false;
				} else {
					trmPO.setDate("EFFECTIVE_DATE_S", item.getEffectiveDateS());
				}
				// 有效日期结束
				if (!Utility.testIsNotNull(String.valueOf(item.getEffectiveDateE()))) {// effectiveDateE
					return false;
				} else {
					trmPO.setDate("EFFECTIVE_DATE_E", item.getEffectiveDateE());
				}
				trmPO.setInteger("IS_VALID", OemDictCodeConstants.STATUS_ENABLE);
				List<Map> list = dao.findDetail(trmPO);
				if (list.size() != 0) {
					// 有重复数据
					return false;
				}
				trmPO.setLong("CREATE_BY", loginInfo.getUserId());
				Date cDate = new Date();
				trmPO.setDate("CREATE_DATE", cDate);
				trmPO.setString("IS_SCAN", "0");
				// trmPO.setRate(rate.doubleValue());
				excelList.add(trmPO);
			}
			for (int i = 0; i < excelList.size(); i++) {
				excelList.get(i).insert();
			}
			logger.info("*****************成功写入EXCEL数据历史表!************************");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.toString());
			return false;
		}
		return true;
	}

	/**
	 * 多选/全量删除 by wangJian 2016-04-13
	 */
	public void delAll(TmLoanRatMaintainDTO tldto) {
		dao.delAll(tldto);
	}

	@Override
	public void sentDiscountRateInfo(TmLoanRatMaintainDTO tldto) throws ServiceBizException {
		String command = tldto.getFlag();
		try {
			String flag = "";
			if (command.equals("0")) {// 全量下发
				List<Map> count = dao.countDo();
				int f = count.size() % 200;
				int j = count.size() / 200;
				if (f == 0) {
					for (int i = 0; i < j; i++) {
						flag = sadcs022.handleExecute();
					}
				} else {
					for (int d = 0; d < j + 1; d++) {
						flag = sadcs022.handleExecute();
					}
				}
			} else if (command.equals("1")) {// 多选下发
				String array = tldto.getIds();
				String[] split = array.split(",");
				String vinss = "";
				for (String vins : split) {
					vinss = "'" + vins + "'," + vinss;
				}
				String vinn = vinss.substring(0, vinss.length() - 1);
				flag = sadcs022.esendDataAll(vinn);
			}
		} catch (Exception e) {
			System.out.println(e);
			throw new ServiceBizException("贴息利率信息下发失败！");
		}
	}

}
