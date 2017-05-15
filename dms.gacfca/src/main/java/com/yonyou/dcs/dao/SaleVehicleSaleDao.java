/**
 * @Title: SaleVehicleSaleDao.java 
 * @Description:经销商车辆实销信息上报数据操作类
 * @Date: 2012-6-27
 * @author yuyang
 * @version 1.0
 * @remark
 */

package com.yonyou.dcs.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.yonyou.dms.common.Util.CheckUtil;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.CommonUtils;

@SuppressWarnings({"unchecked","rawtypes"})
@Repository
public class SaleVehicleSaleDao extends OemBaseDAO{
	public static Logger logger = Logger.getLogger(SaleVehicleSaleDao.class);
	/**
	 * 车辆ID信息是否已存在实销表中查询
	 * @param 
	 * @param  vin
	 * @return 
	 * @throws Exception
	 */
	public Map<String, Object> getVehicleIdByVinForExist(String vin,String dealerId) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT  VEHICLE_ID  \n");
		sql.append(" From TM_VEHICLE_DEC   ");
		sql.append(" WHERE 1=1 ");
		sql.append(" AND DEALER_ID ='").append(dealerId).append("' \n");
		sql.append(" AND (LIFE_CYCLE ='").append(OemDictCodeConstants.LIF_CYCLE_04).append("' \n");
		sql.append(" OR LIFE_CYCLE ='").append(OemDictCodeConstants.LIF_CYCLE_03).append("') \n");
		if (vin!=null&&!"".equals(vin))
        {
			sql.append(" AND VIN = '"+vin+"' \n");
		}
		return OemDAOUtil.findFirst(sql.toString(), null);
	}
	/**
	 * 车辆ID信息是否已存在实销表中查询
	 * @param 
	 * @param  vin
	 * @return 
	 * @throws Exception
	 */
	public Map<String, Object> getDealerInfo(String dealerId) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT  DEALER_ID,OEM_COMPANY_ID,COMPANY_ID,DEALER_TYPE,DEALER_CODE,DEALER_NAME  \n");
		sql.append(" From TM_DEALER ");
		sql.append(" WHERE STATUS ='").append(OemDictCodeConstants.STATUS_ENABLE).append("' ");
		sql.append(" AND DEALER_ID = '"+dealerId+"' \n");
		return OemDAOUtil.findFirst(sql.toString(), null);
	}
	
	/**
	 * 车辆ID信息查询
	 * @param 
	 * @param  vin
	 * @return 
	 * @throws Exception
	 */
	public Map<String, Object> getVehicleIdByVin(String vin) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT VEHICLE_ID  \n");
		sql.append(" From TM_VEHICLE_DEC \n");
		sql.append(" WHERE 1=1 ");
		if (vin!=null&&!"".equals(vin))
        {
			sql.append(" AND VIN = '"+vin+"' \n");
		}
		return OemDAOUtil.findFirst(sql.toString(), null);
	}
	/**
	 * 老车主VIN查询
	 * @param vin
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getOldVehicleIdByVin(String vin) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT VEHICLE_ID  \n");
		sql.append(" From TM_VEHICLE \n");
		sql.append(" WHERE 1=1 ");
		sql.append(" AND VIN = '"+vin+"' \n");
		return OemDAOUtil.findFirst(sql.toString(), null);
	}
	/**
	 * 车辆实销客户信息查询
	 * @param 
	 * @param  vin
	 * @return 
	 * @throws Exception
	 */
	public Map<String, Object> getCustomInfo(String customName,String cardNum,Integer cardType,String dealerId) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT  CTM_ID  \n");
		sql.append(" From TT_VS_CUSTOMER  ");
		sql.append(" WHERE 1=1 ");
		if (customName!=null&&!"".equals(customName))
        {
			sql.append(" AND CTM_NAME = '"+customName+"' \n");
		}
		if (cardNum!=null&&!"".equals(cardNum))
        {
			sql.append(" AND CARD_NUM = '"+cardNum+"' \n");
		}
		if (cardType!=null)
        {
			sql.append(" AND CARD_TYPE = '").append(cardType).append("' \n");
		}
		/*if (dealerId!=null&&!"".equals(dealerId))
        {
			sql.append(" AND DEALER_ID = '").append(dealerId).append("' \n");
		}*/
		return OemDAOUtil.findFirst(sql.toString(), null);
	}
	public Map<String, Object> getReportIdIsExist(Long reportId) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT  YWZJ  \n");
		sql.append(" From FS_FILEUPLOAD  ");
		sql.append(" WHERE YWZJ = " + reportId);
		return OemDAOUtil.findFirst(sql.toString(), null);
	}
	
	
	/**
	 * 获得车辆业务类别
	 * @param vehicleId
	 * @param vin
	 * @throws Exception
	 */
	public String getVehicleBusinessType(String vehicleId, String vin) throws Exception {
		
		StringBuffer sql = new StringBuffer();
		sql.append(" \n");
		sql.append("SELECT V.VEHICLE_ID, -- 车辆ID \n");
		sql.append("       V.VIN, -- 车架号 \n");
		sql.append("       M.GROUP_TYPE -- 业务类型 \n");
		sql.append("  FROM ("+getVwMaterialSql()+") M \n");
		sql.append(" INNER JOIN TM_VEHICLE_DEC V ON V.MATERIAL_ID = M.MATERIAL_ID \n");
		sql.append(" WHERE M.BRAND_STATUS = '" + OemDictCodeConstants.STATUS_ENABLE + "' \n");
		sql.append("   AND M.SERIES_STATUS = '" + OemDictCodeConstants.STATUS_ENABLE + "' \n");
		sql.append("   AND M.MODEL_STATUS = '" + OemDictCodeConstants.STATUS_ENABLE + "' \n");
		sql.append("   AND M.GROUP_STATUS = '" + OemDictCodeConstants.STATUS_ENABLE + "' \n");
		
		// 车辆ID
		if (!CheckUtil.checkNull(vehicleId)) {
			sql.append("   AND V.VEHICLE_ID = '" + vehicleId + "' \n");
		}
		
		// 车架号
		if (!CheckUtil.checkNull(vin)) {
			sql.append("   AND V.VIN = '" + vin + "' \n");
		}
		
		List<Map> list = OemDAOUtil.findAll(sql.toString(), null);
		
		String businessType = null;
		
		if (null != list && list.size() > 0) {
			businessType = CommonUtils.checkNull(list.get(0).get("GROUP_TYPE"));
		}

		return businessType;
	}
	
	/**
	 * 
	* @Title: queryPtPartBaseInfo 
	* @Description:()兴趣爱好code查询
	* @return List<PtPartBaseVO>    返回类型 
	* @throws
	 */
	public List queryHobbyInfo() {
		
		StringBuffer sql= new StringBuffer();
		sql.append("select r.RELATION_CODE,r.RELATION_CN from tc_relation r,tc_code c where r.CODE_ID=c.CODE_ID and c.TYPE=1117 order by r.RELATION_CODE" );
		List<Map> relist =OemDAOUtil.findAll(sql.toString(), null);
		return relist;
	}
	
	
	/**
	 * 车辆物料信息查询
	 * @param 
	 * @param  vin
	 * @return 
	 * @throws Exception
	 */
	public Map<String, Object> getVehicleMaterialIdByVin(String vin) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT MATERIAL_ID  \n");
		sql.append(" From TM_VEHICLE_DEC \n");
		sql.append(" WHERE 1=1 ");
		if (vin!=null&&!"".equals(vin))
        {
			sql.append(" AND VIN = '"+vin+"' \n");
		}
		return OemDAOUtil.findFirst(sql.toString(), null);
	}
	
	
	
	/**
	 * 车辆发动机编号信息查询
	 * @param 
	 * @param  vin
	 * @return 
	 * @throws Exception
	 */
	public Map<String, Object> getVehicleEngineNoByVin(String vin) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ENGINE_NO  \n");
		sql.append(" From TM_VEHICLE \n");
		sql.append(" WHERE 1=1 ");
		if (vin!=null&&!"".equals(vin))
        {
			sql.append(" AND VIN = '"+vin+"' \n");
		}
		return OemDAOUtil.findFirst(sql.toString(), null);
	}
	
	/**
	 * 
	 * @Title: queryVhclMaterialByVin
	 * @Description: (查询车辆物料信息)
	 * @return List<CompeteModeVO> 返回类型
	 * @throws
	 */
	public Map<String, Object> queryVhclMaterialByMaterialId(Long materialId) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT MATERIAL_ID, \n");
		sql.append(" MATERIAL_CODE, \n");
		sql.append(" MATERIAL_NAME, \n");
		sql.append(" COLOR_CODE, \n");
		sql.append(" COLOR_NAME, \n");
		sql.append(" TRIM_CODE, \n");
		sql.append(" TRIM_NAME, \n");
		sql.append(" GROUP_ID, \n");
		sql.append(" GROUP_CODE, \n");
		sql.append(" GROUP_NAME, \n");
		sql.append(" MODEL_ID, \n");
		sql.append(" MODEL_CODE, \n");
		sql.append(" MODEL_NAME, \n");
		sql.append(" SERIES_CODE, \n");
		sql.append(" SERIES_NAME, \n");
		sql.append(" BRAND_CODE, \n");
		sql.append(" BRAND_NAME \n");
		sql.append(" FROM ("+getVwMaterialSql()+") vm WHERE vm.MATERIAL_ID =? \n");
		List paramList=new ArrayList<>();
		paramList.add(materialId);
		return OemDAOUtil.findFirst(sql.toString(), paramList);
	}
	
	
	/**
	 * 
	 * @Title: queryGroupIdByTreeCode
	 * @Description: (根据groupCode查询TreeCode)
	 * @return String 返回类型
	 * @throws
	 */
	public String queryTreeCodeByGroupCode(String groupCode) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT t1.TREE_CODE  \n");
		sql.append(" FROM TM_VHCL_MATERIAL_GROUP t1 \n");
		sql.append(" WHERE t1.GROUP_CODE = ? \n");

		List params = new ArrayList();
		params.add(groupCode);
		Map<String, Object> map = OemDAOUtil.findFirst(sql.toString(), params);
		if (map != null && !map.isEmpty()) {
			return map.get("TREE_CODE").toString();
		}
		return null;
	}
	
}
