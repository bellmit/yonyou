package com.infoeai.eai.dao.bsuv.lms;


import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.common.service.monitor.Utility;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;

/**
 * @author wangJian
 * date 2016-04-29
 *
 */
@Repository
@SuppressWarnings("rawtypes")
public class BSUVDCSTOLMSDAO extends OemBaseDAO{

	
	//(DCS-LMS)官网批售订单状态推送接口 配车成功,发车出库
	
	public List<Map> getOrderState(String FROM,String TO,Integer statu){
		StringBuffer pasql= new StringBuffer();
		pasql.append(" SELECT  \n");
		pasql.append("  TVVC.CHANGE_CODE,TVVC.CHANGE_DATE,TVO.ETA_DATE,TVO.EC_ORDER_NO \n");
		pasql.append("  FROM TT_VS_VHCL_CHNG TVVC,TT_VS_ORDER TVO, TM_VEHICLE_DEC TV  \n");
		pasql.append("  WHERE TVVC.VEHICLE_ID = TV.VEHICLE_ID  AND TV.VIN = TVO.VIN AND TVO.EC_ORDER_NO IS NOT NULL \n");
		if (statu != null) {
			pasql.append(" AND TVVC.CHANGE_CODE = '"+statu+"' \n");
		}
		if (Utility.testIsNotNull(FROM)) {
			pasql.append(" AND TVVC.CREATE_DATE >= '"+FROM+"' \n");
		}
		if (Utility.testIsNotNull(TO)) {
			pasql.append(" AND TVVC.CREATE_DATE <= '"+TO+"' \n");
		}
		List<Map> listJJ = OemDAOUtil.findAll(pasql.toString(), null);
		return listJJ;
	}

	//(DCS-LMS)官网批售订单状态推送接口 验收入库  new add by wangJian 2016-07-05
	public List<Map> getRuKuOrderState(String FROM,String TO,Integer statu){
		StringBuffer pasql= new StringBuffer();
		pasql.append(" SELECT  \n");
		pasql.append("  TVVC.CHANGE_CODE,TVVC.CHANGE_DATE,TVO.ETA_DATE,TVO.EC_ORDER_NO, \n");
		pasql.append("  TVI.INSPECTION_RESULT,TVI.PDI_RESULT,TVID.DAMAGE_PART,TVID.DAMAGE_DESC \n");
		pasql.append("  FROM TT_VS_VHCL_CHNG TVVC,TT_VS_ORDER TVO, TM_VEHICLE_DEC TV, TT_VS_INSPECTION TVI,TT_VS_INSPECTION_DETAIL TVID  \n");
		pasql.append("  WHERE TVVC.VEHICLE_ID = TV.VEHICLE_ID AND TVI.VEHICLE_ID = TV.VEHICLE_ID AND TVI.INSPECTION_ID = TVID.INSPECTION_ID \n");
		pasql.append("   AND TV.VIN = TVO.VIN AND TVO.EC_ORDER_NO IS NOT NULL \n");
		if (statu != null) {
			pasql.append(" AND TVVC.CHANGE_CODE = '"+statu+"' \n");
		}
		if (Utility.testIsNotNull(FROM)) {
			pasql.append(" AND TVVC.CREATE_DATE >= '"+FROM+"' \n");
		}
		if (Utility.testIsNotNull(TO)) {
			pasql.append(" AND TVVC.CREATE_DATE <= '"+TO+"' \n");
		}
		List<Map> listJJ = OemDAOUtil.findAll(pasql.toString(), null);
		return listJJ;
	}
	//(DCS-LMS)经销商确认
	public List<Map> getDealerSure(String FROM,String TO,Integer statu){
		StringBuffer pasql= new StringBuffer();
		pasql.append(" SELECT  \n");
		pasql.append("  TVOH.CHANGE_STATUS,TVOH.CREATE_DATE,TVO.EC_ORDER_NO \n");
		pasql.append("  FROM TT_VS_ORDER_HISTORY TVOH,TT_VS_ORDER TVO  \n");
		pasql.append("  WHERE TVOH.ORDER_ID = TVO.ORDER_ID AND TVO.EC_ORDER_NO IS NOT NULL \n");
		if (statu != null) {
			pasql.append(" AND TVOH.CHANGE_STATUS = '"+statu+"' \n");
			pasql.append(" AND TVOH.CHANGE_REMARK = '经销商订单确认' \n");
			
		}
		if (Utility.testIsNotNull(FROM)) {
			pasql.append(" AND TVOH.CREATE_DATE >= '"+FROM+"' \n");
		}
		if (Utility.testIsNotNull(TO)) {
			pasql.append(" AND TVOH.CREATE_DATE <= '"+TO+"' \n");
		}
		List<Map> listJJ = OemDAOUtil.findAll(pasql.toString(), null);
		return listJJ;
	}
	
	//(DCS-LMS)零售订单信息推送接口
	public List<Map> getOrderInfo(String FROM,String TO,String saleStatu){
		StringBuffer pasql= new StringBuffer();
		pasql.append(" SELECT DEALER_CODE,ID_CRAD, \n");
		pasql.append(" EC_ORDER_NO,BRAND_CODE,SERIES_CODE,MODEL_CODE,GROUP_CODE,TRIM_CODE,COLOR_CODE,  \n");
		pasql.append(" CUSTOMER_NAME,TEL,DEPOSIT_DATE,RETAIL_FINANCE,DEPOSIT_AMOUNT,REVOKE_DATE,  \n");
		pasql.append(" SUBMIT_DATE,DELIVER_DATE,SALE_DATE, ORDER_STATUS,ESC_COMFIRM_TYPE  \n");
		pasql.append(" from  \n");
		pasql.append(" TI_EC_RETAIL_SALE_DCS where 1=1 AND EC_ORDER_NO IS NOT NULL \n");
		//官网批售实销状态
		if (Utility.testIsNotNull(saleStatu)) {
			pasql.append(" AND ORDER_STATUS in ('13011035','13011075') \n");
		}
		if("".equals(saleStatu)){
			pasql.append(" AND ORDER_STATUS not in ('13011035','13011075') \n");
		}
		if (Utility.testIsNotNull(FROM)) {
			pasql.append(" AND CREATE_DATE >= '"+FROM+"' \n");
		}
		if (Utility.testIsNotNull(TO)) {
			pasql.append(" AND CREATE_DATE <= '"+TO+"' \n");
		}
		List<Map> listJJ = OemDAOUtil.findAll(pasql.toString(), null);
		return listJJ;
	}
}
