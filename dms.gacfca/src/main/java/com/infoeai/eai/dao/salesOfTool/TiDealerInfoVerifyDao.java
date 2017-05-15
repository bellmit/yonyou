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
 * 经销商信息验证接口
 * @author luoyang
 *
 */
@Repository
public class TiDealerInfoVerifyDao extends OemBaseDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(TiDealerInfoVerifyDao.class);

	public Map<String, Object> beginAndEndDate() {
		StringBuffer sql=new StringBuffer();
		sql.append("select min(IFNULL(DATE_FORMAT(CREATE_DATE,'%Y-%m-%d %H:%i:%s'),'')) as BEGIN_DATE,max(IFNULL(DATE_FORMAT(CREATE_DATE,'%Y-%m-%d %H:%i:%s'),'')) as END_DATE  ");
		sql.append(" from TI_DEALER_INFO_VERIFY_DCS WHERE 1=1 and IS_SCAN='0'");
		List<Map> mapList = OemDAOUtil.findAll(sql.toString(), null);
		if(mapList != null && !mapList.isEmpty()){
			return mapList.get(0);
		}
		return null;
	}

	public List<Map> searchDataForJSON() {
		StringBuffer sql = new StringBuffer("");		
		sql.append(" SELECT  IFNULL(TC.LMS_CODE,'') as DealerCode, \n");
		sql.append("         TDI.DEALER_NAME as CompanyName, \n");
		sql.append("         IFNULL(TDI.DEALERAB_CN,'') as Dealer_AB_CN, \n");
		//sql.append("         IFNULL(TDI.DEALERAB_EN,'')as DealerabEN, \n");
		sql.append("         IFNULL((select REGION_ID from TM_REGION_DCS where region_code = TDI.CITY_ID limit 1),0) as ProvinceID, \n");
		sql.append("         IFNULL((select REGION_ID from TM_REGION_DCS where region_code = TDI.REALLY_CITY_ID limit 1),0)  as CityID, \n");
		sql.append("         IFNULL(TDI.ADDRESS,'') as Address, \n");
		sql.append("         IFNULL(TDI.SERVICE_TEL,'') as ServiceTel, \n");
		sql.append("         CASE TDI.STATUS WHEN 10011001 THEN 1 WHEN 10011002 THEN 0 ELSE 0 END as DealerStatus, \n");
		sql.append("         DATE_FORMAT(TDI.CREATE_DATE,'%Y-%m-%d %H:%i:%s')  as CreateDate \n");
		sql.append("    FROM TI_DEALER_INFO_VERIFY_DCS TDI \n");
		sql.append("       LEFT JOIN TM_COMPANY TC ON  TC.COMPANY_CODE = TDI.DEALER_CODE \n" );
		sql.append("   WHERE (tdi.IS_SCAN = '0' OR tdi.IS_SCAN IS NULL) \n");
		return OemDAOUtil.findAll(sql.toString(), null);
	}

	public String JSONArrayToPO(JSONArray jsonArray, Map<String, String> pKey) {
		String resolveResult=EAIConstant.DEAL_FAIL;
		String dealer="";
		for (Iterator<JSONObject> it = jsonArray.iterator(); it.hasNext();) {
			Map<String, Object> map = new HashMap<String, Object>();
			JSONObject jsonElement = JSONObject.fromObject(it.next());
			dealer+=jsonElement.get("DealerCode")+",";
		}
		dealer="'"+dealer.substring(0,dealer.length()-1).replaceAll(",", "','")+"'";
		List<Object> params = new ArrayList<>();
		StringBuffer sql=new StringBuffer();
		sql.append("insert into TI_DEALER_INFO_VERIFY_DCS(DEALER_CODE, DEALER_NAME, DEALERAB_CN, DEALERAB_EN, CITY_ID, REALLY_CITY_ID, ADDRESS, SERVICE_TEL, STATUS, IS_SCAN, create_date,create_by) ");
		sql.append("  select COMPANY_CODE as DEALER_CODE,COMPANY_SHORTNAME as DEALER_NAME, COMPANY_NAME as DEALERAB_CN, IFNULL(COMPANY_EN,'') as DEALERAB_EN , PROVINCE_ID as CITY_ID, CITY_ID as REALLY_CITY_ID, IFNULL(ADDRESS,''), IFNULL(PHONE,'') as SERVICE_TEL,STATUS,'0' as IS_SCAN ,CREATE_DATE,111222222");
		sql.append(" from TM_COMPANY  ");
		sql.append("where 1=1 ");
		sql.append(" AND COMPANY_CODE not in("+dealer+")");
		try {
			//开启事物		
			OemDAOUtil.execBatchPreparement(sql.toString(), params);
			resolveResult=EAIConstant.DEAL_SUCCESS;
		} catch (Exception e) {
			logger.info("=============insert TI_DEALER_INFO_VERIFY 异常"+e.getMessage());
			e.printStackTrace();
		}
		return resolveResult;
	}

}
