package com.yonyou.dms.manage.dao.organization;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.common.OemDictCodeConstants;

@Repository
public class CarCompanyMaintainDao extends OemBaseDAO {

	public PageInfoDto searchCompanyInfo(Map<String, String> param) {
		List<Object> queryParam = new ArrayList<Object>();
		String companyCode = param.get("companyCode");
		String companyName = param.get("companyName");
	    StringBuffer sql=new StringBuffer();
	    sql.append("SELECT TC.COMPANY_ID,\n");
	    sql.append("       TC.OEM_COMPANY_ID,\n");  
	    sql.append("       TC.COMPANY_TYPE,\n");  
	    sql.append("       TC.COMPANY_CODE,\n");  
	    sql.append("       TC.COMPANY_SHORTNAME,\n");  
	    sql.append("       TC.COMPANY_NAME,\n");  
	    sql.append("       TC.STATUS\n");  
	    sql.append("  FROM TM_COMPANY TC\n");  
	    sql.append(" WHERE TC.COMPANY_TYPE = ? \n"); 
	    queryParam.add(OemDictCodeConstants.COMPANY_TYPE_OEM);
	    if(StringUtils.isNotBlank(companyCode))
	    {
	    	sql.append("   AND TC.COMPANY_CODE LIKE ? \n");  
	    	queryParam.add("%"+companyCode+"%");
	    }
	    if(StringUtils.isNotBlank(companyName))
	    {
	    	sql.append("   AND TC.COMPANY_NAME LIKE ? \n");
	    	queryParam.add("%"+companyName+"%");
	    }
	    System.out.println("SQL \n ==================="+sql+"==================\n");
	    System.out.println("Param:   "+ queryParam);
		return OemDAOUtil.pageQuery(sql.toString(), queryParam);
	}

	public List<Map> getOrgTreeCode() {
		List<Object> queryParam = new ArrayList<Object>();
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT \n");
		sql.append("       DISTINCT CASE LENGTH(G.NUM) WHEN 3 THEN CHARSET(ASCII(G.CHARS) + 1)||'01' ELSE G.CHARS||G.NUM END  AS NEW_TREECODE \n");  
		sql.append("  FROM (SELECT  CASE  LENGTH(SUBSTR(TREE_CODE, LENGTH(TREE_CODE) - 1, 2) + 1) \n");
		sql.append("						WHEN  1 THEN '0'||(SUBSTR(TREE_CODE, LENGTH(TREE_CODE) - 1, 2) + 1) \n");
		sql.append("						ELSE (SUBSTR(TREE_CODE, LENGTH(TREE_CODE) - 1, 2) + 1) END AS NUM,\n");  
		sql.append("               SUBSTR(TREE_CODE, LENGTH(TREE_CODE) - 2, 1) AS CHARS,\n");  
		sql.append("               MAX(TREE_CODE)\n");  
		sql.append("          FROM TM_ORG T\n");  
		sql.append("         WHERE T.ORG_LEVEL = 1\n");  
		sql.append("         GROUP BY SUBSTR(TREE_CODE, LENGTH(TREE_CODE) - 1, 2),\n");  
		sql.append("                  SUBSTR(TREE_CODE, LENGTH(TREE_CODE) - 2, 1)) G\n");
		return OemDAOUtil.findAll(sql.toString(), queryParam);
	}

	public List<Map> getroleList(Long companyId) {
		List<Object> queryParam = new ArrayList<Object>();
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT DISTINCT TR.ROLE_ID\n");  
		sql.append("  FROM TC_ROLE TR, TR_ROLE_FUNC TRF\n");  
		sql.append(" WHERE TR.ROLE_NAME LIKE '%超级用户%'\n");  
		sql.append("   AND TR.ROLE_ID = TRF.ROLE_ID\n");
		sql.append("   AND TR.OEM_COMPANY_ID = "+companyId+"\n");  
		sql.append("   AND TRF.FUNC_ID IN (SELECT C.FUNC_ID\n");  
		sql.append("                         FROM TC_FUNC C\n");  
		sql.append("                        WHERE C.FUNC_NAME LIKE '%用户维护%'\n");  
		sql.append("                           OR C.FUNC_NAME LIKE '%角色维护%'\n");  
		sql.append("                           OR C.FUNC_NAME LIKE '%用户维护%'\n");  
		sql.append("                           OR C.FUNC_NAME LIKE '%车厂公司维护%'\n");
		sql.append("                           OR C.FUNC_NAME LIKE '%职位维护%')\n");
		return OemDAOUtil.findAll(sql.toString(), queryParam);
	}

	public List<Map> getposeList(Long roleId) {
		List<Object> queryParam = new ArrayList<Object>();
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT T.POSE_ID\n");
		sql.append("  FROM TC_POSE T, TR_ROLE_POSE TR\n");  
		sql.append(" WHERE T.POSE_NAME LIKE '%超级用户%'\n");  
		sql.append("   AND TR.POSE_ID = T.POSE_ID\n");  
		sql.append("   AND tr.role_id="+roleId+"\n");
		return OemDAOUtil.findAll(sql.toString(), queryParam);
	}

	public List<Map> getfuncList() {
		List<Object> queryParam = new ArrayList<Object>();
		StringBuffer sql= new StringBuffer();
		sql.append(" SELECT C.FUNC_ID\n");  
		sql.append("  FROM TC_FUNC C\n");  
		sql.append("  WHERE C.FUNC_NAME LIKE '%用户维护%'\n");  
		sql.append("  OR C.FUNC_NAME LIKE '%角色维护%'\n");  
		sql.append("  OR C.FUNC_NAME LIKE '%用户维护%'\n");  
		sql.append("  OR C.FUNC_NAME LIKE '%车厂公司维护%'\n");
		sql.append("  OR C.FUNC_NAME LIKE '%职位维护%'\n");
		return OemDAOUtil.findAll(sql.toString(), queryParam);
	}

	public void insertBusinessPara(Long userId, Long yuanCompanyId, Long newCompanyId) {
		List<Object> queryParam = new ArrayList<Object>();
		StringBuffer sql= new StringBuffer();
		sql.append("INSERT INTO TM_BUSINESS_PARA\n");
		sql.append("  (PARA_ID,\n");  
		sql.append("   TYPE_CODE,\n");  
		sql.append("   TYPE_NAME,\n");  
		sql.append("   PARA_NAME,\n");  
		sql.append("   PARA_VALUE,\n");  
		sql.append("   REMARK,\n");  
		sql.append("   CREATE_DATE,\n");  
		sql.append("   CREATE_BY,\n");  
		sql.append("   OEM_COMPANY_ID)\n");  
		sql.append("  SELECT PARA_ID,\n");  
		sql.append("         TYPE_CODE,\n");  
		sql.append("         TYPE_NAME,\n");  
		sql.append("         PARA_NAME,\n");  
		sql.append("         PARA_VALUE,\n");  
		sql.append("         REMARK,\n");  
		sql.append("         SYSDATE(),\n");  
		sql.append("         "+userId+",\n");  
		sql.append("         "+newCompanyId+"\n");  
		sql.append("    FROM TM_BUSINESS_PARA\n");  
		sql.append("   WHERE OEM_COMPANY_ID = "+yuanCompanyId+"\n");
		OemDAOUtil.execBatchPreparement(sql.toString(), queryParam);
	}

	public void insertVariablePara(Long userId, Long yuanCompanyId, Long newCompanyId) {
		List<Object> queryParam = new ArrayList<Object>();
	    StringBuffer sql= new StringBuffer();
		sql.append("INSERT INTO TM_VARIABLE_PARA\n");
		sql.append("  (PARA_ID,\n");  
		sql.append("   PARA_TYPE,\n");  
		sql.append("   PARA_CODE,\n");  
		sql.append("   PARA_NAME,\n");  
		sql.append("   STATUS,\n");  
		sql.append("   ISSUE,\n");  
		sql.append("   REMARK,\n");  
		sql.append("   CREATE_DATE,\n");  
		sql.append("   CREATE_BY,\n");  
		sql.append("   OEM_COMPANY_ID)\n");  
		sql.append("  SELECT PARA_ID,\n");  
		sql.append("         PARA_TYPE,\n");  
		sql.append("         PARA_CODE,\n");  
		sql.append("         PARA_NAME,\n");  
		sql.append("         STATUS,\n");  
		sql.append("         ISSUE,\n");  
		sql.append("         REMARK,\n");  
		sql.append("         SYSDATE(),\n");  
		sql.append("         "+userId+",\n");  
		sql.append("         "+newCompanyId+"\n");  
		sql.append("    FROM TM_VARIABLE_PARA\n");  
		sql.append("   WHERE OEM_COMPANY_ID = "+yuanCompanyId+"\n");
		sql.append("    AND  PARA_TYPE  LIKE '"+OemDictCodeConstants.PARA_TYPE+"%'\n");
		OemDAOUtil.execBatchPreparement(sql.toString(), queryParam);
	}

}
