package com.infoeai.eai.dao.ctcai;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.infoeai.eai.DTO.Dcs2SwtDTO;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.CommonUtils;

@Repository
public class SI02Dao extends OemBaseDAO {

	public List<Dcs2SwtDTO> getSI02Info(String type) {
		StringBuffer sql = new StringBuffer();
		sql.append(" \n");
		sql.append("SELECT TSI.SEQUENCE_ID, \n");
		sql.append("       TSI.VIN, \n");
		sql.append("       'ZDRR' AS ACTION_CODE, \n");
		sql.append("       DATE_FORMAT (TSI.CREATE_DATE, '%Y-%m-%d') AS ACTION_DATE, \n");
		sql.append("       (SELECT CTCAI_CODE FROM TM_COMPANY WHERE COMPANY_CODE = TSI.RECEIVING_DEALER) AS RECEIVING_DEALER, \n");
		sql.append("       (SELECT DEALER_NAME FROM TM_DEALER WHERE DEALER_CODE = TSI.RECEIVING_DEALER) AS DEALER_NAME, \n");
		sql.append("       TSI.PAYMENT_TYPE, \n");
		sql.append("       MODEL.GROUP_CODE || '-8BL' AS GROUP_CODE, \n");
		sql.append("       TVMG.MODEL_YEAR, \n");
		sql.append("       TVM.COLOR_CODE, \n");
		sql.append("       TVM.TRIM_CODE, \n");
		sql.append("       TVMG.STANDARD_OPTION, \n");
		sql.append("       TVMG.FACTORY_OPTIONS, \n");
		sql.append("       CASE  TRR.SPECIAL_REMARK WHEN '12511002' THEN 'RGA' WHEN '12511009' THEN 'SP6' ELSE TVMG.LOCAL_OPTION END AS LOCAL_OPTION, \n");
		sql.append("       TSI.VEHICLE_USAGE_TYPE, \n");
		sql.append("       TSI.WHOLESALE_PRICE \n");
		sql.append("  FROM TI_SALESORDER_IMPORT TSI, \n");
		sql.append("       TM_VEHICLE_DEC TMV \n");
		sql.append("       LEFT JOIN TT_RESOURCE_REMARK TRR ON TRR.VIN = TMV.VIN, \n");
		sql.append("       TM_VHCL_MATERIAL TVM, \n");
		sql.append("       TM_VHCL_MATERIAL_GROUP_R TVMGR, \n");
		sql.append("       TM_VHCL_MATERIAL_GROUP TVMG, \n");
		sql.append("       TM_VHCL_MATERIAL_GROUP MODEL, \n");
		sql.append("       TM_VHCL_MATERIAL_GROUP MG2 \n");
		sql.append(" WHERE (TSI.IS_SCAN = '0' OR TSI.IS_SCAN IS NULL) \n");
		sql.append("   AND TSI.PAYMENT_TYPE IS NOT NULL \n");
		sql.append("   AND TMV.VIN = TSI.VIN \n");
		sql.append("   AND TVM.MATERIAL_ID = TMV.MATERIAL_ID \n");
		sql.append("   AND TVMGR.MATERIAL_ID = TVM.MATERIAL_ID \n");
		sql.append("   AND TVMG.GROUP_ID = TVMGR.GROUP_ID \n");
		sql.append("   AND MODEL.GROUP_ID = TVMG.PARENT_GROUP_ID \n");
		sql.append("   AND MG2.GROUP_ID = MODEL.PARENT_GROUP_ID \n");
		sql.append("   AND MG2.GROUP_TYPE = '" + OemDictCodeConstants.GROUP_TYPE_IMPORT + "' \n");
		List<Map> mapList = OemDAOUtil.findAll(sql.toString(), null);
		return setDcs2SwtDTOList(mapList);
	}

	private List<Dcs2SwtDTO> setDcs2SwtDTOList(List<Map> mapList) {
		 List<Dcs2SwtDTO> list = new ArrayList<>();
		if(mapList != null && !mapList.isEmpty()){
			for(Map map : mapList){
				Dcs2SwtDTO dto = new Dcs2SwtDTO();
				Long sequenceId = Long.parseLong(CommonUtils.checkNull(map.get("SEQUENCE_ID")));            //序列号
				String vin = CommonUtils.checkNull(map.get("VIN"));;                 //VIN号
				String actionCode = CommonUtils.checkNull(map.get("ACTION_CODE"));;          //交易代码
				String actionDate = CommonUtils.checkNull(map.get("ACTION_DATE"));;          //交易时间
				String dealerCode = CommonUtils.checkNull(map.get("RECEIVING_DEALER"));;          //经销商代码
				String dealerName = CommonUtils.checkNull(map.get("DEALER_NAME"));;          //经销商简称
				String paymentType = CommonUtils.checkNull(map.get("PAYMENT_TYPE"));;         //付款方式
				String model = CommonUtils.checkNull(map.get("GROUP_CODE"));;               //车型
				String modelYear = CommonUtils.checkNull(map.get("MODEL_YEAR"));;           //车型年
				String colourCode = CommonUtils.checkNull(map.get("COLOR_CODE"));;          //颜色
				String trimCode = CommonUtils.checkNull(map.get("TRIM_CODE"));;            //内饰
				String standardOptions = CommonUtils.checkNull(map.get("STANDARD_OPTION"));;     //标准配置
				String factoryOptions = CommonUtils.checkNull(map.get("FACTORY_OPTIONS"));;      //其他配置
				String localOptions = CommonUtils.checkNull(map.get("LOCAL_OPTION"));;     	 //本地配置
				String vehicleUsage = CommonUtils.checkNull(map.get("VEHICLE_USAGE_TYPE"));;      	//车辆用途
				Float wholesalePrice = Float.parseFloat(CommonUtils.checkNull(map.get("WHOLESALE_PRICE")) == "" ? "0" : CommonUtils.checkNull(map.get("WHOLESALE_PRICE")));		//中进采购价
				
				dto.setActionCode(actionCode);
				dto.setActionDate(actionDate);
				dto.setColourCode(colourCode);
				dto.setDealerCode(dealerCode);
				dto.setDealerName(dealerName);
				dto.setFactoryOptions(factoryOptions);
				dto.setLocalOptions(localOptions);
				dto.setModel(modelYear);
				dto.setModelYear(modelYear);
				dto.setPaymentType(paymentType);
				dto.setSequenceId(sequenceId);
				dto.setStandardOptions(standardOptions);
				dto.setTrimCode(trimCode);
				dto.setVehicleUsage(vehicleUsage);
				dto.setVin(vin);
				dto.setWholesalePrice(wholesalePrice);
				list.add(dto);
			}
		}
		return list;
	}

}
