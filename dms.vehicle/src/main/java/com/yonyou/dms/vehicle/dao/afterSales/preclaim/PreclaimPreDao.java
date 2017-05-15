package com.yonyou.dms.vehicle.dao.afterSales.preclaim;

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
import com.yonyou.dms.function.utils.common.GetVinUtil;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * 索赔预授权维护
 * @author Administrator
 *
 */
@Repository
public class PreclaimPreDao extends OemBaseDAO{

	//索赔预授权维护查询
	public PageInfoDto PreclaimPreQuery(Map<String, String> queryParam) {
		   List<Object> params = new ArrayList<>();
		    String sql = getQuerySql(queryParam,params);
		    PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql,params);
		    System.out.println(pageInfoDto);
			return pageInfoDto;
	}
	private String getQuerySql(Map<String, String> queryParam, List<Object> params) {
		StringBuffer sql = new StringBuffer();
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		sql.append(" select twf.ID, twf.VIN, twf.FORE_NO, twf.REPAIR_NO, twf.APPLY_DATE, twf.APPLY_MAN, \n" );
		sql.append(" twf.STATUS");
		sql.append(" from TT_WR_FOREAPPROVAL_dcs twf" );
		sql.append(" where twf.OEM_COMPANY_ID = "+loginInfo.getOemCompanyId()+" \n" );//添加oem_company_id条件
		sql.append(" and twf.dealer_id = "+loginInfo.getDealerId()+" and twf.IS_DEL ="+OemDictCodeConstants.IS_DEL_00+" \n" );
		sql.append(" and twf.STATUS not in('"+OemDictCodeConstants.FORE_APPLOVAL_STATUS_02+"','"+OemDictCodeConstants.FORE_APPLOVAL_STATUS_04+"','"+OemDictCodeConstants.FORE_APPLOVAL_STATUS_05+"')");
		if (!StringUtils.isNullOrEmpty(queryParam.get("beginTime"))) {//开始时间不为空
			sql.append(" and twf.APPLY_DATE >= DATE_FORMAT('"+queryParam.get("beginTime")+"', '%Y-%m-%d')\n");
		}		
		if (!StringUtils.isNullOrEmpty(queryParam.get("endTiem"))) {//结束时间不为空
			sql.append(" and twf.APPLY_DATE <= DATE_FORMAT('"+queryParam.get("endTiem")+"', '%Y-%m-%d')\n");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("orderId"))) {//预授权单号不为空
			sql.append(" and twf.FORE_NO like '%"+queryParam.get("orderId")+"%'\n");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("roNo"))) {//工单号不为空
			sql.append(" and twf.REPAIR_NO like '%"+queryParam.get("roNo")+"%'\n");
		}		
		if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {//VIN不为空
			sql.append(GetVinUtil.getVins(queryParam.get("vin"),"twf"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("preAuthStatus"))) {//提报状态不为空
			sql.append(" and twf.STATUS = '"+queryParam.get("preAuthStatus")+"'\n");
		}		
		 System.out.println(sql.toString());
			return sql.toString();
	}
	
	//查询所有项目信息
	public List<Map> getGongshi(Map<String, String> queryParam) {
			StringBuffer sqlStr = new StringBuffer();
			sqlStr.append("SELECT TWML.ID,\n" );
			sqlStr.append("       TWML.FID,\n" );
			sqlStr.append("       TWML.ITEM_ID,\n" );
			sqlStr.append("       TWML.ITEM_TYPE,\n" );
			sqlStr.append("       TWML.ITEM_CODE,\n" );
			sqlStr.append("       TWML.ITEM_DESC,TWML.CHECK_REMARK\n" );
			sqlStr.append("  FROM TT_WR_FOREAPPROVALITEM_dcs TWML\n" );
			sqlStr.append(" WHERE  1=1  \n");
			 if (!StringUtils.isNullOrEmpty(queryParam.get("itemType"))) {
					sqlStr.append("AND  TWML.ITEM_TYPE = '"+queryParam.get("itemType")+"'  \n");
				}
			//项目代码
			 if (!StringUtils.isNullOrEmpty(queryParam.get("ITEM_CODE"))) {
					sqlStr.append("AND  TWML.ITEM_CODE like '%"+queryParam.get("ITEM_CODE")+"%'  \n");
				}
			 //项目名称
			 if (!StringUtils.isNullOrEmpty(queryParam.get("ITEM_DESC"))) {
					sqlStr.append("AND   TWML.ITEM_DESC like '%"+queryParam.get("ITEM_DESC")+"%'  \n");
				}
			return OemDAOUtil.findAll(sqlStr.toString(), null);
		}
	
	//通过id查询附件信息
	public PageInfoDto getFuJian(Map<String, String> queryParam,Long id) {
		   List<Object> params = new ArrayList<>();
		    String sql = getQuerySql2(queryParam,params,id);
		    PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql,params);
		    System.out.println(pageInfoDto);
			return pageInfoDto;
	}
	private String getQuerySql2(Map<String, String> queryParam, List<Object> params,Long id) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT A.FILENAME,A.FILEURL,A.FILEID,A.FJID FROM FS_FILEUPLOAD A ");
		sql.append(" WHERE 1=1 " );
		sql.append(" AND A.YWZJ='"+id+"'");
		 System.out.println(sql.toString());
			return sql.toString();
	}
	//通过dealer_code获取该公司基本信息
	public List<Map> getPreclaimPre() {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		StringBuffer cusSql = new StringBuffer();
		cusSql.append("SELECT OEM_COMPANY_ID,DEALER_ID,DEALER_CODE,DEALER_NAME,LINK_TEL,APPLY_MAN FROM  TT_WR_FOREAPPROVAL_dcs \n");
		cusSql.append("   WHERE dealer_id='"+loginInfo.getDealerId()+"' ");
		System.out.println(cusSql);
	   return OemDAOUtil.findAll(cusSql.toString(),null); 
	}



}
