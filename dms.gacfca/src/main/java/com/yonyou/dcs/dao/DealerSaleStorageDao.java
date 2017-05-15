package com.yonyou.dcs.dao;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.common.OemDictCodeConstants;
/**
 * 
* @ClassName: DealerSaleStorageDao 
* @Description: 车辆验收上报（DMS验收后，上报验收明细给上端）
* @author zhengzengliang 
* @date 2017年4月12日 上午10:30:37 
*
 */
@Repository
public class DealerSaleStorageDao extends OemBaseDAO{
	
	/**
	 * 插入国产车验收接口表
	 * @param dealerCode
	 * @param userId
	 * @param vin
	 * @throws Exception
	 */
	public void insertTiK4VsNvdr(String arriveDate, String dealerCode, Long userId, String vin)
			throws Exception {
		
		StringBuffer sql = new StringBuffer();
		sql.append(" \n");
		sql.append("INSERT INTO TI_K4_VS_NVDR \n");
		sql.append("  (IF_ID, ACTION_CODE, ACTION_DATE, ACTION_TIME, INSPECTION_DATE, VIN, DEALER_CODE, VEHICLE_USE, ROW_ID, IS_RESULT, CREATE_BY, CREATE_DATE, IS_DEL) \n");
		sql.append("SELECT F_GETID(), -- 主键ID \n");
		sql.append("       'ZPOD', -- 交易代码 \n");
		sql.append("       TO_CHAR(CURRENT TIMESTAMP, 'YYYYMMDD') AS ACTION_DATE, -- 交易日期 \n");
		sql.append("       TO_CHAR(CURRENT TIMESTAMP, 'HHMMSS') AS ACTION_TIME, -- 交易时间 \n");
		sql.append("       '" + arriveDate + "' AS INSPECTION_DATE, -- 验收日期 \n");
		sql.append("       V.VIN, -- 车架号 \n");
		sql.append("       '" + dealerCode + "' AS DEALER_CODE, -- 经销商代码 \n");
		sql.append("       DECODE(NVL(R.RELATION_CODE, '-1'), '-1', '68', R.RELATION_CODE) AS VEHICLE_USE, -- 车辆用途 \n");
		sql.append("       'S0008' || TO_CHAR(CURRENT TIMESTAMP, 'YY') || TO_CHAR(CURRENT TIMESTAMP, 'MM') || SEQ_TI_K4_VS_NVDR.NEXTVAL AS ROW_ID, \n");
		sql.append("       '" + OemDictCodeConstants.IF_TYPE_NO + "' AS IS_RESULT, -- 是否上报 \n");
		sql.append("       '" + userId + "' AS CREATE_BY, -- 创建人ID \n");
		sql.append("       CURRENT TIMESTAMP AS CREATE_DATE, -- 创建日期 \n");
		sql.append("       '" + OemDictCodeConstants.IS_DEL_00 + "' AS IS_DEL \n");
		sql.append("  FROM TM_VEHICLE_DEC V \n");
		sql.append(" INNER JOIN VW_MATERIAL M ON M.MATERIAL_ID = V.MATERIAL_ID \n");
		sql.append("  LEFT JOIN TC_RELATION R ON CHAR(R.CODE_ID) = V.VEHICLE_USAGE \n");
		sql.append(" WHERE M.GROUP_TYPE = '" + OemDictCodeConstants.GROUP_TYPE_DOMESTIC + "' \n");
		sql.append("   AND V.VIN = '" + vin + "' \n");
		
		OemDAOUtil.execBatchPreparement(sql.toString(), null);
	}

}
