package com.yonyou.dcs.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.DTO.gacfca.TiAppUSalesQuotasDto;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.utils.common.CommonUtils;

/**
 * 销售人员分配信息APP更新
 * @author luoyang
 *
 */
@Repository
public class TiAppUSalesQuotasDao extends OemBaseDAO {
	
	/**
	 * 获取经销商业务范围
	 * @param dealerCode
	 * @return
	 */
	public String[] getDealerByGroupId(String[] groupID,String dealerCode) {
		String[] groupIds = null;
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT DISTINCT T1.GROUP_ID FROM TM_DEALER_BUSS t1 , TM_DEALER t2 WHERE  t1.DEALER_ID = t2.DEALER_ID AND T2.DEALER_CODE = '" + dealerCode + "' ");
		if(null!= groupID && groupID.length>0){
			sql.append(" AND T1.GROUP_ID IN( ");
			for (int i = 0; i < groupID.length; i++) {
				if (i == groupID.length - 1) {
					sql.append(" '" + groupID[i] + "'");
				} else {
					sql.append(" '" + groupID[i] + "',");
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

	public LinkedList<TiAppUSalesQuotasDto> queryBodyInfo(String[] groupId) throws ParseException {
		StringBuffer sql = this.getSql();
		List<Map> mapList = OemDAOUtil.findAll(sql.toString(), null);
		LinkedList<TiAppUSalesQuotasDto> list = this.setTiAppUSalesQuotasDtoList(mapList);
		return list;
	}

	public LinkedList<TiAppUSalesQuotasDto> queryBodyInfo() throws ParseException {
		StringBuffer sql = this.getSql();
		List<Map> mapList = OemDAOUtil.findAll(sql.toString(), null);
		LinkedList<TiAppUSalesQuotasDto> list = this.setTiAppUSalesQuotasDtoList(mapList);
		return list;
	}
	
	private LinkedList<TiAppUSalesQuotasDto> setTiAppUSalesQuotasDtoList(List<Map> mapList) throws ParseException {
		LinkedList<TiAppUSalesQuotasDto> resultList = new LinkedList<TiAppUSalesQuotasDto>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(mapList != null && !mapList.isEmpty()){
			for(Map map : mapList){
				TiAppUSalesQuotasDto dto = new TiAppUSalesQuotasDto();
				Long salesQuotasId = CommonUtils.checkNull(map.get("SALES_QUOTAS_ID")) == "" ? null : Long.parseLong(CommonUtils.checkNull(map.get("SALES_QUOTAS_ID")));
				String uniquenessID = map.get("UNIQUENESS_ID") == null ? null : CommonUtils.checkNull(map.get("UNIQUENESS_ID"));//DMS客户唯一ID
			    Integer FCAID = CommonUtils.checkNull(map.get("FCA_ID")) == "" ? null : Integer.parseInt(CommonUtils.checkNull(map.get("FCA_ID")));//售中客户唯一标识
			    String oldDealerUserID = map.get("OLD_DEALER_USER_ID") == null ? null : CommonUtils.checkNull(map.get("OLD_DEALER_USER_ID"));//销售人员的ID//原销售人员ID
			    String dealerUserID = map.get("DEALER_USER_ID") == null ? null : CommonUtils.checkNull(map.get("DEALER_USER_ID"));//销售人员的ID
			    String dealerCode = map.get("OLD_DEALER_USER_ID") == null ? null : CommonUtils.checkNull(map.get("OLD_DEALER_USER_ID"));
			    Date updateDate = CommonUtils.checkNull(map.get("UPDATE_DATE")) == "" ? null : sdf.parse(CommonUtils.checkNull(map.get("UPDATE_DATE")));//更新时间
				
			    dto.setSalesQuotasId(salesQuotasId);
				dto.setDealerCode(dealerCode);
				dto.setDealerUserID(dealerUserID);
				dto.setFCAID(FCAID);
				dto.setOldDealerUserID(oldDealerUserID);
				dto.setUniquenessID(uniquenessID);
				dto.setUpdateDate(updateDate);
				resultList.add(dto);
			}
		}
		return resultList;
	}

	private StringBuffer getSql(){
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT TAUS.SALES_QUOTAS_ID, \n" );
		sql.append("       TAUS.FCA_ID, \n" );
		sql.append("       TAUS.UNIQUENESS_ID, \n" );
		sql.append("       TAUS.OLD_DEALER_USER_ID, \n" );
		sql.append("       TAUS.DEALER_USER_ID, \n" );
		sql.append("       DR.DMS_CODE AS DEALER_CODE, \n" );
		sql.append("       TAUS.UPDATE_DATE \n" );
		sql.append("   FROM TI_APP_U_SALES_QUOTAS TAUS \n" );
		sql.append("       LEFT JOIN TI_DEALER_RELATION DR ON TAUS.DEALER_CODE = DR.DCS_CODE \n" );
		sql.append("   WHERE TAUS.IS_SEND = '0' OR TAUS.IS_SEND IS NULL  OR TAUS.IS_SEND ='9' \n" );
		sql.append("  and DR.dms_code is not null");
		return sql;
	}

}
