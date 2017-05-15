package com.yonyou.dms.vehicle.dao.activityRecallManage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.vehicle.domains.DTO.activityManage.TtRecallServiceDcsDTO;

/**
* @author liujiming
* @date 2017年4月10日
*/
@SuppressWarnings("all")

@Repository
public class RecallActivityBuildDao extends OemBaseDAO{

	
	/**
	 *  召回活动建立 主页面查询
	 * @param queryParam
	 * @return
	 */
	public PageInfoDto getRecallInitQuery(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getInitQuerySql(queryParam, params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;

	}
	/**
	 * 召回活动建立 主页面 下载
	 * @param queryParam
	 * @return
	 */
	public List<Map> getRecallInitQueryDownload(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getInitQuerySql(queryParam, params);
		List<Map> orderList = OemDAOUtil.downloadPageQuery(sql, params);
		return orderList;
	}
	
	/**
	 * SQL组装  
	 * 
	 * @param queryParam
	 * @param params
	 * @return
	 */
	private String getInitQuerySql(Map<String, String> queryParam, List<Object> params) {

		
		StringBuffer sql = new StringBuffer("\n");
		sql.append("SELECT RECALL_ID, #主键ID \n");
		sql.append("		RECALL_NO, #召回编号 \n");
		sql.append("		RECALL_NAME, #召回名称 \n");
		sql.append("		RECALL_THEME, #召回类别 \n");
		sql.append("		DATE_FORMAT(RECALL_START_DATE,'%Y-%c-%d') RECALL_START_DATE, #召回开始日期 \n");
		sql.append("		DATE_FORMAT(RECALL_END_DATE,'%Y-%c-%d') RECALL_END_DATE, #召回结束日期 \n");
		sql.append("		RECALL_STATUS #召回状态 \n");
		sql.append("	FROM TT_RECALL_SERVICE_DCS \n");
		sql.append("	WHERE 1=1 \n");
		//召回编号
		if(!StringUtils.isNullOrEmpty(queryParam.get("recallNo"))){
			sql.append("		AND RECALL_NO like '%"+queryParam.get("recallNo")+"%' \n");
		}
		//召回名称
		if(!StringUtils.isNullOrEmpty(queryParam.get("recallName"))){
			sql.append("		AND RECALL_NAME like '%"+queryParam.get("recallName")+"%' \n");
		}
		//召回开始时间
		if(!StringUtils.isNullOrEmpty(queryParam.get("recallStartDate"))){
			sql.append("		AND RECALL_START_DATE >= DATE_FORMAT('"+queryParam.get("recallStartDate")+"','%Y-%c-%d') \n");
		}
		//召回结束时间
		if(!StringUtils.isNullOrEmpty(queryParam.get("recallEndDate"))){
			sql.append("		AND RECALL_END_DATE <= DATE_FORMAT('"+queryParam.get("recallEndDate")+"','%Y-%c-%d') \n");
		}
		//召回状态
		if(!StringUtils.isNullOrEmpty(queryParam.get("recallStatus"))){
			sql.append("		AND RECALL_STATUS = '"+queryParam.get("recallStatus")+"' \n");
		}
		
		return sql.toString();
	}
	
	/**
	 * 召回活动编号是否存在
	 * @param trsdDto
	 * @return
	 */
	public Boolean checkRecallCode(TtRecallServiceDcsDTO trsdDto) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql=new StringBuffer();
		sql.append(" select COUNT(1) CNT from TT_RECALL_SERVICE_DCS where RECALL_NO = '"+trsdDto.getRecallNo()+"' ");
		List<Map> list = OemDAOUtil.findAll(sql.toString(), params);
		Boolean flag = true;
		if(null!=list&&list.size()>0){			
			if(Integer.parseInt(list.get(0).get("CNT").toString())>0){
				flag = false;
			}
			
		}
		return flag;
	}
	/**
	 * 服务活动编号是否存在
	 * @param trsdDto
	 * @return
	 */
	public Boolean checkActivityRecallNo(TtRecallServiceDcsDTO trsdDto) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql=new StringBuffer();
		sql.append(" SELECT COUNT(1) CNT FROM TT_WR_ACTIVITY_DCS WHERE ACTIVITY_CODE = '"+trsdDto.getRecallNo()+"' ");
		List<Map> list = OemDAOUtil.findAll(sql.toString(), params);
		Boolean flag = true;
		if(null!=list&&list.size()>0){			
			if(Integer.parseInt(list.get(0).get("CNT").toString())>0){
				flag = false;
			}
			
		}
		return flag;
	}
	
	/**
	 * 修改时  召回编号是否存在
	 * @param trsdDto
	 * @return
	 */
	public Boolean checkEditRecallNo(TtRecallServiceDcsDTO trsdDto) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("\n");
		sql.append("SELECT COUNT(*) CNT FROM TT_RECALL_SERVICE_DCS   T1 WHERE NOT EXISTS \n");
		sql.append("   (SELECT 1 FROM TT_RECALL_SERVICE_DCS T2 WHERE T1.RECALL_NO=T2.RECALL_NO \n");
		sql.append("	AND T2.RECALL_NO IN ('"+trsdDto.getRecallOldNo()+"')) AND T1.RECALL_NO='"+trsdDto.getRecallNo()+"'  \n");
		List<Map> list = OemDAOUtil.findAll(sql.toString(), params);
		Boolean flag = true;
		if(null!=list&&list.size()>0){			
			if(Integer.parseInt(list.get(0).get("CNT").toString())>0){
				flag = false;
			}
			
		}
		return flag;
	}
	
	/**
	 *  召回活动  修改页面 回显
	 * @param recallId
	 * @return
	 */
	public Map getEditRecallServiceQuery(Long recallId) {
		Map map = new HashMap();
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql=new StringBuffer();
		sql.append("	SELECT * FROM Tt_Recall_Service_DCS WHERE 1=1  AND Recall_Id= "+recallId+"	\n");
		List<Map> list = OemDAOUtil.findAll(sql.toString(), params);
		if(list !=null && list.size()>0){
			map = list.get(0);
		}
		
		return map;
	}
	
	public PageInfoDto getDealerRecallQueryList(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("\n");
		sql.append("   SELECT  TRD.ID, TRD.RECALL_ID, TRD.DEALER_CODE DEALER_ID,D.DEALER_CODE, D.DEALER_SHORTNAME \n");
		sql.append("		FROM TT_RECALL_DEALER_DCS TRD, TM_DEALER D \n");
		sql.append("		WHERE 1 = 1 \n");
		sql.append("	AND TRD.DEALER_CODE = D.DEALER_ID \n");
		sql.append("	AND D.DEALER_TYPE = 10771002 \n");
		sql.append("	AND  TRD.RECALL_ID ='"+queryParam.get("recallId1")+"' \n");
		//经销商
		if(!StringUtils.isNullOrEmpty(queryParam.get("dealerCode"))){
			sql.append(" AND D.DEALER_CODE in("+queryParam.get("dealerCode")+") \n");		
		}
		//经销商简称
		if(!StringUtils.isNullOrEmpty(queryParam.get("dealerName"))){
			sql.append("	AND D.DEALER_NAME like'%"+queryParam.get("dealerName")+"%' \n");		
		}
		
		
		sql.append("	 \n");
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql.toString(), params);
		return pageInfoDto;

	}
	
	public PageInfoDto getDealerRecallAddQueryList(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("\n");
		sql.append("SELECT (D.DEALER_ID || ',' || D.DEALER_CODE || ',' || D.DEALER_NAME) DEALER_ID_CODE_NAME, \n");
		sql.append("	D.DEALER_ID, \n");
		sql.append("	D.DEALER_CODE, \n");
		sql.append("	D.DEALER_NAME, \n");
		sql.append("	D.DEALER_SHORTNAME, \n");
		sql.append("	TCD.LICENSED_BRANDS \n");
		sql.append("  FROM TM_DEALER D, TM_COMPANY TC, TT_COMPANY_DETAIL TCD \n");
		sql.append("  WHERE 1 = 1 AND D.IS_DEL = 0 \n");
		sql.append("	AND D.COMPANY_ID = TC.COMPANY_ID  \n");
		sql.append("	AND TC.COMPANY_ID = TCD.COMPANY_ID  \n");
		sql.append("	AND D.DEALER_TYPE = 10771002  \n");
		sql.append("	AND D.STATUS = 10011001  \n");
		sql.append("	AND D.DEALER_LEVEL = 10851001  \n");
		sql.append("	AND D.DEALER_ID not in ( SELECT  DEALER_CODE FROM TT_RECALL_DEALER_DCS WHERE 1 = 1 AND  RECALL_ID ='"+queryParam.get("recallId2")+"' )  \n");
		
		//经销商
		if(!StringUtils.isNullOrEmpty(queryParam.get("dealerCode"))){
			sql.append(" AND D.DEALER_CODE in("+queryParam.get("dealerCode")+") \n");		
		}
		//经销商简称
		if(!StringUtils.isNullOrEmpty(queryParam.get("dealerName"))){
			sql.append(" AND D.DEALER_NAME like'%"+queryParam.get("dealerName")+"%' \n");		
		}
		//品牌
		if(!StringUtils.isNullOrEmpty(queryParam.get("brandName"))){
			sql.append(" AND TCD.LICENSED_BRANDS in ('"+queryParam.get("brandName")+"')  \n");	
		}
		
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql.toString(), params);
		return pageInfoDto;

	}
	
	
	/**
	 * 发布时判断是否存在参与经销商
	 * @param trsdDto
	 * @return
	 */
	public Boolean checkRecallDealer(Long recallId) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql=new StringBuffer();
		sql.append("   SELECT 1  FROM TT_RECALL_DEALER_DCS WHERE RECALL_ID = " + recallId );
		List<Map> list = OemDAOUtil.findAll(sql.toString(), params);
		Boolean flag = true;
		if(null!=list&&list.size()>0){						
				flag = false;
			
		}
		return flag;
	}
	
	
	/**
	 * 发布时判断是否存在未分配的车型
	 * @param trsdDto
	 * @return
	 */
	public Boolean checkRecallModel(Long recallId) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("\n");
		sql.append("SELECT DISTINCT TRV.RECALL_ID,VM.MATERIAL_ID,VM.BRAND_NAME,VM.SERIES_NAME, \n");
		sql.append("				VM.MODEL_NAME,VM.GROUP_NAME,VM.MODEL_YEAR, \n");
		sql.append("				VM.COLOR_NAME,VM.TRIM_NAME \n");
		sql.append("			FROM TT_RECALL_VEHICLE_DCS TRV, TM_VEHICLE_DEC TV, ("+getVwMaterialSql()+") VM \n");
		sql.append("			WHERE  TRV.VIN = TV.VIN AND TV.MATERIAL_ID = VM.MATERIAL_ID \n");
		sql.append("					AND TRV.RECALL_ID = "+recallId+" \n");
		sql.append("					AND NOT EXISTS (SELECT 1 FROM TT_RECALL_MATERIAL_DCS TRM \n");
		sql.append("							WHERE TRM.RECALL_ID = "+recallId+" \n");
		sql.append("							AND TRM.MATERIAL_ID=VM.MATERIAL_ID) \n");
		List<Map> list = OemDAOUtil.findAll(sql.toString(), params);
		Boolean flag = true;
		if(null!=list&&list.size()>0){						
				flag = false;			
			
		}
		return flag;
	}
	
	/**
	 *  VIN下载列表
	 * @param queryParam
	 * @return
	 */
	public List<Map> getrecallActivityVinDownLoadList(Long recallId) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("\n");
		sql.append("SELECT TRS.RECALL_NO, TRS.RECALL_NAME, TRV.VIN, #vin \n");
		sql.append("		TM.DEALER_CODE #经销商CODE \n");
		sql.append("	FROM TT_RECALL_VEHICLE_DCS TRV,TM_DEALER TM, TT_RECALL_SERVICE_DCS TRS \n");
		sql.append("	WHERE 1=1 AND TM.DEALER_ID = TRV.DUTY_DEALER AND TRS.RECALL_ID = TRV.RECALL_ID \n");
		sql.append("		AND TRV.RECALL_ID = '"+recallId+"' \n");
		sql.append("		ORDER BY TM.DEALER_CODE DESC \n");

		List<Map> orderList = OemDAOUtil.downloadPageQuery(sql.toString(), params);
		return orderList;
	}
	/**
	 *  召回项目下载列表
	 * @param queryParam
	 * @return
	 */
	public List<Map> getrecallActivityProjectDownLoadList(Long recallId) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("\n");
		sql.append("	SELECT * FROM (SELECT DISTINCT trs.RECALL_ID, trs.RECALL_NO, trs.RECALL_NAME,tma.GROUP_NO, \n");
		sql.append("		vm.SERIES_ID,vm.MODEL_ID,  \n");
		sql.append("		VM.BRAND_NAME,VM.SERIES_NAME,VM.MODEL_NAME,VM.GROUP_NAME,VM.MODEL_YEAR,VM.COLOR_NAME,VM.TRIM_NAME \n");
		sql.append("		FROM TT_RECALL_MATERIAL_DCS tma, ("+getVwMaterialSql()+") vm,TT_RECALL_SERVICE_DCS trs \n");
		sql.append("		WHERE tma.RECALL_ID= trs.RECALL_ID AND tma.MATERIAL_ID=vm.MATERIAL_ID   \n");
		sql.append("		AND tma.RECALL_ID='"+recallId+"') tmma \n");
		sql.append("		LEFT JOIN ( \n");
		sql.append("		SELECT DISTINCT trm.RECALL_ID, \n");	
		sql.append("		CASE WHEN  TMMH.GROUP_TYPE = 70391001 THEN VM.SERIES_ID WHEN  TMMH.GROUP_TYPE = 70391002 THEN VM.VM.MODEL_ID END SERIES_ID,		 \n");
		sql.append("		 tmmh.MANHOUR_CODE, tmmh.MANHOUR_NAME,tmmh.MANHOUR_NUM,trm.REBATE,trm.GROUP_NO \n");
		sql.append("		FROM TT_RECALL_MANHOUR_DCS trm,TT_MMH_MAN_HOUR_DCS tmmh, ("+getVwMaterialSql()+") VM WHERE tmmh.MMH_ID = trm.MANHOUR_ID \n");
		sql.append("      	AND (vm.SERIES_ID=tmmh.SERIES_ID OR vm.MODEL_ID=tmmh.SERIES_ID) \n");
		sql.append("		) b \n");
		sql.append("		ON tmma.RECALL_ID = b.RECALL_ID   \n");
		sql.append("		AND (tmma.SERIES_ID=b.SERIES_ID OR tmma.MODEL_ID = b.SERIES_ID) \n");
		sql.append("		AND tmma.GROUP_NO = b.GROUP_NO \n");
		sql.append("    LEFT JOIN ( \n");
		sql.append("    SELECT DISTINCT trp.PART_CODE,trp.GROUP_NO,trp.RECALL_ID, TPPB.PART_NAME,trp.PART_NUM,tc.CODE_DESC,trp.CHANGE_RATIO,tppb.PART_PRICE \n");
		sql.append("    FROM TT_RECALL_PART_DCS trp, TT_PT_PART_BASE_DCS TPPB,TC_CODE_DCS TC \n");
		sql.append("    WHERE  trp.PART_CODE = tppb.PART_CODE \n");
		sql.append("    AND tc.CODE_ID = trp.CHECK_STATUS \n");
		sql.append("    ) a ON  b.RECALL_ID = a.RECALL_ID  \n");
		sql.append("    AND b.GROUP_NO = a.GROUP_NO   \n");
		sql.append("		GROUP BY tmma.RECALL_NO, tmma.RECALL_NAME, tmma.MODEL_ID, \n");
		sql.append("		 tmma.GROUP_NO,tmma.SERIES_ID, tmma.BRAND_NAME, tmma.SERIES_NAME, tmma.MODEL_NAME, tmma.GROUP_NAME, tmma.MODEL_YEAR, \n");
		sql.append("		tmma.COLOR_NAME, tmma.TRIM_NAME,tmma.RECALL_ID, \n");
		sql.append("		a.PART_CODE,a.GROUP_NO,a.RECALL_ID, a.PART_NAME, a.PART_NUM, a.CODE_DESC, a.CHANGE_RATIO, a.PART_PRICE, \n");
		sql.append("		 b.RECALL_ID,b.SERIES_ID,b.GROUP_NO,b.MANHOUR_CODE, b.MANHOUR_NAME, b.MANHOUR_NUM, b.REBATE \n");
		sql.append("		ORDER BY tmma.GROUP_NO 	 \n");

		List<Map> orderList = OemDAOUtil.downloadPageQuery(sql.toString(), params);
		return orderList;
	}
	/**
	 *  经销商下载列表
	 * @param queryParam
	 * @return
	 */
	public List<Map> getrecallActivityDealerDownLoadList(Long recallId) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("\n");
		sql.append("SELECT  TRS.RECALL_NO, TRS.RECALL_NAME, \n");
		sql.append("	TM.DEALER_CODE,        #经销商CODE \n");
		sql.append("	TM.DEALER_SHORTNAME    #经销商简称 \n");
		sql.append("FROM TT_RECALL_DEALER_DCS TRD,TM_DEALER TM,TT_RECALL_SERVICE_DCS TRS \n");
		sql.append("WHERE 1=1 AND TM.DEALER_ID = TRD.DEALER_CODE AND TRS.RECALL_ID = TRD.RECALL_ID \n");
		sql.append("	AND TRD.RECALL_ID = '"+recallId+"' \n");
		sql.append("ORDER BY TM.DEALER_CODE DESC	 \n");

		List<Map> orderList = OemDAOUtil.downloadPageQuery(sql.toString(), params);
		return orderList;
	} 
	/**
	 * 根据RECALL_CODE查找记录
	 * @param recallCode
	 * @return
	 */
	public Map findRecallServiceByCode(String recallNo) {
		Map map = new HashMap();
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql=new StringBuffer();
		sql.append("	SELECT * FROM Tt_Recall_Service_DCS WHERE 1=1  AND RECALL_NO = '"+recallNo+"' \n");
		List<Map> list = OemDAOUtil.findAll(sql.toString(), params);
		if(list !=null && list.size()>0){
			map = list.get(0);
		}
		
		return map;
	}
	/**
	 * 查询临时表数据
	 * @return
	 */
	public List<Map> findTmpRecallVehicleList() {		
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql=new StringBuffer();
		sql.append("  SELECT * FROM TMP_RECALL_VEHICLE_DCS	ORDER BY TMP_ID \n");
		List<Map> list = OemDAOUtil.findAll(sql.toString(), params);
		
		return list;
	}
	
	/**
	 * 检查VIN是否重复
	 * @return
	 */
	public List<Map> checkDataVIN() {		
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql=new StringBuffer();
		sql.append(" SELECT  p1.ROW_NO ROW_NO, CONCAT('与第：',p2.ROW_NO, '行数据重复') ERROR  	 \n");
		sql.append(" 	FROM TMP_RECALL_VEHICLE_DCS p1, TMP_RECALL_VEHICLE_DCS p2	 \n");
		sql.append(" 	WHERE p1.VIN = p2.VIN	AND p1.TMP_ID <> p2.TMP_ID	 \n");
		sql.append(" 	ORDER BY p1.TMP_ID \n");
		List<Map> list = OemDAOUtil.findAll(sql.toString(), params);			
		return list;
	}
	/**
	 * 验证合法数据 （1） VIN是否在车辆表中存在  （2）经销商是否存在  
	 * @return
	 */
	public List<Map> checkDataTmpList(Integer inportType) {		
		List<Object> params = new ArrayList<Object>();
		
		StringBuffer sql = new StringBuffer("\n");
		sql.append("SELECT t1.ROW_NO, t1.RECALL_NO,t1.VIN, IFNULL(tm.VIN,'') tmInfo, CONCAT('VIN:',t1.vin,'不存在') ERROR, \n");
		sql.append("		t1.DUTY_DEALER,t1.INPORT_TYPE,t1.INPORT_CLASS FROM TMP_RECALL_VEHICLE_DCS t1 \n");
		sql.append("		LEFT JOIN TM_VEHICLE_DEC tm ON tm.VIN = t1.VIN \n");
		sql.append("		WHERE t1.INPORT_TYPE='"+inportType+"' AND t1.INPORT_CLASS = '"+OemDictCodeConstants.INPORT_CLASS_01+"' \n");
		sql.append("		 AND (tm.VIN IS NULL OR tm.VIN='') \n");
		sql.append("		 UNION ALL \n");
		sql.append("		 SELECT t1.ROW_NO, t1.RECALL_NO,t1.VIN,IFNULL(tm.DEALER_CODE,'') tmInfo, CONCAT('经销商:',t1.DUTY_DEALER,'不存在') ERROR, \n");
		sql.append("		 t1.DUTY_DEALER, \n");
		sql.append("		 t1.INPORT_TYPE,t1.INPORT_CLASS FROM TMP_RECALL_VEHICLE_DCS t1 \n");
		sql.append("		LEFT JOIN TM_DEALER tm ON tm.DEALER_CODE = t1.DUTY_DEALER \n");
		sql.append("		WHERE t1.INPORT_TYPE='"+inportType+"' AND t1.INPORT_CLASS = '"+OemDictCodeConstants.INPORT_CLASS_01+"' \n");
		sql.append("		 AND (tm.DEALER_CODE IS NULL OR tm.DEALER_CODE ='') 		 \n");
		List<Map> list = OemDAOUtil.findAll(sql.toString(), params);			
		return list;
	}
	
	public Long findIdByDealerCode(String dealerCode) {		
		List<Object> params = new ArrayList<Object>();
		
		StringBuffer sql = new StringBuffer("\n");
		sql.append("	SELECT * FROM TM_DEALER WHERE DEALER_CODE = '"+dealerCode+"'	\n");
	
		Map map = OemDAOUtil.findFirst(sql.toString(), params);			
		return Long.parseLong(map.get("DEALER_ID").toString()) ;
	}
	
}






