package com.yonyou.dcs.dao;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.infoeai.eai.po.TiRepairOrderPO;
import com.infoeai.eai.po.TiRoAddItemPO;
import com.infoeai.eai.po.TiRoLabourPO;
import com.infoeai.eai.po.TiRoRepairPartPO;
import com.infoeai.eai.po.TiSalesPartItemPO;
import com.infoeai.eai.po.TiSalesPartPO;
import com.yonyou.dms.common.Util.Utility;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.utils.common.CommonUtils;

@Repository
public class SEDCS112Dao extends OemBaseDAO {
	
	public List<Map> select(String dealerCode,String roNo) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT * FROM TI_REPAIR_ORDER_DCS	\n");
		sql.append("	WHERE DEALER_CODE ='"+dealerCode+"'\n");
		sql.append("		AND RO_NO ='"+dealerCode+"'	\n");
		List<Map> listMap=OemDAOUtil.findAll(sql.toString(), null);
		return listMap;
		
	}
	/**
	 * 根据下端上报的VIN查询查询车辆是否存在，存在则添加,不存在则不添加 
	 * @param vin
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getVehiceVin(String vin) throws Exception {
		if("".equals(CommonUtils.checkNull(vin))){
			return null;
		}
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT V.VIN\n" );
		sql.append("  FROM TM_VEHICLE V\n" );
		sql.append(" WHERE V.VIN = '"+vin+"'\n" );
		sql.append("   AND V.IS_DEL = 0");
		Map<String, Object> map = OemDAOUtil.findFirst(sql.toString(),null);
		return map;
	}
	/**
	 * 删除配件销售单明细
	 * @param dealerCode
	 * @param roNo
	 */
	public void delSalesPartItem(String dealerCode,String roNo) {
		StringBuffer sql = new StringBuffer();
		if(Utility.testIsNotNull(dealerCode) || Utility.testIsNotNull(roNo)){
			sql.append("	SELECT ITEM_ID FROM TI_SALES_PART_ITEM WHERE DEALER_CODE='"+dealerCode+"' \n");
			sql.append("		AND SALES_PART_NO IN	\n");
			sql.append("		(SELECT SALES_PART_NO FROM TI_SALES_PART \n");
			sql.append("			WHERE RO_NO='"+roNo+"' AND DEALER_CODE='"+dealerCode+"') \n");
			List<Map> listmap=OemDAOUtil.findAll(sql.toString(), null); 
			if(null!=listmap&&listmap.size()>0){
				for(Map map:listmap){
					int i=CommonUtils.checkNull(map.get("ITEM_ID"))==""?0:TiSalesPartItemPO.delete("ITEM_ID= ? ", CommonUtils.checkNull(map.get("ITEM_ID")));
				}
			}
		}
	}
	/**
	* @Title: delRepairTable
	* @param TI_REPAIR_ORDER
	* @param TI_RO_REPAIR_PART 工单维修配件明细
	* @param TI_RO_ADD_ITEM 工单附加项目明细
	* @param TI_RO_LABOUR 工单维修项目明细
	* @param TI_SALES_PART 配件销售单
	* @return void    返回类型 
	* @throws
	 */
//	private final String[] repairTables = {"TI_REPAIR_ORDER_DCS", "TI_RO_REPAIR_PART_DCS", "TI_RO_ADD_ITEM_DCS","TI_RO_LABOUR_DCS","TI_SALES_PART_DCS"};
	public void delRepairTable(String dealerCode,String roNo) {
		if(Utility.testIsNotNull(dealerCode) || Utility.testIsNotNull(roNo)){
			TiRepairOrderPO.delete("DEALER_CODE = ? AND RO_NO = ?", dealerCode, roNo);
			TiRoRepairPartPO.delete("DEALER_CODE = ? AND RO_NO = ?", dealerCode, roNo);
			TiRoAddItemPO.delete("DEALER_CODE = ? AND RO_NO = ?", dealerCode, roNo);
			TiRoLabourPO.delete("DEALER_CODE = ? AND RO_NO = ?", dealerCode, roNo);
			TiSalesPartPO.delete("DEALER_CODE = ? AND RO_NO = ?", dealerCode, roNo);
//			for (String table : repairTables) {
//				StringBuffer sql = new StringBuffer();
//				sql.append("DELETE FROM " + table + "\n");
//				sql.append("WHERE DEALER_CODE='"+dealerCode+"' AND RO_NO='"+roNo+"'	\n");
//				delete(sql.toString(), null);
//			}
		}
	}
}
