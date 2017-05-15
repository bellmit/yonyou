package com.yonyou.dcs.dao;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.springframework.stereotype.Repository;

import com.yonyou.dms.DTO.gacfca.VehicleShippingDetailDto;
import com.yonyou.dms.DTO.gacfca.VehicleShippingDto;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
/**
 * 
* @ClassName: SaleVehicleShippingDao 
* @Description: 车辆发运信息下发
* @author zhengzengliang 
* @date 2017年4月11日 下午5:12:11 
*
 */
@SuppressWarnings("rawtypes")
@Repository
public class SaleVehicleShippingDao extends OemBaseDAO{
	
	/**
	* @Title: queryVehicleShipInfo 
	* @Description: TODO(查询已发运出库车辆信息) 
	* @param @return  
	* @return List<VehicleShippingVO>    返回类型 
	* @throws
	 */
	public LinkedList<VehicleShippingDto> queryVehicleShipInfo2(String dealerId,String vehicleId) {
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT DISTINCT t3.ORDER_NO, t3.DEALER_ID, t3.VIN,t3.EC_ORDER_NO \n" );
		sql.append("  FROM TT_VS_ORDER t3 \n" );
		sql.append(" where t3.DEALER_ID = "+dealerId+"\n" );
		sql.append("   AND t3.ORDER_STATUS != "+OemDictCodeConstants.ORDER_STATUS_08+" \n");//已取消
		sql.append("   AND t3.ORDER_STATUS != "+OemDictCodeConstants.ORDER_STATUS_09+" \n");//已撤单
		sql.append("   AND t3.ORDER_STATUS != "+OemDictCodeConstants.SALE_ORDER_TYPE_13+" \n");//已取消
		sql.append("   AND t3.ORDER_TYPE != "+OemDictCodeConstants.ORDER_TYPE_DOMESTIC_03+" \n");//不再将直销车辆信息推送给【提车经销商】（代交车实体经销商） add by huyu
		sql.append("   AND t3.IS_DEL <> 1 \n");//未逻辑删除
		sql.append(" and t3.VIN = (select vin from tm_vehicle_dec where vehicle_id = "+vehicleId+")");
		System.err.println(sql.toString());
		LinkedList<VehicleShippingDto> list = wrapperVO(OemDAOUtil.findAll(sql.toString(),null));
		return list;
	}
	protected LinkedList<VehicleShippingDto> wrapperVO(List<Map> rs) {
		LinkedList<VehicleShippingDto> resultList = new LinkedList<VehicleShippingDto>();
		try {
			if(null!=rs && rs.size()>0){
				for (int i = 0; i < rs.size(); i++) {
					VehicleShippingDto dto = new VehicleShippingDto();
					dto.setEcOrderNo(CommonUtils.checkNull(rs.get(i).get("EC_ORDER_NO")));
					dto.setPoNo(CommonUtils.checkNull(rs.get(i).get("ORDER_NO")));
					dto.setIsValid(12781001);
					dto.setDownTimestamp(new Date());
					dto.setVehicleVoList(queryVehicleShipDetialInfo(CommonUtils.checkNull(rs.get(i).get("DEALER_ID")),CommonUtils.checkNull(rs.get(i).get("VIN"))));
					dto.setEcOrderNo(CommonUtils.checkNull(rs.get(i).get("EC_ORDER_NO")));//官网订单号
					resultList.add(dto);
				}
			}
		} catch (Exception e) {
			throw new ServiceBizException(e);
		}
		return resultList;
	}
	
	/**
	 * @Title: queryActivityReadyVehicle
	 * @Description: TODO(查询经销商修好上报需要下发的服务活动车辆)
	 * @param @param activityCode
	 * @param @return 设定文件
	 * @return List<BaseVO> 返回类型
	 * @throws
	 */
	private LinkedList<VehicleShippingDetailDto> queryVehicleShipDetialInfo(String dealerId, String vin) {
		
		StringBuffer sql = new StringBuffer();
		sql.append(" \n");
		sql.append("SELECT V.VIN, -- 车架号 \n");
		sql.append("       V.ENGINE_NO, -- 发动机号 \n");
		sql.append("       DATE(V.PRODUCT_DATE) AS PRODUCT_DATE, -- 生产日期 \n");
		sql.append("       V.MATERIAL_ID, -- 物料ID \n");
		sql.append("       (M.MATERIAL_CODE || M.COLOR_CODE || M.TRIM_CODE || M.MODEL_YEAR) AS PRODUCTCODE, -- 生产代码 \n");
		sql.append("       V.STOCKOUT_DEALER_DATE AS ACTUAL_DATE, -- 经销商库存日期 \n");
		sql.append("       V.MODEL_YEAR -- 年款 \n");
		sql.append("  FROM TM_VEHICLE_DEC V \n");
		sql.append(" INNER JOIN ("+getVwMaterialSql()+") M ON V.MATERIAL_ID = M.MATERIAL_ID \n");
		sql.append(" WHERE V.IS_DEL = 0 \n");
		sql.append("   AND (V.IS_SEND = 0 OR V.IS_SEND IS NULL) \n");
		sql.append("   AND (V.NODE_STATUS = '" + OemDictCodeConstants.VEHICLE_NODE_12 + "' OR \n");
		sql.append("        V.NODE_STATUS = '" + OemDictCodeConstants.K4_VEHICLE_NODE_16 + "' OR \n");
		sql.append("        V.NODE_STATUS = '" + OemDictCodeConstants.K4_VEHICLE_NODE_17 + "' OR \n");
		sql.append("        V.NODE_STATUS = '" + OemDictCodeConstants.K4_VEHICLE_NODE_18 + "') \n");
		sql.append("   AND V.DEALER_ID = '" + dealerId + "' \n");
		sql.append("   AND V.VIN = '" + vin + "' \n");
		//sql.append("  WITH UR \n");

		LinkedList<VehicleShippingDetailDto> vos = wrapperDetailVO(OemDAOUtil.findAll(sql.toString(),null));
		
		return vos;
		
	}
	
	protected LinkedList<VehicleShippingDetailDto> wrapperDetailVO(List<Map> rs) {
		LinkedList<VehicleShippingDetailDto> resultList = new LinkedList<VehicleShippingDetailDto>();
		try {
			if(null!=rs && rs.size()>0){
				for (int i = 0; i < rs.size(); i++) {
					VehicleShippingDetailDto dto = new VehicleShippingDetailDto();
					dto.setVin(CommonUtils.checkNull(rs.get(i).get("VIN")));
					dto.setProductCode(CommonUtils.checkNull(rs.get(i).get("PRODUCTCODE")));
					dto.setEngineNo(CommonUtils.checkNull(rs.get(i).get("ENGINE_NO")));
					dto.setManufactureDate(CommonUtils.parseDate(CommonUtils.checkNull(rs.get(i).get("PRODUCT_DATE"))));
					dto.setFactoryDate(CommonUtils.parseDate(CommonUtils.checkNull(rs.get(i).get("ACTUAL_DATE"))));
					dto.setModelYear(CommonUtils.checkNull(rs.get(i).get("MODEL_YEAR")));
					resultList.add(dto);
				}
			}
		} catch (Exception e) {
			throw new ServiceBizException(e);
		}
		return resultList;
	}
	
	public void updateVehicle(String dealerId, String vehicleId) {
		try {
			String sql = "update TM_VEHICLE_DEC set IS_SEND = 1  where  IS_DEL = 0 AND (IS_SEND = 0 or IS_SEND IS NULL) AND (NODE_STATUS =?  OR NODE_STATUS = ? OR    NODE_STATUS =  ?  OR    NODE_STATUS =  ? )  AND DEALER_ID =  ?   AND VEHICLE_ID =  ?  ";
			Base.exec(sql,OemDictCodeConstants.VEHICLE_NODE_12, OemDictCodeConstants.K4_VEHICLE_NODE_16, OemDictCodeConstants.K4_VEHICLE_NODE_17,OemDictCodeConstants.K4_VEHICLE_NODE_18,dealerId,vehicleId );
		System.err.println(sql);
		} catch (Exception e) {
			
		}
		//TmVehiclePO.update(" IS_SEND = 1 ", " IS_DEL = 0 AND (IS_SEND = 0 or IS_SEND IS NULL) AND (NODE_STATUS = ' ? ' OR NODE_STATUS = '?' OR    NODE_STATUS = ' ? ' OR    NODE_STATUS = ' ? ')  AND DEALER_ID = ' ? '  AND VEHICLE_ID = ' ? ' ", OemDictCodeConstants.VEHICLE_NODE_12, OemDictCodeConstants.K4_VEHICLE_NODE_16, OemDictCodeConstants.K4_VEHICLE_NODE_17,OemDictCodeConstants.K4_VEHICLE_NODE_18,dealerId,vehicleId);
	}

}
