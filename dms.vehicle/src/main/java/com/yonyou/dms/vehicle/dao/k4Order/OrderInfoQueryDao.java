package com.yonyou.dms.vehicle.dao.k4Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * @author liujiming
 * @date 2017年3月1日
 */

@Repository
public class OrderInfoQueryDao extends OemBaseDAO {

	/**
	 * 整车订单汇总查询
	 * 
	 * @param queryParam
	 * @return pageInfoDto
	 */
	public PageInfoDto getOrderInfoTotalQueryList(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getTotalQuerySql(queryParam, params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;

	}

	/**
	 * 整车订单汇总下载
	 * 
	 * @param queryParam
	 * @return pageInfoDto
	 */
	public List<Map> getOrderInfoTotalDownloadList(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getTotalQuerySql(queryParam, params);
		List<Map> orderList = OemDAOUtil.downloadPageQuery(sql, params);
		return orderList;
	}

	/**
	 * 整车订单明细查询
	 * 
	 * @param queryParam
	 * @return pageInfoDto
	 */
	public PageInfoDto getOrderInfoDetailQueryList(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getDetailQuerySql(queryParam, params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;

	}

	/**
	 * 整车订单明细下载
	 * 
	 * @param queryParam
	 * @return pageInfoDto
	 */
	public List<Map> getOrderInfoDetailDownloadList(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getDetailQuerySql(queryParam, params);
		List<Map> orderList = OemDAOUtil.downloadPageQuery(sql, params);
		return orderList;
	}

	/**
	 * SQL组装 汇总查询
	 * 
	 * @param queryParam
	 * @param params
	 * @return
	 */
	private String getTotalQuerySql(Map<String, String> queryParam, List<Object> params) {

		// 查询条件
		StringBuilder conditionWhere = new StringBuilder();

		// 测试数据，减少记录
		// conditionWhere.append(" AND TVO.ORDER_ID in
		// (2015121022606608,2016010823081061) "); //测试数据，减少记录

		// 品牌
		if (!StringUtils.isNullOrEmpty(queryParam.get("brandId"))) {
			conditionWhere.append(" and VM.BRAND_ID= " + queryParam.get("brandId") + " \n");
		}
		// 车系
		if (!StringUtils.isNullOrEmpty(queryParam.get("seriesId"))) {
			conditionWhere.append(" and VM.SERIES_ID= " + queryParam.get("seriesId") + " \n");
		}
		// 车款
		if (!StringUtils.isNullOrEmpty(queryParam.get("groupId"))) {
			conditionWhere.append(" and VM.GROUP_ID= " + queryParam.get("groupId") + " \n");
		}
		// 年款
		if (!StringUtils.isNullOrEmpty(queryParam.get("modelId"))) {
			conditionWhere.append(" and VM.MODEL_YEAR= " + queryParam.get("modelId") + " \n");
		}
		// 外饰
		if (!StringUtils.isNullOrEmpty(queryParam.get("colorId"))) {
			conditionWhere.append(" and VM.COLOR_NAME= '" + queryParam.get("colorId") + "' \n");
		}
		// 内饰
		if (!StringUtils.isNullOrEmpty(queryParam.get("trimId"))) {
			conditionWhere.append(" and VM.TRIM_NAME= '" + queryParam.get("trimId") + "' \n");
		}
		// 经销商
		if (!StringUtils.isNullOrEmpty(queryParam.get("dealerCode"))) {
			conditionWhere.append(" and TD.DEALER_CODE in (" + queryParam.get("dealerCode") + ") \n");
		}
		// VIN
		if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
			conditionWhere.append(" and TV.VIN like '%" + queryParam.get("vin") + "%' \n");
		}
		// 车辆节点
		if (!StringUtils.isNullOrEmpty(queryParam.get("vehicleNode"))) {
			conditionWhere.append(" and TV.NODE_STATUS = " + queryParam.get("vehicleNode") + " \n");
		}
		// 订单类型
		if (!StringUtils.isNullOrEmpty(queryParam.get("orderType"))) {
			conditionWhere.append(" and TVO.ORDER_TYPE = " + queryParam.get("orderType") + " \n");
		}
		// 付款方式
		if (!StringUtils.isNullOrEmpty(queryParam.get("vehicleSalePayType"))) {
			conditionWhere.append(" and TVO.PAYMENT_TYPE = " + queryParam.get("vehicleSalePayType") + " \n");
		}

		// 订单审批状态
		if (!StringUtils.isNullOrEmpty(queryParam.get("orderState"))) {
			conditionWhere.append(" and TVO.ORDER_STATUS = " + queryParam.get("orderState") + " \n");
		}

		// 销售大区
		if (!StringUtils.isNullOrEmpty(queryParam.get("bigOrgName"))) {
			conditionWhere.append(" and TOR.PARENT_ORG_ID in (" + queryParam.get("bigOrgName") + ") \n");
		}
		// 销售小区
		if (!StringUtils.isNullOrEmpty(queryParam.get("smallOrgName"))) {
			conditionWhere.append(" and TOR.ORG_ID in (" + queryParam.get("smallOrgName") + ") \n");
		}

		// 超过几天未付款(老系统没有)
		if (!StringUtils.isNullOrEmpty(queryParam.get("delay"))) {
			// conditionWhere.append(" and = ? \n");
			// params.add(queryParam.get("delay"));
		}
		// 订单提报日期 起始
		if (!StringUtils.isNullOrEmpty(queryParam.get("orderDateStart"))) {
			conditionWhere.append(" and TVO.DEAL_ORDER_AFFIRM_DATE>=" + "date_format('"
					+ queryParam.get("orderDateStart") + "' '23:59:59'," + "'%Y-%m-%d %H:%i:%s'" + ")" + " \n");

		}
		// 订单提报日期 结束
		if (!StringUtils.isNullOrEmpty(queryParam.get("orderDateEnd"))) {
			conditionWhere.append(" and TVO.DEAL_ORDER_AFFIRM_DATE <= " + "date_format('"
					+ queryParam.get("orderDateEnd") + "' '23:59:59'," + "'%Y-%m-%d %H:%i:%s'" + ")" + " \n");

		}
		// //指派分配日期 起始
		// if(!StringUtils.isNullOrEmpty(queryParam.get("allotDateStart"))){
		// conditionWhere.append(" and TVO.CREATE_DATE2 >=? \n");
		// params.add( "date_format('" + queryParam.get("allotDateStart") + "'
		// '23:59:59',"
		// + "'%Y-%m-%d %H:%i:%s'" + ")");
		//
		// }
		// //指派分配日期 结束
		// if(!StringUtils.isNullOrEmpty(queryParam.get("allotDateEnd"))){
		// conditionWhere.append(" and TVO.CREATE_DATE2 <=? \n");
		// params.add( "date_format('" + queryParam.get("allotDateEnd") + "'
		// '23:59:59',"
		// + "'%Y-%m-%d %H:%i:%s'" + ")");
		//
		// }
		//
		// 资源池上传日期 起始
		if (!StringUtils.isNullOrEmpty(queryParam.get("commonDateStart"))) {
			conditionWhere
					.append(" and TVO.CREATE_DATE>='" + queryParam.get("commonDateStart") + " 00:00:00'" + ")" + " \n");

		}
		// 资源池上传日期结束
		if (!StringUtils.isNullOrEmpty(queryParam.get("commonDateEnd"))) {
			conditionWhere
					.append(" and TVO.CREATE_DATE <='" + queryParam.get("commonDateEnd") + " 23:59:59'" + ")" + " \n");

		}

		StringBuilder sql = new StringBuilder();
		sql.append("    SELECT tt.MODEL_CODE,tt.GROUP_NAME,tt.ORDER_TYPE,tt.TOTAL1,tt.TOTAL2,t3.TOTAL3\n");
		sql.append("    	FROM  (SELECT t1.* ,t2.TOTAL2\n");
		sql.append(
				"                   FROM  (SELECT COUNT(*) total1,VM.GROUP_ID,VM.MODEL_CODE,VM.GROUP_NAME,TVO.ORDER_TYPE\n");
		sql.append("                              FROM TT_VS_ORDER                TVO,\n");
		sql.append("                                   TM_VEHICLE_DEC             TV,\n");
		sql.append("                                   (" + getVwMaterialSql() + ")                VM,\n");
		sql.append("                                   TM_DEALER                  TD,\n");
		sql.append("                                   TM_ORG                     TOR,\n");
		sql.append("                                   TM_DEALER_ORG_RELATION     TDOR\n");
		sql.append("                              WHERE TV.MATERIAL_ID = VM.MATERIAL_ID\n");
		sql.append("                                AND  VM.GROUP_TYPE='90081002'\n");
		sql.append("                                AND TVO.VIN = TV.VIN\n");
		sql.append("                               	AND TVO.DEALER_ID = TD.DEALER_ID\n");
		sql.append("                                AND TD.DEALER_ID = TDOR.DEALER_ID\n");
		sql.append("                                AND TOR.ORG_ID = TDOR.ORG_ID\n");
		// 屏蔽已撤单和已取消的记录
		sql.append("              					AND TVO.ORDER_STATUS<20071008\n");

		if (conditionWhere != null && !conditionWhere.equals("")) {
			sql.append("                             " + conditionWhere + "\n");
		}

		sql.append(
				"                              GROUP BY VM.GROUP_ID,VM.MODEL_CODE,VM.GROUP_NAME,TVO.ORDER_TYPE)     t1\n");
		sql.append(
				"                   LEFT JOIN (SELECT COUNT(*) TOTAL2,VM.GROUP_ID,VM.MODEL_CODE,VM.GROUP_NAME,TVO.ORDER_TYPE\n");
		sql.append("                              FROM TT_VS_ORDER               TVO,\n");
		sql.append("                                   TM_VEHICLE_DEC             TV,\n");
		sql.append("                                   (" + getVwMaterialSql() + ")                VM,\n");
		sql.append("                                   TM_DEALER                  TD,\n");
		sql.append("                                   TM_ORG                     TOR,\n");
		sql.append("                                   TM_DEALER_ORG_RELATION     TDOR\n");
		sql.append("                              WHERE TV.MATERIAL_ID = VM.MATERIAL_ID\n");
		sql.append("                                AND  VM.GROUP_TYPE='90081002'\n");
		sql.append("                                AND TVO.VIN = TV.VIN\n");
		sql.append("                                AND TVO.DEALER_ID = TD.DEALER_ID\n");
		sql.append("                                AND TD.DEALER_ID = TDOR.DEALER_ID\n");
		sql.append("                                AND TOR.ORG_ID = TDOR.ORG_ID\n");
		sql.append("                                AND TVO.ORDER_STATUS=20071004\n");

		if (conditionWhere != null && !conditionWhere.equals("")) {
			sql.append("                             " + conditionWhere + "\n");
		}

		sql.append(
				"                                GROUP BY VM.GROUP_ID,VM.MODEL_CODE,VM.GROUP_NAME,TVO.ORDER_TYPE)    t2\n");
		sql.append("                   ON t1.GROUP_ID = t2.GROUP_ID AND t1.ORDER_TYPE=t2.ORDER_TYPE)  tt\n");
		sql.append(
				"       LEFT JOIN  (SELECT COUNT(*) TOTAL3,VM.GROUP_ID,VM.MODEL_CODE,VM.GROUP_NAME,TVO.ORDER_TYPE\n");
		sql.append("                              FROM TT_VS_ORDER                TVO,\n");
		sql.append("                                   TM_VEHICLE_DEC             TV,\n");
		sql.append("                                   (" + getVwMaterialSql() + ")                VM,\n");
		sql.append("                                   TM_DEALER                  TD,\n");
		sql.append("                                   TM_ORG                     TOR,\n");
		sql.append("                                   TM_DEALER_ORG_RELATION     TDOR\n");
		sql.append("                              WHERE TV.MATERIAL_ID = VM.MATERIAL_ID\n");
		sql.append("                                AND  VM.GROUP_TYPE='90081002'\n");
		sql.append("                                AND TVO.VIN = TV.VIN\n");
		sql.append("                                AND TVO.DEALER_ID = TD.DEALER_ID\n");
		sql.append("                                AND TD.DEALER_ID = TDOR.DEALER_ID\n");
		sql.append("                                AND TOR.ORG_ID = TDOR.ORG_ID\n");
		// 屏蔽已撤单和已取消的记录
		sql.append("              				    AND TVO.ORDER_STATUS<20071008\n");
		sql.append("                                AND TV.LIFE_CYCLE=11521003\n");

		if (conditionWhere != null && !conditionWhere.equals("")) {
			sql.append("                             " + conditionWhere + "\n");
		}

		sql.append("    GROUP BY VM.GROUP_ID,VM.MODEL_CODE,VM.GROUP_NAME,TVO.ORDER_TYPE)     t3\n");
		sql.append("    ON tt.GROUP_ID = t3.GROUP_ID AND tt.ORDER_TYPE = t3.ORDER_TYPE\n");

		return sql.toString();
	}

	/**
	 * SQL组装 明细查询
	 * 
	 * @param queryParam
	 * @param params
	 * @return
	 */
	private String getDetailQuerySql(Map<String, String> queryParam, List<Object> params) {

		// 查询条件
		StringBuilder conditionWhere = new StringBuilder();
		// 品牌
		if (!StringUtils.isNullOrEmpty(queryParam.get("brandId"))) {
			conditionWhere.append(" and VM.BRAND_ID= " + queryParam.get("brandId") + " \n");
		}
		// 车系
		if (!StringUtils.isNullOrEmpty(queryParam.get("seriesId"))) {
			conditionWhere.append(" and VM.SERIES_ID= " + queryParam.get("seriesId") + " \n");
		}
		// 车款
		if (!StringUtils.isNullOrEmpty(queryParam.get("groupId"))) {
			conditionWhere.append(" and VM.GROUP_ID= " + queryParam.get("groupId") + " \n");
		}
		// 年款
		if (!StringUtils.isNullOrEmpty(queryParam.get("modelId"))) {
			conditionWhere.append(" and VM.MODEL_YEAR= " + queryParam.get("modelId") + " \n");
		}
		// 外饰
		if (!StringUtils.isNullOrEmpty(queryParam.get("colorId"))) {
			conditionWhere.append(" and VM.COLOR_CODE= '" + queryParam.get("colorId") + "' \n");
		}
		// 内饰
		if (!StringUtils.isNullOrEmpty(queryParam.get("trimId"))) {
			conditionWhere.append(" and VM.TRIM_CODE= '" + queryParam.get("trimId") + "' \n");
		}

		StringBuilder sql = new StringBuilder();
		sql.append(
				"SELECT tt.*,trr.REMARK,trr.OTHER_REMARK FROM (SELECT t.* FROM (SELECT TVO.ORDER_ID,TVO.COMMONALITY_ID,TOR.ORG_ID,TOR.PARENT_ORG_ID,\n");
		sql.append("       TOR2.ORG_DESC ORG_NAME,\n");// 销售大区
		sql.append("       TOR.ORG_DESC ORG_NAME_SMALL,\n");// 销售小区
		sql.append("	   (SELECT SWT_CODE FROM TM_COMPANY WHERE COMPANY_ID = TD.COMPANY_ID) SWT_CODE,\n");
		sql.append("	   TD.DEALER_CODE,\n");
		sql.append("	   TD.DEALER_SHORTNAME DEALER_NAME,\n");
		sql.append("       TVO.ORDER_NO,\n");// 销售订单号
		sql.append(
				"       (CASE WHEN TVO.ORDER_TYPE IN(20831001,20831002)  THEN DATE_FORMAT((SELECT CREATE_DATE FROM TT_VS_COMMON_RESOURCE_DETAIL WHERE VEHICLE_ID=TV.VEHICLE_ID AND STATUS=10011001),'%Y-%m-%d ')  ELSE '' END) CREATE_DATE,\n");
		sql.append(
				"       (CASE WHEN TVO.ORDER_TYPE IN(20831002,20831003)  THEN DATE_FORMAT(TVO.CREATE_DATE,'%Y-%m-%d ') ELSE '' END) CREATE_DATE2,\n");
		sql.append("       LEFT((DATE_FORMAT(TVO.ORDER_DATE,'%Y-%m-%d')),10) ORDER_DATE,\n");
		sql.append(
				"       (CASE WHEN TV.NODE_STATUS!=11511018  THEN DATE_FORMAT(TVO.DEAL_ORDER_AFFIRM_DATE,'%Y-%m-%d ') ELSE NULL END) DEAL_ORDER_AFFIRM_DATE,\n");
		sql.append("       TVO.ORDER_TYPE,\n");// 订单类型
		sql.append("       TVO.ORDER_STATUS,\n");// 订单审批状态
		sql.append("       TVO.VIN,\n");// VIN
		sql.append("       (SELECT COMMERCIAL_NO FROM TT_VS_FACTORY_ORDER WHERE vin = TV.vin) COMMERCIAL_NO,\n");
		sql.append("       (SELECT CERTIFICATION_NO FROM TT_VS_FACTORY_ORDER WHERE vin = TV.vin) CERTIFICATION_NO,\n");
		sql.append("       TV.NODE_STATUS,\n");// 车辆节点状态
		sql.append("       LEFT((DATE_FORMAT(TV.EXPECT_PORT_DATE,'%Y-%m-%d')),10) ETA,\n");// 预计交货期
		sql.append("       LEFT((DATE_FORMAT(TV.ORG_STORAGE_DATE,'%Y-%m-%d')),10) ORG_STORAGE_DATE,\n");// 入库日期
		sql.append("       LEFT((DATE_FORMAT(TVO.SWT_AFFIRM_DATE,'%Y-%m-%d')),10) SWT_AFFIRM_DATE,\n");// 中间收款时间

		if (!StringUtils.isNullOrEmpty(queryParam.get("delay"))) { // 超过几天未付款
			if (queryParam.get("orderType").equals(String.valueOf(OemDictCodeConstants.ORDER_TYPE_01))
					|| queryParam.get("orderType").equals(String.valueOf(OemDictCodeConstants.ORDER_TYPE_03))) {
				sql.append("       IFNULL(days(SWT_AFFIRM_DATE)- days(DEAL_ORDER_AFFIRM_DATE),0)   delay,\n");
			} else if (queryParam.get("orderType").equals(String.valueOf(OemDictCodeConstants.ORDER_TYPE_02))) {
				sql.append("       IFNULL(days(SWT_AFFIRM_DATE)- days(DELIVER_ORDER_DATE),0)   delay,\n");
			}
		}
		sql.append("       VM.MODEL_CODE,\n");// 车型代码
		sql.append("       VM.MODEL_NAME,\n");// 车型描述
		sql.append("	   VM.BRAND_CODE,\n");// 品牌
		sql.append("	   VM.SERIES_CODE,\n");// 车系
		sql.append(" 	   VM.GROUP_NAME,\n");// 物料
		sql.append(" 	   VM.MODEL_YEAR,\n");// 车型年
		sql.append("  	   VM.COLOR_NAME,\n");// 颜色
		sql.append("       VM.TRIM_NAME,\n");// 内饰描述
		sql.append("	   TVO.PAYMENT_TYPE,\n");// 车辆用途
		sql.append("	   TV.VEHICLE_USAGE,\n");// 天津VPC港口
		sql.append("	   TV.VPC_PORT\n");
		sql.append("    FROM  TT_VS_ORDER                TVO,\n");
		sql.append("          TM_DEALER                  TD,\n");
		sql.append("          TM_ORG                     TOR,\n");
		sql.append("          TM_ORG                     TOR2,\n");
		sql.append("          TM_DEALER_ORG_RELATION     TDOR,\n");
		sql.append("          TM_VEHICLE_DEC                 TV,\n");
		sql.append("          (" + getVwMaterialSql() + ")                VM\n");
		sql.append("    WHERE TVO.DEALER_ID = TD.DEALER_ID\n");
		sql.append(" 		  AND  VM.GROUP_TYPE=90081002\n");
		sql.append("          AND TD.DEALER_ID = TDOR.DEALER_ID\n");
		sql.append("      	  AND TOR.ORG_ID = TDOR.ORG_ID\n");
		sql.append("          AND TOR.PARENT_ORG_ID = TOR2.ORG_ID\n");

		// 测试数据，减少记录
		// sql.append(" AND TVO.ORDER_ID in
		// (2016010823081061,2015121022609401,2015121022609405) "); //测试数据，减少记录

		sql.append("      	  AND TVO.VIN = TV.VIN\n");
		sql.append("      	  AND TV.MATERIAL_ID = VM.MATERIAL_ID\n");
		// 屏蔽已撤单和已取消的记录
		sql.append("          AND TVO.ORDER_STATUS<20071008\n");
		// 品牌...条件
		if (conditionWhere != null && !conditionWhere.equals("")) {
			sql.append("                             " + conditionWhere + "\n");
		}

		sql.append("UNION ALL\n");
		sql.append("SELECT t1.ORDER_ID,\n");
		sql.append("    t1.COMMONALITY_ID,\n");
		sql.append("    t1.ORG_ID,\n");
		sql.append("    t1.PARENT_ORG_ID,\n");
		sql.append("    t1.ORG_NAME,\n");
		sql.append("    t1.ORG_NAME_SMALL,\n");
		sql.append("    t1.SWT_CODE,\n");
		sql.append("    (CASE WHEN IFNULL(t2.ORDER_ID,0)>0 THEN t2.DEALER_CODE END) DEALER_CODE,\n");
		sql.append("    (CASE WHEN IFNULL(t2.ORDER_ID,0)>0 THEN t2.DEALER_NAME  END) DEALER_NAME,\n");
		sql.append("    t1.ORDER_NO,\n");
		sql.append("    t1.CREATE_DATE,\n");
		sql.append("    t1.CREATE_DATE2,\n");
		sql.append("    t1.ORDER_DATE,\n");
		sql.append("  	t1.DEAL_ORDER_AFFIRM_DATE,\n");
		sql.append("   	t1.ORDER_TYPE,\n");
		sql.append("    t1.ORDER_STATUS,\n");
		sql.append("  	t1.VIN,\n");
		sql.append("  	t1.COMMERCIAL_NO,\n");
		sql.append("    t1.CERTIFICATION_NO,\n");
		sql.append("   	t1.NODE_STATUS,       t1.ETA,\n");
		sql.append("  	t1.ORG_STORAGE_DATE,\n");
		sql.append("    t1.SWT_AFFIRM_DATE,\n");
		sql.append("    t1.MODEL_CODE,\n");
		sql.append("    t1.MODEL_NAME,\n");
		sql.append("    t1.BRAND_CODE,\n");
		sql.append("    t1.SERIES_CODE,\n");
		sql.append("    t1.GROUP_NAME,\n");
		sql.append("    t1.MODEL_YEAR,\n");
		sql.append("    t1.COLOR_NAME,\n");
		sql.append("    t1.TRIM_NAME,\n");
		sql.append("    t1.PAYMENT_TYPE,\n");
		sql.append("    t1.VEHICLE_USAGE,\n");
		sql.append("	t1.VPC_PORT\n"); // 天津VPC港口
		sql.append("FROM (SELECT DISTINCT '-1'AS ORDER_ID,'-1'AS COMMONALITY_ID,'-1'AS ORG_ID,\n");
		sql.append("	TVCR.RESOURCE_SCOPE PARENT_ORG_ID,\n");
		sql.append(
				"  	(SELECT t.ORG_DESC FROM TM_ORG t WHERE t.ORG_ID=TVCR.RESOURCE_SCOPE AND t.DUTY_TYPE=10431003) ORG_NAME,\n");
		sql.append("  	''AS ORG_NAME_SMALL,\n");
		sql.append("   	''AS SWT_CODE,\n");
		sql.append("    ''AS DEALER_CODE,\n");
		sql.append("   	''AS DEALER_NAME,\n");
		sql.append("    ''AS ORDER_NO,\n");
		sql.append("    DATE_FORMAT(TVCRD.CREATE_DATE,'%Y-%m-%d ') CREATE_DATE,\n");// 资源池上传日期
		sql.append("    NULL AS CREATE_DATE2,\n");// 指派分配日期
		sql.append("   	NULL AS ORDER_DATE,\n");
		sql.append("   	NULL AS DEAL_ORDER_AFFIRM_DATE,\n");
		sql.append(
				"   	(CASE WHEN TVCR.TYPE=20811002 THEN 20831001 WHEN TVCR.TYPE=20811001 THEN 20831002 END) ORDER_TYPE,\n");
		sql.append("   	0 AS ORDER_STATUS,\n");
		sql.append("   	TV.VIN,\n");
		sql.append("   	(SELECT COMMERCIAL_NO FROM TT_VS_FACTORY_ORDER WHERE vin = TV.vin) COMMERCIAL_NO,\n");
		sql.append("   	(SELECT CERTIFICATION_NO FROM TT_VS_FACTORY_ORDER WHERE vin = TV.vin) CERTIFICATION_NO,\n");
		sql.append("   	TV.NODE_STATUS,       LEFT(CHAR(TIMESTAMP(TV.EXPECT_PORT_DATE)),10) ETA,\n");
		sql.append("   	 LEFT(CHAR(TIMESTAMP(TV.ORG_STORAGE_DATE)),10) ORG_STORAGE_DATE,\n");
		sql.append(" 	''AS SWT_AFFIRM_DATE,\n");
		if (!StringUtils.isNullOrEmpty(queryParam.get("delay"))) {
			sql.append(" 	0 as delay,\n");
		}
		sql.append("    VM.MODEL_CODE,\n");// 车型代码
		sql.append("    VM.MODEL_NAME,\n");
		sql.append("	VM.BRAND_CODE,\n");// 品牌
		sql.append("	VM.SERIES_CODE,\n");
		sql.append(" 	VM.GROUP_NAME,\n");
		sql.append(" 	VM.MODEL_YEAR,\n");// 车型年
		sql.append("  	VM.COLOR_NAME,\n");
		sql.append("    VM.TRIM_NAME,\n");
		sql.append("	0 AS PAYMENT_TYPE,\n");
		sql.append("	TV.VEHICLE_USAGE,\n");
		sql.append("	TV.VPC_PORT\n");// 天津VPC港口
		sql.append("   FROM  TM_VEHICLE_DEC                 	   TV,\n");
		sql.append("  		 (" + getVwMaterialSql() + ")                	   VM,\n");
		sql.append("  		 TT_VS_COMMON_RESOURCE      	   TVCR,\n");
		sql.append("   		 TT_VS_COMMON_RESOURCE_DETAIL      TVCRD\n");
		sql.append("    WHERE  TV.MATERIAL_ID = VM.MATERIAL_ID\n");
		sql.append(" 	  AND  VM.GROUP_TYPE=90081002\n");
		sql.append("   	  AND  TVCR.VEHICLE_ID = TV.VEHICLE_ID\n");
		sql.append("  	  AND  TVCR.COMMON_ID = TVCRD.COMMON_ID\n");
		sql.append("      AND  TVCRD.STATUS=10011001\n");
		// 品牌...条件
		if (conditionWhere != null && !conditionWhere.equals("")) {
			sql.append("                             " + conditionWhere + "\n");
		}
		sql.append("      AND NOT EXISTS (SELECT 1 FROM TT_VS_ORDER WHERE vin=TV.VIN  AND ORDER_STATUS<20071008)\n");
		sql.append("  	  AND  TVCR.STATUS=20801002) t1\n");
		sql.append(
				"    LEFT JOIN (SELECT TVO.VIN,TVO.ORDER_ID,TD.DEALER_CODE,TD.DEALER_SHORTNAME DEALER_NAME FROM TT_VS_ORDER TVO,TM_DEALER TD WHERE TVO.DEALER_ID = TD.DEALER_ID  AND TVO.ORDER_STATUS<20071008) t2\n");
		sql.append("    ON t1.VIN=t2.VIN\n");
		// 连接指派的订单
		sql.append("UNION ALL\n");
		sql.append("SELECT DISTINCT '-1'AS ORDER_ID,'-1'AS COMMONALITY_ID,\n");
		sql.append(
				"	 IFNULL((SELECT t1.ORG_ID FROM TM_ORG t1,TM_ORG t2 WHERE t1.PARENT_ORG_ID=t2.ORG_ID AND t1.ORG_ID=TV.ORG_ID AND t1.DUTY_TYPE=10431004),-1) ORG_ID,\n");
		sql.append("  	 (CASE WHEN (SELECT DUTY_TYPE FROM TM_ORG WHERE ORG_ID=TV.ORG_ID)=10431003\n");
		sql.append(
				"			    THEN (SELECT t.ORG_ID FROM TM_ORG t WHERE t.ORG_ID=TV.ORG_ID AND t.DUTY_TYPE=10431003)\n");
		sql.append("	       WHEN (SELECT DUTY_TYPE FROM TM_ORG WHERE ORG_ID=TV.ORG_ID)=10431004\n");
		sql.append(
				"				THEN (SELECT t1.PARENT_ORG_ID FROM TM_ORG t1,TM_ORG t2 WHERE t1.PARENT_ORG_ID=t2.ORG_ID AND t1.ORG_ID=TV.ORG_ID AND t1.DUTY_TYPE=10431004) \n");
		sql.append("	 END)   PARENT_ORG_ID,\n");
		sql.append("  	 (CASE WHEN (SELECT DUTY_TYPE FROM TM_ORG WHERE ORG_ID=TV.ORG_ID)=10431003\n");
		sql.append(
				"			    THEN (SELECT t.ORG_DESC FROM TM_ORG t WHERE t.ORG_ID=TV.ORG_ID AND t.DUTY_TYPE=10431003)\n");
		sql.append("	       WHEN (SELECT DUTY_TYPE FROM TM_ORG WHERE ORG_ID=TV.ORG_ID)=10431004\n");
		sql.append(
				"				THEN (SELECT t2.ORG_DESC FROM TM_ORG t1,TM_ORG t2 WHERE t1.PARENT_ORG_ID=t2.ORG_ID AND t1.ORG_ID=TV.ORG_ID AND t1.DUTY_TYPE=10431004) \n");
		sql.append("	 END)   ORG_NAME,\n");
		sql.append(
				"	(SELECT t.ORG_DESC FROM TM_ORG t WHERE t.ORG_ID=TV.ORG_ID AND t.DUTY_TYPE=10431004) ORG_NAME_SMALL,\n");
		sql.append("   	''AS SWT_CODE,\n");
		sql.append("    ''AS DEALER_CODE,\n");
		sql.append("   	''AS DEALER_NAME,\n");
		sql.append("    TOR.ORDER_NO,\n");
		sql.append("    NULL AS CREATE_DATE,\n");// 资源池上传日期
		sql.append(
				"    (CASE WHEN TOR.ORDER_TYPE IN(20831002,20831003)  THEN DATE_FORMAT(CHAR(TIMESTAMP(TOR.CREATE_DATE)),'%Y-%m-%d ') ELSE '' END) CREATE_DATE2,\n");
		sql.append("   	DATE_FORMAT(TOR.ORDER_DATE,'%Y-%m-%d') ORDER_DATE,\n");
		sql.append(
				"    (CASE WHEN TV.NODE_STATUS!=11511018  THEN DATE_FORMAT(TOR.DEAL_ORDER_AFFIRM_DATE,'%Y-%m-%d ') ELSE NULL END) DEAL_ORDER_AFFIRM_DATE,\n");
		sql.append("   	TOR.ORDER_TYPE,\n");
		sql.append("   	IFNULL(TOR.ORDER_STATUS,0) ORDER_STATUS,\n");
		sql.append("   	TV.VIN,\n");
		sql.append("   	(SELECT COMMERCIAL_NO FROM TT_VS_FACTORY_ORDER WHERE vin = TV.vin) COMMERCIAL_NO,\n");
		sql.append("   	(SELECT CERTIFICATION_NO FROM TT_VS_FACTORY_ORDER WHERE vin = TV.vin) CERTIFICATION_NO,\n");
		sql.append("   	TV.NODE_STATUS,       LEFT(CHAR(TIMESTAMP(TV.EXPECT_PORT_DATE)),10) ETA,\n");
		sql.append("   	LEFT(CHAR(TIMESTAMP(TV.ORG_STORAGE_DATE)),10) ORG_STORAGE_DATE,\n");
		sql.append(" 	NULL AS SWT_AFFIRM_DATE,\n");

		if (!StringUtils.isNullOrEmpty(queryParam.get("delay"))) {
			sql.append(" 	0 as delay,\n");
		}

		sql.append("    VM.MODEL_CODE,\n");// 车型代码
		sql.append("    VM.GROUP_NAME,\n");// 车型描述
		sql.append("	VM.BRAND_CODE,\n");// 品牌
		sql.append("	VM.SERIES_CODE,\n");// 车系
		sql.append(" 	VM.GROUP_NAME,\n");// 物料
		sql.append(" 	VM.MODEL_YEAR,\n");
		sql.append("  	VM.COLOR_NAME,\n");
		sql.append("    VM.TRIM_NAME,\n");
		sql.append("	0 AS PAYMENT_TYPE,\n");
		sql.append("	TV.VEHICLE_USAGE,\n");
		sql.append("	TV.VPC_PORT\n");// 天津VPC港口
		sql.append("   FROM  TM_VEHICLE_DEC                 	   TV,\n");
		sql.append("  		 (" + getVwMaterialSql() + ")                	   VM,\n");
		sql.append("  		 TT_VS_ORDER                       TOR\n");
		sql.append("    WHERE  TV.MATERIAL_ID = VM.MATERIAL_ID\n");
		sql.append(" 	  AND  VM.GROUP_TYPE=90081002\n");
		sql.append("      AND  TV.VIN = TOR.VIN\n");
		sql.append("      AND  TOR.ORDER_TYPE=20831003\n");
		sql.append("      AND  TOR.ORDER_STATUS<20071008\n");
		// 品牌...条件
		if (conditionWhere != null && !conditionWhere.equals("")) {
			sql.append("                             " + conditionWhere + "\n");
		}
		sql.append(
				"      AND  NOT EXISTS (SELECT 1 FROM TT_VS_COMMON_RESOURCE WHERE VEHICLE_ID=TV.VEHICLE_ID AND STATUS=20801002))   t\n");

		sql.append("  ORDER BY t.ORG_NAME,t.ORG_NAME,t.DEALER_CODE ) tt\n");
		sql.append("  LEFT JOIN TT_RESOURCE_REMARK trr\n");
		sql.append("  ON tt.VIN=trr.VIN  WHERE 1=1\n");
		/*
		 * //超过几天未付款 if(!StringUtils.isNullOrEmpty(queryParam.get("delay"))){
		 * sql.append("   and tt.delay>="+queryParam.get("delay")+"\n"); }
		 */

		// 过滤ORDER_ID=-1的数据
		sql.append("    AND tt.order_ID != -1  ");

		// 经销商
		if (!StringUtils.isNullOrEmpty(queryParam.get("dealerCode"))) {
			sql.append(" and tt.DEALER_CODE in (" + queryParam.get("dealerCode") + ") \n");
		}
		// VIN
		if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
			sql.append(" and tt.VIN like ? \n");
			params.add("%" + queryParam.get("vin") + "%");
		}
		// 销售大区
		if (!StringUtils.isNullOrEmpty(queryParam.get("bigOrgName"))) {
			sql.append(" and tt.PARENT_ORG_ID in (" + queryParam.get("bigOrgName") + ") \n");
		}
		// 销售小区
		if (!StringUtils.isNullOrEmpty(queryParam.get("smallOrgName"))) {
			sql.append(" and tt.ORG_ID in (" + queryParam.get("smallOrgName") + ") \n");
		}
		// 车辆节点
		if (!StringUtils.isNullOrEmpty(queryParam.get("vehicleNode"))) {
			sql.append(" and tt.NODE_STATUS = " + queryParam.get("vehicleNode") + " \n");
		}
		// 订单类型
		if (!StringUtils.isNullOrEmpty(queryParam.get("orderType"))) {
			sql.append(" and tt.ORDER_TYPE = " + queryParam.get("orderType") + " \n");
		}
		// 付款方式
		if (!StringUtils.isNullOrEmpty(queryParam.get("vehicleSalePayType"))) {
			sql.append(" and tt.PAYMENT_TYPE = " + queryParam.get("vehicleSalePayType") + " \n");
		}

		// 订单审批状态
		if (!StringUtils.isNullOrEmpty(queryParam.get("orderState"))) {
			sql.append(" and tt.ORDER_STATUS = " + queryParam.get("orderState") + " \n");
		}
		// 订单提报日期 起始
		if (!StringUtils.isNullOrEmpty(queryParam.get("orderDateStart"))) {
			sql.append(" and tt.DEAL_ORDER_AFFIRM_DATE>='" + queryParam.get("orderDateStart") + " 00:00:00'" + " \n");
		}
		// 订单提报日期 结束
		if (!StringUtils.isNullOrEmpty(queryParam.get("orderDateEnd"))) {
			sql.append(" and tt.DEAL_ORDER_AFFIRM_DATE <='" + queryParam.get("orderDateEnd") + " 23:59:59'" + " \n");

		}
		// 指派分配日期 起始
		if (!StringUtils.isNullOrEmpty(queryParam.get("allotDateStart"))) {
			sql.append(" and tt.CREATE_DATE2 >='" + queryParam.get("allotDateStart") + " 00:00:00'" + " \n");
			// params.add("'" + queryParam.get("allotDateStart") + "
			// 23:59:59'");

		}
		// 指派分配日期 结束
		if (!StringUtils.isNullOrEmpty(queryParam.get("allotDateEnd"))) {
			sql.append(" and tt.CREATE_DATE2 <='" + queryParam.get("allotDateEnd") + " 23:59:59'" + "\n");
			// params.add("'" + queryParam.get("allotDateEnd") + " 23:59:59'");

		}

		// 资源池上传日期 起始
		if (!StringUtils.isNullOrEmpty(queryParam.get("commonDateStart"))) {
			sql.append(" and tt.CREATE_DATE>='" + queryParam.get("commonDateStart") + " 00:00:00'" + " \n");

		}
		// 资源池上传日期结束
		if (!StringUtils.isNullOrEmpty(queryParam.get("commonDateEnd"))) {
			sql.append(" and tt.CREATE_DATE <='" + queryParam.get("commonDateEnd") + "23:59:59'" + " \n");

		}
		System.out.println(sql.toString());
		return sql.toString();
	}

}
