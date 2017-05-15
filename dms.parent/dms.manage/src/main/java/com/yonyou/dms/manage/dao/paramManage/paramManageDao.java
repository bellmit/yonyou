package com.yonyou.dms.manage.dao.paramManage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.common.OemDictCodeConstants;

@Repository
public class paramManageDao extends OemBaseDAO {

	public PageInfoDto salesForecastProportionQuery(Map<String, String> param) {
		List<Object> queryParam = new ArrayList<>();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM (");
		sql.append("SELECT M.BRAND_ID, -- 品牌ID \n");
		sql.append("       M.BRAND_CODE, -- 品牌代码 \n");
		sql.append("       M.BRAND_NAME, -- 品牌名称 \n");
		sql.append("       M.SERIES_ID, -- 车系ID \n");
		sql.append("       M.SERIES_CODE, -- 车系代码 \n");
		sql.append("       M.SERIES_NAME, -- 车系名称 \n");
		sql.append("       (CASE WHEN PP.MACHT_LOWER IS NULL THEN '100%' \n");
		sql.append("             ELSE CONCAT(PP.MACHT_LOWER,'%') END) AS MACHT_LOWER, -- 匹配比例下限 \n");
		sql.append("       (CASE WHEN PP.MACHT_UPPER IS NULL THEN '100%' \n");
		sql.append("             ELSE CONCAT(PP.MACHT_UPPER,'%') END) AS MACHT_UPPER, -- 匹配比例上限 \n");
		sql.append("       PP.REMARK, -- 备注 \n");
		sql.append("       DATE_FORMAT(PP.UPDATE_DATE, '%Y-%m-%d') AS UPDATE_DATE -- 更新日期 \n");
		sql.append("  FROM ("+OemBaseDAO.getVwMaterialSql()+") M \n");
		sql.append("  LEFT JOIN TM_BUSINESS_PARAM_PERCENT PP ON PP.MATERIAL_GROUP_ID = M.SERIES_ID \n");
		sql.append("   AND (PP.PARA_TYPE = '" + OemDictCodeConstants.PARAM_TYPE_01 + "' OR PP.PARA_TYPE IS NULL) \n");
		sql.append(" WHERE M.GROUP_TYPE = '" + OemDictCodeConstants.GROUP_TYPE_DOMESTIC + "' \n");
		sql.append("   AND M.SERIES_STATUS = '" + OemDictCodeConstants.STATUS_ENABLE + "' \n");
		sql.append(" GROUP BY M.BRAND_ID, M.BRAND_CODE, M.BRAND_NAME, \n");
		sql.append("          M.SERIES_ID, M.SERIES_CODE, M.SERIES_NAME, \n");
		sql.append("          PP.MACHT_LOWER, PP.MACHT_UPPER, \n");
		sql.append("          PP.REMARK, PP.UPDATE_DATE \n");
		sql.append("           ) T");
		System.out.println("SQL\n===============\n"+sql+"\n=================");
		System.out.println("Params："+queryParam);
		return OemDAOUtil.pageQuery(sql.toString(), queryParam);
	}

	/**
	 * 销售预测比例下限
	 * @param seriesId
	 * @return
	 */
	public Map salesForecastProportionQuery(Long seriesId) {
		List<Object> queryParam = new ArrayList<>();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM (");
		sql.append("SELECT M.BRAND_ID, -- 品牌ID \n");
		sql.append("       M.BRAND_CODE, -- 品牌代码 \n");
		sql.append("       M.BRAND_NAME, -- 品牌名称 \n");
		sql.append("       M.SERIES_ID, -- 车系ID \n");
		sql.append("       M.SERIES_CODE, -- 车系代码 \n");
		sql.append("       M.SERIES_NAME, -- 车系名称 \n");
		sql.append("       (CASE WHEN PP.MACHT_LOWER IS NULL THEN '100' \n");
		sql.append("             ELSE PP.MACHT_LOWER  END) AS MACHT_LOWER, -- 匹配比例下限 \n");
		sql.append("       (CASE WHEN PP.MACHT_UPPER IS NULL THEN '100' \n");
		sql.append("             ELSE PP.MACHT_UPPER  END) AS MACHT_UPPER, -- 匹配比例上限 \n");
		sql.append("       PP.REMARK, -- 备注 \n");
		sql.append("       DATE_FORMAT(PP.UPDATE_DATE, '%Y-%m-%d') AS UPDATE_DATE -- 更新日期 \n");
		sql.append("  FROM ("+OemBaseDAO.getVwMaterialSql()+") M \n");
		sql.append("  LEFT JOIN TM_BUSINESS_PARAM_PERCENT PP ON PP.MATERIAL_GROUP_ID = M.SERIES_ID \n");
		sql.append("   AND (PP.PARA_TYPE = '" + OemDictCodeConstants.PARAM_TYPE_01 + "' OR PP.PARA_TYPE IS NULL) \n");
		sql.append(" WHERE M.GROUP_TYPE = '" + OemDictCodeConstants.GROUP_TYPE_DOMESTIC + "' \n");
		sql.append("   AND M.SERIES_STATUS = '" + OemDictCodeConstants.STATUS_ENABLE + "' \n");
		sql.append("   AND M.SERIES_ID = '"+seriesId+"'");
		sql.append(" GROUP BY M.BRAND_ID, M.BRAND_CODE, M.BRAND_NAME, \n");
		sql.append("          M.SERIES_ID, M.SERIES_CODE, M.SERIES_NAME, \n");
		sql.append("          PP.MACHT_LOWER, PP.MACHT_UPPER, \n");
		sql.append("          PP.REMARK, PP.UPDATE_DATE \n");
		sql.append("           ) T");
		System.out.println("SQL\n===============\n"+sql+"\n=================");
		System.out.println("Params："+queryParam);
		return OemDAOUtil.findFirst(sql.toString(), queryParam);
	}

	/**
	 * 配额转换率下限
	 * @param param
	 * @return
	 */
	public PageInfoDto quotaConversionRateQuery(Map<String, String> param) {
		List<Object> queryParam = new ArrayList<>();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM (");
		sql.append("SELECT M.BRAND_ID, -- 品牌ID \n");
		sql.append("       M.BRAND_CODE, -- 品牌代码 \n");
		sql.append("       M.BRAND_NAME, -- 品牌名称 \n");
		sql.append("       M.SERIES_ID, -- 车系ID \n");
		sql.append("       M.SERIES_CODE, -- 车系代码 \n");
		sql.append("       M.SERIES_NAME, -- 车系名称 \n");
		sql.append("       M.MODEL_ID, -- 车型ID \n");
		sql.append("       M.MODEL_CODE, -- CPOS \n");
		sql.append("       M.MODEL_NAME, -- CPOS描述 \n");
		sql.append("       (CASE WHEN PP.TRANS_LOWER_RATE IS NULL THEN '100%' \n");
		sql.append("             ELSE CONCAT(PP.TRANS_LOWER_RATE,'%') END) AS TRANS_LOWER_RATE, -- 转换率下限 \n");
		sql.append("       (CASE WHEN PP.TRANS_UPPER_RATE IS NULL THEN '100%' \n");
		sql.append("             ELSE CONCAT(PP.TRANS_UPPER_RATE,'%') END) AS TRANS_UPPER_RATE, -- 转换率上限 \n");
		sql.append("       PP.REMARK, -- 备注 \n");
		sql.append("       DATE_FORMAT(PP.UPDATE_DATE, '%Y-%m-%d') AS UPDATE_DATE -- 更新日期 \n");
		sql.append("  FROM ("+OemBaseDAO.getVwMaterialSql()+") M \n");
		sql.append("  LEFT JOIN TM_BUSINESS_PARAM_PERCENT PP ON PP.MATERIAL_GROUP_ID = M.MODEL_ID \n");
		sql.append("   AND (PP.PARA_TYPE = '" + OemDictCodeConstants.PARAM_TYPE_06 + "' OR PP.PARA_TYPE IS NULL) \n");
		sql.append(" WHERE M.GROUP_TYPE = '" + OemDictCodeConstants.GROUP_TYPE_DOMESTIC + "' \n");
		sql.append("   AND M.SERIES_STATUS = '" + OemDictCodeConstants.STATUS_ENABLE + "' \n");
		sql.append(" GROUP BY M.BRAND_ID, M.BRAND_CODE, M.BRAND_NAME, \n");
		sql.append("          M.SERIES_ID, M.SERIES_CODE, M.SERIES_NAME, \n");
		sql.append("          M.MODEL_ID, M.MODEL_CODE, M.MODEL_NAME, \n");
		sql.append("          PP.TRANS_LOWER_RATE, PP.TRANS_UPPER_RATE, \n");
		sql.append("          PP.REMARK, PP.UPDATE_DATE \n");
		sql.append("           ) T");
		System.out.println("SQL\n===============\n"+sql+"\n=================");
		System.out.println("Params："+queryParam);
		return OemDAOUtil.pageQuery(sql.toString(), queryParam);
	}
	
	public Map quotaConversionRateQuery(Long modelId) {
		List<Object> queryParam = new ArrayList<>();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM (");
		sql.append("SELECT M.BRAND_ID, -- 品牌ID \n");
		sql.append("       M.BRAND_CODE, -- 品牌代码 \n");
		sql.append("       M.BRAND_NAME, -- 品牌名称 \n");
		sql.append("       M.SERIES_ID, -- 车系ID \n");
		sql.append("       M.SERIES_CODE, -- 车系代码 \n");
		sql.append("       M.SERIES_NAME, -- 车系名称 \n");
		sql.append("       M.MODEL_ID, -- 车型ID \n");
		sql.append("       M.MODEL_CODE, -- CPOS \n");
		sql.append("       M.MODEL_NAME, -- CPOS描述 \n");
		sql.append("       (CASE WHEN PP.TRANS_LOWER_RATE IS NULL THEN '100' \n");
		sql.append("             ELSE PP.TRANS_LOWER_RATE  END) AS TRANS_LOWER_RATE, -- 转换率下限 \n");
		sql.append("       (CASE WHEN PP.TRANS_UPPER_RATE IS NULL THEN '100' \n");
		sql.append("             ELSE PP.TRANS_UPPER_RATE  END) AS TRANS_UPPER_RATE, -- 转换率上限 \n");
		sql.append("       PP.REMARK, -- 备注 \n");
		sql.append("       DATE_FORMAT(PP.UPDATE_DATE, '%Y-%m-%d') AS UPDATE_DATE -- 更新日期 \n");
		sql.append("  FROM ("+OemBaseDAO.getVwMaterialSql()+") M \n");
		sql.append("  LEFT JOIN TM_BUSINESS_PARAM_PERCENT PP ON PP.MATERIAL_GROUP_ID = M.MODEL_ID \n");
		sql.append("   AND (PP.PARA_TYPE = '" + OemDictCodeConstants.PARAM_TYPE_06 + "' OR PP.PARA_TYPE IS NULL) \n");
		sql.append(" WHERE M.GROUP_TYPE = '" + OemDictCodeConstants.GROUP_TYPE_DOMESTIC + "' \n");
		sql.append("   AND M.SERIES_STATUS = '" + OemDictCodeConstants.STATUS_ENABLE + "' \n");
		sql.append("   AND M.MODEL_ID = '"+modelId+"'");
		sql.append(" GROUP BY M.BRAND_ID, M.BRAND_CODE, M.BRAND_NAME, \n");
		sql.append("          M.SERIES_ID, M.SERIES_CODE, M.SERIES_NAME, \n");
		sql.append("          M.MODEL_ID, M.MODEL_CODE, M.MODEL_NAME, \n");
		sql.append("          PP.TRANS_LOWER_RATE, PP.TRANS_UPPER_RATE, \n");
		sql.append("          PP.REMARK, PP.UPDATE_DATE \n");
		sql.append("           ) T");
		System.out.println("SQL\n===============\n"+sql+"\n=================");
		System.out.println("Params："+queryParam);
		return OemDAOUtil.findFirst(sql.toString(), queryParam);
	}

	public List<Map> getDealerLeft(Long paraId) {
		List<Object> queryParam = new ArrayList<>();
		StringBuffer sql = new StringBuffer();
		sql.append(" select concat(d.DEALER_CODE,'：',d.DEALER_SHORTNAME) as data, d.DEALER_ID AS code from TM_DEALER d where d.dealer_id not in\n");
		sql.append(" (select group_ID from TM_BUSINESS_DATE_DEALER where PARA_ID=?) \n");
		sql.append(" and d.DEALER_TYPE = '"+OemDictCodeConstants.DEALER_TYPE_DVS+"' and  d.STATUS = '"+OemDictCodeConstants.STATUS_ENABLE+"' \n"); 
		sql.append(" order by d.DEALER_CODE \n");
		queryParam.add(paraId);
		return OemDAOUtil.findAll(sql.toString(), queryParam);
	}

	public List<Map> getDealerRight(Long paraId) {
		List<Object> queryParam = new ArrayList<>();
		StringBuffer sql = new StringBuffer();
		sql.append(" select concat(d.DEALER_CODE,'：',d.DEALER_SHORTNAME) as data, d.DEALER_ID AS code from TM_DEALER d where d.dealer_id in\n");
		sql.append(" (select group_ID from TM_BUSINESS_DATE_DEALER where PARA_ID=?) \n");
		sql.append(" and d.DEALER_TYPE = '"+OemDictCodeConstants.DEALER_TYPE_DVS+"' and  d.STATUS = '"+OemDictCodeConstants.STATUS_ENABLE+"' \n"); 
		sql.append(" order by d.DEALER_CODE \n");
		queryParam.add(paraId);
		return OemDAOUtil.findAll(sql.toString(), queryParam);
	}

	public List<Map> getOrgLeft(Long paraId) {
		List<Object> queryParam = new ArrayList<>();
		StringBuffer sql = new StringBuffer();
		sql.append("  select  A.ORG_ID as code , concat(A.ORG_CODE,'：', A.ORG_NAME) as data  \n");
		sql.append("   from TM_ORG A  WHERE A.ORG_LEVEL = '2'   AND A.DUTY_TYPE = '"+OemDictCodeConstants.DUTY_TYPE_LARGEREGION+"' AND A.BUSS_TYPE = '"+OemDictCodeConstants.ORG_BUSS_TYPE_01+"'  \n");
		sql.append("   AND A.ORG_ID not in ( select  t.GROUP_ID from TM_BUSINESS_DATE_DEALER t where PARA_ID= ? ) \n");
		queryParam.add(paraId);
		return OemDAOUtil.findAll(sql.toString(), queryParam);
	}
	
	public List<Map> getOrgRight(Long paraId) {
		List<Object> queryParam = new ArrayList<>();
		StringBuffer sql = new StringBuffer();
		sql.append("  select  A.ORG_ID as code , concat(A.ORG_CODE,'：', A.ORG_NAME) as data  \n");
		sql.append("   from TM_ORG A  WHERE A.ORG_LEVEL = '2'   AND A.DUTY_TYPE = '"+OemDictCodeConstants.DUTY_TYPE_LARGEREGION+"' AND A.BUSS_TYPE = '"+OemDictCodeConstants.ORG_BUSS_TYPE_01+"'  \n");
		sql.append("   AND A.ORG_ID in ( select  t.GROUP_ID from TM_BUSINESS_DATE_DEALER t where PARA_ID= ? ) \n");
		queryParam.add(paraId);
		return OemDAOUtil.findAll(sql.toString(), queryParam);
	}

}
