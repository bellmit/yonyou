package com.yonyou.dcs.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.DTO.gacfca.TiAppNSwapDto;
import com.yonyou.dms.DTO.gacfca.TiAppNTestDriveDto;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.utils.common.CommonUtils;

@Repository
public class TiAppNSwapDao extends OemBaseDAO {
	
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

	/**
	 * 包含groupIds
	 * @param groupIds
	 * @return
	 * @throws ParseException
	 */
	public LinkedList<TiAppNSwapDto> queryAppNSwap(String[] groupIds) throws ParseException {
		StringBuffer sql = this.getSql();
		List<Map> mapList = OemDAOUtil.findAll(sql.toString(), null);
		LinkedList<TiAppNSwapDto> list = this.setTiAppNSwapDtoList(mapList);
		return list;
	}

	/**
	 * 不包含groupIds
	 * @return
	 * @throws ParseException
	 */
	public LinkedList<TiAppNSwapDto> queryAppNSwap() throws ParseException {
		StringBuffer sql = this.getSql();
		List<Map> mapList = OemDAOUtil.findAll(sql.toString(), null);
		LinkedList<TiAppNSwapDto> list = this.setTiAppNSwapDtoList(mapList);
		return list;
	}
	
	private LinkedList<TiAppNSwapDto> setTiAppNSwapDtoList(List<Map> list) throws ParseException {
		LinkedList<TiAppNSwapDto> resultList = new LinkedList<TiAppNSwapDto>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(list != null && !list.isEmpty()){
			for(Map map : list){
				TiAppNSwapDto dto = new TiAppNSwapDto();
				String dealerCode = map.get("DEALER_CODE") == null ? null : String.valueOf(map.get("DEALER_CODE"));			
				Long swapId = map.get("SWAP_ID") == null ? null : Long.parseLong(String.valueOf(map.get("SWAP_ID")));	
				Long fcaId = map.get("FCA_ID") == null ? null : Long.parseLong(String.valueOf(map.get("FCA_ID")));				
				Date licencelssueDate = map.get("LICENCELSSUE_DATE") == null ? null : sdf.parse(String.valueOf(map.get("LICENCELSSUE_DATE")));				
				String estimatedTwo = map.get("ESTIMATED_ONE") == null ? null : String.valueOf(map.get("ESTIMATED_ONE"));		
				String estimatedOne = map.get("ESTIMATED_TWO") == null ? null : String.valueOf(map.get("ESTIMATED_TWO"));			
				String driverLicense = map.get("DRIVE_LICENSE") == null ? null : String.valueOf(map.get("DRIVE_LICENSE"));				
				String ownBrandId = map.get("OWN_BRAND_ID") == null ? null : String.valueOf(map.get("OWN_BRAND_ID")); 		
				String ownModelId = map.get("OWN_MODEL_ID") == null ? null : String.valueOf(map.get("OWN_MODEL_ID")); 		
				String ownCarStyleId = map.get("OWN_CAR_STYLE_ID") == null ? null : String.valueOf(map.get("OWN_CAR_STYLE_ID")); 	
				Long isEstimated = map.get("IS_ESTIMATED") == null ? null : Long.parseLong(String.valueOf(map.get("IS_ESTIMATED"))); 		
				String vinCode = map.get("VIN_CODE") == null ? null : String.valueOf(map.get("VIN_CODE")); 				
				Long travlledDistance = map.get("TRAVLLED_DISTANCE") == null ? null : Long.parseLong(String.valueOf(map.get("TRAVLLED_DISTANCE"))); 			
				Long estimeedPrice = map.get("ESTIMATED_PRICE") == null ? null : Long.parseLong(String.valueOf(map.get("ESTIMATED_PRICE"))); 	
				Date createDate = map.get("CREATE_DATE") == null ? null : sdf.parse(String.valueOf(map.get("CREATE_DATE"))); 			
				String ownCarColor = map.get("DEALER_CODE") == null ? null : String.valueOf(map.get("DEALER_CODE")); 		
				String dealerUserId = map.get("DEALER_USER_ID") == null ? null : String.valueOf(map.get("DEALER_USER_ID")); 			
				String uniquenessID = map.get("UNIQUENESS_ID") == null ? null : String.valueOf(map.get("UNIQUENESS_ID"));
				
				dto.setSwapId(swapId);
				dto.setCreateDate(createDate);
				dto.setDealerCode(dealerCode);
				dto.setDealerUserId(dealerUserId);
				dto.setDriverLicense(driverLicense);
				dto.setEstimatedOne(estimatedOne);
				dto.setEstimatedTwo(estimatedTwo);
				dto.setEstimeedPrice(estimeedPrice);
				dto.setFcaId(fcaId);
				dto.setIsEstimated(isEstimated);
				dto.setLicencelssueDate(licencelssueDate);
				dto.setOwnBrandId(ownBrandId);
				dto.setOwnCarColor(ownCarColor);
				dto.setOwnCarStyleId(ownCarStyleId);
				dto.setOwnModelId(ownModelId);
				dto.setTravlledDistance(travlledDistance);
				dto.setUniquenessID(uniquenessID);
				dto.setVinCode(vinCode);
				resultList.add(dto);
				
			}
		}
		return resultList;
	}
	
	private StringBuffer getSql(){
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT ANS.SWAP_ID, \n" );
		sql.append("       ANS.FCA_ID, \n" );
		sql.append("       ANS.OWN_BRAND_ID, \n" );
		sql.append("       ANS.OWN_MODEL_ID, \n" );
		sql.append("       ANS.OWN_CAR_STYLE_ID, \n" );
		sql.append("       ANS.OWN_CAR_COLOR, \n" );
		sql.append("       ANS.VIN_CODE, \n" );
		sql.append("       DATE_FORMAT(ANS.LICENCELSSUE_DATE,'%Y-%m-%d %H:%i:%S') AS LICENCELSSUE_DATE, \n" );
		sql.append("       ANS.TRAVLLED_DISTANCE, \n" );
		sql.append("       case ANS.IS_ESTIMATED when 1 then 12781001 when 0 then 1278002 end  as IS_ESTIMATED, \n" );
		sql.append("       ANS.ESTIMATED_PRICE, \n" );
		sql.append("       ANS.ESTIMATED_ONE, \n" );
		sql.append("       ANS.ESTIMATED_TWO, \n" );
		sql.append("       ANS.DRIVE_LICENSE, \n" );
		sql.append("       ANS.DEALER_USER_ID, \n" );
		sql.append("       ANS.UNIQUENESS_ID, \n" );
		sql.append("       DR.DMS_CODE AS DEALER_CODE, \n" );
		sql.append("       DATE_FORMAT(ANS.CREATE_DATE,'%Y-%m-%d %H:%i:%S') AS CREATE_DATE \n" );
		sql.append("   FROM TI_APP_N_SWAP ANS \n" );
		sql.append("       LEFT JOIN TI_DEALER_RELATION DR ON ANS.DEALER_CODE = DR.DCS_CODE \n" );
		sql.append("   WHERE ANS.IS_SEND = '0' OR ANS.IS_SEND IS NULL  OR ANS.IS_SEND = '9' \n" );
		sql.append("  and DR.dms_code is not null");
		return sql;	
	}

}
