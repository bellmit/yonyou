package com.yonyou.dcs.dao;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.DTO.gacfca.TiUsedCarReplacementRateDTO;
import com.yonyou.dms.common.Util.Utility;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.common.OemDictCodeConstants;

@Repository
public class DMSTODCS050Dao extends OemBaseDAO {

	/**
	 * 
	* @Title:	queryTiUsedData
	* @Description: TODO(查询接口表上报的数据) 
	 */
	public List<TiUsedCarReplacementRateDTO> queryTiUsedData(String entityCode, String reportType,String reportDate){
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT ID, DEALER_CODE, SERIES_CODE, HSL_HABOD, POTENTIAL_CUSTOMERS_NUM,	\n" );
		sql.append("	 INTENTION_NUM, SALE_NUM, DEAL_NUM, INTENTION_RATIO, DEAL_RATIO, CONVERSION_RATIO,	\n" );
		sql.append("	 REPORT_TYPE, REPORT_DATE, RESULT, MESSAGE, IS_DEL, CREATE_BY, CREATE_DATE, UPDATE_BY, UPDATE_DATE	\n");
		sql.append("	 	FROM TI_USED_CAR_REPLACEMENT_RATE_DCS	\n" );
		sql.append("	 		where to_char(REPORT_DATE,'yyyy-MM-dd')='"+reportDate+"'	\n" );
		sql.append("	 			and REPORT_TYPE="+reportType+"	\n" );
		sql.append("	 			and DEALER_CODE='"+entityCode+"'	\n" );
	
		List<Map> list = OemDAOUtil.findAll(sql.toString(), null);
		List<TiUsedCarReplacementRateDTO> dtoList=null;
		TiUsedCarReplacementRateDTO dto=null;
		if (list!=null && list.size()>0) {
			dtoList=new ArrayList<TiUsedCarReplacementRateDTO>();
			for(int i=0;i<list.size();i++){
				dto =new TiUsedCarReplacementRateDTO();
            	
				dto.setId(new Long( list.get(0).get("ID").toString()));
	        	dto.setDealerCode(list.get(0).get("DEALER_CODE").toString());
	        	dto.setSeriesCode(list.get(0).get("SERIES_CODE").toString());
	        	dto.setHslHabod(list.get(0).get("HSL_HABOD").toString());
	        	dto.setPotentialCustomersNum(list.get(0).get("POTENTIAL_CUSTOMERS_NUM").toString());
	        	dto.setIntentionNum(list.get(0).get("INTENTION_NUM").toString());
	        	dto.setSaleNum(list.get(0).get("SALE_NUM").toString());
	        	dto.setDealNum(list.get(0).get("DEAL_NUM").toString());
	        	dto.setIntentionRatio(list.get(0).get("INTENTION_RATIO").toString());
	        	dto.setDealRatio(list.get(0).get("DEAL_RATIO").toString());
	        	dto.setConversionRatio(list.get(0).get("CONVERSION_RATIO").toString());
	        	dto.setReportType(list.get(0).get("REPORT_TYPE").toString());
	        	dto.setReportDate(list.get(0).get("REPORT_DATE").toString());
				dtoList.add(dto);
			}
			
		}
        return dtoList;
	}
	
	/**
	 * 根据下端车系code查找上端的车系code
	 */
	public String querySeriesCode(String dmsSeriesCode) {
		String seriesCode = "";
		if (Utility.testIsNotNull(dmsSeriesCode)){
			StringBuilder sqlSb = new StringBuilder("  SELECT * from tm_vhcl_material_group where 1=1");
			List<Object> params = new ArrayList<>();
			sqlSb.append(" and  GROUP_CODE=?");
			params.add(dmsSeriesCode);
			sqlSb.append(" and  GROUP_LEVEL=?");
			params.add(2);
			sqlSb.append(" and  STATUS=?");
			params.add(OemDictCodeConstants.STATUS_ENABLE);
			List<Map> list = OemDAOUtil.findAll(sqlSb.toString(), null);
			if (list!=null && list.size()>0) {
				seriesCode = String.valueOf(list.get(0).get("GROUP_CODE"));
				return seriesCode;
			}
		}
		return seriesCode;
	}
	
	/**
	 * 查询当前二手车周
	 * @param currentDate
	 * @return
	 * @throws Exception
	 */
	public String getReportWeek(String reportDate) throws Exception {
		String reportWeek = "";
		if (Utility.testIsNotNull(reportDate)){
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT WEEK_CODE  \n");
			sql.append(" From TM_WEEK \n");
			sql.append(" WHERE 1=1 ");
			sql.append(" AND START_DATE <= '"+reportDate+"' \n");
			sql.append(" AND END_DATE >= '"+reportDate+"' \n");
			List<Map> list = OemDAOUtil.findAll(sql.toString(), null);
			if (list!=null && list.size()>0) {
				reportWeek = String.valueOf(list.get(0).get("WEEK_CODE"));
			}
		}
		return reportWeek;
	}
	
	/**
	 * 查询报表日期的前一天
	 * @param reportDate
	 * @return
	 */
	public List<Map<String,Object>> getReportList(String reportDate){
		StringBuffer sqlStr= new StringBuffer();
		sqlStr.append("SELECT YEAR_CODE, MONTH_CODE, WEEK_CODE	\n" );
		sqlStr.append("	FROM TM_WEEK	\n" );
		sqlStr.append("	 WHERE 1=1	\n");
		sqlStr.append("	  AND START_DATE <= to_char(date('"+reportDate+"')-1 days,'yyyy-MM-dd')	\n");
		sqlStr.append("	  AND END_DATE >= to_char(date('"+reportDate+"')-1 days,'yyyy-MM-dd')	\n");
		List<Map<String, Object>> list = pageQuery(sqlStr.toString(), null, getFunName());
		return list;
	}
	/**
	* @Description: TODO(根据下端entityCode查询上端dealerCode 区分销售和售后,此方法为销售) 
	* @param @param dealerCode 下端entityCode
	* @param @return 上端dealerCode
	 */
	public String getSaDcsDealerCode1(String entityCode) throws Exception {
		String dealerCode = "";
		if (Utility.testIsNotNull(entityCode)){
			StringBuffer sql= new StringBuffer();
			sql.append("SELECT DEALER_ID, DEALER_CODE, DEALER_NAME FROM TM_DEALER A \n");
			sql.append("	WHERE A.COMPANY_ID = \n");
			sql.append("		(SELECT B.COMPANY_ID FROM TM_COMPANY B \n");
			sql.append("			WHERE B.COMPANY_CODE = \n");
			sql.append("				(SELECT DCS_CODE from TI_DEALER_RELATION C \n");
			sql.append("					WHERE C.DMS_CODE = '").append(entityCode).append("') \n");
			sql.append("		)AND A.DEALER_TYPE = ").append(OemDictCodeConstants.DEALER_TYPE_DVS);
			List<Map> list = OemDAOUtil.findAll(sql.toString(), null);
			if (list!=null && list.size()>0) {
				dealerCode = String.valueOf(list.get(0).get("DEALER_CODE"));
			}
		}
		return dealerCode;
	}
	
}
