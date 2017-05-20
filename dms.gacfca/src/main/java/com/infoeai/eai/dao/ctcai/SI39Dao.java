package com.infoeai.eai.dao.ctcai;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.infoeai.eai.DTO.SI39DTO;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.utils.common.CommonUtils;

@Repository
public class SI39Dao extends OemBaseDAO {

	public static List<SI39DTO> getSI39Info() {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT POS.SERIAL_NUMBER, -- 生产序列号   \n");
		sql.append("       TVMF.TASK_ID, -- 生产任务编号  \n");
		sql.append("       DATE_FORMAT(POS.CREATE_DATE,'%Y-%m-%d') CREATE_DATE, -- 日期  \n");
		sql.append("       TM.CTCAI_CODE,    -- 中进代码  \n");
		sql.append("       TM.COMPANY_NAME,     -- 公司名称  \n");
		sql.append("       TVMF.FORECAST_YEAR,     -- 年  \n");
		sql.append("       TVMF.FORECAST_MONTH,    -- 月  \n");
		sql.append("       VM.MODEL_CODE || '-8BL' MODEL_CODE,-- 车型  \n");
		sql.append("       VM.COLOR_CODE,    -- 颜色  \n");
		sql.append("       VM.TRIM_CODE,     -- 内饰  \n");
		sql.append("       POS.ORDER_SERIAL_NUMBER_ID -- 主键  \n");
		sql.append("FROM PRO_ORDER_SERIAL POS  \n");
		sql.append("    INNER JOIN TT_VS_MONTHLY_FORECAST_DETAIL_DCS_COLOR TVMFDC ON POS.DETAIL_COLOR_ID = TVMFDC.DETAIL_COLOR_ID  \n");
		sql.append("    INNER JOIN TT_VS_MONTHLY_FORECAST_DETAIL_DCS TVMFD ON TVMFD.DETAIL_ID = TVMFDC.DETAIL_ID  \n");
		sql.append("    INNER JOIN TT_VS_MONTHLY_FORECAST TVMF ON TVMF.FORECAST_ID = TVMFD.FORECAST_ID  \n");
		sql.append("    INNER JOIN TM_DEALER TD ON TVMF.DEALER_ID = TD.DEALER_ID  \n");
		sql.append("    INNER JOIN TM_COMPANY TM ON TD.DEALER_CODE = TM.COMPANY_CODE  \n");
		sql.append("    INNER JOIN ("+getVwMaterialSql()+") VM ON TVMFDC.MATERIAL_ID = VM.MATERIAL_ID  \n");
		sql.append("WHERE POS.IS_SEND IS NULL OR POS.IS_SEND = 0  \n");
		List<Map> mapList = OemDAOUtil.findAll(sql.toString(), null);
		return setSI39DTOList(mapList);
	}

	private static List<SI39DTO> setSI39DTOList(List<Map> mapList) {
		List<SI39DTO> resultList = new ArrayList<>();
		if(mapList != null && !mapList.isEmpty()){
			for(Map map : mapList){
				SI39DTO dto = new SI39DTO();
				String serialNumber = CommonUtils.checkNull(map.get("SERIAL_NUMBER"));//生产序列号
			    String taskId = CommonUtils.checkNull(map.get("TASK_ID"));//生产任务编号
			    String createDate = CommonUtils.checkNull(map.get("CREATE_DATE"));//日期
			    String ctcaiCode = CommonUtils.checkNull(map.get("CTCAI_CODE"));//中进代码
			    String companyName = CommonUtils.checkNull(map.get("COMPANY_NAME"));//公司名称
			    String forecastYear = CommonUtils.checkNull(map.get("FORECAST_YEAR"));//年
			    String forecastMonth = CommonUtils.checkNull(map.get("FORECAST_MONTH"));//月
			    String modelCode = CommonUtils.checkNull(map.get("MODEL_CODE"));//车型
			    String colorCode = CommonUtils.checkNull(map.get("COLOR_CODE"));//颜色
			    String trimCode = CommonUtils.checkNull(map.get("COLOR_CODE"));//内饰
			    Long orderSerialNumberId = Long.parseLong(CommonUtils.checkNull(map.get("ORDER_SERIAL_NUMBER_ID")));//主键
			    
			    dto.setColorCode(colorCode);
			    dto.setCompanyName(companyName);
			    dto.setCreateDate(createDate);
			    dto.setCtcaiCode(ctcaiCode);
			    dto.setForecastMonth(forecastMonth);
			    dto.setForecastYear(forecastYear);
			    dto.setModelCode(modelCode);
			    dto.setOrderSerialNumberId(orderSerialNumberId);
			    dto.setSerialNumber(serialNumber);
			    dto.setTaskId(taskId);
			    dto.setTrimCode(trimCode);
			    resultList.add(dto);
			}
		}
		return resultList;
	}

}
