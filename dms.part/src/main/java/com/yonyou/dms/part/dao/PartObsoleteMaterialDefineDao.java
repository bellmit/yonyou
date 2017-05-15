package com.yonyou.dms.part.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.part.domains.DTO.basedata.TtObsoleteMaterialDefineDcsDTO;
import com.yonyou.dms.part.domains.PO.partBaseInfoManage.TtObsoleteMaterialDefineDcsPO;

/**
 * 呆滞品定义Dao
 * @author ZhaoZ
 * @date 2017年3月24日
 */
@Repository
public class PartObsoleteMaterialDefineDao extends OemBaseDAO{

	/**
	 * 查询呆滞品定义Lists
	 * @param queryParams
	 * @return
	 */
	public PageInfoDto defineQuery(Map<String, String> queryParams) {
		StringBuffer sql=new StringBuffer();
		sql.append("\n SELECT MD.DEFINE_ID, \n");
		sql.append("          MD.OM_NAME,\n");
		sql.append("          MD.OVERSTOCK_MONTH, \n");
		sql.append("          MD.RELEASE_MONTH, \n");
		sql.append("          CASE WHEN (MD.IS_SEND ='0') THEN '未下发' ELSE '已下发' END AS IS_SEND,\n");
		sql.append("          DATE_FORMAT(MD.SEND_DATE,'%Y-%m-%d %h:%i:%s') SEND_DATE,\n");
		sql.append("          MD.REMARK, \n");
		sql.append("          TU1.NAME CREATE_BY, \n");
		sql.append("          DATE_FORMAT(MD.CREATE_DATE,'%Y-%m-%d %h:%i:%s') CREATE_DATE,\n");
		sql.append("          TU2.NAME UPDATE_BY, \n");
		sql.append("          DATE_FORMAT(MD.UPDATE_DATE,'%Y-%m-%d %h:%i:%s') UPDATE_DATE, \n");
		sql.append("          MD.IS_DEL \n");
		sql.append("FROM TT_OBSOLETE_MATERIAL_DEFINE_dcs MD \n");
		sql.append("LEFT JOIN TC_USER TU1 ON TU1.USER_ID=MD.CREATE_BY \n");
		sql.append("LEFT JOIN TC_USER TU2 ON TU2.USER_ID=MD.UPDATE_BY \n");
		return OemDAOUtil.pageQuery(sql.toString(), null);
	}

	/**
	 * 呆滞品定义修改
	 * @param id
	 * @param queryParams
	 */
	public void updatePartObsolete(Long id, TtObsoleteMaterialDefineDcsDTO dto) {
		LoginInfoDto loginUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		TtObsoleteMaterialDefineDcsPO po = TtObsoleteMaterialDefineDcsPO.findById(id);
		boolean flag = false;
		if(po!=null){
			SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			long time= System.currentTimeMillis();
			try {
				Date date = sdf.parse(sdf.format(new Date(time)));
				java.sql.Timestamp st = new java.sql.Timestamp(date.getTime());
				po.setTimestamp("UPDATE_DATE", st);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			po.setInteger("IS_SEND", 0);
			po.setLong("UPDATE_BY", loginUser.getUserId());
		    if(!StringUtils.isNullOrEmpty(dto.getRemark())){ 
		    	po.setString("REMARK", dto.getRemark());
	        }else{
	        	po.setString("REMARK","");
	        }
			po.setString("OM_NAME", dto.getDefineName());
			po.setDate("SEND_DATE", null);
			po.setInteger("OVERSTOCK_MONTH", dto.getOverstockMonth());
			po.setInteger("RELEASE_MONTH", dto.getReleaseMonth());
			flag = po.saveIt();
			if(flag){			
			}else{
				throw new ServiceBizException("修改失败");
			}
		}
	}

	/**
	 * 呆滞品查询
	 * @param queryParams
	 */
	public PageInfoDto getALLList(Map<String, String> queryParams) {
		List<Object> params = new ArrayList<Object>();
		String sql = getALLListSql(queryParams, params);
		return OemDAOUtil.pageQuery(sql, params);
	}

	/**
	 * 呆滞品查询SQL
	 * @param queryParams
	 * @param params
	 * @return
	 */
	private String getALLListSql(Map<String, String> queryParams, List<Object> params) {
		StringBuffer sql=new StringBuffer();
		sql.append("   SELECT TOR2.ORG_NAME ORG_NAME2,		   \n");
		sql.append("   TOR.ORG_NAME ORG_NAME, 		 \n");
		sql.append("   TD.DEALER_CODE  DDEALER_CODE,	TD.DEALER_ID,	  \n");
		sql.append("   TD.DEALER_SHORTNAME DDEALER_SHORTNAME, 		\n");
		sql.append("   TPDS.STORAGE_CODE, 		\n");
		sql.append("   TPDS.STORAGE_NAME, 		\n");
		sql.append("   TP.PART_CODE,		\n");
		sql.append("   TP.PART_NAME,	 \n");
		sql.append("   TPDS.OH_COUNT,        	\n");
		sql.append("   TPDS.SALES_PRICE,              	\n");
		sql.append("   TPDS.OH_COUNT*TPDS.SALES_PRICE  TOTALS,     \n");
		sql.append("   DATE_FORMAT(TPDS.LAST_DATE,'%Y-%m-%d %h:%i:%s') LAST_DATE,     	\n");
		sql.append("   TIMESTAMPDIFF (DAY,TPDS.LAST_DATE,CURRENT_TIMESTAMP)\n");
		sql.append("   INTERVALDATE   \n");
		sql.append("   FROM TT_PT_DEALER_STOCK_dcs TPDS \n");
		sql.append("   LEFT JOIN TT_PT_PART_BASE_dcs TP \n");
		sql.append("   ON TP.PART_CODE = TPDS.PART_NO \n");
		sql.append("   LEFT JOIN TM_DEALER TD\n");
		sql.append("   ON  TD.DEALER_CODE=replace(TPDS.DEALER_CODE,'A','')||'A' \n");
		sql.append("   LEFT JOIN  TM_DEALER_ORG_RELATION TDOR		\n");
		sql.append("   ON TD.DEALER_ID = TDOR.DEALER_ID	\n");
		sql.append("   LEFT JOIN  TM_ORG TOR		\n");
		sql.append("   ON  TDOR.ORG_ID = TOR.ORG_ID	\n");
		sql.append("   LEFT JOIN  TM_ORG TOR2		\n");
		sql.append("   ON TOR.PARENT_ORG_ID = TOR2.ORG_ID		\n");
		sql.append("    WHERE  		\n");
		sql.append("   TIMESTAMPDIFF (MONTH,TPDS.LAST_DATE,CURRENT_TIMESTAMP)>= 		\n");
		sql.append("  (		\n");
		sql.append("    SELECT  TOMD.OVERSTOCK_MONTH FROM  TT_OBSOLETE_MATERIAL_DEFINE_dcs TOMD	\n");
		sql.append("  )		\n");
		if(!StringUtils.isNullOrEmpty(queryParams.get("bigArea"))){ 
			sql.append(" 					AND TOR2.ORG_ID = ? \n");
        	params.add(queryParams.get("bigArea"));
        }
		if(!StringUtils.isNullOrEmpty(queryParams.get("smallArea"))){ 
			sql.append(" 					AND TOR.ORG_ID = ? \n");
        	params.add(queryParams.get("smallArea"));
        }
		if(!StringUtils.isNullOrEmpty(queryParams.get("dealerCode"))){ 
			String[] dealerCodes=queryParams.get("dealerCode").split(",");
	   		String code = "";
   			for(int i=0;i<dealerCodes.length;i++){
   				code+=""+dealerCodes[i]+",";
   				code=code.substring(0,code.length()-1);		
   			}
			sql.append(" 				AND	TD.DEALER_CODE IN (?)  \n");
	        params.add(code);
	    }
		if(!StringUtils.isNullOrEmpty(queryParams.get("partCode"))){ 
			sql.append(" 					AND TP.PART_CODE = ? \n");
        	params.add(queryParams.get("partCode"));
        }
		if(!StringUtils.isNullOrEmpty(queryParams.get("partName"))){ 
			sql.append(" 					AND TP.PART_NAME = ? \n");
        	params.add(queryParams.get("partName"));
        }
		if(!StringUtils.isNullOrEmpty(queryParams.get("beginDate"))){ 
		     
			sql.append(" AND  DATE_FORMAT(TPDS.LAST_DATE,'%Y-%m-%d') >='" + queryParams.get("beginDate") +"' \n");
        }
        if(!StringUtils.isNullOrEmpty(queryParams.get("endDate"))){ 
        	
        	sql.append(" AND  DATE_FORMAT(TPDS.LAST_DATE,'%Y-%m-%d') <='" + queryParams.get("endDate") +"' \n");
        }
		if(!StringUtils.isNullOrEmpty(queryParams.get("intervalDate"))){ 
			sql.append(" 					AND TIMESTAMPDIFF  (DAY,TPDS.LAST_DATE,CURRENT_TIMESTAMP) >= ? \n");
        	params.add(queryParams.get("intervalDate"));
        }
		return sql.toString();
	}

	/**
	 * 
	 */
	public List<Map> queryDownLoadList(Map<String, String> queryParams) {
		List<Object> params = new ArrayList<Object>();
		String sql = getALLListSql(queryParams, params);
		return OemDAOUtil.findAll(sql, params);
	}

}
