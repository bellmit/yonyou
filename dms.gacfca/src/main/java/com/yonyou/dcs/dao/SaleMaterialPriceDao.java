package com.yonyou.dcs.dao;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.DTO.gacfca.ProductModelPriceDTO;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.function.utils.common.StringUtils;
/**
 * 
* @ClassName: SaleMaterialPriceDao 
* @Description: 车型价格信息下发
* @author zhengzengliang 
* @date 2017年4月5日 下午2:28:51 
*
 */
@SuppressWarnings("rawtypes")
@Repository
public class SaleMaterialPriceDao extends OemBaseDAO {
	
	/**
	 * 
	* @Title: queryMaterialGroupInfo 
	* @Description: TODO(查询车型价格列表) 
	* @return List<CompeteModeVO>    返回类型 
	* @throws
	 */
	public LinkedList<ProductModelPriceDTO> selectMaterialPriceInfo(String[] groupIds) {
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT distinct t1.GROUP_CODE as CONFIGCODE,\n" );
		sql.append("       t1.GROUP_NAME as CONFIGNAME,\n" );
		sql.append("       t2.GROUP_CODE as MODELCODE,\n" );
		sql.append("       t2.GROUP_NAME as MODELNAME,\n" );
		sql.append("       t3.GROUP_CODE as SERIESCODE,\n" );
		sql.append("       t3.GROUP_NAME as SERIESNAME,\n" );
		sql.append("       t4.GROUP_CODE as BRANDCODE,\n" );
		sql.append("       t4.GROUP_NAME as BRANDNAME,\n" );
		sql.append("       t5.VHCL_PRICE,\n" );
		sql.append("       t5.STATUS,\n" );
		sql.append("       t5.SALE_PRICE,\n" );
		sql.append("       RTRIM(char(t5.MATERIAL_CODE || t5.COLOR_CODE || t5.TRIM_CODE ||\n" );
		sql.append("                  t1.MODEL_YEAR)) as PRODUCTCODE,\n" );
		sql.append("       t5.COLOR_CODE,\n" );
		sql.append("       t5.COLOR_NAME,\n" );
		sql.append("       RTRIM(char(t5.MATERIAL_NAME || t5.COLOR_NAME || t5.TRIM_NAME ||\n" );
		sql.append("                  t1.MODEL_YEAR)) as PRODUCTNAME,\n" );
		sql.append("       t1.MODEL_YEAR\n" );
		//wjs  2016-01-05 添加燃油类型，发动机排量
		sql.append(",   t3.OILE_TYPE,t3.WX_ENGINE  ");
		sql.append("  FROM TM_VHCL_MATERIAL         t5,\n" );
		sql.append("       TM_VHCL_MATERIAL_GROUP_R t6,\n" );
		sql.append("       TM_VHCL_MATERIAL_GROUP   t1,\n" );
		sql.append("       TM_VHCL_MATERIAL_GROUP   t2,\n" );
		sql.append("       TM_VHCL_MATERIAL_GROUP   t3,\n" );
		sql.append("       TM_VHCL_MATERIAL_GROUP   t4\n" );
		sql.append(" WHERE t5.MATERIAL_ID = t6.MATERIAL_ID\n" );
		sql.append("   AND t6.GROUP_ID = t1.GROUP_ID\n" );
		sql.append("   AND t1.PARENT_GROUP_ID = t2.GROUP_ID\n" );
		sql.append("   AND t2.PARENT_GROUP_ID = t3.GROUP_ID\n" );
		sql.append("   AND t3.PARENT_GROUP_ID = t4.GROUP_ID\n" );
		sql.append("   AND t1.GROUP_LEVEL = '4'\n" );
		if(null!=groupIds && groupIds.length>0){
			sql.append(" AND  t3.GROUP_ID in (");
			for(int i=0;i< groupIds.length ; i++){
				if( (i+1) == groupIds.length){
					sql.append(" '"+groupIds[i]+"' ");
				}else{
					sql.append(" '"+groupIds[i]+"', ");
				}
			}
			sql.append(" ) ");
		}
		LinkedList<ProductModelPriceDTO> list = wrapperVO(OemDAOUtil.findAll(sql.toString(),null));
		return list;
	}
	protected LinkedList<ProductModelPriceDTO> wrapperVO(List<Map> rs) {
		LinkedList<ProductModelPriceDTO> resultList = new LinkedList<ProductModelPriceDTO>();
		try {
			if(null!=rs && rs.size()>0){
				for (int i = 0; i < rs.size(); i++) {
					ProductModelPriceDTO dto = new ProductModelPriceDTO();
					dto.setBrandCode(CommonUtils.checkNull(rs.get(i).get("BRANDCODE")));
					dto.setBrandName(CommonUtils.checkNull(rs.get(i).get("BRANDNAME")));
					dto.setSeriesCode(CommonUtils.checkNull(rs.get(i).get("SERIESCODE")));
					dto.setSeriesName(CommonUtils.checkNull(rs.get(i).get("SERIESNAME")));
					dto.setModelCode(CommonUtils.checkNull(rs.get(i).get("MODELCODE")));
					dto.setModelName(CommonUtils.checkNull(rs.get(i).get("MODELNAME")));
					dto.setConfigCode(CommonUtils.checkNull(rs.get(i).get("CONFIGCODE")));
					dto.setConfigName(CommonUtils.checkNull(rs.get(i).get("CONFIGNAME")));
					dto.setColorCode(CommonUtils.checkNull(rs.get(i).get("COLOR_CODE")));
					dto.setColorName(CommonUtils.checkNull(rs.get(i).get("COLOR_NAME")));
					dto.setDownTimestamp(new Date());
					dto.setProductCode(CommonUtils.checkNull(rs.get(i).get("PRODUCTCODE")));
					dto.setProductName(CommonUtils.checkNull(rs.get(i).get("PRODUCTNAME")));
					if(!StringUtils.isNullOrEmpty(rs.get(i).get("SALE_PRICE"))) {
						dto.setPurchasePrice(Double.parseDouble(rs.get(i).get("SALE_PRICE").toString()));
					}
					
					if(!StringUtils.isNullOrEmpty(rs.get(i).get("SALE_PRICE"))) {
						dto.setOemDirectivePrice(Double.valueOf(rs.get(i).get("VHCL_PRICE").toString()));
					}
					
					dto.setPurchasePrice(0.0);
					dto.setOemDirectivePrice(0.0);
					dto.setIsValid(CommonUtils.checkNullInt(rs.get(i).get("STATUS"),0));
					dto.setModelYear(CommonUtils.checkNull(rs.get(i).get("MODEL_YEAR")));
					resultList.add(dto);
				}
			}
		} catch (Exception e) {
			throw new ServiceBizException(e);
		}
		return resultList;
	}
	
	/**
	 * 
	* @Title: queryMaterialGroupInfo 
	* @Description: TODO(查询车型价格列表) 
	* @return List<ProductModelPriceDTO>    返回类型 
	* @throws
	 */
	public LinkedList<ProductModelPriceDTO> queryMaterialPriceInfo() {
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT distinct t1.GROUP_CODE as CONFIGCODE,\n" );
		sql.append("       t1.GROUP_NAME as CONFIGNAME,\n" );
		sql.append("       t2.GROUP_CODE as MODELCODE,\n" );
		sql.append("       t2.GROUP_NAME as MODELNAME,\n" );
		sql.append("       t3.GROUP_CODE as SERIESCODE,\n" );
		sql.append("       t3.GROUP_NAME as SERIESNAME,\n" );
		sql.append("       t4.GROUP_CODE as BRANDCODE,\n" );
		sql.append("       t4.GROUP_NAME as BRANDNAME,\n" );
		sql.append("       t5.VHCL_PRICE,\n" );
		sql.append("       t5.STATUS,\n" );
		sql.append("       t5.SALE_PRICE,\n" );
		sql.append("       RTRIM(char(t5.MATERIAL_CODE || t5.COLOR_CODE || t5.TRIM_CODE ||\n" );
		sql.append("                  t1.MODEL_YEAR)) as PRODUCTCODE,\n" );
		sql.append("       t5.COLOR_CODE,\n" );
		sql.append("       t5.COLOR_NAME,\n" );
		sql.append("       RTRIM(char(t5.MATERIAL_NAME || t5.COLOR_NAME || t5.TRIM_NAME ||\n" );
		sql.append("                  t1.MODEL_YEAR)) as PRODUCTNAME,\n" );
		sql.append("       t1.MODEL_YEAR\n" );
		//wjs  2016-01-05 添加燃油类型，发动机排量
		sql.append(",   t3.OILE_TYPE,t3.WX_ENGINE  ");
		
		sql.append("  FROM TM_VHCL_MATERIAL         t5,\n" );
		sql.append("       TM_VHCL_MATERIAL_GROUP_R t6,\n" );
		sql.append("       TM_VHCL_MATERIAL_GROUP   t1,\n" );
		sql.append("       TM_VHCL_MATERIAL_GROUP   t2,\n" );
		sql.append("       TM_VHCL_MATERIAL_GROUP   t3,\n" );
		sql.append("       TM_VHCL_MATERIAL_GROUP   t4\n" );
		//sql.append("       ,VW_MATERIAL              t7\n" );
		sql.append(" WHERE t5.MATERIAL_ID = t6.MATERIAL_ID\n" );
		sql.append("   AND t6.GROUP_ID = t1.GROUP_ID\n" );
		sql.append("   AND t1.PARENT_GROUP_ID = t2.GROUP_ID\n" );
		sql.append("   AND t2.PARENT_GROUP_ID = t3.GROUP_ID\n" );
		sql.append("   AND t3.PARENT_GROUP_ID = t4.GROUP_ID\n" );
		sql.append("   AND t1.GROUP_LEVEL = '4'\n" );
		
		LinkedList<ProductModelPriceDTO> list = wrapperVO(OemDAOUtil.findAll(sql.toString(), null));
		return list;
	}

}
