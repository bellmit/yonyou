package com.yonyou.dms.part.dao;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * 配件订单管理Dao
 * @author ZhaoZ
 * @date 2017年3月24日
 */

@Repository
public class PartAllotAaDao extends OemBaseDAO{

	/**
	 * 配件调拨单查询
	 * @param queryParams
	 * @return
	 */
	public PageInfoDto getAllotInfo(Map<String, String> queryParams) {
		List<Object> params = new ArrayList<Object>();
		String sql = getAllotInfoSql(queryParams, params);
		return  OemDAOUtil.pageQuery(sql, params);
	}
	
	/**
	 * 配件调拨单查询SQL
	 * @param queryParams
	 * @return
	 */
	private String getAllotInfoSql(Map<String, String> queryParams, List<Object> params) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT  \n");
		sql.append("  TPAO.OUT_ID, \n");
		sql.append("  TPAO.OUT_NO, \n");
		sql.append("  TPAO.FROM_DLR_CODE, \n");
		sql.append("  TDO.DEALER_SHORTNAME OUT_DEALER_SHORTNAME, \n");
		sql.append("  TPAO.TO_DLR_CODE,\n");
		sql.append("  TDI.DEALER_SHORTNAME IN_DEALER_SHORTNAME,  \n");
		sql.append("  CASE WHEN TPAO.ALLOCATE_TYPE='16991001' THEN '网内调拨' WHEN TPAO.ALLOCATE_TYPE='16991002' THEN '普通调拨' WHEN TPAO.ALLOCATE_TYPE='16991003' THEN '呆滞调拨'  ELSE '' END AS ALLOCATE_TYPE ,   \n");
		sql.append("  IFNULL(DATE_FORMAT(TPAO.OUT_DATE,'%Y-%m-%d'),'') AS OUT_DATE, \n");
		sql.append("  CAST(ROUND(TPAO.OUT_AMOUNT,2) AS DECIMAL(10, 2)) OUT_AMOUNT,\n");
		sql.append("  CAST(ROUND(TPAO.COST_AMOUNT,2) AS DECIMAL(10, 2)) COST_AMOUNT, \n");
		sql.append("  CAST(ROUND(TPAO.DNP_AMOUNT,2) AS DECIMAL(10, 2)) DNP_AMOUNT, \n");
		sql.append("  CAST(ROUND(TPAO.MSRP_AMOUNT,2) AS DECIMAL(10, 2)) MSRP_AMOUNT, \n");
		sql.append("  TI.IN_NO, \n");
		sql.append("  CAST(ROUND(TI.IN_AMOUNT,2) AS DECIMAL(10, 2)) IN_AMOUNT, \n");
		sql.append("  IFNULL(DATE_FORMAT(TI.IN_DATE,'%Y-%m-%d'),'') AS IN_DATE, \n");
		sql.append("  CASE WHEN (TI.IN_NO IS NULL OR TI.IN_NO='') THEN '已出库' ELSE '已入库' END AS STATUS \n");
		sql.append("FROM TT_PT_ALLOT_OUT_dcs TPAO \n");
		sql.append("LEFT JOIN  TM_DEALER TDO ON TDO.DEALER_CODE=TPAO.FROM_DLR_CODE \n");
		sql.append("LEFT JOIN  TM_DEALER TDI ON TDI.DEALER_CODE=TPAO.TO_DLR_CODE \n");
		sql.append("LEFT JOIN  TT_PT_ALLOT_IN_dcs TI ON TI.OUT_NO=TPAO.OUT_NO \n");
		sql.append("WHERE  1=1\n");
		//调出经销商不为空
		if(!StringUtils.isNullOrEmpty(queryParams.get("fromDlrCode"))){ 
        	  String[] dealerCodes=queryParams.get("fromDlrCode").split(",");
    		  String code="";
    		  for(int i=0;i<dealerCodes.length;i++){
    			  code+=""+dealerCodes[i]+",";
    		  }
    		  code=code.substring(0,code.length()-1);
    		  sql.append(" 					 AND TPAO.FROM_DLR_CODE IN (?) \n");
        	  params.add(code);
          }
		
		//调入经销商不为空
		if(!StringUtils.isNullOrEmpty(queryParams.get("toDlrCode"))){ 
      	  String[] dealerCodes=queryParams.get("toDlrCode").split(",");
  		  String code="";
  		  for(int i=0;i<dealerCodes.length;i++){
  			  code+=""+dealerCodes[i]+",";
  		  }
  		  code=code.substring(0,code.length()-1);
  		  sql.append(" 					 AND TPAO.TO_DLR_CODE IN (?) \n");
      	  params.add(code);
        }
		//出库单号
		if(!StringUtils.isNullOrEmpty(queryParams.get("outNo"))){ 
			sql.append(" AND TPAO.OUT_NO LIKE ? \n");
       	    params.add("%"+queryParams.get("outNo")+"%");
         }
		//入库单号
		if(!StringUtils.isNullOrEmpty(queryParams.get("inNo"))){ 
			sql.append(" AND TI.IN_NO LIKE ? \n");
       	    params.add("%"+queryParams.get("inNo")+"%");
         }
		//出库日期
		if (!StringUtils.isNullOrEmpty(queryParams.get("dateType")) &&"93341001".equals(queryParams.get("dateType"))) {
			//开始日期不为空
			if(!StringUtils.isNullOrEmpty(queryParams.get("beginDate"))){ 
				sql.append(" AND  DATE_FORMAT(TPAO.OUT_DATE,'%Y-%m-%d') >='" + queryParams.get("beginDate") +"' \n");
	         }
			//结束日期不为空
			if(!StringUtils.isNullOrEmpty(queryParams.get("beginDate"))){ 
				sql.append(" AND  DATE_FORMAT(TPAO.OUT_DATE,'%Y-%m-%d') <='" + queryParams.get("endDate") +"' \n");
	         }
			
		}
		//入库日期
		if (!StringUtils.isNullOrEmpty(queryParams.get("dateType")) &&"93341002".equals(queryParams.get("dateType"))) {
			//开始日期不为空
			if(!StringUtils.isNullOrEmpty(queryParams.get("beginDate"))){ 
				sql.append(" AND  DATE_FORMAT(TI.IN_DATE,'%Y-%m-%d') >='" + queryParams.get("beginDate") +"' \n");
	         }
			//结束日期不为空
			if(!StringUtils.isNullOrEmpty(queryParams.get("beginDate"))){ 
				sql.append(" AND  DATE_FORMAT(TI.IN_DATE,'%Y-%m-%d') <='" + queryParams.get("endDate") +"' \n");
	         }
			
		}
		return sql.toString();
	}

	/**
	 * 配件调拨单查询
	 * @param queryParams
	 * @return
	 */
	public List<Map> getDownLoadInfo(Map<String, String> queryParams) {
		List<Object> params = new ArrayList<Object>();
		String sql = getDownLoadInfoSql(queryParams, params);
		return  OemDAOUtil.findAll(sql, params);
	}
	/**
	 * 配件调拨单查询SQL
	 * @param queryParams
	 * @return
	 */
	private String getDownLoadInfoSql(Map<String, String> queryParams, List<Object> params) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT  \n");
		sql.append("  TPAO.OUT_ID, \n");
		sql.append("  TPAO.OUT_NO, \n");
		sql.append("  TPAO.FROM_DLR_CODE, \n");
		sql.append("  TDO.DEALER_SHORTNAME OUT_DEALER_SHORTNAME, \n");
		sql.append("  TPAO.TO_DLR_CODE,\n");
		sql.append("  TDI.DEALER_SHORTNAME IN_DEALER_SHORTNAME,  \n");
		sql.append("  CASE WHEN TPAO.ALLOCATE_TYPE='16991001' THEN '网内调拨' WHEN TPAO.ALLOCATE_TYPE='16991002' THEN '普通调拨' WHEN TPAO.ALLOCATE_TYPE='16991003' THEN '呆滞调拨'  ELSE '' END AS ALLOCATE_TYPE ,   \n");
		sql.append("  IFNULL(DATE_FORMAT(TPAO.OUT_DATE,'%Y-%m-%d'),'') AS OUT_DATE, \n");
		sql.append("  CAST(ROUND(TPAO.OUT_AMOUNT,2) AS DECIMAL(10, 2)) OUT_AMOUNT,\n");
		sql.append("  CAST(ROUND(TPAO.COST_AMOUNT,2) AS DECIMAL(10, 2)) COST_AMOUNT, \n");
		sql.append("  CAST(ROUND(TPAO.DNP_AMOUNT,2) AS DECIMAL(10, 2)) DNP_AMOUNT, \n");
		sql.append("  CAST(ROUND(TPAO.MSRP_AMOUNT,2) AS DECIMAL(10, 2)) MSRP_AMOUNT, \n");
		sql.append("  TI.IN_NO, \n");
		sql.append("  CAST(ROUND(TI.IN_AMOUNT,2) AS DECIMAL(10, 2)) IN_AMOUNT, \n");
		sql.append("  IFNULL(DATE_FORMAT(TI.IN_DATE,'%Y-%m-%d'),'') AS IN_DATE, \n");
		sql.append("  CASE WHEN (TI.IN_NO IS NULL OR TI.IN_NO='') THEN '已出库' ELSE '已入库' END AS STATUS \n");
		sql.append("FROM TT_PT_ALLOT_OUT_dcs TPAO \n");
		sql.append("LEFT JOIN  TM_DEALER TDO ON TDO.DEALER_CODE=TPAO.FROM_DLR_CODE \n");
		sql.append("LEFT JOIN  TM_DEALER TDI ON TDI.DEALER_CODE=TPAO.TO_DLR_CODE \n");
		sql.append("LEFT JOIN  TT_PT_ALLOT_IN_dcs TI ON TI.OUT_NO=TPAO.OUT_NO \n");
		sql.append("WHERE  1=1\n");
		//调出经销商不为空
				if(!StringUtils.isNullOrEmpty(queryParams.get("fromDlrCode"))){ 
		        	  String[] dealerCodes=queryParams.get("fromDlrCode").split(",");
		    		  String code="";
		    		  for(int i=0;i<dealerCodes.length;i++){
		    			  code+=""+dealerCodes[i]+",";
		    		  }
		    		  code=code.substring(0,code.length()-1);
		    		  sql.append(" 					 AND TPAO.FROM_DLR_CODE IN (?) \n");
		        	  params.add(code);
		          }
				
				//调入经销商不为空
				if(!StringUtils.isNullOrEmpty(queryParams.get("toDlrCode"))){ 
			      	  String[] dealerCodes=queryParams.get("toDlrCode").split(",");
			  		  String code="";
			  		  for(int i=0;i<dealerCodes.length;i++){
			  			  code+=""+dealerCodes[i]+",";
			  		  }
			  		  code=code.substring(0,code.length()-1);
			  		  sql.append(" 					 AND TPAO.TO_DLR_CODE IN (?) \n");
			      	  params.add(code);
		        }
				//出库单号
				if(!StringUtils.isNullOrEmpty(queryParams.get("outNo"))){ 
					sql.append(" AND TPAO.OUT_NO LIKE ? \n");
		       	    params.add("%"+queryParams.get("outNo")+"%");
		         }
				//入库单号
				if(!StringUtils.isNullOrEmpty(queryParams.get("inNo"))){ 
					sql.append(" AND TI.IN_NO LIKE ? \n");
		       	    params.add("%"+queryParams.get("inNo")+"%");
		         }
				//出库日期
				if (!StringUtils.isNullOrEmpty(queryParams.get("dateType")) &&"out".equals(queryParams.get("dateType"))) {
					//开始日期不为空
					if(!StringUtils.isNullOrEmpty(queryParams.get("beginDate"))){ 
						sql.append(" AND  DATE_FORMAT(TPAO.OUT_DATE,'%Y-%m-%d') >='" + queryParams.get("beginDate") +"' \n");
			         }
					//结束日期不为空
					if(!StringUtils.isNullOrEmpty(queryParams.get("beginDate"))){ 
						sql.append(" AND  DATE_FORMAT(TPAO.OUT_DATE,'%Y-%m-%d') <='" + queryParams.get("endDate") +"' \n");
			         }
					
				}
				//入库日期
				if (!StringUtils.isNullOrEmpty(queryParams.get("dateType")) &&"in".equals(queryParams.get("dateType"))) {
					//开始日期不为空
					if(!StringUtils.isNullOrEmpty(queryParams.get("beginDate"))){ 
						sql.append(" AND  DATE_FORMAT(TI.IN_DATE,'%Y-%m-%d') >='" + queryParams.get("beginDate") +"' \n");
			         }
					//结束日期不为空
					if(!StringUtils.isNullOrEmpty(queryParams.get("beginDate"))){ 
						sql.append(" AND  DATE_FORMAT(TI.IN_DATE,'%Y-%m-%d') <='" + queryParams.get("endDate") +"' \n");
			         }
					
				}
				return sql.toString();
	}

	/**
	 * 配件调拨单查询明细
	 * @param id
	 * @return
	 */
	public PageInfoDto allotOutDeInfo(BigDecimal id) {
		StringBuilder sql = new StringBuilder();
		sql.append("    SELECT \n");
		sql.append("    STORAGE_CODE, \n");
		sql.append("    STORAGE_POSITION_CODE,\n");
		sql.append("    PART_NO, \n");
		sql.append("    PART_NAME,  \n");
		sql.append("    UNIT_NAME, \n");
		sql.append("    OUT_QUANTITY,  \n");
		sql.append("    OUT_PRICE ,\n");
		sql.append("    OUT_AMOUNT \n");
		sql.append("    FROM TT_PT_ALLOT_OUT_DETAIL_dcs \n");
		sql.append("    WHERE 1 = 1 \n");
		sql.append("    AND OUT_ID = '" + id + "'\n");
		return  OemDAOUtil.pageQuery(sql.toString(), null);
	}

	/**
	 * 配件调拨单明细查询
	 * @param queryParams
	 * @return
	 */
	public PageInfoDto getAllotDetailInfo(Map<String, String> queryParams) {
		List<Object> params = new ArrayList<Object>();
		String sql = getAllotDetailInfoSQL(queryParams, params);
		return  OemDAOUtil.pageQuery(sql, params);
	}

	/**
	 * 配件调拨单明细查询SQL
	 * @param queryParams
	 * @return
	 */
	private String getAllotDetailInfoSQL(Map<String, String> queryParams, List<Object> params) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT DISTINCT \n");
		sql.append("  TPAO.OUT_ID, \n");
		sql.append("  TPAO.OUT_NO,\n");
		sql.append("  TPAO.FROM_DLR_CODE,  \n");
		sql.append("  TDO.DEALER_SHORTNAME OUT_DEALER_SHORTNAME, \n");
		sql.append("  TPAO.TO_DLR_CODE, \n");
		sql.append("  TDI.DEALER_SHORTNAME IN_DEALER_SHORTNAME,   \n");
		sql.append("  CASE WHEN TPAO.ALLOCATE_TYPE='16991001' THEN '网内调拨' WHEN TPAO.ALLOCATE_TYPE='16991002' THEN '普通调拨' WHEN TPAO.ALLOCATE_TYPE='16991003' THEN '呆滞调拨'  ELSE '' END AS ALLOCATE_TYPE , \n");
		sql.append("  IFNULL(DATE_FORMAT(TPAO.OUT_DATE,'%Y-%m-%d'),'') AS OUT_DATE, \n");
		
		sql.append("  TPD.PART_NO, \n");
		sql.append("  TPD.PART_NAME, \n");
		sql.append("  TPD.STORAGE_CODE OUT_STORAGE_CODE, \n");
		sql.append("  CAST(ROUND(TPD.COST_PRICE,2) AS  DECIMAL(10, 2)) COST_PRICE, \n");
		sql.append("  CAST(ROUND(TPD.COST_AMOUNT,2) AS  DECIMAL(10, 2)) COST_AMOUNT, \n");
		sql.append("  TPD.OUT_QUANTITY, \n");
		sql.append("  CAST(ROUND(TPD.OUT_PRICE,2) AS DECIMAL(10, 2)) OUT_PRICE, \n");
		sql.append("  CAST(ROUND(TPD.OUT_AMOUNT,2) AS DECIMAL(10, 2)) OUT_AMOUNT, \n");
		sql.append("  CAST(ROUND(TPD.DNP_PRICE,2) AS DECIMAL(10, 2)) DNP_PRICE, \n");
		sql.append("  CAST(ROUND(TPD.MSRP_PRICE,2) AS DECIMAL(10, 2)) MSRP_PRICE, \n");
		sql.append("  CAST(ROUND(TPD.DNP_PRICE*TPD.OUT_QUANTITY,2) AS DECIMAL(10, 2)) DNP_AMOUNT, \n");
		sql.append("  CAST(ROUND(TPD.MSRP_PRICE*TPD.OUT_QUANTITY,2) AS DECIMAL(10, 2)) MSRP_AMOUNT, \n");
		
		sql.append("  TI.IN_NO, \n");
		sql.append("  TID.STORAGE_CODE IN_STORAGE_CODE, \n");
		sql.append("  TPD.UNIT_NAME,  \n");
		sql.append("  CAST(ROUND(TID.IN_PRICE,2) AS DECIMAL(10, 2)) IN_PRICE, \n");
		sql.append("  CAST(ROUND(TID.IN_AMOUNT,2) AS DECIMAL(10, 2)) IN_AMOUNT, \n");
		sql.append("  IFNULL(DATE_FORMAT(TI.IN_DATE,'%Y-%m-%d'),'') AS IN_DATE ,\n");
		sql.append("  CASE WHEN  (TI.IN_NO IS NULL OR TI.IN_NO='') THEN '已出库'   ELSE '已入库' END AS STATUS \n");
		sql.append("FROM TT_PT_ALLOT_OUT_dcs TPAO \n");
		sql.append("LEFT JOIN  TT_PT_ALLOT_OUT_DETAIL_dcs TPD ON TPD.OUT_ID=TPAO.OUT_ID \n");
		sql.append("LEFT JOIN  TM_DEALER TDO ON TDO.DEALER_CODE=TPAO.FROM_DLR_CODE \n");
		sql.append("LEFT JOIN  TM_DEALER TDI ON TDI.DEALER_CODE=TPAO.TO_DLR_CODE \n");
		sql.append("LEFT JOIN  TT_PT_ALLOT_IN_dcs TI ON TI.OUT_NO=TPAO.OUT_NO \n");
		sql.append("LEFT JOIN  TT_PT_ALLOT_IN_DETAIL_dcs TID ON TID.IN_ID=TI.IN_ID AND TID.PART_NO = TPD.PART_NO\n");
		sql.append("WHERE 1=1 \n");
		//调出经销商不为空
				if(!StringUtils.isNullOrEmpty(queryParams.get("fromDlrCode"))){ 
		        	  String[] dealerCodes=queryParams.get("fromDlrCode").split(",");
		    		  String code="";
		    		  for(int i=0;i<dealerCodes.length;i++){
		    			  code+=""+dealerCodes[i]+",";
		    		  }
		    		  code=code.substring(0,code.length()-1);
		    		  sql.append(" 					 AND TPAO.FROM_DLR_CODE IN (?) \n");
		        	  params.add(code);
		          }
				
				//调入经销商不为空
				if(!StringUtils.isNullOrEmpty(queryParams.get("toDlrCode"))){ 
		      	  String[] dealerCodes=queryParams.get("toDlrCode").split(",");
		  		  String code="";
		  		  for(int i=0;i<dealerCodes.length;i++){
		  			  code+=""+dealerCodes[i]+",";
		  		  }
		  		  code=code.substring(0,code.length()-1);
		  		  sql.append(" 					 AND TPAO.TO_DLR_CODE IN (?) \n");
		      	  params.add(code);
		        }
		//出库单号
		if(!StringUtils.isNullOrEmpty(queryParams.get("outNo"))){ 
			sql.append(" AND TPAO.OUT_NO LIKE ? \n");
       	    params.add("%"+queryParams.get("outNo")+"%");
         }
		//入库单号
		if(!StringUtils.isNullOrEmpty(queryParams.get("inNo"))){ 
			sql.append(" AND TI.IN_NO LIKE ? \n");
       	    params.add("%"+queryParams.get("inNo")+"%");
         }
		//出库日期
		if (!StringUtils.isNullOrEmpty(queryParams.get("dateType")) &&"93341001".equals(queryParams.get("dateType"))) {
			//开始日期不为空
			if(!StringUtils.isNullOrEmpty(queryParams.get("beginDate"))){ 
				sql.append(" AND  DATE_FORMAT(TPAO.OUT_DATE,'%Y-%m-%d') >='" + queryParams.get("beginDate") +"' \n");
	         }
			//结束日期不为空
			if(!StringUtils.isNullOrEmpty(queryParams.get("beginDate"))){ 
				sql.append(" AND  DATE_FORMAT(TPAO.OUT_DATE,'%Y-%m-%d') <='" + queryParams.get("endDate") +"' \n");
	         }
			
		}
		//入库日期
		if (!StringUtils.isNullOrEmpty(queryParams.get("dateType")) &&"93341002".equals(queryParams.get("dateType"))) {
			//开始日期不为空
			if(!StringUtils.isNullOrEmpty(queryParams.get("beginDate"))){ 
				sql.append(" AND  DATE_FORMAT(TI.IN_DATE,'%Y-%m-%d') >='" + queryParams.get("beginDate") +"' \n");
	         }
			//结束日期不为空
			if(!StringUtils.isNullOrEmpty(queryParams.get("beginDate"))){ 
				sql.append(" AND  DATE_FORMAT(TI.IN_DATE,'%Y-%m-%d') <='" + queryParams.get("endDate") +"' \n");
	         }
		}	
		//配件代码不为空
		if(!StringUtils.isNullOrEmpty(queryParams.get("partNo"))){ 
			sql.append(" AND UPPER(TPD.PART_NO) LIKE UPPER(?) \n");
       	    params.add("%"+queryParams.get("partNo")+"%");
         }
		
		//配件名称不为空
		if(!StringUtils.isNullOrEmpty(queryParams.get("partName"))){ 
			sql.append(" AND TPD.PART_NAME LIKE ? \n");
       	    params.add("%"+queryParams.get("partName")+"%");
         }
		return sql.toString();
	}

	/**
	 * 配件调拨单明细下载查询
	 * @param queryParams
	 * @return
	 */
	public List<Map> getdownAllotDetailInfo(Map<String, String> queryParams) {
		List<Object> params = new ArrayList<Object>();
		String sql = getAllotDetailInfoSQL(queryParams, params);
		return  OemDAOUtil.findAll(sql, params);
	}

	

	

}
