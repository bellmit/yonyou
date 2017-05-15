package com.yonyou.dms.vehicle.dao.claimManage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
* @author liujm
* @date 2017年4月27日
*/


@Repository
public class ClaimNoticeQueryDao extends OemBaseDAO{
	
	/**
	 * 索赔结算单查询
	 * @param queryParam
	 * @return
	 */
	public PageInfoDto getClaimNoticeQuery(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam, params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;

	}
	
	/**
	 * SQL组装   查询（主页面） 
	 * @param queryParam
	 * @param params
	 * @return
	 */
	private String getQuerySql(Map<String, String> queryParam, List<Object> params) {
		//获取当前用户
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		
		StringBuffer sql = new StringBuffer("\n");
		sql.append("SELECT 	A.CLAIMS_BILLING_ID, # 索赔单ID \n");
		sql.append("		A.VBN_NO, # 结算流水号 \n");
		sql.append("		A.COMPANY_CODE, # 公司代码 \n");
		sql.append("		A.COMPANY_NAME, # 公司名称 \n");
		sql.append("		A.SVC_DEALER, # 经销商代码 \n");
		sql.append("		A.SVC_DEALER_NAME, # 经销商名称 \n");
		sql.append("		A.CCLAIMS_MONTH_CYCLE_FROM, # 索赔单开始时间 \n");
		sql.append("		A.CCLAIMS_MONTH_CYCLE_TO, # 索赔单结束时间 \n");
		sql.append("		DATE_FORMAT(A.VBN_DATE, '%Y-%c-%d') AS VBN_DATE, # 结算日期 \n");
		sql.append("		A.CLAIM_CHARGERBACK_AMT, # 索赔扣款 \n");
		sql.append("		A.TOTAL_AMT, # 全部付款金额 \n");
		sql.append("		A.TOTAL_RECS, # 记录数 \n");
		sql.append("		A.VAT_INVOICE_AMOUNT, # 增值税发票金额 \n");
		sql.append("		A.VBN_REMARKS, # VBN备注 \n");
		sql.append("		A.IS_INVOICE # 开票状态 \n");
		sql.append("	FROM TT_CLAIMS_NOTICE_DCS A \n");
		sql.append("	WHERE 1 = 1 \n");
		//	条件 
		//dlr条件限定(经销商)
		if(loginInfo.getPoseType()!=null && loginInfo.getPoseType() == 10021002){
			sql.append("		AND A.SVC_DEALER = '"+loginInfo.getDealerCode()+"' \n");
					
		}
		
		//公司CODE
		if(!StringUtils.isNullOrEmpty(queryParam.get("companyCode"))){
			sql.append("		AND A.COMPANY_CODE LIKE '%"+queryParam.get("companyCode")+"%' \n");	
		}
		//公司NAME
		if(!StringUtils.isNullOrEmpty(queryParam.get("companyName"))){
			sql.append("		AND A.COMPANY_NAME LIKE '%"+queryParam.get("companyName")+"%' \n");	
		}
		//索赔结算单开始日期
		if(!StringUtils.isNullOrEmpty(queryParam.get("claimsMonthCycleFrom"))){
			sql.append("		AND A.CCLAIMS_MONTH_CYCLE_FROM >= '"+queryParam.get("claimsMonthCycleFrom").replace("-", "")+"' \n");	
		}
		//索赔结算单结束日期
		if(!StringUtils.isNullOrEmpty(queryParam.get("claimsMonthCycleTo"))){
			sql.append("		AND A.CCLAIMS_MONTH_CYCLE_TO <= '"+queryParam.get("claimsMonthCycleTo").replace("-", "")+"' \n");	
		}
		//结算流水号
		if(!StringUtils.isNullOrEmpty(queryParam.get("vbnNo"))){
			sql.append("		AND A.VBN_NO LIKE '%"+queryParam.get("vbnNo")+"%' \n");	
		}
		//结算日期开始
		if(!StringUtils.isNullOrEmpty(queryParam.get("vbnDate1"))){
			sql.append("		AND VBN_DATE >= DATE_FORMAT('"+queryParam.get("vbnDate1")+"','%Y-%c-%d')  \n");	
		}
		//结算日期结束
		if(!StringUtils.isNullOrEmpty(queryParam.get("vbnDate2"))){
			sql.append("		AND VBN_DATE <= DATE_FORMAT('"+queryParam.get("vbnDate2")+"','%Y-%c-%d') \n");
		}
		//结算状态
		if(!StringUtils.isNullOrEmpty(queryParam.get("isInvoice"))){
			sql.append("		AND A.IS_INVOICE = '"+queryParam.get("isInvoice")+"' \n");
		}
				
		return sql.toString();
	}
	
	
	
	/**
	 * 索赔单明细 详细信息
	 * @return
	 */
	public PageInfoDto getClaimNoticeQueryDetail(Long claimBillingId) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("\n");
		sql.append("SELECT a.COMPANY_CODE,a.COMPANY_NAME,a.SVC_DEALER,a.SVC_DEALER_NAME,DATE_FORMAT(a.VBN_DATE,'%Y-%c-%d') AS VBN_DATE,a.VBN_REMARKS, \n");
		sql.append("	a.CCLAIMS_MONTH_CYCLE_FROM,a.CCLAIMS_MONTH_CYCLE_TO,a.CLAIMS_BILLING_ID, \n");
		sql.append("	a.F_AMT,a.F_RECS,a.W_AMT,a.W_RECS,a.GOODWILL_AMT,a.GOODWILL_RECS, \n");
		sql.append("	a.RECALL_AMT,a.RECALL_RECS,a.MOPAR_AMT,a.MOPAR_RECS,a.IS_INVOICE, \n");
		sql.append("	a.PDI_AMT,a.PDI_RECS,a.TCR_AMT,a.TCR_RECS,a.VAT_INVOICE_AMOUNT,a.CLAIM_CHARGERBACK_AMT, \n");
		sql.append("	a.TOTAL_AMT,b.CLAIMS_BILLING_ID,b.CLAIM_NUMBER,b.TRANSACTION_TYPE, \n");
		sql.append("	b.TRANSACTION_TYPE_DESC,b.PART_FAILED,b.FAILED_PART_DESC, \n");
		sql.append("	DATE_FORMAT( b.DATE_VHCL_IN_SVC,'%Y-%c-%d') AS DATE_VHCL_IN_SVC,DATE_FORMAT( b.DATE_VHCL_RECD,'%Y-%c-%d') AS DATE_VHCL_RECD,b.CLAIM_PAYMENT_CYCLE, \n");
		sql.append("	b.VIN,b.VHCL_FAMILY_CODE,b.VHCL_MODEL_YEAR,b.ODOMETER_VHCL_RECD, \n");
		sql.append("	b.LOP_CAUSAL,b.LOPS_ON_CONDITION_ALL,b.X_FALUR_LOP_DESC,b.COND_SPL_SRV_AMT_CALC, \n");
		sql.append("	b.COND_LABOR_AMT_CALC,b.COND_PARTS_AMT_CALC,b.COND_TOTAL_AMT_CALC,b.REMARKS,b.MOPAR_PEG_RATE,b.CLAIMS_BILLING_DETAIL_ID \n");
		sql.append("FROM TT_CLAIMS_NOTICE_DCS a,TT_CLAIMS_NOTICE_DETAIL_DCS b \n");
		sql.append("WHERE a.CLAIMS_BILLING_ID=b.CLAIMS_BILLING_ID AND \n");
		sql.append("		a.CLAIMS_BILLING_ID="+claimBillingId+" \n");		
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql.toString(), params);
		return pageInfoDto;

	}
	/**
	 * 索赔单明细
	 * @param claimBillingId
	 * @return
	 */	
	public Map getClaimNoticeQueryDetailMap(Long claimBillingId) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("\n");
		sql.append("SELECT a.COMPANY_CODE,a.COMPANY_NAME,a.SVC_DEALER,a.SVC_DEALER_NAME,DATE_FORMAT(a.VBN_DATE,'%Y-%c-%d') AS VBN_DATE,a.VBN_REMARKS, \n");
		sql.append("		a.CCLAIMS_MONTH_CYCLE_FROM,a.CCLAIMS_MONTH_CYCLE_TO,a.CLAIMS_BILLING_ID, \n");
		sql.append("		a.F_AMT,a.F_RECS,a.W_AMT,a.W_RECS,a.GOODWILL_AMT,a.GOODWILL_RECS, \n");
		sql.append("		a.RECALL_AMT,a.RECALL_RECS,a.MOPAR_AMT,a.MOPAR_RECS,a.IS_INVOICE, \n");
		sql.append("		a.PDI_AMT,a.PDI_RECS,a.TCR_AMT,a.TCR_RECS,a.VAT_INVOICE_AMOUNT,a.CLAIM_CHARGERBACK_AMT, \n");
		sql.append("		a.TOTAL_AMT \n");
		sql.append("	FROM TT_CLAIMS_NOTICE_DCS a \n");
		sql.append("	WHERE a.CLAIMS_BILLING_ID= "+claimBillingId+" \n");		
		Map map = OemDAOUtil.findFirst(sql.toString(), params);
		return map;

	}
	/**
	 * 下载
	 * @param queryParam
	 * @return
	 */
	public List<Map> getClaimNoticeQueryDownloadList(Map<String, String> queryParam) {
		//获取当前用户
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("\n");
		sql.append("SELECT A.SVC_DEALER, # 经销商代码 \n");
		sql.append("       A.SVC_DEALER_NAME, # 经销商名称 \n");
		sql.append("       A.F_RECS, # 首保笔数 \n");
		sql.append("       A.F_AMT, # 首保金额 \n");
		sql.append("       A.W_RECS, # 保修（第1.2年）笔数 \n");
		sql.append("       A.W_AMT, # 保修（第1.2年）金额 \n");
		sql.append("       A.OTHERS_RECS, # 保修（第5年）笔数 \n");
		sql.append("       A.OTHERS_AMT, # 保修（第5年）金额 \n");
		sql.append("       A.GOODWILL_RECS, # 善意索赔笔数 \n");
		sql.append("       A.GOODWILL_AMT, # 善意索赔金额 \n");
		sql.append("       A.RECALL_RECS, # 召回笔数 \n");
		sql.append("       A.RECALL_AMT, # 召回金额 \n");
		sql.append("       A.MOPAR_RECS, # 零件索赔笔数 \n");
		sql.append("       A.MOPAR_AMT, # 零件索赔金额 \n");
		sql.append("       A.PDI_RECS, # PDI笔数 \n");
		sql.append("       A.PDI_AMT, # PDI金额 \n");
		sql.append("       A.TCR_RECS, # 海运索赔笔数 \n");
		sql.append("       A.TCR_AMT, # 海运索赔金额 \n");
		sql.append("       A.TOTAL_RECS, # 合计笔数 \n");
		sql.append("       A.TOTAL_AMT # 合计金额 \n");
		sql.append("  FROM TT_CLAIMS_NOTICE_DCS A \n");
		sql.append(" WHERE 1 = 1 \n");
		sql.append(" AND A.SVC_DEALER = '"+loginInfo.getDealerCode()+"' \n");
		//条件
		//公司CODE
		if(!StringUtils.isNullOrEmpty(queryParam.get("companyCode"))){
			sql.append("		AND A.COMPANY_CODE LIKE '%"+queryParam.get("companyCode")+"%' \n");	
		}
		//公司NAME
		if(!StringUtils.isNullOrEmpty(queryParam.get("companyName"))){
			sql.append("		AND A.COMPANY_NAME LIKE '%"+queryParam.get("companyName")+"%' \n");	
		}
		//索赔结算单开始日期
		if(!StringUtils.isNullOrEmpty(queryParam.get("claimsMonthCycleFrom"))){
			sql.append("		AND A.CCLAIMS_MONTH_CYCLE_FROM >= '"+queryParam.get("claimsMonthCycleFrom").replace("-", "")+"' \n");	
		}
		//索赔结算单结束日期
		if(!StringUtils.isNullOrEmpty(queryParam.get("claimsMonthCycleTo"))){
			sql.append("		AND A.CCLAIMS_MONTH_CYCLE_TO <= '"+queryParam.get("claimsMonthCycleTo").replace("-", "")+"' \n");	
		}
		//结算流水号
		if(!StringUtils.isNullOrEmpty(queryParam.get("vbnNo"))){
			sql.append("		AND A.VBN_NO LIKE '%"+queryParam.get("vbnNo")+"%' \n");	
		}
		//结算日期开始
		if(!StringUtils.isNullOrEmpty(queryParam.get("vbnDate1"))){
			sql.append("		AND VBN_DATE >= DATE_FORMAT('"+queryParam.get("vbnDate1")+"','%Y-%c-%d')  \n");	
		}
		//结算日期结束
		if(!StringUtils.isNullOrEmpty(queryParam.get("vbnDate2"))){
			sql.append("		AND VBN_DATE <= DATE_FORMAT('"+queryParam.get("vbnDate2")+"','%Y-%c-%d') \n");
		}
		//结算状态
		if(!StringUtils.isNullOrEmpty(queryParam.get("isInvoice"))){
			sql.append("		AND A.IS_INVOICE = '"+queryParam.get("isInvoice")+"' \n");
		}				
		
		sql.append(" ORDER BY VBN_DATE DESC \n");

			
		List<Map> resultList = OemDAOUtil.downloadPageQuery(sql.toString(), params);
		return resultList;

	}
	
}



















