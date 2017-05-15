package com.yonyou.dms.retail.dao.basedata;

import java.util.ArrayList;
import java.util.Date;
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

@Repository
@SuppressWarnings("rawtypes")
public class BankingDao extends OemBaseDAO{
	
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);

	/**
	 * 查询方法
	 * 
	 * @param queryParam
	 * @return
	 */

	public PageInfoDto findBanking(LoginInfoDto loginInfo,Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam, params,loginInfo);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;
	}

	private String getQuerySql(Map<String, String> queryParam, List<Object> params, LoginInfoDto loginInfo) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM ( \n");
		sql.append("SELECT TRDO.ID,TVSR.REPORT_ID,TRDO.BANK_NAME,TVSR.DEALER_ID,TRDO.DISCOUNT_DEALER_CODE,TRDO.CTM_NAME,TV.VIN,DATE_FORMAT(TVSR.SALES_DATE,'%Y-%m-%d')  SALES_DATE,TV.VEHICLE_USAGE,TV.RETAIL_PRICE, \n");
		sql.append("TRDO.NET_PRICE,DATE_FORMAT(TRDO.APPLY_DATE,'%Y-%m-%d')  APPLY_DATE,DATE_FORMAT(TRDO.CREATE_DATE,'%Y-%m-%d')  CREATE_DATE,DATE_FORMAT(TRDO.DEAL_DATE,'%Y-%m-%d')  DEAL_DATE,TRDO.DEAL_AMOUNT,TRDO.FIRST_PERMENT_RATIO,TRDO.INSTALL_MENT_NUM,TRDO.MERCHANT_FEES, \n");
		sql.append("TRDO.IS_BILLING,TD1.DEALER_SHORTNAME,TD1.DEALER_CODE,TD2.DEALER_SHORTNAME DEALER_SHORTNAME2,TD2.DEALER_CODE DEALER_CODE2, ");
		sql.append("VM.MODEL_YEAR,VM.SERIES_NAME,VM.BRAND_NAME,VM.MODEL_NAME,VM.GROUP_NAME,CONCAT(ROUND(TRDO.MERCHANT_FEES_RATE,2),'%') AS MERCHANT_FEES_RATE ,IFNULL(TRDO.IS_EDIT,0) as IS_EDIT,");
		sql.append("  CONCAT(ROUND(((1-TRDO.DEAL_AMOUNT/TRDO.NET_PRICE)*100),2),'%') SF_RATE\n");
		sql.append("        FROM TT_VS_SALES_REPORT TVSR \n");
		sql.append("        LEFT JOIN (select TRDO.*,tb.BANK_NAME from TM_RETAIL_DISCOUNT_OFFER TRDO inner join TC_BANK tb on TRDO.BANK=tb.ID where tb.STATUS=10011001) TRDO  ");
		sql.append("        ON TVSR.REPORT_ID = TRDO.REPORT_ID \n");
		sql.append("        INNER JOIN TM_VEHICLE_DEC TV \n");
		sql.append("        ON TVSR.VEHICLE_ID = TV.VEHICLE_ID \n");
		sql.append("        INNER JOIN TM_DEALER TD1 \n");
		sql.append("        ON TVSR.DEALER_ID = TD1.DEALER_ID \n");
		//已经上报
		if(OemDictCodeConstants.IF_TYPE_YES.equals(queryParam.get("nvdr"))){
			sql.append("        INNER JOIN TM_DEALER TD2 \n");
		}else{
			sql.append("        LEFT JOIN TM_DEALER TD2 \n");
		}
		sql.append("        ON TRDO.DISCOUNT_DEALER_CODE = TD2.DEALER_CODE \n");
		sql.append("        INNER JOIN ("+getVwMaterialSql()+") VM \n");
		sql.append("        ON VM.MATERIAL_ID = TV.MATERIAL_ID \n");
		sql.append("        WHERE TD1.DEALER_ID = '"+loginInfo.getDealerId()+"'");
		sql.append("        AND TVSR.STATUS = '" + OemDictCodeConstants.STATUS_ENABLE + "'");
		//国产化判断
		if (!StringUtils.isNullOrEmpty(queryParam.get("brandName"))) { 
			sql.append(" and VM.brand_id = ? \n");
			params.add(queryParam.get("brandName"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("seriesName"))) {
			sql.append(" and VM.series_id = ?\n");
			params.add(queryParam.get("seriesName"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("groupName"))) {
			sql.append(" and VM.group_id = ? \n");
			params.add(queryParam.get("groupName"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("modelYear"))) {
			sql.append(" and VM.model_year = ? \n");
			params.add(queryParam.get("modelYear"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("bank"))) {
			sql.append(" and TRDO.BANK = ?\n");
			params.add(queryParam.get("bank"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("CTM_NAME"))) {
			sql.append(" and TRDO.CTM_NAME like ? \n");
			params.add("%"+queryParam.get("CTM_NAME")+"%");
		}
		if(!StringUtils.isNullOrEmpty(queryParam.get("dealerCode"))){
			String dealerCode = queryParam.get("dealerCode").replaceAll(",", "','");
			sql.append(" and TD2.DEALER_CODE in('").append(dealerCode).append("')");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("installmentNumber"))) {
			sql.append(" and TRDO.INSTALL_MENT_NUM IN ("+queryParam.get("installmentNumber")+") \n");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("VIN"))) {
			String vin = queryParam.get("VIN");
			vin = vin.replaceAll("\\^\\^", "\n");
			vin = vin.replaceAll("\\,", "\n");
			sql.append("      " +  getVinsAuto(vin, "TV"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("beginDateTB"))) {
			sql.append("   AND DATE(TRDO.CREATE_DATE) >= ? \n");
			params.add(queryParam.get("beginDateTB"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("endDateTB"))) {
			sql.append("   AND DATE(TRDO.CREATE_DATE) <= ? \n");
			params.add(queryParam.get("endDateTB"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("beginDateJY"))) {
			sql.append("   AND DATE(TRDO.DEAL_DATE) >= ? \n");
			params.add(queryParam.get("beginDateJY"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("endDateJY"))) {
			sql.append("   AND DATE(TRDO.DEAL_DATE) <= ? \n");
			params.add(queryParam.get("endDateJY"));
		}
		//未上报
		String nvdr = queryParam.get("nvdr");
		if("10041002".equals(nvdr)){
			sql.append("        AND TRDO.BANK IS NULL AND TRDO.CREATE_DATE IS  NULL");
		}
		sql.append("  ORDER BY TVSR.SALES_DATE, TVSR.DEALER_ID desc");
		sql.append("        )A   \n");
		//sql.append(" AND  TRDO.IS_EDIT=1");
		logger.info("经销商端零售贴息提报查询"+sql.toString()+"  " + params.toString());
		return sql.toString();
	}
	
	/**
	 * 下载
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<Map> queryEmpInfoforExport(LoginInfoDto loginInfo,Map<String, String> queryParam) throws Exception {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam, params,loginInfo);
		return OemDAOUtil.findAll(sql.toString(), params);

	}
	/**
	 * 修改
	 * 
	 * @param id
	 * @param user
	 * @throws ServiceBizException
	 */
	public void modifyBanking(TmRetailDiscountOfferDTO tcdto,LoginInfoDto loginInfo) throws ServiceBizException {
		try {
			//commond 是判断提报，还是修改操作
			if(tcdto.getIsEdit()== 1){//修改操作
				TmRetailDiscountOfferPO trdopo = new TmRetailDiscountOfferPO();
				List<TmRetailDiscountOfferPO> trdopoList  = TmRetailDiscountOfferPO.find("  REPORT_ID = ? ", tcdto.getReportId());
				if(trdopoList.size() >=0 ){
					trdopo = trdopoList.get(0);
					trdopo.setLong("REPORT_ID", tcdto.getReportId());
					trdopo.setString("DISCOUNT_DEALER_CODE", tcdto.getDiscountDealerCode());
					trdopo.setLong("BANK", tcdto.getBank());
					trdopo.setDouble("NET_PRICE", tcdto.getNetPrice()); 
					trdopo.setTimestamp("APPLY_DATE", tcdto.getApplyDate());
					trdopo.setTimestamp("DEAL_DATE", tcdto.getDealDate());
					trdopo.setDouble("DEAL_AMOUNT", tcdto.getDealAmount());
					trdopo.setInteger("INSTALL_MENT_NUM", tcdto.getInstallMentNum());
					trdopo.setDouble("MERCHANT_FEES", tcdto.getMerchantFees());
					Double firstPermentRatio =1- (tcdto.getDealAmount()/tcdto.getNetPrice());
					trdopo.setDouble("FIRST_PERMENT_RATIO", firstPermentRatio);
					trdopo.setDouble("MERCHANT_FEES_RATE", tcdto.getMerchantFeesRate());
					trdopo.setString("CTM_NAME", tcdto.getCtmName());
					trdopo.setInteger("IS_EDIT", 2);//已经修改过一次
					System.out.println(tcdto.getReportId()+"  "+tcdto.getDiscountDealerCode()+" "+tcdto.getCtmName());
					trdopo.saveIt();
				}else{
					throw new ServiceBizException("报告ID为"+tcdto.getReportId()+" 的数据修改失败！");
				}
			}else{//提报操作
				TmRetailDiscountOfferPO trdopo = new TmRetailDiscountOfferPO();
				List<TmRetailDiscountOfferPO> trdopoList  = TmRetailDiscountOfferPO.find("  REPORT_ID = ? ", tcdto.getReportId());
				if(trdopoList.size() == 0){
					System.out.println(tcdto.getDiscountDealerCode());
					trdopo.setString("DISCOUNT_DEALER_CODE", tcdto.getDiscountDealerCode());//	申请贴息经销商
					trdopo.setLong("REPORT_ID", tcdto.getReportId());
					System.out.println(tcdto.getBank());//	银行
					trdopo.setLong("BANK", tcdto.getBank());//	银行
					trdopo.setDouble("NET_PRICE", tcdto.getNetPrice()); //	净车价
					trdopo.setLong("CREATE_BY", loginInfo.getUserId());
					trdopo.setTimestamp("APPLY_DATE", tcdto.getApplyDate());//申请时间
					trdopo.setTimestamp("CREATE_DATE", new Date());//创建日期
					trdopo.setTimestamp("DEAL_DATE", tcdto.getDealDate());//银行放款时间
					trdopo.setDouble("DEAL_AMOUNT", tcdto.getDealAmount());//贷款金额
					trdopo.setInteger("INSTALL_MENT_NUM", tcdto.getInstallMentNum());//	分期期数
					trdopo.setDouble("MERCHANT_FEES", tcdto.getMerchantFees());//	商户手续费
					Double firstPermentRatio =1- (tcdto.getDealAmount()/tcdto.getNetPrice());
					trdopo.setDouble("FIRST_PERMENT_RATIO", firstPermentRatio);
					trdopo.setDouble("MERCHANT_FEES_RATE", tcdto.getMerchantFeesRate());//	商户手续费
					trdopo.setString("CTM_NAME", tcdto.getCtmName());//	客户名称
					trdopo.setInteger("IS_EDIT", 1);//标识已提报
					trdopo.insert();
				}else {
					throw new ServiceBizException("报告ID为"+tcdto.getReportId()+" 的数据以提报！");
				}
			}
		} catch (Exception e) {
			if("1".equals(tcdto.getIsEdit())){
				System.out.println(e.toString());
				throw new ServiceBizException("提报贴息利率信息修改出错");
			}else {
				System.out.println(e);
				throw new ServiceBizException("提报贴息利率信息提报出错");
			}
			
		}
	}


	@SuppressWarnings("unchecked")
	public Map<String, Object> findById(String vin,LoginInfoDto loginInfo)  throws ServiceBizException  {
		List<Object> params = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT TRDO.ID,  TRDO.BANK_ID,TVSR.REPORT_ID,TRDO.BANK_NAME,TVSR.DEALER_ID,TRDO.DISCOUNT_DEALER_CODE,TRDO.CTM_NAME,TV.VIN,DATE_FORMAT(TVSR.SALES_DATE,'%Y-%m-%d')  SALES_DATE,TV.VEHICLE_USAGE,TV.RETAIL_PRICE, \n");
		sql.append("TRDO.NET_PRICE,DATE_FORMAT(TRDO.APPLY_DATE,'%Y-%m-%d')  APPLY_DATE,DATE_FORMAT(TRDO.CREATE_DATE,'%Y-%m-%d')  CREATE_DATE,DATE_FORMAT(TRDO.DEAL_DATE,'%Y-%m-%d')  DEAL_DATE,TRDO.DEAL_AMOUNT,TRDO.FIRST_PERMENT_RATIO,TRDO.INSTALL_MENT_NUM,TRDO.MERCHANT_FEES, \n");
		sql.append("TRDO.IS_BILLING,TD1.DEALER_SHORTNAME,TD1.DEALER_CODE,");
		sql.append("TRDO.MERCHANT_FEES_RATE ,IFNULL(TRDO.IS_EDIT,0) as IS_EDIT,");
		sql.append("  CONCAT(ROUND(((1-TRDO.DEAL_AMOUNT/TRDO.NET_PRICE)*100),2),'%') SF_RATE\n");
		sql.append("        FROM TT_VS_SALES_REPORT TVSR \n");
		sql.append("        LEFT JOIN (select TRDO.*,tb.BANK_NAME,TB.ID AS BANK_ID from TM_RETAIL_DISCOUNT_OFFER TRDO inner join TC_BANK tb on TRDO.BANK=tb.ID where tb.STATUS=10011001) TRDO  ");
		sql.append("        ON TVSR.REPORT_ID = TRDO.REPORT_ID \n");
		sql.append("        INNER JOIN TM_VEHICLE_DEC TV \n");
		sql.append("        ON TVSR.VEHICLE_ID = TV.VEHICLE_ID \n");
		sql.append("        INNER JOIN TM_DEALER TD1 \n");
		sql.append("        ON TVSR.DEALER_ID = TD1.DEALER_ID \n");
		sql.append("        WHERE TD1.DEALER_ID = '"+loginInfo.getDealerId()+"'");
		sql.append("        AND TVSR.STATUS = '" + OemDictCodeConstants.STATUS_ENABLE + "'");
		sql.append("        AND TV.VIN = '"+vin+"'");
		logger.info("详细查询"+sql.toString()+"  " + params.toString());
		return OemDAOUtil.findFirst(sql.toString(), params);
	}

}
