package com.yonyou.dcs.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.DTO.gacfca.TiAppUCultivateDto;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.utils.common.CommonUtils;

/**
 * 更新客户信息（客户培育）APP更新
 * @author luoyang
 *
 */
@Repository
public class TiAppUCultivateDao extends OemBaseDAO {

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

	public LinkedList<TiAppUCultivateDto> queryBodyInfo(String[] groupId) throws ParseException {
		StringBuffer sql = this.getSql();
		List<Map> mapList = OemDAOUtil.findAll(sql.toString(), null);
		LinkedList<TiAppUCultivateDto> list = this.setTiAppUCultivateDtoList(mapList);
		return list;
	}

	public LinkedList<TiAppUCultivateDto> queryBodyInfo() throws ParseException {
		StringBuffer sql = this.getSql();
		List<Map> mapList = OemDAOUtil.findAll(sql.toString(), null);
		LinkedList<TiAppUCultivateDto> list = this.setTiAppUCultivateDtoList(mapList);
		return list;
	}
	
	private LinkedList<TiAppUCultivateDto> setTiAppUCultivateDtoList(List<Map> mapList) throws ParseException {
		LinkedList<TiAppUCultivateDto> resultList = new LinkedList<TiAppUCultivateDto>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(mapList != null && !mapList.isEmpty()){
			for(Map map : mapList){
				TiAppUCultivateDto dto = new TiAppUCultivateDto();
				Long cultivateId = CommonUtils.checkNull(map.get("CULTIVATE_ID")) == "" ? null : Long.parseLong(CommonUtils.checkNull(map.get("CULTIVATE_ID")));
				String uniquenessID = map.get("UNIQUENESS_ID") == null ? null : CommonUtils.checkNull(map.get("UNIQUENESS_ID"));
				Integer FCAID = CommonUtils.checkNull(map.get("FCA_ID")) == "" ? null : Integer.parseInt(CommonUtils.checkNull(map.get("FCA_ID")));
				Date commData = CommonUtils.checkNull(map.get("COMM_DATE")) == "" ? null : sdf.parse(CommonUtils.checkNull(map.get("COMM_DATE")));
				String commType = map.get("COMM_TYPE") == null ? null : CommonUtils.checkNull(map.get("COMM_TYPE"));
				String commContent = map.get("COMM_CONTENT") == null ? null : CommonUtils.checkNull(map.get("COMM_CONTENT"));
				String followOppLevelID = map.get("FOLLOW_OPP_LEVEL_ID") == null ? null : CommonUtils.checkNull(map.get("FOLLOW_OPP_LEVEL_ID"));
				Date nextCommDate = CommonUtils.checkNull(map.get("NEXT_COMM_DATE")) == "" ? null : sdf.parse(CommonUtils.checkNull(map.get("NEXT_COMM_DATE")));
				String nextCommContent = map.get("NEXT_COMM_CONTENT") == null ? null : CommonUtils.checkNull(map.get("NEXT_COMM_CONTENT"));
				String dealerUserID = map.get("DEALER_USER_ID") == null ? null : CommonUtils.checkNull(map.get("DEALER_USER_ID"));
				String dealerCode = map.get("NEXT_COMM_CONTENT") == null ? null : CommonUtils.checkNull(map.get("NEXT_COMM_CONTENT"));
				Date updateDate = CommonUtils.checkNull(map.get("CREATE_DATE")) == "" ? null : sdf.parse(CommonUtils.checkNull(map.get("CREATE_DATE")));
				
				dto.setCultivateId(cultivateId);
				dto.setCommContent(commContent);
				dto.setCommData(commData);
				dto.setCommType(commType);
				dto.setDealerCode(dealerCode);
				dto.setDealerUserID(dealerUserID);
				dto.setFCAID(FCAID);
				dto.setFollowOppLevelID(followOppLevelID);
				dto.setNextCommContent(nextCommContent);
				dto.setNextCommDate(nextCommDate);
				dto.setUniquenessID(uniquenessID);
				dto.setUpdateDate(updateDate);
				resultList.add(dto);
			}
		}
		return resultList;
	}

	private StringBuffer getSql(){
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT ANC.CULTIVATE_ID, \n" );
		sql.append("       ANC.FCA_ID, \n" );
		sql.append("       ANC.UNIQUENESS_ID, \n" );
		sql.append("       ANC.COMM_DATE, \n" );
		sql.append("       ANC.COMM_TYPE, \n" );
		sql.append("       ANC.COMM_CONTENT, \n" );
		sql.append("       ANC.FOLLOW_OPP_LEVEL_ID, \n" );
		sql.append("       ANC.NEXT_COMM_DATE, \n" );
		sql.append("       ANC.NEXT_COMM_CONTENT, \n" );
		sql.append("       ANC.DEALER_USER_ID, \n" );
		sql.append("       DR.DMS_CODE AS DEALER_CODE, \n" );
		sql.append("       ANC.UPDATE_DATE \n" );
		sql.append("   FROM TI_APP_U_CULTIVATE ANC \n" );
		sql.append("       LEFT JOIN TI_DEALER_RELATION DR ON ANC.DEALER_CODE = DR.DCS_CODE \n" );
		sql.append("   WHERE ANC.IS_SEND = '0' OR ANC.IS_SEND IS NULL OR ANC.IS_SEND ='9' \n" );
		return sql;
		
	}

}
