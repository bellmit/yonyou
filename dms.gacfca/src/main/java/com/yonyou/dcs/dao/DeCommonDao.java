package com.yonyou.dcs.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.common.OemDictCodeConstants;
/**
 * 
* @ClassName: DeCommonDao 
* @Description: 
* @author zhengzengliang 
* @date 2017年4月11日 下午6:24:49 
*
 */
@SuppressWarnings("rawtypes")
@Repository
public class DeCommonDao extends OemBaseDAO{
	
	/**
	 * 根据车架号查询订单信息
	 * @param vin
	 * @return
	 * @throws Exception
	 */
	public Map getOrderByVin(String vin) throws Exception {
		
		StringBuffer sql = new StringBuffer();
		sql.append(" \n");
		sql.append("SELECT V.VIN, -- 车架号 \n");
		sql.append("       D.DEALER_CODE, -- 经销商代码 \n");
		sql.append("       O.ORDER_ID -- 订单ID \n");
		sql.append("  FROM TM_VEHICLE_DEC V \n");
		sql.append(" INNER JOIN TT_VS_ORDER O ON O.VIN = V.VIN \n");
		sql.append(" INNER JOIN TM_DEALER D ON D.DEALER_ID = O.DEALER_ID \n");
		sql.append(" WHERE O.VIN = '" + vin + "' \n");
		sql.append("  WITH UR \n");

		Map map = OemDAOUtil.findFirst(sql.toString(), null);
		return map;
	}
	
	/**
	 * 
	* @Title: getDcsCompanyCode 
	* @Description: TODO(根据下端dealerCode取上端公司code) 
	* @param @param dealerCode 下端经销商公司code
	* @param @return    上端公司code
	* @return String    返回类型 
	 * @throws Exception 
	* @throws
	 */
	public Map getDcsCompanyCode(String dealerCode) throws Exception {
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT DCS_CODE, DMS_CODE FROM TI_DEALER_RELATION");
		sql.append(" WHERE DMS_CODE = '").append(dealerCode).append("'");
		Map map = OemDAOUtil.findFirst(sql.toString(), null);
		if (map == null || map.size() == 0) {
			throw new Exception("DCS端不存在编码为" + dealerCode + "的经销商记录");
		} else {
			return map;
		}
	}

	/**
	 * 根据上端dealerCode查询下端entityCode
	 * 先根据dealerCode查询companyCode,再根据companyCode对应entityCode
	 * @param dealerCode 上端经销商Code
	 * @return entityCode 下端经销商公司Code
	 * add by huyu
	 */
	public List<Map> getDmsDealerCodes(String dealerCode) throws Exception {
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT C.DMS_CODE\n" );
		sql.append("	FROM TM_DEALER A, TM_COMPANY B, TI_DEALER_RELATION C\n" );
		sql.append("		WHERE A.COMPANY_ID = B.COMPANY_ID\n" );
		sql.append("			AND B.COMPANY_CODE = C.DCS_CODE\n" );
		sql.append("			AND C.STATUS="+OemDictCodeConstants.STATUS_ENABLE+"\n" );
		sql.append("			AND A.DEALER_CODE in ('").append(dealerCode).append("')\n");
		List<Map> ps = OemDAOUtil.findAll(sql.toString(), null);
		return ps;
	}
}
