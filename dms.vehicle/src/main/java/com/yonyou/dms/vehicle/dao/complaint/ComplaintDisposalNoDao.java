package com.yonyou.dms.vehicle.dao.complaint;

import java.util.ArrayList;
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
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * 客户投诉处理Dao
 * @author ZhaoZ
 * 
 */
@SuppressWarnings("all")
@Repository
public class ComplaintDisposalNoDao extends OemBaseDAO{

	/**
	 * 客户投诉处理(总部) 不需回访查询
	 * @param queryParams
	 * @return
	 */
	public PageInfoDto complaintDisposalNo(Map<String, String> queryParams) {
		List<Object> params = new ArrayList<Object>();
		String sql = getComplaintDisposalNoSql(queryParams,params);
		return OemDAOUtil.pageQuery(sql, params);
	}

	/**
	 * 客户投诉处理(总部) 不需回访查询SQL
	 * @param queryParams
	 * @param params
	 * @return
	 */
	private String getComplaintDisposalNoSql(Map<String, String> queryParams, List<Object> params) {
		LoginInfoDto logonUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT * FROM (");
		sql.append("SELECT TCC.COMP_ID,");
		sql.append("        TCC.COMP_CODE,");
		sql.append("        TCCC.LINK_MAN,");
		sql.append("(SELECT Dealer_shortname");
		sql.append("		          FROM TM_DEALER");
  		sql.append("		         WHERE dealer_id = TCC.ALLOT_DEALER)");
 		sql.append("		          ALLOT_DEALER,");
 		sql.append("(SELECT Dealer_code");
		sql.append("		          FROM TM_DEALER");
  		sql.append("		         WHERE dealer_id = TCC.ALLOT_DEALER)");
 		sql.append("		          ALLOT_DEALER_CODE,");
		sql.append("        TCCC.TEL,");
		sql.append("        (SELECT ORG_NAME FROM TM_ORG WHERE ORG_ID=TCCC.OWN_ORG_ID) OWN_ORG_ID,");
		sql.append("        (SELECT  REGION_NAME FROM TM_REGION WHERE REGION_CODE=TCCC.PROVINCE) PROVINCE,");
		sql.append("       (SELECT code_desc FROM TC_CODE_dcs WHERE code_id = TCC.COMP_NATURE) COMP_NATURE,");
		sql.append("        (SELECT Dealer_shortname");
		sql.append("           FROM TM_DEALER");
		sql.append("          WHERE dealer_id = TCC.COMP_DEALER)");
		sql.append("           dealer_name,");
		sql.append("        (SELECT Dealer_CODE");
		sql.append("           FROM TM_DEALER");
		sql.append("          WHERE dealer_id = TCC.COMP_DEALER)");
		sql.append("           dealer_CODE,");
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
		sql.append("        IS_RETURN,");
		sql.append("		DATE_FORMAT(TCC.UPDATE_DATE,'%Y-%m-%d') UPDATE_DATE \n");
		sql.append("  FROM TT_CR_COMPLAINTS_CUSTOM_dcs TCCC,");
		sql.append("        TT_CR_COMPLAINTS_dcs TCC");
		sql.append("  WHERE     TCC.COMP_ID = TCCC.COMP_ID");
		sql.append("  AND     TCC.IS_RETURN=").append(OemDictCodeConstants.IF_TYPE_YES);
		sql.append(" AND TCC.STATUS IN (").append(OemDictCodeConstants.COMP_STATUS_TYPE_01);
		sql.append(",").append(OemDictCodeConstants.COMP_STATUS_TYPE_04).append(")");
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
		if(!logonUser.getDutyType().toString().equals(OemDictCodeConstants.DUTY_TYPE_DEPT.toString()) && !logonUser.getDutyType().toString().equals(OemDictCodeConstants.DUTY_TYPE_COMPANY.toString())){
			sql.append("      AND  TCC.ALLOT_DEALER in ("+getDealersByArea(logonUser.getOrgId().toString())+")\n");
		}
		sql.append(") dcs");
		return sql.toString();
	}

	/**
	 * 导入数据校验
	 * @param poList
	 * @return
	 */
	public List<Map> checkData2() {
		List<Map> errList = new ArrayList<Map>();
		//验证临时表重复数据
		List<Map> forVin = checkForVin();
		//验证导入时业务表重复数据
		List<Map> saveInfo =checkSaveInfo();//未结案
		List<Map> overInfo = checkOverInfo();//已结案
		//验证合法数据
		List<Map> dataList = findTmpList();
		
		if(forVin.size()>0){
			for(int i=0;i<forVin.size();i++){
				Map<String, String> errMap = new HashMap<String, String>();
				errMap.put("rowNum", forVin.get(i).get("LINE_NO").toString());
				errMap.put("errorDesc", "导入的任务存在重复，请重新导入");
				errList.add(errMap);
			}
		}
		
		if(saveInfo.size()>0){
			for(int i=0;i<saveInfo.size();i++){
				Map<String, String> errMap = new HashMap<String, String>();
				errMap.put("rowNum", saveInfo.get(i).get("LINE_NO").toString());
				errMap.put("errorDesc", "该任务已在处理中，不允许再导入");
				errList.add(errMap);
			}
		}
		if(overInfo.size()>0){
			for(int i=0;i<overInfo.size();i++){
				Map<String, String> errMap = new HashMap<String, String>();
				errMap.put("rowNum", overInfo.get(i).get("LINE_NO").toString());
				errMap.put("errorDesc", "该任务已结案，不允许再导入");
				errList.add(errMap);
			}
		}
		
		if(dataList.size()>0){
			for(int i=0;i<dataList.size();i++){
				Map<String, String> errMap = new HashMap<String, String>();
				errMap.put("rowNum", dataList.get(i).get("LINE_NO").toString());
				errMap.put("errorDesc", dataList.get(i).get("ERROR").toString());
				errList.add(errMap);
			}
		}	
				
		return errList;
	}

	/**
	 * 验证查询临时表
	 * @return
	 */
	private List<Map> findTmpList() {
		StringBuffer sql = new StringBuffer();
		sql.append(" select t1.LINE_NO, t1.VIN, ifnull(tm.VIN,'') tmInfo,  CONCAT(CONCAT('VIN:',t1.VIN),'不存在') error \n");
		sql.append("		   from TMP_VISIT_JDPOWER_dcs t1 \n");
		sql.append("		   left join TM_VEHICLE_dec tm on tm.VIN = t1.VIN \n");
		sql.append("		   where  (tm.VIN is null or tm.VIN='') \n");
		sql.append("  union all \n");
		sql.append("  select t1.LINE_NO,t1.VIN, ifnull(t1.VIN,'') tmInfo, 'VIN不能为空' error \n");
		sql.append("		   from TMP_VISIT_JDPOWER_dcs t1 \n");
		sql.append("		   where (t1.VIN is null or t1.VIN='')        \n");
		sql.append("  union all \n");
		sql.append("  select t1.LINE_NO,t1.VIN, ifnull(tm.DEALER_CODE,'') tmInfo,  CONCAT(CONCAT('经销商:',t1.DEALER_CODE),'不存在') error \n");
		sql.append("		   from TMP_VISIT_JDPOWER_dcs t1 \n");
		sql.append("		   left join TM_DEALER tm on tm.DEALER_CODE = t1.DEALER_CODE \n");
		sql.append("		    where (tm.DEALER_CODE is null or tm.DEALER_CODE='')  \n");
		sql.append("  union all \n");
		sql.append("  select t1.LINE_NO,t1.VIN, ifnull(t1.DEALER_CODE,'') tmInfo, '经销商不能为空' error \n");
		sql.append("		   from TMP_VISIT_JDPOWER_dcs t1 \n");
		sql.append("		   where (t1.DEALER_CODE is null or t1.DEALER_CODE='') \n");
		sql.append("  union all \n");
		sql.append("  select t1.LINE_NO,t1.VIN, ifnull(t1.LINK_NAME,'') tmInfo, '联系人为空' error \n");
		sql.append("		   from TMP_VISIT_JDPOWER_dcs t1 \n");
		sql.append("       where (t1.LINK_NAME is null or t1.LINK_NAME='') \n");
		sql.append("  union all \n");
		sql.append("  select t1.LINE_NO,t1.VIN, ifnull(t1.LINK_PHONE,'') tmInfo, '联系方式不合法' error \n");
		sql.append("		   from TMP_VISIT_JDPOWER_dcs t1 \n");
		sql.append("		left join   (SELECT (t.LINK_PHONE REGEXP '[^0-9.]') A,t.LINE_NO LINE FROM TMP_VISIT_JDPOWER_dcs t) AB \n");
		sql.append("        ON AB.LINE = t1.LINE_NO  \n");
		sql.append("       where t1.LINK_PHONE != ''  \n");
		sql.append("        and AB.A!=0 OR LENGTH(t1.LINK_PHONE) != 11       \n");
		sql.append("  union all \n");
		sql.append("  select t1.LINE_NO,t1.VIN, ifnull(t1.BACK_DATE,'') tmInfo, '反馈日期格式不为YYYY-MM-DD' error \n");
		sql.append("		   from TMP_VISIT_JDPOWER_dcs t1 \n");
		sql.append("       where  DATE(t1.BACK_DATE)=0 \n");
		sql.append("       and   t1.BACK_DATE != ''       \n");
		sql.append("  union all \n");
		sql.append("  select t1.LINE_NO,t1.VIN, ifnull(tc.CODE_DESC,'') tmInfo, CONCAT(CONCAT('跟踪性质:',t1.VISIT_TYPE),'不存在') error \n");
		sql.append("		   from TMP_VISIT_JDPOWER_dcs t1 \n");
		sql.append("		   left join TC_CODE_dcs tc on tc.CODE_DESC = t1.VISIT_TYPE 		   \n");
		sql.append("		   where (tc.CODE_DESC is null or tc.CODE_DESC='')  		   \n");
		sql.append("  union all \n");
		sql.append("  select t1.LINE_NO,t1.VIN, ifnull(t1.VISIT_TYPE,'') tmInfo, '跟踪性质不能为空' error \n");
		sql.append("		   from TMP_VISIT_JDPOWER_dcs t1 \n");
		sql.append("		   where (t1.VISIT_TYPE is null or t1.VISIT_TYPE='') \n");
		sql.append("  union all \n");
		sql.append("  select t1.LINE_NO,t1.VIN, ifnull(tc.CODE_DESC,'') tmInfo, CONCAT(CONCAT('渠道来源:',t1.VISIT_SOURCE),'不存在') error \n");
		sql.append("		   from TMP_VISIT_JDPOWER_dcs t1 \n");
		sql.append("		   left join TC_CODE_dcs tc on tc.CODE_DESC = t1.VISIT_SOURCE \n");
		sql.append("		   where (tc.CODE_DESC is null or tc.CODE_DESC='')  		   \n");
		sql.append("  union all \n");
		sql.append("  select t1.LINE_NO,t1.VIN, ifnull(t1.VISIT_SOURCE,'') tmInfo, '渠道来源不能为空' error \n");
		sql.append("		   from TMP_VISIT_JDPOWER_dcs t1 \n");
		sql.append("		   where (t1.VISIT_SOURCE is null or t1.VISIT_SOURCE='')      \n");
		return OemDAOUtil.findAll(sql.toString(), null);
	}

	/**
	 * 验证业务表重复数据已结案
	 * @return
	 */
	private List<Map> checkOverInfo() {
		StringBuffer sql = new StringBuffer();
		sql.append(" select t1.vin, t1.LINE_NO from TMP_VISIT_JDPOWER_dcs t1 \n");
		sql.append("      where EXISTS ( \n");
		sql.append("		  select t2.vin from TT_VISIT_JDPOWER_dcs t2 \n");
		sql.append("		  LEFT JOIN tmp_visit_jdpower_dcs t1 ON t1.VIN=T2.VIN AND t1.BACK_DATE=DATE_FORMAT (t2.BACK_DATE,'%Y-%m-%d')  \n");
		sql.append("     	  left join TC_CODE_dcs tcType on tcType.CODE_DESC = t1.VISIT_TYPE \n");
		sql.append("          left join TC_CODE_dcs tcSource on tcSource.CODE_DESC = t1.VISIT_SOURCE \n");
		sql.append("              and t2.VISIT_TYPE = tcType.CODE_ID \n");
		sql.append("              and t2.VISIT_SOURCE = tcSource.CODE_ID \n");
		sql.append("              and t2.VISIT_STATUS = 92031002 \n");
		sql.append("              and t2.UPDATE_DATE is not null \n");
		sql.append("		) \n");
		return OemDAOUtil.findAll(sql.toString(), null);
	}

	/**
	 * 验证业务表重复数据未结案
	 * @return
	 */
	private List<Map> checkSaveInfo() {
		StringBuffer sql = new StringBuffer();
		sql.append(" select t1.vin, t1.LINE_NO from TMP_VISIT_JDPOWER_dcs t1 \n");
		sql.append("      where EXISTS ( \n");
		sql.append("		  select t2.vin from TT_VISIT_JDPOWER_dcs t2 \n");
		sql.append("		  LEFT JOIN tmp_visit_jdpower_dcs t1 ON t1.VIN=T2.VIN AND t1.BACK_DATE=DATE_FORMAT (t2.BACK_DATE,'%Y-%m-%d')  \n");
		sql.append("     	  left join TC_CODE_dcs tcType on tcType.CODE_DESC = t1.VISIT_TYPE \n");
		sql.append("          left join TC_CODE_dcs tcSource on tcSource.CODE_DESC = t1.VISIT_SOURCE \n");
		sql.append("              and t2.VISIT_TYPE = tcType.CODE_ID \n");
		sql.append("              and t2.VISIT_SOURCE = tcSource.CODE_ID \n");
		sql.append("              and t2.VISIT_STATUS = 92031001 \n");
		sql.append("              and t2.UPDATE_DATE is not null \n");
		sql.append("		)  \n");
		return OemDAOUtil.findAll(sql.toString(), null);
	}

	/**
	 * 验证临时表重复数据
	 * @return
	 */
	private List<Map> checkForVin() {
		StringBuffer sql = new StringBuffer();
		sql.append(" select count(1),t1.vin ,t1.LINE_NO from TMP_VISIT_JDPOWER_dcs t1 where EXISTS ( \n");
		sql.append("	 select count(1),t2.vin from TMP_VISIT_JDPOWER_dcs t2 where t1.VIN=T2.VIN \n");
		sql.append("	 		and t1.VISIT_TYPE = t2.VISIT_TYPE and t1.VISIT_SOURCE = t2.VISIT_SOURCE and t1.BACK_DATE = t2.BACK_DATE  \n");
		sql.append("	 group by t2.vin \n");
		sql.append("	 having count(1) > 1 \n");
		sql.append("	) group by t1.vin,t1.LINE_NO  \n");
		return OemDAOUtil.findAll(sql.toString(), null);
	}

	/**
	 * 回访结果查询
	 * @param queryParams
	 * @return
	 */
	public PageInfoDto returnVisitResults(Map<String, String> queryParams) {
		List<Object> params = new ArrayList<Object>();
		String sql = getReturnVisitResultsSql(queryParams,params);
		return OemDAOUtil.pageQuery(sql, params);
	}

	/**
	 * 回访结果查询SQL
	 * @param queryParams
	 * @param params
	 * @return
	 */
	private String getReturnVisitResultsSql(Map<String, String> queryParams, List<Object> params) {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from ( ");
		sql.append("select  tvj.VISIT_ID, ");
		sql.append("        tmm.ORG_NAME, \n");
		sql.append("        tmm.ORG_NAME_SMALL,\n");
		sql.append("        tmm.DEALER_CODE, \n");
		sql.append("        tmm.DEALER_SHORTNAME, \n");
		sql.append("        tvj.LINK_NAME,  \n");
		sql.append("        tvj.LINK_PHONE, \n");
		sql.append("        tvj.VIN,  \n");
		sql.append("        DATE_FORMAT(tvj.BACK_DATE,'%Y-%m-%d') BACK_DATE,  \n");
		sql.append("        tvj.BACK_TYPE,  \n");
		sql.append("        tvj.BACK_CONTENT, \n");
		sql.append("        tvj.VISIT_TYPE,  \n");
		sql.append("        tvj.VISIT_SOURCE,  \n");
		sql.append("        tvj.VISIT_STATUS,  \n");
		sql.append("        tvj.DISPOSE_NAME,  \n");
		sql.append("        tvj.DISPOSE_RESULT,  \n");
		sql.append("        tvj.DISPOSE_DATE, \n");
		sql.append("        tvj.RESULT_CONTENT,  \n");
		sql.append("        DATE_FORMAT(tvj.CREATE_DATE,'%Y-%m-%d %H:%m:%s') CREATE_DATE  \n");
		sql.append("from TT_VISIT_JDPOWER_dcs tvj \n");
		sql.append("inner join (select   \n");
		sql.append("		       TM.DEALER_ID,  \n");
		sql.append("		       TOR2.ORG_DESC ORG_NAME,  \n");
		sql.append("		       TOR3.ORG_DESC ORG_NAME_SMALL,   \n");
		sql.append("		       TM.DEALER_CODE, \n");
		sql.append("		       TM.DEALER_SHORTNAME   \n");
		sql.append("		       from TM_DEALER TM,TM_DEALER_ORG_RELATION TDOR,TM_ORG TOR3,TM_ORG TOR2 \n");
		sql.append("		      where  TDOR.DEALER_ID = TM.DEALER_ID  \n");
		sql.append("		           AND (TOR3.ORG_ID = TDOR.ORG_ID AND TOR3.ORG_LEVEL = 3 )  \n");
		sql.append("		           AND (TOR3.PARENT_ORG_ID = TOR2.ORG_ID AND TOR2.ORG_LEVEL = 2 )  \n");
		sql.append("		           AND  TOR3.BUSS_TYPE = 12351002  \n");
		sql.append("		           AND TOR2.BUSS_TYPE = 12351002 \n");
		 //大区条件查询
		if(!StringUtils.isNullOrEmpty(queryParams.get("bigArea"))){
        	sql.append(" AND TOR2.ORG_ID = ? \n");
        	params.add(queryParams.get("bigArea"));
        }
        //小区条件查询
		if(!StringUtils.isNullOrEmpty(queryParams.get("smallArea"))){
        	sql.append(" AND TOR3.ORG_ID = ? \n");
        	params.add(queryParams.get("smallArea"));
        }
        
		if(!StringUtils.isNullOrEmpty(queryParams.get("dealerCode"))){
			String dealerCode = queryParams.get("dealerCode");
			dealerCode = dealerCode.replaceAll("\\,", "\n");
			dealerCode = dealerCode.replaceAll("[\\t\\n\\r]", "','");
			dealerCode = dealerCode.replaceAll(",''", "");
			sql.append("and tm.dealer_code in ('"+dealerCode+"') \n");
		}
		sql.append(") tmm on tvj.DEALER_CODE = tmm.DEALER_CODE \n");
		sql.append("left join TC_CODE_dcs tcType on tcType.CODE_ID = tvj.VISIT_TYPE where 1=1 \n");

		if(!StringUtils.isNullOrEmpty(queryParams.get("linkName"))){
        	sql.append("AND tvj.LINK_NAME like  ? \n");
        	params.add("%"+queryParams.get("linkName")+"%");
        }
		if(!StringUtils.isNullOrEmpty(queryParams.get("linkPhone"))){
        	sql.append(" AND tvj.LINK_PHONE like  ? \n");
        	params.add("%"+queryParams.get("linkPhone")+"%");
        }
		if(!StringUtils.isNullOrEmpty(queryParams.get("visitStatus"))){
        	sql.append(" AND tvj.VISIT_STATUS =  ? \n");
        	params.add(queryParams.get("visitStatus"));
        }
		
		if(!StringUtils.isNullOrEmpty(queryParams.get("bacakDateStart"))){ 
		     
			sql.append(" AND  DATE_FORMAT(tvj.BACK_DATE,'%Y-%m-%d') >='" + queryParams.get("bacakDateStart") +"' \n");
        }
		if(!StringUtils.isNullOrEmpty(queryParams.get("bacakDateStart"))){ 
		     
			sql.append(" AND  DATE_FORMAT(tvj.BACK_DATE,'%Y-%m-%d') <='" + queryParams.get("bacakDateStart") +"' \n");
        }
		if(!StringUtils.isNullOrEmpty(queryParams.get("visitType"))){
        	sql.append("AND tvj.VISIT_TYPE =  ? \n");
        	params.add(queryParams.get("visitType"));
        }
		if(!StringUtils.isNullOrEmpty(queryParams.get("visitSource"))){
        	sql.append(" AND tvj.VISIT_SOURCE =  ? \n");
        	params.add(queryParams.get("visitSource"));
        }
		if(!StringUtils.isNullOrEmpty(queryParams.get("disposeResult"))){
        	sql.append(" AND tvj.DISPOSE_RESULT =  ? \n");
        	params.add(queryParams.get("disposeResult"));
        }
		if(!StringUtils.isNullOrEmpty(queryParams.get("disposeDateStart"))){ 
		     
			sql.append(" AND  DATE_FORMAT(tvj.DISPOSE_DATE,'%Y-%m-%d') >='" + queryParams.get("disposeDateStart") +"' \n");
        }
		if(!StringUtils.isNullOrEmpty(queryParams.get("disposeDateStart"))){ 
		     
			sql.append(" AND  DATE_FORMAT(tvj.DISPOSE_DATE,'%Y-%m-%d') <='" + queryParams.get("disposeDateStart") +"' \n");
        }
		
		if(!StringUtils.isNullOrEmpty(queryParams.get("vin"))){
			String vin = queryParams.get("vin");
			vin = vin.replaceAll("\\,", "\n");
			vin = vin.replaceAll("[\\t\\n\\r]", "','");
			vin = vin.replaceAll(",''", "");
			if(vin.length()==8 && vin.indexOf(",")==-1 ){
				sql.append("AND tvj.VIN like ('%"+vin+"') \n");
			}else{
				sql.append("AND tvj.VIN in ('"+vin+"') \n");
			}
		}
		sql.append(") dcs ");
		return sql.toString();
	}

	/**
	 * 经销商端回访任务处理/回访结果下载
	 * @param queryParams
	 * @return
	 */
	public List<Map> returnVisitDealerDownLoad(Map<String, String> queryParams,String dealerCode) {
		List<Object> params = new ArrayList<Object>();
		String sql = returnVisitDealerDownLoadSql(queryParams,params,dealerCode);
		return OemDAOUtil.findAll(sql, params);
	}

	/**
	 * 回访结果下载SQL
	 * @param queryParams
	 * @param params
	 * @return
	 */
	private String returnVisitDealerDownLoadSql(Map<String, String> queryParams, List<Object> params,String dealerCode) {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from ( ");
		sql.append("select  tvj.VISIT_ID, \n");
		sql.append("        tmm.ORG_NAME, \n");
		sql.append("        tmm.ORG_NAME_SMALL,\n");
		sql.append("        tmm.DEALER_CODE, \n");
		sql.append("        tmm.DEALER_SHORTNAME, \n");
		sql.append("        tvj.LINK_NAME, \n");
		sql.append("        tvj.LINK_PHONE,  \n");
		sql.append("        tvj.VIN, \n");
		sql.append("        DATE_FORMAT(tvj.BACK_DATE,'%Y-%m-%d') BACK_DATE,\n");
		sql.append("        tvj.BACK_TYPE, \n");
		sql.append("        tvj.BACK_CONTENT,  \n");
		sql.append("        tvj.VISIT_TYPE,tcType.CODE_DESC CODE_DESC_TYPE, \n");
		sql.append("        tvj.VISIT_SOURCE,tcSource.CODE_DESC CODE_DESC_SOURCE, \n");
		sql.append("        tvj.VISIT_STATUS,tcStatus.CODE_DESC CODE_DESC_STATUS, \n");
		sql.append("        tvj.DISPOSE_NAME,\n");
		sql.append("        tvj.DISPOSE_RESULT,tcResult.CODE_DESC CODE_DESC_RESULT,  \n");
		sql.append("        DATE_FORMAT(tvj.DISPOSE_DATE,'%Y-%m-%d %H:%m:%s') DISPOSE_DATE, \n");
		sql.append("        tvj.RESULT_CONTENT,  \n");
		sql.append("         DATE_FORMAT(tvj.CREATE_DATE,'%Y-%m-%d %H:%m:%s') CREATE_DATE   \n");
		sql.append("from TT_VISIT_JDPOWER_dcs tvj \n");
		sql.append("inner join (select   \n");
		sql.append("		       TM.DEALER_ID,  \n");
		sql.append("		       TOR2.ORG_DESC ORG_NAME, \n");
		sql.append("		       TOR3.ORG_DESC ORG_NAME_SMALL,  \n");
		sql.append("		       TM.DEALER_CODE,  \n");
		sql.append("		       TM.DEALER_SHORTNAME    \n");
		sql.append("		       from TM_DEALER TM,TM_DEALER_ORG_RELATION TDOR,TM_ORG TOR3,TM_ORG TOR2 \n");
		sql.append("		      where  TDOR.DEALER_ID = TM.DEALER_ID  \n");
		sql.append("		           AND (TOR3.ORG_ID = TDOR.ORG_ID AND TOR3.ORG_LEVEL = 3 )  \n");
		sql.append("		           AND (TOR3.PARENT_ORG_ID = TOR2.ORG_ID AND TOR2.ORG_LEVEL = 2 )  \n");
		sql.append("		           AND  TOR3.BUSS_TYPE = 12351002  \n");
		sql.append("		           AND TOR2.BUSS_TYPE = 12351002 \n");
		 //大区条件查询
		if(!StringUtils.isNullOrEmpty(queryParams.get("bigArea"))){
        	sql.append(" AND TOR2.ORG_ID = ? \n");
        	params.add(queryParams.get("bigArea"));
        }
        //小区条件查询
		if(!StringUtils.isNullOrEmpty(queryParams.get("smallArea"))){
        	sql.append(" AND TOR3.ORG_ID = ? \n");
        	params.add(queryParams.get("smallArea"));
        }
		if(!StringUtils.isNullOrEmpty(dealerCode)){
			dealerCode = dealerCode.replaceAll("\\,", "\n");
			dealerCode = dealerCode.replaceAll("[\\t\\n\\r]", "','");
			dealerCode = dealerCode.replaceAll(",''", "");
			sql.append("and tm.dealer_code in ('"+dealerCode+"') \n");
		}
		sql.append(") tmm on tvj.DEALER_CODE = tmm.DEALER_CODE \n");
		sql.append("left join TC_CODE_dcs tcType on tcType.CODE_ID = tvj.VISIT_TYPE \n");
		sql.append("left join TC_CODE_dcs tcSource on tcSource.CODE_ID = tvj.VISIT_SOURCE \n");
		sql.append("left join TC_CODE_dcs tcStatus on tcStatus.CODE_ID = tvj.VISIT_STATUS \n");
		sql.append("left join TC_CODE_dcs tcResult on tcResult.CODE_ID = tvj.DISPOSE_RESULT where 1=1 \n");

		if(!StringUtils.isNullOrEmpty(queryParams.get("linkName"))){
        	sql.append("AND tvj.LINK_NAME like  ? \n");
        	params.add("%"+queryParams.get("linkName")+"%");
        }
		if(!StringUtils.isNullOrEmpty(queryParams.get("linkPhone"))){
        	sql.append(" AND tvj.LINK_PHONE like  ? \n");
        	params.add("%"+queryParams.get("linkPhone")+"%");
        }
		if(!StringUtils.isNullOrEmpty(queryParams.get("visitStatus"))){
        	sql.append(" AND tvj.VISIT_STATUS =  ? \n");
        	params.add(queryParams.get("visitStatus"));
        }
		if(!StringUtils.isNullOrEmpty(queryParams.get("bacakDateStart"))){ 
		     
			sql.append(" AND  DATE_FORMAT(tvj.BACK_DATE,'%Y-%m-%d') >='" + queryParams.get("bacakDateStart") +"' \n");
        }
		if(!StringUtils.isNullOrEmpty(queryParams.get("bacakDateStart"))){ 
		     
			sql.append(" AND  DATE_FORMAT(tvj.BACK_DATE,'%Y-%m-%d') <='" + queryParams.get("bacakDateStart") +"' \n");
        }
		if(!StringUtils.isNullOrEmpty(queryParams.get("visitType"))){
        	sql.append("AND tvj.VISIT_TYPE =  ? \n");
        	params.add(queryParams.get("visitType"));
        }
		if(!StringUtils.isNullOrEmpty(queryParams.get("visitSource"))){
        	sql.append(" AND tvj.VISIT_SOURCE =  ? \n");
        	params.add(queryParams.get("visitSource"));
        }
		if(!StringUtils.isNullOrEmpty(queryParams.get("disposeResult"))){
        	sql.append("AND tvj.DISPOSE_RESULT =  ? \n");
        	params.add(queryParams.get("disposeResult"));
        }
		if(!StringUtils.isNullOrEmpty(queryParams.get("disposeDateStart"))){ 
		     
			sql.append(" AND  DATE_FORMAT(tvj.DISPOSE_DATE,'%Y-%m-%d') >='" + queryParams.get("disposeDateStart") +"' \n");
        }
		if(!StringUtils.isNullOrEmpty(queryParams.get("disposeDateStart"))){ 
		     
			sql.append(" AND  DATE_FORMAT(tvj.DISPOSE_DATE,'%Y-%m-%d') <='" + queryParams.get("disposeDateStart") +"' \n");
        }
		
		if(!StringUtils.isNullOrEmpty(queryParams.get("vin"))){
			String vin = queryParams.get("vin");
			vin = vin.replaceAll("\\,", "\n");
			vin = vin.replaceAll("[\\t\\n\\r]", "','");
			vin = vin.replaceAll(",''", "");
			if(vin.length()==8 && vin.indexOf(",")==-1 ){
				sql.append("AND tvj.VIN like ('%"+vin+"') \n");
			}else{
				sql.append("AND tvj.VIN in ('"+vin+"') \n");
			}
		}
		sql.append(") dcs ");
		return sql.toString();
	}

	/**
	 * 经销商端回访任务处理查询
	 * @param queryParams
	 * @param params 
	 * @return
	 */
	public String returnVisitDealersSql(Map<String, String> queryParams, List<Object> params) {
		LoginInfoDto logonUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		String dealerCode = logonUser.getDealerCode();
		StringBuffer sql = new StringBuffer();
		sql.append("select  tvj.VISIT_ID, \n");
		sql.append("        tmm.ORG_NAME, \n");
		sql.append("        tmm.ORG_NAME_SMALL, \n");
		sql.append("        tmm.DEALER_CODE, \n");
		sql.append("        tmm.DEALER_SHORTNAME, \n");
		sql.append("        tvj.LINK_NAME,  \n");
		sql.append("        tvj.LINK_PHONE,  \n");
		sql.append("        tvj.VIN, \n");
		sql.append("         DATE_FORMAT(tvj.BACK_DATE,'%Y-%m-%d') BACK_DATE,  \n");
		sql.append("        tvj.BACK_TYPE, \n");
		sql.append("        tvj.BACK_CONTENT,  \n");
		sql.append("        tvj.VISIT_TYPE, \n");
		sql.append("        tvj.VISIT_SOURCE,  \n");
		sql.append("        tvj.VISIT_STATUS, \n");
		sql.append("        tvj.DISPOSE_NAME,  \n");
		sql.append("        tvj.DISPOSE_RESULT,  \n");
		sql.append("         DATE_FORMAT(tvj.DISPOSE_DATE,'%Y-%m-%d')DISPOSE_DATE, \n");
		sql.append("        tvj.RESULT_CONTENT, \n");
		sql.append("       DATE_FORMAT(tvj.CREATE_DATE,'%Y-%m-%d %H:%m:%s') CREATE_DATE  \n");
		sql.append("from TT_VISIT_JDPOWER_dcs tvj \n");
		sql.append("inner join (select   \n");
		sql.append("		       TM.DEALER_ID,  \n");
		sql.append("		       TOR2.ORG_DESC ORG_NAME, \n");
		sql.append("		       TOR3.ORG_DESC ORG_NAME_SMALL,   \n");
		sql.append("		       TM.DEALER_CODE,  \n");
		sql.append("		       TM.DEALER_SHORTNAME   \n");
		sql.append("		       from TM_DEALER TM,TM_DEALER_ORG_RELATION TDOR,TM_ORG TOR3,TM_ORG TOR2 \n");
		sql.append("		      where  TDOR.DEALER_ID = TM.DEALER_ID  \n");
		sql.append("		           AND (TOR3.ORG_ID = TDOR.ORG_ID AND TOR3.ORG_LEVEL = 3 )  \n");
		sql.append("		           AND (TOR3.PARENT_ORG_ID = TOR2.ORG_ID AND TOR2.ORG_LEVEL = 2 )  \n");
		sql.append("		           AND  TOR3.BUSS_TYPE = 12351002  \n");
		sql.append("		           AND TOR2.BUSS_TYPE = 12351002 \n");
		 //大区条件查询
		if(!StringUtils.isNullOrEmpty(queryParams.get("bigArea"))){
        	sql.append(" AND TOR2.ORG_ID = ? \n");
        	params.add(queryParams.get("bigArea"));
        }
        //小区条件查询
		if(!StringUtils.isNullOrEmpty(queryParams.get("smallArea"))){
        	sql.append(" AND TOR3.ORG_ID = ? \n");
        	params.add(queryParams.get("smallArea"));
        }
        if(!StringUtils.isNullOrEmpty(dealerCode)){
			dealerCode = dealerCode.replaceAll("\\,", "\n");
			dealerCode = dealerCode.replaceAll("[\\t\\n\\r]", "','");
			dealerCode = dealerCode.replaceAll(",''", "");
			sql.append("and tm.dealer_code in ('"+dealerCode+"') \n");
		}
		sql.append(") tmm on tvj.DEALER_CODE = tmm.DEALER_CODE \n");
		sql.append("left join TC_CODE_dcs tcType on tcType.CODE_ID = tvj.VISIT_TYPE where 1=1 \n");

		if(!StringUtils.isNullOrEmpty(queryParams.get("linkName"))){
        	sql.append(" AND tvj.LINK_NAME like  ? \n");
        	params.add("%"+queryParams.get("linkName")+"%");
        }
		if(!StringUtils.isNullOrEmpty(queryParams.get("linkPhone"))){
        	sql.append(" AND tvj.LINK_PHONE like  ? \n");
        	params.add("%"+queryParams.get("linkPhone")+"%");
        }
		if(!StringUtils.isNullOrEmpty(queryParams.get("visitStatus"))){
        	sql.append(" AND tvj.VISIT_STATUS =  ? \n");
        	params.add(queryParams.get("visitStatus"));
        }
		if(!StringUtils.isNullOrEmpty(queryParams.get("bacakDateStart"))){ 
		     
			sql.append(" AND  DATE_FORMAT(tvj.BACK_DATE,'%Y-%m-%d') >='" + queryParams.get("bacakDateStart") +"' \n");
        }
		if(!StringUtils.isNullOrEmpty(queryParams.get("bacakDateStart"))){ 
		     
			sql.append(" AND  DATE_FORMAT(tvj.BACK_DATE,'%Y-%m-%d') <='" + queryParams.get("bacakDateStart") +"' \n");
        }
		if(!StringUtils.isNullOrEmpty(queryParams.get("visitType"))){
        	sql.append(" AND tvj.VISIT_TYPE =  ? \n");
        	params.add(queryParams.get("visitType"));
        }
		if(!StringUtils.isNullOrEmpty(queryParams.get("visitSource"))){
        	sql.append(" AND tvj.VISIT_SOURCE =  ? \n");
        	params.add(queryParams.get("visitSource"));
        }
		if(!StringUtils.isNullOrEmpty(queryParams.get("disposeResult"))){
        	sql.append(" AND tvj.DISPOSE_RESULT =  ? \n");
        	params.add(queryParams.get("disposeResult"));
        }
		if(!StringUtils.isNullOrEmpty(queryParams.get("disposeDateStart"))){ 
		     
			sql.append(" AND  DATE_FORMAT(tvj.DISPOSE_DATE,'%Y-%m-%d') >='" + queryParams.get("disposeDateStart") +"' \n");
        }
		if(!StringUtils.isNullOrEmpty(queryParams.get("disposeDateStart"))){ 
		     
			sql.append(" AND  DATE_FORMAT(tvj.DISPOSE_DATE,'%Y-%m-%d') <='" + queryParams.get("disposeDateStart") +"' \n");
        }
		
		if(!StringUtils.isNullOrEmpty(queryParams.get("vin"))){
			String vin = queryParams.get("vin");
			vin = vin.replaceAll("\\,", "\n");
			vin = vin.replaceAll("[\\t\\n\\r]", "','");
			vin = vin.replaceAll(",''", "");
			if(vin.length()==8 && vin.indexOf(",")==-1 ){
				sql.append("AND tvj.VIN like ('%"+vin+"') \n");
			}else{
				sql.append("AND tvj.VIN in ('"+vin+"') \n");
			}
		}
		return sql.toString();
	}

	/**
	 * 
	 * @param queryParams
	 * @return
	 */
	public PageInfoDto returnVisitDealers(Map<String, String> queryParams) {
		List<Object> params = new ArrayList<Object>();
		String sql = returnVisitDealersSql(queryParams,params);
		return OemDAOUtil.pageQuery(sql, params);
	}

	

}
