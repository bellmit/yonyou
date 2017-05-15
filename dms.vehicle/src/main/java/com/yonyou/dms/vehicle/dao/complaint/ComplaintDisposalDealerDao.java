package com.yonyou.dms.vehicle.dao.complaint;

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
 * 客户投诉处理Dao
 * @author ZhaoZ
 * 
 */
@Repository
public class ComplaintDisposalDealerDao extends OemBaseDAO{

	/**
	 * 客户投诉处理(总部)查询
	 * @param queryParams
	 * @return
	 */
	public PageInfoDto complaintDisposalOemList(Map<String, String> queryParams) {
		List<Object> params = new ArrayList<Object>();
		String sql = getcomplaintDisposalOemListSql(queryParams,params);
		return OemDAOUtil.pageQuery(sql, params);
	}

	/**
	 * 客户投诉处理(总部)查询SQL
	 * @param queryParams
	 * @return
	 */
	private String getcomplaintDisposalOemListSql(Map<String, String> queryParams,List<Object> params) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		Long dealerId = loginInfo.getDealerId();
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT * FROM (");
		sql.append(" SELECT TCC.COMP_ID,");
		sql.append("       TCC.COMP_CODE,");
		sql.append("       TCCC.LINK_MAN,");
		/*sql.append("       TCCV.PURCHASED_DEALER,");
		sql.append("       (SELECT DEALER_CODE");
    	sql.append("          FROM TM_DEALER");
  		sql.append("         WHERE DEALER_ID = TCCV.PURCHASED_DEALER)");
 		sql.append("          DEALER_NAME,");
  		sql.append("       TCCV.MODEL_CODE,");
		sql.append("       (SELECT GROUP_NAME");
    	sql.append("          FROM TM_VHCL_MATERIAL_GROUP");
  		sql.append("         WHERE GROUP_CODE = TCCV.MODEL_CODE)");
 		sql.append("          GROUP_NAME,");*/
		sql.append("       TCCC.TEL,");
		sql.append("	   TCC.COMP_NATURE,");
		sql.append("       TCC.COMP_LEVEL,");
		sql.append("       TCC.COMP_BTYPE,");
		sql.append("       (SELECT code_desc FROM TC_CODE_dcs WHERE code_id = TCC.COMP_STYPE) COMP_STYPE,");
		sql.append("        date_format(TCC.COMP_DATE,'%Y-%m-%d')  COMP_DATE,");
		sql.append("       date_format(TCC.ALLOT_DATE,'%Y-%m-%d') ALLOT_DATE,");
  		sql.append("       (SELECT CODE_DESC FROM TC_CODE_DCS WHERE CODE_ID=TCC.STATUS) STATUS, ");
  		sql.append("       date_format(TCC.UPDATE_DATE,'%Y-%m-%d')  UPDATE_DATE");
		sql.append("  FROM TT_CR_COMPLAINTS_CUSTOM_dcs TCCC,");
	    sql.append("       TT_CR_COMPLAINTS_dcs TCC");
		sql.append(" WHERE TCC.COMP_ID = TCCC.COMP_ID AND TCC.STATUS IN (").append(OemDictCodeConstants.COMP_STATUS_TYPE_02);
		sql.append(",").append(OemDictCodeConstants.COMP_STATUS_TYPE_03).append(")");
		sql.append("   AND TCC.ALLOT_DEALER='").append(dealerId).append("'");
		if(!StringUtils.isNullOrEmpty(queryParams.get("linkman"))){
			sql.append(" AND TCCC.LINK_MAN LIKE ? ");
			params.add("%"+queryParams.get("linkman")+"%");
    	}
		if(!StringUtils.isNullOrEmpty(queryParams.get("tel"))){
			sql.append(" AND TCCC.TEL LIKE ? ");
			params.add("%"+queryParams.get("tel")+"%");
    	}
		if(!StringUtils.isNullOrEmpty(queryParams.get("compCode"))){
			sql.append(" AND TCC.COMP_CODE LIKE ? ");
			params.add("%"+queryParams.get("compCode")+"%");
    	}
		if(!StringUtils.isNullOrEmpty(queryParams.get("compBtype"))){
			sql.append(" AND TCC.COMP_BTYPE = ? ");
			params.add(queryParams.get("compBtype"));
    	}
		if(!StringUtils.isNullOrEmpty(queryParams.get("compStype"))){
			sql.append(" AND TCC.COMP_STYPE = ? ");
			params.add(queryParams.get("compStype"));
    	}
		if(!StringUtils.isNullOrEmpty(queryParams.get("status"))){
			sql.append(" AND TCC.STATUS = ? ");
			params.add(queryParams.get("status"));
    	}
		if(!StringUtils.isNullOrEmpty(queryParams.get("compLevel"))){
			sql.append(" AND TCC.COMP_LEVEL = ? ");
			params.add(queryParams.get("compLevel"));
    	}
		if(!StringUtils.isNullOrEmpty(queryParams.get("compNature"))){
			sql.append(" AND TCC.COMP_NATURE = ? ");
			params.add(queryParams.get("compNature"));
    	}
		if(!StringUtils.isNullOrEmpty(queryParams.get("beginTime"))){ 
		     
			sql.append(" AND  DATE_FORMAT(TCC.COMP_DATE,'%Y-%m-%d') >='" + queryParams.get("beginTime") +"' \n");
        }
        if(!StringUtils.isNullOrEmpty(queryParams.get("endTime"))){ 
            
        	sql.append(" AND  DATE_FORMAT (TCC.COMP_DATE,'%Y-%m-%d') <='" + queryParams.get("endTime") +"' \n");
        }
		sql.append(") DCS");
		return sql.toString();
	}

	/**
	 * 取得客户投诉表的基本信息
	 * @param compId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> getComplaint(Long compId) {
		StringBuffer sql = new StringBuffer();
		sql.append("         SELECT * FROM (");
		sql.append("SELECT CC.COMP_ID,");
		sql.append(" 	CC.LINK_MAN,");
		sql.append("	CC.CUSTOM_NO,");
		sql.append("	 date_format(CC.BIRTHDAY,'%Y-%m-%d') AS BIRTHDAY,");
		sql.append("	CC.SEX,");
		sql.append("	(SELECT ORG_NAME FROM TM_ORG WHERE ORG_ID=CC.OWN_ORG_ID) ORG_NAME,");
		sql.append("	CC.OWN_ORG_ID,");
		sql.append("	CC.AGE,");
		sql.append("	CC.PROVINCE,");
		sql.append("	CC.TEL,");
		sql.append("	CC.CITY,");
		sql.append("	CC.E_MAIL,");
		sql.append("	CC.DISTRICT,");
		sql.append("	CC.ZIP_CODE,");
		sql.append("	CC.CAR_RELATION,	");
		sql.append("    CC.ADDRESS,");
		sql.append("	C.COMP_CODE,");
		sql.append("	C.PRE_COMP_CODE,");
		sql.append("	C.COMP_DATE,");
		sql.append("    C.WEEK,C.DEAL_RESULT,");
		sql.append("	C.COMP_DEALER,");
		sql.append("   (SELECT DEALER_CODE");
		sql.append(" 	FROM TM_DEALER");
		sql.append("	WHERE DEALER_ID = C.COMP_DEALER)");
		sql.append("	TS_DEALER_CODE,");
		sql.append("   (SELECT DEALER_SHORTNAME");
		sql.append(" 	FROM TM_DEALER");
		sql.append("	WHERE DEALER_ID = C.COMP_DEALER)");
		sql.append("	TS_DEALER_NAME,");
		sql.append("	C.COMP_NATURE,");
		sql.append("    C.COMP_LEVEL,");
		sql.append(" 	C.COMP_BTYPE,");
		sql.append(" 	(SELECT code_desc FROM TC_CODE_dcs WHERE code_id = C.COMP_STYPE) COMP_STYPE,");
		sql.append(" 	C.COMP_CREATEBY,");
		sql.append(" 	C.COMP_CONTENT,");
		sql.append(" 	 date_format(C.ALLOT_DATE,'%Y-%m-%d') ALLOT_DATE,");
		sql.append(" 	 C.IS_RETURN,");
		sql.append(" 	 C.NORETURN_REMARK,");
		sql.append(" 	 C.NORETURN_BACKREMARK,");
		sql.append(" 	 C.NORETURN_DEALDATE,");
		sql.append(" 	 C.STATUS");
		sql.append(" 	FROM TT_CR_COMPLAINTS_dcs C,");
		sql.append(" 	TT_CR_COMPLAINTS_CUSTOM_dcs CC");
		sql.append(" 	WHERE  C.COMP_ID = CC.COMP_ID AND C.COMP_ID="+compId+"");
		sql.append("         ) DCS");
		return OemDAOUtil.findFirst(sql.toString(), null);
	}

	/**
	 * 根据投诉id查询车辆表信息
	 * @param compId
	 * @return
	 */
	public PageInfoDto getVicheil(Long compId) {
		StringBuffer sql = new StringBuffer();
		sql.append("         SELECT * FROM (");
		sql.append("         SELECT  CV.VIN,");
		sql.append("        (SELECT GROUP_NAME");
		sql.append(" 			          FROM TM_VHCL_MATERIAL_GROUP");
  		sql.append(" 			         WHERE GROUP_CODE = CV.BRAND_CODE)");
 		sql.append(" 			          BRAND_NAME,");
  		sql.append(" 			       CV.BRAND_CODE,");
		sql.append(" 			       (SELECT GROUP_NAME");
		sql.append(" 			          FROM TM_VHCL_MATERIAL_GROUP");
  		sql.append(" 			         WHERE GROUP_CODE = CV.MODEL_CODE)");
 		sql.append(" 			          MODEL_NAME,");
  		sql.append(" 			       CV.MODEL_CODE,");
		sql.append(" 			       CV.ENGINE_NO,");
		sql.append(" 			       CV.LICENSE_NO,");
		sql.append(" 			       CV.MILEAGE,");
		sql.append(" 			        date_format(CV.PURCHASED_DATE,'%Y-%m-%d') PURCHASED_DATE,");
		sql.append(" 			       CV.PURCHASED_DEALER,");
		sql.append(" 			       (SELECT DEALER_SHORTNAME");
		sql.append("			          FROM TM_DEALER");
		sql.append(" 			         WHERE DEALER_ID = CV.PURCHASED_DEALER)");
		sql.append(" 			          GC_DEALER_NAME ,");
		sql.append(" 			       (SELECT DEALER_CODE");
		sql.append("			          FROM TM_DEALER");
  		sql.append(" 			         WHERE DEALER_ID = CV.PURCHASED_DEALER)");
 		sql.append(" 			          GC_DEALER_CODE ");
  		sql.append("                 FROM  TT_CR_COMPLAINTS_VEHICLE_dcs CV  WHERE CV.COMP_ID="+compId+"");
  		sql.append("         ) DCS");
		return OemDAOUtil.pageQuery(sql.toString(), null);
	}

	/**
	 * 活动明细中查询活动工时列表
	 * @param id
	 * @param dealerId
	 * @return
	 */
	public List<Map> queryLabourList(Long dealerId, Long compId) {
		List<Object> sqlParams=new ArrayList<Object>();//参数
		StringBuffer sql= new StringBuffer();
		sql.append("select h.DEAL_USER,date_format(h.DEAL_DATE,'%Y-%m-%d') DEAL_DATE ,h.DEAL_PLAN,h.DEAL_RESULT,h.CUSTOM_FEEDBACK from TT_CR_COMPLAINTS_HISTORY_dcs h  \n" );
		sql.append(" WHERE 1 = 1");
		if (!StringUtils.isNullOrEmpty(dealerId)) 
		{
			sql.append(" AND DEALER_ID = ? ");
			sqlParams.add(dealerId);
		}
		if (!StringUtils.isNullOrEmpty(compId)) 
		{
			sql.append(" AND COMP_ID = ? ");
			sqlParams.add(compId);
		}
		return OemDAOUtil.findAll(sql.toString(), sqlParams);
	}

	/**
	 * 客户投诉处理(总部) 不需回访查询
	 * @param queryParams
	 * @return
	 */
	public PageInfoDto complaintDisposalList(Map<String, String> queryParams) {
		List<Object> params = new ArrayList<Object>();
		String sql = getcomplaintDisposalListSql(queryParams,params);
		return OemDAOUtil.pageQuery(sql, params);
	}

	/**
	 * 客户投诉处理(总部) 不需回访查询SQL
	 * @param queryParams
	 * @param params
	 * @return
	 */
	private String getcomplaintDisposalListSql(Map<String, String> queryParams, List<Object> params) {
		LoginInfoDto logonUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		Long dealer=logonUser.getDealerId();
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT * FROM (");
		sql.append("SELECT TCC.COMP_ID,");
		sql.append("        TCC.COMP_CODE,");
		sql.append("(SELECT DEALER_SHORTNAME");
		sql.append("		          FROM TM_DEALER");
  		sql.append("		         WHERE dealer_id = TCC.ALLOT_DEALER)");
 		sql.append("		          ALLOT_DEALER,");
 		sql.append("(SELECT DEALER_CODE");
		sql.append("		          FROM TM_DEALER");
  		sql.append("		         WHERE dealer_id = TCC.ALLOT_DEALER)");
 		sql.append("		          ALLOT_DEALER_CODE,");
		sql.append("        TCCC.LINK_MAN,");
		sql.append("        TCCC.TEL,");
		sql.append("       (SELECT code_desc FROM TC_CODE_dcs WHERE code_id = TCC.COMP_NATURE) COMP_NATURE,");
		sql.append("        (SELECT ORG_NAME FROM TM_ORG WHERE ORG_ID=TCCC.OWN_ORG_ID) OWN_ORG_ID,");
		sql.append("        (SELECT  REGION_NAME FROM TM_REGION_dcs WHERE REGION_CODE=TCCC.PROVINCE) PROVINCE,");
		sql.append("        (SELECT DEALER_SHORTNAME");
		sql.append("           FROM TM_DEALER");
		sql.append("          WHERE dealer_id = TCC.COMP_DEALER)");
		sql.append("           dealer_name,");
		sql.append("        (SELECT DEALER_CODE");
		sql.append("           FROM TM_DEALER");
		sql.append("          WHERE dealer_id = TCC.COMP_DEALER)");
		sql.append("           dealer_code,");
		sql.append("        (SELECT code_desc");
		sql.append("           FROM TC_CODE_dcs");
		sql.append("          WHERE code_id = TCC.COMP_LEVEL)");
		sql.append("           comp_level,");
		sql.append("          (SELECT code_desc");
		sql.append("           FROM TC_CODE_dcs");
		sql.append("          WHERE code_id = TCC.COMP_BTYPE)");
		sql.append("           comp_btype,");
		sql.append("        (SELECT code_desc");
		sql.append("           FROM TC_CODE_dcs");
		sql.append("          WHERE code_id = TCC.COMP_STYPE)");
		sql.append("           comp_stype,");
		sql.append("        DATE_FORMAT(TCC.COMP_DATE,'%Y-%m-%d') COMP_DATE,");
		sql.append("        (SELECT code_desc");
		sql.append("           FROM TC_CODE_dcs");
		sql.append("          WHERE code_id = TCC.STATUS)");
		sql.append("           STATUS,");
		sql.append("        (SELECT CODE_DESC FROM TC_CODE_dcs WHERE  CODE_ID=TCC.IS_RETURN) ");
		sql.append("        IS_RETURN, \n");
		sql.append("        DATE_FORMAT(TCC.UPDATE_DATE,'%Y-%m-%d') UPDATE_DATE \n");
		sql.append("  FROM TT_CR_COMPLAINTS_CUSTOM_dcs TCCC,");
		sql.append("        TT_CR_COMPLAINTS_dcs TCC");
		sql.append("  WHERE     TCC.COMP_ID = TCCC.COMP_ID");
		if(dealer!=null && !"".equals(dealer)){
			sql.append(" AND TCC.ALLOT_DEALER='").append(dealer).append("'");
		}
		if(!logonUser.getDutyType().toString().equals(OemDictCodeConstants.DUTY_TYPE_DEPT.toString()) && !logonUser.getDutyType().toString().equals(OemDictCodeConstants.DUTY_TYPE_COMPANY.toString()) && !logonUser.getDutyType().equals(OemDictCodeConstants.DUTY_TYPE_DEALER.toString())){
			sql.append("      AND  TCC.ALLOT_DEALER in ("+getDealersByArea(logonUser.getOrgId().toString())+")\n");
		}
		if(!StringUtils.isNullOrEmpty(queryParams.get("linkman"))){
			sql.append(" AND TCCC.LINK_MAN LIKE ? ");
			params.add("%"+queryParams.get("linkman")+"%");
    	}
		if(!StringUtils.isNullOrEmpty(queryParams.get("tel"))){
			sql.append(" AND TCCC.TEL LIKE ? ");
			params.add("%"+queryParams.get("tel")+"%");
    	}
		if(!StringUtils.isNullOrEmpty(queryParams.get("compCode"))){
			sql.append(" AND TCC.COMP_CODE LIKE ? ");
			params.add("%"+queryParams.get("compCode")+"%");
    	}
		if(!StringUtils.isNullOrEmpty(queryParams.get("compBtype"))){
			sql.append(" AND TCC.COMP_BTYPE = ? ");
			params.add(queryParams.get("compBtype"));
    	}
		if(!StringUtils.isNullOrEmpty(queryParams.get("compStype"))){
			sql.append(" AND TCC.COMP_STYPE = ? ");
			params.add(queryParams.get("compStype"));
    	}
		if(!StringUtils.isNullOrEmpty(queryParams.get("status"))){
			sql.append(" AND TCC.STATUS = ? ");
			params.add(queryParams.get("status"));
    	}
		if(!StringUtils.isNullOrEmpty(queryParams.get("compLevel"))){
			sql.append(" AND TCC.COMP_LEVEL = ? ");
			params.add(queryParams.get("compLevel"));
    	}
		if(!StringUtils.isNullOrEmpty(queryParams.get("compNature"))){
			sql.append(" AND TCC.COMP_NATURE = ? ");
			params.add(queryParams.get("compNature"));
    	}
		if(!StringUtils.isNullOrEmpty(queryParams.get("beginTime"))){ 
		     
			sql.append(" AND  DATE_FORMAT(TCC.COMP_DATE,'%Y-%m-%d') >='" + queryParams.get("beginTime") +"' \n");
        }
        if(!StringUtils.isNullOrEmpty(queryParams.get("endTime"))){ 
            
        	sql.append(" AND  DATE_FORMAT(TCC.COMP_DATE,'%Y-%m-%d') <='" + queryParams.get("endTime") +"' \n");
        }
    	sql.append(") dcs");
        return sql.toString();
	}

	/**
	 * 客户投诉处理(总部)不需回访处理 页面初始化
	 * @param compId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> getComplaintList(Long compId) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM (");
		sql.append("SELECT CC.COMP_ID,");
		sql.append(" 	CC.LINK_MAN,");
		sql.append("	CC.CUSTOM_NO,");
		sql.append("	DATE_FORMAT(CC.BIRTHDAY,'%Y-%m-%d') AS BIRTHDAY,");
		sql.append("	CC.SEX,");
		sql.append("	(SELECT ORG_NAME FROM TM_ORG WHERE ORG_ID=CC.OWN_ORG_ID) ORG_NAME,");
		sql.append("	CC.OWN_ORG_ID,");
		sql.append("	CC.AGE,");
		sql.append("	CC.PROVINCE,");
		sql.append("	CC.TEL,");
		sql.append("	CC.CITY,");
		sql.append("	CC.E_MAIL,");
		sql.append("	CC.DISTRICT,");
		sql.append("	CC.ZIP_CODE,");
		sql.append("	CC.CAR_RELATION,	");
		sql.append("    CC.ADDRESS,");
		sql.append("	C.COMP_CODE,");
		sql.append("	C.PRE_COMP_CODE,");
		sql.append("	C.COMP_DATE,");
		sql.append("    C.WEEK,C.DEAL_RESULT,");
		sql.append("	C.COMP_DEALER,");
		sql.append("   (SELECT DEALER_CODE");
		sql.append(" 	FROM TM_DEALER");
		sql.append("	WHERE DEALER_ID = C.COMP_DEALER)");
		sql.append("	TS_DEALER_CODE,");
		sql.append("   (SELECT DEALER_SHORTNAME");
		sql.append(" 	FROM TM_DEALER");
		sql.append("	WHERE DEALER_ID = C.COMP_DEALER)");
		sql.append("	TS_DEALER_NAME,");
		sql.append("	C.COMP_NATURE,");
		sql.append("    C.COMP_LEVEL,");
		sql.append(" 	C.COMP_BTYPE,");
		sql.append("        (SELECT code_desc");
		sql.append("           FROM TC_CODE_dcs");
		sql.append("          WHERE code_id =C.COMP_STYPE)");
		sql.append("           COMP_STYPE,");
		sql.append(" 	C.COMP_CREATEBY,");
		sql.append(" 	C.COMP_CONTENT,");
		sql.append(" 	 C.ALLOT_DATE,");
		sql.append(" 	 C.IS_RETURN,");
		sql.append(" 	 C.NORETURN_REMARK,");
		sql.append(" 	 C.NORETURN_BACKREMARK,");
		sql.append(" 	 C.NORETURN_DEALDATE,");
		sql.append(" 	 C.STATUS");
		sql.append(" 	FROM TT_CR_COMPLAINTS_dcs C,");
		sql.append(" 	TT_CR_COMPLAINTS_CUSTOM_dcs CC");
		sql.append(" 	WHERE  C.COMP_ID = CC.COMP_ID AND C.COMP_ID="+compId+"");
		sql.append(") DCS");
		return OemDAOUtil.findFirst(sql.toString(), null);
	}

	public PageInfoDto getComplaintList1(Long compId) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM (");
		sql.append("SELECT ");
		sql.append(" 	 C.IS_RETURN,");
		sql.append(" 	 C.NORETURN_REMARK,");
		sql.append(" 	 DATE_FORMAT(C.NORETURN_DEALDATE,'%Y-%m-%d') NORETURN_DEALDATE,");
		sql.append(" 	 C.NORETURN_BACKREMARK");
		sql.append(" 	FROM TT_CR_COMPLAINTS_dcs C");
		sql.append(" 	WHERE C.COMP_ID = "+compId+"");
		sql.append(") DCS");
		return OemDAOUtil.pageQuery(sql.toString(), null);
	}

	/**
	 * 客户投诉处理(总部) 不需回访查询DLR
	 * @param queryParams
	 * @return
	 */
	public PageInfoDto getComplaintDLR(Map<String, String> queryParams) {
		List<Object> params = new ArrayList<Object>();
		String sql = getComplaintDLRSql(queryParams,params);
		return OemDAOUtil.pageQuery(sql, params);
	}

	/**
	 * 客户投诉处理(总部) 不需回访查询DLRSQL
	 * @param queryParams
	 * @param params
	 * @return
	 */
	private String getComplaintDLRSql(Map<String, String> queryParams, List<Object> params) {
		LoginInfoDto logonUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		Long dealer = logonUser.getDealerId();
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT * FROM (");
		sql.append("SELECT TCC.COMP_ID,");
		sql.append("        TCC.COMP_CODE,");
		sql.append("(SELECT DEALER_SHORTNAME");
		sql.append("		          FROM TM_DEALER");
  		sql.append("		         WHERE dealer_id = TCC.ALLOT_DEALER)");
 		sql.append("		          ALLOT_DEALER,");
 		sql.append("(SELECT DEALER_CODE");
		sql.append("		          FROM TM_DEALER");
  		sql.append("		         WHERE dealer_id = TCC.ALLOT_DEALER)");
 		sql.append("		          ALLOT_DEALER_CODE,");
		sql.append("        TCCC.LINK_MAN,");
		sql.append("        TCCC.TEL,");
		sql.append("       (SELECT code_desc FROM TC_CODE_DCS WHERE code_id = TCC.COMP_NATURE) COMP_NATURE,");
		sql.append("        (SELECT ORG_NAME FROM TM_ORG WHERE ORG_ID=TCCC.OWN_ORG_ID) OWN_ORG_ID,");
		sql.append("        (SELECT  REGION_NAME FROM TM_REGION WHERE REGION_CODE=TCCC.PROVINCE) PROVINCE,");
		sql.append("        (SELECT DEALER_SHORTNAME");
		sql.append("           FROM TM_DEALER");
		sql.append("          WHERE dealer_id = TCC.COMP_DEALER)");
		sql.append("           dealer_name,");
		sql.append("        (SELECT DEALER_CODE");
		sql.append("           FROM TM_DEALER");
		sql.append("          WHERE dealer_id = TCC.COMP_DEALER)");
		sql.append("           dealer_code,");
		sql.append("        (SELECT code_desc");
		sql.append("           FROM TC_CODE_DCS");
		sql.append("          WHERE code_id = TCC.COMP_LEVEL)");
		sql.append("           comp_level,");
		sql.append("          (SELECT code_desc");
		sql.append("           FROM TC_CODE_DCS");
		sql.append("          WHERE code_id = TCC.COMP_BTYPE)");
		sql.append("           comp_btype,");
		sql.append("        (SELECT code_desc");
		sql.append("           FROM TC_CODE_DCS");
		sql.append("          WHERE code_id = TCC.COMP_STYPE)");
		sql.append("           comp_stype,");
		sql.append("        DATE_FORMAT (TCC.COMP_DATE,'%Y-%m-%d') COMP_DATE,");
		sql.append("        (SELECT code_desc");
		sql.append("           FROM TC_CODE_DCS");
		sql.append("          WHERE code_id = TCC.STATUS)");
		sql.append("           STATUS,");
		sql.append("        (SELECT CODE_DESC FROM TC_CODE_DCS WHERE  CODE_ID=TCC.IS_RETURN) ");
		sql.append("        IS_RETURN, \n");
		sql.append("         DATE_FORMAT (TCC.UPDATE_DATE,'%Y-%m-%d') UPDATE_DATE \n");
		sql.append("  FROM TT_CR_COMPLAINTS_CUSTOM_DCS TCCC,");
		sql.append("        TT_CR_COMPLAINTS_DCS TCC");
		sql.append("  WHERE     TCC.COMP_ID = TCCC.COMP_ID");
		if(dealer!=null && !"".equals(dealer)){
			sql.append(" AND TCC.ALLOT_DEALER='").append(dealer).append("'");
		}
		if(!logonUser.getDutyType().toString().equals(OemDictCodeConstants.DUTY_TYPE_DEPT.toString()) && !logonUser.getDutyType().toString().equals(OemDictCodeConstants.DUTY_TYPE_COMPANY.toString()) && !logonUser.getDutyType().equals(OemDictCodeConstants.DUTY_TYPE_DEALER.toString())){
			sql.append("      AND  TCC.ALLOT_DEALER in ("+getDealersByArea(logonUser.getOrgId().toString())+")\n");
		}
		if(!StringUtils.isNullOrEmpty(queryParams.get("linkman"))){
			sql.append(" AND TCCC.LINK_MAN LIKE ? ");
			params.add("%"+queryParams.get("linkman")+"%");
    	}
		if(!StringUtils.isNullOrEmpty(queryParams.get("tel"))){
			sql.append(" AND TCCC.TEL LIKE ? ");
			params.add("%"+queryParams.get("tel")+"%");
    	}
		if(!StringUtils.isNullOrEmpty(queryParams.get("dealerId"))){
			sql.append(" AND TCC.ALLOT_DEALER= ? ");
			params.add(queryParams.get("dealerId"));
    	}
		if(!StringUtils.isNullOrEmpty(queryParams.get("orgId"))){
			sql.append(" AND TCCC.OWN_ORG_ID= ? ");
			params.add(queryParams.get("orgId"));
    	}
		if(!StringUtils.isNullOrEmpty(queryParams.get("dealerId_"))){
			sql.append(" AND TCC.COMP_DEALER= ? ");
			params.add(queryParams.get("dealerId_"));
    	}
		if(!StringUtils.isNullOrEmpty(queryParams.get("compBtype"))){
			sql.append(" AND TCC.COMP_BTYPE= ? ");
			params.add(queryParams.get("compBtype"));
    	}
		if(!StringUtils.isNullOrEmpty(queryParams.get("compStype"))){
			sql.append(" AND TCC.COMP_STYPE = ? ");
			params.add(queryParams.get("compStype"));
    	}
		if(!StringUtils.isNullOrEmpty(queryParams.get("status"))){
			sql.append(" AND TCC.STATUS= ? ");
			params.add(queryParams.get("status"));
    	}
		if(!StringUtils.isNullOrEmpty(queryParams.get("compLevel"))){
			sql.append(" AND TCC.COMP_LEVEL = ? ");
			params.add(queryParams.get("compLevel"));
    	}
		if(!StringUtils.isNullOrEmpty(queryParams.get("compCode"))){
			sql.append(" AND TCC.COMP_CODE LIKE ? ");
			params.add("%"+queryParams.get("compCode")+"%");
    	}
		if(!StringUtils.isNullOrEmpty(queryParams.get("compNature"))){
			sql.append(" AND TCC.COMP_NATURE = ? ");
			params.add(queryParams.get("compNature"));
    	}
		if(!StringUtils.isNullOrEmpty(queryParams.get("beginTime"))){ 
		     
			sql.append(" AND  DATE_FORMAT(TCC.COMP_DATE,'%Y-%m-%d') >='" + queryParams.get("beginTime") +"' \n");
        }
        if(!StringUtils.isNullOrEmpty(queryParams.get("endTime"))){ 
            
        	sql.append(" AND  DATE_FORMAT (TCC.COMP_DATE,'%Y-%m-%d') <='" + queryParams.get("endTime") +"' \n");
        }
    	sql.append(") DCS");
		return sql.toString();
	}

	/**
	 * 客户投诉查询
	 * @param queryParams
	 * @return
	 */
	public List<Map> complaintDisposalQuery(Map<String, String> queryParams) {
		List<Object> params = new ArrayList<Object>();
		String sql = getComplaintDisposalSql(queryParams,params);
		return OemDAOUtil.findAll(sql, params);
	}

	/**
	 * 客户投诉查询下载
	 * @param queryParams
	 * @param params
	 * @return
	 */
	private String getComplaintDisposalSql(Map<String, String> queryParams, List<Object> params) {
		LoginInfoDto logonUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		Long dealer = logonUser.getDealerId();
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT * FROM (");
		sql.append("SELECT TCC.COMP_ID,");
		sql.append("        TCC.COMP_CODE,");
		sql.append("(SELECT DEALER_SHORTNAME");
		sql.append("		          FROM TM_DEALER");
  		sql.append("		         WHERE dealer_id = TCC.ALLOT_DEALER)");
 		sql.append("		          ALLOT_DEALER,");
 		sql.append("(SELECT DEALER_CODE");
		sql.append("		          FROM TM_DEALER");
  		sql.append("		         WHERE dealer_id = TCC.ALLOT_DEALER)");
 		sql.append("		          ALLOT_DEALER_CODE,");
		sql.append("        TCCC.LINK_MAN,");
		sql.append("        TCCC.TEL,");
		sql.append("       (SELECT code_desc FROM TC_CODE_dcs WHERE code_id = TCC.COMP_NATURE ) COMP_NATURE,");
		sql.append("        (SELECT ORG_NAME FROM TM_ORG WHERE ORG_ID=TCCC.OWN_ORG_ID  ) OWN_ORG_ID,");
		sql.append("        (SELECT  REGION_NAME FROM TM_REGION WHERE REGION_CODE=TCCC.PROVINCE ) PROVINCE,");
		sql.append("        (SELECT DEALER_SHORTNAME");
		sql.append("           FROM TM_DEALER");
		sql.append("          WHERE dealer_id = TCC.COMP_DEALER)");
		sql.append("           dealer_name,");
		sql.append("        (SELECT DEALER_CODE");
		sql.append("           FROM TM_DEALER");
		sql.append("          WHERE dealer_id = TCC.COMP_DEALER )");
		sql.append("           dealer_code,");
		sql.append("        (SELECT code_desc");
		sql.append("           FROM TC_CODE_dcs");
		sql.append("          WHERE code_id = TCC.COMP_LEVEL  )");
		sql.append("           comp_level,");
		sql.append("          (SELECT code_desc");
		sql.append("           FROM TC_CODE_dcs");
		sql.append("          WHERE code_id = TCC.COMP_BTYPE )");
		sql.append("           comp_btype,");
		sql.append("        (SELECT code_desc");
		sql.append("           FROM TC_CODE_dcs");
		sql.append("          WHERE code_id = TCC.COMP_STYPE )");
		sql.append("           comp_stype,");
		sql.append("        DATE_FORMAT(TCC.COMP_DATE,'%Y-%m-%d') COMP_DATE,");
		sql.append("        (SELECT code_desc");
		sql.append("           FROM TC_CODE_dcs");
		sql.append("          WHERE code_id = TCC.STATUS )");
		sql.append("           STATUS,");
		sql.append("        (SELECT CODE_DESC FROM TC_CODE_dcs WHERE  CODE_ID=TCC.IS_RETURN ) ");
		sql.append("        IS_RETURN, \n");
		sql.append("        DATE_FORMAT(TCC.UPDATE_DATE,'%Y-%m-%d') UPDATE_DATE \n");
		sql.append("  FROM TT_CR_COMPLAINTS_CUSTOM_dcs TCCC,");
		sql.append("        TT_CR_COMPLAINTS_dcs TCC");
		sql.append("  WHERE     TCC.COMP_ID = TCCC.COMP_ID");
		if(dealer!=null && !"".equals(dealer)){
			sql.append(" AND TCC.ALLOT_DEALER='").append(dealer).append("'");
		}
		if(!StringUtils.isNullOrEmpty(queryParams.get("linkman"))){
			sql.append(" AND TCCC.LINK_MAN LIKE ? ");
			params.add("%"+queryParams.get("linkman")+"%");
    	}
		if(!StringUtils.isNullOrEmpty(queryParams.get("tel"))){
			sql.append(" AND TCCC.TEL LIKE ? ");
			params.add("%"+queryParams.get("tel")+"%");
    	}
		if(!StringUtils.isNullOrEmpty(queryParams.get("dealerId"))){
			sql.append(" AND TCC.ALLOT_DEALER= ? ");
			params.add(queryParams.get("dealerId"));
    	}
		if(!StringUtils.isNullOrEmpty(queryParams.get("orgId"))){
			sql.append(" AND TCCC.OWN_ORG_ID= ? ");
			params.add(queryParams.get("orgId"));
    	}
		if(!StringUtils.isNullOrEmpty(queryParams.get("dealerCode_"))){
			sql.append(" AND TCC.COMP_DEALER= ? ");
			params.add(queryParams.get("dealerCode_"));
    	}
		if(!StringUtils.isNullOrEmpty(queryParams.get("compBtype"))){
			sql.append(" AND TCC.COMP_BTYPE= ? ");
			params.add(queryParams.get("compBtype"));
    	}
		if(!StringUtils.isNullOrEmpty(queryParams.get("compStype"))){
			sql.append(" AND TCC.COMP_STYPE = ? ");
			params.add(queryParams.get("compStype"));
    	}
		if(!StringUtils.isNullOrEmpty(queryParams.get("status"))){
			sql.append(" AND TCC.STATUS= ? ");
			params.add(queryParams.get("status"));
    	}
		if(!StringUtils.isNullOrEmpty(queryParams.get("compLevel"))){
			sql.append(" AND TCC.COMP_LEVEL = ? ");
			params.add(queryParams.get("compLevel"));
    	}
		if(!StringUtils.isNullOrEmpty(queryParams.get("compCode"))){
			sql.append(" AND TCC.COMP_CODE LIKE ? ");
			params.add("%"+queryParams.get("compCode")+"%");
    	}
		if(!StringUtils.isNullOrEmpty(queryParams.get("compNature"))){
			sql.append(" AND TCC.COMP_NATURE = ? ");
			params.add(queryParams.get("compNature"));
    	}
		if(!StringUtils.isNullOrEmpty(queryParams.get("beginTime"))){ 
		     
			sql.append(" AND  DATE_FORMAT(TCC.COMP_DATE,'%Y-%m-%d') >='" + queryParams.get("beginTime") +"' \n");
        }
        if(!StringUtils.isNullOrEmpty(queryParams.get("endTime"))){ 
            
        	sql.append(" AND  DATE_FORMAT (TCC.COMP_DATE,'%Y-%m-%d') <='" + queryParams.get("endTime") +"' \n");
        }
		if(!logonUser.getDutyType().toString().equals(OemDictCodeConstants.DUTY_TYPE_DEPT.toString()) && !logonUser.getDutyType().toString().equals(OemDictCodeConstants.DUTY_TYPE_COMPANY.toString()) && !logonUser.getDutyType().equals(OemDictCodeConstants.DUTY_TYPE_DEALER.toString())){
			sql.append("      AND  TCC.ALLOT_DEALER in ("+getDealersByArea(logonUser.getOrgId().toString())+")\n");
		}
		sql.append(") dcs");
		return sql.toString();
	}

}
