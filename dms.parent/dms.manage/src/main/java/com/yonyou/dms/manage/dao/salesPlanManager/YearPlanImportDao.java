package com.yonyou.dms.manage.dao.salesPlanManager;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.domains.DTO.ImportResultDto;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.manage.domains.DTO.salesPlanManager.TmpVsYearlyPlanDTO;
import com.yonyou.dms.manage.domains.PO.salesPlanManager.TmpVsYearlyPlanPO;
import com.yonyou.dms.manage.domains.PO.salesPlanManager.TtVsYearlyPlanDetailPO;
import com.yonyou.dms.manage.domains.PO.salesPlanManager.TtVsYearlyPlanPO;
/**
 * 
* @ClassName: DlrForecastQueryDao 
* @Description: 年度目标上传 
* @author zhengzengliang
* @date 2017年2月17日 下午5:48:15 
*
 */
@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
@Repository
public class YearPlanImportDao extends OemBaseDAO{

	/**
	 * 
	* @Title: deleteTmpVsYearlyPlan 
	* @Description:  清空临时表中目标年度的数据 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void deleteTmpVsYearlyPlan() {
		//获取当前用户
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		TmpVsYearlyPlanPO.deleteAll();
	}

	/**
	 * 
	* @Title: insertTmpVsYearlyPlan 
	* @Description: 年度目标上传(导入临时表)
	* @param @param rowDto    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void insertTmpVsYearlyPlan(TmpVsYearlyPlanDTO tvypDTO) {
		TmpVsYearlyPlanPO tvypPO = new TmpVsYearlyPlanPO();
		//设置对象属性
		setTmpVsYearlyPlanPO(tvypPO, tvypDTO);
		tvypPO.insert();
	}
	private void setTmpVsYearlyPlanPO(TmpVsYearlyPlanPO tvypPO, 
			TmpVsYearlyPlanDTO tvypDTO) {
		tvypPO.setString("ROW_NUMBER", tvypDTO.getRowNO());
		tvypPO.setString("DEALER_CODE", tvypDTO.getDealerCode());
		//1经销商名称开始
		StringBuilder sqlSb = new StringBuilder();
		List<Object> params2 = new ArrayList<Object>();
        sqlSb.append("SELECT td.DEALER_NAME FROM tm_dealer td WHERE 1=1 AND td.DEALER_CODE = ? \n" );
        params2.add(tvypDTO.getDealerCode());
		List<Map> tdList = OemDAOUtil.findAll(sqlSb.toString(), params2);
		if(tdList.size() > 0){
			tvypPO.setString("DEALER_NAME", tdList!=null?tdList.get(0).get("DEALER_NAME"):"");
		}
		//1经销商名称结束
		tvypPO.setString("GROUP_CODE", tvypDTO.getGroupCode());
		//2名称开始
		StringBuilder sqlSb2 = new StringBuilder();
		List<Object> params3 = new ArrayList<Object>();
        sqlSb2.append("SELECT tvmg.GROUP_NAME FROM tm_vhcl_material_group tvmg WHERE 1=1 AND tvmg.GROUP_CODE = '" + tvypDTO.getGroupCode() + "'");
		List<Map> result2 = OemDAOUtil.findAll(sqlSb2.toString(), params3);
		if(result2.size()>0) {
			tvypPO.setString("GROUP_NAME", result2!=null?result2.get(0).get("GROUP_NAME"):"");
		}else{
			tvypPO.setString("GROUP_NAME", "");
		}
		//2名称结束
		tvypPO.setString("PLAN_YEAR", tvypDTO.getYear());
		tvypPO.setString("PLAN_TYPE", tvypDTO.getPlanType());
		tvypPO.setString("SUM_AMT", tvypDTO.getSumAmt());
		tvypPO.setString("JAN_AMT", tvypDTO.getJanAmt());
		tvypPO.setString("FEB_AMT", tvypDTO.getFebAmt());
		tvypPO.setString("MAR_AMT", tvypDTO.getMarAmt());
		tvypPO.setString("APR_AMT", tvypDTO.getAprAmt());
		tvypPO.setString("MAY_AMOUNT", tvypDTO.getMayAmount());
		tvypPO.setString("JUN_AMT", tvypDTO.getJunAmt());
		tvypPO.setString("JUL_AMT", tvypDTO.getJulAmt());
		tvypPO.setString("AUG_AMT", tvypDTO.getAugAmt());
		tvypPO.setString("SEP_AMT", tvypDTO.getSepAmt());
		tvypPO.setString("OCT_AMT", tvypDTO.getOctAmt());
		tvypPO.setString("NOV_AMT", tvypDTO.getNovAmt());
		tvypPO.setString("DEC_AMT", tvypDTO.getDecAmt());
		//获取当前用户
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		tvypPO.setString("USER_ID", loginInfo.getUserId().toString());
	}

	/**
	 * 
	* @Title: checkData 
	* @Description: 年度目标上传（ 校验临时表数据） 
	* @param @param rowDto
	* @param @return    设定文件 
	* @return List<ImportResultDto>    返回类型 
	* @throws
	 */
	public ImportResultDto<TmpVsYearlyPlanDTO> checkData(TmpVsYearlyPlanDTO rowDto) {
		String year = rowDto.getYear();
		//获取当前用户
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		StringBuilder fullQuery = new StringBuilder();
		List<Object> params = new ArrayList<Object>();
		fullQuery.append("SELECT * FROM tmp_vs_yearly_plan tvyp WHERE 1=1 AND tvyp.PLAN_YEAR = " + rowDto.getYear() + " AND tvyp.USER_ID = " + loginInfo.getUserId().toString());
		List<Map> tvypList = OemDAOUtil.findAll(fullQuery.toString(), params);
		if(tvypList == null || tvypList.size()<0){
			tvypList = new ArrayList<Map>();
		}
		TmpVsYearlyPlanPO tvypPO = null;
		Map<String,Object> tvypMap = new HashMap<String,Object>();
		StringBuffer errorInfo = new StringBuffer("");
		boolean isError = false;
		ArrayList<TmpVsYearlyPlanDTO> tvypDTOList = new ArrayList<TmpVsYearlyPlanDTO>();
		ImportResultDto<TmpVsYearlyPlanDTO> importResult = new ImportResultDto<TmpVsYearlyPlanDTO>();
		for(int i=0; i < tvypList.size(); i++){
			tvypMap = tvypList.get(i);
			// 取得行号
			Integer rowNum = Integer.valueOf((String) tvypMap.get("ROW_NUMBER"));
			// 校验合计
			String numCheck = checkSumAmt(tvypMap);
			if (numCheck.length() != 0) {
				isError = true;
				errorInfo.append(numCheck);
			}
			if (errorInfo.length() > 0) {
				String info = errorInfo.substring(0, errorInfo.length() - 1);
				rowDto.setRowNO(new Integer(rowNum));
				rowDto.setErrorMsg(info);
				tvypDTOList.add(rowDto);
				importResult.setErrorList(tvypDTOList);
				errorInfo.delete(0, errorInfo.length());
			}
		}
		// 年度目标校验经销商代码是否存在
		List<Object> params2 = new ArrayList<Object>();
		String fullQuery2 = getOemYearPlanCheckOrgSql(year, loginInfo);
		List<Map> notExistsOrgList = OemDAOUtil.findAll(fullQuery2, params2);
		if (null != notExistsOrgList && notExistsOrgList.size() > 0) {
			for (int i = 0; i < notExistsOrgList.size(); i++) {
				Map<String, Object> map = notExistsOrgList.get(i);
				isError = true;
				TmpVsYearlyPlanDTO rowDto2 = new TmpVsYearlyPlanDTO();
				rowDto.setRowNO(Integer.parseInt(map.get("ROW_NUMBER").toString()));
				rowDto.setErrorMsg("经销商代码不存在");
				tvypDTOList.add(rowDto);
				importResult.setErrorList(tvypDTOList);
			}
		}
		// 年度目标校验车系代码是否存在
		List<Object> params3 = new ArrayList<Object>();
		String fullQuery3 = getOemYearPlanCheckGroupSql(year, loginInfo);
		List<Map> notExistsGroupList = OemDAOUtil.findAll(fullQuery3, params3);
		if (null != notExistsGroupList && notExistsGroupList.size() > 0) {
			for (int i = 0; i < notExistsGroupList.size(); i++) {
				Map<String, Object> map = notExistsGroupList.get(i);
				isError = true;
				TmpVsYearlyPlanDTO rowDto2 = new TmpVsYearlyPlanDTO();
				rowDto.setRowNO(Integer.parseInt(map.get("ROW_NUMBER").toString()));
				rowDto.setErrorMsg("车系代码不存在");
				tvypDTOList.add(rowDto);
				importResult.setErrorList(tvypDTOList);
			}
		}
		// 临时表校验重复数据
		List<Object> params4 = new ArrayList<Object>();
		String fullQuery4 = getOemTalbeCheckDumpSql(year, loginInfo);
		List<Map> dumpList = OemDAOUtil.findAll(fullQuery4, params4);
		String errorSb = "";
		if (null != dumpList && dumpList.size() > 0) {
			isError = true;
			for (int i = 0; i < dumpList.size(); i++) {
				Map<String, Object> map = dumpList.get(i);
				errorSb+=map.get("ROW_NUMBER").toString();
				if(i<dumpList.size()-2){
					errorSb+=",";
				}
			}
			Map<String, Object> map2 = dumpList.get(dumpList.size()-1);
			TmpVsYearlyPlanDTO rowDto2 = new TmpVsYearlyPlanDTO();
			rowDto.setRowNO(Integer.parseInt(map2.get("ROW_NUMBER").toString()));
			rowDto.setErrorMsg("与第"+errorSb+"行数据重复");
			tvypDTOList.add(rowDto);
			importResult.setErrorList(tvypDTOList);
		}
		if (isError) {
			return importResult;
		} else {
			return null;
		}
	}
	// 临时表校验重复数据
	private String getOemTalbeCheckDumpSql(String year, LoginInfoDto loginInfo) {
		StringBuilder sql = new StringBuilder();
		sql.append("select p1.ROW_NUMBER\n");
		sql.append("  from TMP_VS_YEARLY_PLAN p1\n");
		sql.append(" where exists (\n");
		sql.append("   select 1\n");
		sql.append("   from TMP_VS_YEARLY_PLAN p2\n");
		sql.append("   where p1.GROUP_CODE = p2.GROUP_CODE\n");
		sql.append("   and p1.DEALER_CODE = p2.DEALER_CODE\n");
		sql.append("   and p1.ROW_NUMBER <> p2.ROW_NUMBER\n");
		sql.append("   and p1.USER_ID =" + loginInfo.getUserId() + "\n");
		sql.append("   and p1.PLAN_YEAR = p2.PLAN_YEAR)\n");
		return sql.toString();
	}
	// 年度目标校验车系代码是否存在
	private String getOemYearPlanCheckGroupSql(String year, LoginInfoDto loginInfo) {
		StringBuilder sql = new StringBuilder();
		sql.append("select p.ROW_NUMBER\n");
		sql.append("  from TMP_VS_YEARLY_PLAN p\n");
		sql.append(" where 1 = 1\n");
		sql.append("   and p.PLAN_YEAR = " + year + "\n");
		sql.append("   and p.USER_ID =" + loginInfo.getUserId() + "\n");
		sql.append("   and not exists (select 1\n");
		sql.append("       from TM_VHCL_MATERIAL_GROUP g\n");
		sql.append("      where g.GROUP_CODE = p.GROUP_CODE\n");
		sql.append("        and g.GROUP_LEVEL = "+OemDictCodeConstants.TM_VHCL_MATERIAL_GROUP_CAR+"\n");
		/*
		 * 写死业务范围 Begin
		 */
		sql.append("   AND G.GROUP_TYPE = '" + OemDictCodeConstants.GROUP_TYPE_IMPORT + "'\n");
		/*
		 * 写死业务范围 End..
		 */
		sql.append("        and g.STATUS = " + OemDictCodeConstants.STATUS_ENABLE + "\n");// 有效
		sql.append("        and g.OEM_COMPANY_ID = " + loginInfo.getCompanyId() + ")\n");
		sql.append("   order by CAST(p.ROW_NUMBER AS SIGNED) asc");
		return sql.toString();
	}

	// 年度目标校验经销商代码是否存在
	private String getOemYearPlanCheckOrgSql(String year,LoginInfoDto loginInfo) {
		StringBuilder sql = new StringBuilder();
		sql.append("select p.ROW_NUMBER\n");
		sql.append("  from TMP_VS_YEARLY_PLAN p\n");
		sql.append(" where 1 = 1\n");
		sql.append("   and p.PLAN_YEAR = " + year + "\n");
		sql.append("   and p.USER_ID =" + loginInfo.getUserId() + "\n");
		sql.append("   and not exists (select 1\n");
		sql.append("          from TM_DEALER TD\n");
		sql.append("          where TD.DEALER_CODE = p.DEALER_CODE\n");
		sql.append("            and TD.OEM_COMPANY_ID =  " + loginInfo.getCompanyId() + "\n");
		sql.append("            and TD.STATUS = " + OemDictCodeConstants.STATUS_ENABLE + "\n");// 有效
		sql.append("            and TD.DEALER_TYPE = " + OemDictCodeConstants.DEALER_TYPE_DVS + ")\n");// 经销商类型(整车)
		sql.append("   order by  CAST(p.ROW_NUMBER AS SIGNED) asc");
		return sql.toString();
	}

	/*
	 * 校验合计
	 */
	private String checkSumAmt(Map<String,Object> map) {
		Integer sumTmp = new Integer(0);
		StringBuffer errors = new StringBuffer("");
		try {
			Integer janAmt = Integer.valueOf((String) map.get("JAN_AMT"));
			sumTmp += janAmt;
		} catch (Exception e) {
			errors.append("1月数量必须是整数,");
		}
		try {
			Integer febAmt = Integer.valueOf((String)map.get("FEB_AMT"));
			sumTmp += febAmt;
		} catch (Exception e) {
			errors.append("2月数量必须是整数,");
		}
		try {
			Integer marAmt = Integer.valueOf((String) map.get("MAR_AMT"));
			sumTmp += marAmt;
		} catch (Exception e) {
			errors.append("3月数量必须是整数,");
		}
		try {
			Integer aprAmt = Integer.valueOf((String) map.get("APR_AMT")); 
			sumTmp += aprAmt;
		} catch (Exception e) {
			errors.append("4月数量必须是整数,");
		}
		try {
			Integer mayAmt = Integer.valueOf((String) map.get("MAY_AMOUNT")); 
			sumTmp += mayAmt;
		} catch (Exception e) {
			errors.append("5月数量必须是整数,");
		}
		try {
			Integer junAmt = Integer.valueOf((String) map.get("JUN_AMT")); 
			sumTmp += junAmt;
		} catch (Exception e) {
			errors.append("6月数量必须是整数,");
		}
		try {
			Integer julAmt = Integer.valueOf((String) map.get("JUL_AMT")); 
			sumTmp += julAmt;
		} catch (Exception e) {
			errors.append("7月数量必须是整数,");
		}
		try {
			Integer augAmt = Integer.valueOf((String) map.get("AUG_AMT")); 
			sumTmp += augAmt;
		} catch (Exception e) {
			errors.append("8月数量必须是整数,");
		}
		try {
			Integer sepAmt = Integer.valueOf((String) map.get("SEP_AMT"));
			sumTmp += sepAmt;
		} catch (Exception e) {
			errors.append("9月数量必须是整数,");
		}
		try {
			Integer octAmt = Integer.valueOf((String) map.get("OCT_AMT"));
			sumTmp += octAmt;
		} catch (Exception e) {
			errors.append("10月数量必须是整数,");
		}
		try {
			Integer novAmt = Integer.valueOf((String) map.get("NOV_AMT")); 
			sumTmp += novAmt;
		} catch (Exception e) {
			errors.append("11月数量必须是整数,");
		}
		try {
			Integer decAmt = Integer.valueOf((String) map.get("DEC_AMT")); 
			sumTmp += decAmt;
		} catch (Exception e) {
			errors.append("12月数量必须是整数,");
		}
		if (errors.length() == 0) {
			
			if (sumTmp.longValue() != Integer.valueOf((String) map.get("SUM_AMT")).longValue()) {
				errors.append("合计错误,");
			}
		}
		return errors.toString();
	}

	/**
	 * 
	* @Title: oemSelectTmpYearPlan 
	* @Description: 年度目标上传（确认查询）
	* @param @param rowDto
	* @param @return    设定文件 
	* @return PageInfoDto    返回类型 
	* @throws
	 */
	public List<Map> oemSelectTmpYearPlan(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getTmpYearPlanSql(queryParam,params);
		List<Map> list = OemDAOUtil.findAll(sql,params);
		return list;
	}
	private String getTmpYearPlanSql(Map<String, String> queryParam, List<Object> params) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT TMP.DEALER_CODE,\n");
		sql.append("       TMP.DEALER_NAME,\n");
		sql.append("       TMP.GROUP_CODE,\n");
		sql.append("       TMP.GROUP_NAME,\n");
		sql.append("       TMP.ROW_NUMBER,\n");
		sql.append("       TMP.SUM_AMT,\n");
		sql.append("       TMP.JAN_AMT,\n");
		sql.append("       TMP.FEB_AMT,\n");
		sql.append("       TMP.MAR_AMT,\n");
		sql.append("       TMP.APR_AMT,\n");
		sql.append("       TMP.MAY_AMOUNT,\n");
		sql.append("       TMP.JUN_AMT,\n");
		sql.append("       TMP.JUL_AMT,\n");
		sql.append("       TMP.AUG_AMT,\n");
		sql.append("       TMP.SEP_AMT,\n");
		sql.append("       TMP.OCT_AMT,\n");
		sql.append("       TMP.NOV_AMT,\n");
		sql.append("       TMP.DEC_AMT\n");
		sql.append("  FROM TMP_VS_YEARLY_PLAN TMP,TM_VHCL_MATERIAL_GROUP TVMG\n");
		sql.append("   WHERE TVMG.GROUP_CODE = TMP.GROUP_CODE\n");
		sql.append("   AND TMP.PLAN_YEAR = ? \n");
		params.add(queryParam.get("year"));
		//获取当前用户
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		sql.append("   AND TMP.USER_ID = ? \n");
		params.add(loginInfo.getUserId());
		sql.append("   ORDER BY TMP.ROW_NUMBER ASC");
		System.out.println(sql.toString());
		return sql.toString();
	}

	/**
	 * 
	* @Title: getTmpTtVsYearlyPlanTotal 
	* @Description: 获取年度导入临时表总记录数
	* @param @param queryParam
	* @param @return    设定文件 
	* @return int    返回类型 
	* @throws
	 */
	public int getTmpTtVsYearlyPlanTotal(Map<String, String> queryParam) {
		StringBuilder sql = new StringBuilder();
		sql.append("select 1 from TMP_VS_YEARLY_PLAN where USER_ID=? and PLAN_YEAR=?");
		List<Object> params = new ArrayList<Object>();
		//获取当前用户
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		params.add(loginInfo.getUserId());
		params.add(queryParam.get("year"));
		List<Map> tvypList = OemDAOUtil.findAll(sql.toString(), params);
		int total = tvypList.size();
		return total;
	}

	/**
	 * 
	* @Title: checkImportData 
	* @Description:  校验待导入的数据版本是否一致 
	* @param @param string
	* @param @return    设定文件 
	* @return PageInfoDto    返回类型 
	* @throws
	 */
	public List<Map> checkImportData(String flag) {
		List<Object> params = new ArrayList<Object>();
		String sql = getcheckImportDataSql(flag,params);
		List<Map> list = OemDAOUtil.findAll(sql,params);
		return list;
	}
	private String getcheckImportDataSql(String flag, List<Object> params) {
		StringBuilder sql = new StringBuilder();
		sql.append("select p.ROW_NUMBER\n");
		sql.append("    from TMP_VS_YEARLY_PLAN p\n");
		sql.append("    where 1=1\n");
		sql.append("      AND "+flag+" exists (select 1 from (select TD.DEALER_ID,TD.DEALER_CODE,TVMG.GROUP_ID,TVMG.GROUP_CODE,TVYP.PLAN_YEAR,TVYP.PLAN_TYPE\n");
		sql.append("                                       from TM_DEALER                     TD,\n");
		sql.append("                                            TM_VHCL_MATERIAL_GROUP        TVMG,\n");
		sql.append("                                            TT_VS_YEARLY_PLAN             TVYP,\n");
		sql.append("                                            TT_VS_YEARLY_PLAN_DETAIL      TVYPD\n");
		sql.append("                                       where TD.DEALER_ID = TVYP.DEALER_ID\n");
		sql.append("                                         AND TVMG.GROUP_ID = TVYPD.MATERIAL_GROUPID\n");
		sql.append("                                         AND TVYP.PLAN_ID = TVYPD.PLAN_ID");
		sql.append("                                  )         t\n");
		sql.append("                           where p.DEALER_CODE = t.DEALER_CODE\n");
		sql.append("                             AND p.GROUP_CODE = t.GROUP_CODE\n");
		sql.append("                             AND p.PLAN_TYPE = t.PLAN_TYPE\n");
		sql.append("                             AND p.PLAN_YEAR = t.PLAN_YEAR\n");
		sql.append("                  )");
		sql.append("    order by CAST(p.ROW_NUMBER AS SIGNED) asc");
		return sql.toString();
	}

	
	/**
	 * 
	* @Title: selectTtVsYearlyPlan 
	* @Description:查询要删除的结果集 
	* @param @param year
	* @param @return    设定文件 
	* @return List<TtVsYearlyPlanPO>    返回类型 
	* @throws
	 */
	public List<TtVsYearlyPlanPO> selectTtVsYearlyPlan(String year) {
		//获取当前用户
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		List<TtVsYearlyPlanPO> tvypList = TtVsYearlyPlanPO.find("PLAN_YEAR = ? AND CREATE_BY = ? AND STATUS = ?",year,loginInfo.getUserId(),OemDictCodeConstants.PLAN_MANAGE_01 );
		return tvypList;
	}

	/**
	 * 
	* @Title: deleteTtVsYearlyPlanDetail 
	* @Description: 年度目标上传  删除明细表 
	* @param @param detailPo    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void deleteTtVsYearlyPlanDetail(TtVsYearlyPlanDetailPO detailPo) {
		TtVsYearlyPlanDetailPO.delete(" PLAN_ID = ?",detailPo.getLong("PLAN_ID"));
	}

	/**
	 * 
	* @Title: clearUserYearlyPlan 
	* @Description: 年度目标上传 删除业务表 
	* @param @param actPo    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void clearUserYearlyPlan(TtVsYearlyPlanPO actPo) {
		TtVsYearlyPlanPO.delete(" PLAN_ID = ?",actPo.getLong("PLAN_ID"));
	}

	/**
	 * 
	* @Title: selectTmpVsYearlyPlan 
	* @Description: 年度目标上传 查询要插入到业务表的临时表数据
	* @param @param year
	* @param @return    设定文件 
	* @return List<TmpVsYearlyPlanPO>    返回类型 
	* @throws
	 */
	public List<TmpVsYearlyPlanPO> selectTmpVsYearlyPlan(String year) {
		//获取当前用户
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		List<TmpVsYearlyPlanPO> tvypList = TmpVsYearlyPlanPO.find("PLAN_YEAR = ? AND USER_ID = ?",year,loginInfo.getUserId().toString() );
		return tvypList;
	}

	/**
	 * 
	* @Title: findMaxPlanVer 
	* @Description: 年度目标上传
	* @param @param year
	* @param @param planType
	* @param @return    设定文件 
	* @return List<Map>    返回类型 
	* @throws
	 */
	public List<Map> findMaxPlanVer(String year, String planType) {
		List<Object> params = new ArrayList<Object>();
		String sql = getFindMaxPlanVerSQL(year,planType,params);
		List<Map> list = OemDAOUtil.findAll(sql, params);
		return list;
	}
	private String getFindMaxPlanVerSQL(String year, String planType,List<Object> params) {
		StringBuilder sql = new StringBuilder();
		sql.append("select max(PLAN_VER) PLAN from TT_VS_YEARLY_PLAN   TVYP\n");
		sql.append("    where TVYP.PLAN_YEAR=?\n");
		params.add(year);
		sql.append("      and TVYP.PLAN_TYPE=?\n");
		params.add(planType);
		sql.append("      and exists(select  t.DEALER_ID\n");
		sql.append("                     from (select TD.DEALER_ID from TMP_VS_YEARLY_PLAN     TVYP,\n");
		sql.append("                                                    TM_DEALER               TD\n");
		sql.append("                              where TVYP.DEALER_CODE = TD.DEALER_CODE)   t\n");
		sql.append("                    where t.DEALER_ID = TVYP.DEALER_ID)\n");
		sql.append("  having max(PLAN_VER) >0\n");
		return sql.toString();
	}

	/**
	 * 
	* @Title: selectUnique 
	* @Description: 年度目标上传 
	* @param @param po
	* @param @return    设定文件 
	* @return TmDealerPO    返回类型 
	* @throws
	 */
	public Map selectUnique(TmpVsYearlyPlanPO po) {
		
		StringBuilder sql = new StringBuilder();
		List<Object> params = new ArrayList<Object>();
		sql.append("SELECT td.* FROM tm_dealer td WHERE 1=1 AND td.DEALER_CODE = '" + po.getString("DEALER_CODE")+"'");
		List<Map> groupList = OemDAOUtil.findAll(sql.toString(), params);
		if (null == groupList || groupList.size() == 0) {
			throw new IllegalArgumentException("Not found record.");
		}
		if (groupList.size() > 1) {
			throw new IllegalArgumentException("Not unique record.");
		}
		return groupList.get(0);
	}

	/**
	 * 
	* @Title: getGroupId 
	* @Description: 年度目标上传 
	* @param @param po
	* @param @return    设定文件 
	* @return Long    返回类型 
	* @throws
	 */
	public Long getGroupId(TmpVsYearlyPlanPO po) {
//		List<TmVhclMaterialGroupPO> groupList = TmVhclMaterialGroupPO.find("GROUP_CODE = ?","'"+po.getString("GROUP_CODE")+"'");
		StringBuilder sql = new StringBuilder();
		List<Object> params = new ArrayList<Object>();
		sql.append("SELECT tvmp.GROUP_ID FROM tm_vhcl_material_group tvmp WHERE 1=1 AND GROUP_CODE = '" + po.getString("GROUP_CODE")+"'");
		List<Map> groupList = OemDAOUtil.findAll(sql.toString(), params);
		if (null == groupList || groupList.size() == 0) {
			return null;
		} else {
			return  (Long) groupList.get(0).get("GROUP_ID");
		}
	}

	/**
	 * 
	* @Title: findExistData 
	* @Description: 年度目标上传 
	* @param @param object
	* @param @param groupId
	* @param @param year
	* @param @return    设定文件 
	* @return List<Map<String,Object>>    返回类型 
	* @throws
	 */
	public List<Map> findExistData(Map tmdPo, Long groupId, String year) {
		List<Object> params = new ArrayList<Object>();
		String sql = getfindExistDataSQL(tmdPo,groupId,year,params);
		List<Map> list = OemDAOUtil.findAll(sql, params);
		return list;
	}
	private String getfindExistDataSQL(Map tmdPo, Long groupId, String year, List<Object> params) {
		StringBuilder sql = new StringBuilder();
		sql.append("select 1 from TT_VS_YEARLY_PLAN           TVMP,\n");
		sql.append("     TT_VS_YEARLY_PLAN_DETAIL    TVMPD\n");
		sql.append("         where TVMP.PLAN_ID = TVMPD.PLAN_ID\n");
		sql.append("           AND TVMP.DEALER_ID=?\n");
		params.add(tmdPo.get("DEALER_ID"));
		sql.append("           AND TVMPD.MATERIAL_GROUPID=?\n");
		params.add(groupId);
		sql.append("           AND TVMP.PLAN_YEAR=?\n");
		params.add(year);
		return sql.toString();
	}

	/**
	 * 
	* @Title: setTtVsYearlyPlan 
	* @Description: 年度目标上传 
	* @param @param vPo
	* @param @param year
	* @param @param tmdPo
	* @param @param plan
	* @param @param planType    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void setTtVsYearlyPlan(TtVsYearlyPlanPO vPo, String year, Map tmdPo, int plan, String planType) {
		//获取当前用户
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		vPo.setLong("OEM_COMPANY_ID", loginInfo.getOemCompanyId());
		vPo.setInteger("PLAN_YEAR", Integer.valueOf(year));
		vPo.setLong("ORG_ID", loginInfo.getOrgId());
		vPo.setInteger("ORG_TYPE", loginInfo.getOrgType());
		vPo.setLong("DEALER_ID", tmdPo.get("DEALER_ID"));
		vPo.setInteger("STATUS", OemDictCodeConstants.PLAN_MANAGE_02);
		vPo.setInteger("PLAN_VER", plan+1);
		vPo.setInteger("PLAN_TYPE", Integer.valueOf(planType));
		vPo.setInteger("VER", new Integer(0));
		vPo.setTimestamp("CREATE_DATE", new Date());
		vPo.setLong("CREATE_BY", loginInfo.getUserId());
	}

	/**
	 * 
	* @Title: setTtVsYearlyPlan2 
	* @Description: 年度目标上传 
	* @param @param ttPo
	* @param @param year
	* @param @param tmdPo
	* @param @param planType    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void setTtVsYearlyPlan2(TtVsYearlyPlanPO ttPo, String year, Map tmdPo, String planType) {
		//获取当前用户信息
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		ttPo.setLong("OEM_COMPANY_ID", loginInfo.getOemCompanyId());
		ttPo.setInteger("PLAN_YEAR", Integer.valueOf(year));
		ttPo.setLong("ORG_ID", loginInfo.getOrgId());
		ttPo.setInteger("ORG_TYPE", loginInfo.getOrgType());
		ttPo.setLong("DEALER_ID", tmdPo.get("DEALER_ID"));
		ttPo.setInteger("STATUS", OemDictCodeConstants.PLAN_MANAGE_02);
		ttPo.setInteger("PLAN_VER", new Integer(1));
		ttPo.setInteger("PLAN_TYPE", Integer.valueOf(planType));
		ttPo.setInteger("VER", new Integer(0));
		ttPo.setTimestamp("CREATE_DATE", new Date());
		ttPo.setLong("CREATE_BY", loginInfo.getUserId());
	}

	/**
	 * 
	* @Title: setDetailPo 
	* @Description: 年度目标上传  插入数据到明细表 
	* @param @param detailPo
	* @param @param groupCode
	* @param @param planId
	* @param @param month
	* @param @param saleAmt
	* @param @param userId    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void setDetailPo(TtVsYearlyPlanDetailPO detailPo, String groupCode, Long planId, Integer month,
			Integer saleAmt, Long userId) {
		detailPo.setLong("PLAN_ID", planId);
		detailPo.setInteger("PLAN_MONTH", month);
		detailPo.setInteger("SALE_AMOUNT", saleAmt);
		TmpVsYearlyPlanPO po = new TmpVsYearlyPlanPO();
		po.setString("GROUP_CODE", groupCode);
		Long groupId = getGroupId(po);
		detailPo.setLong("MATERIAL_GROUPID", groupId == null ? new Long(0) : groupId);
		detailPo.setLong("CREATE_BY", userId);
		detailPo.setTimestamp("CREATE_DATE", new Date());
	}
	
	
	
	
	

}
