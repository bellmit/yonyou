package com.yonyou.dms.vehicle.dao.claimApproveMgr;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.function.utils.common.StringUtils;
/**
 * 索赔申请单结算
 * @author ZhaoZ
 * @date 2017年4月28日
 */
@Repository
public class ClaimBillOemQueryDao extends OemBaseDAO{

	/**
	 * 索赔申请单结算查询
	 * @param queryParams
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public PageInfoDto threePackItems(Map<String, String> queryParams) {
		String start=null;
		String end = null;
		List<Object> params = new ArrayList<>();
			StringBuffer sql = new StringBuffer();
			sql.append("select min(start_date) START_DATE,max(end_date) END_DATE from tt_Wr_Claimmonth_dcs where 1=1 \n");
			if (!StringUtils.isNullOrEmpty(queryParams.get("year"))) {
				sql.append(" and work_year = ? \n");
				params.add(queryParams.get("year"));
			}
			if (!StringUtils.isNullOrEmpty(queryParams.get("month"))) {
				sql.append(" and work_nonth = ? \n");
				params.add(queryParams.get("month"));
			}
			
			Map map = OemDAOUtil.findFirst(sql.toString(),params );
			if(null!=map){
				start = CommonUtils.checkNull(map.get("START_DATE"));
				end = CommonUtils.checkNull(map.get("END_DATE"));
			}
		
			List<Object> param = new ArrayList<Object>();
			
			StringBuffer strSql = new StringBuffer();
			strSql.append(" SELECT d.DEALER_CODE,  \n");
			strSql.append("  d.DEALER_SHORTNAME DEALER_NAME,  m.CLAIM_NO,  TWCT.CLAIM_TYPE_CODE,TWCT.CLAIM_TYPE, m.VIN,\n");
			strSql.append("   DATE_FORMAT(m.APPLY_DATE,'%Y-%m-%d') APPLY_DATE, DATE_FORMAT(m.PASS_DATE,'%Y-%m-%d') PASS_DATE, m.SUBMIT_COUNT,m.PART_FEE,m.LABOUR_FEE,m.OTHER_AMOUNT ,m.ALL_AMOUNT,m.DEDUCT_FEE,m.TAX_RATE \n");
			strSql.append("   FROM tt_wr_claim_dcs m " +
					"      inner join tm_dealer d on m.DEALER_ID=d.DEALER_ID " +
					"      left join  TT_WR_CLAIMTYPE_dcs TWCT on TWCT.CLAIM_TYPE_CODE=m.CLAIM_TYPE \n");
			strSql.append("   WHERE   m.STATUS="+OemDictCodeConstants.CLAIM_STATUS_06+" \n");
			strSql.append("  and m.IS_DEL ="+OemDictCodeConstants.IS_DEL_00+"      \n");
			strSql.append("  and d.IS_DEL ="+OemDictCodeConstants.IS_DEL_00+"      \n");
			strSql.append(" and DATE_FORMAT(m.PASS_DATE,'%Y-%m-%d')>= '"+start+"' AND  DATE_FORMAT(m.PASS_DATE,'%Y-%m-%d')<='"+end+"' \n");
			if (!StringUtils.isNullOrEmpty(queryParams.get("dealerCode"))) {
				String s = "";
				if(queryParams.get("dealerCode").indexOf(",")>0){
					String[] str = queryParams.get("dealerCode").split(",");
					for(int i=0;i<str.length;i++){
						s+=""+str[i]+"";
						if(i<str.length-1) s+=",";
					}
				}else{
					s=""+queryParams.get("dealerCode")+"";
				}
				strSql.append(" and d.DEALER_CODE in (?) \n");
				param.add(s);
			}
			if (!StringUtils.isNullOrEmpty(queryParams.get("CLAIM_TYPE"))) {
				strSql.append(" and m.CLAIM_TYPE = ? \n");
				param.add(queryParams.get("CLAIM_TYPE"));
			}
			PageInfoDto dto = OemDAOUtil.pageQuery(strSql.toString(), param);
			//如果有数据
			if(dto.getRows().size()>0){
				for(Map a:dto.getRows()){
					a.put("total", "1");
				}
			}
			return dto;
		}

	/**
	 * //查出所有的符合条件的结算单
	 * @param claimNoIns
	 * @param oemCompanyId
	 * @return
	 */
	public List<Map> getClaimBillOemList(String claimNoIns, Long oemCompanyId) {
		StringBuffer sql= new StringBuffer();
		sql.append(" SELECT * FROM ( \n " );
		sql.append(" SELECT d.DEALER_CODE,max(d.DEALER_ID) DEALER_ID, \n " );
		sql.append(" max(d.DEALER_SHORTNAME) DEALER_NAME, count(*) CLAIMNO_NUM,sum(COALESCE(m.LABOUR_FEE,0)) LABOUR_FEE , \n " );
		sql.append("  sum(COALESCE(m.PART_FEE,0)) PART_FEE,sum(COALESCE(m.OTHER_AMOUNT,0)) OTHER_FEE, \n " );
		sql.append(" sum(COALESCE(m.ALL_AMOUNT,0)) CLAIM_APPLY_FEE, CAST(SUM(COALESCE(m.ALL_AMOUNT,0)*COALESCE(m.TAX_RATE,0)) /100+0.005 AS DECIMAL(10,2)) TAX_FEE, \n " );
		sql.append("  sum(COALESCE(m.DEDUCT_FEE,0)) DEDUCT_FEE,sum(COALESCE(m.ALL_AMOUNT,0)) BALANCE_FEE \n " );
		sql.append(" FROM tt_wr_claim_dcs m, tm_dealer d \n " );
		sql.append("  WHERE m.DEALER_ID=d.DEALER_ID AND m.STATUS="+OemDictCodeConstants.CLAIM_STATUS_06+"  \n " );
		sql.append(" and m.IS_DEL =0  and d.IS_DEL =0 \n " );
		sql.append(" and m.CLAIM_NO in ("+claimNoIns+") \n " );
		sql.append("  group by d.DEALER_CODE \n " );
		sql.append("  ) DCS \n " );
		return OemDAOUtil.findAll(sql.toString(), null);
	}

	/**
	 * 查出所有的符合条件的申请单
	 * @param claimNoIns
	 * @param oemCompanyId
	 * @return
	 */
	public List<Map> getTtWrClaimList(String claimNoIns, Long oemCompanyId) {
		StringBuffer sql= new StringBuffer();
		sql.append(" SELECT DEALER_ID,DEALER_CODE,CLAIM_NO,CLAIM_ID \n " );
		sql.append(" FROM tt_wr_claim_dcs  \n " );
		sql.append("  WHERE  STATUS="+OemDictCodeConstants.CLAIM_STATUS_06+"  \n " );
		sql.append(" and IS_DEL =0   \n " );
		sql.append(" and CLAIM_NO in ("+claimNoIns+") \n " );
		return OemDAOUtil.findAll(sql.toString(), null);
	}

	/**
	 * 索赔结算单查询索赔结算单查询
	 * @param queryParams
	 * @return
	 */
	public PageInfoDto claimBills(Map<String, String> queryParams) {
		List<Object> params = new ArrayList<Object>();
		String sql = getclaimBillsSql(queryParams,params);
		return OemDAOUtil.pageQuery(sql, params);
	}

	/**
	 * 索赔结算单查询SQL
	 * @param queryParams
	 * @param params
	 * @return
	 */
	private String getclaimBillsSql(Map<String, String> queryParams, List<Object> params) {
		LoginInfoDto logonUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		Long oemCompanyId = OemDAOUtil.getOemCompanyId(logonUser);
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT d.DEALER_CODE,  d.DEALER_SHORTNAME DEALER_NAME, bill.BALANCE_ID, \n");
		sql.append(" bill.BALANCE_NO,bill.CLAIMNO_NUM,bill.LABOUR_FEE,bill.PART_FEE,bill.OTHER_FEE, \n");
		sql.append(" bill.CLAIM_APPLY_FEE, bill.TAX_FEE,bill.DEDUCT_FEE,(bill.BALANCE_FEE-bill.DEDUCT_FEE) BALANCE_FEE \n");
		sql.append(" FROM TT_WR_CLAIM_ACCOUNT_DCS bill, tm_dealer d \n");
		sql.append("  WHERE  bill.DEALER_ID=d.DEALER_ID  and bill.IS_DEL =0   \n");
		sql.append(" and bill.OEM_COMPANY_ID="+oemCompanyId+"\n");
		if (!StringUtils.isNullOrEmpty(queryParams.get("DEALER_CODE"))) {
			sql.append(" and d.DEALER_CODE LIKE  ?\n");
			params.add("%"+queryParams.get("DEALER_CODE")+"%");
		}
		if (!StringUtils.isNullOrEmpty(queryParams.get("DEALER_NAME"))) {
			sql.append(" and d.DEALER_NAME LIKE  ?\n");
			params.add("%"+queryParams.get("DEALER_NAME")+"%");
		}
		if (!StringUtils.isNullOrEmpty(queryParams.get("BALANCE_NO"))) {
			sql.append(" and bill.BALANCE_NO LIKE  ?\n");
			params.add("%"+queryParams.get("BALANCE_NO")+"%");
		}
		if(!logonUser.getDutyType().toString().equals(OemDictCodeConstants.DUTY_TYPE_DEPT.toString()) && !logonUser.getDutyType().toString().equals(OemDictCodeConstants.DUTY_TYPE_COMPANY.toString())){
			sql.append("      AND  d.DEALER_ID in ("+getDealersByArea(logonUser.getOrgId().toString())+")\n");
		}
		return sql.toString();
	}

	/**
	 * 索赔结算单明细查询
	 * @param balanceId
	 * @return
	 */
	public PageInfoDto claimBillOemDetails(Long balanceId) {
		StringBuffer sql= new StringBuffer();
		sql.append("  SELECT d.DEALER_CODE, d.DEALER_SHORTNAME DEALER_NAME,  m.CLAIM_NO,  TWCT.CLAIM_TYPE_CODE,TWCT.CLAIM_TYPE,   \n " );
		sql.append(" m.ALL_AMOUNT,CAST(SUM(COALESCE(m.ALL_AMOUNT,0)*COALESCE(m.TAX_RATE,0)) /100+0.005 AS DECIMAL(10,2)) TAX_RATE,m.DEDUCT_FEE,(m.ALL_AMOUNT-m.DEDUCT_FEE) BALANCE_FEE \n " );//
		sql.append("  FROM TT_WR_CLAIMACCOUNT_DETAIL_dcs detail \n " );
		sql.append("  inner join tt_wr_claim_dcs m on detail.CLAIM_ID=m.CLAIM_ID \n " );
		sql.append("  inner join tm_dealer d on m.DEALER_ID=d.DEALER_ID  \n " );
		sql.append("  left join TT_WR_CLAIMTYPE_dcs TWCT on TWCT.CLAIM_TYPE_CODE=m.CLAIM_TYPE \n " );
		sql.append(" WHERE detail.IS_DEL =0   \n " );
		sql.append(" and detail.BALANCE_ID="+balanceId+"\n");
		return OemDAOUtil.pageQuery(sql.toString(), null);
	}

	/**
	 * 索赔结算单查询DLR
	 * @param queryParams
	 * @return
	 */
	public PageInfoDto claimBilldlrs(Map<String, String> queryParams) {
		List<Object> params = new ArrayList<Object>();
		String sql = getclaimBilldlrsSql(queryParams,params);
		return OemDAOUtil.pageQuery(sql, params);
	}

	/**
	 * 索赔结算单查询DLRSQL
	 * @param queryParams
	 * @param params
	 * @return
	 */
	private String getclaimBilldlrsSql(Map<String, String> queryParams, List<Object> params) {
		LoginInfoDto logonUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		Long oemCompanyId = OemDAOUtil.getOemCompanyId(logonUser);
		Long dealerId = logonUser.getDealerId();
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT d.DEALER_CODE,  d.DEALER_SHORTNAME DEALER_NAME, bill.BALANCE_ID, \n");
		sql.append(" bill.BALANCE_NO,bill.CLAIMNO_NUM,bill.LABOUR_FEE,bill.PART_FEE,bill.OTHER_FEE, \n");
		sql.append(" bill.CLAIM_APPLY_FEE, bill.TAX_FEE,bill.DEDUCT_FEE,(bill.BALANCE_FEE-bill.DEDUCT_FEE) BALANCE_FEE \n");
		sql.append(" FROM TT_WR_CLAIM_ACCOUNT_DCS bill, tm_dealer d \n");
		sql.append("  WHERE  bill.DEALER_ID=d.DEALER_ID  and bill.IS_DEL =0   \n");
		sql.append(" and bill.OEM_COMPANY_ID="+oemCompanyId+"\n");
		sql.append(" and bill.DEALER_ID="+dealerId+" \n");
		if (!StringUtils.isNullOrEmpty(queryParams.get("DEALER_CODE"))) {
			sql.append(" and d.DEALER_CODE LIKE  ?\n");
			params.add("%"+queryParams.get("DEALER_CODE")+"%");
		}
		if (!StringUtils.isNullOrEmpty(queryParams.get("DEALER_NAME"))) {
			sql.append(" and d.DEALER_NAME LIKE  ?\n");
			params.add("%"+queryParams.get("DEALER_NAME")+"%");
		}
		if (!StringUtils.isNullOrEmpty(queryParams.get("BALANCE_NO"))) {
			sql.append(" and bill.BALANCE_NO LIKE  ?\n");
			params.add("%"+queryParams.get("BALANCE_NO")+"%");
		}
		
		return sql.toString();
	}

	
}
