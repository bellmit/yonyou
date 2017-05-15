package com.yonyou.dms.vehicle.dao.afterSales.custcomVehicle;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * 车辆开票日期维护
 * @author Administrator
 *
 */
@Repository
public class VehicleInvoiceDateManageDao extends OemBaseDAO{
	
	/**
	 * 车辆开票日期维护查询
	 */
	public PageInfoDto  VehicleInvoiceDateQuery(Map<String, String> queryParam) {
		   List<Object> params = new ArrayList<>();
		    String sql = getQuerySql(queryParam,params);
		    PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql,params);
		    System.out.println(pageInfoDto);
			return pageInfoDto;
	}
	private String getQuerySql(Map<String, String> queryParam, List<Object> params) {
		StringBuffer sql = new StringBuffer();
		  LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		sql.append("SELECT SR.REPORT_ID, -- 实销ID\n");
		sql.append("       org2.ORG_NAME BIG_AREA_NAME, -- 大区\n");
		sql.append("       org3.ORG_NAME SMALL_AREA_NAME, -- 小区\n");
		sql.append("       TD.DEALER_CODE, -- 经销商代码\n");
		sql.append("       TD.DEALER_SHORTNAME, -- 经销商简称\n");
		sql.append("       M.BRAND_CODE,   --  品牌 \n");
		sql.append("       M.SERIES_NAME,  --  车系 \n");
		sql.append("       M.MODEL_NAME, -- 车型  \n");
		sql.append("       M.GROUP_NAME, -- 车款 \n");
		sql.append("       M.MODEL_YEAR, -- 年款 \n");
		sql.append("       M.COLOR_NAME, -- 颜色 \n");
		sql.append("       M.TRIM_NAME, -- 内饰 \n");
		sql.append("       TV.VIN, -- VIN码,\n");
		sql.append("       RR.REMARK, -- 车辆分配备注\n");
		sql.append("       TV.VEHICLE_USAGE, -- 车辆用途\n");
		sql.append("       DATE_FORMAT(SR.INVOICE_DATE,'%Y-%m-%d') as INVOICE_DATE  -- 开票日期\n");
		sql.append("       FROM TT_VS_SALES_REPORT SR\n");
		sql.append("       inner JOIN TM_VEHICLE_dec TV ON TV.VEHICLE_ID = SR.VEHICLE_ID AND SR.STATUS = 10011001\n");
		sql.append("       inner JOIN ("+getVwMaterialSql() +") M ON M.MATERIAL_ID = TV.MATERIAL_ID \n");
		sql.append("       inner join TM_DEALER td on Td.DEALER_ID = SR.DEALER_ID\n");
		sql.append("       inner join TM_DEALER_ORG_RELATION tdor on Td.DEALER_ID = tdor.DEALER_ID  \n");
		sql.append("       inner join TM_ORG org3 on tdor.ORG_ID = org3.ORG_ID\n");
		sql.append("       inner join tm_org org2 on org3.PARENT_ORG_ID = org2.ORG_ID\n");
		sql.append("       LEFT JOIN TT_RESOURCE_REMARK RR ON RR.VIN = TV.VIN\n");
		sql.append("       where 1=1\n");
		sql.append(" "
				+ control(" M.SERIES_ID ", loginInfo.getDealerSeriesIDs(),
						loginInfo.getPoseSeriesIDs()) + " \n");
		 if (!loginInfo.getDutyType().toString().equals(
				 OemDictCodeConstants.DUTY_TYPE_DEPT.toString())
				&& !loginInfo.getDutyType().toString().equals(
						OemDictCodeConstants.DUTY_TYPE_COMPANY.toString())) {

			sql.append("   AND TD.DEALER_ID in ("
					+ OemBaseDAO.getDealersByArea(loginInfo
							.getOrgId().toString()) + ")\n");
		}
		
		// 品牌
				if (!StringUtils.isNullOrEmpty(queryParam.get("brandCode"))) {
					sql.append("   AND M.BRAND_ID = '"+queryParam.get("brandCode")+ "' \n");
				}

				// 车系
				if (!StringUtils.isNullOrEmpty(queryParam.get("seriesCode"))) {
					sql.append("   AND M.SERIES_ID = '"+queryParam.get("seriesCode")+ "' \n");
				}

				

				// 车款
				if (!StringUtils.isNullOrEmpty(queryParam.get("groupName"))) {
					sql.append("   AND M.GROUP_ID = '"+queryParam.get("groupName")+ "' \n");
				}


				// 年款
				if (!StringUtils.isNullOrEmpty(queryParam.get("modelYear"))) {
					sql.append("   AND M.MODEL_YEAR = '"+queryParam.get("modelYear")+ "' \n");
				}

				
				// 颜色
				if (!StringUtils.isNullOrEmpty(queryParam.get("colorName"))) {
					sql.append("   AND M.COLOR_CODE = '"+queryParam.get("colorName")+ "' \n");
				}
			

				// 内饰
				if (!StringUtils.isNullOrEmpty(queryParam.get("trimName"))) {
					sql.append("   AND M.TRIM_CODE = '"+queryParam.get("trimName")+ "' \n");
				}
				
				
				// 开票日期 BEGIN
				if (!StringUtils.isNullOrEmpty(queryParam.get("beginDate"))) {
					sql.append("   AND SR.INVOICE_DATE >= DATE_FORMAT('"+ queryParam.get("beginDate")+ " 00:00:00', '%Y-%m-%d %H:%i:%s') \n");
				}
			
				// 开票日期 END
				if (!StringUtils.isNullOrEmpty(queryParam.get("endDate"))) {
					sql.append("   AND SR.INVOICE_DATE  <= DATE_FORMAT('"+ queryParam.get("endDate")+ " 23:59:59', '%Y-%m-%d %H:%i:%s') \n");
				}
				
				// 底盘号(VIN)
				if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
					sql.append("   AND TV.VIN like '%"+queryParam.get("vin")+ "%' \n");
				}
				
				
				
				// 经销商
				if (!StringUtils.isNullOrEmpty(queryParam.get("dealerCode"))) {
					sql.append("   AND TD.DEALER_CODE IN ('" +queryParam.get("dealerCode")+ "') \n");
				}
		 System.out.println(sql.toString());
			return sql.toString();
	}
	
	/**
	 * 车辆开票日期维护下载
	 */
	public List<Map> download(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam, params);
		List<Map> download = OemDAOUtil.downloadPageQuery(sql, null);
		return download;
	}
	
	/**
	 * 通过id修改日期的回显信息查询
	 */
	public List<Map> getShuju(Long id){
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT TV.VIN, -- 底盘号\n");
		sql.append("       M.BRAND_CODE,  --  品牌 \n");
		sql.append("       M.SERIES_NAME,  --  车系 \n");
		sql.append("       M.MODEL_NAME,  -- 车型\n");
		sql.append("       M.COLOR_NAME,  --  颜色\n");
		sql.append("       M.TRIM_NAME, --  内饰\n");
		sql.append("       SR.REPORT_ID,  --  实销ID\n");
		sql.append("       DATE_FORMAT(SR.INVOICE_DATE,'%Y-%m-%d') as INVOICE_DATE --  开票日期\n");
		sql.append("   FROM TT_VS_SALES_REPORT SR LEFT JOIN  TM_VEHICLE_dec TV\n");
		sql.append("    ON SR.VEHICLE_ID = TV.VEHICLE_ID\n");
		sql.append("    LEFT JOIN ("+getVwMaterialSql()+") M \n");
		sql.append("    ON M.MATERIAL_ID = TV.MATERIAL_ID\n");
		sql.append("    WHERE 1=1 ");
		  if (!StringUtils.isNullOrEmpty(id)) {
				sql.append("  AND SR.REPORT_ID ="+id+"  \n");
			}
		  return OemDAOUtil.findAll(sql.toString(),null); 
		    
	}
	

}
