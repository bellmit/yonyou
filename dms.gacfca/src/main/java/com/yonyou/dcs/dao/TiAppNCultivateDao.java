package com.yonyou.dcs.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.DTO.gacfca.TiAppNCultivateDto;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.utils.common.CommonUtils;

@Repository
public class TiAppNCultivateDao extends OemBaseDAO {

	public String[] getDealerByGroupId(String[] groupID, String dealerCode) {
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

	public LinkedList<TiAppNCultivateDto> queryAppNCultivate(String[] groupId) throws ParseException {
		StringBuffer sql = this.getSql();
		List<Map> mapList = OemDAOUtil.findAll(sql.toString(), null);
		LinkedList<TiAppNCultivateDto> list = this.setTiAppNCultivateDtoList(mapList);
		return list;
	}

	public LinkedList<TiAppNCultivateDto> queryAppNCultivate() throws ParseException {
		StringBuffer sql = this.getSql();
		List<Map> mapList = OemDAOUtil.findAll(sql.toString(), null);
		LinkedList<TiAppNCultivateDto> list = this.setTiAppNCultivateDtoList(mapList);
		return list;
	}
	
	private LinkedList<TiAppNCultivateDto> setTiAppNCultivateDtoList(List<Map> list) throws ParseException {
		LinkedList<TiAppNCultivateDto> resultList = new LinkedList<TiAppNCultivateDto>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(list != null && !list.isEmpty()){
			for(Map map : list){
				TiAppNCultivateDto dto = new TiAppNCultivateDto();
				String entityCode = map.get("DEALER_CODE") == null ? null : String.valueOf(map.get("DEALER_CODE"));
			    Long fcaId = map.get("FCA_ID") == null ? null : Long.parseLong(String.valueOf(map.get("FCA_ID")));
			    String uniquenessID = map.get("UNIQUENESS_ID") == null ? null : String.valueOf(map.get("UNIQUENESS_ID"));
			    Date commDate = map.get("COMM_DATE") == null ? null : sdf.parse(String.valueOf(map.get("COMM_DATE")));
			    String commContent = map.get("COMM_CONTENT") == null ? null : String.valueOf(map.get("COMM_CONTENT"));
			    String dealerCode = map.get("DEALER_CODE") == null ? null : String.valueOf(map.get("DEALER_CODE"));
			    String nextCommContent = map.get("NEXT_COMM_CONTENT") == null ? null : String.valueOf(map.get("NEXT_COMM_CONTENT"));
			    Date nextCommDate = map.get("NEXT_COMM_DATE") == null ? null : sdf.parse(String.valueOf(map.get("NEXT_COMM_DATE")));
			    String commType = map.get("COMM_TYPE") == null ? null : String.valueOf(map.get("COMM_TYPE"));
			    Date createDate = map.get("CREATE_DATE") == null ? null : sdf.parse(String.valueOf(map.get("CREATE_DATE")));
			    String followOppLevelId = map.get("FOLLOW_OPP_LEVEL_ID") == null ? null : String.valueOf(map.get("FOLLOW_OPP_LEVEL_ID"));
			    String dealerUserId = map.get("DEALER_USER_ID") == null ? null : String.valueOf(map.get("DEALER_USER_ID"));
			    Long cultivateId = map.get("CULTIVATE_ID") == null ? null : Long.parseLong(String.valueOf(map.get("CULTIVATE_ID")));
				    
				dto.setCommContent(commContent);
				dto.setCommDate(commDate);
				dto.setCommType(commType);
				dto.setCreateDate(createDate);
				dto.setDealerCode(dealerCode);
				dto.setDealerUserId(dealerUserId);
				dto.setFcaId(fcaId);
				dto.setFollowOppLevelId(followOppLevelId);
				dto.setNextCommContent(nextCommContent);
				dto.setNextCommDate(nextCommDate);
				dto.setUniquenessID(uniquenessID);
				dto.setEntityCode(entityCode);
				dto.setCultivateId(cultivateId);
				resultList.add(dto);
			}
		}
		return resultList;
	}

	private StringBuffer getSql(){
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT ANC.CULTIVATE_ID, \n" );
		sql.append("       ANC.FCA_ID, \n" );
		sql.append("       DATE_FORMAT(ANC.COMM_DATE,'%Y-%m-%d %H:%i:%S') AS COMM_DATE, \n" );
		sql.append("       ANC.COMM_TYPE, \n" );
		sql.append("       ANC.COMM_CONTENT, \n" );
		sql.append("       ANC.FOLLOW_OPP_LEVEL_ID, \n" );
		sql.append("       DATE_FORMAT(ANC.NEXT_COMM_DATE,'%Y-%m-%d %H:%i:%S') AS NEXT_COMM_DATE, \n" );
		sql.append("       ANC.NEXT_COMM_CONTENT, \n" );
		sql.append("       ANC.DEALER_USER_ID, \n" );
		sql.append("       ANC.UNIQUENESS_ID, \n" );
		sql.append("       DR.DMS_CODE AS DEALER_CODE, \n" );
		sql.append("       DATE_FORMAT(ANC.CREATE_DATE,'%Y-%m-%d %H:%i:%S') AS CREATE_DATE \n" );
		sql.append("   FROM TI_APP_N_CULTIVATE ANC \n" );
		sql.append("       LEFT JOIN TI_DEALER_RELATION DR ON ANC.DEALER_CODE = DR.DCS_CODE \n" );
		sql.append("   WHERE ANC.IS_SEND = '0' OR ANC.IS_SEND IS NULL  OR ANC.IS_SEND = '9' \n" );
		sql.append("  and DR.dms_code is not null");
		return sql;
	}

}
