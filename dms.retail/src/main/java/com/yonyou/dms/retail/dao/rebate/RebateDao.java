package com.yonyou.dms.retail.dao.rebate;

import java.text.SimpleDateFormat;
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
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.retail.domains.DTO.rebate.TtRebateCalculateTmpDTO;
import com.yonyou.dms.retail.domains.PO.rebate.RebateDetailPO;
import com.yonyou.dms.retail.domains.PO.rebate.RebateManagePO;
import com.yonyou.dms.retail.domains.PO.rebate.TtRebateCalculateDynamicPO;
import com.yonyou.dms.retail.domains.PO.rebate.TtRebateCalculateTmpPO;

/**
 * 经销商返利核算管理
 * 
 * @author zhoushijie 
 *
 */
@SuppressWarnings({"rawtypes", "unchecked"})
@Repository
public class RebateDao extends OemBaseDAO{
	
	/**
	 * 
	* @Title: findRebateManage 
	* @Description: 返利核算管理（查询） 
	* @param @param queryParam
	* @param @return    设定文件 
	* @return PageInfoDto    返回类型 
	* @throws
	 */
	public PageInfoDto findRebateManage(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam, params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;
	}
		
	/**
	 * 返利核算管理查询
	 * @param dealerCode
	 * @param execAuthor
	 * @param phone
	 * @param inputDate
	 * @param pageSize
	 * @param curPage
	 * @return
	 */

	private String getQuerySql(Map<String, String> queryParam, List<Object> params) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT  \n");
		sql.append("  trcl.LOG_ID, \n");
		sql.append("   trcl.BUSINESS_POLICY_NAME, \n");
		sql.append("   (CASE trcl.BUSINESS_POLICY_TYPE WHEN '91181001' THEN '销售' WHEN '91181002' THEN '售后' WHEN '91181003' THEN '网络'  ELSE '' END) BUSINESS_POLICY_TYPE , \n");
		sql.append("   trcl.START_MONTH, \n");
		sql.append("   trcl.END_MONTH, \n");
		sql.append("   trcl.UPLOAD_WAY, \n");
		sql.append("   trcl.CREATE_BY, \n");
		sql.append("   trcl.CREATE_DATE, \n");
		sql.append("   trcl.UPDATE_BY, \n");
		sql.append("   DATE_FORMAT(trcl.UPDATE_DATE,'%Y-%m-%d') UPDATE_DATE,\n");
		sql.append("   tu.NAME as USER_NAME \n");
		sql.append("FROM \n");
		sql.append(" TT_REBATE_CALCULATE_MANAGE trcl left join TC_USER tu on tu.USER_ID= trcl.UPDATE_BY \n");
		sql.append("WHERE 1=1\n");
		if (!StringUtils.isNullOrEmpty(queryParam.get("BUSINESS_POLICY_NAME"))) {
			sql.append(" and trcl.BUSINESS_POLICY_NAME = ? ");
			params.add(queryParam.get("BUSINESS_POLICY_NAME"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("beginDateTB"))) {
			sql.append("   AND DATE(trcl.START_MONTH) >= ? \n");
			params.add(queryParam.get("beginDateTB"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("endDateTB"))) {
			sql.append("   AND DATE(trcl.END_MONTH) <= ? \n");
			params.add(queryParam.get("endDateTB"));
		}
			return sql.toString();
	}
	/**
	 * 下载
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<Map> queryEmpInfoforExport(Map<String, String> queryParam)
			throws ServiceBizException {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam, params);
		return OemDAOUtil.findAll(sql.toString(), params);

	}
	
	/**
	 * 
	* @Title: deleteTtRebateCalculateTmp 
	* @Description: 返利核算管理清空临时表数据
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void deleteTtRebateCalculateTmp() {
		//获取当前用户
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		TtRebateCalculateTmpPO.delete(" CREATE_BY = ?", loginInfo.getUserId().toString());
	}

	/**
	 * 
	* @Title: insertTmprebateCalculate 
	* @Description: 返利核算管理插入临时表数据 
	* @param @param rowDto    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void insertTmprebateCalculate(TtRebateCalculateTmpDTO rowDto) {
		TtRebateCalculateTmpPO trctPO = new TtRebateCalculateTmpPO();
		//设置对象属性
		setTtRebateCalculateTmp(trctPO, rowDto);
		trctPO.saveIt();
	}
	private void setTtRebateCalculateTmp(TtRebateCalculateTmpPO trctPO, 
			TtRebateCalculateTmpDTO rowDto) {
		trctPO.setString("ROW_NO", rowDto.getRowNO());
		trctPO.setString("BUSINESS_POLICY_NAME", rowDto.getBusinessPolicyName());
		trctPO.setString("APPLICABLE_TIME", rowDto.getApplicableTime());
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
		trctPO.setString("RELEASE_DATE", sdf.format(rowDto.getReleaseDate()));
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM");  
		trctPO.setString("START_MONTH", sdf2.format(rowDto.getStartMonth()));
		trctPO.setString("END_MONTH", sdf2.format(rowDto.getEndMonth()));
		trctPO.setString("DEALER_CODE", rowDto.getDealerCode());
		trctPO.setString("DEALER_NAME", rowDto.getDealerName());
		trctPO.setString("VIN", rowDto.getVin());
		trctPO.setString("MODEL_NAME", rowDto.getModelName());
		trctPO.setString("COUNT", rowDto.getCount());
		trctPO.setString("NOMAL_BONUS", rowDto.getNewIncentives());
		trctPO.setString("SPECIAL_BONUS", rowDto.getSpecialBonus());
		trctPO.setString("BACK_BONUSES_EST", rowDto.getBackBonusesEst());
		trctPO.setString("BACK_BONUSES_DOWN", rowDto.getBackBonusesDown());
		trctPO.setString("NEW_INCENTIVES", rowDto.getNewIncentives());
		//获取当前用户
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		trctPO.setLong("CREATE_BY", loginInfo.getUserId());
		trctPO.setTimestamp("CREATE_DATE", new Date());
	}

	/**
	 * 
	* @Title: findTmpList 
	* @Description: 查询临时表数据 
	* @param @param loginInfo
	* @param @return    设定文件 
	* @return List<Map>    返回类型 
	* @throws
	 */
	public List<Map> findTmpList(LoginInfoDto loginInfo) {
		List<Object> params = new ArrayList<Object>();
		String sql = findTmpListSql(params, loginInfo);
		List<Map> list =  OemDAOUtil.findAll(sql.toString(), params);
		return list;
	}
	private String findTmpListSql(List<Object> params, LoginInfoDto loginInfo) {
		StringBuffer sql = new StringBuffer();
		sql.append("	SELECT ROW_NO, BUSINESS_POLICY_NAME, APPLICABLE_TIME, RELEASE_DATE, START_MONTH, END_MONTH, DEALER_CODE, DEALER_NAME, VIN, MODEL_NAME, COUNT, NOMAL_BONUS, SPECIAL_BONUS, BACK_BONUSES_EST, BACK_BONUSES_DOWN, NEW_INCENTIVES, CREATE_BY, CREATE_DATE, UPDATE_BY, UPDATE_DATE	\n");
		sql.append("   		FROM TT_REBATE_CALCULATE_TMP \n");
		sql.append("  where CREATE_BY=" +loginInfo.getUserId()+"\n");
		sql.append("   			ORDER BY CAST(ROW_NO AS DECIMAL(30,0)) ASC \n");
		return sql.toString();
	}

	/**
	 * 
	* @Title: checkData3 
	* @Description:  
	* @param @param loginInfo
	* @param @return    设定文件 
	* @return List<Map>    返回类型 
	* @throws
	 */
	public List<Map> checkData3(LoginInfoDto loginInfo) {
		List<Object> params = new ArrayList<Object>();
		String sql = checkData3Sql(params, loginInfo);
		List<Map> list =  OemDAOUtil.findAll(sql.toString(), params);
		return list;
	}
	private String checkData3Sql(List<Object> params, LoginInfoDto loginInfo) {
		StringBuffer sql = new StringBuffer();
		sql.append("select trd.ROW_NO,trd.VIN,IFNULL(tv.VIN,'') VIN_DESC,trd.DEALER_CODE,IFNULL(td.dealer_name,'') DEALER_CODE_DESC,\n");
		sql.append("  (case when  IFNULL(td.dealer_code,'')!='' and IFNULL(tv.VIN,'')!='' then 1 else 0 end) flag \n");
    	sql.append("  from TT_REBATE_CALCULATE_TMP trd\n");
		sql.append("  left join tm_vehicle_dec tv on tv.VIN=trd.VIN\n");
		sql.append("  left join TM_DEALER td on td.DEALER_CODE=trd.DEALER_CODE\n");
		sql.append("  where trd.CREATE_BY=" +loginInfo.getUserId()+"\n");
		return sql.toString();
	}

	/**
	 * 
	* @Title: deleteDyn 
	* @Description: 全量覆盖 
	* @param @param businessPolicyName
	* @param @param startMonth
	* @param @param endMonth    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void deleteDyn(String businessPolicyName, String startMonth, String endMonth) {
		List<Object> params = new ArrayList<Object>();
		String sql = TtRebateCalculateSql(businessPolicyName,startMonth,endMonth);
		List<Map> list =  OemDAOUtil.findAll(sql.toString(), params);
		if(list.size() >0){
			for(int i=0;i<list.size();i++){
				TtRebateCalculateDynamicPO.delete(" REBATE_ID = ?", list.get(i).get("REBATE_ID"));
			}
		}
	}
	private String TtRebateCalculateSql(String businessPolicyName, String startMonth, String endMonth) {
		StringBuffer sql = new StringBuffer();
		sql.append("	select distinct trc.REBATE_ID from TT_REBATE_CALCULATE trc \n");
		sql.append("	where  trc.BUSINESS_POLICY_NAME like '"+businessPolicyName+"'\n");
		sql.append("and trc.START_MONTH= '"+startMonth+"' \n");
		sql.append("and trc.END_MONTH='"+endMonth+"' \n");
		return sql.toString();
	}

	/**
	 * 
	* @Title: deleteSta 
	* @Description:全量覆盖 
	* @param @param businessPolicyName
	* @param @param startMonth
	* @param @param endMonth    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void deleteSta(String businessPolicyName, String startMonth, String endMonth) {
		List<Object> params = new ArrayList<Object>();
		String sql = TtRebateCalculateManageSql(businessPolicyName,startMonth,endMonth);
		List<Map> list =  OemDAOUtil.findAll(sql.toString(), params);
		if(list.size() >0){
			for(int i=0;i<list.size();i++){
				RebateDetailPO.delete(" LOG_ID = ?", list.get(i).get("LOG_ID"));
			}
		}
	}
	private String TtRebateCalculateManageSql(String businessPolicyName, String startMonth, String endMonth) {
		StringBuffer sql = new StringBuffer();
		sql.append("	select distinct trc.LOG_ID from TT_REBATE_CALCULATE_MANAGE trc \n");
		sql.append("	where  trc.BUSINESS_POLICY_NAME like '"+businessPolicyName+"'\n");
		sql.append("and trc.START_MONTH= '"+startMonth+"' \n");
		sql.append("and trc.END_MONTH='"+endMonth+"' )\n");
		return sql.toString();
	}

	/**
	 * 
	* @Title: selectRebateManage 
	* @Description: 商务政策主表操作 
	* @param @param businessPolicyName
	* @param @param startMonth
	* @param @param endMonth
	* @param @return    设定文件 
	* @return List<RebateManagePO>    返回类型 
	* @throws
	 */
	public List<RebateManagePO> selectRebateManage(String businessPolicyName, String startMonth,
			String endMonth) {
		List<RebateManagePO> list = RebateManagePO.findBySQL("SELECT trcm.* FROM tt_rebate_calculate_manage trcm WHERE 1=1 AND trcm.BUSINESS_POLICY_NAME= ? AND trcm.START_MONTH= ? AND trcm.END_MONTH= ?", businessPolicyName,startMonth,endMonth);
		return list;
	}

	/**
	 * 
	* @Title: insertRebateStat 
	* @Description:  插入固定列数据表
	* @param @param logId
	* @param @param loginInfo    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void insertRebateStat(Long logId, LoginInfoDto loginInfo) {
		List<Object> params = new ArrayList<Object>();
		String sql = insertRebateStatSql(logId, loginInfo);
		OemDAOUtil.execBatchPreparement(sql, params);
	}
	private String insertRebateStatSql(Long logId, LoginInfoDto loginInfo) {
		StringBuffer sql = new StringBuffer();
		sql.append("insert into TT_REBATE_CALCULATE ( \n");
		sql.append("			REBATE_ID ,LOG_ID ,BUSINESS_POLICY_NAME,APPLICABLE_TIME,RELEASE_DATE \n");
		sql.append("			,START_MONTH,END_MONTH,DEALER_CODE,DEALER_NAME,VIN \n");
		sql.append("			,MODEL_NAME,COUNT,NOMAL_BONUS,SPECIAL_BONUS,BACK_BONUSES_EST,BACK_BONUSES_DOWN \n");
		sql.append("			,NEW_INCENTIVES,OEM_COMPANY_ID,CREATE_BY,CREATE_DATE \n");
		sql.append("		)  ");
		sql.append("  select trct.TMP_ID, ");
		sql.append("  "+logId+", ");
		sql.append("  trct.BUSINESS_POLICY_NAME,trct.APPLICABLE_TIME, ");
		sql.append("  trct.RELEASE_DATE, ");
		sql.append("  trct.START_MONTH, trct.END_MONTH,trct.DEALER_CODE,trct.DEALER_NAME,trct.VIN,");
		sql.append("  trct.MODEL_NAME, ");
		sql.append(" (case WHEN trct.COUNT='NULL' THEN 0.00 ELSE trct.COUNT END) COUNT, \n ");
		
		sql.append(" (case WHEN trct.NOMAL_BONUS='NULL' THEN 0.00 ELSE trct.NOMAL_BONUS END ) NOMAL_BONUS , \n");
		sql.append(" ( case WHEN trct.SPECIAL_BONUS='NULL' THEN 0.00 ELSE trct.SPECIAL_BONUS END ) SPECIAL_BONUS, \n");
		sql.append(" ( case WHEN trct.BACK_BONUSES_EST='NULL' THEN 0.00 ELSE trct.BACK_BONUSES_EST END )BACK_BONUSES_EST, \n ");
		sql.append(" ( case WHEN trct.BACK_BONUSES_DOWN='NULL' THEN 0.00 ELSE trct.BACK_BONUSES_DOWN END) BACK_BONUSES_DOWN,    \n ");
		sql.append(" ( case WHEN trct.NEW_INCENTIVES='NULL' THEN 0.00 ELSE trct.NEW_INCENTIVES END ) NEW_INCENTIVES,  \n");
		
		sql.append("  "+loginInfo.getOemCompanyId()+", ");
		sql.append("  "+loginInfo.getUserId()+", ");
		sql.append("  CURRENT_TIMESTAMP() ");
		sql.append("  from TT_REBATE_CALCULATE_TMP trct where trct.CREATE_BY = "+loginInfo.getUserId()+" ");
		
		return sql.toString();
	}

	/**
	 * 
	* @Title: P_REBATE_CAL_DYN 
	* @Description: 存储过程跑动态列 
	* @param @param loginInfo    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void P_REBATE_CAL_DYN(LoginInfoDto loginInfo) {
		String spliterchar = ")";
		String title_name = "";//存储动态标题
		String title_value = "";//存储动态列值
		
		int v_start = 0;
		int v_end = 0;
		
		int s_start = 0;
		int s_end = 0;
		
		String v_str = "";
		String tmpSql = "";
		
		String sql = " select trct.TMP_ID, trct.DYNAMIC_DATA from TT_REBATE_CALCULATE_TMP trct where CREATE_BY= " + loginInfo.getUserId();
		List<Map> list = OemDAOUtil.findAll(sql, null);
		for (int i = 0; i < list.size(); i++) {
			String name = CommonUtils.checkNull(list.get(i).get("DYNAMIC_DATA"));
			v_start = 1;
			v_end = name.indexOf(spliterchar);
			tmpSql = CommonUtils.checkNull(list.get(i).get("TMP_ID"));
			while (v_end >0) {
				v_str=name.substring(v_start,v_end-v_start);
				v_str=v_str.replace("(","");
				s_start=1;
				s_end=v_str.indexOf(',');
				title_name=v_str.substring(1,s_end-s_start);
				title_value=(v_str.substring(s_end+1,v_str.length()-s_end));
				String insertSql = "insert into TT_REBATE_CALCULATE_DYNAMIC(REBATE_ID,DYNAMIC_TITLE,DYNAMIC_NAME) values("+tmpSql+",'"+title_name+"','"+title_value+"')"; 
				OemDAOUtil.execBatchPreparement(insertSql, null);
				v_start=v_end+2;
				v_end=name.substring(v_start).indexOf(spliterchar);
			}  
		}
	}

	/**
	 * 
	* @Title: selectTtRebateCalculateManage 
	* @Description: 返利核算管理重新上传起始年
	* @param @param logId
	* @param @return    设定文件 
	* @return List<Map>    返回类型 
	* @throws
	 */
	public List<Map> selectTtRebateCalculateManage(String logId) {
		Map m = selectStartMonthSql(logId);
		String stMonth = m.get("START_MONTH").toString();
		String stYear =stMonth.substring(0,4);
		m.put("START_YEAR2", stYear);
		
		List<Map> list = new ArrayList<Map>();
		list.add(m);
		
		return list;
	}

	/**
	 * 
	* @Title: selectReUploadStartMonth 
	* @Description: 返利核算管理重新上传起始月
	* @param @param logId
	* @param @return    设定文件 
	* @return List<Map>    返回类型 
	* @throws
	 */
	public List<Map> selectReUploadStartMonth(String logId) {
		Map m = selectStartMonthSql(logId);
		String stMonth = m.get("START_MONTH").toString();
		String bMonth =stMonth.substring(5,7);
		m.put("START_MONTH2", bMonth);
		
		List<Map> list = new ArrayList<Map>();
		list.add(m);
		
		return list;
	}
	private Map selectStartMonthSql(String logId){
		List<Object> params = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT trcm.START_MONTH FROM tt_rebate_calculate_manage trcm WHERE 1=1 AND LOG_ID = ?");
		params.add(Long.parseLong(logId));
		List<Map> trmList =  OemDAOUtil.findAll(sql.toString(), params);
		Map m = new HashMap();
		if(trmList.size()>0){
			m = trmList.get(0);
		}
		return m;
	}
	
	/**
	 * 
	* @Title: selectReUploadEndMonth 
	* @Description: 返利核算管理获取结束年 
	* @param @param logId
	* @param @return    设定文件 
	* @return List<Map>    返回类型 
	* @throws
	 */
	public List<Map> selectReUploadEndYear(String logId) {
		Map m = selectEndMonthSql(logId);
		String stMonth = m.get("END_MONTH").toString();
		String bMonth =stMonth.substring(0,4);
		m.put("END_YEAR2", bMonth);
		
		List<Map> list = new ArrayList<Map>();
		list.add(m);
		
		return list;
	}
	
	/**
	 * 
	* @Title: selectReUploadEndMonth 
	* @Description: 返利核算管理获取结束月 
	* @param @param logId
	* @param @return    设定文件 
	* @return List<Map>    返回类型 
	* @throws
	 */
	public List<Map> selectReUploadEndMonth(String logId) {
		Map m = selectEndMonthSql(logId);
		String stMonth = m.get("END_MONTH").toString();
		String bMonth =stMonth.substring(5,7);
		m.put("END_MONTH2", bMonth);
		
		List<Map> list = new ArrayList<Map>();
		list.add(m);
		
		return list;
	}
	private Map selectEndMonthSql(String logId) {
		List<Object> params = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT trcm.END_MONTH FROM tt_rebate_calculate_manage trcm WHERE 1=1 AND LOG_ID = ?");
		params.add(Long.parseLong(logId));
		List<Map> trmList =  OemDAOUtil.findAll(sql.toString(), params);
		Map m = new HashMap();
		if(trmList.size()>0){
			m = trmList.get(0);
		}
		return m;
	}

	/**
	 * 
	* @Title: selectReUploadgetBusinessPolicyName 
	* @Description: 获取上午政策名称 
	* @param @param logId
	* @param @return    设定文件 
	* @return List<Map>    返回类型 
	* @throws
	 */
	public List<Map> selectReUploadgetBusinessPolicyName(String logId) {
		List<Object> params = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT trcm.BUSINESS_POLICY_NAME FROM tt_rebate_calculate_manage trcm WHERE 1=1 AND LOG_ID = ?");
		params.add(Long.parseLong(logId));
		List<Map> trmList =  OemDAOUtil.findAll(sql.toString(), params);
		Map m = new HashMap();
		if(trmList.size()>0){
			m = trmList.get(0);
		}
		List<Map> list = new ArrayList<Map>();
		list.add(m);
		return list;
	}

	/**
	 * 
	* @Title: selectReUploadgetgetBusinessPolicyType 
	* @Description: 获取商务政策类型
	* @param @param logId
	* @param @return    设定文件 
	* @return List<Map>    返回类型 
	* @throws
	 */
	public List<Map> selectReUploadgetgetBusinessPolicyType(String logId) {
		List<Object> params = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT (CASE trcm.BUSINESS_POLICY_TYPE WHEN 91181001 THEN '销售' WHEN 91181002 THEN '售后' WHEN 91181003 THEN '网络' ELSE NULL END) BUSINESS_POLICY_TYPE FROM tt_rebate_calculate_manage trcm WHERE 1=1 AND LOG_ID = ?");
		params.add(Long.parseLong(logId));
		List<Map> trmList =  OemDAOUtil.findAll(sql.toString(), params);
		Map m = new HashMap();
		if(trmList.size()>0){
			m = trmList.get(0);
		}
		List<Map> list = new ArrayList<Map>();
		list.add(m);
		return list;
	}

	

}
