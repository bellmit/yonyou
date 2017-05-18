package com.yonyou.dms.vehicle.dao.activityManage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
* @author liujiming
* @date 2017年3月23日
*/
@SuppressWarnings("all")

@Repository
public class ActivityMaintainDao extends OemBaseDAO{
	
	
	/**
	 * 服务活动建立 查询	
	 * @param queryParam
	 * @return pageInfoDto
	 */
	public PageInfoDto getActivityInitQuery(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getActivityInitQuerySql(queryParam, params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;

	}
	
	/**
	 * SQL组装 服务活动建立
	 * 
	 * @param queryParam
	 * @param params
	 * @return
	 */
	private String getActivityInitQuerySql(Map<String, String> queryParam, List<Object> params) {
		
		
		StringBuffer sql = new StringBuffer("\n");
		sql.append("SELECT ACT.ACTIVITY_ID, \n");
		sql.append("       ACT.ACTIVITY_CODE, \n");
		sql.append("       ACT.ACTIVITY_NAME, \n");
		sql.append("       ACT.ACTIVITY_TYPE, \n");
		sql.append("       DATE_FORMAT(ACT.START_DATE,'%Y-%c-%d') START_DATE, \n");
		sql.append("       DATE_FORMAT(ACT.END_DATE,'%Y-%c-%d') END_DATE, \n");
		sql.append("    ACT.STATUS \n");
		sql.append("  FROM TT_WR_ACTIVITY_DCS ACT \n");
		sql.append(" WHERE ACT.IS_DEL = 0 \n");
		sql.append(" AND (STATUS=40171001 OR STATUS=40171003) \n");
		//所属公司ID
		sql.append(" AND ACT.OEM_COMPANY_ID = '2010010100070674' \n");
		//活动代码
		if(!StringUtils.isNullOrEmpty(queryParam.get("activityCode"))){	
			sql.append(" and ACT.ACTIVITY_CODE like '%"+queryParam.get("activityCode")+"%' \n");
        }
		//申请日期 起始
		if(!StringUtils.isNullOrEmpty(queryParam.get("startDate"))){
			sql.append(" and ACT.START_DATE >= '" + queryParam.get("startDate") + "' \n");	                      
		}
		//订申请日期 日期 结束
		if(!StringUtils.isNullOrEmpty(queryParam.get("endDate"))){
		    sql.append(" and ACT.END_DATE <= '" + queryParam.get("endDate") + "'  \n");            
		 }
		//
		if(!StringUtils.isNullOrEmpty(queryParam.get("activityType"))){	
			sql.append(" and ACT.ACTIVITY_TYPE = "+queryParam.get("activityType")+" \n");
        }
		//
		if(!StringUtils.isNullOrEmpty(queryParam.get("activityStatus"))){	
			sql.append(" and ACT.STATUS = "+queryParam.get("activityStatus")+" \n");
        }
		//sql.append(" ORDER BY ACT.STATUS, ACT.ACTIVITY_ID DESC \n");
		return sql.toString();
	}
	/**
	 * 服务活动建立  明细查询	
	 * @param queryParam
	 * @return pageInfoDto
	 */
	public Map getActivityDetailQuery(Long activityId) {

		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("\n");
		sql.append("SELECT ACT.ACTIVITY_ID, \n");
		sql.append("       ACT.ACTIVITY_CODE, \n");
		sql.append("       ACT.ACTIVITY_NAME, \n");
		sql.append("       ACT.ACTIVITY_TYPE, \n");
		sql.append("       DATE_FORMAT(ACT.START_DATE,'%Y-%c-%d') START_DATE, \n");
		sql.append("       DATE_FORMAT(ACT.END_DATE,'%Y-%c-%d') END_DATE, \n");
		sql.append("       ACT.CLAIM_TYPE, \n");
		sql.append("       ACT.SUMMARY_DAYS, \n");
		sql.append("       ACT.CHOOSE_WAY, \n");
		sql.append("       ACT.IS_FEE, \n");
		sql.append("       ACT.LABOUR_FEE, \n");
		sql.append("       ACT.PART_FEE, \n");
		sql.append("       ACT.OTHER_FEE, \n");
		sql.append("       ACT.EXPLANS, \n");
		sql.append("       ACT.IS_MULTI, \n");
		sql.append("       ACT.GUIDE,ACT.SUMM_CLOSEDATE, \n");
		sql.append("	   ACT.GLOBAL_ACTIVITY_CODE,  \n");
		sql.append("	   ACT.ACTIVITY_TITLE,  \n");
		sql.append("	   ACT.ATTACHMENT_URL  \n");
		sql.append("  FROM TT_WR_ACTIVITY_DCS ACT  WHERE  1=1        AND ACT.IS_DEL = 0 \n");
		sql.append(" AND ACT.ACTIVITY_ID = "+activityId+" \n");
		List<Map> list = OemDAOUtil.findAll(sql.toString(), params);
		Map resultMap = new HashMap();
		for(Map map : list){
			resultMap = map;
		}
		return resultMap;

	}	
	
	
	/**
	 * 服务活动建立  明细查询	工时
	 * @param queryParam
	 * @return pageInfoDto
	 */
	public PageInfoDto getActivityLabourQuery(Long activityId) {

		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("\n");
		sql.append(" SELECT  LABOUR_CODE, LABOUR_NAME,DISCOUNT FROM  TT_WR_ACTIVITY_LABOUR_DCS  \n");
		sql.append(" WHERE 1 = 1 \n");
		sql.append(" AND ACTIVITY_ID = " + activityId+"    \n");
		sql.append(" AND is_del=0 \n");
		//sql.append(" ORDER BY labour_name ");
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql.toString(), params);
		return pageInfoDto;

	}

	/**
	 * 服务活动建立  明细查询	
	 * @param queryParam
	 * @return pageInfoDto
	 */
	public PageInfoDto getActivityPartQuery(Long activityId) {

		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("\n");
		sql.append(" SELECT  PART_CODE, PART_NAME, PART_AMOUNT, DNP, MSRP, DISCOUNT FROM  TT_WR_ACTIVITY_PART_DCS WHERE 1=1    \n");
		sql.append(" AND IS_DEL = 0 \n");
		sql.append(" AND ACTIVITY_ID = " + activityId+"   \n");
		//sql.append(" ORDER BY PART_CODE \n");

		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql.toString(), params);
		return pageInfoDto;

	}
	/**
	 * 服务活动建立  明细查询	
	 * @param queryParam
	 * @return pageInfoDto
	 */
	public PageInfoDto getActivityOtherQuery(Long activityId) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("\n");
		sql.append(" SELECT  OTHER_FEE_CODE, OTHER_FEE_NAME,AMOUNT  \n");
		sql.append(" FROM  TT_WR_ACTIVITY_Other_DCS O \n");
		sql.append(" WHERE 1 = 1 \n");
		sql.append(" AND O.ACTIVITY_ID = " + activityId+"  \n");
		//sql.append(" ORDER BY O.OTHER_FEE_NAME \n");

		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql.toString(), params);
		return pageInfoDto;

	}
	/**
	 * 服务活动建立  明细查询	
	 * @param queryParam
	 * @return pageInfoDto
	 */
	public PageInfoDto getActivityVehicleQuery(Long activityId) {

		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("\n");
		sql.append(" SELECT  GROUP_CODE,GROUP_NAME FROM TM_VHCL_MATERIAL_GROUP  \n");
		sql.append(" WHERE GROUP_ID IN(SELECT GROUP_ID FROM TT_WR_ACTIVITY_MODEL_DCS WHERE 1 = 1 AND ACTIVITY_ID = " + activityId+" ) \n");

		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql.toString(), params);
		return pageInfoDto;

	}	
	/**
	 * 服务活动建立  明细查询	
	 * @param queryParam
	 * @return pageInfoDto
	 */
	public PageInfoDto getActivityAgeQuery(Long activityId) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("\n");
		sql.append(" SELECT  AGE_TYPE,DATE_FORMAT(start_date,'%Y-%c-%d')  START_DATE,DATE_FORMAT(end_date,'%Y-%c-%d')  END_DATE \n");
		sql.append(" FROM TT_WR_ACTIVITY_AGE_DCS WHERE 1 = 1 \n");
		sql.append(" AND ACTIVITY_ID = " + activityId+"   \n");
		//sql.append(" ORDER BY age_type \n");

		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql.toString(), params);
		return pageInfoDto;

	}	
	
	/**
	 * 服务活动建立 查询	
	 * @param queryParam
	 * @return pageInfoDto
	 */
	public PageInfoDto getModifyVehicleQuery(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("\n");
		sql.append("SELECT tvmg.group_code SERIES_CODE,G.GROUP_CODE, G.GROUP_NAME, G.GROUP_ID,M.ACTIVITY_ID,M.ID   \n");
		sql.append(" FROM TT_WR_ACTIVITY_MODEL_DCS M, TM_VHCL_MATERIAL_GROUP G,TM_VHCL_MATERIAL_GROUP tvmg \n");
		sql.append(" WHERE M.GROUP_ID = G.GROUP_ID \n");
		sql.append(" AND M.ACTIVITY_ID ="+Long.parseLong(queryParam.get("activityId1")) +" \n"); 
		sql.append(" AND g.PARENT_GROUP_ID = tvmg.GROUP_ID  \n");
		//sql.append(" ORDER BY G.GROUP_NAME \n");
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql.toString(), params);
		return pageInfoDto;

	}
	
	/**
	 * 主页面车型新增查询
	 * @param queryParam
	 * @return pageInfoDto
	 */
	public PageInfoDto getActivityCarModelQuery(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("\n");
		sql.append(" SELECT TVMG.GROUP_ID,TVMG.GROUP_CODE,TVMG.GROUP_NAME FROM TM_VHCL_MATERIAL_GROUP TVMG \n");
		sql.append("	WHERE 1=1 AND GROUP_LEVEL = 3 \n");
		sql.append("		AND GROUP_ID NOT IN (SELECT GROUP_ID FROM TT_WR_ACTIVITY_MODEL_DCS \n");
		sql.append("		WHERE 1=1  \n");
		sql.append("		AND activity_id ='"+queryParam.get("activityId2")+"' ) \n");
		
		//sql.append("		AND TVMG.PARENT_GROUP_ID='2013091010502780' \n");
		if(!StringUtils.isNullOrEmpty(queryParam.get("seriesId"))){	
			sql.append(" AND TVMG.PARENT_GROUP_ID= "+queryParam.get("seriesId")+" \n");
        }		
		//sql.append("		ORDER BY TVMG.GROUP_ID 	 \n");

		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql.toString(), params);
		return pageInfoDto;

	}
	
	/**
	 * 主页面车型新增查询
	 * @param queryParam
	 * @return pageInfoDto
	 */
	public PageInfoDto getCarAgeQuery(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("\n");
		sql.append("	SELECT TWAA.ID,TWAA.AGE_TYPE,TWAA.START_DATE,TWAA.END_DATE \n");
		sql.append("	FROM TT_WR_ACTIVITY_AGE_DCS TWAA \n");
		sql.append("	WHERE 1=1  \n");
		sql.append("		  AND  TWAA.ACTIVITY_ID ='"+Long.parseLong(queryParam.get("activityId1")) +"' \n");
		//sql.append("	ORDER BY TWAA.AGE_TYPE	 \n");

		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql.toString(), params);
		return pageInfoDto;

	}
	/**
	 * 车系代码下拉选
	 */
	public List<Map> getGroupCodeListQuery(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("\n");
		sql.append(" SELECT tvmg.GROUP_ID, tvmg.GROUP_CODE, tvmg.GROUP_NAME \n");
		sql.append(" FROM TM_VHCL_MATERIAL_GROUP tvmg \n");
		sql.append(" WHERE tvmg.GROUP_LEVEL=2 AND tvmg.is_del = 0 \n");		
		List<Map> orderList = OemDAOUtil.findAll(sql.toString(), params);
		return orderList;
	}
	/**
	 * 工时维护 查询
	 * @param queryParam
	 * @return
	 */
	public PageInfoDto getRangeLabourQuery(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("\n");
		sql.append("	SELECT GROUP_CODE,ID,LABOUR_CODE,LABOUR_NAME,LABOUR_NUM ,1 DISCOUNT\n"); //表中无DISCOUNT字段，默认值=1
		sql.append("		FROM  tt_wr_labour_dcs twld \n");
		sql.append("		WHERE twld.LABOUR_CODE NOT IN (SELECT TWAL.LABOUR_CODE FROM TT_WR_ACTIVITY_LABOUR_DCS TWAL WHERE TWAL.ACTIVITY_ID = '"+queryParam.get("activityId2")+"' AND is_del=0 )  \n");
		if(!StringUtils.isNullOrEmpty(queryParam.get("labourCode"))){
			sql.append("			AND LABOUR_CODE LIKE '%"+queryParam.get("labourCode")+"%' \n");          
		 }
		if(!StringUtils.isNullOrEmpty(queryParam.get("labourName"))){
			sql.append("			AND LABOUR_NAME LIKE '%"+queryParam.get("labourName")+"%' \n");          
		 }
		if(!StringUtils.isNullOrEmpty(queryParam.get("groupCode"))){
			sql.append("			AND  GROUP_CODE = '"+queryParam.get("groupCode")+"' \n");         
		 }
		//sql.append("			AND LABOUR_CODE LIKE '%55554%' \n");
		//sql.append("			AND LABOUR_NAME LIKE '%55555%' \n");
		//sql.append("			AND  GROUP_CODE = 'L300C' \n");
		sql.append("			AND  IS_DEL =0 \n");
		//sql.append("			ORDER BY LABOUR_NUM DESC \n");
		sql.append(" \n");



		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql.toString(), params);
		return pageInfoDto;

	}
	
	/**
	 * 工时 查询
	 * @param queryParam
	 * @return
	 */
	public PageInfoDto getRangeLabourList(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("\n");
		sql.append("	SELECT TWAL.DETAIL_ID,TWAL.LABOUR_CODE,TWAL.LABOUR_NAME,TWAL.LABOUR_NUM,TWAL.DISCOUNT  \n");
		sql.append("		FROM TT_WR_ACTIVITY_LABOUR_DCS TWAL  \n");
		sql.append("		WHERE 1=1  AND is_del=0  \n");
		sql.append("			   AND  TWAL.ACTIVITY_ID ='"+Long.parseLong(queryParam.get("activityId1")) +"' \n");
		sql.append("		ORDER BY TWAL.DETAIL_ID \n");
		sql.append(" \n");

		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql.toString(), params);
		return pageInfoDto;

	}
	/**
	 * 配件维护 查询
	 * @param queryParam
	 * @return
	 */
	public PageInfoDto getRangePartQuery(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("\n");
		sql.append("	SELECT tppb.ID,tppb.PART_CODE,tppb.PART_NAME,tppb.DNP_PRICE,tppb.PART_PRICE , 1 DISCOUNT\n");//表中无DISCOUNT字段 ，默认为1
		sql.append("	FROM tt_pt_part_base_dcs tppb \n");
		sql.append("	WHERE PART_CODE NOT IN(SELECT PART_CODE FROM tt_wr_activity_part_dcs twap WHERE twap.ACTIVITY_ID = '"+queryParam.get("activityId2")+"' AND is_del=0 )  \n");
		if(!StringUtils.isNullOrEmpty(queryParam.get("partCode"))){
			sql.append("			AND TPPB.PART_CODE LIKE '%"+queryParam.get("partCode")+"%' \n");          
		 }
		if(!StringUtils.isNullOrEmpty(queryParam.get("partName"))){
			sql.append("			AND TPPB.PART_NAME LIKE '%"+queryParam.get("partName")+"%' \n");          
		 }		
		//sql.append("		AND TPPB.PART_CODE LIKE'%33%' \n");
		//sql.append("		AND TPPB.PART_NAME LIKE'%33%' \n");
		sql.append("		ORDER BY tppb.PART_NAME DESC \n");
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql.toString(), params);
		return pageInfoDto;

	}
	
	/**
	 * 配件 查询
	 * @param queryParam
	 * @return
	 */
	public PageInfoDto getRangePartList(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("\n");
		sql.append("	 SELECT twap.DETAIL_ID,twap.PART_CODE,twap.PART_NAME,twap.PART_AMOUNT,twap.DNP,twap.DISCOUNT,twap.MSRP  \n");
		sql.append("	 FROM  TT_WR_ACTIVITY_PART_DCS twap  \n");
		sql.append("	 WHERE 1=1  AND is_del=0 \n");
		sql.append("	 AND activity_Id='"+Long.parseLong(queryParam.get("activityId1")) +"' \n");
		sql.append("	 ORDER BY twap.DETAIL_ID \n");
		sql.append(" \n");
		
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql.toString(), params);
		return pageInfoDto;

	}
	
	/**
	 *  其他项目 维护 查询
	 * @param queryParam
	 * @return
	 */
	public PageInfoDto getRangeOtherQuery(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("\n");
		sql.append("	SELECT two.OTHER_FEE_ID,two.OTHER_FEE_CODE,two.OTHER_FEE_NAME  \n");
		sql.append("	FROM TT_WR_OTHERFEE_DCS two \n");
		sql.append("	WHERE IS_DEL=0 AND two.OTHER_FEE_CODE NOT IN(SELECT other_fee_code FROM TT_WR_ACTIVITY_Other_DCS  WHERE ACTIVITY_ID = '"+queryParam.get("activityId2")+"' )  \n");
		sql.append("	ORDER BY two.OTHER_FEE_NAME DESC \n");
		sql.append(" \n");

		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql.toString(), params);
		return pageInfoDto;

	}
	
	/**
	 * 其他项目 查询
	 * @param queryParam
	 * @return
	 */
	public PageInfoDto getRangeOtherList(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("\n");
		sql.append("	SELECT TWAO.OTHER_REL_ID,TWAO.OTHER_FEE_CODE,TWAO.OTHER_FEE_NAME,TWAO.AMOUNT \n");
		sql.append("	FROM TT_WR_ACTIVITY_OTHER_DCS TWAO  \n");
		sql.append("	WHERE 1=1  AND  activity_Id='"+Long.parseLong(queryParam.get("activityId1")) +"' \n");
		sql.append("	ORDER BY TWAO.OTHER_FEE_NAME \n");
		sql.append(" \n");

		
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql.toString(), params);
		return pageInfoDto;

	}
	/**
	 * 根据VIN查找dealerCode
	 * @param queryParam
	 * @return
	 */
	public String findDealerCodeByVIN(String vin) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("\n");
		sql.append(" SELECT CONCAT(td.dealer_code,'A') DEALER_CODE FROM tt_vs_sales_report tvsr,tm_vehicle_DEC tm,tm_dealer td 	\n");
		sql.append(" 	WHERE tvsr.vehicle_id = tm.vehicle_id AND tvsr.dealer_id = td.dealer_id 	\n");
		sql.append(" 	and tm.vin=  '"+vin+"' \n");		
		Map map = OemDAOUtil.findFirst(sql.toString(), params);
		return map.get("DEALER_CODE").toString();
	}
	/**
	 * 根据dealerCode查找DealerName
	 * @param queryParam
	 * @return
	 */
	public String findDealerNameByCode(String dealerCode) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("\n");
		sql.append("  SELECT * FROM tm_dealer WHERE DEALER_CODE = '"+dealerCode+"'      \n");
		Map map = OemDAOUtil.findFirst(sql.toString(), params);
		return map.get("DEALER_SHORTNAME").toString();
	}
	/**
	 * 查询临时表数据
	 * @return
	 */
	public List<Map> findTmpWrActivityVehicleDcsList() {

		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("\n");
		sql.append("  SELECT * FROM TMP_WR_ACTIVITY_VEHICLE_DCS  \n");
		List<Map> list = OemDAOUtil.findAll(sql.toString(), params);
		return list;

	}
	/**
	 * 检查vin是否存在
	 * @param activityId
	 * @return
	 */
	public List<Map> checkDataVIN(String activityId) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("\n");
		sql.append(" SELECT p1.ID ID, p1.VIN VIN, CONCAT('VIN',p1.VIN, '已经存在') ERROR		\n");
		sql.append("  FROM TMP_WR_ACTIVITY_VEHICLE_DCS p1, TT_WR_ACTIVITY_VEHICLE_DCS p2		\n");
		sql.append("  WHERE p1.VIN = p2.VIN 		\n");
		sql.append("  and p2.ACTIVITY_ID = "+activityId+"  order by p1.ID		\n");
		List<Map> resultList = OemDAOUtil.findAll(sql.toString(), params);
		return resultList;
	}
	/**
	 * 检查数据是否重复
	 * @return
	 */
	public List<Map> checkDataTmpTableDump() {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("\n");
		sql.append("  SELECT p1.ID ID, CONCAT('与第：',p2.ID, '行数据重复') ERROR	\n");
		sql.append("   FROM TMP_WR_ACTIVITY_VEHICLE_DCS p1, TMP_WR_ACTIVITY_VEHICLE_DCS p2	\n");
		sql.append("  WHERE p1.VIN = p2.VIN	\n");
		sql.append("   AND p1.ID <> p2.ID	\n");
		List<Map> resultList = OemDAOUtil.findAll(sql.toString(), params);
		return resultList;
	}
	
	/**
	 * 校验VIN是否在车辆表中存在
	 * @return
	 */
	public List<Map> tableCheckNotVinDump() {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("\n");
		sql.append("  SELECT TMP.ID, CONCAT('车辆表中不存在VIN：', TMP.VIN) ERROR	\n");
		sql.append("  FROM TMP_WR_ACTIVITY_VEHICLE_DCS TMP 	\n");
		sql.append("    WHERE TMP.VIN NOT IN (SELECT VIN FROM TM_VEHICLE_DEC)	\n");

		List<Map> resultList = OemDAOUtil.findAll(sql.toString(), params);
		return resultList;
	}
	
	/**
	 * 校验经销商CODE是否在经销表中存在
	 * @return
	 */
	public List<Map> tableCheckNotDealerDump() {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("\n");
		sql.append("  SELECT TMP.ID, CONCAT('经销商列表表中不存在代码：',TMP.DEALER_CODE ) ERROR	\n");
		sql.append("     FROM TMP_WR_ACTIVITY_VEHICLE_DCS TMP	\n");
		sql.append("   WHERE TMP.DEALER_CODE NOT IN (SELECT DEALER_CODE FROM tm_dealer) 	\n");

		List<Map> resultList = OemDAOUtil.findAll(sql.toString(), params);
		return resultList;
	}
	
	
	/**
	 * 
	 */
	public boolean checkActivityCode(String activityCode) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("\n");
		sql.append("  SELECT * from TT_WR_ACTIVITY_DCS  WHERE  ACTIVITY_CODE = '"+activityCode+"' \n");
		
		boolean flag = false;
		List<Map> resultList = OemDAOUtil.findAll(sql.toString(), params);
		if(resultList !=null && resultList.size()>0){
			flag = true;
		}

		return flag;
	}
	
}











