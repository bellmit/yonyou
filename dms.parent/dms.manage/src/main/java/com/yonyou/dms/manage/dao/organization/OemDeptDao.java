package com.yonyou.dms.manage.dao.organization;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.common.domains.PO.basedata.TmOrgPO;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.function.utils.common.StringUtils;

@Repository
public class OemDeptDao extends OemBaseDAO {
	
	/**
	 * 部门树形结构初始化
	 * @return
	 */
	public List<Map> getOemDeptTree() {
		StringBuffer sql = new StringBuffer();
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		
		StringBuffer sql1 = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		int poseBusType = loginInfo.getPoseBusType();
		String parOrgId = CommonUtils.checkNull(loginInfo.getOrgId());
		
		//经销商树中售后只查询售后的经销商，销售只查询销售的经销商。
		if(OemDictCodeConstants.POSE_BUS_TYPE_WR==poseBusType||OemDictCodeConstants.POSE_BUS_TYPE_DWR==poseBusType){
			sql1.append("   and (BUSS_TYPE ="+OemDictCodeConstants.ORG_BUSS_TYPE_02+" or BUSS_TYPE is null) \n");
		}
		else if(OemDictCodeConstants.POSE_BUS_TYPE_VS==poseBusType||OemDictCodeConstants.POSE_BUS_TYPE_DVS==poseBusType){
			sql1.append("   and (BUSS_TYPE ="+OemDictCodeConstants.ORG_BUSS_TYPE_01+" or BUSS_TYPE is null)\n");
		}
		//判断传入组织类型
		Integer dutyType = 0;
		if(null!=parOrgId && !"".equals(parOrgId)){
			TmOrgPO tmOrg = TmOrgPO.findById(Long.parseLong(parOrgId));
			dutyType = tmOrg.getInteger("DUTY_TYPE");
		}
		StringBuffer sql2 = new StringBuffer();
		if(dutyType!=0 && !OemDictCodeConstants.DUTY_TYPE_DEPT.equals(dutyType) && !OemDictCodeConstants.DUTY_TYPE_COMPANY.equals(dutyType)){
			sql2.append(" AND ORG_ID = "+parOrgId );
		}else{
			sql2.append(" AND  PARENT_ORG_ID = -1 \n");
		}
		
		sql.append("	    SELECT ORG_ID,ORG_CODE,ORG_NAME,ORG_DESC,PARENT_ORG_ID FROM (SELECT ORG_ID,ORG_CODE,ORG_NAME,ORG_DESC,'' PARENT_ORG_ID FROM TM_ORG \n");
		sql.append("			      WHERE 1=1 \n");
		sql.append(sql2);
		sql.append("			      AND   STATUS = "+OemDictCodeConstants.STATUS_ENABLE+" \n");
		sql.append("			    UNION ALL \n");
		sql.append("			    SELECT B.ORG_ID,B.ORG_CODE,B.ORG_NAME,B.ORG_DESC, B.PARENT_ORG_ID \n");
		sql.append("			    FROM (SELECT ORG_ID,ORG_CODE,ORG_NAME,ORG_DESC,PARENT_ORG_ID FROM TM_ORG \n");
		sql.append("			      WHERE 1=1 \n");
		sql.append(sql2);
		sql.append("			      AND   STATUS = "+OemDictCodeConstants.STATUS_ENABLE+") A, TM_ORG B \n");
		sql.append("			    WHERE B.PARENT_ORG_ID = A.ORG_ID \n");
		sql.append(sql1);
		sql.append("				  AND B.COMPANY_ID="+loginInfo.getCompanyId() +" \n");
		sql.append("			    AND   STATUS = "+OemDictCodeConstants.STATUS_ENABLE+" \n");
		sql.append("          union all \n");
		sql.append("          SELECT B.ORG_ID,B.ORG_CODE,B.ORG_NAME,B.ORG_DESC, B.PARENT_ORG_ID \n");
		sql.append("			    FROM (SELECT B.ORG_ID,B.ORG_CODE,B.ORG_NAME,B.ORG_DESC, B.PARENT_ORG_ID \n");
		sql.append("			    FROM (SELECT ORG_ID,ORG_CODE,ORG_NAME,ORG_DESC,PARENT_ORG_ID FROM TM_ORG \n");
		sql.append("			      WHERE 1=1 \n");
		sql.append(sql2);
		sql.append("			      AND   STATUS = "+OemDictCodeConstants.STATUS_ENABLE+") A, TM_ORG B \n");
		sql.append("			    WHERE B.PARENT_ORG_ID = A.ORG_ID \n");
		sql.append(sql1);
		sql.append("				  AND B.COMPANY_ID="+loginInfo.getCompanyId() +" \n");
		sql.append("			    AND   STATUS = "+OemDictCodeConstants.STATUS_ENABLE+" ) A, TM_ORG B \n");
		sql.append("			    WHERE B.PARENT_ORG_ID = A.ORG_ID \n");
		sql.append(sql1);
		sql.append("				  AND B.COMPANY_ID="+loginInfo.getCompanyId() +" \n");
		sql.append("			    AND   STATUS = "+OemDictCodeConstants.STATUS_ENABLE+" \n");
		sql.append("          union all \n");
		sql.append("          SELECT B.ORG_ID,B.ORG_CODE,B.ORG_NAME,B.ORG_DESC, B.PARENT_ORG_ID \n");
		sql.append("			    FROM ( SELECT B.ORG_ID,B.ORG_CODE,B.ORG_NAME,B.ORG_DESC, B.PARENT_ORG_ID \n");
		sql.append("			    FROM (SELECT B.ORG_ID,B.ORG_CODE,B.ORG_NAME,B.ORG_DESC, B.PARENT_ORG_ID \n");
		sql.append("			    FROM (SELECT ORG_ID,ORG_CODE,ORG_NAME,ORG_DESC,PARENT_ORG_ID FROM TM_ORG \n");
		sql.append("			      WHERE 1=1 \n");
		sql.append(sql2);
		sql.append("			      AND   STATUS = "+OemDictCodeConstants.STATUS_ENABLE+") A, TM_ORG B \n");
		sql.append("			    WHERE B.PARENT_ORG_ID = A.ORG_ID \n");
		sql.append(sql1);
		sql.append("				  AND B.COMPANY_ID="+loginInfo.getCompanyId() +" \n");
		sql.append("			    AND   STATUS = "+OemDictCodeConstants.STATUS_ENABLE+" ) A, TM_ORG B \n");
		sql.append("			    WHERE B.PARENT_ORG_ID = A.ORG_ID \n");
		sql.append(sql1);
		sql.append("				  AND B.COMPANY_ID="+loginInfo.getCompanyId() +" \n");
		sql.append("			    AND   STATUS = "+OemDictCodeConstants.STATUS_ENABLE+" ) A, TM_ORG B \n");
		sql.append("			    WHERE B.PARENT_ORG_ID = A.ORG_ID \n");
		sql.append(sql1);
		sql.append("				  AND B.COMPANY_ID="+loginInfo.getCompanyId() +" \n");
		sql.append("			    AND   STATUS = "+OemDictCodeConstants.STATUS_ENABLE+" \n");
		sql.append("          union all \n");
		sql.append("          SELECT B.ORG_ID,B.ORG_CODE,B.ORG_NAME,B.ORG_DESC, B.PARENT_ORG_ID \n");
		sql.append("			    FROM (SELECT B.ORG_ID,B.ORG_CODE,B.ORG_NAME,B.ORG_DESC, B.PARENT_ORG_ID \n");
		sql.append("			    FROM ( SELECT B.ORG_ID,B.ORG_CODE,B.ORG_NAME,B.ORG_DESC, B.PARENT_ORG_ID \n");
		sql.append("			    FROM (SELECT B.ORG_ID,B.ORG_CODE,B.ORG_NAME,B.ORG_DESC, B.PARENT_ORG_ID \n");
		sql.append("			    FROM (SELECT ORG_ID,ORG_CODE,ORG_NAME,ORG_DESC,PARENT_ORG_ID FROM TM_ORG \n");
		sql.append("			      WHERE 1=1 \n");
		sql.append(sql2);
		sql.append("			      AND   STATUS = "+OemDictCodeConstants.STATUS_ENABLE+") A, TM_ORG B \n");
		sql.append("			    WHERE B.PARENT_ORG_ID = A.ORG_ID \n");
		sql.append(sql1);
		sql.append("				  AND B.COMPANY_ID="+loginInfo.getCompanyId() +" \n");
		sql.append("			    AND   STATUS = "+OemDictCodeConstants.STATUS_ENABLE+" ) A, TM_ORG B \n");
		sql.append("			    WHERE B.PARENT_ORG_ID = A.ORG_ID \n");
		sql.append(sql1);
		sql.append("				  AND B.COMPANY_ID="+loginInfo.getCompanyId() +" \n");
		sql.append("			    AND   STATUS = "+OemDictCodeConstants.STATUS_ENABLE+" ) A, TM_ORG B \n");
		sql.append("			    WHERE B.PARENT_ORG_ID = A.ORG_ID \n");
		sql.append(sql1);
		sql.append("				  AND B.COMPANY_ID="+loginInfo.getCompanyId() +" \n");
		sql.append("			    AND   STATUS = "+OemDictCodeConstants.STATUS_ENABLE+" ) A, TM_ORG B \n");
		sql.append("			    WHERE B.PARENT_ORG_ID = A.ORG_ID \n");
		sql.append(sql1);
		sql.append("				  AND B.COMPANY_ID="+loginInfo.getCompanyId() +" \n");
		sql.append("			    AND   STATUS = "+OemDictCodeConstants.STATUS_ENABLE+"                   \n");
		sql.append("          ) VIRTUAL \n");

		
//		if(dutyType!=0 && !OemDictCodeConstants.DUTY_TYPE_DEPT.equals(dutyType) && !OemDictCodeConstants.DUTY_TYPE_COMPANY.equals(dutyType)){
//			sql.append("  SELECT ORG_ID,ORG_CODE,ORG_NAME,ORG_DESC,'' PARENT_ORG_ID FROM TM_ORG A  \n");
//			sql.append("  WHERE 1=1   \n");
//			sql.append("  AND ORG_ID = "+parOrgId+"   \n");
//			sql.append("  AND   STATUS = "+OemDictCodeConstants.STATUS_ENABLE+"   \n");
//			sql.append(sql1);
//			sql.append("  union all   \n");
//			sql.append("  ELECT B.ORG_ID,ORG_CODE,ORG_NAME,ORG_DESC,PARENT_ORG_ID FROM TM_ORG B,(  \n");
//			sql.append("  SELECT ORG_ID FROM TM_ORG   \n");
//			sql.append("  WHERE ORG_ID = "+parOrgId+" AND   STATUS = "+OemDictCodeConstants.STATUS_ENABLE+"   \n");
//			sql.append(sql1);
//			sql.append("  ) A   \n");
//			sql.append("  WHERE B.PARENT_ORG_ID = A.ORG_ID  \n");
//		}else{
//			sql.append("  SELECT ORG_ID,ORG_CODE,ORG_NAME,ORG_DESC,PARENT_ORG_ID \n");
//			sql.append("  FROM TM_ORG \n");
//			sql.append("  WHERE ORG_TYPE = "+OemDictCodeConstants.ORG_TYPE_OEM+"  AND STATUS = "+OemDictCodeConstants.STATUS_ENABLE+" \n");
//			sql.append(sql1);
//			sql.append("  AND COMPANY_ID = "+loginInfo.getCompanyId() +" \n");
//			sql.append("  UNION ALL \n");
//			sql.append("  SELECT ORG_ID,ORG_CODE,ORG_NAME,ORG_DESC,'' PARENT_ORG_ID FROM TM_ORG \n");
//			sql.append("  WHERE PARENT_ORG_ID = -1 AND STATUS = "+OemDictCodeConstants.STATUS_ENABLE+" \n");
//			
//		}
		
		
		System.out.println(sql.toString());
		return OemDAOUtil.findAll(sql.toString(), params);
		
	}
	
	/**
	 * 根据CODE获取部门信息
	 * @param orgCode
	 * @return
	 */
	public Map getOrgByCode(String orgCode) {
		StringBuilder sql = new StringBuilder("SELECT ORG_ID,ORG_CODE,ORG_NAME FROM tm_org WHERE 1=1");
        List<Object> queryParams = new ArrayList<>();
        if(!StringUtils.isNullOrEmpty(orgCode)){
            sql.append(" and ORG_ID = ?");
            queryParams.add(orgCode);
        }
		return OemDAOUtil.findFirst(sql.toString(), queryParams);
	}

	
	
}
