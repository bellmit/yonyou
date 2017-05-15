package com.yonyou.dms.vehicle.dao.claimMgr;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.common.domains.PO.salesPlanManage.TmVhclMaterialGroupPO;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.warranty.TmVhclMaterialGroupDTO;

/**
 * MVS 家族维护
 * @author zhiahongmiao 
 *
 */
@Repository
public class OldPartQueryDlrDao extends OemBaseDAO{
	
	/**
	 *查询
	 */
	public PageInfoDto MVSFamilyMaintainQuery(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam, params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;

	}
	/**
	 * 索赔类型下拉框
	 */
	public List<Map> GetMVCCheXi(Map<String, String> queryParams) throws ServiceBizException {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		StringBuffer sql  = new StringBuffer("\n");
		if(OemDictCodeConstants.SPECIAL_DEALER_ZHONGJIN.startsWith(loginInfo.getDealerCode())){
			sql.append("select CLAIM_TYPE_CODE,CLAIM_TYPE from tt_wr_claimtype_dcs WHERE 1=1 \n");
		}else{
			sql.append("select CLAIM_TYPE_CODE,CLAIM_TYPE from tt_wr_claimtype_dcs WHERE 1=1 and claim_type_code not in ('T','V') \n");
		}
		return OemDAOUtil.findAll(sql.toString(), null);
	}
	/**
	 * 下载
	 */
	public List<Map> OldPartQueryDownload(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam, params);
		List<Map> orderList = OemDAOUtil.downloadPageQuery(sql, params);
		return orderList;
	}
	/**
	 * SQL组装   
	 * @param queryParam
	 * @param params
	 * @return
	 */
	private String getQuerySql(Map<String, String> queryParam, List<Object> params) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		StringBuffer sql = new StringBuffer("\n");
		sql.append("                    SELECT  TWC.OEM_COMPANY_ID,  \n");
		sql.append("		        TWRP.PART_CODE, #零件代码 \n");
		sql.append("		        TWRP.PART_NAME, #零件名称 \n");
		sql.append("		        TWC.CLAIM_NO, #索赔单号 \n");
		sql.append("		        CASE WHEN TWC.CLAIM_TYPE = 'A' THEN '服务活动' #索赔类型 \n");
		sql.append("		        	 WHEN TWC.CLAIM_TYPE = 'V' THEN 'VPC索赔' \n");
		sql.append("		        	 WHEN TWC.CLAIM_TYPE = 'G' THEN '善意索赔' \n");
		sql.append("		        	 WHEN TWC.CLAIM_TYPE = 'E' THEN '延保索赔' \n");
		sql.append("		        	 WHEN TWC.CLAIM_TYPE = 'E' THEN '延保索赔' \n");
		sql.append("		        	 WHEN TWC.CLAIM_TYPE = 'W' THEN '整车索赔' \n");
		sql.append("		        	 WHEN TWC.CLAIM_TYPE = 'M' THEN '零件索赔' \n");
		sql.append("		        	 WHEN TWC.CLAIM_TYPE = 'F' THEN '首保索赔' \n");
		sql.append("		        	 WHEN TWC.CLAIM_TYPE = 'S' THEN '召回索赔' \n");
		sql.append("		        	 WHEN TWC.CLAIM_TYPE = 'T' THEN '运输索赔' \n");
		sql.append("						END	CLAIM_TYPE, \n");
		sql.append("		        TWC.RO_NO, #工单号 \n");
		sql.append("		        TWR.VIN, #VIN \n");
		sql.append("		        TWR.MAKE_DATE  #申请日期 \n");
		sql.append("		     FROM tt_wr_claim_dcs TWC, \n");
		sql.append("		          tt_wr_repair_dcs TWR, \n");
		sql.append("		          tt_wr_repair_part_dcs TWRP \n");
		sql.append("				WHERE  TWC.RO_NO = TWR.REPAIR_NO AND  TWR.REPAIR_ID = TWRP.REPAIR_ID  \n");
		sql.append("		   AND TWC.DEALER_ID= '"+loginInfo.getDealerId()+"' \n");
		//索赔单号
		if(!StringUtils.isNullOrEmpty(queryParam.get("claimNo"))){
			sql.append("				and TWC.CLAIM_NO like '%"+queryParam.get("claimNo")+"%' \n");
		}
		//零件号
		if(!StringUtils.isNullOrEmpty(queryParam.get("partCode"))){
			sql.append("				and TWRP.PART_CODE like '%"+queryParam.get("partCode")+"%'  \n");
		}
		//索赔类型
		if(!StringUtils.isNullOrEmpty(queryParam.get("claimType"))){
			sql.append("				and TWC.CLAIM_TYPE = '"+queryParam.get("claimType")+"'  \n");
		}
		//申请日期
		if(!StringUtils.isNullOrEmpty(queryParam.get("claimStartDate"))&&!StringUtils.isNullOrEmpty(queryParam.get("claimEndDate"))){
			sql.append("				AND TWR.MAKE_DATE >= DATE_FORMAT('"+queryParam.get("claimStartDate")+"','%Y-%c-%d') \n");
			sql.append("			    AND TWR.MAKE_DATE <= DATE_FORMAT('"+queryParam.get("claimEndDate")+" 23:59:59','%Y-%c-%d %H:%i:%s')  \n");
		}
		//VIN
		if(!StringUtils.isNullOrEmpty(queryParam.get("VIN"))){
			sql.append("				and TWR.VIN like '%"+queryParam.get("VIN")+"%'  \n");
		}
		 	System.out.println("-----查询sql------"+sql.toString());
		return sql.toString();
	}
}
