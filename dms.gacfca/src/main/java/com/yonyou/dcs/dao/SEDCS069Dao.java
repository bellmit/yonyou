package com.yonyou.dcs.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dcs.util.DEConstant;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.common.OemDictCodeConstants;

/**
 * 呆滞件下架or变更数量上报接口
 * @author luoyang
 *
 */
@Repository
public class SEDCS069Dao extends OemBaseDAO {

	/**
	 * 根据经销商+配件代码+仓库查询总的可申请数
	 * @param dealerCode
	 * @param partNo
	 * @param storageCode
	 * @return
	 */
	public List<Map> queryCount(String dealerCode, String partNo, String storageCode) {
		StringBuffer sql = new StringBuffer();
		sql.append("	select sum(IFNULL(APPLY_NUMBER, '0')) as APPLY_NUMBER from TT_OBSOLETE_MATERIAL_RELEASE where STATUS = "+OemDictCodeConstants.PART_OBSOLETE_RELESE_STATUS_01+"	\n");
		sql.append("			AND IS_DEL <> '1'  	\n");
		sql.append("			AND DEALER_CODE = '"+dealerCode+"'  	\n");
		sql.append("			AND PART_CODE = '"+partNo+"'	\n");
		sql.append("			AND WAREHOUSE = '"+storageCode+"' \n");
		return OemDAOUtil.findAll(sql.toString(), null);
	}

	/**
	 * 根据经销商+配件代码+仓库查询发布信息
	 * @param dealerCode
	 * @param partNo
	 * @param storageCode
	 * @return
	 */
	public List<Map> queryPo(String dealerCode, String partNo, String storageCode) {
		StringBuffer sql = new StringBuffer();
		sql.append("	SELECT * FROM TT_OBSOLETE_MATERIAL_RELEASE WHERE STATUS = "+OemDictCodeConstants.PART_OBSOLETE_RELESE_STATUS_01+"	\n");
		sql.append("			AND IS_DEL <> '1'  	\n");
		sql.append("			AND DEALER_CODE = '"+dealerCode+"'  	\n");
		sql.append("			AND PART_CODE = '"+partNo+"'	\n");
		sql.append("			AND WAREHOUSE = '"+storageCode+"'	\n");
		sql.append("			order by DATE_FORMAT(END_DATE,'%Y-%m-%d') asc \n");
		return OemDAOUtil.findAll(sql.toString(), null);
	}

	/**
	 * DMS库存小于等于0时，更新下架 
	 * @param dealerCode
	 * @param partNo
	 * @param storageCode
	 */
	public void updateStatueAll(String dealerCode, String partNo, String storageCode) {
		String updateStatus = "UPDATE TT_OBSOLETE_MATERIAL_RELEASE set APPLY_NUMBER = 0,STATUS = "+OemDictCodeConstants.PART_OBSOLETE_RELESE_STATUS_02+"," +
				"CANCEL_TYPE = 3,UPDATE_BY="+DEConstant.DE_UPDATE_BY+",END_DATE=sysdate,UPDATE_DATE=sysdate where STATUS = "+OemDictCodeConstants.PART_OBSOLETE_RELESE_STATUS_01+" AND " +
				"DEALER_CODE = '"+dealerCode+"' AND PART_CODE = '"+partNo+"' and WAREHOUSE = '"+storageCode+"' ";
		OemDAOUtil.execBatchPreparement(updateStatus, null);
	}

	
	/**
	 * 可申请数为0是，根据流水号更新下架
	 * @param itemId
	 */
	public void updateStatue(Long itemId) {
		String updateStatus = "UPDATE TT_OBSOLETE_MATERIAL_RELEASE set APPLY_NUMBER = 0,STATUS = "+OemDictCodeConstants.PART_OBSOLETE_RELESE_STATUS_02+"," +
				"CANCEL_TYPE = 3,UPDATE_BY="+DEConstant.DE_UPDATE_BY+",END_DATE=sysdate,UPDATE_DATE=sysdate where ITEM_ID ="+itemId;
		OemDAOUtil.execBatchPreparement(updateStatus, null);
	}

	/**
	 * 可申请数不为0，根据流水号更新可申请数
	 * @param dcsRest
	 * @param itemId
	 */
	public void updateNum(int dcsRest, Long itemId) {
		String updateNum = "UPDATE TT_OBSOLETE_MATERIAL_RELEASE set APPLY_NUMBER = "+dcsRest+", " +
				"UPDATE_BY="+DEConstant.DE_UPDATE_BY+",UPDATE_DATE=sysdate where ITEM_ID ="+itemId;
		OemDAOUtil.execBatchPreparement(updateNum, null);
	}

}
