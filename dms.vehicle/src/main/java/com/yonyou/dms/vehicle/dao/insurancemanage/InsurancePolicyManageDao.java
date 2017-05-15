package com.yonyou.dms.vehicle.dao.insurancemanage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.validator.internal.xml.ElementType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.yonyou.dms.common.domains.PO.salesPlanManage.TmVhclMaterialGroupPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.warranty.TmVhclMaterialGroupDTO;
import com.yonyou.dms.vehicle.domains.DTO.insurancemanage.CardCouponsDTO;
import com.yonyou.dms.vehicle.domains.PO.insurancemanage.TmInsuranceActivityDcsPO;
import com.yonyou.dms.vehicle.domains.PO.threePack.TtThreepackNovehiclePO;

/**
 * 
 * @author zhiahongmiao 
 *
 */
@Repository
public class InsurancePolicyManageDao extends OemBaseDAO{
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	/**
	 *查询
	 */
	public PageInfoDto InsurancePolicyManageQuery(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam, params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;

	}
	
	/**
	 * 下载
	 */
	public List<Map> insurancePolicyManangeDownload(Map<String, String> queryParam) {
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
		StringBuffer sql = new StringBuffer("\n");
		sql.append("        SELECT  \n");
		sql.append("		  TA.ID, -- 主键ID \n");
		sql.append("		  TA.ACTIVITY_CODE, -- 活动编号 \n");
		sql.append("		  TA.ACTIVITY_NAME, -- 活动名称 \n");
		sql.append("		  TA.ACTIVITY_ISSUE_NUMBER, -- 活动发放卡券数量上限 \n");
		sql.append("		  TA.ACTIVITY_START_DATE, -- 开始日期 \n");
		sql.append("			TA.ACTIVITY_END_DATE, -- 结束日期 \n");
		sql.append("		  TA.INSURANCE_COMPANY_CODE, -- 保险公司代码 \n");
		sql.append("		  TICM.INSURANCE_COMPANY_NAME, -- 保险公司名称 \n");
		sql.append("		  TA.RELEASE_STATUS, -- 发布状态 \n");
		sql.append("		  TA.RELEASE_DATE  -- 发布时间 \n");
		sql.append("		FROM tm_insurance_activity_dcs TA \n");
		sql.append("		LEFT JOIN  tt_insurance_company_main_dcs TICM ON TICM.INSURANCE_COMPANY_CODE=TA.INSURANCE_COMPANY_CODE \n");
		sql.append("		WHERE TA.IS_DEL <>'1' \n");
		//保险公司代码
		if(!StringUtils.isNullOrEmpty(queryParam.get("insuranceCompanyCode"))){
			sql.append("       AND UPPER(TA.INSURANCE_COMPANY_CODE) LIKE UPPER ('%"+queryParam.get("insuranceCompanyCode")+"%')  \n");
		}
		//保险公司名称
		if(!StringUtils.isNullOrEmpty(queryParam.get("insuranceCompanyName"))){
			sql.append("      AND TICM.INSURANCE_COMPANY_NAME LIKE '%"+queryParam.get("insuranceCompanyName")+"%'  \n");
		}
		//状态
		if(!StringUtils.isNullOrEmpty(queryParam.get("releaseStatus"))){
				sql.append("        AND TA.RELEASE_STATUS = '"+queryParam.get("releaseStatus")+"' \n");
		}
		//活动编号
		if(!StringUtils.isNullOrEmpty(queryParam.get("activityNo"))){
			sql.append("				AND TA.ACTIVITY_CODE LIKE '%"+queryParam.get("activityNo")+"%' \n");
		}
		//合作保险公司
		if(!StringUtils.isNullOrEmpty(queryParam.get("activityName"))){
			sql.append("				AND TA.ACTIVITY_NAME like '%"+queryParam.get("activityName")+"%' \n");
		}
		return sql.toString();
	}
	/**
	 *查询
	 */
	public PageInfoDto codeDescQuery(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getcodeDescSql(queryParam, params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;
	}
	private String getcodeDescSql(Map<String, String> queryParam, List<Object> params) {
		StringBuffer sql = new StringBuffer("\n");
		sql.append("        select CODE_DESC,CODE_ID from tc_code_dcs  \n");
		sql.append("		 where code_id in(41001001,41001002,41001003,41001004,41001005,41001007,41001008,41001009,41001010) \n");
		sql.append("		 and status=10011001 \n");
		return sql.toString();
	}
	/**
	 *查询
	 */
	public PageInfoDto insurancecompanyQuery(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = insurancecompanyQuerySql(queryParam, params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;
	}
	private String insurancecompanyQuerySql(Map<String, String> queryParam, List<Object> params) {
		StringBuffer sql = new StringBuffer("\n");
		sql.append("	SELECT  \n");
		sql.append("		  TICM.INS_COMPANY_ID,-- ID \n");
		sql.append("		  TICM.INSURANCE_COMPANY_CODE,  -- 保险公司code  \n");
		sql.append("		  TICM.INSURANCE_COMPANY_NAME,  -- 保险公司name  \n");
		sql.append("		  TICM.INS_COMPANY_SHORT_NAME,  -- 保险公司简称  \n");
		sql.append("		  case when TICM.IS_CO_INSURANCE_COMPANY = 1 then '是' else '否' end IS_CO_INSURANCE_COMPANY  -- 合作保险公司 \n");
		sql.append("		FROM \n");
		sql.append("		  tt_insurance_company_main_dcs TICM \n");
		sql.append("		 LEFT JOIN tt_insurance_company_dcs TIC ON TICM.INS_COMPANY_ID = TIC.INS_COMPANY_ID \n");
		sql.append("		WHERE 1=1 \n");
		//保险公司代码
		if(!StringUtils.isNullOrEmpty(queryParam.get("insuranceCompanyCode"))){
			sql.append("       AND UPPER(TICM.INSURANCE_COMPANY_CODE) LIKE UPPER ('%"+queryParam.get("insuranceCompanyCode")+"%')  \n");
		}
		//保险公司名称
		if(!StringUtils.isNullOrEmpty(queryParam.get("insuranceCompanyName"))){
			sql.append("      AND TICM.INS_COMPANY_SHORT_NAME like'%"+queryParam.get("insuranceCompanyName")+"%'  \n");
		}
		//状态
		if(!StringUtils.isNullOrEmpty(queryParam.get("isCoCompany"))){
			if(queryParam.get("isCoCompany").equals("1")){
				sql.append("       AND TICM.IS_CO_INSURANCE_COMPANY = 1 \n");
			}else{
				sql.append("       AND ((TICM.IS_CO_INSURANCE_COMPANY = 0) or (TICM.IS_CO_INSURANCE_COMPANY is null)) \n");
			}
		}
		return sql.toString();
	}
	/**
	 * 新增
	 */
	public long Add(CardCouponsDTO ccDTO) throws ServiceBizException {
//		StringBuffer sb = new StringBuffer(
//				"select vin from TT_THREEPACK_NOVEHICLE_DCS where is_del='0' and vin=? ");
//		List<Object> list = new ArrayList<Object>();
//		list.add(tcdto.getVin());
//		List<Map> map = OemDAOUtil.findAll(sb.toString(), list);
			TmInsuranceActivityDcsPO tiadPO = new TmInsuranceActivityDcsPO();
			setTt(tiadPO, ccDTO);
			tiadPO.saveIt();
			return (Long) tiadPO.getLongId();
	}
	private void setTt(TmInsuranceActivityDcsPO tiadPO, CardCouponsDTO ccDTO) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		tiadPO.setString("ACTIVITY_NAME",ccDTO.getActName());
		tiadPO.setString("INSURANCE_COMPANY_CODE",ccDTO.getInsuranceCompanyCode());
		tiadPO.setDate("ACTIVITY_START_DATE",ccDTO.getActStartDate());
		tiadPO.setDate("ACTIVITY_END_DATE",ccDTO.getActEndDate());
		tiadPO.setString("ACTIVITY_ISSUE_NUMBER",ccDTO.getActIssueNumber());
		tiadPO.setString("RELEASE_STATUS","92201001");
		tiadPO.setString("ACTIVITY_CODE","");
		tiadPO.setString("ACTIVITY_CODE","");
		tiadPO.setDate("CREATE_DATE", new Date());
		tiadPO.setDate("CREATE_BY", loginInfo.getUserId());
	}
}
