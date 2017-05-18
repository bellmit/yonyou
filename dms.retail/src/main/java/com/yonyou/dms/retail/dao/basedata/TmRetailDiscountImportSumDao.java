package com.yonyou.dms.retail.dao.basedata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.utils.common.StringUtils;
/**
 * 销售折扣查询
 * @author Administrator
 *
 */
@Repository
public class TmRetailDiscountImportSumDao extends OemBaseDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	/**
	 * 查询方法
	 * 
	 * @param queryParam
	 * @return
	 * @throws Exception
	 */
	public PageInfoDto getlist(Map<String, String> queryParam) throws Exception {
		List<Object> params = new ArrayList<Object>();
		String sql = getRetailRateList(queryParam, params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;
	}

	public String getRetailRateList(Map<String, String> queryParam, List<Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from ( \n");
		sql.append(" SELECT distinct trd.BANK BANK,tor2.ORG_NAME as BIG_AREA_NAME,tor.ORG_NAME , -- 金融机构，大区，小区\n");
		sql.append(" td.DEALER_SHORTNAME,trd.DEALER_CODE,trd.DEALER_CODE2,-- 经销商名称,经销商code,code2 \n");
		sql.append(" TD1.DEALER_SHORTNAME AS DEALERSHORTNAME,TD1.DEALER_CODE AS DEALERCODE, -- 零售经销code,零售经销名称\n");
		sql.append(" trd.CUSTOMER,trd.VIN, vm.SERIES_NAME,vm.GROUP_NAME,vm.MODEL_YEAR,vm.MODEL_NAME ,trd.SALES_TYPE,\n");
		sql.append("  DATE_FORMAT(trd.APPLY_DATE,'%Y-%m-%d') APPLY_DATE,-- 申请时间 \n");
		sql.append("  DATE_FORMAT(trd.DEAL_DATE,'%Y-%m-%d') DEAL_DATE, -- 放款时间\n");
		sql.append("  trd.MSRP,trd.NET_PRICE,-- 成交价\n");
		sql.append(" trd.FINANCING_PNAME,\n");
		sql.append(" trd.FIRST_PERMENT,-- 首付\n");
		sql.append(" trd.DEAL_AMOUNT,-- 货款金额\n");
		sql.append(" CONCAT(ROUND(trd.FIRST_PERMENT_RATIO,2),'%') FIRST_PERMENT_RATIO,-- 首付比例\n");
		sql.append(" trd.INSTALL_MENT_NUM,-- 分期期数\n");
		sql.append(" trd.TOTAL_INTEREST,-- 总利息\n");
		sql.append(" CONCAT(ROUND(trd.MERCHANT_FEES_RATE,2),'%' ) MERCHANT_FEES_RATE,-- 原利率\n");//商户手续费率	
		sql.append(" CONCAT(ROUND(trd.INTEREST_RATE,2),'%')INTEREST_RATE,-- 政策费率\n");// Usage
		sql.append(" CONCAT(ROUND(trd.POLICY_RATE,2),'%') POLICY_RATE,-- 商户手续费率\n");
		sql.append(" trd.ALLOWANCED_SUM_TAX,-- 贴息金额\n");
		sql.append(" trd.ALLOWANCE_BANK_SUM_TAX,-- 已补贴金额\n");
		sql.append(" DATE_FORMAT(tvn.CREATE_DATE,'%Y-%m-%d %H:%i:%s') NVDR_DATE,-- 上报日期\n");
		sql.append(" DATE_FORMAT(TVSRI.SCANNING_DATE,'%Y-%m-%d %H:%i:%s') SCANNING_DATE,-- 发票扫描日期\n");
		sql.append(" trd.R_MONTH, -- 贴息月份\n");
		sql.append(" DATE_FORMAT(trd.CREATE_DATE,'%Y-%m-%d %H:%i:%s') CREATE_DATE,-- 上传时间\n");
		sql.append(" TU.ACNT as CREATE_BY,-- 上传账号\n");
		sql.append(" trd.REMARK \n");
		sql.append(" from TM_RETAIL_DISCOUNT_IMPORT  trd\n");
		sql.append(" left join TM_DEALER td on td.DEALER_CODE=trd.DEALER_CODE\n");
		sql.append(" left join TM_VEHICLE_DEC tv on tv.VIN=trd.VIN\n");
		sql.append(" left join TT_VS_SALES_REPORT TVSR on TVSR.VEHICLE_ID = tv.VEHICLE_ID-- 实销信息\n");
		sql.append(" left join TM_DEALER TD1 on TD1.DEALER_ID = TVSR.DEALER_ID -- 零售销售经销商\n");
		sql.append(" left join ("+getVwMaterialSql()+") vm on tv.MATERIAL_ID=vm.MATERIAL_ID\n");
		sql.append(" left join TT_VS_NVDR tvn on tvn.VIN=trd.VIN\n");
		sql.append(" left join TC_USER TU on TU.USER_ID=trd.create_By\n");
		sql.append(" left join TT_VS_SALES_REPORT_INVOICE TVSRI on TVSRI.VIN=trd.VIN \n");
		sql.append(" left join TM_DEALER_ORG_RELATION tdor on tdor.DEALER_ID=td.DEALER_ID \n");
		sql.append(" left join TM_ORG tor on tor.ORG_ID=tdor.ORG_ID \n");
		sql.append(" LEFT JOIN TM_ORG tor2 on tor.PARENT_ORG_ID=tor2.ORG_ID \n");
		sql.append(" LEFT JOIN TM_ORG_DLR_ORDER TOD on (TOR.ORG_ID=TOD.ORG_DLR_ID AND TOD.type=3) \n");
		sql.append(" LEFT JOIN TM_ORG_DLR_ORDER TDO1 on TDO1.ORG_DLR_ID = tor2.ORG_ID  AND TDO1.TYPE=2 -- 大区 \n");
		sql.append(" LEFT JOIN TM_ORG_DLR_ORDER TDO2 on TDO2.ORG_DLR_ID = tor.ORG_ID   AND TDO2.TYPE=3 -- 小区\n ");
		sql.append(" where 1=1\n");
		//品牌
		if(!StringUtils.isNullOrEmpty(queryParam.get("brandName"))){
			sql.append(" and VM.BRAND_ID  = ? \n");
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
		//银行
		if (!StringUtils.isNullOrEmpty(queryParam.get("bank"))) {
			sql.append(" and trd.BANK = ? ");
			params.add(queryParam.get("bank"));
		}
		//客户姓名
		if (!StringUtils.isNullOrEmpty(queryParam.get("CUSTOMER"))) {
			sql.append(" and trd.CUSTOMER like '%"+queryParam.get("CUSTOMER")+"%'  ");
		}
		//VIN
		if(!StringUtils.isNullOrEmpty(queryParam.get("vin"))){
			String vin = queryParam.get("vin");
			vin = vin.replaceAll("\\^", "\n");
			vin = vin.replaceAll("\\,", "\n");
			sql.append("		"+getVinsAuto(vin, "trd"));
		}
		//申请日期
		if (!StringUtils.isNullOrEmpty(queryParam.get("beginDate"))) {
			sql.append("   AND DATE(trd.APPLY_DATE) >= ? \n");
			params.add(queryParam.get("beginDate"));			
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("endDate"))) {
			sql.append("   AND DATE(trd.APPLY_DATE) <= ? \n");
			params.add(queryParam.get("endDate"));			
		}
		//银行放款日期
		if (!StringUtils.isNullOrEmpty(queryParam.get("beginDateJY"))) {
			sql.append("   AND DATE(trd.DEAL_DATE) >= '"+queryParam.get("beginDateJY")+"' \n");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("endDateJY"))) {
			sql.append("   AND DATE(trd.DEAL_DATE) <= '"+queryParam.get("endDateJY")+"' \n");
		}
		//经销商
		if (!StringUtils.isNullOrEmpty(queryParam.get("dealerCode"))) {
			sql.append(" and trd.dealer_code IN ("+queryParam.get("dealerCode")+") ");
		}
		//政策费率
		if (!StringUtils.isNullOrEmpty(queryParam.get("policyRate"))) {
			sql.append(" and TRD.POLICY_RATE = ? ");
			params.add(queryParam.get("policyRate"));
		}
		//补贴年月
		if (!StringUtils.isNullOrEmpty(queryParam.get("subsidyYear"))) {
			sql.append(" and TRD.R_YEAR = ? ");
			params.add(queryParam.get("subsidyYear"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("orderMonth"))) {
			sql.append(" and TRD.R_MONTH = ? ");
			params.add(queryParam.get("orderMonth"));
		}
		sql.append(") a \n");
		logger.debug("折扣查询SQL： " + sql.toString()  + " " + params.toString());
		return sql.toString();
	}

	/**
	 * 下载
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> queryEmpInfoforExport(Map<String, String> queryParam) throws Exception {
		List<Object> params = new ArrayList<Object>();
		String sql = getRetailRateList(queryParam, params);
		return OemDAOUtil.findAll(sql.toString(), params);

	}

}
