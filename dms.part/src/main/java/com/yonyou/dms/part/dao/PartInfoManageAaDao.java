package com.yonyou.dms.part.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.LazyList;
import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.part.domains.PO.partBaseInfoManage.TmpPtPartWbpDcsPO;



/**
 * 配件信息管理Dao
 * @author ZhaoZ
 * @date 2017年3月23日
 */
@SuppressWarnings("all")
@Repository
public class PartInfoManageAaDao extends OemBaseDAO{

	/**
	 * 配件信息维护查询
	 * @param queryParams
	 * @return
	 */
	public PageInfoDto findList(Map<String, String> queryParams) {
		List<Object> params = new ArrayList<Object>();
		String sql = getfindListSql(queryParams, params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;
	}
	
	/**
	 * 配件信息维护查询SQL
	 * @param queryParams
	 * @return
	 */
	private String getfindListSql(Map<String, String> queryParams, List<Object> params) {
		StringBuffer pasql = new StringBuffer();
        pasql.append("	SELECT TPPB.ID,TPPB.PART_CODE,TPPB.PART_NAME,TPPB.PART_MDOEL,TPPB.PART_PROPERTY,TPPB.DNP_PRICE,TPPB.PART_PRICE,  \n  ");
        pasql.append("	TPPB.IS_SALES,TPPB.PART_STATUS,TPPB.IS_MJV,TPPB.IS_MOP,TU.NAME UPDATE_BY,date_format(tppb.UPDATE_DATE,'%Y-%m-%d') UPDATE_DATE,TPPB.WBP \n  ");
//        pasql.append("	(CASE WHEN TPPB.IS_MJV=10041001 THEN cast(TPPB.PART_PRICE*1.17  as  decimal(13, 2))  WHEN TPPB.IS_MJV=10041002 THEN TPPB.PART_PRICE END) PART_PRICE, \n  ");
//        pasql.append("	(CASE WHEN TPPB.IS_MJV=10041001 THEN TPPB.PART_PRICE  WHEN TPPB.IS_MJV=10041002 THEN TPPB.DNP_PRICE END ) DNP_PRICE \n  ");
        pasql.append("		 	FROM TT_PT_PART_BASE_dcs TPPB LEFT OUTER JOIN TC_USER TU ON TU.USER_ID=TPPB.UPDATE_BY  WHERE 1=1	\n  ");

        //配件编号
        if(!StringUtils.isNullOrEmpty(queryParams.get("partCode"))){ 
       		pasql.append(" 					AND TPPB.PART_CODE LIKE ? \n");
       		params.add("%"+queryParams.get("partCode")+"%");
        }
        //配件名称
        if(!StringUtils.isNullOrEmpty(queryParams.get("partName"))){ 
        	pasql.append(" 					AND TPPB.PART_NAME LIKE ? \n");
        	params.add("%"+queryParams.get("partName")+"%");
        }//配件类别
        if(!StringUtils.isNullOrEmpty(queryParams.get("partType"))){ 
        	pasql.append(" 					AND TPPB.PART_MDOEL = ? \n");
        	params.add(queryParams.get("partType"));
        }
        //配件属性
        if(!StringUtils.isNullOrEmpty(queryParams.get("partProperty"))){ 
       		pasql.append(" 					AND TPPB.PART_PROPERTY = ? \n");
       		params.add(queryParams.get("partProperty"));
        }
       //是否有效" + Integer.valueOf(partStatus) + "
        if(!StringUtils.isNullOrEmpty(queryParams.get("partStatus"))){ 
       		pasql.append(" 					AND TPPB.PART_Status = ? \n");
       		params.add(Integer.valueOf(queryParams.get("partStatus")));
        }
        //是否S
        if(!StringUtils.isNullOrEmpty(queryParams.get("isMjv"))){ 
       		pasql.append(" 					AND TPPB.IS_MJV = ? \n");
       		params.add(Integer.valueOf(queryParams.get("isMjv")));
        }
        
        //更新时间
        if(!StringUtils.isNullOrEmpty(queryParams.get("startDate"))){ 
     
        	pasql.append(" AND  date_format(TPPB.UPDATE_DATE,'%Y-%m-%d') >='" + queryParams.get("startDate") +"' \n");
        }
        if(!StringUtils.isNullOrEmpty(queryParams.get("endDate"))){ 
            
        	pasql.append(" AND  date_format(TPPB.UPDATE_DATE,'%Y-%m-%d') <='" + queryParams.get("endDate") +"' \n");
        }
		return pasql.toString();
       
	}
	
	/**
	 * 替换件信息
	 */
	
	public List<Map> selectParts(String oldPartCode) {
		
		StringBuffer pasql = new StringBuffer();
		 pasql.append(" SELECT * FROM ( \n  ");
		 pasql.append(" SELECT  TPPB.ID,TPPB.PART_CODE,TPPB.PART_NAME FROM TT_PT_PART_BASE_dcs TPPB \n  ");
		 pasql.append("    WHERE 1=1  \n   ");
		 //配件编号
		 if(!StringUtils.isNullOrEmpty(oldPartCode)){  
	       		pasql.append(" AND PART_CODE IN  (" + oldPartCode+ " ) \n");
	        }
		 pasql.append(" ) DCS \n  ");
		 
		return OemDAOUtil.findAll(pasql.toString(), null);
	}

	/**
	 * 车型信息
	 */
	public List<Map> vhclMaterialinfos(String code) {
		StringBuffer pasql = new StringBuffer();
		 pasql.append(" SELECT * FROM ( \n  ");
		 pasql.append(" SELECT  TVMG.group_id,TVMG.GROUP_CODE,TVMG.GROUP_NAME  \n  ");
		 pasql.append("  from  TM_VHCL_MATERIAL_GROUP TVMG \n  ");
		 pasql.append("  where TVMG.GROUP_LEVEL = '3' and TVMG.STATUS = '10011001' and TVMG.IS_DEL = '0' AND 1=1  \n   ");	
		
		 //配件编号
		 if(!StringUtils.isNullOrEmpty(code)){  
	       		pasql.append(" AND TVMG.GROUP_CODE in (" + code+ ") \n");
	        }
		 pasql.append("     GROUP BY TVMG.group_id,TVMG.GROUP_CODE,TVMG.GROUP_NAME  \n  ");
		 pasql.append(" ) DCS \n  ");
		 return OemDAOUtil.findAll(pasql.toString(), null);
	}

	
	/**
	 * 替换件信息
	 */
	public PageInfoDto selectParts(Map<String, String> queryParams) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer pasql = new StringBuffer();
		   pasql.append("	SELECT   \n  ");
		   pasql.append("	TPPB.ID, \n  ");
		   pasql.append("	TPPB.PART_CODE,   \n  ");
		   pasql.append("	TPPB.PART_NAME   \n  ");
		   pasql.append("	FROM TT_PT_PART_BASE_dcs TPPB WHERE 1=1	\n  ");
		   pasql.append("	AND  TPPB.PART_STATUS='10011001'	\n  ");
		   pasql.append("	AND TPPB.IS_DEL = '0'	\n  ");
		   //配件替换配件
//	        if(!StringUtils.isNullOrEmpty(code){ 
//	       		pasql.append("    AND TPPB.PART_CODE NOT IN (" + code + ") \n");
//	        }
		   //配件code
	        if(!StringUtils.isNullOrEmpty(queryParams.get("partCode"))){ 
	       		pasql.append("    AND TPPB.PART_CODE LIKE ? \n");
	       		params.add("%"+queryParams.get("partCode")+"%");
	        }
	        //配件名称
	        if(!StringUtils.isNullOrEmpty(queryParams.get("partName"))){ 
	        	pasql.append("AND TPPB.PART_NAME LIKE ? \n");
	        	params.add("%"+queryParams.get("partCode")+"%");
	        }
	        PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(pasql.toString(), params);
			return pageInfoDto;  
	}

	public PageInfoDto addTmVhclMaterial(Map<String, String> queryParams) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer pasql = new StringBuffer();
		   pasql.append("	SELECT   \n  ");
		   pasql.append("	TVMG.GROUP_ID,\n  ");
		   pasql.append("	TVMG.GROUP_CODE,   \n  ");
		   pasql.append("	TVMG.GROUP_NAME   \n  ");
		   pasql.append("	FROM TM_VHCL_MATERIAL_GROUP TVMG 	\n  ");
		   pasql.append("  where TVMG.GROUP_LEVEL = '3' and TVMG.STATUS = '10011001' and TVMG.IS_DEL = '0' AND 1=1  \n   ");	
		   //配件替换配件
	        if(!StringUtils.isNullOrEmpty(queryParams.get("modelCode"))){ 
	       		pasql.append("    AND TVMG.GROUP_CODE NOT IN (?) \n");
	       		params.add(queryParams.get("modelCode"));
	        }
		   //配件code
	        if(!StringUtils.isNullOrEmpty(queryParams.get("groupCode"))){ 
	       		pasql.append("    AND TVMG.GROUP_CODE LIKE ? \n");
	       		params.add("%"+queryParams.get("groupCode")+"%");
	        }
	        //配件名称
	        if(!StringUtils.isNullOrEmpty(queryParams.get("groupName"))){ 
	        	pasql.append("AND TVMG.GROUP_NAME LIKE ? \n");
	        	params.add("%"+queryParams.get("groupName")+"%");
	        }
	        pasql.append(" GROUP BY TVMG.group_id,TVMG.GROUP_CODE,TVMG.GROUP_NAME	 \n");
	        PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(pasql.toString(), params);
			return pageInfoDto; 
	}

	

	

	public Map checkTableDump(String partCode,String createBy) {
		StringBuffer pasql = new StringBuffer();
		 pasql.append(" select count(part_code) partCodeCount from Tmp_Pt_Part_Wbp_dcs where 1=1   \n  ");
		 if(!StringUtils.isNullOrEmpty(partCode)){ 
			 pasql.append("  and part_code ='"+partCode+"'  \n   ");
		 }
		 pasql.append("  and Create_By = '"+createBy+"'  \n   "); 
		 pasql.append("   group by part_code  \n   ");
		 return OemDAOUtil.findFirst(pasql.toString(), null);
	}

	/**
	 * 配件主数据监控查询异常
	 * @param queryParams
	 * @return
	 */
	public PageInfoDto exceList(Map<String, String> queryParams) {
		List<Object> params = new ArrayList<Object>();
		String sql = getExceListSql(queryParams, params);
		return OemDAOUtil.pageQuery(sql, params);
	}

	/**
	 * 配件主数据监控查询异常SQL
	 * @param queryParams
	 * @param params
	 * @return
	 */
	private String getExceListSql(Map<String, String> queryParams, List<Object> params) {
		StringBuffer pasql = new StringBuffer();
        pasql.append("	SELECT MATNR, \n  ");   //配件编号
        pasql.append("	MAKTX, \n  ");          //配件描述
        pasql.append("	ID, \n  ");             //调度编号
        pasql.append("	date_format(UPDATE_DATE,'%Y-%m-%d') UPDATE_DATE, \n  ");    //调度时间
        pasql.append("	MATKL, \n  ");          //物料组
        pasql.append("	MTPOS, \n  ");          //项目类别组
        pasql.append("	MSEHL, \n  ");          //采购单位
        pasql.append("	VORMG, \n  ");          //最小订货量
        pasql.append("	KBETR, \n  ");          //定价
        pasql.append("	KPEIN, \n  ");          //定价数量
        pasql.append("	CASE ZFLAG WHEN 'I' THEN '新增' WHEN 'C' THEN '修改' WHEN 'D' THEN '删除' ELSE '' END ZFLAG,  \n  ");         //数据标记
        pasql.append("	CASE STATUS  WHEN '10041001' THEN '成功' WHEN '10041002' THEN '失败' ELSE '' END STATUS,  \n  ");        //状态
        pasql.append("	ERR_INFO \n  ");        //错误消息
        pasql.append("		 	FROM TI_PT_PART_BASE_dcs WHERE 1=1	\n  ");

        //配件编号
        if(!StringUtils.isNullOrEmpty(queryParams.get("partCode"))){ 
        	pasql.append(" 					AND MATNR LIKE ? \n");
       		params.add("%"+queryParams.get("partCode")+"%");
        }
        //配件名称
        if(!StringUtils.isNullOrEmpty(queryParams.get("partName"))){ 
        	pasql.append(" 					AND MAKTX LIKE ? \n");
       		params.add("%"+queryParams.get("partName")+"%");
        }
        //数据标记
        if(!StringUtils.isNullOrEmpty(queryParams.get("dataFlag"))){ 
        	String flag = queryParams.get("dataFlag");
        	if("93351001".equals(flag)){
        		pasql.append(" 					AND ZFLAG = 'I' \n");
        	}else if("93351002".equals(flag)){
        		pasql.append(" 					AND ZFLAG = 'C' \n");
        	}else{
        		pasql.append(" 					AND ZFLAG = 'D' \n");
        	}
        	
        }
       //状态
        if(!StringUtils.isNullOrEmpty(queryParams.get("partStatus"))){ 
        	pasql.append(" 					AND STATUS = ? \n");
       		params.add(Integer.parseInt(queryParams.get("partStatus")));
        }
        //更新时间
        if(!StringUtils.isNullOrEmpty(queryParams.get("startDate"))){ 
        	pasql.append(" AND  date_format(UPDATE_DATE,'%Y-%m-%d') >='" + queryParams.get("startDate") +"' \n");
        }
        if(!StringUtils.isNullOrEmpty(queryParams.get("endDate"))){ 
        	pasql.append(" AND  date_format(UPDATE_DATE,'%Y-%m-%d') <='" + queryParams.get("endDate") +"' \n");
        }
		return pasql.toString();
       
	}

	/**
	 * 状态
	 * @return
	 */
	public List<Map> status() {
		String sql = "SELECT CODE_ID CO,CASE CODE_ID  WHEN '10041001' THEN '成功' WHEN '10041002' THEN '失败' END CODE_ID FROM TC_CODE_DCS WHERE TYPE = 1004";
		return OemDAOUtil.findAll(sql, null);
	}
	/**
	 * 数据标记
	 * @return
	 */
	public List<Map> dataFlag() {
		StringBuffer pasql = new StringBuffer();
        pasql.append("	SELECT distinct(ZFLAG) ZF,CASE ZFLAG WHEN 'I' THEN '新增' WHEN 'C' THEN '修改' WHEN 'D' THEN '删除' ELSE '' END ZFLAG \n  "); 
        pasql.append(" 			FROM TI_PT_PART_BASE_dcs \n");
        return OemDAOUtil.findAll(pasql.toString(), null);
	}

	/**
	 * 配件查询
	 * @return
	 */
	public PageInfoDto getPartBaseDlr(Map<String, String> queryParams) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer pasql = new StringBuffer();
        pasql.append("	SELECT TPPB.ID,TPPB.PART_CODE,TPPB.PART_NAME,TPPB.PART_MDOEL,TPPB.REPORT_TYPE,TPPB.DNP_PRICE,TPPB.PART_PRICE,  \n  ");
        pasql.append("	TPPB.PART_STATUS,TPPB.IS_MJV,TPPB.IS_MOP,TU.NAME UPDATE_BY,date_format(tppb.UPDATE_DATE,'%Y-%m-%d') UPDATE_DATE,tppb.UPDATE_DATE ORDER_DATE  \n  ");
        pasql.append("		 	FROM TT_PT_PART_BASE_dcs TPPB LEFT OUTER JOIN Tc_user TU ON TU.USER_ID=TPPB.UPDATE_BY  WHERE 1=1	\n  ");

        //配件编号
        if(!StringUtils.isNullOrEmpty(queryParams.get("partCode"))){ 
       		pasql.append(" 					AND TPPB.PART_CODE LIKE ? \n");
       		params.add("%"+queryParams.get("partCode")+"%");
        }
      //配件名称
        if(!StringUtils.isNullOrEmpty(queryParams.get("partName"))){ 
        	pasql.append(" 					AND TPPB.PART_NAME LIKE ? \n");
        	params.add("%"+queryParams.get("partName")+"%");
        }
        //配件类别
        if(!StringUtils.isNullOrEmpty(queryParams.get("partType"))){ 
        	pasql.append(" 					AND TPPB.PART_MDOEL = ? \n");
        	params.add(queryParams.get("partType"));
        }
        //配件属性
        if(!StringUtils.isNullOrEmpty(queryParams.get("reportType"))){ 
        	pasql.append(" 					AND TPPB.REPORT_TYPE = ? \n");
        	params.add(queryParams.get("reportType"));
        }
        
        //是否有效" + Integer.valueOf(partStatus) + "
        if(!StringUtils.isNullOrEmpty(queryParams.get("partStatus"))){ 
       		pasql.append(" 					AND TPPB.PART_Status = ? \n");
       		params.add(Integer.valueOf(queryParams.get("partStatus")));
        }
        //是否S
        if(!StringUtils.isNullOrEmpty(queryParams.get("isMjv"))){ 
       		pasql.append(" 					AND TPPB.IS_MJV = ? \n");
       		params.add(Integer.valueOf(queryParams.get("isMjv")));
        }
        
        //更新时间
        if(!StringUtils.isNullOrEmpty(queryParams.get("startDate"))){ 
     
        	pasql.append(" AND  date_format(TPPB.UPDATE_DATE,'%Y-%m-%d') >='" + queryParams.get("startDate") +"' \n");
        }
        if(!StringUtils.isNullOrEmpty(queryParams.get("endDate"))){ 
            
        	pasql.append(" AND  date_format(TPPB.UPDATE_DATE,'%Y-%m-%d') <='" + queryParams.get("endDate") +"' \n");
        }
        return OemDAOUtil.pageQuery(pasql.toString(), params);
	}

	

	/**
	 * 校验临时表数据TMP_RECALL_VEHICLE_DCS
	 * @param queryParam
	 * @return
	 */

	public List<Map> checkData2(LazyList<TmpPtPartWbpDcsPO> poList) {
		LoginInfoDto logonUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		List<Map> errList = new ArrayList<Map>();
		for(TmpPtPartWbpDcsPO po:poList){
			//判断经销商CODE是否为空
			if(StringUtils.isNullOrEmpty(po.getString("PART_CODE"))){
				Map<String, String> errMap = new HashMap<String, String>();
				errMap.put("ROW_NUM", po.getString("LINE_NO"));
				errMap.put("ERROR", "配件代码不能为空!");
				errList.add(errMap);
			}else{
				StringBuffer pasql = new StringBuffer();
		        pasql.append("	SELECT * FROM tt_pt_part_base_dcs WHERE PART_CODE = '"+po.getString("PART_CODE")+"' \n  "); 
		        pasql.append(" 			AND IS_MJV = "+OemDictCodeConstants.IF_TYPE_YES+" \n");
				
				List<Map> partList = OemDAOUtil.findAll(pasql.toString(), null);
				if (null == partList||partList.size() == 0 || partList.size() < 0) {
					Map<String, String> map = new HashMap<String, String>();
					map.put("ROW_NUM", po.getString("LINE_NO"));
					map.put("ERROR", "配件代码不存在或者【是否S】为否。");
					errList.add(map);

				}else{
					Long countPartCode = getPartCodeCount(po.getString("PART_CODE"), logonUser);
					if(countPartCode > 1){
						Map<String, String> map = new HashMap<String, String>();
						map.put("ROW_NUM", po.getString("LINE_NO"));
						map.put("ERROR", "配件代码:"+po.getString("PART_CODE")+"在列表中重复");
						errList.add(map);
					}
				} 
			}
			
			if (!CommonUtils.isNumber(po.getString("WBP"))) {
				Map<String, String> errMap = new HashMap<String, String>();
				errMap.put("ROW_NUM", po.getString("LINE_NO"));
				errMap.put("ERROR", "WBP价格不是数字");
				errList.add(errMap);
			}
		}
		return errList;
	}

	
	
	@SuppressWarnings("unchecked")
	private Long getPartCodeCount(String partCode, LoginInfoDto logonUser) {
		Long partCodeCount= (long) -1;
		StringBuffer pasql = new StringBuffer();
		 pasql.append(" select count(part_code) partCodeCount from Tmp_Pt_Part_Wbp_dcs where 1=1   \n  ");
		 if(!StringUtils.isNullOrEmpty(partCode)){ 
			 pasql.append("  and part_code ='"+partCode+"'  \n   ");
		 }
		 pasql.append("  and Create_By = '"+logonUser.getUserId().toString()+"'  \n   "); 
		 pasql.append("   group by part_code   \n   "); 
		 Map<String,Long> map = OemDAOUtil.findFirst(pasql.toString(), null);
		 partCodeCount = map.get("partCodeCount");
		 return partCodeCount;
	}

	public List<Map> tmpPtPartWbpDcsList() {
		//获取当前用户
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		StringBuffer sql = new StringBuffer("\n");
		sql.append(" SELECT * FROM  tmp_pt_part_wbp_dcs   \n");
		sql.append(" WHERE  CREATE_BY =  "+loginInfo.getUserId()+"  \n");
		sql.append(" order by LINE_NO  \n");
		List<Map> resultList = OemDAOUtil.findAll(sql.toString(), null);
		return resultList;
	}

}
