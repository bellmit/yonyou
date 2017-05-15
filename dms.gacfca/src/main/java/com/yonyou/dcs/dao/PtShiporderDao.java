package com.yonyou.dcs.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.DTO.gacfca.PtDlyInfoDetailDto;
import com.yonyou.dms.DTO.gacfca.PtDlyInfoDto;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;

@Repository
public class PtShiporderDao extends OemBaseDAO {
	
	private StringBuffer getSql(){
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT TPS.SAPSALESORDERNUMBER,\n");
		sql.append("        TPS.DELIVERYNOTE,\n");
		sql.append("       TPS.INVOICCREATIONDATE,\n");
		sql.append("        C.DMS_CODE \n");
		sql.append("          FROM TI_PT_SHIPORDER TPS,");
		sql.append("          TM_DEALER A, TM_COMPANY B, TI_DEALER_RELATION C ");
		sql.append("          WHERE  1=1   \n");
		sql.append("          AND  A.COMPANY_ID = B.COMPANY_ID AND B.COMPANY_CODE = C.DCS_CODE");
		sql.append("          AND A.DEALER_CODE = TPS.DEALERCODE");
		sql.append("          AND C.STATUS="+OemDictCodeConstants.STATUS_ENABLE);
		sql.append("          AND ( TPS.IS_DOWN = 0 OR TPS.IS_DOWN is null) ");
		sql.append("          group by DMS_CODE,DELIVERYNOTE,SAPSALESORDERNUMBER, TPS.INVOICCREATIONDATE limit "+OemDictCodeConstants.PAGE_SIZE200+" ");
		return sql;
	}

	public LinkedList<PtDlyInfoDto> queryPtShiporderPOInfo() {
		StringBuffer sql = this.getSql();
		LinkedList<PtDlyInfoDto> list = setPtDlyInfoDtoList(OemDAOUtil.findAll(sql.toString(), null));
		return list;
	}

	private LinkedList<PtDlyInfoDto> setPtDlyInfoDtoList(List<Map> rs) {
		LinkedList<PtDlyInfoDto> list = new LinkedList<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			if(rs != null && rs.size() > 0){
				for (int i = 0; i < rs.size(); i++) {
					PtDlyInfoDto dto = new PtDlyInfoDto();
					dto.setDeliverynote(CommonUtils.checkNull(rs.get(i).get("DELIVERYNOTE")));
					dto.setElinkorderno(CommonUtils.checkNull(rs.get(i).get("SAPSALESORDERNUMBER")));
					Date invoiccreationdate = CommonUtils.checkNull(rs.get(i).get("INVOICCREATIONDATE")) == "" ? null : sdf.parse(CommonUtils.checkNull(rs.get(i).get("INVOICCREATIONDATE")));
					dto.setInvoiccreationdate( invoiccreationdate );
					dto.setDealerCode(CommonUtils.checkNull(rs.get(i).get("DMS_CODE")));
					dto.setDownTimestamp(new Date());
					list.add(dto);
				}
			}
		} catch (Exception e) {
			throw new ServiceBizException(e);
		}
		return list;
	}

	public LinkedList<PtDlyInfoDetailDto> queryPtShiporderDetailInfo(String dealerCode, String deliverynote,
			String elinkorderno, Date invoiccreationdate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		StringBuffer sql = new StringBuffer();
		List<Object> queryParam = new ArrayList<>();
		sql.append("SELECT TPS.SAPSALESORDERNUMBER,\n");
		sql.append("        TPS.DELIVERYNOTE,\n");
		sql.append("       TPS.INVOICCREATIONDATE,\n");
		sql.append("       TPS.DELIVERYORDERPOSITION,\n");
		sql.append("       TPS.PARTNO,\n");
		sql.append("       TPS.PARTDESCRIPTION,\n");
		sql.append("       TPS.SAPINVOICEQUANTITY,\n");
		sql.append("       TPS.LANDEDPRICE,\n");
		sql.append("       TPS.NETPRICE, \n");
		sql.append("       C.DMS_CODE \n");
		sql.append("          FROM TI_PT_SHIPORDER TPS,");
		sql.append("          TM_DEALER A, TM_COMPANY B, TI_DEALER_RELATION C ");
		sql.append("          WHERE  1=1   \n");
		sql.append("          AND  A.COMPANY_ID = B.COMPANY_ID AND B.COMPANY_CODE = C.DCS_CODE");
		sql.append("          AND A.DEALER_CODE = TPS.DEALERCODE");
		sql.append("          AND C.DMS_CODE= ? and TPS.DELIVERYNOTE= ? and TPS.SAPSALESORDERNUMBER= ? and DATE_FORMAT(TPS.INVOICCREATIONDATE,'%Y-%m-%d')= ? ");
		sql.append("          AND ( TPS.IS_DOWN = 0 OR TPS.IS_DOWN is null) ");
		sql.append("          AND C.STATUS="+OemDictCodeConstants.STATUS_ENABLE);
		queryParam.add(dealerCode);
		queryParam.add(deliverynote);
		queryParam.add(elinkorderno);
		String tempdate = invoiccreationdate == null ? null : sdf.format(invoiccreationdate);
		queryParam.add(tempdate);
		LinkedList<PtDlyInfoDetailDto> list = setPtDlyInfoDetailDto(OemDAOUtil.findAll(sql.toString(), queryParam));
		return list;
	}

	private LinkedList<PtDlyInfoDetailDto> setPtDlyInfoDetailDto(List<Map> rs) {
		LinkedList<PtDlyInfoDetailDto> list = new LinkedList<>();
		try {
			if(rs != null && rs.size() > 0){
				for (int i = 0; i < rs.size(); i++) {
					PtDlyInfoDetailDto dto = new PtDlyInfoDetailDto();
					Integer deliverynoteItem = CommonUtils.checkNull(rs.get(i).get("DELIVERYORDERPOSITION")) == "" ? null : Integer.valueOf(CommonUtils.checkNull(rs.get(i).get("DELIVERYORDERPOSITION")));//货运单序号
					Double sapinvoicequantity = CommonUtils.checkNull(rs.get(i).get("SAPINVOICEQUANTITY")) == "" ? null : Double.valueOf(CommonUtils.checkNull(rs.get(i).get("SAPINVOICEQUANTITY")));//配件数量
					Double netprice = CommonUtils.checkNull(rs.get(i).get("NETPRICE")) == "" ? null : Double.valueOf(CommonUtils.checkNull(rs.get(i).get("NETPRICE")));//销售单价（不含税）下端订货价
					Double valueofgoods = CommonUtils.checkNull(rs.get(i).get("NETPRICE")) == "" ? null : Double.valueOf(CommonUtils.checkNull(rs.get(i).get("NETPRICE")));//销售总价
					String partno;//配件代码
					String partname = CommonUtils.checkNull(rs.get(i).get("PARTDESCRIPTION"));//配件名称 
					Date downTimestamp;
					String dealerCode = CommonUtils.checkNull(rs.get(i).get("DMS_CODE"));
					
					dto.setDealerCode(dealerCode);
					dto.setDeliverynoteItem(deliverynoteItem);
					dto.setNetprice(netprice);
					dto.setPartname(partname);
					dto.setPartno(CommonUtils.checkNull(rs.get(i).get("PARTNO")));
					dto.setSapinvoicequantity(sapinvoicequantity);
					dto.setValueofgoods(valueofgoods);
					list.add(dto);
				}
			}
		} catch (Exception e) {
			throw new ServiceBizException(e);
		}
		return list;
	}

	public String[] getDealerByGroupId(String[] groupId, String dealerCode) {
		String[] groupIds = null;
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT DISTINCT T1.GROUP_ID FROM TM_DEALER_BUSS t1 , TM_DEALER t2 WHERE  t1.DEALER_ID = t2.DEALER_ID AND T2.DEALER_CODE = '" + dealerCode + "' ");
		if(null!= groupId && groupId.length>0){
			sql.append(" AND T1.GROUP_ID IN( ");
			for (int i = 0; i < groupId.length; i++) {
				if (i == groupId.length - 1) {
					sql.append(" '" + groupId[i] + "'");
				} else {
					sql.append(" '" + groupId[i] + "',");
				}
			}
			sql.append(" ) ");
		}
		System.out.println(sql.toString());
		List<Map> list = OemDAOUtil.findAll(sql.toString(), null);
		if(null!= list && list.size()>0){
			groupIds = new String[list.size()];
			for (int i = 0; i < list.size(); i++) {
				groupIds[i] = CommonUtils.checkNull(list.get(i).get("GROUP_ID"));
			}
		}
		return groupIds;
	}

	public LinkedList<PtDlyInfoDto> queryPtShiporderPOInfo(String[] groupId) {
		StringBuffer sql = this.getSql();
		LinkedList<PtDlyInfoDto> list = setPtDlyInfoDtoList(OemDAOUtil.findAll(sql.toString(), null));
		return list;
	}

}
