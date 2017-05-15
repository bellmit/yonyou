package com.yonyou.dcs.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.DTO.gacfca.TiAppUSwapDto;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.utils.common.CommonUtils;

@Repository
public class TiAppUSwapDao extends OemBaseDAO {
	
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

	public LinkedList<TiAppUSwapDto> queryBodyInfo(String[] groupId) throws ParseException {
		StringBuffer sql = this.getSql();
		List<Map> mapList = OemDAOUtil.findAll(sql.toString(), null);
		LinkedList<TiAppUSwapDto> list = this.setTiAppUSwapDtoList(mapList);
		return list;
	}

	public LinkedList<TiAppUSwapDto> queryBodyInfo() throws ParseException {
		StringBuffer sql = this.getSql();
		List<Map> mapList = OemDAOUtil.findAll(sql.toString(), null);
		LinkedList<TiAppUSwapDto> list = this.setTiAppUSwapDtoList(mapList);
		return list;
	}
	
	private LinkedList<TiAppUSwapDto> setTiAppUSwapDtoList(List<Map> mapList) throws ParseException {
		LinkedList<TiAppUSwapDto> resultList = new LinkedList<TiAppUSwapDto>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(mapList != null && !mapList.isEmpty()){
			for(Map map : mapList){
				TiAppUSwapDto dto = new TiAppUSwapDto();
				Long swapId = CommonUtils.checkNull(map.get("SWAP_ID")) == "" ? null : Long.parseLong(CommonUtils.checkNull(map.get("SWAP_ID")));
				String uniquenessID = map.get("UNIQUENESS_ID") == null ? null : CommonUtils.checkNull(map.get("UNIQUENESS_ID"));//DMS客户唯一ID
			    Integer FCAID = CommonUtils.checkNull(map.get("FCA_ID")) == "" ? null : Integer.parseInt(CommonUtils.checkNull(map.get("FCA_ID")));//售中APP的客户ID
			    String ownBrandID = map.get("OWN_BRAND_ID") == null ? null : CommonUtils.checkNull(map.get("OWN_BRAND_ID"));//现有品牌
			    String ownModelID = map.get("OWN_MODEL_ID") == null ? null : CommonUtils.checkNull(map.get("OWN_MODEL_ID"));//现有车型
			    String ownCarStyleID = map.get("OWN_CAR_STYLE_ID") == null ? null : CommonUtils.checkNull(map.get("OWN_CAR_STYLE_ID"));//现有车款
			    String VINCode = map.get("VIN_CODE") == null ? null : CommonUtils.checkNull(map.get("VIN_CODE"));//现有的车辆VIN  
			    Date licenceIssueDate = CommonUtils.checkNull(map.get("LICENCELSSUE_DATE")) == "" ? null : sdf.parse(CommonUtils.checkNull(map.get("LICENCELSSUE_DATE")));//上牌时间
			    Long travlledDistance = CommonUtils.checkNull(map.get("TRAVLLED_DISTANCE")) == "" ? null : Long.parseLong(CommonUtils.checkNull(map.get("TRAVLLED_DISTANCE")));//里程数
			    Long isEstimated = CommonUtils.checkNull(map.get("IS_ESTIMATED")) == "" ? null : Long.parseLong(CommonUtils.checkNull(map.get("IS_ESTIMATED")));//是否评估
			    Long estimatedPrice = CommonUtils.checkNull(map.get("ESTIMATED_PRICE")) == "" ? null : Long.parseLong(CommonUtils.checkNull(map.get("ESTIMATED_PRICE")));//评估金额
			    String estimatedOne = map.get("ESTIMATED_ONE") == null ? null : CommonUtils.checkNull(map.get("ESTIMATED_ONE"));//评估报告1
			    String estimatedTwo = map.get("ESTIMATED_TWO") == null ? null : CommonUtils.checkNull(map.get("ESTIMATED_TWO"));//评估报告2
			    String driveLicense = map.get("DRIVE_LICENSE") == null ? null : CommonUtils.checkNull(map.get("DRIVE_LICENSE"));//旧车行驶证
			    Date updateDate = CommonUtils.checkNull(map.get("UPDATE_DATE")) == "" ? null : sdf.parse(CommonUtils.checkNull(map.get("UPDATE_DATE")));
			    String dealerUserID = map.get("DEALER_USER_ID") == null ? null : CommonUtils.checkNull(map.get("DEALER_USER_ID"));//销售人员的ID
			    String dealerCode = map.get("DEALER_CODE") == null ? null : CommonUtils.checkNull(map.get("DEALER_CODE"));
			    String ownCarColor = map.get("OWN_CAR_COLOR") == null ? null : CommonUtils.checkNull(map.get("OWN_CAR_COLOR"));//现有车色
			    
			    dto.setSwapId(swapId);
				dto.setDealerCode(dealerCode);
				dto.setDealerUserID(dealerUserID);
				dto.setDriveLicense(driveLicense);
				dto.setEstimatedOne(estimatedOne);
				dto.setEstimatedPrice(estimatedPrice);
				dto.setEstimatedTwo(estimatedTwo);
				dto.setFCAID(FCAID);
				dto.setIsEstimated(isEstimated);
				dto.setLicenceIssueDate(licenceIssueDate);
				dto.setOwnBrandID(ownBrandID);
				dto.setOwnCarColor(ownCarColor);
				dto.setOwnCarStyleID(ownCarStyleID);
				dto.setOwnModelID(ownModelID);
				dto.setTravlledDistance(travlledDistance);
				dto.setUniquenessID(uniquenessID);
				dto.setUpdateDate(updateDate);
				dto.setVINCode(VINCode);
				resultList.add(dto);
			}
		}
		return resultList;
	}

	private StringBuffer getSql(){
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT ANS.SWAP_ID, \n" );
		sql.append("       ANS.FCA_ID, \n" );
		sql.append("       ANS.UNIQUENESS_ID, \n" );
		sql.append("       ANS.OWN_BRAND_ID, \n" );
		sql.append("       ANS.OWN_MODEL_ID, \n" );
		sql.append("       ANS.OWN_CAR_STYLE_ID, \n" );
		sql.append("       ANS.OWN_CAR_COLOR, \n" );
		sql.append("       ANS.VIN_CODE, \n" );
		sql.append("       ANS.LICENCELSSUE_DATE, \n" );
		sql.append("       ANS.TRAVLLED_DISTANCE, \n" );
		sql.append("       case ANS.IS_ESTIMATED when 1 then 12781001 when 0 then 1278002 end  as IS_ESTIMATED, \n" );
		sql.append("       ANS.ESTIMATED_PRICE, \n" );
		sql.append("       ANS.ESTIMATED_ONE, \n" );
		sql.append("       ANS.ESTIMATED_TWO, \n" );
		sql.append("       ANS.DRIVE_LICENSE, \n" );
		sql.append("       ANS.DEALER_USER_ID, \n" );
		sql.append("       DR.DMS_CODE AS DEALER_CODE, \n" );
		sql.append("       IFNULL(DATE_FORMAT(ANS.UPDATE_DATE,'%Y-%m-%d %H:%i:%S'),'') as UPDATE_DATE \n" );
		sql.append("   FROM TI_APP_U_SWAP ANS \n" );
		sql.append("       LEFT JOIN TI_DEALER_RELATION DR ON ANS.DEALER_CODE = DR.DCS_CODE \n" );
		sql.append("   WHERE ANS.IS_SEND = '0' OR ANS.IS_SEND IS NULL  OR ANS.IS_SEND='9' \n" );
		sql.append("  and DR.dms_code is not null");
		return sql;
	}

}
