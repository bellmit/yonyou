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
 * 超48小时未结算
 * @author Administrator
 *
 */
@Repository
public class ThreePackWarnNotSettledQueryDao extends OemBaseDAO{
	
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);

	/**
	 * 三包预警车辆查询(未结算)
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
		sql.append("select * from (select t1.*,DATE_FORMAT(TV.PURCHASE_DATE,'%Y-%m-%d') PURCHASE_DATE,c.color, \n");
		sql.append("  (case when tv.LICENSE_NO is null then '无牌照' else  tv.LICENSE_NO end) LICENSE_NO \n");
		sql.append("    from( \n");
		sql.append("      select twrns.repair_no RO_NO,twrns.REPAIR_ID,twrns.VIN,twrns.make_date, \n");
		sql.append("         twrns.CUSTOMER_NAME CTM_NAME,twrns.CUSTOMER_TEL MAIN_PHONE, \n");
		//“经销商简称” 、“经销商代码”取工单中的维修经销商
		sql.append("         twrns.DEALER_NAME DEALER_SHORTNAME, \n");
		sql.append("         twrns.DEALER_CODE DEALER_CODE, \n");
		
		sql.append("         twrns.ORDER_TYPE ORDER_TYPE, \n");
		sql.append("         (DATEDIFF(current_timestamp,twrns.make_date)) DEAYHOUR, \n");
		sql.append("     tw.REASON     from tt_Wr_Repair_not_settle_dcs twrns \n");
		sql.append("     left join TM_DEALER td \n");
		sql.append("     on twrns.DEALER_ID = td.DEALER_ID  \n");
		sql.append("     left join TM_VEHICLE_DEC e  \n");
		sql.append("     on td.DEALER_ID = e.DEALER_ID \n");
		sql.append("     left join ("+getVwMaterialSql()+") vm on vm.MATERIAL_ID = e.MATERIAL_ID \n");
		sql.append("     left join TT_VS_SALES_REPORT tvsr  \n");
		sql.append("     on e.VEHICLE_ID = tvsr.VEHICLE_ID  \n");
		sql.append("     left join TT_VS_CUSTOMER tvc \n");
		sql.append("     on tvsr.CTM_ID = tvc.CTM_ID \n");
		sql.append("     left join TT_THREEPACK_WARN_not_settle_dcs n  \n");
		sql.append("     on e.VIN = n.VIN \n");
		sql.append("     LEFT JOIN \n");
		sql.append("     (SELECT \n");
		sql.append("     distinct (case when twrnsr.wait_Work_Reason is not null and twrnsr.wait_Material_Part_Name is not null then '2' \n");
		sql.append("                    when twrnsr.wait_Work_Reason is not null then '0'   \n");
		sql.append("                    when twrnsr.wait_Material_Part_Name is not null \n");
		sql.append("                    then '1' end) REASON ,repair_id  \n");
		sql.append("      FROM tt_wr_repair_not_settle_Reason_dcs twrnsr)  tw \n");
		sql.append("			on twrns.REPAIR_ID = tw.REPAIR_ID\n");
		sql.append("      where 1=1 AND (DATEDIFF(current_timestamp,twrns.make_date)) > 2  \n");
		if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
			sql.append("   AND UPPER(twrns.vin) like UPPER(?) ");
			params.add("%"+queryParam.get("vin")+"%");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("ctmName"))) {
			sql.append("   AND UPPER(twrns.CUSTOMER_NAME) like UPPER(?) ");
			params.add("%"+queryParam.get("ctmName")+"%");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("dealerCode"))) {
				sql.append("   AND td.dealer_code in (?) ");
				params.add(queryParam.get("dealerCode"));
		}else {
			sql.append("       AND td.dealer_code not in ('33250') \n");
		}
		//end
		if (!StringUtils.isNullOrEmpty(queryParam.get("modelId"))) {
			sql.append("   AND vm.model_id in (?) ");
			params.add(queryParam.get("modelId"));
		}
//		if(!"".equals(repairNo)&&!(null==repairNo)){
//			sql.append("		AND twrns.repair_no in ('"+repairNo+"') \n");
//		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("roNo"))) {
			sql.append("   AND twrns.repair_no in (?) ");
			params.add(queryParam.get("roNo"));
		}
		sql.append("  ) t1 \n");
		sql.append("    inner join TM_VEHICLE_DEC tv \n");
		sql.append("    on t1.vin = tv.vin \n");
		if (!StringUtils.isNullOrEmpty(queryParam.get("licenseNo"))) {
			sql.append("   AND tv.LICENSE_NO like UPPER(?) ");
			params.add("%"+queryParam.get("licenseNo")+"%");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("startdate"))) {
			sql.append("   AND DATE(TV.PURCHASE_DATE) >= ? \n");
			params.add(queryParam.get("startdate"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("enddate"))) {
			sql.append("   AND DATE(TV.PURCHASE_DATE) <= ? \n");
			params.add(queryParam.get("enddate"));
		}
		sql.append("    LEFT JOIN \n");
		sql.append("    (select vin, \n");
		sql.append("    max(CASE WHEN n.WARN_TIMES >= p.RED_STANDARD THEN 3 \n");
		sql.append("    WHEN n.WARN_TIMES >= p.ORANGE_STANDARD and n.WARN_TIMES < p.RED_STANDARD THEN 2  \n");
		sql.append("    WHEN n.WARN_TIMES >= p.YELLOW_STANDARD and n.WARN_TIMES < p.ORANGE_STANDARD THEN 1 \n");
		sql.append("    ELSE 0 \n");
		sql.append("    END) AS COLOR from  \n");
		sql.append("    TT_THREEPACK_WARN_DCS n ,TT_THREEPACK_WARN_PARA_DCS p where  n.WARN_ITEM_NO = p.ITEM_NO \n");
		sql.append("    group by vin) c \n");
		sql.append("    on c.vin=t1.vin \n");
		sql.append("  ) tx \n");
	//	sql.append("    order by c.COLOR ASC,t1.DEAYHOUR DESC,t1.make_date DESC  \n");
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
		String condition=null;
		if(!StringUtils.isNullOrEmpty(params.get("condition"))){
			 condition=params.get("condition").toString();
		}
		condition=condition.replace("R.REPAIR_DATE", " DATE_FORMAT (R.REPAIR_DATE, '%Y-%M-%d')");
		StringBuffer sqlStr= new StringBuffer();
		sqlStr.append(" SELECT DISTINCT R.DEALER_CODE, -- 经销商代码\n" );
		sqlStr.append("        R.DEALER_NAME, -- 经销商名称\n" );
		sqlStr.append("        R.REPAIR_ID, -- 主键ID\n" );
		sqlStr.append("        R.REPAIR_NO, -- 维修工单号\n" );
		sqlStr.append("        R.REPAIR_TYPE, -- 维修类型\n" );
		sqlStr.append("        R.VIN, -- 车架号\n" ); 
		sqlStr.append("        m.MATERIAL_NAME, -- 车型\n" );
		sqlStr.append("        v.MODEL_YEAR, -- 年款\n" );
		sqlStr.append("        R.MILLEAGE, -- 里程数\n" );
		sqlStr.append("        R.CUSTOMER_NAME, -- 客户\n" );
		sqlStr.append("        v.ENGINE_NO, -- 发动机号\n" );
		sqlStr.append("        R.MAIN_PART, -- 主因零部件\n" );
		sqlStr.append("        DATE_FORMAT (R.REPAIR_DATE, '%y-%M-%d') AS REPAIR_DATE_CHAR\n" );
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
		sqlStr.append("  	  AND R.IS_CLAIM = '"+OemDictCodeConstants.IF_TYPE_YES+"' -- 是索赔工单 \n");
		sqlStr.append("       AND R.ORDER_TYPE = '"+OemDictCodeConstants.REPAIR_ORD_TYPE_01+"' -- 索赔 \n");
		sqlStr.append("  	  AND R.REPAIR_TYPE in('"+OemDictCodeConstants.REPAIR_FIXED_TYPE_12+"','"+OemDictCodeConstants.REPAIR_FIXED_TYPE_17+"') --保修、 三包维修 \n");
		sqlStr.append("       AND R.STATUS = "+OemDictCodeConstants.REPAIR_ORD_BALANCE_TYPE_02+" \n");//已结算的
		if(!StringUtils.isNullOrEmpty(condition)){
			sqlStr.append("AND "+condition+"\n");
		}
		if(!StringUtils.isNullOrEmpty(params.get("partCode"))){
			String partCode=params.get("partCode").replaceAll(",", "','");
			sqlStr.append("AND r.repair_id in (SELECT TWRP.REPAIR_ID FROM TT_WR_REPAIR_PART_DCS TWRP WHERE 1=1 and part_code in ('?'))\n");
		    queryParam.add(partCode);
		}
	
		return sqlStr.toString();
	}
	/**
	 * /**
	 * 预警明细-客户信息
	 * @param vin
	 * @return
	 */
	public Map<String, Object> threePackWarnNotSettledInfo(String vin) {
		Map<String, Object> map = new HashMap<>();
		StringBuilder sql = new StringBuilder();
		sql.append("select e.VIN, e.LICENSE_NO, r.CTM_NAME, e.MILEAGE,  " +
				" v.MODEL_CODE, e.PURCHASE_DATE,td.DEALER_SHORTNAME,td.dealer_code \n");
		sql.append(" FROM TM_VEHICLE_DEC e left join TT_VS_SALES_REPORT t on e.VEHICLE_ID = t.VEHICLE_ID  \n");
		sql.append(" left join TT_VS_CUSTOMER r on t.CTM_ID = r.CTM_ID left join TM_DEALER td on t.DEALER_ID=td.DEALER_ID  \n");
		sql.append(" left join ("+getVwMaterialSql()+") v on v.MATERIAL_ID = e.MATERIAL_ID \n");
		sql.append("  left join TT_THREEPACK_WARN_not_settle_dcs n on e.vin = n.vin \n");
		sql.append(" WHERE e.vin = "+"'"+vin+"'");
		sql.append(" group by e.VIN, e.LICENSE_NO, r.CTM_NAME, e.MILEAGE, " +
				" v.MODEL_CODE, e.PURCHASE_DATE,td.DEALER_SHORTNAME,td.dealer_code ");
		List<Map> relist = OemDAOUtil.findAll(sql.toString(), null);
		if(relist != null && relist.size() > 0){
			map = relist.get(0);
		}	    
		return map;		
	}
	
	/**
	 * 预警明细-客户信息
	 * @param vin
	 * @return
	 */
	public Map<String,Object> threePackWarnInfo(String vin ,Long id){
		Map<String,Object> 	map = new HashMap();
		StringBuilder sql = new StringBuilder();
		sql.append("select e.VIN, e.LICENSE_NO, r.CTM_NAME, e.MILEAGE, n.WARN_ITEM_NO,n.WARN_ITEM_NAME,n.WARN_TIMES, e.ENGINE_NO, v.MODEL_CODE, e.PURCHASE_DATE,td.DEALER_SHORTNAME,td.dealer_code \n");
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
	 * 预警明细-发动机和变速器总成监控统计信息
	 * @param vin
	 * @return
	 */
	public PageInfoDto mapcount(String itemNo,String vin)throws Exception{
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT N.WARN_ITEM_NAME, N.WARN_TIMES, PA.LEGAL_STANDARD  \n");
		sql.append(" FROM TT_THREEPACK_WARN_not_settle_dcs N, TT_THREEPACK_WARN_PARA_DCS PA \n");
		sql.append(" WHERE N.WARN_ITEM_NO = "+"'"+itemNo+"'"+" and N.VIN = '"+vin+"' \n");
		sql.append(" AND N.WARN_ITEM_NO = PA.ITEM_NO");
		PageInfoDto relist =  OemDAOUtil.pageQuery(sql.toString(), null);
		return relist;		
	}

	/**
	 * 预警明细-发动机和变速器总成监控信息
	 * @param vin
	 * @return
	 */
	public PageInfoDto threePackNotSettledControl(String vin, String itemNo) {
		StringBuffer query = new StringBuffer();
		List<Object>  queryParam =new ArrayList<>();
		query.append("SELECT reppart.PART_CODE, \n");
		query.append("       reppart.PART_NAME, \n");
		query.append("       PA.LEGAL_STANDARD, \n");
		query.append("       PA.YELLOW_STANDARD, \n");
		query.append("       PA.ORANGE_STANDARD, \n");
		query.append("       PA.RED_STANDARD, \n");
		query.append("       COUNT(1) AS PARTNUM \n");
		query.append("  FROM TM_VEHICLE_DEC vhl, \n");
		query.append("       tt_Wr_Repair_not_settle_dcs rep, \n");
		query.append("          (SELECT * FROM TT_WR_REPAIR_PART_DCS WHERE DETAIL_ID IN ( ");
		query.append("  SELECT MIN(t1.DETAIL_ID) FROM TT_WR_REPAIR_PART_DCS  t1 ,tt_Wr_Repair_not_settle_DCS t2  ");
		query.append("   WHERE t1.REPAIR_ID = t2.REPAIR_ID AND t2.VIN =?  ");
		queryParam.add(vin);
		query.append( " GROUP BY t1.REPAIR_ID,t1.PART_CODE,t1.ITEM_NO HAVING SUM(t1.QUANTITY) > 0 )) reppart \n");
		query.append("       LEFT JOIN \n");
		query.append("          TT_THREEPACK_WARN_PARA_DCS PA \n");
		query.append("       ON reppart.ITEM_NO = pa.ITEM_NO \n");
		query.append(" WHERE     vhl.VIN = rep.vin \n");
		query.append("       AND rep.REPAIR_ID = reppart.REPAIR_ID \n");
		query.append("       AND reppart.ITEM_NO = '").append(itemNo).append("' \n");
		query.append("       AND vhl.MILEAGE < ").append(OemDictCodeConstants.TEN_WAN_MILLAGE).append(" \n");
		query.append("       AND ").append(OemDictCodeConstants.TWO_YEAR_DAYS).append(" > \n");
		query.append("              (DATEDIFF(rep.CREATE_DATE,vhl.PURCHASE_DATE) )   \n");
		query.append("       AND reppart.PART_CODE NOT IN (SELECT nt.PART_CODE \n");
		query.append("                                       FROM TT_THREEPACK_NOPART_DCS nt \n");
		query.append("                                      WHERE nt.is_del = ").append(OemDictCodeConstants.IS_DEL_00).append(") \n");
		query.append("       AND reppart.PART_CODE NOT IN \n");
		query.append("              (SELECT ACTPART.PART_CODE \n");
		query.append("                 FROM TT_WR_ACTIVITY_DCS ACT, TT_WR_ACTIVITY_PART_DCS ACTPART \n");
		query.append("                WHERE     ACT.ACTIVITY_ID = ACTPART.ACTIVITY_ID \n");
		query.append("                      AND ACT.ACTIVITY_CODE = reppart.ACTIVITY_CODE \n");
		query.append("                      AND ACT.IS_DEL = ").append(OemDictCodeConstants.IS_DEL_00).append(" \n");
		query.append("                      AND (ACT.ACTIVITY_TYPE = '").append(OemDictCodeConstants.ACTIVITY_TYPE_02).append("'                --TSB \n");
		query.append("                      	OR ACT.ACTIVITY_TYPE = '").append(OemDictCodeConstants.ACTIVITY_TYPE_03).append("'                --CSN \n");
		query.append("                      	OR ACT.ACTIVITY_TYPE = '").append(OemDictCodeConstants.ACTIVITY_TYPE_04).append("'))            --RECALL \n");
		query.append("       AND vhl.VIN NOT IN (SELECT ne.VIN \n");
		query.append("                             FROM TT_THREEPACK_NOVEHICLE_DCS ne \n");
		query.append("                            WHERE ne.IS_DEL = ").append(OemDictCodeConstants.IS_DEL_00).append(") \n");
		query.append("       AND rep.IS_CLAIM = ").append(OemDictCodeConstants.IF_TYPE_YES).append(" \n");
		query.append("       AND vhl.vin = ?");
		queryParam.add(vin);
		query.append("GROUP BY reppart.PART_CODE, \n");
		query.append("         reppart.PART_NAME, \n");
		query.append("         PA.LEGAL_STANDARD, \n");
		query.append("         PA.YELLOW_STANDARD, \n");
		query.append("         PA.ORANGE_STANDARD, \n");
		query.append("         PA.RED_STANDARD \n");
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(query.toString(), null);
		return pageInfoDto;
	}


	/**
	 * 获取三包预警信息
	 * @return
	 */
	public Map<String,Object> getThreePackWarnItems(){
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT T.VIN,\n");
		sql.append("   VARCHAR(REPLACE(REPLACE(XML2CLOB(XMLAGG(XMLELEMENT(NAME A,\n");
		sql.append("                                                      T.WARN_ITEM_NO||'='||T.WARN_TIMES||','))),\n");
		sql.append("                          '<A>',\n");
		sql.append("                          ''),\n");
		sql.append("                  '</A>',\n");
		sql.append("                  ' ')) AS WARN_ITEMS\n");
		sql.append(" FROM TT_THREEPACK_WARN_not_settle_dcs T\n");
		sql.append(" GROUP BY T.VIN\n");
		List<Map<String, Object>> list = pageQuery(sql.toString(), null,getFunName());
		Map<String,Object> map = new HashMap<String,Object>();
		for(Map<String,Object> m:list){
			map.put(m.get("VIN").toString(), m.get("WARN_ITEMS"));
		}
		return map;
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
		sql.append(" FROM TT_WR_REPAIR_ITEM_DCS T WHERE T.REPAIR_ID IN (" + repairId +") \n");
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
		sql.append("                                                       T.PART_CODE||'【'||T.PART_NAME||'】,'))),\n");
		sql.append("                          '<A>',\n");
		sql.append("                          ''),\n");
		sql.append("                  '</A>',\n");
		sql.append("                  ' ')) AS PART_INFO\n");
		sql.append(" FROM TT_WR_REPAIR_PART T WHERE T.REPAIR_ID IN (" + repairId +") \n");
		sql.append(" GROUP BY T.REPAIR_ID\n");
		List<Map<String, Object>> list = pageQuery(sql.toString(), null,getFunName());
		Map<String,String> resMap = new HashMap<String,String>();
		for(Map<String, Object> m:list){
			resMap.put(CommonUtils.checkNull(m.get("REPAIR_ID")), CommonUtils.checkNull(m.get("PART_INFO")));
		}
		return resMap;
	}

	/**
	 * 未结算原因下载
	 * @return
	 */
	public List<Map> threePackWarnNotSettleDownloadDownload() {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("select twrns.repair_no RO_NO,twrns.VIN,twrnsr.wait_Material_Part_Quantity, \n");
		sql.append("     twrnsr.operator,twrnsr.wait_Work_Reason,twrnsr.wait_Work_Remark,twrnsr.wait_Material_Part_Name,twrnsr.wait_Material_Part_Code, \n");
		sql.append("     twrnsr.wait_Material_Order_No,twrnsr.wait_Material_Order_Date,twrnsr.wait_Material_Remark,twrnsr.create_date \n");
		sql.append("    from tt_Wr_Repair_not_settle_dcs twrns left join \n");
		sql.append("    tt_wr_repair_not_settle_Reason_dcs twrnsr on twrns.REPAIR_ID=twrnsr.REPAIR_ID \n");
		sql.append("  order by twrns.REPAIR_NO DESC \n");
		List<Map> list =OemDAOUtil.findAll(sql.toString(), null);
		return list;
	}

	/**
	 * 600颜色数据
	 * @return
	 */
	public Map<String,Object> color600() {
	
		StringBuffer sql = new StringBuffer();
		sql.append(" select p.WARN_STANDARD,p.YELLOW_STANDARD,p.ORANGE_STANDARD,p.RED_STANDARD from TT_THREEPACK_WARN_PARA_DCS P where item_no = 600 ");
		List<Map> resultList = OemDAOUtil.findAll(sql.toString(), null);
		 Map<String,Object>	map =new HashMap<>();
		for(Map<String, Object> m:resultList){
			 map=(Map) m.get(0);
		}
		return map;
	}
	/**
	 * 600颜色数据
	 * @return
	 */
	public List<Map> threepackWarnParaPOColor600() {
		
		StringBuffer sql = new StringBuffer();
		sql.append(" select p.WARN_STANDARD,p.YELLOW_STANDARD,p.ORANGE_STANDARD,p.RED_STANDARD from TT_THREEPACK_WARN_PARA_DCS P where item_no = 600 ");
		List<Map> list =OemDAOUtil.findAll(sql.toString(), null);
		return list;
	}
	
	
	/**
	 * 怠工
	 * @param reasonId
	 * @return
	 */
	public PageInfoDto findTtWrRepairNotSettleReasonByReasonId(
			Long repairId) {
		// TODO Auto-generated method stub
		String sql = "select wait_Work_Reason,wait_Work_Remark,DATE_FORMAT(create_Date,'%Y-%m-%d') AS create_Date,DATE_FORMAT(update_Date,'%Y-%m-%d') AS update_Date from Tt_Wr_Repair_Not_Settle_Reason_DCS where wait_Work_Reason is not null and repair_id = " + repairId;
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql.toString(), null);
		return pageInfoDto;
	}

	/**
	 * 待料
	 * @param repairId
	 * @return
	 */
	public PageInfoDto findTtWrRepairNotSettleReasonByRepairId(
			Long repairId) {
		// TODO Auto-generated method stub
		String sql = "select wait_Material_Part_Quantity,wait_Material_Part_Name,wait_Material_Part_Code,wait_Material_Order_No,DATE_FORMAT(wait_Material_Order_Date,'%Y-%m-%d') AS wait_Material_Order_Date ,wait_Material_Remark,DATE_FORMAT(create_Date,'%Y-%m-%d') AS create_Date,DATE_FORMAT(update_Date,'%Y-%m-%d') AS update_Date from Tt_Wr_Repair_Not_Settle_Reason_DCS where wait_Material_Part_Name is not null and repair_id = " + repairId;
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql.toString(), null);
		return pageInfoDto;
	}

  
}
