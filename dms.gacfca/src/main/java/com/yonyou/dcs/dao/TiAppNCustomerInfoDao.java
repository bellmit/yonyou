package com.yonyou.dcs.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.DTO.gacfca.TiAppNCustomerInfoDto;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.CommonUtils;

@Repository
public class TiAppNCustomerInfoDao extends OemBaseDAO {
	
	public LinkedList<TiAppNCustomerInfoDto> queryAppNCustomerInfo() throws ParseException{
		StringBuffer sql = getSql();
		List<Map> mapList = OemDAOUtil.findAll(sql.toString(), null);
		LinkedList<TiAppNCustomerInfoDto> list = setTiAppNCustomerInfoDtoList(mapList);
		return list;
		
	}

	private LinkedList<TiAppNCustomerInfoDto> setTiAppNCustomerInfoDtoList(List<Map> list) throws ParseException {
		LinkedList<TiAppNCustomerInfoDto> resultList = new LinkedList<TiAppNCustomerInfoDto>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(list != null && !list.isEmpty()){
			for(Map<String,Object> map : list){
				TiAppNCustomerInfoDto dto = new TiAppNCustomerInfoDto();
				String dealerCode = map.get("DEALER_CODE") == null ? null : String.valueOf(map.get("DEALER_CODE"));
				Long customerId = map.get("CUSTOMER_ID") == null ? null : Long.parseLong(String.valueOf(map.get("CUSTOMER_ID")));
				Long fcaId = map.get("FCA_ID") == null ? null : Long.parseLong(String.valueOf(map.get("FCA_ID")));
				String clientType = map.get("CLIENT_TYPE") == null ? null : String.valueOf(map.get("CLIENT_TYPE"));
				String name = map.get("NAME") == null ? null : String.valueOf(map.get("NAME"));
				String gender = map.get("GENDER") == null ? null : String.valueOf(map.get("GENDER"));
				String phone = map.get("PHOME") == null ? null : String.valueOf(map.get("PHOME"));
				String telephone = map.get("TELEPHONE") == null ? null : String.valueOf(map.get("TELEPHONE"));
				Integer provinceId = map.get("PROVINCE_ID") == null ? null : Integer.parseInt(String.valueOf(map.get("PROVINCE_ID")));
				Integer cityId = map.get("CITY_ID") == null ? null : Integer.parseInt(String.valueOf(map.get("CITY_ID")));
				Date birthday = map.get("BIRTHDAY") == null ? null : sdf.parse(String.valueOf(map.get("BIRTHDAY")));
				String oppLevelId = map.get("OPP_LEVEL_ID") == null ? null : String.valueOf(map.get("OPP_LEVEL_ID"));
				String sourceType = map.get("SOURCE_TYPE") == null ? null : String.valueOf(map.get("SOURCE_TYPE"));
				String secondSourceType = map.get("SECOND_SOURCE_TYPE") == null ? null : String.valueOf(map.get("SECOND_SOURCE_TYPE"));
				String dealerUserId = map.get("DEALER_USER_ID") == null ? null : String.valueOf(map.get("DEALER_USER_ID"));
				String buyCarBugget = map.get("BUY_CAR_BUGGET") == null ? null : String.valueOf(map.get("BUY_CAR_BUGGET"));
				String brandId = map.get("BRAND_ID") == null ? null : String.valueOf(map.get("BRAND_ID"));
				String modelId = map.get("MODEL_ID") == null ? null : String.valueOf(map.get("MODEL_ID"));
				String carStyleId = map.get("CAR_STYLE_ID") == null ? null : String.valueOf(map.get("CAR_STYLE_ID"));
				String intentCarColor = map.get("INTENT_CAR_COLOR") == null ? null : String.valueOf(map.get("INTENT_CAR_COLOR"));
				Integer buyCarcondition = map.get("BUY_CARCONDITION") == null ? null : Integer.parseInt(String.valueOf(map.get("BUY_CARCONDITION")));
				String giveUpType = map.get("GIVE_UP_TYPE") == null ? null : String.valueOf(map.get("GIVE_UP_TYPE"));
				String giveUpCause = map.get("GIVE_UP_REASON") == null ? null : String.valueOf(map.get("GIVE_UP_REASON"));
				String contendCar = map.get("CONTEND_CAR") == null ? null : String.valueOf(map.get("CONTEND_CAR"));
				Date giveUpDate = map.get("GIVE_UP_DATE") == null ? null : sdf.parse(String.valueOf(map.get("GIVE_UP_DATE")));
				Date createDate = map.get("CREATE_DATE") == null ? null : sdf.parse(String.valueOf(map.get("CREATE_DATE")));
				Integer isToShop = map.get("IS_TO_SHOP") == null ? null : Integer.parseInt((String.valueOf(map.get("IS_TO_SHOP"))));
				Date timeToShop = map.get("TIME_TO_SHOP") == null ? null : sdf.parse(String.valueOf(map.get("TIME_TO_SHOP")));
				
				dto.setDealerCode(dealerCode);
				dto.setCustomerId(customerId);
				dto.setFcaId(fcaId);
				dto.setClientType(clientType);
				dto.setName(name);
				dto.setGender(gender);
				dto.setPhone(phone);
				dto.setTelephone(telephone);
				dto.setProvinceId(provinceId);
				dto.setCityId(cityId);
				dto.setBirthday(birthday);
				dto.setOppLevelId(oppLevelId);
				dto.setSourceType(sourceType);
				dto.setSecondSourceType(secondSourceType);
				dto.setDealerUserId(dealerUserId);
				dto.setBuyCarBugget(buyCarBugget);
				dto.setBrandId(brandId);
				dto.setModelId(modelId);
				dto.setCarStyleId(carStyleId);
				dto.setIntentCarColor(intentCarColor);
				dto.setBuyCarcondition(buyCarcondition);
				dto.setGiveUpType(giveUpType);
				dto.setGiveUpCause(giveUpCause);
				dto.setContendCar(contendCar);
				dto.setGiveUpDate(giveUpDate);
				dto.setCreateDate(createDate);
				dto.setIsToShop(isToShop);
				dto.setTimeToShop(timeToShop);
				resultList.add(dto);
			}
		}
		return resultList;
	}
	
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

	public LinkedList<TiAppNCustomerInfoDto> queryAppNCustomerInfo(String[] groupIds) throws ParseException {
		StringBuffer sql = getSql();
		if(null!=groupIds && groupIds.length>0){
			sql.append(" AND  VMG_CARSTYLE.GROUP_ID in (");
			for(int i=0;i< groupIds.length ; i++){
				if( (i+1) == groupIds.length){
					sql.append(" '"+groupIds[i]+"' ");
				}else{
					sql.append(" '"+groupIds[i]+"', ");
				}
			}
			sql.append(" ) ");
		}
		List<Map> mapList = OemDAOUtil.findAll(sql.toString(), null);
		LinkedList<TiAppNCustomerInfoDto> list = setTiAppNCustomerInfoDtoList(mapList);
		return list;
	}

	private StringBuffer getSql() {
		StringBuffer sql = new StringBuffer();
		sql.append(" \n");
		sql.append("SELECT ANCI.CUSTOMER_ID, \n");
		sql.append("       ANCI.FCA_ID, \n");
		sql.append("       ANCI.CLIENT_TYPE, \n");
		sql.append("       ANCI.NAME, \n");
		sql.append("       ANCI.GENDER, \n");
		sql.append("       ANCI.PHOME, \n");
		sql.append("       ANCI.TELEPHONE, \n");
		sql.append("       (SELECT R.REGION_CODE FROM TM_REGION R WHERE R.REGION_ID = ANCI.PROVINCE_ID LIMIT 1 ) AS PROVINCE_ID, \n");
		sql.append("       (SELECT TR.REGION_CODE FROM TM_REGION TR WHERE TR.REGION_ID = ANCI.CITY_ID LIMIT 1 ) AS CITY_ID, \n");
		sql.append("       ANCI.BIRTHDAY, \n");
		sql.append("       ANCI.OPP_LEVEL_ID, \n");
		sql.append("       ANCI.SOURCE_TYPE, \n");
		sql.append("       DR.DMS_CODE AS DEALER_CODE, \n");
		sql.append("       ANCI.DEALER_USER_ID, \n");
		sql.append("       ANCI.BUY_CAR_BUGGET, \n");
		sql.append("       VMG_BRAND.GROUP_CODE AS BRAND_ID, \n");
		sql.append("       VMG_MODEL.GROUP_CODE AS MODEL_ID, \n");
		sql.append("       VMG_CARSTYLE.GROUP_CODE AS CAR_STYLE_ID, \n");
		sql.append("       ANCI.INTENT_CAR_COLOR, \n");
		sql.append("       ANCI.BUY_CARCONDITION, \n");
		sql.append("       ANCI.GIVE_UP_TYPE, \n");
		sql.append("       ANCI.GIVE_UP_REASON, \n");
		sql.append("       ANCI.CONTEND_CAR, \n");
		sql.append("       ANCI.SECOND_SOURCE_TYPE, \n");
		sql.append("       DATE_FORMAT (ANCI.GIVE_UP_DATE, '%Y-%m-%d %H:%i:%S') AS GIVE_UP_DATE, \n");
		sql.append("       DATE_FORMAT (ANCI.CREATE_DATE, '%Y-%m-%d %H:%i:%S') AS CREATE_DATE, \n");
		sql.append("       ANCI.IS_TO_SHOP, \n");
		sql.append("       DATE_FORMAT (ANCI.TIME_TO_SHOP, '%Y-%m-%d %H:%i:%S') AS TIME_TO_SHOP \n");
		sql.append("  FROM TI_APP_N_CUSTOMER_INFO ANCI \n");
		sql.append("  LEFT JOIN TI_DEALER_RELATION DR ON ANCI.DEALER_CODE = DR.DCS_CODE \n");
		sql.append("  LEFT JOIN TM_VHCL_MATERIAL_GROUP VMG_BRAND ON ANCI.BRAND_ID = VMG_BRAND.TREE_CODE \n");
		sql.append("   AND VMG_BRAND.STATUS = '" + OemDictCodeConstants.STATUS_ENABLE + "' \n");
		sql.append("   AND VMG_BRAND.GROUP_LEVEL = 1 \n");
		sql.append("   AND VMG_BRAND.IS_DEL = 0 \n");
		sql.append("  LEFT JOIN TM_VHCL_MATERIAL_GROUP VMG_MODEL ON ANCI.MODEL_ID = VMG_MODEL.TREE_CODE \n");
		sql.append("   AND VMG_MODEL.STATUS = '" + OemDictCodeConstants.STATUS_ENABLE + "' \n");
		sql.append("   AND VMG_MODEL.GROUP_LEVEL = 2 \n");
		sql.append("   AND VMG_MODEL.IS_DEL = 0 \n");
		sql.append("  LEFT JOIN TM_VHCL_MATERIAL_GROUP VMG_CARSTYLE ON ANCI.CAR_STYLE_ID = VMG_CARSTYLE.TREE_CODE \n");
		sql.append("   AND VMG_CARSTYLE.STATUS = '" + OemDictCodeConstants.STATUS_ENABLE + "' \n");
		sql.append("   AND VMG_CARSTYLE.GROUP_LEVEL = 4 \n");
		sql.append("   AND VMG_CARSTYLE.IS_DEL = 0 \n");
		sql.append(" WHERE ANCI.IS_SEND = '0' OR ANCI.IS_SEND IS NULL OR ANCI.IS_SEND = '9' \n");
		sql.append("   AND DR.DMS_CODE IS NOT NULL \n");
		return sql;
	}

}
