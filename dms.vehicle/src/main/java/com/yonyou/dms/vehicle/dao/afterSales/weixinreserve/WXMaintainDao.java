package com.yonyou.dms.vehicle.dao.afterSales.weixinreserve;

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
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * 微信保养套餐维护
 * @author Administrator
 *
 */
@Repository
public class WXMaintainDao extends OemBaseDAO{
	//微信保养套餐维护列表查询
	public PageInfoDto WXMaintainQuery(Map<String, String> queryParam) {
		   List<Object> params = new ArrayList<>();
		    String sql = getQuerySql(queryParam,params);
		    PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql,params);
		    System.out.println(pageInfoDto);
			return pageInfoDto;
	}
	
	private String getQuerySql(Map<String, String> queryParam, List<Object> params) {
		StringBuffer sql = new StringBuffer();
		sql.append("  select * from (  ");
		sql.append("   SELECT TWMP.PACKAGE_ID,TWMP.PACKAGE_CODE,TWMP.PACKAGE_NAME,TWMP.MAINTAIN_STARTMILEAGE,TWMP.MAINTAIN_ENDMILEAGE,	\n");
		sql.append("	DATE_FORMAT(TWMP.MAINTAIN_STARTDATE,'%Y-%m-%d') MAINTAIN_STARTDATE,DATE_FORMAT(TWMP.MAINTAIN_ENDDATE,'%Y-%m-%d') MAINTAIN_ENDDATE ,	\n" );
		sql.append("	TWMP.MODEL_YEAR,TWMP.SERIES_CODE,VW.SERIES_NAME,TWMP.ENGINE_DESC,TWMP.OILE_TYPE,TWMP.TOTAL_AMOUNT, \n" );
		
		sql.append("	  CASE WHEN TWMP.IS_DMS_SEND =0 then 70281001 else  70281002  end  IS_DMS_SEND ,  \n");
		sql.append("	  CASE WHEN TWMP.IS_WX_SEND =0 then 70281001 else  70281002  end  IS_WX_SEND,  \n");
		
		sql.append("	  CASE WHEN TWMP.P_TYPE =0 then 90111002 else  90111001  end  P_TYPE  \n");
		sql.append("	FROM TM_WX_MAINTAIN_PACKAGE_dcs TWMP	\n");
		sql.append("		LEFT JOIN ("+getVwMaterialSql()+") VW ON TWMP.SERIES_CODE = VW.SERIES_CODE \n");
		sql.append("     WHERE	TWMP.IS_DEL="+OemDictCodeConstants.IS_DEL_00+"	AND TWMP.M_TYPE=0	\n");
		//保养套餐代码
		 if (!StringUtils.isNullOrEmpty(queryParam.get("packageCode"))) {
				sql.append("AND   TWMP.PACKAGE_CODE like '%"+queryParam.get("packageCode")+"%'  \n");
			}
		 //保养套餐名称
		 if (!StringUtils.isNullOrEmpty(queryParam.get("packageName"))) {
				sql.append("AND   TWMP.PACKAGE_NAME like '%"+queryParam.get("packageName")+"%'  \n");
			}
		 
		//年款
		 if (!StringUtils.isNullOrEmpty(queryParam.get("modelYear"))) {
				sql.append("AND  TWMP.MODEL_YEAR= '"+queryParam.get("modelYear")+"'  \n");
			}
		 //车系
		 if (!StringUtils.isNullOrEmpty(queryParam.get("seriesCode"))) {
				sql.append("AND   TWMP.SERIES_CODE= '"+queryParam.get("seriesCode")+"'  \n");
			}
		 //有效日期
		 if (!StringUtils.isNullOrEmpty(queryParam.get("maintainStartdate"))) {
				sql.append("AND   TWMP.MAINTAIN_STARTDATE= '"+queryParam.get("maintainStartdate")+"'  \n");
			}
		 if (!StringUtils.isNullOrEmpty(queryParam.get("maintainEnddate"))) {
				sql.append("AND  TWMP.MAINTAIN_ENDDATE= '"+queryParam.get("maintainEnddate")+"'  \n");
			}
		 
		 //发动机排量
		 if (!StringUtils.isNullOrEmpty(queryParam.get("wxEngine"))) {
				sql.append("AND  TWMP.ENGINE_DESC = '"+queryParam.get("wxEngine")+"'  \n");
			}
		 
		 //套餐类型
		 if (!StringUtils.isNullOrEmpty(queryParam.get("pType"))) {
				sql.append("AND TWMP.P_TYPE = '"+queryParam.get("pType")+"'  \n");
			}
		 
		 //燃油类型
		 if (!StringUtils.isNullOrEmpty(queryParam.get("oileType"))) {
				sql.append("AND  TWMP.OILE_TYPE = '"+queryParam.get("oileType")+"'  \n");
			}
		 
		 
		sql.append("     	GROUP BY TWMP.PACKAGE_ID,TWMP.PACKAGE_CODE,TWMP.PACKAGE_NAME,TWMP.MAINTAIN_STARTMILEAGE,TWMP.MAINTAIN_ENDMILEAGE,	\n");
		sql.append("     		TWMP.MAINTAIN_STARTDATE,TWMP.MAINTAIN_ENDDATE,TWMP.MODEL_YEAR,TWMP.SERIES_CODE,VW.SERIES_NAME	\n");
		sql.append("     		,TWMP.ENGINE_DESC,TWMP.OILE_TYPE,TWMP.TOTAL_AMOUNT,TWMP.P_TYPE,TWMP.IS_DMS_SEND,TWMP.IS_WX_SEND	\n");
		sql.append("  ) con ");
		  
		 System.out.println(sql.toString());
			return sql.toString();
	}

	

	
	
	
	//年款查询
	public List<Map> getModelYearList() {
		StringBuffer sql = new StringBuffer();
		sql.append("select distinct MODEL_YEAR from TM_VHCL_MATERIAL_GROUP	\n");
		sql.append("	 where GROUP_LEVEL=4 and GROUP_CODE <> GROUP_NAME AND GROUP_NAME <> 'TBD' AND length(MODEL_YEAR) = 4\n");
		sql.append(" and STATUS="+OemDictCodeConstants.STATUS_ENABLE+"	\n");
		sql.append(" and (GROUP_TYPE="+OemDictCodeConstants.GROUP_TYPE_IMPORT+" or GROUP_TYPE is null)	\n");
		return OemDAOUtil.findAll(sql.toString(), null);
	}	
	
	//通过年款查询车系
	public List<Map> getSeriesList(String modelYear) {
		StringBuffer sql = new StringBuffer();
		sql.append("select distinct t2.GROUP_CODE SERIES_CODE,t2.GROUP_NAME SERIES_NAME\n");
		sql.append("  from TM_VHCL_MATERIAL_GROUP  t2\n");
		sql.append("  inner join TM_VHCL_MATERIAL_GROUP t3 on t3.PARENT_GROUP_ID = t2.GROUP_ID\n");
		sql.append("  inner join TM_VHCL_MATERIAL_GROUP t4 on t4.PARENT_GROUP_ID = t3.GROUP_ID\n");
		sql.append("  where t2.GROUP_LEVEL=2\n");
		if(!"".equals(modelYear)){
			sql.append(" AND t4.MODEL_YEAR = '"+modelYear+"' \n");
		}
		System.out.println(sql);
		return OemDAOUtil.findAll(sql.toString(), null);
	}
	
	//通过车系和年款查询排量信息
	public List<Map> getEngineList(String modelYear,String seriesCode) {
		StringBuffer sql = new StringBuffer();
		sql.append("select distinct wx.WX_ENGINE  from ("+getVwMaterialSql()+") wx  where GROUP_CODE <> GROUP_NAME AND GROUP_NAME <> 'TBD' AND length(MODEL_YEAR) = 4	\n");
		if(!"".equals(modelYear)){
			sql.append(" AND MODEL_YEAR = '"+modelYear+"' \n");
		}
		if(!"".equals(seriesCode)){
			sql.append(" AND SERIES_CODE = '"+seriesCode+"' \n");
		}
		return OemDAOUtil.findAll(sql.toString(), null);
	}		
	
	
	//工时信息列表查询
		public List<Map> getGongshi(Map<String, String> queryParam) throws ServiceBizException {
			StringBuffer sqlStr = new StringBuffer();
			sqlStr.append("SELECT TWML.ID,\n" );
			sqlStr.append("       TWML.FEE,\n" );
			sqlStr.append("       TWML.FRT,\n" );
			sqlStr.append("       TWML.LABOUR_CODE,\n" );
			sqlStr.append("       TWML.LABOUR_NAME,\n" );
			sqlStr.append("       TWML.DEAL_TYPE,\n" );
			sqlStr.append("       TWML.PACKAGE_ID,\n" );
			sqlStr.append("       TWML.PRICE\n" );
			sqlStr.append("  FROM Tm_Wx_Maintain_Labour_dcs TWML\n" );
			sqlStr.append(" WHERE TWML.LABOUR_CODE <> '' \n");
			//工时代码
			 if (!StringUtils.isNullOrEmpty(queryParam.get("LABOUR_CODE"))) {
					sqlStr.append("AND  t.labour_code like '%"+queryParam.get("LABOUR_CODE")+"%'  \n");
				}
			 //工时名称
			 if (!StringUtils.isNullOrEmpty(queryParam.get("LABOUR_DESC"))) {
					sqlStr.append("AND   t.labour_name like '%"+queryParam.get("LABOUR_DESC")+"%'  \n");
				}
			return OemDAOUtil.findAll(sqlStr.toString(), null);
		}
		
		//获得保养套餐零部件信息列表
		public List<Map> getLingJian(Map<String, String> queryParam) throws ServiceBizException {
			StringBuffer sqlStr = new StringBuffer();
			sqlStr.append("SELECT TWMP.ID,\n" );
			sqlStr.append("       TWMP.PACKAGE_ID,\n" );
			sqlStr.append("       TWMP.PART_CODE,\n" );
			sqlStr.append("       TWMP.PART_NAME,\n" );
			sqlStr.append("       TWMP.DEAL_TYPE,\n" );
			sqlStr.append("       TWMP.AMOUNT,\n" );
			sqlStr.append("       TWMP.PRICE,\n" );
			sqlStr.append("       TWMP.FEE,\n" );
			sqlStr.append("       TWMP.IS_MAIN,\n" );
			sqlStr.append("       TWMP.IS_DEL\n" );
			sqlStr.append("  FROM Tm_Wx_Maintain_Part_dcs TWMP\n" );
			sqlStr.append("     WHERE  TWMP.IS_DEL="+OemDictCodeConstants.IS_DEL_00+"      \n");
			//零部件代码
			 if (!StringUtils.isNullOrEmpty(queryParam.get("partCode"))) {
					sqlStr.append("AND  TWMP.PART_CODE like '%"+queryParam.get("partCode")+"%'  \n");
				}
			 //零部件名称
			 if (!StringUtils.isNullOrEmpty(queryParam.get("partName"))) {
					sqlStr.append("AND   TWMP.PART_NAME like '%"+queryParam.get("partName")+"%'  \n");
				}
			 System.out.println(sqlStr);
			return OemDAOUtil.findAll(sqlStr.toString(), null);
		}

		public List<Map> getBaoYang(Long id) {
			  LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT TWMP.PACKAGE_ID,TWMP.PACKAGE_CODE,TWMP.PACKAGE_NAME,TWMP.MAINTAIN_STARTMILEAGE,TWMP.MAINTAIN_ENDMILEAGE \n");
			sql.append("     ,DATE_FORMAT(TWMP.MAINTAIN_STARTDATE,'%Y-%m-%d') MAINTAIN_STARTDATE,DATE_FORMAT(TWMP.MAINTAIN_ENDDATE,'%Y-%m-%d') MAINTAIN_ENDDATE, \n");
			sql.append("	 TWMP.MODEL_YEAR,TWMP.SERIES_CODE,TWMP.ENGINE_DESC,TWMP.OILE_TYPE,TWMP.TOTAL_AMOUNT, CASE WHEN TWMP.P_TYPE =0 then 90111002 else  90111001  end  P_TYPE	\n");
			sql.append("		 FROM TM_WX_MAINTAIN_PACKAGE_dcs TWMP	\n");
			sql.append("     where TWMP.IS_DEL="+OemDictCodeConstants.IS_DEL_00+" \n");
			if(!"".equals(loginInfo.getCompanyId())&&!(null==loginInfo.getCompanyId())){                 //公司ID不为空
				sql.append("		AND TWMP.OEM_COMPANY_ID = "+loginInfo.getCompanyId()+" \n");
			}
			  if (!StringUtils.isNullOrEmpty(id)) {
					sql.append("  and TWMP.PACKAGE_ID = '"+id+"'  \n");
				}
			  return OemDAOUtil.findAll(sql.toString(),null); 
		}
		
		
		//得到项目列表的基本信息
		public PageInfoDto  getXiangMu(Map<String, String> queryParam,Long id) {
			   List<Object> params = new ArrayList<>();
			    String sql = getQuerySql2(queryParam,params,id);
			    PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql,params);
			    System.out.println(pageInfoDto);
				return pageInfoDto;
		}
		private String getQuerySql2(Map<String, String> queryParam, List<Object> params,Long id) {
		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append("SELECT TWML.ID,\n" );
		sqlStr.append("       TWML.FEE,\n" );
		sqlStr.append("       TWML.FRT,\n" );
		sqlStr.append("       TWML.LABOUR_CODE,\n" );
		sqlStr.append("       TWML.LABOUR_NAME,\n" );
		sqlStr.append("       TWML.DEAL_TYPE,\n" );
		sqlStr.append("       TWML.PACKAGE_ID,\n" );
		sqlStr.append("       TWML.PRICE\n" );
		sqlStr.append("  FROM Tm_Wx_Maintain_Labour_dcs TWML\n" );
		sqlStr.append("  	where  1=1 ");
		  if (!StringUtils.isNullOrEmpty(id)) {
				sqlStr.append("   and  TWML.PACKAGE_ID = '"+id+"'  and  TWML.IS_DEL="+OemDictCodeConstants.IS_DEL_00 +" \n");
			}
			 System.out.println(sqlStr.toString());
				return sqlStr.toString();
		}
		//得到零件列表的基本信息
		public PageInfoDto  getLingJian(Map<String, String> queryParam,Long id) {
			   List<Object> params = new ArrayList<>();
			    String sql = getQuerySql3(queryParam,params,id);
			    PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql,params);
			    System.out.println(pageInfoDto);
				return pageInfoDto;
		}
		private String getQuerySql3(Map<String, String> queryParam, List<Object> params,Long id) {
			StringBuffer sqlStr = new StringBuffer();
			sqlStr.append("SELECT TWMP.ID,\n" );
			sqlStr.append("       TWMP.PACKAGE_ID,\n" );
			sqlStr.append("       TWMP.PART_CODE,\n" );
			sqlStr.append("       TWMP.PART_NAME,\n" );
			sqlStr.append("       TWMP.DEAL_TYPE,\n" );
			sqlStr.append("       TWMP.AMOUNT,\n" );
			sqlStr.append("       TWMP.PRICE,\n" );
			sqlStr.append("       TWMP.FEE,\n" );
			sqlStr.append("       TWMP.IS_MAIN,\n" );
			sqlStr.append("       TWMP.IS_DEL\n" );
			sqlStr.append("  FROM Tm_Wx_Maintain_Part_dcs TWMP\n" );
			sqlStr.append("  	where  1=1 ");
			  if (!StringUtils.isNullOrEmpty(id)) {
					sqlStr.append("   and TWMP.PACKAGE_ID = '"+id+"'  and  TWMP.IS_DEL="+OemDictCodeConstants.IS_DEL_00 +" \n");
				}
			 System.out.println(sqlStr.toString());
				return sqlStr.toString();
		}
	

}
