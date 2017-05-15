package com.infoeai.eai.dao.ctcai;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.infoeai.eai.DTO.Dcs2Ctcai02DTO;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.CommonUtils;

@Repository
public class SI31Dao extends OemBaseDAO {

	public static List<Dcs2Ctcai02DTO> getSI31Info(String type) {
		StringBuffer sql = new StringBuffer("");		
		sql.append(" SELECT tsi.SEQUENCE_ID,tsi.VIN, \n");
		sql.append("        'DDCX' ACTION_CODE, \n");
		sql.append("        DATE_FORMAT (tsi.CREATE_DATE, '%Y%m%d') ACTION_DATE, \n");
		sql.append("        DATE_FORMAT (tsi.CREATE_DATE, '%H%i%S') ACTION_TIME, \n");
		sql.append("        (SELECT ctcai_code FROM TM_COMPANY WHERE company_code = tsi.DEALER_CODE) ORDER_DEALER, \n");
		sql.append("        (SELECT DEALER_NAME FROM TM_DEALER WHERE dealer_code = tsi.DEALER_CODE) DEALER_NAME, \n");
		sql.append("        model.GROUP_CODE||'-8BL' GROUP_CODE, \n");
		sql.append("        tvmg.MODEL_YEAR, \n");
		sql.append("        tvm.COLOR_CODE, \n");
		sql.append("        tvm.TRIM_CODE, \n");
		sql.append("        tvmg.STANDARD_OPTION, \n");
		sql.append("        tvmg.FACTORY_OPTIONS, \n");
		sql.append("        tvmg.LOCAL_OPTION \n");
		sql.append("   FROM TI_FUTUREORDER_CANCEL_IMPORT_DCS tsi,  \n");
		sql.append("        TM_VEHICLE_DEC tmv,  \n");
		sql.append("        TM_VHCL_MATERIAL tvm,  \n");
		sql.append("        TM_VHCL_MATERIAL_GROUP_R tvmgr,  \n");
		sql.append("        TM_VHCL_MATERIAL_GROUP tvmg, \n");
		sql.append("        TM_VHCL_MATERIAL_GROUP model \n");
		sql.append("        ,TM_VHCL_MATERIAL_GROUP MG2 \n");//edit 只查询进口车  by weixia 2015-08-05 
		sql.append("  WHERE (tsi.IS_SCAN = '0' OR tsi.IS_SCAN IS NULL) \n");
		sql.append("    and tmv.vin = tsi.VIN \n");
		sql.append("    and tvm.MATERIAL_ID = tmv.MATERIAL_ID \n");
		sql.append("    and tvmgr.MATERIAL_ID = tvm.MATERIAL_ID \n");
		sql.append("    and tvmg.GROUP_ID = tvmgr.GROUP_ID \n");
		sql.append("    and model.GROUP_ID = tvmg.PARENT_GROUP_ID \n");
		
		/** edit 只查询进口车  by weixia 2015-08-05 start **/
		sql.append("    AND MG2.GROUP_ID = model.PARENT_GROUP_ID \n");	
		sql.append("    AND MG2.GROUP_TYPE = '"+OemDictCodeConstants.GROUP_TYPE_IMPORT+"' \n");
		/** edit 只查询进口车  by weixia 2015-08-05 end **/
		List<Map> mapList = OemDAOUtil.findAll(sql.toString(), null);
		return setDcs2Ctcai02DTOList(mapList);
	}

	private static List<Dcs2Ctcai02DTO> setDcs2Ctcai02DTOList(List<Map> mapList) {
		List<Dcs2Ctcai02DTO> resultList = new ArrayList<>();
		if(mapList != null && !mapList.isEmpty()){
			for(Map map : mapList){
				Dcs2Ctcai02DTO dto = new Dcs2Ctcai02DTO();
				Long sequenceId = Long.parseLong(CommonUtils.checkNull(map.get("SEQUENCE_ID")) == "" ? "0" : CommonUtils.checkNull(map.get("SEQUENCE_ID")));            //序列号
				String vin = CommonUtils.checkNull(map.get("VIN"));                 //VIN号
				String actionCode = CommonUtils.checkNull(map.get("ACTION_CODE"));          //交易代码
				String actionDate = CommonUtils.checkNull(map.get("ACTION_DATE"));          //交易时间
				String actionTime = CommonUtils.checkNull(map.get("ACTION_TIME"));          //交易时间
				String dealerCode = CommonUtils.checkNull(map.get("ORDER_DEALER"));          //经销商代码
				String dealerName = CommonUtils.checkNull(map.get("DEALER_NAME"));          //经销商简称
				String finalNetAmount = CommonUtils.checkNull(map.get(""));      //最终应付车价
				String model = CommonUtils.checkNull(map.get("GROUP_CODE"));               //车型
				String modelYear = CommonUtils.checkNull(map.get("GROUP_CODE"));           //车型年
				String colourCode = CommonUtils.checkNull(map.get("COLOR_CODE"));          //颜色
				String trimCode = CommonUtils.checkNull(map.get("TRIM_CODE"));            //内饰
				String standardOptions = CommonUtils.checkNull(map.get("STANDARD_OPTION"));     //标准配置
				String factoryOptions = CommonUtils.checkNull(map.get("FACTORY_OPTIONS"));      //其他配置
				String localOptions = CommonUtils.checkNull(map.get("LOCAL_OPTION"));     	 //本地配置
				
				dto.setActionCode(actionCode);
				dto.setActionDate(actionDate);
				dto.setActionTime(actionTime);
				dto.setColourCode(colourCode);
				dto.setDealerCode(dealerCode);
				dto.setDealerName(dealerName);
				dto.setFactoryOptions(factoryOptions);
				dto.setFinalNetAmount(finalNetAmount);
				dto.setLocalOptions(localOptions);
				dto.setModel(model);
				dto.setModelYear(modelYear);
				dto.setSequenceId(sequenceId);
				dto.setStandardOptions(standardOptions);
				dto.setTrimCode(trimCode);
				dto.setVin(vin);
				resultList.add(dto);
			}
		}
		return resultList;
	}

}
