package com.yonyou.dms.retail.dao.basedata;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.retail.domains.DTO.basedata.TmRetailDiscountBankImportTempDTO;
import com.yonyou.dms.retail.domains.PO.basedata.TmRetailDiscountBankImportTempPO;

/**
 * 银行提报查询
 * 
 * @author Administrator
 *
 */

@Repository
@SuppressWarnings("rawtypes")
public class RetailBankDao extends OemBaseDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);

	/**
	 * 查询方法
	 * 
	 * @param queryParam
	 * @return
	 */

	public PageInfoDto findRetailDiscountBank(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<>();
		String sql = getQuerySql(queryParam, params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;
	}

	private String getQuerySql(Map<String, String> queryParam, List<Object> params) {
		StringBuilder sql = new StringBuilder();
		sql.append("select \n");
		sql.append("tu1.NAME BANK_NAME ,");
		sql.append("td.DEALER_SHORTNAME,");
		sql.append("TOR2.ORG_NAME ORG_D,");
		sql.append("TOR3.ORG_NAME ORG_X,");
		sql.append("trdb.DEALER_CODE,");
		sql.append("tdr.DMS_CODE,");
		sql.append("data.DEALER_CODE S_DEALER_CODE,");
		sql.append("data.DEALER_SHORTNAME S_DEALER_SHORTNAME,");
		sql.append("trdb.CUSTOMER,");
		sql.append("trdb.VIN,vm.SERIES_NAME,vm.MODEL_NAME,vm.MODEL_YEAR,\n");
		sql.append("trdb.APP_STATE,\n");
		sql.append("DATE_FORMAT(trdb.APPLY_DATE,'%Y-%m-%d ') APPLY_DATE,\n");
		sql.append("DATE_FORMAT(trdb.DEAL_DATE,'%Y-%m-%d ') DEAL_DATE,\n");
		sql.append("data.RETAIL_PRICE,");
		sql.append("trdb.NET_PRICE,");
		sql.append("trdb.FINANCING_PNAME,\n");
		sql.append("trdb.FIRST_PERMENT,\n");
		sql.append("trdb.DEAL_AMOUNT,\n");
		sql.append("CONCAT(trdb.FIRST_PERMENT_RATIO,'%') AS FIRST_PERMENT_RATIO,\n");
		sql.append("trdb.END_PERMENT, CONCAT(trdb.END_PERMENT_RATIO,'%') AS END_PERMENT_RATIO,\n");
		sql.append("trdb.INSTALL_MENT_NUM,\n");
		sql.append("trdb.TOTAL_INTEREST,\n");
		sql.append("CONCAT(trdb.INTEREST_RATE,'%') AS INTEREST_RATE,\n");
		sql.append("CONCAT(trdb.POLICY_RATE,'%') AS POLICY_RATE, \n");
		sql.append("CONCAT(trdb.MERCHANT_FEES_RATE,'%') AS MERCHANT_FEES_RATE,\n");
		sql.append("trdb.ALLOWANCED_SUM_TAX,\n");
		sql.append("DATE_FORMAT(data.INVOICE_DATE,'%Y-%m-%d ') INVOICE_DATE ,\n");
		sql.append("DATE_FORMAT(trdb.CREATE_DATE,'%Y-%m-%d ') CREATE_DATE,\n");
		sql.append("tu.ACNT,\n");
		sql.append("tv1.VEHICLE_USAGE ,\n");
		sql.append("trdb.REMARK \n");
		sql.append("from TM_RETAIL_DISCOUNT_BANK_IMPORT trdb \n");
		sql.append("left join TC_USER tu1 on tu1.USER_ID =trdb.bank \n");

		sql.append(
				"left join (select tvs.DEALER_ID ,td1.DEALER_CODE,td1.DEALER_SHORTNAME,tv.VIN,tv.RETAIL_PRICE ,tvs.INVOICE_DATE from TT_VS_SALES_REPORT tvs\n");
		sql.append("				LEFT join TM_DEALER td1 on tvs.DEALER_ID = td1.DEALER_ID\n");
		sql.append("				Left join TM_VEHICLE_DEC tv on tvs.VEHICLE_ID = tv.VEHICLE_ID where tvs.STATUS = '"
												+ OemDictCodeConstants.STATUS_ENABLE + "') data on trdb.VIN = data.VIN \n");
		sql.append("Left join TM_VEHICLE_DEC tv1 on trdb.VIN = tv1.VIN \n");
		sql.append("Left join (" + getVwMaterialSql() + ") vm on vm.MATERIAL_ID=tv1.MATERIAL_ID \n");
		sql.append(" LEFT join TC_USER tu on tu.USER_ID=trdb.CREATE_BY          \n");
		sql.append("LEFT join TM_DEALER td on trdb.DEALER_CODE = td.DEALER_CODE \n");
		sql.append("LEFT join TT_DEALER_RELATION tdr on  tdr.dcs_code= td.DEALER_CODE\n");
		sql.append("LEFT JOIN TM_DEALER_ORG_RELATION TDOR ON TDOR.DEALER_ID = TD.DEALER_ID\n");
		sql.append(
				"LEFT JOIN TM_ORG TOR3 ON TOR3.ORG_ID = TDOR.ORG_ID AND TOR3.ORG_LEVEL = 3  AND TOR3.BUSS_TYPE=12351001\n");
		sql.append("LEFT JOIN TM_ORG TOR2 ON TOR3.PARENT_ORG_ID = TOR2.ORG_ID AND TOR2.ORG_LEVEL = 2  \n");
		sql.append(" LEFT JOIN TM_ORG_DLR_ORDER TDO1 on TDO1.ORG_DLR_ID = tor2.ORG_ID  AND TDO1.TYPE=2  \n");
		sql.append(" LEFT JOIN TM_ORG_DLR_ORDER TDO2 on TDO2.ORG_DLR_ID = TOR3.ORG_ID   AND TDO2.TYPE=3 \n ");
		sql.append("        WHERE 1=1 ");
		//品牌
		if (!StringUtils.isNullOrEmpty(queryParam.get("brandName"))) {
			sql.append(" and vm.brand_id = ?");
			params.add(queryParam.get("brandName"));
		}
		//车系
		if(!StringUtils.isNullOrEmpty(queryParam.get("seriesName"))){
			sql.append(" and VM.SERIES_ID  = ? \n");
			params.add(queryParam.get("seriesName"));
		}
		//车款
		if(!StringUtils.isNullOrEmpty(queryParam.get("groupName"))){
			sql.append(" and VM.GROUP_ID  = ? \n");
			params.add(queryParam.get("groupName"));
		}
		//年款
		if(!StringUtils.isNullOrEmpty(queryParam.get("modelYear"))){
			sql.append(" and VM.MODEL_YEAR  = '"+queryParam.get("modelYear")+"' \n");
		}
		//银行名称
		if (!StringUtils.isNullOrEmpty(queryParam.get("bankName"))) {
			sql.append(" and tu1.NAME = '"+queryParam.get("bankName")+"' ");
		}
		//客户名称
		if (!StringUtils.isNullOrEmpty(queryParam.get("CUSTOMER"))) {
			sql.append(" and trdb.CUSTOMER LIKE '%"+queryParam.get("CUSTOMER")+"%' ");
		}
		//分期期数
		if (!StringUtils.isNullOrEmpty(queryParam.get("installmentNumber"))) {
			sql.append(" and trdb.INSTALL_MENT_NUM IN ('"+queryParam.get("installmentNumber")+"')  ");
		}
		//申请贴息经销商代码
		if (!StringUtils.isNullOrEmpty(queryParam.get("dealerCode"))) {
			sql.append(" and trdb.dealer_code IN ("+queryParam.get("dealerCode")+")  ");
		}
		//VIN
		if (!StringUtils.isNullOrEmpty(queryParam.get("VIN"))) {
			String vin = queryParam.get("VIN");
			vin = vin.replaceAll("\\^", "\n");
			vin = vin.replaceAll("\\,", "\n");
			sql.append( getVinsAuto(vin, "trdb"));
		}
		//银行放款时间
		if (!StringUtils.isNullOrEmpty(queryParam.get("beginDateJY"))) {
			sql.append("   AND DATE(trdb.DEAL_DATE) >= ? \n");
			params.add(queryParam.get("beginDateJY"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("endDateJY"))) {
			sql.append("   AND DATE(trdb.DEAL_DATE) <= ? \n");
			params.add(queryParam.get("endDateJY"));
		}
		//提报时间
		if (!StringUtils.isNullOrEmpty(queryParam.get("beginDateTB"))) {
			sql.append("   AND DATE(trdb.CREATE_DATE) >= ? \n");
			params.add(queryParam.get("beginDateTB"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("endDateTB"))) {
			sql.append("   AND DATE(trdb.CREATE_DATE) <= ? \n");
			params.add(queryParam.get("endDateTB"));
		}
		logger.info(" 查询SQL : " + sql.toString() +"***"+ params.toString() );
		return sql.toString();
	}

	/**
	 * 下载
	 * 
	 * @param param
	 * @return
	 * @throws ServiceBizException
	 */
	public List<Map> queryEmpInfoforExport(Map<String, String> param) throws ServiceBizException {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(param, params);
		return OemDAOUtil.findAll(sql.toString(), params);

	}


	// 导入查询为空数据
	public List<Map> selectNullData() {
		List<Object> queryParam = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append(
				" select  trdb.ROW_NO, '经销商代码不能为空' as Err_Desc from TM_RETAIL_DISCOUNT_BANK_IMPORT_TEMP trdb where (trdb.DEALER_CODE is null or trdb.DEALER_CODE='') \n");
		sql.append("union\n");
		sql.append(
				"select  trdb.ROW_NO, '客户姓名不能为空' Err_Desc from TM_RETAIL_DISCOUNT_BANK_IMPORT_TEMP trdb where trdb.CUSTOMER is null or trdb.CUSTOMER=''\n");
		sql.append("union\n");
		sql.append(
				"select  trdb.ROW_NO, '车架号不能为空' Err_Desc from TM_RETAIL_DISCOUNT_BANK_IMPORT_TEMP trdb where trdb.VIN is null or trdb.VIN=''\n");
		sql.append("union\n");
		sql.append(
				"select  trdb.ROW_NO, '审批状态不能为空' Err_Desc from TM_RETAIL_DISCOUNT_BANK_IMPORT_TEMP trdb where trdb.APP_STATE is null or trdb.APP_STATE=''\n");
		sql.append("union\n");
		sql.append(
				"select  trdb.ROW_NO, '申请时间不能为空' Err_Desc from TM_RETAIL_DISCOUNT_BANK_IMPORT_TEMP trdb where trdb.APPLY_DATE is null or trdb.APPLY_DATE=''\n");
		sql.append("union\n");
		sql.append(
				"select  trdb.ROW_NO, '银行放款时间不能为空' Err_Desc from TM_RETAIL_DISCOUNT_BANK_IMPORT_TEMP trdb where trdb.DEAL_DATE is null or trdb.DEAL_DATE=''\n");
		sql.append("union\n");
		sql.append(
				"select  trdb.ROW_NO, '成交价不能为空' Err_Desc from TM_RETAIL_DISCOUNT_BANK_IMPORT_TEMP trdb where trdb.NET_PRICE is null or trdb.NET_PRICE=''\n");
		sql.append("union\n");
		sql.append(
				"select  trdb.ROW_NO, '首付不能为空' Err_Desc from TM_RETAIL_DISCOUNT_BANK_IMPORT_TEMP trdb where trdb.FIRST_PERMENT is null or trdb.FIRST_PERMENT=''\n");
		sql.append("union\n");
		sql.append(
				"select  trdb.ROW_NO, '贷款金额不能为空' Err_Desc from TM_RETAIL_DISCOUNT_BANK_IMPORT_TEMP trdb where trdb.DEAL_AMOUNT is null or trdb.DEAL_AMOUNT=''\n");
		sql.append("union\n");
		sql.append(
				"select  trdb.ROW_NO, '尾款不能为空' Err_Desc from TM_RETAIL_DISCOUNT_BANK_IMPORT_TEMP trdb where trdb.END_PERMENT is null or trdb.END_PERMENT=''\n");
		sql.append("union\n");
		sql.append(
				"select  trdb.ROW_NO, '首付比例不能为空' as Err_Desc from TM_RETAIL_DISCOUNT_BANK_IMPORT_TEMP trdb where (trdb.FIRST_PERMENT_RATIO is null or trdb.FIRST_PERMENT_RATIO='')\n");
		sql.append("union\n");
		sql.append(
				"select  trdb.ROW_NO, '分期期数不能为空' as Err_Desc from TM_RETAIL_DISCOUNT_BANK_IMPORT_TEMP trdb where (trdb.INSTALL_MENT_NUM is null or trdb.INSTALL_MENT_NUM='')\n");
		sql.append("union\n");
		sql.append(
				"select  trdb.ROW_NO, '总利息（手续费）不能为空' as Err_Desc from TM_RETAIL_DISCOUNT_BANK_IMPORT_TEMP trdb where (trdb.TOTAL_INTEREST is null or trdb.TOTAL_INTEREST='')\n");
		sql.append("union\n");
		sql.append(
				"select  trdb.ROW_NO, '原利率不能为空' as Err_Desc from TM_RETAIL_DISCOUNT_BANK_IMPORT_TEMP trdb where (trdb.INTEREST_RATE is null or trdb.INTEREST_RATE='')\n");
		sql.append("union\n");
		sql.append(
				"select  trdb.ROW_NO, '政策费率不能为空' as Err_Desc from TM_RETAIL_DISCOUNT_BANK_IMPORT_TEMP trdb where (trdb.POLICY_RATE is null or trdb.POLICY_RATE='')\n");
		sql.append("union\n");
		sql.append(
				"select  trdb.ROW_NO, '商户手续费率（客户利率）不能为空' as Err_Desc from TM_RETAIL_DISCOUNT_BANK_IMPORT_TEMP trdb where (trdb.MERCHANT_FEES_RATE is null or trdb.MERCHANT_FEES_RATE='')\n");
		sql.append("union\n");
		sql.append(
				"select  trdb.ROW_NO, '贴息金额不能为空' as Err_Desc from TM_RETAIL_DISCOUNT_BANK_IMPORT_TEMP trdb where (trdb.ALLOWANCED_SUM_TAX is null or trdb.ALLOWANCED_SUM_TAX='')\n");
		List<Map> resultList = OemDAOUtil.findAll(sql.toString(), queryParam);
		return resultList;
	}

	/**
	 * 数据正确性校验
	 * 
	 * @return
	 */
	public List<Map> checkRight() {
		List<Object> queryParam = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append(" select trdt.ROW_NO ,'申请日不正确' ERR_DATA  \n");
		sql.append("from TM_RETAIL_DISCOUNT_BANK_IMPORT_TEMP trdt where DATE_FORMAT(trdt.APPLY_DATE,'%Y-%m-%d') <> trdt.APPLY_DATE\n");
		sql.append("union  \n");
		sql.append("select trdt.ROW_NO ,'银行放款时间不正确' ERR_DATA \n"); 
		sql.append("from TM_RETAIL_DISCOUNT_BANK_IMPORT_TEMP trdt where DATE_FORMAT(trdt.DEAL_DATE,'%Y-%m-%d') <> trdt.DEAL_DATE\n");
		sql.append("union  \n");
		
		sql.append("select trdt.ROW_NO ,'分期期数只能为整数' ERR_DATA \n");
		sql.append("from TM_RETAIL_DISCOUNT_BANK_IMPORT_TEMP trdt  where 0 <> TRIM(trdt.INSTALL_MENT_NUM REGEXP '[^0-9]')  \n");
		sql.append(" union  \n");
		sql.append("select ROW_NO ,'成交价只能大于0' ERR_DATA  \n");
		sql.append("from TM_RETAIL_DISCOUNT_BANK_IMPORT_TEMP trdt  where trdt.NET_PRICE <=0 \n");
		sql.append("union  \n");
		sql.append("select ROW_NO ,'首付数据只能大于0' ERR_DATA \n");
		sql.append("from TM_RETAIL_DISCOUNT_BANK_IMPORT_TEMP trdt  where trdt.FIRST_PERMENT <=0 \n");
		sql.append("union  \n");
		sql.append("select ROW_NO ,'贷款金额数据只能大于0' ERR_DATA   \n"); 
		sql.append("from TM_RETAIL_DISCOUNT_BANK_IMPORT_TEMP trdt  where trdt.DEAL_AMOUNT <=0 \n");
		sql.append("union  \n");
		sql.append("select ROW_NO ,'尾款数据只能大于0' ERR_DATA  \n");
		sql.append("from TM_RETAIL_DISCOUNT_BANK_IMPORT_TEMP trdt  where trdt.END_PERMENT <=0 \n");
		sql.append("union  \n");
		sql.append("select ROW_NO ,'总利息（手续费）数据只能大于0' ERR_DATA \n"); 
		sql.append("from TM_RETAIL_DISCOUNT_BANK_IMPORT_TEMP trdt  where trdt.TOTAL_INTEREST <=0 \n");
		sql.append("union  \n");
		sql.append("select ROW_NO ,'贴息金额 数据只能大于0' ERR_DATA   \n");
		sql.append("from TM_RETAIL_DISCOUNT_BANK_IMPORT_TEMP trdt  where trdt.ALLOWANCED_SUM_TAX <=0 \n");
		sql.append("union  \n");
		
		
		sql.append("select ROW_NO, '首付比例不在0~1之间' ERR_DATA  from( select trdt.ROW_NO ,trdt.FIRST_PERMENT_RATIO \n");
		sql.append("from TM_RETAIL_DISCOUNT_BANK_IMPORT_TEMP trdt   \n");
		sql.append(" ) t WHERE t.FIRST_PERMENT_RATIO < 0 or t.FIRST_PERMENT_RATIO > 1    \n");
		sql.append(" union  \n");
		sql.append("select ROW_NO, '尾款比例不在0~1之间' ERR_DATA  from( select trdt.ROW_NO ,trdt.END_PERMENT_RATIO \n");
		sql.append("from TM_RETAIL_DISCOUNT_BANK_IMPORT_TEMP trdt \n");
		sql.append(" ) t WHERE t.END_PERMENT_RATIO<0 or t.END_PERMENT_RATIO>1   \n");
		sql.append(" union  \n");
		sql.append("select ROW_NO, '原利率不在0~1之间' ERR_DATA  from( select trdt.ROW_NO ,trdt.INTEREST_RATE \n");
		sql.append(" from TM_RETAIL_DISCOUNT_BANK_IMPORT_TEMP trdt \n");
		sql.append(" ) t  WHERE t.INTEREST_RATE<0 or t.INTEREST_RATE>1   \n");
		sql.append(" union  \n");
		sql.append("select ROW_NO, '政策费率不在0~1之间' ERR_DATA  from( select trdt.ROW_NO ,trdt.POLICY_RATE \n");
		sql.append("from TM_RETAIL_DISCOUNT_BANK_IMPORT_TEMP trdt \n");
		sql.append(")  t WHERE  t.POLICY_RATE<0 or t.POLICY_RATE>1  \n");
		sql.append(" union  \n");
		sql.append("select ROW_NO, '商户手续费率(客户利率)不在0~1之间' ERR_DATA  from( select trdt.ROW_NO ,trdt.MERCHANT_FEES_RATE \n");
		sql.append("from TM_RETAIL_DISCOUNT_BANK_IMPORT_TEMP trdt \n");
		sql.append(" ) t WHERE t.MERCHANT_FEES_RATE<0 or t.MERCHANT_FEES_RATE>1 \n");

		List<Map> resultList = OemDAOUtil.findAll(sql.toString(), queryParam);
		return resultList;
	}

	public List<Map> selectTmRetailDiscountBankImportTempList(LoginInfoDto logonUser) {
		List<Object> params = new LinkedList<Object>();
		StringBuffer sql = new StringBuffer("");
		sql.append("SELECT * ");
		sql.append("  FROM TM_RETAIL_DISCOUNT_BANK_IMPORT_TEMP tmp ");
		sql.append("where tmp.bank='" + logonUser.getUserId() + "'");
		sql.append("ORDER BY ROW_NO");
		List<Map> resultList = OemDAOUtil.findAll(sql.toString(), params);
		return resultList;
	}

	/**
	 * 导入时，查询临时表数据
	 * 
	 * @param userId
	 *            登陆人信息-----
	 */
	public List<Map> findSalesReportInfoByVin(String vin) {
		List<Object> queryParam = new LinkedList<Object>();
		StringBuffer sql = new StringBuffer("");
		sql.append("select * from TM_VEHICLE tv, TT_VS_SALES_REPORT tvsr ");
		sql.append("  where tv.VEHICLE_ID = tvsr.VEHICLE_ID  ");
		sql.append("   and TV.VIN = '" + vin + "'");
		sql.append("        AND TVSR.STATUS = '" + OemDictCodeConstants.STATUS_ENABLE + "'");
		List<Map> resultList = OemDAOUtil.findAll(sql.toString(), queryParam);
		return resultList;
	}

	public void deleteTmpVsYearlyPlan() {
		// 获取当前用户
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		TmRetailDiscountBankImportTempPO.delete(" USER_ID = ?", loginInfo.getUserId().toString());
	}

	public List<Map> findBankIsExists(LoginInfoDto loginInfo) {
		List<Object> queryParam = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT tmp.* ");
		sql.append("  FROM TM_RETAIL_DISCOUNT_BANK_IMPORT_TEMP  tmp\n");
		sql.append("  where not exists(select 1 from TC_BANK tb where tb.BANK_NAME=tmp.BANK)");
		sql.append("   ORDER BY ROW_NO");
		List<Map> resultList = OemDAOUtil.findAll(sql.toString(), queryParam);
		return resultList;
	}

	/**
	 * 查验零时表中经销商代码合法化
	 * 
	 * @return
	 */
	public List<Map> checkDealer() {
		List<Object> queryParam = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append(" select trdb.ROW_NO,trdb.DEALER_CODE\n");
		sql.append(" from TM_RETAIL_DISCOUNT_BANK_IMPORT_TEMP trdb \n");
		sql.append(" where not exists ( select 1 from TM_DEALER td where  td.DEALER_CODE = trdb.DEALER_CODE ) \n");
		List<Map> resultList = OemDAOUtil.findAll(sql.toString(), queryParam);
		return resultList;
	}

	/**
	 * 校验vin合法化
	 * 
	 * @return
	 */
	public List<Map> checkVin() {
		List<Object> queryParam = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append(" select trdb.ROW_NO,trdb.VIN from TM_RETAIL_DISCOUNT_BANK_IMPORT_TEMP trdb \n");
		sql.append(" where not exists (select 1 from TM_VEHICLE_DEC tv where tv.VIN = trdb.VIN)\n");
		List<Map> resultList = OemDAOUtil.findAll(sql.toString(), queryParam);
		return resultList;
	}

	/**
	 * 校验重复数据
	 * 
	 * @return
	 */
	public List<Map> checkRecycle() {
		List<Object> queryParam = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append(
				"  SELECT GROUP_CONCAT((T.ROW_NO+1) SEPARATOR ',') AS SAME_DATA, T.DEALER_CODE,T.VIN,T.BANK,T.CUSTOMER,T.APPLY_DATE   , COUNT(1) flag  \n");
		sql.append("  from TM_RETAIL_DISCOUNT_BANK_IMPORT_TEMP T \n");
		sql.append("  group by T.DEALER_CODE,T.VIN,T.BANK,T.CUSTOMER,T.APPLY_DATE ");
		sql.append("  having count(*) > 1 \n");
		List<Map> resultList = OemDAOUtil.findAll(sql.toString(), queryParam);
		return resultList;
	}

	/**
	 * 插入数据到临时表
	 * 
	 * @param tvypDTO
	 */
	public void insertTmpVsYearlyPlan(TmRetailDiscountBankImportTempDTO tvypDTO) {
		TmRetailDiscountBankImportTempPO tvypPO = new TmRetailDiscountBankImportTempPO();
		// 设置对象属性
		setTmRetailDiscountBankImportTempPO(tvypPO, tvypDTO);
		tvypPO.saveIt();
	}

	public void setTmRetailDiscountBankImportTempPO(TmRetailDiscountBankImportTempPO retailBank,
			TmRetailDiscountBankImportTempDTO retailBankDTO) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		retailBank.setLong("BANK", loginInfo.getUserId());
		retailBank.setString("DEALER_SHORTNAME", retailBankDTO.getDealerShortname());
		retailBank.setString("DEALER_CODE", retailBankDTO.getDealerCode());
		retailBank.setString("CUSTOMER", retailBankDTO.getCustomer());
		retailBank.setString("VIN", retailBankDTO.getVin());
		retailBank.setString("APP_STATE", retailBankDTO.getAppState());
		retailBank.setString("APPLY_DATE", retailBankDTO.getApplyDate());
		retailBank.setString("DEAL_DATE", retailBankDTO.getDealDate());
		retailBank.setString("NET_PRICE", retailBankDTO.getNetPrice());
		retailBank.setString("FINANCING_PNAME", retailBankDTO.getFinancingPname());
		retailBank.setString("FIRST_PERMENT", retailBankDTO.getFirstPerment());
		retailBank.setString("DEAL_AMOUNT", retailBankDTO.getDealAmount());
		retailBank.setString("END_PERMENT", retailBankDTO.getEndPerment());
		retailBank.setString("FIRST_PERMENT_RATIO", retailBankDTO.getFirstPermentRatio().replace("%", ""));
		retailBank.setString("END_PERMENT_RATIO", retailBankDTO.getEndPermentRatio().replace("%", ""));
		retailBank.setString("INSTALL_MENT_NUM", retailBankDTO.getInstallMentNum());
		retailBank.setString("TOTAL_INTEREST", retailBankDTO.getTotalInterest());
		retailBank.setString("INTEREST_RATE", retailBankDTO.getInterestRate().replace("%", ""));
		retailBank.setString("POLICY_RATE", retailBankDTO.getPolicyRate().replace("%", ""));
		retailBank.setString("MERCHANT_FEES_RATE", retailBankDTO.getMerchantFeesRate().replace("%", ""));
		retailBank.setString("ALLOWANCED_SUM_TAX", retailBankDTO.getAllowancedSumTax());
		retailBank.setString("REMARK", retailBankDTO.getRemark());
		retailBank.setString("ROW_NO", retailBankDTO.getRowNO());

	}

	/**
	 * 
	 * @Title: oemSelectTmpYearPlan @Description: 临时表回显 @param @param
	 * rowDto @param @return 设定文件 @return PageInfoDto 返回类型 @throws
	 */
	public List<Map> oemSelectTmpYearPlan(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getTmpYearPlanSql(queryParam, params);
		List<Map> list = OemDAOUtil.findAll(sql, params);
		return list;
	}

	private String getTmpYearPlanSql(Map<String, String> queryParam, List<Object> params) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DEALER_SHORTNAME, -- 经销商名称\n");
		sql.append("       DEALER_CODE, -- 经销商代码\n");
		sql.append("       CUSTOMER, -- 客户名称\n");
		sql.append("       VIN, -- 车架号\n");
		sql.append("       APP_STATE, -- 审批状态\n");
		sql.append("       APPLY_DATE, -- 申请时间\n");
		sql.append("       DEAL_DATE, -- 银行放款时间\n");
		sql.append("       NET_PRICE, -- 成交价\n");
		sql.append("       FINANCING_PNAME, -- 零售融资产品名称\n");
		sql.append("       FIRST_PERMENT, -- 首付\n");
		sql.append("       DEAL_AMOUNT, -- 贷款金额\n");
		sql.append("       END_PERMENT, -- 尾款\n");
		sql.append("       CONCAT(FORMAT(FIRST_PERMENT_RATIO*100,2),'%') AS FIRST_PERMENT_RATIO, -- 首付比例\n");
		sql.append("       CONCAT(FORMAT(END_PERMENT_RATIO*100,2),'%') AS END_PERMENT_RATIO, -- 尾款比例\n");
		sql.append("       CONCAT(FORMAT(INTEREST_RATE*100,2),'%') AS INTEREST_RATE, -- 原利率\n");
		sql.append("       TOTAL_INTEREST, -- 总利息（手续费）\n");
		sql.append("       INSTALL_MENT_NUM, -- 分期期数\n");
		sql.append("       CONCAT(FORMAT(POLICY_RATE*100,2),'%') AS POLICY_RATE, -- 政策费率\n");
		sql.append("       CONCAT(FORMAT(MERCHANT_FEES_RATE*100,2),'%')AS MERCHANT_FEES_RATE, -- 商户手续费率(客户利率)\n");
		sql.append("       ALLOWANCED_SUM_TAX, -- 贴息金额\n");
		sql.append("       REMARK -- 备注\n");
		sql.append(" 	 FROM TM_RETAIL_DISCOUNT_BANK_IMPORT_TEMP\n");
		sql.append("   WHERE 1=1\n");
		return sql.toString();
	}

}
