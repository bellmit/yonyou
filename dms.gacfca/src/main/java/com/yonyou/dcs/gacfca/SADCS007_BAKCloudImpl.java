package com.yonyou.dcs.gacfca;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.dao.RepairOrderResultStatusDao;
import com.yonyou.dms.DTO.gacfca.SA007Dto;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.gacfca.SADMS007Coud;

@Service
public class SADCS007_BAKCloudImpl extends BaseCloudImpl implements SADCS007_BAKCloud {
	
	private static final Logger logger = LoggerFactory.getLogger(SADCS007_BAKCloudImpl.class);
	
	@Autowired
	RepairOrderResultStatusDao dao ;

	@Autowired
	SADMS007Coud sadms007;
	
	@Override
	public String sendData(){
		try {
			logger.info("================经销商之间车辆调拨下发开始（SADCS007_BAKCloud）====================");
			//获取下发数据
			List<Map> list=this.getSendDate();
			if (null == list || list.size() == 0) {
				logger.info("================经销商之间车辆调拨下发（SADCS007_BAKCloud）,无数据====================");
				return null;
			}
			List<String> dmsCodes = new ArrayList<String>();
			//下发经销商
			List<Long> dealerIds = new ArrayList<Long>();
		    //组装数据
			List<SA007Dto> dtoList=this.setDto(list);
			
			for(int i=0;i<list.size();i++){
				Long inDealerId = Long.parseLong(CommonUtils.checkNull(list.get(i).get("IN_DEALER_ID"), "0L"));
				Long outDealerId = Long.parseLong(CommonUtils.checkNull(list.get(i).get("OUT_DEALER_ID"), "0L"));
				dealerIds.add(inDealerId);
				dealerIds.add(outDealerId);
			}
			
			for (Long dealerId : dealerIds) {
				try {
					System.out.println("经销商ID：" + dealerId);
					Map<String, Object> dmsDealer = dao.getDmsDealerCode(dealerId);
					System.out.println("dmsDealer.get(DMS_CODE).toString()::" + dmsDealer.get("DMS_CODE").toString());
					if(null!=dmsDealer&&dmsDealer.size()>0){
						 //下发操作
						 int flag = sadms007.getSADMS007(dmsDealer.get("DMS_CODE").toString(),dtoList);
						 if(flag==1){
							 logger.info("================经销商之间车辆调拨下发成功（SADCS007_BAKCloud）====================");
						 }else{
							 logger.info("================经销商之间车辆调拨下发失败（SADCS007_BAKCloud）====================");
						 	 logger.error("Cann't send to " + dmsDealer.get("DMS_CODE").toString());
						 }
					}else{
						logger.info("经销商:"+dealerId+"没有维护对应的下端的entityCode");
					}
					// 可下发的经销商列表
					dmsCodes.add(dmsDealer.get("DMS_CODE").toString());
					
				} catch (Exception e) {
					logger.error("Cann't send to " + dealerId, e);
				}
			}
		
			logger.info("================经销商之间车辆调拨下发结束（SADCS007_BAKCloud）====================");
		}catch (Exception e) {
			logger.info("================经销商之间车辆调拨下发异常（SADCS007_BAKCloud）====================");
		}
		return null;
	}
	/**
	 * 获取下发数据
	 * @return
	 */
	@Override
	public List<Map> getSendDate(){
		StringBuffer sql = new StringBuffer();
		sql.append(" \n");
		sql.append("SELECT V1.IN_DEALER_ID, \n");
		sql.append("       IND.DEALER_CODE  AS IN_DEALER_CODE, \n");
		sql.append("       V1.OUT_DEALER_ID, \n");
		sql.append("       OUTD.DEALER_CODE AS OUT_DEALER_CODE, \n");
		sql.append("       V3.PRODUCTCODE   AS PRODUCT_CODE, \n");
		sql.append("       V2.ENGINE_NO, \n");
		sql.append("       V2.VIN, \n");
		sql.append("       V2.PRODUCT_DATE \n");
		sql.append("  FROM TT_VS_VEHICLE_TRANSFER V1, \n");
		sql.append("       TM_VEHICLE V2, \n");
		sql.append("       (SELECT T1.GROUP_CODE AS CONFIGCODE, \n");
		sql.append("               T1.GROUP_NAME AS CONFIGNAME, \n");
		sql.append("               T2.GROUP_CODE AS MODELCODE, \n");
		sql.append("               T2.GROUP_NAME AS MODELNAME, \n");
		sql.append("               T5.MATERIAL_ID, \n");
		sql.append("               T3.GROUP_CODE AS SERIESCODE, \n");
		sql.append("               T3.GROUP_NAME AS SERIESNAME, \n");
		sql.append("               T4.GROUP_CODE AS BRANDCODE, \n");
		sql.append("               T4.GROUP_NAME AS BRANDNAME, \n");
		sql.append("               T5.VHCL_PRICE, \n");
		sql.append("               T5.STATUS, \n");
		sql.append("               T5.SALE_PRICE, \n");
		sql.append("               RTRIM(CHAR(T5.MATERIAL_CODE || T5.COLOR_CODE || T5.TRIM_CODE || \n");
		sql.append("                          V.MODEL_YEAR)) AS PRODUCTCODE, \n");
		sql.append("               T5.COLOR_CODE, \n");
		sql.append("               T5.COLOR_NAME, \n");
		sql.append("               RTRIM(CHAR(T5.MATERIAL_NAME || T5.COLOR_NAME || T5.TRIM_NAME || \n");
		sql.append("                          V.MODEL_YEAR)) AS PRODUCTNAME, \n");
		sql.append("               V.MODEL_YEAR \n");
		sql.append("          FROM TM_VHCL_MATERIAL         T5, \n");
		sql.append("               TM_VHCL_MATERIAL_GROUP_R T6, \n");
		sql.append("               TM_VHCL_MATERIAL_GROUP   T1, \n");
		sql.append("               TM_VHCL_MATERIAL_GROUP   T2, \n");
		sql.append("               TM_VHCL_MATERIAL_GROUP   T3, \n");
		sql.append("               TM_VHCL_MATERIAL_GROUP   T4, \n");
		sql.append("               VW_MATERIAL              V \n");
		sql.append("         WHERE T5.MATERIAL_ID = T6.MATERIAL_ID \n");
		sql.append("           AND T5.MATERIAL_ID = V.MATERIAL_ID \n");
		sql.append("           AND T6.GROUP_ID = T1.GROUP_ID \n");
		sql.append("           AND T1.PARENT_GROUP_ID = T2.GROUP_ID \n");
		sql.append("           AND T2.PARENT_GROUP_ID = T3.GROUP_ID \n");
		sql.append("           AND T3.PARENT_GROUP_ID = T4.GROUP_ID \n");
		sql.append("           AND T1.GROUP_LEVEL = '4') V3, \n");
		sql.append("       TM_DEALER IND, \n");
		sql.append("       TM_DEALER OUTD \n");
		sql.append(" WHERE V1.VEHICLE_ID = V2.VEHICLE_ID \n");
		sql.append("   AND V2.MATERIAL_ID = V3.MATERIAL_ID \n");
		sql.append("   AND V1.IN_DEALER_ID = IND.DEALER_ID \n");
		sql.append("   AND V1.OUT_DEALER_ID = OUTD.DEALER_ID \n");
		sql.append("   AND V1.IS_SEND = 0 \n");
		sql.append("   AND V1.CREATE_DATE > '2015-07-25' \n");

		List<Map> ps = OemDAOUtil.findAll(sql.toString(), null);
		return ps;
	}
	/**
	 * 组装数据
	 * @return
	 */
	@Override
	public LinkedList<SA007Dto> setDto(List<Map> list){
		LinkedList<SA007Dto> dtoList=new LinkedList<SA007Dto>();
		for(int i=0;i<list.size();i++){
			
			String inEntityCode = "";
			String outEntityCode = "";

			Map<String, Object> inDmsDealer = dao.getDmsDealerCode(CommonUtils.checkNull(list.get(i).get("IN_DEALER_CODE")));
			if (inDmsDealer != null && !"".equals(CommonUtils.checkNull(inDmsDealer.get("DMS_CODE")))) {
				// 可下发的经销商列表
				inEntityCode = inDmsDealer.get("DMS_CODE").toString();
			}
			Map<String, Object> outDmsDealer = dao.getDmsDealerCode(CommonUtils.checkNull(list.get(i).get("OUT_DEALER_CODE")));
			if (outDmsDealer != null && !"".equals(CommonUtils.checkNull(outDmsDealer.get("DMS_CODE")))) {
				// 可下发的经销商列表
				outEntityCode = outDmsDealer.get("DMS_CODE").toString();
			}
			SA007Dto dto =new SA007Dto();
			dto.setInEntityCode(inEntityCode);
			dto.setOutEntityCode(outEntityCode);
			dto.setProductCode(CommonUtils.checkNull(list.get(i).get("PRODUCT_CODE")));
			dto.setEngineNo(CommonUtils.checkNull(list.get(i).get("ENGINE_NO")));
			dto.setVin(CommonUtils.checkNull(list.get(i).get("VIN")));
			dto.setManufactureDate(CommonUtils.parseDate(CommonUtils.checkNull(list.get(i).get("PRODUCT_DATE"))));
			dto.setFactoryDate(CommonUtils.parseDate(CommonUtils.checkNull(list.get(i).get("PRODUCT_DATE"))));
			dtoList.add(dto);
		}
		return dtoList;
	}
}
