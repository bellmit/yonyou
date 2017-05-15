/**
 * 
 */
package com.infoeai.eai.dao.bsuv.dms;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.common.OemDictCodeConstants;

/**
 * @author Administrator
 *
 */
@Repository
public class BSUVDCSTODMSDAO extends OemBaseDAO{
	/**
	 * 查询SEND_DMS状态为0和2的数据
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> queryTiEcHitSingle() {
		StringBuffer sql = new StringBuffer();
		sql.append(" \n");
		sql.append("SELECT ID, -- 主键ID \n");
		sql.append("       DEALER_NAME, -- 经销商名称 \n");
		sql.append("       ENTITY_CODE, -- DMS端经销商代码 \n");
		sql.append("       TEL -- 客户联系电话 \n");
		sql.append("  FROM TI_EC_HIT_SINGLE_DCS \n");
		sql.append(" WHERE (ENTITY_CODE IS NOT NULL OR ENTITY_CODE != '') \n");
		sql.append("   AND (TEL IS NOT NULL OR TEL <> 0) \n");
		sql.append("   AND SEND_DMS IN (0, 2) \n");
		//List<TiEcHitSinglePO> list = factory.select(sql.toString(), null, new POCallBack<TiEcHitSinglePO>(factory, TiEcHitSinglePO.class));
		List<Map> list = OemDAOUtil.findAll(sql.toString(), null);
		
		return list;
	}
	
	/**
	 * 获取待发送或需重新发送的官网潜客信息
	 */
	public List<Map> getPotentialCustomerList() {
		
		StringBuffer sql = new StringBuffer();
		sql.append(" \n");
		sql.append("SELECT EC_ORDER_NO, -- 官网订单号 \n");
		sql.append("       ID, -- 官网潜客主键ID \n");
		sql.append("       DEALER_NAME, -- 经销商名称 \n");
		sql.append("       ENTITY_CODE, -- DMS经销商代码 \n");
		sql.append("       CUSTOMER_NAME, -- 客户名称 \n");
		sql.append("       ID_CRAD, -- 身份证 \n");
		sql.append("       TEL, -- 客户联系电话 \n");
		sql.append("       BRAND_CODE, -- 意向品牌 \n");
		sql.append("       SERIES_CODE, -- 意向车系 \n");
		sql.append("       MODEL_CODE, -- 意向车型 \n");
		sql.append("       GROUP_CODE, -- 意向车款 \n");
		sql.append("       MODEL_YEAR, -- 年款 \n");
		sql.append("       COLOR_CODE, -- 颜色 \n");
		sql.append("       TRIM_CODE, -- 内饰 \n");
		sql.append("       RETAIL_FINANCIAL, -- 零售金融 \n");
		sql.append("       DEPOSIT_AMOUNT, -- 定金金额 \n");
		sql.append("       DEPOSIT_DATE, -- 下定日期 \n");
		sql.append("       OPERATION_FLAG, -- 操作标识 \n");
		sql.append("       EC_ORDER_TYPE -- 官网订单类型 \n");
		sql.append("  FROM TI_EC_POTENTIAL_CUSTOMER_DCS \n");
		sql.append(" WHERE SEND_DMS IN (0, 2) \n");
		sql.append("   AND (RESULT = '" + OemDictCodeConstants.IF_TYPE_YES + "' OR RESULT IS NULL) \n");
		sql.append("   AND ENTITY_CODE IS NOT NULL \n");
		sql.append("   AND ENTITY_CODE <> '' \n");
		sql.append("   AND TEL IS NOT NULL \n");
		sql.append("   AND TEL <> '' \n");
		
		
		List<Map> list = OemDAOUtil.findAll(sql.toString(), null);		
		return list;
		
	}
	
	/**
	 * 获取待发送或需重新发送的官网撤单信息
	 */
	public List<Map> getRevokeOrderList() {
		
		StringBuffer sql = new StringBuffer();
		sql.append(" \n");
		sql.append("SELECT ID, -- 官网撤单主键ID \n");
		sql.append("       EC_ORDER_NO, -- 官网订单号 \n");
		sql.append("       DEALER_CODE, -- DCS端经销商代码 \n");
		sql.append("       ENTITY_CODE, -- DMS端经销商代码\n");
		sql.append("       REVOKE_DATE, -- 撤单日期 \n");
		sql.append("       REVOKE_TYPE -- 官网订单状态 1：撤单、2：逾期 \n");
		sql.append("  FROM TI_EC_REVOKE_ORDER_DCS \n");
		sql.append(" WHERE SEND_DMS IN (0, 2) \n");
		sql.append("   AND (RESULT = '" + OemDictCodeConstants.IF_TYPE_YES + "' OR RESULT IS NULL) \n");
		sql.append("   AND ENTITY_CODE IS NOT NULL \n");
//		sql.append("   AND REVOKE_TYPE IN(1, 2) \n");
		sql.append("   AND ENTITY_CODE <> '' \n");
		
		List<Map> list = OemDAOUtil.findAll(sql.toString(), null);		
		return list;
		
	}
	
	
	
}
