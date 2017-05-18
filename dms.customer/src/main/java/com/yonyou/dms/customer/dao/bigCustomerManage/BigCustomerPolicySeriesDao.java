package com.yonyou.dms.customer.dao.bigCustomerManage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * 政策车系定义
 * @author Administrator
 *
 */
@Repository
public class BigCustomerPolicySeriesDao extends OemBaseDAO{
	/**
	 * 查询政策车系定义信息
	 */
	public PageInfoDto policySeriesQuery(Map<String, String> queryParam) {
		   List<Object> params = new ArrayList<>();
		    String sql = getQuerySql(queryParam,params);
		    PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql,params);
			return pageInfoDto;
	}
	private String getQuerySql(Map<String, String> queryParam, List<Object> params) {
		StringBuilder sql = new StringBuilder();
		sql.append("  SELECT  * from ( \n");
		sql.append("SELECT  \n");
		sql.append("   TGCPS.BIG_CUSTOMER_POLICY_ID,\n");
		sql.append("   TGCPS.PS_TYPE, \n");
		sql.append("   TGCPS.BRAND_CODE,\n");
		sql.append("   VM.BRAND_NAME,\n");
		sql.append("   TGCPS.SERIES_CODE, \n");
		sql.append("   VM.SERIES_NAME, \n");
		sql.append("   TGCPS.STATUS, \n");
		sql.append("   CASE TGCPS.IS_SCAN WHEN TGCPS.IS_SCAN=0 THEN '未下发' ELSE'已下发' END  as  IS_SCAN  \n");
		sql.append("  FROM TT_BIG_CUSTOMER_POLICY_SERIES TGCPS,\n");
		sql.append("  ("+getVwMaterialSql()+") VM \n");
		sql.append("  WHERE 1=1 \n");
		sql.append("  AND TGCPS.BRAND_CODE = VM.BRAND_CODE\n");
		sql.append("  AND TGCPS.SERIES_CODE = VM.SERIES_CODE\n");
		sql.append("  AND TGCPS.IS_DELETE = 0 \n");
	    //政策类别
		  if (!StringUtils.isNullOrEmpty(queryParam.get("type"))) {
	            sql.append("  AND TGCPS.PS_TYPE = ? ");
	            params.add( queryParam.get("type"));
	        }
		  //品牌
	        if (!StringUtils.isNullOrEmpty(queryParam.get("brandCode"))) {
	            sql.append("  AND TGCPS.BRAND_CODE  =  ?   ");
	            params.add( queryParam.get("brandCode"));
	        }
	        //车系
	        if (!StringUtils.isNullOrEmpty(queryParam.get("seriesCode"))) {
	            sql.append("  AND TGCPS.SERIES_CODE  =  ?  ");
	            params.add( queryParam.get("seriesCode"));
	        }
	        //状态
	        if (!StringUtils.isNullOrEmpty(queryParam.get("status"))) {
	            sql.append("  AND TGCPS.STATUS  =  ?  ");
	            params.add( queryParam.get("status"));
	        }
	        sql.append("  GROUP BY \n");
			sql.append("   TGCPS.BIG_CUSTOMER_POLICY_ID,\n");
			sql.append("   TGCPS.PS_TYPE, \n");
			sql.append("   TGCPS.BRAND_CODE,\n");
			sql.append("   VM.BRAND_NAME,\n");
			sql.append("   TGCPS.SERIES_CODE, \n");
			sql.append("   VM.SERIES_NAME, \n");
			sql.append("   TGCPS.STATUS, \n");
			sql.append("   TGCPS.IS_SCAN \n");
		     sql.append("   )  t  \n");
		System.out.println(sql);
		return sql.toString();
	}
	
/**
 * 查询所有品牌代码
 */
	public List<Map> queryBrand() {
		StringBuilder sql = new StringBuilder(" select DEALER_CODE,BRAND_CODE,BRAND_NAME,OEM_TAG  from tm_brand ");
		List<Object> params = new ArrayList<Object>();
		//执行查询操作
		List<Map> result=OemDAOUtil.findAll(sql.toString(), params);
		return result;
	}
	
	/**
	 * 通过品牌代码查询车系代码
	 */
	public List<Map> queryChexi(Map<String, String> queryParam, String brandCode) {
		 List<Object> params = new ArrayList<>();
		StringBuffer sql = new StringBuffer();
		sql.append(" \n");
		sql.append("SELECT DISTINCT \n");
		sql.append("       S.GROUP_ID, -- 车系ID \n");
		sql.append("       S.GROUP_CODE, -- 车系代码 \n");
		sql.append("       S.GROUP_NAME, -- 车系名称 \n");
		sql.append("       S.PARENT_GROUP_ID -- 父级ID \n");
		sql.append("  FROM TM_VHCL_MATERIAL_GROUP  S  \n");
		sql.append("  LEFT JOIN  TT_BIG_CUSTOMER_POLICY_SERIES  t  \n");
		sql.append("  ON  s.group_code=t.series_code  \n");
		sql.append("  where 1=1  \n");
			if(!StringUtils.isNullOrEmpty(brandCode)){
				sql.append("   and t.brand_code  = ?   \n");
				params.add(brandCode)	;
	}
			System.out.println(sql.toString());
		return OemDAOUtil.findAll(sql.toString(), params);
	    }
	
	
	

}
