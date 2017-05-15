package com.infoeai.eai.dao.bsuv.lms;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.axis.encoding.Base64;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.infoeai.eai.wsServer.bsuv.lms.basicManagerService.DealerVo;
import com.infoeai.eai.wsServer.bsuv.lms.basicManagerService.MaterialVO;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.CommonUtils;


/**
 * BSUV官网主数据查询
 * @author weixia
 *
 */
@Repository
@SuppressWarnings("rawtypes")
public class BsuvMaterialDao extends OemBaseDAO{

	public static Logger logger = Logger.getLogger(BsuvMaterialDao.class);
	
	
	/**
	 * BSUV官网物料主数据发送查询
	 * @return
	 * @throws Exception 
	 */
	public List<MaterialVO> getMaterialList(Date from,Date to) throws Exception {
		List<MaterialVO> ps = new ArrayList<>();
		StringBuffer sql = new StringBuffer();
		
		sql.append("  SELECT MG1.GROUP_CODE AS BRAND_CODE,MG1.GROUP_NAME AS BRAND_NAME,MG1.STATUS AS BRAND_STATUS,MG1.GROUP_LEVEL AS BRAND_LEVEL, \n");
		sql.append("  MG2.GROUP_CODE AS SERIES_CODE,MG2.GROUP_NAME AS SERIES_NAME,MG2.STATUS AS SERIES_STATUS,MG2.GROUP_LEVEL AS SERIES_LEVEL,     \n");
		sql.append("  MG3.GROUP_CODE AS MODEL_CODE,MG3.GROUP_NAME AS MODEL_NAME,MG3.STATUS AS MODEL_STATUS,MG3.GROUP_LEVEL AS MODEL_LEVEL,  \n");      
		sql.append("  MG4.GROUP_CODE AS GROUP_CODE,MG4.GROUP_NAME AS GROUP_NAME,MG4.STATUS AS GROUP_STATUS,MG4.GROUP_LEVEL AS GROUP_LEVEL, \n");
		sql.append("  MG4.MODEL_YEAR,MG4.FACTORY_OPTIONS,MG4.STANDARD_OPTION,MG4.LOCAL_OPTION, \n");
		sql.append("  M.TRIM_CODE,M.TRIM_NAME,M.COLOR_CODE,M.COLOR_NAME,M.STATUS,IFNULL(M.IS_EC,"+OemDictCodeConstants.IF_TYPE_NO+") IS_EC \n");
		sql.append("    FROM TM_VHCL_MATERIAL M,TM_VHCL_MATERIAL_GROUP_R MGR,TM_VHCL_MATERIAL_GROUP MG4,      \n");   
		sql.append("    TM_VHCL_MATERIAL_GROUP MG3,TM_VHCL_MATERIAL_GROUP MG2,TM_VHCL_MATERIAL_GROUP MG1       \n");                      
		sql.append("   WHERE M.MATERIAL_ID = MGR.MATERIAL_ID AND MGR.GROUP_ID = MG4.GROUP_ID           \n");
		sql.append("    AND MG4.PARENT_GROUP_ID = MG3.GROUP_ID AND MG3.PARENT_GROUP_ID = MG2.GROUP_ID      \n");     
		sql.append("    AND MG2.PARENT_GROUP_ID = MG1.GROUP_ID AND M.COMPANY_ID = "+OemDictCodeConstants.OEM_ACTIVITIES+"  \n");
		sql.append(" 	AND ((M.CREATE_DATE BETWEEN '"+CommonUtils.printDate(from)+" 00:00:00' AND '"+CommonUtils.printDate(to)+" 23:59:59' AND M.UPDATE_DATE IS NULL) \n");
		sql.append("   OR (M.UPDATE_DATE BETWEEN '"+CommonUtils.printDate(from)+" 00:00:00' AND '"+CommonUtils.printDate(to)+" 23:59:59')  \n");
		sql.append("   OR (MG1.CREATE_AT BETWEEN '"+CommonUtils.printDate(from)+" 00:00:00' AND '"+CommonUtils.printDate(to)+" 23:59:59' AND MG1.UPDATE_AT IS NULL)  \n");
		sql.append("   OR (MG1.UPDATE_AT BETWEEN '"+CommonUtils.printDate(from)+" 00:00:00' AND '"+CommonUtils.printDate(to)+" 23:59:59')  \n");
		sql.append("   OR (MG2.CREATE_AT BETWEEN '"+CommonUtils.printDate(from)+" 00:00:00' AND '"+CommonUtils.printDate(to)+" 23:59:59' AND MG2.UPDATE_AT IS NULL)  \n");
		sql.append("   OR (MG2.UPDATE_AT BETWEEN '"+CommonUtils.printDate(from)+" 00:00:00' AND '"+CommonUtils.printDate(to)+" 23:59:59')  \n");
		sql.append("   OR (MG3.CREATE_AT BETWEEN '"+CommonUtils.printDate(from)+" 00:00:00' AND '"+CommonUtils.printDate(to)+" 23:59:59' AND MG3.UPDATE_AT IS NULL )  \n");
		sql.append("   OR (MG3.UPDATE_AT BETWEEN '"+CommonUtils.printDate(from)+" 00:00:00' AND '"+CommonUtils.printDate(to)+" 23:59:59')  \n");
		sql.append("   OR (MG4.CREATE_AT BETWEEN '"+CommonUtils.printDate(from)+" 00:00:00' AND '"+CommonUtils.printDate(to)+" 23:59:59' AND MG4.UPDATE_AT IS NULL)  \n");
		sql.append("   OR (MG4.UPDATE_AT BETWEEN '"+CommonUtils.printDate(from)+" 00:00:00' AND '"+CommonUtils.printDate(to)+" 23:59:59'))  \n");
		
		
		List<Map> list = OemDAOUtil.findAll(sql.toString(), null);
		if(null!= list && list.size()>0){
			for (Map map : list) {
				MaterialVO vo = new MaterialVO();
				vo.setBRAND_CODE(CommonUtils.checkNull(map.get("BRAND_CODE")));//品牌代码
				vo.setBRAND_NAME(Base64.encode(CommonUtils.checkNull(map.get("BRAND_NAME")).getBytes("UTF-8")));//品牌名称
				vo.setBRAND_LEVEL(Integer.parseInt(CommonUtils.checkNull(map.get("BRAND_LEVEL"),"0")));//品牌级别
				vo.setBRAND_STATUS(Integer.parseInt(CommonUtils.checkNull(map.get("BRAND_STATUS"),"0")));//品牌状态
				vo.setSERIES_CODE(CommonUtils.checkNull(map.get("SERIES_CODE")));//车系代码
				vo.setSERIES_NAME(Base64.encode(CommonUtils.checkNull(map.get("SERIES_NAME")).getBytes("UTF-8")));//车系名称
				vo.setSERIES_LEVEL(Integer.parseInt(CommonUtils.checkNull(map.get("SERIES_LEVEL"),"0")));//车系级别
				vo.setSERIES_STATUS(Integer.parseInt(CommonUtils.checkNull(map.get("SERIES_STATUS"),"0")));//车系状态
				vo.setMODEL_CODE(CommonUtils.checkNull(map.get("MODEL_CODE")));//车型代码
				vo.setMODEL_NAME(Base64.encode(CommonUtils.checkNull(map.get("MODEL_NAME")).getBytes("UTF-8")));//车型名称
				vo.setMODEL_LEVEL(Integer.parseInt(CommonUtils.checkNull(map.get("MODEL_LEVEL"),"0")));//车型级别
				vo.setMODEL_STATUS(Integer.parseInt(CommonUtils.checkNull(map.get("MODEL_STATUS"),"0")));//车型状态
				vo.setGROUP_CODE(CommonUtils.checkNull(map.get("GROUP_CODE")));//车款代码
				vo.setGROUP_NAME(Base64.encode(CommonUtils.checkNull(map.get("GROUP_NAME")).getBytes("UTF-8")));//车款名称
				vo.setGROUP_LEVEL(Integer.parseInt(CommonUtils.checkNull(map.get("GROUP_LEVEL"),"0")));//车款级别
				vo.setGROUP_STATUS(Integer.parseInt(CommonUtils.checkNull(map.get("GROUP_STATUS"),"0")));//车款状态
				vo.setMODEL_YEAR(CommonUtils.checkNull(map.get("MODEL_YEAR")));//年款
				vo.setFACTORY_OPTIONS(Base64.encode(CommonUtils.checkNull(map.get("FACTORY_OPTIONS")).getBytes("UTF-8")));//其他配置
				vo.setSTANDARD_OPTION(Base64.encode(CommonUtils.checkNull(map.get("STANDARD_OPTION")).getBytes("UTF-8")));//标准配置
				vo.setLOCAL_OPTION(Base64.encode(CommonUtils.checkNull(map.get("LOCAL_OPTION")).getBytes("UTF-8")));//本地配置
				vo.setTRIM_CODE(CommonUtils.checkNull(map.get("TRIM_CODE")));//内饰代码
				vo.setTRIM_NAME(Base64.encode(CommonUtils.checkNull(map.get("TRIM_NAME")).getBytes("UTF-8")));//内饰名称
				vo.setCOLOR_CODE(CommonUtils.checkNull(map.get("COLOR_CODE")));//颜色代码
				vo.setCOLOR_NAME(Base64.encode(CommonUtils.checkNull(map.get("COLOR_NAME")).getBytes("UTF-8")));//颜色名称
				vo.setSTATUS(Integer.parseInt(CommonUtils.checkNull(map.get("STATUS"),"0")));//状态
				vo.setIS_EC(Integer.parseInt(CommonUtils.checkNull(map.get("IS_EC"),"0")));//是否官网
				ps.add(vo);
			}
		}
		return ps;
	}
	
	/**
	 * BSUV经销商基础信息全量发送查询
	 * @return
	 * @throws Exception 
	 */
	public List<DealerVo> getDealerList() throws Exception {
		List<DealerVo> ps = new ArrayList<>();
		StringBuffer sql = new StringBuffer();
		
		sql.append("    SELECT TD.DEALER_CODE,TD.DEALER_SHORTNAME,TD.DEALER_NAME,TD.OFFICE_PHONE,  \n");
		sql.append("      PROVINCE.REGION_NAME PROVINCE,CITY.REGION_NAME CITY,TD.ADDRESS,TD.STATUS,  \n");
		sql.append("      IFNULL(TD.IS_EC,"+OemDictCodeConstants.IF_TYPE_NO+") IS_EC  \n");
		sql.append("      FROM TM_DEALER TD   \n");
		sql.append("      LEFT JOIN TM_REGION PROVINCE ON TD.PROVINCE_ID = PROVINCE.REGION_CODE  \n");
		sql.append("      LEFT JOIN TM_REGION CITY ON TD.CITY_ID = CITY.REGION_CODE  \n");
		sql.append("      WHERE TD.DEALER_TYPE = "+OemDictCodeConstants.DEALER_TYPE_DVS+"  \n");
//		sql.append("      FETCH FIRST 150 ROW ONLY  \n");
//		sql.append("      AND DEALER_CODE IN( '14055')  \n");
		
		List<Map> list = OemDAOUtil.findAll(sql.toString(), null);
		if(null!= list && list.size()>0){
			for (Map map : list) {
				DealerVo vo = new DealerVo();
				vo.setDEALER_CODE(CommonUtils.checkNull(map.get("DEALER_CODE")));//经销商代码
	        	 vo.setDEALER_SHORTNAME(Base64.encode(CommonUtils.checkNull(map.get("DEALER_SHORTNAME")).getBytes("UTF-8")));//经销商简称
	        	 vo.setDEALER_NAME(Base64.encode(CommonUtils.checkNull(map.get("DEALER_NAME")).getBytes("UTF-8")));//经销商全称
	        	 vo.setOFFICE_PHONE(CommonUtils.checkNull(map.get("OFFICE_PHONE")));//经销商热线
	        	 vo.setPROVINCE(Base64.encode(CommonUtils.checkNull(map.get("PROVINCE")).getBytes("UTF-8")));//省
	        	 vo.setCITY(Base64.encode(CommonUtils.checkNull(map.get("CITY")).getBytes("UTF-8")));//市
	        	 vo.setADDRESS(Base64.encode(CommonUtils.checkNull(map.get("ADDRESS")).getBytes("UTF-8")));//地址
	        	 vo.setSTATUS(Integer.parseInt(CommonUtils.checkNull(map.get("STATUS"),"0")));//经销商状态
	        	 vo.setIS_EC(Integer.parseInt(CommonUtils.checkNull(map.get("IS_EC"),"0")));//是否官网
	        	 ps.add(vo);
			}
		}
		return ps;
	}
	
}
