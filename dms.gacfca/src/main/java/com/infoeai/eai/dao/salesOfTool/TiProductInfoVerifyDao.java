package com.infoeai.eai.dao.salesOfTool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.infoeai.eai.common.EAIConstant;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 产品信息验证接口
 * @author luoyang
 *
 */
@Repository
public class TiProductInfoVerifyDao extends OemBaseDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(TiProductInfoVerifyDao.class);

	public Map<String, Object> beginAndEndDate() {
		StringBuffer sql=new StringBuffer();
		sql.append("select min(IFNULL(DATE_FORMAT(CREATE_DATE,'%Y-%m-%d %H:%i:%s'),'')) as BEGIN_DATE,max(IFNULL(DATE_FORMAT(CREATE_DATE,'%Y-%m-%d %H:%i:%s'),'')) as END_DATE  ");
		sql.append(" from TI_PRODUCT_INFO_VERIFY_DCS WHERE 1=1 and IS_SCAN='0'");
		List<Map> mapList = OemDAOUtil.findAll(sql.toString(), null);
		if(mapList != null && !mapList.isEmpty()){
			return mapList.get(0);
		}
		return null;
	}

	public List<Map> searchDataForJSON() {
		StringBuffer sql=new StringBuffer();
		sql.append("SELECT BRAND_ID as BrandID,BRAND_NAME as BrandName, \n");
		sql.append("SERIES_ID as SeriesID,SERIES_NAME as SeriesName,MODEL_ID as ModelID,\n");
		sql.append("MODEL_NAME as ModelName,PACKAGE_ID as CarStyleID,PACKAGE_NAME as CarStyleName,\n");
		sql.append("COLOR_CODE as ColorID,COLOR_NAME  as ColorName, \n");
		sql.append("DATE_FORMAT(CREATE_DATE,'%Y-%m-%d %H:%i:%s') as CreateDate,\n");
		sql.append("CASE STATUS WHEN 10011001 THEN 1 WHEN 10011002 THEN 0 ELSE 0 END as IsValid \n");
		sql.append("  FROM TI_PRODUCT_INFO_VERIFY_DCS where 1=1 and IS_SCAN='0'");
		return OemDAOUtil.findAll(sql.toString(), null);
	}

	public String JSONArrayToPO(JSONArray jsonArray, Map<String, String> pKey) {
		String resolveResult=EAIConstant.DEAL_FAIL;
		String productId="";
		for (Iterator<JSONObject> it = jsonArray.iterator(); it.hasNext();) {
			Map<String, Object> map = new HashMap<String, Object>();
			JSONObject jsonElement = JSONObject.fromObject(it.next());
			productId+=jsonElement.get("ProductID")+",";
		}
		productId="'"+productId.substring(0,productId.length()-1).replaceAll(",", "','")+"'";
		logger.info("productId is ++++++++++++++++++"+productId);
		List<Object> params = new ArrayList<>();
		StringBuffer sql=new StringBuffer();
		sql.append("INSERT INTO TI_PRODUCT_INFO_VERIFY_DCS(OEM_COMPANY_ID, BRAND_ID, BRAND_CODE, BRAND_NAME,\n");
		sql.append("SERIES_ID, SERIES_CODE, SERIES_NAME,\n");
		sql.append("MODEL_ID, MODEL_CODE, MODEL_NAME, PACKAGE_ID, PACKAGE_CODE,PACKAGE_NAME, MODEL_YEAR, STATUS,\n");
		sql.append("COLOR_CODE, COLOR_NAME, IS_SCAN ,CREATE_DATE, CREATE_BY) \n");
		sql.append("  SELECT tvm.MATERIAL_ID,tvmg1.TREE_CODE,tvmg1.GROUP_CODE AS BRAND_CODE,tvmg1.GROUP_NAME AS BRAND_NAME,\n");
		sql.append("tvmg2.TREE_CODE As SERIES_ID,tvmg2.GROUP_CODE AS SERIES_CODE,tvmg2.GROUP_NAME AS SERIES_NAME,\n");
		sql.append("tvmg3.TREE_CODE AS MODEL_ID,tvmg3.GROUP_CODE AS MODEL_CODE,tvmg3.GROUP_NAME AS MODEL_NAME,\n");
		sql.append("tvmg4.TREE_CODE AS PACKAGE_ID,tvmg4.GROUP_CODE AS PACKAGE_CODE,tvmg4.GROUP_NAME AS PACKAGE_NAME,\n");
		sql.append("tvmg4.MODEL_YEAR AS MODEL_YEAR,tvmg4.STATUS,\n");
		sql.append("tvm.COLOR_CODE,tvm.COLOR_NAME,0 as IS_SCAN,tvm.CREATE_DATE,12121211 as CREATE_BY  ");
		sql.append("  FROM TM_VHCL_MATERIAL tvm,\n");
		sql.append(" TM_VHCL_MATERIAL_GROUP_R tvmgr, \n");
		sql.append(" TM_VHCL_MATERIAL_GROUP tvmg4,\n");
		sql.append(" TM_VHCL_MATERIAL_GROUP tvmg3,\n");
		sql.append(" TM_VHCL_MATERIAL_GROUP tvmg2,\n");
		sql.append(" TM_VHCL_MATERIAL_GROUP tvmg1 \n");
		sql.append(" WHERE   tvm.MATERIAL_ID = tvmgr.MATERIAL_ID \n");
		sql.append("  AND tvmgr.GROUP_ID = tvmg4.GROUP_ID   \n");
		sql.append("  AND tvmg4.PARENT_GROUP_ID = tvmg3.GROUP_ID \n");
		sql.append("  AND tvmg3.PARENT_GROUP_ID = tvmg2.GROUP_ID \n");
		sql.append("  AND tvmg2.PARENT_GROUP_ID = tvmg1.GROUP_ID \n");
		sql.append("  AND tvmg4.STATUS=10011001 ");
		sql.append("  AND tvm.COMPANY_ID = 2010010100070674 ");
		sql.append("  AND tvmg4.TREE_CODE not in("+productId+")");
		try {
			OemDAOUtil.execBatchPreparement(sql.toString(), params);
			resolveResult=EAIConstant.DEAL_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resolveResult;
	}

}
