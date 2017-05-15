package com.yonyou.dms.vehicle.dao.afterSales.preAuthorization;

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
 * 索赔预授权工时规则维护
 * @author Administrator
 *
 */
@Repository
public class TtWrForelabourRuleDao extends OemBaseDAO{
	/**
	 * 索赔预授权工时规则维护查询
	 */
	public PageInfoDto TtWrForelabourRuleQuery(Map<String, String> queryParam) {
		   List<Object> params = new ArrayList<>();
		    String sql = getQuerySql(queryParam,params);
		    PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql,params);
		    System.out.println(pageInfoDto);
			return pageInfoDto;
	}
	private String getQuerySql(Map<String, String> queryParam, List<Object> params) {
		StringBuffer sb = new StringBuffer();
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		List<Map>  list=getAuthLevel();//获得授权级别列表
		StringBuffer sql= new StringBuffer();
		sql.append(" select t1.RULE_ID,t1.OEM_COMPANY_ID,t1.MODEL_GROUP,t1.LABOUR_CODE, \n " );
		sql.append(" t1.AUTH_LEVEL,t2.LABOUR_NAME,t2.GROUP_CODE \n " );
		//根据授权级别进行拼列，动态列，按照“，”打开列显示
		for(int i = 0 ; i < list.size(); i++){
			Map tcpo = list.get(i);
			sql.append(" ,CASE WHEN instr(t1.AUTH_LEVEL,'"+tcpo.get("LEVEL_CODE")+"')>0 then 10571001 else 10571002 end \""+tcpo.get("LEVEL_CODE")+"\"");
		}
		sql.append(" from (select RULE_ID,OEM_COMPANY_ID,MODEL_GROUP,LABOUR_CODE,AUTH_LEVEL \n " );
		sql.append("  from TT_WR_FORELABOUR_RULE_dcs where IS_DEL=0) t1 \n " );
		sql.append(" inner join  \n " );
		sql.append(" (select LABOUR_CODE,LABOUR_NAME,group_id,GROUP_CODE from TT_WR_LABOUR_dcs  where IS_DEL=0) t2   \n " );
		sql.append(" on t1.LABOUR_CODE=t2.LABOUR_CODE and t1.model_group = t2.group_id \n " );
		sql.append(" and t1.OEM_COMPANY_ID=").append(loginInfo.getCompanyId()).append("\n");
		  if (!StringUtils.isNullOrEmpty(queryParam.get("LABOUR_CODE"))) {
				sql.append(" and t1.LABOUR_CODE like '%"+queryParam.get("LABOUR_CODE")+"%'  \n");
			}
		  if (!StringUtils.isNullOrEmpty(queryParam.get("LABOUR_DESC"))) {
				sql.append(" and t2.LABOUR_NAME like '%"+queryParam.get("LABOUR_DESC")+"%'  \n");
			}
		  
		  if (!StringUtils.isNullOrEmpty(queryParam.get("GROUP_ID"))) {
				sql.append(" and GROUP_ID = '"+queryParam.get("GROUP_ID")+"'  \n");
			}
		sql.append("group by t1.RULE_ID,t1.OEM_COMPANY_ID,t1.MODEL_GROUP,t1.LABOUR_CODE, \n");
		sql.append("t1.AUTH_LEVEL,t2.LABOUR_NAME,t2.group_code");
		//sql.append(" order by t1.LABOUR_CODE ");
		 System.out.println(sql.toString());
			return sql.toString();
	}
	
	//查询授权级别列表
	public List<Map> getAuthLevel() throws ServiceBizException {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT distinct LEVEL_TIER,LEVEL_CODE,LEVEL_NAME  from  TT_WR_AUTHLEVEL_dcs where OEM_COMPANY_ID= '"+loginInfo.getCompanyId()+"' ");
		return OemDAOUtil.findAll(sql.toString(), null);
	}
	
	
	/**
	 * 查询授权顺序
	 */
	 public List<Map> getLevel(){
	        StringBuffer sqlStr = new StringBuffer();
	      sqlStr.append("SELECT distinct L.LEVEL_NAME,L.LEVEL_CODE\n" );
			sqlStr.append("FROM TT_WR_AUTHLEVEL_dcs L LEFT JOIN TT_WR_AUTO_RULE_dcs M\n" );
			sqlStr.append("ON L.LEVEL_CODE=M.LEVEL_CODE AND L.IS_DEL=0\n" );
			sqlStr.append("AND M.IS_DEL=0  \n" );
			  System.out.println(sqlStr.toString());
	    return OemDAOUtil.findAll(sqlStr.toString(),null); 
	}
	 
	 public List<Map> getAllLevel(String labourCode){
	        StringBuffer sqlStr = new StringBuffer();
	        sqlStr.append("SELECT  tf.AUTH_LEVEL FROM TT_WR_LABOUR_dcs  tw \n");
	        sqlStr.append("        LEFT JOIN TT_WR_FORELABOUR_RULE_dcs tf  ON tw.labour_code=tf.labour_code \n");
			sqlStr.append("  where tw.LABOUR_CODE= '"+labourCode+"'  \n" );
			  System.out.println(sqlStr.toString());
	    return OemDAOUtil.findAll(sqlStr.toString(),null); 
	}
	 
	 /**
	  * 信息回显查询
	  */
		public List<Map> getJiBenXinXi(Long id){
			StringBuffer cusSql = new StringBuffer();
			cusSql.append("SELECT tw.LABOUR_NAME,tw.LABOUR_CODE,tf.AUTH_LEVEL FROM TT_WR_LABOUR_dcs  tw \n");
			cusSql.append("        LEFT JOIN TT_WR_FORELABOUR_RULE_dcs tf  ON tw.labour_code=tf.labour_code \n");
			cusSql.append("   WHERE tf.RULE_ID= ");
			cusSql.append(id);
			System.out.println(cusSql);
		   return OemDAOUtil.findAll(cusSql.toString(),null); 
		}
	

}
