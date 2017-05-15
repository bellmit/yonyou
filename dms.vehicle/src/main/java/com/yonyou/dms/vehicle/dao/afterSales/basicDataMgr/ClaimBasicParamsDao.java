package com.yonyou.dms.vehicle.dao.afterSales.basicDataMgr;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * 索赔基本参数设定
 * @author Administrator
 *
 */
@Repository
public class ClaimBasicParamsDao extends OemBaseDAO{
	/**
	 * 索赔基本参数设定查询
	 */
	public PageInfoDto  BasicParamsQuery(Map<String, String> queryParam) {
		   List<Object> params = new ArrayList<>();
		    String sql = getQuerySql(queryParam,params);
		    PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql,params);
		    System.out.println(pageInfoDto);
			return pageInfoDto;
	}
	private String getQuerySql(Map<String, String> queryParam, List<Object> params) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT  a.PARA_ID,  b.DEALER_CODE,b.DEALER_SHORTNAME,\n" );
		sql.append("       a.LABOUR_PRICE,\n" );
		sql.append("       a.OEM_COMPANY_ID,\n" );
		sql.append("       a.TAX_RATE,\n" );
		sql.append("       a.PART_MANGEFEE,\n" );
		sql.append("       a.VALID_DAYS\n" );
		sql.append("  FROM TT_WR_BASIC_PARA_DCS a,TM_DEALER b \n" );
		sql.append("  	where  a.is_del = 0 ");
		sql.append("  	and    b.DEALER_ID=a.DEALER_ID  ");
		  if (!StringUtils.isNullOrEmpty(queryParam.get("dealerCode"))) {
				sql.append("AND b.DEALER_CODE = "+queryParam.get("dealerCode")+"  \n");
			}
		 System.out.println(sql.toString());
			return sql.toString();
	}
	/**
	 * 回显
	 * @param id
	 * @return
	 */
	public List<Map> getShuju(Long id){
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT  a.PARA_ID,  b.DEALER_CODE,b.DEALER_SHORTNAME,\n" );
		sql.append("       a.LABOUR_PRICE,\n" );
		sql.append("       a.OEM_COMPANY_ID,\n" );
		sql.append("       a.TAX_RATE,\n" );
		sql.append("       a.PART_MANGEFEE,\n" );
		sql.append("       a.VALID_DAYS\n" );
		sql.append("  FROM TT_WR_BASIC_PARA_DCS a,TM_DEALER b  where 1=1 \n" );
		sql.append("  	and    b.DEALER_ID=a.DEALER_ID  ");
		  if (!StringUtils.isNullOrEmpty(id)) {
				sql.append("  AND a.PARA_ID = "+id+"  \n");
			}
		  return OemDAOUtil.findAll(sql.toString(),null); 
		    
	}
	
	/**
	 * 查询所有索赔基本参数设定信息
	 */
	
	     public List<Map> getAll(){
		        StringBuffer sql = new StringBuffer();
				sql.append("  	SELECT  a.PARA_ID FROM TT_WR_BASIC_PARA_DCS a");
				sql.append("  WHERE  is_down= "+OemDictCodeConstants.IS_DOWN_00+"  \n");
				sql.append("  and  is_del= "+OemDictCodeConstants.IS_DEL_00+"  \n");
		    return OemDAOUtil.findAll(sql.toString(),null); 
	}
	

}
