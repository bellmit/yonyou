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
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * 索赔预授权反馈
 * @author Administrator
 *
 */
@Repository
public class PerClaimFeedBackDao extends OemBaseDAO{
	/**
	 * 查询索赔反馈信息
	 */
	public PageInfoDto PerClaimFeedBack(Map<String, String> queryParam) {
		   List<Object> params = new ArrayList<>();
		    String sql = getQuerySql(queryParam,params);
		    PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql,params);
		    System.out.println(pageInfoDto);
			return pageInfoDto;
	}
	private String getQuerySql(Map<String, String> queryParam, List<Object> params) {
		StringBuffer sql = new StringBuffer();
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		sql.append("SELECT a.ID,a.VIN,a.FORE_NO,a.REPAIR_NO,a.APPLY_DATE,a.APPLY_MAN \n" );
		sql.append("  FROM TT_WR_FOREAPPROVAL_dcs a\n" );
		sql.append(" WHERE a.IS_DEL = "+OemDictCodeConstants.IS_DEL_00+" \n" );
        //sql.append(" and a.CURRENT_AUTH_CODE='"+queryParam.get("levelCode")+"' \n");
      	sql.append(" and a.STATUS = "+OemDictCodeConstants.FORE_APPLOVAL_STATUS_02+" \n" );
      //申请日期
		  if (!StringUtils.isNullOrEmpty(queryParam.get("startDate"))) {
				sql.append("AND a.APPLY_DATE>= '"+queryParam.get("startDate")+"'  \n");
			}
		  if (!StringUtils.isNullOrEmpty(queryParam.get("endDate"))) {
				sql.append("AND a.APPLY_DATE<= '"+queryParam.get("endDate")+"'  \n");
			}
		  if (!StringUtils.isNullOrEmpty(queryParam.get("foreNo"))) {
				sql.append("AND a.FORE_NO  like '%"+queryParam.get("foreNo")+"%'  \n");
			}
		  if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
				sql.append("AND a.VIN  like '%"+queryParam.get("vin")+"%'  \n");
			}
		if(!loginInfo.getDutyType().toString().equals(OemDictCodeConstants.DUTY_TYPE_DEPT.toString()) && !loginInfo.getDutyType().toString().equals(OemDictCodeConstants.DUTY_TYPE_COMPANY.toString())){
			sql.append("      AND  a.DEALER_ID in ("+OemBaseDAO.getDealersByArea(loginInfo.getOrgId().toString())+")\n");	
	       }
		 System.out.println(sql.toString());
			return sql.toString();
	}
	
	/**
	 * 基本信息查询
	 */
	public List<Map> getJiBenXinXi(Long id){
		StringBuffer cusSql = new StringBuffer();
		cusSql.append("select a.ID,a.DEALER_CODE,a.DEALER_NAME, a.FORE_NO, a.REPAIR_NO,a.START_DATE,a.INTO_DATE,a.INTO_MILEAGE,a.VIN,a.PLATE_NO,a.ENGINE_NO, \n");//客户类型
		cusSql.append("        a.BRAND,a.SERIES,a.MODEL,a.APPLY_DATE,a.APPLY_MAN,a.LINK_TEL,a.STATUS from TT_WR_FOREAPPROVAL_dcs a  WHERE 1=1  and a.is_del=0 \n");//客户姓名
		cusSql.append("   and id= ");
		cusSql.append(id);
		System.out.println(cusSql);
	   return OemDAOUtil.findAll(cusSql.toString(),null); 
	}
		/**
		 * 项目信息
		 * @param queryParam
		 * @param id
		 * @return
		 */
	public PageInfoDto XiangMuXinXi(Map<String, String> queryParam,Long id) {
		   List<Object> params = new ArrayList<>();
		    String sql = getQuerySql2(queryParam,params,id);
		    PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql,params);
		    System.out.println(pageInfoDto);
			return pageInfoDto;
	}
	private String getQuerySql2(Map<String, String> queryParam, List<Object> params,Long id) {
		StringBuffer sql = new StringBuffer();
		sql.append("  select B.FID,B.ITEM_ID,B.ITEM_CODE,B.ITEM_TYPE,B.ITEM_DESC,B.CHECK_REMARK from TT_WR_FOREAPPROVAL_dcs a,TT_WR_FOREAPPROVALITEM_dcs b where A.ID=B.FID \n");
		sql.append(" and B.IS_DEL = "+OemDictCodeConstants.IS_DEL_00 +" and B.FID ="+id);
		 System.out.println(sql.toString());
			return sql.toString();
	}
	
	/**
	 * 通过id查询附件信息
	 */
	public List<Map> getFuJian(Long id){
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT A.FILENAME FROM FS_FILEUPLOAD A ");
		sql.append(" WHERE 1=1 " );
		sql.append(" AND A.YWZJ='"+id+"'");
		System.out.println(sql);
	   return OemDAOUtil.findAll(sql.toString(),null); 
	}
	
	/**
	 * 通过id查询反馈信息
	 */
	public List<Map> getFanKui(Long id){
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT AMOUNT,FEEDBACK_NO,REMARK FROM TT_WR_FOREAPPROVAL_FEEDBACK_dcs ");
		sql.append(" WHERE 1=1 " );
		sql.append(" AND FORE_ID='"+id+"'");
		System.out.println(sql);
	   return OemDAOUtil.findAll(sql.toString(),null); 
	}
}
