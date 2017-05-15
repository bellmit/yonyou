package com.yonyou.dcs.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.DTO.gacfca.SEDMS068Dto;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.common.OemDictCodeConstants;

/**
 * 调拨入库单下发接口
 * @author luoyang
 *
 */
@Repository
public class SEDMS068Dao extends OemBaseDAO {

	public LinkedList<SEDMS068Dto> getStockInList(String allocateOutNo) {
		StringBuffer sql = getSql(allocateOutNo);
		List<Map> mapList = OemDAOUtil.findAll(sql.toString(), null);
		LinkedList<SEDMS068Dto> list = setSEDMS068DtoList(mapList);
		return list;
	}
	
	private LinkedList<SEDMS068Dto> setSEDMS068DtoList(List<Map> mapList) {
		LinkedList<SEDMS068Dto> resultList = new LinkedList<>();
		if(mapList != null && !mapList.isEmpty()){
			for(Map map : mapList){
				SEDMS068Dto dto = new SEDMS068Dto();
				String dealerCode = map.get("DMS_CODE") == null ? null : String.valueOf(map.get("DMS_CODE"));
				String entityCode = map.get("DMS_CODE") == null ? null : String.valueOf(map.get("DMS_CODE"));
				String providerName = map.get("PROVIDER_NAME") == null ? null : String.valueOf(map.get("PROVIDER_NAME"));
				String partNo = map.get("PART_CODE") == null ? null : String.valueOf(map.get("PART_CODE"));
				String partName = map.get("PART_NAME") == null ? null : String.valueOf(map.get("PART_NAME"));
				String unitCode = map.get("MEASURE_UNITS") == null ? null : String.valueOf(map.get("MEASURE_UNITS"));
				String inQuantity = map.get("APPLY_NUMBER") == null ? null : String.valueOf(map.get("APPLY_NUMBER"));
				String inPrice = map.get("SALES_PRICE") == null ? null : String.valueOf(map.get("SALES_PRICE"));
				String inAmount = map.get("IN_AMOUNT") == null ? null : String.valueOf(map.get("IN_AMOUNT"));
				String outWarehousNo = map.get("OUT_WAREHOUS_NO") == null ? null : String.valueOf(map.get("OUT_WAREHOUS_NO"));
				//DCS生成 约定规则：I+年月日(160101)+自增长共12位
				SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
				String dateStr = sdf.format(new Date());
				String allocateInNo = "I"+dateStr+getMaxInNo();
				dto.setAllocateInNo(allocateInNo);
				dto.setDealerCode(dealerCode);
				dto.setEntityCode(entityCode);
				dto.setInAmount(inAmount);
				dto.setInPrice(inPrice);
				dto.setInQuantity(inQuantity);
				dto.setPartName(partName);
				dto.setPartNo(partNo);
				dto.setProviderName(providerName);
				dto.setUnitCode(unitCode);
				dto.setOutWarehousNo(outWarehousNo);
				resultList.add(dto);
			}
		}
		return resultList;
	}

	/**
	 * 从数据库中获取最大PUT_WAREHOUS_NO
	 * @param allocateOutNo
	 * @return
	 */
	private StringBuffer getSql(String allocateOutNo){
		StringBuffer sql = new StringBuffer();
		sql.append("select toma.APPLY_DEALER_CODE, -- 入库经销商代码	\n");
		sql.append("	tdr.DMS_CODE, -- 申请经销商名称	\n");
		sql.append("	toma.OUT_WAREHOUS_NO, -- 出库单号	\n");
		sql.append("	CASE IFNULL(td.DEALER_NAME , '0') WHEN '0' THEN '' ELSE td.DEALER_NAME END AS PROVIDER_NAME, -- 供应商名称（默认：发布经销商名称（全称））	\n");
		sql.append("	tomr.PART_CODE, -- 配件代码	\n");
		sql.append("	tomr.PART_NAME, -- 配件名称	\n");
		sql.append("	toma.MEASURE_UNITS, -- 单位	\n");
		sql.append("	toma.APPLY_NUMBER, -- 入库数量	\n");
		sql.append("	toma.SALES_PRICE, -- 入库不含税单价（上报单价）	\n");
		sql.append("	(toma.APPLY_NUMBER*toma.SALES_PRICE) as IN_AMOUNT -- 入库不含税金额	\n");
		sql.append("		from TT_OBSOLETE_MATERIAL_APPLY_DCS toma	\n");
		sql.append("			inner join TT_OBSOLETE_MATERIAL_RELEASE_DCS tomr on toma.RELEASE_ID=tomr.RELEASE_ID	\n");
		sql.append("			inner join TM_DEALER td on toma.RELEASE_DEALER_CODE=td.DEALER_CODE	\n");
		sql.append("			inner join TM_DEALER tds on toma.APPLY_DEALER_CODE=tds.DEALER_CODE	\n");
		sql.append("			inner join TM_COMPANY tc on tds.COMPANY_ID = tc.COMPANY_ID	\n");
		sql.append("			inner join TI_DEALER_RELATION tdr on tc.COMPANY_CODE = tdr.DCS_CODE	\n");
		sql.append("			where 1=1	\n");
		sql.append("				and toma.STATUS="+OemDictCodeConstants.PART_OBSOLETE_APPLY_STATUS_03+" -- 出库	\n");
		sql.append("				and toma.PUT_SEND_DMS=0 -- 未下发入库单	\n");
		sql.append("				and toma.OUT_WAREHOUS_NO='"+allocateOutNo+"'	\n");
		System.out.println("SQL:"+sql);
		return sql;
	}
	
	private String getMaxInNo(){
		String sql = "select SUBSTR(max(t.PUT_WAREHOUS_NO) FROM 8) as num from TT_OBSOLETE_MATERIAL_APPLY_DCS t  ";
		List<Map> list = OemDAOUtil.findAll(sql, null);
		Integer num = Integer.parseInt(String.valueOf(list.get(0).get("num")));
		num = num + 1;
		String result = null;
		if(num.SIZE == 6){
			result = "00001";
		}else{
			result = String.valueOf(num);
		}
		return result;
	}

}
