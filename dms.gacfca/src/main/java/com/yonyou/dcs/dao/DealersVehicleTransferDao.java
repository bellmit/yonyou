package com.yonyou.dcs.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.yonyou.dms.DTO.gacfca.SA007Dto;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
/**
 * 
* @ClassName: DealersVehicleTransferDao 
* @Description: 经销商之间车辆下发调拨
* @author zhengzengliang 
* @date 2017年4月12日 下午7:29:10 
*
 */
@SuppressWarnings("rawtypes")
@Repository
public class DealersVehicleTransferDao extends OemBaseDAO{
	
	@Autowired
	DeCommonDao deCommonDao;

	/**
	 * 
	* @Title: queryVehicleTransferInfo 
	* @Description: TODO(经销商之间车辆调拨列表) 
	* @return List<CompeteModeVO>    返回类型 
	* @throws
	 */
	public List<SA007Dto> queryVehicleTransferInfo(Long vehicleId, Long inDealerId, Long outDealerId) {
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT V1.IN_DEALER_ID,\n" );
		sql.append("       IND.DEALER_CODE  IN_DEALER_CODE,\n" );
		sql.append("       V1.OUT_DEALER_ID,\n" );
		sql.append("       OUTD.DEALER_CODE OUT_DEALER_CODE,\n" );
		sql.append("       V3.PRODUCTCODE   PRODUCT_CODE,\n" );
		sql.append("       V2.ENGINE_NO,\n" );
		sql.append("       V2.VIN,\n" );
		sql.append("       V2.PRODUCT_DATE\n" );
		sql.append("  FROM TT_VS_VEHICLE_TRANSFER V1,\n" );
		sql.append("       TM_VEHICLE_DEC V2,\n" );
		sql.append("       (SELECT t1.GROUP_CODE AS CONFIGCODE,\n" );
		sql.append("               t1.GROUP_NAME AS CONFIGNAME,\n" );
		sql.append("               t2.GROUP_CODE AS MODELCODE,\n" );
		sql.append("               t2.GROUP_NAME AS MODELNAME,\n" );
		sql.append("               t5.MATERIAL_ID,\n" );
		sql.append("               t3.GROUP_CODE AS SERIESCODE,\n" );
		sql.append("               t3.GROUP_NAME AS SERIESNAME,\n" );
		sql.append("               t4.GROUP_CODE AS BRANDCODE,\n" );
		sql.append("               t4.GROUP_NAME AS BRANDNAME,\n" );
		sql.append("               t5.VHCL_PRICE,\n" );
		sql.append("               t5.STATUS,\n" );
		sql.append("               t5.SALE_PRICE,\n" );
		sql.append("               CONCAT(t5.MATERIAL_CODE,t5.COLOR_CODE,t5.TRIM_CODE,v.MODEL_YEAR) AS PRODUCTCODE,\n" );
		sql.append("               t5.COLOR_CODE,\n" );
		sql.append("               t5.COLOR_NAME,\n" );
		sql.append("               CONCAT(t5.MATERIAL_NAME,t5.COLOR_NAME,t5.TRIM_NAME,v.MODEL_YEAR) AS PRODUCTNAME,\n" );
		sql.append("               v.MODEL_YEAR\n" );
		sql.append("          FROM TM_VHCL_MATERIAL         t5,\n" );
		sql.append("               TM_VHCL_MATERIAL_GROUP_R t6,\n" );
		sql.append("               TM_VHCL_MATERIAL_GROUP   t1,\n" );
		sql.append("               TM_VHCL_MATERIAL_GROUP   t2,\n" );
		sql.append("               TM_VHCL_MATERIAL_GROUP   t3,\n" );
		sql.append("               TM_VHCL_MATERIAL_GROUP   t4,\n" );
		sql.append("               ("+getVwMaterialSql()+") v \n" );
		sql.append("         WHERE t5.MATERIAL_ID = t6.MATERIAL_ID\n" );
		sql.append("           AND T5.MATERIAL_ID = V.MATERIAL_ID\n" );
		sql.append("           AND t6.GROUP_ID = t1.GROUP_ID\n" );
		sql.append("           AND t1.PARENT_GROUP_ID = t2.GROUP_ID\n" );
		sql.append("           AND t2.PARENT_GROUP_ID = t3.GROUP_ID\n" );
		sql.append("           AND t3.PARENT_GROUP_ID = t4.GROUP_ID\n" );
		sql.append("           AND t1.GROUP_LEVEL = '4') V3,\n" );
		sql.append("       TM_DEALER IND,\n" );
		sql.append("       TM_DEALER OUTD\n" );
		sql.append(" WHERE V1.VEHICLE_ID = V2.VEHICLE_ID\n" );
		sql.append("   AND V2.MATERIAL_ID = V3.MATERIAL_ID\n" );
		sql.append("   AND V1.IN_DEALER_ID = IND.DEALER_ID\n" );
		sql.append("   AND V1.OUT_DEALER_ID = OUTD.DEALER_ID\n");
		sql.append("   AND V1.IS_SEND = 0\n");
		sql.append("   AND V2.VEHICLE_ID = "+vehicleId+"\n");
		sql.append("   AND V1.IN_DEALER_ID = "+inDealerId+"\n");
		sql.append("   AND V1.OUT_DEALER_ID = "+outDealerId);
		System.out.println(sql.toString());
		List<SA007Dto> list = wrapperVO(OemDAOUtil.findAll(sql.toString(),null));
		return list;
	}
	protected List<SA007Dto> wrapperVO(List<Map> rs) {
		List<SA007Dto> resultList = new ArrayList<SA007Dto>();
		try {
			if(null!=rs && rs.size()>0){
				for (int i = 0; i < rs.size(); i++) {
					SA007Dto vo = new SA007Dto();
					String inEntityCode = "";
					String outEntityCode = "";
					Map inDmsDealer = deCommonDao.getDmsDealerCode(rs.get(i).get("IN_DEALER_CODE").toString());
					if(inDmsDealer!=null&&!"".equals(CommonUtils.checkNull(inDmsDealer.get("DMS_CODE")))){
						//可下发的经销商列表
						inEntityCode=inDmsDealer.get("DMS_CODE").toString();
					}
					Map outDmsDealer = deCommonDao.getDmsDealerCode(rs.get(i).get("OUT_DEALER_CODE").toString());
					if(outDmsDealer!=null&&!"".equals(CommonUtils.checkNull(outDmsDealer.get("DMS_CODE")))){
						//可下发的经销商列表
						outEntityCode=outDmsDealer.get("DMS_CODE").toString();
					}
					vo.setInEntityCode(inEntityCode);
					vo.setOutEntityCode(outEntityCode);
					vo.setProductCode(CommonUtils.checkNull(rs.get(i).get("PRODUCT_CODE")) );
					vo.setEngineNo(CommonUtils.checkNull(rs.get(i).get("ENGINE_NO")) );
					vo.setVin(CommonUtils.checkNull(rs.get(i).get("VIN")) );
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					Date productDate = CommonUtils.checkNull(rs.get(i).get("PRODUCT_DATE")) == "" ? null : sdf.parse(CommonUtils.checkNull(rs.get(i).get("PRODUCT_DATE")));
					vo.setManufactureDate(productDate );
					vo.setFactoryDate(productDate );
					
					resultList.add(vo);
				}
			}
		} catch (Exception e) {
			throw new ServiceBizException(e);
		}
		return resultList;
	}
	
	

}
