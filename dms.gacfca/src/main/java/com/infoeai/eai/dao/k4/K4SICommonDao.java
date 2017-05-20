package com.infoeai.eai.dao.k4;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.yonyou.dms.common.Util.CheckUtil;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.common.OemDictCodeConstants;

/**
 * K4 接口公共 DAO
 */
@Repository
@SuppressWarnings("rawtypes")
public class K4SICommonDao extends OemBaseDAO {

	public static Logger logger = LoggerFactory.getLogger(K4SICommonDao.class);

	/**
	 * 车辆节点状态变更表数据插入
	 * 
	 * @param params
	 * @throws Exception
	 */
	public void insertVehicleChange(Map<String, Object> params) throws Exception {

		StringBuffer sql = new StringBuffer();
		sql.append(" \n");
		sql.append("INSERT INTO TT_VS_VHCL_CHNG ( \n");
//		sql.append("  (VHCL_CHANGE_ID, \n");
		sql.append("   VEHICLE_ID, \n");
		sql.append("   CHANGE_CODE, \n");
		sql.append("   CHANGE_NAME, \n");
		sql.append("   CHANGE_DATE, \n");
		sql.append("   CHANGE_DESC, \n");
		sql.append("   CHANGE_MEMO, \n");
		sql.append("   CREATE_BY, \n");
		sql.append("   CREATE_DATE, \n");
		sql.append("   VER, \n");
		sql.append("   IS_DEL, \n");
		sql.append("   IS_ARC, \n");
		sql.append("   RESOURCE_TYPE, \n");
		sql.append("   RESOURCE_ID, \n");
		sql.append("   MAIN_STATUS, \n");
		sql.append("   SECOND_STATUS, \n");
		sql.append("   MAIN_STATUS_DESC, \n");
		sql.append("   SECOND_STATUS_DESC, \n");
		sql.append("   BLOCK_REASON, \n");
		sql.append("   EXPECTED_UNBLOCK_DATE) VALUES ( \n");
//		sql.append(" F_GETID(), -- 节点变更ID \n");
		sql.append(" (SELECT VEHICLE_ID FROM TM_VEHICLE_dec WHERE VIN = '" + params.get("vin") + "'), -- 车辆ID \n");
		sql.append(" '" + params.get("changeCode") + "', -- 车辆节点状态 \n");
		sql.append(" '" + params.get("changeName") + "', -- 车辆节点名称 \n");

		// 节点变更时间
		if (null != params.get("changeDate")) {
			sql.append(" DATE_FORMAT ('" + params.get("changeDate") + "', '%Y-%m-%d %H:%i:%S'), -- 节点变更时间 \n");
		} else {
			sql.append(" NULL, -- 节点变更时间 \n");
		}

		sql.append(" '" + params.get("changeDesc") + "', -- 车辆节点描述 \n");
		sql.append(" NULL, -- 备忘录 \n");
		sql.append(" '" + params.get("createBy") + "', -- 创建人ID \n");
		sql.append(" now(), -- 创建时间 \n");
		sql.append(" '0', -- 版本控制 \n");
		sql.append(" '0', -- 归档标志 \n");
		sql.append(" '0', -- 逻辑删除 \n");
		sql.append(" NULL, -- 资源类型 \n");
		sql.append(" NULL, -- 资源ID \n");

		// 主要状态
		if (null != params.get("mainStatusCode")) {
			sql.append(" '" + params.get("mainStatusCode") + "', -- 主要状态 \n");
		} else {
			sql.append(" NULL, -- 主要状态 \n");
		}

		// 主要状态描述
		if (null != params.get("mainStatusDesc")) {
			sql.append(" '" + params.get("mainStatusDesc") + "', -- 主要状态描述 \n");
		} else {
			sql.append(" NULL, -- 主要状态描述 \n");
		}

		// 第二状态
		if (null != params.get("secondStatusCode")) {
			sql.append(" '" + params.get("secondStatusCode") + "', -- 第二状态 \n");
		} else {
			sql.append(" NULL, -- 第二状态 \n");
		}

		// 第二状态描述
		if (null != params.get("secondStatusDesc")) {
			sql.append(" '" + params.get("secondStatusDesc") + "' -- 第二状态描述 \n");
		} else {
			sql.append(" NULL, -- 第二状态描述 \n");
		}

		// 冻结原因
		if (null != params.get("blockReason")) {
			sql.append(" '" + params.get("blockReason") + "', -- 冻结原因 \n");
		} else {
			sql.append(" NULL, -- 冻结原因 \n");
		}

		// 预计解冻日期
		if (null != params.get("expectedUnblockDate")) {
			sql.append(" '" + params.get("expectedUnblockDate") + "' -- 预计解冻日期 \n");
		} else {
			sql.append(" NULL -- 预计解冻日期 \n");
		}

		sql.append(") \n");

		OemDAOUtil.execBatchPreparement(sql.toString(), new ArrayList<Object>());

	}

	/**
	 * 通过车架号查询车辆信息（车辆ID、车架号、节点日期）
	 * 
	 * @param vin
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> vehicleInfoByVin(String vin) throws Exception {

		StringBuffer sql = new StringBuffer();
		sql.append(" \n");
		sql.append("SELECT V.VEHICLE_ID, -- 车辆ID \n");
		sql.append("       V.VIN, -- 车架号 \n");
		sql.append("       DATE_FORMAT(V.NODE_DATE, '%Y%m%d%H%i%S') AS NODEDATE -- 节点日期 \n");
		sql.append("  FROM TM_VEHICLE_dec V \n");
		sql.append(" WHERE V.VIN = '" + vin + "' \n");
		sql.append("   AND V.IS_DEL = '" + OemDictCodeConstants.IS_DEL_00 + "' \n");

		Map map = OemDAOUtil.findFirst(sql.toString(), null);

		return map;
	}

	/**
	 * 查询全部主要状态
	 * 
	 * @param type
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> codeMappingQuery(String type, String code) {

		StringBuffer sql = new StringBuffer();
		sql.append(" \n");
		sql.append("SELECT C.CODE_ID AS NO, -- 编号 \n");
		sql.append("       M.SAP_CODE AS CODE, -- 代码 \n");
		sql.append("       M.CODE_DESC AS DESC -- 描述 \n");
		sql.append("  FROM TC_CODE C \n");
		sql.append(" INNER JOIN TC_CODE_K4_MAPPING M ON M.CODE_ID = C.CODE_ID \n");
		sql.append(" WHERE C.TYPE = '" + type + "' \n");

		// 如果 code 不为 null 则增加 code 查询条件
		if (!CheckUtil.checkNull(code)) {
			sql.append("   AND M.SAP_CODE = '" + code + "' \n");
		}

		Map map = OemDAOUtil.findFirst(sql.toString(), null);

		return map;

	}

	/**
	 * 更新S0003接口失败的订单
	 * 
	 * @param type
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public void updateOrder(String reason, String orderNo) {

		StringBuffer sql = new StringBuffer();
		sql.append(" \n");
		sql.append("UPDATE TT_VS_ORDER \n");
		sql.append("   SET SO_CR_FAILURE_REASON = '" + reason + "', \n");
		sql.append("       SO_CR_FAILURE_DATE = now(), \n");
		sql.append("       IS_SEND = '" + OemDictCodeConstants.IF_TYPE_NO + "', \n");
		sql.append("       ALLOT_VEHICLE_DATE = NULL, \n");
		sql.append("       UPDATE_BY = '" + OemDictCodeConstants.K4_S0003 + "', \n");
		sql.append("       UPDATE_DATE = now() \n");
		sql.append(" WHERE ORDER_NO = '" + orderNo + "' \n");

		OemDAOUtil.execBatchPreparement(sql.toString(), new ArrayList<Object>());

	}

	/**
	 * 根据VIN获取不是菲亚特的车辆
	 * 
	 * @param vin
	 */
	public Map<String, Object> getNotFiatVehicle(String vin) {

		StringBuffer sql = new StringBuffer();
		sql.append(" \n");
		sql.append("SELECT V.VEHICLE_ID -- 车辆ID \n");
		sql.append("  FROM TM_VEHICLE V \n");
		sql.append(
				" WHERE NOT EXISTS (SELECT * FROM TMP_SDS_MATERIAL_MAPPING SMM WHERE SMM.MATERIAL_ID = V.MATERIAL_ID) \n");
		sql.append("   AND V.VIN = '" + vin + "' \n");
		sql.append("  WITH UR \n");

		Map map = OemDAOUtil.findFirst(sql.toString(), null);

		return map;

	}

	/**
	 * 根据国产车SAP系统经销商code取DCS系统dealerId
	 * 
	 * @param fcaCode
	 * @return
	 */
	public Map<String, Object> getDealerByFCACode(String fcaCode) {

		StringBuffer sql = new StringBuffer();
		sql.append(" \n");
		sql.append("SELECT D.DEALER_ID, -- 经销商ID \n");
		sql.append("       D.DEALER_CODE, -- 经销商代码 \n");
		sql.append("       D.DEALER_NAME -- 经销商名称 \n");
		sql.append("  FROM TM_COMPANY C, TM_DEALER D \n");
		sql.append(" WHERE C.COMPANY_ID = D.COMPANY_ID \n");
		sql.append("   AND D.DEALER_TYPE = '" + OemDictCodeConstants.DEALER_TYPE_DVS + "' \n");
		sql.append("   AND C.FCA_CODE = '" + fcaCode + "' \n");
		Map map = OemDAOUtil.findFirst(sql.toString(), null);
		if (map == null || map.size() == 0) {
			return map;
		} else {
			return map;
		}
	}

}
