package com.yonyou.dms.vehicle.dao.afterSales.partMgr;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * 配件限价
 * @author Administrator
 *
 */
@Repository
public class PartCeilingPriceDao extends OemBaseDAO{
	
	public PageInfoDto OtherMaintainQuery(Map<String, String> queryParam) {
		   List<Object> params = new ArrayList<>();
		    String sql = getQuerySql(queryParam,params);
		    PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql,params);
		    System.out.println(pageInfoDto);
			return pageInfoDto;
	}
	private String getQuerySql(Map<String, String> queryParam, List<Object> params) {
		StringBuffer pasql = new StringBuffer();
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		  pasql.append("	SELECT  TPCP.ID, TPCP.CEILING_PRICE_CODE, TPCP.CEILING_PRICE_NAME,TPCP.CEILING_PRICE_SCOPE,  \n  ");
	        pasql.append("		TPCP.REMARK, TPCP.IS_DOWN, TPCP.CREATE_BY, TPCP.CREATE_DATE,TPCP.IS_ERR_NUM,TPCP.IS_SUC_NUM,TPCP.SEND_DATE,TPCP.PART_FLAG \n  ");
	        pasql.append("		 	FROM TT_PART_CEILING_PRICE_dcs TPCP\n  ");
	        pasql.append("		 		WHERE TPCP.CREATE_BY = '"+loginInfo.getUserId()+"'\n  ");
	    	if (!StringUtils.isNullOrEmpty(queryParam.get("ceilingPriceCode"))) {
	       		pasql.append(" 					AND tpcp.CEILING_PRICE_CODE = '" +queryParam.get("ceilingPriceCode") + "' \n");
	        }
	    	if (!StringUtils.isNullOrEmpty(queryParam.get("ceilingPriceName"))) {
	        	pasql.append(" 					AND tpcp.CEILING_PRICE_NAME LIKE '%" + queryParam.get("ceilingPriceName") + "%' \n");
	        }
	    	if (!StringUtils.isNullOrEmpty(queryParam.get("ceilingPriceScope"))) {
	        	pasql.append(" 					AND tpcp.CEILING_PRICE_SCOPE = '" + queryParam.get("ceilingPriceScope")+ "' \n");
	        }
	    	if (!StringUtils.isNullOrEmpty(queryParam.get("startDate"))||!StringUtils.isNullOrEmpty(queryParam.get("endDate"))) {
	        	pasql.append(" 					AND to_char(tpcp.CREATE_DATE,'yyyy-MM-dd') between '" + queryParam.get("startDate") + "' and '"+ queryParam.get("endDate") + "'\n");
	        }
	    	if (!StringUtils.isNullOrEmpty(queryParam.get("isDown"))) {
	            	pasql.append(" 					AND tpcp.IS_DOWN =  '" +queryParam.get("isDown")+ "'	\n");
	        }
		 System.out.println(pasql.toString());
			return pasql.toString();
	}
	
	/**
	 * 查询明细~配件限价列表
	 * @return
	 * @throws ServiceBizException
	 */
	public PageInfoDto PartCeilingPriceQuery(Map<String, String> queryParam,String priceCode,String priceScope) {
		   List<Object> params = new ArrayList<>();
		    String sql = getQuerySql2(queryParam,params,priceCode,priceScope);
		    PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql,params);
		    System.out.println(pageInfoDto);
			return pageInfoDto;
	}
	private String getQuerySql2(Map<String, String> queryParam, List<Object> params,String priceCode,String priceScope) {
		StringBuffer sql = new StringBuffer();
		sql.append("	SELECT TPCP.DEALER_CODE,TPCP.PART_CODE,TPCP.PART_NAME,TPCP.PART_PRICE, \n");
		sql.append("		TPCP.CEILING_PRICE_RATIO,TPCP.AFTER_CEILING_PRICE  \n");
		sql.append("			FROM TT_PART_CEILING_PRICE_DETAIL_dcs TPCP \n");
		sql.append("				WHERE \n");
		sql.append("				TPCP.CEILING_PRICE_CODE = '"+priceCode+"' \n");
		if (!StringUtils.isNullOrEmpty(queryParam.get("ceilingPriceScope"))) {
			sql.append("				AND TPCP.CEILING_PRICE_SCOPE = '"+priceScope +"' \n");
		}
		 System.out.println(sql.toString());
			return sql.toString();
	}
	
	
	

}
