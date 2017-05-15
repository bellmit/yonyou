package com.yonyou.dms.vehicle.dao.afterSales.basicDataMgr;

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
 * 保养套餐维护
 * @author Administrator
 *
 */
@Repository
public class MaintainDao extends OemBaseDAO{
	
	//查询所有车型
	
	public List<Map> getAllCheXing(Map<String, String> queryParams) throws ServiceBizException {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT DISTINCT P.GROUP_ID , P.GROUP_NAME  FROM TM_VHCL_MATERIAL_GROUP P WHERE P.GROUP_LEVEL = 3 ");
		return OemDAOUtil.findAll(sql.toString(), null);
	}
	
	//查询保养套餐
	
	public PageInfoDto  MaintainQuery(Map<String, String> queryParam) {
		   List<Object> params = new ArrayList<>();
		    String sql = getQuerySql(queryParam,params);
		    PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql,params);
		    System.out.println(pageInfoDto);
			return pageInfoDto;
	}
	private String getQuerySql(Map<String, String> queryParam, List<Object> params) {
		StringBuffer sql = new StringBuffer();
	LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		sql.append("	 SELECT TWMP.PACKAGE_ID,TWMP.PACKAGE_CODE,TWMP.PACKAGE_NAME,TWMP.MAINTAIN_ENDDAY,TWMP.MAINTAIN_ENDMILEAGE,\n");
		sql.append("       DATE_FORMAT(TWMP.MAINTAIN_STARTDATE,'%Y-%m-%d') MAINTAIN_STARTDATE,\n" );
		sql.append("       DATE_FORMAT(TWMP.MAINTAIN_ENDDATE,'%Y-%m-%d') MAINTAIN_ENDDATE\n" );
		sql.append("     ,TWMP.MAINTAIN_STARTDAY,TWMP.MAINTAIN_STARTMILEAGE,TWMP.MODEL_ID,P.GROUP_NAME  FROM TT_WR_MAINTAIN_PACKAGE_dcs TWMP,TM_VHCL_MATERIAL_GROUP P \n");
		sql.append("     where  TWMP.IS_DEL="+OemDictCodeConstants.IS_DEL_00+"  AND TWMP.MODEL_ID = P.GROUP_ID AND P.GROUP_LEVEL =3 \n");
		if(!"".equals(loginInfo.getCompanyId())&&!(null==loginInfo.getCompanyId())){//公司ID不为空
			sql.append(" and TWMP.OEM_COMPANY_ID = '"+loginInfo.getCompanyId()+"' \n");
		}
		 if (!StringUtils.isNullOrEmpty(queryParam.get("packageCode"))) {
				sql.append("AND   TWMP.PACKAGE_CODE like '%"+queryParam.get("packageCode")+"%'  \n");
			}
		 if (!StringUtils.isNullOrEmpty(queryParam.get("packageName"))) {
				sql.append("AND   TWMP.PACKAGE_NAME like '%"+queryParam.get("packageName")+"%'  \n");
			}
		 
		//保养里程数
		 if (!StringUtils.isNullOrEmpty(queryParam.get("maintainStartmileage"))) {
				sql.append("AND  TWMP.MAINTAIN_STARTMILEAGE= '"+queryParam.get("maintainStartmileage")+"'  \n");
			}
		 if (!StringUtils.isNullOrEmpty(queryParam.get("maintainEndmileage"))) {
				sql.append("AND   TWMP.MAINTAIN_ENDMILEAGE= '"+queryParam.get("maintainEndmileage")+"'  \n");
			}
		 //开始时间结束时间
		 if (!StringUtils.isNullOrEmpty(queryParam.get("maintainStartdate"))) {
				sql.append("AND   TWMP.MAINTAIN_STARTDATE= '"+queryParam.get("maintainStartdate")+"'  \n");
			}
		 if (!StringUtils.isNullOrEmpty(queryParam.get("maintainEnddate"))) {
				sql.append("AND  TWMP.MAINTAIN_ENDDATE= '"+queryParam.get("maintainEnddate")+"'  \n");
			}
		 
		 //车型
		 if (!StringUtils.isNullOrEmpty(queryParam.get("groupName"))) {
				sql.append("AND  P.GROUP_NAME = '"+queryParam.get("groupName")+"'  \n");
			}
		 System.out.println(sql.toString());
			return sql.toString();
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
		sqlStr.append("  FROM TT_WR_MAINTAIN_LABOUR_dcs TWML\n" );
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
		sqlStr.append("       TWMP.IS_DEL,TWMP.PART_NO\n" );
		sqlStr.append("  FROM TT_WR_MAINTAIN_PART_dcs TWMP\n" );
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

	//获取保养套餐表的基本信息
	public List<Map> getBaoYang(Long id) {
		StringBuffer sql = new StringBuffer();
		sql.append("	 SELECT TWMP.PACKAGE_ID,TWMP.PACKAGE_CODE,TWMP.PACKAGE_NAME,TWMP.MAINTAIN_ENDDAY,TWMP.MAINTAIN_ENDMILEAGE,\n");
		sql.append("       DATE_FORMAT(TWMP.MAINTAIN_STARTDATE,'%Y-%m-%d') MAINTAIN_STARTDATE,\n" );
		sql.append("       DATE_FORMAT(TWMP.MAINTAIN_ENDDATE,'%Y-%m-%d') MAINTAIN_ENDDATE\n" );
		sql.append("     ,TWMP.MAINTAIN_STARTDAY,TWMP.MAINTAIN_STARTMILEAGE,TWMP.MODEL_ID  FROM TT_WR_MAINTAIN_PACKAGE_dcs TWMP  \n");
		sql.append("  	where  1=1 ");
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
	sqlStr.append("  FROM TT_WR_MAINTAIN_LABOUR_dcs TWML\n" );
	sqlStr.append("  	where  1=1 ");
	  if (!StringUtils.isNullOrEmpty(id)) {
			sqlStr.append("   and  TWML.PACKAGE_ID = '"+id+"'  and  TWML.IS_DEL="+OemDictCodeConstants.IS_DEL_00 +" \n");
		}
		 System.out.println(sqlStr.toString());
			return sqlStr.toString();
	}
	//得到零件列表的基本信息
	public PageInfoDto  getTaoCan(Map<String, String> queryParam,Long id) {
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
		sqlStr.append("       TWMP.IS_DEL,TWMP.PART_NO\n" );
		sqlStr.append("  FROM TT_WR_MAINTAIN_PART_dcs TWMP\n" );
		sqlStr.append("  	where  1=1 ");
		  if (!StringUtils.isNullOrEmpty(id)) {
				sqlStr.append("   and TWMP.PACKAGE_ID = '"+id+"'  and  TWMP.IS_DEL="+OemDictCodeConstants.IS_DEL_00 +" \n");
			}
		 System.out.println(sqlStr.toString());
			return sqlStr.toString();
	}

}
