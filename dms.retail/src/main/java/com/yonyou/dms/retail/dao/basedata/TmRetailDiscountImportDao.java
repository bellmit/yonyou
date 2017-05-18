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
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.retail.domains.DTO.basedata.TmRetailDiscountOfferDTO;
import com.yonyou.dms.retail.domains.PO.basedata.TmRetailDiscountOfferPO;

/**
 * 经销商提报查询
 * 
 * @author Administrator
 *
 */
@Repository
public class TmRetailDiscountImportDao extends OemBaseDAO {
	
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
		sql.append("select t.* from (select TRO.ID,tb.BANK_NAME BANK,\n");
		sql.append(" tor2.ORG_NAME ORG_NAME2,\n");
		sql.append(" tor.ORG_NAME ORG_NAME,\n");
		sql.append(" td1.DEALER_CODE DEALER_CODE1,\n");
		sql.append(" td1.DEALER_SHORTNAME DEALER_SHORTNAME1,\n");
		sql.append(" td2.DEALER_CODE DEALER_CODE2,\n");
		sql.append(" td2.DEALER_SHORTNAME DEALER_SHORTNAME2,\n");
		sql.append(" tro.CTM_NAME,\n");
		sql.append(" vm.SERIES_NAME,\n");
		sql.append(" vm.GROUP_NAME,\n");
		sql.append(" vm.MODEL_YEAR,\n");
		sql.append(" tv.VIN,\n");
		sql.append(" DATE_FORMAT(tvn.CREATE_DATE,'%Y-%m-%d') NVDR_DATE,\n");// 零售上报时间
		sql.append(" tv.VEHICLE_USAGE,\n");// Usage
		sql.append(" tv.RETAIL_PRICE,\n");// MSRP
		sql.append(" tro.NET_PRICE,\n");// 净车价
		sql.append(" DATE_FORMAT(tro.APPLY_DATE,'%Y-%m-%d') APPLY_DATE,\n");// 申请时间
		sql.append(" DATE_FORMAT(tro.CREATE_DATE,'%Y-%m-%d') CREATE_DATE,\n");// 提报时间
		sql.append(" DATE_FORMAT(tro.DEAL_DATE,'%Y-%m-%d') DEAL_DATE,\n");// 交易时间
		sql.append(" tro.DEAL_AMOUNT,\n");// 交易金额
		// sql.append(" cast(tro.FIRST_PERMENT_RATIO as numeric(20,0))||'%'
		// FIRST_PERMENT_RATIO,\n");//首付比例
		sql.append("  CONCAT(ROUND(((1-tro.DEAL_AMOUNT/tro.NET_PRICE)*100),2),'%') FIRST_PERMENT_RATIO,\n");
		// sql.append(" cast(tro.FIRST_PERMENT_RATIO as numeric(20,0))||'%'
		// FIRST_PERMENT_RATIO,\n");//首付比例
		sql.append(" tro.INSTALL_MENT_NUM,\n");// 分期期数
		sql.append(" tro.MERCHANT_FEES,\n");// 商户手续费
		sql.append(" CONCAT(ROUND(tro.MERCHANT_FEES_RATE,2 ),'%') MERCHANT_FEES_RATE,\n");// 商户手续费率
		sql.append(" tvn.STATUS,\n");// 是否已开票
		sql.append(" (case when td1.DEALER_CODE=td2.DEALER_CODE then '是' else '否' end) IS_SAME,\n");// 是否相同
		sql.append(" (case when tro.ID>0 then "+OemDictCodeConstants.IF_TYPE_YES+" else "+OemDictCodeConstants.IF_TYPE_NO+" end) TRDO\n");// 是否上报
		sql.append(" from TT_VS_SALES_REPORT tvsr\n");
		sql.append(" left join TM_RETAIL_DISCOUNT_OFFER tro on tro.REPORT_ID=tvsr.REPORT_ID\n");
		sql.append(" left join TM_DEALER td1 on td1.DEALER_ID=tvsr.DEALER_ID\n");
		sql.append(" left join TM_DEALER td2 on td2.DEALER_CODE=tro.DISCOUNT_DEALER_CODE\n");
		sql.append(" left join TM_VEHICLE_DEC tv on tv.VEHICLE_ID=tvsr.VEHICLE_ID\n");
		sql.append(" left join ("+getVwMaterialSql()+") vm on tv.MATERIAL_ID=vm.MATERIAL_ID\n");
		sql.append(" left join TT_VS_NVDR tvn on tvn.VIN=tv.VIN\n");
		sql.append(" left join TM_DEALER_ORG_RELATION tdor on tdor.DEALER_ID=td1.DEALER_ID\n");
		sql.append(" left join TM_ORG tor on tor.ORG_ID=tdor.ORG_ID\n");
		sql.append(" left join TM_ORG tor2 on tor.PARENT_ORG_ID=tor2.ORG_ID\n");
		sql.append(" left join TC_BANK TB ON TB.ID=TRO.BANK	AND tb.STATUS=" + OemDictCodeConstants.STATUS_ENABLE
				+ "	\n");
		sql.append(" where 1=1  \n");
		//品牌
		if (!StringUtils.isNullOrEmpty(queryParam.get("brandCode"))) {
			sql.append(" and VM.BRAND_ID  = ? \n");
			params.add(queryParam.get("brandCode"));;
		}
		//车系
		if (!StringUtils.isNullOrEmpty(queryParam.get("seriesName"))) {
			sql.append(" and VM.SERIES_ID  = ? \n");
			params.add(queryParam.get("seriesName"));
		}
		//车款
		if(!StringUtils.isNullOrEmpty(queryParam.get("groupName"))){
			sql.append(" and VM.GROUP_ID  = ? \n");
			params.add(queryParam.get("groupName"));
		}
		//年款
		if (!StringUtils.isNullOrEmpty(queryParam.get("modelYear"))) {
			sql.append(" and VM.MODEL_YEAR  = '"+queryParam.get("modelYear")+"' \n");
		}
		//银行
		if (!StringUtils.isNullOrEmpty(queryParam.get("bank"))) {
			sql.append(" and tb.bank_name = ? ");
			params.add(queryParam.get("bank"));
		}
		//客户姓名
		if (!StringUtils.isNullOrEmpty(queryParam.get("CTM_NAME"))) {
			sql.append(" and tro.CTM_NAME LIKE '%"+queryParam.get("CTM_NAME")+"%'  ");
		}
		//销售大区
		if (!StringUtils.isNullOrEmpty(queryParam.get("bigOrgName"))) {
			sql.append(" and TOR.PARENT_ORG_ID = ? ");
			params.add(queryParam.get("bigOrgName"));
		}
		//销售小区
		if (!StringUtils.isNullOrEmpty(queryParam.get("smallOrgName"))) {
			sql.append(" and TOR.ORG_ID = ? ");
			params.add(queryParam.get("smallOrgName"));
		}
		//VIN
		if(!StringUtils.isNullOrEmpty(queryParam.get("vin"))){
			String vin = queryParam.get("vin");
			vin = vin.replaceAll("\\^", "\n");
			vin = vin.replaceAll("\\,", "\n");
			sql.append("		"+getVinsAuto(vin, "tv"));
		}
		//分期期数
		if (!StringUtils.isNullOrEmpty(queryParam.get("installmentNumber"))) {
			sql.append(" and tro.INSTALL_MENT_NUM = ? ");
			params.add(queryParam.get("installmentNumber"));
		}
		//申请时间
		if (!StringUtils.isNullOrEmpty(queryParam.get("beginDate"))) {
			sql.append("   AND DATE(tro.APPLY_DATE) >= ? \n");
			params.add(queryParam.get("beginDate"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("endDate"))) {
			sql.append("   AND DATE(tro.APPLY_DATE) <= ? \n");
			params.add(queryParam.get("endDate"));
		}
		//放款时间
		if (!StringUtils.isNullOrEmpty(queryParam.get("beginDateJY"))) {
			sql.append("   AND DATE(tro.DEAL_DATE) >= ? \n");
			params.add(queryParam.get("beginDateJY"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("endDateJY"))) {
			sql.append("   AND DATE(tro.DEAL_DATE) <= ? \n");
			params.add(queryParam.get("endDateJY"));
		}
		//提报时间
		if (!StringUtils.isNullOrEmpty(queryParam.get("beginDateTB"))) {
			sql.append("   AND DATE(tro.CREATE_DATE) >= ? \n");
			params.add(queryParam.get("beginDateTB"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("endDateTB"))) {
			sql.append("   AND DATE(tro.CREATE_DATE) <= ? \n");
			params.add(queryParam.get("endDateTB"));
		}
		//经销商
		if (!StringUtils.isNullOrEmpty(queryParam.get("dealerCode"))) {
			sql.append(" and td2.DEALER_CODE IN ("+queryParam.get("dealerCode")+") ");
		}
		sql.append(" ) t\n");
		sql.append(" where 1=1\n");
		//是否相同
		if(!StringUtils.isNullOrEmpty(queryParam.get("TRDO"))){
			sql.append(" and t.TRDO= ? ");
			params.add(queryParam.get("TRDO"));
		}
		logger.debug("厂端查询生成SQL为:"+sql.toString()+" "+ params.toString());
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
		return OemDAOUtil.downloadPageQuery(sql.toString(), params);

	}

	/**
	 * 根据ID查询
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> findById(Long id) {
		StringBuffer sql=new StringBuffer();
		sql.append(" SELECT TRD.ID,TVH.VIN, TRD.DISCOUNT_DEALER_CODE, TM.DEALER_CODE,TRD.CTM_NAME,TRD.BANK, \n");
		sql.append(" TRD.NET_PRICE,IFNULL(DATE_FORMAT(TRD.APPLY_DATE,'%Y-%m-%d'),'') AS APPLY_DATE,");
		sql.append(" IFNULL(DATE_FORMAT(TRD.DEAL_DATE,'%Y-%m-%d'),'') AS DEAL_DATE,TRD.DEAL_AMOUNT,");
		sql.append( " TRD.INSTALL_MENT_NUM,TRD.MERCHANT_FEES,CAST(IFNULL(TRD.MERCHANT_FEES_RATE,0.00) as decimal(7,2)) as MERCHANT_FEES_RATE \n");
		sql.append("   FROM  TT_VS_SALES_REPORT TVS \n");
		sql.append("  left join TM_RETAIL_DISCOUNT_OFFER TRD  on TVS.REPORT_ID = TRD.REPORT_ID \n");
		sql.append("  left join TM_DEALER TM on TVS.DEALER_ID = TM.DEALER_ID \n");
		sql.append("  left join TM_VEHICLE_DEC TVH on TVS.VEHICLE_ID = TVH.VEHICLE_ID \n");
		sql.append("  left join TC_BANK TB on TRD.BANK=TB.ID \n");
		sql.append("  where 1=1 ");
		sql.append(" AND TRD.ID="+id);
		logger.debug("修改查询SQL为:"+sql.toString()+" ");
		return OemDAOUtil.findFirst(sql.toString(), null);
	}

	public void doSave(TmRetailDiscountOfferDTO tcDto, LoginInfoDto loginInfo) throws Exception {
		try{
			TmRetailDiscountOfferPO po = new TmRetailDiscountOfferPO();
			po=TmRetailDiscountOfferPO.findById(tcDto.getId());
			if(!StringUtils.isNullOrEmpty(tcDto.getApplyDate())){
				po.setTimestamp("APPLY_DATE", tcDto.getApplyDate());
			}
			po.setLong("BANK", tcDto.getBank());
			po.setString("CTM_NAME", tcDto.getCtmName());
			po.setDouble("DEAL_AMOUNT", tcDto.getDealAmount());
			if(!StringUtils.isNullOrEmpty(tcDto.getDealDate())){
				po.setDate("DEAL_DATE", tcDto.getDealDate());
			}
			po.setString("DISCOUNT_DEALER_CODE", tcDto.getDiscountDealerCode());
			if(!StringUtils.isNullOrEmpty(tcDto.getInstallMentNum())){
				po.setInteger("INSTALL_MENT_NUM", tcDto.getInstallMentNum());
			}
			po.setDouble("MERCHANT_FEES", tcDto.getMerchantFees());
			po.setDouble("MERCHANT_FEES_RATE", tcDto.getMerchantFeesRate());
			po.setDouble("NET_PRICE", tcDto.getNetPrice());
			po.saveIt();
		}catch (Exception e) {
			System.out.println(e);
			throw new ServiceBizException("经销商提报修改出错！请检查数据！");
		}
		
	}

}
