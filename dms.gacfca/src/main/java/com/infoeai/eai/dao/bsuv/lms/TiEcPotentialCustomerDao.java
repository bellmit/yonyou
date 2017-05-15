package com.infoeai.eai.dao.bsuv.lms; 

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.common.OemDictCodeConstants;


/**
 * @author wjs
 * @date 2016年5月9日 下午4:22:26
 */
@Repository
@SuppressWarnings("rawtypes")
public class TiEcPotentialCustomerDao extends OemBaseDAO {

	/* (non-Javadoc)
	 * @see com.infoservice.dms.cgcsl.dao.AbstractIFDao#wrapperVO(java.sql.ResultSet, int, int)
	 */
	public List<Map> getEcPotentialCustomerInfo(String from,String to){
		StringBuffer sql=new StringBuffer();
		sql.append("select  ID, DEALER_NAME, DEALER_CODE,  TEL, SOLD_BY, SOLD_MOBILE, IS_HIT_SINGLE  \n");
		sql.append("  from ti_ec_hit_single_dcs T  \n");
		sql.append(" where 1=1 \n");
		sql.append("  AND ((T.CREATE_DATE>='"+from+"' and CREATE_DATE<='"+to+"' ) ");
		sql.append("  OR  (T.UPDATE_DATE >= '"+from+"' AND UPDATE_DATE<='"+to+"')) ");
		List<Map> list= OemDAOUtil.findAll(sql.toString(), null);
		
		return list;
	}
	/**
	 * 校验是否是官网经销商
	 * @return
	 */
	public String  checkIsEcDealer(String dealerCode){
		StringBuffer sql=new StringBuffer();
		sql.append("  SELECT * from TM_DEALER ");
		sql.append("   WHERE 1=1 ");
		sql.append("   AND DEALER_CODE='"+dealerCode+"' ");
		sql.append("   AND STATUS="+OemDictCodeConstants.STATUS_ENABLE);
		sql.append("   AND IS_EC="+OemDictCodeConstants.IF_TYPE_YES);
		List<Map> list=OemDAOUtil.findAll(sql.toString(), null);
		if (list.size()>0) {
			return "";
		}
		return "【经销商不存在，或者经销商不是官网经销商】";
	}
	/**
	 * 校验物料数据(品牌，车系，车型，车款，颜色，内饰)是否存在
	 * @return
	 */
	public String checkMertires(String brandCode,String seriesCode,String groupCode,String modelCode,String colorCode,String trimCode,String model_year){
		StringBuffer sql=new StringBuffer();
		sql.append(" SELECT * FROM ("+OemBaseDAO.getVwMaterialSql()+")  vw  \n");
		sql.append(" WHERE 1=1  \n");
		sql.append(" AND VW.BRAND_CODE='"+brandCode+"' AND BRAND_STATUS= "+OemDictCodeConstants.STATUS_ENABLE+"\n");
		sql.append(" AND vw.SERIES_CODE='"+seriesCode+"' AND SERIES_STATUS= "+OemDictCodeConstants.STATUS_ENABLE+"\n");
		sql.append(" AND vw.GROUP_CODE='"+groupCode+"' AND GROUP_STATUS= " +OemDictCodeConstants.STATUS_ENABLE+"\n");
		sql.append(" AND vw.MODEL_CODE='"+modelCode+"' AND MODEL_STATUS= " +OemDictCodeConstants.STATUS_ENABLE+"\n");
		sql.append(" AND vw.MODEL_YEAR='"+model_year+"' \n");
		sql.append(" AND vw.COLOR_CODE='"+colorCode+"' \n");
		sql.append(" AND vw.TRIM_CODE='"+trimCode+"' \n");
		List<Map> list=OemDAOUtil.findAll(sql.toString(), null);
		if(list.size()>0){
			return "";
		}
		return "【物料组组数据不合法】";
	}
	
	

}
 