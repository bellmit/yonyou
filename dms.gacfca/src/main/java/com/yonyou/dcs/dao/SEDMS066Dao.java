package com.yonyou.dcs.dao;

import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.DTO.gacfca.SEDMS066Dto;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * 调拨出库单下发接口
 * @author luoyang
 *
 */
@Repository
public class SEDMS066Dao extends OemBaseDAO {

	public LinkedList<SEDMS066Dto> getStockOutList(String outWarehousNos) {
		StringBuffer sql = getSql(outWarehousNos);
		List<Map> mapList = OemDAOUtil.findAll(sql.toString(), null);
		LinkedList<SEDMS066Dto> list = setSEDMS066DtoList(mapList);
		return list;
	}
	
	private LinkedList<SEDMS066Dto> setSEDMS066DtoList(List<Map> mapList) {
		LinkedList<SEDMS066Dto> resultList = new LinkedList<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(mapList != null && !mapList.isEmpty()){
			for(Map map : mapList){
				SEDMS066Dto dto = new SEDMS066Dto();
				String dealerCode = map.get("DMS_CODE") == null ? null : String.valueOf(map.get("DMS_CODE"));
				String entityCode = map.get("DMS_CODE") == null ? null : String.valueOf(map.get("DMS_CODE"));
				String allocateOutNo = map.get("OUT_WAREHOUS_NO") == null ? null : String.valueOf(map.get("OUT_WAREHOUS_NO"));
				String customerName = map.get("CUSTOMER_NAME") == null ? null : String.valueOf(map.get("CUSTOMER_NAME"));
				String storageCode = map.get("WAREHOUSE") == null ? null : String.valueOf(map.get("WAREHOUSE"));
				String storagePositionCode = map.get("STORAGE_CODE") == null ? null : String.valueOf(map.get("STORAGE_CODE"));
				String partNo = map.get("PART_CODE") == null ? null : String.valueOf(map.get("PART_CODE"));
				String partName = map.get("PART_NAME") == null ? null : String.valueOf(map.get("PART_NAME"));
				String unitCode = map.get("MEASURE_UNITS") == null ? null : String.valueOf(map.get("MEASURE_UNITS"));
				String outgoingQuantity = map.get("APPLY_NUMBER") == null ? null : String.valueOf(map.get("APPLY_NUMBER"));
				String outgoingPrice = map.get("SALES_PRICE") == null ? null : String.valueOf(map.get("SALES_PRICE"));
				String outgoingTotal = map.get("OUTGOING_AMOUNT") == null ? null : String.valueOf(map.get("OUTGOING_AMOUNT"));
				String reportedCostPrice = map.get("COST_PRICE") == null ? null : String.valueOf(map.get("COST_PRICE"));
				String reportedCostTotal = map.get("COST_AMOUNT") == null ? null : String.valueOf(map.get("COST_AMOUNT"));
				
				dto.setAllocateOutNo(allocateOutNo);
				dto.setCustomerName(customerName);
				dto.setDealerCode(dealerCode);
				dto.setEntityCode(entityCode);
				dto.setOutgoingPrice(outgoingPrice);
				dto.setOutgoingQuantity(outgoingQuantity);
				dto.setOutgoingTotal(outgoingTotal);
				dto.setPartName(partName);
				dto.setPartNo(partNo);
				dto.setReportedCostPrice(reportedCostPrice);
				dto.setReportedCostTotal(reportedCostTotal);
				dto.setStorageCode(storageCode);
				dto.setStoragePositionCode(storagePositionCode);
				dto.setUnitCode(unitCode);
				resultList.add(dto);
			}
		}
		return resultList;
	}

	private StringBuffer getSql(String outWarehousNos){
		StringBuffer sql = new StringBuffer();
		sql.append("select tdr.DMS_CODE,-- 发布DMS经销商代码	\n");
		sql.append("	toma.APPLY_ID,-- 发布信息ID	\n");
		sql.append("	toma.OUT_WAREHOUS_NO,-- 调拨出库单号	\n");
		sql.append("	tds.DEALER_NAME AS CUSTOMER_NAME, -- 客户名称（默认:购买经销商名称(全称)）\n");
		sql.append("	tomr.WAREHOUSE,-- 仓库	\n");
		sql.append("	tomr.STORAGE_CODE,-- 库位代码	\n");
		sql.append("	tomr.PART_CODE,-- 配件代码	\n");
		sql.append("	tomr.PART_NAME,-- 配件名称	\n");
		sql.append("	toma.MEASURE_UNITS,-- 计量单位	\n");
		sql.append("	toma.APPLY_NUMBER,-- 出库数量(申请数量)	\n");
		sql.append("	toma.SALES_PRICE,-- 出库单价(销售单价(发布价格))	\n");
		sql.append("	(toma.APPLY_NUMBER*toma.SALES_PRICE) as OUTGOING_AMOUNT,-- 出库金额	\n");
		sql.append("	toma.COST_PRICE,-- 上报时成本单价(成本单价)	\n");
		sql.append("	(toma.APPLY_NUMBER*toma.COST_PRICE) as COST_AMOUNT-- 上报时成本金额	\n");
		sql.append("		from TT_OBSOLETE_MATERIAL_APPLY_DCS toma	\n");
		sql.append("			inner join TT_OBSOLETE_MATERIAL_RELEASE_DCS tomr on toma.RELEASE_ID=tomr.RELEASE_ID	\n");
		sql.append("			inner join TM_DEALER tds on toma.APPLY_DEALER_CODE=tds.DEALER_CODE -- 申请经销商	\n");
		sql.append("			inner join TM_DEALER tdf on toma.RELEASE_DEALER_CODE=tdf.DEALER_CODE -- 发布经销商	\n");
		sql.append("			inner join TM_COMPANY tc on tdf.COMPANY_ID = tc.COMPANY_ID	\n");
		sql.append("			inner join TI_DEALER_RELATION tdr on tc.COMPANY_CODE = tdr.DCS_CODE	\n");
		sql.append("			where 1=1	\n");
		sql.append("				and toma.STATUS="+OemDictCodeConstants.PART_OBSOLETE_APPLY_STATUS_02+"-- 已确认	\n");
		sql.append("				and toma.OUT_SEND_DMS=0	-- 未下发出库单	\n");
		if(!StringUtils.isNullOrEmpty(outWarehousNos)){//出库单号
			outWarehousNos = outWarehousNos.replace("，", ",");
			outWarehousNos = outWarehousNos.replace(",", "','");
			sql.append("				and toma.OUT_WAREHOUS_NO in('"+outWarehousNos+"')-- 调拨出库单号	\n");
		}
		return sql;
	}

}
