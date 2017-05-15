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
import com.yonyou.dcs.util.DEConstant;
import com.yonyou.dms.common.domains.PO.basedata.TiDealerSalerMapDcsPO;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 销售人员信息验证
 * @author luoyang
 *
 */
@Repository
public class TiSalerInfoVerifyDao extends OemBaseDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(TiSalerInfoVerifyDao.class);

	public Map<String, Object> beginAndEndDate() {
		StringBuffer sql=new StringBuffer();
		sql.append("select min(IFNULL(DATE_FORMAT(CREATE_DATE,'%Y-%m-%d %H:%i:%s'),'')) as BEGIN_DATE,max(IFNULL(DATE_FORMAT(CREATE_DATE,'%Y-%m-%d %H:%i:%s'),'')) as END_DATE  ");
		sql.append(" from TI_SALEER_INFO_VERIFY_DCS WHERE 1=1 and is_send='0'");
		List<Map> mapList = OemDAOUtil.findAll(sql.toString(), null);
		if(mapList != null && !mapList.isEmpty()){
			return mapList.get(0);
		}
		return null;
	}

	public List<Map> searchDataForJSON() {
		StringBuffer sql=new StringBuffer();
		sql.append(" select  IFNULL(UNIQUENESS_ID,'') as UniquenessID, USER_ID as UserID,");
		sql.append("DR.DCS_CODE as DealerCode, USER_NAME as UserName,IFNULL(ROLE_ID,'') as RoleID, IFNULL(EMAIL,'') as Email, IFNULL(MOBILE,'') as Mobile, IFNULL(ROLE_NAME,'') as RoleName, IFNULL(IS_DCC_VIEW,'') as IsDccView, IFNULL(USER_STATUS,'') as UserStatus ");
/*		sql.append("IFNULL(to_char(CREATE_DATE,'yyyy-mm-dd hh24:mi:ss'),'') as CREATE_DATE,");
		sql.append("IFNULL(to_char(UPDATE_DATE,'yyyy-mm-dd hh24:mi:ss'),'') as UPDATE_DATE ");*/
		sql.append("  FROM TI_SALEER_INFO_VERIFY_DCS TSIV ");
		sql.append("   LEFT JOIN TI_DEALER_RELATION DR ON DR.DMS_CODE=TSIV.DEALER_CODE  \n" );
		sql.append("where 1=1 and is_send='0'");
		return OemDAOUtil.findAll(sql.toString(), null);
	}

	public String JSONArrayToPO(JSONArray jsonArray, Map<String, String> pKey) throws Exception {
		String resolveResult=EAIConstant.DEAL_FAIL;
		try {
			if(jsonArray != null && jsonArray.size()>0){
				for (Iterator<JSONObject> it = jsonArray.iterator(); it.hasNext();) {
					Map<String, Object> map = new HashMap<String, Object>();
					JSONObject jsonElement = JSONObject.fromObject(it.next());
					TiDealerSalerMapDcsPO po=new TiDealerSalerMapDcsPO();
					po.setString("DEALER_CODE",jsonElement.get("DealerCode").toString());
					po.setString("USER_ID",jsonElement.get("UserID").toString());
					boolean flag = po.saveIt();
				}
			}else{
				throw new Exception("jsonArray is null ");
			}
			logger.info("=============insert TiDealerSalerMap success！");
		} catch (Exception e) {
			logger.info("=============insert TiDealerSalerMap 异常"+e);
			throw e;
		} finally{
			
		}
				
		resolveResult=EAIConstant.DEAL_SUCCESS;
		return resolveResult;
	}
	
	public String JSONArrayToPO1(JSONArray jsonArray, Map<String, String> pKey) throws Exception {
		String resolveResult=EAIConstant.DEAL_FAIL;
		List<Object> params = new ArrayList<>();
		StringBuffer sql=new StringBuffer();
		sql.append("insert into TI_SALEER_INFO_VERIFY_DCS( UNIQUENESS_ID, USER_ID, DEALER_CODE, USER_NAME, ROLE_ID, EMAIL, MOBILE, ROLE_NAME, IS_DCC_VIEW, USER_STATUS, IS_SEND, CREATE_DATE, CREATE_BY)");
		sql.append("  select tsp.UNIQUENESS_ID, tsp.USER_ID, tsp.DEALER_CODE, tsp.USER_NAME, tsp.ROLE_ID, tsp.EMAIL, tsp.MOBILE, tsp.ROLE_NAME, tsp.IS_DCC_VIEW, tsp.USER_STATUS,'0' as IS_SEND,NOW(),"+DEConstant.DE_CREATE_BY);
		sql.append("  from TM_SALES_PERSONNEL_DCS tsp");
		sql.append("  where  ");
		sql.append("   not exists (");
		sql.append(" select 1 from TI_DEALER_SALER_MAP_DCS dsm");
		sql.append(" where tsp.USER_ID = dsm.user_id and tsp.dealer_CODE  = dsm.dealer_code");
		sql.append(")");
		OemDAOUtil.execBatchPreparement(sql.toString(), params);
		
		TiDealerSalerMapDcsPO.deleteAll();
				
		resolveResult=EAIConstant.DEAL_SUCCESS;
		return resolveResult;
	}

}
