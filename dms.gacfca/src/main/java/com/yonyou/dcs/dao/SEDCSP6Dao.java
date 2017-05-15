package com.yonyou.dcs.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.DTO.gacfca.SEDCSP6DetailDto;
import com.yonyou.dms.DTO.gacfca.SEDCSP6Dto;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.common.OemDictCodeConstants;

/**
 * 经销商交货单发送接口
 * @author luoyang
 *
 */
@Repository
public class SEDCSP6Dao extends OemBaseDAO {

	public LinkedList<SEDCSP6Dto> queryData() throws Exception {
		StringBuffer sql = getSql();
		List<Map> mapList = OemDAOUtil.findAll(sql.toString(), null);
		LinkedList<SEDCSP6Dto> list = setSEDCSP6DtoList(mapList);
		return list;
	}
	
	private LinkedList<SEDCSP6Dto> setSEDCSP6DtoList(List<Map> mapList) throws Exception {
		LinkedList<SEDCSP6Dto> resultList = new LinkedList<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(mapList != null && !mapList.isEmpty()){
			for(Map map : mapList){
				SEDCSP6Dto dto = new SEDCSP6Dto();
				String dealerCode = map.get("DMS_CODE") == null ? null : String.valueOf(map.get("DMS_CODE"));
				String entityCode = map.get("DMS_CODE") == null ? null : String.valueOf(map.get("DMS_CODE"));
				String deliverNo = map.get("DELIVER_NO") == null ? null : String.valueOf(map.get("DELIVER_NO"));
				String sapOrderNo = map.get("SAP_ORDER_NO") == null ? null : String.valueOf(map.get("SAP_ORDER_NO"));
				String dmsOrderNo = map.get("DMS_ORDER_NO") == null ? null : String.valueOf(map.get("DMS_ORDER_NO"));
				Double amount = map.get("AMOUNT") == null ? null : Double.parseDouble(String.valueOf(map.get("AMOUNT")));
				Double netPrice = map.get("NET_PRICE") == null ? null : Double.parseDouble(String.valueOf(map.get("NET_PRICE")));
				Double taxAmount = map.get("TAX_AMOUNT") == null ? null : Double.parseDouble(String.valueOf(map.get("TAX_AMOUNT")));
				Double transAmount = map.get("TRANS_AMOUNT") == null ? null : Double.parseDouble(String.valueOf(map.get("TRANS_AMOUNT")));
				String transNo = map.get("TRANS_NO") == null ? null : String.valueOf(map.get("TRANS_NO"));
				String transCompany = map.get("TRANS_COMPANY") == null ? null : String.valueOf(map.get("TRANS_COMPANY"));
				Date transDate = map.get("TRANS_DATE") == null ? null : sdf.parse(String.valueOf(map.get("TRANS_DATE")));
				Date arrivedDate = map.get("ARRIVED_DATE") == null ? null : sdf.parse(String.valueOf(map.get("ARRIVED_DATE")));
				Date deliverDate = map.get("DELIVER_DATE") == null ? null : sdf.parse(String.valueOf(map.get("DELIVER_DATE")));
				String ecOrderNo = map.get("DMS_CODE") == null ? null : String.valueOf(map.get("DMS_CODE"));
				String transType = map.get("DMS_CODE") == null ? null : String.valueOf(map.get("DMS_CODE"));
				//明细
				Long deliverId = map.get("DELIVER_ID") == null ? null : Long.parseLong(String.valueOf(map.get("DELIVER_ID")));
				HashMap<Integer, SEDCSP6DetailDto> waybillList = null;
				if(deliverId != null){
					LinkedList<SEDCSP6DetailDto> detailList = queryDataDetail(deliverId);					
					waybillList = setSEDCSP6DetailDtoMap(detailList);
				}
				dto.setAmount(amount);
				dto.setArrivedDate(arrivedDate);
				dto.setDealerCode(dealerCode);
				dto.setDeliverDate(deliverDate);
				dto.setDeliverNo(deliverNo);
				dto.setDmsOrderNo(dmsOrderNo);
				dto.setEcOrderNo(ecOrderNo);
				dto.setEntityCode(entityCode);
				dto.setNetPrice(netPrice);
				dto.setSapOrderNo(sapOrderNo);
				dto.setTaxAmount(taxAmount);
				dto.setTransAmount(transAmount);
				dto.setTransCompany(transCompany);
				dto.setTransDate(transDate);
				dto.setTransNo(transNo);
				dto.setTransType(transType);
				dto.setWaybillList(waybillList);
				resultList.add(dto);
			}
		}
		return resultList;
	}

	private StringBuffer getSql(){
		StringBuffer sql = new StringBuffer("\n");
		sql.append("SELECT PD.DELIVER_ID, -- 发运单主键ID \n");
		sql.append("       DR.DMS_CODE, -- DMS经销商代码 \n");
		sql.append("       PD.DELIVER_NO, -- 交货单号 \n");
		sql.append("       PD.SAP_ORDER_NO, -- SAP订单号 \n");
		sql.append("       PD.DMS_ORDER_NO, -- DMS订单号 \n");
		sql.append("       PD.AMOUNT, -- 总价 \n");
		sql.append("       PD.NET_PRICE, -- 净价 \n");
		sql.append("       PD.TAX_AMOUNT, -- 税额 \n");
		sql.append("       PD.TRANS_AMOUNT, -- 运费 \n");
		sql.append("       PD.TRANS_NO, -- 运单号 \n");
		sql.append("       PD.TRANS_COMPANY, -- 运输公司 \n");
		sql.append("       PD.TRANS_DATE, -- 运输日期 \n");
		sql.append("       PD.ARRIVED_DATE, -- 到达日期 \n");
		sql.append("       PD.DELIVER_DATE, -- 交货单创建日期 \n");
		sql.append("       TPO.EC_ORDER_NO, -- 电商订单号\n");
		sql.append("       PD.TRANS_TYPE -- 运输方式 \n");
		sql.append("  FROM TT_PT_DELIVER_DCS PD \n");
		sql.append(" INNER JOIN TM_DEALER D ON D.DEALER_ID = PD.DEALER_ID \n");
		sql.append(" INNER JOIN TI_DEALER_RELATION DR ON DR.DCS_CODE || 'A' = D.DEALER_CODE AND DR.STATUS = '"+OemDictCodeConstants.STATUS_ENABLE+"' \n");
		sql.append("  LEFT JOIN TT_PT_ORDER_DCS TPO ON TPO.ORDER_NO = PD.DMS_ORDER_NO \n");
		sql.append("   AND (TPO.ORDER_TYPE <> "+OemDictCodeConstants.PART_ORDER_TYPE_09+" or TPO.ORDER_TYPE is null) \n");
		sql.append(" WHERE PD.IS_DCS_SEND = " + OemDictCodeConstants.IF_TYPE_NO + " \n");
		return sql;
	}
	
	public HashMap<Integer, SEDCSP6DetailDto> setSEDCSP6DetailDtoMap(LinkedList<SEDCSP6DetailDto> list){
		HashMap<Integer, SEDCSP6DetailDto> map = new HashMap<>();
		if(list != null && !list.isEmpty()){
			for(int i = 0; i < list.size(); i++){
				map.put(i, list.get(i));
			}
		}
		return map;
	}
	
	public LinkedList<SEDCSP6DetailDto> queryDataDetail(Long deliverId){
		StringBuffer sql = getDetailSql(deliverId);
		List<Map> mapList = OemDAOUtil.findAll(sql.toString(), null);
		LinkedList<SEDCSP6DetailDto> list = setSEDCSP6DetailDtoList(mapList);
		return list;
	}

	
	private LinkedList<SEDCSP6DetailDto> setSEDCSP6DetailDtoList(List<Map> mapList) {
		LinkedList<SEDCSP6DetailDto> resultList = new LinkedList<>();
		for(Map map : mapList){
			SEDCSP6DetailDto dto = new SEDCSP6DetailDto();
			String partCode = map.get("PART_CODE") == null ? null : String.valueOf(map.get("PART_CODE"));
			String partName = map.get("PART_NAME") == null ? null : String.valueOf(map.get("PART_NAME"));
			String orderNo = map.get("ORDER_NO") == null ? null : String.valueOf(map.get("ORDER_NO"));
			Long planNum = map.get("PLAN_NUM") == null ? null : Long.parseLong(String.valueOf(map.get("PLAN_NUM")));
			Double instorPrice = map.get("INSTOR_PRICE") == null ? null : Double.parseDouble(String.valueOf(map.get("INSTOR_PRICE")));
			Double discount = map.get("DISCOUNT") == null ? null : Double.parseDouble(String.valueOf(map.get("DISCOUNT")));
			Integer iposnr = map.get("LINE_NO") == null ? null : Integer.parseInt(String.valueOf(map.get("LINE_NO")));
			String transStock = map.get("TRANS_STOCK") == null ? null : String.valueOf(map.get("TRANS_STOCK"));
			dto.setDiscount(discount);
			dto.setInstorPrice(instorPrice);
			dto.setIposnr(iposnr);
			dto.setOrderNo(orderNo);
			dto.setPartCode(partCode);
			dto.setPartName(partName);
			dto.setPlanNum(planNum);
			dto.setTransStock(transStock);
			resultList.add(dto);
		}
		return resultList;
	}

	private StringBuffer getDetailSql(Long deliverId){
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT  \n");
		sql.append("    TPDD.PART_CODE,  -- 配件代码  \n");
		sql.append("    TPDD.PART_NAME,  -- 配件名称  \n");
		sql.append("    TPDD.ORDER_NO,   -- 订单号  \n");
		sql.append("    TPDD.LINE_NO,    -- 交货项目  \n");
		sql.append("    TPDD.PLAN_NUM,      -- 计划量  \n");
		sql.append("    TPDD.INSTOR_PRICE,  -- 入库单价  \n");
		sql.append("    TPDD.DISCOUNT,      -- 折扣额   \n");
		sql.append("    TPDD.TRANS_STOCK    -- 库存发货地点   \n");
		sql.append("FROM TT_PT_DELIVER_DETAIL_DCS TPDD  \n");
		sql.append("WHERE TPDD.DELIVER_ID = "+deliverId+"  \n");
		sql.append("order by TPDD.ORDER_NO  \n");
		return sql;
	}

}
