package com.yonyou.dms.vehicle.dao.threePack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.function.utils.common.StringUtils;
/**
 * 三包预警车辆查询
 * @author Administrator
 *
 */
@Repository
public class ThreePackWarnQueryDao  extends OemBaseDAO{
	
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);

	/**
	 * 预警车辆查询方法
	 * @param vin
	 * @param plateNo
	 * @param ctmName
	 * @param color
	 * @param curPage
	 * @param pageSize
	 * @return
	 */
	public PageInfoDto findItem(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam, params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;
	}
	/**
	 * 下载
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<Map> queryEmpInfoforExport(Map<String, String> queryParam) throws Exception {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam, params);
		return OemDAOUtil.findAll(sql.toString(), params);

	}
	private String getQuerySql(Map<String, String> queryParam, List<Object> params) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT  \n");
		sql.append("       TW.ID, \n");
		sql.append("       TW.VIN,  \n");
		sql.append("       TW.LICENSE_NO,  \n");
		sql.append("       SR.CTM_NAME,  \n");
		sql.append("       TW.MODEL_CODE, \n");
		sql.append("       IFNULL(SR.DEALER_CODE || 'A', WR.DEALER_CODE) AS DEALER_CODE,\n");
		sql.append("       IFNULL(SR.DEALER_SHORTNAME, WR.DEALER_NAME) AS DEALER_SHORTNAME, \n");
		sql.append("       TW.WARN_ITEM_NO,  \n");
		sql.append("       TW.WARN_ITEM_NAME, \n");
		sql.append("      case TW.COLOR when '1' then '黄色预警' when '2' then '橙色预警' when '3' then '红色预警'else '无预警信息' end as COLOR,  \n");
		sql.append("       WARN_DATE,  \n");
		sql.append("       TW.WARN_ITEMS  \n");
		sql.append("  FROM (SELECT V.VIN, V.LICENSE_NO, TW.WARN_ITEM_NO, TW.WARN_ITEM_NAME, V.VEHICLE_ID, M.MODEL_CODE,M.MODEL_ID,TW.ID,\n");
		sql.append("   TW.WARN_ITEM_NO||'='||TW.WARN_TIMES AS WARN_ITEMS,\n");
		sql.append("               MAX(CASE WHEN TW.WARN_TIMES >= TWP.RED_STANDARD THEN 3 \n");
		sql.append("                        WHEN TW.WARN_TIMES >= TWP.ORANGE_STANDARD AND TW.WARN_TIMES < TWP.RED_STANDARD THEN 2 \n");
		sql.append("                        WHEN TW.WARN_TIMES >= TWP.YELLOW_STANDARD AND TW.WARN_TIMES < TWP.ORANGE_STANDARD THEN 1 \n");
		sql.append("                        ELSE 0 END) AS COLOR, \n");
		sql.append("               MAX(CASE WHEN TW.UPDATE_DATE IS NOT NULL THEN TW.UPDATE_DATE \n");
		sql.append("                        WHEN TW.CREATE_DATE IS NOT NULL THEN TW.CREATE_DATE \n");
		sql.append("                        ELSE '' END) AS WARN_DATE \n");
		sql.append("          FROM TT_THREEPACK_WARN_DCS TW \n");
		sql.append("         INNER JOIN TM_VEHICLE_DEC V ON V.VIN = TW.VIN \n");
		sql.append("         INNER JOIN ("+getVwMaterialSql()+") M ON M.MATERIAL_ID = V.MATERIAL_ID \n");
		sql.append("         INNER JOIN TT_THREEPACK_WARN_PARA_DCS TWP ON TWP.ITEM_NO = TW.WARN_ITEM_NO \n");
		sql.append("         WHERE TW.IS_DEL = '0' AND LENGTH(TW.VIN) = '17' \n");

		if (!StringUtils.isNullOrEmpty(queryParam.get("itemNo"))) {
			sql.append("   AND TW.WARN_ITEM_NO = ? ");
			params.add(queryParam.get("itemNo"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
			sql.append("   AND TW.VIN = ? ");
			params.add(queryParam.get("vin"));
		}
//		if(!"".equals(modelId)&&!(null==modelId)){			//车型不为空
//			sql.append("   AND M.MODEL_ID IN ("+modelId+") \n");
//		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("modelId"))) {
			sql.append("   AND M.MODEL_ID =? ");
			params.add(queryParam.get("modelId"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("plateNo"))) {
			sql.append("   AND V.LICENSE_NO = ? ");
			params.add(queryParam.get("plateNo"));
		}
		sql.append("         GROUP BY V.VIN, V.LICENSE_NO, TW.WARN_ITEM_NO, TW.WARN_ITEM_NAME, V.VEHICLE_ID, M.MODEL_CODE,M.MODEL_ID,TW.ID,TW.WARN_ITEM_NO||'='||TW.WARN_TIMES) TW \n");
		sql.append("  LEFT JOIN (SELECT D.DEALER_CODE, D.DEALER_SHORTNAME, C.CTM_NAME, SR.VEHICLE_ID \n");
		sql.append("               FROM TT_VS_SALES_REPORT SR \n");
		sql.append("              INNER JOIN TM_DEALER D ON D.DEALER_ID = SR.DEALER_ID \n");
		sql.append("              INNER JOIN TT_VS_CUSTOMER C ON C.CTM_ID = SR.CTM_ID \n");
		sql.append("              WHERE (C.CTM_TYPE IS NULL OR C.CTM_TYPE = '"+OemDictCodeConstants.CTM_TYPE_01+"') \n");
		sql.append("                AND D.DEALER_TYPE = '10771001' \n");
		sql.append("                AND (SR.VEHICLE_TYPE IS NULL OR \n");
		sql.append("                     SR.VEHICLE_TYPE ="+OemDictCodeConstants.VEHICLE_NATURE_TYPE_01+" OR \n");
		sql.append("                     SR.VEHICLE_TYPE = "+OemDictCodeConstants.VEHICLE_NATURE_TYPE_02+" OR\n");
		sql.append("                     SR.VEHICLE_TYPE = "+OemDictCodeConstants.VEHICLE_USE_01+")) SR ON SR.VEHICLE_ID = TW.VEHICLE_ID \n");
		sql.append(" INNER JOIN (SELECT DISTINCT VIN, DEALER_CODE, DEALER_NAME, MAX(CREATE_DATE) FROM TT_WR_REPAIR_DCS \n");
		sql.append("              WHERE LENGTH(VIN) = '17' AND ORDER_TYPE = '"+OemDictCodeConstants.REPAIR_ORD_TYPE_01+"' \n");
		sql.append("              GROUP BY VIN, DEALER_CODE, DEALER_NAME) WR ON WR.VIN = TW.VIN \n");
		sql.append(" WHERE TW.COLOR IN ('黄色预警', '橙色预警', '红色预警')  \n");
		sql.append("   AND IFNULL(SR.DEALER_CODE, WR.DEALER_CODE) IS NOT NULL \n");

		//车主姓名
		String ctmName=queryParam.get("ctmName");
		if (!StringUtils.isNullOrEmpty(ctmName)) {
			sql.append("   AND SR.CTM_NAME like UPPER(?) ");
			params.add("%"+ctmName+"%");
		}
		//预警级别
		if (!StringUtils.isNullOrEmpty(queryParam.get("color"))) {
			sql.append("   AND TW.COLOR = ? ");
			params.add(queryParam.get("color"));
		}
		//经销商不为空
		String dealerCode=queryParam.get("dealerCode");
		if (!StringUtils.isNullOrEmpty(dealerCode)) {
			sql.append("   AND SR.DEALER_CODE LIKE UPPER(?) ");
			params.add("%"+dealerCode+"%");
			sql.append("   OR WR.DEALER_CODE LIKE UPPER(?) ");
			params.add("%"+dealerCode+"%");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("startdate"))) {
			sql.append("   AND DATE(WARN_DATE) >= ? \n");
			params.add(queryParam.get("startdate"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("enddate"))) {
			sql.append("   AND DATE(WARN_DATE) <= ? \n");
			params.add(queryParam.get("enddate"));
		}
		sql.append(" \n");
		sql.append(" GROUP BY TW.VIN, TW.LICENSE_NO, SR.CTM_NAME, TW.MODEL_CODE, SR.DEALER_CODE, WR.DEALER_CODE, SR.DEALER_SHORTNAME, \n");
		sql.append("          WR.DEALER_NAME, TW.WARN_ITEM_NO, TW.WARN_ITEM_NAME, TW.COLOR, WARN_DATE,TW.ID, TW.WARN_ITEMS \n");
		return sql.toString();
}
	
	/**
	 * 维修历史
	 * @param params
	 * @param pageSize
	 * @param curPage
	 * @return
	 */	
	public PageInfoDto findHis(Map<String, String> queryParam) {
			List<Object> params = new ArrayList<Object>();
			String sql = queryThreePackRepairHisInfo(queryParam, params);
			PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
			return pageInfoDto;
		}
	/**
	 * 下载
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<Map> queryHis(Map<String, String> queryParam) throws Exception {
		List<Object> params = new ArrayList<Object>();
		String sql = queryThreePackRepairHisInfo(queryParam, params);
		return OemDAOUtil.findAll(sql.toString(), params);

	}
	public String queryThreePackRepairHisInfo(Map<String, String> params, List<Object>  queryParam){
		StringBuffer sqlStr= new StringBuffer();
		sqlStr.append(" SELECT DISTINCT R.DEALER_CODE, \n" );
		sqlStr.append("        R.DEALER_NAME, \n" );
		sqlStr.append("        R.REPAIR_ID, \n" );
		sqlStr.append("        R.REPAIR_NO, \n" );
		sqlStr.append("        R.REPAIR_TYPE, \n" );
		sqlStr.append("        R.VIN, \n" ); 
		sqlStr.append("        m.MATERIAL_NAME,\n" );
		sqlStr.append("        v.MODEL_YEAR, \n" );
		sqlStr.append("        R.MILLEAGE,\n" );
		sqlStr.append("        R.CUSTOMER_NAME,\n" );
		sqlStr.append("        v.ENGINE_NO, \n" );
		sqlStr.append("        R.MAIN_PART, \n" );
		sqlStr.append("        DATE_FORMAT(R.REPAIR_DATE, '%y-%M-%d') AS REPAIR_DATE_CHAR\n" );
		sqlStr.append("  FROM TT_WR_REPAIR_DCS R\n" );
		sqlStr.append("       inner join TM_VEHICLE_DEC v on v.VIN = R.VIN \n" );
		sqlStr.append("       inner join ("+getVwMaterialSql()+") m on m.MATERIAL_ID = v.MATERIAL_ID \n" );
		sqlStr.append("       inner join  TT_WR_REPAIR_PART_DCS P on P.REPAIR_ID = R.REPAIR_ID \n" );
		sqlStr.append("       inner join TT_THREEPACK_PTITEM_RELATION_DCS TR on TR.PART_CODE=P.PART_CODE \n" );
		sqlStr.append("       inner join TT_THREEPACK_ITEM_DCS TI on TI.ID=TR.ITEM_ID \n" );
		sqlStr.append(" WHERE    1 = 1\n" );
		if(!StringUtils.isNullOrEmpty(params.get("itemNo"))){      //项目编号
			sqlStr.append("       AND TI.ITEM_NO = ? \n" );
			queryParam.add(params.get("itemNo"));
		}
		sqlStr.append("       AND R.IS_DEL = 0\n" );
		sqlStr.append("  	  AND R.IS_CLAIM = '"+OemDictCodeConstants.IF_TYPE_YES+"' \n");
		sqlStr.append("       AND R.ORDER_TYPE = '"+OemDictCodeConstants.REPAIR_ORD_TYPE_01+"' \n");
		sqlStr.append("  	  AND R.REPAIR_TYPE in('"+OemDictCodeConstants.REPAIR_FIXED_TYPE_12+"','"+OemDictCodeConstants.REPAIR_FIXED_TYPE_17+"')  \n");
		sqlStr.append("       AND R.STATUS = "+OemDictCodeConstants.REPAIR_ORD_BALANCE_TYPE_02+" \n");//已结算的
		if(!StringUtils.isNullOrEmpty(params.get("repairType"))){      //维修类型
			sqlStr.append("       AND R.REPAIR_TYPE = ? \n" );
			queryParam.add(params.get("repairType"));
		}
		if(!StringUtils.isNullOrEmpty(params.get("repairNo"))){      //工单号
			sqlStr.append("       AND R.REPAIR_NO = ? \n" );
			queryParam.add(params.get("repairNo"));
		}	
		if (!StringUtils.isNullOrEmpty(params.get("startdate"))) {    //维修时间
			sqlStr.append("   AND DATE(REPAIR_DATE_CHAR) >= ? \n");
			queryParam.add(params.get("startdate"));
		}
		if (!StringUtils.isNullOrEmpty(params.get("enddate"))) {
			sqlStr.append("   AND DATE(REPAIR_DATE_CHAR) <= ? \n");
			queryParam.add(params.get("enddate"));
		}		
		if(!StringUtils.isNullOrEmpty(params.get("partCode"))){
			String partCode=params.get("partCode").replaceAll(",", "','");
			sqlStr.append("AND r.repair_id in (SELECT TWRP.REPAIR_ID FROM TT_WR_REPAIR_PART_DCS TWRP WHERE 1=1 and part_code in ('?'))\n");
		    queryParam.add(partCode);
		}
		return sqlStr.toString();
	}
	
	/**
	 * 预警明细-发动机和变速器总成监控统计信息
	 * @param vin
	 * @return
	 */
	public List<Map> mapcount(String itemNo,String vin){
		List<Map> map = new ArrayList<>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT N.WARN_ITEM_NAME, N.WARN_TIMES, PA.LEGAL_STANDARD  \n");
		sql.append(" FROM TT_THREEPACK_WARN_DCS N, TT_THREEPACK_WARN_PARA_DCS PA \n");
		sql.append(" WHERE N.WARN_ITEM_NO = "+"'"+itemNo+"'"+" and N.VIN = '"+vin+"' \n");
		sql.append(" AND N.WARN_ITEM_NO = PA.ITEM_NO");
		List<Map> relist = OemDAOUtil.findAll(sql.toString(), null);  
		return relist;		
	}
	
	/**
	 * 获取维修工单维修项目信息
	 * @return
	 */
	public Map<String,String> getThreePackItemInfo(String repairId){
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT T.REPAIR_ID,\n");
		sql.append("   VARCHAR(REPLACE(REPLACE(XML2CLOB(XMLAGG(XMLELEMENT(NAME A,\n");
		sql.append("                          T.LABOUR_CODE||'【'||T.LABOUR_NAME||'】,'))),\n");
		sql.append("                          '<A>',\n");
		sql.append("                          ''),\n");
		sql.append("                  '</A>',\n");
		sql.append("                  ' ')) AS ITEM_INFO\n");
		sql.append(" FROM TT_WR_REPAIR_ITEM_DCS T WHERE 1=1 \n");
		if(!StringUtils.isNullOrEmpty(repairId)){      //项目编号
			sql.append(" AND  T.REPAIR_ID IN (" + repairId +")\n" );
		}
		sql.append(" GROUP BY T.REPAIR_ID\n");
		List<Map<String, Object>> list = pageQuery(sql.toString(), null,getFunName());
		Map<String,String> resMap = new HashMap<String,String>();
		for(Map<String, Object> m:list){
			resMap.put(CommonUtils.checkNull(m.get("REPAIR_ID")), CommonUtils.checkNull(m.get("ITEM_INFO")));
		}
		return resMap;
	}
	/**
	 * 获取维修工单零件信息
	 * @return
	 */
	public Map<String,String> getThreePackPartInfo(String repairId){
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT T.REPAIR_ID,\n");
		sql.append("   VARCHAR(REPLACE(REPLACE(XML2CLOB(XMLAGG(XMLELEMENT(NAME A,\n");
		sql.append("                                                       T.PART_CODE||','))),\n");
		sql.append("                          '<A>',\n");
		sql.append("                          ''),\n");
		sql.append("                  '</A>',\n");
		sql.append("                  ' ')) AS PART_INFO\n");
		sql.append(" FROM TT_WR_REPAIR_PART_DCS T WHERE 1=1 \n");
		if(!StringUtils.isNullOrEmpty(repairId)){      //项目编号
			sql.append(" AND T.REPAIR_ID IN (" + repairId +")\n" );
		}
		sql.append(" GROUP BY T.REPAIR_ID\n");
		List<Map<String, Object>> list = pageQuery(sql.toString(), null,getFunName());
		Map<String,String> resMap = new HashMap<String,String>();
		for(Map<String, Object> m:list){
			resMap.put(CommonUtils.checkNull(m.get("REPAIR_ID")), CommonUtils.checkNull(m.get("PART_INFO")));
		}
		return resMap;
	}
	
	/**
	 * 获取维修工单维修项目信息
	 * @return
	 */
	public Map<String,String> getThreePackItemInfo2(String repairId){
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT T.REPAIR_ID,\n");
		sql.append("   VARCHAR(REPLACE(REPLACE(XML2CLOB(XMLAGG(XMLELEMENT(NAME A,\n");
		sql.append("                          T.LABOUR_CODE||'【'||T.LABOUR_NAME||'】,'))),\n");
		sql.append("                          '<A>',\n");
		sql.append("                          ''),\n");
		sql.append("                  '</A>',\n");
		sql.append("                  ' ')) AS ITEM_INFO\n");
		sql.append(" FROM TT_WR_REPAIR_ITEM_DCS T WHERE 1=1 \n");
		if(!StringUtils.isNullOrEmpty(repairId)){      //项目编号
			sql.append(" AND  T.REPAIR_ID IN (" + repairId +")\n" );
		}
		sql.append(" GROUP BY T.REPAIR_ID\n");
		List<Map<String, Object>> list = pageQuery(sql.toString(), null,getFunName());
		Map<String,String> resMap = new HashMap<String,String>();
		for(Map<String, Object> m:list){
			resMap.put(CommonUtils.checkNull(m.get("REPAIR_ID")), CommonUtils.checkNull(m.get("ITEM_INFO")));
		}
		return resMap;
	}
	/**
	 * 三包预警明细下载查询
	 * @param vin
	 * @param plateNo
	 * @param ctmName
	 * @param color
	 * @param dealerCode
	 * @param modelId
	 * @param startdate
	 * @param enddate
	 * @param isColor
	 * @param curPage
	 * @param pageSize
	 * @return
	 */
	public List<Map> threePackWarnDetailQuery(Map<String, String> params){
		List<Object>  queryParam =new ArrayList<>();
		StringBuffer sql = new StringBuffer();
		sql.append("\n");
		sql.append("WITH TA AS(select * from (");
		sql.append("select n.WARN_ITEM_NO||'='||n.WARN_TIMES AS WARN_ITEMS, n.VIN,n.WARN_ITEM_NO,n.WARN_ITEM_NAME, e.LICENSE_NO, CASE WHEN r.CTM_NAME IS NULL or r.CTM_NAME = 'NULL' THEN '' ELSE R.CTM_NAME END CTM_NAME, CASE WHEN r.MAIN_PHONE IS NULL OR R.MAIN_PHONE = 'NULL' THEN '' ELSE R.MAIN_PHONE END MAIN_PHONE, max (CASE  \n");
		sql.append(" WHEN n.WARN_TIMES >= p.RED_STANDARD THEN 3 \n");
		sql.append(" WHEN n.WARN_TIMES >= p.ORANGE_STANDARD and n.WARN_TIMES < p.RED_STANDARD THEN 2 \n");
		sql.append(" WHEN n.WARN_TIMES >= p.YELLOW_STANDARD and n.WARN_TIMES < p.ORANGE_STANDARD THEN 1 \n");
		sql.append(" ELSE 0 \n");
		sql.append(" END) AS color,--td.DEALER_SHORTNAME,td.dealer_code, \n");
		//“经销商简称” 、“经销商代码”取工单中的维修经销商
		sql.append(" (case when td.DEALER_SHORTNAME is null then (select distinct twr.DEALER_NAME from TT_WR_REPAIR_DCS twr where  \n");
		sql.append(" twr.create_date in (select max(create_date) from TT_WR_REPAIR_DCS where vin =n.VIN) \n");
		sql.append(" and twr.vin = n.VIN ) else td.DEALER_SHORTNAME end) DEALER_SHORTNAME, \n");
		sql.append(" (case when td.DEALER_CODE is null then (select distinct twr.DEALER_CODE from TT_WR_REPAIR_DCS twr where   \n");
		sql.append(" twr.create_date in (select max(create_date) from TT_WR_REPAIR_DCS where vin =n.VIN) \n");
		sql.append(" and twr.vin = n.VIN ) else td.DEALER_CODE end) dealer_code, \n");
		sql.append(" max(case when n.update_date is not null then n.update_date \n");
		sql.append(" 		  when n.create_date is not null then n.create_date \n");
		sql.append(" 		  else '' \n");
		sql.append(" 	 end) warn_date \n");
		sql.append(" FROM TM_VEHICLE_DEC e left join TT_VS_SALES_REPORT t on e.VEHICLE_ID = t.VEHICLE_ID  \n");
		sql.append("  left join TT_VS_CUSTOMER r on t.CTM_ID = r.CTM_ID \n");
		sql.append("  left join TM_DEALER td on t.DEALER_ID=td.DEALER_ID  \n");
		sql.append("  left join ("+getVwMaterialSql()+") vm on vm.MATERIAL_ID = e.MATERIAL_ID, \n");
		sql.append("   TT_THREEPACK_WARN_DCS n, \n");
		sql.append("   TT_THREEPACK_WARN_PARA_DCS p \n");
		sql.append(" WHERE e.VIN = n.VIN AND n.WARN_ITEM_NO = p.ITEM_NO \n");
		if(!StringUtils.isNullOrEmpty(params.get("itemNo"))){      //项目名称
			String itemNo=params.get("itemNo").substring(0, 3);
			sql.append(" and n.WARN_ITEM_NO = ? \n");
			queryParam.add(itemNo);
		}
		sql.append(" AND n.is_del = 0 \n");
		sql.append(" AND E.PURCHASE_DATE>DATE_FORMAT('2013-07-31','%y-%m-%d')      \n");
		sql.append(" AND (r.CTM_TYPE is null or r.CTM_TYPE = ").append(OemDictCodeConstants.CTM_TYPE_01).append(")             ---个人客户  \n");
		sql.append(" and (t.VEHICLE_TYPE is null or t.VEHICLE_TYPE = ").append(OemDictCodeConstants.VEHICLE_NATURE_TYPE_01).append(" or t.VEHICLE_TYPE = ").append(OemDictCodeConstants.VEHICLE_NATURE_TYPE_02).append(" or t.VEHICLE_TYPE = ").append(OemDictCodeConstants.VEHICLE_USE_01).append(") ---私家车   \n");
		
		if(!StringUtils.isNullOrEmpty(params.get("vin"))){      
			sql.append("		AND UPPER(n.VIN) like UPPER(?)\n");
			queryParam.add("%"+params.get("vin")+"%");
		}
		if(!StringUtils.isNullOrEmpty(params.get("plateNo"))){      
			sql.append("		AND UPPER(e.LICENSE_NO) like UPPER(?)\n");
			queryParam.add("%"+params.get("plateNo")+"%");
		}
		if(!StringUtils.isNullOrEmpty(params.get("ctmName"))){      
			sql.append("		AND UPPER(r.CTM_NAME) like UPPER(?)\n");
			queryParam.add("%"+params.get("ctmName")+"%");
		}
		if(!StringUtils.isNullOrEmpty(params.get("modelId"))){     
			String modelId=params.get("modelId").replaceAll(",", "','");
			sql.append(" and vm.model_id in (?) \n");
			queryParam.add(modelId);
		}
		sql.append(" GROUP BY n.VIN, e.LICENSE_NO, n.WARN_ITEM_NO,n.WARN_ITEM_NAME,r.CTM_NAME, r.MAIN_PHONE,td.DEALER_SHORTNAME,td.dealer_code ,n.WARN_ITEM_NO||'='||n.WARN_TIMES ORDER BY COLOR DESC \n");
		sql.append(") where 1=1 \n");

		if(!StringUtils.isNullOrEmpty(params.get("color"))){     
			sql.append(" and COLOR = ? \n");
			queryParam.add(params.get("color"));
		}
		if(!StringUtils.isNullOrEmpty(params.get("isColor"))){  
			sql.append(" and COLOR in (1,2,3) \n");
		}
		if (!StringUtils.isNullOrEmpty(params.get("startdate"))) {
			sql.append("   AND DATE(warn_date) >= ? \n");
			queryParam.add(params.get("startdate"));
		}
		if (!StringUtils.isNullOrEmpty(params.get("enddate"))) {
			sql.append("   AND DATE(warn_date) <= ? \n");
			queryParam.add(params.get("enddate"));
		}
		if (!StringUtils.isNullOrEmpty(params.get("dealerCode"))) {

			String dealerCode = params.get("dealerCode").replace("A", "");
			dealerCode = dealerCode.replace("a", "");
			dealerCode = dealerCode.replaceAll(",", "','");
			sql.append("		AND (dealer_code in (?)\n");
			queryParam.add(params.get("dealerCode"));
			sql.append("		    or dealer_code in (?))\n");
			queryParam.add(params.get("dealerCode")+"A");
		}
		sql.append(" order by COLOR desc \n");
		sql.append("),TB AS(\n");
		sql.append(" select DISTINCT R.REPAIR_ID,R.REPAIR_NO,R.VIN,R.ORDER_TYPE,R.REPAIR_TYPE,R.IS_CLAIM,R.REPAIR_DATE,R.CREATE_DATE,R.FINISH_DATE, \n");
		sql.append("(day(R.FINISH_DATE-R.REPAIR_DATE) + 1) as COST_DAYS,P.PART_CODE,TI.ITEM_NO \n");
		sql.append("from TT_WR_REPAIR_DCS R left join  TT_WR_REPAIR_DCS_PART_DCS P on P.REPAIR_ID =R.REPAIR_ID \n" );
		sql.append("INNER JOIN TT_THREEPACK_PTITEM_RELATION_DCS rel ON rel.PART_CODE = P.PART_CODE \n");
		sql.append("INNER JOIN TT_THREEPACK_ITEM_MINCLASS_DCS min  ON rel.MINCLASS_ID = min.ID \n");
		sql.append("INNER join TT_THREEPACK_ITEM_DCS TI on TI.ID=rel.ITEM_ID \n");
		sql.append("where  R.VIN in (select VIN from TA) \n");
     	sql.append("and R.REPAIR_TYPE in('"+OemDictCodeConstants.REPAIR_FIXED_TYPE_12+"','"+OemDictCodeConstants.REPAIR_FIXED_TYPE_17+"') --保修、 三包维修 \n");
		sql.append("and R.IS_CLAIM = '"+OemDictCodeConstants.IF_TYPE_YES+"' and R.ORDER_TYPE ='"+OemDictCodeConstants.REPAIR_ORD_TYPE_01+"')\n");
		sql.append("select DISTINCT TA.*,TB.REPAIR_ID,TB.REPAIR_NO,TB.ORDER_TYPE,TB.REPAIR_TYPE,TB.IS_CLAIM,TB.REPAIR_DATE,TB.CREATE_DATE,TB.FINISH_DATE,TB.COST_DAYS,TT.WARN_TIMES \n" );
		sql.append("from TA inner join TB on TB.VIN = TA.VIN AND TB.ITEM_NO=TA.WARN_ITEM_NO \n");
		sql.append("INNER join (select vin,warn_times,WARN_ITEM_NO from TT_THREEPACK_WARN_DCS ) TT \n" );
		sql.append("ON TT.VIN = TA.VIN and TT.WARN_ITEM_NO = TA.WARN_ITEM_NO  with ur \n");
		System.out.println("Mingxi"+sql.toString());
		List<Map> list = OemDAOUtil.findAll(sql.toString(), queryParam);
		return list;		
	}
	/**
	 * 预警明细-客户信息
	 * @param vin
	 * @return
	 */
	public Map<String,Object> threePackWarnInfo(String vin ,Long id){
		Map<String,Object> map = new HashMap();
		StringBuilder sql = new StringBuilder();
		sql.append("select e.VIN, e.LICENSE_NO, r.CTM_NAME, e.MILEAGE, n.WARN_ITEM_NO,n.WARN_ITEM_NAME,n.WARN_TIMES, e.ENGINE_NO, v.MODEL_CODE, DATE_FORMAT(e.PURCHASE_DATE,'%y-%m-%d')AS PURCHASE_DATE,td.DEALER_SHORTNAME,td.DEALER_CODE \n");
		sql.append(" FROM TM_VEHICLE_DEC e left join TT_VS_SALES_REPORT t on e.VEHICLE_ID = t.VEHICLE_ID  \n");
		sql.append(" left join TT_VS_CUSTOMER r on t.CTM_ID = r.CTM_ID left join TM_DEALER td on t.DEALER_ID=td.DEALER_ID  \n");
		sql.append(" left join ("+getVwMaterialSql()+") v on v.MATERIAL_ID = e.MATERIAL_ID, \n");
		sql.append(" TT_THREEPACK_WARN_DCS n \n");
		sql.append(" WHERE e.VIN = n.VIN AND e.vin = "+"'"+vin+"'");
		if(!StringUtils.isNullOrEmpty(id)){        
			sql.append("	 and n.ID="+id+"\n");
		}
		System.out.println(sql.toString());
		List<Map> relist = OemDAOUtil.findAll(sql.toString(), null);
		if(relist != null && relist.size() > 0){
			map = relist.get(0);
		}	    
		return map;		
	}
	
	/**
	 * 预警明细
	 * @param vin itemNO
	 * @return
	 */
	public PageInfoDto threePackControl(String vin, String itemNo){
		StringBuffer query = new StringBuffer();
		List<Object>  queryParam =new ArrayList<>();
		query.append("SELECT reppart.PART_CODE, \n");
		query.append("       reppart.PART_NAME, \n");
		query.append("       PA.LEGAL_STANDARD, \n");
		query.append("       PA.YELLOW_STANDARD, \n");
		query.append("       PA.ORANGE_STANDARD, \n");
		query.append("       PA.RED_STANDARD, \n"); 
	    query.append("       min.MINCLASS_NO, \n");
		query.append("       min.MINCLASS_NAME, \n");
		query.append("       COUNT(1) AS PARTNUM \n");
		query.append("  FROM (select t.ITEM_NO,t.PART_CODE,t.PART_NAME,t.DETAIL_ID,t.REPAIR_ID,t.ACTIVITY_CODE from TT_WR_REPAIR_PART_DCS t  where DETAIL_ID in (\n");
		query.append(" SELECT min(t1.DETAIL_ID) FROM TT_WR_REPAIR_PART_DCS  t1 ,TT_WR_REPAIR_DCS t2  \n");
		query.append(" where t1.REPAIR_ID = t2.REPAIR_ID and t2.VIN =?\n");
		queryParam.add(vin);
		query.append("  GROUP BY t1.REPAIR_ID,t1.PART_CODE,t1.ITEM_NO HAVING SUM(t1.QUANTITY) > 0 )) reppart \n");
		query.append("       LEFT JOIN TT_THREEPACK_WARN_PARA_DCS PA  ON reppart.ITEM_NO = pa.ITEM_NO  \n");
		query.append("       LEFT JOIN TT_WR_REPAIR_DCS rep on  rep.REPAIR_ID = reppart.REPAIR_ID \n");
		query.append("       LEFT JOIN TM_VEHICLE_DEC vhl on vhl.VIN=rep.VIN \n");
		query.append("       LEFT JOIN TT_THREEPACK_PTITEM_RELATION_DCS rel ON rel.PART_CODE = reppart.PART_CODE  \n");
		query.append("       LEFT JOIN TT_THREEPACK_ITEM_MINCLASS_DCS min  ON rel.MINCLASS_ID = min.ID \n");
		query.append("       INNER join TT_THREEPACK_ITEM_DCS TI on TI.ID=rel.ITEM_ID \n");
		query.append(" WHERE  rel.IS_DEL =0   \n");
		query.append("       AND min.IS_DEL =0 \n");
		query.append("       AND TI.ITEM_NO=?\n");
		queryParam.add(itemNo);
		query.append("       AND vhl.MILEAGE < ").append(OemDictCodeConstants.TEN_WAN_MILLAGE).append(" \n");
		query.append("       AND ").append(OemDictCodeConstants.TWO_YEAR_DAYS).append(" > \n");
		query.append("              (DATEDIFF(rep.CREATE_DATE,vhl.PURCHASE_DATE))   \n");
		query.append("       AND reppart.PART_CODE NOT IN (SELECT nt.PART_CODE \n");
		
		query.append("                                       FROM TT_THREEPACK_NOPART_DCS nt \n");
		query.append("                                      WHERE nt.is_del = ").append(OemDictCodeConstants.IS_DEL_00).append(") \n");
		query.append("       AND reppart.PART_CODE NOT IN \n");
		query.append("              (SELECT ACTPART.PART_CODE \n");
		query.append("                 FROM TT_WR_ACTIVITY_DCS ACT, TT_WR_ACTIVITY_PART_DCS ACTPART \n");
		query.append("                WHERE     ACT.ACTIVITY_ID = ACTPART.ACTIVITY_ID \n");
		query.append("                      AND ACT.ACTIVITY_CODE = reppart.ACTIVITY_CODE \n");
		query.append("                      AND ACT.IS_DEL = ").append(OemDictCodeConstants.IS_DEL_00).append(" \n");
		query.append("                      AND (ACT.ACTIVITY_TYPE = '").append(OemDictCodeConstants.ACTIVITY_TYPE_02).append("'                \n");
		query.append("                      	OR ACT.ACTIVITY_TYPE = '").append(OemDictCodeConstants.ACTIVITY_TYPE_03).append("'                \n");
		query.append("                      	OR ACT.ACTIVITY_TYPE = '").append(OemDictCodeConstants.ACTIVITY_TYPE_04).append("'))             \n");
		query.append("       AND vhl.VIN NOT IN (SELECT ne.VIN \n");
		query.append("                             FROM TT_THREEPACK_NOVEHICLE_DCS ne \n");
		query.append("                            WHERE ne.IS_DEL = ").append(OemDictCodeConstants.IS_DEL_00).append(") \n");
		query.append("		 AND rep.REPAIR_TYPE in('"+OemDictCodeConstants.REPAIR_FIXED_TYPE_12+"','"+OemDictCodeConstants.REPAIR_FIXED_TYPE_17+"')  \n");
		query.append("		 AND rep.IS_CLAIM = '"+OemDictCodeConstants.IF_TYPE_YES+"' AND rep.ORDER_TYPE ='"+OemDictCodeConstants.REPAIR_ORD_TYPE_01+"'\n");
		query.append("       AND vhl.vin = ?");
		queryParam.add(vin);
		query.append("GROUP BY reppart.PART_CODE, \n");
		query.append("         reppart.PART_NAME, \n");
		query.append("         PA.LEGAL_STANDARD, \n");
		query.append("         PA.YELLOW_STANDARD, \n");
		query.append("         PA.ORANGE_STANDARD, \n");
		query.append("         PA.RED_STANDARD, \n");
		query.append("         min.MINCLASS_NO, \n");
		query.append("         min.MINCLASS_NAME \n");
		System.out.println(query.toString());
		PageInfoDto list =  OemDAOUtil.pageQuery(query.toString(),queryParam);
        return list;		
	}
	/**
	 * 车型
	 * @param params
	 * @return
	 */
	
	 public List<Map> selectGroupName(Map<String, String> params) {
			List<Object> queryParam = new ArrayList<Object>();			
			StringBuffer sql = new StringBuffer();
			sql.append(" \n");
			sql.append("SELECT DISTINCT \n");
			sql.append("       C.GROUP_ID,  \n");
			sql.append("       C.GROUP_CODE,  \n");
			sql.append("       C.GROUP_NAME  \n");
			sql.append("  FROM TM_VHCL_MATERIAL_GROUP C \n");
			sql.append("   WHERE C.GROUP_LEVEL = ? \n");
			queryParam.add(OemDictCodeConstants.TM_VHCL_MATERIAL_GROUP_MODEL);
			if(!StringUtils.isNullOrEmpty(params.get("groupCode"))){      //车型代码
				sql.append("       AND  C.GROUP_CODE = ? \n" );
				queryParam.add(params.get("groupCode"));
			}
			if(!StringUtils.isNullOrEmpty(params.get("groupName"))){      //车型名称
				sql.append("       AND C.GROUP_NAME = ? \n" );
				queryParam.add(params.get("groupName"));
			}
			List<Map> resultList = OemDAOUtil.findAll(sql.toString(), queryParam);
			return resultList;
			
		}
}
